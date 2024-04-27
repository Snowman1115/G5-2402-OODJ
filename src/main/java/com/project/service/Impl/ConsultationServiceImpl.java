package com.project.service.Impl;

import com.project.common.constants.MessageConstant;
import com.project.common.constants.UserRoleType;
import com.project.common.utils.DateTimeUtils;
import com.project.common.utils.Dialog;
import com.project.dao.ConsultationDAO;
import com.project.dao.UserAccountDAO;
import com.project.dao.UserRoleDAO;
import com.project.pojo.Consultation;
import com.project.pojo.UserAccount;
import com.project.service.ConsultationService;

import java.util.*;

public class ConsultationServiceImpl implements ConsultationService {

    private ConsultationDAO consultationDAO = new ConsultationDAO();
    private UserAccountDAO userAccountDAO = new UserAccountDAO();
    private UserRoleDAO userRoleDAO = new UserRoleDAO();

    /**
     * Get Number of Student's Upcoming and Finished Consultation
     * @return Map of Integer
     */
    @Override
    public Map<String, Integer> getUpcomingNFinishedConsultationForStudent(Integer studentId) {
        return consultationDAO.getUpcomingNFinishedConsultationForStudent(studentId);
    }

    @Override
    public List<Map<String, String>> getUpcomingEventForStudent(Integer studentId) {
        List<Map<String, String>> mappedLists = new ArrayList<>();
        List<Map<String, String>> lists = consultationDAO.getUpcomingEventForStudent(studentId);
        for (Map<String, String> event : lists) {
            Map<String, String> mappedMap = new HashMap<>();
            UserAccount userAccount = userAccountDAO.getUserAccountById(Integer.parseInt(event.get("lecturer")));
            mappedMap.put("lecturer", userAccount.getFirstName() + " " + userAccount.getLastName());
            mappedMap.put("date", event.get("date"));
            mappedLists.add(mappedMap);
        }
        return mappedLists;
    }

    /**
     * Get All Consultation Details for Student
     * @return List of Map
     */
    @Override
    public List getAllEventsForStudent(Integer studentId) {
        List<Map<String, String>> mappedLists = new ArrayList<>();
        List<Consultation> lists = consultationDAO.getAllEventsForStudent(studentId);
        for (Consultation consultation : lists) {
            Map<String, String> map = new HashMap<>();
            map.put("id", consultation.getConsultationId().toString());
            UserAccount userAccount = userAccountDAO.getUserAccountById(consultation.getLecturerId());
            map.put("lecturer", userAccount.getFirstName() + " " + userAccount.getLastName());
            map.put("date", DateTimeUtils.formatStrDateTime(consultation.getConsultationDateTime()));
            map.put("status", consultation.getConsultationStatus().toString());
            mappedLists.add(map);
        }
        return mappedLists;
    }

    /**
     * Get All Available Consultation Slots for Student
     * @return List of Map
     */
    @Override
    public List<Map<String, String>> getAllAvailableConsultationSlots() {
        List<Map<String, String>> mappedList = new ArrayList<>();
        List<Consultation> consultations = consultationDAO.checkAllAvailableConsultationsSlots();
        for (Consultation consultation : consultations) {
            Map<String,String> map = new HashMap<>();
            map.put("id", consultation.getConsultationId().toString());
            UserAccount userAccount = userAccountDAO.getUserAccountById(consultation.getLecturerId());
            map.put("lecturer", userAccount.getFirstName() + " " + userAccount.getLastName());
            map.put("date", DateTimeUtils.formatStrDateTime(consultation.getConsultationDateTime()));
            map.put("status", consultation.getConsultationStatus().toString());
            mappedList.add(map);
        }
        return mappedList;
    }

    /**
     * Get All Lecturer and Project Manager Name
     * @return List
     */
    @Override
    public List getAllLecturerName() {
        List<String> lecturers = new ArrayList<>();
        List<UserAccount> users = userAccountDAO.getAllUsers();
        for (UserAccount user : users) {
            if (userRoleDAO.checkRoleType(user.getUserId()).equals(UserRoleType.LECTURER) || userRoleDAO.checkRoleType(user.getUserId()).equals(UserRoleType.PROJECT_MANAGER)) {
                lecturers.add(user.getFirstName() + " " + user.getLastName());
            }
        }
        return lecturers;
    }

    /**
     * Book Consultation Slots by Consultation Id
     * @param consultationId
     * @param studentId
     * @return boolean
     */
    @Override
    public boolean bookConsultationSlot(Integer consultationId, Integer studentId) {
        if(consultationDAO.bookConsultationSlot(consultationId, studentId)) {
            Dialog.SuccessDialog(MessageConstant.SUCCESS_BOOKING_CONSULTATION);
            return true;
        };
        return false;
    }
}
