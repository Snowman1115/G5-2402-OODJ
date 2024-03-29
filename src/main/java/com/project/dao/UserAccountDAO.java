package com.project.dao;

import com.project.common.constants.MessageConstant;
import com.project.common.utils.DateTimeUtils;
import com.project.common.utils.PropertiesReader;
import com.project.common.utils.JsonHandler;
import com.project.pojo.UserAccount;
import lombok.extern.slf4j.Slf4j;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.mindrot.jbcrypt.BCrypt;

@Slf4j
public class UserAccountDAO {

    private static final String USER_ACCOUNT = PropertiesReader.getProperty("UserFile");
    private static List<UserAccount> users = new ArrayList<>();

    static {
        loadUserData();
    }

    // test run
    public static void main(String[] args) {
        System.out.println(BCrypt.checkpw("1234", users.get(6).getPassword()));
    }

    /**
     * Get user account by username / email
     * @param account
     * @return user account
     */
    public UserAccount getUserAccount(String account) {
        for (UserAccount user:users) {
            if (user.getUsername().equals(account) || user.getEmail().equals(account)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Verify account and password
     * @param account
     * @param password
     * @return boolean
     */
    public Boolean verifyPassword(String account, String password) {
        for (UserAccount user: users) {
            if ((user.getUsername().equals(account) || user.getEmail().equals(account)) && BCrypt.checkpw(password, user.getPassword())) {
                return true;
            }
        }
        return false;
    }

    // Load user data
    private static void loadUserData() {

        JsonHandler userData = new JsonHandler();
        userData.encode(readFile(USER_ACCOUNT));

        for (int i=0; i<(userData.getAll().size()); i++) {
            JsonHandler obj = new JsonHandler();
            obj.setObject(userData.getObject(i));

            UserAccount ua = new UserAccount();
            ua.setUserId(obj.getInt("id"));
            ua.setUsername(obj.get("username"));
            ua.setFirstName(obj.get("first_name"));
            ua.setLastName(obj.get("last_name"));
            ua.setEmail(obj.get("email"));
            ua.setPassword(obj.get("password"));
            ua.setSecurityPhrase(obj.get("safeWord"));
            ua.setUpdatedAt(DateTimeUtils.formatDateTime(obj.get("updated_at")));
            ua.setCreatedAt(DateTimeUtils.formatDateTime(obj.get("created_at")));
            users.add(ua);
        }
    }

    // Update user data
    public static boolean update(Integer userId, String field, String value) {
        // find user object in arraylist
        for (UserAccount user : users) {
            if (user.getUserId() == userId) {
                try {
                    switch (field) {
                        case "username" -> {
                            user.setUsername(value);
                            return store(userId, "username", value);
                        }
                        case "firstName" -> {
                            user.setFirstName(value);
                            return store(userId, "first_name", value);
                        }
                        case "lastName" -> {
                            user.setLastName(value);
                            return store(userId, "last_name", value);
                        }
                        case "password" -> {
                            String encryptedPassword = BCrypt.hashpw(value, BCrypt.gensalt());
                            user.setPassword(encryptedPassword);
                            return store(userId, "password", encryptedPassword);
                        }
                        case "email" -> {
                            user.setEmail(value);
                            return store(userId, "email", value);
                        }
                        case "securityPhrase" -> {
                            user.setPassword(value);
                            return store(userId, "safeWord", value);
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
     * @return
     */
    private static boolean store(Integer userId, String attribute, String value) {
        JsonHandler userJson = new JsonHandler();
        userJson.encode(readFile(USER_ACCOUNT));
        return userJson.update(userId, attribute, value);
    }

    // testings





    /**
     * Read text file
     * @param filePath
     * @return String
     */
    private static String readFile(String filePath) {
        try {
            return new BufferedReader(new FileReader(filePath)).readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
