package com.project.ui.project_manager;

import com.project.ui.student.*;
import com.project.common.constants.MessageConstant;
import com.project.common.constants.ReportStatus;
import com.project.common.utils.DateTimeUtils;
import com.project.common.utils.Dialog;
import com.project.common.utils.JsonHandler;
import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

import com.project.controller.ConsultationController;
import com.project.controller.ModuleFeedbackController;
import com.project.controller.ProjectModuleController;
import com.project.controller.SubmissionController;
import com.project.controller.UserAccountController;
import java.time.LocalDate;
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
public class ManagerManageFeedback extends javax.swing.JInternalFrame {

     private Integer reportId;
     private Map<String, String> currentModuleDetails;
     private String firstMarkerName, secondMarkerName;
     private JsonHandler lecturerLists;
    /**
     * Creates new form StudentAssignmentGui
     */
    public ManagerManageFeedback() {
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
        DefaultTableModel dtm =  (DefaultTableModel)jTableStudentFeedback.getModel();
        dtm.setRowCount(0);
        List<Map<String, String>> feedbackLists = ModuleFeedbackController.getAllFeedbackForPM();
        for (Map<String, String> list : feedbackLists) {
                String[] data = {list.get("moduleId"), list.get("moduleCode"), list.get("studentId"), list.get("studentName"), list.get("comments")};
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
//                    rdReportId.setText(mLists.get("id"));
//                    rdStudentId.setText(mLists.get("studentId"));
//                    rdStudentName.setText(mLists.get("studentName"));
//                    rdReportStatus.setText(mLists.get("reportStatus"));
//                    rdReportType.setText(mLists.get("reportType"));
//                    rdComment.setText(mLists.get("comment"));
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
        jPanel7 = new javax.swing.JPanel();
        manageSupervisor = new javax.swing.JLabel();
        menuBtn16 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableStudentFeedback = new javax.swing.JTable();
        menuBtn17 = new javax.swing.JLabel();

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
        menuBtn4.setText("TOTAL");
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
        menuBtn14.setText("Student Feedback");
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
        Panel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 190, 40, 35));

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
        JField12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JField12ActionPerformed(evt);
            }
        });
        Panel1.add(JField12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 290, 35));

        jPanel7.setBackground(new java.awt.Color(254, 254, 254));

        manageSupervisor.setBackground(new java.awt.Color(105, 105, 105));
        manageSupervisor.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        manageSupervisor.setForeground(new java.awt.Color(1, 1, 1));
        manageSupervisor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/exportCsv-24x24.png"))); // NOI18N
        manageSupervisor.setText("Export Student Feedback");
        manageSupervisor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        manageSupervisor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                manageSupervisorMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(manageSupervisor, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(127, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(manageSupervisor)
                .addContainerGap(51, Short.MAX_VALUE))
        );

        Panel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 50, 500, 90));

        menuBtn16.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn16.setText("VIEW STUDENT FEEDBACK");
        menuBtn16.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn16.setOpaque(true);
        Panel1.add(menuBtn16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 670, 40));

        jTableStudentFeedback.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Module Id", "Module", "Student Id", "Student Name", "Comment"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableStudentFeedback.setFocusable(false);
        jTableStudentFeedback.setRequestFocusEnabled(false);
        jTableStudentFeedback.getTableHeader().setResizingAllowed(false);
        jTableStudentFeedback.getTableHeader().setReorderingAllowed(false);
        jTableStudentFeedback.setUpdateSelectionOnSort(false);
        jTableStudentFeedback.setVerifyInputWhenFocusTarget(false);
        jScrollPane3.setViewportView(jTableStudentFeedback);
        if (jTableStudentFeedback.getColumnModel().getColumnCount() > 0) {
            jTableStudentFeedback.getColumnModel().getColumn(0).setResizable(false);
            jTableStudentFeedback.getColumnModel().getColumn(1).setResizable(false);
            jTableStudentFeedback.getColumnModel().getColumn(2).setResizable(false);
            jTableStudentFeedback.getColumnModel().getColumn(3).setResizable(false);
            jTableStudentFeedback.getColumnModel().getColumn(4).setResizable(false);
        }

        Panel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 1020, 360));

        menuBtn17.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn17.setText("ACTION");
        menuBtn17.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn17.setOpaque(true);
        Panel1.add(menuBtn17, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 10, 420, 40));

        MainTabbedPanel.addTab("View Report", Panel1);

        getContentPane().add(MainTabbedPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 660));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void manageSupervisorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageSupervisorMouseClicked
//      add code to get row detail (Module Id)
        int selectedRow = jTableStudentFeedback.getSelectedRow();
        if (selectedRow != -1) {
            reportId = Integer.valueOf(jTableStudentFeedback.getValueAt(selectedRow, 0).toString());
            autofillReportDetails(reportId);
            MainTabbedPanel.setSelectedIndex(1);
        } else {
            Dialog.ErrorDialog(MessageConstant.ERROR_EMPTY_MODULE);
        }
    }//GEN-LAST:event_manageSupervisorMouseClicked

    private void JField12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField12MouseClicked
        if (JField12.getText().equals("Enter Keywords To Search")) {
            JField12.setText("");
        }
    }//GEN-LAST:event_JField12MouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        DefaultTableModel dtm = (DefaultTableModel)jTableStudentFeedback.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dtm);
        jTableStudentFeedback.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(JField12.getText().trim()));
    }//GEN-LAST:event_jLabel11MouseClicked

    private void menuBtn12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuBtn12MouseClicked
//        consultationComboBox.setSelectedIndex(1);
    }//GEN-LAST:event_menuBtn12MouseClicked

    private void menuBtn13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuBtn13MouseClicked
//        consultationComboBox.setSelectedIndex(2);
    }//GEN-LAST:event_menuBtn13MouseClicked

    private void MainTabbedPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MainTabbedPanelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_MainTabbedPanelMouseClicked

    private void JField12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JField12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JField12ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField JField12;
    private javax.swing.JTabbedPane MainTabbedPanel;
    private javax.swing.JPanel Panel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTableStudentFeedback;
    private javax.swing.JLabel manageSupervisor;
    private static javax.swing.JLabel menuBtn12;
    private static javax.swing.JLabel menuBtn13;
    private javax.swing.JLabel menuBtn14;
    private javax.swing.JLabel menuBtn16;
    private javax.swing.JLabel menuBtn17;
    private javax.swing.JLabel menuBtn3;
    private javax.swing.JLabel menuBtn4;
    // End of variables declaration//GEN-END:variables


}
