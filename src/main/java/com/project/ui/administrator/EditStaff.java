/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.project.ui.administrator;

import com.project.common.constants.UserRoleType;
import com.project.controller.UserAccountController;
import com.project.pojo.UserAccount;

import static com.project.common.constants.UserRoleType.LECTURER;
import static com.project.common.constants.UserRoleType.PROJECT_MANAGER;
import javax.swing.plaf.basic.BasicInternalFrameUI;

/**
 *
 * @author Dell Technologies
 */
public class EditStaff extends javax.swing.JInternalFrame {

    private String roleType;
    private int userId;
    /**
     * Creates new form EditLecturerPM
     */
    public EditStaff(int userId, String roleType) {
        this.roleType = roleType;
        this.userId = userId;
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI)this.getUI();
        ui.setNorthPane(null);
        
        setUpForm();
    }
    
    private void setUpForm() {
        UserAccount staffAcc = UserAccountController.getUserDetailsByUserId(this.userId);
        usernameField.setText(staffAcc.getUsername());
        emailField.setText(staffAcc.getEmail());
        firstName.setText(staffAcc.getFirstName());
        lastName.setText(staffAcc.getLastName());

        switch (this.roleType) {
            case "LECTURER" -> {
                pageTitle.setText("LECTURER DETAILS");
            }
            case "PROJECT MANAGER" -> {
                pageTitle.setText("PROJECT MANAGER DETAILS");
                
            }
            case "ADMIN" -> {
                pageTitle.setText("ADMIN DETAILS");
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

        jPanel1 = new javax.swing.JPanel();
        pageTitle = new javax.swing.JLabel();
        firstName = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        lastName = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        emailField = new javax.swing.JLabel();
        resetPW = new javax.swing.JButton();
        updateBtn = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        usernameField = new javax.swing.JLabel();
        cancelBtn = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(1093, 695));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pageTitle.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        pageTitle.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        pageTitle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/action-24x24.png"))); // NOI18N
        pageTitle.setText("EDIT STAFF DETAILS");
        pageTitle.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pageTitle.setOpaque(true);
        jPanel1.add(pageTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 700, 70));

        firstName.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        firstName.setForeground(new java.awt.Color(1, 1, 1));
        firstName.setBorder(null);
        firstName.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        firstName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                firstNameKeyReleased(evt);
            }
        });
        jPanel1.add(firstName, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 250, 290, 35));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("First Name :");
        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel5.setOpaque(true);
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 250, 100, 35));

        lastName.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        lastName.setForeground(new java.awt.Color(1, 1, 1));
        lastName.setBorder(null);
        lastName.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        lastName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lastNameKeyReleased(evt);
            }
        });
        jPanel1.add(lastName, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 310, 290, 35));

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Last Name :");
        jLabel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel6.setOpaque(true);
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 310, 100, 35));

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Email :");
        jLabel7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel7.setOpaque(true);
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 190, 100, 35));

        emailField.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        emailField.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        emailField.setOpaque(true);
        jPanel1.add(emailField, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 190, 290, 35));

        resetPW.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/passwordKey-24x24.png"))); // NOI18N
        resetPW.setText("Password Reset");
        resetPW.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                resetPWMouseClicked(evt);
            }
        });
        jPanel1.add(resetPW, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 580, 180, 40));

        updateBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/save-24.png"))); // NOI18N
        updateBtn.setText("Update");
        updateBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updateBtnMouseClicked(evt);
            }
        });
        jPanel1.add(updateBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 580, 110, 40));

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Username :");
        jLabel12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel12.setOpaque(true);
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 140, 100, 35));

        usernameField.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        usernameField.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        usernameField.setOpaque(true);
        jPanel1.add(usernameField, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 140, 290, 35));

        cancelBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/cancel-24x24.png"))); // NOI18N
        cancelBtn.setText("Cancel");
        cancelBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelBtnMouseClicked(evt);
            }
        });
        jPanel1.add(cancelBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 580, 110, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 660));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void firstNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_firstNameKeyReleased

    }//GEN-LAST:event_firstNameKeyReleased

    private void lastNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lastNameKeyReleased

    }//GEN-LAST:event_lastNameKeyReleased

    private void resetPWMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resetPWMouseClicked

    }//GEN-LAST:event_resetPWMouseClicked

    private void updateBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateBtnMouseClicked
        
    }//GEN-LAST:event_updateBtnMouseClicked

    private void cancelBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelBtnMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_cancelBtnMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelBtn;
    private javax.swing.JLabel emailField;
    private javax.swing.JTextField firstName;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField lastName;
    private javax.swing.JLabel pageTitle;
    private javax.swing.JButton resetPW;
    private javax.swing.JButton updateBtn;
    private javax.swing.JLabel usernameField;
    // End of variables declaration//GEN-END:variables
}
