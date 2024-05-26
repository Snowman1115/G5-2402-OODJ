package com.project.service.Impl;

import com.project.common.constants.MessageConstant;
import com.project.common.constants.ReportStatus;
import com.project.common.constants.ReportType;
import com.project.common.utils.DateTimeUtils;
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

            if(module.getFirstMarker().equals(0)) {
                mappedList.put("lecturerName", "Lecturer Not Assigned.");
            } else {
                UserAccount userAccount = userAccountDAO.getUserAccountById(module.getFirstMarker());
                mappedList.put("lecturerName", userAccount.getFirstName() + " " + userAccount.getLastName());
            }

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
    
    /**
     * Get All Pending Marking Submission (First and Second Marker) By Lecturer ID
     * @param lecturerId
     * @return Map of Number
     */
    @Override
    public Map<String, Integer> getPendingMarkingSubmissionForLecturer(Integer lecturerId) {
        return submissionDAO.getPendingMarkingSubmissionForLecturer(lecturerId);
    }
    
    /**
     * Get All Upcoming Submission Due Date (First and Second Marker) By Lecturer ID
     * @param lecturerId
     * @return List
     */
    @Override
    public List<Map<String, String>> getAllUpcomingSubmissionDueDateByLecId(Integer lecturerId) {
        List<Map<String, String>> mappedList = new ArrayList<>();
        List<ProjectModule> moduleList1=moduleDAO.getModuleListByFirstMarkerId(lecturerId);
        List<ProjectModule> moduleList2=moduleDAO.getModuleListBySecondMarkerId(lecturerId);
        for(ProjectModule module:moduleList1)
        {
            List<Submission> submission1 = submissionDAO.getSubmissionListByModuleId(module.getModuleId());
            for (Submission submission:submission1)
            {
                if(submission.getModuleId().equals(module.getModuleId()) && module.getFirstMarker().equals(lecturerId))
                {
                    if(submission.getSubmissionDueDate().isAfter(LocalDateTime.now()))
                    {
                        Map<String,String> map = new HashMap<>();
                        map.put("submissionDueDate", DateTimeUtils.formatStrDateTime(submission.getSubmissionDueDate()));
                        map.put("moduleName", module.getModuleCode());
                        mappedList.add(map); 
                        //Break the loop to avoid duplication of same data adding to the list
                        break;
                    }
                }
            }
        }  
        for(ProjectModule module:moduleList2)
        {
            List<Submission> submission2 = submissionDAO.getSubmissionListByModuleId(module.getModuleId());
            for (Submission submission:submission2)
            {
                if(submission.getModuleId().equals(module.getModuleId()) && module.getSecondMarker().equals(lecturerId))
                {
                    if(submission.getSubmissionDueDate().isAfter(LocalDateTime.now()))
                    {
                        Map<String,String> map = new HashMap<>();
                        map.put("submissionDueDate", DateTimeUtils.formatStrDateTime(submission.getSubmissionDueDate()));
                        map.put("moduleName", module.getModuleCode());
                        mappedList.add(map); 
                        //Break the loop to avoid duplication of same data adding to the list
                        break;
                    }
                }
            }
        }
        return mappedList;
    }
    
    /**
     * Get All Submission Details By Lecturer Id
     * @param lecturerId
     * @return List
     */
    @Override
    public List getAllSubmissionDetailsByLecId(Integer lecturerId)
    {
        List<Map<String, String>> mappedLists = new ArrayList<>();
        List<ProjectModule> moduleList1=moduleDAO.getModuleListByFirstMarkerId(lecturerId);
        List<ProjectModule> moduleList2=moduleDAO.getModuleListBySecondMarkerId(lecturerId);
        //Get all module details by lecturer ID
        for(ProjectModule module:moduleList1)
        {
            List<Submission> submission1 = submissionDAO.getSubmissionListByModuleId(module.getModuleId());
            for (Submission submission:submission1)
            {
                if(submission.getModuleId().equals(module.getModuleId()) && module.getFirstMarker().equals(lecturerId))
                {
                    Map<String,String> map = new HashMap<>();
                    Integer studentId=submission.getStudentId();
                    if(studentId != 0)
                    {
                        UserAccount student=userAccountDAO.getUserAccountById(studentId);
                        String studentName=student.getFirstName()+" "+student.getLastName();
                        map.put("studentId", studentId.toString());
                        map.put("studentName", studentName);
                    }
                    else 
                    {
                        map.put("studentId", "EMPTY");
                        map.put("studentName", "EMPTY");
                    }
                    map.put("moduleName", module.getModuleCode());
                    map.put("reportType", submission.getReportType().toString());
                    map.put("submissionDueDate", DateTimeUtils.formatStrDateTime(submission.getSubmissionDueDate()));
                    map.put("submissionStatus", submission.getReportStatus().toString());
                    map.put("reportResult", submission.getReportResult().toString());
                    mappedLists.add(map); 
                }
            }
        }
        for(ProjectModule module:moduleList2)
        {
            List<Submission> submission2 = submissionDAO.getSubmissionListByModuleId(module.getModuleId());
            for (Submission submission:submission2)
            {
                if(submission.getModuleId().equals(module.getModuleId()) && module.getSecondMarker().equals(lecturerId))
                {
                    Map<String,String> map = new HashMap<>();
                    Integer studentId=submission.getStudentId();
                    if(studentId != 0)
                    {
                        UserAccount student=userAccountDAO.getUserAccountById(studentId);
                        String studentName=student.getFirstName()+" "+student.getLastName();
                        map.put("studentId", studentId.toString());
                        map.put("studentName", studentName);
                    }
                    else 
                    {
                        map.put("studentId", "EMPTY");
                        map.put("studentName", "EMPTY");
                    }
                    map.put("moduleName", module.getModuleCode());
                    map.put("reportType", submission.getReportType().toString());
                    map.put("submissionDueDate", DateTimeUtils.formatStrDateTime(submission.getSubmissionDueDate()));
                    map.put("submissionStatus", submission.getReportStatus().toString());
                    map.put("reportResult", submission.getReportResult().toString());
                    mappedLists.add(map); 
                }
            }
        }
        return mappedLists;
    }
    
    /**
     * Get All Pending Marking Submission By Module Id
     * @param moduleId
     * @return List
     */    
    @Override
    public List getPendingMarkingSubmissionByModuleId(Integer moduleId)
    {
        List<Map<String, String>> mappedLists = new ArrayList<>();
        List<Submission> submissionList = submissionDAO.getSubmissionListByModuleId(moduleId);
        for(Submission submission:submissionList)
        {
            if(submission.getModuleId().equals(moduleId) && (submission.getReportStatus().equals(ReportStatus.PENDING_MARKING) || submission.getReportStatus().equals(ReportStatus.OVERDUE)))
            {
                Map<String,String> map = new HashMap<>();
                map.put("id", submission.getSubmissionId().toString());
                
                UserAccount student=userAccountDAO.getUserAccountById(submission.getStudentId());
                String studentName=student.getFirstName()+" "+student.getLastName();
                map.put("studentName", studentName);
                
                map.put("reportType", submission.getReportType().toString());
                map.put("markingStatus", submission.getReportStatus().toString());
                map.put("reportMarks", submission.getReportResult().toString());
                map.put("lecturerComment", submission.getComment());
                mappedLists.add(map);
            }
        }
        return mappedLists;
    }
    
    /**
     * Get All Marked_1 Submission By Module Id
     * @param moduleId
     * @return List
     */    
    @Override
    public List getMarked1SubmissionByModuleId(Integer moduleId)
    {
        List<Map<String, String>> mappedLists = new ArrayList<>();
        List<Submission> submissionList = submissionDAO.getSubmissionListByModuleId(moduleId);
        for(Submission submission:submissionList)
        {
            if(submission.getModuleId().equals(moduleId) && submission.getReportStatus().equals(ReportStatus.MARKED_1))
            {
                Map<String,String> map = new HashMap<>();
                map.put("id", submission.getSubmissionId().toString());
                
                UserAccount student=userAccountDAO.getUserAccountById(submission.getStudentId());
                String studentName=student.getFirstName()+" "+student.getLastName();
                map.put("studentName", studentName);
                
                map.put("reportType", submission.getReportType().toString());
                map.put("markingStatus", submission.getReportStatus().toString());
                map.put("reportMarks", submission.getReportResult().toString());
                map.put("lecturerComment", submission.getComment());
                mappedLists.add(map);
            }
        }
        return mappedLists;
    }
    
    /**
     * Get All Marked_2 Submission By Module Id
     * @param moduleId
     * @return List
     */    
    @Override
    public List getMarked2SubmissionByModuleId(Integer moduleId)
    {
        List<Map<String, String>> mappedLists = new ArrayList<>();
        List<Submission> submissionList = submissionDAO.getSubmissionListByModuleId(moduleId);
        for(Submission submission:submissionList)
        {
            if(submission.getModuleId().equals(moduleId) && submission.getReportStatus().equals(ReportStatus.MARKED_2))
            {
                Map<String,String> map = new HashMap<>();
                map.put("id", submission.getSubmissionId().toString());
                
                UserAccount student=userAccountDAO.getUserAccountById(submission.getStudentId());
                String studentName=student.getFirstName()+" "+student.getLastName();
                map.put("studentName", studentName);
                
                map.put("reportType", submission.getReportType().toString());
                map.put("markingStatus", submission.getReportStatus().toString());
                map.put("reportMarks", submission.getReportResult().toString());
                map.put("lecturerComment", submission.getComment());
                mappedLists.add(map);
            }
        }
        return mappedLists;
    }
    
    /**
     * Get Submission Details By Submission Id
     * @param submissionId
     * @return List
     */   
    @Override
    public List<Map<String, String>> getSubmissionDetailsById(Integer submissionId)
    {
        List<Map<String, String>> mappedLists = new ArrayList<>();
        Submission submission = submissionDAO.getSubmissionById(submissionId);
        if(submission.getSubmissionId().equals(submissionId) && (submission.getReportStatus().equals(ReportStatus.PENDING_MARKING) || submission.getReportStatus().equals(ReportStatus.OVERDUE)))
            {
                Map<String,String> map = new HashMap<>();
                map.put("id", submission.getSubmissionId().toString());

                UserAccount student=userAccountDAO.getUserAccountById(submission.getStudentId());
                String studentName=student.getFirstName()+" "+student.getLastName();
                map.put("studentName", studentName);

                map.put("submissionDueDate", DateTimeUtils.formatStrDateTime(submission.getSubmissionDueDate()));
                map.put("reportType", submission.getReportType().toString());
                Integer reportId=submission.getReportId();
                if (reportId.equals(0)) {
                    map.put("FilePath", "");
                    map.put("FileName", "");
                } else {
                    Report report = reportDAO.getAllReportByIdnType(submission.getReportId(), submission.getReportType());
                    map.put("filePath", report.getReportPath());
                    map.put("fileName", report.getReportName());
                }
                map.put("markingStatus", submission.getReportStatus().toString());
                map.put("reportMarks", submission.getReportResult().toString());
                map.put("lecturerComment", submission.getComment());
                mappedLists.add(map);
            }
        return mappedLists;
    }
    
    /**
     * Get Marked_1 Submission Details By Submission Id
     * @param submissionId
     * @return List
     */   
    @Override
    public List<Map<String, String>> getMarked1SubmissionDetailsById(Integer submissionId)
    {
        List<Map<String, String>> mappedLists = new ArrayList<>();
        Submission submission = submissionDAO.getSubmissionById(submissionId);
        if(submission.getSubmissionId().equals(submissionId) && submission.getReportStatus().equals(ReportStatus.MARKED_1))
            {
                Map<String,String> map = new HashMap<>();
                map.put("id", submission.getSubmissionId().toString());

                UserAccount student=userAccountDAO.getUserAccountById(submission.getStudentId());
                String studentName=student.getFirstName()+" "+student.getLastName();
                map.put("studentName", studentName);

                map.put("submissionDueDate", DateTimeUtils.formatStrDateTime(submission.getSubmissionDueDate()));
                map.put("reportType", submission.getReportType().toString());
                Integer reportId=submission.getReportId();
                if (reportId.equals(0)) {
                    map.put("FilePath", "");
                    map.put("FileName", "");
                } else {
                    Report report = reportDAO.getAllReportByIdnType(submission.getReportId(), submission.getReportType());
                    map.put("filePath", report.getReportPath());
                    map.put("fileName", report.getReportName());
                }
                map.put("markingStatus", submission.getReportStatus().toString());
                map.put("reportMarks", submission.getReportResult().toString());
                map.put("lecturerComment", submission.getComment());
                mappedLists.add(map);
            }
        return mappedLists;
    }
    
    /**
     * Get Marked_2 Submission Details By Submission Id
     * @param submissionId
     * @return List
     */   
    @Override
    public List<Map<String, String>> getMarked2SubmissionDetailsById(Integer submissionId)
    {
        List<Map<String, String>> mappedLists = new ArrayList<>();
        Submission submission = submissionDAO.getSubmissionById(submissionId);
        if(submission.getSubmissionId().equals(submissionId) && submission.getReportStatus().equals(ReportStatus.MARKED_2))
            {
                Map<String,String> map = new HashMap<>();
                map.put("id", submission.getSubmissionId().toString());

                UserAccount student=userAccountDAO.getUserAccountById(submission.getStudentId());
                String studentName=student.getFirstName()+" "+student.getLastName();
                map.put("studentName", studentName);

                map.put("submissionDueDate", DateTimeUtils.formatStrDateTime(submission.getSubmissionDueDate()));
                map.put("reportType", submission.getReportType().toString());
                Integer reportId=submission.getReportId();
                if (reportId.equals(0)) {
                    map.put("FilePath", "");
                    map.put("FileName", "");
                } else {
                    Report report = reportDAO.getAllReportByIdnType(submission.getReportId(), submission.getReportType());
                    map.put("filePath", report.getReportPath());
                    map.put("fileName", report.getReportName());
                }
                map.put("markingStatus", submission.getReportStatus().toString());
                map.put("reportMarks", submission.getReportResult().toString());
                map.put("lecturerComment", submission.getComment());
                mappedLists.add(map);
            }
        return mappedLists;
    }
    
    /**
     * Update Submission Status To Marked_1 By Submission Id
     * @param submissionId
     * @param marks
     * @param comment
     * @return Boolean
     */
    @Override
    public Boolean updateSubmissionMarksByIdForFirstMarker(Integer submissionId, Double marks, String comment) {
        if (submissionDAO.updateSubmissionMarksByIdForFirstMarker(submissionId, marks, comment)) {
            log.info("Submission Status Update To Marked_1 Successfully: " + submissionId);
            return true;
        } else{
            log.info("Submission Status Update To Marked_1 Failed: " + submissionId);
            return false;
        }        
    }
    
    /**
     * Update Submission Status To Marked_1 By Submission Id
     * @param submissionId
     * @param marks
     * @param comment
     * @return Boolean
     */
    @Override
    public Boolean updateSubmissionMarksByIdForSecondMarker(Integer submissionId, Double marks, String comment) {
        if (submissionDAO.updateSubmissionMarksByIdForSecondMarker(submissionId, marks, comment)) {
            log.info("Submission Status Update To Marked_2 Successfully: " + submissionId);
            return true;
        } else{
            log.info("Submission Status Update To Marked_2 Failed: " + submissionId);
            return false;
        }        
    }

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
}
