/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.project.ui.student;

import com.project.controller.ConsultationController;
import com.project.controller.PresentationController;
import com.project.pojo.ProjectModule;
import com.project.pojo.UserAccount;

import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Olaf
 */
public class StudentPresentationGui extends javax.swing.JInternalFrame {

    /**
     * Creates new form StudentAssignmentGui
     */
    public StudentPresentationGui() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI)this.getUI();
        ui.setNorthPane(null);        
        // salesManagementPanel.setText(PropertiesReader.getProperty("SalesManagementPanelVersion"));
        refresh();
    }

    private void refresh() {
        Map<String, Integer> presentation = PresentationController.getAllPresentationStatusByStudentId();
        menuBtn12.setText(presentation.get("pendingBooking").toString());
        menuBtn13.setText(presentation.get("overdue").toString());

        refreshTable();
        refreshjTable3(0);
    }
    
    private void refreshjTable3(Integer value) {
        DefaultTableModel dtm = (DefaultTableModel)jTable3.getModel();
        dtm.setRowCount(0);
        
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dtm);
        jTable3.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter("".trim()));
        
        
        List<Map<String, String>> lists = PresentationController.getAllPresentationDetailsForStudent();

        switch(value) {
            case 0 -> {
                for (Map<String, String> list : lists) {
                    String[] data = {list.get("moduleName"), list.get("lecturerName"), list.get("dueDate"), list.get("presentationDate"), list.get("Status")};
                    dtm.addRow(data);
                }
            }
            case 1 -> {
                for (Map<String, String> list : lists) {
                    if (list.get("Status").equals("PENDING_BOOKING")) {
                        String[] data = {list.get("moduleName"), list.get("lecturerName"), list.get("dueDate"), list.get("presentationDate"), list.get("Status")};
                        dtm.addRow(data);
                    }
                }
            }
            case 2 -> {
                for (Map<String, String> list : lists) {
                    if (list.get("Status").equals("PENDING_CONFIRM")) {
                        String[] data = {list.get("moduleName"), list.get("lecturerName"), list.get("dueDate"), list.get("presentationDate"), list.get("Status")};
                        dtm.addRow(data);
                    }
                }
            }
            case 3 -> {
                for (Map<String, String> list : lists) {
                    if (list.get("Status").equals("REJECTED")) {
                        String[] data = {list.get("moduleName"), list.get("lecturerName"), list.get("dueDate"), list.get("presentationDate"), list.get("Status")};
                        dtm.addRow(data);
                    }
                }
            }
            case 4 -> {
                for (Map<String, String> list : lists) {
                    if (list.get("Status").equals("BOOKED")) {
                        String[] data = {list.get("moduleName"), list.get("lecturerName"), list.get("dueDate"), list.get("presentationDate"), list.get("Status")};
                        dtm.addRow(data);
                    }
                }
            }
            case 5 -> {
                for (Map<String, String> list : lists) {
                    if (list.get("Status").equals("MARKED")) {
                        String[] data = {list.get("moduleName"), list.get("lecturerName"), list.get("dueDate"), list.get("presentationDate"), list.get("Status")};
                        dtm.addRow(data);
                    }
                }
            }
            case 6 -> {
                for (Map<String, String> list : lists) {
                    if (list.get("Status").equals("OVERDUE")) {
                        String[] data = {list.get("moduleName"), list.get("lecturerName"), list.get("dueDate"), list.get("presentationDate"), list.get("Status")};
                        dtm.addRow(data);
                    }
                }
            }
        }  
    }

    private void refreshTable() {
        DefaultTableModel dtm =  (DefaultTableModel)jTable2.getModel();
        dtm.setRowCount(0);
        List<Map<String, String>> lists = PresentationController.getAllUpcomingNPendingBookingPresentation();
        for (Map<String,String> list : lists) {
            String[] data = {list.get("moduleName"), list.get("dueDate")};
            dtm.addRow(data);
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

        MainTabbedPanel = new javax.swing.JTabbedPane();
        Panel1 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        menuBtn4 = new javax.swing.JLabel();
        menuBtn13 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        menuBtn3 = new javax.swing.JLabel();
        menuBtn12 = new javax.swing.JLabel();
        menuBtn14 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        JField12 = new javax.swing.JTextField();
        presentationComboBox = new javax.swing.JComboBox<>();
        menuBtn11 = new javax.swing.JLabel();
        menuBtn15 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        menuBtn16 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        Panel2 = new javax.swing.JPanel();
        MainTabbedPanel1 = new javax.swing.JTabbedPane();
        Panel4 = new javax.swing.JPanel();
        menuBtn22 = new javax.swing.JLabel();
        menuBtn23 = new javax.swing.JLabel();
        menuBtn24 = new javax.swing.JLabel();
        menuBtn25 = new javax.swing.JLabel();
        JField14 = new javax.swing.JTextField();
        JField13 = new javax.swing.JTextField();
        menuBtn26 = new javax.swing.JLabel();
        JField15 = new javax.swing.JTextField();
        JSeparator33 = new javax.swing.JSeparator();
        jLabel36 = new javax.swing.JLabel();
        menuBtn29 = new javax.swing.JLabel();
        presentationComboBox1 = new javax.swing.JComboBox<>();
        menuBtn39 = new javax.swing.JLabel();
        menuBtn27 = new javax.swing.JLabel();
        JField24 = new javax.swing.JTextField();
        dateTimePicker1 = new com.github.lgooddatepicker.components.DateTimePicker();
        Panel8 = new javax.swing.JPanel();
        menuBtn30 = new javax.swing.JLabel();
        menuBtn37 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        JSeparator37 = new javax.swing.JSeparator();
        jLabel41 = new javax.swing.JLabel();
        JSeparator38 = new javax.swing.JSeparator();
        menuBtn28 = new javax.swing.JLabel();
        presentationComboBox2 = new javax.swing.JComboBox<>();
        menuBtn43 = new javax.swing.JLabel();
        JField20 = new javax.swing.JTextField();
        menuBtn45 = new javax.swing.JLabel();
        dateTimePicker2 = new com.github.lgooddatepicker.components.DateTimePicker();
        menuBtn48 = new javax.swing.JLabel();
        menuBtn50 = new javax.swing.JLabel();
        JField26 = new javax.swing.JTextField();
        JField29 = new javax.swing.JTextField();
        menuBtn53 = new javax.swing.JLabel();
        JField30 = new javax.swing.JTextField();
        Panel9 = new javax.swing.JPanel();
        menuBtn31 = new javax.swing.JLabel();
        menuBtn49 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        JSeparator39 = new javax.swing.JSeparator();
        jLabel43 = new javax.swing.JLabel();
        JSeparator40 = new javax.swing.JSeparator();
        menuBtn32 = new javax.swing.JLabel();
        presentationComboBox3 = new javax.swing.JComboBox<>();
        menuBtn54 = new javax.swing.JLabel();
        JField22 = new javax.swing.JTextField();
        menuBtn55 = new javax.swing.JLabel();
        dateTimePicker3 = new com.github.lgooddatepicker.components.DateTimePicker();
        menuBtn56 = new javax.swing.JLabel();
        menuBtn58 = new javax.swing.JLabel();
        JField32 = new javax.swing.JTextField();
        JField33 = new javax.swing.JTextField();
        menuBtn60 = new javax.swing.JLabel();
        JField34 = new javax.swing.JTextField();
        Panel6 = new javax.swing.JPanel();
        menuBtn38 = new javax.swing.JLabel();
        JField35 = new javax.swing.JTextField();
        menuBtn61 = new javax.swing.JLabel();
        JField36 = new javax.swing.JTextField();
        menuBtn62 = new javax.swing.JLabel();
        JField37 = new javax.swing.JTextField();
        menuBtn63 = new javax.swing.JLabel();
        presentationComboBox4 = new javax.swing.JComboBox<>();
        menuBtn33 = new javax.swing.JLabel();
        menuBtn64 = new javax.swing.JLabel();
        JField38 = new javax.swing.JTextField();
        menuBtn65 = new javax.swing.JLabel();
        JField39 = new javax.swing.JTextField();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        MainTabbedPanel.setBackground(new java.awt.Color(230, 230, 230));
        MainTabbedPanel.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N

        Panel1.setPreferredSize(new java.awt.Dimension(1050, 570));
        Panel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel10.setBackground(new java.awt.Color(254, 254, 254));

        menuBtn4.setBackground(new java.awt.Color(250, 250, 250));
        menuBtn4.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/warningIcon-24x24.png"))); // NOI18N
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
            .addComponent(menuBtn13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(menuBtn4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(menuBtn13, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE))
        );

        Panel1.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 50, 240, 90));

        jPanel6.setBackground(new java.awt.Color(254, 254, 254));

        menuBtn3.setBackground(new java.awt.Color(250, 250, 250));
        menuBtn3.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/status-24x24.png"))); // NOI18N
        menuBtn3.setText("PENDING BOOKING");
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
            .addComponent(menuBtn12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(menuBtn3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(menuBtn12, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        Panel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 240, 90));

        menuBtn14.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn14.setText("PRESENTATION");
        menuBtn14.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn14.setOpaque(true);
        Panel1.add(menuBtn14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 500, 40));

        jLabel11.setBackground(new java.awt.Color(254, 254, 254));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/search-24x24.png"))); // NOI18N
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel11.setOpaque(true);
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });
        Panel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 180, 40, 35));

        JField12.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField12.setForeground(new java.awt.Color(1, 1, 1));
        JField12.setText("Enter Keywords To Search");
        JField12.setBorder(null);
        JField12.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField12MouseClicked(evt);
            }
        });
        Panel1.add(JField12, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 180, 290, 35));

        presentationComboBox.setBackground(new java.awt.Color(254, 254, 254));
        presentationComboBox.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 12)); // NOI18N
        presentationComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Pending Booking", "Pending Confirm", "Rejcted", "Booked", "Completed", "Overdue" }));
        presentationComboBox.setToolTipText("d");
        presentationComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        presentationComboBox.setFocusable(false);
        presentationComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                presentationComboBoxActionPerformed(evt);
            }
        });
        Panel1.add(presentationComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 330, 35));

        menuBtn11.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/calendar-24x24.png"))); // NOI18N
        menuBtn11.setText("PENDING BOOKING");
        menuBtn11.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn11.setOpaque(true);
        Panel1.add(menuBtn11, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 140, 330, 40));

        menuBtn15.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn15.setText("ACTION");
        menuBtn15.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn15.setOpaque(true);
        Panel1.add(menuBtn15, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 10, 420, 40));

        jPanel7.setBackground(new java.awt.Color(254, 254, 254));

        jLabel13.setBackground(new java.awt.Color(105, 105, 105));
        jLabel13.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(1, 1, 1));
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/add-24x24.png"))); // NOI18N
        jLabel13.setText("BOOK PRESENTATION");
        jLabel13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });

        jLabel15.setBackground(new java.awt.Color(105, 105, 105));
        jLabel15.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(1, 1, 1));
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/edit-24x24.png"))); // NOI18N
        jLabel15.setText("EDIT PRESENTATION");
        jLabel15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
        });

        jLabel21.setBackground(new java.awt.Color(105, 105, 105));
        jLabel21.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(1, 1, 1));
        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/delete-24x24.png"))); // NOI18N
        jLabel21.setText("REJECTED PRESENTATION");
        jLabel21.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel21MouseClicked(evt);
            }
        });

        jLabel37.setBackground(new java.awt.Color(105, 105, 105));
        jLabel37.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(1, 1, 1));
        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/view-24x24.png"))); // NOI18N
        jLabel37.setText("PRESENTATION RESULT");
        jLabel37.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel37.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel37MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jLabel37))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        Panel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 50, 500, 90));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Module", "DueDate"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setResizable(false);
            jTable2.getColumnModel().getColumn(1).setResizable(false);
        }

        Panel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 180, 330, 420));

        menuBtn16.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/view-24x24.png"))); // NOI18N
        menuBtn16.setText("VIEW PRESENTATION DETAILS");
        menuBtn16.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn16.setOpaque(true);
        Panel1.add(menuBtn16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 670, 40));

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "MODULE", "LECTURER", "DUE DATE", "SELECTED DATE", "STATUS"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable3.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(jTable3);
        if (jTable3.getColumnModel().getColumnCount() > 0) {
            jTable3.getColumnModel().getColumn(0).setResizable(false);
            jTable3.getColumnModel().getColumn(1).setResizable(false);
            jTable3.getColumnModel().getColumn(2).setResizable(false);
            jTable3.getColumnModel().getColumn(3).setResizable(false);
            jTable3.getColumnModel().getColumn(4).setResizable(false);
        }

        Panel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 670, 360));

        MainTabbedPanel.addTab("OverView", Panel1);

        Panel2.setPreferredSize(new java.awt.Dimension(1050, 570));
        Panel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        MainTabbedPanel1.setBackground(new java.awt.Color(230, 230, 230));
        MainTabbedPanel1.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N

        Panel4.setPreferredSize(new java.awt.Dimension(1050, 570));
        Panel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menuBtn22.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn22.setText("BOOKING");
        menuBtn22.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn22.setOpaque(true);
        Panel4.add(menuBtn22, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 500, 40));

        menuBtn23.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn23.setText("LECTURER NAME");
        menuBtn23.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn23.setOpaque(true);
        Panel4.add(menuBtn23, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 330, 40));

        menuBtn24.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn24.setText("SELECT MODULE");
        menuBtn24.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn24.setOpaque(true);
        Panel4.add(menuBtn24, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 330, 40));

        menuBtn25.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn25.setText("PRESENTATION ID");
        menuBtn25.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn25.setOpaque(true);
        Panel4.add(menuBtn25, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 330, 40));

        JField14.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField14.setForeground(new java.awt.Color(1, 1, 1));
        JField14.setText("Module Name");
        JField14.setBorder(null);
        JField14.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField14MouseClicked(evt);
            }
        });
        Panel4.add(JField14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 330, 35));

        JField13.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField13.setForeground(new java.awt.Color(1, 1, 1));
        JField13.setText("Lectuerer Name");
        JField13.setBorder(null);
        JField13.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField13MouseClicked(evt);
            }
        });
        Panel4.add(JField13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 330, 35));

        menuBtn26.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn26.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn26.setText("DUE DATE");
        menuBtn26.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn26.setOpaque(true);
        Panel4.add(menuBtn26, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 330, 40));

        JField15.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField15.setForeground(new java.awt.Color(1, 1, 1));
        JField15.setText("Project Due Date");
        JField15.setBorder(null);
        JField15.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField15MouseClicked(evt);
            }
        });
        Panel4.add(JField15, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 350, 330, 35));

        JSeparator33.setForeground(new java.awt.Color(1, 1, 1));
        Panel4.add(JSeparator33, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 550, 170, 10));

        jLabel36.setBackground(new java.awt.Color(254, 254, 254));
        jLabel36.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(1, 1, 1));
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/add-green-24x24.png"))); // NOI18N
        jLabel36.setText("BOOK");
        jLabel36.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel36.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel36.setOpaque(true);
        jLabel36.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel36MouseClicked(evt);
            }
        });
        Panel4.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 510, 170, 35));

        menuBtn29.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn29.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/action-24x24.png"))); // NOI18N
        menuBtn29.setText("ACTION :");
        menuBtn29.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn29.setOpaque(true);
        Panel4.add(menuBtn29, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 510, 90, 40));

        presentationComboBox1.setBackground(new java.awt.Color(254, 254, 254));
        presentationComboBox1.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 12)); // NOI18N
        presentationComboBox1.setToolTipText("d");
        presentationComboBox1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        presentationComboBox1.setFocusable(false);
        presentationComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                presentationComboBox1ActionPerformed(evt);
            }
        });
        Panel4.add(presentationComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 330, 35));

        menuBtn39.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn39.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn39.setText("PRESENTATION DATE");
        menuBtn39.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn39.setOpaque(true);
        Panel4.add(menuBtn39, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 130, 330, 40));

        menuBtn27.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn27.setText("STATUS");
        menuBtn27.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn27.setOpaque(true);
        Panel4.add(menuBtn27, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 220, 330, 40));

        JField24.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField24.setForeground(new java.awt.Color(1, 1, 1));
        JField24.setText("Module Name");
        JField24.setBorder(null);
        JField24.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField24MouseClicked(evt);
            }
        });
        Panel4.add(JField24, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 260, 330, 35));
        Panel4.add(dateTimePicker1, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 170, 330, 35));

        MainTabbedPanel1.addTab("Book", Panel4);

        Panel8.setPreferredSize(new java.awt.Dimension(1050, 570));
        Panel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menuBtn30.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn30.setText("MODIFICATION");
        menuBtn30.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn30.setOpaque(true);
        Panel8.add(menuBtn30, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 500, 40));

        menuBtn37.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn37.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/action-24x24.png"))); // NOI18N
        menuBtn37.setText("ACTION :");
        menuBtn37.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn37.setOpaque(true);
        Panel8.add(menuBtn37, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 510, 90, 40));

        jLabel40.setBackground(new java.awt.Color(254, 254, 254));
        jLabel40.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(1, 1, 1));
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/delete-red-24x24.png"))); // NOI18N
        jLabel40.setText("CANCEL");
        jLabel40.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel40.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel40.setOpaque(true);
        jLabel40.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel40MouseClicked(evt);
            }
        });
        Panel8.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 510, 170, 35));

        JSeparator37.setForeground(new java.awt.Color(1, 1, 1));
        Panel8.add(JSeparator37, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 550, 170, 10));

        jLabel41.setBackground(new java.awt.Color(254, 254, 254));
        jLabel41.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(1, 1, 1));
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/add-green-24x24.png"))); // NOI18N
        jLabel41.setText("EDIT");
        jLabel41.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel41.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel41.setOpaque(true);
        jLabel41.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel41MouseClicked(evt);
            }
        });
        Panel8.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 510, 170, 35));

        JSeparator38.setForeground(new java.awt.Color(1, 1, 1));
        Panel8.add(JSeparator38, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 550, 170, 10));

        menuBtn28.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn28.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn28.setText("SELECT MODULE");
        menuBtn28.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn28.setOpaque(true);
        Panel8.add(menuBtn28, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 330, 40));

        presentationComboBox2.setBackground(new java.awt.Color(254, 254, 254));
        presentationComboBox2.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 12)); // NOI18N
        presentationComboBox2.setToolTipText("d");
        presentationComboBox2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        presentationComboBox2.setFocusable(false);
        presentationComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                presentationComboBox2ActionPerformed(evt);
            }
        });
        Panel8.add(presentationComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 330, 35));

        menuBtn43.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn43.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn43.setText("PRESENTATION ID");
        menuBtn43.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn43.setOpaque(true);
        Panel8.add(menuBtn43, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 330, 40));

        JField20.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField20.setForeground(new java.awt.Color(1, 1, 1));
        JField20.setText("Module Name");
        JField20.setBorder(null);
        JField20.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField20MouseClicked(evt);
            }
        });
        Panel8.add(JField20, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 330, 35));

        menuBtn45.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn45.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn45.setText("PRESENTATION DATE");
        menuBtn45.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn45.setOpaque(true);
        Panel8.add(menuBtn45, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 130, 330, 40));
        Panel8.add(dateTimePicker2, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 170, 330, 35));

        menuBtn48.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn48.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn48.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn48.setText("LECTURER NAME");
        menuBtn48.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn48.setOpaque(true);
        Panel8.add(menuBtn48, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 330, 40));

        menuBtn50.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn50.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn50.setText("STATUS");
        menuBtn50.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn50.setOpaque(true);
        Panel8.add(menuBtn50, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 220, 330, 40));

        JField26.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField26.setForeground(new java.awt.Color(1, 1, 1));
        JField26.setText("Module Name");
        JField26.setBorder(null);
        JField26.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField26MouseClicked(evt);
            }
        });
        Panel8.add(JField26, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 260, 330, 35));

        JField29.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField29.setForeground(new java.awt.Color(1, 1, 1));
        JField29.setText("Lectuerer Name");
        JField29.setBorder(null);
        JField29.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField29.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField29MouseClicked(evt);
            }
        });
        Panel8.add(JField29, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 330, 35));

        menuBtn53.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn53.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn53.setText("DUE DATE");
        menuBtn53.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn53.setOpaque(true);
        Panel8.add(menuBtn53, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 330, 40));

        JField30.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField30.setForeground(new java.awt.Color(1, 1, 1));
        JField30.setText("Project Due Date");
        JField30.setBorder(null);
        JField30.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField30.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField30MouseClicked(evt);
            }
        });
        Panel8.add(JField30, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 350, 330, 35));

        MainTabbedPanel1.addTab("Edit", Panel8);

        Panel9.setPreferredSize(new java.awt.Dimension(1050, 570));
        Panel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menuBtn31.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn31.setText("RE-SCHEDULE");
        menuBtn31.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn31.setOpaque(true);
        Panel9.add(menuBtn31, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 500, 40));

        menuBtn49.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn49.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/action-24x24.png"))); // NOI18N
        menuBtn49.setText("ACTION :");
        menuBtn49.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn49.setOpaque(true);
        Panel9.add(menuBtn49, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 510, 90, 40));

        jLabel42.setBackground(new java.awt.Color(254, 254, 254));
        jLabel42.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(1, 1, 1));
        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/delete-red-24x24.png"))); // NOI18N
        jLabel42.setText("CANCEL");
        jLabel42.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel42.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel42.setOpaque(true);
        jLabel42.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel42MouseClicked(evt);
            }
        });
        Panel9.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 510, 170, 35));

        JSeparator39.setForeground(new java.awt.Color(1, 1, 1));
        Panel9.add(JSeparator39, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 550, 170, 10));

        jLabel43.setBackground(new java.awt.Color(254, 254, 254));
        jLabel43.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(1, 1, 1));
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/add-green-24x24.png"))); // NOI18N
        jLabel43.setText("REBOOK");
        jLabel43.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel43.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel43.setOpaque(true);
        jLabel43.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel43MouseClicked(evt);
            }
        });
        Panel9.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 510, 170, 35));

        JSeparator40.setForeground(new java.awt.Color(1, 1, 1));
        Panel9.add(JSeparator40, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 550, 170, 10));

        menuBtn32.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn32.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn32.setText("SELECT MODULE");
        menuBtn32.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn32.setOpaque(true);
        Panel9.add(menuBtn32, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 330, 40));

        presentationComboBox3.setBackground(new java.awt.Color(254, 254, 254));
        presentationComboBox3.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 12)); // NOI18N
        presentationComboBox3.setToolTipText("d");
        presentationComboBox3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        presentationComboBox3.setFocusable(false);
        presentationComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                presentationComboBox3ActionPerformed(evt);
            }
        });
        Panel9.add(presentationComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 330, 35));

        menuBtn54.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn54.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn54.setText("PRESENTATION ID");
        menuBtn54.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn54.setOpaque(true);
        Panel9.add(menuBtn54, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 330, 40));

        JField22.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField22.setForeground(new java.awt.Color(1, 1, 1));
        JField22.setText("Module Name");
        JField22.setBorder(null);
        JField22.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField22MouseClicked(evt);
            }
        });
        Panel9.add(JField22, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 330, 35));

        menuBtn55.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn55.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn55.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn55.setText("PRESENTATION DATE");
        menuBtn55.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn55.setOpaque(true);
        Panel9.add(menuBtn55, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 130, 330, 40));
        Panel9.add(dateTimePicker3, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 170, 330, 35));

        menuBtn56.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn56.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn56.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn56.setText("LECTURER NAME");
        menuBtn56.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn56.setOpaque(true);
        Panel9.add(menuBtn56, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 330, 40));

        menuBtn58.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn58.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn58.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn58.setText("STATUS");
        menuBtn58.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn58.setOpaque(true);
        Panel9.add(menuBtn58, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 220, 330, 40));

        JField32.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField32.setForeground(new java.awt.Color(1, 1, 1));
        JField32.setText("Module Name");
        JField32.setBorder(null);
        JField32.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField32.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField32MouseClicked(evt);
            }
        });
        Panel9.add(JField32, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 260, 330, 35));

        JField33.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField33.setForeground(new java.awt.Color(1, 1, 1));
        JField33.setText("Lectuerer Name");
        JField33.setBorder(null);
        JField33.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField33MouseClicked(evt);
            }
        });
        Panel9.add(JField33, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 330, 35));

        menuBtn60.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn60.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn60.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn60.setText("DUE DATE");
        menuBtn60.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn60.setOpaque(true);
        Panel9.add(menuBtn60, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 330, 40));

        JField34.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField34.setForeground(new java.awt.Color(1, 1, 1));
        JField34.setText("Project Due Date");
        JField34.setBorder(null);
        JField34.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField34.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField34MouseClicked(evt);
            }
        });
        Panel9.add(JField34, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 350, 330, 35));

        MainTabbedPanel1.addTab("Rejected", Panel9);

        Panel6.setPreferredSize(new java.awt.Dimension(1050, 570));
        Panel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menuBtn38.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn38.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn38.setText("RESULT");
        menuBtn38.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn38.setOpaque(true);
        Panel6.add(menuBtn38, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 500, 40));

        JField35.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField35.setForeground(new java.awt.Color(1, 1, 1));
        JField35.setText("Project Due Date");
        JField35.setBorder(null);
        JField35.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField35.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField35MouseClicked(evt);
            }
        });
        Panel6.add(JField35, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 350, 330, 35));

        menuBtn61.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn61.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn61.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn61.setText("DUE DATE");
        menuBtn61.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn61.setOpaque(true);
        Panel6.add(menuBtn61, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 330, 40));

        JField36.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField36.setForeground(new java.awt.Color(1, 1, 1));
        JField36.setText("Lectuerer Name");
        JField36.setBorder(null);
        JField36.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField36.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField36MouseClicked(evt);
            }
        });
        Panel6.add(JField36, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 330, 35));

        menuBtn62.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn62.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn62.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn62.setText("LECTURER NAME");
        menuBtn62.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn62.setOpaque(true);
        Panel6.add(menuBtn62, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 330, 40));

        JField37.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField37.setForeground(new java.awt.Color(1, 1, 1));
        JField37.setText("Module Name");
        JField37.setBorder(null);
        JField37.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField37.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField37MouseClicked(evt);
            }
        });
        Panel6.add(JField37, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 330, 35));

        menuBtn63.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn63.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn63.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn63.setText("PRESENTATION ID");
        menuBtn63.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn63.setOpaque(true);
        Panel6.add(menuBtn63, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 330, 40));

        presentationComboBox4.setBackground(new java.awt.Color(254, 254, 254));
        presentationComboBox4.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 12)); // NOI18N
        presentationComboBox4.setToolTipText("d");
        presentationComboBox4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        presentationComboBox4.setFocusable(false);
        presentationComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                presentationComboBox4ActionPerformed(evt);
            }
        });
        Panel6.add(presentationComboBox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 330, 35));

        menuBtn33.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn33.setText("SELECT MODULE");
        menuBtn33.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn33.setOpaque(true);
        Panel6.add(menuBtn33, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 330, 40));

        menuBtn64.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn64.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn64.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn64.setText("PRESENTATION DATE");
        menuBtn64.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn64.setOpaque(true);
        Panel6.add(menuBtn64, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 130, 330, 40));

        JField38.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField38.setForeground(new java.awt.Color(1, 1, 1));
        JField38.setText("Presentation Date");
        JField38.setBorder(null);
        JField38.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField38.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField38MouseClicked(evt);
            }
        });
        JField38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JField38ActionPerformed(evt);
            }
        });
        Panel6.add(JField38, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 170, 330, 35));

        menuBtn65.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn65.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn65.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn65.setText("STATUS");
        menuBtn65.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn65.setOpaque(true);
        Panel6.add(menuBtn65, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 220, 330, 40));

        JField39.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField39.setForeground(new java.awt.Color(1, 1, 1));
        JField39.setText("Status");
        JField39.setBorder(null);
        JField39.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField39.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField39MouseClicked(evt);
            }
        });
        Panel6.add(JField39, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 260, 330, 35));

        MainTabbedPanel1.addTab("Result", Panel6);

        Panel2.add(MainTabbedPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 630));

        MainTabbedPanel.addTab("Presentation", Panel2);

        getContentPane().add(MainTabbedPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 660));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        DefaultTableModel dtm = (DefaultTableModel)jTable3.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dtm);
        jTable3.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(JField12.getText().trim()));
    }//GEN-LAST:event_jLabel11MouseClicked

    private void JField12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField12MouseClicked
        if (JField12.getText().equals("Enter Keywords To Search")) {
            JField12.setText("");
        }
    }//GEN-LAST:event_JField12MouseClicked

    private void presentationComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_presentationComboBoxActionPerformed
        refreshjTable3(presentationComboBox.getSelectedIndex());
    }//GEN-LAST:event_presentationComboBoxActionPerformed

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        MainTabbedPanel.setSelectedIndex(1);
    }//GEN-LAST:event_jLabel13MouseClicked

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
        MainTabbedPanel.setSelectedIndex(1);
        MainTabbedPanel1.setSelectedIndex(1);
    }//GEN-LAST:event_jLabel15MouseClicked

    private void jLabel21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel21MouseClicked
        MainTabbedPanel.setSelectedIndex(1);
        MainTabbedPanel1.setSelectedIndex(2);
    }//GEN-LAST:event_jLabel21MouseClicked

    private void jLabel37MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel37MouseClicked
        MainTabbedPanel.setSelectedIndex(1);
        MainTabbedPanel1.setSelectedIndex(3);
    }//GEN-LAST:event_jLabel37MouseClicked

    private void jLabel41MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel41MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel41MouseClicked

    private void jLabel40MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel40MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel40MouseClicked

    private void presentationComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_presentationComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_presentationComboBox1ActionPerformed

    private void jLabel36MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel36MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel36MouseClicked

    private void JField15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField15MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JField15MouseClicked

    private void JField13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField13MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JField13MouseClicked

    private void JField14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField14MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JField14MouseClicked

    private void menuBtn13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuBtn13MouseClicked
         presentationComboBox.setSelectedIndex(6);
    }//GEN-LAST:event_menuBtn13MouseClicked

    private void menuBtn12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuBtn12MouseClicked
        presentationComboBox.setSelectedIndex(1);
    }//GEN-LAST:event_menuBtn12MouseClicked

    private void JField24MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField24MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JField24MouseClicked

    private void presentationComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_presentationComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_presentationComboBox2ActionPerformed

    private void JField20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField20MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JField20MouseClicked

    private void JField26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField26MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JField26MouseClicked

    private void JField29MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField29MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JField29MouseClicked

    private void JField30MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField30MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JField30MouseClicked

    private void JField34MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField34MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JField34MouseClicked

    private void JField33MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField33MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JField33MouseClicked

    private void JField32MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField32MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JField32MouseClicked

    private void JField22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField22MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JField22MouseClicked

    private void presentationComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_presentationComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_presentationComboBox3ActionPerformed

    private void jLabel43MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel43MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel43MouseClicked

    private void jLabel42MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel42MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel42MouseClicked

    private void JField35MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField35MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JField35MouseClicked

    private void JField36MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField36MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JField36MouseClicked

    private void JField37MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField37MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JField37MouseClicked

    private void presentationComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_presentationComboBox4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_presentationComboBox4ActionPerformed

    private void JField38MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField38MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JField38MouseClicked

    private void JField39MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField39MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JField39MouseClicked

    private void JField38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JField38ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JField38ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField JField12;
    private javax.swing.JTextField JField13;
    private javax.swing.JTextField JField14;
    private javax.swing.JTextField JField15;
    private javax.swing.JTextField JField20;
    private javax.swing.JTextField JField22;
    private javax.swing.JTextField JField24;
    private javax.swing.JTextField JField26;
    private javax.swing.JTextField JField29;
    private javax.swing.JTextField JField30;
    private javax.swing.JTextField JField32;
    private javax.swing.JTextField JField33;
    private javax.swing.JTextField JField34;
    private javax.swing.JTextField JField35;
    private javax.swing.JTextField JField36;
    private javax.swing.JTextField JField37;
    private javax.swing.JTextField JField38;
    private javax.swing.JTextField JField39;
    private javax.swing.JSeparator JSeparator33;
    private javax.swing.JSeparator JSeparator37;
    private javax.swing.JSeparator JSeparator38;
    private javax.swing.JSeparator JSeparator39;
    private javax.swing.JSeparator JSeparator40;
    private javax.swing.JTabbedPane MainTabbedPanel;
    private javax.swing.JTabbedPane MainTabbedPanel1;
    private javax.swing.JPanel Panel1;
    private javax.swing.JPanel Panel2;
    private javax.swing.JPanel Panel4;
    private javax.swing.JPanel Panel6;
    private javax.swing.JPanel Panel8;
    private javax.swing.JPanel Panel9;
    private com.github.lgooddatepicker.components.DateTimePicker dateTimePicker1;
    private com.github.lgooddatepicker.components.DateTimePicker dateTimePicker2;
    private com.github.lgooddatepicker.components.DateTimePicker dateTimePicker3;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JLabel menuBtn11;
    private static javax.swing.JLabel menuBtn12;
    private static javax.swing.JLabel menuBtn13;
    private javax.swing.JLabel menuBtn14;
    private javax.swing.JLabel menuBtn15;
    private javax.swing.JLabel menuBtn16;
    private javax.swing.JLabel menuBtn22;
    private javax.swing.JLabel menuBtn23;
    private javax.swing.JLabel menuBtn24;
    private javax.swing.JLabel menuBtn25;
    private javax.swing.JLabel menuBtn26;
    private javax.swing.JLabel menuBtn27;
    private javax.swing.JLabel menuBtn28;
    private javax.swing.JLabel menuBtn29;
    private javax.swing.JLabel menuBtn3;
    private javax.swing.JLabel menuBtn30;
    private javax.swing.JLabel menuBtn31;
    private javax.swing.JLabel menuBtn32;
    private javax.swing.JLabel menuBtn33;
    private javax.swing.JLabel menuBtn37;
    private javax.swing.JLabel menuBtn38;
    private javax.swing.JLabel menuBtn39;
    private javax.swing.JLabel menuBtn4;
    private javax.swing.JLabel menuBtn43;
    private javax.swing.JLabel menuBtn45;
    private javax.swing.JLabel menuBtn48;
    private javax.swing.JLabel menuBtn49;
    private javax.swing.JLabel menuBtn50;
    private javax.swing.JLabel menuBtn53;
    private javax.swing.JLabel menuBtn54;
    private javax.swing.JLabel menuBtn55;
    private javax.swing.JLabel menuBtn56;
    private javax.swing.JLabel menuBtn58;
    private javax.swing.JLabel menuBtn60;
    private javax.swing.JLabel menuBtn61;
    private javax.swing.JLabel menuBtn62;
    private javax.swing.JLabel menuBtn63;
    private javax.swing.JLabel menuBtn64;
    private javax.swing.JLabel menuBtn65;
    private static javax.swing.JComboBox<String> presentationComboBox;
    private static javax.swing.JComboBox<String> presentationComboBox1;
    private static javax.swing.JComboBox<String> presentationComboBox2;
    private javax.swing.JComboBox<String> presentationComboBox3;
    private static javax.swing.JComboBox<String> presentationComboBox4;
    // End of variables declaration//GEN-END:variables
}
