package com.project.service.Impl;

import com.project.common.utils.JsonHandler;
import com.project.controller.ProjectModuleController;
import com.project.dao.IntakeDAO;
import com.project.pojo.Intake;
import com.project.service.IntakeService;
import com.project.service.ProjectModuleService;
import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class IntakeServiceImpl implements IntakeService {
    private IntakeDAO intakesDAO = new IntakeDAO();
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
}
