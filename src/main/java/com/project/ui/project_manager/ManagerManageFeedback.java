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
    
    private void exportToPdf(){
        int selectedRow = jTableStudentFeedback.getSelectedRow();
        if (selectedRow != -1) {
            try {
                generateFeedbackPdf();
                Dialog.SuccessDialog(MessageConstant.SUCCESS_GENERATE_PDF);
            } catch (FileNotFoundException e) {
                Dialog.ErrorDialog("Error creating PDF: " + e.getMessage());
            }
        } else {
            Dialog.ErrorDialog(MessageConstant.ERROR_EMPTY_MODULE);
        }
    }
    
    private void generateFeedbackPdf() throws FileNotFoundException{
        int selectedRow = jTableStudentFeedback.getSelectedRow();
        String moduleId = jTableStudentFeedback.getValueAt(selectedRow, 0).toString();
        String moduleName = jTableStudentFeedback.getValueAt(selectedRow, 1).toString();
        String studentId = jTableStudentFeedback.getValueAt(selectedRow, 2).toString();
        String studentName = jTableStudentFeedback.getValueAt(selectedRow, 3).toString();
        String comments = jTableStudentFeedback.getValueAt(selectedRow, 4).toString();
        
        
        
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

        menuBtn14.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn14.setText("Student Feedback");
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

        manageSupervisor.setBackground(new java.awt.Color(204, 204, 255));
        manageSupervisor.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        manageSupervisor.setForeground(new java.awt.Color(1, 1, 1));
        manageSupervisor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/exportPdf.png"))); // NOI18N
        manageSupervisor.setText(" Export Student Feedback");
        manageSupervisor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        manageSupervisor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                manageSupervisorMouseClicked(evt);
            }
        });
        jPanel7.add(manageSupervisor, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 30));

        Panel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 100, 250, 35));

        menuBtn16.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn16.setText("VIEW STUDENT FEEDBACK");
        menuBtn16.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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

        menuBtn17.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn17.setText("ACTION");
        menuBtn17.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn17.setOpaque(true);
        Panel1.add(menuBtn17, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 60, 250, 40));

        MainTabbedPanel.addTab("View Feedback", Panel1);

        getContentPane().add(MainTabbedPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 660));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void manageSupervisorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageSupervisorMouseClicked
//      add code to get row detail (Module Id)
        exportToPdf();
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField JField12;
    private javax.swing.JTabbedPane MainTabbedPanel;
    private javax.swing.JPanel Panel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTableStudentFeedback;
    private javax.swing.JLabel manageSupervisor;
    private javax.swing.JLabel menuBtn14;
    private javax.swing.JLabel menuBtn16;
    private javax.swing.JLabel menuBtn17;
    // End of variables declaration//GEN-END:variables


}
