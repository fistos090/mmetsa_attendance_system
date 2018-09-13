/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.tut.controller;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import za.ac.tut.service.StudentService;

/**
 *
 * @author Mmetsa
 */

//Handle data from presentation layer that relate to students
@RequestMapping("/TUT")
@RestController
public class StudentController {
    
    
    @Autowired 
    private StudentService studentService;
    
    @RequestMapping(value ="/register", method = RequestMethod.POST) 
    public HashMap registerStudent(@RequestBody String studentData){
        
        
        HashMap response = null;
        try {
            response = studentService.saveStudent(studentData);
        } catch (JSONException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
 
        return response;
    }
    
     @RequestMapping(value ="/displayAllStudents/{sessionID}", method = RequestMethod.GET) 
    public HashMap getAllStudents(@PathVariable("sessionID") String sessionID){

        HashMap response = studentService.retrieveAllStudents(sessionID);
     
        return response;
    }
    
    @RequestMapping(value ="/remove/{studentNumber}/{sessionID}", method = RequestMethod.GET) 
    public HashMap removeStudent(@PathVariable("studentNumber") Long studentNumber,
        @PathVariable("sessionID") String sessionID){
  
        HashMap response = studentService.deleteStudent(studentNumber,sessionID);
       
        return response;
    }
    
    @RequestMapping(value ="/update/{sessionID}", method = RequestMethod.POST) 
    public HashMap updateStudent(@RequestBody String studentData,@PathVariable("sessionID") String sessionID){

        HashMap response = null;
        try {
            response = studentService.updateStudent(studentData,sessionID);
        } catch (JSONException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
 
        return response;
    }
    
    @RequestMapping(value ="/signRegister/{studentID}", method = RequestMethod.GET) 
    public HashMap signRegister(@PathVariable("studentID") Long studentID){

        HashMap response = studentService.signRegister(studentID);
       
        return response;
    }
    
    @RequestMapping(value ="/signRegister/out/{studentID}", method = RequestMethod.GET) 
    public HashMap signRegisterOut(@PathVariable("studentID") Long studentID){

        HashMap response = studentService.signRegisterOut(studentID);
       
        return response;
    }
    
    @RequestMapping(value ="/signRegister/", method = RequestMethod.GET) 
    public HashMap signReg(){

        HashMap response = new HashMap();
        
       response.put("message","Student id not found");
        return response;
    }
}
