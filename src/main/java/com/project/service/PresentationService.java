package com.project.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface PresentationService {

    /**
     * Get All Presentation Status By Student Id
     * @param studentId
     * @return Map of List
     */
    public Map<String, Integer> getAllPresentationStatusByStudentId(Integer studentId);

    /**
     * Get All Upcoming N Pending Booking Presentation
     * @param studentId
     * @return Map of list
     */
    List getAllUpcomingNPendingBookingPresentation(Integer studentId);

    /**
     * Get All Presentation Details for Student
     * @param studentId
     * @return List of Map
     */
    List getAllPresentationDetailsForStudent(Integer studentId);

    /**
     * Book Presentation Slot By For Student
     * @param presentationId
     * @param dateTime
     * @return Boolean
     */
    Boolean bookPresentationSlotByStudentId(Integer presentationId, LocalDateTime dateTime);

    /**
     * Edit Presentation Slot By For Student
     * @param presentationId
     * @param dateTime
     * @return Boolean
     */
    Boolean editPresentationSlotByStudentId(Integer presentationId, LocalDateTime dateTime);

    /**
     * Cancel Presentation Slot For Student (Condition: PENDING_BOOKING)
     * @param presentationId
     * @return Boolean
     */
    Boolean cancelPresentationSlotByStudentId(Integer presentationId);
    
    /**
     * Get All Pending Confirm And Pending Marking Presentation For Lecturer
     * @param lecturerId
     * @return Map of Integer
     */
    public Map<String, Integer> getPendingConfirmAndMarkingPresentationForLecturer(Integer lecturerId);
    
    /**
     * Get All Consultation Details By Lecturer Id
     * @param lecturerId
     * @return List
     */
    public List getAllPresentationDetailsByLecId(Integer lecturerId); 
    
    /**
     * Get All Booked Presentation By Lecturer Id
     * @param lecturerId
     * @return List
     */
    public List <Map<String, String>> getAllBookedPresentationByLecId(Integer lecturerId); 
    
    /**
     * Get All Pending Confirm Presentation By Lecturer Id
     * @param lecturerId
     * @return List
     */
    public List <Map<String, String>> getAllPendingConfirmPresentationByLecId(Integer lecturerId); 
    
    /**
     * Get All Not Yet Graded Presentation By Lecturer Id
     * @param lecturerId
     * @return List
     */
    public List <Map<String, String>> getNotYetGradedPresentationByLecId(Integer lecturerId); 

    /**
     * Update Pending Confirm Presentation To Accepted By Presentation Id
     * @param presentationId
     * @param dateTime
     * @param lecturerId
     * @return Boolean
     */
    public Boolean acceptPresentationById(Integer presentationId, LocalDateTime dateTime, Integer lecturerId);
    
    /**
     * Update Pending Confirm Presentation To Rejected By Presentation Id
     * @param presentationId
     * @param lecturerId
     * @return Boolean
     */
    public Boolean rejectPresentationById(Integer presentationId);
    
    /**
     * Update Booked Presentation To Marked By Presentation Id
     * @param presentationId
     * @param marks
     * @return Boolean
     */
    public Boolean updatePresentationMarksById(Integer presentationId, Double marks);
}
