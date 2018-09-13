/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.tut.controller;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import za.ac.tut.entity.Lecturer;
import za.ac.tut.service.AdminService;
import za.ac.tut.service.LecturerService;

/**
 *
 * @author fistos
 */
@RequestMapping("/TUT")
@RestController
public class LecturerController {

    @Autowired
    private LecturerService lecturerService;

    @RequestMapping(value = "/lecturer/register", method = RequestMethod.POST)
    public HashMap registerStudent(@RequestBody String lecturerData) {

        HashMap response = null;
        try {
            response = lecturerService.saveLecturer(lecturerData);
        } catch (JSONException ex) {
            Logger.getLogger(LecturerController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return response;
    }

    @RequestMapping(value = "/lecturer/displaylectures/{sessionID}", method = RequestMethod.GET)
    public HashMap getAllLecturers(@PathVariable("sessionID") String sessionID) {
       
        HashMap response = lecturerService.retrieveLecturers(sessionID);
        
        return response;
    }

    @RequestMapping(value = "/lecturer/remove/{lecturerNumber}/{sessionID}", method = RequestMethod.GET)
    public HashMap removeLecturer(@PathVariable("lecturerNumber") Long lecturerNumber,
            @PathVariable("sessionID") String sessionID) {

        HashMap response = lecturerService.deleteLecturer(lecturerNumber, sessionID);

        return response;
    }

    @RequestMapping(value = "/lecturer/update/{sessionID}", method = RequestMethod.POST)
    public HashMap updateLecturer(@RequestBody String lecturerData, @PathVariable("sessionID") String sessionID) {

        try {
            
            HashMap response = lecturerService.updateLecturer(lecturerData, sessionID);
            return response;
        } catch (JSONException ex) {
            Logger.getLogger(LecturerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    @RequestMapping(value = "/lecturer/login", method = RequestMethod.POST)
    public HashMap login(@RequestBody Lecturer lecturer, HttpServletRequest request) {

        
        HashMap response = lecturerService.signIn(lecturer, request.getSession());
        System.out.println(response);
        return response;
    }

    @RequestMapping(value = "/lecturer/logout/{sessionID}", method = RequestMethod.GET)
    public HashMap logout(@PathVariable("sessionID") String sessionID) {

        HashMap response = new HashMap();
        String status = "FAILED";

        String StoredSessionID = (String) AdminService.usersIn.get(sessionID);

        if (StoredSessionID != null) {
            if (StoredSessionID.equals(sessionID)) {
                AdminService.usersIn.remove(sessionID);
                status = "OK";
            }
        } else {

        }

        response.put("status", status);

        return response;
    }
    
    @RequestMapping(value = "/lecturer/activateRegister", method = RequestMethod.POST)
    public HashMap activateRegister(@RequestBody String data) {
        
        try {
            HashMap response = lecturerService.activateRegister(data);
            System.out.println(response);
            return response;
            
        } catch (JSONException ex) {
            Logger.getLogger(LecturerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @RequestMapping(value = "/lecturer/{sessionID}/{sheetID}", method = RequestMethod.GET)
    public HashMap updateRegister(@PathVariable("sessionID") String sessionID,@PathVariable("sheetID") Long sheetID ) {
        
        HashMap response = lecturerService.updateRegister(sessionID,sheetID);

        return response;
    }
    
    @RequestMapping(value = "/lecturer/closeRegister", method = RequestMethod.POST)
    public HashMap closeRegister(@RequestBody String data) {
     
       
        HashMap response = null;
        try {
            response = lecturerService.closeRegister(data);
        } catch (JSONException ex) {
            Logger.getLogger(LecturerController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
            
        return response;
    }
    
    @RequestMapping(value = "/lecturer/generateReport/{sessionID}/{sheetID}", method = RequestMethod.GET)
    public HashMap generateReport(@PathVariable("sessionID") String sessionID,@PathVariable("sheetID") Long sheetID ) {
        
        HashMap response = lecturerService.generateReport(sessionID,sheetID);

        return response;
    }

    
    @RequestMapping(value = "/lecturer/generateHLReport/{sessionID}", method = RequestMethod.POST)
    public HashMap generateHLReport(@PathVariable("sessionID") String sessionID,@RequestBody String data ) {
        
        HashMap response = null;
        try {
            response = lecturerService.generateHighLevelReport(sessionID, data);
        } catch (Exception ex) {
            Logger.getLogger(LecturerController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return response;
    }
}
