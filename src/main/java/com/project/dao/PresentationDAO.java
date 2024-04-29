package com.project.dao;

import com.project.common.constants.PresentationStatus;
import com.project.common.utils.DateTimeUtils;
import com.project.common.utils.FileHandler;
import com.project.common.utils.JsonHandler;
import com.project.common.utils.PropertiesReader;
import com.project.pojo.Presentation;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class PresentationDAO {

    private static final String PRESENTATION_DATA = PropertiesReader.getProperty("PresentationData");
    private static List<Presentation> presentations = new ArrayList<>();

    static {
        loadConsultationData();
    }

    /**
     * Get All Presentaion Status By Student Id
     * @param studentId
     * @return Map of List
     */
    public Map<String, Integer> getAllPresentationStatusByStudentId(Integer studentId) {
        int pendingBooking = 0;
        int pendingConfirm = 0;
        int confirmed = 0;
        int overdue = 0;
        List<Map<String, Integer>> list = new ArrayList<>();
        for (Presentation presentation : presentations) {
            if (presentation.getStudentId().equals(studentId)) {
                if (presentation.getPresentationStatus().equals(PresentationStatus.PENDING_BOOKING)) {
                    pendingBooking = pendingBooking + 1;
                } else if (presentation.getPresentationStatus().equals(PresentationStatus.PENDING_CONFIRM)) {
                    pendingConfirm = pendingConfirm + 1;
                } else if (presentation.getPresentationStatus().equals(PresentationStatus.BOOKED)) {
                    confirmed = confirmed + 1;
                } else if (presentation.getPresentationStatus().equals(PresentationStatus.OVERDUE)) {
                    overdue = overdue + 1;
                }
            }
        }
        Map<String, Integer> map = new HashMap<>();
        map.put("pendingBooking" , pendingBooking);
        map.put("pendingConfirm" , pendingConfirm);
        map.put("confirmed" , confirmed);
        map.put("overdue" , overdue);
        return map;
    }

    public static void main(String[] args) {
        System.out.println(presentations);
        PresentationDAO presentationDAO = new PresentationDAO();
        System.out.println(presentationDAO.getAllPresentationStatusByStudentId(4));
    }


    /**
     * Preload Data into presentations Array
     */
    private static void loadConsultationData() {
        JsonHandler userData = new JsonHandler();
        userData.encode(FileHandler.readFile(PRESENTATION_DATA));

        for (int i = 0; i < (userData.getAll().size()); i++) {
            JsonHandler obj = new JsonHandler();
            obj.cloneObject(userData.getObject(i));

            Presentation presentation = new Presentation();
            presentation.setPresentationId(obj.getInt("id"));
            presentation.setModuleId(obj.getInt("moduleId"));
            presentation.setLecturerId(obj.getInt("lecturerId"));
            presentation.setStudentId(obj.getInt("studentId"));
            presentation.setPresentationDueDate(DateTimeUtils.formatDateTime(obj.get("presentationDueDate")));
            presentation.setPresentationDateTime(DateTimeUtils.formatDateTime(obj.get("presentationDateTime")));
            presentation.setPresentationStatus(PresentationStatus.valueOf(obj.get("presentationStatus")));
            presentation.setPresentationResult(obj.getDouble("presentationResult"));
            presentation.setCreatedAt(DateTimeUtils.formatDateTime(obj.get("created_at")));
            presentation.setUpdatedAt(DateTimeUtils.formatDateTime(obj.get("updated_at")));

            presentations.add(presentation);
        }
    }



    /*

    // Update consultation data
    public static boolean update(Integer consultationId, String field, String value) {
        // System.out.println(value);
        for (Consultation consultation : consultations) {
            if (consultation.getConsultationId().equals(consultationId)) {
                try {
                    switch (field) {
                        case "lecturerId" -> {
                            consultation.setLecturerId(Integer.parseInt(value));
                            return store(consultationId, "lecturerId", value);
                        }
                        case "studentId" -> {
                            consultation.setStudentId(Integer.parseInt(value));
                            return store(consultationId, "studentId", value);
                        }
                        case "consultationDateTime" -> {
                            consultation.setConsultationDateTime(DateTimeUtils.formatDateTime(value));
                            return store(consultationId, "consultationDateTime", value);
                        }
                        case "consultationStatus" -> {
                            consultation.setConsultationStatus(ConsultationStatus.valueOf(value));
                            return store(consultationId, "consultationStatus", value);
                        }
                        case "updated_at" -> {
                            consultation.setUpdatedAt(DateTimeUtils.formatDateTime(value));
                            return store(consultationId, "updated_at", value);
                        }
                        default -> {
                            log.info("Error: " + MessageConstant.ERROR_OBJECT_FIELD_NOT_FOUND);
                            return false;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }

        log.info("Error: " + MessageConstant.ERROR_OBJECT_NOT_FOUND + value);
        return false;

    }

    *//**
     * Store updated data into text file
     * @param consultationId
     * @param attribute
     * @param value
     * @return
     *//*
    private static boolean store(Integer consultationId, String attribute, String value) {
        // System.out.println(consultationId + attribute + value);
        JsonHandler userJson = new JsonHandler();
        userJson.encode(FileHandler.readFile(CONSULTATION_DATA));
        return userJson.update(consultationId, attribute, value, CONSULTATION_DATA);
    }
*/


}
