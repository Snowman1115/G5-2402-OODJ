package com.project.service.Impl;

import com.project.common.constants.AccountStatus;
import com.project.common.constants.MessageConstant;
import com.project.common.constants.UserRoleType;
import com.project.common.utils.Dialog;
import com.project.common.utils.IDGenerator;
import com.project.common.utils.JsonHandler;
import com.project.common.utils.LongIDGenerator;
import com.project.dao.*;
import com.project.pojo.Intake;
import com.project.pojo.UserAccount;
import com.project.service.UserAccountService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UserAccountServiceImpl implements UserAccountService {

    private UserAccountDAO userAccountDAO = new UserAccountDAO();

    private UserAuthenticationDAO userAuthenticationDAO = new UserAuthenticationDAO();

    private UserRoleDAO userRoleDAO = new UserRoleDAO();

    private IntakeDAO intakesDAO = new IntakeDAO();
    private ModuleDAO moduleDAO = new ModuleDAO();
    private SubmissionDAO submissionDAO = new SubmissionDAO();

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
        UserAccountService userAccountImp = new UserAccountServiceImpl();
        System.out.println(userAccountImp.getUsersByRole(UserRoleType.LECTURER).getAll());

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

                for (UserAccount ua : userAccounts) {
                    JSONObject lecturer = new JSONObject();
                    lecturer.put("id", ua.getUserId());
                    lecturer.put("username", ua.getUsername());
                    lecturer.put("first_name", ua.getFirstName());
                    lecturer.put("last_name", ua.getLastName());
                    lecturer.put("email", ua.getEmail());

                    lecturersJson.addObject(lecturer);
                }

                return lecturersJson;
            }
            case PROJECT_MANAGER -> {
                JsonHandler PMsJson = new JsonHandler();

                for (UserAccount ua : userAccounts) {
                    JSONObject projectManager = new JSONObject();
                    projectManager.put("id", ua.getUserId());
                    projectManager.put("username", ua.getUsername());
                    projectManager.put("first_name", ua.getFirstName());
                    projectManager.put("last_name", ua.getLastName());
                    projectManager.put("email", ua.getEmail());

                    PMsJson.addObject(projectManager);
                }

                return PMsJson;
            }
        }

        return null;
    }

    /**
     * get all intake codes
     * @return
     */
    @Override
    public List<String> getAllIntakes() {
        List<String> list = new ArrayList<>();

        for (Intake itk : intakesDAO.getAllIntakes()) {
            list.add(itk.getIntakeCode());
        }

        return list;
    }

    /**
     * request new user id
     * @return
     */
    @Override
    public Integer getNewId() {
        List<Integer> userIds = new ArrayList<>();
        IDGenerator idGenerator = new LongIDGenerator();

        for (UserAccount ua : userAccountDAO.getAllUsers()) {
            userIds.add(ua.getUserId());
        }

        return idGenerator.generateNewID(userIds);
    }

    @Override
    public boolean registerNewUser(JsonHandler userData, UserRoleType roleType) {
        int userId = this.getNewId();
        String defSafeWord = userData.get("first_name")+userData.get("last_name"); //create default safeword

        try {
            userAccountDAO.add(userId, userData.get("username"), userData.get("first_name"), userData.get("last_name"), userData.get("email"), userData.get("password"), defSafeWord);
            userRoleDAO.add(userId, roleType);

            switch (roleType) {
                case STUDENT -> {
                    int intakeId = intakesDAO.getIntakeIdByIntakeCode(userData.get("intake"));
                    LocalDate endDateOfIntake = intakesDAO.getIntakeById(intakeId).getEndDate();
                    List<Integer> modulesInIntake = moduleDAO.getModulesByIntakeId(intakeId);
                    intakesDAO.addNewStudent(intakeId, userId);

                    for (int moduleId : modulesInIntake) {
                        submissionDAO.createSubmission(moduleId, userId, endDateOfIntake);
                    }
                }
                case LECTURER -> {

                }
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
