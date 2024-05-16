/**
 * User Account Controller
 * @author CHAN HOONG JIAN
 * @version 1.0, Last edited on 2024-03-29
 * @since 2024-03-29
 */
package com.project.controller;

import com.project.common.constants.UserRoleType;
import com.project.common.utils.DataValidator;
import com.project.common.utils.JsonHandler;
import com.project.pojo.UserAccount;
import com.project.pojo.UserAuthentication;
import com.project.service.Impl.UserAccountServiceImpl;
import com.project.service.Impl.UserAuthenticationServiceImpl;
import com.project.service.UserAccountService;
import com.project.service.UserAuthenticationService;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

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
     * Get User Authentication Details
     * @return User Authentication
     */
    public static UserAuthentication getAuthenticationUserDetails() {
        log.info("Get Authenticated User Details.");
        return userAuthenticationService.getAuthenticationUserDetails();
    }

    /**
     * Get user account by userID
     * @param userId
     * @return user account
     */
    public static UserAccount getUserDetailsByUserId(Integer userId) {
        log.info("Get user details: " + userId);
        return userAccountService.getUserDetailsByUserId(userId);
    }

    /**
     * Update User Profile
     * @param userId
     * @param username
     * @param firstName
     * @param lastName
     * @param email
     * @return boolean result
     */
    public static boolean updateUserProfileById(Integer userId, String username, String firstName, String lastName, String email) {
        if (!DataValidator.validateEmptyInput(userId)
                || !DataValidator.validateEmptyInput(username)
                || !DataValidator.validateEmptyInput(firstName)
                || !DataValidator.validateEmptyInput(lastName)
                || !DataValidator.validateEmptyInput(email)
                || !DataValidator.validateUsername(username)
                || !DataValidator.validateEmail(email)) {
            return false;
        }
        log.info("Update Profile: {} - {} {} {} {}", userId, username, firstName, lastName, email);
        return userAccountService.updateProfileById(userId, username, firstName, lastName, email);
    }

    /**
     * Change Password
     * @param userId
     * @param oldPassword
     * @param newPassword
     * @param confirmPassword
     * @return boolean result
     */
    public static boolean changePasswordById(Integer userId, String oldPassword, String newPassword, String confirmPassword) {
        if (!DataValidator.validateEmptyInput(userId)
                || !DataValidator.validateEmptyInput(oldPassword)
                || !DataValidator.validateEmptyInput(newPassword)
                || !DataValidator.validateEmptyInput(confirmPassword)
                || !DataValidator.validatePassword(newPassword)
                || !DataValidator.validatePasswordNConfirmPassword(newPassword,confirmPassword)) {
            return false;
        }
        log.info("User Change Password : " + userId);
        return userAccountService.changePasswordById(userId, oldPassword, newPassword);
    }

    /**
     * Change SecurityPhrase
     * @param userId
     * @param oldSecurityPhrase
     * @param newSecurityPhrase
     * @param confirmSecurityPhrase
     * @return boolean result
     */
    public static boolean changeSecurityPhraseById(Integer userId, String oldSecurityPhrase, String newSecurityPhrase, String confirmSecurityPhrase) {
        if (!DataValidator.validateEmptyInput(userId)
                || !DataValidator.validateEmptyInput(oldSecurityPhrase)
                || !DataValidator.validateEmptyInput(newSecurityPhrase)
                || !DataValidator.validateEmptyInput(confirmSecurityPhrase)
                || !DataValidator.validatePassword(newSecurityPhrase)
                || !DataValidator.validatePasswordNConfirmPassword(newSecurityPhrase,confirmSecurityPhrase)) {
            return false;
        }
        log.info("User Change Security Phrase : " + userId);
        return userAccountService.changeSecurityPhraseById(userId, oldSecurityPhrase, confirmSecurityPhrase);
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
        if (!DataValidator.validateEmptyInput(account)
                || !DataValidator.validateEmptyInput(securityPhrase)
                || !DataValidator.validateEmptyInput(password)
                || !DataValidator.validateEmptyInput(confirmPassword)
                || !DataValidator.validatePasswordNConfirmPassword(password,confirmPassword)) {
            return false;
        }
        log.info("User Reset Password By SecurityPhrase : " + account);
        return userAccountService.resetUserPasswordBySecurityPhrase(account,securityPhrase, password);
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
     * Logout Function
     */
    public static void logout() {
        log.info("User logout.");
        userAuthenticationService.destroy();
    }

    /**
     * get all students
     * @return students
     */
    public static JsonHandler getStudents() {
        return userAccountService.getUsersByRole(UserRoleType.STUDENT);
    }

    /**
     * get all lecturers
     * @return lecturers
     */
    public static JsonHandler getLecturers() {
        return userAccountService.getUsersByRole(UserRoleType.LECTURER);
    }

    /**
     * get all project managers
     * @return projectManagers
     */
    public static JsonHandler getPMs() {
        return userAccountService.getUsersByRole(UserRoleType.PROJECT_MANAGER);
    }

    /**
     * get all admins
     * @return admins
     */
    public static JsonHandler getAdmins() {
        return userAccountService.getUsersByRole(UserRoleType.ADMIN);
    }

    /**
     * get all intakes
     * @return intakes
     */
    public static List<String> getIntakes() {
        return userAccountService.getAllIntakes();
    }
}
