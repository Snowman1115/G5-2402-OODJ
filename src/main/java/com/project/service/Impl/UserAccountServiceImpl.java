package com.project.service.Impl;

import com.project.dao.UserAccountDAO;
import com.project.pojo.UserAccount;
import com.project.service.UserAccountService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserAccountServiceImpl implements UserAccountService {

    private UserAccountDAO userAccountDAO = new UserAccountDAO();

    /**
     * Login Authentication
     * @param account
     * @param password
     * @return boolean result
     */
    @Override
    public Boolean loginAuthentication(String account, String password) {
        log.info("Service Implemented");
        UserAccount user = userAccountDAO.getUserAccount(account);
        if(user != null){
            // TODO: 28-Mar-24 To Addon Verify Account Status function
            log.info("User Info: "+ user);
            return true;
        }
        return false;
    }
}
