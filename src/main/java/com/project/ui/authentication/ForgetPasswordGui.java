/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

package com.project.ui.authentication;

import com.project.controller.authentication.UserAccountController;

/**
 *
 * @author Olaf
 */
public class ForgetPasswordGui extends javax.swing.JFrame {
    
    private static ForgetPasswordGui instance;
    
    /** Creates new form ResetPassword */
    public ForgetPasswordGui() {
        if (instance == null) {
            instance = this;
            setTitle("Project Management System - Reset Password");
            initComponents();
            DesktopPane.setVisible(false);
            setVisible(true); 
        }     
           
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MainPanel = new javax.swing.JPanel();
        menuBtn18 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        JField28 = new javax.swing.JTextField();
        JSeparator25 = new javax.swing.JSeparator();
        JField34 = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        JSeparator30 = new javax.swing.JSeparator();
        resetBtn = new javax.swing.JButton();
        backBtn = new javax.swing.JButton();
        DesktopPane = new javax.swing.JDesktopPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        MainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menuBtn18.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn18.setText("RESET PASSWORD");
        menuBtn18.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn18.setOpaque(true);
        MainPanel.add(menuBtn18, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 290, 40));

        jLabel32.setBackground(new java.awt.Color(105, 105, 105));
        jLabel32.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(1, 1, 1));
        jLabel32.setText("USERNAME");
        MainPanel.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, -1));

        JField28.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField28.setForeground(new java.awt.Color(1, 1, 1));
        JField28.setText("Please enter your username / email.");
        JField28.setBorder(null);
        JField28.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        JField28.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField28.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField28MouseClicked(evt);
            }
        });
        MainPanel.add(JField28, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 580, 35));

        JSeparator25.setForeground(new java.awt.Color(1, 1, 1));
        MainPanel.add(JSeparator25, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 580, 10));

        JField34.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField34.setForeground(new java.awt.Color(1, 1, 1));
        JField34.setText("Please enter your security phrase.");
        JField34.setBorder(null);
        JField34.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        JField34.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField34.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField34MouseClicked(evt);
            }
        });
        MainPanel.add(JField34, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 580, 35));

        jLabel37.setBackground(new java.awt.Color(105, 105, 105));
        jLabel37.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(1, 1, 1));
        jLabel37.setText("SECURITY PHRASE");
        MainPanel.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, -1, -1));

        JSeparator30.setForeground(new java.awt.Color(1, 1, 1));
        MainPanel.add(JSeparator30, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 580, -1));

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
        MainPanel.add(resetBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 300, -1, -1));

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
        MainPanel.add(backBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 300, -1, -1));

        getContentPane().add(MainPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 640, 360));
        getContentPane().add(DesktopPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 640, 360));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void JField28MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField28MouseClicked
        JField28.setText("");
    }//GEN-LAST:event_JField28MouseClicked

    private void JField34MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField34MouseClicked
        JField34.setText("");
    }//GEN-LAST:event_JField34MouseClicked

    private void resetBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resetBtnMouseClicked
        String username = UserAccountController.verifyUserSecurityPhrase(JField28.getText(), JField34.getText());
        if(username != null) {
            MainPanel.setVisible(false);
            DesktopPane.setVisible(true);
            DesktopPane.add(new SetNewPasswordGui()).setVisible(true);
            SetNewPasswordGui.JField28.setText(username);
        }
    }//GEN-LAST:event_resetBtnMouseClicked

    private void backBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backBtnMouseClicked
        LoginGui.unFreeze();
        instance.dispose();
        instance = null;
    }//GEN-LAST:event_backBtnMouseClicked
    
    public static void close() {
        LoginGui.unFreeze();
        instance.dispose();
        instance = null;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ForgetPasswordGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ForgetPasswordGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ForgetPasswordGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ForgetPasswordGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ForgetPasswordGui().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JDesktopPane DesktopPane;
    public static javax.swing.JTextField JField28;
    public static javax.swing.JTextField JField34;
    private javax.swing.JSeparator JSeparator25;
    private javax.swing.JSeparator JSeparator30;
    private javax.swing.JPanel MainPanel;
    private javax.swing.JButton backBtn;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel menuBtn18;
    private javax.swing.JButton resetBtn;
    // End of variables declaration//GEN-END:variables

}