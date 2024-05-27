package com.project.ui.project_manager;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import java.io.FileNotFoundException;
import com.project.ui.student.*;
import com.project.common.constants.MessageConstant;
import com.project.common.constants.ReportStatus;
import com.project.common.utils.DateTimeUtils;
import com.project.common.utils.Dialog;
import com.project.common.utils.JsonHandler;
import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

import com.project.controller.ConsultationController;
import com.project.controller.ModuleFeedbackController;
import com.project.controller.ProjectModuleController;
import com.project.controller.SubmissionController;
import com.project.controller.UserAccountController;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import org.bouncycastle.tsp.TSPUtil;
import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;

import java.util.List;
import java.util.Map;
import javax.swing.table.TableRowSorter;
import org.json.simple.JSONObject;

/**
 *
 * @author Jin Xun
 */
public class ManagerManageFeedback extends javax.swing.JInternalFrame {

    /**
     * Creates new form StudentAssignmentGui
     */
    public ManagerManageFeedback() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI)this.getUI();
        ui.setNorthPane(null);
        refresh();
    }

    private void refresh() {
        refreshTable();
        refreshTextField();
    }
    
    private void refreshTable() {
        DefaultTableModel dtm =  (DefaultTableModel)jTableStudentFeedback.getModel();
        dtm.setRowCount(0);
        List<Map<String, String>> feedbackLists = ModuleFeedbackController.getAllFeedbackForPM();
        for (Map<String, String> list : feedbackLists) {
                String[] data = {list.get("moduleId"), list.get("moduleCode"), list.get("studentId"), list.get("studentName"), list.get("comments")};
                dtm.addRow(data);
        }
    }
    
    private void refreshTextField(){
        fbModuleId.setText(null);
        fbModuleName.setText(null);
        fbStudentId.setText(null);
        fbStudentName.setText(null);
        fbComment.setText(null);
    }
    
    private void autofillStudentFeedback(String moduleId, String moduleName,String studentId,String studentName,String comment){
        fbModuleId.setText(moduleId);
        fbModuleName.setText(moduleName);
        fbStudentId.setText(studentId);
        fbStudentName.setText(studentName);
        fbComment.setText(comment);
    }
    
    private void exportToPdf(){
        String moduleId = fbModuleId.getText();
        if (moduleId != null && moduleId != "") {
            try {
                generateFeedbackPdf();
                Dialog.SuccessDialog(MessageConstant.SUCCESS_GENERATE_PDF);
            } catch (FileNotFoundException e) {
                Dialog.ErrorDialog("Error creating PDF: " + e.getMessage());
            }
        } else {
            Dialog.ErrorDialog(MessageConstant.ERROR_EMPTY_FEEDBACK);
        }
    }
    
    private void generateFeedbackPdf() throws FileNotFoundException{
        int selectedRow = jTableStudentFeedback.getSelectedRow();
        String moduleId = fbModuleId.getText();
        String moduleName = fbModuleName.getText();
        String studentId = fbStudentId.getText();
        String studentName = fbStudentName.getText();
        String comments = fbComment.getText();
        
        
        
        String fileName = moduleId + studentName;
        String path= fileName + ".pdf";
        PdfWriter pdfWriter = new PdfWriter(path);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        Document document = new Document(pdfDocument);
        float threecol =190f;
        float twocol =285f;
        float twocol150=twocol+150f;
        float twocolumnWidth[]={twocol150,twocol};
        float fullwidth[]= {threecol*3};
        float oneColumnWidth[]= {twocol150};
        Paragraph newline = new Paragraph("\n");
        Paragraph title = new Paragraph("Module Feedback").setBold().setBorder(Border.NO_BORDER).setFontSize(20f);
        document.add(title);
        document.add(newline);
        //Adding Title
        Table table= new Table(twocolumnWidth);
        table.addCell(new Cell().add("Receipt").setBold().setBorder(Border.NO_BORDER)).setFontSize(20f);
        Border gb = new SolidBorder(Color.DARK_GRAY, 2f);
        Table seperator = new Table(fullwidth);
        seperator.setBorder(gb);
        document.add(seperator);
        document.add(newline);
        
        Table twoColTable = new Table(twocolumnWidth);
        twoColTable.addCell(getFeedbackTitleCell("Student Details"));
        twoColTable.addCell(getFeedbackTitleCell("Modules Details"));
        document.add(twoColTable);
//        Table twoColTable = new Table(twocolumnWidth);
//        twoColTable.addCell(getBillingShippingCell("Room Information"));
//        twoColTable.addCell(getBillingShippingCell("Customer Information"));
        Table feedbackDetails = new Table(twocolumnWidth);
        feedbackDetails.addCell(getCellDetail("Student ID", true));
        feedbackDetails.addCell(getCellDetail("Module ID", true));
        feedbackDetails.addCell(getCellDetail(studentId, false));
        feedbackDetails.addCell(getCellDetail(moduleId, false));
        feedbackDetails.addCell(getCellDetail("Student Name", true));
        feedbackDetails.addCell(getCellDetail("Module Name", true));
        feedbackDetails.addCell(getCellDetail(studentName, false));
        feedbackDetails.addCell(getCellDetail(moduleName, false));
        document.add(feedbackDetails);
        
        Table oneCol = new Table(oneColumnWidth);
        oneCol.addCell(getCellDetail("Feedback", true));
        oneCol.addCell(getCellDetail(comments, false));
        document.add(oneCol);
        document.add(newline);
        document.add(seperator);
        document.add(newline);
        document.add(newline);
        document.add(newline);
//        document.add(seperator.setMarginTop(12f).setMarginBottom(12f));
        Table signature = new Table(twocolumnWidth);
        signature.addCell(getCellDetail("Project Manager: ___________________", true));
        signature.addCell(getCellDetail("Supervisor: ___________________", true));
        document.add(signature);
        document.close();
        System.out.println("PDF generated successfully: " + fileName);
    }
  
    static Cell getFeedbackTitleCell(String textValue){
        return new Cell().add(textValue).setFontSize(12f).setBold().setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
    }
//    
    static Cell getCellDetail(String textValue, Boolean isBold){
        Cell myCell= new Cell().add(textValue).setFontSize(10f).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
        return isBold ?myCell.setBold():myCell;
    }
    
    void generate() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    void generate(String[] get) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
        menuBtn14 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        JField12 = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        manageSupervisor = new javax.swing.JLabel();
        menuBtn16 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableStudentFeedback = new javax.swing.JTable();
        menuBtn17 = new javax.swing.JLabel();
        Panel2 = new javax.swing.JPanel();
        menuBtn38 = new javax.swing.JLabel();
        fbStudentId = new javax.swing.JTextField();
        fbModuleId = new javax.swing.JTextField();
        JSeparator37 = new javax.swing.JSeparator();
        jLabel40 = new javax.swing.JLabel();
        menuBtn37 = new javax.swing.JLabel();
        menuBtn30 = new javax.swing.JLabel();
        fbStudentName = new javax.swing.JTextField();
        menuBtn32 = new javax.swing.JLabel();
        menuBtn34 = new javax.swing.JLabel();
        menuBtn33 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        fbComment = new javax.swing.JTextArea();
        menuBtn31 = new javax.swing.JLabel();
        atSaveButton = new javax.swing.JLabel();
        JSeparator34 = new javax.swing.JSeparator();
        menuBtn35 = new javax.swing.JLabel();
        fbModuleName = new javax.swing.JTextField();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        MainTabbedPanel.setBackground(new java.awt.Color(230, 230, 230));
        MainTabbedPanel.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        MainTabbedPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MainTabbedPanelMouseClicked(evt);
            }
        });

        Panel1.setPreferredSize(new java.awt.Dimension(1050, 570));
        Panel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menuBtn14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn14.setText("Student Feedback");
        menuBtn14.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn14.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn14.setOpaque(true);
        Panel1.add(menuBtn14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 500, 40));

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/search-24x24.png"))); // NOI18N
        jLabel11.setBackground(new java.awt.Color(254, 254, 254));
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel11.setOpaque(true);
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });
        Panel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 100, 40, 35));

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
        JField12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JField12ActionPerformed(evt);
            }
        });
        Panel1.add(JField12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 290, 35));

        jPanel7.setBackground(new java.awt.Color(204, 204, 255));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        manageSupervisor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/view-24x24.png"))); // NOI18N
        manageSupervisor.setText(" View Student Feedback");
        manageSupervisor.setBackground(new java.awt.Color(204, 204, 255));
        manageSupervisor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        manageSupervisor.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        manageSupervisor.setForeground(new java.awt.Color(1, 1, 1));
        manageSupervisor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                manageSupervisorMouseClicked(evt);
            }
        });
        jPanel7.add(manageSupervisor, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 30));

        Panel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 100, 250, 35));

        menuBtn16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn16.setText("VIEW STUDENT FEEDBACK");
        menuBtn16.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn16.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn16.setOpaque(true);
        Panel1.add(menuBtn16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 230, 40));

        jTableStudentFeedback.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Module Id", "Module", "Student Id", "Student Name", "Comment"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableStudentFeedback.setFocusable(false);
        jTableStudentFeedback.setRequestFocusEnabled(false);
        jTableStudentFeedback.getTableHeader().setResizingAllowed(false);
        jTableStudentFeedback.getTableHeader().setReorderingAllowed(false);
        jTableStudentFeedback.setUpdateSelectionOnSort(false);
        jTableStudentFeedback.setVerifyInputWhenFocusTarget(false);
        jScrollPane3.setViewportView(jTableStudentFeedback);
        if (jTableStudentFeedback.getColumnModel().getColumnCount() > 0) {
            jTableStudentFeedback.getColumnModel().getColumn(0).setResizable(false);
            jTableStudentFeedback.getColumnModel().getColumn(1).setResizable(false);
            jTableStudentFeedback.getColumnModel().getColumn(2).setResizable(false);
            jTableStudentFeedback.getColumnModel().getColumn(3).setResizable(false);
            jTableStudentFeedback.getColumnModel().getColumn(4).setResizable(false);
        }

        Panel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 1020, 360));

        menuBtn17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn17.setText("ACTION");
        menuBtn17.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn17.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn17.setOpaque(true);
        Panel1.add(menuBtn17, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 60, 250, 40));

        MainTabbedPanel.addTab("View Feedback", Panel1);

        Panel2.setPreferredSize(new java.awt.Dimension(1050, 570));
        Panel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menuBtn38.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/name-24x24.png"))); // NOI18N
        menuBtn38.setText("STUDENT ID");
        menuBtn38.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn38.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn38.setOpaque(true);
        Panel2.add(menuBtn38, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 50, 300, 40));

        fbStudentId.setEditable(false);
        fbStudentId.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        fbStudentId.setBackground(new java.awt.Color(255, 255, 255));
        fbStudentId.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        fbStudentId.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        fbStudentId.setForeground(new java.awt.Color(1, 1, 1));
        fbStudentId.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fbStudentIdMouseClicked(evt);
            }
        });
        fbStudentId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fbStudentIdActionPerformed(evt);
            }
        });
        Panel2.add(fbStudentId, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 90, 340, 35));

        fbModuleId.setEditable(false);
        fbModuleId.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        fbModuleId.setBackground(new java.awt.Color(255, 255, 255));
        fbModuleId.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        fbModuleId.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        fbModuleId.setForeground(new java.awt.Color(1, 1, 1));
        fbModuleId.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fbModuleIdMouseClicked(evt);
            }
        });
        fbModuleId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fbModuleIdActionPerformed(evt);
            }
        });
        Panel2.add(fbModuleId, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 340, 35));

        JSeparator37.setForeground(new java.awt.Color(1, 1, 1));
        Panel2.add(JSeparator37, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 590, 170, 10));

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/delete-red-24x24.png"))); // NOI18N
        jLabel40.setText("Back");
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
        Panel2.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 550, 170, 35));

        menuBtn37.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/action-24x24.png"))); // NOI18N
        menuBtn37.setText("ACTION :");
        menuBtn37.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn37.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn37.setOpaque(true);
        Panel2.add(menuBtn37, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 550, 90, 40));

        menuBtn30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn30.setText("View Feedback");
        menuBtn30.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn30.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn30.setOpaque(true);
        Panel2.add(menuBtn30, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 500, 40));

        fbStudentName.setEditable(false);
        fbStudentName.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        fbStudentName.setBackground(new java.awt.Color(255, 255, 255));
        fbStudentName.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        fbStudentName.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        fbStudentName.setForeground(new java.awt.Color(1, 1, 1));
        fbStudentName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fbStudentNameMouseClicked(evt);
            }
        });
        fbStudentName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fbStudentNameActionPerformed(evt);
            }
        });
        Panel2.add(fbStudentName, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 170, 340, 35));

        menuBtn32.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn32.setText("MODULE ID");
        menuBtn32.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn32.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn32.setOpaque(true);
        Panel2.add(menuBtn32, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 300, 40));

        menuBtn34.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/user-24x24.png"))); // NOI18N
        menuBtn34.setText("STUDENT NAME");
        menuBtn34.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn34.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn34.setOpaque(true);
        Panel2.add(menuBtn34, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 130, 300, 40));

        menuBtn33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/edit-24x24.png"))); // NOI18N
        menuBtn33.setText("STUDENT FEEDBACK");
        menuBtn33.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn33.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn33.setOpaque(true);
        Panel2.add(menuBtn33, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 300, 40));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        fbComment.setColumns(20);
        fbComment.setRows(5);
        fbComment.setBackground(new java.awt.Color(255, 255, 255));
        fbComment.setForeground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(fbComment);

        Panel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 340, 120));

        menuBtn31.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/action-24x24.png"))); // NOI18N
        menuBtn31.setText("ACTION :");
        menuBtn31.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn31.setOpaque(true);
        Panel2.add(menuBtn31, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 470, 90, 40));

        atSaveButton.setBackground(new java.awt.Color(254, 254, 254));
        atSaveButton.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        atSaveButton.setForeground(new java.awt.Color(1, 1, 1));
        atSaveButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        atSaveButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/exportPdf.png"))); // NOI18N
        atSaveButton.setText("EXPORT PDF");
        atSaveButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        atSaveButton.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        atSaveButton.setOpaque(true);
        atSaveButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                atSaveButtonMouseClicked(evt);
            }
        });
        Panel2.add(atSaveButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 470, 170, 35));

        JSeparator34.setForeground(new java.awt.Color(1, 1, 1));
        Panel2.add(JSeparator34, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 510, 170, 10));

        menuBtn35.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn35.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn35.setText("MODULE NAME");
        menuBtn35.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn35.setOpaque(true);
        Panel2.add(menuBtn35, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 300, 40));

        fbModuleName.setEditable(false);
        fbModuleName.setBackground(new java.awt.Color(255, 255, 255));
        fbModuleName.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        fbModuleName.setForeground(new java.awt.Color(1, 1, 1));
        fbModuleName.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        fbModuleName.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        fbModuleName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fbModuleNameMouseClicked(evt);
            }
        });
        fbModuleName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fbModuleNameActionPerformed(evt);
            }
        });
        Panel2.add(fbModuleName, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 340, 35));

        MainTabbedPanel.addTab("Feedback Details", Panel2);

        getContentPane().add(MainTabbedPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 660));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void manageSupervisorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageSupervisorMouseClicked
//      add code to get row detail (Module Id)
        int selectedRow = jTableStudentFeedback.getSelectedRow();
        if (selectedRow != -1) {
            String moduleId = jTableStudentFeedback.getValueAt(selectedRow, 0).toString();
            String moduleName = jTableStudentFeedback.getValueAt(selectedRow, 1).toString();
            String studentId = jTableStudentFeedback.getValueAt(selectedRow, 2).toString();
            String studentName = jTableStudentFeedback.getValueAt(selectedRow, 3).toString();
            String comment = jTableStudentFeedback.getValueAt(selectedRow, 4).toString();
            MainTabbedPanel.setSelectedIndex(1);
            autofillStudentFeedback(moduleId, moduleName, studentId, studentName, comment);
        } else {
            Dialog.ErrorDialog(MessageConstant.ERROR_EMPTY_MODULE);
        }
    }//GEN-LAST:event_manageSupervisorMouseClicked

    private void JField12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JField12MouseClicked
        if (JField12.getText().equals("Enter Keywords To Search")) {
            JField12.setText("");
        }
    }//GEN-LAST:event_JField12MouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        DefaultTableModel dtm = (DefaultTableModel)jTableStudentFeedback.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dtm);
        jTableStudentFeedback.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(JField12.getText().trim()));
    }//GEN-LAST:event_jLabel11MouseClicked

    private void MainTabbedPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MainTabbedPanelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_MainTabbedPanelMouseClicked

    private void JField12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JField12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JField12ActionPerformed

    private void fbStudentIdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fbStudentIdMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_fbStudentIdMouseClicked

    private void fbStudentIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fbStudentIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fbStudentIdActionPerformed

    private void fbModuleIdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fbModuleIdMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_fbModuleIdMouseClicked

    private void fbModuleIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fbModuleIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fbModuleIdActionPerformed

    private void jLabel40MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel40MouseClicked

        MainTabbedPanel.setSelectedIndex(0);
        refresh();
    }//GEN-LAST:event_jLabel40MouseClicked

    private void fbStudentNameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fbStudentNameMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_fbStudentNameMouseClicked

    private void fbStudentNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fbStudentNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fbStudentNameActionPerformed

    private void atSaveButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_atSaveButtonMouseClicked
        // Save Assessment Type button
        exportToPdf();
    }//GEN-LAST:event_atSaveButtonMouseClicked

    private void fbModuleNameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fbModuleNameMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_fbModuleNameMouseClicked

    private void fbModuleNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fbModuleNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fbModuleNameActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField JField12;
    private javax.swing.JSeparator JSeparator34;
    private javax.swing.JSeparator JSeparator37;
    private javax.swing.JTabbedPane MainTabbedPanel;
    private javax.swing.JPanel Panel1;
    private javax.swing.JPanel Panel2;
    private javax.swing.JLabel atSaveButton;
    private javax.swing.JTextArea fbComment;
    private javax.swing.JTextField fbModuleId;
    private javax.swing.JTextField fbModuleName;
    private javax.swing.JTextField fbStudentId;
    private javax.swing.JTextField fbStudentName;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTableStudentFeedback;
    private javax.swing.JLabel manageSupervisor;
    private javax.swing.JLabel menuBtn14;
    private javax.swing.JLabel menuBtn16;
    private javax.swing.JLabel menuBtn17;
    private javax.swing.JLabel menuBtn30;
    private javax.swing.JLabel menuBtn31;
    private javax.swing.JLabel menuBtn32;
    private javax.swing.JLabel menuBtn33;
    private javax.swing.JLabel menuBtn34;
    private javax.swing.JLabel menuBtn35;
    private javax.swing.JLabel menuBtn37;
    private javax.swing.JLabel menuBtn38;
    // End of variables declaration//GEN-END:variables


}
