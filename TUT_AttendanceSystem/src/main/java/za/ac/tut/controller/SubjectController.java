/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.tut.controller;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import za.ac.tut.entity.Subject;
import za.ac.tut.service.SubjectService;

/**
 *
 * @author fistos
 */
@RequestMapping("/TUT")
@RestController
public class SubjectController {
    
    @Autowired
    private SubjectService subjectServie;
    
    @RequestMapping(value = "/subject/add", method = RequestMethod.POST)
    public HashMap registerSubject(@RequestBody Subject subject) {

        HashMap response = subjectServie.saveSubject(subject);

        return response;
    }

    @RequestMapping(value = "/subject/displaySubjects/{sessionID}", method = RequestMethod.GET)
    public HashMap getAllSubjects(@PathVariable("sessionID") String sessionID) {

        HashMap response = subjectServie.retrieveSubjects(sessionID);
        
        return response;
    }

    @RequestMapping(value = "/subject/remove/{id}/{sessionID}", method = RequestMethod.GET)
    public HashMap removeSubject(@PathVariable("id") String code,
            @PathVariable("sessionID") String sessionID) {

        HashMap response = subjectServie.deleteSubject(code, sessionID);

        return response;
    }

    @RequestMapping(value = "/subject/update/{sessionID}", method = RequestMethod.POST)
    public HashMap updateSubject(@RequestBody Subject subject, @PathVariable("sessionID") String sessionID) {

        HashMap response = subjectServie.updateSubject(subject, sessionID);

        return response;
    }
    
    @RequestMapping(value = "/subject/findStudentSubjects/{studentNumber}/{sessionID}", method = RequestMethod.GET)
    public HashMap findStudentSubjects(@PathVariable("studentNumber") Long studentNumber,
            @PathVariable("sessionID") String sessionID) {

        HashMap response = subjectServie.findStudentSubjects(studentNumber, sessionID);

        return response;
    }
    
    @RequestMapping(value = "/subject/findLecturerSubjects/{userNumber}/{sessionID}", method = RequestMethod.GET)
    public HashMap findLecturerSubjects(@PathVariable("userNumber") Long userNumber,
            @PathVariable("sessionID") String sessionID) {

        HashMap response = subjectServie.findLecturerSubjects(userNumber, sessionID);

        return response;
    }
    
}
