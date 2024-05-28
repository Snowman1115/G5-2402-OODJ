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
import com.project.service.PresentationService;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class PresentationServiceImpl {
    public Map<String, Integer> getAllPresentationStatusByStudentId(Integer studentId) {
        return presentationDAO.getAllPresentationStatusByStudentId(studentId);
    }


    private PresentationDAO presentationDAO = new PresentationDAO();

    private UserAccountDAO userAccountDAO = new UserAccountDAO();
    
    private ModuleDAO moduleDAO = new ModuleDAO();

    /**
     * Get All Presentation Status By Student Id
     * @param studentId
     * @return Map of List
     */

    /**
     * Get All Upcoming N Pending Booking Presentation
     * @param studentId
     * @return Map of list
     */

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

    public List getAllPresentationDetailsForStudent(Integer studentId) {
        List<Map<String, String>> list = new ArrayList<>();
        for (Map<String, String> map : presentationDAO.getAllPresentationDetailsByStudentId(studentId)) {
            Map<String, String> mappedList = new HashMap<>();
            ProjectModule module = moduleDAO.getModuleById(Integer.valueOf(map.get("moduleId")));
            mappedList.put("id", map.get("id"));
            mappedList.put("moduleName",module.getModuleCode().toString());

            if(map.get("lecturerId").equals("0")) {
                mappedList.put("lecturerName", "Lecturer Not Assigned.");
            } else {
                UserAccount userAccount = userAccountDAO.getUserAccountById(Integer.valueOf(map.get("lecturerId")));
                mappedList.put("lecturerName", userAccount.getFirstName() + " " + userAccount.getLastName());
            }

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
    
    /**
     * Get All Pending Confirm And Pending Marking Presentation For Lecturer
     * @param lecturerId
     * @return Map of Integer
     */
    public Map<String, Integer> getPendingConfirmAndMarkingPresentationForLecturer(Integer lecturerId) {
        return presentationDAO.getPendingConfirmAndMarkingPresentationForLecturer(lecturerId);
//        return null;
    }
    
    /**
     * Get All Presentation Details By Lecturer Id
     * @param lecturerId
     * @return List
     */
    public List getAllPresentationDetailsByLecId(Integer lecturerId)
    {
        List<Map<String, String>> mappedLists = new ArrayList<>();
        //Get all cosultation details by lecturer ID
        List<Map<String, String>> presentationList = presentationDAO.getPresentationByLecturerId(lecturerId);
        for (Map<String, String> list : presentationList){
            Map<String, String> mappedMap = new HashMap<>();
            //Get module name by module ID that is from moduleDAO
            Integer moduleId=Integer.valueOf(list.get("moduleId"));
            ProjectModule module=moduleDAO.getModuleById(moduleId);
            String moduleName=module.getModuleCode();
            mappedMap.put("moduleName", moduleName);
            Integer studentId=Integer.valueOf(list.get("studentId"));
            //If the presentation slot is not yet booked by any student, set studentId, studentName, presentationDateTime and presentationResult to EMPTY before displaying to avoid bug     
            if(studentId != 0)
            {
                //Get student name by student ID that is from presentationDAO object
                UserAccount student=userAccountDAO.getUserAccountById(studentId);
                String studentName=student.getFirstName()+" "+student.getLastName();
                mappedMap.put("studentId", studentId.toString());
                mappedMap.put("studentName", studentName);
                mappedMap.put("presentationDateTime", list.get("presentationDateTime"));
                mappedMap.put("presentationResult", list.get("presentationResult"));
            }
            else 
            {
                mappedMap.put("studentId", "EMPTY");
                mappedMap.put("studentName", "EMPTY");
                mappedMap.put("presentationDateTime", "EMPTY");
                mappedMap.put("presentationResult", "EMPTY");
            }
            mappedMap.put("presentationDueDate", list.get("presentationDueDate"));
            mappedMap.put("presentationStatus", list.get("presentationStatus"));
            
            mappedLists.add(mappedMap);
        }
        return mappedLists;
    }


}
