/**
 * User Account Controller
 * @author CHAN HOONG JIAN
 * @version 1.0, Last edited on 2024-03-29
 * @since 2024-03-29
 */
package com.project.controller.authentication;

import com.project.pojo.UserAccount;
import com.project.service.Impl.UserAccountServiceImpl;
import com.project.service.UserAccountService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserAccountController {

    private static UserAccountService userAccountService = new UserAccountServiceImpl();

    /**
     * Login Authentication
     * @author CHAN HOONG JIAN
     * @param account
     * @param password
     * @return boolean result
     */
    public static Boolean loginAuthentication(String account, String password) {
        log.info("User login : " + account);
        if(userAccountService.loginAuthentication(account,password)) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(loginAuthentication("xxx", "xxx"));
    }

}
