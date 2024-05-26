/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.service.Impl;

import com.project.common.constants.MessageConstant;
import com.project.common.constants.ReportStatus;
import com.project.common.utils.DateTimeUtils;
import com.project.common.utils.Dialog;
import lombok.extern.slf4j.Slf4j;
import com.project.common.utils.JsonHandler;
import com.project.dao.IntakeDAO;
import com.project.dao.ModuleDAO;
import com.project.dao.PresentationDAO;
import com.project.dao.SubmissionDAO;
import com.project.dao.UserAccountDAO;
import com.project.pojo.Intake;
import com.project.pojo.ProjectModule;
import com.project.pojo.Submission;
import com.project.pojo.UserAccount;
import com.project.service.ProjectModuleService;
import org.json.simple.JSONObject;

import java.time.LocalDate;
import static java.lang.Integer.parseInt;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
/**
 *
 * @author Sin Lian Feng
 */
public class ProjectModuleServiceImpl implements ProjectModuleService {
    
    private ModuleDAO moduleDAO = new ModuleDAO();
    private IntakeDAO intakeDAO = new IntakeDAO();
    private UserAccountDAO userAccountDAO = new UserAccountDAO();
    private SubmissionDAO submissionDAO = new SubmissionDAO();
    private PresentationDAO presentationDAO = new PresentationDAO();

    //TODO: Based on selection of module, need to find the intake name
    //TODO: Get all supervisee based on module ID

    //Get student ID, student name, intake code, module code, project type, and status to be displayed at the LecturerDashboard.java
    //If need to get other details from different txt files, insert your logic under this method    
    @Override    
    public List getAllModuleDetailsByFirstMarkerId(Integer lecturerId) {
        List<Map<String, String>> mappedLists = new ArrayList<>();
        //Get all module details by lecturer ID
        List<Map<String, String>> moduleList = moduleDAO.getModuleByFirstMarkerId(lecturerId);
        for (Map<String, String> list : moduleList){
            Map<String, String> mappedMap = new HashMap<>();
            mappedMap.put("id", list.get("id"));
            mappedMap.put("moduleCode", list.get("moduleCode"));
            mappedMap.put("moduleStartDate", list.get("startDate"));
            mappedMap.put("moduleEndDate", list.get("endDate"));

            //Get intake code by intakeId that is from moduleDAO object
            Intake intake = intakeDAO.getIntakeById(Integer.parseInt(list.get("intakeId")));
            mappedMap.put("intakeCode", intake.getIntakeCode());

            //Get report type, student ID, report status, and submission due date from Submission by module ID that is from moduleDAO object
            Submission submission = submissionDAO.getSubmissionByModuleId(Integer.parseInt(list.get("id")));
            if(submission.getModuleId().equals(Integer.parseInt(list.get("id"))))
            {
                mappedMap.put("reportType", submission.getReportType().toString());
                mappedMap.put("reportStatus", submission.getReportStatus().toString());
                mappedMap.put("reportResult", submission.getReportResult().toString());
                mappedMap.put("lecturerComment", submission.getComment());
                mappedMap.put("studentId", submission.getStudentId().toString());
                mappedMap.put("submissionDueDate", DateTimeUtils.formatStrDateTime(submission.getSubmissionDueDate()));
                //Get student name from UserAccount by student ID that is from Submission object
                UserAccount student=userAccountDAO.getUserAccountById(submission.getStudentId());
                String studentName=student.getFirstName()+" "+student.getLastName();
                mappedMap.put("studentName", studentName);
            }
            mappedLists.add(mappedMap);
        }
        return mappedLists;
    }
    
    @Override    
    public List getAllModuleDetailsBySecondMarkerId(Integer lecturerId) {
        List<Map<String, String>> mappedLists = new ArrayList<>();
        List<Map<String, String>> lists = moduleDAO.getModuleBySecondMarkerId(lecturerId);
        for (Map<String, String> list : lists){
            Map<String, String> mappedMap = new HashMap<>();
            mappedMap.put("id", list.get("id"));
            mappedMap.put("moduleCode", list.get("moduleCode"));
            mappedMap.put("moduleStartDate", list.get("startDate"));
            mappedMap.put("moduleEndDate", list.get("endDate"));
            //Get intake code by intakeId that is from moduleDAO object
            Intake intake = intakeDAO.getIntakeById(Integer.valueOf(list.get("intakeId")));
            mappedMap.put("intakeCode", intake.getIntakeCode());

            //Get report type, student ID, report status, and submission due date from Submission by module ID that is from moduleDAO object
            Submission submission = submissionDAO.getSubmissionByModuleId(Integer.valueOf(list.get("id")));
            if(submission.getModuleId().equals(Integer.parseInt(list.get("id"))))
            {
                mappedMap.put("reportType", submission.getReportType().toString());
                mappedMap.put("reportStatus", submission.getReportStatus().toString());
                mappedMap.put("reportResult", submission.getReportResult().toString());
                mappedMap.put("lecturerComment", submission.getComment());
                mappedMap.put("studentId", submission.getStudentId().toString());
                mappedMap.put("submissionDueDate", DateTimeUtils.formatStrDateTime(submission.getSubmissionDueDate()));
                //Get student name from UserAccount by student ID that is from Submission object
                UserAccount student=userAccountDAO.getUserAccountById(submission.getStudentId());
                String studentName=student.getFirstName()+" "+student.getLastName();
                mappedMap.put("studentName", studentName);
            }
            mappedLists.add(mappedMap);
        }
        return mappedLists;
    }

    @Override
    public boolean addModule(int intakeId, String moduleCode, int projectManagerId, LocalDate startDate, LocalDate endDate) {
        try {
            moduleDAO.addModule(intakeId, moduleCode, projectManagerId, startDate, endDate);
            return true;
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return false;
        }
    }

    //    For debug purpose, run the below main method to view the data

    //Jin Xun - Project Manager
    // To filter the module details by project manager ID
    @Override
    public List getAllModuleDetailsByProjectManagerId(Integer ProjectManagerId){
        List<Map<String, String>> mappedLists = new ArrayList<>();
        List<Map<String, String>> lists = moduleDAO.getModuleByProjectManagerId(ProjectManagerId);
        for (Map<String, String> list : lists){
            Map<String, String> mappedMap = new HashMap<>();
            mappedMap.put("id", list.get("id"));
            mappedMap.put("moduleCode", list.get("moduleCode"));
            mappedMap.put("startDate", list.get("startDate"));
            mappedMap.put("endDate", list.get("endDate"));
            mappedMap.put("firstMarker", list.get("firstMarker"));
            mappedMap.put("secondMarker", list.get("secondMarker"));
            mappedLists.add(mappedMap);
        }
        return mappedLists;
    }

    @Override
    public List getModuleById(Integer moduleId) {
        return (List) moduleDAO.getModuleByModuleId(moduleId);
    }

    /**
     * Get Modules Detail By Lecturer Id
     * @param lecturerId
     * @return List
     */
    @Override
    public List getModuleDetailsByFirstMarkerId(Integer lecturerId)
    {
        List<Map<String, String>> mappedLists = new ArrayList<>();
        //Get all module details by lecturer ID
        List<ProjectModule> moduleList = moduleDAO.getModuleListByFirstMarkerId(lecturerId);
        for(ProjectModule module:moduleList)
        {
            List<Submission> submissionList=submissionDAO.getSubmissionListByModuleId(module.getModuleId());
            for(Submission submission:submissionList)
            {
                if(submission.getModuleId().equals(module.getModuleId()) && module.getFirstMarker().equals(lecturerId))
                {
                    Map<String,String> map = new HashMap<>();
                    Integer studentId=submission.getStudentId();
                    UserAccount student=userAccountDAO.getUserAccountById(studentId);
                    String studentName=student.getFirstName()+" "+student.getLastName();
                    map.put("id", module.getModuleId().toString());
                    map.put("moduleCode", module.getModuleCode());
                    map.put("studentId", studentId.toString());
                    map.put("studentName", studentName);
                    Intake studentIntake=intakeDAO.getIntakeById(module.getIntakeId());
                    map.put("intakeCode", studentIntake.getIntakeCode());
                    map.put("reportType", submission.getReportType().toString());
                    map.put("submissionDueDate", DateTimeUtils.formatStrDateTime(submission.getSubmissionDueDate()));
                    map.put("submissionStatus", submission.getReportStatus().toString());
                    mappedLists.add(map);
                }
            }
        }
        return mappedLists;
    }

    @Override
    public List getModuleDetailsBySecondMarkerId(Integer lecturerId)
    {
        List<Map<String, String>> mappedLists = new ArrayList<>();
        //Get all module details by lecturer ID
        List<ProjectModule> moduleList = moduleDAO.getModuleListBySecondMarkerId(lecturerId);
        for(ProjectModule module:moduleList)
        {
            List<Submission> submissionList=submissionDAO.getSubmissionListByModuleId(module.getModuleId());
            for(Submission submission:submissionList)
            {
                if(submission.getModuleId().equals(module.getModuleId()) && module.getSecondMarker().equals(lecturerId))
                {
                    Map<String,String> map = new HashMap<>();
                    Integer studentId=submission.getStudentId();
                    UserAccount student=userAccountDAO.getUserAccountById(studentId);
                    String studentName=student.getFirstName()+" "+student.getLastName();
                    map.put("id", module.getModuleId().toString());
                    map.put("moduleCode", module.getModuleCode());
                    map.put("studentId", studentId.toString());
                    map.put("studentName", studentName);
                    Intake studentIntake=intakeDAO.getIntakeById(module.getIntakeId());
                    map.put("intakeCode", studentIntake.getIntakeCode());
                    map.put("reportType", submission.getReportType().toString());
                    map.put("submissionDueDate", DateTimeUtils.formatStrDateTime(submission.getSubmissionDueDate()));
                    map.put("submissionStatus", submission.getReportStatus().toString());
                    mappedLists.add(map);
                }
            }
        }
        return mappedLists;
    }


    @Override
    public Boolean saveModuleDetails(List moduleDetails) {
        if (moduleDAO.saveModuleChanges(moduleDetails)) {
//            log.info("Module Changes Has Been Saved! : " + MessageConstant.ERROR_PRESENTATION_SLOT_BOOKED);
            Dialog.SuccessDialog(MessageConstant.SUCCESS_ASSIGNED_SUPERVISOR);
            return true;
        } else {
//            log.warn("UNEXPECTED ERROR : " + MessageConstant.UNEXPECTED_ERROR);
            Dialog.ErrorDialog(MessageConstant.ERROR_EMPTY_MODULE);
            return false;
        }

    }


    @Override
    public List getModuleTypeById(Integer moduleId) {
        List<Map<String, String>> moduleDetails = moduleDAO.getModuleByModuleId(moduleId);
        String assessmentType = submissionDAO.getAssessmentTypeByModuleId(moduleId);

        // Adding the assessmentType to each module detail map
        if (moduleDetails != null && assessmentType != null) {
            for (Map<String, String> moduleDetail : moduleDetails) {
                moduleDetail.put("assessmentType", assessmentType);
            }
        }
        return moduleDetails;
    }


//    Get all report details for Table
    @Override
    public List<Map<String, String>> getAllReportDetails(Integer authenticatedUserId) {
      List<Map<String, String>> mappedList = new ArrayList<>();
      List<Map<String, String>> modules = moduleDAO.getModuleByProjectManagerId(authenticatedUserId);

      for (Map<String, String> module : modules) {
          Integer moduleId = Integer.parseInt(module.get("id"));
          List<Submission> reportLists = submissionDAO.getSubmissionListByModuleId(moduleId);

          for (Submission report : reportLists) {
              Integer studentId = report.getStudentId();

              String moduleCode = moduleDAO.getModuleNameById(moduleId);
              UserAccount studentAccount = userAccountDAO.getUserAccountById(studentId);
              String studentName = studentAccount.getFirstName() + " " + studentAccount.getLastName();

              Map<String, String> reportMap = new HashMap<>();
              reportMap.put("submissionId", report.getSubmissionId().toString());
              reportMap.put("moduleId", module.get("id"));
              reportMap.put("moduleCode", moduleCode);
              reportMap.put("studentId", studentId.toString());
              reportMap.put("studentName", studentName);
              reportMap.put("reportStatus", report.getReportStatus().toString());
              reportMap.put("reportType", report.getReportType().toString());
              reportMap.put("comment", report.getComment());

              mappedList.add(reportMap);
          }
      }
      return mappedList;
  }



    @Override
    public List<Map<String, String>> getReportDetailsById(Integer reportId) {
        Submission reportDetails = submissionDAO.getSubmissionById(reportId);
        System.out.println(reportDetails);

        if (reportDetails != null) {
            Integer studentId = reportDetails.getStudentId();
            Integer moduleId = reportDetails.getModuleId();

            String module = moduleDAO.getModuleNameById(moduleId);
            String studentName = userAccountDAO.getUserAccountById(studentId).getFirstName() + " " + userAccountDAO.getUserAccountById(studentId).getLastName();
            Map<String, String> mappedMap = new HashMap<>();
            mappedMap.put("id", reportDetails.getSubmissionId().toString());
            mappedMap.put("moduleId", moduleId.toString());
            mappedMap.put("moduleCode", module);
            mappedMap.put("studentId", studentId.toString());
            mappedMap.put("studentName", studentName);
            mappedMap.put("reportStatus", reportDetails.getReportStatus().toString());
            mappedMap.put("reportType", reportDetails.getReportType().toString());
            mappedMap.put("comment", reportDetails.getComment() != null ? reportDetails.getComment() : ""); // Handle possible null comment

            // Create a list and add the map to it
            List<Map<String, String>> reportList = new ArrayList<>();
            reportList.add(mappedMap);

            return reportList;
        }

        return null;
    }

    @Override
    public Boolean saveModuleDate(Integer moduleId, LocalDate startDate, LocalDate endDate){
        Boolean mdao = moduleDAO.saveModuleDateChanges(moduleId, startDate, endDate);
        Boolean sdao = submissionDAO.saveSubmissionDueDate(moduleId, endDate);
        Boolean pdao = presentationDAO.savePresentationDueDate(moduleId, endDate);
        System.out.println("Module: " + mdao + " ,Submission: " + sdao + " ,Presentation: " + pdao);
        if (mdao == true && sdao == true && pdao == true) {
//            log.info("Module Changes Has Been Saved! : " + MessageConstant.ERROR_PRESENTATION_SLOT_BOOKED);
            Dialog.SuccessDialog(MessageConstant.SUCCESS_UPDATE_DATE);
            return true;
        } else {
//            log.warn("UNEXPECTED ERROR : " + MessageConstant.UNEXPECTED_ERROR);
            Dialog.ErrorDialog(MessageConstant.ERROR_EMPTY_MODULE);
            return false;
        }
    }
    
    @Override
    public Map<String, Integer> getModuleStatusForPM(Integer pmId) {
        return moduleDAO.getModuleStatusForPM(pmId);
    }
    
    @Override
    public Map<String, Integer> getReportStatusForPM(Integer pmId) {
        List<Map<String, String>> modules = moduleDAO.getModuleByProjectManagerId(pmId);
        int total = 0;
        int pendingSubmit = 0;
        int pendingMarking = 0;
        int marked = 0;
        int overdue = 0;
        for (Map<String, String> module : modules) {
            Integer moduleId = Integer.valueOf(module.get("id"));
            List<Submission> reportLists = submissionDAO.getSubmissionListByModuleId(moduleId);
            for(Submission reportList : reportLists){
                total = total + 1;
                ReportStatus reportStatus = reportList.getReportStatus();
                switch (reportStatus) {
                    case PENDING_SUBMIT  -> {
                        pendingSubmit = pendingSubmit + 1;
                    }
                    case PENDING_MARKING  -> {
                        pendingMarking = pendingMarking + 1;
                    }
                    case MARKED_1  -> {
                        marked = marked + 1;
                    }
                    case MARKED_2  -> {
                        marked = marked + 1;
                    }
                    case OVERDUE  -> {
                        overdue = overdue + 1;
                    }
                }
            }
        }
        Map<String, Integer> map = new HashMap<>();
        map.put("total", total);
        map.put("pendingSubmit", pendingSubmit);
        map.put("pendingMarking", pendingMarking);
        map.put("marked", marked);
        map.put("overdue", overdue);
        return map;
    }
//    For debug purpose, run the below main method to view the data
    public static void main(String[] args) {
        ProjectModuleServiceImpl prje = new ProjectModuleServiceImpl();
        // System.out.println(prje.getAllModuleDetailsByLecId(88608036));

        System.out.println(prje.getAllReportDetails(39904006));
    }



}


