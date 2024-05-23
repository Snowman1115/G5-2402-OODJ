package com.project.service.Impl;

import com.project.common.constants.MessageConstant;
import com.project.common.utils.Dialog;
import com.project.dao.ModuleDAO;
import com.project.dao.ModuleFeedbackDAO;
import com.project.dao.SubmissionDAO;
import com.project.dao.UserAccountDAO;
import com.project.pojo.ModuleFeedback;
import com.project.pojo.ProjectModule;
import com.project.pojo.Submission;
import com.project.pojo.UserAccount;
import com.project.service.ModuleFeedbackService;
import com.project.service.ProjectModuleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.Message;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class ModuleFeedbackServiceImpl implements ModuleFeedbackService {

    private SubmissionDAO submissionDAO = new SubmissionDAO();
    private ModuleFeedbackDAO moduleFeedbackDAO = new ModuleFeedbackDAO();
    private ModuleDAO moduleDAO = new ModuleDAO();
    private UserAccountDAO userAccountDAO = new UserAccountDAO();

    /**
     * Get Available Feedback Module For Student
     * @param studentId
     * @return List of Map
     */
    @Override
    public List<Map<String, String>> getAllAvailableFeedbackForStudent(Integer studentId) {
        List<Map<String, String>> mappedList = new ArrayList<>();

        List<ModuleFeedback> feedbacks = moduleFeedbackDAO.getAllFeedbackByStudentId(studentId);
        List<Map<String, String>> submissions = submissionDAO.getAllSubmissionDetailsByStudentId(studentId);

        Set<Integer> feedbackModuleIds = new HashSet<>();
        for (ModuleFeedback feedback : feedbacks) {
            if (feedback.getStudentId().equals(studentId)) {
                feedbackModuleIds.add(feedback.getModuleId());
            }
        }

        for (Map<String, String> submission : submissions) {
            Integer submissionModuleId = Integer.parseInt(submission.get("moduleId"));
            if (!feedbackModuleIds.contains(submissionModuleId)) {
                ProjectModule projectModule = moduleDAO.getModuleById(submissionModuleId);
                if (projectModule != null) {
                    Map<String, String> map = new HashMap<>();
                    map.put("moduleCode", projectModule.getModuleCode());
                    mappedList.add(map);
                }
            }
        }

        return mappedList;
    }

    @Override
    public Map<String,String> getProjectModuleByCode(String moduleCode) {
        Map<String,String> map = new HashMap<>();
        ProjectModule projectModule = moduleDAO.getModuleByCode(moduleCode);
        map.put("moduleId", projectModule.getModuleId().toString());
        if (projectModule.getFirstMarker().equals(0)) {
            map.put("lecturerName","Lecturer Not Assigned.");
        } else {
            UserAccount userAccount = userAccountDAO.getUserAccountById(projectModule.getFirstMarker());
            map.put("lecturerName", userAccount.getFirstName() + " " + userAccount.getLastName());
        }
        return map;
    }

    @Override
    public Boolean submitModuleFeedback(Integer studentId,Integer moduleId, String commets) {
        if (moduleFeedbackDAO.submitFeedback(studentId,moduleId,commets)){
            Dialog.SuccessDialog(MessageConstant.SUCCESS_SUBMIT_FEEDBACK);
            log.info("Student Feedback Submit Successful : " + studentId);
            return true;
        } else {
            Dialog.ErrorDialog(MessageConstant.UNEXPECTED_ERROR);
            log.info(MessageConstant.UNEXPECTED_ERROR);
            return false;
        }
    }

}
