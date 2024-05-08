package com.project.dao;

import com.project.common.constants.MessageConstant;
import com.project.common.constants.PresentationStatus;
import com.project.common.constants.ReportType;
import com.project.common.utils.DateTimeUtils;
import com.project.common.utils.FileHandler;
import com.project.common.utils.JsonHandler;
import com.project.common.utils.PropertiesReader;
import com.project.pojo.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportDAO {


    private static final String REPORT_DATA = PropertiesReader.getProperty("ReportData");
    private static List<Report> reports = new ArrayList<>();
    private static List<FinalYearReport> finalYearReports = new ArrayList<>();
    private static List<InvestigateReport> investigateReports = new ArrayList<>();
    private static List<Capstone1Report> capstone1Reports = new ArrayList<>();
    private static List<Capstone2Report> capstone2Reports = new ArrayList<>();

    static {
        loadConsultationData();
    }

    /**
     * Get All Reports ID
     * @return List of ID
     */
    private List<Integer> getAllReportId() {
        List<Integer> list = new ArrayList<>();
        for (Report report : reports) {
            list.add(report.getReportId());
        }
        for (InvestigateReport investigateReport : investigateReports) {
            list.add(investigateReport.getReportId());
        }
        for (FinalYearReport finalYearReports : finalYearReports) {
            list.add(finalYearReports.getReportId());
        }
        for (Capstone1Report capstone1Report : capstone1Reports) {
            list.add(capstone1Report.getReportId());
        }
        for (Capstone2Report capstone2Report : capstone2Reports) {
            list.add(capstone2Report.getReportId());
        }
        return list;
    }

    /**
     * Get All Report's Path
     *
     */
    public List<String> getAllReportsPath() {
        List<String> list = new ArrayList<>();
        for (Report report : reports) {
            list.add(report.getReportPath());
        }
        for (InvestigateReport investigateReport : investigateReports) {
            list.add(investigateReport.getReportPath());
        }
        for (FinalYearReport finalYearReports : finalYearReports) {
            list.add(finalYearReports.getReportPath());
        }
        for (Capstone1Report capstone1Report : capstone1Reports) {
            list.add(capstone1Report.getReportPath());
        }
        for (Capstone2Report capstone2Report : capstone2Reports) {
            list.add(capstone2Report.getReportPath());
        }
        return list;
    }

    /**
     * Preload Data into presentations Array
     */
    private static void loadConsultationData() {
        JsonHandler reportData = new JsonHandler();
        reportData.encode(FileHandler.readFile(REPORT_DATA));

        for (int i = 0; i < (reportData.getAll().size()); i++) {
            JsonHandler obj = new JsonHandler();
            obj.cloneObject(reportData.getObject(i));

            switch (ReportType.valueOf(obj.get("reportType"))) {
                case REPORT -> {
                    Report report = new Report();
                    report.setReportId(obj.getInt("id"));
                    report.setReportName(obj.get("reportName"));
                    report.setReportType(ReportType.valueOf(obj.get("reportType")));
                    report.setReportPath(obj.get("reportPath"));
                    reports.add(report);
                }
                case FINAL_YEAR -> {
                    FinalYearReport finalYearReport = new FinalYearReport();
                    finalYearReport.setReportId(obj.getInt("id"));
                    finalYearReport.setReportName(obj.get("reportName"));
                    finalYearReport.setReportType(ReportType.valueOf(obj.get("reportType")));
                    finalYearReport.setReportPath(obj.get("reportPath"));
                    finalYearReports.add(finalYearReport);
                }
                case INVESTIGATION -> {
                    InvestigateReport investigateReport = new InvestigateReport();
                    investigateReport.setReportId(obj.getInt("id"));
                    investigateReport.setReportName(obj.get("reportName"));
                    investigateReport.setReportType(ReportType.valueOf(obj.get("reportType")));
                    investigateReport.setReportPath(obj.get("reportPath"));
                    investigateReports.add(investigateReport);
                }
                case CAPSTONE_1 -> {
                    Capstone1Report capstone1Report = new Capstone1Report();
                    capstone1Report.setReportId(obj.getInt("id"));
                    capstone1Report.setReportName(obj.get("reportName"));
                    capstone1Report.setReportType(ReportType.valueOf(obj.get("reportType")));
                    capstone1Report.setReportPath(obj.get("reportPath"));
                    capstone1Reports.add(capstone1Report);
                }
                case CAPSTONE_2 -> {
                    Capstone2Report capstone2Report = new Capstone2Report();
                    capstone2Report.setReportId(obj.getInt("id"));
                    capstone2Report.setReportName(obj.get("reportName"));
                    capstone2Report.setReportType(ReportType.valueOf(obj.get("reportType")));
                    capstone2Report.setReportPath(obj.get("reportPath"));
                    capstone2Reports.add(capstone2Report);
                }
            }
        }
    }



/*

    // Update consultation data
    public static boolean update(Integer presentationId, String field, String value) {
        // System.out.println(value);
        for (Presentation presentation : presentations) {
            if (presentation.getPresentationId().equals(presentationId)) {
                try {
                    switch (field) {
                        case "moduleId" -> {
                            presentation.setModuleId(Integer.parseInt(value));
                            return store(presentationId, "moduleId", value);
                        }
                        case "lecturerId" -> {
                            presentation.setLecturerId(Integer.parseInt(value));
                            return store(presentationId, "lecturerId", value);
                        }
                        case "studentId" -> {
                            presentation.setStudentId(Integer.parseInt(value));
                            return store(presentationId, "studentId", value);
                        }
                        case "presentationDueDate" -> {
                            presentation.setPresentationDueDate(DateTimeUtils.formatDateTime(value));
                            return store(presentationId, "presentationDueDate", value);
                        }
                        case "presentationDateTime" -> {
                            presentation.setPresentationDateTime(DateTimeUtils.formatDateTime(value));
                            return store(presentationId, "presentationDateTime", value);
                        }
                        case "presentationStatus" -> {
                            presentation.setPresentationStatus(PresentationStatus.valueOf(value));
                            return store(presentationId, "presentationStatus", value);
                        }
                        case "presentationResult" -> {
                            presentation.setPresentationResult(Double.valueOf(value));
                            return store(presentationId, "presentationResult", value);
                        }
                        case "updated_at" -> {
                            presentation.setUpdatedAt(DateTimeUtils.formatDateTime(value));
                            return store(presentationId, "updated_at", value);
                        }
                        default -> {
                            log.info("Error: " + MessageConstant.ERROR_OBJECT_FIELD_NOT_FOUND);
                            return false;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }

        log.info("Error: " + MessageConstant.ERROR_OBJECT_NOT_FOUND + value);
        return false;

    }

    */
/**
     * Store updated data into text file
     * @param consultationId
     * @param attribute
     * @param value
     * @return
     *//*

    private static boolean store(Integer consultationId, String attribute, String value) {
        // System.out.println(consultationId + attribute + value);
        JsonHandler userJson = new JsonHandler();
        userJson.encode(FileHandler.readFile(PRESENTATION_DATA));
        return userJson.update(consultationId, attribute, value, PRESENTATION_DATA);
    }

*/

}