package com.project.ui.lecturer;

import com.project.common.constants.MessageConstant;
import com.project.common.utils.DateTimeUtils;
import com.project.common.utils.Dialog;
import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

import com.project.controller.PresentationController;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Map;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Sin Lian Feng
 */
public class LecturerPresentation extends javax.swing.JInternalFrame {

    /**
     * Creates new form LecturerPresentation
     */
    public LecturerPresentation() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI)this.getUI();
        ui.setNorthPane(null);

        refresh();
    }

    private void refresh() {
        refreshDashboard();
        refreshPresentationOverviewTable(0);
        refreshPresentationDetailsInAcceptReject(presentationIDComboBox.getSelectedItem());
        presentationIDComboBox();
        refreshPresentationDetailsInUpdate(presentationIDComboBoxInUpdate.getSelectedItem());
        presentationIDComboBoxInUpdate();

    }
    
    private void refreshDashboard() {
        Map<String, Integer> map = PresentationController.getPendingConfirmAndMarkingPresentationForLecturer();
        pendingConfirm.setText(map.get("pendingConfirm").toString());
        pendingMarking.setText(map.get("pendingMarking").toString());

        refreshUpcomingPresentationTable();
    }

    private void refreshPresentationDetailsInAcceptReject(Object value) {
        if (presentationIDComboBox.getSelectedItem() != null) {
            List<Map<String, String>> lists = PresentationController.getAllPendingConfirmPresentationByLecId();
            for (Map<String, String> list : lists) {
                if (value.equals(list.get("id"))) {
                    JField20.setText(list.get("studentName"));
                    JField18.setText(list.get("presentationDateTime"));
                    JField25.setText(list.get("presentationDueDate"));
                    JField24.setText(list.get("moduleName"));
                    JField19.setText(list.get("presentationStatus"));
                }
            }
        }
    }
    private void refreshPresentationDetailsInUpdate(Object value) {
        if (presentationIDComboBoxInUpdate.getSelectedItem() != null) {
            List<Map<String, String>> lists = PresentationController.getNotYetGradedPresentationByLecId();
            for (Map<String, String> list : lists) {
                if (value.equals(list.get("id"))) {
                    JField28.setText(list.get("studentName"));
                    JField26.setText(list.get("presentationDateTime"));
                    JField30.setText(list.get("presentationDueDate"));
                    JField29.setText(list.get("moduleName"));
                    JField27.setText(list.get("presentationStatus"));
                    presentationMarksField.setText(list.get("presentationMarks"));                    
                }
            }
        }
    }

    private void presentationIDComboBox() {
        presentationIDComboBox.removeAllItems();
        List<Map<String, String>> lists2 = PresentationController.getAllPendingConfirmPresentationByLecId();
        if (lists2.isEmpty()) {
            presentationIDComboBox.addItem("There is no pending confirm presentation.");
            //Need to set the below fields back to default after accept/reject the consultation and there is no more pending confirm presentation
            JField20.setText("Student Name");
            JField18.setText("Presentation DateTime");
            JField25.setText("Presentation Due Date");
            JField24.setText("Module Name");
            JField19.setText("Presentation Status");
        } 
        else
        {
            for (Map<String, String> list : lists2)
            {
                presentationIDComboBox.addItem(list.get("id"));
            }
        }
        presentationIDComboBox.setSelectedIndex(0);
    }
    private void presentationIDComboBoxInUpdate() {
        presentationIDComboBoxInUpdate.removeAllItems();
        List<Map<String, String>> lists2 = PresentationController.getNotYetGradedPresentationByLecId();
        if (lists2.isEmpty()) {
            presentationIDComboBoxInUpdate.addItem("There is no presentation to be marked.");
            //Need to set the below fields back to default after update the presetation marks
            JField28.setText("Student Name");
            JField26.setText("Presentation DateTime");
            JField30.setText("Presentation Due Date");
            JField29.setText("Module Name");
            JField27.setText("Presentation Status");
            presentationMarksField.setText("Presentation Marks");
        } 
        else
        {
            for (Map<String, String> list : lists2)
            {
                presentationIDComboBoxInUpdate.addItem(list.get("id"));
            }
        }
        presentationIDComboBoxInUpdate.setSelectedIndex(0);
    }

    private void refreshPresentationOverviewTable(Integer value) {
        DefaultTableModel dtm = (DefaultTableModel)presentationOverviewTbl.getModel();
        dtm.setRowCount(0);
        
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dtm);
        presentationOverviewTbl.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter("".trim()));
        
        List<Map<String, String>> lists = PresentationController.getAllPresentationDetailsByLecId();

        switch(value) {
            case 0 -> {
                for (Map<String, String> list : lists) {
                    if (list.get("presentationStatus").equals("PENDING_BOOKING"))
                    {
                        String[] data = {list.get("studentId"), list.get("studentName"), list.get("moduleName"), list.get("presentationDueDate"), list.get("presentationDateTime"), list.get("presentationStatus"),list.get("presentationResult")};
                        dtm.addRow(data);
                    }
                }
            }
            case 1 -> {
                for (Map<String, String> list : lists) {
                    if (list.get("presentationStatus").equals("PENDING_CONFIRM")) {
                        String[] data = {list.get("studentId"), list.get("studentName"), list.get("moduleName"), list.get("presentationDueDate"), list.get("presentationDateTime"), list.get("presentationStatus"),list.get("presentationResult")};
                        dtm.addRow(data);
                    }
                }
            }
            case 2 -> {
                for (Map<String, String> list : lists) {
                    if (list.get("presentationStatus").equals("REJECTED")) {
                        String[] data = {list.get("studentId"), list.get("studentName"), list.get("moduleName"), list.get("presentationDueDate"), list.get("presentationDateTime"), list.get("presentationStatus"),list.get("presentationResult")};
                        dtm.addRow(data);
                    }
                }
            }    
            case 3 -> {
                for (Map<String, String> list : lists) {
                    if (list.get("presentationStatus").equals("BOOKED")) {
                        String[] data = {list.get("studentId"), list.get("studentName"), list.get("moduleName"), list.get("presentationDueDate"), list.get("presentationDateTime"), list.get("presentationStatus"),list.get("presentationResult")};
                        dtm.addRow(data);
                    }
                }
            }
            case 4 -> {
                for (Map<String, String> list : lists) {
                    if (list.get("presentationStatus").equals("MARKED")) {
                        String[] data = {list.get("studentId"), list.get("studentName"), list.get("moduleName"), list.get("presentationDueDate"), list.get("presentationDateTime"), list.get("presentationStatus"),list.get("presentationResult")};
                        dtm.addRow(data);
                    }
                }
            }
            case 5 -> {
                for (Map<String, String> list : lists) {
                    if (list.get("presentationStatus").equals("OVERDUE")) {
                        String[] data = {list.get("studentId"), list.get("studentName"), list.get("moduleName"), list.get("presentationDueDate"), list.get("presentationDateTime"), list.get("presentationStatus"),list.get("presentationResult")};
                        dtm.addRow(data);
                    }
                }
            }
        }
    }
    
    private void refreshUpcomingPresentationTable() {
        DefaultTableModel dtm =  (DefaultTableModel)upcomingPresentationTbl.getModel();
        dtm.setRowCount(0);
        List<Map<String, String>> lists = PresentationController.getAllBookedPresentationByLecId();
        if (!lists.isEmpty()) 
        {
            for (Map<String,String> list : lists) 
            {
            String[] data = {list.get("presentationDateTime"), list.get("studentName")};
            dtm.addRow(data);
            }
        }
    }
    
    //Check whether the input of presentation marks from lecturer is Double
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
        pendingMarking = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        menuBtn3 = new javax.swing.JLabel();
        pendingConfirm = new javax.swing.JLabel();
        menuBtn14 = new javax.swing.JLabel();
        searchBtn = new javax.swing.JLabel();
        searchFieldInOverview = new javax.swing.JTextField();
        presentationComboBox = new javax.swing.JComboBox<>();
        menuBtn11 = new javax.swing.JLabel();
        menuBtn15 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        acceptPresentationBtn = new javax.swing.JLabel();
        updatePresentationMarksBtn = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        upcomingPresentationTbl = new javax.swing.JTable();
        menuBtn16 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        presentationOverviewTbl = new javax.swing.JTable();
        Panel2 = new javax.swing.JPanel();
        MainTabbedPanel1 = new javax.swing.JTabbedPane();
        Panel8 = new javax.swing.JPanel();
        menuBtn33 = new javax.swing.JLabel();
        menuBtn34 = new javax.swing.JLabel();
        JField18 = new javax.swing.JTextField();
        menuBtn30 = new javax.swing.JLabel();
        menuBtn32 = new javax.swing.JLabel();
        menuBtn37 = new javax.swing.JLabel();
        rejectPresentationBtnInManage = new javax.swing.JLabel();
        JSeparator37 = new javax.swing.JSeparator();
        JField19 = new javax.swing.JTextField();
        menuBtn36 = new javax.swing.JLabel();
        presentationIDComboBox = new javax.swing.JComboBox<>();
        JField20 = new javax.swing.JTextField();
        acceptPresentationBtnInManage = new javax.swing.JLabel();
        JSeparator38 = new javax.swing.JSeparator();
        menuBtn42 = new javax.swing.JLabel();
        JField24 = new javax.swing.JTextField();
        menuBtn43 = new javax.swing.JLabel();
        JField25 = new javax.swing.JTextField();
        Panel10 = new javax.swing.JPanel();
        menuBtn44 = new javax.swing.JLabel();
        menuBtn45 = new javax.swing.JLabel();
        JField26 = new javax.swing.JTextField();
        menuBtn46 = new javax.swing.JLabel();
        menuBtn47 = new javax.swing.JLabel();
        menuBtn48 = new javax.swing.JLabel();
        JSeparator39 = new javax.swing.JSeparator();
        JField27 = new javax.swing.JTextField();
        menuBtn49 = new javax.swing.JLabel();
        presentationIDComboBoxInUpdate = new javax.swing.JComboBox<>();
        JField28 = new javax.swing.JTextField();
        updateBtn = new javax.swing.JLabel();
        menuBtn50 = new javax.swing.JLabel();
        JField29 = new javax.swing.JTextField();
        menuBtn51 = new javax.swing.JLabel();
        JField30 = new javax.swing.JTextField();
        presentationMarksField = new javax.swing.JTextField();
        menuBtn52 = new javax.swing.JLabel();

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
        menuBtn4.setText("PENDING MARKING");
        menuBtn4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn4.setOpaque(true);

        pendingMarking.setBackground(new java.awt.Color(254, 254, 254));
        pendingMarking.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 18)); // NOI18N
        pendingMarking.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pendingMarking.setText("0");
        pendingMarking.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pendingMarking.setOpaque(true);
        pendingMarking.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pendingMarkingMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menuBtn4, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
            .addComponent(pendingMarking, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(menuBtn4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pendingMarking, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE))
        );

        Panel1.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 50, 240, 90));

        jPanel6.setBackground(new java.awt.Color(254, 254, 254));

        menuBtn3.setBackground(new java.awt.Color(250, 250, 250));
        menuBtn3.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/status-24x24.png"))); // NOI18N
        menuBtn3.setText("PENDING CONFIRM");
        menuBtn3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn3.setOpaque(true);

        pendingConfirm.setBackground(new java.awt.Color(254, 254, 254));
        pendingConfirm.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 18)); // NOI18N
        pendingConfirm.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pendingConfirm.setText("0");
        pendingConfirm.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pendingConfirm.setOpaque(true);
        pendingConfirm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pendingConfirmMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menuBtn3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
            .addComponent(pendingConfirm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(menuBtn3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pendingConfirm, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
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

        presentationComboBox.setBackground(new java.awt.Color(254, 254, 254));
        presentationComboBox.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 12)); // NOI18N
        presentationComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pending Booking", "Pending Confirm", "Rejected", "Booked", "Marked", "Overdue" }));
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
        menuBtn11.setText("UPCOMING BOOKED PRESENTATION");
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

        acceptPresentationBtn.setBackground(new java.awt.Color(105, 105, 105));
        acceptPresentationBtn.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        acceptPresentationBtn.setForeground(new java.awt.Color(1, 1, 1));
        acceptPresentationBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/edit-24x24.png"))); // NOI18N
        acceptPresentationBtn.setText("ACCEPT/REJECT PRESENTATION");
        acceptPresentationBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        acceptPresentationBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                acceptPresentationBtnMouseClicked(evt);
            }
        });

        updatePresentationMarksBtn.setBackground(new java.awt.Color(105, 105, 105));
        updatePresentationMarksBtn.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        updatePresentationMarksBtn.setForeground(new java.awt.Color(1, 1, 1));
        updatePresentationMarksBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/action-24x24.png"))); // NOI18N
        updatePresentationMarksBtn.setText("UPDATE PRESENTATION MARKS");
        updatePresentationMarksBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        updatePresentationMarksBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updatePresentationMarksBtnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(updatePresentationMarksBtn)
                    .addComponent(acceptPresentationBtn))
                .addContainerGap(213, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(acceptPresentationBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(updatePresentationMarksBtn)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        Panel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 50, 500, 90));

        upcomingPresentationTbl.setModel(new javax.swing.table.DefaultTableModel(
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
        upcomingPresentationTbl.setFocusable(false);
        upcomingPresentationTbl.setRequestFocusEnabled(false);
        upcomingPresentationTbl.getTableHeader().setResizingAllowed(false);
        upcomingPresentationTbl.getTableHeader().setReorderingAllowed(false);
        upcomingPresentationTbl.setUpdateSelectionOnSort(false);
        upcomingPresentationTbl.setVerifyInputWhenFocusTarget(false);
        jScrollPane2.setViewportView(upcomingPresentationTbl);
        if (upcomingPresentationTbl.getColumnModel().getColumnCount() > 0) {
            upcomingPresentationTbl.getColumnModel().getColumn(0).setResizable(false);
            upcomingPresentationTbl.getColumnModel().getColumn(1).setResizable(false);
        }

        Panel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 180, 330, 420));

        menuBtn16.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn16.setText("VIEW PRESENTATATION DETAILS");
        menuBtn16.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn16.setOpaque(true);
        Panel1.add(menuBtn16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 670, 40));

        presentationOverviewTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Student ID", "Student Name", "Module Name", "Due Date", "Date", "Status", "Result"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        presentationOverviewTbl.setFocusable(false);
        presentationOverviewTbl.setRequestFocusEnabled(false);
        presentationOverviewTbl.getTableHeader().setResizingAllowed(false);
        presentationOverviewTbl.getTableHeader().setReorderingAllowed(false);
        presentationOverviewTbl.setUpdateSelectionOnSort(false);
        presentationOverviewTbl.setVerifyInputWhenFocusTarget(false);
        jScrollPane3.setViewportView(presentationOverviewTbl);
        if (presentationOverviewTbl.getColumnModel().getColumnCount() > 0) {
            presentationOverviewTbl.getColumnModel().getColumn(0).setResizable(false);
            presentationOverviewTbl.getColumnModel().getColumn(1).setResizable(false);
            presentationOverviewTbl.getColumnModel().getColumn(2).setResizable(false);
            presentationOverviewTbl.getColumnModel().getColumn(3).setResizable(false);
            presentationOverviewTbl.getColumnModel().getColumn(4).setResizable(false);
            presentationOverviewTbl.getColumnModel().getColumn(5).setResizable(false);
            presentationOverviewTbl.getColumnModel().getColumn(6).setResizable(false);
        }

        Panel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 670, 360));

        MainTabbedPanel.addTab("Overview", Panel1);

        Panel2.setPreferredSize(new java.awt.Dimension(1050, 570));
        Panel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        MainTabbedPanel1.setBackground(new java.awt.Color(230, 230, 230));
        MainTabbedPanel1.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N

        Panel8.setPreferredSize(new java.awt.Dimension(1050, 570));
        Panel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menuBtn33.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/officer-24x24.png"))); // NOI18N
        menuBtn33.setText("STUDENT NAME");
        menuBtn33.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn33.setOpaque(true);
        Panel8.add(menuBtn33, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 300, 40));

        menuBtn34.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn34.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/calendar-24x24.png"))); // NOI18N
        menuBtn34.setText("PRESENTATION DATE");
        menuBtn34.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn34.setOpaque(true);
        Panel8.add(menuBtn34, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 300, 40));

        JField18.setEditable(false);
        JField18.setBackground(new java.awt.Color(255, 255, 255));
        JField18.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField18.setForeground(new java.awt.Color(1, 1, 1));
        JField18.setText("Presentation Date");
        JField18.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        JField18.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        Panel8.add(JField18, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, 300, 35));

        menuBtn30.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn30.setText("REQUESTED PRESENTATION DETAILS");
        menuBtn30.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn30.setOpaque(true);
        Panel8.add(menuBtn30, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 300, 40));

        menuBtn32.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn32.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn32.setText("PRESENTATION ID");
        menuBtn32.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn32.setOpaque(true);
        Panel8.add(menuBtn32, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 300, 40));

        menuBtn37.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn37.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/action-24x24.png"))); // NOI18N
        menuBtn37.setText("ACTION :");
        menuBtn37.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn37.setOpaque(true);
        Panel8.add(menuBtn37, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 520, 90, 40));

        rejectPresentationBtnInManage.setBackground(new java.awt.Color(254, 254, 254));
        rejectPresentationBtnInManage.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        rejectPresentationBtnInManage.setForeground(new java.awt.Color(1, 1, 1));
        rejectPresentationBtnInManage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rejectPresentationBtnInManage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/cancel-24x24_1.png"))); // NOI18N
        rejectPresentationBtnInManage.setText("REJECT");
        rejectPresentationBtnInManage.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        rejectPresentationBtnInManage.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        rejectPresentationBtnInManage.setOpaque(true);
        rejectPresentationBtnInManage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rejectPresentationBtnInManageMouseClicked(evt);
            }
        });
        Panel8.add(rejectPresentationBtnInManage, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 520, 170, 35));

        JSeparator37.setForeground(new java.awt.Color(1, 1, 1));
        Panel8.add(JSeparator37, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 560, 200, 10));

        JField19.setEditable(false);
        JField19.setBackground(new java.awt.Color(255, 255, 255));
        JField19.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField19.setForeground(new java.awt.Color(1, 1, 1));
        JField19.setText("Presentation Status");
        JField19.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        JField19.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        Panel8.add(JField19, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 170, 300, 35));

        menuBtn36.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn36.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/status-24x24.png"))); // NOI18N
        menuBtn36.setText("STATUS");
        menuBtn36.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn36.setOpaque(true);
        Panel8.add(menuBtn36, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 130, 300, 40));

        presentationIDComboBox.setBackground(new java.awt.Color(254, 254, 254));
        presentationIDComboBox.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 12)); // NOI18N
        presentationIDComboBox.setToolTipText("d");
        presentationIDComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        presentationIDComboBox.setFocusable(false);
        presentationIDComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                presentationIDComboBoxActionPerformed(evt);
            }
        });
        Panel8.add(presentationIDComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 300, 35));

        JField20.setEditable(false);
        JField20.setBackground(new java.awt.Color(255, 255, 255));
        JField20.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField20.setForeground(new java.awt.Color(1, 1, 1));
        JField20.setText("Student Name");
        JField20.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        JField20.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        Panel8.add(JField20, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 300, 35));

        acceptPresentationBtnInManage.setBackground(new java.awt.Color(254, 254, 254));
        acceptPresentationBtnInManage.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        acceptPresentationBtnInManage.setForeground(new java.awt.Color(1, 1, 1));
        acceptPresentationBtnInManage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        acceptPresentationBtnInManage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/success-24x24.png"))); // NOI18N
        acceptPresentationBtnInManage.setText("ACCEPT");
        acceptPresentationBtnInManage.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        acceptPresentationBtnInManage.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        acceptPresentationBtnInManage.setOpaque(true);
        acceptPresentationBtnInManage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                acceptPresentationBtnInManageMouseClicked(evt);
            }
        });
        Panel8.add(acceptPresentationBtnInManage, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 520, 200, 35));

        JSeparator38.setForeground(new java.awt.Color(1, 1, 1));
        Panel8.add(JSeparator38, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 560, 170, 10));

        menuBtn42.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn42.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn42.setText("MODULE NAME");
        menuBtn42.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn42.setOpaque(true);
        Panel8.add(menuBtn42, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 50, 300, 40));

        JField24.setEditable(false);
        JField24.setBackground(new java.awt.Color(255, 255, 255));
        JField24.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField24.setForeground(new java.awt.Color(1, 1, 1));
        JField24.setText("Module Name");
        JField24.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        JField24.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        Panel8.add(JField24, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 90, 300, 35));

        menuBtn43.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn43.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/calendar-24x24.png"))); // NOI18N
        menuBtn43.setText("PRESENTATION DUE DATE");
        menuBtn43.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn43.setOpaque(true);
        Panel8.add(menuBtn43, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, 300, 40));

        JField25.setEditable(false);
        JField25.setBackground(new java.awt.Color(255, 255, 255));
        JField25.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField25.setForeground(new java.awt.Color(1, 1, 1));
        JField25.setText("Presentation Due Date");
        JField25.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        JField25.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        Panel8.add(JField25, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, 300, 35));

        MainTabbedPanel1.addTab("Accept/Reject", Panel8);

        Panel10.setPreferredSize(new java.awt.Dimension(1050, 570));
        Panel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menuBtn44.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn44.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/officer-24x24.png"))); // NOI18N
        menuBtn44.setText("STUDENT NAME");
        menuBtn44.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn44.setOpaque(true);
        Panel10.add(menuBtn44, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 300, 40));

        menuBtn45.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn45.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/calendar-24x24.png"))); // NOI18N
        menuBtn45.setText("PRESENTATION DATE");
        menuBtn45.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn45.setOpaque(true);
        Panel10.add(menuBtn45, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 300, 40));

        JField26.setEditable(false);
        JField26.setBackground(new java.awt.Color(255, 255, 255));
        JField26.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField26.setForeground(new java.awt.Color(1, 1, 1));
        JField26.setText("Presentation Date");
        JField26.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        JField26.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        Panel10.add(JField26, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, 300, 35));

        menuBtn46.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn46.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn46.setText("PRESENTATION DETAILS");
        menuBtn46.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn46.setOpaque(true);
        Panel10.add(menuBtn46, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 300, 40));

        menuBtn47.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn47.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn47.setText("PRESENTATION ID");
        menuBtn47.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn47.setOpaque(true);
        Panel10.add(menuBtn47, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 300, 40));

        menuBtn48.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn48.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn48.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/action-24x24.png"))); // NOI18N
        menuBtn48.setText("ACTION :");
        menuBtn48.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn48.setOpaque(true);
        Panel10.add(menuBtn48, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 520, 90, 40));

        JSeparator39.setForeground(new java.awt.Color(1, 1, 1));
        Panel10.add(JSeparator39, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 560, 200, 10));

        JField27.setEditable(false);
        JField27.setBackground(new java.awt.Color(255, 255, 255));
        JField27.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField27.setForeground(new java.awt.Color(1, 1, 1));
        JField27.setText("Presentation Status");
        JField27.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        JField27.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        Panel10.add(JField27, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 170, 300, 35));

        menuBtn49.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn49.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/status-24x24.png"))); // NOI18N
        menuBtn49.setText("STATUS");
        menuBtn49.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn49.setOpaque(true);
        Panel10.add(menuBtn49, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 130, 300, 40));

        presentationIDComboBoxInUpdate.setBackground(new java.awt.Color(254, 254, 254));
        presentationIDComboBoxInUpdate.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 12)); // NOI18N
        presentationIDComboBoxInUpdate.setToolTipText("d");
        presentationIDComboBoxInUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        presentationIDComboBoxInUpdate.setFocusable(false);
        presentationIDComboBoxInUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                presentationIDComboBoxInUpdateActionPerformed(evt);
            }
        });
        Panel10.add(presentationIDComboBoxInUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 300, 35));

        JField28.setEditable(false);
        JField28.setBackground(new java.awt.Color(255, 255, 255));
        JField28.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField28.setForeground(new java.awt.Color(1, 1, 1));
        JField28.setText("Student Name");
        JField28.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        JField28.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        Panel10.add(JField28, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 300, 35));

        updateBtn.setBackground(new java.awt.Color(254, 254, 254));
        updateBtn.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        updateBtn.setForeground(new java.awt.Color(1, 1, 1));
        updateBtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        updateBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/success-24x24.png"))); // NOI18N
        updateBtn.setText("UPDATE ");
        updateBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        updateBtn.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        updateBtn.setOpaque(true);
        updateBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updateBtnMouseClicked(evt);
            }
        });
        Panel10.add(updateBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 520, 200, 35));

        menuBtn50.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn50.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn50.setText("MODULE NAME");
        menuBtn50.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn50.setOpaque(true);
        Panel10.add(menuBtn50, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 50, 300, 40));

        JField29.setEditable(false);
        JField29.setBackground(new java.awt.Color(255, 255, 255));
        JField29.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField29.setForeground(new java.awt.Color(1, 1, 1));
        JField29.setText("Module Name");
        JField29.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        JField29.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        Panel10.add(JField29, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 90, 300, 35));

        menuBtn51.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn51.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/calendar-24x24.png"))); // NOI18N
        menuBtn51.setText("PRESENTATION DUE DATE");
        menuBtn51.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn51.setOpaque(true);
        Panel10.add(menuBtn51, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, 300, 40));

        JField30.setEditable(false);
        JField30.setBackground(new java.awt.Color(255, 255, 255));
        JField30.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField30.setForeground(new java.awt.Color(1, 1, 1));
        JField30.setText("Presentation Due Date");
        JField30.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        JField30.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        Panel10.add(JField30, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, 300, 35));

        presentationMarksField.setBackground(new java.awt.Color(255, 255, 255));
        presentationMarksField.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        presentationMarksField.setForeground(new java.awt.Color(1, 1, 1));
        presentationMarksField.setText("Presentation Marks");
        presentationMarksField.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        presentationMarksField.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        presentationMarksField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                presentationMarksFieldMouseClicked(evt);
            }
        });
        Panel10.add(presentationMarksField, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 250, 300, 35));

        menuBtn52.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn52.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/user-24x24.png"))); // NOI18N
        menuBtn52.setText("PRESENTATION MARKS");
        menuBtn52.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn52.setOpaque(true);
        Panel10.add(menuBtn52, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 210, 300, 40));

        MainTabbedPanel1.addTab("Update Marks", Panel10);

        Panel2.add(MainTabbedPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 630));

        MainTabbedPanel.addTab("Manage Presentation", Panel2);

        getContentPane().add(MainTabbedPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 660));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void acceptPresentationBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_acceptPresentationBtnMouseClicked
        MainTabbedPanel.setSelectedIndex(1);
        MainTabbedPanel1.setSelectedIndex(0);
    }//GEN-LAST:event_acceptPresentationBtnMouseClicked

    private void presentationComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_presentationComboBoxActionPerformed
        refreshPresentationOverviewTable(presentationComboBox.getSelectedIndex());
    }//GEN-LAST:event_presentationComboBoxActionPerformed

    private void searchFieldInOverviewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchFieldInOverviewMouseClicked
        if (searchFieldInOverview.getText().equals("Enter Keywords To Search")) {
            searchFieldInOverview.setText("");
        }
    }//GEN-LAST:event_searchFieldInOverviewMouseClicked

    private void searchBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchBtnMouseClicked
        DefaultTableModel dtm = (DefaultTableModel)presentationOverviewTbl.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dtm);
        presentationOverviewTbl.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(searchFieldInOverview.getText().trim()));
    }//GEN-LAST:event_searchBtnMouseClicked

    private void pendingConfirmMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pendingConfirmMouseClicked
        presentationComboBox.setSelectedIndex(1);
    }//GEN-LAST:event_pendingConfirmMouseClicked

    private void pendingMarkingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pendingMarkingMouseClicked
        presentationComboBox.setSelectedIndex(3);
    }//GEN-LAST:event_pendingMarkingMouseClicked

    private void updatePresentationMarksBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updatePresentationMarksBtnMouseClicked
        MainTabbedPanel.setSelectedIndex(1);
        MainTabbedPanel1.setSelectedIndex(1);
    }//GEN-LAST:event_updatePresentationMarksBtnMouseClicked

    private void updateBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateBtnMouseClicked
        if (presentationIDComboBoxInUpdate.getSelectedItem().equals("There is no presentation to be marked.")) {
            Dialog.ErrorDialog(MessageConstant.ERROR_SELECTION_EMPTY);
            return;
        }
        
        String presentationInput=presentationMarksField.getText();
        boolean flag=checkIsDouble(presentationInput);
        if(flag == true)
        {
            Double presentationMarks=Double.valueOf(presentationMarksField.getText());
            if(presentationMarks >= 0 && presentationMarks <= 100) {
                if(Dialog.ConfirmationDialog(MessageConstant.WARNING_MARK_PRESENTATION))
                {
                   PresentationController.updatePresentationMarksById(Integer.valueOf((String)presentationIDComboBoxInUpdate.getSelectedItem()),Double.valueOf(presentationMarksField.getText()));
                    Dialog.SuccessDialog(MessageConstant.SUCCESS_UPDATED_PRESENTATION_MARKS);
                    refresh(); 
                }
                else
                {

                }

            } 
            else
            {
               Dialog.ErrorDialog(MessageConstant.ERROR_MARKS_EXCEED_RANGE); 
            }  
        }
        else
        {
           Dialog.ErrorDialog(MessageConstant.ERROR_MARKS_FORMAT_INCORRECT); 
        }       
    }//GEN-LAST:event_updateBtnMouseClicked

    private void presentationIDComboBoxInUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_presentationIDComboBoxInUpdateActionPerformed
        refreshPresentationDetailsInUpdate(presentationIDComboBoxInUpdate.getSelectedItem());
    }//GEN-LAST:event_presentationIDComboBoxInUpdateActionPerformed

    private void acceptPresentationBtnInManageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_acceptPresentationBtnInManageMouseClicked
        if (presentationIDComboBox.getSelectedItem().equals("There is no requested presentation.")) {
            Dialog.ErrorDialog(MessageConstant.ERROR_SELECTION_EMPTY);
            return;
        }
        
        LocalDateTime requestedDateTime=DateTimeUtils.formatDateTime(JField18.getText());
        if (PresentationController.acceptPresentationById(Integer.valueOf((String)presentationIDComboBox.getSelectedItem()),requestedDateTime)){
            refresh();
        }
        //Refresh the page if the presentation is automatically rejected
        refresh();
    }//GEN-LAST:event_acceptPresentationBtnInManageMouseClicked

    private void presentationIDComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_presentationIDComboBoxActionPerformed
        refreshPresentationDetailsInAcceptReject(presentationIDComboBox.getSelectedItem());
    }//GEN-LAST:event_presentationIDComboBoxActionPerformed

    private void rejectPresentationBtnInManageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rejectPresentationBtnInManageMouseClicked
        if (presentationIDComboBox.getSelectedItem().equals("There is no pending confirm consultation.")) {
            Dialog.ErrorDialog(MessageConstant.ERROR_SELECTION_EMPTY);
            return;
        }

        if (PresentationController.rejectPresentationById(Integer.valueOf((String)presentationIDComboBox.getSelectedItem()))) {
            refresh();
        }
    }//GEN-LAST:event_rejectPresentationBtnInManageMouseClicked

    private void presentationMarksFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_presentationMarksFieldMouseClicked
        if (presentationMarksField.getText().equals("Presentation Marks") || presentationMarksField.getText().equals("0.0")) {
            presentationMarksField.setText("");
    }//GEN-LAST:event_presentationMarksFieldMouseClicked
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField JField18;
    private javax.swing.JTextField JField19;
    private javax.swing.JTextField JField20;
    private javax.swing.JTextField JField24;
    private javax.swing.JTextField JField25;
    private javax.swing.JTextField JField26;
    private javax.swing.JTextField JField27;
    private javax.swing.JTextField JField28;
    private javax.swing.JTextField JField29;
    private javax.swing.JTextField JField30;
    private javax.swing.JSeparator JSeparator37;
    private javax.swing.JSeparator JSeparator38;
    private javax.swing.JSeparator JSeparator39;
    private javax.swing.JTabbedPane MainTabbedPanel;
    private javax.swing.JTabbedPane MainTabbedPanel1;
    private javax.swing.JPanel Panel1;
    private javax.swing.JPanel Panel10;
    private javax.swing.JPanel Panel2;
    private javax.swing.JPanel Panel8;
    private javax.swing.JLabel acceptPresentationBtn;
    private javax.swing.JLabel acceptPresentationBtnInManage;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel menuBtn11;
    private javax.swing.JLabel menuBtn14;
    private javax.swing.JLabel menuBtn15;
    private javax.swing.JLabel menuBtn16;
    private javax.swing.JLabel menuBtn3;
    private javax.swing.JLabel menuBtn30;
    private javax.swing.JLabel menuBtn32;
    private javax.swing.JLabel menuBtn33;
    private javax.swing.JLabel menuBtn34;
    private javax.swing.JLabel menuBtn36;
    private javax.swing.JLabel menuBtn37;
    private javax.swing.JLabel menuBtn4;
    private javax.swing.JLabel menuBtn42;
    private javax.swing.JLabel menuBtn43;
    private javax.swing.JLabel menuBtn44;
    private javax.swing.JLabel menuBtn45;
    private javax.swing.JLabel menuBtn46;
    private javax.swing.JLabel menuBtn47;
    private javax.swing.JLabel menuBtn48;
    private javax.swing.JLabel menuBtn49;
    private javax.swing.JLabel menuBtn50;
    private javax.swing.JLabel menuBtn51;
    private javax.swing.JLabel menuBtn52;
    private static javax.swing.JLabel pendingConfirm;
    private static javax.swing.JLabel pendingMarking;
    private static javax.swing.JComboBox<String> presentationComboBox;
    private static javax.swing.JComboBox<String> presentationIDComboBox;
    private static javax.swing.JComboBox<String> presentationIDComboBoxInUpdate;
    private javax.swing.JTextField presentationMarksField;
    private javax.swing.JTable presentationOverviewTbl;
    private javax.swing.JLabel rejectPresentationBtnInManage;
    private javax.swing.JLabel searchBtn;
    private javax.swing.JTextField searchFieldInOverview;
    private javax.swing.JTable upcomingPresentationTbl;
    private javax.swing.JLabel updateBtn;
    private javax.swing.JLabel updatePresentationMarksBtn;
    // End of variables declaration//GEN-END:variables
}
