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
     * @param account
     * @param password
     * @return boolean result
     */
    public static Boolean loginAuthentication(String account, String password) {
        log.info("Controller Implemented");
        if(userAccountService.loginAuthentication(account,password)) {
            log.info("Login Success");
        }
        log.info("Login Failed");
        return false;
    }

    public static void main(String[] args) {
        System.out.println(loginAuthentication("rlanston0", "xxx"));
    }

}
