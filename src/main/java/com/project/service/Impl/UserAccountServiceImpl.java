package com.project.service.Impl;

import com.project.common.constants.MessageConstant;
import com.project.common.utils.Dialog;
import com.project.dao.UserAccountDAO;
import com.project.pojo.UserAccount;
import com.project.service.UserAccountService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserAccountServiceImpl implements UserAccountService {

    private UserAccountDAO userAccountDAO = new UserAccountDAO();

    /**
     * Login Authentication
     * @param account
     * @param password
     * @return boolean result
     */

    @Override
    public Boolean loginAuthentication(String account, String password) {
        UserAccount user = userAccountDAO.getUserAccount(account);
        Boolean result = userAccountDAO.verifyPassword(account,password);

        if(user == null){
            Dialog.ErrorDialog(MessageConstant.ERROR_USERNAME_INCORRECT);
            return false;
        }
        if (!result) {
            Dialog.ErrorDialog(MessageConstant.ERROR_PASSWORD_INCORRECT);
            return false;
        }

        // TODO: 28-Mar-24 To Addon Verify Account Status function
        log.info("User Info: "+ user);
        return true;
    }
}
