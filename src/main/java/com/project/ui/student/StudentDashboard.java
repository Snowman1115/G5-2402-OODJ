/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.project.ui.student;

import com.project.common.utils.PropertiesReader;
import com.project.controller.ConsultationController;
import com.project.controller.PresentationController;
import com.project.controller.SubmissionController;
import com.project.ui.administrator.*;
import java.util.Map;
import javax.swing.plaf.basic.BasicInternalFrameUI;

/**
 *
 * @author chanh
 */
public class StudentDashboard extends javax.swing.JInternalFrame {

    /**
     * Creates new form AdminDashboard
     */
    public StudentDashboard() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI)this.getUI();
        ui.setNorthPane(null);        
        studentPanelVersionLabel.setText(PropertiesReader.getProperty("StudentDashboardVersion"));
        dashboardRefresh();
    }
    
    public static void dashboardRefresh(){
        Map<String, Integer> consultation = ConsultationController.getUpcomingNFinishedConsultationForStudent();
        menuBtn18.setText(consultation.get("upcoming").toString());
        menuBtn19.setText(consultation.get("finished").toString());

        Map<String, Integer> presentation = PresentationController.getAllPresentationStatusByStudentId();
        menuBtn15.setText(presentation.get("pendingBooking").toString());
        menuBtn14.setText(presentation.get("pendingConfirm").toString());
        menuBtn12.setText(presentation.get("confirmed").toString());
        menuBtn13.setText(presentation.get("overdue").toString());
        
        
        Map<String, Integer> submission = SubmissionController.getAllSubmissionStatusByStudentId();
        menuBtn22.setText(submission.get("pendingSubmit").toString());
        menuBtn28.setText(submission.get("pendingMarking").toString());
        menuBtn26.setText(submission.get("marked").toString());
        menuBtn24.setText(submission.get("overdue").toString());
        
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
        menuBtn10 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        menuBtn2 = new javax.swing.JLabel();
        menuBtn14 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        menuBtn3 = new javax.swing.JLabel();
        menuBtn12 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        menuBtn1 = new javax.swing.JLabel();
        menuBtn15 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        menuBtn4 = new javax.swing.JLabel();
        menuBtn13 = new javax.swing.JLabel();
        menuBtn20 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        menuBtn21 = new javax.swing.JLabel();
        menuBtn22 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        menuBtn27 = new javax.swing.JLabel();
        menuBtn28 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        menuBtn29 = new javax.swing.JLabel();
        menuBtn30 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        menuBtn31 = new javax.swing.JLabel();
        menuBtn32 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        menuBtn23 = new javax.swing.JLabel();
        menuBtn24 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        menuBtn25 = new javax.swing.JLabel();
        menuBtn26 = new javax.swing.JLabel();
        studentPanelVersionLabel = new javax.swing.JLabel();
        menuBtn11 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        menuBtn7 = new javax.swing.JLabel();
        menuBtn18 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        menuBtn8 = new javax.swing.JLabel();
        menuBtn19 = new javax.swing.JLabel();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        MainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menuBtn10.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn10.setText("PRESENTAION DETAILS");
        menuBtn10.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn10.setOpaque(true);
        MainPanel.add(menuBtn10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, 500, 40));

        jPanel7.setBackground(new java.awt.Color(254, 254, 254));

        menuBtn2.setBackground(new java.awt.Color(250, 250, 250));
        menuBtn2.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn2.setText("PENDING CONFIRM");
        menuBtn2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn2.setOpaque(true);

        menuBtn14.setBackground(new java.awt.Color(254, 254, 254));
        menuBtn14.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 18)); // NOI18N
        menuBtn14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn14.setText("0");
        menuBtn14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuBtn14.setOpaque(true);
        menuBtn14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuBtn14MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menuBtn2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
            .addComponent(menuBtn14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(menuBtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(menuBtn14, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        MainPanel.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 240, 240, -1));

        jPanel6.setBackground(new java.awt.Color(254, 254, 254));

        menuBtn3.setBackground(new java.awt.Color(250, 250, 250));
        menuBtn3.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn3.setText("COMFIRMED");
        menuBtn3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn3.setOpaque(true);

        menuBtn12.setBackground(new java.awt.Color(254, 254, 254));
        menuBtn12.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 18)); // NOI18N
        menuBtn12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn12.setText("0");
        menuBtn12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuBtn12.setOpaque(true);
        menuBtn12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuBtn12MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menuBtn3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
            .addComponent(menuBtn12, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(menuBtn3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(menuBtn12, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        MainPanel.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 240, 240, 120));

        jPanel8.setBackground(new java.awt.Color(254, 254, 254));

        menuBtn1.setBackground(new java.awt.Color(250, 250, 250));
        menuBtn1.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn1.setText("PENDING BOOKING");
        menuBtn1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn1.setOpaque(true);

        menuBtn15.setBackground(new java.awt.Color(254, 254, 254));
        menuBtn15.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 18)); // NOI18N
        menuBtn15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn15.setText("0");
        menuBtn15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuBtn15.setOpaque(true);
        menuBtn15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuBtn15MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menuBtn1, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
            .addComponent(menuBtn15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(menuBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(menuBtn15, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        MainPanel.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 240, 120));

        jPanel10.setBackground(new java.awt.Color(254, 254, 254));

        menuBtn4.setBackground(new java.awt.Color(250, 250, 250));
        menuBtn4.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn4.setText("OVERDUE");
        menuBtn4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn4.setOpaque(true);

        menuBtn13.setBackground(new java.awt.Color(254, 254, 254));
        menuBtn13.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 18)); // NOI18N
        menuBtn13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn13.setText("0");
        menuBtn13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuBtn13.setOpaque(true);
        menuBtn13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuBtn13MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menuBtn4, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
            .addComponent(menuBtn13, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(menuBtn4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(menuBtn13, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
        );

        MainPanel.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 240, 240, 120));

        menuBtn20.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn20.setText("ASSIGNMENT DETAILS");
        menuBtn20.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn20.setOpaque(true);
        MainPanel.add(menuBtn20, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 1020, 40));

        jPanel5.setBackground(new java.awt.Color(254, 254, 254));

        menuBtn21.setBackground(new java.awt.Color(250, 250, 250));
        menuBtn21.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn21.setText("PENDING SUBMIT");
        menuBtn21.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn21.setOpaque(true);

        menuBtn22.setBackground(new java.awt.Color(254, 254, 254));
        menuBtn22.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 18)); // NOI18N
        menuBtn22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn22.setText("0");
        menuBtn22.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuBtn22.setOpaque(true);
        menuBtn22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuBtn22MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menuBtn21, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
            .addComponent(menuBtn22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(menuBtn21, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(menuBtn22, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        MainPanel.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 240, 120));

        jPanel12.setBackground(new java.awt.Color(254, 254, 254));

        menuBtn27.setBackground(new java.awt.Color(250, 250, 250));
        menuBtn27.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn27.setText("PENDING MARKING");
        menuBtn27.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn27.setOpaque(true);

        menuBtn28.setBackground(new java.awt.Color(254, 254, 254));
        menuBtn28.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 18)); // NOI18N
        menuBtn28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn28.setText("0");
        menuBtn28.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuBtn28.setOpaque(true);
        menuBtn28.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuBtn28MouseClicked(evt);
            }
        });

        jPanel13.setBackground(new java.awt.Color(254, 254, 254));

        menuBtn29.setBackground(new java.awt.Color(250, 250, 250));
        menuBtn29.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn29.setText("CANCELLED");
        menuBtn29.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn29.setOpaque(true);

        menuBtn30.setBackground(new java.awt.Color(254, 254, 254));
        menuBtn30.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 18)); // NOI18N
        menuBtn30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn30.setText("0");
        menuBtn30.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuBtn30.setOpaque(true);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menuBtn29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(menuBtn30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(menuBtn29, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(menuBtn30, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel14.setBackground(new java.awt.Color(254, 254, 254));

        menuBtn31.setBackground(new java.awt.Color(250, 250, 250));
        menuBtn31.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn31.setText("SECCEED");
        menuBtn31.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn31.setOpaque(true);

        menuBtn32.setBackground(new java.awt.Color(254, 254, 254));
        menuBtn32.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 18)); // NOI18N
        menuBtn32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn32.setText("0");
        menuBtn32.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuBtn32.setOpaque(true);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menuBtn31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(menuBtn32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(menuBtn31, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(menuBtn32, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menuBtn27, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
            .addComponent(menuBtn28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(menuBtn27, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(menuBtn28, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        MainPanel.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 50, -1, -1));

        jPanel9.setBackground(new java.awt.Color(254, 254, 254));

        menuBtn23.setBackground(new java.awt.Color(250, 250, 250));
        menuBtn23.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn23.setText("OVERDUE");
        menuBtn23.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn23.setOpaque(true);

        menuBtn24.setBackground(new java.awt.Color(254, 254, 254));
        menuBtn24.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 18)); // NOI18N
        menuBtn24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn24.setText("0");
        menuBtn24.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuBtn24.setOpaque(true);
        menuBtn24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuBtn24MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menuBtn23, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
            .addComponent(menuBtn24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(menuBtn23, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(menuBtn24, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        MainPanel.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 50, -1, -1));

        jPanel11.setBackground(new java.awt.Color(254, 254, 254));

        menuBtn25.setBackground(new java.awt.Color(250, 250, 250));
        menuBtn25.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn25.setText("MARKED");
        menuBtn25.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn25.setOpaque(true);

        menuBtn26.setBackground(new java.awt.Color(254, 254, 254));
        menuBtn26.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 18)); // NOI18N
        menuBtn26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn26.setText("0");
        menuBtn26.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuBtn26.setOpaque(true);
        menuBtn26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuBtn26MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menuBtn25, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
            .addComponent(menuBtn26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(menuBtn25, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(menuBtn26, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        MainPanel.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 50, 240, 120));

        studentPanelVersionLabel.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        studentPanelVersionLabel.setForeground(new java.awt.Color(80, 80, 80));
        studentPanelVersionLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        studentPanelVersionLabel.setText("StudentPanel @Version x.x Built On xx xx xxxx");
        studentPanelVersionLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        MainPanel.add(studentPanelVersionLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(455, 628, 620, 30));

        menuBtn11.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn11.setText("CONSULTATIONS");
        menuBtn11.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn11.setOpaque(true);
        MainPanel.add(menuBtn11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 390, 500, 40));

        jPanel3.setBackground(new java.awt.Color(254, 254, 254));

        menuBtn7.setBackground(new java.awt.Color(250, 250, 250));
        menuBtn7.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn7.setText("UPCOMING");
        menuBtn7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn7.setOpaque(true);

        menuBtn18.setBackground(new java.awt.Color(254, 254, 254));
        menuBtn18.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 18)); // NOI18N
        menuBtn18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn18.setText("0");
        menuBtn18.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuBtn18.setOpaque(true);
        menuBtn18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuBtn18MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menuBtn7, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
            .addComponent(menuBtn18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(menuBtn7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(menuBtn18, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        MainPanel.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 430, 240, 120));

        jPanel4.setBackground(new java.awt.Color(254, 254, 254));

        menuBtn8.setBackground(new java.awt.Color(250, 250, 250));
        menuBtn8.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn8.setText("COMPLETED");
        menuBtn8.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn8.setOpaque(true);

        menuBtn19.setBackground(new java.awt.Color(254, 254, 254));
        menuBtn19.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 18)); // NOI18N
        menuBtn19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn19.setText("0");
        menuBtn19.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuBtn19.setOpaque(true);
        menuBtn19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuBtn19MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menuBtn8, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
            .addComponent(menuBtn19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(menuBtn8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(menuBtn19, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        MainPanel.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 430, 240, -1));

        getContentPane().add(MainPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 660));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuBtn14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuBtn14MouseClicked
        this.dispose();
        StudentGui.ButtonClicked("menuBtn7");
    }//GEN-LAST:event_menuBtn14MouseClicked

    private void menuBtn12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuBtn12MouseClicked
        this.dispose();
        StudentGui.ButtonClicked("menuBtn7");
    }//GEN-LAST:event_menuBtn12MouseClicked

    private void menuBtn15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuBtn15MouseClicked
        this.dispose();
        StudentGui.ButtonClicked("menuBtn7");
    }//GEN-LAST:event_menuBtn15MouseClicked

    private void menuBtn13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuBtn13MouseClicked
        this.dispose();
        StudentGui.ButtonClicked("menuBtn7");
    }//GEN-LAST:event_menuBtn13MouseClicked

    private void menuBtn18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuBtn18MouseClicked
        this.dispose();
        StudentGui.ButtonClicked("menuBtn2");
    }//GEN-LAST:event_menuBtn18MouseClicked

    private void menuBtn19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuBtn19MouseClicked
        this.dispose();
        StudentGui.ButtonClicked("menuBtn2");
    }//GEN-LAST:event_menuBtn19MouseClicked

    private void menuBtn22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuBtn22MouseClicked
        this.dispose();
        StudentGui.ButtonClicked("menuBtn8");
    }//GEN-LAST:event_menuBtn22MouseClicked

    private void menuBtn28MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuBtn28MouseClicked
        this.dispose();
        StudentGui.ButtonClicked("menuBtn8");
    }//GEN-LAST:event_menuBtn28MouseClicked

    private void menuBtn26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuBtn26MouseClicked
        this.dispose();
        StudentGui.ButtonClicked("menuBtn8");
    }//GEN-LAST:event_menuBtn26MouseClicked

    private void menuBtn24MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuBtn24MouseClicked
        this.dispose();
        StudentGui.ButtonClicked("menuBtn8");
    }//GEN-LAST:event_menuBtn24MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel MainPanel;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel menuBtn1;
    private javax.swing.JLabel menuBtn10;
    private javax.swing.JLabel menuBtn11;
    private static javax.swing.JLabel menuBtn12;
    private static javax.swing.JLabel menuBtn13;
    private static javax.swing.JLabel menuBtn14;
    private static javax.swing.JLabel menuBtn15;
    private static javax.swing.JLabel menuBtn18;
    private static javax.swing.JLabel menuBtn19;
    private javax.swing.JLabel menuBtn2;
    private javax.swing.JLabel menuBtn20;
    private javax.swing.JLabel menuBtn21;
    private static javax.swing.JLabel menuBtn22;
    private javax.swing.JLabel menuBtn23;
    private static javax.swing.JLabel menuBtn24;
    private javax.swing.JLabel menuBtn25;
    private static javax.swing.JLabel menuBtn26;
    private javax.swing.JLabel menuBtn27;
    private static javax.swing.JLabel menuBtn28;
    private javax.swing.JLabel menuBtn29;
    private javax.swing.JLabel menuBtn3;
    private static javax.swing.JLabel menuBtn30;
    private javax.swing.JLabel menuBtn31;
    private static javax.swing.JLabel menuBtn32;
    private javax.swing.JLabel menuBtn4;
    private javax.swing.JLabel menuBtn7;
    private javax.swing.JLabel menuBtn8;
    private javax.swing.JLabel studentPanelVersionLabel;
    // End of variables declaration//GEN-END:variables
}
