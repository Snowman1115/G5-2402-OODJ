package com.project.service;

import com.project.common.constants.UserRoleType;

public interface UserAuthenticationService {

    /**
     * Verify User Authentication Status
     * @param userRoleType
     * @return User Authentication
     */
    public Boolean checkUserAuthorization(UserRoleType userRoleType);

    /**
     * Destroy User Authentication
     */
    public void destroy();

}
