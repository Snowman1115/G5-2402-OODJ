package com.project.service;

import com.project.common.constants.UserRoleType;
import com.project.pojo.UserAuthentication;

public interface UserAuthenticationService {

    /**
     * Verify User Authentication Status
     * @param userRoleType
     * @return User Authentication
     */
    public Boolean checkUserAuthorization(UserRoleType userRoleType);

}
