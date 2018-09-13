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
@Table(name = "LECT_SUB")
public class LecturerSubject implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;
    @Column(name = "SUBJECT_CODE" ,nullable = false)
    private String subjectCode;
    @Column(name = "LECTURER_NO" ,nullable = false)
    private Long lecturerNumber;

    public LecturerSubject() {
    }

    public LecturerSubject(String subjectCode, Long lecturerNumber) {
        this.subjectCode = subjectCode;
        this.lecturerNumber = lecturerNumber;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public Long getLecturerNumber() {
        return lecturerNumber;
    }

    public void setLecturerNumber(Long lecturerNumber) {
        this.lecturerNumber = lecturerNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
 
}
