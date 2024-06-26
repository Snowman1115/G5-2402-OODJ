package com.project.ui.authentication;

import com.project.common.constants.UserRoleType;
import com.project.common.utils.PropertiesReader;
import com.project.controller.UserAccountController;
import com.project.ui.administrator.AdminGui;
import com.project.ui.lecturer.LecturerGui;
import com.project.ui.project_manager.ProjectManagerGui;
import com.project.ui.student.StudentGui;
import java.awt.Color;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author chanh
 */
@Slf4j
public class LoginGui extends javax.swing.JFrame {

    public static boolean isEnabled;
    /**
     * Creates new form LoginGui
     */
    public LoginGui() {
        setTitle("Project Management System - Login");
        setVisible(true);
        initComponents();
        MainPanel.setVisible(false);
        pmsVersionLabel.setText(PropertiesReader.getProperty("PMSVersion"));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LeftPanel = new javax.swing.JPanel();
        LogoTitle_1 = new javax.swing.JLabel();
        LogoTitle_2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        RightPanel = new javax.swing.JPanel();
        RoyaleLabel = new javax.swing.JLabel();
        WelcomeToRoyalelHotelLabel = new javax.swing.JLabel();
        InputFieldPanel = new javax.swing.JPanel();
        usernameLabel = new javax.swing.JLabel();
        usernameField = new javax.swing.JTextField();
        usernameSeparator = new javax.swing.JSeparator();
        passwordLabel = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        passwordSeparator = new javax.swing.JSeparator();
        showPasswordBtn = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        loginBtn = new javax.swing.JButton();
        loginBtnSeperator = new javax.swing.JSeparator();
        ExitBtn = new javax.swing.JButton();
        forgetPasswordBtn = new javax.swing.JLabel();
        pmsVersionLabel = new javax.swing.JLabel();
        MainPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LeftPanel.setBackground(new java.awt.Color(164, 196, 181));
        LeftPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LogoTitle_1.setBackground(new java.awt.Color(255, 255, 255));
        LogoTitle_1.setFont(new java.awt.Font("Alibaba PuHuiTi H", 1, 36)); // NOI18N
        LogoTitle_1.setForeground(new java.awt.Color(255, 255, 255));
        LogoTitle_1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LogoTitle_1.setText("PROJECT");
        LeftPanel.add(LogoTitle_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 480, 700, -1));

        LogoTitle_2.setBackground(new java.awt.Color(255, 255, 255));
        LogoTitle_2.setFont(new java.awt.Font("Alibaba PuHuiTi H", 1, 36)); // NOI18N
        LogoTitle_2.setForeground(new java.awt.Color(255, 255, 255));
        LogoTitle_2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LogoTitle_2.setText("MANAGEMENT SYSTEM");
        LeftPanel.add(LogoTitle_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 540, 700, -1));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/PMS Logo Large.png"))); // NOI18N
        LeftPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 700, -1));

        getContentPane().add(LeftPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 720));

        RightPanel.setBackground(new java.awt.Color(255, 255, 255));
        RightPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        RoyaleLabel.setBackground(new java.awt.Color(105, 105, 105));
        RoyaleLabel.setFont(new java.awt.Font("Tempus Sans ITC", 1, 36)); // NOI18N
        RoyaleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RoyaleLabel.setText("Academic Guidance Hub");
        RightPanel.add(RoyaleLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 580, -1));

        WelcomeToRoyalelHotelLabel.setBackground(new java.awt.Color(105, 105, 105));
        WelcomeToRoyalelHotelLabel.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 20)); // NOI18N
        WelcomeToRoyalelHotelLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        WelcomeToRoyalelHotelLabel.setText("Welcome to");
        RightPanel.add(WelcomeToRoyalelHotelLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 580, -1));

        InputFieldPanel.setBackground(new java.awt.Color(255, 255, 255));
        InputFieldPanel.setForeground(new java.awt.Color(1, 1, 1));

        usernameLabel.setBackground(new java.awt.Color(105, 105, 105));
        usernameLabel.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 16)); // NOI18N
        usernameLabel.setForeground(new java.awt.Color(1, 1, 1));
        usernameLabel.setText("USERNAME");

        usernameField.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 15)); // NOI18N
        usernameField.setForeground(new java.awt.Color(1, 1, 1));
        usernameField.setText("Enter Your User Name");
        usernameField.setBorder(null);
        usernameField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                usernameFieldFocusGained(evt);
            }
        });

        usernameSeparator.setForeground(new java.awt.Color(1, 1, 1));

        passwordLabel.setBackground(new java.awt.Color(105, 105, 105));
        passwordLabel.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 16)); // NOI18N
        passwordLabel.setText("PASSWORD");

        passwordField.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 15)); // NOI18N
        passwordField.setForeground(new java.awt.Color(1, 1, 1));
        passwordField.setText("Enter Your Password");
        passwordField.setBorder(null);
        passwordField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                passwordFieldFocusGained(evt);
            }
        });

        passwordSeparator.setForeground(new java.awt.Color(1, 1, 1));

        showPasswordBtn.setBackground(new java.awt.Color(255, 255, 255));
        showPasswordBtn.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        showPasswordBtn.setForeground(new java.awt.Color(1, 1, 1));
        showPasswordBtn.setText("Show Password");
        showPasswordBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showPasswordBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout InputFieldPanelLayout = new javax.swing.GroupLayout(InputFieldPanel);
        InputFieldPanel.setLayout(InputFieldPanelLayout);
        InputFieldPanelLayout.setHorizontalGroup(
            InputFieldPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, InputFieldPanelLayout.createSequentialGroup()
                .addGroup(InputFieldPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(InputFieldPanelLayout.createSequentialGroup()
                        .addContainerGap(327, Short.MAX_VALUE)
                        .addComponent(showPasswordBtn))
                    .addGroup(InputFieldPanelLayout.createSequentialGroup()
                        .addGap(31, 140, Short.MAX_VALUE)
                        .addGroup(InputFieldPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(passwordSeparator)
                            .addComponent(passwordField, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                            .addComponent(passwordLabel, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(usernameSeparator)
                            .addComponent(usernameLabel, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(usernameField, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))))
                .addGap(140, 140, 140))
        );
        InputFieldPanelLayout.setVerticalGroup(
            InputFieldPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, InputFieldPanelLayout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(usernameLabel)
                .addGap(0, 0, 0)
                .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(usernameSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(passwordLabel)
                .addGap(0, 0, 0)
                .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(passwordSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(showPasswordBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        RightPanel.add(InputFieldPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 580, 220));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        loginBtn.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 16)); // NOI18N
        loginBtn.setForeground(new java.awt.Color(1, 1, 1));
        loginBtn.setText("Sign In");
        loginBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(1, 1, 1)));
        loginBtn.setContentAreaFilled(false);
        loginBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        loginBtn.setPreferredSize(new java.awt.Dimension(105, 40));
        loginBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginBtnActionPerformed(evt);
            }
        });

        loginBtnSeperator.setForeground(new java.awt.Color(1, 1, 1));

        ExitBtn.setFont(new java.awt.Font("Alibaba PuHuiTi M", 0, 16)); // NOI18N
        ExitBtn.setForeground(new java.awt.Color(1, 1, 1));
        ExitBtn.setText("EXIT");
        ExitBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(1, 1, 1)));
        ExitBtn.setContentAreaFilled(false);
        ExitBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ExitBtn.setPreferredSize(new java.awt.Dimension(105, 40));
        ExitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitBtnActionPerformed(evt);
            }
        });

        forgetPasswordBtn.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        forgetPasswordBtn.setForeground(new java.awt.Color(1, 1, 1));
        forgetPasswordBtn.setText("Forget Password ?");
        forgetPasswordBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        forgetPasswordBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                forgetPasswordBtnMouseClicked(evt);
            }
        });

        pmsVersionLabel.setFont(new java.awt.Font("Alibaba PuHuiTi R", 0, 12)); // NOI18N
        pmsVersionLabel.setForeground(new java.awt.Color(80, 80, 80));
        pmsVersionLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pmsVersionLabel.setText("PMS @Version x.x Built On xx xx xxxx");
        pmsVersionLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(140, 140, 140)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(loginBtnSeperator, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                            .addComponent(loginBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ExitBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(239, 239, 239)
                        .addComponent(forgetPasswordBtn)))
                .addContainerGap(140, Short.MAX_VALUE))
            .addComponent(pmsVersionLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(loginBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ExitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(loginBtnSeperator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(forgetPasswordBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                .addComponent(pmsVersionLabel)
                .addGap(15, 15, 15))
        );

        RightPanel.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 430, 580, 290));

        getContentPane().add(RightPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 0, 580, 720));

        MainPanel.setBackground(new Color(0, 0, 0, 128));

        javax.swing.GroupLayout MainPanelLayout = new javax.swing.GroupLayout(MainPanel);
        MainPanel.setLayout(MainPanelLayout);
        MainPanelLayout.setHorizontalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1280, Short.MAX_VALUE)
        );
        MainPanelLayout.setVerticalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 720, Short.MAX_VALUE)
        );

        getContentPane().add(MainPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 720));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void usernameFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_usernameFieldFocusGained
        if(usernameField.getText().equals("Enter Your User Name")) {
            usernameField.setText("");
        }
    }//GEN-LAST:event_usernameFieldFocusGained

    private void passwordFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passwordFieldFocusGained
        if(passwordField.getText().equals("passwordField")) {
            passwordField.setText("");
        }        
    }//GEN-LAST:event_passwordFieldFocusGained

    private void showPasswordBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showPasswordBtnActionPerformed
        if (passwordField.getEchoChar() == '*') {
            passwordField.setEchoChar((char) 0);
            showPasswordBtn.setText("Hide Password");
        } else {
            passwordField.setEchoChar('*');
            showPasswordBtn.setText("Show Password");
        }
    }//GEN-LAST:event_showPasswordBtnActionPerformed

    private void loginBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginBtnActionPerformed
        UserRoleType userRoleType = UserAccountController.loginAuthentication(usernameField.getText(), passwordField.getText());
        if(userRoleType != null) {
            switch (userRoleType) {
                case ADMIN -> { new AdminGui(); this.dispose(); }
                case PROJECT_MANAGER -> { new ProjectManagerGui(); this.dispose(); }
                case LECTURER -> { new LecturerGui(); this.dispose(); }
                case STUDENT -> { new StudentGui(); this.dispose(); }
            }
        }
    }//GEN-LAST:event_loginBtnActionPerformed

    private void ExitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitBtnActionPerformed
        log.info("Project Management System Destroy Successful.");
        System.exit(0);
    }//GEN-LAST:event_ExitBtnActionPerformed

    private void forgetPasswordBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_forgetPasswordBtnMouseClicked
        isEnabled = false;
        MainPanel.setVisible(true);
        RightPanel.setEnabled(isEnabled);
        LeftPanel.setEnabled(isEnabled);
        usernameField.setEnabled(isEnabled);
        passwordField.setEnabled(isEnabled);
        showPasswordBtn.setEnabled(isEnabled);
        forgetPasswordBtn.setEnabled(isEnabled);
        loginBtn.setEnabled(isEnabled);
        ExitBtn.setEnabled(isEnabled);
        new ForgetPasswordGui();
    }//GEN-LAST:event_forgetPasswordBtnMouseClicked

    public static void unFreeze() {
        isEnabled = true;
        MainPanel.setVisible(false);
        RightPanel.setEnabled(isEnabled);
        LeftPanel.setEnabled(isEnabled);
        usernameField.setEnabled(isEnabled);
        passwordField.setEnabled(isEnabled);
        showPasswordBtn.setEnabled(isEnabled);
        forgetPasswordBtn.setEnabled(isEnabled);
        loginBtn.setEnabled(isEnabled);
        ExitBtn.setEnabled(isEnabled);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoginGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginGui().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JButton ExitBtn;
    private javax.swing.JPanel InputFieldPanel;
    private static javax.swing.JPanel LeftPanel;
    private javax.swing.JLabel LogoTitle_1;
    private javax.swing.JLabel LogoTitle_2;
    private static javax.swing.JPanel MainPanel;
    private static javax.swing.JPanel RightPanel;
    private javax.swing.JLabel RoyaleLabel;
    private javax.swing.JLabel WelcomeToRoyalelHotelLabel;
    private static javax.swing.JLabel forgetPasswordBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private static javax.swing.JButton loginBtn;
    private javax.swing.JSeparator loginBtnSeperator;
    private static javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JSeparator passwordSeparator;
    private javax.swing.JLabel pmsVersionLabel;
    private static javax.swing.JCheckBox showPasswordBtn;
    private static javax.swing.JTextField usernameField;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JSeparator usernameSeparator;
    // End of variables declaration//GEN-END:variables
}
