package com.project.ui.lecturer;

import com.project.ui.student.*;
import com.project.common.constants.MessageConstant;
import com.project.common.utils.Dialog;
import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

import com.project.controller.ConsultationController;
import com.project.controller.ProjectModuleController;
import org.bouncycastle.tsp.TSPUtil;
import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;

import java.util.List;
import java.util.Map;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Olaf
 */
public class LecturerDashboard extends javax.swing.JInternalFrame {

    /**
     * Creates new form StudentAssignmentGui
     */
    public LecturerDashboard() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI)this.getUI();
        ui.setNorthPane(null);

        // refresh();

        // salesManagementPanel.setText(PropertiesReader.getProperty("SalesManagementPanelVersion"));
        refresh();
    }

    private void refresh() {
        fillInSelectModuleComboBox();
        // refreshDashboard();
        // refreshComboBox(0);
    }
//    
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
//            List<Map<String, String>> lists = ConsultationController.getAllScheduledConsultationIdByStudentId(mpduleID);
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
    private void fillInSelectModuleComboBox() {
        selectModuleComboBox.removeAllItems();
        List<Map<String, String>> lists = ProjectModuleController.getAllModuleDetailsByLecId();
        
        if (lists.isEmpty()) {
            selectModuleComboBox.addItem(MessageConstant.CONDITION_NO_MODULES_UNDER_LECTURER);
            return;
        }
        
        for (Map<String, String> list : lists) {
            selectModuleComboBox.addItem(list.get("moduleCode"));
        }
        selectModuleComboBox.setSelectedIndex(0);
        // selectModuleComboBox.getSelectedItem();

        
        //TODO: fillInSelectModuleComboBox for second marker, can refer to the below code structure and make modification        
//        consultationComboBox2.removeAllItems();
//        List<Map<String, String>> lists2 = ConsultationController.getAllScheduledConsultationIdByStudentId();
//        if (lists2.isEmpty()) {
//            consultationComboBox2.addItem("There is no scheduled consultation.");
//        }
//        for (Map<String, String> list : lists2) {
//            consultationComboBox2.addItem(list.get("id"));
//        }
//        consultationComboBox2.setSelectedIndex(0);
    }

    private void refreshTableAfterSelectModule(String value) {
        DefaultTableModel dtm1 =  (DefaultTableModel)superviseeTbl.getModel();
        dtm1.setRowCount(0);
        
        //Search function
//        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dtm1);
//        jTable4.setRowSorter(tr);
//        tr.setRowFilter(RowFilter.regexFilter("".trim()));
        
        List<Map<String, String>> superviseeList = ProjectModuleController.getAllModuleDetailsByLecId();
        for (Map<String,String> list : superviseeList) {
            if (value.equals(list.get("moduleCode"))) {
                String[] data = {list.get("studentId"), list.get("studentName"), list.get("intakeCode"), list.get("moduleCode"), list.get("reportType"), list.get("reportStatus")};
                dtm1.addRow(data);
            }
        }
    }
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
        jScrollPane1 = new javax.swing.JScrollPane();
        superviseeTbl = new javax.swing.JTable();
        menuBtn20 = new javax.swing.JLabel();
        selectModuleComboBox = new javax.swing.JComboBox<>();
        menuBtn21 = new javax.swing.JLabel();
        Panel2 = new javax.swing.JPanel();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        MainTabbedPanel.setBackground(new java.awt.Color(230, 230, 230));
        MainTabbedPanel.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N

        Panel1.setPreferredSize(new java.awt.Dimension(1050, 570));
        Panel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        superviseeTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Student ID", "Student Name", "Intake", "Module Code", "Project Type", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        superviseeTbl.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(superviseeTbl);
        if (superviseeTbl.getColumnModel().getColumnCount() > 0) {
            superviseeTbl.getColumnModel().getColumn(0).setResizable(false);
            superviseeTbl.getColumnModel().getColumn(1).setResizable(false);
            superviseeTbl.getColumnModel().getColumn(2).setResizable(false);
            superviseeTbl.getColumnModel().getColumn(3).setResizable(false);
            superviseeTbl.getColumnModel().getColumn(4).setResizable(false);
            superviseeTbl.getColumnModel().getColumn(5).setResizable(false);
        }

        Panel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 1020, -1));

        menuBtn20.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn20.setText("MODULE SELECTED:");
        menuBtn20.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn20.setOpaque(true);
        Panel1.add(menuBtn20, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 150, 40));

        selectModuleComboBox.setBackground(new java.awt.Color(254, 254, 254));
        selectModuleComboBox.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 12)); // NOI18N
        selectModuleComboBox.setToolTipText("d");
        selectModuleComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        selectModuleComboBox.setFocusable(false);
        selectModuleComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectModuleComboBoxActionPerformed(evt);
            }
        });
        Panel1.add(selectModuleComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, 380, 40));

        menuBtn21.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn21.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn21.setText("LIST OF ASSIGNED SUPERVISEES");
        menuBtn21.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn21.setOpaque(true);
        Panel1.add(menuBtn21, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 1020, 40));

        MainTabbedPanel.addTab("First Marker", Panel1);

        Panel2.setPreferredSize(new java.awt.Dimension(1050, 570));
        Panel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        MainTabbedPanel.addTab("Second Marker", Panel2);

        getContentPane().add(MainTabbedPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 660));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void selectModuleComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectModuleComboBoxActionPerformed
        refreshTableAfterSelectModule(selectModuleComboBox.getSelectedItem().toString());
    }//GEN-LAST:event_selectModuleComboBoxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane MainTabbedPanel;
    private javax.swing.JPanel Panel1;
    private javax.swing.JPanel Panel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel menuBtn20;
    private javax.swing.JLabel menuBtn21;
    private static javax.swing.JComboBox<String> selectModuleComboBox;
    private javax.swing.JTable superviseeTbl;
    // End of variables declaration//GEN-END:variables
}
