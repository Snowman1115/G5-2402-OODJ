/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.project.ui.administrator;

import javax.swing.plaf.basic.BasicInternalFrameUI;

/**
 Dashboard - Admin
 * @author AU YIK HOE
 * @version 1.0, Last edited on 2024-05-14
 * @since 2024-03-27
 */
public class AdminDashboard extends javax.swing.JInternalFrame {

    /**
     * Creates new form AdminDashboard
     */
    public AdminDashboard() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI)this.getUI();
        ui.setNorthPane(null);
        // adminPanelVersionLabel.setText(PropertiesReader.getProperty("AdminDashboardVersion"));
        dashboardRefresh();
    }

    public static void dashboardRefresh(){
        // menuBtn12.setText(String.valueOf(UserAccountOperator.checkTotalActiveOfficer()));
        // menuBtn13.setText(String.valueOf(UserAccountOperator.checkTotalActiveSalesPerson()));
        // menuBtn15.setText(String.valueOf(checkTotalFurniture()));
        // menuBtn14.setText(String.valueOf(checkStockAlertsFurniture()));
        // menuBtn16.setText(String.valueOf(SalesOperator.getTotalSalesAmount()));
        // menuBtn17.setText(String.valueOf(SalesOperator.getTotalPendingSales()));
        // menuBtn18.setText(String.valueOf(SalesOperator.getTotalSuccessfulSales()));
        // menuBtn19.setText(String.valueOf(SalesOperator.getTotalCancelledSales()));
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
        jPanel6 = new javax.swing.JPanel();
        lecturersBtn = new javax.swing.JLabel();
        menuBtn28 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        adminBtn = new javax.swing.JLabel();
        menuBtn21 = new javax.swing.JLabel();
        adminPanelVersionLabel = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        studentBtn = new javax.swing.JLabel();
        menuBtn24 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        PMBtn = new javax.swing.JLabel();
        menuBtn26 = new javax.swing.JLabel();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        MainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(254, 254, 254));

        lecturersBtn.setBackground(new java.awt.Color(254, 254, 254));
        lecturersBtn.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 18)); // NOI18N
        lecturersBtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lecturersBtn.setText("0");
        lecturersBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lecturersBtn.setOpaque(true);

        menuBtn28.setBackground(new java.awt.Color(250, 250, 250));
        menuBtn28.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn28.setText("LECTURERS");
        menuBtn28.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn28.setOpaque(true);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lecturersBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(menuBtn28, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(menuBtn28, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lecturersBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        MainPanel.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 50, 450, 250));

        jPanel5.setBackground(new java.awt.Color(254, 254, 254));

        adminBtn.setBackground(new java.awt.Color(254, 254, 254));
        adminBtn.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 18)); // NOI18N
        adminBtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        adminBtn.setText("0");
        adminBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminBtn.setOpaque(true);

        menuBtn21.setBackground(new java.awt.Color(250, 250, 250));
        menuBtn21.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn21.setText("ADMINSTRATORS");
        menuBtn21.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn21.setOpaque(true);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(adminBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(menuBtn21, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(menuBtn21, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(adminBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        MainPanel.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 350, 450, 250));

        adminPanelVersionLabel.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        adminPanelVersionLabel.setForeground(new java.awt.Color(80, 80, 80));
        adminPanelVersionLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        adminPanelVersionLabel.setText("AdminPanel @Version x.x Built On xx xx xxxx");
        adminPanelVersionLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        MainPanel.add(adminPanelVersionLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(455, 628, 620, 30));

        jPanel7.setBackground(new java.awt.Color(254, 254, 254));

        studentBtn.setBackground(new java.awt.Color(254, 254, 254));
        studentBtn.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 18)); // NOI18N
        studentBtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        studentBtn.setText("0");
        studentBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentBtn.setOpaque(true);
        studentBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                studentBtnMouseClicked(evt);
            }
        });

        menuBtn24.setBackground(new java.awt.Color(250, 250, 250));
        menuBtn24.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn24.setText("STUDENTS");
        menuBtn24.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn24.setOpaque(true);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
                jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(studentBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(menuBtn24, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel7Layout.setVerticalGroup(
                jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(menuBtn24, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(studentBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        MainPanel.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 450, 250));

        jPanel8.setBackground(new java.awt.Color(254, 254, 254));

        PMBtn.setBackground(new java.awt.Color(254, 254, 254));
        PMBtn.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 18)); // NOI18N
        PMBtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PMBtn.setText("0");
        PMBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        PMBtn.setOpaque(true);

        menuBtn26.setBackground(new java.awt.Color(250, 250, 250));
        menuBtn26.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn26.setText("PROJECT MANAGERS");
        menuBtn26.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn26.setOpaque(true);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
                jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel8Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(PMBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
                        .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(menuBtn26, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
                jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(menuBtn26, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(PMBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        MainPanel.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 350, 450, 250));

        getContentPane().add(MainPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 660));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void studentBtnMouseClicked(java.awt.event.MouseEvent evt) {
        this.dispose();
        AdminGui.ButtonClicked("student");
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel MainPanel;
    private static javax.swing.JLabel PMBtn;
    private javax.swing.JLabel adminBtn;
    private javax.swing.JLabel adminPanelVersionLabel;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private static javax.swing.JLabel lecturersBtn;
    private javax.swing.JLabel menuBtn21;
    private javax.swing.JLabel menuBtn24;
    private javax.swing.JLabel menuBtn26;
    private javax.swing.JLabel menuBtn28;
    private static javax.swing.JLabel studentBtn;
    // End of variables declaration//GEN-END:variables
}
