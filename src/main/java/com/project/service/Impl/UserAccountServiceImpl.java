package com.project.service.Impl;

import com.project.common.constants.AccountStatus;
import com.project.common.constants.MessageConstant;
import com.project.common.constants.UserRoleType;
import com.project.common.utils.DataValidator;
import com.project.common.utils.Dialog;
import com.project.dao.UserAccountDAO;
import com.project.dao.UserAuthenticationDAO;
import com.project.dao.UserRoleDAO;
import com.project.pojo.UserAccount;
import com.project.pojo.UserAuthentication;
import com.project.service.UserAccountService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserAccountServiceImpl implements UserAccountService {

    private UserAccountDAO userAccountDAO = new UserAccountDAO();

    private UserAuthenticationDAO userAuthenticationDAO = new UserAuthenticationDAO();

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
            log.info("User (" + account + ") login fail error: " + MessageConstant.ERROR_USERNAME_INCORRECT);
            return null;
        }
        if (!userAccountDAO.verifyPassword(account,password)) {
            Dialog.ErrorDialog(MessageConstant.ERROR_PASSWORD_INCORRECT);
            log.info("User (" + account + ") login fail error: " + MessageConstant.ERROR_PASSWORD_INCORRECT);
            return null;
        }

        AccountStatus accountStatus = userRoleDAO.checkAccountStatus(user.getUserId());
        if(accountStatus == AccountStatus.deActivated) {
            Dialog.ErrorDialog(MessageConstant.ERROR_ACCOUNT_DEACTIVATED);
            log.info("User (" + account + ") login fail error: " + MessageConstant.ERROR_ACCOUNT_DEACTIVATED);
            return null;
        }

        UserRoleType userRoleType = userRoleDAO.checkRoleType(user.getUserId());

        userAuthenticationDAO.insertAuthenticatedUser(user.getUserId(),user.getUsername(),userRoleType,accountStatus);


        log.info("User: (" + account + ") ,Role: (" + userRoleType + ") login successful.");

        switch (userRoleType) {
            case ADMIN -> { log.info("Redirecting (" + account + ") to Admin Panel."); return UserRoleType.ADMIN; }
            case PROJECT_MANAGER -> { log.info("Redirecting (" + account + ") to Project Manager Panel."); return UserRoleType.PROJECT_MANAGER; }
            case LECTURER -> { log.info("Redirecting (" + account + ") to Lecturer Panel."); return UserRoleType.LECTURER; }
            case STUDENT -> { log.info("Redirecting (" + account + ") to Student Panel."); return UserRoleType.STUDENT; }
            default -> { return null; }
        }
    }

    /**
     * Verify User Security Phrase
     * @param account
     * @param securityPhrase
     * @return boolean result
     */
    @Override
    public String verifyUserSecurityPhrase(String account, String securityPhrase) {
        UserAccount user = userAccountDAO.verifySecurityPhrase(account,securityPhrase);
        if (user == null) {
            Dialog.ErrorDialog(MessageConstant.ERROR_SECURITY_PHRASE_N_ACCOUNT_INCORRECT);
            log.info("User (" + account + ") verify fail error: " + MessageConstant.ERROR_SECURITY_PHRASE_N_ACCOUNT_INCORRECT);
            return null;
        }
        return user.getUsername();
    }

    /**
     * Reset User Password - FORGET PASSWORD
     * @param account
     * @param securityPhrase
     * @return boolean result
     */
    @Override
    public boolean resetUserPasswordBySecurityPhrase(String account, String securityPhrase, String password) {
        if (!userAccountDAO.resetPasswordBySecurityPhrase(account,securityPhrase,password)) {
            Dialog.ErrorDialog(MessageConstant.ERROR_SECURITY_PHRASE_N_ACCOUNT_INCORRECT);
            log.info("User (" + account + ") reset password fail error: " + MessageConstant.ERROR_SECURITY_PHRASE_N_ACCOUNT_INCORRECT);
            return false;
        }
        Dialog.SuccessDialog(MessageConstant.SUCCESS_RESET_PASSWORD);
        log.info("User (" + account + ") reset password successful");
        return true;
    }

    @Override
    public boolean updateProfile(UserAccount userAccount) {
        /* Input Validation Check */
        /*if (DataValidator.validateUsername(userAccount.getUsername()))
*/
        return false;
    }
}
