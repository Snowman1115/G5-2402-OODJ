package com.project.service.Impl;

import com.project.common.constants.UserRoleType;
import com.project.common.utils.JsonHandler;

public class AdminAccountServiceImpl extends UserAccountServiceImpl {

    /**
     * get admin details
     * @return
     */
    public JsonHandler getAdmins() {
        return super.getUsersByRole(UserRoleType.ADMIN);
    }
}
