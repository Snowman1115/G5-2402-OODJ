/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.project.ui.authentication;

import static com.project.controller.authentication.UserAccountController.resetUserPasswordBySecurityPhrase;
import javax.swing.plaf.basic.BasicInternalFrameUI;

/**
 *
 * @author Olaf
 */
public class SetNewPasswordGui extends javax.swing.JInternalFrame {

    /**
     * Creates new form SetNewPasswordGui
     */
    public SetNewPasswordGui() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI)this.getUI();
        ui.setNorthPane(null);      
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
        menuBtn18 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        JField28 = new javax.swing.JTextField();
        JSeparator25 = new javax.swing.JSeparator();
        jLabel48 = new javax.swing.JLabel();
        JField36 = new javax.swing.JTextField();
        JSeparator37 = new javax.swing.JSeparator();
        jLabel37 = new javax.swing.JLabel();
        JField34 = new javax.swing.JTextField();
        JSeparator30 = new javax.swing.JSeparator();
        backBtn = new javax.swing.JButton();
        resetBtn = new javax.swing.JButton();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menuBtn18.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn18.setText("RESET PASSWORD");
        menuBtn18.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn18.setOpaque(true);
        jPanel1.add(menuBtn18, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 290, 40));

        jLabel32.setBackground(new java.awt.Color(105, 105, 105));
        jLabel32.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(1, 1, 1));
        jLabel32.setText("USERNAME");
        jPanel1.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, -1));

        JField28.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField28.setForeground(new java.awt.Color(1, 1, 1));
        JField28.setText("Username");
        JField28.setBorder(null);
        JField28.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        JField28.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField28.setEnabled(false);
        jPanel1.add(JField28, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 580, 35));

        JSeparator25.setForeground(new java.awt.Color(1, 1, 1));
        jPanel1.add(JSeparator25, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 580, 10));

        jLabel48.setBackground(new java.awt.Color(105, 105, 105));
        jLabel48.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(1, 1, 1));
        jLabel48.setText("NEW PASSWORD");
        jLabel48.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel1.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, -1, -1));

        JField36.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField36.setForeground(new java.awt.Color(1, 1, 1));
        JField36.setText("Please enter new password.");
        JField36.setBorder(null);
        JField36.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        JField36.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField36.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField36MouseClicked(evt);
            }
        });
        jPanel1.add(JField36, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 580, 35));

        JSeparator37.setForeground(new java.awt.Color(1, 1, 1));
        jPanel1.add(JSeparator37, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, 580, 10));

        jLabel37.setBackground(new java.awt.Color(105, 105, 105));
        jLabel37.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(1, 1, 1));
        jLabel37.setText("CONFIRM NEW PASSWORD");
        jPanel1.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, -1, -1));

        JField34.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField34.setForeground(new java.awt.Color(1, 1, 1));
        JField34.setText("Please re-enter new password");
        JField34.setBorder(null);
        JField34.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        JField34.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField34.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField34MouseClicked(evt);
            }
        });
        jPanel1.add(JField34, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 580, 35));

        JSeparator30.setForeground(new java.awt.Color(1, 1, 1));
        jPanel1.add(JSeparator30, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 580, -1));

        backBtn.setBackground(new java.awt.Color(254, 254, 254));
        backBtn.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 16)); // NOI18N
        backBtn.setForeground(new java.awt.Color(1, 1, 1));
        backBtn.setText("BACK");
        backBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(1, 1, 1)));
        backBtn.setContentAreaFilled(false);
        backBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        backBtn.setOpaque(true);
        backBtn.setPreferredSize(new java.awt.Dimension(105, 40));
        backBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backBtnMouseClicked(evt);
            }
        });
        jPanel1.add(backBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 300, -1, -1));

        resetBtn.setBackground(new java.awt.Color(254, 254, 254));
        resetBtn.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 16)); // NOI18N
        resetBtn.setForeground(new java.awt.Color(1, 1, 1));
        resetBtn.setText("RESET");
        resetBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(1, 1, 1)));
        resetBtn.setContentAreaFilled(false);
        resetBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        resetBtn.setOpaque(true);
        resetBtn.setPreferredSize(new java.awt.Dimension(105, 40));
        resetBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                resetBtnMouseClicked(evt);
            }
        });
        jPanel1.add(resetBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 300, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 640, 360));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JField36MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField36MouseClicked
        JField36.setText("");
    }//GEN-LAST:event_JField36MouseClicked

    private void JField34MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField34MouseClicked
        JField34.setText("");
    }//GEN-LAST:event_JField34MouseClicked

    private void backBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backBtnMouseClicked
        ForgetPasswordGui.close();
        this.dispose();
    }//GEN-LAST:event_backBtnMouseClicked

    private void resetBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resetBtnMouseClicked
        if(resetUserPasswordBySecurityPhrase(ForgetPasswordGui.JField28.getText(),ForgetPasswordGui.JField34.getText(),JField36.getText(),JField34.getText())) {
            ForgetPasswordGui.close();
            this.dispose();
        }
    }//GEN-LAST:event_resetBtnMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JTextField JField28;
    private javax.swing.JTextField JField34;
    private javax.swing.JTextField JField36;
    private javax.swing.JSeparator JSeparator25;
    private javax.swing.JSeparator JSeparator30;
    private javax.swing.JSeparator JSeparator37;
    private javax.swing.JButton backBtn;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel menuBtn18;
    private javax.swing.JButton resetBtn;
    // End of variables declaration//GEN-END:variables
}
