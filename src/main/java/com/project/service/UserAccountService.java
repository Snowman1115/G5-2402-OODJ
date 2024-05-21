package com.project.service;

import com.project.common.constants.UserRoleType;
import com.project.common.utils.JsonHandler;
import com.project.pojo.UserAccount;
import org.json.simple.JSONObject;

import java.util.List;

public interface UserAccountService {

    /**
     * Login Authentication
     * @param account
     * @param password
     * @return boolean result
     */
    public UserRoleType loginAuthentication(String account, String password);

    /**
     * Update User Profile
     * @param userId
     * @param username
     * @param firstName
     * @param lastName
     * @param email
     * @return boolean result
     */
    boolean updateProfileById(Integer userId, String username, String firstName, String lastName, String email);

    /**
     * Verify User Security Phrase
     * @param account
     * @param securityPhrase
     * @return boolean result
     */
    String verifyUserSecurityPhrase(String account, String securityPhrase);

    /**
     * Verify User Security Phrase
     * @param account
     * @param securityPhrase
     * @param password
     * @return boolean result
     */
    boolean resetUserPasswordBySecurityPhrase(String account, String securityPhrase, String password);

    /**
     * Get user account by userID
     * @param userId
     * @return user account
     */
    UserAccount getUserDetailsByUserId(Integer userId);

    /**
     * Change Password
     * @param userId
     * @param oldPassword
     * @param newPassword
     * @return boolean result
     */
    public Boolean changePasswordById(Integer userId, String oldPassword, String newPassword);

    /**
     * Change Security Phrase
     * @param userId
     * @param oldSecurityPhrase
     * @param confirmSecurityPhrase
     * @return boolean result
     */
    boolean changeSecurityPhraseById(Integer userId, String oldSecurityPhrase, String confirmSecurityPhrase);

    /**
     * get users by role
     * @return user
     */
    public JsonHandler getUsersByRole(UserRoleType roleType);

    /**
     * get all intake codes
     * @return
     */
    public List<String> getAllIntakes();

    /**
     * request new id
     * @return
     */
    public Integer getNewId();
    public boolean registerNewUser(JsonHandler userData, UserRoleType roleType);

}
