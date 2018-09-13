/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.tut.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Mmetsa
 */
@Controller
//@RequestMapping("TUT")
public class SiteController {
    
    @RequestMapping("/")
    public String loadHomePage(){
        return "home";
    }
    
    @RequestMapping("/{page}")
    public String loadPage(@PathVariable String page){ 
      
        return page;
    }
}
