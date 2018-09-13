/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.tut.repository;


import java.util.List;
import org.springframework.data.repository.CrudRepository;
import za.ac.tut.entity.AttendanceSheet;

/**
 *
 * @author Mmetsa
 */
public interface AttendanceSheetRepository extends CrudRepository<AttendanceSheet, Long>{
    public List<AttendanceSheet> findByLecturerNo(Long lecturerNo);
}
