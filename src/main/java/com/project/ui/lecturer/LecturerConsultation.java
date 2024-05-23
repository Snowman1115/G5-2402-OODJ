package com.project.ui.lecturer;

import com.github.lgooddatepicker.optionalusertools.DateTimeChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateTimeChangeEvent;
import com.project.ui.student.*;
import com.project.common.constants.MessageConstant;
import com.project.common.utils.Dialog;
import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

import com.project.controller.ConsultationController;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.bouncycastle.tsp.TSPUtil;
import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;

import java.util.List;
import java.util.Map;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Sin Lian Feng
 */
public class LecturerConsultation extends javax.swing.JInternalFrame {

    /**
     * Creates new form LecturerConsultation
     */
    public LecturerConsultation() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI)this.getUI();
        ui.setNorthPane(null);

        refresh();
        fillInDateTime();
        consultationDateTimePicker.addDateTimeChangeListener(new DateTimeChangeListener()
        {      
            @Override
            public void dateOrTimeChanged(DateTimeChangeEvent event) {
                checkIfSelectedPastDate(); 
            }
        });
    }

    private void refresh() {
        refreshDashboard();
        refreshConsultationOverviewTable(0);
        refreshConsultationTableInCreate();
        refreshConsultationDetailsInEdit(consultationIDComboBox.getSelectedItem());
        refreshConsultationDetailsInDelete(consultationIDComboBoxInDelete.getSelectedItem());
    }
    
    private void refreshDashboard() {
        Map<String, Integer> map = ConsultationController.getAvailableNScheduledConsultationForLecturer();
        availableConsultation.setText(map.get("available").toString());
        upcomingConsultation.setText(map.get("upcoming").toString());

        consultationIDComboBoxInEdit();
        refreshUpcomingConsultationTable();
        consultationIDComboBoxInDelete();
    }

    private void refreshConsultationDetailsInEdit(Object value) {
        if (consultationIDComboBox.getSelectedItem() != null) {
            List<Map<String, String>> lists = ConsultationController.getAllScheduledConsultationByLecId();
            for (Map<String, String> list : lists) {
                if (value.equals(list.get("id"))) {
                    JField20.setText(list.get("studentName"));
                    JField18.setText(list.get("consultationDateTime"));
                    JField19.setText(list.get("consultationStatus"));
                }
            }
        }
    }
    private void refreshConsultationDetailsInDelete(Object value) {
        if (consultationIDComboBoxInDelete.getSelectedItem() != null) {
            List<Map<String, String>> lists = ConsultationController.getAllConsultationExceptCompletedByLecId();
            for (Map<String, String> list : lists) {
                if (value.equals(list.get("id"))) {
                    JField23.setText(list.get("studentName"));
                    JField21.setText(list.get("consultationDateTime"));
                    JField22.setText(list.get("consultationStatus"));
                }
            }
        }
    }

    private void consultationIDComboBoxInEdit() {
        consultationIDComboBox.removeAllItems();
        List<Map<String, String>> lists2 = ConsultationController.getAllScheduledConsultationByLecId();
        if (lists2.isEmpty()) {
            consultationIDComboBox.addItem("There is no scheduled consultation.");
            //Need to set the below fields back to default after update the consultation status to completed/cancelled
            JField20.setText("Student Name");
            JField18.setText("Consultation DateTime");
            JField19.setText("Consultation Status");
        } 
        else
        {
            for (Map<String, String> list : lists2)
            {
                consultationIDComboBox.addItem(list.get("id"));
            }
        }
        consultationIDComboBox.setSelectedIndex(0);
    }
    private void consultationIDComboBoxInDelete() {
        consultationIDComboBoxInDelete.removeAllItems();
        List<Map<String, String>> lists2 = ConsultationController.getAllConsultationExceptCompletedByLecId();
        if (lists2.isEmpty()) {
            consultationIDComboBoxInDelete.addItem("There is no consultation created.");
            //Need to set the below fields back to default after update the consultation status to completed/cancelled
            JField23.setText("Student Name");
            JField21.setText("Consultation DateTime");
            JField22.setText("Consultation Status");
        } 
        else
        {
            for (Map<String, String> list : lists2)
            {
                consultationIDComboBoxInDelete.addItem(list.get("id"));
            }
        }
        consultationIDComboBoxInDelete.setSelectedIndex(0);
    }

    private void refreshConsultationOverviewTable(Integer value) {
        DefaultTableModel dtm = (DefaultTableModel)consultationOverviewTbl.getModel();
        dtm.setRowCount(0);
        
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dtm);
        consultationOverviewTbl.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter("".trim()));
        
        List<Map<String, String>> lists = ConsultationController.getAllConsultationDetailsByLecId();

        switch(value) {
            case 0 -> {
                for (Map<String, String> list : lists) {
                    if (list.get("consultationStatus").equals("AVAILABLE"))
                    {
                        String[] data = {list.get("id"), list.get("studentId"), list.get("studentName"), list.get("consultationDateTime"), list.get("consultationStatus")};
                        dtm.addRow(data);
                    }
                }
            }
            case 1 -> {
                for (Map<String, String> list : lists) {
                    if (list.get("consultationStatus").equals("SCHEDULED")) {
                        String[] data = {list.get("id"), list.get("studentId"), list.get("studentName"), list.get("consultationDateTime"), list.get("consultationStatus")};
                        dtm.addRow(data);
                    }
                }
            }
            case 2 -> {
                for (Map<String, String> list : lists) {
                    if (list.get("consultationStatus").equals("COMPLETED")) {
                        String[] data = {list.get("id"), list.get("studentId"), list.get("studentName"), list.get("consultationDateTime"), list.get("consultationStatus")};
                        dtm.addRow(data);
                    }
                }
            }
        }
    }
    
    private void refreshUpcomingConsultationTable() {
        DefaultTableModel dtm =  (DefaultTableModel)upcomingConsultationTbl.getModel();
        dtm.setRowCount(0);
        List<Map<String, String>> lists = ConsultationController.getAllScheduledConsultationByLecId();
        if (!lists.isEmpty()) 
        {
            for (Map<String,String> list : lists) 
            {
            String[] data = {list.get("consultationDateTime"), list.get("studentName")};
            dtm.addRow(data);
            }
        }
    }
    
    private void fillInDateTime() {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0));
        consultationDateTimePicker.setDateTimePermissive(localDateTime);
    }
    
    private void checkIfSelectedPastDate()
    {
        LocalDateTime selectedDateTime = consultationDateTimePicker.getDateTimePermissive();
        if(selectedDateTime != null)
        {
            if (consultationDateTimePicker.getDateTimePermissive().toLocalDate().isBefore(LocalDate.now())) 
            {
                Dialog.ErrorDialog(MessageConstant.ERROR_PAST_DATE_SELECTION);
                fillInDateTime();
            }
        }
        else
        {
            Dialog.ErrorDialog(MessageConstant.ERROR_NO_DATETIME_SELECTED);
            fillInDateTime(); 
        }
    }
    private void refreshConsultationTableInCreate() {
        DefaultTableModel dtm = (DefaultTableModel)createConsultationTbl.getModel();
        dtm.setRowCount(0);
        
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dtm);
        createConsultationTbl.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter("".trim()));
        
        List<Map<String, String>> lists = ConsultationController.getAllConsultationDetailsByLecId();
        
        if (!lists.isEmpty()) 
        {
            for (Map<String,String> list : lists) 
            {
                if(list.get("consultationStatus").equals("AVAILABLE") || list.get("consultationStatus").equals("SCHEDULED"))
                {
                    String[] data = {list.get("id"), list.get("studentId"), list.get("studentName"), list.get("consultationDateTime"), list.get("consultationStatus")};
                    dtm.addRow(data); 
                }
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
        upcomingConsultation = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        menuBtn3 = new javax.swing.JLabel();
        availableConsultation = new javax.swing.JLabel();
        menuBtn14 = new javax.swing.JLabel();
        searchBtn = new javax.swing.JLabel();
        searchFieldInOverview = new javax.swing.JTextField();
        consultationComboBox = new javax.swing.JComboBox<>();
        menuBtn11 = new javax.swing.JLabel();
        menuBtn15 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        createConsultationBtn = new javax.swing.JLabel();
        editConsultationBtn = new javax.swing.JLabel();
        deleteConsultationBtn = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        upcomingConsultationTbl = new javax.swing.JTable();
        menuBtn16 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        consultationOverviewTbl = new javax.swing.JTable();
        Panel2 = new javax.swing.JPanel();
        MainTabbedPanel1 = new javax.swing.JTabbedPane();
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
        menuBtn4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/pending-24x24.png"))); // NOI18N
        menuBtn4.setText("UPCOMING");
        menuBtn4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn4.setOpaque(true);

        upcomingConsultation.setBackground(new java.awt.Color(254, 254, 254));
        upcomingConsultation.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 18)); // NOI18N
        upcomingConsultation.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        upcomingConsultation.setText("0");
        upcomingConsultation.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        upcomingConsultation.setOpaque(true);
        upcomingConsultation.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                upcomingConsultationMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menuBtn4, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
            .addComponent(upcomingConsultation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(menuBtn4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(upcomingConsultation, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE))
        );

        Panel1.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 50, 240, 90));

        jPanel6.setBackground(new java.awt.Color(254, 254, 254));

        menuBtn3.setBackground(new java.awt.Color(250, 250, 250));
        menuBtn3.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/status-24x24.png"))); // NOI18N
        menuBtn3.setText("AVAILABLE");
        menuBtn3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn3.setOpaque(true);

        availableConsultation.setBackground(new java.awt.Color(254, 254, 254));
        availableConsultation.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 18)); // NOI18N
        availableConsultation.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        availableConsultation.setText("0");
        availableConsultation.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        availableConsultation.setOpaque(true);
        availableConsultation.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                availableConsultationMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menuBtn3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
            .addComponent(availableConsultation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(menuBtn3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(availableConsultation, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        Panel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 240, 90));

        menuBtn14.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn14.setText("CONSULTATION");
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
        searchFieldInOverview.setForeground(new java.awt.Color(1, 1, 1));
        searchFieldInOverview.setText("Enter Keywords To Search");
        searchFieldInOverview.setBorder(null);
        searchFieldInOverview.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        searchFieldInOverview.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchFieldInOverviewMouseClicked(evt);
            }
        });
        Panel1.add(searchFieldInOverview, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 180, 290, 35));

        consultationComboBox.setBackground(new java.awt.Color(254, 254, 254));
        consultationComboBox.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 12)); // NOI18N
        consultationComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Available", "Upcoming", "Completed" }));
        consultationComboBox.setToolTipText("d");
        consultationComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        consultationComboBox.setFocusable(false);
        consultationComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consultationComboBoxActionPerformed(evt);
            }
        });
        Panel1.add(consultationComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 330, 35));

        menuBtn11.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn11.setText("UPCOMING SCHEDULED CONSULTATION");
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
        createConsultationBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/add-24x24.png"))); // NOI18N
        createConsultationBtn.setText("CREATE CONSULTATION SLOT");
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
        editConsultationBtn.setText("EDIT CONSULTATION");
        editConsultationBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        editConsultationBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editConsultationBtnMouseClicked(evt);
            }
        });

        deleteConsultationBtn.setBackground(new java.awt.Color(105, 105, 105));
        deleteConsultationBtn.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        deleteConsultationBtn.setForeground(new java.awt.Color(1, 1, 1));
        deleteConsultationBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/delete-24x24.png"))); // NOI18N
        deleteConsultationBtn.setText("DELETE CONSULTATION SLOT");
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
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(deleteConsultationBtn)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(createConsultationBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
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

        upcomingConsultationTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Student Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        upcomingConsultationTbl.setFocusable(false);
        upcomingConsultationTbl.setRequestFocusEnabled(false);
        upcomingConsultationTbl.getTableHeader().setResizingAllowed(false);
        upcomingConsultationTbl.getTableHeader().setReorderingAllowed(false);
        upcomingConsultationTbl.setUpdateSelectionOnSort(false);
        upcomingConsultationTbl.setVerifyInputWhenFocusTarget(false);
        jScrollPane2.setViewportView(upcomingConsultationTbl);
        if (upcomingConsultationTbl.getColumnModel().getColumnCount() > 0) {
            upcomingConsultationTbl.getColumnModel().getColumn(0).setResizable(false);
            upcomingConsultationTbl.getColumnModel().getColumn(1).setResizable(false);
        }

        Panel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 180, 330, 420));

        menuBtn16.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn16.setText("VIEW CONSULTATION DETAILS");
        menuBtn16.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn16.setOpaque(true);
        Panel1.add(menuBtn16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 670, 40));

        consultationOverviewTbl.setModel(new javax.swing.table.DefaultTableModel(
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
        consultationOverviewTbl.setFocusable(false);
        consultationOverviewTbl.setRequestFocusEnabled(false);
        consultationOverviewTbl.getTableHeader().setResizingAllowed(false);
        consultationOverviewTbl.getTableHeader().setReorderingAllowed(false);
        consultationOverviewTbl.setUpdateSelectionOnSort(false);
        consultationOverviewTbl.setVerifyInputWhenFocusTarget(false);
        jScrollPane3.setViewportView(consultationOverviewTbl);
        if (consultationOverviewTbl.getColumnModel().getColumnCount() > 0) {
            consultationOverviewTbl.getColumnModel().getColumn(0).setResizable(false);
            consultationOverviewTbl.getColumnModel().getColumn(1).setResizable(false);
            consultationOverviewTbl.getColumnModel().getColumn(2).setResizable(false);
            consultationOverviewTbl.getColumnModel().getColumn(3).setResizable(false);
            consultationOverviewTbl.getColumnModel().getColumn(4).setResizable(false);
        }

        Panel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 670, 360));

        MainTabbedPanel.addTab("Overview", Panel1);

        Panel2.setPreferredSize(new java.awt.Dimension(1050, 570));
        Panel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        MainTabbedPanel1.setBackground(new java.awt.Color(230, 230, 230));
        MainTabbedPanel1.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N

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
        JField21.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField21.setText("Consultation Date");
        JField21.setBackground(new java.awt.Color(255, 255, 255));
        JField21.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        JField21.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField21.setForeground(new java.awt.Color(1, 1, 1));
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
        JField22.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField22.setText("Consultation Status");
        JField22.setBackground(new java.awt.Color(255, 255, 255));
        JField22.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        JField22.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField22.setForeground(new java.awt.Color(1, 1, 1));
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
        JField23.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField23.setText("Student Name");
        JField23.setBackground(new java.awt.Color(255, 255, 255));
        JField23.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        JField23.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField23.setForeground(new java.awt.Color(1, 1, 1));
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

        MainTabbedPanel.addTab("Manage Consultation", Panel2);

        getContentPane().add(MainTabbedPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 660));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void createConsultationBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createConsultationBtnMouseClicked
        MainTabbedPanel.setSelectedIndex(1);
        MainTabbedPanel1.setSelectedIndex(0);
    }//GEN-LAST:event_createConsultationBtnMouseClicked

    private void consultationComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consultationComboBoxActionPerformed
        refreshConsultationOverviewTable(consultationComboBox.getSelectedIndex());
    }//GEN-LAST:event_consultationComboBoxActionPerformed

    private void searchFieldInOverviewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchFieldInOverviewMouseClicked
        if (searchFieldInOverview.getText().equals("Enter Keywords To Search")) {
            searchFieldInOverview.setText("");
        }
    }//GEN-LAST:event_searchFieldInOverviewMouseClicked

    private void searchBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchBtnMouseClicked
        DefaultTableModel dtm = (DefaultTableModel)consultationOverviewTbl.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dtm);
        consultationOverviewTbl.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(searchFieldInOverview.getText().trim()));
    }//GEN-LAST:event_searchBtnMouseClicked

    private void editConsultationBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editConsultationBtnMouseClicked
        MainTabbedPanel.setSelectedIndex(1);
        MainTabbedPanel1.setSelectedIndex(1);
    }//GEN-LAST:event_editConsultationBtnMouseClicked

    private void availableConsultationMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_availableConsultationMouseClicked
        consultationComboBox.setSelectedIndex(0);
    }//GEN-LAST:event_availableConsultationMouseClicked

    private void upcomingConsultationMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_upcomingConsultationMouseClicked
        consultationComboBox.setSelectedIndex(1);
    }//GEN-LAST:event_upcomingConsultationMouseClicked

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
        refreshConsultationDetailsInDelete(consultationIDComboBoxInDelete.getSelectedItem());
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
        refreshConsultationDetailsInEdit(consultationIDComboBox.getSelectedItem());
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField JField18;
    private javax.swing.JTextField JField19;
    private javax.swing.JTextField JField20;
    private javax.swing.JTextField JField21;
    private javax.swing.JTextField JField22;
    private javax.swing.JTextField JField23;
    private javax.swing.JSeparator JSeparator33;
    private javax.swing.JSeparator JSeparator37;
    private javax.swing.JSeparator JSeparator38;
    private javax.swing.JSeparator JSeparator42;
    private javax.swing.JTabbedPane MainTabbedPanel;
    private javax.swing.JTabbedPane MainTabbedPanel1;
    private javax.swing.JPanel Panel1;
    private javax.swing.JPanel Panel2;
    private javax.swing.JPanel Panel4;
    private javax.swing.JPanel Panel8;
    private javax.swing.JPanel Panel9;
    private static javax.swing.JLabel availableConsultation;
    private javax.swing.JLabel cancelConsultationBtnInEdit;
    private javax.swing.JLabel completedConsultationBtnInEdit;
    private static javax.swing.JComboBox<String> consultationComboBox;
    private com.github.lgooddatepicker.components.DateTimePicker consultationDateTimePicker;
    private static javax.swing.JComboBox<String> consultationIDComboBox;
    private static javax.swing.JComboBox<String> consultationIDComboBoxInDelete;
    private javax.swing.JTable consultationOverviewTbl;
    private javax.swing.JLabel createConsultationBtn;
    private javax.swing.JTable createConsultationTbl;
    private javax.swing.JLabel createNewConsutationBtn;
    private javax.swing.JLabel deleteConsultationBtn;
    private javax.swing.JLabel deleteConsultationBtnInDelete;
    private javax.swing.JLabel editConsultationBtn;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
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
    private javax.swing.JLabel searchBtn;
    private javax.swing.JTextField searchFieldInCreateConsultation;
    private javax.swing.JTextField searchFieldInOverview;
    private static javax.swing.JLabel upcomingConsultation;
    private javax.swing.JTable upcomingConsultationTbl;
    // End of variables declaration//GEN-END:variables
}
