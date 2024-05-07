package com.project.dao;

import com.project.common.constants.ReportStatus;
import com.project.common.constants.ReportType;
import com.project.common.utils.DateTimeUtils;
import com.project.common.utils.FileHandler;
import com.project.common.utils.JsonHandler;
import com.project.common.utils.PropertiesReader;
import com.project.pojo.Intake;
import com.project.pojo.Submission;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubmissionDAO {

    private static final String SUBMISSION_DATA = PropertiesReader.getProperty("SubmissionData");
    private static List<Submission> submissions = new ArrayList<>();

    static {
        loadSubmissionData();
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
            submission.setSubmittedAt(DateTimeUtils.formatDateTime(obj.get("submitted_at")));
            submission.setMarkedAt(DateTimeUtils.formatDateTime(obj.get("marked_at")));
            submission.setCreatedAt(DateTimeUtils.formatDateTime(obj.get("created_at")));
            submission.setUpdatedAt(DateTimeUtils.formatDateTime(obj.get("updated_at")));

            submissions.add(submission);
        }
    }

    public static void main(String[] args) {
        System.out.println(submissions);
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
