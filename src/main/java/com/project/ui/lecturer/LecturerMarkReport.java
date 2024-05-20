package com.project.ui.lecturer;

import com.project.common.constants.MessageConstant;
import com.project.common.utils.DateTimeUtils;
import com.project.common.utils.Dialog;
import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

import com.project.controller.ConsultationController;
import com.project.controller.ProjectModuleController;
import com.project.controller.SubmissionController;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import javax.swing.table.TableRowSorter;
import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;

/**
 *
 * @author Sin Lian Feng
 */
public class LecturerMarkReport extends javax.swing.JInternalFrame {
    
    private String uploadedfilePath = null; 
    private JPanel pdfSubmissionPreview = null;
    /**
     * Creates new form LecturerConsultation
     */
    public LecturerMarkReport() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI)this.getUI();
        ui.setNorthPane(null);

        refresh();
    }

    private void refresh() {
        refreshDashboard();
        refreshSubmissionOverviewTable(0);
        refreshSelectModuleInMarkReport1(selectModuleComboBoxInMarkReport1.getSelectedItem());
    }
    
    private void refreshDashboard() {
        Map<String, Integer> map = SubmissionController.getPendingMarkingSubmissionForLecturer();
        firstMarkerPendingMarking.setText(map.get("firstMarkerPendingMarking").toString());
        secondMarkerPendingMarking.setText(map.get("secondMarkerPendingMarking").toString());

        selectModuleComboBoxInMarkReport1();
        refreshUpcomingSubmissionDueDateTable();   
    }

    private void refreshSelectModuleInMarkReport1(Object value) {
        selectSubmissionComboBoxInMarkReport1.removeAllItems();
        if(pdfSubmissionPreview != null)
        {
            projectFileNameField.setText("Project File Name");
            filePanel.remove(pdfSubmissionPreview); // Remove the panel 's' from jPanel2
            filePanel.revalidate(); // Recalculate layout
            filePanel.repaint(); // Repaint 
        }
        if (selectModuleComboBoxInMarkReport1.getSelectedItem() != null) {
            List<Map<String, String>> lists = ProjectModuleController.getAllModuleDetailsByFirstMarkerId();
            for (Map<String, String> list : lists) {
                if (value.equals(list.get("id"))) {
                    //Need to set the below fields back to default after update
                    JField27.setText("Student Name");
                    JField25.setText(list.get("moduleCode"));
                    JField31.setText("Report Type");
                    JField28.setText("Marking Status");
                    reportResultField.setText("Report Result");
                    lecturerCommentField.setText("Lecturer Comment");
                }
            }
        }
    }

    private void selectModuleComboBoxInMarkReport1() {
        selectModuleComboBoxInMarkReport1.removeAllItems();
        List<Map<String, String>> list1 = ProjectModuleController.getAllModuleDetailsByFirstMarkerId();
        if (list1.isEmpty()) {
            selectModuleComboBoxInMarkReport1.addItem(MessageConstant.CONDITION_NO_MODULES_UNDER_LECTURER);
            //Need to set the below fields back to default after update
            JField27.setText("Student Name");
            JField25.setText("Module Name");
            JField31.setText("Report Type");
            JField28.setText("Marking Status");
            reportResultField.setText("Report Result");
            lecturerCommentField.setText("Lecturer Comment");
        } 
        else
        {
            for (Map<String, String> list : list1)
            {
               selectModuleComboBoxInMarkReport1.addItem(list.get("id"));
               JField25.setText(list.get("moduleCode"));
            }
        }
        selectModuleComboBoxInMarkReport1.setSelectedIndex(0);
    }
    
    private void refreshSubmissionDetailsInMarkReport1(Object value) {
        if (selectSubmissionComboBoxInMarkReport1.getSelectedItem() != null && selectSubmissionComboBoxInMarkReport1.getSelectedItem() != MessageConstant.CONDITION_NO_SUBMISSIONS_UNDER_MODULES) {
            List<Map<String, String>> lists = SubmissionController.getSubmissionDetailsById(Integer.valueOf((String)value));
            for (Map<String, String> list : lists) {
                if (value.equals(list.get("id"))) {
                    JField27.setText(list.get("studentName"));
                    JField31.setText(list.get("reportType"));
                    JField28.setText(list.get("markingStatus"));
                    reportResultField.setText(list.get("reportMarks"));
                    lecturerCommentField.setText(list.get("lecturerComment"));
                    projectFileNameField.setText(list.get("fileName"));
                    if(pdfSubmissionPreview != null)
                    {
                        filePanel.remove(pdfSubmissionPreview); // Remove the panel 's' from jPanel
                        filePanel.revalidate(); // Recalculate layout
                        filePanel.repaint(); // Repaint 
                    }
                    SwingController ctr1 = new SwingController();
                    SwingViewBuilder vb = new SwingViewBuilder(ctr1);
                    pdfSubmissionPreview = vb.buildViewerPanel();
                    ComponentKeyBinding.install(ctr1, pdfSubmissionPreview);
                    Path destinationPath = Paths.get(list.get("filePath")); 
                    ctr1.openDocument(String.valueOf(destinationPath));
                    filePanel.add(pdfSubmissionPreview);   
                }
            }
        }
    }
    
    private void selectSubmissionComboBoxInMarkReport1(Object value) {
        selectSubmissionComboBoxInMarkReport1.removeAllItems();
        List<Map<String, String>> lists2 = SubmissionController.getAllSubmissionByModuleId(Integer.valueOf((String)value));
        if (lists2.isEmpty()) {
            selectSubmissionComboBoxInMarkReport1.addItem(MessageConstant.CONDITION_NO_SUBMISSIONS_UNDER_MODULES);
            //Need to set the below fields back to default after update
            JField27.setText("Student Name");
            JField31.setText("Report Type");
            JField28.setText("Marking Status");
            reportResultField.setText("Report Result");
            lecturerCommentField.setText("Lecturer Comment");
        } 
        else
        {
            for (Map<String, String> list : lists2)
            {
                selectSubmissionComboBoxInMarkReport1.addItem(list.get("id"));
            }
        }
        selectSubmissionComboBoxInMarkReport1.setSelectedIndex(0);
    }

    private void refreshSubmissionOverviewTable(Integer value) {
        DefaultTableModel dtm = (DefaultTableModel)submissionOverviewTbl.getModel();
        dtm.setRowCount(0);
        
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dtm);
        submissionOverviewTbl.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter("".trim()));
        
        List<Map<String, String>> lists = SubmissionController.getAllSubmissionDetailsByLecId();

        switch(value) {
            case 0 -> {
                for (Map<String, String> list : lists) {
                    if (list.get("submissionStatus").equals("PENDING_SUBMIT"))
                    {
                        String[] data = {list.get("studentId"), list.get("studentName"), list.get("moduleName"), list.get("reportType"), list.get("submissionDueDate"), list.get("submissionStatus"), list.get("reportResult")};
                        dtm.addRow(data);
                    }
                }
            }
            case 1 -> {
                for (Map<String, String> list : lists) {
                    if (list.get("submissionStatus").equals("PENDING_MARKING")) {
                        String[] data = {list.get("studentId"), list.get("studentName"), list.get("moduleName"), list.get("reportType"), list.get("submissionDueDate"), list.get("submissionStatus"), list.get("reportResult")};
                        dtm.addRow(data);
                    }
                }
            }
            case 2 -> {
                for (Map<String, String> list : lists) {
                    if (list.get("submissionStatus").equals("MARKED_1")) {
                        String[] data = {list.get("studentId"), list.get("studentName"), list.get("moduleName"), list.get("reportType"), list.get("submissionDueDate"), list.get("submissionStatus"), list.get("reportResult")};
                        dtm.addRow(data);
                    }
                }
            }
            case 3 -> {
                for (Map<String, String> list : lists) {
                    if (list.get("submissionStatus").equals("MARKED_2")) {
                        String[] data = {list.get("studentId"), list.get("studentName"), list.get("moduleName"), list.get("reportType"), list.get("submissionDueDate"), list.get("submissionStatus"), list.get("reportResult")};
                        dtm.addRow(data);
                    }
                }
            }
            case 4 -> {
                for (Map<String, String> list : lists) {
                    if (list.get("submissionStatus").equals("OVERDUE")) {
                        String[] data = {list.get("studentId"), list.get("studentName"), list.get("moduleName"), list.get("reportType"), list.get("submissionDueDate"), list.get("submissionStatus"), list.get("reportResult")};
                        dtm.addRow(data);
                    }
                }
            }
        }
    }
    
    private void refreshUpcomingSubmissionDueDateTable() {
        DefaultTableModel dtm =  (DefaultTableModel)upcomingSubmissionDueDateTbl.getModel();
        dtm.setRowCount(0);
        List<Map<String, String>> lists = SubmissionController.getAllUpcomingSubmissionDueDateByLecId();
        if (!lists.isEmpty()) 
        {
            for (Map<String,String> list : lists) 
            {
            String[] data = {list.get("submissionDueDate"), list.get("moduleName")};
            dtm.addRow(data);
            }
        }
    }

    //Check whether the input of report marks from lecturer is Double
    private static boolean checkIsDouble(String input)
    {
        try{
            Double.parseDouble(input);
            return true;
        }catch (NumberFormatException e){
            return false;
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
        secondMarkerPendingMarking = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        menuBtn3 = new javax.swing.JLabel();
        firstMarkerPendingMarking = new javax.swing.JLabel();
        menuBtn14 = new javax.swing.JLabel();
        searchBtn = new javax.swing.JLabel();
        searchFieldInOverview = new javax.swing.JTextField();
        submissionComboBox = new javax.swing.JComboBox<>();
        menuBtn11 = new javax.swing.JLabel();
        menuBtn15 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        createConsultationBtn = new javax.swing.JLabel();
        editConsultationBtn = new javax.swing.JLabel();
        deleteConsultationBtn = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        upcomingSubmissionDueDateTbl = new javax.swing.JTable();
        menuBtn16 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        submissionOverviewTbl = new javax.swing.JTable();
        Panel2 = new javax.swing.JPanel();
        MainTabbedPanel1 = new javax.swing.JTabbedPane();
        Panel10 = new javax.swing.JPanel();
        projectFileNameField = new javax.swing.JTextField();
        menuBtn44 = new javax.swing.JLabel();
        menuBtn45 = new javax.swing.JLabel();
        selectModuleComboBoxInMarkReport1 = new javax.swing.JComboBox<>();
        updateReportBtn = new javax.swing.JLabel();
        JSeparator39 = new javax.swing.JSeparator();
        JField27 = new javax.swing.JTextField();
        menuBtn46 = new javax.swing.JLabel();
        JField31 = new javax.swing.JTextField();
        menuBtn47 = new javax.swing.JLabel();
        menuBtn49 = new javax.swing.JLabel();
        filePanel = new javax.swing.JPanel();
        menuBtn51 = new javax.swing.JLabel();
        selectSubmissionComboBoxInMarkReport1 = new javax.swing.JComboBox<>();
        reportResultField = new javax.swing.JTextField();
        menuBtn43 = new javax.swing.JLabel();
        JField28 = new javax.swing.JTextField();
        menuBtn52 = new javax.swing.JLabel();
        menuBtn54 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        lecturerCommentField = new javax.swing.JTextArea();
        fetchSubmissionBtn = new javax.swing.JLabel();
        JSeparator40 = new javax.swing.JSeparator();
        menuBtn50 = new javax.swing.JLabel();
        JField25 = new javax.swing.JTextField();
        Panel4 = new javax.swing.JPanel();
        menuBtn22 = new javax.swing.JLabel();
        JSeparator33 = new javax.swing.JSeparator();
        createNewConsutationBtn = new javax.swing.JLabel();
        menuBtn29 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        createConsultationTbl = new javax.swing.JTable();
        searchFieldInCreateConsultation = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        consultationDateTimePicker = new com.github.lgooddatepicker.components.DateTimePicker();
        menuBtn23 = new javax.swing.JLabel();
        Panel8 = new javax.swing.JPanel();
        menuBtn33 = new javax.swing.JLabel();
        menuBtn34 = new javax.swing.JLabel();
        JField18 = new javax.swing.JTextField();
        menuBtn30 = new javax.swing.JLabel();
        menuBtn32 = new javax.swing.JLabel();
        menuBtn37 = new javax.swing.JLabel();
        cancelConsultationBtnInEdit = new javax.swing.JLabel();
        JSeparator37 = new javax.swing.JSeparator();
        JField19 = new javax.swing.JTextField();
        menuBtn36 = new javax.swing.JLabel();
        consultationIDComboBox = new javax.swing.JComboBox<>();
        JField20 = new javax.swing.JTextField();
        completedConsultationBtnInEdit = new javax.swing.JLabel();
        JSeparator38 = new javax.swing.JSeparator();
        Panel9 = new javax.swing.JPanel();
        menuBtn35 = new javax.swing.JLabel();
        menuBtn38 = new javax.swing.JLabel();
        JField21 = new javax.swing.JTextField();
        menuBtn31 = new javax.swing.JLabel();
        menuBtn39 = new javax.swing.JLabel();
        menuBtn40 = new javax.swing.JLabel();
        JField22 = new javax.swing.JTextField();
        menuBtn41 = new javax.swing.JLabel();
        consultationIDComboBoxInDelete = new javax.swing.JComboBox<>();
        JField23 = new javax.swing.JTextField();
        deleteConsultationBtnInDelete = new javax.swing.JLabel();
        JSeparator42 = new javax.swing.JSeparator();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        MainTabbedPanel.setBackground(new java.awt.Color(230, 230, 230));
        MainTabbedPanel.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N

        Panel1.setPreferredSize(new java.awt.Dimension(1050, 570));
        Panel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel10.setBackground(new java.awt.Color(254, 254, 254));

        menuBtn4.setBackground(new java.awt.Color(250, 250, 250));
        menuBtn4.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/status-24x24.png"))); // NOI18N
        menuBtn4.setText("PENDING MARKING (2ND)");
        menuBtn4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn4.setOpaque(true);

        secondMarkerPendingMarking.setBackground(new java.awt.Color(254, 254, 254));
        secondMarkerPendingMarking.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 18)); // NOI18N
        secondMarkerPendingMarking.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        secondMarkerPendingMarking.setText("0");
        secondMarkerPendingMarking.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        secondMarkerPendingMarking.setOpaque(true);
        secondMarkerPendingMarking.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                secondMarkerPendingMarkingMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menuBtn4, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
            .addComponent(secondMarkerPendingMarking, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(menuBtn4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(secondMarkerPendingMarking, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE))
        );

        Panel1.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 50, 240, 90));

        jPanel6.setBackground(new java.awt.Color(254, 254, 254));

        menuBtn3.setBackground(new java.awt.Color(250, 250, 250));
        menuBtn3.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/status-24x24.png"))); // NOI18N
        menuBtn3.setText("PENDING MARKING (1ST)");
        menuBtn3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn3.setOpaque(true);

        firstMarkerPendingMarking.setBackground(new java.awt.Color(254, 254, 254));
        firstMarkerPendingMarking.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 18)); // NOI18N
        firstMarkerPendingMarking.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        firstMarkerPendingMarking.setText("0");
        firstMarkerPendingMarking.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        firstMarkerPendingMarking.setOpaque(true);
        firstMarkerPendingMarking.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                firstMarkerPendingMarkingMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(firstMarkerPendingMarking, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(menuBtn3, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(menuBtn3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(firstMarkerPendingMarking, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        Panel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 240, 90));

        menuBtn14.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn14.setText("SUBMISSION");
        menuBtn14.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn14.setOpaque(true);
        Panel1.add(menuBtn14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 500, 40));

        searchBtn.setBackground(new java.awt.Color(254, 254, 254));
        searchBtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        searchBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/search-24x24.png"))); // NOI18N
        searchBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        searchBtn.setOpaque(true);
        searchBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchBtnMouseClicked(evt);
            }
        });
        Panel1.add(searchBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 180, 40, 35));

        searchFieldInOverview.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        searchFieldInOverview.setText("Enter Keywords To Search");
        searchFieldInOverview.setBorder(null);
        searchFieldInOverview.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        searchFieldInOverview.setForeground(new java.awt.Color(1, 1, 1));
        searchFieldInOverview.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchFieldInOverviewMouseClicked(evt);
            }
        });
        Panel1.add(searchFieldInOverview, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 180, 290, 35));

        submissionComboBox.setBackground(new java.awt.Color(254, 254, 254));
        submissionComboBox.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 12)); // NOI18N
        submissionComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pending Submit", "Pending Marking", "First Marker Marked", "Second Marker Marked", "Overdue" }));
        submissionComboBox.setToolTipText("d");
        submissionComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        submissionComboBox.setFocusable(false);
        submissionComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submissionComboBoxActionPerformed(evt);
            }
        });
        Panel1.add(submissionComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 330, 35));

        menuBtn11.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn11.setText("UPCOMING SUBMISSION DUE DATE");
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

        createConsultationBtn.setBackground(new java.awt.Color(105, 105, 105));
        createConsultationBtn.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        createConsultationBtn.setForeground(new java.awt.Color(1, 1, 1));
        createConsultationBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/edit-orange-24x24.png"))); // NOI18N
        createConsultationBtn.setText("MARK REPORT (FIRST MARKER)");
        createConsultationBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        createConsultationBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                createConsultationBtnMouseClicked(evt);
            }
        });

        editConsultationBtn.setBackground(new java.awt.Color(105, 105, 105));
        editConsultationBtn.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        editConsultationBtn.setForeground(new java.awt.Color(1, 1, 1));
        editConsultationBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/action-24x24.png"))); // NOI18N
        editConsultationBtn.setText("MODIFY MARKS");
        editConsultationBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        editConsultationBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editConsultationBtnMouseClicked(evt);
            }
        });

        deleteConsultationBtn.setBackground(new java.awt.Color(105, 105, 105));
        deleteConsultationBtn.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        deleteConsultationBtn.setForeground(new java.awt.Color(1, 1, 1));
        deleteConsultationBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/edit-orange-24x24.png"))); // NOI18N
        deleteConsultationBtn.setText("MARK REPORT (SECOND MARKER)");
        deleteConsultationBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deleteConsultationBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteConsultationBtnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(0, 19, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(deleteConsultationBtn)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(createConsultationBtn)
                        .addGap(18, 18, 18)
                        .addComponent(editConsultationBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editConsultationBtn)
                    .addComponent(createConsultationBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(deleteConsultationBtn)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        Panel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 50, 500, 90));

        upcomingSubmissionDueDateTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Module Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        upcomingSubmissionDueDateTbl.setFocusable(false);
        upcomingSubmissionDueDateTbl.setRequestFocusEnabled(false);
        upcomingSubmissionDueDateTbl.getTableHeader().setResizingAllowed(false);
        upcomingSubmissionDueDateTbl.getTableHeader().setReorderingAllowed(false);
        upcomingSubmissionDueDateTbl.setUpdateSelectionOnSort(false);
        upcomingSubmissionDueDateTbl.setVerifyInputWhenFocusTarget(false);
        jScrollPane2.setViewportView(upcomingSubmissionDueDateTbl);
        if (upcomingSubmissionDueDateTbl.getColumnModel().getColumnCount() > 0) {
            upcomingSubmissionDueDateTbl.getColumnModel().getColumn(0).setResizable(false);
            upcomingSubmissionDueDateTbl.getColumnModel().getColumn(1).setResizable(false);
        }

        Panel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 180, 330, 420));

        menuBtn16.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn16.setText("VIEW SUBMISSION DETAILS");
        menuBtn16.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn16.setOpaque(true);
        Panel1.add(menuBtn16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 670, 40));

        submissionOverviewTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Student ID", "Student Name", "Module Name", "Report Type", "Due Date", "Status", "Result"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        submissionOverviewTbl.setFocusable(false);
        submissionOverviewTbl.setRequestFocusEnabled(false);
        submissionOverviewTbl.getTableHeader().setResizingAllowed(false);
        submissionOverviewTbl.getTableHeader().setReorderingAllowed(false);
        submissionOverviewTbl.setUpdateSelectionOnSort(false);
        submissionOverviewTbl.setVerifyInputWhenFocusTarget(false);
        jScrollPane3.setViewportView(submissionOverviewTbl);
        if (submissionOverviewTbl.getColumnModel().getColumnCount() > 0) {
            submissionOverviewTbl.getColumnModel().getColumn(0).setResizable(false);
            submissionOverviewTbl.getColumnModel().getColumn(1).setResizable(false);
            submissionOverviewTbl.getColumnModel().getColumn(2).setResizable(false);
            submissionOverviewTbl.getColumnModel().getColumn(3).setResizable(false);
            submissionOverviewTbl.getColumnModel().getColumn(4).setResizable(false);
            submissionOverviewTbl.getColumnModel().getColumn(5).setResizable(false);
            submissionOverviewTbl.getColumnModel().getColumn(6).setResizable(false);
        }

        Panel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 670, 360));

        MainTabbedPanel.addTab("Overview", Panel1);

        Panel2.setPreferredSize(new java.awt.Dimension(1050, 570));
        Panel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        MainTabbedPanel1.setBackground(new java.awt.Color(230, 230, 230));
        MainTabbedPanel1.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N

        Panel10.setPreferredSize(new java.awt.Dimension(1050, 570));
        Panel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        projectFileNameField.setEditable(false);
        projectFileNameField.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        projectFileNameField.setForeground(new java.awt.Color(1, 1, 1));
        projectFileNameField.setText("Project File Name");
        projectFileNameField.setBorder(null);
        projectFileNameField.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        Panel10.add(projectFileNameField, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 0, 300, 40));

        menuBtn44.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn44.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn44.setText("SELECT MODULE");
        menuBtn44.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn44.setOpaque(true);
        Panel10.add(menuBtn44, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 300, 30));

        menuBtn45.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn45.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/action-24x24.png"))); // NOI18N
        menuBtn45.setText("ACTION :");
        menuBtn45.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn45.setOpaque(true);
        Panel10.add(menuBtn45, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 530, 90, 40));

        selectModuleComboBoxInMarkReport1.setBackground(new java.awt.Color(254, 254, 254));
        selectModuleComboBoxInMarkReport1.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 12)); // NOI18N
        selectModuleComboBoxInMarkReport1.setToolTipText("d");
        selectModuleComboBoxInMarkReport1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        selectModuleComboBoxInMarkReport1.setFocusable(false);
        selectModuleComboBoxInMarkReport1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectModuleComboBoxInMarkReport1ActionPerformed(evt);
            }
        });
        Panel10.add(selectModuleComboBoxInMarkReport1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 300, 35));

        updateReportBtn.setBackground(new java.awt.Color(254, 254, 254));
        updateReportBtn.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        updateReportBtn.setForeground(new java.awt.Color(1, 1, 1));
        updateReportBtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        updateReportBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/edit-orange-24x24.png"))); // NOI18N
        updateReportBtn.setText("UPDATE");
        updateReportBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        updateReportBtn.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        updateReportBtn.setOpaque(true);
        updateReportBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updateReportBtnMouseClicked(evt);
            }
        });
        Panel10.add(updateReportBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 530, 170, 35));

        JSeparator39.setForeground(new java.awt.Color(1, 1, 1));
        Panel10.add(JSeparator39, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 570, 170, 10));

        JField27.setEditable(false);
        JField27.setBackground(new java.awt.Color(255, 255, 255));
        JField27.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField27.setForeground(new java.awt.Color(1, 1, 1));
        JField27.setText("Student Name");
        JField27.setBorder(null);
        JField27.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        Panel10.add(JField27, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 300, 35));

        menuBtn46.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn46.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn46.setText("REPORT TYPE");
        menuBtn46.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn46.setOpaque(true);
        Panel10.add(menuBtn46, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 300, 40));

        JField31.setEditable(false);
        JField31.setBackground(new java.awt.Color(255, 255, 255));
        JField31.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField31.setForeground(new java.awt.Color(1, 1, 1));
        JField31.setText("Report Type");
        JField31.setBorder(null);
        JField31.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        Panel10.add(JField31, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, 300, 35));

        menuBtn47.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn47.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn47.setText("SELECT SUBMISSION");
        menuBtn47.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn47.setOpaque(true);
        Panel10.add(menuBtn47, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 300, 40));

        menuBtn49.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn49.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/user-24x24.png"))); // NOI18N
        menuBtn49.setText("STUDENT NAME");
        menuBtn49.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn49.setOpaque(true);
        Panel10.add(menuBtn49, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 300, 40));

        filePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        filePanel.setLayout(new java.awt.BorderLayout());
        Panel10.add(filePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 40, 700, 360));

        menuBtn51.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn51.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn51.setText("PROJECT FILE");
        menuBtn51.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn51.setOpaque(true);
        Panel10.add(menuBtn51, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 0, 170, 40));

        selectSubmissionComboBoxInMarkReport1.setBackground(new java.awt.Color(254, 254, 254));
        selectSubmissionComboBoxInMarkReport1.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 12)); // NOI18N
        selectSubmissionComboBoxInMarkReport1.setToolTipText("d");
        selectSubmissionComboBoxInMarkReport1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        selectSubmissionComboBoxInMarkReport1.setFocusable(false);
        selectSubmissionComboBoxInMarkReport1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectSubmissionComboBoxInMarkReport1ActionPerformed(evt);
            }
        });
        Panel10.add(selectSubmissionComboBoxInMarkReport1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 300, 35));

        reportResultField.setBackground(new java.awt.Color(255, 255, 255));
        reportResultField.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        reportResultField.setForeground(new java.awt.Color(1, 1, 1));
        reportResultField.setText("Report Result");
        reportResultField.setBorder(null);
        reportResultField.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        reportResultField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reportResultFieldMouseClicked(evt);
            }
        });
        Panel10.add(reportResultField, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 550, 300, 35));

        menuBtn43.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn43.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/action-24x24.png"))); // NOI18N
        menuBtn43.setText("REPORT RESULT");
        menuBtn43.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn43.setOpaque(true);
        Panel10.add(menuBtn43, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 510, 300, 40));

        JField28.setEditable(false);
        JField28.setBackground(new java.awt.Color(255, 255, 255));
        JField28.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField28.setForeground(new java.awt.Color(1, 1, 1));
        JField28.setText("Marking Status");
        JField28.setBorder(null);
        JField28.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        Panel10.add(JField28, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, 300, 35));

        menuBtn52.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn52.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn52.setText("MARKING STATUS");
        menuBtn52.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn52.setOpaque(true);
        Panel10.add(menuBtn52, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, 300, 40));

        menuBtn54.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn54.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/officer-24x24.png"))); // NOI18N
        menuBtn54.setText("LECTURER COMMENT");
        menuBtn54.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn54.setOpaque(true);
        Panel10.add(menuBtn54, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 400, 300, 40));

        lecturerCommentField.setBackground(new java.awt.Color(255, 255, 255));
        lecturerCommentField.setColumns(20);
        lecturerCommentField.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        lecturerCommentField.setForeground(new java.awt.Color(0, 0, 0));
        lecturerCommentField.setLineWrap(true);
        lecturerCommentField.setRows(5);
        lecturerCommentField.setText("Lecturer Comment");
        lecturerCommentField.setWrapStyleWord(true);
        lecturerCommentField.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        lecturerCommentField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lecturerCommentFieldMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(lecturerCommentField);

        Panel10.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 440, 460, 120));

        fetchSubmissionBtn.setBackground(new java.awt.Color(254, 254, 254));
        fetchSubmissionBtn.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        fetchSubmissionBtn.setForeground(new java.awt.Color(1, 1, 1));
        fetchSubmissionBtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fetchSubmissionBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/status-24x24.png"))); // NOI18N
        fetchSubmissionBtn.setText("FETCH SUBMISSION");
        fetchSubmissionBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        fetchSubmissionBtn.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        fetchSubmissionBtn.setOpaque(true);
        fetchSubmissionBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fetchSubmissionBtnMouseClicked(evt);
            }
        });
        Panel10.add(fetchSubmissionBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 150, 170, 30));

        JSeparator40.setForeground(new java.awt.Color(1, 1, 1));
        Panel10.add(JSeparator40, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 310, 10));

        menuBtn50.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn50.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn50.setText("MODULE NAME");
        menuBtn50.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn50.setOpaque(true);
        Panel10.add(menuBtn50, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 66, 300, 30));

        JField25.setEditable(false);
        JField25.setBackground(new java.awt.Color(255, 255, 255));
        JField25.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField25.setForeground(new java.awt.Color(1, 1, 1));
        JField25.setText("Module Name");
        JField25.setBorder(null);
        JField25.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        Panel10.add(JField25, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 300, 35));

        MainTabbedPanel1.addTab("Mark Report", Panel10);

        Panel4.setPreferredSize(new java.awt.Dimension(1050, 570));
        Panel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menuBtn22.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/calendar-24x24.png"))); // NOI18N
        menuBtn22.setText("SELECT DATE TIME:");
        menuBtn22.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn22.setOpaque(true);
        Panel4.add(menuBtn22, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 170, 40));

        JSeparator33.setForeground(new java.awt.Color(1, 1, 1));
        Panel4.add(JSeparator33, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 550, 170, 10));

        createNewConsutationBtn.setBackground(new java.awt.Color(254, 254, 254));
        createNewConsutationBtn.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        createNewConsutationBtn.setForeground(new java.awt.Color(1, 1, 1));
        createNewConsutationBtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        createNewConsutationBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/add-green-24x24.png"))); // NOI18N
        createNewConsutationBtn.setText("CREATE");
        createNewConsutationBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        createNewConsutationBtn.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        createNewConsutationBtn.setOpaque(true);
        createNewConsutationBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                createNewConsutationBtnMouseClicked(evt);
            }
        });
        Panel4.add(createNewConsutationBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 510, 170, 35));

        menuBtn29.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn29.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/action-24x24.png"))); // NOI18N
        menuBtn29.setText("ACTION :");
        menuBtn29.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn29.setOpaque(true);
        Panel4.add(menuBtn29, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 510, 90, 40));

        createConsultationTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Consultation ID", "Student ID", "Student Name", "Date", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        createConsultationTbl.setFocusable(false);
        createConsultationTbl.setRequestFocusEnabled(false);
        createConsultationTbl.getTableHeader().setResizingAllowed(false);
        createConsultationTbl.getTableHeader().setReorderingAllowed(false);
        createConsultationTbl.setUpdateSelectionOnSort(false);
        createConsultationTbl.setVerifyInputWhenFocusTarget(false);
        jScrollPane4.setViewportView(createConsultationTbl);
        if (createConsultationTbl.getColumnModel().getColumnCount() > 0) {
            createConsultationTbl.getColumnModel().getColumn(0).setResizable(false);
            createConsultationTbl.getColumnModel().getColumn(1).setResizable(false);
            createConsultationTbl.getColumnModel().getColumn(2).setResizable(false);
            createConsultationTbl.getColumnModel().getColumn(3).setResizable(false);
            createConsultationTbl.getColumnModel().getColumn(4).setResizable(false);
        }

        Panel4.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 1020, 390));

        searchFieldInCreateConsultation.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        searchFieldInCreateConsultation.setText("Enter Keywords To Search");
        searchFieldInCreateConsultation.setBorder(null);
        searchFieldInCreateConsultation.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        searchFieldInCreateConsultation.setForeground(new java.awt.Color(1, 1, 1));
        searchFieldInCreateConsultation.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchFieldInCreateConsultationMouseClicked(evt);
            }
        });
        Panel4.add(searchFieldInCreateConsultation, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 50, 370, 35));

        jLabel12.setBackground(new java.awt.Color(254, 254, 254));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/search-24x24.png"))); // NOI18N
        jLabel12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel12.setOpaque(true);
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });
        Panel4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 50, 40, 35));
        Panel4.add(consultationDateTimePicker, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 50, 360, 40));

        menuBtn23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn23.setText("CREATE CONSULTATION");
        menuBtn23.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn23.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn23.setOpaque(true);
        Panel4.add(menuBtn23, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 210, 40));

        MainTabbedPanel1.addTab("Create", Panel4);

        Panel8.setPreferredSize(new java.awt.Dimension(1050, 570));
        Panel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menuBtn33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/officer-24x24.png"))); // NOI18N
        menuBtn33.setText("STUDENT NAME");
        menuBtn33.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn33.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn33.setOpaque(true);
        Panel8.add(menuBtn33, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 300, 40));

        menuBtn34.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/calendar-24x24.png"))); // NOI18N
        menuBtn34.setText("DATE");
        menuBtn34.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn34.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn34.setOpaque(true);
        Panel8.add(menuBtn34, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 300, 40));

        JField18.setEditable(false);
        JField18.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField18.setText("Consultation Date");
        JField18.setBackground(new java.awt.Color(255, 255, 255));
        JField18.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        JField18.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField18.setForeground(new java.awt.Color(1, 1, 1));
        Panel8.add(JField18, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, 300, 35));

        menuBtn30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn30.setText("EDIT SCHEDULED CONSULTATION");
        menuBtn30.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn30.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn30.setOpaque(true);
        Panel8.add(menuBtn30, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 500, 40));

        menuBtn32.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn32.setText("CONSULTATION ID");
        menuBtn32.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn32.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn32.setOpaque(true);
        Panel8.add(menuBtn32, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 300, 40));

        menuBtn37.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/action-24x24.png"))); // NOI18N
        menuBtn37.setText("ACTION :");
        menuBtn37.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn37.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn37.setOpaque(true);
        Panel8.add(menuBtn37, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 520, 90, 40));

        cancelConsultationBtnInEdit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cancelConsultationBtnInEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/cancel-24x24_1.png"))); // NOI18N
        cancelConsultationBtnInEdit.setText("CANCEL");
        cancelConsultationBtnInEdit.setBackground(new java.awt.Color(254, 254, 254));
        cancelConsultationBtnInEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cancelConsultationBtnInEdit.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        cancelConsultationBtnInEdit.setForeground(new java.awt.Color(1, 1, 1));
        cancelConsultationBtnInEdit.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        cancelConsultationBtnInEdit.setOpaque(true);
        cancelConsultationBtnInEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelConsultationBtnInEditMouseClicked(evt);
            }
        });
        Panel8.add(cancelConsultationBtnInEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 520, 170, 35));

        JSeparator37.setForeground(new java.awt.Color(1, 1, 1));
        Panel8.add(JSeparator37, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 560, 200, 10));

        JField19.setEditable(false);
        JField19.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField19.setText("Consultation Status");
        JField19.setBackground(new java.awt.Color(255, 255, 255));
        JField19.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        JField19.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField19.setForeground(new java.awt.Color(1, 1, 1));
        Panel8.add(JField19, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 300, 35));

        menuBtn36.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/status-24x24.png"))); // NOI18N
        menuBtn36.setText("STATUS");
        menuBtn36.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn36.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn36.setOpaque(true);
        Panel8.add(menuBtn36, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, 300, 40));

        consultationIDComboBox.setBackground(new java.awt.Color(254, 254, 254));
        consultationIDComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        consultationIDComboBox.setFocusable(false);
        consultationIDComboBox.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 12)); // NOI18N
        consultationIDComboBox.setToolTipText("d");
        consultationIDComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consultationIDComboBoxActionPerformed(evt);
            }
        });
        Panel8.add(consultationIDComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 300, 35));

        JField20.setEditable(false);
        JField20.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField20.setText("Student Name");
        JField20.setBackground(new java.awt.Color(255, 255, 255));
        JField20.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        JField20.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField20.setForeground(new java.awt.Color(1, 1, 1));
        Panel8.add(JField20, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 300, 35));

        completedConsultationBtnInEdit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        completedConsultationBtnInEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/success-24x24.png"))); // NOI18N
        completedConsultationBtnInEdit.setText("MARK AS COMPLETED");
        completedConsultationBtnInEdit.setBackground(new java.awt.Color(254, 254, 254));
        completedConsultationBtnInEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        completedConsultationBtnInEdit.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        completedConsultationBtnInEdit.setForeground(new java.awt.Color(1, 1, 1));
        completedConsultationBtnInEdit.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        completedConsultationBtnInEdit.setOpaque(true);
        completedConsultationBtnInEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                completedConsultationBtnInEditMouseClicked(evt);
            }
        });
        Panel8.add(completedConsultationBtnInEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 520, 200, 35));

        JSeparator38.setForeground(new java.awt.Color(1, 1, 1));
        Panel8.add(JSeparator38, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 560, 170, 10));

        MainTabbedPanel1.addTab("Edit", Panel8);

        Panel9.setPreferredSize(new java.awt.Dimension(1050, 570));
        Panel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menuBtn35.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/officer-24x24.png"))); // NOI18N
        menuBtn35.setText("STUDENT NAME");
        menuBtn35.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn35.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn35.setOpaque(true);
        Panel9.add(menuBtn35, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 300, 40));

        menuBtn38.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/calendar-24x24.png"))); // NOI18N
        menuBtn38.setText("DATE");
        menuBtn38.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn38.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn38.setOpaque(true);
        Panel9.add(menuBtn38, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 300, 40));

        JField21.setEditable(false);
        JField21.setBackground(new java.awt.Color(255, 255, 255));
        JField21.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField21.setForeground(new java.awt.Color(1, 1, 1));
        JField21.setText("Consultation Date");
        JField21.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        JField21.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        Panel9.add(JField21, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, 300, 35));

        menuBtn31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn31.setText("DELETE CONSULTATION");
        menuBtn31.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn31.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn31.setOpaque(true);
        Panel9.add(menuBtn31, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 500, 40));

        menuBtn39.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn39.setText("CONSULTATION ID");
        menuBtn39.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn39.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn39.setOpaque(true);
        Panel9.add(menuBtn39, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 300, 40));

        menuBtn40.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/action-24x24.png"))); // NOI18N
        menuBtn40.setText("ACTION :");
        menuBtn40.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn40.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn40.setOpaque(true);
        Panel9.add(menuBtn40, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 520, 90, 40));

        JField22.setEditable(false);
        JField22.setBackground(new java.awt.Color(255, 255, 255));
        JField22.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField22.setForeground(new java.awt.Color(1, 1, 1));
        JField22.setText("Consultation Status");
        JField22.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        JField22.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        Panel9.add(JField22, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 300, 35));

        menuBtn41.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/status-24x24.png"))); // NOI18N
        menuBtn41.setText("STATUS");
        menuBtn41.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn41.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn41.setOpaque(true);
        Panel9.add(menuBtn41, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, 300, 40));

        consultationIDComboBoxInDelete.setBackground(new java.awt.Color(254, 254, 254));
        consultationIDComboBoxInDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        consultationIDComboBoxInDelete.setFocusable(false);
        consultationIDComboBoxInDelete.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 12)); // NOI18N
        consultationIDComboBoxInDelete.setToolTipText("d");
        consultationIDComboBoxInDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consultationIDComboBoxInDeleteActionPerformed(evt);
            }
        });
        Panel9.add(consultationIDComboBoxInDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 300, 35));

        JField23.setEditable(false);
        JField23.setBackground(new java.awt.Color(255, 255, 255));
        JField23.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField23.setForeground(new java.awt.Color(1, 1, 1));
        JField23.setText("Student Name");
        JField23.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        JField23.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        Panel9.add(JField23, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 300, 35));

        deleteConsultationBtnInDelete.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        deleteConsultationBtnInDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/delete-red-24x24.png"))); // NOI18N
        deleteConsultationBtnInDelete.setText("DELETE");
        deleteConsultationBtnInDelete.setBackground(new java.awt.Color(254, 254, 254));
        deleteConsultationBtnInDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deleteConsultationBtnInDelete.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        deleteConsultationBtnInDelete.setForeground(new java.awt.Color(1, 1, 1));
        deleteConsultationBtnInDelete.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        deleteConsultationBtnInDelete.setOpaque(true);
        deleteConsultationBtnInDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteConsultationBtnInDeleteMouseClicked(evt);
            }
        });
        Panel9.add(deleteConsultationBtnInDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 520, 170, 35));

        JSeparator42.setForeground(new java.awt.Color(1, 1, 1));
        Panel9.add(JSeparator42, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 560, 170, 10));

        MainTabbedPanel1.addTab("Delete", Panel9);

        Panel2.add(MainTabbedPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 630));

        MainTabbedPanel.addTab("Manage Submission (First Marker)", Panel2);

        getContentPane().add(MainTabbedPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 660));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void createConsultationBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createConsultationBtnMouseClicked
        MainTabbedPanel.setSelectedIndex(1);
        MainTabbedPanel1.setSelectedIndex(0);
    }//GEN-LAST:event_createConsultationBtnMouseClicked

    private void submissionComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submissionComboBoxActionPerformed
        refreshSubmissionOverviewTable(submissionComboBox.getSelectedIndex());
    }//GEN-LAST:event_submissionComboBoxActionPerformed

    private void searchFieldInOverviewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchFieldInOverviewMouseClicked
        if (searchFieldInOverview.getText().equals("Enter Keywords To Search")) {
            searchFieldInOverview.setText("");
        }
    }//GEN-LAST:event_searchFieldInOverviewMouseClicked

    private void searchBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchBtnMouseClicked
        DefaultTableModel dtm = (DefaultTableModel)submissionOverviewTbl.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dtm);
        submissionOverviewTbl.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(searchFieldInOverview.getText().trim()));
    }//GEN-LAST:event_searchBtnMouseClicked

    private void editConsultationBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editConsultationBtnMouseClicked
        MainTabbedPanel.setSelectedIndex(1);
        MainTabbedPanel1.setSelectedIndex(1);
    }//GEN-LAST:event_editConsultationBtnMouseClicked

    private void firstMarkerPendingMarkingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_firstMarkerPendingMarkingMouseClicked
        submissionComboBox.setSelectedIndex(1);
    }//GEN-LAST:event_firstMarkerPendingMarkingMouseClicked

    private void secondMarkerPendingMarkingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_secondMarkerPendingMarkingMouseClicked
        submissionComboBox.setSelectedIndex(2);
    }//GEN-LAST:event_secondMarkerPendingMarkingMouseClicked

    private void deleteConsultationBtnInDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteConsultationBtnInDeleteMouseClicked
        if (consultationIDComboBoxInDelete.getSelectedItem().equals("There is no consultation created.")) {
            Dialog.ErrorDialog(MessageConstant.ERROR_SELECTION_EMPTY);
            return;
        }
        
        if (ConsultationController.deleteConsultationById(Integer.parseInt((String)consultationIDComboBoxInDelete.getSelectedItem()))) {
            refresh();
        }
    }//GEN-LAST:event_deleteConsultationBtnInDeleteMouseClicked

    private void consultationIDComboBoxInDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consultationIDComboBoxInDeleteActionPerformed
//        refreshSubmissionDetailsInMarkReport1(selectSubmissionComboBoxInMarkReport1.getSelectedItem());
    }//GEN-LAST:event_consultationIDComboBoxInDeleteActionPerformed

    private void completedConsultationBtnInEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_completedConsultationBtnInEditMouseClicked
        if (consultationIDComboBox.getSelectedItem().equals("There is no scheduled consultation.")) {
            Dialog.ErrorDialog(MessageConstant.ERROR_SELECTION_EMPTY);
            return;
        }

        if (ConsultationController.completeBookedConsultationById(Integer.valueOf((String)consultationIDComboBox.getSelectedItem()))) {
            refresh();
        }
    }//GEN-LAST:event_completedConsultationBtnInEditMouseClicked

    private void consultationIDComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consultationIDComboBoxActionPerformed
        refreshSelectModuleInMarkReport1(consultationIDComboBox.getSelectedItem());
    }//GEN-LAST:event_consultationIDComboBoxActionPerformed

    private void cancelConsultationBtnInEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelConsultationBtnInEditMouseClicked
        if (consultationIDComboBox.getSelectedItem().equals("There is no scheduled consultation.")) {
            Dialog.ErrorDialog(MessageConstant.ERROR_SELECTION_EMPTY);
            return;
        }

        if (ConsultationController.cancelBookedConsultationById(Integer.valueOf((String)consultationIDComboBox.getSelectedItem()))) {
            refresh();
        }
    }//GEN-LAST:event_cancelConsultationBtnInEditMouseClicked

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        DefaultTableModel dtm = (DefaultTableModel)createConsultationTbl.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dtm);
        createConsultationTbl.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(searchFieldInCreateConsultation.getText().trim()));
    }//GEN-LAST:event_jLabel12MouseClicked

    private void searchFieldInCreateConsultationMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchFieldInCreateConsultationMouseClicked
        if (searchFieldInCreateConsultation.getText().equals("Enter Keywords To Search")) {
            searchFieldInCreateConsultation.setText("");
        }
    }//GEN-LAST:event_searchFieldInCreateConsultationMouseClicked

    private void createNewConsutationBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createNewConsutationBtnMouseClicked
        LocalDateTime selectedDateTime=consultationDateTimePicker.getDateTimePermissive();
        ConsultationController.createConsultationSlotForLecturer(selectedDateTime);
        refresh();
    }//GEN-LAST:event_createNewConsutationBtnMouseClicked

    private void deleteConsultationBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteConsultationBtnMouseClicked
        MainTabbedPanel.setSelectedIndex(1);
        MainTabbedPanel1.setSelectedIndex(2);
    }//GEN-LAST:event_deleteConsultationBtnMouseClicked

    private void selectModuleComboBoxInMarkReport1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectModuleComboBoxInMarkReport1ActionPerformed
        refreshSelectModuleInMarkReport1(selectModuleComboBoxInMarkReport1.getSelectedItem());
    }//GEN-LAST:event_selectModuleComboBoxInMarkReport1ActionPerformed

    private void updateReportBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateReportBtnMouseClicked
        if (selectModuleComboBoxInMarkReport1.getSelectedItem().equals(MessageConstant.CONDITION_NO_MODULES_UNDER_LECTURER) || selectSubmissionComboBoxInMarkReport1.getSelectedItem().equals(MessageConstant.CONDITION_NO_SUBMISSIONS_UNDER_MODULES)) {
            Dialog.ErrorDialog(MessageConstant.CONDITION_EDIT_PROJECT_COMBOBOX);
            return;
        }

//        filePanel.remove(pdfSubmissionPreview2); // Remove the panel 's' from jPanel2
//        filePanel.revalidate(); // Recalculate layout
//        filePanel.repaint(); // Repaint

        if (SubmissionController.removeSubmissionById(Integer.parseInt(JField22.getText()))) {
            refresh();
        }
    }//GEN-LAST:event_updateReportBtnMouseClicked

    private void selectSubmissionComboBoxInMarkReport1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectSubmissionComboBoxInMarkReport1ActionPerformed
        
        refreshSubmissionDetailsInMarkReport1(selectSubmissionComboBoxInMarkReport1.getSelectedItem());     
    }//GEN-LAST:event_selectSubmissionComboBoxInMarkReport1ActionPerformed

    private void reportResultFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportResultFieldMouseClicked
        if (lecturerCommentField.getText().equals("Lecturer Comment")) {
            lecturerCommentField.setText("");
        }     
    }//GEN-LAST:event_reportResultFieldMouseClicked

    private void lecturerCommentFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lecturerCommentFieldMouseClicked
        if (reportResultField.getText().equals("Report Result") || reportResultField.getText().equals("0.0")) {
            reportResultField.setText("");
        } 
    }//GEN-LAST:event_lecturerCommentFieldMouseClicked

    private void fetchSubmissionBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fetchSubmissionBtnMouseClicked
        selectSubmissionComboBoxInMarkReport1(selectModuleComboBoxInMarkReport1.getSelectedItem()); 
        refreshSubmissionDetailsInMarkReport1(selectSubmissionComboBoxInMarkReport1.getSelectedItem());
    }//GEN-LAST:event_fetchSubmissionBtnMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField JField18;
    private javax.swing.JTextField JField19;
    private javax.swing.JTextField JField20;
    private javax.swing.JTextField JField21;
    private javax.swing.JTextField JField22;
    private javax.swing.JTextField JField23;
    private javax.swing.JTextField JField25;
    private javax.swing.JTextField JField27;
    private javax.swing.JTextField JField28;
    private javax.swing.JTextField JField31;
    private javax.swing.JSeparator JSeparator33;
    private javax.swing.JSeparator JSeparator37;
    private javax.swing.JSeparator JSeparator38;
    private javax.swing.JSeparator JSeparator39;
    private javax.swing.JSeparator JSeparator40;
    private javax.swing.JSeparator JSeparator42;
    private javax.swing.JTabbedPane MainTabbedPanel;
    private javax.swing.JTabbedPane MainTabbedPanel1;
    private javax.swing.JPanel Panel1;
    private javax.swing.JPanel Panel10;
    private javax.swing.JPanel Panel2;
    private javax.swing.JPanel Panel4;
    private javax.swing.JPanel Panel8;
    private javax.swing.JPanel Panel9;
    private javax.swing.JLabel cancelConsultationBtnInEdit;
    private javax.swing.JLabel completedConsultationBtnInEdit;
    private com.github.lgooddatepicker.components.DateTimePicker consultationDateTimePicker;
    private static javax.swing.JComboBox<String> consultationIDComboBox;
    private static javax.swing.JComboBox<String> consultationIDComboBoxInDelete;
    private javax.swing.JLabel createConsultationBtn;
    private javax.swing.JTable createConsultationTbl;
    private javax.swing.JLabel createNewConsutationBtn;
    private javax.swing.JLabel deleteConsultationBtn;
    private javax.swing.JLabel deleteConsultationBtnInDelete;
    private javax.swing.JLabel editConsultationBtn;
    private javax.swing.JLabel fetchSubmissionBtn;
    private javax.swing.JPanel filePanel;
    private static javax.swing.JLabel firstMarkerPendingMarking;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTextArea lecturerCommentField;
    private javax.swing.JLabel menuBtn11;
    private javax.swing.JLabel menuBtn14;
    private javax.swing.JLabel menuBtn15;
    private javax.swing.JLabel menuBtn16;
    private javax.swing.JLabel menuBtn22;
    private javax.swing.JLabel menuBtn23;
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
    private javax.swing.JLabel menuBtn45;
    private javax.swing.JLabel menuBtn46;
    private javax.swing.JLabel menuBtn47;
    private javax.swing.JLabel menuBtn49;
    private javax.swing.JLabel menuBtn50;
    private javax.swing.JLabel menuBtn51;
    private javax.swing.JLabel menuBtn52;
    private javax.swing.JLabel menuBtn54;
    private javax.swing.JTextField projectFileNameField;
    private javax.swing.JTextField reportResultField;
    private javax.swing.JLabel searchBtn;
    private javax.swing.JTextField searchFieldInCreateConsultation;
    private javax.swing.JTextField searchFieldInOverview;
    private static javax.swing.JLabel secondMarkerPendingMarking;
    private javax.swing.JComboBox<String> selectModuleComboBoxInMarkReport1;
    private static javax.swing.JComboBox<String> selectSubmissionComboBoxInMarkReport1;
    private static javax.swing.JComboBox<String> submissionComboBox;
    private javax.swing.JTable submissionOverviewTbl;
    private javax.swing.JTable upcomingSubmissionDueDateTbl;
    private javax.swing.JLabel updateReportBtn;
    // End of variables declaration//GEN-END:variables
}
