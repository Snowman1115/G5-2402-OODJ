package com.project.dao;

import com.project.common.constants.PresentationStatus;
import com.project.common.constants.ReportStatus;
import com.project.common.constants.ReportType;
import com.project.common.utils.DateTimeUtils;
import com.project.common.utils.FileHandler;
import com.project.common.utils.JsonHandler;
import com.project.common.utils.PropertiesReader;
import com.project.pojo.Intake;
import com.project.pojo.Presentation;
import com.project.pojo.Submission;

import java.time.LocalDateTime;
import java.util.*;

public class SubmissionDAO {

    private static final String SUBMISSION_DATA = PropertiesReader.getProperty("SubmissionData");
    private static List<Submission> submissions = new ArrayList<>();

    static {
        loadSubmissionData();
    }

    /**
     * Get All Submission Status By Student Id
     * @param studentId
     * @return Map of List
     */
    public Map<String, Integer> getAllSubmissionStatusByStudentId(Integer studentId) {
        int pendingSubmit = 0;
        int pendingMarking = 0;
        int marked = 0;
        int overdue = 0;
        for (Submission submission : submissions) {
            if (submission.getStudentId().equals(studentId)) {
                if (submission.getReportStatus().equals(ReportStatus.PENDING_SUBMIT)) {
                    pendingSubmit = pendingSubmit + 1;
                } else if (submission.getReportStatus().equals(ReportStatus.PENDING_MARKING)) {
                    pendingMarking = pendingMarking + 1;
                } else if (submission.getReportStatus().equals(ReportStatus.MARKED_2)) {
                    marked = marked + 1;
                } else if (submission.getReportStatus().equals(PresentationStatus.OVERDUE)) {
                    overdue = overdue + 1;
                }
            }
        }
        Map<String, Integer> map = new HashMap<>();
        map.put("pendingSubmit" , pendingSubmit);
        map.put("pendingMarking" , pendingMarking);
        map.put("marked" , marked);
        map.put("overdue" , overdue);
        return map;
    }

    /**
     * Get All Submission Details By Student ID
     * @param studentId
     * @return Map Of List
     */
    public List<Map<String, String>> getAllSubmissionDetailsByStudentId(Integer studentId) {
        List<Map<String, String>> list = new ArrayList<>();
        for (Submission submission : submissions) {
            if (submission.getStudentId().equals(studentId)) {
                Map<String, String> map = new HashMap<>();

                map.put("id", submission.getSubmissionId().toString());
                map.put("reportId", submission.getReportId().toString());
                map.put("moduleId", submission.getModuleId().toString());
                map.put("dueDate", DateTimeUtils.formatStrDateTime(submission.getSubmissionDueDate()));
                map.put("type", submission.getReportType().toString());
                map.put("Status", submission.getReportStatus().toString());
                map.put("result", submission.getReportResult().toString());
                map.put("submitAt", DateTimeUtils.formatStrDateTime(submission.getSubmittedAt()));
                map.put("markedAt", DateTimeUtils.formatStrDateTime(submission.getMarkedAt()));

                list.add(map);
            }
        }
        return list;
    }

    /**
     * Preload Data into presentations Array
     */
    private static void loadSubmissionData() {
        JsonHandler userData = new JsonHandler();
        userData.encode(FileHandler.readFile(SUBMISSION_DATA));

        for (int i = 0; i < (userData.getAll().size()); i++) {
            JsonHandler obj = new JsonHandler();
            obj.cloneObject(userData.getObject(i));

            Submission submission = new Submission();

            submission.setSubmissionId(obj.getInt("id"));
            submission.setReportId(obj.getInt("reportId"));
            submission.setModuleId(obj.getInt("moduleId"));
            submission.setStudentId(obj.getInt("studentId"));
            submission.setSubmissionDueDate(DateTimeUtils.formatDateTime(obj.get("submissionDueDate")));
            submission.setReportStatus(ReportStatus.valueOf(obj.get("reportStatus")));
            submission.setReportType(ReportType.valueOf(obj.get("reportType")));
            submission.setReportResult(Double.parseDouble(obj.get("reportResult")));
            submission.setSubmittedAt(DateTimeUtils.formatDateTime(obj.get("submitted_at")));
            submission.setMarkedAt(DateTimeUtils.formatDateTime(obj.get("marked_at")));
            submission.setCreatedAt(DateTimeUtils.formatDateTime(obj.get("created_at")));
            submission.setUpdatedAt(DateTimeUtils.formatDateTime(obj.get("updated_at")));

            submissions.add(submission);
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
