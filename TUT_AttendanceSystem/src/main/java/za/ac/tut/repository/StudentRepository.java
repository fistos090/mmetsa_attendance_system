/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.tut.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import za.ac.tut.entity.Student;

/**
 *
 * @author Mmetsa
 */

public interface StudentRepository extends CrudRepository<Student, Long>{
    public Student findByStudentNumber(Long studentNumber);
    public Student findByIdNumber(String idNumber);
    public List<Student> findByCourseCode(String code);
}
