package com.project.dao;

import com.project.common.constants.ConsultationStatus;
import com.project.common.constants.MessageConstant;
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

    /**
     * Get Pending Booking Presentation Module and Due Date
     * @param studentId
     * @return Map of List
     */
    public List<Map<String, String>> getAllUpcomingPendingBookingPresentation(Integer studentId) {
        List<Map<String, String>> list = new ArrayList<>();
        for (Presentation presentation : presentations) {
            if (presentation.getStudentId().equals(studentId) && presentation.getPresentationStatus().equals(PresentationStatus.PENDING_BOOKING)) {
                Map<String, String> map = new HashMap<>();
                map.put("moduleId", presentation.getModuleId().toString());
                map.put("dueDate", DateTimeUtils.formatStrDateTime(presentation.getPresentationDueDate()));
                list.add(map);
            }
        }
        return list;
    }

    /**
     * Get All Presentation Details By Student ID
     * @param studentId
     * @return Map Of List
     */
    public List<Map<String, String>> getAllPresentationDetailsByStudentId(Integer studentId) {
        List<Map<String, String>> list = new ArrayList<>();
        for (Presentation presentation : presentations) {
            if (presentation.getStudentId().equals(studentId)) {
                Map<String, String> map = new HashMap<>();
                map.put("id", presentation.getPresentationId().toString());
                map.put("moduleId", presentation.getModuleId().toString());
                map.put("lecturerId", presentation.getLecturerId().toString());
                map.put("dueDate", DateTimeUtils.formatStrDateTime(presentation.getPresentationDueDate()));
                map.put("presentationDate", DateTimeUtils.formatStrDateTime(presentation.getPresentationDateTime()));
                map.put("Status", presentation.getPresentationStatus().toString());
                map.put("result", presentation.getPresentationResult().toString());
                list.add(map);
            }
        }
        return list;
    }

    /**
     * Check Slot Availability
     * @param presentationId
     * @param dateTime
     * @return Boolean
     */
    public Boolean checkAvailableSlot(Integer presentationId, LocalDateTime dateTime) {
        Integer ModuleId = null;
        for (Presentation presentation : presentations) {
            if (presentation.getPresentationId().equals(presentationId)) {
                ModuleId = presentation.getModuleId();
            }
        }

        for (Presentation presentation : presentations) {
            if (!presentation.getPresentationId().equals(presentationId)) {
                if (presentation.getModuleId().equals(ModuleId)) {
                    if (presentation.getPresentationDateTime().equals(dateTime)) {
                        return false;
                    };
                }
            }
        }

        return true;
    }

    /**
     * Cancel Presentation Slot By Presentation Id (Student)
     *
     */
    public Boolean cancelPresentationSlot(Integer presentationId) {
        for (Presentation presentation : presentations) {
            if (presentation.getPresentationId().equals(presentationId)) {
                update(presentationId, "presentationStatus", PresentationStatus.PENDING_BOOKING.toString());
                update(presentationId, "updated_at", DateTimeUtils.formatStrDateTime(LocalDateTime.now()));
                return true;
            }
        }
        return false;
    }

    /**
     * Book Presentation Slot By Presentation Id (Student)
     * @param presentationId
     * @param dateTime
     */
    public Boolean bookPresentationSlot(Integer presentationId, LocalDateTime dateTime) {
        for (Presentation presentation : presentations) {
            if (presentation.getPresentationId().equals(presentationId)) {
                update(presentationId, "presentationDateTime", DateTimeUtils.formatStrDateTime(dateTime));
                update(presentationId, "presentationStatus", PresentationStatus.PENDING_CONFIRM.toString());
                update(presentationId, "updated_at", DateTimeUtils.formatStrDateTime(LocalDateTime.now()));
                return true;
            }
        }
        return false;
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

            PresentationStatus presentationStatus = PresentationStatus.valueOf(obj.get("presentationStatus"));

            if (presentation.getPresentationDueDate().isBefore(LocalDateTime.now()) && presentationStatus.equals(PresentationStatus.PENDING_BOOKING)) {
                presentation.setPresentationStatus(PresentationStatus.OVERDUE);
            } else {
                presentation.setPresentationStatus(presentationStatus);
            }


            presentation.setPresentationResult(obj.getDouble("presentationResult"));
            presentation.setCreatedAt(DateTimeUtils.formatDateTime(obj.get("created_at")));
            presentation.setUpdatedAt(DateTimeUtils.formatDateTime(obj.get("updated_at")));

            presentations.add(presentation);
        }
    }

    // Update consultation data
    public static boolean update(Integer presentationId, String field, String value) {
        // System.out.println(value);
        for (Presentation presentation : presentations) {
            if (presentation.getPresentationId().equals(presentationId)) {
                try {
                    switch (field) {
                       case "moduleId" -> {
                            presentation.setModuleId(Integer.parseInt(value));
                            return store(presentationId, "moduleId", value);
                        }
                        case "lecturerId" -> {
                            presentation.setLecturerId(Integer.parseInt(value));
                            return store(presentationId, "lecturerId", value);
                        }
                        case "studentId" -> {
                            presentation.setStudentId(Integer.parseInt(value));
                            return store(presentationId, "studentId", value);
                        }
                        case "presentationDueDate" -> {
                            presentation.setPresentationDueDate(DateTimeUtils.formatDateTime(value));
                            return store(presentationId, "presentationDueDate", value);
                        }
                        case "presentationDateTime" -> {
                            presentation.setPresentationDateTime(DateTimeUtils.formatDateTime(value));
                            return store(presentationId, "presentationDateTime", value);
                        }
                        case "presentationStatus" -> {
                            presentation.setPresentationStatus(PresentationStatus.valueOf(value));
                            return store(presentationId, "presentationStatus", value);
                        }
                        case "presentationResult" -> {
                            presentation.setPresentationResult(Double.valueOf(value));
                            return store(presentationId, "presentationResult", value);
                        }
                        case "updated_at" -> {
                            presentation.setUpdatedAt(DateTimeUtils.formatDateTime(value));
                            return store(presentationId, "updated_at", value);
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
        userJson.encode(FileHandler.readFile(PRESENTATION_DATA));
        return userJson.update(consultationId, attribute, value, PRESENTATION_DATA);
    }


}
