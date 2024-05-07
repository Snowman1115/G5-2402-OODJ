package com.project.service.Impl;

import com.project.common.constants.MessageConstant;
import com.project.common.constants.PresentationStatus;
import com.project.common.utils.DateTimeUtils;
import com.project.common.utils.Dialog;
import com.project.dao.ModuleDAO;
import com.project.dao.PresentationDAO;
import com.project.dao.UserAccountDAO;
import com.project.pojo.ProjectModule;
import com.project.pojo.UserAccount;
import com.project.service.PresentationService;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
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
            mappedList.put("id", map.get("id"));
            mappedList.put("moduleName",module.getModuleCode().toString());
            UserAccount userAccount = userAccountDAO.getUserAccountById(Integer.valueOf(map.get("lecturerId")));
            mappedList.put("lecturerName", userAccount.getFirstName() + " " + userAccount.getLastName());
            mappedList.put("dueDate", map.get("dueDate"));
            if (map.get("Status").equals("PENDING_BOOKING")) {
                mappedList.put("presentationDate", "EMPTY");
            } else if (map.get("Status").equals("OVERDUE")) {
                mappedList.put("presentationDate", "EMPTY");
            } else {
                mappedList.put("presentationDate", map.get("presentationDate"));
            }
            mappedList.put("Status", map.get("Status"));
            mappedList.put("result", map.get("result"));
            list.add(mappedList);
        }
        return list;
    }

    /**
     * Book Presentation Slot By For Student
     * @param presentationId
     * @param dateTime
     * @return Boolean
     */
    @Override
    public Boolean bookPresentationSlotByStudentId(Integer presentationId, LocalDateTime dateTime) {
        // Check Slot Availability
        if (!presentationDAO.checkAvailableSlot(presentationId,dateTime)) {
            log.info("Presentation Slot Booked Fail : " + MessageConstant.ERROR_PRESENTATION_SLOT_BOOKED);
            Dialog.ErrorDialog(MessageConstant.ERROR_PRESENTATION_SLOT_BOOKED);
            return false;
        }

        if (presentationDAO.bookPresentationSlot(presentationId, dateTime)) {
            log.info("Presentation Slot Booked Successfully : " + MessageConstant.ERROR_PRESENTATION_SLOT_BOOKED);
            Dialog.SuccessDialog(MessageConstant.SUCCESS_BOOKED_PRESENTATION_SLOT);
            return true;
        } else {
            log.warn("UNEXPECTED ERROR : " + MessageConstant.UNEXPECTED_ERROR);
            Dialog.SuccessDialog(MessageConstant.UNEXPECTED_ERROR);
            return false;
        }

    }

    /**
     * Edit Presentation Slot By For Student
     * @param presentationId
     * @param dateTime
     * @return Boolean
     */
    @Override
    public Boolean editPresentationSlotByStudentId(Integer presentationId, LocalDateTime dateTime) {
        // Check Slot Availability
        if (!presentationDAO.checkAvailableSlot(presentationId,dateTime)) {
            log.info("Presentation Slot Booked Fail : " + MessageConstant.ERROR_PRESENTATION_SLOT_BOOKED);
            Dialog.ErrorDialog(MessageConstant.ERROR_PRESENTATION_SLOT_BOOKED);
            return false;
        }

        if (presentationDAO.bookPresentationSlot(presentationId, dateTime)) {
            log.info("Presentation Slot Booked Successfully : " + MessageConstant.SUCCESS_BOOKED_PRESENTATION_SLOT);
            Dialog.SuccessDialog(MessageConstant.SUCCESS_BOOKED_PRESENTATION_SLOT);
            return true;
        } else {
            log.warn("UNEXPECTED ERROR : " + MessageConstant.UNEXPECTED_ERROR);
            Dialog.SuccessDialog(MessageConstant.UNEXPECTED_ERROR);
            return false;
        }

    }

    /**
     * Cancel Presentation Slot For Student (Condition: PENDING_BOOKING)
     * @param presentationId
     * @return Boolean
     */
    @Override
    public Boolean cancelPresentationSlotByStudentId(Integer presentationId) {
        if (presentationDAO.cancelPresentationSlot(presentationId)) {
            log.info("Presentation Slot Cancelled Successfully : " + MessageConstant.SUCCESS_CANCELLED_PRESENTATION_SLOT);
            Dialog.SuccessDialog(MessageConstant.SUCCESS_CANCELLED_PRESENTATION_SLOT);
            return true;
        } else {
            log.warn("UNEXPECTED ERROR : " + MessageConstant.UNEXPECTED_ERROR);
            Dialog.SuccessDialog(MessageConstant.UNEXPECTED_ERROR);
            return false;
        }
    }
}
