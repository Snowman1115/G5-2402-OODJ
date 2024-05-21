package com.project.service;

import java.util.List;
import java.util.Map;

public interface SubmissionService {

    /**
     * Get All Submission Status By Student Id
     * @param studentId
     * @return Map of List
     */
    public Map<String, Integer> getAllSubmissionStatusByStudentId(Integer studentId);

    /**
     * Get All Submission Details for Student
     * @param studentId
     * @return List of Map
     */
    List getAllSubmissionDetailsForStudent(Integer studentId);

    /**
     * Submit report base on submission Id
     * @param submissionId
     * @param reportPath
     * @return Boolean
     */
    Boolean submitReport(Integer submissionId, String reportPath);

    /**
     * Remove Report By Submission ID
     * @param submissionId
     * @return Boolean
     */
    Boolean removeSubmissionById(Integer submissionId);

    /**
     * Get All Pending Marking Submission (First and Second Marker) By Lecturer ID
     * @param lecturerId
     * @return Map of List
     */
    public Map<String,Integer> getPendingMarkingSubmissionForLecturer(Integer lecturerId);
    
    /**
     * Get All Upcoming Submission Due Date (First and Second Marker) By Lecturer ID
     * @param lecturerId
     * @return List
     */
    public List<Map<String, String>> getAllUpcomingSubmissionDueDateByLecId(Integer lecturerId);
    
    /**
     * Get All Submission Details By Lecturer Id
     * @param lecturerId
     * @return List
     */
    public List getAllSubmissionDetailsByLecId(Integer lecturerId); 
    
    /**
     * Get All Pending Marking Submission By Module Id
     * @param moduleId
     * @return List
     */
    public List getPendingMarkingSubmissionByModuleId(Integer moduleId);
    
    /**
     * Get All Marked_1 Submission By Module Id
     * @param moduleId
     * @return List
     */
    public List getMarked1SubmissionByModuleId(Integer moduleId);
    
    /**
     * Get All Marked_2 Submission By Module Id
     * @param moduleId
     * @return List
     */
    public List getMarked2SubmissionByModuleId(Integer moduleId);
    
    /**
     * Get Submission Details By Submission Id
     * @param submissionId
     * @return List
     */
    public List<Map<String, String>> getSubmissionDetailsById(Integer submissionId);
    
    /**
     * Get Marked_1 Submission Details By Submission Id
     * @param submissionId
     * @return List
     */
    public List<Map<String, String>> getMarked1SubmissionDetailsById(Integer submissionId);
    
    /**
     * Get Marked_2 Submission Details By Submission Id
     * @param submissionId
     * @return List
     */
    public List<Map<String, String>> getMarked2SubmissionDetailsById(Integer submissionId);
    
    /**
     * Update Submission Status To Marked_1 By Submission Id
     * @param submissionId
     * @param marks
     * @param comment
     * @return Boolean
     */
    public Boolean updateSubmissionMarksByIdForFirstMarker(Integer submissionId, Double marks, String comment);
    
    /**
     * Update Submission Status To Marked_2 By Submission Id
     * @param submissionId
     * @param marks
     * @param comment
     * @return Boolean
     */
    public Boolean updateSubmissionMarksByIdForSecondMarker(Integer submissionId, Double marks, String comment);

    public List getAssessmentByModuleId(Integer moduleId);

    public Boolean saveAssessmentType(Integer moduleId, String savedAssessment);

}
