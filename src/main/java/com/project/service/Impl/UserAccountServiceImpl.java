package com.project.service.Impl;

import com.project.common.constants.AccountStatus;
import com.project.common.constants.MessageConstant;
import com.project.common.constants.UserRoleType;
import com.project.common.utils.Dialog;
import com.project.common.utils.JsonHandler;
import com.project.dao.IntakeDAO;
import com.project.dao.UserAccountDAO;
import com.project.dao.UserAuthenticationDAO;
import com.project.dao.UserRoleDAO;
import com.project.pojo.Intake;
import com.project.pojo.UserAccount;
import com.project.service.UserAccountService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UserAccountServiceImpl implements UserAccountService {

    private UserAccountDAO userAccountDAO = new UserAccountDAO();

    private UserAuthenticationDAO userAuthenticationDAO = new UserAuthenticationDAO();

    private UserRoleDAO userRoleDAO = new UserRoleDAO();

    private IntakeDAO intakesDAO = new IntakeDAO();

    /**
     * Login Authentication
     * @param account
     * @param password
     * @return boolean result
     */
    @Override
    public UserRoleType loginAuthentication(String account, String password) {
        UserAccount user = userAccountDAO.getUserAccount(account);

        if(user == null){
            Dialog.ErrorDialog(MessageConstant.ERROR_USERNAME_INCORRECT);
            log.info("User (" + account + ") login fail error: " + MessageConstant.ERROR_USERNAME_INCORRECT);
            return null;
        }
        if (!userAccountDAO.verifyPassword(account,password)) {
            Dialog.ErrorDialog(MessageConstant.ERROR_PASSWORD_INCORRECT);
            log.info("User (" + account + ") login fail error: " + MessageConstant.ERROR_PASSWORD_INCORRECT);
            return null;
        }

        AccountStatus accountStatus = userRoleDAO.checkAccountStatus(user.getUserId());
        if(accountStatus == AccountStatus.deActivated) {
            Dialog.ErrorDialog(MessageConstant.ERROR_ACCOUNT_DEACTIVATED);
            log.info("User (" + account + ") login fail error: " + MessageConstant.ERROR_ACCOUNT_DEACTIVATED);
            return null;
        }

        UserRoleType userRoleType = userRoleDAO.checkRoleType(user.getUserId());

        userAuthenticationDAO.insertAuthenticatedUser(user.getUserId(),user.getUsername(),userRoleType,accountStatus);

        log.info("User: (" + account + ") ,Role: (" + userRoleType + ") login successful.");

        switch (userRoleType) {
            case ADMIN -> { log.info("Redirecting (" + account + ") to Admin Panel."); return UserRoleType.ADMIN; }
            case PROJECT_MANAGER -> { log.info("Redirecting (" + account + ") to Project Manager Panel."); return UserRoleType.PROJECT_MANAGER; }
            case LECTURER -> { log.info("Redirecting (" + account + ") to Lecturer Panel."); return UserRoleType.LECTURER; }
            case STUDENT -> { log.info("Redirecting (" + account + ") to Student Panel."); return UserRoleType.STUDENT; }
            default -> { return null; }
        }
    }

    public static void main(String[] args) {
        UserAccountServiceImpl userAccountService = new UserAccountServiceImpl();
        System.out.println(userAccountService.loginAuthentication("student1", "Passw0rd123@"));
    }

    /**
     * Verify User Security Phrase
     * @param account
     * @param securityPhrase
     * @return boolean result
     */
    @Override
    public String verifyUserSecurityPhrase(String account, String securityPhrase) {
        UserAccount user = userAccountDAO.verifySecurityPhrase(account,securityPhrase);
        if (user == null) {
            Dialog.ErrorDialog(MessageConstant.ERROR_SECURITY_PHRASE_N_ACCOUNT_INCORRECT);
            log.info("User (" + account + ") verify fail error: " + MessageConstant.ERROR_SECURITY_PHRASE_N_ACCOUNT_INCORRECT);
            return null;
        }
        return user.getUsername();
    }

    /**
     * Reset User Password - FORGET PASSWORD
     * @param account
     * @param securityPhrase
     * @return boolean result
     */
    @Override
    public boolean resetUserPasswordBySecurityPhrase(String account, String securityPhrase, String password) {
        if (!userAccountDAO.resetPasswordBySecurityPhrase(account,securityPhrase,password)) {
            Dialog.ErrorDialog(MessageConstant.ERROR_SECURITY_PHRASE_N_ACCOUNT_INCORRECT);
            log.info("User (" + account + ") reset password fail error: " + MessageConstant.ERROR_SECURITY_PHRASE_N_ACCOUNT_INCORRECT);
            return false;
        }
        Dialog.SuccessDialog(MessageConstant.SUCCESS_RESET_PASSWORD);
        log.info("User (" + account + ") reset password successful");
        return true;
    }

    /**
     * Get user account by userID
     * @param userId
     * @return user account
     */
    @Override
    public UserAccount getUserDetailsByUserId(Integer userId) {
        UserAccount user = userAccountDAO.getUserAccountById(userId);
        if (user == null) {
            Dialog.ErrorDialog(MessageConstant.ERROR_USER_NOT_FOUND);
            log.info("User (" + userId + ") not found. " + MessageConstant.ERROR_USER_NOT_FOUND);
            return null;
        }
        return user;
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
    @Override
    public boolean updateProfileById(Integer userId, String username, String firstName, String lastName, String email) {
        Boolean result;
        result = userAccountDAO.checkUsernameOrEmailUsability(userId, username);
        if (!result) {
            Dialog.ErrorDialog(MessageConstant.ERROR_USERNAME_ALREADY_EXIST); log.info("User update fail. - " + MessageConstant.ERROR_USERNAME_ALREADY_EXIST); return false;
        }
        result = userAccountDAO.checkUsernameOrEmailUsability(userId, email);
        if (!result) {
            Dialog.ErrorDialog(MessageConstant.ERROR_EMAIL_ALREADY_EXIST); log.info("User update fail. - " + MessageConstant.ERROR_EMAIL_ALREADY_EXIST); return false;
        }
        Boolean isUpdated = userAccountDAO.updateUserProfileById(userId, username, firstName, lastName, email);
        if (!isUpdated) {
            Dialog.ErrorDialog(MessageConstant.UNEXPECTED_ERROR); log.info("User update fail. - " + MessageConstant.UNEXPECTED_ERROR); return false;
        };
        Dialog.SuccessDialog(MessageConstant.SUCCESS_PROFILE_UPDATE_SUCCESSFUL);
        log.info("User {} update success", userId);
        return true;
    }

    /**
     * Change Password
     * @param userId
     * @param oldPassword
     * @param newPassword
     * @return boolean result
     */
    @Override
    public Boolean changePasswordById(Integer userId, String oldPassword, String newPassword) {
        if (!userAccountDAO.verifyPasswordById(userId,oldPassword)) {
            Dialog.ErrorDialog(MessageConstant.ERROR_PASSWORD_INCORRECT); log.info("User change password fail. - " + MessageConstant.ERROR_PASSWORD_INCORRECT); return false;
        }
        Dialog.SuccessDialog(MessageConstant.SUCCESS_PASSWORD_UPDATE_SUCCESSFUL);
        log.info("User {} password update successful.", userId);
        return userAccountDAO.resetPasswordBySecurityPhrase(userId, newPassword);
    }

    /**
     * Change Security Phrase
     * @param userId
     * @param oldSecurityPhrase
     * @param confirmSecurityPhrase
     * @return boolean result
     */
    @Override
    public boolean changeSecurityPhraseById(Integer userId, String oldSecurityPhrase, String confirmSecurityPhrase) {
        if (!userAccountDAO.verifySecurityPhraseById(userId,oldSecurityPhrase)) {
            Dialog.ErrorDialog(MessageConstant.ERROR_SECURITY_PHRASE_INCORRECT); log.info("User change security phrase fail. - " + MessageConstant.ERROR_SECURITY_PHRASE_INCORRECT); return false;
        }
        Dialog.SuccessDialog(MessageConstant.SUCCESS_SECURITY_PHRASE_UPDATE_SUCCESSFUL);
        log.info("User {} security phrase update successful.", userId);
        return userAccountDAO.changeSecurityPhraseById(userId, confirmSecurityPhrase);
    }

    /**
     * get users by role
     * @param roleType
     * @return Userlist
     */
    @Override
    public JsonHandler getUsersByRole(UserRoleType roleType) {
        List<Integer> userIds = userRoleDAO.filterUserByRole(roleType);
        List<UserAccount> userAccounts = userAccountDAO.getUserAccountById(userIds);

        switch (roleType) {
            case STUDENT -> {
                JsonHandler studentsJson = new JsonHandler();

                for (Intake itk : intakesDAO.getAllIntakes()) {
                    String intake_code = itk.getIntakeCode();
                    List<Integer> students = itk.getStudentList();

                    for (UserAccount ua : userAccounts) {
                        int student_id = ua.getUserId();

                        if (students.contains(student_id)) {
                            JSONObject studentJsonObject = new JSONObject();
                            studentJsonObject.put("id", ua.getUserId());
                            studentJsonObject.put("username", ua.getUsername());
                            studentJsonObject.put("firstName", ua.getFirstName());
                            studentJsonObject.put("lastName", ua.getLastName());
                            studentJsonObject.put("intake", intake_code);

                            studentsJson.addObject(studentJsonObject);
                        }
                    }
                }

                return studentsJson;
            }
            case LECTURER -> {
                JsonHandler lecturersJson = new JsonHandler();
            }
        }

        return null;
    }

    @Override
    public List<String> getAllIntakes() {
        List<String> list = new ArrayList<>();

        for (Intake itk : intakesDAO.getAllIntakes()) {
            list.add(itk.getIntakeCode());
        }

        return list;
    }
}
