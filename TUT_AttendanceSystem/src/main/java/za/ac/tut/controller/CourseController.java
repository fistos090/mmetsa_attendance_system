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
import za.ac.tut.entity.Course;
import za.ac.tut.service.CourseService;

/**
 *
 * @author fistos
 */
@RestController
@RequestMapping("/TUT")
public class CourseController {
    
    @Autowired
    private CourseService courseService;
    
      @RequestMapping(value = "/course/add", method = RequestMethod.POST)
    public HashMap registerCourse(@RequestBody Course course) {

        HashMap response = courseService.saveCourse(course);

        return response;
    }

    @RequestMapping(value = "/course/displayCourses/{sessionID}", method = RequestMethod.GET)
    public HashMap getAllCourses(@PathVariable("sessionID") String sessionID) {

        HashMap response = courseService.retrieveCourses(sessionID);
        System.out.println(response.toString());
        return response;
    }

    @RequestMapping(value = "/course/remove/{id}/{sessionID}", method = RequestMethod.GET)
    public HashMap removeCourse(@PathVariable("id") Long id,
            @PathVariable("sessionID") String sessionID) {

        HashMap response = courseService.deleteCourse(id, sessionID);

        return response;
    }

    @RequestMapping(value = "/course/update/{sessionID}", method = RequestMethod.POST)
    public HashMap updateCourse(@RequestBody Course course, @PathVariable("sessionID") String sessionID) {

        HashMap response = courseService.updateCourse(course, sessionID);

        return response;
    }
    
}
