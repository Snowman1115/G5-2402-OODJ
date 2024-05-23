package com.project.controller;

import com.project.common.utils.JsonHandler;
import com.project.service.Impl.IntakeServiceImpl;
import com.project.service.IntakeService;

import java.time.LocalDate;
import java.util.List;

public class IntakesController {
    private static IntakeService intakeService = new IntakeServiceImpl();

    public static boolean validateNewIntakeCode(String intakeCode) {
        return intakeService.checkIntakeCode(intakeCode);
    }
    public static boolean registerNewIntake(String intakeCode, LocalDate startDate, LocalDate endDate, JsonHandler assignedModules) { return intakeService.saveIntake(intakeCode, startDate, endDate, assignedModules); }

    public static List<String> getUpToDateIntakeCodes() { return intakeService.getDateValidIntakes(); }
    public static String getStudentIntake(int studentId) { return intakeService.getIntakeCodeByStudentId(studentId); }
}
