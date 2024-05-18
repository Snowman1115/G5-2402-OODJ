/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.project.ui.student;

import com.github.lgooddatepicker.optionalusertools.DateTimeChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateTimeChangeEvent;
import com.project.common.constants.MessageConstant;
import com.project.common.constants.PresentationStatus;
import com.project.common.utils.DateTimeUtils;
import com.project.common.utils.Dialog;
import com.project.controller.PresentationController;
import java.text.SimpleDateFormat;

import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
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

        fillInDateTime();
        dateTimePicker1.addDateTimeChangeListener(new DateTimeChangeListener() {
            @Override
            public void dateOrTimeChanged(DateTimeChangeEvent event) {
                checkDateTime1();
            }

        });

        dateTimePicker2.addDateTimeChangeListener(new DateTimeChangeListener() {
            @Override
            public void dateOrTimeChanged(DateTimeChangeEvent event) {
                checkDateTime2();
            }

        });

        dateTimePicker3.addDateTimeChangeListener(new DateTimeChangeListener() {
            @Override
            public void dateOrTimeChanged(DateTimeChangeEvent event) {
                checkDateTime3();
            }

        });

        refresh();
    }

    private void refresh() {
        Map<String, Integer> presentation = PresentationController.getAllPresentationStatusByStudentId();
        menuBtn12.setText(presentation.get("pendingBooking").toString());
        menuBtn13.setText(presentation.get("overdue").toString());

        refreshTable();
        refreshjTable3(0);

        fillInComboBox1();
        fillInComboBox2();
        fillInComboBox3();
        fillInComboBox4();
    }

    private void fillInComboBox1() {
        presentationComboBox1.removeAllItems();
        List<Map<String, String>> lists2 = PresentationController.getAllUpcomingNPendingBookingPresentation();
        if (lists2.isEmpty()) {
            presentationComboBox1.addItem(MessageConstant.CONDITION_PRESENTATION_COMBOBOX);
        }
        for (Map<String, String> list : lists2) {
            presentationComboBox1.addItem(list.get("moduleName"));
        }
        presentationComboBox1.setSelectedIndex(0);
    }

    private void fillInComboBox2() {
        presentationComboBox2.removeAllItems();
        List<Map<String, String>> lists = PresentationController.getAllPresentationDetailsForStudent();
        List<Map<String, String>> pendingList = new ArrayList<>();
        for (Map<String, String> list : lists) {
            if(PresentationStatus.valueOf(list.get("Status")).equals(PresentationStatus.PENDING_CONFIRM)) {
                pendingList.add(list);
            }
        }

        if (pendingList.isEmpty()) {
            presentationComboBox2.addItem(MessageConstant.CONDITION_EDIT_PRESENTATION_COMBOBOX);
        }

        for (Map<String, String> list : lists) {
            if (PresentationStatus.valueOf(list.get("Status")).equals(PresentationStatus.PENDING_CONFIRM)) {
                presentationComboBox2.addItem(list.get("moduleName"));
            }
        }
        presentationComboBox2.setSelectedIndex(0);
    }

    private void fillInComboBox3() {
        presentationComboBox3.removeAllItems();
        List<Map<String, String>> lists = PresentationController.getAllPresentationDetailsForStudent();
        List<Map<String, String>> rejectedList = new ArrayList<>();
        for (Map<String, String> list : lists) {
            if(PresentationStatus.valueOf(list.get("Status")).equals(PresentationStatus.REJECTED)) {
                rejectedList.add(list);
            }
        }

        if (rejectedList.isEmpty()) {
            presentationComboBox3.addItem(MessageConstant.CONDITION_REJECT_PRESENTATION_COMBOBOX);
        }

        for (Map<String, String> list : lists) {
            if (PresentationStatus.valueOf(list.get("Status")).equals(PresentationStatus.REJECTED)) {
                presentationComboBox3.addItem(list.get("moduleName"));
            }
        }
        presentationComboBox3.setSelectedIndex(0);
    }


    private void fillInComboBox4() {
        presentationComboBox4.removeAllItems();
        List<Map<String, String>> lists = PresentationController.getAllPresentationDetailsForStudent();
        List<Map<String, String>> markeddList = new ArrayList<>();
        for (Map<String, String> list : lists) {
            if(PresentationStatus.valueOf(list.get("Status")).equals(PresentationStatus.MARKED)) {
                markeddList.add(list);
            }
        }

        if (markeddList.isEmpty()) {
            presentationComboBox4.addItem(MessageConstant.CONDITION_PRESENTATION_RESULT_COMBOBOX);
        }

        for (Map<String, String> list : lists) {
            if (PresentationStatus.valueOf(list.get("Status")).equals(PresentationStatus.MARKED)) {
                presentationComboBox4.addItem(list.get("moduleName"));
            }
        }
        presentationComboBox4.setSelectedIndex(0);
    }

    private void checkDateTime1() {
        if (dateTimePicker1.getDateTimePermissive().toLocalDate().isBefore(LocalDate.now())) {
            Dialog.ErrorDialog(MessageConstant.ERROR_PAST_DATE_SELECTION);
            fillInDateTime();
            return;
        }

        if (!presentationComboBox1.getSelectedItem().equals(MessageConstant.CONDITION_PRESENTATION_COMBOBOX)) {
            if (dateTimePicker1.getDateTimePermissive().isAfter(DateTimeUtils.formatDateTime(JField15.getText()))) {
                Dialog.ErrorDialog(MessageConstant.ERROR_OVER_DATE_SELECTION);
                fillInDateTime();
                return;
            }
        }
    }
    
    private void checkDateTime2() {
        if (!presentationComboBox2.getSelectedItem().equals(MessageConstant.CONDITION_EDIT_PRESENTATION_COMBOBOX)) {
            if (dateTimePicker2.getDateTimePermissive().isAfter(DateTimeUtils.formatDateTime(JField30.getText()))) {
                Dialog.ErrorDialog(MessageConstant.ERROR_OVER_DATE_SELECTION);
                return;
            }
        }
    }

    private void checkDateTime3() {
        if (!presentationComboBox3.getSelectedItem().equals(MessageConstant.CONDITION_REJECT_PRESENTATION_COMBOBOX)) {
            if (dateTimePicker3.getDateTimePermissive().isAfter(DateTimeUtils.formatDateTime(JField34.getText()))) {
                Dialog.ErrorDialog(MessageConstant.ERROR_OVER_DATE_SELECTION);
                return;
            }
        }
    }

    private void refreshComboBox1Details(Object value) {
        if (presentationComboBox1.getSelectedItem() != null) {
            
            if (presentationComboBox1.getSelectedItem().equals(MessageConstant.CONDITION_PRESENTATION_COMBOBOX)) {
                JField14.setText("Presentation ID");
                JField13.setText("Lectuerer Name");
                JField15.setText("Project Due Date");
                JField24.setText("Status");
            } else {
                List<Map<String, String>> lists = PresentationController.getAllPresentationDetailsForStudent();
                for (Map<String, String> list : lists) {
                    if (value.equals(list.get("moduleName"))) {
                        JField14.setText(list.get("id"));
                        JField13.setText(list.get("lecturerName"));
                        JField15.setText(list.get("dueDate"));
                        JField24.setText(list.get("Status"));
                    }
                }
            }
        }
    }

    private void refreshComboBox2Details(Object value) {
        if (presentationComboBox2.getSelectedItem() != null) {
            
            if (presentationComboBox2.getSelectedItem().equals(MessageConstant.CONDITION_EDIT_PRESENTATION_COMBOBOX)) {
                JField20.setText("Presentation ID");
                JField29.setText("Lectuerer Name");
                JField30.setText("Project Due Date");
                JField26.setText("Status");
                dateTimePicker2.setDateTimePermissive(LocalDateTime.now());
            } else {
                List<Map<String, String>> lists = PresentationController.getAllPresentationDetailsForStudent();
                for (Map<String, String> list : lists) {
                    if (value.equals(list.get("moduleName"))) {
                        JField20.setText(list.get("id"));
                        JField29.setText(list.get("lecturerName"));
                        JField30.setText(list.get("dueDate"));
                        JField26.setText(list.get("Status"));
                        dateTimePicker2.setDateTimePermissive(DateTimeUtils.formatDateTime(list.get("presentationDate")));
                    }
                }
            }
        }
    }

    private void refreshComboBox3Details(Object value) {
        if (presentationComboBox3.getSelectedItem() != null) {
            if (presentationComboBox3.getSelectedItem().equals(MessageConstant.CONDITION_REJECT_PRESENTATION_COMBOBOX)) {
                JField22.setText("Presentation ID");
                JField33.setText("Lectuerer Name");
                JField34.setText("Project Due Date");
                JField32.setText("Status");
                dateTimePicker3.setDateTimePermissive(LocalDateTime.now());
            } else {
                List<Map<String, String>> lists = PresentationController.getAllPresentationDetailsForStudent();
                for (Map<String, String> list : lists) {
                    if (value.equals(list.get("moduleName"))) {
                        JField22.setText(list.get("id"));
                        JField33.setText(list.get("lecturerName"));
                        JField34.setText(list.get("dueDate"));
                        JField32.setText(list.get("Status"));
                        dateTimePicker3.setDateTimePermissive(DateTimeUtils.formatDateTime(list.get("presentationDate")));
                    }
                }
            }
        }
    }

    private void refreshComboBox4Details(Object value) {
        if (presentationComboBox4.getSelectedItem() != null) {
            List<Map<String, String>> lists = PresentationController.getAllPresentationDetailsForStudent();
            for (Map<String, String> list : lists) {
                if (value.equals(list.get("moduleName"))) {
                    JField37.setText(list.get("id"));
                    JField36.setText(list.get("lecturerName"));
                    JField35.setText(list.get("dueDate"));
                    JField38.setText(list.get("presentationDate"));
                    JField40.setText(list.get("result"));
                    JField39.setText(list.get("Status"));
                }
            }
        }
    }

    private void fillInDateTime() {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0));
        dateTimePicker1.setDateTimePermissive(localDateTime);
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
                    if (PresentationStatus.valueOf(list.get("Status")).equals(PresentationStatus.PENDING_BOOKING)) {
                        String[] data = {list.get("moduleName"), list.get("lecturerName"), list.get("dueDate"), list.get("presentationDate"), list.get("Status")};
                        dtm.addRow(data);
                    }
                }
            }
            case 2 -> {
                for (Map<String, String> list : lists) {
                    if (PresentationStatus.valueOf(list.get("Status")).equals(PresentationStatus.PENDING_CONFIRM)) {
                        String[] data = {list.get("moduleName"), list.get("lecturerName"), list.get("dueDate"), list.get("presentationDate"), list.get("Status")};
                        dtm.addRow(data);
                    }
                }
            }
            case 3 -> {
                for (Map<String, String> list : lists) {
                    if (PresentationStatus.valueOf(list.get("Status")).equals(PresentationStatus.REJECTED)) {
                        String[] data = {list.get("moduleName"), list.get("lecturerName"), list.get("dueDate"), list.get("presentationDate"), list.get("Status")};
                        dtm.addRow(data);
                    }
                }
            }
            case 4 -> {
                for (Map<String, String> list : lists) {
                    if (PresentationStatus.valueOf(list.get("Status")).equals(PresentationStatus.BOOKED)) {
                        String[] data = {list.get("moduleName"), list.get("lecturerName"), list.get("dueDate"), list.get("presentationDate"), list.get("Status")};
                        dtm.addRow(data);
                    }
                }
            }
            case 5 -> {
                for (Map<String, String> list : lists) {
                    if (PresentationStatus.valueOf(list.get("Status")).equals(PresentationStatus.MARKED)) {
                        String[] data = {list.get("moduleName"), list.get("lecturerName"), list.get("dueDate"), list.get("presentationDate"), list.get("Status")};
                        dtm.addRow(data);
                    }
                }
            }
            case 6 -> {
                for (Map<String, String> list : lists) {
                    if (PresentationStatus.valueOf(list.get("Status")).equals(PresentationStatus.OVERDUE)) {
                        String[] data = {list.get("moduleName"), list.get("lecturerName"), list.get("dueDate"), list.get("presentationDate"), list.get("Status")};
                        dtm.addRow(data);
                    }
                }
            }
        }  
    }

    private void refreshTable() {
        DefaultTableModel dtm =  (DefaultTableModel)jTable5.getModel();
        dtm.setRowCount(0);
        List<Map<String, String>> lists = PresentationController.getAllUpcomingNPendingBookingPresentation();
        for (Map<String,String> list : lists) {
            String[] data = {list.get("moduleName"), list.get("dueDate")};
            dtm.addRow(data);
        }
        
        DefaultTableModel dtm1 =  (DefaultTableModel)jTable4.getModel();
        dtm1.setRowCount(0);
        List<Map<String, String>> lists1 = PresentationController.getAllPresentationDetailsForStudent();
        for (Map<String,String> list : lists1) {
            if (PresentationStatus.valueOf(list.get("Status")).equals(PresentationStatus.BOOKED)) {
                String[] data = {list.get("moduleName"),list.get("presentationDate")};
                dtm1.addRow(data);
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
        menuBtn16 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        menuBtn17 = new javax.swing.JLabel();
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
        presentationComboBox2 = new javax.swing.JComboBox<>();
        JField20 = new javax.swing.JTextField();
        dateTimePicker2 = new com.github.lgooddatepicker.components.DateTimePicker();
        JField26 = new javax.swing.JTextField();
        JField29 = new javax.swing.JTextField();
        JField30 = new javax.swing.JTextField();
        menuBtn34 = new javax.swing.JLabel();
        menuBtn35 = new javax.swing.JLabel();
        menuBtn40 = new javax.swing.JLabel();
        menuBtn36 = new javax.swing.JLabel();
        menuBtn41 = new javax.swing.JLabel();
        menuBtn42 = new javax.swing.JLabel();
        Panel9 = new javax.swing.JPanel();
        menuBtn31 = new javax.swing.JLabel();
        menuBtn49 = new javax.swing.JLabel();
        JSeparator39 = new javax.swing.JSeparator();
        jLabel43 = new javax.swing.JLabel();
        presentationComboBox3 = new javax.swing.JComboBox<>();
        JField22 = new javax.swing.JTextField();
        dateTimePicker3 = new com.github.lgooddatepicker.components.DateTimePicker();
        JField32 = new javax.swing.JTextField();
        JField33 = new javax.swing.JTextField();
        JField34 = new javax.swing.JTextField();
        menuBtn44 = new javax.swing.JLabel();
        menuBtn46 = new javax.swing.JLabel();
        menuBtn47 = new javax.swing.JLabel();
        menuBtn51 = new javax.swing.JLabel();
        menuBtn52 = new javax.swing.JLabel();
        menuBtn57 = new javax.swing.JLabel();
        Panel6 = new javax.swing.JPanel();
        menuBtn38 = new javax.swing.JLabel();
        JField35 = new javax.swing.JTextField();
        JField36 = new javax.swing.JTextField();
        JField37 = new javax.swing.JTextField();
        presentationComboBox4 = new javax.swing.JComboBox<>();
        JField38 = new javax.swing.JTextField();
        JField39 = new javax.swing.JTextField();
        JField40 = new javax.swing.JTextField();
        menuBtn59 = new javax.swing.JLabel();
        menuBtn67 = new javax.swing.JLabel();
        menuBtn68 = new javax.swing.JLabel();
        menuBtn69 = new javax.swing.JLabel();
        menuBtn70 = new javax.swing.JLabel();
        menuBtn71 = new javax.swing.JLabel();
        menuBtn72 = new javax.swing.JLabel();

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
        menuBtn11.setText("UPCOMING PRESENTATION");
        menuBtn11.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn11.setOpaque(true);
        Panel1.add(menuBtn11, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 370, 330, 40));

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
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(47, Short.MAX_VALUE))
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

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Module", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable4.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(jTable4);
        if (jTable4.getColumnModel().getColumnCount() > 0) {
            jTable4.getColumnModel().getColumn(0).setResizable(false);
            jTable4.getColumnModel().getColumn(1).setResizable(false);
        }

        Panel1.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 410, 330, 190));

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable5.getTableHeader().setReorderingAllowed(false);
        jScrollPane5.setViewportView(jTable5);
        if (jTable5.getColumnModel().getColumnCount() > 0) {
            jTable5.getColumnModel().getColumn(0).setResizable(false);
            jTable5.getColumnModel().getColumn(1).setResizable(false);
        }

        Panel1.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 180, 330, 190));

        menuBtn17.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/calendar-24x24.png"))); // NOI18N
        menuBtn17.setText("PENDING BOOKING");
        menuBtn17.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn17.setOpaque(true);
        Panel1.add(menuBtn17, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 140, 330, 40));

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
        menuBtn23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/user-24x24.png"))); // NOI18N
        menuBtn23.setText("LECTURER NAME");
        menuBtn23.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn23.setOpaque(true);
        Panel4.add(menuBtn23, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 330, 40));

        menuBtn24.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/search-24x24.png"))); // NOI18N
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

        JField14.setEditable(false);
        JField14.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField14.setText("Presentation ID");
        JField14.setBackground(new java.awt.Color(255, 255, 255));
        JField14.setBorder(null);
        JField14.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField14.setForeground(new java.awt.Color(1, 1, 1));
        JField14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField14MouseClicked(evt);
            }
        });
        Panel4.add(JField14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 330, 35));

        JField13.setEditable(false);
        JField13.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField13.setText("Lectuerer Name");
        JField13.setBackground(new java.awt.Color(255, 255, 255));
        JField13.setBorder(null);
        JField13.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField13.setForeground(new java.awt.Color(1, 1, 1));
        JField13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField13MouseClicked(evt);
            }
        });
        Panel4.add(JField13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 330, 35));

        menuBtn26.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn26.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/calendar-24x24.png"))); // NOI18N
        menuBtn26.setText("DUE DATE");
        menuBtn26.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn26.setOpaque(true);
        Panel4.add(menuBtn26, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 330, 40));

        JField15.setEditable(false);
        JField15.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField15.setText("Project Due Date");
        JField15.setBackground(new java.awt.Color(255, 255, 255));
        JField15.setBorder(null);
        JField15.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField15.setForeground(new java.awt.Color(1, 1, 1));
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
        menuBtn39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/calendar-24x24.png"))); // NOI18N
        menuBtn39.setText("PRESENTATION DATE");
        menuBtn39.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn39.setOpaque(true);
        Panel4.add(menuBtn39, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 130, 330, 40));

        menuBtn27.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/status-24x24.png"))); // NOI18N
        menuBtn27.setText("STATUS");
        menuBtn27.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn27.setOpaque(true);
        Panel4.add(menuBtn27, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 220, 330, 40));

        JField24.setEditable(false);
        JField24.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField24.setText("Status");
        JField24.setBackground(new java.awt.Color(255, 255, 255));
        JField24.setBorder(null);
        JField24.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField24.setForeground(new java.awt.Color(1, 1, 1));
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

        menuBtn30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn30.setText("MODIFICATION");
        menuBtn30.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn30.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn30.setOpaque(true);
        Panel8.add(menuBtn30, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 500, 40));

        menuBtn37.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/action-24x24.png"))); // NOI18N
        menuBtn37.setText("ACTION :");
        menuBtn37.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn37.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn37.setOpaque(true);
        Panel8.add(menuBtn37, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 510, 90, 40));

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/delete-red-24x24.png"))); // NOI18N
        jLabel40.setText("CANCEL");
        jLabel40.setBackground(new java.awt.Color(254, 254, 254));
        jLabel40.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel40.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(1, 1, 1));
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

        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/edit-orange-24x24.png"))); // NOI18N
        jLabel41.setText("EDIT");
        jLabel41.setBackground(new java.awt.Color(254, 254, 254));
        jLabel41.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel41.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(1, 1, 1));
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

        presentationComboBox2.setBackground(new java.awt.Color(254, 254, 254));
        presentationComboBox2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        presentationComboBox2.setFocusable(false);
        presentationComboBox2.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 12)); // NOI18N
        presentationComboBox2.setToolTipText("d");
        presentationComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                presentationComboBox2ActionPerformed(evt);
            }
        });
        Panel8.add(presentationComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 330, 35));

        JField20.setEditable(false);
        JField20.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField20.setText("Presentation ID");
        JField20.setBackground(new java.awt.Color(255, 255, 255));
        JField20.setBorder(null);
        JField20.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField20.setForeground(new java.awt.Color(1, 1, 1));
        JField20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField20MouseClicked(evt);
            }
        });
        Panel8.add(JField20, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 330, 35));
        Panel8.add(dateTimePicker2, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 170, 330, 35));

        JField26.setEditable(false);
        JField26.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField26.setText("Status");
        JField26.setBackground(new java.awt.Color(255, 255, 255));
        JField26.setBorder(null);
        JField26.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField26.setForeground(new java.awt.Color(1, 1, 1));
        JField26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField26MouseClicked(evt);
            }
        });
        Panel8.add(JField26, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 260, 330, 35));

        JField29.setEditable(false);
        JField29.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField29.setText("Lectuerer Name");
        JField29.setBackground(new java.awt.Color(255, 255, 255));
        JField29.setBorder(null);
        JField29.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField29.setForeground(new java.awt.Color(1, 1, 1));
        JField29.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField29MouseClicked(evt);
            }
        });
        Panel8.add(JField29, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 330, 35));

        JField30.setEditable(false);
        JField30.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField30.setText("Project Due Date");
        JField30.setBackground(new java.awt.Color(255, 255, 255));
        JField30.setBorder(null);
        JField30.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField30.setForeground(new java.awt.Color(1, 1, 1));
        JField30.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField30MouseClicked(evt);
            }
        });
        Panel8.add(JField30, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 350, 330, 35));

        menuBtn34.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/search-24x24.png"))); // NOI18N
        menuBtn34.setText("SELECT MODULE");
        menuBtn34.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn34.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn34.setOpaque(true);
        Panel8.add(menuBtn34, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 330, 40));

        menuBtn35.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn35.setText("PRESENTATION ID");
        menuBtn35.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn35.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn35.setOpaque(true);
        Panel8.add(menuBtn35, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 330, 40));

        menuBtn40.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/calendar-24x24.png"))); // NOI18N
        menuBtn40.setText("PRESENTATION DATE");
        menuBtn40.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn40.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn40.setOpaque(true);
        Panel8.add(menuBtn40, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 130, 330, 40));

        menuBtn36.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/status-24x24.png"))); // NOI18N
        menuBtn36.setText("STATUS");
        menuBtn36.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn36.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn36.setOpaque(true);
        Panel8.add(menuBtn36, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 220, 330, 40));

        menuBtn41.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/user-24x24.png"))); // NOI18N
        menuBtn41.setText("LECTURER NAME");
        menuBtn41.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn41.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn41.setOpaque(true);
        Panel8.add(menuBtn41, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 330, 40));

        menuBtn42.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/calendar-24x24.png"))); // NOI18N
        menuBtn42.setText("DUE DATE");
        menuBtn42.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn42.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn42.setOpaque(true);
        Panel8.add(menuBtn42, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 330, 40));

        MainTabbedPanel1.addTab("Edit", Panel8);

        Panel9.setPreferredSize(new java.awt.Dimension(1050, 570));
        Panel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menuBtn31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn31.setText("RE-SCHEDULE");
        menuBtn31.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn31.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn31.setOpaque(true);
        Panel9.add(menuBtn31, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 500, 40));

        menuBtn49.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/action-24x24.png"))); // NOI18N
        menuBtn49.setText("ACTION :");
        menuBtn49.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn49.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn49.setOpaque(true);
        Panel9.add(menuBtn49, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 510, 90, 40));

        JSeparator39.setForeground(new java.awt.Color(1, 1, 1));
        Panel9.add(JSeparator39, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 550, 170, 10));

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/add-green-24x24.png"))); // NOI18N
        jLabel43.setText("REBOOK");
        jLabel43.setBackground(new java.awt.Color(254, 254, 254));
        jLabel43.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel43.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(1, 1, 1));
        jLabel43.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel43.setOpaque(true);
        jLabel43.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel43MouseClicked(evt);
            }
        });
        Panel9.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 510, 170, 35));

        presentationComboBox3.setBackground(new java.awt.Color(254, 254, 254));
        presentationComboBox3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        presentationComboBox3.setFocusable(false);
        presentationComboBox3.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 12)); // NOI18N
        presentationComboBox3.setToolTipText("d");
        presentationComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                presentationComboBox3ActionPerformed(evt);
            }
        });
        Panel9.add(presentationComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 330, 35));

        JField22.setEditable(false);
        JField22.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField22.setText("Presentation ID");
        JField22.setBackground(new java.awt.Color(255, 255, 255));
        JField22.setBorder(null);
        JField22.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField22.setForeground(new java.awt.Color(1, 1, 1));
        JField22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField22MouseClicked(evt);
            }
        });
        Panel9.add(JField22, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 330, 35));
        Panel9.add(dateTimePicker3, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 170, 330, 35));

        JField32.setEditable(false);
        JField32.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField32.setText("Status");
        JField32.setBackground(new java.awt.Color(255, 255, 255));
        JField32.setBorder(null);
        JField32.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField32.setForeground(new java.awt.Color(1, 1, 1));
        JField32.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField32MouseClicked(evt);
            }
        });
        Panel9.add(JField32, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 260, 330, 35));

        JField33.setEditable(false);
        JField33.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField33.setText("Lectuerer Name");
        JField33.setBackground(new java.awt.Color(255, 255, 255));
        JField33.setBorder(null);
        JField33.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField33.setForeground(new java.awt.Color(1, 1, 1));
        JField33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField33MouseClicked(evt);
            }
        });
        Panel9.add(JField33, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 330, 35));

        JField34.setEditable(false);
        JField34.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField34.setText("Project Due Date");
        JField34.setBackground(new java.awt.Color(255, 255, 255));
        JField34.setBorder(null);
        JField34.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField34.setForeground(new java.awt.Color(1, 1, 1));
        JField34.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField34MouseClicked(evt);
            }
        });
        Panel9.add(JField34, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 350, 330, 35));

        menuBtn44.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/search-24x24.png"))); // NOI18N
        menuBtn44.setText("SELECT MODULE");
        menuBtn44.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn44.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn44.setOpaque(true);
        Panel9.add(menuBtn44, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 330, 40));

        menuBtn46.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn46.setText("PRESENTATION ID");
        menuBtn46.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn46.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn46.setOpaque(true);
        Panel9.add(menuBtn46, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 330, 40));

        menuBtn47.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/calendar-24x24.png"))); // NOI18N
        menuBtn47.setText("PRESENTATION DATE");
        menuBtn47.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn47.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn47.setOpaque(true);
        Panel9.add(menuBtn47, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 130, 330, 40));

        menuBtn51.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/status-24x24.png"))); // NOI18N
        menuBtn51.setText("STATUS");
        menuBtn51.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn51.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn51.setOpaque(true);
        Panel9.add(menuBtn51, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 220, 330, 40));

        menuBtn52.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/user-24x24.png"))); // NOI18N
        menuBtn52.setText("LECTURER NAME");
        menuBtn52.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn52.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn52.setOpaque(true);
        Panel9.add(menuBtn52, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 330, 40));

        menuBtn57.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn57.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/calendar-24x24.png"))); // NOI18N
        menuBtn57.setText("DUE DATE");
        menuBtn57.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn57.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn57.setOpaque(true);
        Panel9.add(menuBtn57, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 330, 40));

        MainTabbedPanel1.addTab("Rejected", Panel9);

        Panel6.setPreferredSize(new java.awt.Dimension(1050, 570));
        Panel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menuBtn38.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn38.setText("RESULT");
        menuBtn38.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn38.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn38.setOpaque(true);
        Panel6.add(menuBtn38, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 500, 40));

        JField35.setEditable(false);
        JField35.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField35.setText("Project Due Date");
        JField35.setBackground(new java.awt.Color(255, 255, 255));
        JField35.setBorder(null);
        JField35.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField35.setForeground(new java.awt.Color(1, 1, 1));
        JField35.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField35MouseClicked(evt);
            }
        });
        Panel6.add(JField35, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 350, 330, 35));

        JField36.setEditable(false);
        JField36.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField36.setText("Lectuerer Name");
        JField36.setBackground(new java.awt.Color(255, 255, 255));
        JField36.setBorder(null);
        JField36.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField36.setForeground(new java.awt.Color(1, 1, 1));
        JField36.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField36MouseClicked(evt);
            }
        });
        Panel6.add(JField36, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 330, 35));

        JField37.setEditable(false);
        JField37.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField37.setText("Module Name");
        JField37.setBackground(new java.awt.Color(255, 255, 255));
        JField37.setBorder(null);
        JField37.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField37.setForeground(new java.awt.Color(1, 1, 1));
        JField37.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField37MouseClicked(evt);
            }
        });
        Panel6.add(JField37, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 330, 35));

        presentationComboBox4.setBackground(new java.awt.Color(254, 254, 254));
        presentationComboBox4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        presentationComboBox4.setFocusable(false);
        presentationComboBox4.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 12)); // NOI18N
        presentationComboBox4.setToolTipText("d");
        presentationComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                presentationComboBox4ActionPerformed(evt);
            }
        });
        Panel6.add(presentationComboBox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 330, 35));

        JField38.setEditable(false);
        JField38.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField38.setText("Presentation Date");
        JField38.setBackground(new java.awt.Color(255, 255, 255));
        JField38.setBorder(null);
        JField38.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField38.setForeground(new java.awt.Color(1, 1, 1));
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

        JField39.setEditable(false);
        JField39.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField39.setText("Status");
        JField39.setBackground(new java.awt.Color(255, 255, 255));
        JField39.setBorder(null);
        JField39.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField39.setForeground(new java.awt.Color(1, 1, 1));
        JField39.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField39MouseClicked(evt);
            }
        });
        Panel6.add(JField39, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 260, 330, 35));

        JField40.setEditable(false);
        JField40.setBackground(new java.awt.Color(255, 255, 255));
        JField40.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField40.setForeground(new java.awt.Color(1, 1, 1));
        JField40.setText("Result");
        JField40.setBorder(null);
        JField40.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField40.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField40MouseClicked(evt);
            }
        });
        Panel6.add(JField40, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 350, 330, 35));

        menuBtn59.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn59.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/search-24x24.png"))); // NOI18N
        menuBtn59.setText("SELECT MODULE");
        menuBtn59.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn59.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn59.setOpaque(true);
        Panel6.add(menuBtn59, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 330, 40));

        menuBtn67.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn67.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn67.setText("PRESENTATION ID");
        menuBtn67.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn67.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn67.setOpaque(true);
        Panel6.add(menuBtn67, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 330, 40));

        menuBtn68.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn68.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/calendar-24x24.png"))); // NOI18N
        menuBtn68.setText("PRESENTATION DATE");
        menuBtn68.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn68.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn68.setOpaque(true);
        Panel6.add(menuBtn68, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 130, 330, 40));

        menuBtn69.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn69.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn69.setText("RESULT");
        menuBtn69.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn69.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn69.setOpaque(true);
        Panel6.add(menuBtn69, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 310, 330, 40));

        menuBtn70.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn70.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/user-24x24.png"))); // NOI18N
        menuBtn70.setText("LECTURER NAME");
        menuBtn70.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn70.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn70.setOpaque(true);
        Panel6.add(menuBtn70, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 330, 40));

        menuBtn71.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn71.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/calendar-24x24.png"))); // NOI18N
        menuBtn71.setText("DUE DATE");
        menuBtn71.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn71.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn71.setOpaque(true);
        Panel6.add(menuBtn71, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 330, 40));

        menuBtn72.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn72.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/status-24x24.png"))); // NOI18N
        menuBtn72.setText("STATUS");
        menuBtn72.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn72.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn72.setOpaque(true);
        Panel6.add(menuBtn72, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 220, 330, 40));

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
        if (presentationComboBox2.getSelectedItem().equals(MessageConstant.CONDITION_EDIT_PRESENTATION_COMBOBOX)) {
            Dialog.ErrorDialog(MessageConstant.CONDITION_EDIT_PRESENTATION_COMBOBOX);
            return;
        }

        if (PresentationController.editPresentationSlotForStudent(Integer.parseInt(JField20.getText()),dateTimePicker2.getDateTimePermissive())) {
            refresh();
        }
    }//GEN-LAST:event_jLabel41MouseClicked

    private void jLabel40MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel40MouseClicked
        if (presentationComboBox2.getSelectedItem().equals(MessageConstant.CONDITION_EDIT_PRESENTATION_COMBOBOX)) {
            Dialog.ErrorDialog(MessageConstant.CONDITION_EDIT_PRESENTATION_COMBOBOX);
            return;
        }

        if (PresentationController.cancelPresentationSlotForStudent(Integer.parseInt(JField20.getText()))) {
            refresh();
        }
    }//GEN-LAST:event_jLabel40MouseClicked

    private void presentationComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_presentationComboBox1ActionPerformed
        refreshComboBox1Details(presentationComboBox1.getSelectedItem());
    }//GEN-LAST:event_presentationComboBox1ActionPerformed

    private void jLabel36MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel36MouseClicked
        if (presentationComboBox1.getSelectedItem().equals(MessageConstant.CONDITION_PRESENTATION_COMBOBOX)) {
            Dialog.ErrorDialog(MessageConstant.CONDITION_PRESENTATION_COMBOBOX);
            return;
        }

        if (PresentationController.bookPresentationSlotForStudent(Integer.parseInt(JField14.getText()),dateTimePicker1.getDateTimePermissive())) {
            refresh();
        }

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
        refreshComboBox2Details(presentationComboBox2.getSelectedItem());
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
        refreshComboBox3Details(presentationComboBox3.getSelectedItem());
    }//GEN-LAST:event_presentationComboBox3ActionPerformed

    private void jLabel43MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel43MouseClicked
        if (presentationComboBox3.getSelectedItem().equals(MessageConstant.CONDITION_REJECT_PRESENTATION_COMBOBOX)) {
            Dialog.ErrorDialog(MessageConstant.CONDITION_REJECT_PRESENTATION_COMBOBOX);
            return;
        }

        if (PresentationController.editPresentationSlotForStudent(Integer.parseInt(JField22.getText()),dateTimePicker3.getDateTimePermissive())) {
            refresh();
        }
    }//GEN-LAST:event_jLabel43MouseClicked

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
        refreshComboBox4Details(presentationComboBox4.getSelectedItem());
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

    private void JField40MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField40MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JField40MouseClicked


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
    private javax.swing.JTextField JField40;
    private javax.swing.JSeparator JSeparator33;
    private javax.swing.JSeparator JSeparator37;
    private javax.swing.JSeparator JSeparator38;
    private javax.swing.JSeparator JSeparator39;
    private javax.swing.JTabbedPane MainTabbedPanel;
    private javax.swing.JTabbedPane MainTabbedPanel1;
    private javax.swing.JPanel Panel1;
    private javax.swing.JPanel Panel2;
    private javax.swing.JPanel Panel4;
    private javax.swing.JPanel Panel6;
    private javax.swing.JPanel Panel8;
    private javax.swing.JPanel Panel9;
    private static com.github.lgooddatepicker.components.DateTimePicker dateTimePicker1;
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
    private javax.swing.JLabel jLabel43;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JLabel menuBtn11;
    private static javax.swing.JLabel menuBtn12;
    private static javax.swing.JLabel menuBtn13;
    private javax.swing.JLabel menuBtn14;
    private javax.swing.JLabel menuBtn15;
    private javax.swing.JLabel menuBtn16;
    private javax.swing.JLabel menuBtn17;
    private javax.swing.JLabel menuBtn22;
    private javax.swing.JLabel menuBtn23;
    private javax.swing.JLabel menuBtn24;
    private javax.swing.JLabel menuBtn25;
    private javax.swing.JLabel menuBtn26;
    private javax.swing.JLabel menuBtn27;
    private javax.swing.JLabel menuBtn29;
    private javax.swing.JLabel menuBtn3;
    private javax.swing.JLabel menuBtn30;
    private javax.swing.JLabel menuBtn31;
    private javax.swing.JLabel menuBtn34;
    private javax.swing.JLabel menuBtn35;
    private javax.swing.JLabel menuBtn36;
    private javax.swing.JLabel menuBtn37;
    private javax.swing.JLabel menuBtn38;
    private javax.swing.JLabel menuBtn39;
    private javax.swing.JLabel menuBtn4;
    private javax.swing.JLabel menuBtn40;
    private javax.swing.JLabel menuBtn41;
    private javax.swing.JLabel menuBtn42;
    private javax.swing.JLabel menuBtn44;
    private javax.swing.JLabel menuBtn46;
    private javax.swing.JLabel menuBtn47;
    private javax.swing.JLabel menuBtn49;
    private javax.swing.JLabel menuBtn51;
    private javax.swing.JLabel menuBtn52;
    private javax.swing.JLabel menuBtn57;
    private javax.swing.JLabel menuBtn59;
    private javax.swing.JLabel menuBtn67;
    private javax.swing.JLabel menuBtn68;
    private javax.swing.JLabel menuBtn69;
    private javax.swing.JLabel menuBtn70;
    private javax.swing.JLabel menuBtn71;
    private javax.swing.JLabel menuBtn72;
    private static javax.swing.JComboBox<String> presentationComboBox;
    private static javax.swing.JComboBox<String> presentationComboBox1;
    private static javax.swing.JComboBox<String> presentationComboBox2;
    private static javax.swing.JComboBox<String> presentationComboBox3;
    private static javax.swing.JComboBox<String> presentationComboBox4;
    // End of variables declaration//GEN-END:variables
}
