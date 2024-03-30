package com.project.dao;

import com.project.common.constants.AccountStatus;
import com.project.common.constants.UserRoleType;
import com.project.pojo.UserAuthentication;

public class UserAuthenticationDAO {

    private static UserAuthentication userAuthentication;

    /**
     * Insert Authenticated User
     * @param userId
     * @param username
     * @param userRoleType
     * @param accountStatus
     */
    public static void insertAuthenticatedUser(Integer userId, String username, UserRoleType userRoleType, AccountStatus accountStatus) {
        userAuthentication = new UserAuthentication(userId,username,userRoleType,accountStatus);
    }

    /**
     * Get User Authentication Status
     * @return User Authentication
     */
    public static UserAuthentication checkUserAuthorization() {
        return userAuthentication;
    }

    public static void destroy() {
        userAuthentication = null;
    }

}
