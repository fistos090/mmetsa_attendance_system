/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.tut.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.tut.entity.AttendanceSheet;
import za.ac.tut.entity.Course;
import za.ac.tut.entity.Lecturer;
import za.ac.tut.entity.LecturerSubject;
import za.ac.tut.entity.Student;
import za.ac.tut.entity.StudentAttendanceSheet;
import za.ac.tut.entity.Subject;
import za.ac.tut.repository.AttendanceSheetRepository;
import za.ac.tut.repository.CourseRepository;
import za.ac.tut.repository.LecturerRespository;
import za.ac.tut.repository.LecturerSubjectRepository;
import za.ac.tut.repository.StudAttendanceSheetRepository;
import za.ac.tut.repository.SubjectRespository;
import static za.ac.tut.service.AdminService.usersIn;

/**
 *
 * @author fistos
 */
@Service
public class LecturerService {

    @Autowired
    private LecturerRespository lecturerRespository;
    @Autowired
    private LecturerSubjectRepository lecturerSubjectRepository;
    @Autowired
    private AttendanceSheetRepository sheetRepository;
    @Autowired
    private StudAttendanceSheetRepository sasRepository;
    @Autowired
    private SubjectRespository subjectRespository;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private PrinterService printerService;
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    public static HashMap activeRegisters = new HashMap();

    public static final String REGISTER = "register";
    public static final String STUDENTS = "students";
    public static final String TIME = "time";
    public static final String STUDENTS_DATA = "students_data";

    public HashMap saveLecturer(String data) throws JSONException {
        HashMap response = new HashMap();

        //System.out.println(data);
        //Extract student data
        JSONObject lecturerData = new JSONObject(data);

        Long userNumber = lecturerData.getLong("userNumber");
        String surname = lecturerData.getString("surname");
        String name = lecturerData.getString("name");
        String email = lecturerData.getString("email");
        String password = lecturerData.getString("password");
        String usrRole = "LECTURER";

        Lecturer lecturer = new Lecturer(userNumber, surname, name, email, password, usrRole);

        String message = "Lecturer is already registered. You can't register same person twice";
        String status = "FAILED";

        //Read number of students in the database before inserting 1 record
        //Read number of students in the database before inserting 1 record
        List<Lecturer> allLecturers = getAllLecturers();
        int numberBefore = allLecturers.size();

        //Check if this student is not registered already
        Lecturer tempLecturer = lecturerRespository.findByUserNumber(userNumber);

        if (tempLecturer == null) {

            lecturerRespository.save(lecturer);
            message = "Lecturer is successfully added";

            JSONArray jsonArray = lecturerData.getJSONArray("lecturerSubjects");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                lecturerSubjectRepository.save(new LecturerSubject(obj.getString("subjectCode"), userNumber));

            }

        }

        //Read number of students in the database after inserting 1 record
        allLecturers = getAllLecturers();
        int numberAfter = allLecturers.size();

        //check if the records have increased 
        if (numberAfter > numberBefore) {
            status = "REGISTERED";
        }

        response.put("status", status);
        response.put("message", message);

        return response;

    }

    public HashMap retrieveLecturers(String sessionID) {
        HashMap response = new HashMap();
        String message = "This operation has failed";
        String status = "FAILED";

        String StoredSessionID = (String) AdminService.usersIn.get(sessionID);

        if (sessionID.equals(StoredSessionID)) {
            List<Lecturer> allLecturers = getAllLecturers();
            status = "OK";
            message = allLecturers.size() + " registered lecturers found";
            response.put("lecturers", allLecturers);
        }

        response.put("status", status);
        response.put("message", message);
        return response;
    }

    public HashMap deleteLecturer(Long lecturerNumber, String sessionID) {
        HashMap response = new HashMap();
        String message = "This operation has failed";
        String status = "FAILED";

        List<Lecturer> allLecturers = null;
        String StoredSessionID = (String) AdminService.usersIn.get(sessionID);
        if (sessionID.equals(StoredSessionID)) {

            lecturerRespository.delete(lecturerNumber);

            allLecturers = getAllLecturers();

            status = "OK";
            message = "Lecturer record is deleted";
            response.put("lecturers", allLecturers);
        }

        response.put("status", status);
        response.put("message", message);

        return response;
    }

    public HashMap updateLecturer(String lecturerData, String sessionID) throws JSONException {

        HashMap response = new HashMap();
        String message = "This operation has failed";
        String status = "FAILED";

        JSONObject lecData = new JSONObject(lecturerData);

        Long userNumber = lecData.getLong("userNumber");
        String surname = lecData.getString("surname");
        String name = lecData.getString("name");
        String password = lecData.getString("password");
        String usrRole = lecData.getString("usrRole");
        String email = lecData.getString("email");

        Lecturer lecturer = new Lecturer(userNumber, surname, name, email, password, usrRole);
        String StoredSessionID = (String) AdminService.usersIn.get(sessionID);

        Lecturer tempLecturer = lecturerRespository.findByUserNumber(lecturer.getUserNumber());

        if (tempLecturer != null) {

            if (sessionID.equals(StoredSessionID)) {

                lecturerRespository.save(lecturer);
                status = "OK";
                message = "Lecturer's record is updated";

                List<Lecturer> allLecturers = getAllLecturers();
                response.put("lecturers", allLecturers);

                List<LecturerSubject> lecturerSubjects = lecturerSubjectRepository.findByLecturerNumber(userNumber);
                lecturerSubjectRepository.delete(lecturerSubjects);

                JSONArray jsonArray = lecData.getJSONArray("lecturerSubjects");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    lecturerSubjectRepository.save(new LecturerSubject(obj.getString("subjectCode"), userNumber));
                }
            }

        }

        response.put("status", status);
        response.put("message", message);

        return response;
    }

    public HashMap signIn(Lecturer lecturer, HttpSession session) {

        HashMap response = new HashMap();
        String message = "User with the username entered does not exist";
        String status = "FAILED";

        Lecturer user = null;
        AttendanceSheet activeRegister = null;
        List<StudentAttendanceSheet> sasList = null;
        List<Student> students = null;

        Lecturer tempLecturer = lecturerRespository.findByUserNumber(lecturer.getUserNumber());

        // System.out.println(tempLecturer);
        //System.out.println(tempLecturer + "   ndfkjhndfkjhndfkjhnkjdfhfdkjhn");
        if (tempLecturer != null) {

            if (tempLecturer.getPassword().equals(lecturer.getPassword())) {
                message = "You have successfully logged in";
                status = "OK";
                String sessionID = session.getId();

                user = tempLecturer;
                usersIn.put(sessionID, sessionID);

                List<LecturerSubject> lecturersSubjects = lecturerSubjectRepository.findByLecturerNumber(lecturer.getUserNumber());

                List<AttendanceSheet> previousRegisters = sheetRepository.findByLecturerNo(lecturer.getUserNumber());

                List<Subject> mySubjects = new ArrayList();

                for (int i = 0; i < lecturersSubjects.size(); i++) {

                    LecturerSubject sub = lecturersSubjects.get(i);
                    Subject subject = subjectRespository.findBySubjectCode(sub.getSubjectCode());
                    mySubjects.add(subject);
                }

                activeRegister = getActiveRegister(previousRegisters, lecturer.getUserNumber());

                if (activeRegister != null) {

                    sasList = sasRepository.findByAttendanceID(activeRegister.getId());
                    students = subjectService.getSubjectStudents(activeRegister.getSubjectCode());

                    HashMap activeRegisterInfo = (HashMap) activeRegisters.get(activeRegister.getSubjectCode());

                    if (activeRegisterInfo == null) {

                        activeRegisterInfo = new HashMap();
                        activeRegisterInfo.put(REGISTER, activeRegister);
                        activeRegisterInfo.put(STUDENTS, sasList);
                        activeRegisterInfo.put(STUDENTS_DATA, students);
                        activeRegisterInfo.put(TIME, 60);

                        activeRegisters.put(activeRegister.getSubjectCode(), activeRegisterInfo);
                    }
                }

                response.put("activeRegister", activeRegister);
                response.put("studentsOnSheet", sasList);
                response.put("subjectAttendingStudents", students);
                response.put("preRegisters", previousRegisters);
                response.put("mySubjects", mySubjects);
                response.put("sessionID", sessionID);

            } else {
                message = "You've entered the wrong password";
            }
        }

        response.put("user", user);
        response.put("status", status);
        response.put("message", message);

        return response;
    }

    private List<Lecturer> getAllLecturers() {

        List<Lecturer> lecturers = new ArrayList<>();
        lecturerRespository.findAll().forEach(lecturers::add);

        return lecturers;
    }

    public HashMap activateRegister(String data) throws JSONException {
        HashMap response = new HashMap();
        String message = "Register activation failed.";
        String status = "FAILED";

        JSONObject jObj = new JSONObject(data);

        String StoredSessionID = (String) AdminService.usersIn.get(jObj.getString("sessionID"));

        if (jObj.getString("sessionID").equals(StoredSessionID)) {

            //Extract register data
            jObj = (JSONObject) jObj.get("registerData");

            Long lecturerNo = jObj.getLong("lecturerNo");
            String venue = jObj.getString("venue");
            Date sheetDate = new Date();
            String subjectCode = jObj.getString("subjectCode");
            String time = "60";//jObj.getString("time");

            Subject subject = subjectRespository.findBySubjectCode(subjectCode);

            //HashMap registerInfo = (HashMap) activeRegisters.get(subjectCode);
            List<AttendanceSheet> previousRegisters = sheetRepository.findByLecturerNo(lecturerNo);

            AttendanceSheet activeRegister = getActiveRegister(previousRegisters, lecturerNo);

            if (activeRegister == null) {

                List<Student> students = subjectService.getSubjectStudents(subjectCode);

                if (!students.isEmpty()) {
                    AttendanceSheet attendanceSheet = new AttendanceSheet(lecturerNo, venue, sheetDate, subjectCode);

                    attendanceSheet = sheetRepository.save(attendanceSheet);

                    for (int i = 0; i < students.size(); i++) {
                        System.out.println("phakathi");
                        Student student = students.get(i);
                        StudentAttendanceSheet sas = new StudentAttendanceSheet(student.getStudentNumber(), attendanceSheet.getId(), "ABSENT");
                        sasRepository.save(sas);

                    }

                    List<StudentAttendanceSheet> sasList = sasRepository.findByAttendanceID(attendanceSheet.getId());

                    HashMap activeRegisterInfo = new HashMap();
                    activeRegisterInfo.put(REGISTER, attendanceSheet);
                    activeRegisterInfo.put(STUDENTS, sasList);
                    activeRegisterInfo.put(STUDENTS_DATA, students);
                    activeRegisterInfo.put(TIME, time);

                    activeRegisters.put(subjectCode, activeRegisterInfo);

                    status = "OK";

                    response.put("activeRegister", attendanceSheet);
                    response.put("studentsOnSheet", sasList);
                    response.put("subjectAttendingStudents", students);
                    message = "You have successfully activated " + subject.getSubjectCode() + " | "
                            + subject.getSubjectDesc() + " register";
                } else {
                    message = students.size() + " students for this subject/module. Register cannot be activated";
                }

            } else {
                message = "There is a register that on this stuff number. Please close it first to continue";

            }
        }

        response.put("status", status);
        response.put("message", message);

        return response;

    }

    public HashMap updateRegister(String sessionID, Long sheetID) {
        HashMap response = new HashMap();

        String StoredSessionID = (String) AdminService.usersIn.get(sessionID);

        if (sessionID.equals(StoredSessionID)) {

            List<StudentAttendanceSheet> studentsOnSheet = sasRepository.findByAttendanceID(sheetID);
            response.put("studentsOnSheet", studentsOnSheet);
        }

        return response;
    }

    public HashMap closeRegister(String data) throws JSONException {
        HashMap response = new HashMap();
        String message = "Closing register failed";
        String status = "FAILED";

        JSONObject jObj = new JSONObject(data);

        String StoredSessionID = (String) AdminService.usersIn.get(jObj.getString("sessionID"));

        if (jObj.getString("sessionID").equals(StoredSessionID)) {

            Long lecturerNo = jObj.getLong("lecturerNo");
            String subjectCode = jObj.getString("subjectCode");

            HashMap activeRegisterInfo = (HashMap) LecturerService.activeRegisters.get(subjectCode);
            if (activeRegisterInfo != null) {

                AttendanceSheet attendanceSheet = (AttendanceSheet) activeRegisterInfo.get(REGISTER);
                attendanceSheet.setStatus("CLOSED");

                sheetRepository.save(attendanceSheet);
                activeRegisters.remove(subjectCode);

                List<AttendanceSheet> registers = sheetRepository.findByLecturerNo(lecturerNo);
                //sheetRepository.findAll().forEach(registers::add);
                status = "OK";

                response.put("preRegisters", registers);
                response.put("activeRegister", null);
                message = "Register is now closed";
            }
        }

        response.put("status", status);
        response.put("message", message);

        return response;

    }

    private AttendanceSheet getActiveRegister(List<AttendanceSheet> previousRegisters, Long lecNo) {

        AttendanceSheet activeRegister = null;
        for (int x = 0; x < previousRegisters.size(); x++) {
            AttendanceSheet register = previousRegisters.get(x);

            if (register.getStatus().equalsIgnoreCase("active")) {
                if (register.getLecturerNo().equals(lecNo)) {

                    activeRegister = register;
                    previousRegisters.remove(x);
                    x = previousRegisters.size();
                }
            }

        }
        return activeRegister;
    }

    public HashMap generateReport(String sessionID, Long sheetID) {

        HashMap response = new HashMap();
        String message = "";
        String status = "FAILED";

        String StoredSessionID = (String) AdminService.usersIn.get(sessionID);

        if (sessionID.equals(StoredSessionID)) {

            try {

                AttendanceSheet registerSheet = sheetRepository.findOne(sheetID);
                List<StudentAttendanceSheet> specificSheet = sasRepository.findByAttendanceID(registerSheet.getId());
                List<Student> subjectStudents = subjectService.getSubjectStudents(registerSheet.getSubjectCode());
                Lecturer lecturer = lecturerRespository.findOne(registerSheet.getLecturerNo());

                message = printerService.printReport(registerSheet, subjectStudents, specificSheet, lecturer);

                status = "OK";

            } catch (IOException ex) {
                Logger.getLogger(LecturerService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        response.put("message", message);
        response.put("status", status);

        return response;
    }
//
//    public Date getMillis(JSONObject obj) throws JSONException {
//        int day = obj.getInt("day");
//        int month = obj.getInt("month");
//        int year = obj.getInt("year");
//
//        Calendar cal = Calendar.getInstance();
//        cal.set(year, month, day);
//        
//        
//       
//        System.out.println(cal.get(Calendar.DAY_OF_MONTH)+"/"+cal.get(Calendar.MONTH)+"/"+cal.get(Calendar.YEAR));
//        return cal.getTime();
//    }

    public HashMap generateHighLevelReport(String sessionID, String data) throws Exception {

        HashMap response = new HashMap();
        String message = "Report failed to print. Please contact admin.";
        String status = "FAILED";

        String StoredSessionID = (String) AdminService.usersIn.get(sessionID);

        if (sessionID.equals(StoredSessionID)) {

            JSONObject obj = new JSONObject(data);

            JSONObject firstDate = (JSONObject) obj.get("firstDate");
            JSONObject secondDate = (JSONObject) obj.get("secondDate");
            Long lecturerNo = obj.getLong("lecturerNo");
            String subjectCode = obj.getString("subjectCode");

            Subject sub = subjectRespository.findBySubjectCode(subjectCode);
            Course course = courseRepository.findByCourseCode(sub.getCourseCode());
            Lecturer lec = lecturerRespository.findOne(lecturerNo);
            List<AttendanceSheet> sheets = sheetRepository.findByLecturerNo(lecturerNo);
            sheets = getBySubjectCode(sheets, subjectCode);

            HashMap attendanceSheets = new HashMap();

            attendanceSheets.put("subject", sub);
            attendanceSheets.put("course", course);
            attendanceSheets.put("lecturer", lec);
//
//            Date startDate = getMillis(firstDate);
//            Date lastDate = getMillis(secondDate);

            List<AttendanceSheet> inRangeSheets = new ArrayList<>();

            for (int i = 0; i < sheets.size(); i++) {
                AttendanceSheet sheet = sheets.get(i);

                Calendar cal = Calendar.getInstance();
                cal.setTime(sheet.getSheetDate());
                //cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH) , cal.get(Calendar.DAY_OF_MONTH));
                
                    if (compareYears(firstDate.getInt("year"),secondDate.getInt("year"),cal.get(Calendar.YEAR))  && 
                        compareMonths(firstDate.getInt("month"),secondDate.getInt("month"),cal.get(Calendar.MONTH)) && 
                        compareDays(firstDate.getInt("day"),secondDate.getInt("day"),cal.get(Calendar.DAY_OF_MONTH)) ){

                        inRangeSheets.add(sheet);
                    }
           
            }
            attendanceSheets.put("sheets", inRangeSheets);

            message = printerService.printHighLevelReport(attendanceSheets);
            status = "OK";

        }

        response.put("message", message);
        response.put("status", status);

        return response;
    }

    private boolean compareYears(int y1, int y2,int registerYear){
        
        if(registerYear >= y1 && registerYear <= y2){
            return true;
        }
        return false;
    }
    private boolean compareMonths(int m1, int m2,int registerMonth){
        if(registerMonth >= m1 && registerMonth <= m2){
            
            return true;
        }
        return false;
    }
   private boolean compareDays(int d1, int d2,int registerDay){
        if(registerDay >= d1 && registerDay <= d2){
            return true;
        }
        return false;
    }
    
    
    private List<AttendanceSheet> getBySubjectCode(List<AttendanceSheet> sheets, String subjectCode) {

        List<AttendanceSheet> sheetsToReturn = new ArrayList<>();

        for (int i = 0; i < sheets.size(); i++) {
            AttendanceSheet sheet = sheets.get(i);
            if (sheet.getSubjectCode().equalsIgnoreCase(subjectCode)) {
                sheetsToReturn.add(sheet);
            }
        }
        return sheetsToReturn;
    }

}
