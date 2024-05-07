package com.project.service.Impl;

import com.project.dao.ModuleDAO;
import com.project.dao.SubmissionDAO;
import com.project.dao.UserAccountDAO;
import com.project.pojo.ProjectModule;
import com.project.pojo.UserAccount;
import com.project.service.SubmissionService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class SubmissionServiceImpl implements SubmissionService {

    private SubmissionDAO submissionDAO = new SubmissionDAO();

    private UserAccountDAO userAccountDAO = new UserAccountDAO();
    private ModuleDAO moduleDAO = new ModuleDAO();

    /**
     * Get All Submission Status By Student Id
     * @param studentId
     * @return Map of List
     */
    @Override
    public Map<String, Integer> getAllSubmissionStatusByStudentId(Integer studentId) {
        return submissionDAO.getAllSubmissionStatusByStudentId(studentId);
    }

    /**
     * Get All Submission Details for Student
     * @param studentId
     * @return List of Map
     */
    @Override
    public List getAllSubmissionDetailsForStudent(Integer studentId) {
        List<Map<String, String>> list = new ArrayList<>();
        for (Map<String, String> map : submissionDAO.getAllSubmissionDetailsByStudentId(studentId)) {
            Map<String, String> mappedList = new HashMap<>();
            ProjectModule module = moduleDAO.getModuleById(Integer.valueOf(map.get("moduleId")));
            mappedList.put("id", map.get("id"));
            mappedList.put("moduleName",module.getModuleCode().toString());
            Integer lecturerId = module.getFirstMarker();
            UserAccount user = userAccountDAO.getUserAccountById(lecturerId);
            mappedList.put("lecturerName", user.getFirstName() + " " + user.getLastName());
            mappedList.put("reportId", map.get("reportId"));
            mappedList.put("dueDate", map.get("dueDate"));
            if (map.get("Status").equals("PENDING_SUBMIT")) {
                mappedList.put("submitAt", "EMPTY");
            } else if (map.get("Status").equals("OVERDUE")) {
                mappedList.put("submitAt", "EMPTY");
            } else {
                mappedList.put("submitAt", map.get("submitAt"));
            }
            mappedList.put("type", map.get("type"));
            mappedList.put("markedAt", map.get("markedAt"));
            mappedList.put("Status", map.get("Status"));
            mappedList.put("result", map.get("result"));
            list.add(mappedList);
        }
        return list;
    }

    @Override
    public Boolean submitStudentProject(Integer submissionId, String filePath) {



        return null;
    }
}
