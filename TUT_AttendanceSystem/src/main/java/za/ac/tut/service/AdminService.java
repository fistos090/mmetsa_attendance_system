/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.tut.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.tut.entity.Administrator;
import za.ac.tut.repository.AdminRepository;

/**
 *
 * @author Mmetsa
 */
@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    public static HashMap usersIn = new HashMap();

    public HashMap saveAdmin(Administrator adminstrator) {

        HashMap response = new HashMap();
        String message = "You are already registered. You can't register twice";
        String status = "FAILED";

        List<Administrator> allAdmins = getAllAdmins();

        int numberBefore = allAdmins.size();

        Administrator tempAdmin = adminRepository.findByUserNumber(adminstrator.getUserNumber());

        if (tempAdmin == null) {
            adminRepository.save(adminstrator);
            message = "You are successfully registered";

        }
        allAdmins = getAllAdmins();
        int numberAfter = allAdmins.size();

        //check if the records has increased 
        if (numberAfter > numberBefore) {
            status = "REGISTERED";
        }

        response.put("status", status);
        response.put("message", message);

        return response;

    }

    public HashMap signIn(Administrator adminstrator, HttpSession session) {

        HashMap response = new HashMap();
        String message = "User with the username entered does not exist";
        String status = "FAILED";
        Administrator user = null;

        Administrator tempAdmin = adminRepository.findByUserNumber(adminstrator.getUserNumber());

        if (tempAdmin != null) {

            if (tempAdmin.getPassword().equals(adminstrator.getPassword())) {
                message = "You have successfully logged in";
                status = "OK";
                String sessionID = session.getId();

                user = tempAdmin;
                usersIn.put(sessionID, sessionID);
                response.put("sessionID", sessionID);
            } else {
                message = "You've entered the wrong password";
            }
        }

        response.put("user", user);
        response.put("status", status);
        response.put("message", message);

        return response;
    }

    private List<Administrator> getAllAdmins() {

        List<Administrator> admins = new ArrayList<>();
        adminRepository.findAll().forEach(admins::add);

        return admins;
    }

}
