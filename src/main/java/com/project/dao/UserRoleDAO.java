package com.project.dao;

import com.project.common.constants.AccountStatus;
import com.project.common.constants.UserRoleType;
import com.project.common.utils.FileHandler;
import com.project.common.utils.JsonHandler;
import com.project.common.utils.PropertiesReader;
import com.project.pojo.UserRole;
import org.json.simple.JSONObject;

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
     * filter users by role
     * @param roleType
     * @return
     */
    public List<Integer> filterUserByRole(UserRoleType roleType) {
        List<Integer> list = new ArrayList<>();
        for (UserRole userRole : user_roles) {
            if (userRole.getUserRoleType().equals(roleType) && userRole.getAccountStatus().equals(AccountStatus.isActive)) {
                list.add(userRole.getUserId());
            }
        }
        return list;
    }

    /**
     * add new user and their role
     * @param userId
     * @param roleType
     */
    public void add(int userId, UserRoleType roleType) {
        UserRole ur = new UserRole();
        ur.setUserId(userId);
        ur.setUserRoleType(roleType);
        ur.setAccountStatus(AccountStatus.isActive);
        user_roles.add(ur);

        JSONObject newRecord = new JSONObject();
        newRecord.put("id", userId);
        newRecord.put("roleType", roleType.toString());
        newRecord.put("status", AccountStatus.isActive.toString());

        JsonHandler userRolesJson = new JsonHandler();
        userRolesJson.encode(FileHandler.readFile(USER_ACCOUNT));
        userRolesJson.addObject(newRecord, USER_ACCOUNT);
    }

    /**
     * remove user
     * @param userId
     */
    public boolean remove(int userId) {
        for (UserRole ur : user_roles) {
            if (ur.getUserId().equals(userId)) {
                user_roles.remove(ur);
                JsonHandler userRoleJson = new JsonHandler();
                userRoleJson.encode(FileHandler.readFile(USER_ACCOUNT));
                return userRoleJson.delete(userId, USER_ACCOUNT);
            }
        }

        return false;
    }

    /**
     * Pre-load User Role Data
     */
    private static void loadUserRoleData() {

        JsonHandler userRole = new JsonHandler();
        userRole.encode(FileHandler.readFile(USER_ACCOUNT));

        for (int i=0; i<(userRole.getAll().size()); i++) {
            JsonHandler obj = new JsonHandler();
            obj.setObject(userRole.getObject(i));

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
