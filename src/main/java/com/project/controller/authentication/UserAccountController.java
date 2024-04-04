/**
 * User Account Controller
 * @author CHAN HOONG JIAN
 * @version 1.0, Last edited on 2024-03-29
 * @since 2024-03-29
 */
package com.project.controller.authentication;

import com.project.common.constants.UserRoleType;
import com.project.pojo.UserAccount;
import com.project.service.Impl.UserAccountServiceImpl;
import com.project.service.Impl.UserAuthenticationServiceImpl;
import com.project.service.UserAccountService;
import com.project.service.UserAuthenticationService;
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
     * Logout Function
     */
    public static void logout() {
        log.info("User logout.");
        userAuthenticationService.destroy();
    }

}
