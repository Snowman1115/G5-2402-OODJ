/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.service;

import com.project.common.utils.JsonHandler;

import java.util.List;

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
    public List getAllModuleDetailsByLecId(Integer lecturerId);

    public List getAllModuleDetailsBySecondMarkerId(Integer lecturerId);

    /**
     * get all modules information
     * @return modules
     */
    public JsonHandler getAllModules();

    public List<String> getAllModuleCodes();
}
