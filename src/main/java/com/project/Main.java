/**
 * Function To Run The Program
 * @author CHAN HOONG JIAN
 * @version 1.0, Last edited on 2024-03-26
 * @since 2024-03-26
 */

package com.project;

import com.project.common.constants.MessageConstant;
import com.project.common.utils.Dialog;
import com.project.pojo.UserAccount;
import com.project.ui.authentication.LoginGui;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class Main {
    public static void main(String[] args) {
        Dialog.ErrorDialog(MessageConstant.ERROR_USERNAME_INCORRECT);
        log.info(MessageConstant.ERROR_USERNAME_INCORRECT);
        //  new LoginGui();
    }

}