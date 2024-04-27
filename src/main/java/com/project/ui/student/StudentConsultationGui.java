package com.project.ui.student;

import com.project.common.constants.MessageConstant;
import com.project.common.utils.Dialog;
import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

import com.project.controller.ConsultationController;
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
public class StudentConsultationGui extends javax.swing.JInternalFrame {

    /**
     * Creates new form StudentAssignmentGui
     */
    public StudentConsultationGui() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI)this.getUI();
        ui.setNorthPane(null);

        refresh();

        // salesManagementPanel.setText(PropertiesReader.getProperty("SalesManagementPanelVersion"));
        // refresh();
    }

    private void refresh() {
        refreshDashboard();
        refreshComboBox(0);
    }
    
    private void refreshDashboard() {
        Map<String, Integer> map = ConsultationController.getUpcomingNFinishedConsultationForStudent();
        menuBtn12.setText(map.get("upcoming").toString());
        menuBtn13.setText(map.get("finished").toString());

        fillInJComboBox1();
        refreshTable();
    }

    private void fillInJComboBox1() {
        consultationComboBox1.removeAllItems();
        consultationComboBox1.addItem("All");
        List<String> lists = ConsultationController.getAllLecturerNProjectManagerNameForStudent();
        for (String list : lists) {
            consultationComboBox1.addItem(list);
        }
        
        consultationComboBox1.setSelectedIndex(0);
    }

    private void refreshComboBox1(String value) {
        DefaultTableModel dtm1 =  (DefaultTableModel)jTable4.getModel();
        dtm1.setRowCount(0);
        
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dtm1);
        jTable4.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter("".trim()));
        
        List<Map<String, String>> availableSlots = ConsultationController.getAllAvailableConsultationSlots();
        if (value.equals("All")) {
            for (Map<String,String> list : availableSlots) {
                String[] data = {list.get("id"), list.get("lecturer"), list.get("date"), list.get("status")};
                dtm1.addRow(data);
            }
        } else {
            for (Map<String,String> list : availableSlots) {
                if (list.get("lecturer").equals(value)) {
                    String[] data = {list.get("id"), list.get("lecturer"), list.get("date"), list.get("status")};
                    dtm1.addRow(data);
                }
            }
        }
    }

    private void refreshComboBox(Integer value) {
        DefaultTableModel dtm = (DefaultTableModel)jTable3.getModel();
        dtm.setRowCount(0);
        
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dtm);
        jTable3.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter("".trim()));
        
        
        List<Map<String, String>> lists = ConsultationController.getAllEventsForStudent();

        switch(value) {
            case 0 -> {
                for (Map<String, String> list : lists) {
                    String[] data = {list.get("id"), list.get("lecturer"), list.get("date"), list.get("status")};
                    dtm.addRow(data);
                }
            }
            case 1 -> {
                for (Map<String, String> list : lists) {
                    if (list.get("status").equals("SCHEDULED")) {
                        String[] data = {list.get("id"), list.get("lecturer"), list.get("date"), list.get("status")};
                        dtm.addRow(data);
                    }
                }
            }
            case 2 -> {
                for (Map<String, String> list : lists) {
                    if (list.get("status").equals("COMPLETED")) {
                        String[] data = {list.get("id"), list.get("lecturer"), list.get("date"), list.get("status")};
                        dtm.addRow(data);
                    }
                }
            }
        }
    }
    
    private void refreshTable() {
        DefaultTableModel dtm =  (DefaultTableModel)jTable2.getModel();
        dtm.setRowCount(0);
        List<Map<String, String>> lists = ConsultationController.getUpcomingEventForStudent();
        for (Map<String,String> list : lists) {
            String[] data = {list.get("date"), list.get("lecturer")};
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
        consultationComboBox = new javax.swing.JComboBox<>();
        menuBtn11 = new javax.swing.JLabel();
        menuBtn15 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        menuBtn16 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        Panel2 = new javax.swing.JPanel();
        MainTabbedPanel1 = new javax.swing.JTabbedPane();
        Panel4 = new javax.swing.JPanel();
        menuBtn22 = new javax.swing.JLabel();
        JSeparator33 = new javax.swing.JSeparator();
        jLabel36 = new javax.swing.JLabel();
        menuBtn29 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        consultationComboBox1 = new javax.swing.JComboBox<>();
        JField13 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        Panel8 = new javax.swing.JPanel();
        menuBtn31 = new javax.swing.JLabel();
        menuBtn33 = new javax.swing.JLabel();
        menuBtn34 = new javax.swing.JLabel();
        JField18 = new javax.swing.JTextField();
        menuBtn35 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        menuBtn30 = new javax.swing.JLabel();
        menuBtn32 = new javax.swing.JLabel();
        menuBtn37 = new javax.swing.JLabel();
        projectComboBox2 = new javax.swing.JComboBox<>();
        jLabel40 = new javax.swing.JLabel();
        JSeparator37 = new javax.swing.JSeparator();
        JField22 = new javax.swing.JTextField();
        JField19 = new javax.swing.JTextField();

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
        menuBtn14.setText("CONSULTATION");
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

        menuBtn11.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
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
        jLabel13.setText("BOOK CONSULTATION");
        jLabel13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });

        jLabel21.setBackground(new java.awt.Color(105, 105, 105));
        jLabel21.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(1, 1, 1));
        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/delete-24x24.png"))); // NOI18N
        jLabel21.setText("CANCEL CONSULTATION");
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
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(75, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel21))
                .addContainerGap(51, Short.MAX_VALUE))
        );

        Panel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 50, 500, 90));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Lecturer"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setFocusable(false);
        jTable2.setRequestFocusEnabled(false);
        jTable2.getTableHeader().setResizingAllowed(false);
        jTable2.getTableHeader().setReorderingAllowed(false);
        jTable2.setUpdateSelectionOnSort(false);
        jTable2.setVerifyInputWhenFocusTarget(false);
        jScrollPane2.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setResizable(false);
            jTable2.getColumnModel().getColumn(1).setResizable(false);
        }

        Panel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 180, 330, 420));

        menuBtn16.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn16.setText("VIEW CONSULTATION DETAILS");
        menuBtn16.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn16.setOpaque(true);
        Panel1.add(menuBtn16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 670, 40));

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable3.setFocusable(false);
        jTable3.setRequestFocusEnabled(false);
        jTable3.getTableHeader().setResizingAllowed(false);
        jTable3.getTableHeader().setReorderingAllowed(false);
        jTable3.setUpdateSelectionOnSort(false);
        jTable3.setVerifyInputWhenFocusTarget(false);
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

        menuBtn22.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn22.setText("BOOK CONSULTATION");
        menuBtn22.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn22.setOpaque(true);
        Panel4.add(menuBtn22, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 500, 40));

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

        Panel8.setPreferredSize(new java.awt.Dimension(1050, 570));
        Panel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menuBtn31.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn31.setText("INTAKE CODE");
        menuBtn31.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn31.setOpaque(true);
        Panel8.add(menuBtn31, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 300, 40));

        menuBtn33.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn33.setText("LECTURER NAME");
        menuBtn33.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn33.setOpaque(true);
        Panel8.add(menuBtn33, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 300, 40));

        menuBtn34.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn34.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn34.setText("DUE DATE");
        menuBtn34.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn34.setOpaque(true);
        Panel8.add(menuBtn34, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 300, 40));

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

        menuBtn35.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn35.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn35.setText("PROJECT DESCRIPTION");
        menuBtn35.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn35.setOpaque(true);
        Panel8.add(menuBtn35, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 400, 300, 40));

        jTextArea3.setEditable(false);
        jTextArea3.setColumns(20);
        jTextArea3.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        jTextArea3.setLineWrap(true);
        jTextArea3.setRows(5);
        jTextArea3.setText("Project Description");
        jTextArea3.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        jScrollPane5.setViewportView(jTextArea3);

        Panel8.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 440, 300, 110));

        menuBtn30.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn30.setText("EDIT CONSULTATION");
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
        Panel8.add(menuBtn37, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 510, 90, 40));

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
        Panel8.add(JField22, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 300, 35));

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

        MainTabbedPanel1.addTab("Edit", Panel8);

        Panel2.add(MainTabbedPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 630));

        MainTabbedPanel.addTab("Concultation", Panel2);

        getContentPane().add(MainTabbedPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 660));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        MainTabbedPanel.setSelectedIndex(1);
    }//GEN-LAST:event_jLabel13MouseClicked

    private void consultationComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consultationComboBoxActionPerformed
        refreshComboBox(consultationComboBox.getSelectedIndex());
    }//GEN-LAST:event_consultationComboBoxActionPerformed

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

    private void JField19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField19MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JField19MouseClicked

    private void JField22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField22MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JField22MouseClicked

    private void jLabel40MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel40MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel40MouseClicked

    private void projectComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_projectComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_projectComboBox2ActionPerformed

    private void JField18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField18MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JField18MouseClicked

    private void jLabel36MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel36MouseClicked
        int selectedRow = jTable4.getSelectedRow();
        if (selectedRow == -1) {
            Dialog.ErrorDialog(MessageConstant.ERROR_SELECTION_EMPTY);
        } else {
            Integer value = Integer.parseInt(jTable4.getValueAt(selectedRow, 0).toString());
            if (ConsultationController.bookConsultationSlot(value)) {
                refresh();
            }
        }
    }//GEN-LAST:event_jLabel36MouseClicked

    private void jLabel21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel21MouseClicked
        MainTabbedPanel.setSelectedIndex(1);
        MainTabbedPanel1.setSelectedIndex(1);
    }//GEN-LAST:event_jLabel21MouseClicked

    private void menuBtn12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuBtn12MouseClicked
        consultationComboBox.setSelectedIndex(1);
    }//GEN-LAST:event_menuBtn12MouseClicked

    private void menuBtn13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuBtn13MouseClicked
        consultationComboBox.setSelectedIndex(2);
    }//GEN-LAST:event_menuBtn13MouseClicked

    private void consultationComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consultationComboBox1ActionPerformed
        if (consultationComboBox1.getSelectedItem() != null) {
            refreshComboBox1(consultationComboBox1.getSelectedItem().toString());
        }
    }//GEN-LAST:event_consultationComboBox1ActionPerformed

    private void JField13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField13MouseClicked
        if (JField13.getText().equals("Enter Keywords To Search")) {
            JField13.setText("");
        }
    }//GEN-LAST:event_JField13MouseClicked

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        DefaultTableModel dtm = (DefaultTableModel)jTable4.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dtm);
        jTable4.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(JField13.getText().trim()));
    }//GEN-LAST:event_jLabel12MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField JField12;
    private javax.swing.JTextField JField13;
    private javax.swing.JTextField JField18;
    private javax.swing.JTextField JField19;
    private javax.swing.JTextField JField22;
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
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JLabel menuBtn11;
    private static javax.swing.JLabel menuBtn12;
    private static javax.swing.JLabel menuBtn13;
    private javax.swing.JLabel menuBtn14;
    private javax.swing.JLabel menuBtn15;
    private javax.swing.JLabel menuBtn16;
    private javax.swing.JLabel menuBtn22;
    private javax.swing.JLabel menuBtn29;
    private javax.swing.JLabel menuBtn3;
    private javax.swing.JLabel menuBtn30;
    private javax.swing.JLabel menuBtn31;
    private javax.swing.JLabel menuBtn32;
    private javax.swing.JLabel menuBtn33;
    private javax.swing.JLabel menuBtn34;
    private javax.swing.JLabel menuBtn35;
    private javax.swing.JLabel menuBtn37;
    private javax.swing.JLabel menuBtn4;
    private static javax.swing.JComboBox<String> projectComboBox2;
    // End of variables declaration//GEN-END:variables
}
