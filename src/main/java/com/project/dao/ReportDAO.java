package com.project.dao;

import com.project.common.constants.ReportType;
import com.project.common.utils.*;
import com.project.pojo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
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
     * Get Report Details By Report ID and Type
     * @param reportId
     * @param reportType
     * return Report
     */
    public Report getAllReportByIdnType(Integer reportId, ReportType reportType) {
        switch (reportType) {
            case REPORT -> {
                for (Report report : reports) {
                    if (report.getReportId().equals(reportId)) {
                        return report;
                    }
                }
            }
            case INVESTIGATION -> {
                for (InvestigateReport investigateReport : investigateReports) {
                    if (investigateReport.getReportId().equals(reportId)) {
                        return investigateReport;
                    }
                }
            }
            case FINAL_YEAR -> {
                for (FinalYearReport finalYearReport : finalYearReports) {
                    if (finalYearReport.getReportId().equals(reportId)) {
                        return finalYearReport;
                    }
                }
            }
            case CAPSTONE_1 -> {
                for (Capstone1Report capstone1Report : capstone1Reports) {
                    if (capstone1Report.getReportId().equals(reportId)) {
                        return capstone1Report;
                    }
                }
            }
            case CAPSTONE_2 -> {
                for (Capstone2Report capstone2Report : capstone2Reports) {
                    if (capstone2Report.getReportId().equals(reportId)) {
                        return capstone2Report;
                    }
                }
            }
        }
        return null;
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
     * Save File into System Resources
     *
     */
    public Integer saveFile(String filePath, String systemFilePath, ReportType reportType) {
        try {
            PDDocument document = PDDocument.load(new File(filePath));
            String fileName = UUIDHanlder.genUUID().toString() + ".pdf";

            Path sourcePath = Paths.get(filePath);
            String originalFileName = sourcePath.getFileName().toString();

            Path destinationFolder = Paths.get(systemFilePath);
            if (!Files.exists(destinationFolder)) {
                Files.createDirectories(destinationFolder);
            }
            Path destinationPath = Paths.get(systemFilePath, fileName);
            Files.copy(new File(filePath).toPath(), destinationPath);
            document.close();

            LongIDGenerator longIDGenerator = new LongIDGenerator();
            Integer newReportId = longIDGenerator.generateNewID(getAllReportId());
            switch (reportType) {
                case REPORT -> {
                    Report report = new Report();
                    report.setReportId(newReportId);
                    report.setReportName(originalFileName);
                    report.setReportPath(destinationPath.toString());
                    report.setReportType(reportType);

                    reports.add(report);

                    JSONObject newReportJSON = new JSONObject();
                    newReportJSON.put("id", newReportId);
                    newReportJSON.put("reportName", originalFileName);
                    newReportJSON.put("reportType", reportType.toString());
                    newReportJSON.put("reportPath", destinationPath.toString());

                    JsonHandler reportJSON = new JsonHandler();
                    reportJSON.encode(FileHandler.readFile(REPORT_DATA));
                    reportJSON.addObject(newReportJSON, REPORT_DATA);
                }
                case INVESTIGATION -> {
                    InvestigateReport investigateReport = new InvestigateReport();
                    investigateReport.setReportId(newReportId);
                    investigateReport.setReportName(originalFileName);
                    investigateReport.setReportPath(destinationPath.toString());
                    investigateReport.setReportType(reportType);
                    investigateReports.add(investigateReport);

                    JSONObject newReportJSON = new JSONObject();
                    newReportJSON.put("id", newReportId);
                    newReportJSON.put("reportName", originalFileName);
                    newReportJSON.put("reportType", reportType.toString());
                    newReportJSON.put("reportPath", destinationPath.toString());

                    JsonHandler reportJSON = new JsonHandler();
                    reportJSON.encode(FileHandler.readFile(REPORT_DATA));
                    reportJSON.addObject(newReportJSON, REPORT_DATA);
                }
                case FINAL_YEAR -> {
                    FinalYearReport finalYearReport = new FinalYearReport();
                    finalYearReport.setReportId(newReportId);
                    finalYearReport.setReportName(originalFileName);
                    finalYearReport.setReportPath(destinationPath.toString());
                    finalYearReport.setReportType(reportType);
                    finalYearReports.add(finalYearReport);

                    JSONObject newReportJSON = new JSONObject();
                    newReportJSON.put("id", newReportId);
                    newReportJSON.put("reportName", originalFileName);
                    newReportJSON.put("reportType", reportType.toString());
                    newReportJSON.put("reportPath", destinationPath.toString());

                    JsonHandler reportJSON = new JsonHandler();
                    reportJSON.encode(FileHandler.readFile(REPORT_DATA));
                    reportJSON.addObject(newReportJSON, REPORT_DATA);
                }
                case CAPSTONE_1 -> {
                    Capstone1Report capstone1Report = new Capstone1Report();
                    capstone1Report.setReportId(newReportId);
                    capstone1Report.setReportName(originalFileName);
                    capstone1Report.setReportPath(destinationPath.toString());
                    capstone1Report.setReportType(reportType);
                    capstone1Reports.add(capstone1Report);

                    JSONObject newReportJSON = new JSONObject();
                    newReportJSON.put("id", newReportId);
                    newReportJSON.put("reportName", originalFileName);
                    newReportJSON.put("reportType", reportType.toString());
                    newReportJSON.put("reportPath", destinationPath.toString());

                    JsonHandler reportJSON = new JsonHandler();
                    reportJSON.encode(FileHandler.readFile(REPORT_DATA));
                    reportJSON.addObject(newReportJSON, REPORT_DATA);

                }
                case CAPSTONE_2 -> {
                    Capstone2Report capstone2Report = new Capstone2Report();
                    capstone2Report.setReportId(newReportId);
                    capstone2Report.setReportName(originalFileName);
                    capstone2Report.setReportPath(destinationPath.toString());
                    capstone2Report.setReportType(reportType);
                    capstone2Reports.add(capstone2Report);

                    JSONObject newReportJSON = new JSONObject();
                    newReportJSON.put("id", newReportId);
                    newReportJSON.put("reportName", originalFileName);
                    newReportJSON.put("reportType", reportType.toString());
                    newReportJSON.put("reportPath", destinationPath.toString());

                    JsonHandler reportJSON = new JsonHandler();
                    reportJSON.encode(FileHandler.readFile(REPORT_DATA));
                    reportJSON.addObject(newReportJSON, REPORT_DATA);

                }
            }

            log.info("PDF File Saved Successfully : " + destinationPath);
            return newReportId;
        } catch (IOException e) {
            log.warn("PDF File Saved Failed : " + e.getMessage());
            return null;
        }
    }

    /**
     * Remove Report Data
     * @param reportId
     * @param reportType
     */
    public void removeReport(Integer reportId, ReportType reportType) {
        switch (reportType) {
            case REPORT -> {
                for (Report report : reports) {
                    if (report.getReportId().equals(reportId)) {
                        // deleteFile(report.getReportPath().toString());
                        JsonHandler jsonHandler = new JsonHandler();
                        jsonHandler.encode(FileHandler.readFile(REPORT_DATA));
                        jsonHandler.delete(reportId, REPORT_DATA);
                    }
                }
                reports.removeIf(report -> report.getReportId().equals(reportId));
            }
            case INVESTIGATION -> {
                for (InvestigateReport investigateReport : investigateReports) {
                    if (investigateReport.getReportId().equals(reportId)) {
                        // deleteFile(investigateReport.getReportPath().toString());
                        JsonHandler jsonHandler = new JsonHandler();
                        jsonHandler.encode(FileHandler.readFile(REPORT_DATA));
                        jsonHandler.delete(reportId, REPORT_DATA);
                    }
                }
                investigateReports.removeIf(investigateReport -> investigateReport.getReportId().equals(reportId));
            }
            case FINAL_YEAR -> {
                for (FinalYearReport finalYearReport : finalYearReports) {
                    if (finalYearReport.getReportId().equals(reportId)) {
                        // deleteFile(finalYearReport.getReportPath().toString());
                        JsonHandler jsonHandler = new JsonHandler();
                        jsonHandler.encode(FileHandler.readFile(REPORT_DATA));
                        jsonHandler.delete(reportId, REPORT_DATA);
                    }
                }
                finalYearReports.removeIf(finalYearReport -> finalYearReport.getReportId().equals(reportId));
            }
            case CAPSTONE_1 -> {
                for (Capstone1Report capstone1Report : capstone1Reports) {
                    if (capstone1Report.getReportId().equals(reportId)) {
                        // deleteFile(capstone1Report.getReportPath().toString());
                        JsonHandler jsonHandler = new JsonHandler();
                        jsonHandler.encode(FileHandler.readFile(REPORT_DATA));
                        jsonHandler.delete(reportId, REPORT_DATA);
                    }
                }
                capstone1Reports.removeIf(capstone1Report -> capstone1Report.getReportId().equals(reportId));
            }
            case CAPSTONE_2 -> {
                for (Capstone2Report capstone2Report : capstone2Reports) {
                    if (capstone2Report.getReportId().equals(reportId)) {
                        // deleteFile(capstone2Report.getReportPath().toString());
                        JsonHandler jsonHandler = new JsonHandler();
                        jsonHandler.encode(FileHandler.readFile(REPORT_DATA));
                        jsonHandler.delete(reportId, REPORT_DATA);
                    }
                }
                capstone2Reports.removeIf(capstone2Report -> capstone2Report.getReportId().equals(reportId));
            }
        }
    }

    private void deleteFile(String filePath) {
        Path file = Paths.get(filePath);
        try {
            // Now try to delete the file
            Files.deleteIfExists(file);
        } catch (IOException e) {
            // Handle if file deletion fails
            e.printStackTrace(); // Print stack trace for debugging
        }
    }

    /**
     * Preload Data into presentations Array
     */
    private static void loadConsultationData() {
        JsonHandler reportData = new JsonHandler();
        reportData.encode(FileHandler.readFile(REPORT_DATA));

        for (int i = 0; i < (reportData.getAll().size()); i++) {
            JsonHandler obj = new JsonHandler();
            obj.setObject(reportData.getObject(i));

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

/**
     * Store updated data into text file
     * @param consultationId
     * @param attribute
     * @param value
     * @return
     */

    private static boolean store(Integer consultationId, String attribute, String value) {
        // System.out.println(consultationId + attribute + value);
        JsonHandler userJson = new JsonHandler();
        userJson.encode(FileHandler.readFile(REPORT_DATA));
        return userJson.update(consultationId, attribute, value, REPORT_DATA);
    }


}