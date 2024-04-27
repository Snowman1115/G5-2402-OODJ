package com.project.service;

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

}
