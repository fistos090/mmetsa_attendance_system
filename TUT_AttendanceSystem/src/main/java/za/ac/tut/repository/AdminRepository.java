/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.tut.repository;

import java.io.Serializable;
import org.springframework.data.repository.CrudRepository;
import za.ac.tut.entity.Administrator;

/**
 *
 * @author Mmetsa
 */
public interface AdminRepository extends CrudRepository<Administrator, String>{
    public Administrator findByUserNumber(String userNumber);
}
