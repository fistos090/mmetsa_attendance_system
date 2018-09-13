/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.tut.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 *
 * @author Mmetsa
 */
@Entity
@Table(name = "ATTENDENCE_SHEET")
public class AttendanceSheet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ATT_ID",nullable = false)
    private Long id;
    @Column(name = "LECTURER_NO",nullable = false)
    private Long lecturerNo;
    @Column(name = "ATT_VENUE",nullable = false)
    private String venue;
    @Column(name = "ATT_DATE",nullable = false)
    @Temporal(TemporalType.DATE)
    private Date sheetDate;
    @Column(name = "SUBJECT_CODE",nullable = false)
    private String subjectCode;
    @Column(name = "REGISTER_STATUS",nullable = false)
    private String status;
    

    public AttendanceSheet() {
    }

    public AttendanceSheet(Long lecturerNo, String venue, Date sheetDate, String subjectCode) {
        this.lecturerNo = lecturerNo;
        this.venue = venue;
        this.sheetDate = sheetDate;
        this.subjectCode = subjectCode;
        this.status = "ACTIVE";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public Date getSheetDate() {
        return sheetDate;
    }

    public void setSheetDate(Date sheetDate) {
        this.sheetDate = sheetDate;
    }
 

    public Long getLecturerNo() {
        return lecturerNo;
    }

    public void setLecturerNo(Long lecturerNo) {
        this.lecturerNo = lecturerNo;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }
     
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AttendanceSheet)) {
            return false;
        }
        AttendanceSheet other = (AttendanceSheet) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.ac.tut.entity.AttendanceSheet[ id=" + id + " ]";
    }
    
}
