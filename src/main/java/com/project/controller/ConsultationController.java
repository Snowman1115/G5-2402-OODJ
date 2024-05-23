package com.project.controller;

import com.project.service.ConsultationService;
import com.project.service.Impl.ConsultationServiceImpl;
import com.project.service.Impl.UserAuthenticationServiceImpl;
import com.project.service.UserAuthenticationService;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class ConsultationController {

    private static UserAuthenticationService userAuthenticationService = new UserAuthenticationServiceImpl();
    private static ConsultationService consultationService = new ConsultationServiceImpl();


    /**
     * Get All Available Consultation Slots for Student
     * @return List of Map
     */
    public static List getAllAvailableConsultationSlots() {
        return consultationService.getAllAvailableConsultationSlots();
    }
    
    /**
     * Get Number of Student's Upcoming and Finished Consultation
     * @return Map of Integer
     */
    public static Map<String, Integer> getUpcomingNFinishedConsultationForStudent() {
        return consultationService.getUpcomingNFinishedConsultationForStudent(getAuthenticatedUserId());
    }

    /**
     * Get Upcoming Event for Student
     * @return List of Map
     */
    public static List getUpcomingEventForStudent() {
        return consultationService.getUpcomingEventForStudent(getAuthenticatedUserId());
    }

    /**
     * Get All Consultation Details for Student
     * @return List of Map
     */
    public static List getAllEventsForStudent() {
        return consultationService.getAllEventsForStudent(getAuthenticatedUserId());
    }

    /**
     * Get All Lecturer and Project Manager Name
     * @return List
     */
    public static List getAllLecturerNProjectManagerNameForStudent() {
        return consultationService.getAllLecturerName();
    }

    /**
     * Book Consultation Slots by Consultation Id
     * @param consultationId
     * @return boolean
     */
    public static boolean bookConsultationSlot(Integer consultationId) {
        log.info("Book Consultation: " + consultationId);
        return consultationService.bookConsultationSlot(consultationId, getAuthenticatedUserId());
    }

    /**
     * Get Scheduled Consultation Details By Student Id
     * @return userId
     */
    public static List getAllScheduledConsultationIdByStudentId() {
        return consultationService.getAllScheduledConsultationIdByStudentId(getAuthenticatedUserId());
    }

    /**
     * Cancel Booked Consultation Details By Consultation Id
     * @param consultationId
     * @return boolean
     */
    public static Boolean cancelBookedConsultationById(Integer consultationId) {
        log.info("Cancel Booked Consultation: " + consultationId);
        return consultationService.cancelBookedConsultationById(consultationId);
    }
    
    /**
     * Get All Consultation Details By Lecturer Id
     * @return List
     */
    public static List getAllConsultationDetailsByLecId() {
        return consultationService.getAllConsultationDetailsByLecId(getAuthenticatedUserId());
    }
    
    /**
     * Get Number of Student's Upcoming and Finished Consultation
     * @return Map of Integer
     */
    public static Map<String, Integer> getAvailableNScheduledConsultationForLecturer() {
        return consultationService.getAvailableNSchduledConsultationForLecturer(getAuthenticatedUserId());
    }
    
    /**
     * Get All Scheduled Consultation By Lecturer Id
     * @return List
     */
    public static List getAllScheduledConsultationByLecId() {
        return consultationService.getAllScheduledConsultationByLecId(getAuthenticatedUserId());
    }
    
    /**
     * Get All Consultation (Except Completed Consultation) By Lecturer Id
     * @return List
     */
    public static List getAllConsultationExceptCompletedByLecId() {
        return consultationService.getAllConsultationExceptCompletedByLecId(getAuthenticatedUserId());
    }
    
    /**
     * Update Booked Consultation To Complete By Consultation Id
     * @param consultationId
     * @return Boolean
     */
    public static Boolean completeBookedConsultationById(Integer consultationId) {
        log.info("Booked Consultation with ID: " + consultationId + " is completed");
        return consultationService.completeBookedConsultationById(consultationId);
    }
    
     /**
     * Create Consultation Slot For Lecturer By Lecturer Id and Date Time
     * @param dateTime
     * @return Boolean
     */
    public static Boolean createConsultationSlotForLecturer(LocalDateTime dateTime) {
        log.info("New consultation slot for the lecturer with lecturerID : " + getAuthenticatedUserId() + " has created successfully");
        return consultationService.createConsultationSlotForLecturer(getAuthenticatedUserId(), dateTime);
    }
    
    /**
     * Delete Consultation By Consultation ID
     * @param consultationId
     * @return Boolean
     */
    public static Boolean deleteConsultationById(Integer consultationId) {
        log.info("Consultation with the ID : " + consultationId + " is deleted");
        return consultationService.deleteConsultationById(consultationId);
    }
    
    /**
     * Get Authenticated UserId
     * @return userId
     */
    private static Integer getAuthenticatedUserId() {
        return userAuthenticationService.getAuthenticationUserDetails().getUserId();
    }
}
