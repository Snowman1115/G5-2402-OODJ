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
import com.project.service.UserAccountService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserAccountController {

    private static UserAccountService userAccountService = new UserAccountServiceImpl();

    // Testing
//    public static void main(String[] args) {
//        UserRoleType ut = loginAuthentication("admin", "1234");
//        System.out.println(ut);
//    }

    /**
     * Login Authentication
     * @author CHAN HOONG JIAN
     * @param account
     * @param password
     * @return boolean result
     */
    public static UserRoleType loginAuthentication(String account, String password) {
        log.info("User login : " + account);
        return userAccountService.loginAuthentication(account,password);
    }

}
