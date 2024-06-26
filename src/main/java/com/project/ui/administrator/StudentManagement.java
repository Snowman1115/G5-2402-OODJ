/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.project.ui.administrator;

import com.project.common.utils.Dialog;
import com.project.common.utils.JsonHandler;
import com.project.controller.UserAccountController;
import org.json.simple.JSONObject;

import java.util.List;

import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;
import java.util.regex.Pattern;


/**
 * student management - Admin
 * @author AU YIK HOE
 * @version 1.0, Last edited on 2024-05-23
 * @since 2024-05-01
 */
public class StudentManagement extends javax.swing.JInternalFrame {
    DefaultTableModel table;
    /**
     * Creates new form StudentManagement
     */
    public StudentManagement() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI)this.getUI();
        ui.setNorthPane(null);
        refreshData();
    }

    private void refreshData() {
        List<String> intakeList = UserAccountController.getIntakes();

        for (String intake : intakeList) {
            filterByIntake.addItem(intake);
        }

        JsonHandler students = UserAccountController.getStudents();
        table = (DefaultTableModel) studentTable.getModel();
        table.setRowCount(0);

        for (int i = 0; i < students.getAll().size(); i++) {
            JSONObject student = (JSONObject) students.getAll().get(i);
            table.addRow(new Object[] {student.get("id"), student.get("username"), student.get("firstName"), student.get("lastName"), student.get("intake")});
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

        MainPanel = new javax.swing.JPanel();
        menuBtn16 = new javax.swing.JLabel();
        searchField = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        studentTable = new javax.swing.JTable();
        filterByIntake = new javax.swing.JComboBox<>();
        menuBtn17 = new javax.swing.JLabel();
        addBtn = new javax.swing.JButton();
        editBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1093, 695));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        MainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menuBtn16.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn16.setText("ACTIONS");
        menuBtn16.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn16.setOpaque(true);
        MainPanel.add(menuBtn16, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 560, 120, 40));

        searchField.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        searchField.setForeground(new java.awt.Color(1, 1, 1));
        searchField.setBorder(null);
        searchField.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchFieldKeyReleased(evt);
            }
        });
        MainPanel.add(searchField, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, 290, 35));

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/filter-24.png"))); // NOI18N
        jLabel11.setText("Filter :");
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel11.setOpaque(true);
        MainPanel.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 50, 80, 35));

        studentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Username", "First Name", "Last Name", "Intake"
            }
        ));
        jScrollPane3.setViewportView(studentTable);

        MainPanel.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 1020, 440));

        filterByIntake.setBackground(new java.awt.Color(254, 254, 254));
        filterByIntake.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 12)); // NOI18N
        filterByIntake.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All" }));
        filterByIntake.setToolTipText("d");
        filterByIntake.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        filterByIntake.setFocusable(false);
        filterByIntake.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterByIntakeActionPerformed(evt);
            }
        });
        MainPanel.add(filterByIntake, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 50, 330, 35));

        menuBtn17.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn17.setText("STUDENT DETAILS");
        menuBtn17.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn17.setOpaque(true);
        MainPanel.add(menuBtn17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 670, 40));

        addBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/add-24.png"))); // NOI18N
        addBtn.setText("Add");
        addBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addBtnMouseClicked(evt);
            }
        });
        MainPanel.add(addBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 600, 110, 40));

        editBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/edit-24x24.png"))); // NOI18N
        editBtn.setText("Edit");
        editBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editBtnMouseClicked(evt);
            }
        });
        MainPanel.add(editBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 600, 110, 40));

        deleteBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/delete-red-24x24.png"))); // NOI18N
        deleteBtn.setText("Delete");
        deleteBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteBtnMouseClicked(evt);
            }
        });
        MainPanel.add(deleteBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 600, 110, 40));

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/search-24x24.png"))); // NOI18N
        jLabel12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel12.setOpaque(true);
        MainPanel.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 50, 40, 35));

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Search :");
        jLabel13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel13.setOpaque(true);
        MainPanel.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 50, 35));

        getContentPane().add(MainPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 660));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchFieldKeyReleased
        String searchKey = searchField.getText();

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(table);
        studentTable.setRowSorter(sorter);
        String regex = "(?i).*" + Pattern.quote(searchKey) + ".*";

        sorter.setRowFilter(RowFilter.regexFilter(regex));
    }//GEN-LAST:event_searchFieldKeyReleased

    private void addBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addBtnMouseClicked
        AdminGui.ButtonClicked("addStudent");
    }//GEN-LAST:event_addBtnMouseClicked

    private void editBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editBtnMouseClicked
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow < 0) {
            Dialog.ErrorDialog("Please select a record to edit!");
        } else {
            int studentId = Integer.parseInt(studentTable.getValueAt(selectedRow, 0).toString());
            AdminGui.editStudent(studentId);
        }
    }//GEN-LAST:event_editBtnMouseClicked

    private void deleteBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteBtnMouseClicked
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow < 0) {
            Dialog.ErrorDialog("Please select a record to remove!");
        } else {
            if (Dialog.ConfirmationDialog("Warning", "This action cannot be undone! Confirm to proceed.")) {
                int studentId = Integer.parseInt(studentTable.getValueAt(selectedRow, 0).toString());
                if (UserAccountController.removeStudent(studentId)) {
                    AdminGui.ButtonClicked("student");
                } else {
                    Dialog.ErrorDialog("An unexpected error has occurred. Please contact technical department for assistance.");
                }
            }
        }
    }//GEN-LAST:event_deleteBtnMouseClicked

    private void filterByIntakeActionPerformed(java.awt.event.ActionEvent evt) {
        String selectedIntake = (String) filterByIntake.getSelectedItem();

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(table);
        studentTable.setRowSorter(sorter);
        String regex = selectedIntake.equals("All") ? ".*" : Pattern.quote(selectedIntake);

        sorter.setRowFilter(RowFilter.regexFilter(regex, table.findColumn("Intake")));
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel MainPanel;
    private javax.swing.JButton addBtn;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JButton editBtn;
    private static javax.swing.JComboBox<String> filterByIntake;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel menuBtn16;
    private javax.swing.JLabel menuBtn17;
    private javax.swing.JTextField searchField;
    private javax.swing.JTable studentTable;
    // End of variables declaration//GEN-END:variables
}
