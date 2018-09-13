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
@Table(name = "STUDENT_SUBJECT")
public class StudSubject implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
   
    @Column(name = "SUBJECT_CODE")
    private String subjectCode;
    @Column(name = "STUDENT_NO",nullable = false)
    private Long studentNumber;

    

    
    public StudSubject() {
    }

    public StudSubject(String subjectCode, Long studentNumber) {
        this.subjectCode = subjectCode;
        this.studentNumber = studentNumber;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(Long studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }
       
}
