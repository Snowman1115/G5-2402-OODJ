package com.project.service;

public interface ReportService {

    /**
     * Submit Report (Student)
     * @param submissionId
     * @param filePath
     * @return Boolean
     */
    Boolean submitReport(Integer submissionId, String filePath);


    /**
     * Remove Report By Submission ID
     * @param submissionId
     * @return Boolean
     */
    Boolean removeSubmissionById(Integer submissionId);

}
