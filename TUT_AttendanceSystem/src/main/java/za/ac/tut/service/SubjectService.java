/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.tut.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.tut.entity.LecturerSubject;
import za.ac.tut.entity.StudSubject;
import za.ac.tut.entity.Student;
import za.ac.tut.entity.Subject;
import za.ac.tut.repository.LecturerSubjectRepository;
import za.ac.tut.repository.StudSubjectRepository;
import za.ac.tut.repository.StudentRepository;
import za.ac.tut.repository.SubjectRespository;

/**
 *
 * @author fistos
 */
@Service
public class SubjectService {

    @Autowired
    private SubjectRespository subjectRespository;
    @Autowired
    private StudSubjectRepository studSubjectRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private LecturerSubjectRepository lecturerSubjectRepository;

    public HashMap saveSubject(Subject subject) {
        HashMap response = new HashMap();
        String message = "This subject is already in the database";
        String status = "FAILED";

        //Read number of subjects in the database before inserting 1 record
        List<Subject> allSubjects = getAllSubjects();
        int numberBefore = allSubjects.size();

        //Check if this student is not registered already
        Subject tempSubject = subjectRespository.findBySubjectCode(subject.getSubjectCode());

        if (tempSubject == null) {
            subject = subjectRespository.save(subject);
            message = "Subject [ " + subject.getSubjectCode() + " ][ " + subject.getSubjectDesc() + " ] is successfully added";
        }

        //Read number of subjects in the database after inserting 1 record
        allSubjects = getAllSubjects();
        int numberAfter = allSubjects.size();

        //check if the records have increased 
        if (numberAfter > numberBefore) {
            status = "REGISTERED";
        }

        response.put("status", status);
        response.put("message", message);

        return response;
    }

    public HashMap retrieveSubjects(String sessionID) {

        HashMap response = new HashMap();
        String message = "This operation has failed";
        String status = "FAILED";

        String StoredSessionID = (String) AdminService.usersIn.get(sessionID);

        if (StoredSessionID != null) {
            if (StoredSessionID.equals(sessionID)) {
                
                List<Subject> allSubjects = getAllSubjects();
                status = "OK";
                message = allSubjects.size() + " registered subjects found";
                response.put("subjects", allSubjects);

            }
        }
    
    response.put ("status", status);
    response.put ("message", message);
    return response ;
}

public HashMap deleteSubject(String code, String sessionID) {
        HashMap response = new HashMap();
        String message = "This operation has failed";
        String status = "FAILED";

        String StoredSessionID = (String) AdminService.usersIn.get(sessionID);
        if (StoredSessionID.equals(sessionID)) {

            List<Student> subjectStudents = getSubjectStudents(code);
          
            message = "Subject cannot be removed, "+subjectStudents.size()+" student(s) are registered this subject."
                    + " Delete students who are registered under this subject first.";
            
            if(subjectStudents.isEmpty()){
               
                subjectRespository.deleteBySubjectCode(code);

                status = "OK";
                message = "Course record is successfully deleted";
            }
            
            List<Subject> allSubjects = getAllSubjects();
            
            response.put("subjects", allSubjects);
        }

        response.put("status", status);
        response.put("message", message);

        return response;
    }

    public HashMap updateSubject(Subject subject, String sessionID) {
        HashMap response = new HashMap();
        String message = "This operation has failed";
        String status = "FAILED";

        String StoredSessionID = (String) AdminService.usersIn.get(sessionID);

        Subject tempSubject = subjectRespository.findBySubjectCode(subject.getSubjectCode());

        if (tempSubject != null) {

            if (StoredSessionID.equals(sessionID)) {

                subjectRespository.save(subject);
                status = "OK";
                message = "Lecturer's record is updated";

                List<Subject> allSubjects = getAllSubjects();
                response.put("subjects", allSubjects);
            }

        }

        response.put("status", status);
        response.put("message", message);

        return response;
    }

    public HashMap findStudentSubjects(Long studentNumber, String sessionID) {
        HashMap response = new HashMap();
        String status = "FAILED";
        List<StudSubject> subjects = null;
        String StoredSessionID = (String) AdminService.usersIn.get(sessionID);

        if (StoredSessionID != null) {

            if (StoredSessionID.equals(sessionID)) {

                subjects = studSubjectRepository.findByStudentNumber(studentNumber);

                status = "OK";

            }

        }

        response.put("status", status);
        response.put("subjects", subjects);

        return response;
    }

    private List<Subject> getAllSubjects() {

        List<Subject> subjects = new ArrayList<>();
        subjectRespository.findAll().forEach(subjects::add);

        return subjects;
    }

    public List<Student> getSubjectStudents(String code) {
               
        List<StudSubject> subjectStudents = studSubjectRepository.findBySubjectCode(code);
        List<Long> studentNumbers = new ArrayList<>();
        
        List<Student> students = new ArrayList<>();
        
        
        for (int i = 0; i < subjectStudents.size(); i++) {
            Long studentNumber = subjectStudents.get(i).getStudentNumber();
            
            boolean isUnique = true;
            
            for (int b = 0; b < studentNumbers.size(); b++) {
                
                if (studentNumber.equals(studentNumbers.get(b))) {
                    isUnique = false;
                }
            }
            
            if (isUnique) {
                studentNumbers.add(studentNumber);
                
                Student student = studentRepository.findByStudentNumber(studentNumber);
                
                if(student != null)
                students.add(student);
            }
            
        }
        return students;
    }

    public HashMap findLecturerSubjects(Long userNumber, String sessionID) {
    
        HashMap response = new HashMap();
        String status = "FAILED";
        List<Subject> subjects = new ArrayList<>();
        String StoredSessionID = (String) AdminService.usersIn.get(sessionID);

        if (StoredSessionID != null) {

            if (StoredSessionID.equals(sessionID)) {
                List<LecturerSubject> subs = lecturerSubjectRepository.findByLecturerNumber(userNumber);
                
                for (int i = 0; i < subs.size(); i++) {
                    LecturerSubject lecturerSubject = subs.get(i);
                    Subject subject = subjectRespository.findBySubjectCode(lecturerSubject.getSubjectCode());
                    subjects.add(subject);
                }
                status = "OK";
                System.out.println(subjects);
            }
        }

        response.put("status", status);
        response.put("subjects", subjects);

        return response;
        
    }

}
