/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.project.ui.administrator;

import com.project.common.constants.UserRoleType;
import com.project.controller.UserAccountController;
import com.project.ui.authentication.LoginGui;
import com.project.ui.authentication.UserProfileGui;

import java.awt.*;

/**
 * User Interface - Admin
 * @author AU YIK HOE
 * @version 1.0, Last edited on 2024-04-17
 * @since 2024-03-27
 */
public class AdminGui extends javax.swing.JFrame {

    private static Color DefaultBtnColor = new Color(245,245,245);
    private static Color ClickedBtnColor = new Color(230,230,230);

    /**
     * Creates new form AdminGui
     */
    public AdminGui() {
        if (!UserAccountController.checkUserAuthorization(UserRoleType.ADMIN)) {
            new LoginGui();
            this.dispose();
        } else {
            setTitle("Project Management System - Admin Panel");
            setVisible(true);
            initComponents();
            ButtonClicked("dashboard");
        }
    }

    public static void ButtonClicked(String index){
        WindowsPane.removeAll();
        switch (index) {
            case "dashboard" -> {
                menuBtn1.setBackground(ClickedBtnColor);
                menuBtn2.setBackground(DefaultBtnColor);
                menuBtn4.setBackground(DefaultBtnColor);
                menuBtn5.setBackground(DefaultBtnColor);
                menuBtn6.setBackground(DefaultBtnColor);
                menuBtn10.setBackground(DefaultBtnColor);
                menuBtn11.setBackground(DefaultBtnColor);
                WindowsPane.add(new AdminDashboard()).setVisible(true);
            }
            case "student" -> {
                menuBtn1.setBackground(DefaultBtnColor);
                menuBtn2.setBackground(ClickedBtnColor);
                menuBtn4.setBackground(DefaultBtnColor);
                menuBtn5.setBackground(DefaultBtnColor);
                menuBtn6.setBackground(DefaultBtnColor);
                menuBtn10.setBackground(DefaultBtnColor);
                menuBtn11.setBackground(DefaultBtnColor);
                WindowsPane.add(new StudentManagement()).setVisible(true);
            }
            case "staff" -> {
                menuBtn1.setBackground(DefaultBtnColor);
                menuBtn2.setBackground(DefaultBtnColor);
                menuBtn4.setBackground(ClickedBtnColor);
                menuBtn5.setBackground(DefaultBtnColor);
                menuBtn10.setBackground(DefaultBtnColor);
                menuBtn11.setBackground(DefaultBtnColor);
                WindowsPane.add(new StaffManagement()).setVisible(true);
            }
            case "profile" -> {
                menuBtn1.setBackground(DefaultBtnColor);
                menuBtn2.setBackground(DefaultBtnColor);
                menuBtn4.setBackground(DefaultBtnColor);
                menuBtn5.setBackground(ClickedBtnColor);
                menuBtn10.setBackground(DefaultBtnColor);
                menuBtn11.setBackground(DefaultBtnColor);
                WindowsPane.add(new UserProfileGui()).setVisible(true);
            }
            case "log" -> {
                menuBtn1.setBackground(DefaultBtnColor);
                menuBtn2.setBackground(DefaultBtnColor);
                menuBtn4.setBackground(DefaultBtnColor);
                menuBtn5.setBackground(DefaultBtnColor);
                menuBtn10.setBackground(ClickedBtnColor);
                menuBtn11.setBackground(DefaultBtnColor);
                WindowsPane.add(new SystemLog()).setVisible(true);
            }
            case "intakes" -> {
                menuBtn1.setBackground(DefaultBtnColor);
                menuBtn2.setBackground(DefaultBtnColor);
                menuBtn4.setBackground(DefaultBtnColor);
                menuBtn5.setBackground(DefaultBtnColor);
                menuBtn6.setBackground(DefaultBtnColor);
                menuBtn10.setBackground(DefaultBtnColor);
                menuBtn11.setBackground(ClickedBtnColor);
                WindowsPane.add(new intakeManagement()).setVisible(true);
            }
            case "addStudent" -> {
                WindowsPane.add(new AddStudent()).setVisible(true);
            }
            case "addStaff" -> {
                WindowsPane.add(new AddStaff()).setVisible(true);
            }
        }
    }

    /**
     * open edit student panel
     * @param studentId
     */
    public static void editStudent(int studentId) {
        WindowsPane.removeAll();
        WindowsPane.add(new EditStudent(studentId)).setVisible(true);
    }

    /**
     * open edit staff panel
     * @param staffId
     * @param userRole
     */
    public static void editStaff(int staffId, String userRole) {
        WindowsPane.removeAll();
        WindowsPane.add(new EditStaff(staffId, userRole)).setVisible(true);
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
        menuBtn2 = new javax.swing.JLabel();
        menuBtn4 = new javax.swing.JLabel();
        menuBtn5 = new javax.swing.JLabel();
        menuBtn6 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        menuBtn10 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        menuBtn11 = new javax.swing.JLabel();
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

        menuBtn2.setBackground(new java.awt.Color(245, 245, 245));
        menuBtn2.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn2.setText("STUDENTS");
        menuBtn2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuBtn2.setOpaque(true);
        menuBtn2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuBtn2MouseClicked(evt);
            }
        });

        menuBtn4.setBackground(new java.awt.Color(245, 245, 245));
        menuBtn4.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn4.setText("STAFFS");
        menuBtn4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuBtn4.setOpaque(true);
        menuBtn4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuBtn4MouseClicked(evt);
            }
        });

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

        jSeparator1.setForeground(new java.awt.Color(230, 230, 230));

        jSeparator4.setForeground(new java.awt.Color(230, 230, 230));

        jLabel3.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        jLabel3.setText("  User Management");

        jLabel4.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        jLabel4.setText("  Admin Home");

        jSeparator5.setForeground(new java.awt.Color(230, 230, 230));

        jLabel5.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        jLabel5.setText(" Intake Management");

        jSeparator6.setForeground(new java.awt.Color(230, 230, 230));

        menuBtn10.setBackground(new java.awt.Color(245, 245, 245));
        menuBtn10.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn10.setText("LOGS");
        menuBtn10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuBtn10.setOpaque(true);
        menuBtn10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuBtn10MouseClicked(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        jLabel6.setText(" System Log");

        jSeparator2.setForeground(new java.awt.Color(230, 230, 230));

        menuBtn11.setBackground(new java.awt.Color(245, 245, 245));
        menuBtn11.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn11.setText("NEW INTAKE");
        menuBtn11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuBtn11.setOpaque(true);
        menuBtn11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuBtn11MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout MenuPanelLayout = new javax.swing.GroupLayout(MenuPanel);
        MenuPanel.setLayout(MenuPanelLayout);
        MenuPanelLayout.setHorizontalGroup(
            MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menuBtn1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(menuBtn5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(menuBtn6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(menuBtn2, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jSeparator4)
            .addComponent(jSeparator5)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator2)
            .addComponent(menuBtn10, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
            .addComponent(menuBtn11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator6)
            .addComponent(menuBtn4, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        MenuPanelLayout.setVerticalGroup(
            MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuPanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(menuBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(menuBtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(menuBtn4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(menuBtn11, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(menuBtn10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 185, Short.MAX_VALUE)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(menuBtn5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(menuBtn6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void menuBtn6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuBtn6MouseClicked
        UserAccountController.logout();
        this.dispose();
        new LoginGui();
    }//GEN-LAST:event_menuBtn6MouseClicked

    private void menuBtn5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuBtn5MouseClicked
        ButtonClicked("profile");
    }//GEN-LAST:event_menuBtn5MouseClicked

    private void menuBtn2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuBtn2MouseClicked
        ButtonClicked("student");
    }//GEN-LAST:event_menuBtn2MouseClicked

    private void menuBtn1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuBtn1MouseClicked
        ButtonClicked("dashboard");
    }//GEN-LAST:event_menuBtn1MouseClicked

    private void menuBtn10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuBtn10MouseClicked
        ButtonClicked("log");
    }//GEN-LAST:event_menuBtn10MouseClicked

    private void menuBtn11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuBtn11MouseClicked
        ButtonClicked("intakes");
    }//GEN-LAST:event_menuBtn11MouseClicked

    private void menuBtn4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuBtn4MouseClicked
        ButtonClicked("staff");
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
            java.util.logging.Logger.getLogger(AdminGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminGui().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Header;
    private javax.swing.JLabel Logo;
    private javax.swing.JLabel LogoTitle_1;
    private javax.swing.JPanel MenuPanel;
    private static javax.swing.JDesktopPane WindowsPane;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private static javax.swing.JLabel menuBtn1;
    private static javax.swing.JLabel menuBtn10;
    private static javax.swing.JLabel menuBtn11;
    private static javax.swing.JLabel menuBtn2;
    private static javax.swing.JLabel menuBtn4;
    private static javax.swing.JLabel menuBtn5;
    private static javax.swing.JLabel menuBtn6;
    // End of variables declaration//GEN-END:variables
}
