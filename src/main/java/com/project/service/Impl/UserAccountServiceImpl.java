package com.project.service.Impl;

import com.project.common.constants.AccountStatus;
import com.project.common.constants.MessageConstant;
import com.project.common.utils.Dialog;
import com.project.dao.UserAccountDAO;
import com.project.dao.UserRoleDAO;
import com.project.pojo.UserAccount;
import com.project.pojo.UserAuthentication;
import com.project.service.UserAccountService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserAccountServiceImpl implements UserAccountService {

    private UserAccountDAO userAccountDAO = new UserAccountDAO();

    private UserRoleDAO userRoleDAO = new UserRoleDAO();

    /**
     * Login Authentication
     * @param account
     * @param password
     * @return boolean result
     */

    @Override
    public Boolean loginAuthentication(String account, String password) {
        UserAccount user = userAccountDAO.getUserAccount(account);

        if(user == null){
            Dialog.ErrorDialog(MessageConstant.ERROR_USERNAME_INCORRECT);
            return false;
        }
        if (!userAccountDAO.verifyPassword(account,password)) {
            Dialog.ErrorDialog(MessageConstant.ERROR_PASSWORD_INCORRECT);
            return false;
        }
        if(userRoleDAO.checkAccountStatus(user.getUserId()) == AccountStatus.deActivated) {
            Dialog.ErrorDialog(MessageConstant.ERROR_ACCOUNT_DEACTIVATED);
            return false;
        }

        // todo insert user authentication details
        switch (userRoleDAO.checkRoleType(user.getUserId())) {
            case ADMIN -> {

            }
        }
        return true;
    }

}
