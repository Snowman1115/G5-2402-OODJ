/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.service.Impl;

import com.project.dao.IntakeDAO;
import com.project.dao.ModuleDAO;
import com.project.dao.SubmissionDAO;
import com.project.dao.UserAccountDAO;
import com.project.pojo.Intake;
import com.project.pojo.Submission;
import com.project.pojo.UserAccount;
import com.project.service.ProjectModuleService;
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
  
    
    //TODO: Based on selection of module, need to find the intake name
    //TODO: Get all supervisee based on module ID     
    
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
            
            //Get intake code
            Intake intake = intakeDAO.getIntakeById(Integer.parseInt(list.get("intakeId")));
            mappedMap.put("intakeCode", intake.getIntakeCode());
                    
//             SubmisisonDAO submissionDAO = new submissionDAO();
//             Submission sumbission = submissiondao.getSubmissionByModuleID(moduleID);

            //Get project type, and status by module ID
            Submission submission = submissionDAO.getSubmissionByModuleId(Integer.parseInt(list.get("id")));
            
            
            if(submission.getModuleId().equals(Integer.parseInt(list.get("id"))))
            {
                mappedMap.put("reportType", submission.getReportType().toString());
                mappedMap.put("reportStatus", submission.getReportStatus().toString());
                mappedMap.put("studentId", submission.getStudentId().toString());
                //Get student name by student ID
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
            mappedLists.add(mappedMap);
        }
        return mappedLists;
    }
    
//    For debug purpose, run the below main method to view the data
    public static void main(String[] args) {
        ProjectModuleServiceImpl prje = new ProjectModuleServiceImpl();
        System.out.println(prje.getAllModuleDetailsByLecId(88608036));
    }
    
}


