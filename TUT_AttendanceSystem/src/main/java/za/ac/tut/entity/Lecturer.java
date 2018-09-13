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
 * @author fistos
 */
@Entity
public class Lecturer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "LECTURER_NO",nullable = false)
    private Long userNumber;
    @Column(name = "LECTURER_LNAME",nullable = false)
    private String surname;
    @Column(name = "LECTURER_FNAME",nullable = false)
    private String name;
    @Column(name = "LECTURER_EMAIL",nullable = false)
    private String email;
    @Column(name = "LECTURER_PASSWORD",nullable = false)
    private String password;
    @Column(name = "USER_ROLE",nullable = false)
    private String usrRole;
    public Lecturer() {
    }

    public Lecturer(Long userNumber, String surname, String name, String email, String password, String usrRole) {
        this.userNumber = userNumber;
        this.surname = surname;
        this.name = name;
        this.email = email;
        this.password = password;
        this.usrRole = usrRole;
    }

    public String getUsrRole() {
        return usrRole;
    }

    public void setUsrRole(String userRole) {
        this.usrRole = userRole;
    }

   

    public Long getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(Long userNumber) {
        this.userNumber = userNumber;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
   
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userNumber != null ? userNumber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lecturer)) {
            return false;
        }
        Lecturer other = (Lecturer) object;
        if ((this.userNumber == null && other.userNumber != null) || (this.userNumber != null && !this.userNumber.equals(other.userNumber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.ac.tut.entity.Lecturer[ id=" + userNumber + " ]";
    }
    
}
