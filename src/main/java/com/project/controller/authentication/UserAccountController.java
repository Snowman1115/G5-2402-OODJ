/**
 * User Account Controller
 * @author CHAN HOONG JIAN
 * @version 1.0, Last edited on 2024-03-29
 * @since 2024-03-29
 */
package com.project.controller.authentication;

import com.project.common.constants.MessageConstant;
import com.project.common.constants.UserRoleType;
import com.project.common.utils.DataValidator;
import com.project.pojo.UserAccount;
import com.project.service.Impl.UserAccountServiceImpl;
import com.project.service.Impl.UserAuthenticationServiceImpl;
import com.project.service.UserAccountService;
import com.project.service.UserAuthenticationService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserAccountController {

    private static UserAccountService userAccountService = new UserAccountServiceImpl();

    private static UserAuthenticationService userAuthenticationService = new UserAuthenticationServiceImpl();

    /**
     * Login Authentication
     * @param account
     * @param password
     * @return boolean result
     */
    public static UserRoleType loginAuthentication(String account, String password) {
        if (!DataValidator.validateEmptyInput(account) || !DataValidator.validateEmptyInput(password)) {
            return null;
        }
        log.info("User login : " + account);
        return userAccountService.loginAuthentication(account,password);
    }

    /**
     * Verify User Authentication Status
     * @param userRoleType
     * @return User Authentication
     */
    public static Boolean checkUserAuthorization(UserRoleType userRoleType) {
        log.info("Verify user authorization status.");
        return userAuthenticationService.checkUserAuthorization(userRoleType);
    }

    /**
     * Update User Profile
     * @param userAccount
     * @return boolean result
     */
    public static boolean updateProfile(UserAccount userAccount) {
        log.info("Update Profile: " + userAccount);
        return userAccountService.updateProfile(userAccount);
    }

    /**
     * Verify User Security Phrase
     * @param account
     * @param securityPhrase
     * @return boolean result
     */
    public static String verifyUserSecurityPhrase(String account, String securityPhrase) {
        if (!DataValidator.validateEmptyInput(account) || !DataValidator.validateEmptyInput(securityPhrase)) {
            return null;
        }
        log.info("Verify User Security Phrase : " + account);
        return userAccountService.verifyUserSecurityPhrase(account,securityPhrase);
    }

    /**
     * Reset User Password With Security Phrase
     * @param account
     * @param securityPhrase
     * @param password
     * @param confirmPassword
     * @return boolean result
     */
    public static boolean resetUserPasswordBySecurityPhrase(String account, String securityPhrase, String password, String confirmPassword) {
        if (!DataValidator.validateEmptyInput(account) || !DataValidator.validateEmptyInput(securityPhrase) || !DataValidator.validateEmptyInput(password) || !DataValidator.validateEmptyInput(confirmPassword)) {
            return false;
        }
        if (!DataValidator.validatePasswordNConfirmPassword(password,confirmPassword)) {
            return false;
        }
        log.info("User Reset Password By SecurityPhrase : " + account);
        return userAccountService.resetUserPasswordBySecurityPhrase(account,securityPhrase, password);
    }

    /**
     * Logout Function
     */
    public static void logout() {
        log.info("User logout.");
        userAuthenticationService.destroy();
    }

}
