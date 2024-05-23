package com.project.ui.lecturer;

import com.project.common.constants.MessageConstant;
import com.project.common.utils.Dialog;
import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

import com.project.controller.ProjectModuleController;
import com.project.controller.SubmissionController;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        refreshSelectModuleInMarkReport2(selectModuleComboBoxInMarkReport2.getSelectedItem());
        refreshSelectModuleInModify(selectModuleComboBoxInModify.getSelectedItem());
    }
    
    private void refreshDashboard() {
        Map<String, Integer> map = SubmissionController.getPendingMarkingSubmissionForLecturer();
        firstMarkerPendingMarking.setText(map.get("firstMarkerPendingMarking").toString());
        secondMarkerPendingMarking.setText(map.get("secondMarkerPendingMarking").toString());

        selectModuleComboBoxInMarkReport1();
        selectModuleComboBoxInMarkReport2();
        selectModuleComboBoxInModify();
        refreshUpcomingSubmissionDueDateTable();   
    }

    private void refreshSelectModuleInMarkReport1(Object value) {
        selectSubmissionComboBoxInMarkReport1.removeAllItems();
        if(pdfSubmissionPreview != null)
        {
            filePanelInMarkReport1.remove(pdfSubmissionPreview); // Remove the panel 's' from jPanel2
            filePanelInMarkReport1.revalidate(); // Recalculate layout
            filePanelInMarkReport1.repaint(); // Repaint 
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
                    reportResultFieldInMarkReport1.setText("Report Result");
                    lecturerCommentFieldInMarkReport1.setText("Lecturer Comment");
                    projectFileNameFieldInMarkReport1.setText("Project File Name");
                }
            }
        }
    }
    private void refreshSelectModuleInMarkReport2(Object value) {
        selectSubmissionComboBoxInMarkReport2.removeAllItems();
        if(pdfSubmissionPreview != null)
        {
            filePanelInMarkReport2.remove(pdfSubmissionPreview); // Remove the panel 's' from jPanel2
            filePanelInMarkReport2.revalidate(); // Recalculate layout
            filePanelInMarkReport2.repaint(); // Repaint 
        }
        if (selectModuleComboBoxInMarkReport2.getSelectedItem() != null) {
            List<Map<String, String>> lists = ProjectModuleController.getAllModuleDetailsBySecondMarkerId();
            for (Map<String, String> list : lists) {
                if (value.equals(list.get("id"))) {
                    //Need to set the below fields back to default after update
                    JField33.setText("Student Name");
                    JField36.setText(list.get("moduleCode"));
                    JField34.setText("Report Type");
                    JField35.setText("Marking Status");
                    reportResultFieldInMarkReport2.setText("Report Result");
                    lecturerCommentFieldInMarkReport2.setText("Lecturer Comment");
                    projectFileNameFieldInMarkReport2.setText("Project File Name");
                }
            }
        }
    }
    
    private void refreshSelectModuleInModify(Object value) {
        selectSubmissionComboBoxInModify.removeAllItems();
        if(pdfSubmissionPreview != null)
        {
            filePanelInModify.remove(pdfSubmissionPreview); // Remove the panel 's' from jPanel2
            filePanelInModify.revalidate(); // Recalculate layout
            filePanelInModify.repaint(); // Repaint 
        }
        if (selectModuleComboBoxInModify.getSelectedItem() != null) {
            List<Map<String, String>> lists = ProjectModuleController.getAllModuleDetailsByFirstMarkerId();
            for (Map<String, String> list : lists) {
                if (value.equals(list.get("id"))) {
                    //Need to set the below fields back to default after update
                    JField29.setText("Student Name");
                    JField26.setText(list.get("moduleCode"));
                    JField32.setText("Report Type");
                    JField30.setText("Marking Status");
                    reportResultFieldInModify.setText("Report Result");
                    lecturerCommentFieldInModify.setText("Lecturer Comment");
                    projectFileNameFieldInModify.setText("Project File Name");
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
            reportResultFieldInMarkReport1.setText("Report Result");
            lecturerCommentFieldInMarkReport1.setText("Lecturer Comment");
            projectFileNameFieldInMarkReport1.setText("Project File Name");
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
    
    private void selectModuleComboBoxInMarkReport2() {
        selectModuleComboBoxInMarkReport2.removeAllItems();
        List<Map<String, String>> list1 = ProjectModuleController.getAllModuleDetailsBySecondMarkerId();
        if (list1.isEmpty()) {
            selectModuleComboBoxInMarkReport2.addItem(MessageConstant.CONDITION_NO_MODULES_UNDER_LECTURER);
            //Need to set the below fields back to default after update
            JField33.setText("Student Name");
            JField36.setText("Module Name");
            JField34.setText("Report Type");
            JField35.setText("Marking Status");
            reportResultFieldInMarkReport2.setText("Report Result");
            lecturerCommentFieldInMarkReport2.setText("Lecturer Comment");
            projectFileNameFieldInMarkReport2.setText("Project File Name");
        } 
        else
        {
            for (Map<String, String> list : list1)
            {
               selectModuleComboBoxInMarkReport2.addItem(list.get("id"));
               JField36.setText(list.get("moduleCode"));
            }
        }
        selectModuleComboBoxInMarkReport2.setSelectedIndex(0);
    }
    
    private void selectModuleComboBoxInModify() {
        selectModuleComboBoxInModify.removeAllItems();
        List<Map<String, String>> list1 = ProjectModuleController.getAllModuleDetailsByFirstMarkerId();
        if (list1.isEmpty()) {
            selectModuleComboBoxInModify.addItem(MessageConstant.CONDITION_NO_MODULES_UNDER_LECTURER);
            //Need to set the below fields back to default after update
            JField29.setText("Student Name");
            JField26.setText("Module Name");
            JField32.setText("Report Type");
            JField30.setText("Marking Status");
            reportResultFieldInModify.setText("Report Result");
            lecturerCommentFieldInModify.setText("Lecturer Comment");
            projectFileNameFieldInModify.setText("Project File Name");
        } 
        else
        {
            for (Map<String, String> list : list1)
            {
               selectModuleComboBoxInModify.addItem(list.get("id"));
               JField26.setText(list.get("moduleCode"));
            }
        }
        selectModuleComboBoxInModify.setSelectedIndex(0);
    }
    
    private void refreshSubmissionDetailsInMarkReport1(Object value) {
        if (selectSubmissionComboBoxInMarkReport1.getSelectedItem() != null && selectSubmissionComboBoxInMarkReport1.getSelectedItem() != MessageConstant.CONDITION_NO_SUBMISSIONS_UNDER_MODULES) {
            List<Map<String, String>> lists = SubmissionController.getSubmissionDetailsById(Integer.valueOf((String)value));
            for (Map<String, String> list : lists) {
                if (value.equals(list.get("id"))) {
                    JField27.setText(list.get("studentName"));
                    JField31.setText(list.get("reportType"));
                    JField28.setText(list.get("markingStatus"));
                    reportResultFieldInMarkReport1.setText(list.get("reportMarks"));
                    lecturerCommentFieldInMarkReport1.setText(list.get("lecturerComment"));
                    projectFileNameFieldInMarkReport1.setText(list.get("fileName"));
                    if(pdfSubmissionPreview != null)
                    {
                        filePanelInMarkReport1.remove(pdfSubmissionPreview); // Remove the panel 's' from jPanel
                        filePanelInMarkReport1.revalidate(); // Recalculate layout
                        filePanelInMarkReport1.repaint(); // Repaint 
                    }
                    SwingController ctr1 = new SwingController();
                    SwingViewBuilder vb1 = new SwingViewBuilder(ctr1);
                    pdfSubmissionPreview = vb1.buildViewerPanel();
                    ComponentKeyBinding.install(ctr1, pdfSubmissionPreview);
                    Path destinationPath1 = Paths.get(list.get("filePath")); 
                    ctr1.openDocument(String.valueOf(destinationPath1));
                    filePanelInMarkReport1.add(pdfSubmissionPreview);   
                }
            }
        }
    }
    
    private void refreshSubmissionDetailsInMarkReport2(Object value) {
        if (selectSubmissionComboBoxInMarkReport2.getSelectedItem() != null && selectSubmissionComboBoxInMarkReport2.getSelectedItem() != MessageConstant.CONDITION_NO_SUBMISSIONS_UNDER_MODULES) {
            List<Map<String, String>> lists = SubmissionController.getMarked1SubmissionDetailsById(Integer.valueOf((String)value));
            for (Map<String, String> list : lists) {
                if (value.equals(list.get("id"))) {
                    JField33.setText(list.get("studentName"));
                    JField34.setText(list.get("reportType"));
                    JField35.setText(list.get("markingStatus"));
                    reportResultFieldInMarkReport2.setText(list.get("reportMarks"));
                    lecturerCommentFieldInMarkReport2.setText(list.get("lecturerComment"));
                    projectFileNameFieldInMarkReport2.setText(list.get("fileName"));
                    if(pdfSubmissionPreview != null)
                    {
                        filePanelInMarkReport2.remove(pdfSubmissionPreview); // Remove the panel 's' from jPanel
                        filePanelInMarkReport2.revalidate(); // Recalculate layout
                        filePanelInMarkReport2.repaint(); // Repaint 
                    }
                    SwingController ctr2 = new SwingController();
                    SwingViewBuilder vb2 = new SwingViewBuilder(ctr2);
                    pdfSubmissionPreview = vb2.buildViewerPanel();
                    ComponentKeyBinding.install(ctr2, pdfSubmissionPreview);
                    Path destinationPath2 = Paths.get(list.get("filePath")); 
                    ctr2.openDocument(String.valueOf(destinationPath2));
                    filePanelInMarkReport2.add(pdfSubmissionPreview);   
                }
            }
        }
    }
    
    private void refreshSubmissionDetailsInModify(Object value) {
        if (selectSubmissionComboBoxInModify.getSelectedItem() != null && selectSubmissionComboBoxInModify.getSelectedItem() != MessageConstant.CONDITION_NO_SUBMISSIONS_UNDER_MODULES) {
            List<Map<String, String>> lists = SubmissionController.getMarked2SubmissionDetailsById(Integer.valueOf((String)value));
            for (Map<String, String> list : lists) {
                if (value.equals(list.get("id"))) {
                    JField29.setText(list.get("studentName"));
                    JField32.setText(list.get("reportType"));
                    JField30.setText(list.get("markingStatus"));
                    reportResultFieldInModify.setText(list.get("reportMarks"));
                    lecturerCommentFieldInModify.setText(list.get("lecturerComment"));
                    projectFileNameFieldInModify.setText(list.get("fileName"));
                    if(pdfSubmissionPreview != null)
                    {
                        filePanelInModify.remove(pdfSubmissionPreview); // Remove the panel 's' from jPanel
                        filePanelInModify.revalidate(); // Recalculate layout
                        filePanelInModify.repaint(); // Repaint 
                    }
                    SwingController ctr3 = new SwingController();
                    SwingViewBuilder vb3 = new SwingViewBuilder(ctr3);
                    pdfSubmissionPreview = vb3.buildViewerPanel();
                    ComponentKeyBinding.install(ctr3, pdfSubmissionPreview);
                    Path destinationPath3 = Paths.get(list.get("filePath")); 
                    ctr3.openDocument(String.valueOf(destinationPath3));
                    filePanelInModify.add(pdfSubmissionPreview);   
                }
            }
        }
    }
    
    private void selectSubmissionComboBoxInMarkReport1(Object value) {
        selectSubmissionComboBoxInMarkReport1.removeAllItems();
        List<Map<String, String>> lists2 = SubmissionController.getPendingMarkingSubmissionByModuleId(Integer.valueOf((String)value));
        if (lists2.isEmpty()) {
            selectSubmissionComboBoxInMarkReport1.addItem(MessageConstant.CONDITION_NO_SUBMISSIONS_UNDER_MODULES);
            //Need to set the below fields back to default after update
            JField27.setText("Student Name");
            JField31.setText("Report Type");
            JField28.setText("Marking Status");
            reportResultFieldInMarkReport1.setText("Report Result");
            lecturerCommentFieldInMarkReport1.setText("Lecturer Comment");
            projectFileNameFieldInMarkReport1.setText("Project File Name");
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
    
    private void selectSubmissionComboBoxInMarkReport2(Object value) {
        selectSubmissionComboBoxInMarkReport2.removeAllItems();
        List<Map<String, String>> lists2 = SubmissionController.getMarked1SubmissionByModuleId(Integer.valueOf((String)value));
        if (lists2.isEmpty()) {
            selectSubmissionComboBoxInMarkReport2.addItem(MessageConstant.CONDITION_NO_SUBMISSIONS_UNDER_MODULES);
            //Need to set the below fields back to default after update
            JField33.setText("Student Name");
            JField35.setText("Report Type");
            JField34.setText("Marking Status");
            reportResultFieldInMarkReport2.setText("Report Result");
            lecturerCommentFieldInMarkReport2.setText("Lecturer Comment");
            projectFileNameFieldInMarkReport2.setText("Project File Name");
        } 
        else
        {
            for (Map<String, String> list : lists2)
            {
                selectSubmissionComboBoxInMarkReport2.addItem(list.get("id"));
            }
        }
        selectSubmissionComboBoxInMarkReport2.setSelectedIndex(0);
    }
    
    private void selectSubmissionComboBoxInModify(Object value) {
        selectSubmissionComboBoxInModify.removeAllItems();
        List<Map<String, String>> lists2 = SubmissionController.getMarked2SubmissionByModuleId(Integer.valueOf((String)value));
        if (lists2.isEmpty()) {
            selectSubmissionComboBoxInModify.addItem(MessageConstant.CONDITION_NO_SUBMISSIONS_UNDER_MODULES);
            //Need to set the below fields back to default after update
            JField29.setText("Student Name");
            JField32.setText("Report Type");
            JField30.setText("Marking Status");
            reportResultFieldInModify.setText("Report Result");
            lecturerCommentFieldInModify.setText("Lecturer Comment");
            projectFileNameFieldInModify.setText("Project File Name");
        } 
        else
        {
            for (Map<String, String> list : lists2)
            {
                selectSubmissionComboBoxInModify.addItem(list.get("id"));
            }
        }
        selectSubmissionComboBoxInModify.setSelectedIndex(0);
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
        firstMarkerMarkReportBtn = new javax.swing.JLabel();
        firstMarkerModifyMarksBtn = new javax.swing.JLabel();
        secondMarkerMarkReportBtn = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        upcomingSubmissionDueDateTbl = new javax.swing.JTable();
        menuBtn16 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        submissionOverviewTbl = new javax.swing.JTable();
        Panel2 = new javax.swing.JPanel();
        MainTabbedPanel1 = new javax.swing.JTabbedPane();
        Panel10 = new javax.swing.JPanel();
        projectFileNameFieldInMarkReport1 = new javax.swing.JTextField();
        menuBtn44 = new javax.swing.JLabel();
        menuBtn45 = new javax.swing.JLabel();
        selectModuleComboBoxInMarkReport1 = new javax.swing.JComboBox<>();
        updateReportBtnInMarkReport1 = new javax.swing.JLabel();
        JSeparator39 = new javax.swing.JSeparator();
        JField27 = new javax.swing.JTextField();
        menuBtn46 = new javax.swing.JLabel();
        JField31 = new javax.swing.JTextField();
        menuBtn47 = new javax.swing.JLabel();
        menuBtn49 = new javax.swing.JLabel();
        filePanelInMarkReport1 = new javax.swing.JPanel();
        menuBtn51 = new javax.swing.JLabel();
        selectSubmissionComboBoxInMarkReport1 = new javax.swing.JComboBox<>();
        reportResultFieldInMarkReport1 = new javax.swing.JTextField();
        menuBtn43 = new javax.swing.JLabel();
        JField28 = new javax.swing.JTextField();
        menuBtn52 = new javax.swing.JLabel();
        menuBtn54 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        lecturerCommentFieldInMarkReport1 = new javax.swing.JTextArea();
        fetchSubmissionBtnInMarkReport1 = new javax.swing.JLabel();
        JSeparator40 = new javax.swing.JSeparator();
        menuBtn50 = new javax.swing.JLabel();
        JField25 = new javax.swing.JTextField();
        Panel11 = new javax.swing.JPanel();
        projectFileNameFieldInModify = new javax.swing.JTextField();
        menuBtn48 = new javax.swing.JLabel();
        menuBtn53 = new javax.swing.JLabel();
        selectModuleComboBoxInModify = new javax.swing.JComboBox<>();
        updateReportBtnInModify = new javax.swing.JLabel();
        JSeparator41 = new javax.swing.JSeparator();
        JField29 = new javax.swing.JTextField();
        menuBtn55 = new javax.swing.JLabel();
        JField32 = new javax.swing.JTextField();
        menuBtn56 = new javax.swing.JLabel();
        menuBtn57 = new javax.swing.JLabel();
        filePanelInModify = new javax.swing.JPanel();
        menuBtn58 = new javax.swing.JLabel();
        selectSubmissionComboBoxInModify = new javax.swing.JComboBox<>();
        reportResultFieldInModify = new javax.swing.JTextField();
        menuBtn59 = new javax.swing.JLabel();
        JField30 = new javax.swing.JTextField();
        menuBtn60 = new javax.swing.JLabel();
        menuBtn61 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        lecturerCommentFieldInModify = new javax.swing.JTextArea();
        fetchSubmissionBtnInModify = new javax.swing.JLabel();
        JSeparator43 = new javax.swing.JSeparator();
        menuBtn62 = new javax.swing.JLabel();
        JField26 = new javax.swing.JTextField();
        Panel3 = new javax.swing.JPanel();
        MainTabbedPanel2 = new javax.swing.JTabbedPane();
        Panel12 = new javax.swing.JPanel();
        projectFileNameFieldInMarkReport2 = new javax.swing.JTextField();
        menuBtn63 = new javax.swing.JLabel();
        menuBtn64 = new javax.swing.JLabel();
        selectModuleComboBoxInMarkReport2 = new javax.swing.JComboBox<>();
        updateReportBtnInMarkReport2 = new javax.swing.JLabel();
        JSeparator42 = new javax.swing.JSeparator();
        JField33 = new javax.swing.JTextField();
        menuBtn65 = new javax.swing.JLabel();
        JField34 = new javax.swing.JTextField();
        menuBtn66 = new javax.swing.JLabel();
        menuBtn67 = new javax.swing.JLabel();
        filePanelInMarkReport2 = new javax.swing.JPanel();
        menuBtn68 = new javax.swing.JLabel();
        selectSubmissionComboBoxInMarkReport2 = new javax.swing.JComboBox<>();
        reportResultFieldInMarkReport2 = new javax.swing.JTextField();
        menuBtn69 = new javax.swing.JLabel();
        JField35 = new javax.swing.JTextField();
        menuBtn70 = new javax.swing.JLabel();
        menuBtn71 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        lecturerCommentFieldInMarkReport2 = new javax.swing.JTextArea();
        fetchSubmissionBtnInMarkReport2 = new javax.swing.JLabel();
        JSeparator44 = new javax.swing.JSeparator();
        menuBtn72 = new javax.swing.JLabel();
        JField36 = new javax.swing.JTextField();

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

        firstMarkerMarkReportBtn.setBackground(new java.awt.Color(105, 105, 105));
        firstMarkerMarkReportBtn.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        firstMarkerMarkReportBtn.setForeground(new java.awt.Color(1, 1, 1));
        firstMarkerMarkReportBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/edit-orange-24x24.png"))); // NOI18N
        firstMarkerMarkReportBtn.setText("MARK REPORT (FIRST MARKER)");
        firstMarkerMarkReportBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        firstMarkerMarkReportBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                firstMarkerMarkReportBtnMouseClicked(evt);
            }
        });

        firstMarkerModifyMarksBtn.setBackground(new java.awt.Color(105, 105, 105));
        firstMarkerModifyMarksBtn.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        firstMarkerModifyMarksBtn.setForeground(new java.awt.Color(1, 1, 1));
        firstMarkerModifyMarksBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/action-24x24.png"))); // NOI18N
        firstMarkerModifyMarksBtn.setText("MODIFY MARKS");
        firstMarkerModifyMarksBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        firstMarkerModifyMarksBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                firstMarkerModifyMarksBtnMouseClicked(evt);
            }
        });

        secondMarkerMarkReportBtn.setBackground(new java.awt.Color(105, 105, 105));
        secondMarkerMarkReportBtn.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        secondMarkerMarkReportBtn.setForeground(new java.awt.Color(1, 1, 1));
        secondMarkerMarkReportBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/edit-orange-24x24.png"))); // NOI18N
        secondMarkerMarkReportBtn.setText("MARK REPORT (SECOND MARKER)");
        secondMarkerMarkReportBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        secondMarkerMarkReportBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                secondMarkerMarkReportBtnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(0, 19, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(secondMarkerMarkReportBtn)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(firstMarkerMarkReportBtn)
                        .addGap(18, 18, 18)
                        .addComponent(firstMarkerModifyMarksBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(firstMarkerModifyMarksBtn)
                    .addComponent(firstMarkerMarkReportBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(secondMarkerMarkReportBtn)
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

        projectFileNameFieldInMarkReport1.setEditable(false);
        projectFileNameFieldInMarkReport1.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        projectFileNameFieldInMarkReport1.setText("Project File Name");
        projectFileNameFieldInMarkReport1.setBorder(null);
        projectFileNameFieldInMarkReport1.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        projectFileNameFieldInMarkReport1.setForeground(new java.awt.Color(1, 1, 1));
        Panel10.add(projectFileNameFieldInMarkReport1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 0, 300, 40));

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

        updateReportBtnInMarkReport1.setBackground(new java.awt.Color(254, 254, 254));
        updateReportBtnInMarkReport1.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        updateReportBtnInMarkReport1.setForeground(new java.awt.Color(1, 1, 1));
        updateReportBtnInMarkReport1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        updateReportBtnInMarkReport1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/edit-orange-24x24.png"))); // NOI18N
        updateReportBtnInMarkReport1.setText("UPDATE");
        updateReportBtnInMarkReport1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        updateReportBtnInMarkReport1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        updateReportBtnInMarkReport1.setOpaque(true);
        updateReportBtnInMarkReport1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updateReportBtnInMarkReport1MouseClicked(evt);
            }
        });
        Panel10.add(updateReportBtnInMarkReport1, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 530, 170, 35));

        JSeparator39.setForeground(new java.awt.Color(1, 1, 1));
        Panel10.add(JSeparator39, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 570, 170, 10));

        JField27.setEditable(false);
        JField27.setBackground(new java.awt.Color(255, 255, 255));
        JField27.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField27.setForeground(new java.awt.Color(1, 1, 1));
        JField27.setText("Student Name");
        JField27.setBorder(null);
        JField27.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        Panel10.add(JField27, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 300, 35));

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
        Panel10.add(menuBtn47, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 300, 40));

        menuBtn49.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn49.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/user-24x24.png"))); // NOI18N
        menuBtn49.setText("STUDENT NAME");
        menuBtn49.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn49.setOpaque(true);
        Panel10.add(menuBtn49, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 300, 40));

        filePanelInMarkReport1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        filePanelInMarkReport1.setLayout(new java.awt.BorderLayout());
        Panel10.add(filePanelInMarkReport1, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 40, 700, 360));

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
        Panel10.add(selectSubmissionComboBoxInMarkReport1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 300, 35));

        reportResultFieldInMarkReport1.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        reportResultFieldInMarkReport1.setText("Report Result");
        reportResultFieldInMarkReport1.setBackground(new java.awt.Color(255, 255, 255));
        reportResultFieldInMarkReport1.setBorder(null);
        reportResultFieldInMarkReport1.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        reportResultFieldInMarkReport1.setForeground(new java.awt.Color(1, 1, 1));
        reportResultFieldInMarkReport1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reportResultFieldInMarkReport1MouseClicked(evt);
            }
        });
        Panel10.add(reportResultFieldInMarkReport1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 550, 300, 35));

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

        lecturerCommentFieldInMarkReport1.setColumns(20);
        lecturerCommentFieldInMarkReport1.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        lecturerCommentFieldInMarkReport1.setLineWrap(true);
        lecturerCommentFieldInMarkReport1.setRows(5);
        lecturerCommentFieldInMarkReport1.setText("Lecturer Comment");
        lecturerCommentFieldInMarkReport1.setWrapStyleWord(true);
        lecturerCommentFieldInMarkReport1.setBackground(new java.awt.Color(255, 255, 255));
        lecturerCommentFieldInMarkReport1.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        lecturerCommentFieldInMarkReport1.setForeground(new java.awt.Color(0, 0, 0));
        lecturerCommentFieldInMarkReport1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lecturerCommentFieldInMarkReport1MouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(lecturerCommentFieldInMarkReport1);

        Panel10.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 440, 460, 120));

        fetchSubmissionBtnInMarkReport1.setBackground(new java.awt.Color(254, 254, 254));
        fetchSubmissionBtnInMarkReport1.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        fetchSubmissionBtnInMarkReport1.setForeground(new java.awt.Color(1, 1, 1));
        fetchSubmissionBtnInMarkReport1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fetchSubmissionBtnInMarkReport1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/status-24x24.png"))); // NOI18N
        fetchSubmissionBtnInMarkReport1.setText("FETCH SUBMISSION");
        fetchSubmissionBtnInMarkReport1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        fetchSubmissionBtnInMarkReport1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        fetchSubmissionBtnInMarkReport1.setOpaque(true);
        fetchSubmissionBtnInMarkReport1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fetchSubmissionBtnInMarkReport1MouseClicked(evt);
            }
        });
        Panel10.add(fetchSubmissionBtnInMarkReport1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 150, 170, 30));

        JSeparator40.setForeground(new java.awt.Color(1, 1, 1));
        Panel10.add(JSeparator40, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 187, 310, -1));

        menuBtn50.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn50.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn50.setText("MODULE NAME");
        menuBtn50.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn50.setOpaque(true);
        Panel10.add(menuBtn50, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 66, 300, 30));

        JField25.setEditable(false);
        JField25.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField25.setText("Module Name");
        JField25.setBackground(new java.awt.Color(255, 255, 255));
        JField25.setBorder(null);
        JField25.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        JField25.setForeground(new java.awt.Color(1, 1, 1));
        Panel10.add(JField25, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 300, 35));

        MainTabbedPanel1.addTab("Mark Report", Panel10);

        Panel11.setPreferredSize(new java.awt.Dimension(1050, 570));
        Panel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        projectFileNameFieldInModify.setEditable(false);
        projectFileNameFieldInModify.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        projectFileNameFieldInModify.setForeground(new java.awt.Color(1, 1, 1));
        projectFileNameFieldInModify.setText("Project File Name");
        projectFileNameFieldInModify.setBorder(null);
        projectFileNameFieldInModify.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        Panel11.add(projectFileNameFieldInModify, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 0, 300, 40));

        menuBtn48.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn48.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn48.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn48.setText("SELECT MODULE");
        menuBtn48.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn48.setOpaque(true);
        Panel11.add(menuBtn48, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 300, 30));

        menuBtn53.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn53.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/action-24x24.png"))); // NOI18N
        menuBtn53.setText("ACTION :");
        menuBtn53.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn53.setOpaque(true);
        Panel11.add(menuBtn53, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 530, 90, 40));

        selectModuleComboBoxInModify.setBackground(new java.awt.Color(254, 254, 254));
        selectModuleComboBoxInModify.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 12)); // NOI18N
        selectModuleComboBoxInModify.setToolTipText("d");
        selectModuleComboBoxInModify.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        selectModuleComboBoxInModify.setFocusable(false);
        selectModuleComboBoxInModify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectModuleComboBoxInModifyActionPerformed(evt);
            }
        });
        Panel11.add(selectModuleComboBoxInModify, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 300, 35));

        updateReportBtnInModify.setBackground(new java.awt.Color(254, 254, 254));
        updateReportBtnInModify.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        updateReportBtnInModify.setForeground(new java.awt.Color(1, 1, 1));
        updateReportBtnInModify.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        updateReportBtnInModify.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/edit-orange-24x24.png"))); // NOI18N
        updateReportBtnInModify.setText("UPDATE");
        updateReportBtnInModify.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        updateReportBtnInModify.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        updateReportBtnInModify.setOpaque(true);
        updateReportBtnInModify.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updateReportBtnInModifyMouseClicked(evt);
            }
        });
        Panel11.add(updateReportBtnInModify, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 530, 170, 35));

        JSeparator41.setForeground(new java.awt.Color(1, 1, 1));
        Panel11.add(JSeparator41, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 570, 170, 10));

        JField29.setEditable(false);
        JField29.setBackground(new java.awt.Color(255, 255, 255));
        JField29.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField29.setForeground(new java.awt.Color(1, 1, 1));
        JField29.setText("Student Name");
        JField29.setBorder(null);
        JField29.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        Panel11.add(JField29, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 300, 35));

        menuBtn55.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn55.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn55.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn55.setText("REPORT TYPE");
        menuBtn55.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn55.setOpaque(true);
        Panel11.add(menuBtn55, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 300, 40));

        JField32.setEditable(false);
        JField32.setBackground(new java.awt.Color(255, 255, 255));
        JField32.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField32.setForeground(new java.awt.Color(1, 1, 1));
        JField32.setText("Report Type");
        JField32.setBorder(null);
        JField32.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        Panel11.add(JField32, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, 300, 35));

        menuBtn56.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn56.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn56.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn56.setText("SELECT SUBMISSION");
        menuBtn56.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn56.setOpaque(true);
        Panel11.add(menuBtn56, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 300, 40));

        menuBtn57.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn57.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn57.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/user-24x24.png"))); // NOI18N
        menuBtn57.setText("STUDENT NAME");
        menuBtn57.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn57.setOpaque(true);
        Panel11.add(menuBtn57, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 300, 40));

        filePanelInModify.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        filePanelInModify.setLayout(new java.awt.BorderLayout());
        Panel11.add(filePanelInModify, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 40, 700, 360));

        menuBtn58.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn58.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn58.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn58.setText("PROJECT FILE");
        menuBtn58.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn58.setOpaque(true);
        Panel11.add(menuBtn58, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 0, 170, 40));

        selectSubmissionComboBoxInModify.setBackground(new java.awt.Color(254, 254, 254));
        selectSubmissionComboBoxInModify.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 12)); // NOI18N
        selectSubmissionComboBoxInModify.setToolTipText("d");
        selectSubmissionComboBoxInModify.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        selectSubmissionComboBoxInModify.setFocusable(false);
        selectSubmissionComboBoxInModify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectSubmissionComboBoxInModifyActionPerformed(evt);
            }
        });
        Panel11.add(selectSubmissionComboBoxInModify, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 300, 35));

        reportResultFieldInModify.setBackground(new java.awt.Color(255, 255, 255));
        reportResultFieldInModify.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        reportResultFieldInModify.setForeground(new java.awt.Color(1, 1, 1));
        reportResultFieldInModify.setText("Report Result");
        reportResultFieldInModify.setBorder(null);
        reportResultFieldInModify.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        reportResultFieldInModify.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reportResultFieldInModifyMouseClicked(evt);
            }
        });
        Panel11.add(reportResultFieldInModify, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 550, 300, 35));

        menuBtn59.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn59.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn59.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/action-24x24.png"))); // NOI18N
        menuBtn59.setText("REPORT RESULT");
        menuBtn59.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn59.setOpaque(true);
        Panel11.add(menuBtn59, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 510, 300, 40));

        JField30.setEditable(false);
        JField30.setBackground(new java.awt.Color(255, 255, 255));
        JField30.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField30.setForeground(new java.awt.Color(1, 1, 1));
        JField30.setText("Marking Status");
        JField30.setBorder(null);
        JField30.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        Panel11.add(JField30, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, 300, 35));

        menuBtn60.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn60.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn60.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn60.setText("MARKING STATUS");
        menuBtn60.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn60.setOpaque(true);
        Panel11.add(menuBtn60, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, 300, 40));

        menuBtn61.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn61.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn61.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/officer-24x24.png"))); // NOI18N
        menuBtn61.setText("LECTURER COMMENT");
        menuBtn61.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn61.setOpaque(true);
        Panel11.add(menuBtn61, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 400, 300, 40));

        lecturerCommentFieldInModify.setBackground(new java.awt.Color(255, 255, 255));
        lecturerCommentFieldInModify.setColumns(20);
        lecturerCommentFieldInModify.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        lecturerCommentFieldInModify.setForeground(new java.awt.Color(0, 0, 0));
        lecturerCommentFieldInModify.setLineWrap(true);
        lecturerCommentFieldInModify.setRows(5);
        lecturerCommentFieldInModify.setText("Lecturer Comment");
        lecturerCommentFieldInModify.setWrapStyleWord(true);
        lecturerCommentFieldInModify.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        lecturerCommentFieldInModify.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lecturerCommentFieldInModifyMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(lecturerCommentFieldInModify);

        Panel11.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 440, 460, 120));

        fetchSubmissionBtnInModify.setBackground(new java.awt.Color(254, 254, 254));
        fetchSubmissionBtnInModify.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        fetchSubmissionBtnInModify.setForeground(new java.awt.Color(1, 1, 1));
        fetchSubmissionBtnInModify.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fetchSubmissionBtnInModify.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/status-24x24.png"))); // NOI18N
        fetchSubmissionBtnInModify.setText("FETCH SUBMISSION");
        fetchSubmissionBtnInModify.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        fetchSubmissionBtnInModify.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        fetchSubmissionBtnInModify.setOpaque(true);
        fetchSubmissionBtnInModify.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fetchSubmissionBtnInModifyMouseClicked(evt);
            }
        });
        Panel11.add(fetchSubmissionBtnInModify, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 150, 170, 30));

        JSeparator43.setForeground(new java.awt.Color(1, 1, 1));
        Panel11.add(JSeparator43, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 310, 10));

        menuBtn62.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn62.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn62.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn62.setText("MODULE NAME");
        menuBtn62.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn62.setOpaque(true);
        Panel11.add(menuBtn62, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 66, 300, 30));

        JField26.setEditable(false);
        JField26.setBackground(new java.awt.Color(255, 255, 255));
        JField26.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField26.setForeground(new java.awt.Color(1, 1, 1));
        JField26.setText("Module Name");
        JField26.setBorder(null);
        JField26.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        Panel11.add(JField26, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 300, 35));

        MainTabbedPanel1.addTab("Modify Report Marks", Panel11);

        Panel2.add(MainTabbedPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 630));

        MainTabbedPanel.addTab("Manage Submission (First Marker)", Panel2);

        Panel3.setPreferredSize(new java.awt.Dimension(1050, 570));
        Panel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        MainTabbedPanel2.setBackground(new java.awt.Color(230, 230, 230));
        MainTabbedPanel2.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N

        Panel12.setPreferredSize(new java.awt.Dimension(1050, 570));
        Panel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        projectFileNameFieldInMarkReport2.setEditable(false);
        projectFileNameFieldInMarkReport2.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        projectFileNameFieldInMarkReport2.setForeground(new java.awt.Color(1, 1, 1));
        projectFileNameFieldInMarkReport2.setText("Project File Name");
        projectFileNameFieldInMarkReport2.setBorder(null);
        projectFileNameFieldInMarkReport2.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        Panel12.add(projectFileNameFieldInMarkReport2, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 0, 300, 40));

        menuBtn63.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn63.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn63.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn63.setText("SELECT MODULE");
        menuBtn63.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn63.setOpaque(true);
        Panel12.add(menuBtn63, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 300, 30));

        menuBtn64.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn64.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn64.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/action-24x24.png"))); // NOI18N
        menuBtn64.setText("ACTION :");
        menuBtn64.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn64.setOpaque(true);
        Panel12.add(menuBtn64, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 530, 90, 40));

        selectModuleComboBoxInMarkReport2.setBackground(new java.awt.Color(254, 254, 254));
        selectModuleComboBoxInMarkReport2.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 12)); // NOI18N
        selectModuleComboBoxInMarkReport2.setToolTipText("d");
        selectModuleComboBoxInMarkReport2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        selectModuleComboBoxInMarkReport2.setFocusable(false);
        selectModuleComboBoxInMarkReport2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectModuleComboBoxInMarkReport2ActionPerformed(evt);
            }
        });
        Panel12.add(selectModuleComboBoxInMarkReport2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 300, 35));

        updateReportBtnInMarkReport2.setBackground(new java.awt.Color(254, 254, 254));
        updateReportBtnInMarkReport2.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        updateReportBtnInMarkReport2.setForeground(new java.awt.Color(1, 1, 1));
        updateReportBtnInMarkReport2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        updateReportBtnInMarkReport2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/edit-orange-24x24.png"))); // NOI18N
        updateReportBtnInMarkReport2.setText("UPDATE");
        updateReportBtnInMarkReport2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        updateReportBtnInMarkReport2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        updateReportBtnInMarkReport2.setOpaque(true);
        updateReportBtnInMarkReport2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updateReportBtnInMarkReport2MouseClicked(evt);
            }
        });
        Panel12.add(updateReportBtnInMarkReport2, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 530, 170, 35));

        JSeparator42.setForeground(new java.awt.Color(1, 1, 1));
        Panel12.add(JSeparator42, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 570, 170, 10));

        JField33.setEditable(false);
        JField33.setBackground(new java.awt.Color(255, 255, 255));
        JField33.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField33.setForeground(new java.awt.Color(1, 1, 1));
        JField33.setText("Student Name");
        JField33.setBorder(null);
        JField33.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        Panel12.add(JField33, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 300, 35));

        menuBtn65.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn65.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn65.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn65.setText("REPORT TYPE");
        menuBtn65.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn65.setOpaque(true);
        Panel12.add(menuBtn65, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 300, 40));

        JField34.setEditable(false);
        JField34.setBackground(new java.awt.Color(255, 255, 255));
        JField34.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField34.setForeground(new java.awt.Color(1, 1, 1));
        JField34.setText("Report Type");
        JField34.setBorder(null);
        JField34.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        Panel12.add(JField34, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, 300, 35));

        menuBtn66.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn66.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn66.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn66.setText("SELECT SUBMISSION");
        menuBtn66.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn66.setOpaque(true);
        Panel12.add(menuBtn66, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 300, 40));

        menuBtn67.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn67.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn67.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/user-24x24.png"))); // NOI18N
        menuBtn67.setText("STUDENT NAME");
        menuBtn67.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn67.setOpaque(true);
        Panel12.add(menuBtn67, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 300, 40));

        filePanelInMarkReport2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        filePanelInMarkReport2.setLayout(new java.awt.BorderLayout());
        Panel12.add(filePanelInMarkReport2, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 40, 700, 360));

        menuBtn68.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn68.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn68.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bill-24x24.png"))); // NOI18N
        menuBtn68.setText("PROJECT FILE");
        menuBtn68.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn68.setOpaque(true);
        Panel12.add(menuBtn68, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 0, 170, 40));

        selectSubmissionComboBoxInMarkReport2.setBackground(new java.awt.Color(254, 254, 254));
        selectSubmissionComboBoxInMarkReport2.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 12)); // NOI18N
        selectSubmissionComboBoxInMarkReport2.setToolTipText("d");
        selectSubmissionComboBoxInMarkReport2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        selectSubmissionComboBoxInMarkReport2.setFocusable(false);
        selectSubmissionComboBoxInMarkReport2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectSubmissionComboBoxInMarkReport2ActionPerformed(evt);
            }
        });
        Panel12.add(selectSubmissionComboBoxInMarkReport2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 300, 35));

        reportResultFieldInMarkReport2.setBackground(new java.awt.Color(255, 255, 255));
        reportResultFieldInMarkReport2.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        reportResultFieldInMarkReport2.setForeground(new java.awt.Color(1, 1, 1));
        reportResultFieldInMarkReport2.setText("Report Result");
        reportResultFieldInMarkReport2.setBorder(null);
        reportResultFieldInMarkReport2.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        reportResultFieldInMarkReport2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reportResultFieldInMarkReport2MouseClicked(evt);
            }
        });
        Panel12.add(reportResultFieldInMarkReport2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 550, 300, 35));

        menuBtn69.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn69.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn69.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/action-24x24.png"))); // NOI18N
        menuBtn69.setText("REPORT RESULT");
        menuBtn69.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn69.setOpaque(true);
        Panel12.add(menuBtn69, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 510, 300, 40));

        JField35.setEditable(false);
        JField35.setBackground(new java.awt.Color(255, 255, 255));
        JField35.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField35.setForeground(new java.awt.Color(1, 1, 1));
        JField35.setText("Marking Status");
        JField35.setBorder(null);
        JField35.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        Panel12.add(JField35, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, 300, 35));

        menuBtn70.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn70.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn70.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn70.setText("MARKING STATUS");
        menuBtn70.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn70.setOpaque(true);
        Panel12.add(menuBtn70, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, 300, 40));

        menuBtn71.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn71.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn71.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/officer-24x24.png"))); // NOI18N
        menuBtn71.setText("LECTURER COMMENT");
        menuBtn71.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn71.setOpaque(true);
        Panel12.add(menuBtn71, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 400, 300, 40));

        lecturerCommentFieldInMarkReport2.setBackground(new java.awt.Color(255, 255, 255));
        lecturerCommentFieldInMarkReport2.setColumns(20);
        lecturerCommentFieldInMarkReport2.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        lecturerCommentFieldInMarkReport2.setForeground(new java.awt.Color(0, 0, 0));
        lecturerCommentFieldInMarkReport2.setLineWrap(true);
        lecturerCommentFieldInMarkReport2.setRows(5);
        lecturerCommentFieldInMarkReport2.setText("Lecturer Comment");
        lecturerCommentFieldInMarkReport2.setWrapStyleWord(true);
        lecturerCommentFieldInMarkReport2.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        lecturerCommentFieldInMarkReport2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lecturerCommentFieldInMarkReport2MouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(lecturerCommentFieldInMarkReport2);

        Panel12.add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 440, 460, 120));

        fetchSubmissionBtnInMarkReport2.setBackground(new java.awt.Color(254, 254, 254));
        fetchSubmissionBtnInMarkReport2.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        fetchSubmissionBtnInMarkReport2.setForeground(new java.awt.Color(1, 1, 1));
        fetchSubmissionBtnInMarkReport2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fetchSubmissionBtnInMarkReport2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/status-24x24.png"))); // NOI18N
        fetchSubmissionBtnInMarkReport2.setText("FETCH SUBMISSION");
        fetchSubmissionBtnInMarkReport2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        fetchSubmissionBtnInMarkReport2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        fetchSubmissionBtnInMarkReport2.setOpaque(true);
        fetchSubmissionBtnInMarkReport2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fetchSubmissionBtnInMarkReport2MouseClicked(evt);
            }
        });
        Panel12.add(fetchSubmissionBtnInMarkReport2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 150, 170, 30));

        JSeparator44.setForeground(new java.awt.Color(1, 1, 1));
        Panel12.add(JSeparator44, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 310, 10));

        menuBtn72.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 14)); // NOI18N
        menuBtn72.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menuBtn72.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/quantity-24x24.png"))); // NOI18N
        menuBtn72.setText("MODULE NAME");
        menuBtn72.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuBtn72.setOpaque(true);
        Panel12.add(menuBtn72, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 66, 300, 30));

        JField36.setEditable(false);
        JField36.setBackground(new java.awt.Color(255, 255, 255));
        JField36.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        JField36.setForeground(new java.awt.Color(1, 1, 1));
        JField36.setText("Module Name");
        JField36.setBorder(null);
        JField36.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        Panel12.add(JField36, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 300, 35));

        MainTabbedPanel2.addTab("Mark Report", Panel12);

        Panel3.add(MainTabbedPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 630));

        MainTabbedPanel.addTab("Manage Submission (Second Marker)", Panel3);

        getContentPane().add(MainTabbedPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 660));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void firstMarkerMarkReportBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_firstMarkerMarkReportBtnMouseClicked
        MainTabbedPanel.setSelectedIndex(1);
        MainTabbedPanel1.setSelectedIndex(0);
    }//GEN-LAST:event_firstMarkerMarkReportBtnMouseClicked

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

    private void firstMarkerModifyMarksBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_firstMarkerModifyMarksBtnMouseClicked
        MainTabbedPanel.setSelectedIndex(1);
        MainTabbedPanel1.setSelectedIndex(1);
    }//GEN-LAST:event_firstMarkerModifyMarksBtnMouseClicked

    private void firstMarkerPendingMarkingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_firstMarkerPendingMarkingMouseClicked
        submissionComboBox.setSelectedIndex(1);
    }//GEN-LAST:event_firstMarkerPendingMarkingMouseClicked

    private void secondMarkerPendingMarkingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_secondMarkerPendingMarkingMouseClicked
        submissionComboBox.setSelectedIndex(2);
    }//GEN-LAST:event_secondMarkerPendingMarkingMouseClicked

    private void secondMarkerMarkReportBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_secondMarkerMarkReportBtnMouseClicked
        MainTabbedPanel.setSelectedIndex(2);
        MainTabbedPanel1.setSelectedIndex(0);
    }//GEN-LAST:event_secondMarkerMarkReportBtnMouseClicked

    private void fetchSubmissionBtnInMarkReport1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fetchSubmissionBtnInMarkReport1MouseClicked
        selectSubmissionComboBoxInMarkReport1(selectModuleComboBoxInMarkReport1.getSelectedItem());
        refreshSubmissionDetailsInMarkReport1(selectSubmissionComboBoxInMarkReport1.getSelectedItem());
    }//GEN-LAST:event_fetchSubmissionBtnInMarkReport1MouseClicked

    private void lecturerCommentFieldInMarkReport1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lecturerCommentFieldInMarkReport1MouseClicked
        if (lecturerCommentFieldInMarkReport1.getText().equals("Lecturer Comment")) {
            lecturerCommentFieldInMarkReport1.setText("");
        }
    }//GEN-LAST:event_lecturerCommentFieldInMarkReport1MouseClicked

    private void reportResultFieldInMarkReport1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportResultFieldInMarkReport1MouseClicked
        if (reportResultFieldInMarkReport1.getText().equals("Report Result") || reportResultFieldInMarkReport1.getText().equals("0.0")) {
            reportResultFieldInMarkReport1.setText("");
        }
    }//GEN-LAST:event_reportResultFieldInMarkReport1MouseClicked

    private void selectSubmissionComboBoxInMarkReport1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectSubmissionComboBoxInMarkReport1ActionPerformed
        refreshSubmissionDetailsInMarkReport1(selectSubmissionComboBoxInMarkReport1.getSelectedItem());
    }//GEN-LAST:event_selectSubmissionComboBoxInMarkReport1ActionPerformed

    private void updateReportBtnInMarkReport1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateReportBtnInMarkReport1MouseClicked
        if (selectModuleComboBoxInMarkReport1.getSelectedItem().equals(MessageConstant.CONDITION_NO_MODULES_UNDER_LECTURER))
        {
            //When there is no modules assigned to the lecturer
            Dialog.ErrorDialog(MessageConstant.ERROR_NO_MODULES_SELECTED);
            return;
        }
        if(!reportResultFieldInMarkReport1.getText().equals("Report Result") && (!lecturerCommentFieldInMarkReport1.getText().equals("") && !lecturerCommentFieldInMarkReport1.getText().equals("Lecturer Comment")))
        {
            //If the lecturer has entered report marks and comment
            String reportMarksInput=reportResultFieldInMarkReport1.getText();
            boolean flag=checkIsDouble(reportMarksInput);
            if(flag == true)
            {
                Double reportMarks=Double.valueOf(reportResultFieldInMarkReport1.getText());
                if(reportMarks >= 0 && reportMarks <= 100)
                {
                    if(Dialog.ConfirmationDialog(MessageConstant.WARNING_MARK_REPORT))
                    {
                        SubmissionController.updateSubmissionMarksByIdForFirstMarker(Integer.valueOf((String)selectSubmissionComboBoxInMarkReport1.getSelectedItem()),Double.valueOf(reportResultFieldInMarkReport1.getText()),lecturerCommentFieldInMarkReport1.getText());
                        Dialog.SuccessDialog(MessageConstant.SUCCESS_UPDATED_SUBMISSION_MARKS);
                        filePanelInMarkReport1.remove(pdfSubmissionPreview); // Remove the panel 's' from jPanel
                        filePanelInMarkReport1.revalidate(); // Recalculate layout
                        filePanelInMarkReport1.repaint(); // Repaint
                        selectSubmissionComboBoxInMarkReport1(selectModuleComboBoxInMarkReport1.getSelectedItem());
                        refreshSubmissionDetailsInMarkReport1(selectSubmissionComboBoxInMarkReport1.getSelectedItem());
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
        }

        else if(selectSubmissionComboBoxInMarkReport1.getSelectedItem() != null)
        {
            //When lecturer has fetched modules, but there is no submission
            Dialog.ErrorDialog(MessageConstant.ERROR_NO_SUBMISSION_SELECTED);
        }

        else
        {
            //When the lecturer first time going in the page or switching modules, and havent fetch submission, and clicked the update button
            Dialog.ErrorDialog(MessageConstant.ERROR_NO_MODULES_SELECTED);
        }
    }//GEN-LAST:event_updateReportBtnInMarkReport1MouseClicked

    private void selectModuleComboBoxInMarkReport1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectModuleComboBoxInMarkReport1ActionPerformed
        refreshSelectModuleInMarkReport1(selectModuleComboBoxInMarkReport1.getSelectedItem());
    }//GEN-LAST:event_selectModuleComboBoxInMarkReport1ActionPerformed

    private void selectModuleComboBoxInModifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectModuleComboBoxInModifyActionPerformed
        refreshSelectModuleInModify(selectModuleComboBoxInModify.getSelectedItem());
    }//GEN-LAST:event_selectModuleComboBoxInModifyActionPerformed

    private void updateReportBtnInModifyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateReportBtnInModifyMouseClicked
        if (selectModuleComboBoxInModify.getSelectedItem().equals(MessageConstant.CONDITION_NO_MODULES_UNDER_LECTURER))
        {
            //When there is no modules assigned to the lecturer
            Dialog.ErrorDialog(MessageConstant.ERROR_NO_MODULES_SELECTED);
            return;
        }
        if(!reportResultFieldInModify.getText().equals("Report Result") && (!lecturerCommentFieldInModify.getText().equals("") && !lecturerCommentFieldInModify.getText().equals("Lecturer Comment")))
        {
            //If the lecturer has entered report marks and comment
            String reportMarksInput=reportResultFieldInModify.getText();
            boolean flag=checkIsDouble(reportMarksInput);
            if(flag == true)
            {
                Double reportMarks=Double.valueOf(reportResultFieldInModify.getText());
                if(reportMarks >= 0 && reportMarks <= 100)
                {
                    if(Dialog.ConfirmationDialog(MessageConstant.WARNING_MARK_REPORT))
                    {
                        SubmissionController.updateSubmissionMarksByIdForFirstMarker(Integer.valueOf((String)selectSubmissionComboBoxInModify.getSelectedItem()),Double.valueOf(reportResultFieldInModify.getText()),lecturerCommentFieldInModify.getText());
                        Dialog.SuccessDialog(MessageConstant.SUCCESS_UPDATED_SUBMISSION_MARKS);
                        filePanelInModify.remove(pdfSubmissionPreview); // Remove the panel 's' from jPanel
                        filePanelInModify.revalidate(); // Recalculate layout
                        filePanelInModify.repaint(); // Repaint
                        selectSubmissionComboBoxInModify(selectModuleComboBoxInModify.getSelectedItem());
                        refreshSubmissionDetailsInModify(selectSubmissionComboBoxInModify.getSelectedItem());
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
        }

        else if(selectSubmissionComboBoxInModify.getSelectedItem() != null)
        {
            //When lecturer has fetched modules, but there is no submission
            Dialog.ErrorDialog(MessageConstant.ERROR_NO_SUBMISSION_SELECTED);
        }

        else
        {
            //When the lecturer first time going in the page or switching modules, and havent fetch submission, and clicked the update button
            Dialog.ErrorDialog(MessageConstant.ERROR_NO_MODULES_SELECTED);
        }
    }//GEN-LAST:event_updateReportBtnInModifyMouseClicked

    private void selectSubmissionComboBoxInModifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectSubmissionComboBoxInModifyActionPerformed
        refreshSubmissionDetailsInModify(selectSubmissionComboBoxInModify.getSelectedItem());
    }//GEN-LAST:event_selectSubmissionComboBoxInModifyActionPerformed

    private void reportResultFieldInModifyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportResultFieldInModifyMouseClicked
        if (reportResultFieldInModify.getText().equals("Report Result") || reportResultFieldInModify.getText().equals("0.0")) {
            reportResultFieldInModify.setText("");
        }
    }//GEN-LAST:event_reportResultFieldInModifyMouseClicked

    private void lecturerCommentFieldInModifyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lecturerCommentFieldInModifyMouseClicked
        if (lecturerCommentFieldInModify.getText().equals("Lecturer Comment")) {
            lecturerCommentFieldInModify.setText("");
        }
    }//GEN-LAST:event_lecturerCommentFieldInModifyMouseClicked

    private void fetchSubmissionBtnInModifyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fetchSubmissionBtnInModifyMouseClicked
        selectSubmissionComboBoxInModify(selectModuleComboBoxInModify.getSelectedItem());
        refreshSubmissionDetailsInModify(selectSubmissionComboBoxInModify.getSelectedItem());
    }//GEN-LAST:event_fetchSubmissionBtnInModifyMouseClicked

    private void fetchSubmissionBtnInMarkReport2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fetchSubmissionBtnInMarkReport2MouseClicked
        selectSubmissionComboBoxInMarkReport2(selectModuleComboBoxInMarkReport2.getSelectedItem());
        refreshSubmissionDetailsInMarkReport2(selectSubmissionComboBoxInMarkReport2.getSelectedItem());
    }//GEN-LAST:event_fetchSubmissionBtnInMarkReport2MouseClicked

    private void lecturerCommentFieldInMarkReport2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lecturerCommentFieldInMarkReport2MouseClicked
        if (lecturerCommentFieldInMarkReport2.getText().equals("Lecturer Comment")) {
            lecturerCommentFieldInMarkReport2.setText("");
        }
    }//GEN-LAST:event_lecturerCommentFieldInMarkReport2MouseClicked

    private void reportResultFieldInMarkReport2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportResultFieldInMarkReport2MouseClicked
        if (reportResultFieldInMarkReport2.getText().equals("Report Result") || reportResultFieldInMarkReport2.getText().equals("0.0")) {
            reportResultFieldInMarkReport2.setText("");
        }
    }//GEN-LAST:event_reportResultFieldInMarkReport2MouseClicked

    private void selectSubmissionComboBoxInMarkReport2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectSubmissionComboBoxInMarkReport2ActionPerformed
        refreshSubmissionDetailsInMarkReport2(selectSubmissionComboBoxInMarkReport2.getSelectedItem());
    }//GEN-LAST:event_selectSubmissionComboBoxInMarkReport2ActionPerformed

    private void updateReportBtnInMarkReport2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateReportBtnInMarkReport2MouseClicked
        if (selectModuleComboBoxInMarkReport2.getSelectedItem().equals(MessageConstant.CONDITION_NO_MODULES_UNDER_LECTURER))
        {
            //When there is no modules assigned to the lecturer
            Dialog.ErrorDialog(MessageConstant.ERROR_NO_MODULES_SELECTED);
            return;
        }
        if(!reportResultFieldInMarkReport2.getText().equals("Report Result") && (!lecturerCommentFieldInMarkReport2.getText().equals("") && !lecturerCommentFieldInMarkReport2.getText().equals("Lecturer Comment")))
        {
            //If the lecturer has entered report marks and comment
            String reportMarksInput=reportResultFieldInMarkReport2.getText();
            boolean flag=checkIsDouble(reportMarksInput);
            if(flag == true)
            {
                Double reportMarks=Double.valueOf(reportResultFieldInMarkReport2.getText());
                if(reportMarks >= 0 && reportMarks <= 100)
                {
                    if(Dialog.ConfirmationDialog(MessageConstant.WARNING_MARK_REPORT))
                    {
                        SubmissionController.updateSubmissionMarksByIdForSecondMarker(Integer.valueOf((String)selectSubmissionComboBoxInMarkReport2.getSelectedItem()),Double.valueOf(reportResultFieldInMarkReport2.getText()),lecturerCommentFieldInMarkReport2.getText());
                        Dialog.SuccessDialog(MessageConstant.SUCCESS_UPDATED_SUBMISSION_MARKS);
                        filePanelInMarkReport2.remove(pdfSubmissionPreview); // Remove the panel 's' from jPanel
                        filePanelInMarkReport2.revalidate(); // Recalculate layout
                        filePanelInMarkReport2.repaint(); // Repaint
                        selectSubmissionComboBoxInMarkReport2(selectModuleComboBoxInMarkReport2.getSelectedItem());
                        refreshSubmissionDetailsInMarkReport2(selectSubmissionComboBoxInMarkReport2.getSelectedItem());
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
        }

        else if(selectSubmissionComboBoxInMarkReport2.getSelectedItem() != null)
        {
            //When lecturer has fetched modules, but there is no submission
            Dialog.ErrorDialog(MessageConstant.ERROR_NO_SUBMISSION_SELECTED);
        }

        else
        {
            //When the lecturer first time going in the page or switching modules, and havent fetch submission, and clicked the update button
            Dialog.ErrorDialog(MessageConstant.ERROR_NO_MODULES_SELECTED);
        }
    }//GEN-LAST:event_updateReportBtnInMarkReport2MouseClicked

    private void selectModuleComboBoxInMarkReport2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectModuleComboBoxInMarkReport2ActionPerformed
        refreshSelectModuleInMarkReport2(selectModuleComboBoxInMarkReport2.getSelectedItem());
    }//GEN-LAST:event_selectModuleComboBoxInMarkReport2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField JField25;
    private javax.swing.JTextField JField26;
    private javax.swing.JTextField JField27;
    private javax.swing.JTextField JField28;
    private javax.swing.JTextField JField29;
    private javax.swing.JTextField JField30;
    private javax.swing.JTextField JField31;
    private javax.swing.JTextField JField32;
    private javax.swing.JTextField JField33;
    private javax.swing.JTextField JField34;
    private javax.swing.JTextField JField35;
    private javax.swing.JTextField JField36;
    private javax.swing.JSeparator JSeparator39;
    private javax.swing.JSeparator JSeparator40;
    private javax.swing.JSeparator JSeparator41;
    private javax.swing.JSeparator JSeparator42;
    private javax.swing.JSeparator JSeparator43;
    private javax.swing.JSeparator JSeparator44;
    private javax.swing.JTabbedPane MainTabbedPanel;
    private javax.swing.JTabbedPane MainTabbedPanel1;
    private javax.swing.JTabbedPane MainTabbedPanel2;
    private javax.swing.JPanel Panel1;
    private javax.swing.JPanel Panel10;
    private javax.swing.JPanel Panel11;
    private javax.swing.JPanel Panel12;
    private javax.swing.JPanel Panel2;
    private javax.swing.JPanel Panel3;
    private javax.swing.JLabel fetchSubmissionBtnInMarkReport1;
    private javax.swing.JLabel fetchSubmissionBtnInMarkReport2;
    private javax.swing.JLabel fetchSubmissionBtnInModify;
    private javax.swing.JPanel filePanelInMarkReport1;
    private javax.swing.JPanel filePanelInMarkReport2;
    private javax.swing.JPanel filePanelInModify;
    private javax.swing.JLabel firstMarkerMarkReportBtn;
    private javax.swing.JLabel firstMarkerModifyMarksBtn;
    private static javax.swing.JLabel firstMarkerPendingMarking;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextArea lecturerCommentFieldInMarkReport1;
    private javax.swing.JTextArea lecturerCommentFieldInMarkReport2;
    private javax.swing.JTextArea lecturerCommentFieldInModify;
    private javax.swing.JLabel menuBtn11;
    private javax.swing.JLabel menuBtn14;
    private javax.swing.JLabel menuBtn15;
    private javax.swing.JLabel menuBtn16;
    private javax.swing.JLabel menuBtn3;
    private javax.swing.JLabel menuBtn4;
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
    private javax.swing.JLabel menuBtn53;
    private javax.swing.JLabel menuBtn54;
    private javax.swing.JLabel menuBtn55;
    private javax.swing.JLabel menuBtn56;
    private javax.swing.JLabel menuBtn57;
    private javax.swing.JLabel menuBtn58;
    private javax.swing.JLabel menuBtn59;
    private javax.swing.JLabel menuBtn60;
    private javax.swing.JLabel menuBtn61;
    private javax.swing.JLabel menuBtn62;
    private javax.swing.JLabel menuBtn63;
    private javax.swing.JLabel menuBtn64;
    private javax.swing.JLabel menuBtn65;
    private javax.swing.JLabel menuBtn66;
    private javax.swing.JLabel menuBtn67;
    private javax.swing.JLabel menuBtn68;
    private javax.swing.JLabel menuBtn69;
    private javax.swing.JLabel menuBtn70;
    private javax.swing.JLabel menuBtn71;
    private javax.swing.JLabel menuBtn72;
    private javax.swing.JTextField projectFileNameFieldInMarkReport1;
    private javax.swing.JTextField projectFileNameFieldInMarkReport2;
    private javax.swing.JTextField projectFileNameFieldInModify;
    private javax.swing.JTextField reportResultFieldInMarkReport1;
    private javax.swing.JTextField reportResultFieldInMarkReport2;
    private javax.swing.JTextField reportResultFieldInModify;
    private javax.swing.JLabel searchBtn;
    private javax.swing.JTextField searchFieldInOverview;
    private javax.swing.JLabel secondMarkerMarkReportBtn;
    private static javax.swing.JLabel secondMarkerPendingMarking;
    private static javax.swing.JComboBox<String> selectModuleComboBoxInMarkReport1;
    private static javax.swing.JComboBox<String> selectModuleComboBoxInMarkReport2;
    private static javax.swing.JComboBox<String> selectModuleComboBoxInModify;
    private static javax.swing.JComboBox<String> selectSubmissionComboBoxInMarkReport1;
    private static javax.swing.JComboBox<String> selectSubmissionComboBoxInMarkReport2;
    private static javax.swing.JComboBox<String> selectSubmissionComboBoxInModify;
    private static javax.swing.JComboBox<String> submissionComboBox;
    private javax.swing.JTable submissionOverviewTbl;
    private javax.swing.JTable upcomingSubmissionDueDateTbl;
    private javax.swing.JLabel updateReportBtnInMarkReport1;
    private javax.swing.JLabel updateReportBtnInMarkReport2;
    private javax.swing.JLabel updateReportBtnInModify;
    // End of variables declaration//GEN-END:variables
}
