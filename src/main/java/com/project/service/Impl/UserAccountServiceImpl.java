package com.project.service.Impl;

import com.project.common.constants.AccountStatus;
import com.project.common.constants.MessageConstant;
import com.project.common.constants.UserRoleType;
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
    public UserRoleType loginAuthentication(String account, String password) {
        UserAccount user = userAccountDAO.getUserAccount(account);

        if(user == null){
            Dialog.ErrorDialog(MessageConstant.ERROR_USERNAME_INCORRECT);
            return null;
        }
        if (!userAccountDAO.verifyPassword(account,password)) {
            Dialog.ErrorDialog(MessageConstant.ERROR_PASSWORD_INCORRECT);
            return null;
        }
        if(userRoleDAO.checkAccountStatus(user.getUserId()) == AccountStatus.deActivated) {
            Dialog.ErrorDialog(MessageConstant.ERROR_ACCOUNT_DEACTIVATED);
            return null;
        }

        // todo insert user authentication details
        switch (userRoleDAO.checkRoleType(user.getUserId())) {
            case ADMIN -> { return UserRoleType.ADMIN; }
            case PROJECT_MANAGER -> { return UserRoleType.PROJECT_MANAGER; }
            case LECTURER -> { return UserRoleType.LECTURER; }
            case STUDENT -> { return UserRoleType.STUDENT; }
            default -> { return null; }
        }

    }

}
