package com.project.service.Impl;

import com.project.common.constants.ReportStatus;
import com.project.common.utils.JsonHandler;
import com.project.controller.ProjectModuleController;
import com.project.dao.*;
import com.project.pojo.Intake;
import com.project.service.IntakeService;
import com.project.service.ProjectModuleService;
import org.json.simple.JSONObject;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class IntakeServiceImpl implements IntakeService {
    private IntakeDAO intakesDAO = new IntakeDAO();
    private ModuleDAO moduleDAO = new ModuleDAO();
    private SubmissionDAO submissionDAO = new SubmissionDAO();
    private PresentationDAO presentationDAO = new PresentationDAO();
    @Override
    public boolean checkIntakeCode(String intakeCode) {
        List<String> currentIntakes = new ArrayList<>();

        for (Intake itk : intakesDAO.getAllIntakes()) {
            currentIntakes.add(itk.getIntakeCode());
        }

        return !currentIntakes.contains(intakeCode);
    }

    /**
     * save new intake
     * @param intakeCode
     * @param startDate
     * @param endDate
     * @return boolean
     */
    public boolean saveIntake(String intakeCode, LocalDate startDate, LocalDate endDate, JsonHandler assignedModules) {
        try {
            int newIntakeId = intakesDAO.addIntake(intakeCode, startDate, endDate);

            for (int i=0; i<assignedModules.getAll().size(); i++) {
                JSONObject moduleObj = assignedModules.getObject(i);
                ProjectModuleController.addNewModule(newIntakeId, moduleObj.get("moduleCode").toString(), Integer.parseInt(moduleObj.get("projectManager").toString()), startDate, endDate);
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * get date valid intakes
     * @return
     */
    @Override
    public List<String> getDateValidIntakes() {
        List<String> intakeList = new ArrayList<>();
        int validPeriod = 1; //valid year period

        for (Intake itk : intakesDAO.getAllIntakes()) {
            if (ChronoUnit.YEARS.between(itk.getStartDate(), itk.getEndDate()) >= validPeriod) {
                intakeList.add(itk.getIntakeCode());
            }
        }

        return intakeList;
    }

    /**
     * get student intake code
     * @param studentId
     * @return
     */
    @Override
    public String getIntakeCodeByStudentId(int studentId) {
        for (Intake itk : intakesDAO.getAllIntakes()) {
            String intake_code = itk.getIntakeCode();
            List<Integer> students = itk.getStudentList();

            if (students.contains(studentId)) {
                return intake_code;
            }
        }
        return null;
    }

    /**
     * change student intake
     * @param studentId
     * @param newIntakeCode
     * @param oldIntakeCode
     * @return
     */
    @Override
    public boolean updateIntake(int studentId, String newIntakeCode, String oldIntakeCode) {
        int newIntake = intakesDAO.getIntakeIdByIntakeCode(newIntakeCode);
        int oldIntake = intakesDAO.getIntakeIdByIntakeCode(oldIntakeCode);

        try {
            intakesDAO.removeStudent(oldIntake, studentId);
            intakesDAO.addNewStudent(newIntake, studentId);
            log.info("Change Student Intake: Student ({}) intake changed {} -> {}", studentId, oldIntakeCode, newIntakeCode);

            List<Integer> modules = new ArrayList<>(moduleDAO.getModulesByIntakeId(newIntake));

            LocalDate changedIntakeEndDate = intakesDAO.getIntakeById(newIntake).getEndDate();

            // remove old submissions, add new submissions
            List<Map<String, String>> oldSubmissions = submissionDAO.getAllSubmissionDetailsByStudentId(studentId);
            List<Integer> submissionIds = new ArrayList<>();
            for (Map<String, String> list : oldSubmissions) {
                submissionIds.add(Integer.parseInt(list.get("id")));
            }

            for (int i : submissionIds) {
                submissionDAO.delete(i);
            }
            log.info("Change Student Intake: Old modules submissions has been removed.");

            for (int i : modules) {
                submissionDAO.createSubmission(i, studentId, changedIntakeEndDate);
            }
            log.info("Change Student Intake: New modules submissions has been added.");

            // remove old presentations, add new presentations
            List<Map<String, String>> oldPresentations = presentationDAO.getAllPresentationDetailsByStudentId(studentId);
            List<Integer> presentationIds = new ArrayList<>();
            for (Map<String, String> map : oldPresentations) {
                presentationIds.add(Integer.parseInt(map.get("id")));
            }
            for (int i : presentationIds) {
                presentationDAO.delete(i);
            }
            log.info("Change Student Intake: Old modules presentations has been removed.");

            for (int i : modules) {
                presentationDAO.add(i, 0, studentId, changedIntakeEndDate);
            }
            log.info("Change Student Intake: New modules presentations has been added.");
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
