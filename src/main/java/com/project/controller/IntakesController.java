package com.project.controller;

import com.project.common.utils.JsonHandler;
import com.project.service.Impl.IntakeServiceImpl;
import com.project.service.Impl.UserAuthenticationServiceImpl;
import com.project.service.IntakeService;
import com.project.service.UserAuthenticationService;

import lombok.extern.slf4j.Slf4j;
import java.time.LocalDate;
import java.util.List;

@Slf4j
public class IntakesController {
    private static IntakeService intakeService = new IntakeServiceImpl();
    private static UserAuthenticationService userAuthenticationService = new UserAuthenticationServiceImpl();

    public static boolean validateNewIntakeCode(String intakeCode) {
        return intakeService.checkIntakeCode(intakeCode);
    }
    public static boolean registerNewIntake(String intakeCode, LocalDate startDate, LocalDate endDate, JsonHandler assignedModules) { return intakeService.saveIntake(intakeCode, startDate, endDate, assignedModules); }

    public static List<String> getUpToDateIntakeCodes() { return intakeService.getDateValidIntakes(); }
    public static String getStudentIntake(int studentId) { return intakeService.getIntakeCodeByStudentId(studentId); }
    public static boolean changeIntake(int studentId, String newIntake, String oldIntake) {
        log.info("Change Student Intake: By - {} {} {}", userAuthenticationService.getAuthenticationUserDetails().getUserRoleType(), userAuthenticationService.getAuthenticationUserDetails().getUserId(), userAuthenticationService.getAuthenticationUserDetails().getUsername());
        return intakeService.updateIntake(studentId, newIntake, oldIntake);
    }
}
