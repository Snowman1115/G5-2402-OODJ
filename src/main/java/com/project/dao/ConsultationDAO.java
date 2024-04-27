package com.project.dao;

import com.project.common.constants.ConsultationStatus;
import com.project.common.constants.MessageConstant;
import com.project.common.utils.DateTimeUtils;
import com.project.common.utils.FileHandler;
import com.project.common.utils.JsonHandler;
import com.project.common.utils.PropertiesReader;
import com.project.pojo.Consultation;
import com.project.pojo.UserAccount;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
public class ConsultationDAO {
    private static final String CONSULTATION_DATA = PropertiesReader.getProperty("ConsultationData");
    private static List<Consultation> consultations = new ArrayList<>();

    static {
        loadConsultationData();
    }

    /**
     * Return All Available Consultation Slots
     *
     * @return List of Available Consultation Slots
     */
    public List<Consultation> checkAllAvailableConsultationsSlots() {
        List<Consultation> lists = new ArrayList<>();
        for (Consultation consultation : consultations) {
            if (consultation.getConsultationStatus().equals(ConsultationStatus.AVAILABLE) && consultation.getConsultationDateTime().isAfter(LocalDateTime.now())) {
                lists.add(consultation);
            }
        }
        return lists;
    }

    /**
     * Get Specific Lecturer's All Available Slots
     *
     * @param lecturerId
     * @return List of availableConsultationSlot
     */
    public List<Consultation> checkAvailableConsultationsSlotsByLecturerId(Integer lecturerId) {
        List<Consultation> availableConsultationSlot = new ArrayList<>();
        for (Consultation consultation : consultations) {
            if (consultation.getLecturerId().equals(lecturerId) && consultation.getConsultationStatus().equals(ConsultationStatus.AVAILABLE)) {
                availableConsultationSlot.add(consultation);
            }
        }
        return availableConsultationSlot;
    }

    /**
     * Get All Consultation Details for Student
     *
     * @return List
     */
    public List<Consultation> getAllEventsForStudent(Integer studentId) {
        List<Consultation> list = new ArrayList<>();
        for (Consultation consultation : consultations) {
            if (consultation.getStudentId().equals(studentId)) {
                list.add(consultation);
            }
        }
        return list;
    }

    /**
     * Get Total Number of Upcoming and Finished Consultation For Student
     *
     * @param studentId
     * @return Map of Number
     */
    public Map<String, Integer> getUpcomingNFinishedConsultationForStudent(Integer studentId) {
        Map<String, Integer> map = new HashMap<>();
        Integer upcomingSum = 0;
        Integer finishedSum = 0;
        for (Consultation consultation : consultations) {
            if (consultation.getStudentId().equals(studentId)) {
                if (consultation.getConsultationDateTime().isBefore(LocalDateTime.now())) {
                    finishedSum = finishedSum + 1;
                } else if (consultation.getConsultationDateTime().isAfter(LocalDateTime.now())) {
                    upcomingSum = upcomingSum + 1;
                }
            }
        }
        map.put("upcoming", upcomingSum);
        map.put("finished", finishedSum);
        return map;
    }

    /**
     * Get Upcoming Event Details for Student
     *
     * @return List of Map
     */
    public List<Map<String, String>> getUpcomingEventForStudent(Integer studentId) {
        List<Map<String, String>> list = new ArrayList<>();
        for (Consultation consultation : consultations) {
            if (consultation.getStudentId().equals(studentId) && consultation.getConsultationDateTime().isAfter(LocalDateTime.now())) {
                Map<String, String> map = new HashMap<>();
                map.put("lecturer", consultation.getLecturerId().toString());
                map.put("date", DateTimeUtils.formatStrDateTime(consultation.getConsultationDateTime()));
                list.add(map);
            }
        }
        return list;
    }


    /**
     * Book Consultation Slots by Consultation Id
     *
     * @param consultationId
     * @param studentId
     * @return boolean
     */
    public Boolean bookConsultationSlot(Integer consultationId, Integer studentId) {
        for (Consultation consultation : consultations) {
            if (consultation.getConsultationId().equals(consultationId)) {
                update(consultationId, "studentId", studentId.toString());
                update(consultationId, "consultationStatus", "SCHEDULED");
                update(consultationId, "updated_at", DateTimeUtils.formatStrDateTime(LocalDateTime.now()));
                System.out.println(consultations);
                return true;
            }
        }
        return false;
    }

    /**
     * Preload Data into consultations Array
     */
    private static void loadConsultationData() {

        JsonHandler userData = new JsonHandler();
        userData.encode(FileHandler.readFile(CONSULTATION_DATA));

        for (int i = 0; i < (userData.getAll().size()); i++) {
            JsonHandler obj = new JsonHandler();
            obj.cloneObject(userData.getObject(i));

            Consultation consultation = new Consultation();
            consultation.setConsultationId(obj.getInt("consultationId"));
            consultation.setLecturerId(obj.getInt("lecturerId"));
            consultation.setStudentId(obj.getInt("studentId"));
            consultation.setConsultationDateTime(DateTimeUtils.formatDateTime(obj.get("consultationDateTime")));
            consultation.setConsultationStatus(ConsultationStatus.valueOf(obj.get("consultationStatus")));
            consultation.setCreatedAt(DateTimeUtils.formatDateTime(obj.get("createdAt")));
            consultation.setUpdatedAt(DateTimeUtils.formatDateTime(obj.get("updatedAt")));
            consultations.add(consultation);
        }

    }

    // Update consultation data
    public static boolean update(Integer consultationId, String field, String value) {
        // System.out.println(value);
        for (Consultation consultation : consultations) {
            if (consultation.getConsultationId() == consultationId) {
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
                    return false;
                }
            }
        }

        log.info("Error: " + MessageConstant.ERROR_OBJECT_NOT_FOUND + value);
        return false;

    }

    /**
     * Store updated data into text file
     * @param consultationId
     * @param attribute
     * @param value
     * @return
     */
    private static boolean store(Integer consultationId, String attribute, String value) {
        // System.out.println(consultationId + attribute + value);
        JsonHandler userJson = new JsonHandler();
        userJson.encode(FileHandler.readFile(CONSULTATION_DATA));
        return userJson.update(consultationId, attribute, value, CONSULTATION_DATA);
    }

}


