/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.service;

import com.project.common.utils.JsonHandler;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Sin Lian Feng
 */
public interface ProjectModuleService {
    
    /**
     * Get All Modules Details By Lecturer Id
     * @param lecturerId
     * @return List
     */
    public List getAllModuleDetailsByFirstMarkerId(Integer lecturerId);

    public List getAllModuleDetailsBySecondMarkerId(Integer lecturerId);
    
    public List getModuleDetailsByFirstMarkerId(Integer lecturerId);
    
    public List getModuleDetailsBySecondMarkerId(Integer lecturerId);
    

    /**
     * add new module
     * @param intakeId
     * @param moduleCode
     * @param projectManagerId
     * @param startDate
     * @param endDate
     * @return boolean
     */
    public boolean addModule(int intakeId, String moduleCode, int projectManagerId, LocalDate startDate, LocalDate endDate);

    //Jin Xun - Project Manager

    /**
     *
     * @param ProjectManagerId
     * @return
     */
    public List getAllModuleDetailsByProjectManagerId(Integer ProjectManagerId);

    public List getModuleById(Integer moduleId);

    public Boolean saveModuleDetails(List moduleDetails);

    public List getModuleTypeById(Integer moduleId);
    
    public List getAllReportDetails(Integer authenticatedUserId);

    public List getReportDetailsById(Integer reportId);

    public Boolean saveModuleDate(Integer moduleId, LocalDate startDate, LocalDate endDate);

    public Map<String, Integer> getModuleStatusForPM(Integer authenticatedUserId);

    public Map<String, Integer> getReportStatusForPM(Integer authenticatedUserId);

    boolean projectManagerReassignment(int userId, int newPMId);
}
