package com.project.controller;

import com.project.service.Impl.SubmissionServiceImpl;
import com.project.service.Impl.UserAuthenticationServiceImpl;
import com.project.service.SubmissionService;
import com.project.service.UserAuthenticationService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class SubmissionController {

    private static SubmissionService submissionService = new SubmissionServiceImpl();

    private static UserAuthenticationService userAuthenticationService = new UserAuthenticationServiceImpl();

    /**
     * Get All Presentaion Status By Student Id
     * @return Map of List
     */
    public static Map<String,Integer> getAllSubmissionStatusByStudentId() {
        return submissionService.getAllSubmissionStatusByStudentId(getAuthenticatedUserId());
    }

    /**
     * Get All Presentation Details For Student
     * @return Map of List
     */
    public static List getAllSubmissionDetailsForStudent() {
        return submissionService.getAllSubmissionDetailsForStudent(getAuthenticatedUserId());
    }

    /**
     * Submit Report based on submissionId
     * @param submissionId
     * @param reportPath
     * @return Boolean
     */
    public static Boolean submitReport(Integer submissionId, String reportPath) {
        log.info("Project Submission : " + submissionId);
        return submissionService.submitReport(submissionId, reportPath);
    }

    /**
     * Remove Report By Submission ID
     * @param submissionId
     * @return Boolean
     */
    public static Boolean removeSubmissionById(Integer submissionId) {
        log.info("Submission Remove : " + submissionId);
        return submissionService.removeSubmissionById(submissionId);
    }

    /**
     * Get Authenticated UserId
     * @return userId
     */
    private static Integer getAuthenticatedUserId() {
        return userAuthenticationService.getAuthenticationUserDetails().getUserId();
    }
    
//    public static List getAssessmentByModuleId(Integer moduleId) {
//        return submissionService.getAssessmentByModuleId(moduleId);
//    }

    public static Boolean saveAssessmentType(Integer moduleId, String savedAssessment) {
        return submissionService.saveAssessmentType(moduleId, savedAssessment);
    }

}
