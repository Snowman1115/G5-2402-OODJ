package com.project.dao;

import com.project.common.constants.PresentationStatus;
import com.project.common.utils.DateTimeUtils;
import com.project.common.utils.FileHandler;
import com.project.common.utils.JsonHandler;
import com.project.common.utils.PropertiesReader;
import com.project.pojo.Intake;
import com.project.pojo.Presentation;
import com.project.pojo.ProjectModule;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ModuleDAO {

    private static final String MODULE_DATA = PropertiesReader.getProperty("ModuleData");
    private static List<ProjectModule> modules = new ArrayList<>();

    static {
        loadConsultationData();
    }

    /**
     * Get Module by Id
     * @param moduleId
     * @return Module
     */
    public ProjectModule getModuleById(Integer moduleId) {
        for (ProjectModule module : modules) {
            if (module.getModuleId().equals(moduleId)) {
                return module;
            }
        }
        return null;
    }

    /**
     * Preload Data into presentations Array
     */
    private static void loadConsultationData() {
        JsonHandler userData = new JsonHandler();
        userData.encode(FileHandler.readFile(MODULE_DATA));

        for (int i = 0; i < (userData.getAll().size()); i++) {
            JsonHandler obj = new JsonHandler();
            obj.cloneObject(userData.getObject(i));

            ProjectModule projectModule = new ProjectModule();
            projectModule.setModuleId(obj.getInt("id"));
            projectModule.setModuleCode(obj.get("moduleCode"));
            projectModule.setSupervisorId(obj.getInt("supervisorId"));
            projectModule.setFirstMarker(obj.getInt("firstMarkerId"));
            projectModule.setSecondMarker(obj.getInt("secondMarkerId"));
            projectModule.setStartDate(DateTimeUtils.formatDate(obj.get("startDate")));
            projectModule.setEndDate(DateTimeUtils.formatDate(obj.get("endDate")));
            projectModule.setCreatedAt(DateTimeUtils.formatDateTime(obj.get("created_at")));
            projectModule.setUpdatedAt(DateTimeUtils.formatDateTime(obj.get("updated_at")));

            modules.add(projectModule);
        }
    }

    /*

    // Update consultation data
    public static boolean update(Integer consultationId, String field, String value) {
        // System.out.println(value);
        for (Consultation consultation : consultations) {
            if (consultation.getConsultationId().equals(consultationId)) {
                try {
                    switch (field) {
                        case "lecturerId" -> {
                            consultation.setLecturerId(Integer.parseInt(value));
                            return store(consultationId, "lecturerId", value);
                        }
                        case "studentId" -> {
                            consultation.setStudentId(Integer.parseInt(value));
                            return store(consultationId, "studentId", value);
                        }
                        case "consultationDateTime" -> {
                            consultation.setConsultationDateTime(DateTimeUtils.formatDateTime(value));
                            return store(consultationId, "consultationDateTime", value);
                        }
                        case "consultationStatus" -> {
                            consultation.setConsultationStatus(ConsultationStatus.valueOf(value));
                            return store(consultationId, "consultationStatus", value);
                        }
                        case "updated_at" -> {
                            consultation.setUpdatedAt(DateTimeUtils.formatDateTime(value));
                            return store(consultationId, "updated_at", value);
                        }
                        default -> {
                            log.info("Error: " + MessageConstant.ERROR_OBJECT_FIELD_NOT_FOUND);
                            return false;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }

        log.info("Error: " + MessageConstant.ERROR_OBJECT_NOT_FOUND + value);
        return false;

    }

    *//**
     * Store updated data into text file
     * @param consultationId
     * @param attribute
     * @param value
     * @return
     *//*
    private static boolean store(Integer consultationId, String attribute, String value) {
        // System.out.println(consultationId + attribute + value);
        JsonHandler userJson = new JsonHandler();
        userJson.encode(FileHandler.readFile(CONSULTATION_DATA));
        return userJson.update(consultationId, attribute, value, CONSULTATION_DATA);
    }
*/

}