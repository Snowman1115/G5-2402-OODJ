/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.service.Impl;

import com.project.common.constants.MessageConstant;
import com.project.common.utils.DateTimeUtils;
import com.project.common.utils.Dialog;
import com.project.dao.IntakeDAO;
import com.project.dao.ModuleDAO;
import com.project.dao.SubmissionDAO;
import com.project.dao.UserAccountDAO;
import com.project.pojo.Intake;
import com.project.pojo.Submission;
import com.project.pojo.UserAccount;
import com.project.service.ProjectModuleService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Sin Lian Feng
 */
public class ProjectModuleServiceImpl implements ProjectModuleService {
    
    private ModuleDAO moduleDAO = new ModuleDAO();
    private IntakeDAO intakeDAO = new IntakeDAO();
    private UserAccountDAO userAccountDAO = new UserAccountDAO();
    private SubmissionDAO submissionDAO = new SubmissionDAO();

    //Get student ID, student name, intake code, module code, project type, and status to be displayed at the LecturerDashboard.java
    //If need to get other details from different txt files, insert your logic under this method    
    @Override    
    public List getAllModuleDetailsByLecId(Integer lecturerId) {        
        List<Map<String, String>> mappedLists = new ArrayList<>();
        //Get all module details by lecturer ID
        List<Map<String, String>> moduleList = moduleDAO.getModuleByLecturerId(lecturerId);
        for (Map<String, String> list : moduleList){
            Map<String, String> mappedMap = new HashMap<>();
            mappedMap.put("id", list.get("id"));
            mappedMap.put("moduleCode", list.get("moduleCode"));
            
            //Get intake code by intakeId that is from moduleDAO object
            Intake intake = intakeDAO.getIntakeById(Integer.parseInt(list.get("intakeId")));
            mappedMap.put("intakeCode", intake.getIntakeCode());

            //Get report type, student ID, report status, and submission due date from Submission by module ID that is from moduleDAO object
            Submission submission = submissionDAO.getSubmissionByModuleId(Integer.parseInt(list.get("id")));
            if(submission.getModuleId().equals(Integer.parseInt(list.get("id"))))
            {
                mappedMap.put("reportType", submission.getReportType().toString());
                mappedMap.put("reportStatus", submission.getReportStatus().toString());
                mappedMap.put("studentId", submission.getStudentId().toString());
                mappedMap.put("submissionDueDate", DateTimeUtils.formatStrDateTime(submission.getSubmissionDueDate()));
                //Get student name from UserAccount by student ID that is from Submission object
                UserAccount student=userAccountDAO.getUserAccountById(submission.getStudentId());
                String studentName=student.getFirstName()+" "+student.getLastName();
                mappedMap.put("studentName", studentName);
            }
//             if (submission.getModuleid.equals(moduleId))
//             {
//                 submission.getStatus();
//                 submission.getStudentiD
//                         UserAccount user = userAccountDAO.getUserAccountById(student);
//                String name = user.getFirstName() + " " + user.getLastName();
//                mappedMap.put("name", name);
//                mappedMap.put("studentId", student.toString());   
//             }
//                
//                
//                List<Map<String, String>> studentSubmissions = submissionDAO.getAllSubmissionDetailsByStudentId(student);
//                for (Map<String, String> studentSubmission : studentSubmissions) {
//                    if (studentSubmission.get("moduleId").equals(list.get("id"))) {
//                        mappedMap.put("Status", studentSubmission.get("Status"));
//                        mappedMap.put("submitAt", studentSubmission.get("submitAt"));
//                    }
//            }
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

            //Get intake code by intakeId that is from moduleDAO object
            Intake intake = intakeDAO.getIntakeById(Integer.valueOf(list.get("intakeId")));
            mappedMap.put("intakeCode", intake.getIntakeCode());

            //Get report type, student ID, report status, and submission due date from Submission by module ID that is from moduleDAO object
            Submission submission = submissionDAO.getSubmissionByModuleId(Integer.valueOf(list.get("id")));
            if(submission.getModuleId().equals(Integer.parseInt(list.get("id"))))
            {
                mappedMap.put("reportType", submission.getReportType().toString());
                mappedMap.put("reportStatus", submission.getReportStatus().toString());
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
    
    public Boolean saveModuleDetails(List moduleDetails) {
        if (moduleDAO.saveModuleChanges(moduleDetails)) {
//            log.info("Module Changes Has Been Saved! : " + MessageConstant.ERROR_PRESENTATION_SLOT_BOOKED);
            Dialog.SuccessDialog(MessageConstant.SUCCESS_ASSIGNED_SUPERVISOR);
            return true;
        } else {
//            log.warn("UNEXPECTED ERROR : " + MessageConstant.UNEXPECTED_ERROR);
            Dialog.SuccessDialog(MessageConstant.UNEXPECTED_ERROR);
            return false;
        }

    }

//    For debug purpose, run the below main method to view the data
    public static void main(String[] args) {
        ProjectModuleServiceImpl prje = new ProjectModuleServiceImpl();
        // System.out.println(prje.getAllModuleDetailsByLecId(88608036));

        System.out.println(prje.getModuleById(36887009));
    }


    
}


