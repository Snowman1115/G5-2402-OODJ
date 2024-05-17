package com.project.service.Impl;

import com.project.common.constants.ConsultationStatus;
import com.project.common.constants.MessageConstant;
import com.project.common.constants.UserRoleType;
import com.project.common.utils.DateTimeUtils;
import com.project.common.utils.Dialog;
import com.project.dao.ConsultationDAO;
import com.project.dao.UserAccountDAO;
import com.project.dao.UserRoleDAO;
import com.project.pojo.Consultation;
import com.project.pojo.UserAccount;
import com.project.service.ConsultationService;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
public class ConsultationServiceImpl implements ConsultationService {

    private ConsultationDAO consultationDAO = new ConsultationDAO();
    private UserAccountDAO userAccountDAO = new UserAccountDAO();
    private UserRoleDAO userRoleDAO = new UserRoleDAO();
    
    /**
     * Get Number of Student's Upcoming and Finished Consultation
     * @return Map of Integer
     */
    @Override
    public Map<String, Integer> getUpcomingNFinishedConsultationForStudent(Integer studentId) {
        return consultationDAO.getUpcomingNFinishedConsultationForStudent(studentId);
    }

    @Override
    public List<Map<String, String>> getUpcomingEventForStudent(Integer studentId) {
        List<Map<String, String>> mappedLists = new ArrayList<>();
        List<Map<String, String>> lists = consultationDAO.getUpcomingEventForStudent(studentId);
        for (Map<String, String> event : lists) {
            Map<String, String> mappedMap = new HashMap<>();
            UserAccount userAccount = userAccountDAO.getUserAccountById(Integer.parseInt(event.get("lecturer")));
            mappedMap.put("lecturer", userAccount.getFirstName() + " " + userAccount.getLastName());
            mappedMap.put("date", event.get("date"));
            mappedLists.add(mappedMap);
        }
        return mappedLists;
    }

    /**
     * Get All Consultation Details for Student
     * @return List of Map
     */
    @Override
    public List getAllEventsForStudent(Integer studentId) {
        List<Map<String, String>> mappedLists = new ArrayList<>();
        List<Consultation> lists = consultationDAO.getAllEventsForStudent(studentId);
        for (Consultation consultation : lists) {
            Map<String, String> map = new HashMap<>();
            map.put("id", consultation.getConsultationId().toString());
            UserAccount userAccount = userAccountDAO.getUserAccountById(consultation.getLecturerId());
            map.put("lecturer", userAccount.getFirstName() + " " + userAccount.getLastName());
            map.put("date", DateTimeUtils.formatStrDateTime(consultation.getConsultationDateTime()));
            map.put("status", consultation.getConsultationStatus().toString());
            mappedLists.add(map);
        }
        return mappedLists;
    }

    /**
     * Get All Available Consultation Slots for Student
     * @return List of Map
     */
    @Override
    public List<Map<String, String>> getAllAvailableConsultationSlots() {
        List<Map<String, String>> mappedList = new ArrayList<>();
        List<Consultation> consultations = consultationDAO.checkAllAvailableConsultationsSlots();
        for (Consultation consultation : consultations) {
            Map<String,String> map = new HashMap<>();
            map.put("id", consultation.getConsultationId().toString());
            UserAccount userAccount = userAccountDAO.getUserAccountById(consultation.getLecturerId());
            map.put("lecturer", userAccount.getFirstName() + " " + userAccount.getLastName());
            map.put("date", DateTimeUtils.formatStrDateTime(consultation.getConsultationDateTime()));
            map.put("status", consultation.getConsultationStatus().toString());
            mappedList.add(map);
        }
        return mappedList;
    }

    /**
     * Get All Lecturer and Project Manager Name
     * @return List
     */
    @Override
    public List getAllLecturerName() {
        List<String> lecturers = new ArrayList<>();
        List<UserAccount> users = userAccountDAO.getAllUsers();
        for (UserAccount user : users) {
            if (userRoleDAO.checkRoleType(user.getUserId()).equals(UserRoleType.LECTURER) || userRoleDAO.checkRoleType(user.getUserId()).equals(UserRoleType.PROJECT_MANAGER)) {
                lecturers.add(user.getFirstName() + " " + user.getLastName());
            }
        }
        return lecturers;
    }

    /**
     * Book Consultation Slots by Consultation Id
     * @param consultationId
     * @param studentId
     * @return Boolean
     */
    @Override
    public boolean bookConsultationSlot(Integer consultationId, Integer studentId) {
        if(consultationDAO.bookConsultationSlot(consultationId, studentId)) {
            log.info("Consultation Booked Successfully: " + consultationId + " + " + studentId);
            Dialog.SuccessDialog(MessageConstant.SUCCESS_BOOKING_CONSULTATION);
            return true;
        }
        log.info("Consultation Booked Failed: " + consultationId + " + " + studentId);
        return false;
    }

    /**
     * Get Scheduled Consultation Details By Student Id
     * @param studentId
     * @return userId
     */
    @Override
    public List<Map<String, String>> getAllScheduledConsultationIdByStudentId(Integer studentId) {
        List<Map<String, String>> mappedList = new ArrayList<>();
        List<Consultation> consultations = consultationDAO.getAllEventsForStudent(studentId);
        for (Consultation consultation : consultations) {
            if (consultation.getConsultationStatus().equals(ConsultationStatus.SCHEDULED) && consultation.getConsultationDateTime().isAfter(LocalDateTime.now())) {
                Map<String,String> map = new HashMap<>();
                map.put("id", consultation.getConsultationId().toString());
                UserAccount userAccount = userAccountDAO.getUserAccountById(consultation.getLecturerId());
                map.put("lecturer", userAccount.getFirstName() + " " + userAccount.getLastName());
                map.put("date", DateTimeUtils.formatStrDateTime(consultation.getConsultationDateTime()));
                map.put("status", consultation.getConsultationStatus().toString());
                mappedList.add(map);
            }
        }
        return mappedList;
    }

    /**
     * Cancel Booked Consultation Details By Consultation Id
     * @param consultationId
     * @return boolean
     */
    @Override
    public Boolean cancelBookedConsultationById(Integer consultationId) {
        if (consultationDAO.cancelBookedConsultationById(consultationId)) {
            log.info("Consultation Cancelled Successfully: " + consultationId);
            Dialog.SuccessDialog(MessageConstant.SUCCESS_CONSULTATION_CANCELLED);
            return true;
        } else {
            log.info("Consultation Cancelled Failed: " + consultationId);
            return false;
        }
    }
    
    /**
     * Get All Consultation Details By Lecturer Id
     * @param lecturerId
     * @return List
     */
    @Override
    public List getAllConsultationDetailsByLecId(Integer lecturerId)
    {
        List<Map<String, String>> mappedLists = new ArrayList<>();
        //Get all cosultation details by lecturer ID
        List<Map<String, String>> consultationList = consultationDAO.getConsultationByLecturerId(lecturerId);
        for (Map<String, String> list : consultationList){
            Map<String, String> mappedMap = new HashMap<>();
            mappedMap.put("id", list.get("id"));
            Integer studentId=Integer.valueOf(list.get("studentId"));
            //If the consultation slot is not yet booked by any student, set studentId and studentName to EMPTY before displaying to avoid bug     
            if(studentId != 0)
            {
                //Get student name by student ID that is from consultationDAO object
                UserAccount student=userAccountDAO.getUserAccountById(studentId);
                String studentName=student.getFirstName()+" "+student.getLastName();
                mappedMap.put("studentId", studentId.toString());
                mappedMap.put("studentName", studentName);
            }
            else 
            {
                mappedMap.put("studentId", "EMPTY");
                mappedMap.put("studentName", "EMPTY");
            }
            mappedMap.put("consultationDateTime", list.get("consultationDateTime"));
            mappedMap.put("consultationStatus", list.get("consultationStatus"));
            
            mappedLists.add(mappedMap);
        }
        return mappedLists;
    }
    
    /**
     * Get Number of Lecturer's Upcoming and Finished Consultation
     * @param lecturerId
     * @return Map of Integer
     */
    @Override
    public Map<String, Integer> getAvailableNSchduledConsultationForLecturer(Integer lecturerId) {
        return consultationDAO.getAvailableNScheduledConsultationForLecturer(lecturerId);
    }
    
     /**
     * Get All Scheduled Consultation By Lecturer Id
     * @param lecturerId
     * @return List of Map
     */
    @Override
    public List<Map<String, String>> getAllScheduledConsultationByLecId(Integer lecturerId) {
        List<Map<String, String>> mappedList = new ArrayList<>();
        List<Consultation> consultations = consultationDAO.getAllScheduledConsultationForLec(lecturerId);
        for (Consultation consultation : consultations) {
            Map<String,String> map = new HashMap<>();
            map.put("id", consultation.getConsultationId().toString());
            UserAccount userAccount = userAccountDAO.getUserAccountById(consultation.getStudentId());
            map.put("studentName", userAccount.getFirstName() + " " + userAccount.getLastName());
            map.put("consultationDateTime", DateTimeUtils.formatStrDateTime(consultation.getConsultationDateTime()));
            map.put("consultationStatus", consultation.getConsultationStatus().toString());
            mappedList.add(map); 
        }
        return mappedList;
    }
    
     /**
     * Get All Consultation (Except Completed Consultations) By Lecturer Id
     * @param lecturerId
     * @return List of Map
     */
    @Override
    public List<Map<String, String>> getAllConsultationExceptCompletedByLecId(Integer lecturerId) {
        List<Map<String, String>> mappedList = new ArrayList<>();
        List<Consultation> consultations = consultationDAO.getAllConsultationExceptCompletedForLec(lecturerId);
        for (Consultation consultation : consultations) {
            Map<String,String> map = new HashMap<>();
            map.put("id", consultation.getConsultationId().toString());
            Integer studentId=consultation.getStudentId();
            //If the consultation slot is not yet booked by any student, set studentId and studentName to EMPTY before displaying to avoid bug     
            if(studentId != 0)
            {
                //Get student name by student ID that is from consultationDAO object
                UserAccount student=userAccountDAO.getUserAccountById(studentId);
                String studentName=student.getFirstName()+" "+student.getLastName();
                map.put("studentId", studentId.toString());
                map.put("studentName", studentName);
            }
            else 
            {
                map.put("studentId", "EMPTY");
                map.put("studentName", "EMPTY");
            }
            map.put("consultationDateTime", DateTimeUtils.formatStrDateTime(consultation.getConsultationDateTime()));
            map.put("consultationStatus", consultation.getConsultationStatus().toString());
            mappedList.add(map); 
            }
        return mappedList;
    }
    
    /**
     * Update Booked Consultation To Complete By Consultation Id
     * @param consultationId
     * @return Boolean
     */
    @Override
    public Boolean completeBookedConsultationById(Integer consultationId) {
        if (consultationDAO.completeBookedConsultationById(consultationId)) {
            log.info("Consultation Status Update To Completed Successfully: " + consultationId);
            Dialog.SuccessDialog(MessageConstant.SUCCESS_CONSULTATION_COMPLETED);
            return true;
        } else {
            log.info("Consultation Update To Completed Failed: " + consultationId);
            return false;
        }
    }

    @Override
    public Boolean createConsultationSlotForLecturer(Integer lecturerId, LocalDateTime dateTime) {
        List<Map<String, String>> consultationList = consultationDAO.getConsultationByLecturerId(lecturerId);
        for (Map<String, String> list : consultationList){
            LocalDateTime consultDateTime=DateTimeUtils.formatDateTime(list.get("consultationDateTime"));
            Integer lecId=Integer.valueOf(list.get("lecturerId"));
            //Check whether the selected consultation date time is clashed with the other existing consultation date time
            if(lecId.equals(lecturerId) && consultDateTime.equals(dateTime))
            {
                //If found clashing, return false and display an error message
                Dialog.ErrorDialog(MessageConstant.ERROR_CONSULTATION_DATETIME_CLASHED);
                return false;
            }
        }
        consultationDAO.createConsultationSlot(lecturerId, dateTime);
        Dialog.SuccessDialog(MessageConstant.SUCCESS_CONSULTATION_CREATED);
        return true;
    }
    
     /**
     * Delete Consultation By Consultation ID
     * @param consultationId
     * @return Boolean
     */
    @Override
    public Boolean deleteConsultationById(Integer consultationId)
    {
        if(Dialog.ConfirmationDialog(MessageConstant.WARNING_REMOVE_CONFIRMATION)) {
            log.info("Consultation Slot Deleted Successfully : " + consultationId);
            consultationDAO.deleteConsultation(consultationId);
            return true;
        } else {
            log.info("Delete Consultation Slot Cancelled : " + consultationId);
            return false;
        }    
    }
    //For debug purpose, run the below main method to view the data
    public static void main(String[] args) {
        ConsultationServiceImpl consult = new ConsultationServiceImpl();
        System.out.println(consult.getAllScheduledConsultationByLecId(88608036));
    }
}
