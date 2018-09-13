/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.tut.controller;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import za.ac.tut.entity.Administrator;
import za.ac.tut.service.AdminService;
/**
 *
 * @author Mmetsa
 */
@RestController
@RequestMapping("/ADMIN")
public class AdminController {

    @Autowired
    private AdminService adminService;

    
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public HashMap registerAdmin(@RequestBody Administrator adminstrator) {

        HashMap response = adminService.saveAdmin(adminstrator);

        return response;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public HashMap login(@RequestBody Administrator adminstrator, HttpServletRequest request) {

        System.out.println("dgsdgsdgsdgsdg");
        HashMap response = adminService.signIn(adminstrator, request.getSession());

        return response;
    }

    @RequestMapping(value = "/logout/{sessionID}", method = RequestMethod.GET)
    public HashMap logout(@PathVariable("sessionID") String sessionID) {

        String status = "FAILED";
        //HttpSession session = request.getSession();
        //String StoredSessionID = (String) session.getAttribute(sessionID);

        String StoredSessionID = (String) AdminService.usersIn.get(sessionID);
   
        
        if (StoredSessionID.equals(sessionID)) {
            AdminService.usersIn.remove(sessionID);
            status = "OK";
        }
        HashMap response = new HashMap();
                response.put("status", status);

        return response;
    }

}
