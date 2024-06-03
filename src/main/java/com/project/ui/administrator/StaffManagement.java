/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.project.ui.administrator;

import com.project.common.constants.UserRoleType;
import com.project.common.utils.Dialog;
import com.project.common.utils.JsonHandler;
import com.project.controller.UserAccountController;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author Dell Technologies
 */
public class StaffManagement extends javax.swing.JInternalFrame {

    DefaultTableModel table;
    public StaffManagement() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI)this.getUI();
        ui.setNorthPane(null);
        
        setUpPage();
    }

    private void setUpPage() {
        JsonHandler lecturers = UserAccountController.getLecturers();
        JsonHandler projectManagers = UserAccountController.getPMs();
        JsonHandler admins = UserAccountController.getAdmins();

        table = (DefaultTableModel) staffTable.getModel();
        table.setRowCount(0);

        for (int i = 0; i < lecturers.getAll().size(); i++) {
            JSONObject lecturer = (JSONObject) lecturers.getAll().get(i);
            table.addRow(new Object[] {lecturer.get("id"), lecturer.get("username"), lecturer.get("first_name"), lecturer.get("last_name"), lecturer.get("roleType")});
        }
        for (int i = 0; i < projectManagers.getAll().size(); i++) {
            JSONObject projectManager = (JSONObject) projectManagers.getAll().get(i);
            table.addRow(new Object[] {projectManager.get("id"), projectManager.get("username"), projectManager.get("first_name"), projectManager.get("last_name"), projectManager.get("roleType")});
        }
        for (int i = 0; i < admins.getAll().size(); i++) {
            JSONObject admin = (JSONObject) admins.getAll().get(i);
            table.addRow(new Object[] {admin.get("id"), admin.get("username"), admin.get("first_name"), admin.get("last_name"), admin.get("roleType")});
        }

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table);
        staffTable.setRowSorter(sorter);

        sorter.setSortKeys(List.of(new RowSorter.SortKey(2, SortOrder.ASCENDING)));
        sorter.sort();

        List<String> userRoles = new ArrayList<>();
        userRoles.add(UserRoleType.ADMIN.toString());
        userRoles.add(UserRoleType.PROJECT_MANAGER.toString().replace("_", " "));
        userRoles.add(UserRoleType.LECTURER.toString());

        for (String s : userRoles) {
            filterByRole.addItem(s);
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
        staffTable = new javax.swing.JTable();
        filterByRole = new javax.swing.JComboBox<>();
        pageTitle = new javax.swing.JLabel();
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

        staffTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Username", "First Name", "Last Name", "Role"
            }
        ));
        jScrollPane3.setViewportView(staffTable);

        MainPanel.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 1020, 440));

        filterByRole.setBackground(new java.awt.Color(254, 254, 254));
        filterByRole.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 12)); // NOI18N
        filterByRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All" }));
        filterByRole.setToolTipText("d");
        filterByRole.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        filterByRole.setFocusable(false);
        filterByRole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterByRoleActionPerformed(evt);
            }
        });
        MainPanel.add(filterByRole, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 50, 330, 35));

        pageTitle.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        pageTitle.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        pageTitle.setText("STAFF DETAILS");
        pageTitle.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pageTitle.setOpaque(true);
        MainPanel.add(pageTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 670, 40));

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
        staffTable.setRowSorter(sorter);
        String regex = "(?i).*" + Pattern.quote(searchKey) + ".*";

        sorter.setRowFilter(RowFilter.regexFilter(regex));
    }//GEN-LAST:event_searchFieldKeyReleased

    private void filterByRoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterByRoleActionPerformed
        String selectedIntake = (String) filterByRole.getSelectedItem();

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(table);
        staffTable.setRowSorter(sorter);
        String regex = selectedIntake.equals("All") ? ".*" : Pattern.quote(selectedIntake);

        sorter.setRowFilter(RowFilter.regexFilter(regex, table.findColumn("Role")));
    }//GEN-LAST:event_filterByRoleActionPerformed

    private void addBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addBtnMouseClicked
        AdminGui.ButtonClicked("addStaff");
    }//GEN-LAST:event_addBtnMouseClicked

    private void editBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editBtnMouseClicked
        int selectedRow = staffTable.getSelectedRow();
        if (selectedRow < 0) {
            Dialog.ErrorDialog("Please select a record to edit!");
        } else {
            int staffId = Integer.parseInt(staffTable.getValueAt(selectedRow, 0).toString());
            String staffRole = staffTable.getValueAt(selectedRow, 4).toString();
            AdminGui.editStaff(staffId, staffRole);
        }
    }//GEN-LAST:event_editBtnMouseClicked

    private void deleteBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteBtnMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteBtnMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel MainPanel;
    private javax.swing.JButton addBtn;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JButton editBtn;
    private static javax.swing.JComboBox<String> filterByRole;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel menuBtn16;
    private javax.swing.JLabel pageTitle;
    private javax.swing.JTextField searchField;
    private javax.swing.JTable staffTable;
    // End of variables declaration//GEN-END:variables
}
