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
     * Get User Authentication Details
     * @return User Authentication
     */
    public static UserAuthentication checkUserAuthorization() {
        return userAuthentication;
    }

    /**
     * Destroy User Authentication
     */
    public static void destroy() {
        userAuthentication = null;
    }

}
