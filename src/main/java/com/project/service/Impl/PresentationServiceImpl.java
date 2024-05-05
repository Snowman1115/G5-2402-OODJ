package com.project.service.Impl;

import com.project.dao.ModuleDAO;
import com.project.dao.PresentationDAO;
import com.project.pojo.ProjectModule;
import com.project.service.PresentationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PresentationServiceImpl implements PresentationService {

    private PresentationDAO presentationDAO = new PresentationDAO();

    private ModuleDAO moduleDAO = new ModuleDAO();

    /**
     * Get All Presentaion Status By Student Id
     *
     * @param studentId
     * @return Map of List
     */
    @Override
    public Map<String, Integer> getAllPresentationStatusByStudentId(Integer studentId) {
        return presentationDAO.getAllPresentationStatusByStudentId(studentId);
    }

    /**
     * Get All Upcoming N Pending Booking Presentation
     *
     * @param studentId
     * @return Map of list
     */
    @Override
    public List getAllUpcomingNPendingBookingPresentation(Integer studentId) {
        List<Map<String, String>> list = new ArrayList<>();
        for (Map<String, String> map : presentationDAO.getAllUpcomingPendingBookingPresentation(studentId)) {
            Map<String, String> mappedList = new HashMap<>();
            ProjectModule module = moduleDAO.getModuleById(Integer.valueOf(map.get("moduleId")));
            mappedList.put("moduleName",module.getModuleCode().toString());
            mappedList.put("dueDate",map.get("dueDate"));
            list.add(mappedList);
        }
        return list;
    }

}
