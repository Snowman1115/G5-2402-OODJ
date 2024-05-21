package com.project.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

@Slf4j
public class XlsxHandlerUtils {

    public static void readExcelBySheetName(String filePath, String sheetName) {

        try {
            FileInputStream file = new FileInputStream(filePath);
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                System.out.println("Sheet '" + sheetName + "' not found.");
                return;
            }

            switch (sheet.getSheetName()) {
                case "User" -> {

                    JSONArray jsonUserArray = new JSONArray();
                    JSONArray jsonRoleArray = new JSONArray();

                    for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                        Row row = sheet.getRow(rowIndex);
                        if (row == null) continue; // Skip empty rows
                        JSONObject userJson = new JSONObject(); // Create a JSON object for each user
                        JSONObject roleJson = new JSONObject();
                        for (int i = 0; i < row.getLastCellNum(); i++) { // Start from the first column
                            Cell cell = row.getCell(i);
                            if (cell == null) continue;

                            String columnName = sheet.getRow(0).getCell(i).toString().trim();
                            switch (columnName) {
                                case "id":
                                    Integer userId = Integer.parseInt(cell.getStringCellValue());
                                    userJson.put("id", userId);
                                    roleJson.put("id", userId);
                                    break;
                                case "first_name":
                                    userJson.put("first_name", cell.getStringCellValue());
                                    break;
                                case "last_name":
                                    userJson.put("last_name", cell.getStringCellValue());
                                    break;
                                case "email":
                                    userJson.put("email", cell.getStringCellValue());
                                    break;
                                case "username":
                                    userJson.put("username", cell.getStringCellValue());
                                    break;
                                case "password":
                                    String encryptedPassword = BCrypt.hashpw(cell.getStringCellValue(), BCrypt.gensalt());
                                    userJson.put("password", encryptedPassword);
                                    break;
                                case "safeWord":
                                    userJson.put("safeWord", cell.getStringCellValue());
                                    break;
                                case "created_at":
                                    userJson.put("created_at", cell.getStringCellValue());
                                    break;
                                case "updated_at":
                                    userJson.put("updated_at", cell.getStringCellValue());
                                    break;
                                case "roleType":
                                    roleJson.put("roleType", cell.getStringCellValue());
                                    break;
                                case "status":
                                    roleJson.put("status", cell.getStringCellValue());
                                    break;
                                default:
                                    break;
                            }
                        }
                        jsonUserArray.add(userJson);
                        jsonRoleArray.add(roleJson);
                    }
                    workbook.close();
                    file.close();

                    File file1 = new File("src/main/resources/Data/UserData/UserData.txt");
                    FileWriter fileWriter1 = new FileWriter(file1);
                    fileWriter1.write(jsonUserArray.toJSONString());
                    fileWriter1.close();

                    File file2 = new File("src/main/resources/Data/UserData/UserRole.txt");
                    FileWriter fileWriter2 = new FileWriter(file2);
                    fileWriter2.write(jsonRoleArray.toJSONString());
                    fileWriter2.close();

                }

                case "Intake" -> {

                    JSONArray jsonArray = new JSONArray();

                    for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                        Row row = sheet.getRow(rowIndex);
                        if (row == null) continue; // Skip empty rows
                        JSONObject json = new JSONObject(); // Create a JSON object for each user
                        for (int i = 0; i < row.getLastCellNum(); i++) { // Start from the first column
                            Cell cell = row.getCell(i);
                            if (cell == null) continue;

                            String columnName = sheet.getRow(0).getCell(i).toString().trim();
                            switch (columnName) {
                                case "id":
                                    Integer intakeId = Integer.parseInt(cell.getStringCellValue());
                                    json.put("id", intakeId);
                                    break;
                                case "intakeCode":
                                    json.put("intakeCode", cell.getStringCellValue());
                                    break;
                                case "studentList":
                                    json.put("studentList", cell.getStringCellValue());
                                    break;
                                case "startDate":
                                    json.put("startDate", cell.getStringCellValue());
                                    break;
                                case "endDate":
                                    json.put("endDate", cell.getStringCellValue());
                                    break;
                                case "created_at":
                                    json.put("created_at", cell.getStringCellValue());
                                    break;
                                case "updated_at":
                                    json.put("updated_at", cell.getStringCellValue());
                                    break;
                                default:
                                    break;
                            }
                        }
                        jsonArray.add(json);
                    }
                    workbook.close();
                    file.close();

                    File file1 = new File("src/main/resources/Data/Intake/IntakeData.txt");
                    FileWriter fileWriter1 = new FileWriter(file1);
                    fileWriter1.write(jsonArray.toJSONString());
                    fileWriter1.close();

                }

                case "ProjectModule" -> {

                    JSONArray jsonArray = new JSONArray();

                    for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                        Row row = sheet.getRow(rowIndex);
                        if (row == null) continue; // Skip empty rows
                        JSONObject json = new JSONObject(); // Create a JSON object for each user
                        for (int i = 0; i < row.getLastCellNum(); i++) { // Start from the first column
                            Cell cell = row.getCell(i);
                            if (cell == null) continue;

                            String columnName = sheet.getRow(0).getCell(i).toString().trim();
                            switch (columnName) {
                                case "id":
                                    Integer moduleId = Integer.parseInt(cell.getStringCellValue());
                                    json.put("id", moduleId);
                                    break;
                                case "intakeId":
                                    Integer intakeId = Integer.parseInt(cell.getStringCellValue());
                                    json.put("intakeId", intakeId);
                                    break;
                                case "moduleCode":
                                    json.put("moduleCode", cell.getStringCellValue());
                                    break;
                                case "supervisorId":
                                    Integer supervisorId = Integer.parseInt(cell.getStringCellValue());
                                    json.put("supervisorId", supervisorId);
                                    break;
                                case "firstMarkerId":
                                    Integer firstMarkerId = Integer.parseInt(cell.getStringCellValue());
                                    json.put("firstMarkerId", firstMarkerId);
                                    break;
                                case "secondMarkerId":
                                    Integer secondMarkerId = Integer.parseInt(cell.getStringCellValue());
                                    json.put("secondMarkerId", secondMarkerId);
                                    break;
                                case "startDate":
                                    json.put("startDate", cell.getStringCellValue());
                                    break;
                                case "endDate":
                                    json.put("endDate", cell.getStringCellValue());
                                    break;
                                case "created_at":
                                    json.put("created_at", cell.getStringCellValue());
                                    break;
                                case "updated_at":
                                    json.put("updated_at", cell.getStringCellValue());
                                    break;
                                default:
                                    break;
                            }
                        }
                        jsonArray.add(json);
                    }
                    workbook.close();
                    file.close();

                    File file1 = new File("src/main/resources/Data/Module/ProjectModuleData.txt");
                    FileWriter fileWriter1 = new FileWriter(file1);
                    fileWriter1.write(jsonArray.toJSONString());
                    fileWriter1.close();

                }
                case "Submission" -> {

                    JSONArray jsonArray = new JSONArray();

                    for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                        Row row = sheet.getRow(rowIndex);
                        if (row == null) continue; // Skip empty rows
                        JSONObject json = new JSONObject(); // Create a JSON object for each user
                        for (int i = 0; i < row.getLastCellNum(); i++) { // Start from the first column
                            Cell cell = row.getCell(i);
                            if (cell == null) continue;

                            String columnName = sheet.getRow(0).getCell(i).toString().trim();
                            switch (columnName) {
                                case "id":
                                    Integer submissionId = Integer.parseInt(cell.getStringCellValue());
                                    json.put("id", submissionId);
                                    break;
                                case "reportId":
                                    Integer reportId = Integer.parseInt(cell.getStringCellValue());
                                    json.put("reportId", reportId);
                                    break;
                                case "moduleId":
                                    Integer moduleId = Integer.parseInt(cell.getStringCellValue());
                                    json.put("moduleId", moduleId);
                                    break;
                                case "studentId":
                                    Integer studentId = Integer.parseInt(cell.getStringCellValue());
                                    json.put("studentId", studentId);
                                    break;
                                case "submissionDueDate":
                                    json.put("submissionDueDate", cell.getStringCellValue());
                                    break;
                                case "reportStatus":
                                    json.put("reportStatus", cell.getStringCellValue());
                                    break;
                                case "reportType":
                                    json.put("reportType", cell.getStringCellValue());
                                    break;
                                case "reportResult":
                                    json.put("reportResult", cell.getStringCellValue());
                                    break;
                                case "comment":
                                    json.put("comment", cell.getStringCellValue());
                                    break;
                                case "submitted_at":
                                    json.put("submitted_at", cell.getStringCellValue());
                                    break;
                                case "marked_at":
                                    json.put("marked_at", cell.getStringCellValue());
                                    break;
                                case "created_at":
                                    json.put("created_at", cell.getStringCellValue());
                                    break;
                                case "updated_at":
                                    json.put("updated_at", cell.getStringCellValue());
                                    break;
                                default:
                                    break;
                            }
                        }
                        jsonArray.add(json);
                    }
                    workbook.close();
                    file.close();

                    File file1 = new File("src/main/resources/Data/Submission/SubmissionData.txt");
                    FileWriter fileWriter1 = new FileWriter(file1);
                    fileWriter1.write(jsonArray.toJSONString());
                    fileWriter1.close();

                }

                case "Presentation" -> {

                    JSONArray jsonArray = new JSONArray();

                    for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                        Row row = sheet.getRow(rowIndex);
                        if (row == null) continue; // Skip empty rows
                        JSONObject json = new JSONObject(); // Create a JSON object for each user
                        for (int i = 0; i < row.getLastCellNum(); i++) { // Start from the first column
                            Cell cell = row.getCell(i);
                            if (cell == null) continue;

                            String columnName = sheet.getRow(0).getCell(i).toString().trim();
                            switch (columnName) {
                                case "id":
                                    Integer presentationId = Integer.parseInt(cell.getStringCellValue());
                                    json.put("id", presentationId);
                                    break;
                                case "moduleId":
                                    Integer moduleId = Integer.parseInt(cell.getStringCellValue());
                                    json.put("moduleId", moduleId);
                                    break;
                                case "lecturerId":
                                    Integer lecturerId = Integer.parseInt(cell.getStringCellValue());
                                    json.put("lecturerId", lecturerId);
                                    break;
                                case "studentId":
                                    Integer studentId = Integer.parseInt(cell.getStringCellValue());
                                    json.put("studentId", studentId);
                                    break;
                                case "presentationDueDate":
                                    json.put("presentationDueDate", cell.getStringCellValue());
                                    break;
                                case "presentationDateTime":
                                    json.put("presentationDateTime", cell.getStringCellValue());
                                    break;
                                case "presentationStatus":
                                    json.put("presentationStatus", cell.getStringCellValue());
                                    break;
                                case "presentationResult":
                                    json.put("presentationResult", cell.getStringCellValue());
                                    break;
                                case "created_at":
                                    json.put("created_at", cell.getStringCellValue());
                                    break;
                                case "updated_at":
                                    json.put("updated_at", cell.getStringCellValue());
                                    break;
                                default:
                                    break;
                            }
                        }
                        jsonArray.add(json);
                    }
                    workbook.close();
                    file.close();

                    File file1 = new File("src/main/resources/Data/Presentation/PresentationData.txt");
                    FileWriter fileWriter1 = new FileWriter(file1);
                    fileWriter1.write(jsonArray.toJSONString());
                    fileWriter1.close();

                }
                case "Consultation" -> {

                    JSONArray jsonArray = new JSONArray();

                    for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                        Row row = sheet.getRow(rowIndex);
                        if (row == null) continue; // Skip empty rows
                        JSONObject json = new JSONObject(); // Create a JSON object for each user
                        for (int i = 0; i < row.getLastCellNum(); i++) { // Start from the first column
                            Cell cell = row.getCell(i);
                            if (cell == null) continue;

                            String columnName = sheet.getRow(0).getCell(i).toString().trim();
                            switch (columnName) {
                                case "id":
                                    Integer consultationId = Integer.parseInt(cell.getStringCellValue());
                                    json.put("id", consultationId);
                                    break;
                                case "lecturerId":
                                    Integer lecturerId = Integer.parseInt(cell.getStringCellValue());
                                    json.put("lecturerId", lecturerId);
                                    break;
                                case "studentId":
                                    Integer studentId = Integer.parseInt(cell.getStringCellValue());
                                    json.put("studentId", studentId);
                                    break;
                                case "consultationDateTime":
                                    json.put("consultationDateTime", cell.getStringCellValue());
                                    break;
                                case "consultationStatus":
                                    json.put("consultationStatus", cell.getStringCellValue());
                                    break;
                                case "created_at":
                                    json.put("created_at", cell.getStringCellValue());
                                    break;
                                case "updated_at":
                                    json.put("updated_at", cell.getStringCellValue());
                                    break;
                                default:
                                    break;
                            }
                        }
                        jsonArray.add(json);
                    }
                    workbook.close();
                    file.close();

                    File file1 = new File("src/main/resources/Data/ConsultationData/ConsultationData.txt");
                    FileWriter fileWriter1 = new FileWriter(file1);
                    fileWriter1.write(jsonArray.toJSONString());
                    fileWriter1.close();

                }
                case "Report" -> {

                    JSONArray jsonArray = new JSONArray();

                    for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                        Row row = sheet.getRow(rowIndex);
                        if (row == null) continue; // Skip empty rows
                        JSONObject json = new JSONObject(); // Create a JSON object for each user
                        for (int i = 0; i < row.getLastCellNum(); i++) { // Start from the first column
                            Cell cell = row.getCell(i);
                            if (cell == null) continue;

                            String columnName = sheet.getRow(0).getCell(i).toString().trim();
                            switch (columnName) {
                                case "id":
                                    Integer reportId = Integer.parseInt(cell.getStringCellValue());
                                    json.put("id", reportId);
                                    break;
                                case "reportName":
                                    json.put("reportName", cell.getStringCellValue());
                                    break;
                                case "reportPath":
                                    json.put("reportPath", cell.getStringCellValue());
                                    break;
                                case "reportType":
                                    json.put("reportType", cell.getStringCellValue());
                                    break;
                                default:
                                    break;
                            }
                        }
                        jsonArray.add(json);
                    }
                    workbook.close();
                    file.close();

                    File file1 = new File("src/main/resources/Data/Submission/ReportData.txt");
                    FileWriter fileWriter1 = new FileWriter(file1);
                    fileWriter1.write(jsonArray.toJSONString());
                    fileWriter1.close();

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        String filePath = "src/main/resources/Data/SampleDataXlsx/G5_OODJ_Sample_Data.xlsx"; // Provide the path to your Excel file
//        readExcelBySheetName(filePath, "User");
        // readExcelBySheetName(filePath, "Intake");
//        readExcelBySheetName(filePath, "ProjectModule");
        readExcelBySheetName(filePath, "Submission");
//        readExcelBySheetName(filePath, "Presentation");
//        readExcelBySheetName(filePath, "Consultation");
        // readExcelBySheetName(filePath, "Report");
    }

}
