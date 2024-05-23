package com.project.service;

import com.project.common.utils.JsonHandler;

import java.time.LocalDate;
import java.util.List;

public interface IntakeService {
    /**
     * Check intake code availability
     * @param intakeCode
     * @return
     */
    public boolean checkIntakeCode(String intakeCode);

    /**
     * save new intake and assigned modules
     * @param intakeCode
     * @param startDate
     * @param endDate
     * @param assignedModules
     * @return
     */
    public boolean saveIntake(String intakeCode, LocalDate startDate, LocalDate endDate, JsonHandler assignedModules);

    /**
     * get intakes that has period more or equals to 1 year (will not return those that are less than 1 year period)
     * @return List<String> intakeCodes
     */
    public List<String> getDateValidIntakes();

    public String getIntakeCodeByStudentId(int studentId);
}
