package com.project.service;

import com.project.common.constants.UserRoleType;

public interface UserAccountService {

    /**
     * Login Authentication
     * @param account
     * @param password
     * @return boolean result
     */
    public UserRoleType loginAuthentication(String account, String password);

}
