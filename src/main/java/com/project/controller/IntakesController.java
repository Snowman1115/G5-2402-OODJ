package com.project.controller;

import com.project.service.Impl.IntakeServiceImpl;
import com.project.service.IntakeService;

public class IntakesController {
    private static IntakeService intakeService = new IntakeServiceImpl();

    public static boolean validateNewIntakeCode(String intakeCode) {
        return intakeService.checkIntakeCode(intakeCode);
    }
}
