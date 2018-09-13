/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.tut.repository;


import java.util.List;
import org.springframework.data.repository.CrudRepository;
import za.ac.tut.entity.LecturerSubject;

/**
 *
 * @author Mmetsa
 */
public interface LecturerSubjectRepository extends CrudRepository<LecturerSubject, Long>{
    List<LecturerSubject> findByLecturerNumber(Long lecturerNumber);
}
