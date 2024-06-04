package com.project.dao;

import com.project.common.constants.MessageConstant;
import com.project.common.constants.PresentationStatus;
import com.project.common.constants.ReportStatus;
import com.project.common.utils.*;
import com.project.pojo.Presentation;
import com.project.pojo.Submission;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;

import java.time.LocalDate;
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
     * Get Total Number of Upcoming and Finished Consultation For Student
     * @param lecturerId
     * @return Map of Number
     */
    public Map<String, Integer> getPendingConfirmAndMarkingPresentationForLecturer(Integer lecturerId) {
        Map<String, Integer> map = new HashMap<>();
        Integer pendingConfirmSum = 0;
        Integer pendingMarkingSum = 0;
        for (Presentation presentation : presentations) {
            if (presentation.getLecturerId().equals(lecturerId)) {
                if (presentation.getPresentationStatus().equals(PresentationStatus.PENDING_CONFIRM)) {
                    pendingConfirmSum = pendingConfirmSum + 1;
                } else if (presentation.getPresentationStatus().equals(PresentationStatus.BOOKED)) {
                    pendingMarkingSum = pendingMarkingSum + 1;
                }
            }
        }
        map.put("pendingConfirm", pendingConfirmSum);
        map.put("pendingMarking", pendingMarkingSum);
        return map;
    }
    
    /**
     * Get All Scheduled Presentation for Lecturer
     * @param lecturerId
     * @return List
     */    
    public List getPresentationByLecturerId(Integer lecturerId) {
        List<Map<String, String>> list = new ArrayList<>();
        for (Presentation presentation : presentations) {
            if (presentation.getLecturerId().equals(lecturerId)) {
                Map map = new HashMap<>();
                map.put("id", presentation.getPresentationId().toString());
                map.put("moduleId", presentation.getModuleId().toString());
                map.put("lecturerId", presentation.getLecturerId().toString());
                map.put("studentId", presentation.getStudentId().toString());
                map.put("presentationDueDate", DateTimeUtils.formatStrDateTime(presentation.getPresentationDueDate()));
                map.put("presentationDateTime", DateTimeUtils.formatStrDateTime(presentation.getPresentationDateTime()));
                map.put("presentationStatus", presentation.getPresentationStatus().toString());
                map.put("presentationResult", presentation.getPresentationResult().toString());
                map.put("created_at", DateTimeUtils.formatStrDateTime(presentation.getCreatedAt()));
                map.put("updated_at", DateTimeUtils.formatStrDateTime(presentation.getUpdatedAt()));
                list.add(map);
            }
        }
        return list;
    }

    /**
     * Get All Booked Presentation for Lecturer
     * @param lecturerId
     * @return List
     */  
    public List<Presentation> getAllBookedPresentationForLec(Integer lecturerId) {
        List<Presentation> list = new ArrayList<>();
        for (Presentation presentation : presentations) {
            if (presentation.getLecturerId().equals(lecturerId) && presentation.getPresentationStatus().equals(PresentationStatus.BOOKED)) {
                list.add(presentation);
            }
        }
        return list;
    }
    
    /**
     * Get All Pending Confirm Presentation for Lecturer
     * @param lecturerId
     * @return List
     */  
    public List<Presentation> getAllPendingConfirmPresentationForLec(Integer lecturerId) {
        List<Presentation> list = new ArrayList<>();
        for (Presentation presentation : presentations) {
            if (presentation.getLecturerId().equals(lecturerId) && presentation.getPresentationStatus().equals(PresentationStatus.PENDING_CONFIRM)) {
                list.add(presentation);
            }
        }
        return list;
    }
    
    /**
     * Get All Not Yet Graded Presentation for Lecturer
     * @param lecturerId
     * @return List
     */  
    public List<Presentation> getNotYetGradedPresentationForLec(Integer lecturerId) {
        List<Presentation> list = new ArrayList<>();
        for (Presentation presentation : presentations) {
            //Get all presentation including OVERDUE and BOOKED
            if (presentation.getLecturerId().equals(lecturerId) && (presentation.getPresentationStatus().equals(PresentationStatus.BOOKED) || presentation.getPresentationStatus().equals(PresentationStatus.OVERDUE))) {
                list.add(presentation);
            }
        }
        return list;
    }
    
    /**
     * Update Booked Consultation To Complete By Consultation Id
     * @param presentationId
     * @return Boolean
     */
    public Boolean acceptPresentationById(Integer presentationId) {
        for (Presentation presentation : presentations) {
            if (presentation.getPresentationId().equals(presentationId)) {
                update(presentationId, "presentationStatus", PresentationStatus.BOOKED.toString());
                update(presentationId, "updated_at", DateTimeUtils.formatStrDateTime(LocalDateTime.now()));
                return true;
            }
        }
        return false;
    } 
    /**
     * Update Booked Consultation To Complete By Consultation Id
     * @param presentationId
     * @return Boolean
     */
    public Boolean rejectPresentationById(Integer presentationId) {
        for (Presentation presentation : presentations) {
            if (presentation.getPresentationId().equals(presentationId)) {
                update(presentationId, "presentationStatus", PresentationStatus.REJECTED.toString());
                update(presentationId, "updated_at", DateTimeUtils.formatStrDateTime(LocalDateTime.now()));
                return true;
            }
        }
        return false;
    } 
    
    /**
     * Update Booked Consultation To Complete By Consultation Id
     * @param presentationId
     * @return Boolean
     */
    public Boolean updatePresentationMarksById(Integer presentationId, Double marks) {
        for (Presentation presentation : presentations) {
            if (presentation.getPresentationId().equals(presentationId)) {
                update(presentationId, "presentationStatus", PresentationStatus.MARKED.toString());
                update(presentationId, "presentationResult", marks.toString());
                update(presentationId, "updated_at", DateTimeUtils.formatStrDateTime(LocalDateTime.now()));
                return true;
            }
        }
        return false;
    }

    /**
     * Delete presentation data
     * @param presentationId
     * @return boolean
     */
    public boolean delete(Integer presentationId) {
        for (Presentation p : presentations) {
            if (p.getPresentationId().equals(presentationId)) {
                presentations.remove(p);
                JsonHandler userJson = new JsonHandler();
                userJson.encode(FileHandler.readFile(PRESENTATION_DATA));
                return userJson.delete(p.getPresentationId(), PRESENTATION_DATA);
            }
        }

        log.error("Error: " + MessageConstant.ERROR_USER_NOT_FOUND);
        return false;
    } 
    
    /**
     * Preload Data into presentations Array
     */
    private static void loadConsultationData() {
        JsonHandler presentationData = new JsonHandler();
        presentationData.encode(FileHandler.readFile(PRESENTATION_DATA));

        for (int i = 0; i < (presentationData.getAll().size()); i++) {
            JsonHandler obj = new JsonHandler();
            obj.setObject(presentationData.getObject(i));

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

    public Boolean savePresentationDueDate(Integer moduleId, LocalDate endDate){
        Boolean status = false;
        String strEndDate = endDate.toString();
        for (Presentation presentation : presentations) {
            if (presentation.getModuleId().equals(moduleId)) {
                update(presentation.getPresentationId(), "presentationDueDate", strEndDate);
                update(presentation.getPresentationId(), "updated_at", DateTimeUtils.formatStrDateTime(LocalDateTime.now()));
                status = true;
            }
        }
        return status;
    }

    public void add(int moduleId, int lecturerId, int studentId, LocalDate moduleEndDate) {
        List<Integer> currentIds = new ArrayList<>();
        IDGenerator idGenerator = new LongIDGenerator();

        for (Presentation p : presentations) {
            currentIds.add(p.getPresentationId());
        }

        int newPresentationId = idGenerator.generateNewID(currentIds);
        Presentation newPresentation = new Presentation();
        newPresentation.setPresentationId(newPresentationId);
        newPresentation.setModuleId(moduleId);
        newPresentation.setLecturerId(lecturerId);
        newPresentation.setStudentId(studentId);
        newPresentation.setPresentationDueDate(moduleEndDate.atStartOfDay());
        newPresentation.setPresentationDateTime(moduleEndDate.atStartOfDay());
        newPresentation.setPresentationStatus(PresentationStatus.PENDING_BOOKING);
        newPresentation.setPresentationResult(0.0);
        newPresentation.setCreatedAt(LocalDateTime.now());
        newPresentation.setUpdatedAt(LocalDateTime.now());
        presentations.add(newPresentation);

        JSONObject presentationObj = new JSONObject();
        presentationObj.put("id", newPresentationId);
        presentationObj.put("moduleId", moduleId);
        presentationObj.put("lecturerId", lecturerId);
        presentationObj.put("studentId", studentId);
        presentationObj.put("presentationDueDate", DateTimeUtils.formatStrDateTime(moduleEndDate.atStartOfDay()));
        presentationObj.put("presentationDateTime", DateTimeUtils.formatStrDateTime(LocalDateTime.now()));
        presentationObj.put("presentationStatus", PresentationStatus.PENDING_BOOKING.toString());
        presentationObj.put("presentationResult", 0);
        presentationObj.put("created_at", DateTimeUtils.formatStrDateTime(LocalDateTime.now()));
        presentationObj.put("updated_at", DateTimeUtils.formatStrDateTime(LocalDateTime.now()));

        JsonHandler presentationJson = new JsonHandler();
        presentationJson.encode(FileHandler.readFile(PRESENTATION_DATA));
        presentationJson.addObject(presentationObj, PRESENTATION_DATA);
    }

    // Update consultation data
    public boolean update(Integer presentationId, String field, String value) {
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
