/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.tut.repository;

import org.springframework.data.repository.CrudRepository;
import za.ac.tut.entity.Course;


/**
 *
 * @author fistos
 */

public interface CourseRepository extends CrudRepository<Course, Long> {

  public Course findByCourseCode(String code);
}
