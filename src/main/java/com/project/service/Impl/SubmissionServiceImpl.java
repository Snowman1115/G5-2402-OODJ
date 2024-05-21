package com.project.service.Impl;

import com.project.common.constants.MessageConstant;
import com.project.common.constants.ReportType;
import com.project.common.utils.Dialog;
import com.project.dao.*;
import com.project.pojo.*;
import com.project.service.SubmissionService;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class SubmissionServiceImpl implements SubmissionService {

    private SubmissionDAO submissionDAO = new SubmissionDAO();
    private UserAccountDAO userAccountDAO = new UserAccountDAO();
    private ModuleDAO moduleDAO = new ModuleDAO();
    private ReportDAO reportDAO = new ReportDAO();
    private IntakeDAO intakeDAO = new IntakeDAO();

    /**
     * Get All Submission Status By Student Id
     * @param studentId
     * @return Map of List
     */
    @Override
    public Map<String, Integer> getAllSubmissionStatusByStudentId(Integer studentId) {
        return submissionDAO.getAllSubmissionStatusByStudentId(studentId);
    }

    /**
     * Get All Submission Details for Student
     * @param studentId
     * @return List of Map
     */
    @Override
    public List getAllSubmissionDetailsForStudent(Integer studentId) {
        List<Map<String, String>> list = new ArrayList<>();

        for (Map<String, String> map : submissionDAO.getAllSubmissionDetailsByStudentId(studentId)) {
            Map<String, String> mappedList = new HashMap<>();
            mappedList.put("id", map.get("id"));

            ProjectModule module = moduleDAO.getModuleById(Integer.parseInt(map.get("moduleId")));
            mappedList.put("moduleName", module.getModuleCode());

            UserAccount userAccount = userAccountDAO.getUserAccountById(module.getFirstMarker());
            mappedList.put("lecturerName", userAccount.getFirstName() + " " + userAccount.getLastName());

            mappedList.put("reportId", map.get("reportId"));

            if (map.get("reportId").equals("0")) {
                mappedList.put("FilePath", "");
                mappedList.put("FileName", "");
            } else {
                Report report = reportDAO.getAllReportByIdnType(Integer.parseInt(map.get("reportId")), ReportType.valueOf(map.get("type")));
                mappedList.put("FilePath", report.getReportPath());
                mappedList.put("FileName", report.getReportName());
            }

            if (map.get("Status").equals("PENDING_SUBMIT")) {
                mappedList.put("submitAt", "EMPTY");
            } else if (map.get("Status").equals("OVERDUE")) {
                mappedList.put("submitAt", "EMPTY");
            } else {
                mappedList.put("submitAt", map.get("submitAt"));
            }

            mappedList.put("dueDate", map.get("dueDate"));
            mappedList.put("type", map.get("type"));
            mappedList.put("comment", map.get("comment"));
            mappedList.put("markedAt", map.get("markedAt"));
            mappedList.put("Status", map.get("Status"));
            mappedList.put("result", map.get("result"));

            list.add(mappedList);

        }

        return list;

    }

    /**
     * Submit report base on submission Id
     * @param submissionId
     * @param reportPath
     * @return Boolean
     */
    @Override
    public Boolean submitReport(Integer submissionId, String reportPath) {
        Submission submission = submissionDAO.getSubmissionById(submissionId);
        ProjectModule projectModule = moduleDAO.getModuleById(submission.getModuleId());
        Intake intake = intakeDAO.getIntakeById(projectModule.getIntakeId());

        String strFilePath = "src//main//resources//Data//Submission//Report//" + intake.getIntakeCode() + "//" + projectModule.getModuleCode() + "//";

        Integer reportId = reportDAO.saveFile(reportPath, strFilePath, submission.getReportType());
        if (reportId.equals(null)) {
            Dialog.ErrorDialog(MessageConstant.UNEXPECTED_ERROR);
            return false;
        }

        submissionDAO.saveReportId(submissionId, reportId);
        Dialog.SuccessDialog(MessageConstant.SUCCESS_SUBMITTED_PROJECT);
        return true;
    }

    /**
     * Remove Report By Submission ID
     * @param submissionId
     * @return Boolean
     */
    @Override
    public Boolean removeSubmissionById(Integer submissionId) {
        Submission submission = submissionDAO.getSubmissionById(submissionId);
        if (LocalDateTime.now().isAfter(submission.getSubmissionDueDate())) {
            Dialog.ErrorDialog(MessageConstant.ERROR_SUBMISSION_DUE_DATE_IS_OVER);
            return false;
        }

        if(Dialog.ConfirmationDialog(MessageConstant.WARNING_REMOVE_CONFIRMATION)) {
            log.info("Submission Removed Successfully : " + submissionId);
            reportDAO.removeReport(submission.getReportId(), submission.getReportType());
            submissionDAO.removeSubmission(submissionId);
            return true;
        } else {
            log.info("Submission Removed Cancelled : " + submissionId);
            return false;
        }
    }
    
    
//    
//    public List getAssessmentByModuleId(Integer moduleId){
//        
//    }

    @Override
    public List getAssessmentByModuleId(Integer moduleId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public Boolean saveAssessmentType(Integer moduleId, String savedAssessment) {
        System.out.println(moduleId);
        System.out.println(savedAssessment);
        if (submissionDAO.saveAssessmentTypeChanges(moduleId, savedAssessment)) {
            Dialog.SuccessDialog(MessageConstant.SUCCESS_ASSIGNED_SUPERVISOR);
            return true;
        } else {
    //      log.warn("UNEXPECTED ERROR : " + MessageConstant.UNEXPECTED_ERROR);
            Dialog.SuccessDialog(MessageConstant.ERROR_EMPTY_MODULE);
            return false;
        }
    }
    
    public static void main(String[] args) {
        SubmissionServiceImpl test = new SubmissionServiceImpl();
        // System.out.println(prje.getAllModuleDetailsByLecId(88608036));

        System.out.println(test.saveAssessmentType(36887009,"CAPSTONE_1"));
    }
}
