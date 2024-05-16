package com.project.dao;

import com.project.common.utils.DateTimeUtils;
import com.project.common.utils.FileHandler;
import com.project.common.utils.JsonHandler;
import com.project.common.utils.PropertiesReader;
import com.project.pojo.Intake;
import com.project.pojo.ProjectModule;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class IntakeDAO {

    private static final String INTAKE_DATA = PropertiesReader.getProperty("IntakeData");
    private static List<Intake> intakes = new ArrayList<>();

    static {
        loadConsultationData();
    }
    
//    public static void main(String[] args) {
//        System.out.println(intakes);
//    }

    /**
     * Get Intake By Id
     * @param intakeId
     * @return Intake
     */
    public Intake getIntakeById(Integer intakeId) {
        for (Intake intake : intakes) {
            if (intake.getIntakeId().equals(intakeId)) {
                return intake;
            }
        }
        return null;
    }

    /**
     * Preload Data into presentations Array
     */
    private static void loadConsultationData() {
        JsonHandler userData = new JsonHandler();
        userData.encode(FileHandler.readFile(INTAKE_DATA));

        for (int i = 0; i < (userData.getAll().size()); i++) {
            JsonHandler obj = new JsonHandler();
            obj.cloneObject(userData.getObject(i));

            Intake intake = new Intake();

            intake.setIntakeId(obj.getInt("id"));
            intake.setIntakeCode(obj.get("intakeCode"));

            String studentStr = obj.get("studentList");
            String[] studentArray = studentStr.split(",");
            List<String> studentListStr = Arrays.asList(studentArray);
            ArrayList<Integer> studentList = new ArrayList<>();
            for (String list : studentListStr) {
                studentList.add(Integer.parseInt(list));
            }

            intake.setStudentList(studentList);
            intake.setStartDate(DateTimeUtils.formatDate(obj.get("startDate")));
            intake.setEndDate(DateTimeUtils.formatDate(obj.get("endDate")));
            intake.setCreatedAt(DateTimeUtils.formatDateTime(obj.get("created_at")));
            intake.setUpdatedAt(DateTimeUtils.formatDateTime(obj.get("updated_at")));

            intakes.add(intake);
        }
    }

    public List<Intake> getAllIntakes() { return intakes; }

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
