package com.project.dao;

import com.project.common.constants.MessageConstant;
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
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
public class SubmissionDAO {

    private static final String SUBMISSION_DATA = PropertiesReader.getProperty("SubmissionData");
    private static List<Submission> submissions = new ArrayList<>();

    static {
        loadSubmissionData();
    }

//    public static List<Map<String, String>> getAllReport() {
////        List<Map<String, String>> list = new ArrayList<>();
////        for (Submission submission : submissions) {
////            Map map = new HashMap<>();
////            map.put("moduleId", submission.getModuleId().toString());
////            map.put("studentId", submission.getStudentId().toString());
////            map.put("moduleCode", submission.getModuleCode());
////            map.put("supervisorId", submission.getSupervisorId().toString());
////            map.put("firstMarker", submission.getFirstMarker().toString());
////            map.put("secondMarker", submission.getSecondMarker().toString());
////            map.put("startDate", DateTimeUtils.formatStrDate(submission.getStartDate()));
////            map.put("endDate", DateTimeUtils.formatStrDate(submission.getEndDate()));
////            map.put("created_at", DateTimeUtils.formatStrDateTime(module.getCreatedAt()));
////            map.put("updated_at", DateTimeUtils.formatStrDateTime(module.getUpdatedAt()));
////            list.add(map);
////        }
//        return null;
//    }

    /**
     * Get Submission Details by ID
     *
     * @param submissionId
     * @return Submission
     */
    public Submission getSubmissionById(Integer submissionId) {
        for (Submission submission : submissions) {
            if (submission.getSubmissionId().equals(submissionId)) {
                return submission;
            }
        }
        return null;
    }
    
    /**
     * Get All Submission Details By Module Id
     *
     * @param moduleId
     * @return Submission
     */
    public Submission getSubmissionByModuleId(Integer moduleId) {
        for (Submission submission : submissions) {
            if (submission.getModuleId().equals(moduleId)) {
                return submission;
            }
        }
        return null;
    }
    
    public String getAssessmentTypeByModuleId(Integer moduleId) {
        for (Submission submission : submissions) {
            if (submission.getModuleId().equals(moduleId)) {
                String assessmentType = submission.getReportType().toString();
                return assessmentType;
            }
        }
        return null;
    }
//    public static void main(String[] args) {
//        SubmissionDAO sub = new SubmissionDAO();
//        System.out.println(sub.getSubmissionByModuleId(36887009));  
//    }

    /**
     * Get All Submission Status By Student Id
     *
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
                } else if (submission.getReportStatus().equals(ReportStatus.OVERDUE)) {
                    overdue = overdue + 1;
                }
            }
        }
        Map<String, Integer> map = new HashMap<>();
        map.put("pendingSubmit", pendingSubmit);
        map.put("pendingMarking", pendingMarking);
        map.put("marked", marked);
        map.put("overdue", overdue);
        return map;
    }

    /**
     * update Submission Status (Submitted)
     *
     * @param submissionId
     * @param reportId
     */
    public void saveReportId(Integer submissionId, Integer reportId) {
        for (Submission submission : submissions) {
            if (submission.getSubmissionId().equals(submissionId)) {
                update(submissionId, "reportId", String.valueOf(reportId));
                update(submissionId, "reportStatus", ReportStatus.PENDING_MARKING.toString());
                update(submissionId, "submitted_at", DateTimeUtils.formatStrDateTime(LocalDateTime.now()));
                update(submissionId, "updated_at", DateTimeUtils.formatStrDateTime(LocalDateTime.now()));
            }
        }
    }

    /**
     * Remove Submission
     * @param submissionId
     */
    public void removeSubmission(Integer submissionId) {
        for (Submission submission : submissions) {
            if (submission.getSubmissionId().equals(submissionId)) {
                update(submissionId, "reportId", "0");
                update(submissionId, "reportStatus", ReportStatus.PENDING_SUBMIT.toString());
                update(submissionId, "updated_at", DateTimeUtils.formatStrDateTime(LocalDateTime.now()));
            }
        }
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
                map.put("comment", submission.getComment());
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
            submission.setComment(obj.get("comment"));
            submission.setSubmittedAt(DateTimeUtils.formatDateTime(obj.get("submitted_at")));
            submission.setMarkedAt(DateTimeUtils.formatDateTime(obj.get("marked_at")));
            submission.setCreatedAt(DateTimeUtils.formatDateTime(obj.get("created_at")));
            submission.setUpdatedAt(DateTimeUtils.formatDateTime(obj.get("updated_at")));

            submissions.add(submission);
        }
    }
    /**
     * Save Assessment Type into Submission
     * @param moduleId
     * @param savedAssessment
     * @return Boolean
     */
    public boolean saveAssessmentTypeChanges(Integer moduleId, String savedAssessment) {
        Boolean status = false;
        for (Submission submission : submissions) {
            if (submission.getModuleId().equals(moduleId)) {
                update(submission.getSubmissionId(), "reportType", savedAssessment);
                update(submission.getSubmissionId(), "updated_at", DateTimeUtils.formatStrDateTime(LocalDateTime.now()));
                status = true;
            }
        }
        return status;
    }


    // Update consultation data
    public static boolean update(Integer submissionId, String field, String value) {
        // System.out.println(value);
        for (Submission submission : submissions) {
            if (submission.getSubmissionId().equals(submissionId)) {
                try {
                    switch (field) {
                        case "reportId" -> {
                            submission.setReportId(Integer.parseInt(value));
                            return store(submissionId, "reportId", value);
                        }
                        case "moduleId" -> {
                            submission.setModuleId(Integer.parseInt(value));
                            return store(submissionId, "moduleId", value);
                        }
                        case "studentId" -> {
                            submission.setStudentId(Integer.parseInt(value));
                            return store(submissionId, "studentId", value);
                        }
                        case "submissionDueDate" -> {
                            submission.setSubmissionDueDate(DateTimeUtils.formatDateTime(value));
                            return store(submissionId, "submissionDueDate", value);
                        }
                        case "reportStatus" -> {
                            submission.setReportStatus(ReportStatus.valueOf(value));
                            return store(submissionId, "reportStatus", value);
                        }
                        case "reportType" -> {
                            submission.setReportType(ReportType.valueOf(value));
                            return store(submissionId, "reportType", value);
                        }
                        case "reportResult" -> {
                            submission.setReportResult(Double.valueOf(value));
                            return store(submissionId, "reportResult", value);
                        }
                        case "comment" -> {
                            submission.setComment(value);
                            return store(submissionId, "comment", value);
                        }
                        case "submitted_at" -> {
                            submission.setSubmittedAt(DateTimeUtils.formatDateTime(value));
                            return store(submissionId, "submitted_at", value);
                        }
                        case "marked_at" -> {
                            submission.setMarkedAt(DateTimeUtils.formatDateTime(value));
                            return store(submissionId, "marked_at", value);
                        }
                        case "updated_at" -> {
                            submission.setUpdatedAt(DateTimeUtils.formatDateTime(value));
                            return store(submissionId, "updated_at", value);
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

    private static boolean store(Integer consultationId, String attribute, String value) {
        JsonHandler userJson = new JsonHandler();
        userJson.encode(FileHandler.readFile(SUBMISSION_DATA));
        return userJson.update(consultationId, attribute, value, SUBMISSION_DATA);
    }





}
