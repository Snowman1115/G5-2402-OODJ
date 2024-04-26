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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class ConsultationDAO {
    private static final String CONSULTATION_DATA = PropertiesReader.getProperty("ConsultationDate");
    private static List<Consultation> consultations = new ArrayList<>();

    static {
        loadConsultationData();
    }

    /**
     * Return All Consultation Slots
     * @return All Consultations Slots
     */
    public List<Consultation> checkAllConsultationsSlots() {
        return consultations;
    }

    /**
     * Get Specific Lecturer's All Available Slots
     * @param lecturerId
     * @return List of availableConsultationSlot
     */
    public static List<Consultation> checkAvailableConsultationsSlotsByLecturerId(Integer lecturerId) {
        List<Consultation> availableConsultationSlot = new ArrayList<>();
        for (Consultation consultation : consultations) {
            if (consultation.getLecturerId().equals(lecturerId) && consultation.getConsultationStatus().equals(ConsultationStatus.AVAILABLE)) {
                availableConsultationSlot.add(consultation);
            }
        }
        return availableConsultationSlot;
    }

    /**
     * Preload Data into consultations Array
     */
    private static void loadConsultationData() {

        JsonHandler userData = new JsonHandler();
        userData.encode(FileHandler.readFile(CONSULTATION_DATA));

        for (int i=0; i<(userData.getAll().size()); i++) {
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

    // Update user data
    public static boolean update(Integer userId, String field, String value) {
        // find user object in arraylist
       /* for (UserAccount user : users) {
            if (user.getUserId() == userId) {
                try {
                    switch (field) {
                        case "username" -> {
                            user.setUsername(value);
                            return store(userId, "username", value);
                        }
                        case "firstName" -> {
                            user.setFirstName(value);
                            return store(userId, "first_name", value);
                        }
                        case "lastName" -> {
                            user.setLastName(value);
                            return store(userId, "last_name", value);
                        }
                        case "password" -> {
                            String encryptedPassword = BCrypt.hashpw(value, BCrypt.gensalt());
                            user.setPassword(encryptedPassword);
                            return store(userId, "password", encryptedPassword);
                        }
                        case "email" -> {
                            user.setEmail(value);
                            return store(userId, "email", value);
                        }
                        case "securityPhrase" -> {
                            user.setPassword(value);
                            return store(userId, "safeWord", value);
                        }
                        case "updated_at" -> {
                            user.setUpdatedAt(DateTimeUtils.formatDateTime(value));
                            return store(userId, "updated_at", value);
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
        }*/

        log.info("Error: " + MessageConstant.ERROR_OBJECT_NOT_FOUND);
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
        JsonHandler userJson = new JsonHandler();
        userJson.encode(FileHandler.readFile(CONSULTATION_DATA));
        return userJson.update(consultationId, attribute, value, CONSULTATION_DATA);
    }

}
