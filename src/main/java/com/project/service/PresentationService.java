package com.project.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface PresentationService {

    /**
     * Get All Presentaion Status By Student Id
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


}
