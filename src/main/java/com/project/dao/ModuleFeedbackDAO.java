package com.project.dao;

import com.project.common.constants.MessageConstant;
import com.project.common.utils.DateTimeUtils;
import com.project.common.utils.FileHandler;
import com.project.common.utils.JsonHandler;
import com.project.common.utils.PropertiesReader;
import com.project.pojo.ModuleFeedback;
import com.project.pojo.ProjectModule;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ModuleFeedbackDAO {

    private static final String FEEDBACK_DATA = PropertiesReader.getProperty("ModuleFeedbackData");
    private static List<ModuleFeedback> feedbacks = new ArrayList<>();

    static {
        loadConsultationData();
    }

    /**
     * Preload Data into presentations Array
     */
    private static void loadConsultationData() {
        JsonHandler userData = new JsonHandler();
        userData.encode(FileHandler.readFile(FEEDBACK_DATA));

        for (int i = 0; i < (userData.getAll().size()); i++) {
            JsonHandler obj = new JsonHandler();
            obj.setObject(userData.getObject(i));

            ModuleFeedback moduleFeedback = new ModuleFeedback();
            moduleFeedback.setFeedbackId(obj.getInt("id"));
            moduleFeedback.setModuleId(obj.getInt("ModuleId"));
            moduleFeedback.setStudentId(obj.getInt("StudentId"));
            moduleFeedback.setComments(obj.get("comments"));

            feedbacks.add(moduleFeedback);
        }
    }

/*
    // Update consultation data
    public static boolean update(Integer moduleId, String field, String value) {
        // System.out.println(value);
        for (ProjectModule module : modules) {
            if (module.getModuleId().equals(moduleId)) {
                try {
                    switch (field) {
                        case "intakeId" -> {
                            module.setModuleId(Integer.parseInt(value));
                            return store(moduleId, "intakeId", value);
                        }
                        case "moduleCode" -> {
                            module.setModuleCode(String.valueOf(value));
                            return store(moduleId, "moduleCode", value);
                        }
                        case "supervisorId" -> {
                            module.setSupervisorId(Integer.parseInt(value));
                            return store(moduleId, "supervisorId", value);
                        }
                        case "firstMarker" -> {
                            module.setFirstMarker(Integer.parseInt(value));
                            return store(moduleId, "firstMarkerId", value);
                        }
                        case "secondMarker" -> {
                            module.setSecondMarker(Integer.parseInt(value));
                            return store(moduleId, "secondMarkerId", value);
                        }
                        case "startDate" -> {
                            module.setStartDate(DateTimeUtils.formatDate(value));
                            return store(moduleId, "startDate", value);
                        }
                        case "endDate" -> {
                            module.setEndDate(DateTimeUtils.formatDate(value));
                            return store(moduleId, "endDate", value);
                        }
                        case "updated_at" -> {
                            module.setUpdatedAt(DateTimeUtils.formatDateTime(value));
                            return store(moduleId, "updated_at", value);
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
        userJson.encode(FileHandler.readFile(MODULE_DATA));
        return userJson.update(consultationId, attribute, value, MODULE_DATA);
    }*/


}
