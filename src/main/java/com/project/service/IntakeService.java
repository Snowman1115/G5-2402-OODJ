package com.project.service;

public interface IntakeService {
    /**
     * Check intake code availability
     * @param intakeCode
     * @return
     */
    public boolean checkIntakeCode(String intakeCode);
}
