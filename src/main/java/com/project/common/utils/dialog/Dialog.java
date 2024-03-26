/**
 * JOptionPane Prompt Message Function
 * @author CHAN HOONG JIAN
 * @version 1.0, Last edited on 2024-03-26
 * @since 2024-03-26
 */

package com.project.common.utils.dialog;

import com.project.common.utils.properties.PropertiesReader;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Dialog {

    private static final String DEFAULTERRORTITLE = PropertiesReader.getProperty("ErrorTitle");
    private static final String DEFAULTSUCCESSTITLE = PropertiesReader.getProperty("SuccessTitle");
    private static final String DEFAULTALERTTITLE = PropertiesReader.getProperty("AlertTitle");
    private static final String ERRORICON = PropertiesReader.getProperty("ErrorIcon");
    private static final String SUCCESSICON = PropertiesReader.getProperty("SuccessIcon");
    private static final String ALERTICON = PropertiesReader.getProperty("AlertIcon");

    /**
     * Prompt Error Dialog
     * @param message
     */
    public static void ErrorDialog(String message){
        ImageIcon eicon = new ImageIcon(ERRORICON);
        JOptionPane.showMessageDialog(null,message, DEFAULTERRORTITLE, JOptionPane.ERROR_MESSAGE,eicon);
    }

    /**
     * Prompt Error Dialog
     * @param title
     * @param message
     */
    public static void ErrorDialog(String title, String message){
        ImageIcon eicon = new ImageIcon(ERRORICON);
        JOptionPane.showMessageDialog(null,message, title, JOptionPane.ERROR_MESSAGE,eicon);
    }

    /**
     * Prompt Success Dialog
     * @param message
     */
    public static void SuccessDialog(String message){
        ImageIcon sicon = new ImageIcon(SUCCESSICON);
        JOptionPane.showMessageDialog(null,message, DEFAULTSUCCESSTITLE, JOptionPane.INFORMATION_MESSAGE,sicon);
    }

    /**
     * Prompt Success Dialog
     * @param title
     * @param message
     */
    public static void SuccessDialog(String title, String message){
        ImageIcon sicon = new ImageIcon(SUCCESSICON);
        JOptionPane.showMessageDialog(null,message, title, JOptionPane.INFORMATION_MESSAGE,sicon);
    }

    /**
     * Prompt Confirmation Dialog
     * @param message
     * @return
     */
    public static boolean ConfirmationDialog(String message){
        ImageIcon aicon = new ImageIcon(ALERTICON);
        int result = JOptionPane.showConfirmDialog(null,message,DEFAULTALERTTITLE,JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE,aicon);
        if (result == JOptionPane.YES_OPTION){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Prompt Confirmation Dialog
     * @param title
     * @param message
     * @return
     */
    public static boolean ConfirmationDialog(String title, String message){
        ImageIcon aicon = new ImageIcon(ALERTICON);
        int result = JOptionPane.showConfirmDialog(null,message,title,JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE,aicon);
        return result == JOptionPane.YES_OPTION;
    }

    /*public static String InputCredentialDialog(String message){
        ImageIcon aicon = new ImageIcon("src/main/resources/Icons/Alert.png");
        String result = (String) JOptionPane.showInputDialog(null,"Please enter super admin credential","Alert",JOptionPane.WARNING_MESSAGE,aicon,null,null);
        if (result != null && result.equals("superadmin")){
            return "success";
        } else if (result == null) {
            return "error1";
        } else {
            return "error2";
        }
    }*/

}
