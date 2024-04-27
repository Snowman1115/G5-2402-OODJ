package com.project.dao;

import com.project.common.constants.MessageConstant;
import com.project.common.utils.DateTimeUtils;
import com.project.common.utils.FileHandler;
import com.project.common.utils.PropertiesReader;
import com.project.common.utils.JsonHandler;
import com.project.pojo.UserAccount;
import lombok.extern.slf4j.Slf4j;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.simple.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

@Slf4j
public class UserAccountDAO {

    private static final String USER_ACCOUNT = PropertiesReader.getProperty("UserFile");
    private static List<UserAccount> users = new ArrayList<>();

    static {
        loadUserData();
    }

    // test run
    /*public static void main(String[] args) {
        JsonHandler jh = new JsonHandler();
        jh.encode(FileHandler.readFile(USER_ACCOUNT));
        jh.update(10, "testing", "testing");
//        System.out.println(test);
    }*/


    /**
     * Get All User Details
     * @return List of User Account
     */

    public List<UserAccount> getAllUsers() {
        return users;
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
     * Get user account by userID
     * @param userId
     * @return user account
     */
    public UserAccount getUserAccountById(Integer userId) {
        for (UserAccount user:users) {
            if (user.getUserId() == userId) {
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

    /**
     * Verify userId and password
     * @param userId
     * @param password
     * @return boolean
     */
    public Boolean verifyPasswordById(Integer userId, String password) {
        for (UserAccount user: users) {
            if (user.getUserId().equals(userId) && BCrypt.checkpw(password, user.getPassword())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verify account and securityPhrase
     * @param account
     * @param securityPhrase
     * @return userId
     */
    public UserAccount verifySecurityPhrase(String account, String securityPhrase) {
        for (UserAccount user: users) {
            if ((user.getUsername().equals(account) || user.getEmail().equals(account)) && user.getSecurityPhrase().equals(securityPhrase)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Verify userId and securityPhrase
     * @param userId
     * @param securityPhrase
     * @return userId
     */
    public Boolean verifySecurityPhraseById(Integer userId, String securityPhrase) {
        for (UserAccount user: users) {
            if (user.getUserId().equals(userId) && user.getSecurityPhrase().equals(securityPhrase)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verify username/email usability
     * @param userId
     * @param account
     * @return string value
     */
    public Boolean checkUsernameOrEmailUsability(Integer userId, String account) {
        List<UserAccount> tempUser = new ArrayList<>(users);
        tempUser.removeIf(user -> user.getUserId().equals(userId));
        for (UserAccount user : tempUser) {
            if (user.getUsername().equals(account)) {
                return false;
            }
            if (user.getEmail().equals(account)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Update User Profile
     * @param userId
     * @param username
     * @param firstName
     * @param lastName
     * @param email
     * @return boolean result
     */
    public boolean updateUserProfileById(Integer userId, String username, String firstName, String lastName, String email) {
        for (UserAccount user:users) {
            if (user.getUserId().equals(userId)) {
                update(userId, "username", username);
                update(userId, "firstName", firstName);
                update(userId, "lastName", lastName);
                update(userId, "email", email);
                update(user.getUserId(), "updated_at", DateTimeUtils.formatStrDateTime(LocalDateTime.now()));
                return true;
            }
        }
        return false;
    }

    /**
     * Reset password By securityPhrase
     * @param account
     * @param securityPhrase
     * @param newPassword
     * @return boolean
     */
    public Boolean resetPasswordBySecurityPhrase(String account, String securityPhrase, String newPassword) {
        UserAccount u = verifySecurityPhrase(account,securityPhrase);
        for (UserAccount user:users) {
            if (u.getUserId().equals(user.getUserId())) {
                update(user.getUserId(), "password", newPassword);
                update(user.getUserId(), "updated_at", DateTimeUtils.formatStrDateTime(LocalDateTime.now()));
                return true;
            }
        }
        return false;
    }

    /**
     * Change Password
     * @param userId
     * @param newPassword
     * @return boolean result
     */
    public Boolean resetPasswordBySecurityPhrase(Integer userId, String newPassword) {
        for (UserAccount user:users) {
            if (user.getUserId().equals(userId)) {
                update(userId, "password", newPassword);
                update(userId, "updated_at", DateTimeUtils.formatStrDateTime(LocalDateTime.now()));
                return true;
            }
        }
        return false;
    }

    /**
     * Change Security Phrase
     * @param userId
     * @param newSecurityPhrase
     * @return boolean result
     */
    public Boolean changeSecurityPhraseById(Integer userId, String newSecurityPhrase) {
        for (UserAccount user:users) {
            if (user.getUserId().equals(userId)) {
                update(userId, "securityPhrase", newSecurityPhrase);
                return true;
            }
        }
        return false;
    }

    // Load user data
    private static void loadUserData() {

        JsonHandler userData = new JsonHandler();
        userData.encode(FileHandler.readFile(USER_ACCOUNT));

        for (int i=0; i<(userData.getAll().size()); i++) {
            JsonHandler obj = new JsonHandler();
            obj.cloneObject(userData.getObject(i));

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
                        case "updated_at" -> {
                            user.setUpdatedAt(DateTimeUtils.formatDateTime(value));
                            return store(userId, "updated_at", value);
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
        userJson.encode(FileHandler.readFile(USER_ACCOUNT));
        return userJson.update(userId, attribute, value, USER_ACCOUNT);
    }

}
