package com.project.ui.project_manager;

import com.project.ui.student.*;
import com.project.common.constants.MessageConstant;
import com.project.common.utils.DateTimeUtils;
import com.project.common.utils.Dialog;
import com.project.common.utils.JsonHandler;
import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

import com.project.controller.ConsultationController;
import com.project.controller.ProjectModuleController;
import com.project.controller.SubmissionController;
import com.project.controller.UserAccountController;
import static java.lang.Integer.parseInt;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import org.bouncycastle.tsp.TSPUtil;
import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;

import java.util.List;
import java.util.Map;
import javax.swing.table.TableRowSorter;
import org.json.simple.JSONObject;

/**
 *
 * @author Olaf
 */
public class ManagerModuleGui extends javax.swing.JInternalFrame {

     private Integer moduleId;
     private Map<String, String> currentModuleDetails;
     private String firstMarkerName, secondMarkerName, moduleType;
     private JsonHandler lecturerLists;
    /**
     * Creates new form StudentAssignmentGui
     */
    public ManagerModuleGui() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI)this.getUI();
        ui.setNorthPane(null);
        refresh();

        // salesManagementPanel.setText(PropertiesReader.getProperty("SalesManagementPanelVersion"));
        // refresh();
    }

    private void refresh() {
        moduleId = null;
        refreshTable();
        fillspModuleComboBox();
        fillAssessmentComboBox();
        refreshTextField();
        ModuleTabbedPanel.setSelectedIndex(0);
        Map<String, Integer> moduleStatus = ProjectModuleController.getModuleStatusForPM();
        menuBtn12.setText(String.valueOf(moduleStatus.get("unassigned")));
        menuBtn13.setText(String.valueOf(moduleStatus.get("total")));
    }
    
    private void refreshTable() {
        DefaultTableModel dtm =  (DefaultTableModel)jTableModuleDetails.getModel();
        dtm.setRowCount(0);
        List<Map<String, String>> moduleLists = ProjectModuleController.getAllModuleDetailsByProjectManagerId();
        for (Map<String,String> list : moduleLists) {
            String[] data = {list.get("id"),list.get("moduleCode"), list.get("startDate"),list.get("endDate"), list.get("firstMarker") , list.get("secondMarker"),};
            dtm.addRow(data);   
        }
    }
    
    private void refreshTextField(){
        spModuleId.setText(null);
        spModuleName.setText(null);
        spStartDate.setText(null);
        spEndDate.setText(null);
        spModuleSPComboBox.setSelectedIndex(-1);
        spModuleSMComboBox.setSelectedIndex(-1);
        atModuleId.setText(null);
        atModuleName.setText(null);
        atStartDate.setText(null);
        atEndDate.setText(null);
        assessmentTypeComboBox.setSelectedIndex(-1);
        mdModuleId.setText(null);
        mdModuleName.setText(null);
        mdStartDatePicker.setDate(null);
        mdEndDatePicker.setDate(null);
    }
    
    private void refreshTableByFilter(Integer option) {
        DefaultTableModel dtm = (DefaultTableModel)jTableModuleDetails.getModel();
        dtm.setRowCount(0);
        
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dtm);
        jTableModuleDetails.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter("".trim()));
        
        List<Map<String, String>> lists = ProjectModuleController.getAllModuleDetailsByProjectManagerId();

        switch(option) {
            case 0 -> {
                for (Map<String, String> list : lists) {
                    String[] data = {list.get("id"),list.get("moduleCode"), list.get("startDate"),list.get("endDate"), list.get("firstMarker") , list.get("secondMarker"),};
                    dtm.addRow(data);
                }
            }
            case 1 -> {
                for (Map<String, String> list : lists) {
                    LocalDate startDate = DateTimeUtils.formatDate(list.get("startDate"));
                    LocalDate endDate = DateTimeUtils.formatDate(list.get("endDate"));
                    LocalDate currentDate = LocalDate.now();
                    if (currentDate.isAfter(startDate) && currentDate.isBefore(endDate)) {
                        String[] data = {list.get("id"),list.get("moduleCode"), list.get("startDate"),list.get("endDate"), list.get("firstMarker") , list.get("secondMarker"),};
                        dtm.addRow(data);
                    }
                }
            }
            case 2 -> {
                for (Map<String, String> list : lists) {
                    LocalDate endDate = DateTimeUtils.formatDate(list.get("endDate"));
                    LocalDate currentDate = LocalDate.now();
                    if (endDate.isBefore(currentDate)) {
                        String[] data = {list.get("id"),list.get("moduleCode"), list.get("startDate"),list.get("endDate"), list.get("firstMarker") , list.get("secondMarker"),};
                        dtm.addRow(data);
                    }
                }
            }
            case 3 -> {
                for (Map<String, String> list : lists) {
                    LocalDate startDate = DateTimeUtils.formatDate(list.get("startDate"));
                    LocalDate currentDate = LocalDate.now();
                    if (currentDate.isBefore(startDate)) {
                        String[] data = {list.get("id"),list.get("moduleCode"), list.get("startDate"),list.get("endDate"), list.get("firstMarker") , list.get("secondMarker"),};
                        dtm.addRow(data);
                    }
                }
            }      
            
            case 4 -> {
                for (Map<String, String> list : lists) {
                    String fm = list.get("firstMarker");
                    String sm = list.get("secondMarker");
                    if ("0".equals(fm) || fm == null || "0".equals(sm) || sm == null) {
                        String[] data = {list.get("id"),list.get("moduleCode"), list.get("startDate"),list.get("endDate"), list.get("firstMarker") , list.get("secondMarker"),};
                        dtm.addRow(data);
                    }
                }
            }
        }
    }
    
    
        private void autofillModuleSupervisor(Integer moduleId) {
            // Get module detail by id
            List<Map<String, String>> moduleLists = ProjectModuleController.getModuleById(moduleId);
//          Check if module List is empty 
            Map<String, String> mLists = moduleLists.isEmpty() ? null : moduleLists.get(0);
//              if module list is not empty
                if (mLists != null) {
                    //Store current module details
                    currentModuleDetails = mLists;
                    //Auto fill text field
                    spModuleId.setText(mLists.get("id"));
                    spModuleName.setText(mLists.get("moduleCode"));
                    spStartDate.setText(mLists.get("startDate"));
                    spEndDate.setText(mLists.get("endDate"));
                    String firstMarker = mLists.get("firstMarker");
                    String secondMarker = mLists.get("secondMarker");
                    
                    if (firstMarker != null && !firstMarker.equals("0")) {
                        // Find the lecturer's full name corresponding to first marker ID
                        for (int i = 0; i < lecturerLists.getAll().size(); i++) {
                            JSONObject lecturer = (JSONObject) lecturerLists.getAll().get(i);
                            if (lecturer.get("id").toString().equals(firstMarker)) {
                                String firstName = lecturer.get("first_name").toString();
                                String lastName = lecturer.get("last_name").toString();
                                String fullName = firstName + " " + lastName;
                                firstMarkerName = fullName;
                                spModuleSPComboBox.setSelectedItem(fullName);
                                break;
                            }
                        }
                    } else {
                        spModuleSPComboBox.setSelectedItem("None");
                    }
                    if (secondMarker != null && !secondMarker.equals("0")) {
                    // Find the lecturer's full name corresponding to second marker ID
                        for (int i = 0; i < lecturerLists.getAll().size(); i++) {
                            JSONObject lecturer = (JSONObject) lecturerLists.getAll().get(i);
                            if (lecturer.get("id").toString().equals(secondMarker)) {
                                String firstName = lecturer.get("first_name").toString();
                                String lastName = lecturer.get("last_name").toString();
                                String fullName = firstName + " " + lastName;
                                secondMarkerName = fullName;
                                spModuleSMComboBox.setSelectedItem(fullName);
                                break;
                            }
                        }
                    } else {
                        spModuleSMComboBox.setSelectedItem("None");
                    }
                }
        }
        
        private void autofillAssessment(Integer moduleId){
            List<Map<String, String>> moduleTypeLists = ProjectModuleController.getModuleTypeById(moduleId);
            Map<String, String> mLists = moduleTypeLists.isEmpty() ? null : moduleTypeLists.get(0);

//          if module type list is not empty
            if (moduleTypeLists != null) {
                    moduleType = mLists.get("assessmentType");
                    atModuleId.setText(mLists.get("id"));
                    atModuleName.setText(mLists.get("moduleCode"));
                    atStartDate.setText(mLists.get("startDate"));
                    atEndDate.setText(mLists.get("endDate"));
                    if(moduleType != null){
                    switch (moduleType){
                        case "INTERNSHIP":
                            assessmentTypeComboBox.setSelectedItem("Internship");
                            break;
                         
                        case "INVESTIGATION":
                            assessmentTypeComboBox.setSelectedItem("Investigation Report");
                            break;
                            
                        case "CAPSTONE_1":
                            assessmentTypeComboBox.setSelectedItem("Capstone 1");
                            break;
                        
                        case "CAPSTONE_2":
                            assessmentTypeComboBox.setSelectedItem("Capstone 2");
                            break;
                            
                        case "FINAL_YEAR":
                            assessmentTypeComboBox.setSelectedItem("Final Year Report");
                            break;
                        
                        default:
                            assessmentTypeComboBox.setSelectedItem("None");
                            break;
                    }} else {
                            assessmentTypeComboBox.setSelectedItem("None");
                      }
            } else {
                assessmentTypeComboBox.setSelectedItem("None");
            }
        }
        
        private void autofillStartEndDate(Integer moduleId){
            List<Map<String, String>> moduleLists = ProjectModuleController.getModuleById(moduleId);
            Map<String, String> mLists = moduleLists.isEmpty() ? null : moduleLists.get(0);
//              if module list is not empty
                if (mLists != null) {
                    //Store current module details
                    currentModuleDetails = mLists;
                    //Auto fill text field
                    mdModuleId.setText(mLists.get("id"));
                    mdModuleName.setText(mLists.get("moduleCode"));
                    LocalDate startDate = DateTimeUtils.formatDate(mLists.get("startDate"));
                    LocalDate endDate = DateTimeUtils.formatDate(mLists.get("endDate"));

                    mdStartDatePicker.setDate(startDate);
                    mdEndDatePicker.setDate(endDate);
                }
        }
        
        private void fillspModuleComboBox(){
            // Create list to store all lectures name
            spModuleSPComboBox.removeAllItems();
            spModuleSMComboBox.removeAllItems();
            lecturerLists = UserAccountController.getLecturers();
            List<String> lecturersName = new ArrayList<>();
            
            //Using for loop to add lecturer into combo box selection
            for (int i = 0; i < lecturerLists.getAll().size(); i++) {
                JSONObject lecturer = (JSONObject) lecturerLists.getAll().get(i);
                String firstName = lecturer.get("first_name").toString();
                String lastName = lecturer.get("last_name").toString();
                String fullName = firstName + " " + lastName;
                lecturersName.add(fullName);
            }
            // Sort the name list
            Collections.sort(lecturersName, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o1.compareTo(o2);
                }   
            });
            //Add the "none" option into combo box selection 
            spModuleSPComboBox.addItem("None");
            spModuleSMComboBox.addItem("None");
            //Using for loop to add lecturer into combo box selection
            for (String lecName : lecturersName) {
                spModuleSPComboBox.addItem(lecName);
                spModuleSMComboBox.addItem(lecName);
            }
        }
        
        private void fillAssessmentComboBox(){
            assessmentTypeComboBox.addItem("None");
            assessmentTypeComboBox.addItem("Internship");
            assessmentTypeComboBox.addItem("Investigation Report");
            assessmentTypeComboBox.addItem("Capstone 1");
            assessmentTypeComboBox.addItem("Capstone 2");
            assessmentTypeComboBox.addItem("Final Year Report");
        }
        
        private String getLecturerIdByName(String firstName, String lastName) {
            for (int i = 0; i < lecturerLists.getAll().size(); i++) {
                JSONObject lecturer = (JSONObject) lecturerLists.getAll().get(i);
                if (lecturer.get("first_name").equals(firstName) && lecturer.get("last_name").equals(lastName)) {
                    return lecturer.get("id").toString();
                }
            }
            return null; // Handle case where lecturer name is not found
        }
        
        //All save function 
        private void saveDataChanges(){
            String saveModuleId = spModuleId.getText();
            String saveSelectedSP = (String) spModuleSPComboBox.getSelectedItem();
            String saveSelectedSM = (String) spModuleSMComboBox.getSelectedItem();
            //check if module exist
            if(currentModuleDetails != null){
                if (saveSelectedSP != firstMarkerName || saveSelectedSM != secondMarkerName){
                    if(saveSelectedSP != saveSelectedSM){
                    List<String> savedModule = new ArrayList<>();
                    savedModule.add(saveModuleId);
                    //Break full name to first name and last name
                    String[] spName = saveSelectedSP.split("\\s+");
                    String[] smName = saveSelectedSM.split("\\s+");
                    
                    String spId = getLecturerIdByName(spName[0], spName[1]);
                    String smId = getLecturerIdByName(smName[0], smName[1]);
                    
                    savedModule.add(spId != null ? spId : "");
                    savedModule.add(smId != null ? smId : "");
                    
                    if(ProjectModuleController.saveModuleDetails(savedModule)){
                        refresh();
                    }
                    } else{
                        Dialog.ErrorDialog(MessageConstant.SAME_MARKER_SUPERVISOR);
                    }
                } else{
                    Dialog.ErrorDialog(MessageConstant.CONDITION_NO_DATA_CHANGES);
                }
            }  else {
                Dialog.ErrorDialog(MessageConstant.ERROR_EMPTY_MODULE);
            }
        }
        
        public void saveAssessmentType(){
            String assessmentType = (String) assessmentTypeComboBox.getSelectedItem();
            String savedAssessment;
            if(moduleId != null){
                if (assessmentType != moduleType){
                    switch(assessmentType){
                        case "Internship":
                            savedAssessment = "INTERNSHIP";
                            break;
                            
                        case "Investigation Report":
                            savedAssessment = "INVESTIGATION";
                            break;
                            
                        case "Capstone 1":
                            savedAssessment = "CAPSTONE_1";
                            break;
                        
                        case "Capstone 2":
                            savedAssessment = "CAPSTONE_2";
                            break;
                            
                        case "Final Year Report":
                            savedAssessment = "FINAL_YEAR";
                            break;
                        
                        default:
                            savedAssessment = "REPORT";
                            break;
                    }

                    if(SubmissionController.saveAssessmentType(moduleId, savedAssessment)){
                        refresh();
                    }
                } else{
                        Dialog.ErrorDialog(MessageConstant.CONDITION_NO_DATA_CHANGES);
                    }
            }  else {
                Dialog.ErrorDialog(MessageConstant.ERROR_EMPTY_MODULE);
                }
        }

        private void saveModuleDate(){
            String saveModuleId = mdModuleId.getText();
            if(moduleId != null){
                Integer saveIntModuleId = parseInt(saveModuleId);
                LocalDate startDate = mdStartDatePicker.getDate();
                LocalDate endDate = mdEndDatePicker.getDate();
                if (startDate == null || endDate == null) {
                    Dialog.ErrorDialog("Start date or end date cannot be null.");
                    return;
                }
                if (startDate.isBefore(endDate)) {
                    if(ProjectModuleController.saveModuleDate(saveIntModuleId, startDate, endDate)){
                            refresh();
                    } else {
                        Dialog.ErrorDialog(MessageConstant.UNEXPECTED_ERROR);
                    }
                } else {
                    Dialog.ErrorDialog(MessageConstant.ERROR_DATE_INVALID);
                }
            }else {
                    Dialog.ErrorDialog(MessageConstant.ERROR_EMPTY_MODULE);
            }
        }

//    private void refreshDashboard() {
//        Map<String, Integer> map = ConsultationController.getUpcomingNFinishedConsultationForStudent();
//        menuBtn12.setText(map.get("upcoming").toString());
//        menuBtn13.setText(map.get("finished").toString());
//
//        fillInJComboBox();
//        refreshTable();
//    }
//
//    private void refreshDetails(Object value) {
//        if (consultationComboBox2.getSelectedItem() != null) {
//            List<Map<String, String>> lists = ConsultationController.getAllScheduledConsultationIdByStudentId();
//            for (Map<String, String> list : lists) {
//                if (value.equals(list.get("id"))) {
//                    JField19.setText(list.get("lecturer"));
//                    JField18.setText(list.get("date"));
//                    JField20.setText(list.get("status"));
//                }
//            }
//        }
//    }
//
//    private void fillInJComboBox() {
//        consultationComboBox1.removeAllItems();
//        consultationComboBox1.addItem("All");
//        List<String> lists = ConsultationController.getAllLecturerNProjectManagerNameForStudent();
//        for (String list : lists) {
//            consultationComboBox1.addItem(list);
//        }
//        consultationComboBox1.setSelectedIndex(0);
//
//        consultationComboBox2.removeAllItems();
//        List<Map<String, String>> lists2 = ConsultationController.getAllScheduledConsultationIdByStudentId();
//        if (lists2.isEmpty()) {
//            consultationComboBox2.addItem("There is no scheduled consultation.");
//        }
//        for (Map<String, String> list : lists2) {
//            consultationComboBox2.addItem(list.get("id"));
//        }
//        consultationComboBox2.setSelectedIndex(0);
//    }
//
//    private void refreshComboBox1(String value) {
//        DefaultTableModel dtm1 =  (DefaultTableModel)jTable4.getModel();
//        dtm1.setRowCount(0);
//        
//        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dtm1);
//        jTable4.setRowSorter(tr);
//        tr.setRowFilter(RowFilter.regexFilter("".trim()));
//        
//        List<Map<String, String>> availableSlots = ConsultationController.getAllAvailableConsultationSlots();
//        if (value.equals("All")) {
//            for (Map<String,String> list : availableSlots) {
//                String[] data = {list.get("id"), list.get("lecturer"), list.get("date"), list.get("status")};
//                dtm1.addRow(data);
//            }
//        } else {
//            for (Map<String,String> list : availableSlots) {
//                if (list.get("lecturer").equals(value)) {
//                    String[] data = {list.get("id"), list.get("lecturer"), list.get("date"), list.get("status")};
//                    dtm1.addRow(data);
//                }
//            }
//        }
//    }
//
//    private void refreshComboBox(Integer value) {
//        DefaultTableModel dtm = (DefaultTableModel)jTable3.getModel();
//        dtm.setRowCount(0);
//        
//        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dtm);
//        jTable3.setRowSorter(tr);
//        tr.setRowFilter(RowFilter.regexFilter("".trim()));
//        
//        
//        List<Map<String, String>> lists = ConsultationController.getAllEventsForStudent();
//
//        switch(value) {
//            case 0 -> {
//                for (Map<String, String> list : lists) {
//                    String[] data = {list.get("id"), list.get("lecturer"), list.get("date"), list.get("status")};
//                    dtm.addRow(data);
//                }
//            }
//            case 1 -> {
//                for (Map<String, String> list : lists) {
//                    if (list.get("status").equals("SCHEDULED")) {
//                        String[] data = {list.get("id"), list.get("lecturer"), list.get("date"), list.get("status")};
//                        dtm.addRow(data);
//                    }
//                }
//            }
//            case 2 -> {
//                for (Map<String, String> list : lists) {
//                    if (list.get("status").equals("COMPLETED")) {
//                        String[] data = {list.get("id"), list.get("lecturer"), list.get("date"), list.get("status")};
//                        dtm.addRow(data);
//                    }
//                }
//            }
//        }
//    }
//    
//    private void refreshTable() {
//        DefaultTableModel dtm =  (DefaultTableModel)jTable2.getModel();
//        dtm.setRowCount(0);
//        List<Map<String, String>> lists = ConsultationController.getUpcomingEventForStudent();
//        for (Map<String,String> list : lists) {
//            String[] data = {list.get("date"), list.get("lecturer")};
//            dtm.addRow(data);
//        }
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ModuleTabbedPanel = new javax.swing.JTabbedPane();
        Panel1 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        menuBtn4 = new javax.swing.JLabel();
        menuBtn13 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        menuBtn3 = new javax.swing.JLabel();
        menuBtn12 = new javax.swing.JLabel();
        menuBtn14 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        JField12 = new javax.swing.JTextField();
        moduleStatusComboBox = new javax.swing.JComboBox<>();
        menuBtn15 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        manageSupervisor = new javax.swing.JLabel();
        manageAssessmentType = new javax.swing.JLabel();
        manageModuleDate = new javax.swing.JLabel();
        menuBtn16 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableModuleDetails = new javax.swing.JTable();
        Panel2 = new javax.swing.JPanel();
        JSeparator33 = new javax.swing.JSeparator();
        menuBtn35 = new javax.swing.JLabel();
        menuBtn29 = new javax.swing.JLabel();
        menuBtn38 = new javax.swing.JLabel();
        menuBtn36 = new javax.swing.JLabel();
        spModuleSPComboBox = new javax.swing.JComboBox<>();
        spSaveButton = new javax.swing.JLabel();
        spModuleName = new javax.swing.JTextField();
        spModuleId = new javax.swing.JTextField();
        spEndDate = new javax.swing.JTextField();
        JSeparator37 = new javax.swing.JSeparator();
        jLabel40 = new javax.swing.JLabel();
        spModuleSMComboBox = new javax.swing.JComboBox<>();
        menuBtn37 = new javax.swing.JLabel();
        menuBtn30 = new javax.swing.JLabel();
        spStartDate = new javax.swing.JTextField();
        menuBtn32 = new javax.swing.JLabel();
        menuBtn34 = new javax.swing.JLabel();
        menuBtn33 = new javax.swing.JLabel();
        JSeparator39 = new javax.swing.JSeparator();
        jLabel42 = new javax.swing.JLabel();
        menuBtn47 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        menuBtn40 = new javax.swing.JLabel();
        menuBtn31 = new javax.swing.JLabel();
        menuBtn42 = new javax.swing.JLabel();
        atModuleId = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        atModuleName = new javax.swing.JTextField();
        JSeparator34 = new javax.swing.JSeparator();
        menuBtn44 = new javax.swing.JLabel();
        JSeparator38 = new javax.swing.JSeparator();
        atEndDate = new javax.swing.JTextField();
        menuBtn45 = new javax.swing.JLabel();
        menuBtn39 = new javax.swing.JLabel();
        menuBtn41 = new javax.swing.JLabel();
        assessmentTypeComboBox = new javax.swing.JComboBox<>();
        atStartDate = new javax.swing.JTextField();
        menuBtn43 = new javax.swing.JLabel();
        atSaveButton = new javax.swing.JLabel();
        menuBtn48 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        JSeparator40 = new javax.swing.JSeparator();
        Panel3 = new javax.swing.JPanel();
        JSeparator35 = new javax.swing.JSeparator();
        menuBtn46 = new javax.swing.JLabel();
        menuBtn49 = new javax.swing.JLabel();
        menuBtn50 = new javax.swing.JLabel();
        mdSaveButton = new javax.swing.JLabel();
        mdModuleName = new javax.swing.JTextField();
        mdModuleId = new javax.swing.JTextField();
        JSeparator41 = new javax.swing.JSeparator();
        jLabel44 = new javax.swing.JLabel();
        menuBtn52 = new javax.swing.JLabel();
        menuBtn53 = new javax.swing.JLabel();
        menuBtn54 = new javax.swing.JLabel();
        menuBtn55 = new javax.swing.JLabel();
        JSeparator42 = new javax.swing.JSeparator();
        jLabel45 = new javax.swing.JLabel();
        menuBtn57 = new javax.swing.JLabel();
        mdEndDatePicker = new com.github.lgooddatepicker.components.DatePicker();
        mdStartDatePicker = new com.github.lgooddatepicker.components.DatePicker();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ModuleTabbedPanel.setBackground(new java.awt.Color(230, 230, 230));
        ModuleTabbedPanel.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        ModuleTabbedPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ModuleTabbedPanelMouseClicked(evt);
            }
        });

        Panel1.setPreferredSize(new java.awt.Dimension(1050, 570));
        Panel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel10.setBackground(new java.awt.Color(254, 254, 254));

        menuBtn4.setBackground(new java.awt.Color(153, 255, 153));
        menuBtn4.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/success-24x24.png"))); // NOI18N
        menuBtn4.setText("TOTAL");
        menuBtn4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn4.setOpaque(true);

        menuBtn13.setBackground(new java.awt.Color(153, 255, 153));
        menuBtn13.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 18)); // NOI18N
        menuBtn13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn13.setText("0");
        menuBtn13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuBtn13.setOpaque(true);
        menuBtn13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuBtn13MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menuBtn4, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
            .addComponent(menuBtn13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(menuBtn4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(menuBtn13, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE))
        );

        Panel1.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 50, 240, 90));

        jPanel6.setBackground(new java.awt.Color(255, 255, 153));

        menuBtn3.setBackground(new java.awt.Color(255, 255, 153));
        menuBtn3.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/status-24x24.png"))); // NOI18N
        menuBtn3.setText("UNASSIGNED");
        menuBtn3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn3.setOpaque(true);

        menuBtn12.setBackground(new java.awt.Color(255, 255, 153));
        menuBtn12.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 18)); // NOI18N
        menuBtn12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn12.setText("0");
        menuBtn12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuBtn12.setOpaque(true);
        menuBtn12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuBtn12MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menuBtn3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(menuBtn12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(menuBtn3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(menuBtn12, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                .addContainerGap())
        );

        Panel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 240, 90));

        menuBtn14.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn14.setText("MODULE ");
        menuBtn14.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn14.setOpaque(true);
        Panel1.add(menuBtn14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 500, 40));

        jLabel11.setBackground(new java.awt.Color(254, 254, 254));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/search-24x24.png"))); // NOI18N
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel11.setOpaque(true);
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });
        Panel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 180, 40, 35));

        JField12.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField12.setText("Enter Keywords To Search");
        JField12.setBorder(null);
        JField12.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField12.setForeground(new java.awt.Color(1, 1, 1));
        JField12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField12MouseClicked(evt);
            }
        });
        Panel1.add(JField12, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 180, 290, 35));

        moduleStatusComboBox.setBackground(new java.awt.Color(254, 254, 254));
        moduleStatusComboBox.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 12)); // NOI18N
        moduleStatusComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Current Active","Completed", "Upcoming", "Unassigned" }));
        moduleStatusComboBox.setToolTipText("d");
        moduleStatusComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        moduleStatusComboBox.setFocusable(false);
        moduleStatusComboBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                moduleStatusComboBoxMouseClicked(evt);
            }
        });
        moduleStatusComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moduleStatusComboBoxActionPerformed(evt);
            }
        });
        moduleStatusComboBox.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                moduleStatusComboBoxPropertyChange(evt);
            }
        });
        Panel1.add(moduleStatusComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 330, 35));

        menuBtn15.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn15.setText("ACTION");
        menuBtn15.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn15.setOpaque(true);
        Panel1.add(menuBtn15, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 10, 420, 40));

        jPanel7.setBackground(new java.awt.Color(204, 204, 255));

        manageSupervisor.setBackground(new java.awt.Color(105, 105, 105));
        manageSupervisor.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        manageSupervisor.setForeground(new java.awt.Color(1, 1, 1));
        manageSupervisor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/officer-24x24.png"))); // NOI18N
        manageSupervisor.setText("MANAGE MARKER");
        manageSupervisor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        manageSupervisor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                manageSupervisorMouseClicked(evt);
            }
        });

        manageAssessmentType.setBackground(new java.awt.Color(105, 105, 105));
        manageAssessmentType.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        manageAssessmentType.setForeground(new java.awt.Color(1, 1, 1));
        manageAssessmentType.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        manageAssessmentType.setText("MANAGE ASSESSMENT TYPES");
        manageAssessmentType.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        manageAssessmentType.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                manageAssessmentTypeMouseClicked(evt);
            }
        });

        manageModuleDate.setBackground(new java.awt.Color(105, 105, 105));
        manageModuleDate.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        manageModuleDate.setForeground(new java.awt.Color(1, 1, 1));
        manageModuleDate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/moduleDate.png"))); // NOI18N
        manageModuleDate.setText("MANAGE MODULE DATE");
        manageModuleDate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        manageModuleDate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                manageModuleDateMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(manageAssessmentType, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(manageSupervisor, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(manageModuleDate, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(manageSupervisor)
                    .addComponent(manageModuleDate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(manageAssessmentType)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        Panel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 50, 500, 90));

        menuBtn16.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn16.setText("VIEW MODULE DETAILS");
        menuBtn16.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn16.setOpaque(true);
        Panel1.add(menuBtn16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 670, 40));

        jTableModuleDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Module Id", "Module Name", "Start Date", "End Date", "First Marker", "Second Marker"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableModuleDetails.setFocusable(false);
        jTableModuleDetails.setRequestFocusEnabled(false);
        jTableModuleDetails.getTableHeader().setResizingAllowed(false);
        jTableModuleDetails.getTableHeader().setReorderingAllowed(false);
        jTableModuleDetails.setUpdateSelectionOnSort(false);
        jTableModuleDetails.setVerifyInputWhenFocusTarget(false);
        jScrollPane3.setViewportView(jTableModuleDetails);
        if (jTableModuleDetails.getColumnModel().getColumnCount() > 0) {
            jTableModuleDetails.getColumnModel().getColumn(0).setResizable(false);
            jTableModuleDetails.getColumnModel().getColumn(1).setResizable(false);
            jTableModuleDetails.getColumnModel().getColumn(2).setResizable(false);
            jTableModuleDetails.getColumnModel().getColumn(2).setHeaderValue("Start Date");
            jTableModuleDetails.getColumnModel().getColumn(3).setResizable(false);
            jTableModuleDetails.getColumnModel().getColumn(3).setHeaderValue("End Date");
            jTableModuleDetails.getColumnModel().getColumn(4).setResizable(false);
            jTableModuleDetails.getColumnModel().getColumn(4).setHeaderValue("First Marker");
            jTableModuleDetails.getColumnModel().getColumn(5).setResizable(false);
        }

        Panel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 1020, 360));

        ModuleTabbedPanel.addTab("OverView", Panel1);

        Panel2.setPreferredSize(new java.awt.Dimension(1050, 570));
        Panel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        JSeparator33.setForeground(new java.awt.Color(1, 1, 1));
        Panel2.add(JSeparator33, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 470, 170, 10));

        menuBtn35.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn35.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/calendar-24x24.png"))); // NOI18N
        menuBtn35.setText("END DATE");
        menuBtn35.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn35.setOpaque(true);
        Panel2.add(menuBtn35, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, 300, 40));

        menuBtn29.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn29.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/action-24x24.png"))); // NOI18N
        menuBtn29.setText("ACTION :");
        menuBtn29.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn29.setOpaque(true);
        Panel2.add(menuBtn29, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 430, 90, 40));

        menuBtn38.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn38.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn38.setText("MODULE NAME");
        menuBtn38.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn38.setOpaque(true);
        Panel2.add(menuBtn38, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 300, 40));

        menuBtn36.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn36.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/officer-24x24.png"))); // NOI18N
        menuBtn36.setText("SUPERVISOR");
        menuBtn36.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn36.setOpaque(true);
        Panel2.add(menuBtn36, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 50, 300, 40));

        spModuleSPComboBox.setBackground(new java.awt.Color(254, 254, 254));
        spModuleSPComboBox.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 12)); // NOI18N
        spModuleSPComboBox.setToolTipText("d");
        spModuleSPComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        spModuleSPComboBox.setFocusable(false);
        spModuleSPComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                spModuleSPComboBoxActionPerformed(evt);
            }
        });
        Panel2.add(spModuleSPComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 90, 300, 35));

        spSaveButton.setBackground(new java.awt.Color(254, 254, 254));
        spSaveButton.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        spSaveButton.setForeground(new java.awt.Color(1, 1, 1));
        spSaveButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        spSaveButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/add-green-24x24.png"))); // NOI18N
        spSaveButton.setText("SAVE");
        spSaveButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        spSaveButton.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        spSaveButton.setOpaque(true);
        spSaveButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                spSaveButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                spSaveButtonMouseEntered(evt);
            }
        });
        Panel2.add(spSaveButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 430, 170, 35));

        spModuleName.setEditable(false);
        spModuleName.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        spModuleName.setBackground(new java.awt.Color(255, 255, 255));
        spModuleName.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        spModuleName.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        spModuleName.setForeground(new java.awt.Color(1, 1, 1));
        spModuleName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                spModuleNameMouseClicked(evt);
            }
        });
        spModuleName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                spModuleNameActionPerformed(evt);
            }
        });
        Panel2.add(spModuleName, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 300, 35));

        spModuleId.setEditable(false);
        spModuleId.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        spModuleId.setBackground(new java.awt.Color(255, 255, 255));
        spModuleId.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        spModuleId.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        spModuleId.setForeground(new java.awt.Color(1, 1, 1));
        spModuleId.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                spModuleIdMouseClicked(evt);
            }
        });
        spModuleId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                spModuleIdActionPerformed(evt);
            }
        });
        Panel2.add(spModuleId, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 300, 35));

        spEndDate.setEditable(false);
        spEndDate.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        spEndDate.setBackground(new java.awt.Color(255, 255, 255));
        spEndDate.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        spEndDate.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        spEndDate.setForeground(new java.awt.Color(1, 1, 1));
        spEndDate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                spEndDateMouseClicked(evt);
            }
        });
        Panel2.add(spEndDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 300, 35));

        JSeparator37.setForeground(new java.awt.Color(1, 1, 1));
        Panel2.add(JSeparator37, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 550, 170, 10));

        jLabel40.setBackground(new java.awt.Color(254, 254, 254));
        jLabel40.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(1, 1, 1));
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/delete-red-24x24.png"))); // NOI18N
        jLabel40.setText("CANCEL");
        jLabel40.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel40.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel40.setOpaque(true);
        jLabel40.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel40MouseClicked(evt);
            }
        });
        Panel2.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 510, 170, 35));

        spModuleSMComboBox.setBackground(new java.awt.Color(254, 254, 254));
        spModuleSMComboBox.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 12)); // NOI18N
        spModuleSMComboBox.setToolTipText("d");
        spModuleSMComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        spModuleSMComboBox.setFocusable(false);
        spModuleSMComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                spModuleSMComboBoxActionPerformed(evt);
            }
        });
        Panel2.add(spModuleSMComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 170, 300, 35));

        menuBtn37.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn37.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/action-24x24.png"))); // NOI18N
        menuBtn37.setText("ACTION :");
        menuBtn37.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn37.setOpaque(true);
        Panel2.add(menuBtn37, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 510, 90, 40));

        menuBtn30.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn30.setText("Manage Supervisor & Second Marker");
        menuBtn30.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn30.setOpaque(true);
        Panel2.add(menuBtn30, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 500, 40));

        spStartDate.setEditable(false);
        spStartDate.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        spStartDate.setBackground(new java.awt.Color(255, 255, 255));
        spStartDate.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        spStartDate.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        spStartDate.setForeground(new java.awt.Color(1, 1, 1));
        spStartDate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                spStartDateMouseClicked(evt);
            }
        });
        spStartDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                spStartDateActionPerformed(evt);
            }
        });
        Panel2.add(spStartDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, 300, 35));

        menuBtn32.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn32.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn32.setText("MODULE ID");
        menuBtn32.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn32.setOpaque(true);
        Panel2.add(menuBtn32, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 300, 40));

        menuBtn34.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn34.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/calendar-24x24.png"))); // NOI18N
        menuBtn34.setText("START DATE");
        menuBtn34.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn34.setOpaque(true);
        Panel2.add(menuBtn34, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 300, 40));

        menuBtn33.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/officer-24x24.png"))); // NOI18N
        menuBtn33.setText("SECOND MARKER");
        menuBtn33.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn33.setOpaque(true);
        Panel2.add(menuBtn33, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 130, 300, 40));

        JSeparator39.setForeground(new java.awt.Color(1, 1, 1));
        Panel2.add(JSeparator39, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 530, 170, 10));

        jLabel42.setBackground(new java.awt.Color(254, 254, 254));
        jLabel42.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(1, 1, 1));
        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/resetDefault.png"))); // NOI18N
        jLabel42.setText("RESET TO DEFAULT");
        jLabel42.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel42.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel42.setOpaque(true);
        jLabel42.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel42MouseClicked(evt);
            }
        });
        Panel2.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 490, 170, 35));

        menuBtn47.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn47.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/action-24x24.png"))); // NOI18N
        menuBtn47.setText("ACTION :");
        menuBtn47.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn47.setOpaque(true);
        Panel2.add(menuBtn47, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 490, 90, 40));

        ModuleTabbedPanel.addTab("Manage Supervisor & Second Marker", Panel2);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menuBtn40.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn40.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn40.setText("MODULE NAME");
        menuBtn40.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn40.setOpaque(true);
        jPanel1.add(menuBtn40, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 300, 40));

        menuBtn31.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/action-24x24.png"))); // NOI18N
        menuBtn31.setText("ACTION :");
        menuBtn31.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn31.setOpaque(true);
        jPanel1.add(menuBtn31, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 430, 90, 40));

        menuBtn42.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn42.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/action-24x24.png"))); // NOI18N
        menuBtn42.setText("ACTION :");
        menuBtn42.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn42.setOpaque(true);
        jPanel1.add(menuBtn42, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 510, 90, 40));

        atModuleId.setEditable(false);
        atModuleId.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        atModuleId.setBackground(new java.awt.Color(255, 255, 255));
        atModuleId.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        atModuleId.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        atModuleId.setForeground(new java.awt.Color(1, 1, 1));
        atModuleId.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                atModuleIdMouseClicked(evt);
            }
        });
        atModuleId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                atModuleIdActionPerformed(evt);
            }
        });
        jPanel1.add(atModuleId, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 300, 35));

        jLabel41.setBackground(new java.awt.Color(254, 254, 254));
        jLabel41.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(1, 1, 1));
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/delete-red-24x24.png"))); // NOI18N
        jLabel41.setText("CANCEL");
        jLabel41.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel41.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel41.setOpaque(true);
        jLabel41.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel41MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 510, 170, 35));

        atModuleName.setEditable(false);
        atModuleName.setBackground(new java.awt.Color(255, 255, 255));
        atModuleName.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        atModuleName.setForeground(new java.awt.Color(1, 1, 1));
        atModuleName.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        atModuleName.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        atModuleName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                atModuleNameMouseClicked(evt);
            }
        });
        atModuleName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                atModuleNameActionPerformed(evt);
            }
        });
        jPanel1.add(atModuleName, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 300, 35));

        JSeparator34.setForeground(new java.awt.Color(1, 1, 1));
        jPanel1.add(JSeparator34, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 470, 170, 10));

        menuBtn44.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn44.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn44.setText("MODULE ID");
        menuBtn44.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn44.setOpaque(true);
        jPanel1.add(menuBtn44, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 300, 40));

        JSeparator38.setForeground(new java.awt.Color(1, 1, 1));
        jPanel1.add(JSeparator38, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 550, 170, 10));

        atEndDate.setEditable(false);
        atEndDate.setBackground(new java.awt.Color(255, 255, 255));
        atEndDate.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        atEndDate.setForeground(new java.awt.Color(1, 1, 1));
        atEndDate.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        atEndDate.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        atEndDate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                atEndDateMouseClicked(evt);
            }
        });
        jPanel1.add(atEndDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, 300, 35));

        menuBtn45.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn45.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/calendar-24x24.png"))); // NOI18N
        menuBtn45.setText("START DATE");
        menuBtn45.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn45.setOpaque(true);
        jPanel1.add(menuBtn45, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 300, 40));

        menuBtn39.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn39.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/calendar-24x24.png"))); // NOI18N
        menuBtn39.setText("END DATE");
        menuBtn39.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn39.setOpaque(true);
        jPanel1.add(menuBtn39, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, 300, 40));

        menuBtn41.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn41.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn41.setText("Assessment Type");
        menuBtn41.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn41.setOpaque(true);
        jPanel1.add(menuBtn41, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 50, 300, 40));

        assessmentTypeComboBox.setBackground(new java.awt.Color(254, 254, 254));
        assessmentTypeComboBox.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 12)); // NOI18N
        assessmentTypeComboBox.setToolTipText("d");
        assessmentTypeComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        assessmentTypeComboBox.setFocusable(false);
        assessmentTypeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assessmentTypeComboBoxActionPerformed(evt);
            }
        });
        jPanel1.add(assessmentTypeComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 90, 300, 35));

        atStartDate.setEditable(false);
        atStartDate.setBackground(new java.awt.Color(255, 255, 255));
        atStartDate.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        atStartDate.setForeground(new java.awt.Color(1, 1, 1));
        atStartDate.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        atStartDate.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        atStartDate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                atStartDateMouseClicked(evt);
            }
        });
        atStartDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                atStartDateActionPerformed(evt);
            }
        });
        jPanel1.add(atStartDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, 300, 35));

        menuBtn43.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn43.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn43.setText("Manage Assessment Type");
        menuBtn43.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn43.setOpaque(true);
        jPanel1.add(menuBtn43, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 500, 40));

        atSaveButton.setBackground(new java.awt.Color(254, 254, 254));
        atSaveButton.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        atSaveButton.setForeground(new java.awt.Color(1, 1, 1));
        atSaveButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        atSaveButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/add-green-24x24.png"))); // NOI18N
        atSaveButton.setText("SAVE");
        atSaveButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        atSaveButton.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        atSaveButton.setOpaque(true);
        atSaveButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                atSaveButtonMouseClicked(evt);
            }
        });
        jPanel1.add(atSaveButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 430, 170, 35));

        menuBtn48.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn48.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn48.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/action-24x24.png"))); // NOI18N
        menuBtn48.setText("ACTION :");
        menuBtn48.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn48.setOpaque(true);
        jPanel1.add(menuBtn48, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 490, 90, 40));

        jLabel43.setBackground(new java.awt.Color(254, 254, 254));
        jLabel43.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(1, 1, 1));
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/resetDefault.png"))); // NOI18N
        jLabel43.setText("RESET TO DEFAULT");
        jLabel43.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel43.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel43.setOpaque(true);
        jLabel43.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel43MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 490, 170, 35));

        JSeparator40.setForeground(new java.awt.Color(1, 1, 1));
        jPanel1.add(JSeparator40, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 530, 170, 10));

        ModuleTabbedPanel.addTab("Manage Assessment Type", jPanel1);

        Panel3.setPreferredSize(new java.awt.Dimension(1050, 570));
        Panel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        JSeparator35.setForeground(new java.awt.Color(1, 1, 1));
        Panel3.add(JSeparator35, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 470, 170, 10));

        menuBtn46.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn46.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/calendar-24x24.png"))); // NOI18N
        menuBtn46.setText("END DATE");
        menuBtn46.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn46.setOpaque(true);
        Panel3.add(menuBtn46, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, 300, 40));

        menuBtn49.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn49.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/action-24x24.png"))); // NOI18N
        menuBtn49.setText("ACTION :");
        menuBtn49.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn49.setOpaque(true);
        Panel3.add(menuBtn49, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 430, 90, 40));

        menuBtn50.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn50.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn50.setText("MODULE NAME");
        menuBtn50.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn50.setOpaque(true);
        Panel3.add(menuBtn50, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 300, 40));

        mdSaveButton.setBackground(new java.awt.Color(254, 254, 254));
        mdSaveButton.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        mdSaveButton.setForeground(new java.awt.Color(1, 1, 1));
        mdSaveButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mdSaveButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/add-green-24x24.png"))); // NOI18N
        mdSaveButton.setText("SAVE ");
        mdSaveButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mdSaveButton.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        mdSaveButton.setOpaque(true);
        mdSaveButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mdSaveButtonMouseClicked(evt);
            }
        });
        Panel3.add(mdSaveButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 430, 170, 35));

        mdModuleName.setEditable(false);
        mdModuleName.setBackground(new java.awt.Color(255, 255, 255));
        mdModuleName.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        mdModuleName.setForeground(new java.awt.Color(1, 1, 1));
        mdModuleName.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        mdModuleName.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        mdModuleName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mdModuleNameMouseClicked(evt);
            }
        });
        mdModuleName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mdModuleNameActionPerformed(evt);
            }
        });
        Panel3.add(mdModuleName, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 300, 35));

        mdModuleId.setEditable(false);
        mdModuleId.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        mdModuleId.setBackground(new java.awt.Color(255, 255, 255));
        mdModuleId.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        mdModuleId.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        mdModuleId.setForeground(new java.awt.Color(1, 1, 1));
        mdModuleId.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mdModuleIdMouseClicked(evt);
            }
        });
        mdModuleId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mdModuleIdActionPerformed(evt);
            }
        });
        Panel3.add(mdModuleId, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 300, 35));

        JSeparator41.setForeground(new java.awt.Color(1, 1, 1));
        Panel3.add(JSeparator41, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 550, 170, 10));

        jLabel44.setBackground(new java.awt.Color(254, 254, 254));
        jLabel44.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(1, 1, 1));
        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/delete-red-24x24.png"))); // NOI18N
        jLabel44.setText("CANCEL");
        jLabel44.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel44.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel44.setOpaque(true);
        jLabel44.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel44MouseClicked(evt);
            }
        });
        Panel3.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 510, 170, 35));

        menuBtn52.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn52.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/action-24x24.png"))); // NOI18N
        menuBtn52.setText("ACTION :");
        menuBtn52.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn52.setOpaque(true);
        Panel3.add(menuBtn52, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 510, 90, 40));

        menuBtn53.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn53.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn53.setText("Manage Supervisor & Second Marker");
        menuBtn53.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn53.setOpaque(true);
        Panel3.add(menuBtn53, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 500, 40));

        menuBtn54.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn54.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn54.setText("MODULE ID");
        menuBtn54.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn54.setOpaque(true);
        Panel3.add(menuBtn54, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 300, 40));

        menuBtn55.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn55.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn55.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/calendar-24x24.png"))); // NOI18N
        menuBtn55.setText("START DATE");
        menuBtn55.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn55.setOpaque(true);
        Panel3.add(menuBtn55, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 300, 40));

        JSeparator42.setForeground(new java.awt.Color(1, 1, 1));
        Panel3.add(JSeparator42, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 530, 170, 10));

        jLabel45.setBackground(new java.awt.Color(254, 254, 254));
        jLabel45.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(1, 1, 1));
        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/resetDefault.png"))); // NOI18N
        jLabel45.setText("RESET TO DEFAULT");
        jLabel45.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel45.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel45.setOpaque(true);
        jLabel45.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel45MouseClicked(evt);
            }
        });
        Panel3.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 490, 170, 35));

        menuBtn57.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn57.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn57.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/action-24x24.png"))); // NOI18N
        menuBtn57.setText("ACTION :");
        menuBtn57.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn57.setOpaque(true);
        Panel3.add(menuBtn57, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 490, 90, 40));

        mdEndDatePicker.getComponentDateTextField().setEditable(false);
        Panel3.add(mdEndDatePicker, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 300, 40));

        mdStartDatePicker.getComponentDateTextField().setEditable(false);
        Panel3.add(mdStartDatePicker, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, 300, 40));

        ModuleTabbedPanel.addTab("Manage Module Date", Panel3);

        getContentPane().add(ModuleTabbedPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 660));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void manageSupervisorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageSupervisorMouseClicked
//      add code to get row detail (Module Id)
        int selectedRow = jTableModuleDetails.getSelectedRow();
        if (selectedRow != -1) {
            moduleId = Integer.parseInt(jTableModuleDetails.getValueAt(selectedRow, 0).toString());
            ModuleTabbedPanel.setSelectedIndex(1);
            autofillModuleSupervisor(moduleId);
            autofillAssessment(moduleId);
            autofillStartEndDate(moduleId);
        } else {
            Dialog.ErrorDialog(MessageConstant.ERROR_EMPTY_MODULE);
        }
    }//GEN-LAST:event_manageSupervisorMouseClicked

    private void moduleStatusComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moduleStatusComboBoxActionPerformed
        refreshTableByFilter(moduleStatusComboBox.getSelectedIndex());
    }//GEN-LAST:event_moduleStatusComboBoxActionPerformed

    private void JField12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField12MouseClicked
        if (JField12.getText().equals("Enter Keywords To Search")) {
            JField12.setText("");
        }   
    }//GEN-LAST:event_JField12MouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        DefaultTableModel dtm = (DefaultTableModel)jTableModuleDetails.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dtm);
        jTableModuleDetails.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(JField12.getText().trim()));
    }//GEN-LAST:event_jLabel11MouseClicked

    private void spModuleIdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_spModuleIdMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_spModuleIdMouseClicked

    private void jLabel40MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel40MouseClicked
//  cancel button module page
    refresh();
    }//GEN-LAST:event_jLabel40MouseClicked

    private void spModuleSMComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spModuleSMComboBoxActionPerformed
//        refreshDetails(consultationComboBox2.getSelectedItem());
    }//GEN-LAST:event_spModuleSMComboBoxActionPerformed

    private void spStartDateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_spStartDateMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_spStartDateMouseClicked

    private void spSaveButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_spSaveButtonMouseClicked
       // Save Module Supervisor and Second Marker button
        saveDataChanges();
    }//GEN-LAST:event_spSaveButtonMouseClicked

    private void manageAssessmentTypeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageAssessmentTypeMouseClicked
        int selectedRow = jTableModuleDetails.getSelectedRow();
        if (selectedRow != -1) {
            moduleId = Integer.parseInt(jTableModuleDetails.getValueAt(selectedRow, 0).toString());
            ModuleTabbedPanel.setSelectedIndex(2);
            autofillModuleSupervisor(moduleId);
            autofillAssessment(moduleId);
            autofillStartEndDate(moduleId);
        } else {
            Dialog.ErrorDialog(MessageConstant.ERROR_EMPTY_MODULE);
        }
    }//GEN-LAST:event_manageAssessmentTypeMouseClicked

    private void menuBtn12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuBtn12MouseClicked
//        consultationComboBox.setSelectedIndex(1);
    }//GEN-LAST:event_menuBtn12MouseClicked

    private void menuBtn13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuBtn13MouseClicked
//        consultationComboBox.setSelectedIndex(2);
    }//GEN-LAST:event_menuBtn13MouseClicked

    private void spEndDateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_spEndDateMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_spEndDateMouseClicked

    private void spStartDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spStartDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_spStartDateActionPerformed

    private void spModuleNameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_spModuleNameMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_spModuleNameMouseClicked

    private void spModuleIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spModuleIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_spModuleIdActionPerformed

    private void spModuleSPComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spModuleSPComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_spModuleSPComboBoxActionPerformed

    private void spModuleNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spModuleNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_spModuleNameActionPerformed

    private void assessmentTypeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assessmentTypeComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_assessmentTypeComboBoxActionPerformed

    private void atModuleNameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_atModuleNameMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_atModuleNameMouseClicked

    private void atModuleNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_atModuleNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_atModuleNameActionPerformed

    private void atModuleIdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_atModuleIdMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_atModuleIdMouseClicked

    private void atModuleIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_atModuleIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_atModuleIdActionPerformed

    private void atEndDateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_atEndDateMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_atEndDateMouseClicked

    private void jLabel41MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel41MouseClicked
//        cancel button
        refresh();
    }//GEN-LAST:event_jLabel41MouseClicked

    private void atStartDateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_atStartDateMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_atStartDateMouseClicked

    private void atStartDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_atStartDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_atStartDateActionPerformed

    private void atSaveButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_atSaveButtonMouseClicked
        // Save Assessment Type button
        saveAssessmentType();
    }//GEN-LAST:event_atSaveButtonMouseClicked

    private void ModuleTabbedPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ModuleTabbedPanelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_ModuleTabbedPanelMouseClicked

    private void jLabel42MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel42MouseClicked
        // TODO add your handling code here:
        if(moduleId != null){
            autofillModuleSupervisor(moduleId);
            Dialog.SuccessDialog(MessageConstant.SUCCESS_RESET_FIELD);
        } else{
            Dialog.ErrorDialog(MessageConstant.ERROR_EMPTY_MODULE);
        }
    }//GEN-LAST:event_jLabel42MouseClicked

    private void jLabel43MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel43MouseClicked
        // TODO add your handling code here:
        if(moduleId != null){
            autofillAssessment(moduleId);
            Dialog.SuccessDialog(MessageConstant.SUCCESS_RESET_FIELD);
        } else{
            Dialog.ErrorDialog(MessageConstant.ERROR_EMPTY_MODULE);
        }
    }//GEN-LAST:event_jLabel43MouseClicked

    private void mdSaveButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mdSaveButtonMouseClicked
        // TODO add your handling code here:
        saveModuleDate();
    }//GEN-LAST:event_mdSaveButtonMouseClicked

    private void mdModuleNameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mdModuleNameMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_mdModuleNameMouseClicked

    private void mdModuleNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mdModuleNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mdModuleNameActionPerformed

    private void mdModuleIdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mdModuleIdMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_mdModuleIdMouseClicked

    private void mdModuleIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mdModuleIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mdModuleIdActionPerformed

    private void jLabel44MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel44MouseClicked
        refresh();
    }//GEN-LAST:event_jLabel44MouseClicked

    private void jLabel45MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel45MouseClicked
        if(moduleId != null){
            autofillStartEndDate(moduleId);
            Dialog.SuccessDialog(MessageConstant.SUCCESS_RESET_FIELD);
        } else{
            Dialog.ErrorDialog(MessageConstant.ERROR_EMPTY_MODULE);
        }
    }//GEN-LAST:event_jLabel45MouseClicked

    private void moduleStatusComboBoxPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_moduleStatusComboBoxPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_moduleStatusComboBoxPropertyChange

    private void manageModuleDateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageModuleDateMouseClicked
        int selectedRow = jTableModuleDetails.getSelectedRow();
        if (selectedRow != -1) {
            moduleId = Integer.parseInt(jTableModuleDetails.getValueAt(selectedRow, 0).toString());
            autofillModuleSupervisor(moduleId);
            autofillAssessment(moduleId);
            autofillStartEndDate(moduleId);
            ModuleTabbedPanel.setSelectedIndex(3);
        } else {
            Dialog.ErrorDialog(MessageConstant.ERROR_EMPTY_MODULE);
        }
    }//GEN-LAST:event_manageModuleDateMouseClicked

    private void moduleStatusComboBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_moduleStatusComboBoxMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_moduleStatusComboBoxMouseClicked

    private void spSaveButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_spSaveButtonMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_spSaveButtonMouseEntered


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField JField12;
    private javax.swing.JSeparator JSeparator33;
    private javax.swing.JSeparator JSeparator34;
    private javax.swing.JSeparator JSeparator35;
    private javax.swing.JSeparator JSeparator37;
    private javax.swing.JSeparator JSeparator38;
    private javax.swing.JSeparator JSeparator39;
    private javax.swing.JSeparator JSeparator40;
    private javax.swing.JSeparator JSeparator41;
    private javax.swing.JSeparator JSeparator42;
    private javax.swing.JTabbedPane ModuleTabbedPanel;
    private javax.swing.JPanel Panel1;
    private javax.swing.JPanel Panel2;
    private javax.swing.JPanel Panel3;
    private static javax.swing.JComboBox<String> assessmentTypeComboBox;
    private javax.swing.JTextField atEndDate;
    private javax.swing.JTextField atModuleId;
    private javax.swing.JTextField atModuleName;
    private javax.swing.JLabel atSaveButton;
    private javax.swing.JTextField atStartDate;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTableModuleDetails;
    private javax.swing.JLabel manageAssessmentType;
    private javax.swing.JLabel manageModuleDate;
    private javax.swing.JLabel manageSupervisor;
    private com.github.lgooddatepicker.components.DatePicker mdEndDatePicker;
    private javax.swing.JTextField mdModuleId;
    private javax.swing.JTextField mdModuleName;
    private javax.swing.JLabel mdSaveButton;
    private com.github.lgooddatepicker.components.DatePicker mdStartDatePicker;
    private static javax.swing.JLabel menuBtn12;
    private static javax.swing.JLabel menuBtn13;
    private javax.swing.JLabel menuBtn14;
    private javax.swing.JLabel menuBtn15;
    private javax.swing.JLabel menuBtn16;
    private javax.swing.JLabel menuBtn29;
    private javax.swing.JLabel menuBtn3;
    private javax.swing.JLabel menuBtn30;
    private javax.swing.JLabel menuBtn31;
    private javax.swing.JLabel menuBtn32;
    private javax.swing.JLabel menuBtn33;
    private javax.swing.JLabel menuBtn34;
    private javax.swing.JLabel menuBtn35;
    private javax.swing.JLabel menuBtn36;
    private javax.swing.JLabel menuBtn37;
    private javax.swing.JLabel menuBtn38;
    private javax.swing.JLabel menuBtn39;
    private javax.swing.JLabel menuBtn4;
    private javax.swing.JLabel menuBtn40;
    private javax.swing.JLabel menuBtn41;
    private javax.swing.JLabel menuBtn42;
    private javax.swing.JLabel menuBtn43;
    private javax.swing.JLabel menuBtn44;
    private javax.swing.JLabel menuBtn45;
    private javax.swing.JLabel menuBtn46;
    private javax.swing.JLabel menuBtn47;
    private javax.swing.JLabel menuBtn48;
    private javax.swing.JLabel menuBtn49;
    private javax.swing.JLabel menuBtn50;
    private javax.swing.JLabel menuBtn52;
    private javax.swing.JLabel menuBtn53;
    private javax.swing.JLabel menuBtn54;
    private javax.swing.JLabel menuBtn55;
    private javax.swing.JLabel menuBtn57;
    private static javax.swing.JComboBox<String> moduleStatusComboBox;
    private javax.swing.JTextField spEndDate;
    private javax.swing.JTextField spModuleId;
    private javax.swing.JTextField spModuleName;
    private static javax.swing.JComboBox<String> spModuleSMComboBox;
    private static javax.swing.JComboBox<String> spModuleSPComboBox;
    private javax.swing.JLabel spSaveButton;
    private javax.swing.JTextField spStartDate;
    // End of variables declaration//GEN-END:variables


}
