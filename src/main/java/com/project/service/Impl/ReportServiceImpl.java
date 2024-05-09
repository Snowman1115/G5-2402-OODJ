package com.project.service.Impl;

import com.project.common.constants.MessageConstant;
import com.project.common.utils.Dialog;
import com.project.dao.IntakeDAO;
import com.project.dao.ModuleDAO;
import com.project.dao.ReportDAO;
import com.project.dao.SubmissionDAO;
import com.project.pojo.Intake;
import com.project.pojo.ProjectModule;
import com.project.pojo.Submission;
import com.project.service.ReportService;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
public class ReportServiceImpl implements ReportService {

    private SubmissionDAO submissionDAO = new SubmissionDAO();
    private ModuleDAO moduleDAO = new ModuleDAO();
    private IntakeDAO intakeDAO = new IntakeDAO();
    private ReportDAO reportDAO = new ReportDAO();

    @Override
    public Boolean submitReport(Integer submissionId, String filePath) {
        Submission submission = submissionDAO.getSubmissionById(submissionId);
        ProjectModule projectModule = moduleDAO.getModuleById(submission.getModuleId());
        Intake intake = intakeDAO.getIntakeById(projectModule.getIntakeId());
        String strFilePath = "src/main/resources/Data/Submission/Report/" + intake.getIntakeCode() + "/" + projectModule.getModuleCode() + "/";
        Integer reportId = reportDAO.saveFile(filePath, strFilePath, submission.getReportType());
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
}
