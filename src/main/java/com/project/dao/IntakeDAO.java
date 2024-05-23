package com.project.dao;

import com.project.common.constants.MessageConstant;
import com.project.common.utils.*;
import com.project.pojo.Intake;
import com.project.pojo.UserAccount;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class IntakeDAO {

    private static final String INTAKE_DATA = PropertiesReader.getProperty("IntakeData");
    private static List<Intake> intakes = new ArrayList<>();

    static {
        loadConsultationData();
    }
    
//    public static void main(String[] args) {
//        IntakeDAO intakes = new IntakeDAO();
//        intakes.addNewStudent(41902888, 111222);
//        intakes.addNewStudent(41902888, 222111);
//        intakes.addNewStudent(41902888, 333222);
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
     * get intake id by intake code
     * @param intakeCode
     * @return
     */
    public int getIntakeIdByIntakeCode(String intakeCode) {
        for (Intake i : intakes) {
            if (i.getIntakeCode().equals(intakeCode)) {
                return i.getIntakeId();
            }
        }

        return -1;
    }

    /**
     * Preload Data into presentations Array
     */
    private static void loadConsultationData() {
        JsonHandler userData = new JsonHandler();
        userData.encode(FileHandler.readFile(INTAKE_DATA));

        for (int i = 0; i < (userData.getAll().size()); i++) {
            JsonHandler obj = new JsonHandler();
            obj.setObject(userData.getObject(i));

            Intake intake = new Intake();

            intake.setIntakeId(obj.getInt("id"));
            intake.setIntakeCode(obj.get("intakeCode"));

            String studentStr = obj.get("studentList");
            ArrayList<Integer> studentList = new ArrayList<>();

            if (!studentStr.isEmpty()) {
                String[] studentArray = studentStr.split(",");
                List<String> studentListStr = Arrays.asList(studentArray);
                for (String list : studentListStr) {
                    studentList.add(Integer.parseInt(list));
                }
            }

            intake.setStudentList(studentList);
            intake.setStartDate(DateTimeUtils.formatDate(obj.get("startDate")));
            intake.setEndDate(DateTimeUtils.formatDate(obj.get("endDate")));
            intake.setCreatedAt(DateTimeUtils.formatDateTime(obj.get("created_at")));
            intake.setUpdatedAt(DateTimeUtils.formatDateTime(obj.get("updated_at")));

            intakes.add(intake);
        }
    }

    /**
     * get all intakes
     * @return intakes
     */
    public List<Intake> getAllIntakes() { return intakes; }

    /**
     * add new intake
     * @param intakeCode
     * @param startDate
     * @param endDate
     * @return int
     */
    public Integer addIntake(String intakeCode, LocalDate startDate, LocalDate endDate) {
        JsonHandler intakesJson = new JsonHandler();
        List<Integer> idList = new ArrayList<>();
        IDGenerator idGenerator = new LongIDGenerator();

        intakesJson.encode(FileHandler.readFile(INTAKE_DATA));

        for (Intake itk : intakes) {
            idList.add(itk.getIntakeId());
        }
        int newIntakeId = idGenerator.generateNewID(idList);

        Intake newIntake = new Intake();
        newIntake.setIntakeId(newIntakeId);
        newIntake.setIntakeCode(intakeCode);
        newIntake.setStudentList(new ArrayList<>());
        newIntake.setStartDate(startDate);
        newIntake.setEndDate(endDate);
        newIntake.setCreatedAt(LocalDateTime.now());
        newIntake.setUpdatedAt(LocalDateTime.now());
        intakes.add(newIntake);

        JSONObject newIntakeObj = new JSONObject();
        newIntakeObj.put("id", newIntakeId);
        newIntakeObj.put("intakeCode", intakeCode);
        newIntakeObj.put("studentList", "");
        newIntakeObj.put("startDate", DateTimeUtils.formatStrDate(startDate));
        newIntakeObj.put("endDate", DateTimeUtils.formatStrDate(endDate));
        newIntakeObj.put("created_at", DateTimeUtils.formatStrDateTime(LocalDateTime.now()));
        newIntakeObj.put("updated_at", DateTimeUtils.formatStrDateTime(LocalDateTime.now()));

        intakesJson.addObject(newIntakeObj, INTAKE_DATA);

        return newIntakeId;
    }

    /**
     * register new student into an intake
     * @param intakeId
     * @param studentId
     */
    public void addNewStudent(int intakeId, int studentId) {
        for (Intake itk : intakes) {
            if (itk.getIntakeId().equals(intakeId)) {
                itk.getStudentList().add(studentId);
                String students = itk.getStudentList().stream().map(String::valueOf).collect(Collectors.joining(","));

                JsonHandler userJson = new JsonHandler();
                userJson.encode(FileHandler.readFile(INTAKE_DATA));
                userJson.update(itk.getIntakeId(), "studentList", students, INTAKE_DATA);
                userJson.update(itk.getIntakeId(), "updated_at", DateTimeUtils.formatStrDateTime(LocalDateTime.now()), INTAKE_DATA);
            }
        }
    }

    /**
     * remove student from an intake
     * @param intakeId
     * @param studentId
     */
    public void removeStudent(int intakeId, int studentId) {
        for (Intake itk : intakes) {
            if (itk.getIntakeId().equals(intakeId)) {
                itk.getStudentList().remove((Integer)studentId);
                String students = itk.getStudentList().stream().map(String::valueOf).collect(Collectors.joining(","));

                JsonHandler userJson = new JsonHandler();
                userJson.encode(FileHandler.readFile(INTAKE_DATA));
                userJson.update(itk.getIntakeId(), "studentList", students, INTAKE_DATA);
                userJson.update(itk.getIntakeId(), "updated_at", DateTimeUtils.formatStrDateTime(LocalDateTime.now()), INTAKE_DATA);
            }
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

    */



}
