package com.project.service.Impl;

import com.project.common.constants.AccountStatus;
import com.project.common.constants.MessageConstant;
import com.project.common.constants.UserRoleType;
import com.project.common.utils.Dialog;
import com.project.dao.UserAuthenticationDAO;
import com.project.pojo.UserAuthentication;
import com.project.service.UserAuthenticationService;
import com.project.ui.authentication.LoginGui;

public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    private UserAuthenticationDAO userAuthenticationDAO = new UserAuthenticationDAO();

    /**
     * Verify User Authentication Status
     * @oaram userRoleType
     * @return User Authentication
     */
    @Override
    public Boolean checkUserAuthorization(UserRoleType userRoleType) {
        UserAuthentication userAuthentication = userAuthenticationDAO.checkUserAuthorization();
        if (userAuthentication == null) {
            Dialog.ErrorDialog(MessageConstant.ERROR_NOT_LOGIN);
            return false;
        } else if (userAuthentication.getAccountStatus() == AccountStatus.deActivated) {
            Dialog.ErrorDialog(MessageConstant.ERROR_ACCOUNT_DEACTIVATED);
            return false;
        } else if (userAuthentication.getUserRoleType() != userRoleType) {
            Dialog.ErrorDialog(MessageConstant.ERROR_UNAUTHORIZED_ACCESS);
            return false;
        }
        return true;
    }

    /**
     * Destroy User Authentication
     */
    @Override
    public void destroy() {
        userAuthenticationDAO.destroy();
    }

}
