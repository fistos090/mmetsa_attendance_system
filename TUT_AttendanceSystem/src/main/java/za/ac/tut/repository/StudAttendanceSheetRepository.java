/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.tut.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import za.ac.tut.entity.StudentAttendanceSheet;

/**
 *
 * @author Mmetsa
 */
public interface StudAttendanceSheetRepository extends CrudRepository<StudentAttendanceSheet, Long>{
    List<StudentAttendanceSheet> findByAttendanceID(Long ID);
    
}
