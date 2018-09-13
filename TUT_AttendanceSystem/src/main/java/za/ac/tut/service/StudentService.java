/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.tut.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.tut.entity.StudSubject;
import za.ac.tut.entity.Student;
import za.ac.tut.entity.StudentAttendanceSheet;
import za.ac.tut.repository.StudAttendanceSheetRepository;
import za.ac.tut.repository.StudSubjectRepository;
import za.ac.tut.repository.StudentRepository;

/**
 *
 * @author Mmetsa
 */
@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudSubjectRepository studSubjectRepository;
    @Autowired
    private StudAttendanceSheetRepository sasRepository;

    public HashMap saveStudent(String studentData) throws JSONException {
        HashMap response = new HashMap();

        //Extract student data
        JSONObject studData = new JSONObject(studentData);

        Long studentNumber = studData.getLong("studentNumber");
        String idNumber = String.valueOf(studData.getLong("idNumber"));
        String lastname = studData.getString("lastname");
        String firstname = studData.getString("firstname");
        String cellnumber = studData.getString("cellnumber");
        String email = studData.getString("email");
        String address = studData.getString("address");
        String courseCode = studData.getString("courseCode");
        byte[] thumb = null; //studData.getString("thumb");

        Student student = new Student(studentNumber, idNumber, lastname, firstname, email, cellnumber, email, address, courseCode, thumb);

        String message = "Student is already registered. You can't register same person twice";
        String status = "FAILED";

        //Read number of students in the database before inserting 1 record
        List<Student> allStudents = getAllStudents();
        int numberBefore = allStudents.size();

        if (Character.getNumericValue(idNumber.charAt(6)) < 5) {
            student.setGender("Female");
        } else {
            student.setGender("Male");
        }

        //Check if this student is not registered already
        Student tempStudent = studentRepository.findByStudentNumber(student.getStudentNumber());

        Student idStudent = studentRepository.findByIdNumber(idNumber);

        if (idStudent != null) {

            message = "This ID Number has already registered on the sytem - You can't register same person registered twice";
        }

        if (tempStudent == null && idStudent == null) {
            Student savedStudent = studentRepository.save(student);

            JSONArray jsonArray = studData.getJSONArray("studSubject");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                studSubjectRepository.save(new StudSubject(obj.getString("subjectCode"), studentNumber));

            }

            message = "Student is successfully registered store this student on the fingerprint system using this ID =>'" + savedStudent.getId() + "'";
        }

        //Read number of students in the database after inserting 1 record
        allStudents = getAllStudents();
        int numberAfter = allStudents.size();

        //check if the records have increased 
        if (numberAfter > numberBefore) {
            status = "REGISTERED";
        }

        response.put("status", status);
        response.put("message", message);

        return response;
    }

    List<Student> getAllStudents() {

        List<Student> students = new ArrayList<>();
        studentRepository.findAll().forEach(students::add);

        return students;
    }

    public HashMap retrieveAllStudents(String sessionID) {

        HashMap response = new HashMap();
        String message = "This operation has failed";
        String status = "FAILED";

        String StoredSessionID = (String) AdminService.usersIn.get(sessionID);

        if (StoredSessionID.equals(sessionID)) {
            List<Student> allStudents = getAllStudents();
            status = "OK";
            message = allStudents.size() + " registered students found";
            response.put("students", allStudents);
        }

        response.put("status", status);
        response.put("message", message);
        return response;
    }

    public HashMap updateStudent(String studentData, String sessionID) throws JSONException {

        HashMap response = new HashMap();
        String message = "This operation has failed";
        String status = "FAILED";

        String StoredSessionID = (String) AdminService.usersIn.get(sessionID);

        //Extract student data
        JSONObject studData = new JSONObject(studentData);

        Long id = studData.getLong("id");
        Long studentNumber = studData.getLong("studentNumber");
        String idNumber = String.valueOf(studData.getLong("idNumber"));
        String lastname = studData.getString("lastname");
        String firstname = studData.getString("firstname");
        String cellnumber = studData.getString("cellnumber");
        String gender = studData.getString("gender");
        String email = studData.getString("email");
        String address = studData.getString("address");
        String courseCode = studData.getString("courseCode");
        //byte[] thumb = null; //studData.getString("thumb");

        Student student = new Student(id, studentNumber, idNumber, lastname, firstname, email, cellnumber, email, address, courseCode);

        Student tempStudent = studentRepository.findByStudentNumber(student.getStudentNumber());

        if (tempStudent != null) {

            if (StoredSessionID.equals(sessionID)) {

                studentRepository.save(student);
                status = "OK";
                message = "Student record is updated";

                List<Student> allStudents = getAllStudents();
                response.put("students", allStudents);

                List<StudSubject> studentSubjects = studSubjectRepository.findByStudentNumber(studentNumber);
                for (int i = 0; i < studentSubjects.size(); i++) {

                    StudSubject obj = studentSubjects.get(i);
                    studSubjectRepository.delete(obj);
                }

                JSONArray jsonArray = studData.getJSONArray("studSubject");
                for (int i = 0; i < jsonArray.length(); i++) {
                    
                    JSONObject obj = jsonArray.getJSONObject(i);
                    studSubjectRepository.save(new StudSubject(obj.getString("subjectCode"), studentNumber));

                }

            }

        }

        response.put("status", status);
        response.put("message", message);

        return response;
    }

    public HashMap deleteStudent(Long studentNumber, String sessionID) {

        HashMap response = new HashMap();
        String message = "This operation has failed";
        String status = "FAILED";

        String StoredSessionID = (String) AdminService.usersIn.get(sessionID);
        if (StoredSessionID.equals(sessionID)) {

            studentRepository.delete(studentNumber);

            List<StudSubject> studentSubjects = studSubjectRepository.findByStudentNumber(studentNumber);

            for (int i = 0; i < studentSubjects.size(); i++) {
                StudSubject sub = studentSubjects.get(i);
                studSubjectRepository.delete(sub);
            }

            List<Student> allStudents = getAllStudents();

            status = "OK";
            message = "Student record is deleted";
            response.put("students", allStudents);
        }

        response.put("status", status);
        response.put("message", message);

        return response;
    }

    public HashMap signRegister(Long studentID) {

        HashMap response = new HashMap();
        String status = "NOT SIGNED";
        String message = "No register instance found for this student";
        List<StudentAttendanceSheet> sasList = null;
        // LecturerService.activeRegisters
        
        Student student = studentRepository.findOne(studentID);
        if (student != null) {

            //
            List<StudSubject> studentSubjects = studSubjectRepository.findByStudentNumber(student.getStudentNumber());

            //first, search for active register for this student 
            HashMap activeRegisterInfo = null;

            for (int x = 0; x < studentSubjects.size(); x++) {

                activeRegisterInfo = (HashMap) LecturerService.activeRegisters.get(studentSubjects.get(x).getSubjectCode());
                if (activeRegisterInfo != null) {
                    x = studentSubjects.size();
                }

            }

            // if register is found , then sign it
            if (activeRegisterInfo != null) {

                List<StudentAttendanceSheet> registerStudents
                        = (List<StudentAttendanceSheet>) activeRegisterInfo.get(LecturerService.STUDENTS);

                for (int x = 0; x < registerStudents.size(); x++) {

                    StudentAttendanceSheet sas = registerStudents.get(x);
                  
                    if (sas.getStudentNo().equals(student.getStudentNumber())) {

                        sas.setStatus("PRESENT");
                        sasRepository.save(sas);

                        sasList = sasRepository.findByAttendanceID(sas.getAttendanceID());

                        status = "SIGNED";
                        message = "Register is signed successfully";
                    }
                }

            }

        }
        response.put("studentsOnSheet", sasList);
        response.put("message", message);
        response.put("status", status);
        return response;
    }
    
     public HashMap signRegisterOut(Long studentID) {

        HashMap response = new HashMap();
        String status = "NOT SIGNED";
        String message = "No register instance found for this student";
        List<StudentAttendanceSheet> sasList = null;
        // LecturerService.activeRegisters
        
        Student student = studentRepository.findOne(studentID);
        if (student != null) {

            //
            List<StudSubject> studentSubjects = studSubjectRepository.findByStudentNumber(student.getStudentNumber());

            //first, search for active register for this student 
            HashMap activeRegisterInfo = null;

            for (int x = 0; x < studentSubjects.size(); x++) {

                activeRegisterInfo = (HashMap) LecturerService.activeRegisters.get(studentSubjects.get(x).getSubjectCode());
                if (activeRegisterInfo != null) {
                    x = studentSubjects.size();
                }

            }

            // if register is found , then sign it
            if (activeRegisterInfo != null) {

                List<StudentAttendanceSheet> registerStudents
                        = (List<StudentAttendanceSheet>) activeRegisterInfo.get(LecturerService.STUDENTS);

                for (int x = 0; x < registerStudents.size(); x++) {

                    StudentAttendanceSheet sas = registerStudents.get(x);
                  
                    if (sas.getStudentNo().equals(student.getStudentNumber())) {

                        sas.setStatus("ABSENT");
                        sasRepository.save(sas);

                        sasList = sasRepository.findByAttendanceID(sas.getAttendanceID());

                        status = "SIGNED";
                        message = "Register is signed successfully";
                    }
                }

            }

        }
        response.put("studentsOnSheet", sasList);
        response.put("message", message);
        response.put("status", status);
        return response;
    }
}
