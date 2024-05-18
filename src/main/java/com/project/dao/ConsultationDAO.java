package com.project.dao;

import com.project.common.constants.ConsultationStatus;
import com.project.common.constants.MessageConstant;
import com.project.common.utils.*;
import com.project.pojo.Consultation;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.*;
import org.json.simple.JSONObject;

@Slf4j
public class ConsultationDAO {
    private static final String CONSULTATION_DATA = PropertiesReader.getProperty("ConsultationData");
    private static List<Consultation> consultations = new ArrayList<>();

    private IDGenerator shortIdGenerator = new ShortIDGenerator();

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
     * @param studentId
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
     * @param studentId
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
     * @param consultationId
     * @param studentId
     * @return Boolean
     */
    public Boolean bookConsultationSlot(Integer consultationId, Integer studentId) {
        for (Consultation consultation : consultations) {
            if (consultation.getConsultationId().equals(consultationId)) {
                update(consultationId, "studentId", studentId.toString());
                update(consultationId, "consultationStatus", "SCHEDULED");
                update(consultationId, "updated_at", DateTimeUtils.formatStrDateTime(LocalDateTime.now()));
                return true;
            }
        }
        return false;
    }

    /**
     * Cancel Booked Consultation Details By Consultation Id
     * @param consultationId
     * @return
     */
    public Boolean cancelBookedConsultationById(Integer consultationId) {
        for (Consultation consultation : consultations) {
            if (consultation.getConsultationId().equals(consultationId)) {
                update(consultationId, "studentId", "0");
                update(consultationId, "consultationStatus", ConsultationStatus.AVAILABLE.toString());
                update(consultationId, "updated_at", DateTimeUtils.formatStrDateTime(LocalDateTime.now()));
                return true;
            }
        }
        return false;
    }
    
    /**
     * Get All Consultation Details By Lecturer Id
     * @param lecturerId
     * @return
     */    
    
    public List getConsultationByLecturerId(Integer lecturerId) {
        List<Map<String, String>> list = new ArrayList<>();
        for (Consultation consultation : consultations) {
            if (consultation.getLecturerId().equals(lecturerId)) {
                Map map = new HashMap<>();
                map.put("id", consultation.getConsultationId().toString());
                map.put("lecturerId", consultation.getLecturerId().toString());
                map.put("studentId", consultation.getStudentId().toString());
                map.put("consultationDateTime", DateTimeUtils.formatStrDateTime(consultation.getConsultationDateTime()));
                map.put("consultationStatus", consultation.getConsultationStatus().toString());
                map.put("created_at", DateTimeUtils.formatStrDateTime(consultation.getCreatedAt()));
                map.put("updated_at", DateTimeUtils.formatStrDateTime(consultation.getUpdatedAt()));
                list.add(map);
            }
        }
        return list;
    }
    
    /**
     * Get Total Number of Available and Scheduled Consultation For Lecturer
     * @param lecturerId
     * @return Map of Number
     */
    public Map<String, Integer> getAvailableNScheduledConsultationForLecturer(Integer lecturerId) {
        Map<String, Integer> map = new HashMap<>();
        Integer upcomingSum = 0;
        Integer availableSum = 0;
        for (Consultation consultation : consultations) {
            if (consultation.getLecturerId().equals(lecturerId)) {
                if (consultation.getConsultationStatus().equals(ConsultationStatus.AVAILABLE)) {
                    availableSum = availableSum + 1;
                } else if (consultation.getConsultationStatus().equals(ConsultationStatus.SCHEDULED)) {
                    upcomingSum = upcomingSum + 1;
                }
            }
        }
        map.put("upcoming", upcomingSum);
        map.put("available", availableSum);
        return map;
    }
    
    /**
     * Get All Scheduled Consultation for Lecturer
     * @param lecturerId
     * @return List
     */
    public List<Consultation> getAllScheduledConsultationForLec(Integer lecturerId) {
        List<Consultation> list = new ArrayList<>();
        for (Consultation consultation : consultations) {
            if (consultation.getLecturerId().equals(lecturerId) && consultation.getConsultationStatus().equals(ConsultationStatus.SCHEDULED)) {
                list.add(consultation);
            }
        }
        return list;
    }
    /**
     * Get All Scheduled Consultation Except Completed for Lecturer
     * @param lecturerId
     * @return List
     */
    public List<Consultation> getAllConsultationExceptCompletedForLec(Integer lecturerId) {
        List<Consultation> list = new ArrayList<>();
        for (Consultation consultation : consultations) {
            if (consultation.getLecturerId().equals(lecturerId) && !consultation.getConsultationStatus().equals(ConsultationStatus.COMPLETED)) {
                list.add(consultation);
            }
        }
        return list;
    }
    
    /**
     * Update Booked Consultation To Complete By Consultation Id
     * @param consultationId
     * @return Boolean
     */
    public Boolean completeBookedConsultationById(Integer consultationId) {
        for (Consultation consultation : consultations) {
            if (consultation.getConsultationId().equals(consultationId)) {
                update(consultationId, "consultationStatus", ConsultationStatus.COMPLETED.toString());
                update(consultationId, "updated_at", DateTimeUtils.formatStrDateTime(LocalDateTime.now()));
                return true;
            }
        }
        return false;
    }
    
    /**
     * Get All Consultation ID
     * @return List of consultationId
     */    
    private List<Integer> getAllConsultationId() {
        List<Integer> list = new ArrayList<>();
        for (Consultation consultation : consultations) {
            list.add(consultation.getConsultationId());
        }
        return list;
    }  
    
    /**
     * Get All Consultation ID
     *@param consultationId
     * @return List of consultationId
     */    
    public Consultation getConsultationbyId(Integer consultationId) {
        for (Consultation consultation : consultations) {
            if (consultation.getConsultationId().equals(consultationId)) {
                return consultation;
            }
        }
        return null;
    }    
    
    /**
     * Create A New Consultation Slot And Save Into System Resources
     * @param lecturerId
     * @param consultationDateTime
     * @return Boolean
     */
    public Boolean createConsultationSlot(Integer lecturerId, LocalDateTime consultationDateTime) {
        for(Consultation consultation:consultations)
        {
            if(consultation.getLecturerId().equals(lecturerId))
            {
                Integer newConsultationId = shortIdGenerator.generateNewID(getAllConsultationId());
                Consultation newConsultation=new Consultation();
                newConsultation.setConsultationId(newConsultationId);
                newConsultation.setLecturerId(lecturerId);
                newConsultation.setStudentId(Integer.valueOf("0"));
                newConsultation.setConsultationDateTime(consultationDateTime);
                newConsultation.setConsultationStatus(ConsultationStatus.AVAILABLE);
                newConsultation.setCreatedAt(LocalDateTime.now());
                newConsultation.setUpdatedAt(LocalDateTime.now());

                consultations.add(newConsultation);
            
                JSONObject newConsultationJSON = new JSONObject();
                newConsultationJSON.put("id", newConsultation.getConsultationId());
                newConsultationJSON.put("lecturerId", newConsultation.getLecturerId());
                newConsultationJSON.put("studentId", newConsultation.getStudentId());
                newConsultationJSON.put("consultationDateTime", DateTimeUtils.formatStrDateTime(newConsultation.getConsultationDateTime()));
                newConsultationJSON.put("consultationStatus", newConsultation.getConsultationStatus().toString());
                newConsultationJSON.put("created_at", DateTimeUtils.formatStrDateTime(newConsultation.getCreatedAt()));
                newConsultationJSON.put("updated_at", DateTimeUtils.formatStrDateTime(newConsultation.getUpdatedAt()));

                JsonHandler consultationJSON = new JsonHandler();
                consultationJSON.encode(FileHandler.readFile(CONSULTATION_DATA));
                consultationJSON.addObject(newConsultationJSON, CONSULTATION_DATA);   
                return true;
            }
        }
        return false;
        }
    /**
     * Remove Consultation Data
     * @param consultationId
     */
    public void deleteConsultation(Integer consultationId)
    {
        for (Consultation consultation : consultations) {
            if (consultation.getConsultationId().equals(consultationId)) {
                JsonHandler jsonHandler = new JsonHandler();
                jsonHandler.encode(FileHandler.readFile(CONSULTATION_DATA));
                jsonHandler.delete(consultationId, CONSULTATION_DATA);
            }
        }
        consultations.removeIf(consultation -> consultation.getConsultationId().equals(consultationId));
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
            consultation.setConsultationId(obj.getInt("id"));
            consultation.setLecturerId(obj.getInt("lecturerId"));
            consultation.setStudentId(obj.getInt("studentId"));
            consultation.setConsultationDateTime(DateTimeUtils.formatDateTime(obj.get("consultationDateTime")));
            consultation.setConsultationStatus(ConsultationStatus.valueOf(obj.get("consultationStatus")));
            consultation.setCreatedAt(DateTimeUtils.formatDateTime(obj.get("created_at")));
            consultation.setUpdatedAt(DateTimeUtils.formatDateTime(obj.get("updated_at")));

            consultations.add(consultation);

            if (consultation.getConsultationDateTime().isBefore(LocalDateTime.now())) {
                update(consultation.getConsultationId(), "consultationStatus", ConsultationStatus.COMPLETED.toString());
                update(consultation.getConsultationId(), "updated_at", DateTimeUtils.formatStrDateTime(LocalDateTime.now()));
            }
        }

    }

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


