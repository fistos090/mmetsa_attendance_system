/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.tut.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Mmetsa
 */
@Entity
@Table(name = "STUDENT")
public class Student implements Serializable{
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;     
    
    @Column(name = "STUDENT_NO",nullable = false)
    private Long studentNumber;
    @Column(name = "ID_NO",nullable = false)
    private String idNumber;
    @Column(name = "STUD_SURNAME",nullable = false)
    private String lastname;
    @Column(name = "STUD_NAME",nullable = false)
    private String firstname;
    @Column(name = "GENDER",nullable = false)
    private String gender;
    @Column(name = "PHONE")
    private String cellnumber;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "COURSE_CODE",nullable = false)
    private String courseCode;
    @Column(name = "THUMB_PRINT")
    private byte[] thumb;

    public Student() {
    }

    public Student(Long studentNumber, String idNumber, String lastname, String firstname, String gender, String cellnumber, String email, String address, String courseCode, byte[] thumb) {
        this.studentNumber = studentNumber;
        this.idNumber = idNumber;
        this.lastname = lastname;
        this.firstname = firstname;
        this.gender = gender;
        this.cellnumber = cellnumber;
        this.email = email;
        this.address = address;
        this.courseCode = courseCode;
        this.thumb = thumb;
    }

    public Student(Long id, Long studentNumber, String idNumber, String lastname, String firstname, String gender, String cellnumber, String email, String address, String courseCode) {
        this.id = id;
        this.studentNumber = studentNumber;
        this.idNumber = idNumber;
        this.lastname = lastname;
        this.firstname = firstname;
        this.gender = gender;
        this.cellnumber = cellnumber;
        this.email = email;
        this.address = address;
        this.courseCode = courseCode;
    }

    
    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getGender() {
        return gender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public byte[] getThumb() {
        return thumb;
    }

    public void setThumb(byte[] thumb) {
        this.thumb = thumb;
    }

    
    public Long getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(Long studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getCellnumber() {
        return cellnumber;
    }

    public void setCellnumber(String cellnumber) {
        this.cellnumber = cellnumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
    
    

    @Override
    public String toString() {
        return "Student{" + "studentNumber=" + studentNumber + ", lastname=" + lastname + ", firstname=" + firstname + ", cellnumber=" + cellnumber + ", email=" + email + ", address=" + address + ", courseCode=" + courseCode + '}';
    }
  
    
}
