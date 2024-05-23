package com.project.service;

import java.util.List;
import java.util.Map;

public interface ModuleFeedbackService {

    /**
     * Get Available Feedback Module For Student
     * @param studentId
     * @return List of Map
     */
    public List<Map<String,String>> getAllAvailableFeedbackForStudent(Integer studentId);

    Map<String,String> getProjectModuleByCode(String moduleCode);

    Boolean submitModuleFeedback(Integer studentId,Integer moduleId, String commets);
}
