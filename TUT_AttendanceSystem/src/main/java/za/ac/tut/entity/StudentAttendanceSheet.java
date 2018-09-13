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
@Table(name = "STUDENT_ATTENDENCE_SHEET")
public class StudentAttendanceSheet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "STUDENT_ATT_SHEET_ID", nullable = false)
    private Long id;
    @Column(name = "STUDENT_NO", nullable = false)
    private Long studentNo;
    @Column(name = "ATT_ID", nullable = false)
    private Long attendanceID;
    private String status;
//    @ManyToOne
//    private AttendanceSheet attendanceSheet;

    public StudentAttendanceSheet() {
    }

    public StudentAttendanceSheet(Long studentNo, Long attendanceID, String status) {
        this.studentNo = studentNo;
        this.attendanceID = attendanceID;
        this.status = status;
    }

    public StudentAttendanceSheet(Long id, Long studentNo, Long attendanceID, String status) {
        this.id = id;
        this.studentNo = studentNo;
        this.attendanceID = attendanceID;
        this.status = status;
    }
    
    

    public Long getStudentNo() {
        return studentNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStudentNo(Long studentNo) {
        this.studentNo = studentNo;
    }

    public Long getAttendanceID() {
        return attendanceID;
    }

    public void setAttendanceID(Long attendanceID) {
        this.attendanceID = attendanceID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studentNo != null ? studentNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentAttendanceSheet)) {
            return false;
        }
        StudentAttendanceSheet other = (StudentAttendanceSheet) object;
        if ((this.studentNo == null && other.studentNo != null) || (this.studentNo != null && !this.studentNo.equals(other.studentNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.ac.tut.entity.StudentAttendanceSheet[ id=" + studentNo + " ]";
    }

}
