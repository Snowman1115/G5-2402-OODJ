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

    public List getAllReportDetails();

    public List getAssessmentByModuleId(Integer moduleId);

    public Boolean saveAssessmentType(Integer moduleId, String savedAssessment);

}
