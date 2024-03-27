package com.project.service;

public interface UserAccountService {

    /**
     * Login Authentication
     * @param account
     * @param password
     * @return boolean result
     */
    public Boolean loginAuthentication(String account, String password);

}
