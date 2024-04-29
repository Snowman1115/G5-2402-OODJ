package com.project.service.Impl;

import com.project.dao.PresentationDAO;
import com.project.service.PresentationService;

import java.util.List;
import java.util.Map;

public class PresentationServiceImpl implements PresentationService {

    private PresentationDAO presentationDAO = new PresentationDAO();

    /**
     * Get All Presentaion Status By Student Id
     * @param studentId
     * @return Map of List
     */
    @Override
    public Map<String, Integer> getAllPresentationStatusByStudentId(Integer studentId) {
        return presentationDAO.getAllPresentationStatusByStudentId(studentId);
    }

}
