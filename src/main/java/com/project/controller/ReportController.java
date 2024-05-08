package com.project.controller;

import com.project.service.Impl.ReportServiceImpl;
import com.project.service.ReportService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReportController {

    private static ReportService reportService = new ReportServiceImpl();

    /**
     * Submit Report (Student)
     * @param submissionId
     * @param filePath
     * @return Boolean
     */
    public static Boolean submitReport(Integer submissionId, String filePath) {
        log.info("Project Submission : " + submissionId);
        return reportService.submitReport(submissionId, filePath);
    }



}
