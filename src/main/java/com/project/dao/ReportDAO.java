package com.project.dao;

import com.project.common.constants.MessageConstant;
import com.project.common.constants.PresentationStatus;
import com.project.common.constants.ReportType;
import com.project.common.utils.*;
import com.project.pojo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.filetypedetector.FileType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * Temporary Display
     */
    public void getAllReports() {
        System.out.println(reports);
        System.out.println(finalYearReports);
        System.out.println(investigateReports);
        System.out.println(capstone1Reports);
        System.out.println(capstone2Reports);
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

            Integer newReportId = IDGenUtils.generateNewID(getAllReportId());
            switch (reportType) {
                case REPORT -> {
                    Report report = new Report();
                    report.setReportId(newReportId);
                    report.setReportName(originalFileName);
                    report.setReportPath(destinationPath.toString());
                    report.setReportType(reportType);
                    reports.add(report);
                }
                case INVESTIGATION -> {
                    InvestigateReport investigateReport = new InvestigateReport();
                    investigateReport.setReportId(newReportId);
                    investigateReport.setReportName(originalFileName);
                    investigateReport.setReportPath(destinationPath.toString());
                    investigateReport.setReportType(reportType);
                    investigateReports.add(investigateReport);
                }
                case FINAL_YEAR -> {
                    FinalYearReport finalYearReport = new FinalYearReport();
                    finalYearReport.setReportId(newReportId);
                    finalYearReport.setReportName(originalFileName);
                    finalYearReport.setReportPath(destinationPath.toString());
                    finalYearReport.setReportType(reportType);
                    finalYearReports.add(finalYearReport);
                }
                case CAPSTONE_1 -> {
                    Capstone1Report capstone1Report = new Capstone1Report();
                    capstone1Report.setReportId(newReportId);
                    capstone1Report.setReportName(originalFileName);
                    capstone1Report.setReportPath(destinationPath.toString());
                    capstone1Report.setReportType(reportType);
                    capstone1Reports.add(capstone1Report);
                }
                case CAPSTONE_2 -> {
                    Capstone2Report capstone2Report = new Capstone2Report();
                    capstone2Report.setReportId(newReportId);
                    capstone2Report.setReportName(originalFileName);
                    capstone2Report.setReportPath(destinationPath.toString());
                    capstone2Report.setReportType(reportType);
                    capstone2Reports.add(capstone2Report);
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
                        deleteFile(report.getReportPath().toString());
                    }
                }
                reports.removeIf(report -> report.getReportId().equals(reportId));
            }
            case INVESTIGATION -> {
                for (InvestigateReport investigateReport : investigateReports) {
                    if (investigateReport.getReportId().equals(reportId)) {
                        deleteFile(investigateReport.getReportPath().toString());
                    }
                }
                investigateReports.removeIf(investigateReport -> investigateReport.getReportId().equals(reportId));
            }
            case FINAL_YEAR -> {
                for (FinalYearReport finalYearReport : finalYearReports) {
                    if (finalYearReport.getReportId().equals(reportId)) {
                        deleteFile(finalYearReport.getReportPath().toString());
                    }
                }
                finalYearReports.removeIf(finalYearReport -> finalYearReport.getReportId().equals(reportId));
            }
            case CAPSTONE_1 -> {
                for (Capstone1Report capstone1Report : capstone1Reports) {
                    if (capstone1Report.getReportId().equals(reportId)) {
                        deleteFile(capstone1Report.getReportPath().toString());
                    }
                }
                capstone1Reports.removeIf(capstone1Report -> capstone1Report.getReportId().equals(reportId));
            }
            case CAPSTONE_2 -> {
                for (Capstone2Report capstone2Report : capstone2Reports) {
                    if (capstone2Report.getReportId().equals(reportId)) {
                        deleteFile(capstone2Report.getReportPath().toString());
                    }
                }
                capstone2Reports.removeIf(capstone2Report -> capstone2Report.getReportId().equals(reportId));
            }
        }
        getAllReports();
    }

    private void deleteFile(String filePath) {
        Path file = Paths.get(filePath);
        try {
            Files.delete(file);
            System.out.println("File deleted successfully.");
        } catch (IOException e) {
            System.err.println("Failed to delete the file: " + e.getMessage());
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