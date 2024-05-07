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
     * Submit Project With Submission ID
     * @param submissionId
     * @param filePath
     * @return Boolean
     */
    public static Boolean submitStudentProject(Integer submissionId, String filePath) {
        return submissionService.submitStudentProject(submissionId, filePath);
    }

    /**
     * Get Authenticated UserId
     * @return userId
     */
    private static Integer getAuthenticatedUserId() {
        return userAuthenticationService.getAuthenticationUserDetails().getUserId();
    }

}
