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

}
