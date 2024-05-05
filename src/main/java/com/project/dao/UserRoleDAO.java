package com.project.dao;

import com.project.common.constants.AccountStatus;
import com.project.common.constants.UserRoleType;
import com.project.common.utils.FileHandler;
import com.project.common.utils.JsonHandler;
import com.project.common.utils.PropertiesReader;
import com.project.pojo.UserRole;

import java.util.ArrayList;
import java.util.List;

public class UserRoleDAO {

    private static final String USER_ACCOUNT = PropertiesReader.getProperty("UserRole");
    private static List<UserRole> user_roles = new ArrayList<>();

    static {
        loadUserRoleData();
    }

    /**
     * Check User Account Status (isActive/deActivated)
     * @param userId
     * @return account status
     */
    public AccountStatus checkAccountStatus(Integer userId) {
        for (UserRole user_role:user_roles) {
            if (user_role.getUserId().equals(userId)) {
                return user_role.getAccountStatus();
            }
        }
        return null;
    }


    /**
     * Check User Role Type
     * @param userId
     * @return user role type
     */
    public UserRoleType checkRoleType(Integer userId) {
        for (UserRole user_role:user_roles) {
            if (user_role.getUserId().equals(userId)) {
                return user_role.getUserRoleType();
            }
        }
        return null;
    }

    /**
     * Pre-load User Role Data
     */
    private static void loadUserRoleData() {

        JsonHandler userRole = new JsonHandler();
        userRole.encode(FileHandler.readFile(USER_ACCOUNT));

        for (int i=0; i<(userRole.getAll().size()); i++) {
            JsonHandler obj = new JsonHandler();
            obj.cloneObject(userRole.getObject(i));

            UserRole ur = new UserRole();
            ur.setUserId(obj.getInt("id"));

            switch (obj.get("roleType")) {
                case "ADMIN" -> { ur.setUserRoleType(UserRoleType.ADMIN); }
                case "PROJECT_MANAGER" -> { ur.setUserRoleType(UserRoleType.PROJECT_MANAGER); }
                case "LECTURER" -> { ur.setUserRoleType(UserRoleType.LECTURER); }
                case "STUDENT" -> { ur.setUserRoleType(UserRoleType.STUDENT); }
            };

            switch (obj.get("status")) {
                case "isActive" -> { ur.setAccountStatus(AccountStatus.isActive); }
                case "deActivated" -> { ur.setAccountStatus(AccountStatus.deActivated); }
            };

            user_roles.add(ur);
        }
    }

}
