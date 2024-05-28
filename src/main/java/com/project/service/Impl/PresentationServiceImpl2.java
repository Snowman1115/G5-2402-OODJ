/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package com.project.service.Impl;
import com.project.common.constants.MessageConstant;
import com.project.common.utils.DateTimeUtils;
import com.project.common.utils.Dialog;
import com.project.dao.ModuleDAO;
import com.project.dao.PresentationDAO;
import com.project.dao.UserAccountDAO;
import com.project.pojo.Presentation;
import com.project.pojo.ProjectModule;
import com.project.pojo.UserAccount;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class PresentationServiceImpl2 extends PresentationServiceImpl{

    private PresentationDAO presentationDAO = new PresentationDAO();

    private UserAccountDAO userAccountDAO = new UserAccountDAO();

    private ModuleDAO moduleDAO = new ModuleDAO();

    public Map<String, Integer> getAllPresentationStatusByStudentId(Integer studentId) {
        return super.getAllPresentationStatusByStudentId(studentId);
    }

    public List getAllUpcomingNPendingBookingPresentation(Integer studentId) {
        return super.getAllUpcomingNPendingBookingPresentation(studentId);
    }

    /**
     * Get All Booked Presentation By Lecturer Id
     * @param lecturerId
     * @return List of Map
     */
    public List<Map<String, String>> getAllBookedPresentationByLecId(Integer lecturerId) {
        List<Map<String, String>> mappedList = new ArrayList<>();
        List<Presentation> presentations = presentationDAO.getAllBookedPresentationForLec(lecturerId);
        for (Presentation presentation : presentations) {
            if(presentation.getPresentationDateTime().isAfter(LocalDateTime.now()))
            {
                Map<String,String> map = new HashMap<>();
                UserAccount userAccount = userAccountDAO.getUserAccountById(presentation.getStudentId());
                map.put("studentName", userAccount.getFirstName() + " " + userAccount.getLastName());
                map.put("presentationDateTime", DateTimeUtils.formatStrDateTime(presentation.getPresentationDateTime()));
                map.put("presentationStatus", presentation.getPresentationStatus().toString());
                mappedList.add(map);
            }

        }
        return mappedList;
    }

    /**
     * Get All Pending Confirm Presentation By Lecturer Id
     * @param lecturerId
     * @return List of Map
     */
    public List<Map<String, String>> getAllPendingConfirmPresentationByLecId(Integer lecturerId) {
        List<Map<String, String>> mappedList = new ArrayList<>();
        List<Presentation> presentations = presentationDAO.getAllPendingConfirmPresentationForLec(lecturerId);
        for (Presentation presentation : presentations) {
            Map<String,String> map = new HashMap<>();
            map.put("id", presentation.getPresentationId().toString());
            UserAccount userAccount = userAccountDAO.getUserAccountById(presentation.getStudentId());
            map.put("studentName", userAccount.getFirstName() + " " + userAccount.getLastName());
            map.put("presentationDateTime", DateTimeUtils.formatStrDateTime(presentation.getPresentationDateTime()));
            map.put("presentationDueDate", DateTimeUtils.formatStrDateTime(presentation.getPresentationDueDate()));
            Integer moduleId=presentation.getModuleId();
            ProjectModule module=moduleDAO.getModuleById(moduleId);
            String moduleName=module.getModuleCode();
            map.put("moduleName", moduleName);
            map.put("presentationStatus", presentation.getPresentationStatus().toString());
            mappedList.add(map);
        }
        return mappedList;
    }

    /**
     * Get All Not Yet Graded Presentation By Lecturer Id
     * @param lecturerId
     * @return List of Map
     */
    public List<Map<String, String>> getNotYetGradedPresentationByLecId(Integer lecturerId) {
        List<Map<String, String>> mappedList = new ArrayList<>();
        List<Presentation> presentations = presentationDAO.getNotYetGradedPresentationForLec(lecturerId);
        for (Presentation presentation : presentations) {
            Map<String,String> map = new HashMap<>();
            map.put("id", presentation.getPresentationId().toString());
            UserAccount userAccount = userAccountDAO.getUserAccountById(presentation.getStudentId());
            map.put("studentName", userAccount.getFirstName() + " " + userAccount.getLastName());
            map.put("presentationDateTime", DateTimeUtils.formatStrDateTime(presentation.getPresentationDateTime()));
            map.put("presentationDueDate", DateTimeUtils.formatStrDateTime(presentation.getPresentationDueDate()));
            Integer moduleId=presentation.getModuleId();
            ProjectModule module=moduleDAO.getModuleById(moduleId);
            String moduleName=module.getModuleCode();
            map.put("moduleName", moduleName);
            map.put("presentationStatus", presentation.getPresentationStatus().toString());
            map.put("presentationMarks", presentation.getPresentationResult().toString());
            mappedList.add(map);
        }
        return mappedList;
    }


    /**
     * Update Pending Confirm Presentation To Accepted By Presentation Id
     * @param presentationId
     * @return Boolean
     */
    public Boolean acceptPresentationById(Integer presentationId, LocalDateTime dateTime, Integer lecturerId) {
        List<Presentation> presentationList = presentationDAO.getAllBookedPresentationForLec(lecturerId);
        for (Presentation presentation : presentationList){
            LocalDateTime requestedDateTime=presentation.getPresentationDateTime();
            //Check whether the requested presentation date time is clashed with the other booked presentation date time
            if (lecturerId.equals(presentation.getLecturerId()) && requestedDateTime.equals(dateTime)) {
                //If found clashing, automatically reject the presentation and display an error message
                presentationDAO.rejectPresentationById(presentationId);
                log.info("Presentation Status Update To Rejected Successfully: " + presentationId);
                Dialog.ErrorDialog(MessageConstant.ERROR_PRESENTATION_DATETIME_CLASHED);
                return false;
            }
        }
        presentationDAO.acceptPresentationById(presentationId);
        log.info("Presentation Status Update To Booked Successfully: " + presentationId);
        Dialog.SuccessDialog(MessageConstant.SUCCESS_CONFIRMED_PRESENTATION_SLOT);
        return true;
    }

    /**
     * Update Pending Confirm Presentation To Rejected By Presentation Id
     * @param presentationId
     * @return Boolean
     */
    public Boolean rejectPresentationById(Integer presentationId) {
        if (presentationDAO.rejectPresentationById(presentationId)) {
            log.info("Presentation Status Update To Rejected Successfully: " + presentationId);
            Dialog.SuccessDialog(MessageConstant.SUCCESS_REJECTED_PRESENTATION_SLOT);
            return true;
        } else{
            log.info("Presentation Status Update To Rejected Failed: " + presentationId);
            return false;
        }
    }

    /**
     * Update Booked Presentation To Marked By Presentation Id
     * @param presentationId
     * @param marks
     * @return Boolean
     */
    public Boolean updatePresentationMarksById(Integer presentationId, Double marks) {
        if (presentationDAO.updatePresentationMarksById(presentationId, marks)) {
            log.info("Presentation Status Update To Marked Successfully: " + presentationId);
            return true;
        } else{
            log.info("Presentation Status Update To Marked Failed: " + presentationId);
            return false;
        }
    }

}
