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
    public static List getAllModuleDetailsByLecId() {
        return projectModuleService.getAllModuleDetailsByLecId(getAuthenticatedUserId());
    }
    
    // Method to get all module details with second marker ID
    public static List getAllModuleDetailsBySecondMarkerId() {
        return projectModuleService.getAllModuleDetailsBySecondMarkerId(getAuthenticatedUserId());
    }
    
    /**
     * Get Authenticated UserId
     * @return userId
     */
    private static Integer getAuthenticatedUserId() {
        return userAuthenticationService.getAuthenticationUserDetails().getUserId();
    }

    /**
     * get all modules
     * @return modules
     */
    public static List<String> getModulesCode() {
        return projectModuleService.getAllModuleCodes();
    }
}
