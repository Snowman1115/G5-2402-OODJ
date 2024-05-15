package com.project.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ConsultationService {

    /**
     * Get Number of Student's Upcoming and Finished Consultation
     * @return Map of Integer
     */
    public Map<String, Integer> getUpcomingNFinishedConsultationForStudent(Integer studentId);

    /**
     * Get Upcoming Event for Student
     * @return List of Map
     */
    public List<Map<String, String>> getUpcomingEventForStudent(Integer studentId);

    /**
     * Get All Consultation Details for Student
     * @return List of Map
     */
    List getAllEventsForStudent(Integer studentId);

    /**
     * Get All Available Consultation Slots for Student
     * @return List of Map
     */
    public List<Map<String, String>> getAllAvailableConsultationSlots();

    /**
     * Get All Lecturer and Project Manager Name
     * @return List
     */
    public List getAllLecturerName();

    /**
     * Book Consultation Slots by Consultation Id
     * @param consultationId
     * @param studentId
     * @return boolean
     */
    boolean bookConsultationSlot(Integer consultationId, Integer studentId);

    /**
     * Get Scheduled Consultation Details By Student Id
     * @param studentId
     * @return List
     */
    public List<Map<String, String>> getAllScheduledConsultationIdByStudentId(Integer studentId);

    /**
     * Cancel Booked Consultation Details By Consultation Id
     * @param consultationId
     * @return boolean
     */
    public Boolean cancelBookedConsultationById(Integer consultationId);

    /**
     * Get All Consultation Details By Lecturer Id
     * @param lecturerId
     * @return List
     */
    public List getAllConsultationDetailsByLecId(Integer lecturerId);
    
    /**
     * Get Number of Student's Upcoming and Finished Consultation
     * @param lecturerId
     * @return Map of Integer
     */
    public Map<String, Integer> getUpcomingNFinishedConsultationForLecturer(Integer lecturerId);
    
    /**
     * Get All Scheduled Consultation By Lecturer Id
     * @param lecturerId
     * @return List
     */
    public List<Map<String, String>> getAllScheduledConsultationByLecId(Integer lecturerId);
    
    /**
     * Get All Consultation (Except Completed Consultation) By Lecturer Id
     * @param lecturerId
     * @return List
     */
    public List<Map<String, String>> getAllConsultationExceptCompletedByLecId(Integer lecturerId);
    
    /**
     * Update Booked Consultation To Completed By Consultation Id
     * @param consultationId
     * @return boolean
     */
    public Boolean completeBookedConsultationById(Integer consultationId);
    
     /**
     * Create Consultation Slot For Lecturer By Lecturer Id and Date Time
     * @param lecturerId
     * @param dateTime
     * @return Boolean
     */
    public Boolean createConsultationSlotForLecturer(Integer lecturerId, LocalDateTime dateTime);
    
     /**
     * Delete Consultation By Consultation ID
     * @param consultationId
     * @return Boolean
     */
    public Boolean deleteConsultationById(Integer consultationId);
}
