/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.project.ui.project_manager;

import com.project.common.constants.UserRoleType;
import com.project.controller.UserAccountController;
import com.project.ui.authentication.LoginGui;
import com.project.ui.authentication.UserProfileGui;

import java.awt.Color;

/**
 *
 * @author chanh
 */
public class ProjectManagerGui extends javax.swing.JFrame {

    private static Color DefaultBtnColor = new Color(245,245,245);
    private static Color ClickedBtnColor = new Color(230,230,230);
    
    /**
     * Creates new form AdminGui
     */
    public ProjectManagerGui() {
        if (!UserAccountController.checkUserAuthorization(UserRoleType.PROJECT_MANAGER)) {
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
            case "menuBtn1" -> {
                menuBtn1.setBackground(ClickedBtnColor);
                menuBtn3.setBackground(DefaultBtnColor);
                menuBtn3.setBackground(DefaultBtnColor);
                menuBtn4.setBackground(DefaultBtnColor);
                menuBtn5.setBackground(DefaultBtnColor);
                menuBtn6.setBackground(DefaultBtnColor);
                menuBtn2.setBackground(DefaultBtnColor);
//                menuBtn8.setBackground(DefaultBtnColor);
//                menuBtn9.setBackground(DefaultBtnColor);
//                menuBtn10.setBackground(DefaultBtnColor);
                WindowsPane.add(new ProjectManagerDashboard()).setVisible(true);
            } 
            case "menuBtn2" -> {
                menuBtn1.setBackground(DefaultBtnColor);
                menuBtn3.setBackground(DefaultBtnColor);
                menuBtn3.setBackground(DefaultBtnColor);
                menuBtn4.setBackground(DefaultBtnColor);
                menuBtn5.setBackground(DefaultBtnColor);
                menuBtn6.setBackground(DefaultBtnColor);
                menuBtn2.setBackground(ClickedBtnColor);
//                menuBtn8.setBackground(DefaultBtnColor);
//                menuBtn9.setBackground(DefaultBtnColor);
//                menuBtn10.setBackground(DefaultBtnColor);
                WindowsPane.add(new ManagerManageFeedback()).setVisible(true);
            } 
            case "menuBtn3" -> {
                menuBtn1.setBackground(DefaultBtnColor);
                menuBtn3.setBackground(DefaultBtnColor);
                menuBtn3.setBackground(ClickedBtnColor);
                menuBtn4.setBackground(DefaultBtnColor);
                menuBtn5.setBackground(DefaultBtnColor);
                menuBtn6.setBackground(DefaultBtnColor);
                menuBtn2.setBackground(DefaultBtnColor);
//                menuBtn8.setBackground(DefaultBtnColor);
//                menuBtn9.setBackground(DefaultBtnColor);
//                menuBtn10.setBackground(DefaultBtnColor);
                 WindowsPane.add(new ManagerViewReport()).setVisible(true);
            } 
            case "menuBtn4" -> {
                menuBtn1.setBackground(DefaultBtnColor);
                menuBtn3.setBackground(DefaultBtnColor);
                menuBtn3.setBackground(DefaultBtnColor);
                menuBtn4.setBackground(ClickedBtnColor);
                menuBtn5.setBackground(DefaultBtnColor);
                menuBtn6.setBackground(DefaultBtnColor);
                menuBtn2.setBackground(DefaultBtnColor);
//                menuBtn8.setBackground(DefaultBtnColor);
//                menuBtn9.setBackground(DefaultBtnColor);
//                menuBtn10.setBackground(DefaultBtnColor);
                WindowsPane.add(new ManagerModuleGui()).setVisible(true);
            } 
//            case "menuBtn5" -> {
//                menuBtn1.setBackground(DefaultBtnColor);
//                menuBtn3.setBackground(DefaultBtnColor);
//                menuBtn3.setBackground(DefaultBtnColor);
//                menuBtn4.setBackground(DefaultBtnColor);
//                menuBtn5.setBackground(ClickedBtnColor);
//                menuBtn6.setBackground(DefaultBtnColor);
//                menuBtn2.setBackground(DefaultBtnColor);
//                menuBtn8.setBackground(DefaultBtnColor);
//                menuBtn9.setBackground(DefaultBtnColor);
//                menuBtn10.setBackground(DefaultBtnColor);
//                WindowsPane.add(new UserProfileGui()).setVisible(true);
//            } 
//            case "menuBtn6" -> {
//                menuBtn1.setBackground(DefaultBtnColor);
//                menuBtn3.setBackground(DefaultBtnColor);
//                menuBtn3.setBackground(DefaultBtnColor);
//                menuBtn4.setBackground(DefaultBtnColor);
//                menuBtn5.setBackground(DefaultBtnColor);
//                menuBtn6.setBackground(ClickedBtnColor);
//                menuBtn2.setBackground(DefaultBtnColor);
//                menuBtn8.setBackground(DefaultBtnColor);
//                menuBtn9.setBackground(DefaultBtnColor);
//                menuBtn10.setBackground(DefaultBtnColor);
//            } 
//            case "menuBtn7" -> {
//                menuBtn1.setBackground(DefaultBtnColor);
//                menuBtn3.setBackground(DefaultBtnColor);
//                menuBtn3.setBackground(DefaultBtnColor);
//                menuBtn4.setBackground(DefaultBtnColor);
//                menuBtn5.setBackground(DefaultBtnColor);
//                menuBtn6.setBackground(DefaultBtnColor);
//                menuBtn2.setBackground(ClickedBtnColor);
//                menuBtn8.setBackground(DefaultBtnColor);
//                menuBtn9.setBackground(DefaultBtnColor);
//                menuBtn10.setBackground(DefaultBtnColor);
//                // WindowsPane.add(new SalesGui()).setVisible(true);
//            } 
//            case "menuBtn8" -> {
//                menuBtn1.setBackground(DefaultBtnColor);
//                menuBtn3.setBackground(DefaultBtnColor);
//                menuBtn3.setBackground(DefaultBtnColor);
//                menuBtn4.setBackground(DefaultBtnColor);
//                menuBtn5.setBackground(DefaultBtnColor);
//                menuBtn6.setBackground(DefaultBtnColor);
//                menuBtn2.setBackground(DefaultBtnColor);
//                menuBtn8.setBackground(ClickedBtnColor);
//                menuBtn9.setBackground(DefaultBtnColor);
//                menuBtn10.setBackground(DefaultBtnColor);
//                // WindowsPane.add(new FurnitureGui()).setVisible(true);
//            } 
//            case "menuBtn9" -> {
//                menuBtn1.setBackground(DefaultBtnColor);
//                menuBtn3.setBackground(DefaultBtnColor);
//                menuBtn3.setBackground(DefaultBtnColor);
//                menuBtn4.setBackground(DefaultBtnColor);
//                menuBtn5.setBackground(DefaultBtnColor);
//                menuBtn6.setBackground(DefaultBtnColor);
//                menuBtn2.setBackground(DefaultBtnColor);
//                menuBtn8.setBackground(DefaultBtnColor);
//                menuBtn9.setBackground(ClickedBtnColor);
//                menuBtn10.setBackground(DefaultBtnColor);
//                // WindowsPane.add(new SuperAdminManagement()).setVisible(true);
//            } 
//            case "menuBtn10" -> {
//                menuBtn1.setBackground(DefaultBtnColor);
//                menuBtn3.setBackground(DefaultBtnColor);
//                menuBtn3.setBackground(DefaultBtnColor);
//                menuBtn4.setBackground(DefaultBtnColor);
//                menuBtn5.setBackground(DefaultBtnColor);
//                menuBtn6.setBackground(DefaultBtnColor);
//                menuBtn2.setBackground(DefaultBtnColor);
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
        menuBtn1 = new javax.swing.JLabel();
        menuBtn3 = new javax.swing.JLabel();
        menuBtn5 = new javax.swing.JLabel();
        menuBtn6 = new javax.swing.JLabel();
        menuBtn2 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        menuBtn4 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
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
        MenuPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menuBtn1.setBackground(new java.awt.Color(245, 245, 245));
        menuBtn1.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn1.setText("DASHBOARD");
        menuBtn1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuBtn1.setOpaque(true);
        menuBtn1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuBtn1MouseClicked(evt);
            }
        });
        MenuPanel.add(menuBtn1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 207, 40));

        menuBtn3.setBackground(new java.awt.Color(245, 245, 245));
        menuBtn3.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn3.setText("STUDENT REPORT");
        menuBtn3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuBtn3.setOpaque(true);
        menuBtn3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuBtn3MouseClicked(evt);
            }
        });
        MenuPanel.add(menuBtn3, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 231, 201, 40));

        menuBtn5.setBackground(new java.awt.Color(245, 245, 245));
        menuBtn5.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn5.setText("PROFILE");
        menuBtn5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuBtn5.setOpaque(true);
        menuBtn5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuBtn5MouseClicked(evt);
            }
        });
        MenuPanel.add(menuBtn5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 580, 207, 40));

        menuBtn6.setBackground(new java.awt.Color(245, 245, 245));
        menuBtn6.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn6.setText("LOGOUT");
        menuBtn6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuBtn6.setOpaque(true);
        menuBtn6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuBtn6MouseClicked(evt);
            }
        });
        MenuPanel.add(menuBtn6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 620, 207, 40));

        menuBtn2.setBackground(new java.awt.Color(245, 245, 245));
        menuBtn2.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn2.setText("STUDENT FEEDBACK");
        menuBtn2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuBtn2.setOpaque(true);
        menuBtn2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuBtn2MouseClicked(evt);
            }
        });
        MenuPanel.add(menuBtn2, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 274, 201, 40));

        jSeparator2.setForeground(new java.awt.Color(230, 230, 230));
        MenuPanel.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 106, 1, -1));

        jSeparator3.setForeground(new java.awt.Color(230, 230, 230));
        MenuPanel.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(204, 180, 3, -1));

        jSeparator4.setForeground(new java.awt.Color(230, 230, 230));
        MenuPanel.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 577, 207, -1));

        jLabel3.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        jLabel3.setText("Student Management");
        MenuPanel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 207, 30));

        jLabel4.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        jLabel4.setText("  Project Manager Home");
        MenuPanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 207, 30));

        jSeparator5.setForeground(new java.awt.Color(230, 230, 230));
        MenuPanel.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 207, -1));

        menuBtn4.setBackground(new java.awt.Color(245, 245, 245));
        menuBtn4.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn4.setText("MANAGE MODULE");
        menuBtn4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuBtn4.setOpaque(true);
        menuBtn4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuBtn4MouseClicked(evt);
            }
        });
        MenuPanel.add(menuBtn4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 201, 40));

        jSeparator6.setForeground(new java.awt.Color(230, 230, 230));
        MenuPanel.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 194, -1));

        jLabel2.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        jLabel2.setText("Module Management");
        MenuPanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, -1, 30));

        jSeparator7.setForeground(new java.awt.Color(230, 230, 230));
        MenuPanel.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 195, -1));

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

    private void menuBtn1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuBtn1MouseClicked
        ButtonClicked("menuBtn1");
    }//GEN-LAST:event_menuBtn1MouseClicked

    private void menuBtn3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuBtn3MouseClicked
        ButtonClicked("menuBtn3");
    }//GEN-LAST:event_menuBtn3MouseClicked

    private void menuBtn5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuBtn5MouseClicked
        ButtonClicked("menuBtn5");
    }//GEN-LAST:event_menuBtn5MouseClicked

    private void menuBtn6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuBtn6MouseClicked
        UserAccountController.logout();
        this.dispose();
        new LoginGui();
    }//GEN-LAST:event_menuBtn6MouseClicked

    private void menuBtn2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuBtn2MouseClicked
        ButtonClicked("menuBtn2");
    }//GEN-LAST:event_menuBtn2MouseClicked

    private void menuBtn4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuBtn4MouseClicked
        // TODO add your handling code here:
        ButtonClicked("menuBtn4");
    }//GEN-LAST:event_menuBtn4MouseClicked

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
            java.util.logging.Logger.getLogger(ProjectManagerGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProjectManagerGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProjectManagerGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProjectManagerGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ProjectManagerGui().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Header;
    private javax.swing.JLabel Logo;
    private javax.swing.JLabel LogoTitle_1;
    private javax.swing.JPanel MenuPanel;
    private static javax.swing.JDesktopPane WindowsPane;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private static javax.swing.JLabel menuBtn1;
    private static javax.swing.JLabel menuBtn2;
    private static javax.swing.JLabel menuBtn3;
    private static javax.swing.JLabel menuBtn4;
    private static javax.swing.JLabel menuBtn5;
    private static javax.swing.JLabel menuBtn6;
    // End of variables declaration//GEN-END:variables
}
