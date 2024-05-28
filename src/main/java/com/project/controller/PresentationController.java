package com.project.controller;

import com.project.service.Impl.PresentationServiceImpl;
import com.project.service.Impl.PresentationServiceImpl2;
import com.project.service.Impl.UserAuthenticationServiceImpl;
import com.project.service.PresentationService;
import com.project.service.UserAuthenticationService;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
public class PresentationController {

    private static UserAuthenticationService userAuthenticationService = new UserAuthenticationServiceImpl();
    private static PresentationServiceImpl presentationService = new PresentationServiceImpl();
    private static PresentationServiceImpl2 presentationService2 = new PresentationServiceImpl2();

    /**
     * Get All Presentation Status By Student Id
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
     * Book Presentation Date Time For Student By Presentation ID and Date Time
     * @param presentationId
     * @param dateTime
     * @return Boolean
     */
    public static Boolean bookPresentationSlotForStudent(Integer presentationId, LocalDateTime dateTime) {
        log.info("Book Presentation Slot : " + presentationId);
        return presentationService.bookPresentationSlotByStudentId(presentationId, dateTime);
    }

    /**
     * Modify Presentation Date Time For Student (Condition: PENDING_BOOKING)
     * @param presentationId
     * @param dateTime
     * @return Boolean
     */
    public static Boolean editPresentationSlotForStudent(Integer presentationId, LocalDateTime dateTime) {
        log.info("Edit Presentation Slot : " + presentationId);
        return presentationService.editPresentationSlotByStudentId(presentationId, dateTime);
    }

    /**
     * Cancel Presentation Slot For Student (Condition: PENDING_BOOKING)
     * @param presentationId
     * @return Boolean
     */
    public static Boolean cancelPresentationSlotForStudent(Integer presentationId) {
        log.info("Cancel Presentation Slot : " + presentationId);
        return presentationService.cancelPresentationSlotByStudentId(presentationId);
    }
    
    /**
     * Get All Pending Confirm And Pending Marking Presentation For Lecturer
     * @return Map of list
     */
    public static Map<String,Integer>getPendingConfirmAndMarkingPresentationForLecturer() {
        return presentationService.getPendingConfirmAndMarkingPresentationForLecturer(getAuthenticatedUserId());
    }
    
    /**
     * Get All Presentation Details By Lecturer Id
     * @return List
     */
    public static List getAllPresentationDetailsByLecId() {
        return presentationService.getAllPresentationDetailsByLecId(getAuthenticatedUserId());
    }

    /**
     * Get All Booked Presentation By Lecturer Id
     * @return List
     */
    public static List getAllBookedPresentationByLecId() {
        return presentationService2.getAllBookedPresentationByLecId(getAuthenticatedUserId());
    } 
    
    /**
     * Get All Pending Confirm Presentation By Lecturer Id
     * @return List
     */
    public static List getAllPendingConfirmPresentationByLecId() {
        return presentationService2.getAllPendingConfirmPresentationByLecId(getAuthenticatedUserId());
    } 
    
    /**
     * Get All Not Yet Graded Presentation By Lecturer Id
     * @return List
     */
    public static List getNotYetGradedPresentationByLecId() {
        return presentationService2.getNotYetGradedPresentationByLecId(getAuthenticatedUserId());
    }    

    /**
     * Update Pending Confirm Presentation To Book By Presentation Id
     * @param presentationId
     * @param dateTime
     * @return Boolean
     */
    public static Boolean acceptPresentationById(Integer presentationId, LocalDateTime dateTime) {
        log.info("Pending Confirm Presentation with ID: " + presentationId + " is accepted");
        return presentationService2.acceptPresentationById(presentationId, dateTime, getAuthenticatedUserId());
    }
    
    /**
     * Update Pending Confirm Presentation To Rejected By Consultation Id
     * @param presentationId
     * @param
     * @return Boolean
     */
    public static Boolean rejectPresentationById(Integer presentationId) {
        log.info("Pending Confirm Presentation with ID: " + presentationId + " is rejected");
        return presentationService2.rejectPresentationById(presentationId);
    }
    
    /**
     * Update Booked Presentation To Marked By Consultation Id
     * @param presentationId
     * @param marks
     * @return Boolean
     */
    public static Boolean updatePresentationMarksById(Integer presentationId, Double marks) {
        log.info("Booked Presentation with ID: " + presentationId + " is updated to MARKED");
        return presentationService2.updatePresentationMarksById(presentationId, marks);
    }
    
    /**
     * Get Authenticated UserId
     * @return userId
     */
    private static Integer getAuthenticatedUserId() {
        return userAuthenticationService.getAuthenticationUserDetails().getUserId();
    }  
}
