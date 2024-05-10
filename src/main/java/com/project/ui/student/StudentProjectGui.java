/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.project.ui.student;

import com.project.common.constants.MessageConstant;
import com.project.common.constants.ReportStatus;
import com.project.common.utils.Dialog;
import com.project.controller.SubmissionController;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;

/**
 *
 * @author Olaf
 */
public class StudentProjectGui extends javax.swing.JInternalFrame {

    private String uploadedfilePath = null; 
    private JPanel pdfSubmissionPreview = null;
    private JPanel pdfSubmissionPreview2 = null;
    private JPanel pdfSubmissionPreview3 = null;
    
    /**
     * Creates new form StudentAssignmentGui
     */
    public StudentProjectGui() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI)this.getUI();
        ui.setNorthPane(null);
        
        // salesManagementPanel.setText(PropertiesReader.getProperty("SalesManagementPanelVersion"));
        refresh();
    }

    private void refresh() {
        Map<String, Integer> submission = SubmissionController.getAllSubmissionStatusByStudentId();
        menuBtn12.setText(submission.get("pendingSubmit").toString());
        menuBtn13.setText(submission.get("overdue").toString());
        
        refreshTable();
        refreshjTable3(0);
        fillInComboBox1();
        fillInComboBox2();
        fillInComboBox3();
        fillInComboBox4();
        
        menuBtn22.setVisible(false);
    }

    private void fillInComboBox1() {
        projectComboBox1.removeAllItems();
        List<Map<String, String>> lists = SubmissionController.getAllSubmissionDetailsForStudent();
        List<Map<String, String>> pendingSubmit = new ArrayList<>();
        for (Map<String, String> list : lists) {
            if(ReportStatus.valueOf(list.get("Status")).equals(ReportStatus.PENDING_SUBMIT)) {
                pendingSubmit.add(list);
            }
        }

        if (pendingSubmit.isEmpty()) {
            projectComboBox1.addItem(MessageConstant.CONDITION_PROJECT_COMBOBOX);
        }

        for (Map<String, String> list : lists) {
            if (ReportStatus.valueOf(list.get("Status")).equals(ReportStatus.PENDING_SUBMIT)) {
                projectComboBox1.addItem(list.get("moduleName"));
            }
        }
        projectComboBox1.setSelectedIndex(0);
    }
    
    private void fillInComboBox2() {
        projectComboBox2.removeAllItems();
        List<Map<String, String>> lists = SubmissionController.getAllSubmissionDetailsForStudent();
        List<Map<String, String>> pendingSubmit = new ArrayList<>();
        for (Map<String, String> list : lists) {
            if(ReportStatus.valueOf(list.get("Status")).equals(ReportStatus.PENDING_MARKING)) {
                pendingSubmit.add(list);
            }
        }

        if (pendingSubmit.isEmpty()) {
            projectComboBox2.addItem(MessageConstant.CONDITION_EDIT_PROJECT_COMBOBOX);
        }

        for (Map<String, String> list : lists) {
            if (ReportStatus.valueOf(list.get("Status")).equals(ReportStatus.PENDING_MARKING)) {
                projectComboBox2.addItem(list.get("moduleName"));
            }
        }
        projectComboBox2.setSelectedIndex(0);
    }
    
    private void fillInComboBox3() {
        projectComboBox4.removeAllItems();
        List<Map<String, String>> lists = SubmissionController.getAllSubmissionDetailsForStudent();
        List<Map<String, String>> pendingSubmit = new ArrayList<>();
        for (Map<String, String> list : lists) {
            if(ReportStatus.valueOf(list.get("Status")).equals(ReportStatus.MARKED_2)) {
                pendingSubmit.add(list);
            }
        }

        if (pendingSubmit.isEmpty()) {
            projectComboBox4.addItem(MessageConstant.CONDITION_PROJECT_RESULT_COMBOBOX);
        }

        for (Map<String, String> list : lists) {
            if (ReportStatus.valueOf(list.get("Status")).equals(ReportStatus.MARKED_2)) {
                projectComboBox4.addItem(list.get("moduleName"));
            }
        }
        projectComboBox4.setSelectedIndex(0);
    }
    
    private void fillInComboBox4() {
        projectComboBox5.removeAllItems();
        List<Map<String, String>> lists = SubmissionController.getAllSubmissionDetailsForStudent();
        List<Map<String, String>> submmitedList = new ArrayList<>();

        for (Map<String, String> list : lists) {
            if(!list.get("reportId").equals("0")) {
                submmitedList.add(list);
            }
        }

        if (submmitedList.isEmpty()) {
            projectComboBox5.addItem(MessageConstant.CONDITION_NO_SUBMITTED_PROJECT_COMBOBOX);
        }

        for (Map<String, String> list : lists) {
            if (!list.get("reportId").equals("0")) {
                projectComboBox5.addItem(list.get("moduleName"));
            }
        }
        projectComboBox5.setSelectedIndex(0);
    }
    
    private void refreshjTable3(Integer value) {
        DefaultTableModel dtm = (DefaultTableModel)jTable3.getModel();
        dtm.setRowCount(0);
        
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dtm);
        jTable3.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter("".trim()));
        
        
        List<Map<String, String>> lists = SubmissionController.getAllSubmissionDetailsForStudent();

        switch(value) {
            case 0 -> {
                for (Map<String, String> list : lists) {
                    String[] data = {list.get("moduleName"), list.get("type"), list.get("dueDate"), list.get("Status")};
                    dtm.addRow(data);
                }
            }
            case 1 -> {
                for (Map<String, String> list : lists) {
                    if (ReportStatus.valueOf(list.get("Status")).equals(ReportStatus.PENDING_SUBMIT)) {
                        String[] data = {list.get("moduleName"), list.get("type"), list.get("dueDate"), list.get("Status")};
                    dtm.addRow(data);
                    }
                }
            }
            case 2 -> {
                for (Map<String, String> list : lists) {
                    if (ReportStatus.valueOf(list.get("Status")).equals(ReportStatus.PENDING_MARKING)) {
                        String[] data = {list.get("moduleName"), list.get("type"), list.get("dueDate"), list.get("Status")};
                    dtm.addRow(data);
                    }
                }
            }
            case 3 -> {
                for (Map<String, String> list : lists) {
                    if (ReportStatus.valueOf(list.get("Status")).equals(ReportStatus.MARKED_2)) {
                        String[] data = {list.get("moduleName"), list.get("type"), list.get("dueDate"), list.get("Status")};
                        dtm.addRow(data);
                    }
                }
            }
            case 4 -> {
                for (Map<String, String> list : lists) {
                    if (ReportStatus.valueOf(list.get("Status")).equals(ReportStatus.OVERDUE)) {
                        String[] data = {list.get("moduleName"), list.get("type"), list.get("dueDate"), list.get("Status")};
                        dtm.addRow(data);
                    }
                }
            }
        }  
    }
    
    private void refreshComboBox1Details(Object value) {
        if (projectComboBox1.getSelectedItem() != null) {
            if (projectComboBox1.getSelectedItem().equals(MessageConstant.CONDITION_PROJECT_COMBOBOX)) {
                JField14.setText("Submission ID");
                JField13.setText("Lectuerer Name");
                JField15.setText("Project Due Date");
                JField30.setText("Project Type");
            } else {
                List<Map<String, String>> lists = SubmissionController.getAllSubmissionDetailsForStudent();
                for (Map<String, String> list : lists) {
                    if (value.equals(list.get("moduleName"))) {
                        JField14.setText(list.get("id"));
                        JField13.setText(list.get("lecturerName"));
                        JField15.setText(list.get("dueDate"));
                        JField30.setText(list.get("type"));
                    }
                }
            }
        }
    }
    
     private void refreshComboBox2Details(Object value) {
        if (value == null) {
            return;
        }
        if (projectComboBox2.getSelectedItem() != null) {
            if (projectComboBox2.getSelectedItem().equals(MessageConstant.CONDITION_EDIT_PROJECT_COMBOBOX)) {
                JField22.setText("Submission ID");
                JField19.setText("Lectuerer Name");
                JField18.setText("Project Due Date");
                JField30.setText("Project Type");
                JField16.setText("Submission Date");
                JField23.setText("Marking Status");
                JField17.setText("Project File Name");
                if (pdfSubmissionPreview2 != null) {
                    jPanel3.remove(pdfSubmissionPreview2); // Remove the panel 's' from jPanel2
                    jPanel3.revalidate(); // Recalculate layout
                    jPanel3.repaint(); // Repaint
                }
            } else {
                List<Map<String, String>> lists = SubmissionController.getAllSubmissionDetailsForStudent();
                for (Map<String, String> list : lists) {
                    if (value.equals(list.get("moduleName"))) {
                        JField22.setText(list.get("id"));
                        JField19.setText(list.get("lecturerName"));
                        JField18.setText(list.get("dueDate"));
                        JField31.setText(list.get("type"));
                        JField16.setText(list.get("submitAt"));
                        JField23.setText(list.get("Status"));
                        JField17.setText("File Name : " + list.get("FileName"));    
                       
                        if (pdfSubmissionPreview2 != null) {
                            jPanel3.remove(pdfSubmissionPreview2); // Remove the panel 's' from jPanel2
                            jPanel3.revalidate(); // Recalculate layout
                            jPanel3.repaint(); // Repaint
                        }
                        
                        if (pdfSubmissionPreview3 != null) {
                            jPanel1.remove(pdfSubmissionPreview3); // Remove the panel 's' from jPanel2
                            jPanel1.revalidate(); // Recalculate layout
                            jPanel1.repaint(); // Repaint
                        }
            
                        SwingController ctr1 = new SwingController();
                        SwingViewBuilder vb = new SwingViewBuilder(ctr1);
                        pdfSubmissionPreview2 = vb.buildViewerPanel();
                        ComponentKeyBinding.install(ctr1, pdfSubmissionPreview2);
                        
                        Path destinationPath = Paths.get(list.get("FilePath"));
                        
                        ctr1.openDocument(String.valueOf(destinationPath));

                        jPanel3.add(pdfSubmissionPreview2);   
                    }
                }
            }
        }
    }

     private void refreshComboBox3Details(Object value) {
        if (value == null) {
            return;
        }
        if (projectComboBox2.getSelectedItem() != null) {
            if (projectComboBox2.getSelectedItem().equals(MessageConstant.CONDITION_PROJECT_RESULT_COMBOBOX)) {
                JField25.setText("Submission ID");
                JField27.setText("Lecturer Name");
                JField28.setText("Project Due Date");
                JField32.setText("Project Type");
                JField24.setText("Submission Date");
                JField26.setText("Marking Status");
                JField29.setText("result");
                jTextArea6.setText("Lecturer Comment");
                JField20.setText("Project File Name");
            } else {
                List<Map<String, String>> lists = SubmissionController.getAllSubmissionDetailsForStudent();
                for (Map<String, String> list : lists) {
                    if (value.equals(list.get("moduleName"))) {
                        JField25.setText(list.get("id"));
                        JField27.setText(list.get("lecturerName"));
                        JField28.setText(list.get("dueDate"));
                        JField32.setText(list.get("type"));
                        JField24.setText(list.get("submitAt"));
                        JField26.setText(list.get("Status"));
                        JField29.setText(list.get("result"));
                        jTextArea6.setText(list.get("comment"));
                        JField20.setText(list.get("FileName"));
                    }
                }
            }
        }
    }
     
     private void refreshComboBox4Details(Object value) {
        if (value == null) {
            return;
        }
        if (projectComboBox5.getSelectedItem() != null) {
            if (projectComboBox5.getSelectedItem().equals(MessageConstant.CONDITION_NO_SUBMITTED_PROJECT_COMBOBOX)) {
                JField21.setText("Project File Name");
                if (pdfSubmissionPreview3 != null) {
                    jPanel1.remove(pdfSubmissionPreview3); // Remove the panel 's' from jPanel2
                    jPanel1.revalidate(); // Recalculate layout
                    jPanel1.repaint(); // Repaint
                }
            } else {
                List<Map<String, String>> lists = SubmissionController.getAllSubmissionDetailsForStudent();
                for (Map<String, String> list : lists) {
                    if (value.equals(list.get("moduleName"))) {
                        JField21.setText(list.get("FileName"));
                        
                        if (pdfSubmissionPreview3 != null) {
                            jPanel1.remove(pdfSubmissionPreview3); // Remove the panel 's' from jPanel2
                            jPanel1.revalidate(); // Recalculate layout
                            jPanel1.repaint(); // Repaint
                        }
            
                        SwingController ctr1 = new SwingController();
                        SwingViewBuilder vb = new SwingViewBuilder(ctr1);
                        pdfSubmissionPreview3 = vb.buildViewerPanel();
                        ComponentKeyBinding.install(ctr1, pdfSubmissionPreview3);
                        
                        Path destinationPath = Paths.get(list.get("FilePath"));
                        
                        ctr1.openDocument(String.valueOf(destinationPath));

                        jPanel1.add(pdfSubmissionPreview3);   
                    }
                }
            }
        }
    }
    
    private void refreshTable() {
        DefaultTableModel dtm =  (DefaultTableModel)jTable2.getModel();
        dtm.setRowCount(0);
        List<Map<String, String>> lists = SubmissionController.getAllSubmissionDetailsForStudent();
        for (Map<String,String> list : lists) {
            if (ReportStatus.valueOf(list.get("Status")).equals(ReportStatus.PENDING_SUBMIT)) {
                String[] data = {list.get("moduleName"),list.get("dueDate")};
                dtm.addRow(data);
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
        projectComboBox = new javax.swing.JComboBox<>();
        menuBtn11 = new javax.swing.JLabel();
        menuBtn15 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
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
        menuBtn27 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        JSeparator33 = new javax.swing.JSeparator();
        JSeparator34 = new javax.swing.JSeparator();
        jLabel36 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        JSeparator35 = new javax.swing.JSeparator();
        menuBtn29 = new javax.swing.JLabel();
        projectComboBox1 = new javax.swing.JComboBox<>();
        menuBtn28 = new javax.swing.JLabel();
        JField30 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        menuBtn40 = new javax.swing.JLabel();
        Panel8 = new javax.swing.JPanel();
        JField23 = new javax.swing.JTextField();
        menuBtn48 = new javax.swing.JLabel();
        JField16 = new javax.swing.JTextField();
        JField17 = new javax.swing.JTextField();
        JField18 = new javax.swing.JTextField();
        menuBtn36 = new javax.swing.JLabel();
        menuBtn30 = new javax.swing.JLabel();
        menuBtn32 = new javax.swing.JLabel();
        menuBtn37 = new javax.swing.JLabel();
        projectComboBox2 = new javax.swing.JComboBox<>();
        jLabel40 = new javax.swing.JLabel();
        JSeparator37 = new javax.swing.JSeparator();
        JField22 = new javax.swing.JTextField();
        JField19 = new javax.swing.JTextField();
        menuBtn41 = new javax.swing.JLabel();
        JField31 = new javax.swing.JTextField();
        menuBtn31 = new javax.swing.JLabel();
        menuBtn33 = new javax.swing.JLabel();
        menuBtn34 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        menuBtn35 = new javax.swing.JLabel();
        Panel6 = new javax.swing.JPanel();
        menuBtn38 = new javax.swing.JLabel();
        menuBtn44 = new javax.swing.JLabel();
        JField24 = new javax.swing.JTextField();
        projectComboBox4 = new javax.swing.JComboBox<>();
        JField25 = new javax.swing.JTextField();
        JField26 = new javax.swing.JTextField();
        menuBtn50 = new javax.swing.JLabel();
        JField27 = new javax.swing.JTextField();
        JField28 = new javax.swing.JTextField();
        menuBtn53 = new javax.swing.JLabel();
        JField29 = new javax.swing.JTextField();
        menuBtn54 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTextArea6 = new javax.swing.JTextArea();
        menuBtn55 = new javax.swing.JLabel();
        JField20 = new javax.swing.JTextField();
        JField32 = new javax.swing.JTextField();
        menuBtn39 = new javax.swing.JLabel();
        menuBtn43 = new javax.swing.JLabel();
        menuBtn49 = new javax.swing.JLabel();
        menuBtn52 = new javax.swing.JLabel();
        menuBtn56 = new javax.swing.JLabel();
        Panel7 = new javax.swing.JPanel();
        menuBtn57 = new javax.swing.JLabel();
        projectComboBox5 = new javax.swing.JComboBox<>();
        menuBtn59 = new javax.swing.JLabel();
        JField21 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();

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
        menuBtn3.setText("PENDING SUBMIT");
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
        menuBtn14.setText("PROJECT");
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

        projectComboBox.setBackground(new java.awt.Color(254, 254, 254));
        projectComboBox.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 12)); // NOI18N
        projectComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Pending Submit", "Pending Marking", "Marked", "Overdue" }));
        projectComboBox.setToolTipText("d");
        projectComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        projectComboBox.setFocusable(false);
        projectComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                projectComboBoxActionPerformed(evt);
            }
        });
        Panel1.add(projectComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 330, 35));

        menuBtn11.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/calendar-24x24.png"))); // NOI18N
        menuBtn11.setText("UPCOMING EVENTS");
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
        jLabel13.setText("SUBMIT PROJECT");
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
        jLabel15.setText("EDIT PROJECT");
        jLabel15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
        });

        jLabel37.setBackground(new java.awt.Color(105, 105, 105));
        jLabel37.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(1, 1, 1));
        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/view-24x24.png"))); // NOI18N
        jLabel37.setText("PROJECT RESULT");
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
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(125, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel37)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        Panel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 50, 500, 90));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Module", "Due Date"
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
        menuBtn16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/search-24x24.png"))); // NOI18N
        menuBtn16.setText("VIEW PROJECT DETAILS");
        menuBtn16.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn16.setOpaque(true);
        Panel1.add(menuBtn16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 670, 40));

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Module", "Type", "DueDate", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
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
        }

        Panel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 670, 360));

        MainTabbedPanel.addTab("OverView", Panel1);

        Panel2.setPreferredSize(new java.awt.Dimension(1050, 570));
        Panel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        MainTabbedPanel1.setBackground(new java.awt.Color(230, 230, 230));
        MainTabbedPanel1.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N

        Panel4.setPreferredSize(new java.awt.Dimension(1050, 570));
        Panel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menuBtn22.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 10)); // NOI18N
        menuBtn22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn22.setText("File Path : ");
        menuBtn22.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn22.setOpaque(true);
        Panel4.add(menuBtn22, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 57, 520, 30));

        menuBtn23.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/user-24x24.png"))); // NOI18N
        menuBtn23.setText("LECTURER NAME");
        menuBtn23.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn23.setOpaque(true);
        Panel4.add(menuBtn23, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 330, 40));

        menuBtn24.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn24.setText("SELECT MODULE");
        menuBtn24.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn24.setOpaque(true);
        Panel4.add(menuBtn24, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 330, 40));

        menuBtn25.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn25.setText("SUBMISSION ID");
        menuBtn25.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn25.setOpaque(true);
        Panel4.add(menuBtn25, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 330, 40));

        JField14.setEditable(false);
        JField14.setBackground(new java.awt.Color(255, 255, 255));
        JField14.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField14.setForeground(new java.awt.Color(1, 1, 1));
        JField14.setText("Submission ID");
        JField14.setBorder(null);
        JField14.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField14MouseClicked(evt);
            }
        });
        Panel4.add(JField14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 330, 35));

        JField13.setEditable(false);
        JField13.setBackground(new java.awt.Color(255, 255, 255));
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
        menuBtn26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/calendar-24x24.png"))); // NOI18N
        menuBtn26.setText("DUE DATE");
        menuBtn26.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn26.setOpaque(true);
        Panel4.add(menuBtn26, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 330, 40));

        JField15.setEditable(false);
        JField15.setBackground(new java.awt.Color(255, 255, 255));
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

        menuBtn27.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn27.setText("PROJECT FILE");
        menuBtn27.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn27.setOpaque(true);
        Panel4.add(menuBtn27, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 50, 150, 40));

        jLabel34.setBackground(new java.awt.Color(254, 254, 254));
        jLabel34.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(1, 1, 1));
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/cancel-24x24.png"))); // NOI18N
        jLabel34.setText("UPLOAD");
        jLabel34.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel34.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel34.setOpaque(true);
        jLabel34.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel34MouseClicked(evt);
            }
        });
        Panel4.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 510, 120, 35));

        JSeparator33.setForeground(new java.awt.Color(1, 1, 1));
        Panel4.add(JSeparator33, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 550, 170, 10));

        JSeparator34.setForeground(new java.awt.Color(1, 1, 1));
        Panel4.add(JSeparator34, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 550, 120, 10));

        jLabel36.setBackground(new java.awt.Color(254, 254, 254));
        jLabel36.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(1, 1, 1));
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/add-green-24x24.png"))); // NOI18N
        jLabel36.setText("SUBMIT");
        jLabel36.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel36.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel36.setOpaque(true);
        jLabel36.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel36MouseClicked(evt);
            }
        });
        Panel4.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 510, 170, 35));

        jLabel38.setBackground(new java.awt.Color(254, 254, 254));
        jLabel38.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(1, 1, 1));
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/add-green-24x24.png"))); // NOI18N
        jLabel38.setText("UPLOAD");
        jLabel38.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel38.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel38.setOpaque(true);
        jLabel38.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel38MouseClicked(evt);
            }
        });
        Panel4.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 510, 120, 35));

        JSeparator35.setForeground(new java.awt.Color(1, 1, 1));
        Panel4.add(JSeparator35, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 550, 120, 10));

        menuBtn29.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn29.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/action-24x24.png"))); // NOI18N
        menuBtn29.setText("ACTION :");
        menuBtn29.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn29.setOpaque(true);
        Panel4.add(menuBtn29, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 510, 90, 40));

        projectComboBox1.setBackground(new java.awt.Color(254, 254, 254));
        projectComboBox1.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 12)); // NOI18N
        projectComboBox1.setToolTipText("d");
        projectComboBox1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        projectComboBox1.setFocusable(false);
        projectComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                projectComboBox1ActionPerformed(evt);
            }
        });
        Panel4.add(projectComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 330, 35));

        menuBtn28.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn28.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn28.setText("PROJECT TYPE");
        menuBtn28.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn28.setOpaque(true);
        Panel4.add(menuBtn28, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 400, 330, 40));

        JField30.setEditable(false);
        JField30.setBackground(new java.awt.Color(255, 255, 255));
        JField30.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField30.setForeground(new java.awt.Color(1, 1, 1));
        JField30.setText("Project Type");
        JField30.setBorder(null);
        JField30.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField30.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField30MouseClicked(evt);
            }
        });
        Panel4.add(JField30, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 440, 330, 35));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(new java.awt.BorderLayout());
        Panel4.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 90, 660, 390));

        menuBtn40.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn40.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn40.setText("SUBMISSION");
        menuBtn40.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn40.setOpaque(true);
        Panel4.add(menuBtn40, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 500, 40));

        MainTabbedPanel1.addTab("Submit", Panel4);

        Panel8.setPreferredSize(new java.awt.Dimension(1050, 570));
        Panel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        JField23.setEditable(false);
        JField23.setBackground(new java.awt.Color(255, 255, 255));
        JField23.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField23.setForeground(new java.awt.Color(1, 1, 1));
        JField23.setText("Marking Status");
        JField23.setBorder(null);
        JField23.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField23MouseClicked(evt);
            }
        });
        Panel8.add(JField23, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 90, 300, 35));

        menuBtn48.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn48.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn48.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn48.setText("MARKING STATUS");
        menuBtn48.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn48.setOpaque(true);
        Panel8.add(menuBtn48, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 50, 300, 40));

        JField16.setEditable(false);
        JField16.setBackground(new java.awt.Color(255, 255, 255));
        JField16.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField16.setForeground(new java.awt.Color(1, 1, 1));
        JField16.setText("Submission Date");
        JField16.setBorder(null);
        JField16.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField16MouseClicked(evt);
            }
        });
        Panel8.add(JField16, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 90, 300, 35));

        JField17.setEditable(false);
        JField17.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField17.setForeground(new java.awt.Color(1, 1, 1));
        JField17.setText("Project File Name");
        JField17.setBorder(null);
        JField17.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField17MouseClicked(evt);
            }
        });
        Panel8.add(JField17, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 138, 300, 30));

        JField18.setEditable(false);
        JField18.setBackground(new java.awt.Color(255, 255, 255));
        JField18.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField18.setForeground(new java.awt.Color(1, 1, 1));
        JField18.setText("Project Due Date");
        JField18.setBorder(null);
        JField18.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField18MouseClicked(evt);
            }
        });
        Panel8.add(JField18, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 350, 300, 35));

        menuBtn36.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn36.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn36.setText("SUBMISSION DATE");
        menuBtn36.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn36.setOpaque(true);
        Panel8.add(menuBtn36, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 50, 300, 40));

        menuBtn30.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn30.setText("MODIFICATION");
        menuBtn30.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn30.setOpaque(true);
        Panel8.add(menuBtn30, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 500, 40));

        menuBtn32.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn32.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn32.setText("SELECT MODULE");
        menuBtn32.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn32.setOpaque(true);
        Panel8.add(menuBtn32, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 300, 40));

        menuBtn37.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn37.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/action-24x24.png"))); // NOI18N
        menuBtn37.setText("ACTION :");
        menuBtn37.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn37.setOpaque(true);
        Panel8.add(menuBtn37, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 510, 90, 40));

        projectComboBox2.setBackground(new java.awt.Color(254, 254, 254));
        projectComboBox2.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 12)); // NOI18N
        projectComboBox2.setToolTipText("d");
        projectComboBox2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        projectComboBox2.setFocusable(false);
        projectComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                projectComboBox2ActionPerformed(evt);
            }
        });
        Panel8.add(projectComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 300, 35));

        jLabel40.setBackground(new java.awt.Color(254, 254, 254));
        jLabel40.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(1, 1, 1));
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/delete-red-24x24.png"))); // NOI18N
        jLabel40.setText("DELETE");
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

        JField22.setEditable(false);
        JField22.setBackground(new java.awt.Color(255, 255, 255));
        JField22.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField22.setForeground(new java.awt.Color(1, 1, 1));
        JField22.setText("Submission ID");
        JField22.setBorder(null);
        JField22.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField22MouseClicked(evt);
            }
        });
        JField22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JField22ActionPerformed(evt);
            }
        });
        Panel8.add(JField22, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 300, 35));

        JField19.setEditable(false);
        JField19.setBackground(new java.awt.Color(255, 255, 255));
        JField19.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField19.setForeground(new java.awt.Color(1, 1, 1));
        JField19.setText("Lectuerer Name");
        JField19.setBorder(null);
        JField19.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField19MouseClicked(evt);
            }
        });
        Panel8.add(JField19, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 300, 35));

        menuBtn41.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn41.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn41.setText("PROJECT TYPE");
        menuBtn41.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn41.setOpaque(true);
        Panel8.add(menuBtn41, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 400, 300, 40));

        JField31.setEditable(false);
        JField31.setBackground(new java.awt.Color(255, 255, 255));
        JField31.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField31.setForeground(new java.awt.Color(1, 1, 1));
        JField31.setText("Project Type");
        JField31.setBorder(null);
        JField31.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField31.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField31MouseClicked(evt);
            }
        });
        Panel8.add(JField31, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 440, 300, 35));

        menuBtn31.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn31.setText("SUBMISSION ID");
        menuBtn31.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn31.setOpaque(true);
        Panel8.add(menuBtn31, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 300, 40));

        menuBtn33.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/user-24x24.png"))); // NOI18N
        menuBtn33.setText("LECTURER NAME");
        menuBtn33.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn33.setOpaque(true);
        Panel8.add(menuBtn33, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 300, 40));

        menuBtn34.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn34.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/calendar-24x24.png"))); // NOI18N
        menuBtn34.setText("DUE DATE");
        menuBtn34.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn34.setOpaque(true);
        Panel8.add(menuBtn34, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 300, 40));

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.setLayout(new java.awt.BorderLayout());
        Panel8.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 170, 700, 310));

        menuBtn35.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn35.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn35.setText("PROJECT FILE");
        menuBtn35.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn35.setOpaque(true);
        Panel8.add(menuBtn35, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 130, 170, 40));

        MainTabbedPanel1.addTab("Edit", Panel8);

        Panel6.setPreferredSize(new java.awt.Dimension(1050, 570));
        Panel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menuBtn38.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn38.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn38.setText("RESULT");
        menuBtn38.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn38.setOpaque(true);
        Panel6.add(menuBtn38, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 500, 40));

        menuBtn44.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn44.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn44.setText("SELECT MODULE");
        menuBtn44.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn44.setOpaque(true);
        Panel6.add(menuBtn44, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 300, 40));

        JField24.setEditable(false);
        JField24.setBackground(new java.awt.Color(255, 255, 255));
        JField24.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField24.setForeground(new java.awt.Color(1, 1, 1));
        JField24.setText("Submission Date");
        JField24.setBorder(null);
        JField24.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField24MouseClicked(evt);
            }
        });
        Panel6.add(JField24, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 90, 300, 35));

        projectComboBox4.setBackground(new java.awt.Color(254, 254, 254));
        projectComboBox4.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 12)); // NOI18N
        projectComboBox4.setToolTipText("d");
        projectComboBox4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        projectComboBox4.setFocusable(false);
        projectComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                projectComboBox4ActionPerformed(evt);
            }
        });
        Panel6.add(projectComboBox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 300, 35));

        JField25.setEditable(false);
        JField25.setBackground(new java.awt.Color(255, 255, 255));
        JField25.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField25.setForeground(new java.awt.Color(1, 1, 1));
        JField25.setText("Module Name");
        JField25.setBorder(null);
        JField25.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField25MouseClicked(evt);
            }
        });
        Panel6.add(JField25, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 300, 35));

        JField26.setEditable(false);
        JField26.setBackground(new java.awt.Color(255, 255, 255));
        JField26.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField26.setForeground(new java.awt.Color(1, 1, 1));
        JField26.setText("Marking Status");
        JField26.setBorder(null);
        JField26.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField26MouseClicked(evt);
            }
        });
        Panel6.add(JField26, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 170, 300, 35));

        menuBtn50.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn50.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/status-24x24.png"))); // NOI18N
        menuBtn50.setText("MARKING STATUS");
        menuBtn50.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn50.setOpaque(true);
        Panel6.add(menuBtn50, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 130, 300, 40));

        JField27.setEditable(false);
        JField27.setBackground(new java.awt.Color(255, 255, 255));
        JField27.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField27.setForeground(new java.awt.Color(1, 1, 1));
        JField27.setText("Lectuerer Name");
        JField27.setBorder(null);
        JField27.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField27MouseClicked(evt);
            }
        });
        Panel6.add(JField27, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 300, 35));

        JField28.setEditable(false);
        JField28.setBackground(new java.awt.Color(255, 255, 255));
        JField28.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField28.setForeground(new java.awt.Color(1, 1, 1));
        JField28.setText("Project Due Date");
        JField28.setBorder(null);
        JField28.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField28.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField28MouseClicked(evt);
            }
        });
        Panel6.add(JField28, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 350, 300, 35));

        menuBtn53.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn53.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/view-24x24.png"))); // NOI18N
        menuBtn53.setText("RESULT");
        menuBtn53.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn53.setOpaque(true);
        Panel6.add(menuBtn53, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 220, 300, 40));

        JField29.setEditable(false);
        JField29.setBackground(new java.awt.Color(255, 255, 255));
        JField29.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField29.setForeground(new java.awt.Color(1, 1, 1));
        JField29.setText("Project Result");
        JField29.setBorder(null);
        JField29.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField29.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField29MouseClicked(evt);
            }
        });
        Panel6.add(JField29, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 260, 300, 35));

        menuBtn54.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn54.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/officer-24x24.png"))); // NOI18N
        menuBtn54.setText("LECTURER COMMENT");
        menuBtn54.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn54.setOpaque(true);
        Panel6.add(menuBtn54, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 310, 300, 40));

        jTextArea6.setEditable(false);
        jTextArea6.setBackground(new java.awt.Color(255, 255, 255));
        jTextArea6.setColumns(20);
        jTextArea6.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        jTextArea6.setLineWrap(true);
        jTextArea6.setRows(5);
        jTextArea6.setText("Lecturer Comment");
        jTextArea6.setWrapStyleWord(true);
        jTextArea6.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        jScrollPane8.setViewportView(jTextArea6);

        Panel6.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 350, 300, 130));

        menuBtn55.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn55.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn55.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn55.setText("PROJECT FILE");
        menuBtn55.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn55.setOpaque(true);
        Panel6.add(menuBtn55, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 50, 150, 40));

        JField20.setEditable(false);
        JField20.setBackground(new java.awt.Color(255, 255, 255));
        JField20.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField20.setForeground(new java.awt.Color(1, 1, 1));
        JField20.setText("Project File Name");
        JField20.setBorder(null);
        JField20.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField20MouseClicked(evt);
            }
        });
        Panel6.add(JField20, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 90, 300, 35));

        JField32.setEditable(false);
        JField32.setBackground(new java.awt.Color(255, 255, 255));
        JField32.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField32.setForeground(new java.awt.Color(1, 1, 1));
        JField32.setText("Project Type");
        JField32.setBorder(null);
        JField32.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField32.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField32MouseClicked(evt);
            }
        });
        Panel6.add(JField32, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 440, 300, 35));

        menuBtn39.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn39.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn39.setText("SUBMISSION ID");
        menuBtn39.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn39.setOpaque(true);
        Panel6.add(menuBtn39, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 300, 40));

        menuBtn43.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn43.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/user-24x24.png"))); // NOI18N
        menuBtn43.setText("LECTURER NAME");
        menuBtn43.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn43.setOpaque(true);
        Panel6.add(menuBtn43, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 300, 40));

        menuBtn49.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn49.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/calendar-24x24.png"))); // NOI18N
        menuBtn49.setText("DUE DATE");
        menuBtn49.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn49.setOpaque(true);
        Panel6.add(menuBtn49, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 300, 40));

        menuBtn52.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn52.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn52.setText("PROJECT TYPE");
        menuBtn52.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn52.setOpaque(true);
        Panel6.add(menuBtn52, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 400, 300, 40));

        menuBtn56.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn56.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn56.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn56.setText("SUBMISSION DATE");
        menuBtn56.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn56.setOpaque(true);
        Panel6.add(menuBtn56, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 50, 300, 40));

        MainTabbedPanel1.addTab("Result", Panel6);

        Panel7.setPreferredSize(new java.awt.Dimension(1050, 570));
        Panel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menuBtn57.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn57.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn57.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn57.setText("FILE NAME :");
        menuBtn57.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn57.setOpaque(true);
        Panel7.add(menuBtn57, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 10, 120, 40));

        projectComboBox5.setBackground(new java.awt.Color(254, 254, 254));
        projectComboBox5.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 12)); // NOI18N
        projectComboBox5.setToolTipText("d");
        projectComboBox5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        projectComboBox5.setFocusable(false);
        projectComboBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                projectComboBox5ActionPerformed(evt);
            }
        });
        Panel7.add(projectComboBox5, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 10, 390, 40));

        menuBtn59.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn59.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn59.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn59.setText("SELECT MODULE :");
        menuBtn59.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn59.setOpaque(true);
        Panel7.add(menuBtn59, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 170, 40));

        JField21.setEditable(false);
        JField21.setBackground(new java.awt.Color(255, 255, 255));
        JField21.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField21.setForeground(new java.awt.Color(1, 1, 1));
        JField21.setText("Project File Name");
        JField21.setBorder(null);
        JField21.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField21MouseClicked(evt);
            }
        });
        Panel7.add(JField21, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 10, 360, 40));

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new java.awt.BorderLayout());
        Panel7.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 1040, 500));

        MainTabbedPanel1.addTab("PDF Viwer", Panel7);

        Panel2.add(MainTabbedPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 630));

        MainTabbedPanel.addTab("Project", Panel2);

        getContentPane().add(MainTabbedPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 660));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel37MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel37MouseClicked
        MainTabbedPanel.setSelectedIndex(1);
        MainTabbedPanel1.setSelectedIndex(2);
    }//GEN-LAST:event_jLabel37MouseClicked

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
        MainTabbedPanel.setSelectedIndex(1);
        MainTabbedPanel1.setSelectedIndex(1);
    }//GEN-LAST:event_jLabel15MouseClicked

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        MainTabbedPanel.setSelectedIndex(1);
    }//GEN-LAST:event_jLabel13MouseClicked

    private void projectComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_projectComboBoxActionPerformed
        refreshjTable3(projectComboBox.getSelectedIndex());
    }//GEN-LAST:event_projectComboBoxActionPerformed

    private void JField12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField12MouseClicked
        if (JField12.getText().equals("Enter Keywords To Search")) {
            JField12.setText("");
        }    
    }//GEN-LAST:event_JField12MouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        DefaultTableModel dtm = (DefaultTableModel)jTable3.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dtm);
        jTable3.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(JField12.getText().trim()));
    }//GEN-LAST:event_jLabel11MouseClicked

    private void jLabel40MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel40MouseClicked
        if (projectComboBox2.getSelectedItem().equals(MessageConstant.CONDITION_EDIT_PROJECT_COMBOBOX)) {
            Dialog.ErrorDialog(MessageConstant.CONDITION_EDIT_PROJECT_COMBOBOX);
            return;
        }

        jPanel3.remove(pdfSubmissionPreview2); // Remove the panel 's' from jPanel2
        jPanel3.revalidate(); // Recalculate layout
        jPanel3.repaint(); // Repaint
        
        /*jPanel1.remove(pdfSubmissionPreview3); // Remove the panel 's' from jPanel2
        jPanel1.revalidate(); // Recalculate layout
        jPanel1.repaint(); // Repaint*/
        
        if (SubmissionController.removeSubmissionById(Integer.parseInt(JField22.getText()))) {
            refresh();
        }
    }//GEN-LAST:event_jLabel40MouseClicked

    private void projectComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_projectComboBox2ActionPerformed
        refreshComboBox2Details(projectComboBox2.getSelectedItem());  
    }//GEN-LAST:event_projectComboBox2ActionPerformed

    private void projectComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_projectComboBox1ActionPerformed
        refreshComboBox1Details(projectComboBox1.getSelectedItem());
    }//GEN-LAST:event_projectComboBox1ActionPerformed

    private void jLabel38MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel38MouseClicked
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        fileChooser.setFileFilter(new FileNameExtensionFilter("PDF files", "pdf")); // Set file filter for PDF files

        int result = fileChooser.showOpenDialog(null); // Pass null to open dialog in the center of the screen

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String uploadedFilePath = selectedFile.getAbsolutePath();
            uploadedfilePath = uploadedFilePath;

            if (uploadedFilePath.toLowerCase().endsWith(".pdf")) {
                // Your existing code to handle PDF file selection
                menuBtn22.setVisible(true);
                menuBtn22.setText("File Path : " + uploadedFilePath);
                try {
                    SwingController ctr1 = new SwingController();
                    SwingViewBuilder vb = new SwingViewBuilder(ctr1);
                    pdfSubmissionPreview = vb.buildViewerPanel();
                    ComponentKeyBinding.install(ctr1, pdfSubmissionPreview);
                    ctr1.openDocument(uploadedFilePath);

                    jPanel2.add(pdfSubmissionPreview);
                } catch (Exception e) {
                    // Handle exceptions
                }
            } else {
                Dialog.ErrorDialog(MessageConstant.ERROR_ONLY_PDF_SUPPORTED);
            }
        } else {
            jPanel2.removeAll();
            uploadedfilePath = null;
            menuBtn22.setVisible(false);
        }
    }//GEN-LAST:event_jLabel38MouseClicked

    private void jLabel36MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel36MouseClicked
        if (projectComboBox1.getSelectedItem().equals(MessageConstant.CONDITION_PROJECT_COMBOBOX)) {
            Dialog.ErrorDialog(MessageConstant.CONDITION_PROJECT_COMBOBOX);
            return;
        }

        if (uploadedfilePath == null) {
            Dialog.ErrorDialog(MessageConstant.ERROR_EMPTY_FILE);
            return;
        }
        
        if (SubmissionController.submitReport(Integer.parseInt(JField14.getText()), uploadedfilePath)) {
            refresh();
            jPanel2.removeAll();
            uploadedfilePath = null;
            menuBtn22.setVisible(false);
        }
        
    }//GEN-LAST:event_jLabel36MouseClicked

    private void jLabel34MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel34MouseClicked
        jPanel2.remove(pdfSubmissionPreview); // Remove the panel 's' from jPanel2
        jPanel2.revalidate(); // Recalculate layout
        jPanel2.repaint(); // Repaint
        uploadedfilePath = null;
        menuBtn22.setVisible(false);
    }//GEN-LAST:event_jLabel34MouseClicked

    private void JField15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField15MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JField15MouseClicked

    private void JField13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField13MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JField13MouseClicked

    private void JField14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField14MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JField14MouseClicked

    private void JField23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField23MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JField23MouseClicked

    private void JField16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField16MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JField16MouseClicked

    private void JField17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField17MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JField17MouseClicked

    private void JField18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField18MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JField18MouseClicked

    private void JField22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField22MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JField22MouseClicked

    private void JField24MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField24MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JField24MouseClicked

    private void projectComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_projectComboBox4ActionPerformed
        refreshComboBox3Details(projectComboBox4.getSelectedItem());
    }//GEN-LAST:event_projectComboBox4ActionPerformed

    private void JField25MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField25MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JField25MouseClicked

    private void JField26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField26MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JField26MouseClicked

    private void JField27MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField27MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JField27MouseClicked

    private void JField28MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField28MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JField28MouseClicked

    private void JField29MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField29MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JField29MouseClicked

    private void JField19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField19MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JField19MouseClicked

    private void JField20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField20MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JField20MouseClicked

    private void JField21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField21MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JField21MouseClicked

    private void projectComboBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_projectComboBox5ActionPerformed
        refreshComboBox4Details(projectComboBox5.getSelectedItem());
    }//GEN-LAST:event_projectComboBox5ActionPerformed

    private void menuBtn12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuBtn12MouseClicked
       projectComboBox.setSelectedIndex(1);
    }//GEN-LAST:event_menuBtn12MouseClicked

    private void menuBtn13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuBtn13MouseClicked
        projectComboBox.setSelectedIndex(4);
    }//GEN-LAST:event_menuBtn13MouseClicked

    private void JField30MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField30MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JField30MouseClicked

    private void JField31MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField31MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JField31MouseClicked

    private void JField32MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField32MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JField32MouseClicked

    private void JField22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JField22ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JField22ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField JField12;
    private javax.swing.JTextField JField13;
    private javax.swing.JTextField JField14;
    private javax.swing.JTextField JField15;
    private javax.swing.JTextField JField16;
    private javax.swing.JTextField JField17;
    private javax.swing.JTextField JField18;
    private javax.swing.JTextField JField19;
    private javax.swing.JTextField JField20;
    private javax.swing.JTextField JField21;
    private javax.swing.JTextField JField22;
    private javax.swing.JTextField JField23;
    private javax.swing.JTextField JField24;
    private javax.swing.JTextField JField25;
    private javax.swing.JTextField JField26;
    private javax.swing.JTextField JField27;
    private javax.swing.JTextField JField28;
    private javax.swing.JTextField JField29;
    private javax.swing.JTextField JField30;
    private javax.swing.JTextField JField31;
    private javax.swing.JTextField JField32;
    private javax.swing.JSeparator JSeparator33;
    private javax.swing.JSeparator JSeparator34;
    private javax.swing.JSeparator JSeparator35;
    private javax.swing.JSeparator JSeparator37;
    private javax.swing.JTabbedPane MainTabbedPanel;
    private javax.swing.JTabbedPane MainTabbedPanel1;
    private javax.swing.JPanel Panel1;
    private javax.swing.JPanel Panel2;
    private javax.swing.JPanel Panel4;
    private javax.swing.JPanel Panel6;
    private javax.swing.JPanel Panel7;
    private javax.swing.JPanel Panel8;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextArea jTextArea6;
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
    private javax.swing.JLabel menuBtn34;
    private javax.swing.JLabel menuBtn35;
    private javax.swing.JLabel menuBtn36;
    private javax.swing.JLabel menuBtn37;
    private javax.swing.JLabel menuBtn38;
    private javax.swing.JLabel menuBtn39;
    private javax.swing.JLabel menuBtn4;
    private javax.swing.JLabel menuBtn40;
    private javax.swing.JLabel menuBtn41;
    private javax.swing.JLabel menuBtn43;
    private javax.swing.JLabel menuBtn44;
    private javax.swing.JLabel menuBtn48;
    private javax.swing.JLabel menuBtn49;
    private javax.swing.JLabel menuBtn50;
    private javax.swing.JLabel menuBtn52;
    private javax.swing.JLabel menuBtn53;
    private javax.swing.JLabel menuBtn54;
    private javax.swing.JLabel menuBtn55;
    private javax.swing.JLabel menuBtn56;
    private javax.swing.JLabel menuBtn57;
    private javax.swing.JLabel menuBtn59;
    private static javax.swing.JComboBox<String> projectComboBox;
    private static javax.swing.JComboBox<String> projectComboBox1;
    private static javax.swing.JComboBox<String> projectComboBox2;
    private static javax.swing.JComboBox<String> projectComboBox4;
    private static javax.swing.JComboBox<String> projectComboBox5;
    // End of variables declaration//GEN-END:variables
}
