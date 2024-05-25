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
    }
    
    private void refreshTable() {
        DefaultTableModel dtm =  (DefaultTableModel)jTableReportDetails.getModel();
        dtm.setRowCount(0);
        List<Map<String, Object>> reportLists = ProjectModuleController.getAllReport();
        for (Map<String, Object> list : reportLists) {
                System.out.println(list); // Debugging line

                String submissionId = list.get("submissionId") != null ? list.get("submissionId").toString() : "N/A";
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
//              if module list is not empty
                if (mLists != null) {
                    //Auto fill text field
                    rdReportId.setText(mLists.get("id"));
                    rdStudentId.setText(mLists.get("studentId"));
                    rdStudentName.setText(mLists.get("studentName"));
                    rdReportStatus.setText(mLists.get("reportStatus"));
                    rdReportType.setText(mLists.get("reportType"));
                    rdComment.setText(mLists.get("comment"));
                }
        }
        
       
        
       

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
        menuBtn35 = new javax.swing.JLabel();
        menuBtn38 = new javax.swing.JLabel();
        menuBtn36 = new javax.swing.JLabel();
        rdStudentId = new javax.swing.JTextField();
        rdReportId = new javax.swing.JTextField();
        rdReportType = new javax.swing.JTextField();
        JSeparator37 = new javax.swing.JSeparator();
        jLabel40 = new javax.swing.JLabel();
        menuBtn37 = new javax.swing.JLabel();
        menuBtn30 = new javax.swing.JLabel();
        rdStudentName = new javax.swing.JTextField();
        menuBtn32 = new javax.swing.JLabel();
        menuBtn34 = new javax.swing.JLabel();
        menuBtn33 = new javax.swing.JLabel();
        rdReportStatus = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        rdComment = new javax.swing.JTextArea();

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

        menuBtn35.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn35.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/view-24x24.png"))); // NOI18N
        menuBtn35.setText("REPORT STATUS");
        menuBtn35.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn35.setOpaque(true);
        Panel2.add(menuBtn35, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, 300, 40));

        menuBtn38.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn38.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/name-24x24.png"))); // NOI18N
        menuBtn38.setText("STUDENT ID");
        menuBtn38.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn38.setOpaque(true);
        Panel2.add(menuBtn38, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 300, 40));

        menuBtn36.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn36.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/view-24x24.png"))); // NOI18N
        menuBtn36.setText("REPORT TYPE");
        menuBtn36.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn36.setOpaque(true);
        Panel2.add(menuBtn36, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 40, 300, 40));

        rdStudentId.setEditable(false);
        rdStudentId.setBackground(new java.awt.Color(255, 255, 255));
        rdStudentId.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        rdStudentId.setForeground(new java.awt.Color(1, 1, 1));
        rdStudentId.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        rdStudentId.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        rdStudentId.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdStudentIdMouseClicked(evt);
            }
        });
        rdStudentId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdStudentIdActionPerformed(evt);
            }
        });
        Panel2.add(rdStudentId, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 300, 35));

        rdReportId.setEditable(false);
        rdReportId.setBackground(new java.awt.Color(255, 255, 255));
        rdReportId.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        rdReportId.setForeground(new java.awt.Color(1, 1, 1));
        rdReportId.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        rdReportId.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        rdReportId.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdReportIdMouseClicked(evt);
            }
        });
        rdReportId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdReportIdActionPerformed(evt);
            }
        });
        Panel2.add(rdReportId, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 300, 35));

        rdReportType.setEditable(false);
        rdReportType.setBackground(new java.awt.Color(255, 255, 255));
        rdReportType.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        rdReportType.setForeground(new java.awt.Color(1, 1, 1));
        rdReportType.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        rdReportType.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        rdReportType.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdReportTypeMouseClicked(evt);
            }
        });
        Panel2.add(rdReportType, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 80, 300, 35));

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

        rdStudentName.setEditable(false);
        rdStudentName.setBackground(new java.awt.Color(255, 255, 255));
        rdStudentName.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        rdStudentName.setForeground(new java.awt.Color(1, 1, 1));
        rdStudentName.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        rdStudentName.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        rdStudentName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdStudentNameMouseClicked(evt);
            }
        });
        rdStudentName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdStudentNameActionPerformed(evt);
            }
        });
        Panel2.add(rdStudentName, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, 300, 35));

        menuBtn32.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn32.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn32.setText("REPORT ID");
        menuBtn32.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn32.setOpaque(true);
        Panel2.add(menuBtn32, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 300, 40));

        menuBtn34.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn34.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/user-24x24.png"))); // NOI18N
        menuBtn34.setText("STUDENT NAME");
        menuBtn34.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn34.setOpaque(true);
        Panel2.add(menuBtn34, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 300, 40));

        menuBtn33.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/edit-24x24.png"))); // NOI18N
        menuBtn33.setText("COMMENT");
        menuBtn33.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn33.setOpaque(true);
        Panel2.add(menuBtn33, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 120, 300, 40));

        rdReportStatus.setEditable(false);
        rdReportStatus.setBackground(new java.awt.Color(255, 255, 255));
        rdReportStatus.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        rdReportStatus.setForeground(new java.awt.Color(1, 1, 1));
        rdReportStatus.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        rdReportStatus.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        rdReportStatus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdReportStatusMouseClicked(evt);
            }
        });
        Panel2.add(rdReportStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 300, 35));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        rdComment.setBackground(new java.awt.Color(255, 255, 255));
        rdComment.setColumns(20);
        rdComment.setForeground(new java.awt.Color(0, 0, 0));
        rdComment.setRows(5);
        jScrollPane1.setViewportView(rdComment);

        Panel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 170, 300, 120));

        MainTabbedPanel.addTab("Report Details", Panel2);

        getContentPane().add(MainTabbedPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 660));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void manageSupervisorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageSupervisorMouseClicked
//      add code to get row detail (Module Id)
        int selectedRow = jTableReportDetails.getSelectedRow();
        if (selectedRow != -1) {
            reportId = Integer.valueOf(jTableReportDetails.getValueAt(selectedRow, 0).toString());
            autofillReportDetails(reportId);
            MainTabbedPanel.setSelectedIndex(1);
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
        DefaultTableModel dtm = (DefaultTableModel)jTableReportDetails.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dtm);
        jTableReportDetails.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(JField12.getText().trim()));
    }//GEN-LAST:event_jLabel11MouseClicked

    private void rdReportIdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdReportIdMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_rdReportIdMouseClicked

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

    private void rdStudentNameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdStudentNameMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_rdStudentNameMouseClicked

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

    private void rdReportTypeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdReportTypeMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_rdReportTypeMouseClicked

    private void rdStudentNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdStudentNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdStudentNameActionPerformed

    private void rdStudentIdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdStudentIdMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_rdStudentIdMouseClicked

    private void rdReportIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdReportIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdReportIdActionPerformed

    private void rdStudentIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdStudentIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdStudentIdActionPerformed

    private void MainTabbedPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MainTabbedPanelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_MainTabbedPanelMouseClicked

    private void rdReportStatusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdReportStatusMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_rdReportStatusMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField JField12;
    private javax.swing.JSeparator JSeparator37;
    private javax.swing.JTabbedPane MainTabbedPanel;
    private javax.swing.JPanel Panel1;
    private javax.swing.JPanel Panel2;
    private static javax.swing.JComboBox<String> consultationComboBox;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTableReportDetails;
    private javax.swing.JLabel manageSupervisor;
    private static javax.swing.JLabel menuBtn12;
    private static javax.swing.JLabel menuBtn13;
    private javax.swing.JLabel menuBtn14;
    private javax.swing.JLabel menuBtn15;
    private javax.swing.JLabel menuBtn16;
    private javax.swing.JLabel menuBtn3;
    private javax.swing.JLabel menuBtn30;
    private javax.swing.JLabel menuBtn32;
    private javax.swing.JLabel menuBtn33;
    private javax.swing.JLabel menuBtn34;
    private javax.swing.JLabel menuBtn35;
    private javax.swing.JLabel menuBtn36;
    private javax.swing.JLabel menuBtn37;
    private javax.swing.JLabel menuBtn38;
    private javax.swing.JLabel menuBtn4;
    private javax.swing.JTextArea rdComment;
    private javax.swing.JTextField rdReportId;
    private javax.swing.JTextField rdReportStatus;
    private javax.swing.JTextField rdReportType;
    private javax.swing.JTextField rdStudentId;
    private javax.swing.JTextField rdStudentName;
    // End of variables declaration//GEN-END:variables


}
