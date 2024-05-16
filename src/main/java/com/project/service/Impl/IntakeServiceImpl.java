package com.project.service.Impl;

import com.project.dao.IntakeDAO;
import com.project.pojo.Intake;
import com.project.service.IntakeService;

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
}
