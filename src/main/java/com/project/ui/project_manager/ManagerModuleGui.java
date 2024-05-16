package com.project.ui.project_manager;

import com.project.ui.student.*;
import com.project.common.constants.MessageConstant;
import com.project.common.utils.Dialog;
import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

import com.project.controller.ConsultationController;
import com.project.controller.ProjectModuleController;
import org.bouncycastle.tsp.TSPUtil;
import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;

import java.util.List;
import java.util.Map;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Olaf
 */
public class ManagerModuleGui extends javax.swing.JInternalFrame {

     private Integer moduleId;
    /**
     * Creates new form StudentAssignmentGui
     */
    public ManagerModuleGui() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI)this.getUI();
        ui.setNorthPane(null);

        refreshTable();

        // salesManagementPanel.setText(PropertiesReader.getProperty("SalesManagementPanelVersion"));
        // refresh();
    }

    private void refresh() {
//        refreshDashboard();
//        refreshComboBox(0);
    }
    
    private void refreshTable() {
        DefaultTableModel dtm =  (DefaultTableModel)jTableModuleDetails.getModel();
        dtm.setRowCount(0);
        List<Map<String, String>> moduleLists = ProjectModuleController.getAllModuleDetailsByProjectManagerId();
        for (Map<String,String> list : moduleLists) {
            String[] data = {list.get("id"),list.get("moduleCode"), list.get("startDate"),list.get("endDate"), list.get("firstMarker") , list.get("secondMarker"),};
            dtm.addRow(data);   
        }
    }
    
    private String getSelectedModuleId() {
        int selectedRow = jTableModuleDetails.getSelectedRow();
        if (selectedRow != -1) {
            return (String) jTableModuleDetails.getValueAt(selectedRow, 0); // Assuming the module ID is in the first column
        } else {
            JOptionPane.showMessageDialog(null, "Please select a row first.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
    }
          
}
        private void autofillModuleSupervisor(Integer moduleId) {
             List<Map<String, String>> moduleLists = ProjectModuleController.getModuleById(moduleId);
//             System.out.println(moduleLists);
//          Check if module List is empty 
             Map<String, String> mLists = moduleLists.isEmpty() ? null : moduleLists.get(0);
//              if module list is not empty
                if (mLists != null) {
                    spModuleId.setText(mLists.get("id"));
                    spModuleName.setText(mLists.get("moduleCode"));
                    spStartDate.setText(mLists.get("startDate"));
                    spEndDate.setText(mLists.get("endDate"));
//                    mLists.get("firstMarker")
//                    mLists.get("secondMarker")
                }
        }
        
        private void fillspModuleComboBox(){
            //Get all lecturer list
//            List<Map<String, String>> moduleLists = UserAccountController.getAllLecturer();
//            spModuleSPComboBox.addItem();
        }


//    private void refreshDashboard() {
//        Map<String, Integer> map = ConsultationController.getUpcomingNFinishedConsultationForStudent();
//        menuBtn12.setText(map.get("upcoming").toString());
//        menuBtn13.setText(map.get("finished").toString());
//
//        fillInJComboBox();
//        refreshTable();
//    }
//
//    private void refreshDetails(Object value) {
//        if (consultationComboBox2.getSelectedItem() != null) {
//            List<Map<String, String>> lists = ConsultationController.getAllScheduledConsultationIdByStudentId();
//            for (Map<String, String> list : lists) {
//                if (value.equals(list.get("id"))) {
//                    JField19.setText(list.get("lecturer"));
//                    JField18.setText(list.get("date"));
//                    JField20.setText(list.get("status"));
//                }
//            }
//        }
//    }
//
//    private void fillInJComboBox() {
//        consultationComboBox1.removeAllItems();
//        consultationComboBox1.addItem("All");
//        List<String> lists = ConsultationController.getAllLecturerNProjectManagerNameForStudent();
//        for (String list : lists) {
//            consultationComboBox1.addItem(list);
//        }
//        consultationComboBox1.setSelectedIndex(0);
//
//        consultationComboBox2.removeAllItems();
//        List<Map<String, String>> lists2 = ConsultationController.getAllScheduledConsultationIdByStudentId();
//        if (lists2.isEmpty()) {
//            consultationComboBox2.addItem("There is no scheduled consultation.");
//        }
//        for (Map<String, String> list : lists2) {
//            consultationComboBox2.addItem(list.get("id"));
//        }
//        consultationComboBox2.setSelectedIndex(0);
//    }
//
//    private void refreshComboBox1(String value) {
//        DefaultTableModel dtm1 =  (DefaultTableModel)jTable4.getModel();
//        dtm1.setRowCount(0);
//        
//        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dtm1);
//        jTable4.setRowSorter(tr);
//        tr.setRowFilter(RowFilter.regexFilter("".trim()));
//        
//        List<Map<String, String>> availableSlots = ConsultationController.getAllAvailableConsultationSlots();
//        if (value.equals("All")) {
//            for (Map<String,String> list : availableSlots) {
//                String[] data = {list.get("id"), list.get("lecturer"), list.get("date"), list.get("status")};
//                dtm1.addRow(data);
//            }
//        } else {
//            for (Map<String,String> list : availableSlots) {
//                if (list.get("lecturer").equals(value)) {
//                    String[] data = {list.get("id"), list.get("lecturer"), list.get("date"), list.get("status")};
//                    dtm1.addRow(data);
//                }
//            }
//        }
//    }
//
//    private void refreshComboBox(Integer value) {
//        DefaultTableModel dtm = (DefaultTableModel)jTable3.getModel();
//        dtm.setRowCount(0);
//        
//        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dtm);
//        jTable3.setRowSorter(tr);
//        tr.setRowFilter(RowFilter.regexFilter("".trim()));
//        
//        
//        List<Map<String, String>> lists = ConsultationController.getAllEventsForStudent();
//
//        switch(value) {
//            case 0 -> {
//                for (Map<String, String> list : lists) {
//                    String[] data = {list.get("id"), list.get("lecturer"), list.get("date"), list.get("status")};
//                    dtm.addRow(data);
//                }
//            }
//            case 1 -> {
//                for (Map<String, String> list : lists) {
//                    if (list.get("status").equals("SCHEDULED")) {
//                        String[] data = {list.get("id"), list.get("lecturer"), list.get("date"), list.get("status")};
//                        dtm.addRow(data);
//                    }
//                }
//            }
//            case 2 -> {
//                for (Map<String, String> list : lists) {
//                    if (list.get("status").equals("COMPLETED")) {
//                        String[] data = {list.get("id"), list.get("lecturer"), list.get("date"), list.get("status")};
//                        dtm.addRow(data);
//                    }
//                }
//            }
//        }
//    }
//    
//    private void refreshTable() {
//        DefaultTableModel dtm =  (DefaultTableModel)jTable2.getModel();
//        dtm.setRowCount(0);
//        List<Map<String, String>> lists = ConsultationController.getUpcomingEventForStudent();
//        for (Map<String,String> list : lists) {
//            String[] data = {list.get("date"), list.get("lecturer")};
//            dtm.addRow(data);
//        }
//    }

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
        consultationComboBox = new javax.swing.JComboBox<>();
        menuBtn15 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        manageSupervisor = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        menuBtn16 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableModuleDetails = new javax.swing.JTable();
        Panel2 = new javax.swing.JPanel();
        Panel8 = new javax.swing.JPanel();
        menuBtn33 = new javax.swing.JLabel();
        menuBtn34 = new javax.swing.JLabel();
        spStartDate = new javax.swing.JTextField();
        menuBtn30 = new javax.swing.JLabel();
        menuBtn32 = new javax.swing.JLabel();
        menuBtn37 = new javax.swing.JLabel();
        spModuleSMComboBox = new javax.swing.JComboBox<>();
        jLabel40 = new javax.swing.JLabel();
        JSeparator37 = new javax.swing.JSeparator();
        spModuleId = new javax.swing.JTextField();
        spEndDate = new javax.swing.JTextField();
        menuBtn35 = new javax.swing.JLabel();
        spModuleName = new javax.swing.JTextField();
        menuBtn38 = new javax.swing.JLabel();
        menuBtn36 = new javax.swing.JLabel();
        spModuleSPComboBox = new javax.swing.JComboBox<>();
        jLabel36 = new javax.swing.JLabel();
        menuBtn29 = new javax.swing.JLabel();
        JSeparator33 = new javax.swing.JSeparator();
        MainTabbedPanel1 = new javax.swing.JTabbedPane();
        Panel4 = new javax.swing.JPanel();
        menuBtn22 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        consultationComboBox1 = new javax.swing.JComboBox<>();
        JField13 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        MainTabbedPanel.setBackground(new java.awt.Color(230, 230, 230));
        MainTabbedPanel.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N

        Panel1.setPreferredSize(new java.awt.Dimension(1050, 570));
        Panel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel10.setBackground(new java.awt.Color(254, 254, 254));

        menuBtn4.setBackground(new java.awt.Color(250, 250, 250));
        menuBtn4.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuBtn4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/success-24x24.png"))); // NOI18N
        menuBtn4.setText("COMPLETED");
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
        menuBtn3.setText("UPCOMING");
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
        menuBtn14.setText("MODULE ");
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

        consultationComboBox.setBackground(new java.awt.Color(254, 254, 254));
        consultationComboBox.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 12)); // NOI18N
        consultationComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Upcoming", "Completed" }));
        consultationComboBox.setToolTipText("d");
        consultationComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        consultationComboBox.setFocusable(false);
        consultationComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consultationComboBoxActionPerformed(evt);
            }
        });
        Panel1.add(consultationComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 330, 35));

        menuBtn15.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn15.setText("ACTION");
        menuBtn15.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn15.setOpaque(true);
        Panel1.add(menuBtn15, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 10, 420, 40));

        jPanel7.setBackground(new java.awt.Color(254, 254, 254));

        manageSupervisor.setBackground(new java.awt.Color(105, 105, 105));
        manageSupervisor.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        manageSupervisor.setForeground(new java.awt.Color(1, 1, 1));
        manageSupervisor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/user-24x24.png"))); // NOI18N
        manageSupervisor.setText("MANAGE SUPERVISOR AND SECOND MARKER");
        manageSupervisor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        manageSupervisor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                manageSupervisorMouseClicked(evt);
            }
        });

        jLabel21.setBackground(new java.awt.Color(105, 105, 105));
        jLabel21.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(1, 1, 1));
        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/user-24x24.png"))); // NOI18N
        jLabel21.setText("MANAGE STUDENT AND INTAKE");
        jLabel21.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel21MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(manageSupervisor, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(127, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(manageSupervisor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel21)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        Panel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 50, 500, 90));

        menuBtn16.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn16.setText("VIEW MODULE DETAILS");
        menuBtn16.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn16.setOpaque(true);
        Panel1.add(menuBtn16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 670, 40));

        jTableModuleDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Module Id", "Module Name", "Start Date", "End Date", "First Marker", "Second Marker"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableModuleDetails.setFocusable(false);
        jTableModuleDetails.setRequestFocusEnabled(false);
        jTableModuleDetails.getTableHeader().setResizingAllowed(false);
        jTableModuleDetails.getTableHeader().setReorderingAllowed(false);
        jTableModuleDetails.setUpdateSelectionOnSort(false);
        jTableModuleDetails.setVerifyInputWhenFocusTarget(false);
        jScrollPane3.setViewportView(jTableModuleDetails);
        if (jTableModuleDetails.getColumnModel().getColumnCount() > 0) {
            jTableModuleDetails.getColumnModel().getColumn(0).setResizable(false);
            jTableModuleDetails.getColumnModel().getColumn(1).setResizable(false);
            jTableModuleDetails.getColumnModel().getColumn(2).setResizable(false);
            jTableModuleDetails.getColumnModel().getColumn(3).setResizable(false);
            jTableModuleDetails.getColumnModel().getColumn(4).setResizable(false);
            jTableModuleDetails.getColumnModel().getColumn(5).setResizable(false);
        }

        Panel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 1020, 360));

        MainTabbedPanel.addTab("OverView", Panel1);

        Panel2.setPreferredSize(new java.awt.Dimension(1050, 570));
        Panel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Panel8.setPreferredSize(new java.awt.Dimension(1093, 695));
        Panel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menuBtn33.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/officer-24x24.png"))); // NOI18N
        menuBtn33.setText("SECOND MARKER");
        menuBtn33.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn33.setOpaque(true);
        Panel8.add(menuBtn33, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 130, 300, 40));

        menuBtn34.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn34.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/calendar-24x24.png"))); // NOI18N
        menuBtn34.setText("START DATE");
        menuBtn34.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn34.setOpaque(true);
        Panel8.add(menuBtn34, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 300, 40));

        spStartDate.setEditable(false);
        spStartDate.setBackground(new java.awt.Color(255, 255, 255));
        spStartDate.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        spStartDate.setForeground(new java.awt.Color(1, 1, 1));
        spStartDate.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        spStartDate.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        spStartDate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                spStartDateMouseClicked(evt);
            }
        });
        spStartDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                spStartDateActionPerformed(evt);
            }
        });
        Panel8.add(spStartDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, 300, 35));

        menuBtn30.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn30.setText("Manage Supervisor & Second Marker");
        menuBtn30.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn30.setOpaque(true);
        Panel8.add(menuBtn30, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 500, 40));

        menuBtn32.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn32.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn32.setText("MODULE ID");
        menuBtn32.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn32.setOpaque(true);
        Panel8.add(menuBtn32, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 300, 40));

        menuBtn37.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn37.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/action-24x24.png"))); // NOI18N
        menuBtn37.setText("ACTION :");
        menuBtn37.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn37.setOpaque(true);
        Panel8.add(menuBtn37, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 510, 90, 40));

        spModuleSMComboBox.setBackground(new java.awt.Color(254, 254, 254));
        spModuleSMComboBox.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 12)); // NOI18N
        spModuleSMComboBox.setToolTipText("d");
        spModuleSMComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        spModuleSMComboBox.setFocusable(false);
        spModuleSMComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                spModuleSMComboBoxActionPerformed(evt);
            }
        });
        Panel8.add(spModuleSMComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 170, 300, 35));

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
        Panel8.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 510, 170, 35));

        JSeparator37.setForeground(new java.awt.Color(1, 1, 1));
        Panel8.add(JSeparator37, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 550, 170, 10));

        spModuleId.setEditable(false);
        spModuleId.setBackground(new java.awt.Color(255, 255, 255));
        spModuleId.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        spModuleId.setForeground(new java.awt.Color(1, 1, 1));
        spModuleId.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        spModuleId.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        spModuleId.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                spModuleIdMouseClicked(evt);
            }
        });
        spModuleId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                spModuleIdActionPerformed(evt);
            }
        });
        Panel8.add(spModuleId, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 300, 35));

        spEndDate.setEditable(false);
        spEndDate.setBackground(new java.awt.Color(255, 255, 255));
        spEndDate.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        spEndDate.setForeground(new java.awt.Color(1, 1, 1));
        spEndDate.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        spEndDate.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        spEndDate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                spEndDateMouseClicked(evt);
            }
        });
        Panel8.add(spEndDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 300, 35));

        menuBtn35.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn35.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/calendar-24x24.png"))); // NOI18N
        menuBtn35.setText("END DATE");
        menuBtn35.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn35.setOpaque(true);
        Panel8.add(menuBtn35, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, 300, 40));

        spModuleName.setEditable(false);
        spModuleName.setBackground(new java.awt.Color(255, 255, 255));
        spModuleName.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        spModuleName.setForeground(new java.awt.Color(1, 1, 1));
        spModuleName.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        spModuleName.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        spModuleName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                spModuleNameMouseClicked(evt);
            }
        });
        spModuleName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                spModuleNameActionPerformed(evt);
            }
        });
        Panel8.add(spModuleName, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 300, 35));

        menuBtn38.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn38.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/officer-24x24.png"))); // NOI18N
        menuBtn38.setText("MODULE NAME");
        menuBtn38.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn38.setOpaque(true);
        Panel8.add(menuBtn38, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 300, 40));

        menuBtn36.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn36.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/officer-24x24.png"))); // NOI18N
        menuBtn36.setText("SUPERVISOR");
        menuBtn36.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn36.setOpaque(true);
        Panel8.add(menuBtn36, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 50, 300, 40));

        spModuleSPComboBox.setBackground(new java.awt.Color(254, 254, 254));
        spModuleSPComboBox.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 12)); // NOI18N
        spModuleSPComboBox.setToolTipText("d");
        spModuleSPComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        spModuleSPComboBox.setFocusable(false);
        spModuleSPComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                spModuleSPComboBoxActionPerformed(evt);
            }
        });
        Panel8.add(spModuleSPComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 90, 300, 35));

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
        Panel8.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 430, 170, 35));

        menuBtn29.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn29.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/action-24x24.png"))); // NOI18N
        menuBtn29.setText("ACTION :");
        menuBtn29.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn29.setOpaque(true);
        Panel8.add(menuBtn29, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 430, 90, 40));

        JSeparator33.setForeground(new java.awt.Color(1, 1, 1));
        Panel8.add(JSeparator33, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 470, 170, 10));

        Panel2.add(Panel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        MainTabbedPanel1.setBackground(new java.awt.Color(230, 230, 230));
        MainTabbedPanel1.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N

        Panel4.setPreferredSize(new java.awt.Dimension(1050, 570));
        Panel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menuBtn22.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn22.setText("BOOK CONSULTATION");
        menuBtn22.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn22.setOpaque(true);
        Panel4.add(menuBtn22, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 500, 40));

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Lecturer", "Date", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable4.setFocusable(false);
        jTable4.setRequestFocusEnabled(false);
        jTable4.getTableHeader().setResizingAllowed(false);
        jTable4.getTableHeader().setReorderingAllowed(false);
        jTable4.setUpdateSelectionOnSort(false);
        jTable4.setVerifyInputWhenFocusTarget(false);
        jScrollPane4.setViewportView(jTable4);
        if (jTable4.getColumnModel().getColumnCount() > 0) {
            jTable4.getColumnModel().getColumn(0).setResizable(false);
            jTable4.getColumnModel().getColumn(1).setResizable(false);
            jTable4.getColumnModel().getColumn(2).setResizable(false);
            jTable4.getColumnModel().getColumn(3).setResizable(false);
        }

        Panel4.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 1020, 390));

        consultationComboBox1.setBackground(new java.awt.Color(254, 254, 254));
        consultationComboBox1.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 12)); // NOI18N
        consultationComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Upcoming", "Completed" }));
        consultationComboBox1.setToolTipText("d");
        consultationComboBox1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        consultationComboBox1.setFocusable(false);
        consultationComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consultationComboBox1ActionPerformed(evt);
            }
        });
        Panel4.add(consultationComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 510, 35));

        JField13.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField13.setForeground(new java.awt.Color(1, 1, 1));
        JField13.setText("Enter Keywords To Search");
        JField13.setBorder(null);
        JField13.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JField13MouseClicked(evt);
            }
        });
        Panel4.add(JField13, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 50, 460, 35));

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

        MainTabbedPanel1.addTab("Book", Panel4);

        Panel2.add(MainTabbedPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 630));

        MainTabbedPanel.addTab("Manage Supervisor & Second Marker", Panel2);

        getContentPane().add(MainTabbedPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 660));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void manageSupervisorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageSupervisorMouseClicked
//      add code to get row detail (Module Id)
        int selectedRow = jTableModuleDetails.getSelectedRow();
        if (selectedRow != -1) {
            moduleId = Integer.parseInt(jTableModuleDetails.getValueAt(selectedRow, 0).toString());
            MainTabbedPanel.setSelectedIndex(1);
            autofillModuleSupervisor(moduleId);
        } else {
            JOptionPane.showMessageDialog(null, "Please select a row first.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_manageSupervisorMouseClicked

    private void consultationComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consultationComboBoxActionPerformed
//        refreshComboBox(consultationComboBox.getSelectedIndex());
    }//GEN-LAST:event_consultationComboBoxActionPerformed

    private void JField12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField12MouseClicked
//        if (JField12.getText().equals("Enter Keywords To Search")) {
//            JField12.setText("");
//        }
    }//GEN-LAST:event_JField12MouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
//        DefaultTableModel dtm = (DefaultTableModel)jTableModuleDetails.getModel();
//        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dtm);
//        jTableModuleDetails.setRowSorter(tr);
//        tr.setRowFilter(RowFilter.regexFilter(JField12.getText().trim()));
    }//GEN-LAST:event_jLabel11MouseClicked

    private void spModuleIdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_spModuleIdMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_spModuleIdMouseClicked

    private void jLabel40MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel40MouseClicked
//        if (consultationComboBox2.getSelectedItem().equals("There is no scheduled consultation.")) {
//            Dialog.ErrorDialog(MessageConstant.ERROR_SELECTION_EMPTY);
//            return;
//        }
//
//        if (ConsultationController.cancelBookedConsultationById(Integer.parseInt((String) consultationComboBox2.getSelectedItem()))) {
//            refresh();
//        }
        MainTabbedPanel.setSelectedIndex(0);
    }//GEN-LAST:event_jLabel40MouseClicked

    private void spModuleSMComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spModuleSMComboBoxActionPerformed
//        refreshDetails(consultationComboBox2.getSelectedItem());
    }//GEN-LAST:event_spModuleSMComboBoxActionPerformed

    private void spStartDateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_spStartDateMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_spStartDateMouseClicked

    private void jLabel36MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel36MouseClicked
//        int selectedRow = jTable4.getSelectedRow();
//        if (selectedRow == -1) {
//            Dialog.ErrorDialog(MessageConstant.ERROR_SELECTION_EMPTY);
//        } else {
//            Integer value = Integer.parseInt(jTable4.getValueAt(selectedRow, 0).toString());
//            if (ConsultationController.bookConsultationSlot(value)) {
//                refresh();
//            }
//        }
    }//GEN-LAST:event_jLabel36MouseClicked

    private void jLabel21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel21MouseClicked
//        MainTabbedPanel.setSelectedIndex(1);
//        MainTabbedPanel1.setSelectedIndex(1);
    }//GEN-LAST:event_jLabel21MouseClicked

    private void menuBtn12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuBtn12MouseClicked
//        consultationComboBox.setSelectedIndex(1);
    }//GEN-LAST:event_menuBtn12MouseClicked

    private void menuBtn13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuBtn13MouseClicked
//        consultationComboBox.setSelectedIndex(2);
    }//GEN-LAST:event_menuBtn13MouseClicked

    private void consultationComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consultationComboBox1ActionPerformed
//        if (consultationComboBox1.getSelectedItem() != null) {
//            refreshComboBox1(consultationComboBox1.getSelectedItem().toString());
//        }
    }//GEN-LAST:event_consultationComboBox1ActionPerformed

    private void JField13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField13MouseClicked
//        if (JField13.getText().equals("Enter Keywords To Search")) {
//            JField13.setText("");
//        }
    }//GEN-LAST:event_JField13MouseClicked

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
//        DefaultTableModel dtm = (DefaultTableModel)jTable4.getModel();
//        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dtm);
//        jTable4.setRowSorter(tr);
//        tr.setRowFilter(RowFilter.regexFilter(JField13.getText().trim()));
    }//GEN-LAST:event_jLabel12MouseClicked

    private void spEndDateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_spEndDateMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_spEndDateMouseClicked

    private void spStartDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spStartDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_spStartDateActionPerformed

    private void spModuleNameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_spModuleNameMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_spModuleNameMouseClicked

    private void spModuleIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spModuleIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_spModuleIdActionPerformed

    private void spModuleSPComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spModuleSPComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_spModuleSPComboBoxActionPerformed

    private void spModuleNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spModuleNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_spModuleNameActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField JField12;
    private javax.swing.JTextField JField13;
    private javax.swing.JSeparator JSeparator33;
    private javax.swing.JSeparator JSeparator37;
    private javax.swing.JTabbedPane MainTabbedPanel;
    private javax.swing.JTabbedPane MainTabbedPanel1;
    private javax.swing.JPanel Panel1;
    private javax.swing.JPanel Panel2;
    private javax.swing.JPanel Panel4;
    private javax.swing.JPanel Panel8;
    private static javax.swing.JComboBox<String> consultationComboBox;
    private static javax.swing.JComboBox<String> consultationComboBox1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTableModuleDetails;
    private javax.swing.JLabel manageSupervisor;
    private static javax.swing.JLabel menuBtn12;
    private static javax.swing.JLabel menuBtn13;
    private javax.swing.JLabel menuBtn14;
    private javax.swing.JLabel menuBtn15;
    private javax.swing.JLabel menuBtn16;
    private javax.swing.JLabel menuBtn22;
    private javax.swing.JLabel menuBtn29;
    private javax.swing.JLabel menuBtn3;
    private javax.swing.JLabel menuBtn30;
    private javax.swing.JLabel menuBtn32;
    private javax.swing.JLabel menuBtn33;
    private javax.swing.JLabel menuBtn34;
    private javax.swing.JLabel menuBtn35;
    private javax.swing.JLabel menuBtn36;
    private javax.swing.JLabel menuBtn37;
    private javax.swing.JLabel menuBtn38;
    private javax.swing.JLabel menuBtn4;
    private javax.swing.JTextField spEndDate;
    private javax.swing.JTextField spModuleId;
    private javax.swing.JTextField spModuleName;
    private static javax.swing.JComboBox<String> spModuleSMComboBox;
    private static javax.swing.JComboBox<String> spModuleSPComboBox;
    private javax.swing.JTextField spStartDate;
    // End of variables declaration//GEN-END:variables


}
