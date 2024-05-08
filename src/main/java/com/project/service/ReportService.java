package com.project.service;

public interface ReportService {

    /**
     * Submit Report (Student)
     * @param submissionId
     * @param filePath
     * @return Boolean
     */
    Boolean submitReport(Integer submissionId, String filePath);

}
