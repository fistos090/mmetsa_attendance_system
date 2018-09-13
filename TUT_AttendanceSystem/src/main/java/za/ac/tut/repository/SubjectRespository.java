/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.tut.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import za.ac.tut.entity.Subject;

/**
 *
 * @author fistos
 */
public interface SubjectRespository extends CrudRepository<Subject, Long> {
    public Subject findBySubjectCode(String code);
    public List<Subject> findByCourseCode(String code);
    
    @Transactional
    public void deleteBySubjectCode(String code);
  
}
