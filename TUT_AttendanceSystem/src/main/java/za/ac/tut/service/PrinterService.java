/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.tut.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.tut.entity.AttendanceSheet;
import za.ac.tut.entity.Course;
import za.ac.tut.entity.Lecturer;
import za.ac.tut.entity.Student;
import za.ac.tut.entity.StudentAttendanceSheet;
import za.ac.tut.entity.Subject;
import za.ac.tut.repository.StudAttendanceSheetRepository;

/**
 *
 * @author MANDELACOMP3
 */
@Service
public class PrinterService {

    @Autowired
    private StudAttendanceSheetRepository sasRepository;
    @Autowired
    private EmailService emailService;
    
    public String printReport(AttendanceSheet sheet, List<Student> students,
            List<StudentAttendanceSheet> sas, Lecturer lecturer) throws IOException {

        String message = "";

        if (sas.size() > 0) {

            HashMap currentDate = getCurrentDate();
            File file = new File("C:/ATTENDANCE_REPORT_" + sheet.getSubjectCode() + sheet.getId() + ".csv");

            BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));

            writer.write(",,ATTENDANCE SYSTEM GENERATED REPORT,,REPORT DATE :," + currentDate.get("date") + "\n");
            writer.write(",,,,REPORT TIME :," + currentDate.get("time") + "\n\n");

            String registerInfo = ",,REGISTER INFORMATION \n\n";

            registerInfo += "REGISTER NUMBER :," + sheet.getId() + "\n";
            registerInfo += "SUBJECT CODE :," + sheet.getSubjectCode() + "\n";
            registerInfo += "REGISTER STATUS :," + sheet.getStatus() + "\n";
            registerInfo += "VENUE :," + sheet.getVenue() + "\n\n";
            registerInfo += "DATE OF ATTENDANCE :," + sheet.getSheetDate().toString() + "\n\n";

            writer.write(registerInfo);

            String lecturerInfo = "LECTURER DETAILS : \n\n";
            lecturerInfo += ",LECTURER NO ,FULL NAME \n";
            lecturerInfo += "," + lecturer.getUserNumber() + "," + lecturer.getSurname() + " " + lecturer.getName() + "\n";

            writer.write(lecturerInfo);
            writer.newLine();

            double percentage = 0;
            int totalStudents = students.size();
            int totalPresent = 0;
            int totalAbsent = 0;

            String studentsInfo = "STUDENTS INFORMATION \n\n";

            studentsInfo += ",STUDENT NO,FULL NAME,EMAIL,ATTENDANCE STATUS \n";
            for (int j = 0; j < sas.size(); j++) {

                StudentAttendanceSheet studAttSheet = sas.get(j);
                Student stud = students.get(j);

                String fullName = stud.getLastname() + " " + stud.getFirstname();

                studentsInfo += "," + stud.getStudentNumber() + "," + fullName + ","
                        + stud.getEmail() + "," + studAttSheet.getStatus() + "\n";

                if (studAttSheet.getStatus().equalsIgnoreCase("PRESENT")) {
                    totalPresent += 1;
                } else if (studAttSheet.getStatus().equalsIgnoreCase("ABSENT")) {
                    totalAbsent += 1;
                }

            }

            percentage = totalPresent * 100 / totalStudents;

            writer.write(studentsInfo);
            writer.newLine();

            registerInfo = ",TOTAL STUDENTS,TOTAL ATTENDANCE,TOTAL ABSENT,ATTENDANCE PERCENTAGE \n";
            registerInfo += "," + totalStudents + "," + totalPresent + "," + totalAbsent + "," + percentage + "\n\n";

            writer.write(registerInfo);
            writer.newLine();
            writer.close();

            message = "Report for "
                    + students.size() + " student(s) is successfully printed.";

            String msg = "Hi "+lecturer.getName()+", Find the file attachment below";
            
            try {
                
                emailService.sendEmail("Specific Date Attendace Report", msg, lecturer.getEmail() ,file.getPath());
                
            } catch (MessagingException ex) {
                Logger.getLogger(PrinterService.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {

            message = "Report for "
                    + students.size() + " students is Unsuccessfully";
        }

        return message;
    }

    public HashMap getCurrentDate() {

        HashMap timeAndDate = new HashMap();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(System.currentTimeMillis()));

        String currentDate = cal.get(Calendar.DAY_OF_MONTH) + "/"
                + (cal.get(Calendar.MONTH) + 1) + "/"
                + (cal.get(Calendar.YEAR) + 1);

        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);

        String stringHour = hour + "";
        String stringMinute = minute + "";
        String stringSecond = second + "";

        if (hour <= 9) {
            stringHour = "0" + hour;
        }
        if (minute <= 9) {
            stringMinute = "0" + minute;
        }
        if (second <= 9) {
            stringSecond = "0" + second;
        }

        String time = stringHour + ":" + stringMinute + ":" + stringSecond;

        timeAndDate.put("date", currentDate);
        timeAndDate.put("time", time);

        return timeAndDate;
    }

    public String printHighLevelReport(HashMap attendanceSheets) throws IOException {

        String message = "";

        if (attendanceSheets != null) {

            Subject sub = (Subject) attendanceSheets.get("subject");
            Course course = (Course) attendanceSheets.get("course");
            Lecturer lecturer = (Lecturer) attendanceSheets.get("lecturer");
            List<AttendanceSheet> sheets = (List<AttendanceSheet>) attendanceSheets.get("sheets");

            HashMap currentDate = getCurrentDate();

            File file = new File("C:/ATTENDANCE_RANGE_REPORT_" + ".csv");

            BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));

            writer.write(",,ATTENDANCE SYSTEM GENERATED DATE-RANGE REPORT,,REPORT DATE :," + currentDate.get("date") + "\n");
            writer.write(",,,,REPORT TIME :," + currentDate.get("time") + "\n\n");

            String registerInfo = ",,REPORT DETAILS \n\n";

            registerInfo += ",,SUBJECT AND COURSE INFO. \n";
            registerInfo += "NUMBER OF REGISTERS :," + sheets.size() + "\n";
            registerInfo += "SUBJECT CODE :," + sub.getSubjectCode() + "\n";
            registerInfo += "SUBJECT DESCRIPTION :," + sub.getSubjectDesc() + "\n";
            registerInfo += "COURSE CODE :," + course.getCourseCode() + "\n\n";
            registerInfo += "COURSE DESCRIPTION :," + course.getCourseDescription() + "\n\n";

            writer.write(registerInfo);

            String lecturerInfo = ",,LECTURER DETAILS : \n\n";
            lecturerInfo += "LECTURER NO ,FULL NAME \n";
            lecturerInfo += "" + lecturer.getUserNumber() + "," + lecturer.getSurname() + " " + lecturer.getName() + "\n";

            writer.write(lecturerInfo);
            writer.newLine();

            writer.write(",,REGISTERS INFORMATION \n\n");

            for (int j = 0; j < sheets.size(); j++) {

                AttendanceSheet sheet = sheets.get(j);
                String regInfo = ",REGISTET NO,DATE,ABSENT PERCENTAGE,PRESENT PERCENTAGE\n";

                List<StudentAttendanceSheet> studentsAttendanceSheet = sasRepository.findByAttendanceID(sheet.getId());

                int totalPresent = 0;
                int totalAbsent = 0;
                int totalStudents = studentsAttendanceSheet.size();
                double presentPercentage = 0;
                double absentPercentage = 0;

                for (int i = 0; i < studentsAttendanceSheet.size(); i++) {

                    StudentAttendanceSheet studAttSheet = studentsAttendanceSheet.get(i);

                    if (studAttSheet.getStatus().equalsIgnoreCase("PRESENT")) {
                        totalPresent += 1;
                    } else if (studAttSheet.getStatus().equalsIgnoreCase("ABSENT")) {
                        totalAbsent += 1;
                    }

                }
                
                if (studentsAttendanceSheet.size() > 0) {

                    presentPercentage = totalPresent * 100 / totalStudents;
                    
                    if (presentPercentage > 0 && presentPercentage < 100) {
                        absentPercentage = 100 - presentPercentage;
                    } else
                    if(presentPercentage == 0){
                        absentPercentage = 100;
                    }else
                    if(presentPercentage == 100){  
                      absentPercentage = 0;
                    }
                }

                regInfo += "," + sheet.getId() + "," + sheet.getSheetDate().toString() + "," + absentPercentage + "," + presentPercentage + "\n\n";
                writer.write(regInfo);
                //writer.newLine();
            }

            writer.close();

            message = "Report for "
                    + sheets.size() + " attendances is successfully printed.";

           try {
                 String msg = "Hi "+lecturer.getName()+", Find the file attachment below.";
                 
                emailService.sendEmail("Specific Date Attendace Report", msg, lecturer.getEmail() ,file.getPath());
                
            } catch (MessagingException ex) {
                Logger.getLogger(PrinterService.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        } else {

            message = "Report generation for attendances is Unsuccessfully";
        }

        return message;
    }

}
