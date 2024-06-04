package com.project.dao;

import com.project.common.constants.AccountStatus;
import com.project.common.constants.MessageConstant;
import com.project.common.constants.UserRoleType;
import com.project.common.utils.DateTimeUtils;
import com.project.common.utils.FileHandler;
import com.project.common.utils.JsonHandler;
import com.project.common.utils.PropertiesReader;
import com.project.pojo.UserAccount;
import com.project.pojo.UserRole;
import org.json.simple.JSONObject;
import org.mindrot.jbcrypt.BCrypt;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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

    public List<UserRole> getAllUserRoles() {
        return user_roles;
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
     * update user role
     * @param userId
     * @param field
     * @param value
     * @return
     */
    public boolean update(Integer userId, String field, String value) {
        for (UserRole ur : user_roles) {
            if (ur.getUserId().equals(userId)) {
                try {
                    switch (field) {
                        case "roleType" -> {
                            if (value.equals(UserRoleType.LECTURER.toString())) {
                                ur.setUserRoleType(UserRoleType.LECTURER);
                            } else {
                                ur.setUserRoleType(UserRoleType.PROJECT_MANAGER);
                            }
                            return store(userId, "roleType", value);
                        }
                        case "status" -> {
                            if (value.equals(AccountStatus.isActive.toString())) {
                                ur.setAccountStatus(AccountStatus.isActive);
                            } else {
                                ur.setAccountStatus(AccountStatus.deActivated);
                            }
                            return store(userId, "first_name", value);
                        }
                        default -> {
                            log.info("Error: " + MessageConstant.ERROR_OBJECT_FIELD_NOT_FOUND);
                            return false;
                        }
                    }
                } catch (Exception e) {
                    return false;
                }
            }
        }

        log.info("Error: " + MessageConstant.ERROR_OBJECT_NOT_FOUND);
        return false;
    }

    /**
     * Store updated data into text file
     * @param userId
     * @param attribute
     * @param value
     * @return boolean
     */
    private boolean store(Integer userId, String attribute, String value) {
        JsonHandler userJson = new JsonHandler();
        userJson.encode(FileHandler.readFile(USER_ACCOUNT));
        return userJson.update(userId, attribute, value, USER_ACCOUNT);
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
