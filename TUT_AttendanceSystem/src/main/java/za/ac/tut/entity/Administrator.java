/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.tut.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Mmetsa
 */
@Entity
public class Administrator implements Serializable{
 
    @Id
    @Column(name = "USER_NAME",nullable = false)
    private String userNumber;
    @Column(name = "PASSWORD",nullable = false)
    private String password;
    @Column(name = "USER_ROLE",nullable = false)
    private String usrRole;
    private String name;
    private String surname;

    public Administrator() {
    }  

    public Administrator(String userNumber, String password, String usrRole, String name, String surname) {
        this.userNumber = userNumber;
        this.password = password;
        this.usrRole = usrRole;
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsrRole() {
        return usrRole;
    }

    public void setUsrRole(String usrRole) {
        this.usrRole = usrRole;
    }

    

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Administrator{" + "userName=" + userNumber + ", password=" + password + '}';
    }
    
    
}
