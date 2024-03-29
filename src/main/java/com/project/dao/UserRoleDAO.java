package com.project.dao;

import com.project.common.constants.AccountStatus;
import com.project.common.constants.UserRoleType;
import com.project.common.utils.DateTimeUtils;
import com.project.common.utils.JsonHandler;
import com.project.common.utils.PropertiesReader;
import com.project.pojo.UserAccount;
import com.project.pojo.UserRole;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserRoleDAO {

    private static final String USER_ACCOUNT = PropertiesReader.getProperty("UserRole");
    private static List<UserRole> user_roles = new ArrayList<>();

    static {
        loadUserRoleData();
    }

    /**
     * Pre-load User Role Data
     */
    private static void loadUserRoleData() {

        JsonHandler userRole = new JsonHandler();
        userRole.encode(readFile(USER_ACCOUNT));

        for (int i=0; i<(userRole.getAll().size()); i++) {
            JsonHandler obj = new JsonHandler();
            obj.setObject(userRole.getObject(i));

            UserRole ur = new UserRole();
            ur.setUserId(obj.getInt("id"));

            switch (obj.get("user_role")) {
                case "ADMIN" -> { ur.setUserRoleType(UserRoleType.ADMIN); }
                case "PROJECT_MANAGER" -> { ur.setUserRoleType(UserRoleType.PROJECT_MANAGER); }
                case "LECTURER" -> { ur.setUserRoleType(UserRoleType.LECTURER); }
                case "STUDENT" -> { ur.setUserRoleType(UserRoleType.STUDENT); }
            };

            switch (obj.get("account_status")) {
                case "isActive" -> { ur.setAccountStatus(AccountStatus.isActive); }
                case "deActivated" -> { ur.setAccountStatus(AccountStatus.deActivated); }
            };

            user_roles.add(ur);
        }
    }



    private static String readFile(String filePath) {
        try {
            return new BufferedReader(new FileReader(filePath)).readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
