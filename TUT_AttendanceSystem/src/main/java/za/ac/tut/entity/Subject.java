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

/**
 *
 * @author fistos
 */
@Entity
public class Subject implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "SUBJECT_CODE",unique = true, nullable = false)
    private String subjectCode;
    @Column(name = "COURSE_CODE", nullable = false)
    private String courseCode;
    @Column(name = "SUBJ_DESCRIPTION", nullable = false)
    private String subjectDesc;

    public Subject() {
    }

    public Subject(String subjectCode, String courseCode, String subjectDesc) {
        this.subjectCode = subjectCode;
        this.courseCode = courseCode;
        this.subjectDesc = subjectDesc;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getSubjectDesc() {
        return subjectDesc;
    }

    public void setSubjectDesc(String subjectDesc) {
        this.subjectDesc = subjectDesc;
    }

}
