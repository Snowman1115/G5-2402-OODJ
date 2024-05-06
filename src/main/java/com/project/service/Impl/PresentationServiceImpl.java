package com.project.service.Impl;

import com.project.common.constants.PresentationStatus;
import com.project.common.utils.DateTimeUtils;
import com.project.dao.ModuleDAO;
import com.project.dao.PresentationDAO;
import com.project.dao.UserAccountDAO;
import com.project.pojo.ProjectModule;
import com.project.pojo.UserAccount;
import com.project.service.PresentationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PresentationServiceImpl implements PresentationService {

    private PresentationDAO presentationDAO = new PresentationDAO();

    private UserAccountDAO userAccountDAO = new UserAccountDAO();
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

    /**
     * Get All Presentation Details for Student
     * @param studentId
     * @return List of Map
     */
    @Override
    public List getAllPresentationDetailsForStudent(Integer studentId) {
        List<Map<String, String>> list = new ArrayList<>();
        for (Map<String, String> map : presentationDAO.getAllPresentationDetailsByStudentId(studentId)) {
            Map<String, String> mappedList = new HashMap<>();
            ProjectModule module = moduleDAO.getModuleById(Integer.valueOf(map.get("moduleId")));
            mappedList.put("moduleName",module.getModuleCode().toString());
            UserAccount userAccount = userAccountDAO.getUserAccountById(Integer.valueOf(map.get("lecturerId")));
            mappedList.put("lecturerName", userAccount.getFirstName() + " " + userAccount.getLastName());
            mappedList.put("dueDate", map.get("dueDate"));
            if (map.get("Status").equals("PENDING_BOOKING")) {
                mappedList.put("presentationDate", "EMPTY");
            } else {
                mappedList.put("presentationDate", map.get("presentationDate"));
            }
            mappedList.put("Status", map.get("Status"));
            list.add(mappedList);
        }
        return list;
    }

}
