package com.project.controller;

import com.project.service.Impl.ModuleFeedbackServiceImpl;
import com.project.service.Impl.UserAuthenticationServiceImpl;
import com.project.service.ModuleFeedbackService;
import com.project.service.UserAuthenticationService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class ModuleFeedbackController {

    private static UserAuthenticationService userAuthenticationService = new UserAuthenticationServiceImpl();
    private static ModuleFeedbackService moduleFeedbackService = new ModuleFeedbackServiceImpl();

    /**
     * Get Available Feedback Module For Student
     * @return List of Map
     */
    public static List<Map<String,String>> getAllAvailableFeedbackForStudent() {
        return moduleFeedbackService.getAllAvailableFeedbackForStudent(getAuthenticatedUserId());
    }

    /**
     * Get Module Details By Module Code
     * @param moduleCode
     * @return Details of module
     */
    public static Map getProjectModuleByCode(String moduleCode) {
        return moduleFeedbackService.getProjectModuleByCode(moduleCode);
    }

    /**
     * Submit Module Feedback
     * @param moduleId
     * @param commets
     * @return Boolean
     */
    public static Boolean submitModuleFeedback(Integer moduleId, String commets) {
        log.info("Student Submit Feedback : ", getAuthenticatedUserId());
        return moduleFeedbackService.submitModuleFeedback(getAuthenticatedUserId(),moduleId,commets);
    }

    /**
     * Get Authenticated UserId
     * @return userId
     */
    private static Integer getAuthenticatedUserId() {
        return userAuthenticationService.getAuthenticationUserDetails().getUserId();
    }


}
