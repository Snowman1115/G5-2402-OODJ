/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.project.ui.lecturer;

import com.project.ui.administrator.*;
import com.project.common.constants.UserRoleType;
import com.project.controller.UserAccountController;
import com.project.ui.authentication.LoginGui;
import com.project.ui.authentication.UserProfileGui;

import java.awt.Color;

/**
 *
 * @author chanh
 */
public class LecturerGui extends javax.swing.JFrame {

    private static Color DefaultBtnColor = new Color(245,245,245);
    private static Color ClickedBtnColor = new Color(230,230,230);
    
    /**
     * Creates new form AdminGui
     */
    public LecturerGui() {
        if (!UserAccountController.checkUserAuthorization(UserRoleType.LECTURER)) {
            new LoginGui();
            this.dispose();
        } else {
            setTitle("Project Management System - Admin Panel");
            setVisible(true);
            initComponents();
            ButtonClicked("menuBtn1");
        }
    }
    
    public static void ButtonClicked(String index){
        WindowsPane.removeAll();
        switch (index) {
//            case "menuBtn1" -> {
//                menuBtn1.setBackground(ClickedBtnColor);
//                menuBtn2.setBackground(DefaultBtnColor);
//                menuBtn3.setBackground(DefaultBtnColor);
//                menuBtn4.setBackground(DefaultBtnColor);
//                menuBtn5.setBackground(DefaultBtnColor);
//                menuBtn6.setBackground(DefaultBtnColor);
//                menuBtn7.setBackground(DefaultBtnColor);
//                menuBtn8.setBackground(DefaultBtnColor);
//                menuBtn9.setBackground(DefaultBtnColor);
//                menuBtn10.setBackground(DefaultBtnColor);
//                WindowsPane.add(new AdminDashboard()).setVisible(true);
//            } 
//            case "menuBtn2" -> {
//                menuBtn1.setBackground(DefaultBtnColor);
//                menuBtn2.setBackground(ClickedBtnColor);
//                menuBtn3.setBackground(DefaultBtnColor);
//                menuBtn4.setBackground(DefaultBtnColor);
//                menuBtn5.setBackground(DefaultBtnColor);
//                menuBtn6.setBackground(DefaultBtnColor);
//                menuBtn7.setBackground(DefaultBtnColor);
//                menuBtn8.setBackground(DefaultBtnColor);
//                menuBtn9.setBackground(DefaultBtnColor);
//                menuBtn10.setBackground(DefaultBtnColor);
//                // WindowsPane.add(new AdminOfficerManagement()).setVisible(true);
//            } 
//            case "menuBtn3" -> {
//                menuBtn1.setBackground(DefaultBtnColor);
//                menuBtn2.setBackground(DefaultBtnColor);
//                menuBtn3.setBackground(ClickedBtnColor);
//                menuBtn4.setBackground(DefaultBtnColor);
//                menuBtn5.setBackground(DefaultBtnColor);
//                menuBtn6.setBackground(DefaultBtnColor);
//                menuBtn7.setBackground(DefaultBtnColor);
//                menuBtn8.setBackground(DefaultBtnColor);
//                menuBtn9.setBackground(DefaultBtnColor);
//                menuBtn10.setBackground(DefaultBtnColor);
//                // WindowsPane.add(new AdminSalespersonManagement()).setVisible(true);
//            } 
//            case "menuBtn4" -> {
//                menuBtn1.setBackground(DefaultBtnColor);
//                menuBtn2.setBackground(DefaultBtnColor);
//                menuBtn3.setBackground(DefaultBtnColor);
//                menuBtn4.setBackground(ClickedBtnColor);
//                menuBtn5.setBackground(DefaultBtnColor);
//                menuBtn6.setBackground(DefaultBtnColor);
//                menuBtn7.setBackground(DefaultBtnColor);
//                menuBtn8.setBackground(DefaultBtnColor);
//                menuBtn9.setBackground(DefaultBtnColor);
//                menuBtn10.setBackground(DefaultBtnColor);
//                // WindowsPane.add(new AdminManagement()).setVisible(true);
//            } 
//            case "menuBtn5" -> {
//                menuBtn1.setBackground(DefaultBtnColor);
//                menuBtn2.setBackground(DefaultBtnColor);
//                menuBtn3.setBackground(DefaultBtnColor);
//                menuBtn4.setBackground(DefaultBtnColor);
//                menuBtn5.setBackground(ClickedBtnColor);
//                menuBtn6.setBackground(DefaultBtnColor);
//                menuBtn7.setBackground(DefaultBtnColor);
//                menuBtn8.setBackground(DefaultBtnColor);
//                menuBtn9.setBackground(DefaultBtnColor);
//                menuBtn10.setBackground(DefaultBtnColor);
//                WindowsPane.add(new UserProfileGui()).setVisible(true);
//            } 
//            case "menuBtn6" -> {
//                menuBtn1.setBackground(DefaultBtnColor);
//                menuBtn2.setBackground(DefaultBtnColor);
//                menuBtn3.setBackground(DefaultBtnColor);
//                menuBtn4.setBackground(DefaultBtnColor);
//                menuBtn5.setBackground(DefaultBtnColor);
//                menuBtn6.setBackground(ClickedBtnColor);
//                menuBtn7.setBackground(DefaultBtnColor);
//                menuBtn8.setBackground(DefaultBtnColor);
//                menuBtn9.setBackground(DefaultBtnColor);
//                menuBtn10.setBackground(DefaultBtnColor);
//            } 
//            case "menuBtn7" -> {
//                menuBtn1.setBackground(DefaultBtnColor);
//                menuBtn2.setBackground(DefaultBtnColor);
//                menuBtn3.setBackground(DefaultBtnColor);
//                menuBtn4.setBackground(DefaultBtnColor);
//                menuBtn5.setBackground(DefaultBtnColor);
//                menuBtn6.setBackground(DefaultBtnColor);
//                menuBtn7.setBackground(ClickedBtnColor);
//                menuBtn8.setBackground(DefaultBtnColor);
//                menuBtn9.setBackground(DefaultBtnColor);
//                menuBtn10.setBackground(DefaultBtnColor);
//                // WindowsPane.add(new SalesGui()).setVisible(true);
//            } 
//            case "menuBtn8" -> {
//                menuBtn1.setBackground(DefaultBtnColor);
//                menuBtn2.setBackground(DefaultBtnColor);
//                menuBtn3.setBackground(DefaultBtnColor);
//                menuBtn4.setBackground(DefaultBtnColor);
//                menuBtn5.setBackground(DefaultBtnColor);
//                menuBtn6.setBackground(DefaultBtnColor);
//                menuBtn7.setBackground(DefaultBtnColor);
//                menuBtn8.setBackground(ClickedBtnColor);
//                menuBtn9.setBackground(DefaultBtnColor);
//                menuBtn10.setBackground(DefaultBtnColor);
//                // WindowsPane.add(new FurnitureGui()).setVisible(true);
//            } 
//            case "menuBtn9" -> {
//                menuBtn1.setBackground(DefaultBtnColor);
//                menuBtn2.setBackground(DefaultBtnColor);
//                menuBtn3.setBackground(DefaultBtnColor);
//                menuBtn4.setBackground(DefaultBtnColor);
//                menuBtn5.setBackground(DefaultBtnColor);
//                menuBtn6.setBackground(DefaultBtnColor);
//                menuBtn7.setBackground(DefaultBtnColor);
//                menuBtn8.setBackground(DefaultBtnColor);
//                menuBtn9.setBackground(ClickedBtnColor);
//                menuBtn10.setBackground(DefaultBtnColor);
//                // WindowsPane.add(new SuperAdminManagement()).setVisible(true);
//            } 
//            case "menuBtn10" -> {
//                menuBtn1.setBackground(DefaultBtnColor);
//                menuBtn2.setBackground(DefaultBtnColor);
//                menuBtn3.setBackground(DefaultBtnColor);
//                menuBtn4.setBackground(DefaultBtnColor);
//                menuBtn5.setBackground(DefaultBtnColor);
//                menuBtn6.setBackground(DefaultBtnColor);
//                menuBtn7.setBackground(DefaultBtnColor);
//                menuBtn8.setBackground(DefaultBtnColor);
//                menuBtn9.setBackground(DefaultBtnColor);
//                menuBtn10.setBackground(ClickedBtnColor);
//                // WindowsPane.add(new OrderGui()).setVisible(true);
//            } 
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

        Header = new javax.swing.JPanel();
        LogoTitle_1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        Logo = new javax.swing.JLabel();
        MenuPanel = new javax.swing.JPanel();
        dashboardBtn = new javax.swing.JLabel();
        managePresentationBtn = new javax.swing.JLabel();
        profileBtn = new javax.swing.JLabel();
        manageSubmissionBtn = new javax.swing.JLabel();
        manageSuperviseeBtn = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        manageConsultationBtn = new javax.swing.JLabel();
        WindowsPane = new javax.swing.JDesktopPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Header.setBackground(new java.awt.Color(164, 196, 181));
        Header.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LogoTitle_1.setBackground(new java.awt.Color(255, 255, 255));
        LogoTitle_1.setFont(new java.awt.Font("Alibaba PuHuiTi H", 1, 20)); // NOI18N
        LogoTitle_1.setForeground(new java.awt.Color(255, 255, 255));
        LogoTitle_1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LogoTitle_1.setText("AGH PMS");
        Header.add(LogoTitle_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 0, 150, 60));

        jPanel1.setBackground(new Color(255,255,255,50));

        Logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/PMS Logo Small.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(Logo, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(124, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(Logo, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        Header.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 60));

        getContentPane().add(Header, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 60));

        MenuPanel.setBackground(new java.awt.Color(245, 245, 245));

        dashboardBtn.setBackground(new java.awt.Color(245, 245, 245));
        dashboardBtn.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        dashboardBtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dashboardBtn.setText("DASHBOARD");
        dashboardBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        dashboardBtn.setOpaque(true);
        dashboardBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dashboardBtnMouseClicked(evt);
            }
        });

        managePresentationBtn.setBackground(new java.awt.Color(245, 245, 245));
        managePresentationBtn.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        managePresentationBtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        managePresentationBtn.setText("MANAGE PRESENTATION");
        managePresentationBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        managePresentationBtn.setOpaque(true);
        managePresentationBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                managePresentationBtnMouseClicked(evt);
            }
        });

        profileBtn.setBackground(new java.awt.Color(245, 245, 245));
        profileBtn.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        profileBtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        profileBtn.setText("PROFILE");
        profileBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        profileBtn.setOpaque(true);
        profileBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                profileBtnMouseClicked(evt);
            }
        });

        manageSubmissionBtn.setBackground(new java.awt.Color(245, 245, 245));
        manageSubmissionBtn.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        manageSubmissionBtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        manageSubmissionBtn.setText("MANAGE SUBMISSION");
        manageSubmissionBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        manageSubmissionBtn.setOpaque(true);
        manageSubmissionBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                manageSubmissionBtnMouseClicked(evt);
            }
        });

        manageSuperviseeBtn.setBackground(new java.awt.Color(245, 245, 245));
        manageSuperviseeBtn.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        manageSuperviseeBtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        manageSuperviseeBtn.setText("MANAGE SUPERVISEE");
        manageSuperviseeBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        manageSuperviseeBtn.setOpaque(true);
        manageSuperviseeBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                manageSuperviseeBtnMouseClicked(evt);
            }
        });

        jSeparator1.setForeground(new java.awt.Color(230, 230, 230));

        jSeparator2.setForeground(new java.awt.Color(230, 230, 230));

        jSeparator3.setForeground(new java.awt.Color(230, 230, 230));

        jSeparator4.setForeground(new java.awt.Color(230, 230, 230));

        jLabel1.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        jLabel1.setText("  Report Submission Management");

        jLabel2.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        jLabel2.setText("  Supervisee Management");

        jLabel3.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        jLabel3.setText("  Presentation Management");

        jLabel4.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        jLabel4.setText("  Lecturer Home");

        jSeparator5.setForeground(new java.awt.Color(230, 230, 230));

        jLabel5.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        jLabel5.setText("  Consultation Management");

        jSeparator6.setForeground(new java.awt.Color(230, 230, 230));

        manageConsultationBtn.setBackground(new java.awt.Color(245, 245, 245));
        manageConsultationBtn.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        manageConsultationBtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        manageConsultationBtn.setText("MANAGE CONSULTATION");
        manageConsultationBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        manageConsultationBtn.setOpaque(true);
        manageConsultationBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                manageConsultationBtnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout MenuPanelLayout = new javax.swing.GroupLayout(MenuPanel);
        MenuPanel.setLayout(MenuPanelLayout);
        MenuPanelLayout.setHorizontalGroup(
            MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dashboardBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(profileBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(manageSuperviseeBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(managePresentationBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator2)
            .addComponent(manageSubmissionBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator3)
            .addComponent(jSeparator4)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator5)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator6, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MenuPanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(manageConsultationBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        MenuPanelLayout.setVerticalGroup(
            MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuPanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(dashboardBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(manageSuperviseeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(manageSubmissionBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(managePresentationBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(manageConsultationBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 214, Short.MAX_VALUE)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(profileBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );

        getContentPane().add(MenuPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 200, 660));

        WindowsPane.setForeground(new java.awt.Color(50, 50, 50));
        WindowsPane.setFocusable(false);

        javax.swing.GroupLayout WindowsPaneLayout = new javax.swing.GroupLayout(WindowsPane);
        WindowsPane.setLayout(WindowsPaneLayout);
        WindowsPaneLayout.setHorizontalGroup(
            WindowsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1080, Short.MAX_VALUE)
        );
        WindowsPaneLayout.setVerticalGroup(
            WindowsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 660, Short.MAX_VALUE)
        );

        getContentPane().add(WindowsPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 60, 1080, 660));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void manageSuperviseeBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageSuperviseeBtnMouseClicked
        ButtonClicked("menuBtn8");
    }//GEN-LAST:event_manageSuperviseeBtnMouseClicked

    private void manageSubmissionBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageSubmissionBtnMouseClicked
        ButtonClicked("menuBtn7");
    }//GEN-LAST:event_manageSubmissionBtnMouseClicked

    private void profileBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profileBtnMouseClicked
        ButtonClicked("menuBtn5");
    }//GEN-LAST:event_profileBtnMouseClicked

    private void managePresentationBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_managePresentationBtnMouseClicked
        ButtonClicked("menuBtn2");
    }//GEN-LAST:event_managePresentationBtnMouseClicked

    private void dashboardBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashboardBtnMouseClicked
        ButtonClicked("menuBtn1");
    }//GEN-LAST:event_dashboardBtnMouseClicked

    private void manageConsultationBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageConsultationBtnMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_manageConsultationBtnMouseClicked

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
            java.util.logging.Logger.getLogger(LecturerGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LecturerGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LecturerGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LecturerGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LecturerGui().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Header;
    private javax.swing.JLabel Logo;
    private javax.swing.JLabel LogoTitle_1;
    private javax.swing.JPanel MenuPanel;
    private static javax.swing.JDesktopPane WindowsPane;
    private javax.swing.JLabel dashboardBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private static javax.swing.JLabel manageConsultationBtn;
    private javax.swing.JLabel managePresentationBtn;
    private javax.swing.JLabel manageSubmissionBtn;
    private javax.swing.JLabel manageSuperviseeBtn;
    private javax.swing.JLabel profileBtn;
    // End of variables declaration//GEN-END:variables
}
