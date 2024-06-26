package com.project.ui.lecturer;

import com.project.common.constants.MessageConstant;
import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

import com.project.controller.ProjectModuleController;

import java.util.List;
import java.util.Map;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Sin Lian Feng
 */
public class LecturerDashboard extends javax.swing.JInternalFrame {

    /**
     * Creates new form LecturerDashboard
     */
    public LecturerDashboard() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI)this.getUI();
        ui.setNorthPane(null);

        refresh();
    }

    private void refresh() {
        fillInSelectModuleComboBoxFirstMarker();
        fillInSelectModuleComboBoxSecondMarker();
    }

    //Get all modules if the lecturer is first marker
    private void fillInSelectModuleComboBoxFirstMarker() {
        selectModuleComboBoxFM.removeAllItems();
        List<Map<String, String>> lists = ProjectModuleController.getAllModuleDetailsByFirstMarkerId();
        
        if (lists.isEmpty()) {
            selectModuleComboBoxFM.addItem(MessageConstant.CONDITION_NO_MODULES_UNDER_LECTURER);
            return;
        }
        
        for (Map<String, String> list : lists) {
            selectModuleComboBoxFM.addItem(list.get("moduleCode"));
        }
        selectModuleComboBoxFM.setSelectedIndex(0);
    }
    
    //Get all modules if the lecturer is second marker
    private void fillInSelectModuleComboBoxSecondMarker() {
        selectModuleComboBoxSM.removeAllItems();
        List<Map<String, String>> lists = ProjectModuleController.getAllModuleDetailsBySecondMarkerId();
        
        if (lists.isEmpty()) {
            selectModuleComboBoxSM.addItem(MessageConstant.CONDITION_NO_MODULES_UNDER_LECTURER);
            return;
        }
        
        for (Map<String, String> list : lists) {
            selectModuleComboBoxSM.addItem(list.get("moduleCode"));
        }
        selectModuleComboBoxSM.setSelectedIndex(0);
    }
    

    private void refreshTableAfterSelectModuleFM(String value) {
        DefaultTableModel dtm1 = (DefaultTableModel)superviseeTableFM.getModel();
        dtm1.setRowCount(0);
        
        //Search function
        DefaultTableModel dtm = (DefaultTableModel)superviseeTableFM.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dtm);
        superviseeTableFM.setRowSorter(tr);
        
        List<Map<String, String>> superviseeList = ProjectModuleController.getModuleDetailsByFirstMarkerId();
        for (Map<String,String> list : superviseeList) {
            if (value.equals(list.get("moduleCode"))) {
                String[] data = {list.get("studentId"), list.get("studentName"), list.get("intakeCode"), list.get("reportType"), list.get("submissionStatus"),list.get("submissionDueDate")};
                dtm1.addRow(data);
            }
        }
    }
    private void refreshTableAfterSelectModuleSM(String value) {
        DefaultTableModel dtm1 =  (DefaultTableModel)superviseeTableSM.getModel();
        dtm1.setRowCount(0);
        
        //Search function
        DefaultTableModel dtm = (DefaultTableModel)superviseeTableSM.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dtm);
        superviseeTableSM.setRowSorter(tr);
        
        List<Map<String, String>> superviseeList = ProjectModuleController.getModuleDetailsBySecondMarkerId();
        for (Map<String,String> list : superviseeList) {
            if (value.equals(list.get("moduleCode"))) {
                String[] data = {list.get("studentId"), list.get("studentName"), list.get("intakeCode"), list.get("reportType"), list.get("submissionStatus"),list.get("submissionDueDate")};
                dtm1.addRow(data);
            }
        }
    }

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
        superviseeTableFM = new javax.swing.JTable();
        menuBtn20 = new javax.swing.JLabel();
        selectModuleComboBoxFM = new javax.swing.JComboBox<>();
        menuBtn21 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        searchFieldInFM = new javax.swing.JTextField();
        Panel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        superviseeTableSM = new javax.swing.JTable();
        menuBtn22 = new javax.swing.JLabel();
        selectModuleComboBoxSM = new javax.swing.JComboBox<>();
        menuBtn23 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        searchFieldInSM = new javax.swing.JTextField();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        MainTabbedPanel.setBackground(new java.awt.Color(230, 230, 230));
        MainTabbedPanel.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N

        Panel1.setPreferredSize(new java.awt.Dimension(1050, 570));
        Panel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        superviseeTableFM.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Student ID", "Student Name", "Intake", "Project Type", "Status", "Submission Due Date"
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
        superviseeTableFM.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(superviseeTableFM);
        if (superviseeTableFM.getColumnModel().getColumnCount() > 0) {
            superviseeTableFM.getColumnModel().getColumn(0).setResizable(false);
            superviseeTableFM.getColumnModel().getColumn(1).setResizable(false);
            superviseeTableFM.getColumnModel().getColumn(2).setResizable(false);
            superviseeTableFM.getColumnModel().getColumn(3).setResizable(false);
            superviseeTableFM.getColumnModel().getColumn(4).setResizable(false);
            superviseeTableFM.getColumnModel().getColumn(5).setResizable(false);
        }

        Panel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 1020, -1));

        menuBtn20.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn20.setText("MODULE SELECTED:");
        menuBtn20.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn20.setOpaque(true);
        Panel1.add(menuBtn20, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 150, 40));

        selectModuleComboBoxFM.setBackground(new java.awt.Color(254, 254, 254));
        selectModuleComboBoxFM.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 12)); // NOI18N
        selectModuleComboBoxFM.setToolTipText("d");
        selectModuleComboBoxFM.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        selectModuleComboBoxFM.setFocusable(false);
        selectModuleComboBoxFM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectModuleComboBoxFMActionPerformed(evt);
            }
        });
        Panel1.add(selectModuleComboBoxFM, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, 380, 40));

        menuBtn21.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn21.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn21.setText("LIST OF ASSIGNED SUPERVISEES");
        menuBtn21.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn21.setOpaque(true);
        Panel1.add(menuBtn21, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 610, 40));

        jLabel12.setBackground(new java.awt.Color(254, 254, 254));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/search-24x24.png"))); // NOI18N
        jLabel12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel12.setOpaque(true);
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });
        Panel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 60, 40, 40));

        searchFieldInFM.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        searchFieldInFM.setForeground(new java.awt.Color(1, 1, 1));
        searchFieldInFM.setText("Enter Keywords To Search");
        searchFieldInFM.setBorder(null);
        searchFieldInFM.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        searchFieldInFM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchFieldInFMMouseClicked(evt);
            }
        });
        Panel1.add(searchFieldInFM, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 60, 370, 40));

        MainTabbedPanel.addTab("First Marker", Panel1);

        Panel2.setPreferredSize(new java.awt.Dimension(1050, 570));
        Panel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        superviseeTableSM.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Student ID", "Student Name", "Intake", "Project Type", "Status", "Submission Due Date"
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
        superviseeTableSM.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(superviseeTableSM);
        if (superviseeTableSM.getColumnModel().getColumnCount() > 0) {
            superviseeTableSM.getColumnModel().getColumn(0).setResizable(false);
            superviseeTableSM.getColumnModel().getColumn(1).setResizable(false);
            superviseeTableSM.getColumnModel().getColumn(2).setResizable(false);
            superviseeTableSM.getColumnModel().getColumn(3).setResizable(false);
            superviseeTableSM.getColumnModel().getColumn(4).setResizable(false);
            superviseeTableSM.getColumnModel().getColumn(5).setResizable(false);
        }

        Panel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 1020, -1));

        menuBtn22.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn22.setText("MODULE SELECTED:");
        menuBtn22.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn22.setOpaque(true);
        Panel2.add(menuBtn22, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 150, 40));

        selectModuleComboBoxSM.setBackground(new java.awt.Color(254, 254, 254));
        selectModuleComboBoxSM.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 12)); // NOI18N
        selectModuleComboBoxSM.setToolTipText("d");
        selectModuleComboBoxSM.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        selectModuleComboBoxSM.setFocusable(false);
        selectModuleComboBoxSM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectModuleComboBoxSMActionPerformed(evt);
            }
        });
        Panel2.add(selectModuleComboBoxSM, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, 380, 40));

        menuBtn23.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn23.setText("LIST OF ASSIGNED SUPERVISEES");
        menuBtn23.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn23.setOpaque(true);
        Panel2.add(menuBtn23, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 610, 40));

        jLabel13.setBackground(new java.awt.Color(254, 254, 254));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/search-24x24.png"))); // NOI18N
        jLabel13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel13.setOpaque(true);
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });
        Panel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 60, 40, 40));

        searchFieldInSM.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        searchFieldInSM.setForeground(new java.awt.Color(1, 1, 1));
        searchFieldInSM.setText("Enter Keywords To Search");
        searchFieldInSM.setBorder(null);
        searchFieldInSM.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        searchFieldInSM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchFieldInSMMouseClicked(evt);
            }
        });
        Panel2.add(searchFieldInSM, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 60, 370, 40));

        MainTabbedPanel.addTab("Second Marker", Panel2);

        getContentPane().add(MainTabbedPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 660));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void selectModuleComboBoxFMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectModuleComboBoxFMActionPerformed
        refreshTableAfterSelectModuleFM(selectModuleComboBoxFM.getSelectedItem().toString());
    }//GEN-LAST:event_selectModuleComboBoxFMActionPerformed

    private void selectModuleComboBoxSMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectModuleComboBoxSMActionPerformed
        refreshTableAfterSelectModuleSM(selectModuleComboBoxSM.getSelectedItem().toString());
    }//GEN-LAST:event_selectModuleComboBoxSMActionPerformed

    private void searchFieldInFMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchFieldInFMMouseClicked
        if (searchFieldInFM.getText().equals("Enter Keywords To Search")) {
            searchFieldInFM.setText("");
        }
    }//GEN-LAST:event_searchFieldInFMMouseClicked

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        DefaultTableModel dtm = (DefaultTableModel)superviseeTableFM.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dtm);
        superviseeTableFM.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(searchFieldInFM.getText().trim()));
    }//GEN-LAST:event_jLabel12MouseClicked

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        DefaultTableModel dtm = (DefaultTableModel)superviseeTableSM.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dtm);
        superviseeTableSM.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(searchFieldInFM.getText().trim()));
    }//GEN-LAST:event_jLabel13MouseClicked

    private void searchFieldInSMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchFieldInSMMouseClicked
        if (searchFieldInFM.getText().equals("Enter Keywords To Search")) {
            searchFieldInFM.setText("");
        }
    }//GEN-LAST:event_searchFieldInSMMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane MainTabbedPanel;
    private javax.swing.JPanel Panel1;
    private javax.swing.JPanel Panel2;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel menuBtn20;
    private javax.swing.JLabel menuBtn21;
    private javax.swing.JLabel menuBtn22;
    private javax.swing.JLabel menuBtn23;
    private javax.swing.JTextField searchFieldInFM;
    private javax.swing.JTextField searchFieldInSM;
    private static javax.swing.JComboBox<String> selectModuleComboBoxFM;
    private static javax.swing.JComboBox<String> selectModuleComboBoxSM;
    private javax.swing.JTable superviseeTableFM;
    private javax.swing.JTable superviseeTableSM;
    // End of variables declaration//GEN-END:variables
}
