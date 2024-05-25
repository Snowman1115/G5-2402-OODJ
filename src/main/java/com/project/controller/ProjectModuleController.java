/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.controller;

import com.project.common.utils.JsonHandler;
import com.project.service.Impl.ProjectModuleServiceImpl;
import com.project.service.Impl.UserAuthenticationServiceImpl;
import com.project.service.ProjectModuleService;
import com.project.service.UserAuthenticationService;

import java.time.LocalDate;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Sin Lian Feng
 */
@Slf4j
public class ProjectModuleController {
    
    private static UserAuthenticationService userAuthenticationService = new UserAuthenticationServiceImpl();
    
    private static ProjectModuleService projectModuleService = new ProjectModuleServiceImpl();
    
    /**
     * Get All Module Details By Lecturer Id
     * @return List
     */
    
    // Method to get all module details with first marker ID
    public static List getAllModuleDetailsByFirstMarkerId() {
        return projectModuleService.getAllModuleDetailsByFirstMarkerId(getAuthenticatedUserId());
    }
    
    // Method to get all module details with second marker ID
    public static List getAllModuleDetailsBySecondMarkerId() {
        return projectModuleService.getAllModuleDetailsBySecondMarkerId(getAuthenticatedUserId());
    }

    // Method to get module details with first marker ID
    public static List getModuleDetailsByFirstMarkerId() {
        return projectModuleService.getModuleDetailsByFirstMarkerId(getAuthenticatedUserId());
    }

    // Method to get module details with second marker ID
    public static List getModuleDetailsBySecondMarkerId() {
        return projectModuleService.getModuleDetailsBySecondMarkerId(getAuthenticatedUserId());
    }
    //Jin Xun - Project Manager
    // Method to get all module details by project manager ID
    public static List getAllModuleDetailsByProjectManagerId() {
        return projectModuleService.getAllModuleDetailsByProjectManagerId(getAuthenticatedUserId());
    }

    public static List getModuleById(Integer moduleId) {
        return projectModuleService.getModuleById(moduleId);
    }

     /**
     * Save Supervisor and Second Marker In Module
     * @param moduleDetails
     * @return Boolean
     */
    public static Boolean saveModuleDetails(List moduleDetails){
        //log info
        return projectModuleService.saveModuleDetails(moduleDetails);
    }
    
    public static Boolean saveModuleDate(Integer moduleId, LocalDate startDate, LocalDate endDate){
        return projectModuleService.saveModuleDate(moduleId, startDate, endDate);
    }
    /**
     * Get Authenticated UserId
     * @return userId
     */
    private static Integer getAuthenticatedUserId() {
        return userAuthenticationService.getAuthenticationUserDetails().getUserId();
    }

    /**
     * add new module
     * @param intakeId
     * @param moduleCode
     * @param projectManagerId
     * @param startDate
     * @param endDate
     * @return boolean
     */
    public static boolean addNewModule (int intakeId, String moduleCode, int projectManagerId, LocalDate startDate, LocalDate endDate) { return projectModuleService.addModule(intakeId, moduleCode, projectManagerId, startDate, endDate); }




    public static List getModuleTypeById(Integer moduleId) {
        return projectModuleService.getModuleTypeById(moduleId);
    }

    public static List getAllReport(){
        return projectModuleService.getAllReportDetails();
    }

    public static List getReportById(Integer reportId){
        return projectModuleService.getReportDetailsById(reportId);
    }
}
