package com.project.service;

import java.util.List;
import java.util.Map;

public interface PresentationService {

    /**
     * Get All Presentaion Status By Student Id
     * @param studentId
     * @return Map of List
     */
    public Map<String, Integer> getAllPresentationStatusByStudentId(Integer studentId);


}
