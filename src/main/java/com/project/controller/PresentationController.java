package com.project.controller;

import com.project.service.Impl.PresentationServiceImpl;
import com.project.service.Impl.UserAuthenticationServiceImpl;
import com.project.service.PresentationService;
import com.project.service.UserAuthenticationService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class PresentationController {

    private static UserAuthenticationService userAuthenticationService = new UserAuthenticationServiceImpl();
    private static PresentationService presentationService = new PresentationServiceImpl();

    /**
     * Get All Presentaion Status By Student Id
     * @return Map of List
     */
    public static Map<String,Integer> getAllPresentationStatusByStudentId() {
        return presentationService.getAllPresentationStatusByStudentId(getAuthenticatedUserId());
    }

    /**
     * Get All Upcoming N Pending Booking Presentation
     * @return Map of list
     */
    public static List getAllUpcomingNPendingBookingPresentation() {
        return presentationService.getAllUpcomingNPendingBookingPresentation(getAuthenticatedUserId());
    }

    /**
     * Get All Presentation Details For Student
     * @return Map of List
     */
    public static List getAllPresentationDetailsForStudent() {
        return presentationService.getAllPresentationDetailsForStudent(getAuthenticatedUserId());
    }

    /**
     * Get Authenticated UserId
     * @return userId
     */
    private static Integer getAuthenticatedUserId() {
        return userAuthenticationService.getAuthenticationUserDetails().getUserId();
    }


}
