/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.project.ui.administrator;

import com.project.common.utils.JsonHandler;
import com.project.controller.UserAccountController;
import com.project.pojo.UserRole;
import org.apache.poi.ss.usermodel.Row;
import org.json.simple.JSONObject;

import java.util.List;

import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;
import java.util.regex.Pattern;


/**
 Student Management - Admin
 * @author AU YIK HOE
 * @version 1.0, Last edited on 2024-05-14
 * @since 2024-04-27
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
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
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();

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
        jLabel11.setText("Filter :");
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel11.setOpaque(true);
        MainPanel.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 50, 50, 35));

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

        addBtn.setText("Add");
        MainPanel.add(addBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 600, 110, 40));

        editBtn.setText("Edit");
        MainPanel.add(editBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 600, 110, 40));

        jButton5.setText("xxx");
        MainPanel.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 600, 110, 40));

        jButton6.setText("xxx");
        MainPanel.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 600, 110, 40));

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Search :");
        jLabel12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel12.setOpaque(true);
        MainPanel.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 50, 35));

        getContentPane().add(MainPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 660));

        pack();
    }// </editor-fold>

    private void searchFieldKeyReleased(java.awt.event.KeyEvent evt) {
        String searchKey = searchField.getText();

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(table);
        studentTable.setRowSorter(sorter);
        String regex = "(?i).*" + Pattern.quote(searchKey) + ".*";

        sorter.setRowFilter(RowFilter.regexFilter(regex));
    }

    private void filterByIntakeActionPerformed(java.awt.event.ActionEvent evt) {
        String selectedIntake = (String) filterByIntake.getSelectedItem();
        System.out.println(selectedIntake);

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(table);
        studentTable.setRowSorter(sorter);
        String regex = selectedIntake.equals("All") ? ".*" : Pattern.quote(selectedIntake);

        sorter.setRowFilter(RowFilter.regexFilter(regex, table.findColumn("Intake")));
    }


    // Variables declaration - do not modify
    private javax.swing.JPanel MainPanel;
    private javax.swing.JButton addBtn;
    private javax.swing.JButton editBtn;
    private static javax.swing.JComboBox<String> filterByIntake;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel menuBtn16;
    private javax.swing.JLabel menuBtn17;
    private javax.swing.JTextField searchField;
    private javax.swing.JTable studentTable;
    // End of variables declaration
}
