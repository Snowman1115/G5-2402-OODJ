package com.project.ui.project_manager;

import com.project.ui.student.*;
import com.project.common.constants.MessageConstant;
import com.project.common.constants.ReportStatus;
import com.project.common.utils.Dialog;
import com.project.common.utils.JsonHandler;
import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

import com.project.controller.ConsultationController;
import com.project.controller.ProjectModuleController;
import com.project.controller.SubmissionController;
import com.project.controller.UserAccountController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
 * @author Jin Xun
 */
public class ManagerViewReport extends javax.swing.JInternalFrame {

     private Integer reportId;
     private Map<String, String> currentModuleDetails;
     private String firstMarkerName, secondMarkerName;
     private JsonHandler lecturerLists;
    /**
     * Creates new form StudentAssignmentGui
     */
    public ManagerViewReport() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI)this.getUI();
        ui.setNorthPane(null);
        refresh();
    }

    private void refresh() {
        refreshTable();
        fillspModuleComboBox();
    }
    
    private void refreshTable() {
        DefaultTableModel dtm =  (DefaultTableModel)jTableReportDetails.getModel();
        dtm.setRowCount(0);
        List<Map<String, Object>> reportLists = ProjectModuleController.getAllReport();
        for (Map<String, Object> list : reportLists) {
                System.out.println(list); // Debugging line

                String submissionId = list.get("id") != null ? list.get("id").toString() : "N/A";
                String moduleCode = list.get("moduleCode") != null ? list.get("moduleCode").toString() : "N/A";
                String studentId = list.get("studentId") != null ? list.get("studentId").toString() : "N/A";
                String studentName = list.get("studentName") != null ? list.get("studentName").toString() : "N/A";
                
                // Convert ReportStatus to String
                ReportStatus reportStatusObj = (ReportStatus) list.get("reportStatus");
                String reportStatus = reportStatusObj != null ? reportStatusObj.toString() : "N/A";
                
                String reportType = list.get("reportType") != null ? list.get("reportType").toString() : "N/A";
                String comment = list.get("comment") != null ? list.get("comment").toString() : "N/A";

                String[] data = {submissionId, moduleCode, studentId, studentName, reportStatus, reportType, comment};
                dtm.addRow(data);
        }
    }
    
//    private String getSelectedModuleId() {
//        int selectedRow = jTableModuleDetails.getSelectedRow();
//        if (selectedRow != -1) {
//            return (String) jTableModuleDetails.getValueAt(selectedRow, 0); // Assuming the module ID is in the first column
//        } else {
//            JOptionPane.showMessageDialog(null, "Please select a row first.", "Error", JOptionPane.ERROR_MESSAGE);
//            return null;
//    }
//}
    
        private void autofillReportDetails(Integer reportId) {
            // Get module detail by id
            List<Map<String, String>> reportDetails = ProjectModuleController.getReportById(reportId);
//          Check if module List is empty 
             Map<String, String> mLists = reportDetails.isEmpty() ? null : reportDetails.get(0);
             System.out.println(mLists);
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
                    
                    if (firstMarker != null) {
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
                    if (secondMarker != null) {
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
        
        private void fillspModuleComboBox(){
            // Create list to store all lectures name
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
        
        private void saveDataChanges(){
            String saveModuleId = spModuleId.getText();
            String saveSelectedSP = (String) spModuleSPComboBox.getSelectedItem();
            String saveSelectedSM = (String) spModuleSMComboBox.getSelectedItem();
            //check if module exist
            if(currentModuleDetails != null){
                if (saveSelectedSP != firstMarkerName || saveSelectedSM != secondMarkerName){
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
                        System.out.println("Success");
                    }
                } else{
                    Dialog.ErrorDialog(MessageConstant.CONDITION_NO_DATA_CHANGES);
                }
            }  else {
                Dialog.ErrorDialog(MessageConstant.ERROR_EMPTY_MODULE);
            }
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

        MainTabbedPanel = new javax.swing.JTabbedPane();
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
        consultationComboBox = new javax.swing.JComboBox<>();
        menuBtn15 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        manageSupervisor = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        menuBtn16 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableReportDetails = new javax.swing.JTable();
        Panel2 = new javax.swing.JPanel();
        JSeparator33 = new javax.swing.JSeparator();
        menuBtn35 = new javax.swing.JLabel();
        menuBtn29 = new javax.swing.JLabel();
        menuBtn38 = new javax.swing.JLabel();
        menuBtn36 = new javax.swing.JLabel();
        spModuleSPComboBox = new javax.swing.JComboBox<>();
        jLabel36 = new javax.swing.JLabel();
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
        jPanel1 = new javax.swing.JPanel();
        menuBtn40 = new javax.swing.JLabel();
        menuBtn31 = new javax.swing.JLabel();
        spModuleSMComboBox1 = new javax.swing.JComboBox<>();
        menuBtn42 = new javax.swing.JLabel();
        spModuleId1 = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        spModuleName1 = new javax.swing.JTextField();
        JSeparator34 = new javax.swing.JSeparator();
        menuBtn44 = new javax.swing.JLabel();
        JSeparator38 = new javax.swing.JSeparator();
        spEndDate1 = new javax.swing.JTextField();
        menuBtn45 = new javax.swing.JLabel();
        menuBtn39 = new javax.swing.JLabel();
        menuBtn41 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        menuBtn46 = new javax.swing.JLabel();
        spModuleSPComboBox1 = new javax.swing.JComboBox<>();
        spStartDate1 = new javax.swing.JTextField();
        menuBtn43 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableModuleDetails1 = new javax.swing.JTable();
        jLabel38 = new javax.swing.JLabel();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        MainTabbedPanel.setBackground(new java.awt.Color(230, 230, 230));
        MainTabbedPanel.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        MainTabbedPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MainTabbedPanelMouseClicked(evt);
            }
        });

        Panel1.setPreferredSize(new java.awt.Dimension(1050, 570));
        Panel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel10.setBackground(new java.awt.Color(254, 254, 254));

        menuBtn4.setBackground(new java.awt.Color(250, 250, 250));
        menuBtn4.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/success-24x24.png"))); // NOI18N
        menuBtn4.setText("COMPLETED");
        menuBtn4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn4.setOpaque(true);

        menuBtn13.setBackground(new java.awt.Color(254, 254, 254));
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

        jPanel6.setBackground(new java.awt.Color(254, 254, 254));

        menuBtn3.setBackground(new java.awt.Color(250, 250, 250));
        menuBtn3.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/status-24x24.png"))); // NOI18N
        menuBtn3.setText("UPCOMING");
        menuBtn3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn3.setOpaque(true);

        menuBtn12.setBackground(new java.awt.Color(254, 254, 254));
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
            .addComponent(menuBtn12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(menuBtn3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(menuBtn12, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        Panel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 240, 90));

        menuBtn14.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn14.setText("Student Report");
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
        JField12.setForeground(new java.awt.Color(1, 1, 1));
        JField12.setText("Enter Keywords To Search");
        JField12.setBorder(null);
        JField12.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField12MouseClicked(evt);
            }
        });
        Panel1.add(JField12, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 180, 290, 35));

        consultationComboBox.setBackground(new java.awt.Color(254, 254, 254));
        consultationComboBox.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 12)); // NOI18N
        consultationComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Upcoming", "Completed" }));
        consultationComboBox.setToolTipText("d");
        consultationComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        consultationComboBox.setFocusable(false);
        consultationComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consultationComboBoxActionPerformed(evt);
            }
        });
        Panel1.add(consultationComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 330, 35));

        menuBtn15.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn15.setText("ACTION");
        menuBtn15.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn15.setOpaque(true);
        Panel1.add(menuBtn15, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 10, 420, 40));

        jPanel7.setBackground(new java.awt.Color(254, 254, 254));

        manageSupervisor.setBackground(new java.awt.Color(105, 105, 105));
        manageSupervisor.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        manageSupervisor.setForeground(new java.awt.Color(1, 1, 1));
        manageSupervisor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/user-24x24.png"))); // NOI18N
        manageSupervisor.setText("View Report Details");
        manageSupervisor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        manageSupervisor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                manageSupervisorMouseClicked(evt);
            }
        });

        jLabel21.setBackground(new java.awt.Color(105, 105, 105));
        jLabel21.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(1, 1, 1));
        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/user-24x24.png"))); // NOI18N
        jLabel21.setText("Export Table");
        jLabel21.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel21MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(manageSupervisor, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(127, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(manageSupervisor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel21)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        Panel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 50, 500, 90));

        menuBtn16.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn16.setText("VIEW STUDENT REPORT");
        menuBtn16.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn16.setOpaque(true);
        Panel1.add(menuBtn16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 670, 40));

        jTableReportDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Report Id", "Module", "Student Id", "Student Name", "Report Status", "Report Type", "Comment"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableReportDetails.setFocusable(false);
        jTableReportDetails.setRequestFocusEnabled(false);
        jTableReportDetails.getTableHeader().setResizingAllowed(false);
        jTableReportDetails.getTableHeader().setReorderingAllowed(false);
        jTableReportDetails.setUpdateSelectionOnSort(false);
        jTableReportDetails.setVerifyInputWhenFocusTarget(false);
        jScrollPane3.setViewportView(jTableReportDetails);
        if (jTableReportDetails.getColumnModel().getColumnCount() > 0) {
            jTableReportDetails.getColumnModel().getColumn(0).setResizable(false);
            jTableReportDetails.getColumnModel().getColumn(1).setResizable(false);
            jTableReportDetails.getColumnModel().getColumn(2).setResizable(false);
            jTableReportDetails.getColumnModel().getColumn(3).setResizable(false);
            jTableReportDetails.getColumnModel().getColumn(4).setResizable(false);
            jTableReportDetails.getColumnModel().getColumn(5).setResizable(false);
            jTableReportDetails.getColumnModel().getColumn(6).setResizable(false);
        }

        Panel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 1020, 360));

        MainTabbedPanel.addTab("View Report", Panel1);

        Panel2.setPreferredSize(new java.awt.Dimension(1050, 570));
        Panel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        JSeparator33.setForeground(new java.awt.Color(1, 1, 1));
        Panel2.add(JSeparator33, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 470, 170, 10));

        menuBtn35.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn35.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/calendar-24x24.png"))); // NOI18N
        menuBtn35.setText("REPORT STATUS");
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
        menuBtn38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/officer-24x24.png"))); // NOI18N
        menuBtn38.setText("STUDENT ID");
        menuBtn38.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn38.setOpaque(true);
        Panel2.add(menuBtn38, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 300, 40));

        menuBtn36.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn36.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/officer-24x24.png"))); // NOI18N
        menuBtn36.setText("REPORT TYPE");
        menuBtn36.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn36.setOpaque(true);
        Panel2.add(menuBtn36, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 50, 300, 40));

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
        Panel2.add(spModuleSPComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 90, 300, 35));

        jLabel36.setBackground(new java.awt.Color(254, 254, 254));
        jLabel36.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(1, 1, 1));
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/add-green-24x24.png"))); // NOI18N
        jLabel36.setText("Export File?");
        jLabel36.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel36.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel36.setOpaque(true);
        jLabel36.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel36MouseClicked(evt);
            }
        });
        Panel2.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 430, 170, 35));

        spModuleName.setEditable(false);
        spModuleName.setBackground(new java.awt.Color(255, 255, 255));
        spModuleName.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        spModuleName.setForeground(new java.awt.Color(1, 1, 1));
        spModuleName.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        spModuleName.setDisabledTextColor(new java.awt.Color(1, 1, 1));
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
        spEndDate.setBackground(new java.awt.Color(255, 255, 255));
        spEndDate.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        spEndDate.setForeground(new java.awt.Color(1, 1, 1));
        spEndDate.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        spEndDate.setDisabledTextColor(new java.awt.Color(1, 1, 1));
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
        jLabel40.setText("Back");
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
        Panel2.add(spModuleSMComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 170, 300, 35));

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
        spStartDate.setBackground(new java.awt.Color(255, 255, 255));
        spStartDate.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        spStartDate.setForeground(new java.awt.Color(1, 1, 1));
        spStartDate.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        spStartDate.setDisabledTextColor(new java.awt.Color(1, 1, 1));
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
        menuBtn32.setText("MODULE");
        menuBtn32.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn32.setOpaque(true);
        Panel2.add(menuBtn32, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 300, 40));

        menuBtn34.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn34.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/calendar-24x24.png"))); // NOI18N
        menuBtn34.setText("STUDENT NAME");
        menuBtn34.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn34.setOpaque(true);
        Panel2.add(menuBtn34, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 300, 40));

        menuBtn33.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/officer-24x24.png"))); // NOI18N
        menuBtn33.setText("COMMENT");
        menuBtn33.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn33.setOpaque(true);
        Panel2.add(menuBtn33, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 130, 300, 40));

        MainTabbedPanel.addTab("Report Details", Panel2);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menuBtn40.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn40.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/officer-24x24.png"))); // NOI18N
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

        spModuleSMComboBox1.setBackground(new java.awt.Color(254, 254, 254));
        spModuleSMComboBox1.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 12)); // NOI18N
        spModuleSMComboBox1.setToolTipText("d");
        spModuleSMComboBox1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        spModuleSMComboBox1.setFocusable(false);
        spModuleSMComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                spModuleSMComboBox1ActionPerformed(evt);
            }
        });
        jPanel1.add(spModuleSMComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 490, 300, 35));

        menuBtn42.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn42.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/action-24x24.png"))); // NOI18N
        menuBtn42.setText("ACTION :");
        menuBtn42.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn42.setOpaque(true);
        jPanel1.add(menuBtn42, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 510, 90, 40));

        spModuleId1.setEditable(false);
        spModuleId1.setBackground(new java.awt.Color(255, 255, 255));
        spModuleId1.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        spModuleId1.setForeground(new java.awt.Color(1, 1, 1));
        spModuleId1.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        spModuleId1.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        spModuleId1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                spModuleId1MouseClicked(evt);
            }
        });
        spModuleId1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                spModuleId1ActionPerformed(evt);
            }
        });
        jPanel1.add(spModuleId1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 300, 35));

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

        spModuleName1.setEditable(false);
        spModuleName1.setBackground(new java.awt.Color(255, 255, 255));
        spModuleName1.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        spModuleName1.setForeground(new java.awt.Color(1, 1, 1));
        spModuleName1.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        spModuleName1.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        spModuleName1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                spModuleName1MouseClicked(evt);
            }
        });
        spModuleName1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                spModuleName1ActionPerformed(evt);
            }
        });
        jPanel1.add(spModuleName1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 300, 35));

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

        spEndDate1.setEditable(false);
        spEndDate1.setBackground(new java.awt.Color(255, 255, 255));
        spEndDate1.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        spEndDate1.setForeground(new java.awt.Color(1, 1, 1));
        spEndDate1.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        spEndDate1.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        spEndDate1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                spEndDate1MouseClicked(evt);
            }
        });
        jPanel1.add(spEndDate1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, 300, 35));

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
        menuBtn41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/officer-24x24.png"))); // NOI18N
        menuBtn41.setText("STUDENT");
        menuBtn41.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn41.setOpaque(true);
        jPanel1.add(menuBtn41, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 50, 300, 40));

        jLabel37.setBackground(new java.awt.Color(254, 254, 254));
        jLabel37.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(1, 1, 1));
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/add-green-24x24.png"))); // NOI18N
        jLabel37.setText("ADD STUDENT");
        jLabel37.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel37.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel37.setOpaque(true);
        jLabel37.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel37MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 90, 170, 35));

        menuBtn46.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn46.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/officer-24x24.png"))); // NOI18N
        menuBtn46.setText("INTAKE");
        menuBtn46.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn46.setOpaque(true);
        jPanel1.add(menuBtn46, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 450, 300, 40));

        spModuleSPComboBox1.setBackground(new java.awt.Color(254, 254, 254));
        spModuleSPComboBox1.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 12)); // NOI18N
        spModuleSPComboBox1.setToolTipText("d");
        spModuleSPComboBox1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        spModuleSPComboBox1.setFocusable(false);
        spModuleSPComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                spModuleSPComboBox1ActionPerformed(evt);
            }
        });
        jPanel1.add(spModuleSPComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 90, 300, 35));

        spStartDate1.setEditable(false);
        spStartDate1.setBackground(new java.awt.Color(255, 255, 255));
        spStartDate1.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        spStartDate1.setForeground(new java.awt.Color(1, 1, 1));
        spStartDate1.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        spStartDate1.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        spStartDate1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                spStartDate1MouseClicked(evt);
            }
        });
        spStartDate1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                spStartDate1ActionPerformed(evt);
            }
        });
        jPanel1.add(spStartDate1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, 300, 35));

        menuBtn43.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn43.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn43.setText("Manage Student & Intake");
        menuBtn43.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn43.setOpaque(true);
        jPanel1.add(menuBtn43, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 500, 40));

        jTableModuleDetails1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Student Id", "Student Name", "Intake"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableModuleDetails1.setFocusable(false);
        jTableModuleDetails1.setRequestFocusEnabled(false);
        jTableModuleDetails1.getTableHeader().setResizingAllowed(false);
        jTableModuleDetails1.getTableHeader().setReorderingAllowed(false);
        jTableModuleDetails1.setUpdateSelectionOnSort(false);
        jTableModuleDetails1.setVerifyInputWhenFocusTarget(false);
        jScrollPane4.setViewportView(jTableModuleDetails1);
        if (jTableModuleDetails1.getColumnModel().getColumnCount() > 0) {
            jTableModuleDetails1.getColumnModel().getColumn(0).setResizable(false);
            jTableModuleDetails1.getColumnModel().getColumn(1).setResizable(false);
            jTableModuleDetails1.getColumnModel().getColumn(2).setResizable(false);
        }

        jPanel1.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 150, 610, 180));

        jLabel38.setBackground(new java.awt.Color(254, 254, 254));
        jLabel38.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(1, 1, 1));
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/add-green-24x24.png"))); // NOI18N
        jLabel38.setText("BOOK");
        jLabel38.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel38.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel38.setOpaque(true);
        jLabel38.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel38MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 430, 170, 35));

        MainTabbedPanel.addTab("Manage Student and Intake", jPanel1);

        getContentPane().add(MainTabbedPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 660));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void manageSupervisorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageSupervisorMouseClicked
//      add code to get row detail (Module Id)
        int selectedRow = jTableReportDetails.getSelectedRow();
        if (selectedRow != -1) {
            reportId = Integer.parseInt(jTableReportDetails.getValueAt(selectedRow, 0).toString());
            MainTabbedPanel.setSelectedIndex(1);
            autofillReportDetails(reportId);
        } else {
            Dialog.ErrorDialog(MessageConstant.ERROR_EMPTY_MODULE);
        }
    }//GEN-LAST:event_manageSupervisorMouseClicked

    private void consultationComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consultationComboBoxActionPerformed
//        refreshComboBox(consultationComboBox.getSelectedIndex());
    }//GEN-LAST:event_consultationComboBoxActionPerformed

    private void JField12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField12MouseClicked
//        if (JField12.getText().equals("Enter Keywords To Search")) {
//            JField12.setText("");
//        }
    }//GEN-LAST:event_JField12MouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
//        DefaultTableModel dtm = (DefaultTableModel)jTableModuleDetails.getModel();
//        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dtm);
//        jTableModuleDetails.setRowSorter(tr);
//        tr.setRowFilter(RowFilter.regexFilter(JField12.getText().trim()));
    }//GEN-LAST:event_jLabel11MouseClicked

    private void spModuleIdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_spModuleIdMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_spModuleIdMouseClicked

    private void jLabel40MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel40MouseClicked
//        if (consultationComboBox2.getSelectedItem().equals("There is no scheduled consultation.")) {
//            Dialog.ErrorDialog(MessageConstant.ERROR_SELECTION_EMPTY);
//            return;
//        }
//
//        if (ConsultationController.cancelBookedConsultationById(Integer.parseInt((String) consultationComboBox2.getSelectedItem()))) {
//            refresh();
//        }
        MainTabbedPanel.setSelectedIndex(0);
    }//GEN-LAST:event_jLabel40MouseClicked

    private void spModuleSMComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spModuleSMComboBoxActionPerformed
//        refreshDetails(consultationComboBox2.getSelectedItem());
    }//GEN-LAST:event_spModuleSMComboBoxActionPerformed

    private void spStartDateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_spStartDateMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_spStartDateMouseClicked

    private void jLabel36MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel36MouseClicked
      
      saveDataChanges();
    }//GEN-LAST:event_jLabel36MouseClicked

    private void jLabel21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel21MouseClicked
//        MainTabbedPanel.setSelectedIndex(1);
//        MainTabbedPanel1.setSelectedIndex(1);
    }//GEN-LAST:event_jLabel21MouseClicked

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

    private void spModuleSPComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spModuleSPComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_spModuleSPComboBox1ActionPerformed

    private void jLabel37MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel37MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel37MouseClicked

    private void spModuleName1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_spModuleName1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_spModuleName1MouseClicked

    private void spModuleName1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spModuleName1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_spModuleName1ActionPerformed

    private void spModuleId1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_spModuleId1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_spModuleId1MouseClicked

    private void spModuleId1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spModuleId1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_spModuleId1ActionPerformed

    private void spEndDate1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_spEndDate1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_spEndDate1MouseClicked

    private void jLabel41MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel41MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel41MouseClicked

    private void spModuleSMComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spModuleSMComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_spModuleSMComboBox1ActionPerformed

    private void spStartDate1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_spStartDate1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_spStartDate1MouseClicked

    private void spStartDate1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spStartDate1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_spStartDate1ActionPerformed

    private void jLabel38MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel38MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel38MouseClicked

    private void MainTabbedPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MainTabbedPanelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_MainTabbedPanelMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField JField12;
    private javax.swing.JSeparator JSeparator33;
    private javax.swing.JSeparator JSeparator34;
    private javax.swing.JSeparator JSeparator37;
    private javax.swing.JSeparator JSeparator38;
    private javax.swing.JTabbedPane MainTabbedPanel;
    private javax.swing.JPanel Panel1;
    private javax.swing.JPanel Panel2;
    private static javax.swing.JComboBox<String> consultationComboBox;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTableModuleDetails1;
    private javax.swing.JTable jTableReportDetails;
    private javax.swing.JLabel manageSupervisor;
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
    private javax.swing.JTextField spEndDate;
    private javax.swing.JTextField spEndDate1;
    private javax.swing.JTextField spModuleId;
    private javax.swing.JTextField spModuleId1;
    private javax.swing.JTextField spModuleName;
    private javax.swing.JTextField spModuleName1;
    private static javax.swing.JComboBox<String> spModuleSMComboBox;
    private static javax.swing.JComboBox<String> spModuleSMComboBox1;
    private static javax.swing.JComboBox<String> spModuleSPComboBox;
    private static javax.swing.JComboBox<String> spModuleSPComboBox1;
    private javax.swing.JTextField spStartDate;
    private javax.swing.JTextField spStartDate1;
    // End of variables declaration//GEN-END:variables


}
