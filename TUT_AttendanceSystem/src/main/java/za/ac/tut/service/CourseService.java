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
import za.ac.tut.entity.Course;
import za.ac.tut.entity.Student;
import za.ac.tut.entity.Subject;
import za.ac.tut.repository.CourseRepository;
import za.ac.tut.repository.StudSubjectRepository;
import za.ac.tut.repository.StudentRepository;
import za.ac.tut.repository.SubjectRespository;

/**
 *
 * @author fistos
 */
@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private SubjectRespository subjectRespository;
     @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudSubjectRepository ssRepository;
    
    
    public HashMap  saveCourse(Course course) {
        HashMap response = new HashMap();
        String message = "This course is already in the database";
        String status = "FAILED";

        //Read number of students in the database before inserting 1 record
        List<Course> allCourse = getAllCourses();
        int numberBefore = allCourse.size();

        //Check if this student is not registered already
        Course tempCourse = courseRepository.findByCourseCode(course.getCourseCode());

        if (tempCourse == null) {
            course = courseRepository.save(course);
            message = "Course [ "+course.getCourseCode()+" ][ "+course.getCourseDescription()+" ] is successfully added";
        
        }

        //Read number of students in the database after inserting 1 record
        allCourse = getAllCourses();
        int numberAfter = allCourse.size();
       

        //check if the records have increased 
        if (numberAfter > numberBefore) {
            status = "REGISTERED";
        }

        response.put("status", status);
        response.put("message", message);

        return response;
    }

    public HashMap retrieveCourses(String sessionID) {

        HashMap response = new HashMap();
        String message = "This operation has failed";
        String status = "FAILED";

        String StoredSessionID = (String) AdminService.usersIn.get(sessionID);

        if (StoredSessionID.equals(sessionID)) {
            List<Course> allCourses = getAllCourses();
            status = "OK";
            message = allCourses.size() + " courses found";
            response.put("courses", allCourses);
        }

        response.put("status", status);
        response.put("message", message);
        return response;
    }

    public HashMap deleteCourse(Long id, String sessionID) {
        HashMap response = new HashMap();
        String message = "This operation has failed";
        String status = "FAILED";

        String StoredSessionID = (String) AdminService.usersIn.get(sessionID);
        if (StoredSessionID.equals(sessionID)) {

            String courseCode = courseRepository.findOne(id).getCourseCode();
            List<Subject> subjects = subjectRespository.findByCourseCode(courseCode);
            List<Student> students = studentRepository.findByCourseCode(courseCode);
            message = "Course cannot be removed, "+students.size()+" students are registered on this course."
                    + " Delete students who are registered under this course first.";
            
            if(students.isEmpty()){
                courseRepository.delete(id);
                
                for (int i = 0; i < subjects.size(); i++) {
                    Subject sub = subjects.get(i);
                    subjectRespository.delete(sub);
                    
                }
                
                status = "OK";
                message = "Course record is successfully deleted";
            }
            
            
            subjects = new ArrayList<>();
            
            List<Course> allCourses = getAllCourses();
            subjectRespository.findAll().forEach(subjects::add);
            
            
            
            response.put("courses", allCourses);
            response.put("subjects", subjects);
        }

        response.put("status", status);
        response.put("message", message);

        return response;
    }

    public HashMap updateCourse(Course course, String sessionID){
        HashMap response = new HashMap();
        String message = "This operation has failed";
        String status = "FAILED";

        String StoredSessionID = (String) AdminService.usersIn.get(sessionID);

        Course tempCoure = courseRepository.findByCourseCode(course.getCourseCode());

        if (tempCoure != null) {

            if (StoredSessionID.equals(sessionID)) {

                courseRepository.save(course);
                status = "OK";
                message = "Course record is updated";

                List<Course> allCodes = getAllCourses();
                response.put("courses", allCodes);
            }

        }

        response.put("status", status);
        response.put("message", message);

        return response;
    }

    private List<Course> getAllCourses() {

        List<Course> courses = new ArrayList<>();
        courseRepository.findAll().forEach(courses::add);

        return courses;
    }

}
