package com.project.service.Impl;

import com.project.dao.ReportDAO;
import com.project.dao.SubmissionDAO;
import com.project.pojo.Submission;
import com.project.service.ReportService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReportServiceImpl implements ReportService {

    private SubmissionDAO submissionDAO = new SubmissionDAO();

    private ReportDAO reportDAO = new ReportDAO();

    @Override
    public Boolean submitReport(Integer submissionId, String filePath) {
        return null;


    }
}
