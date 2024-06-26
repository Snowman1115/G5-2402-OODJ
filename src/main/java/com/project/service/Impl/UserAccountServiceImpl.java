package com.project.service.Impl;

import com.project.common.constants.AccountStatus;
import com.project.common.constants.MessageConstant;
import com.project.common.constants.UserRoleType;
import com.project.common.utils.Dialog;
import com.project.common.utils.IDGenerator;
import com.project.common.utils.JsonHandler;
import com.project.common.utils.LongIDGenerator;
import com.project.dao.*;
import com.project.pojo.*;
import com.project.service.UserAccountService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.*;

@Slf4j
public class UserAccountServiceImpl implements UserAccountService {
    private UserAccountDAO userAccountDAO = new UserAccountDAO();

    private UserAuthenticationDAO userAuthenticationDAO = new UserAuthenticationDAO();

    private UserRoleDAO userRoleDAO = new UserRoleDAO();

    private IntakeDAO intakesDAO = new IntakeDAO();
    private ModuleDAO moduleDAO = new ModuleDAO();
    private SubmissionDAO submissionDAO = new SubmissionDAO();
    private PresentationDAO presentationDAO = new PresentationDAO();
    private ConsultationDAO consultationDAO = new ConsultationDAO();
    private ModuleFeedbackDAO moduleFeedbackDAO = new ModuleFeedbackDAO();

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

        // check User SecurityPhrase
        if (userAccountDAO.checkUserSecurityPhrase(user.getUserId())) {
            Dialog.AlertDialog(MessageConstant.WARNING_DEFAULT_SECURITY_PHRASE);
        }

        switch (userRoleType) {
            case ADMIN -> { log.info("Redirecting (" + account + ") to Admin Panel."); return UserRoleType.ADMIN; }
            case PROJECT_MANAGER -> { log.info("Redirecting (" + account + ") to Project Manager Panel."); return UserRoleType.PROJECT_MANAGER; }
            case LECTURER -> { log.info("Redirecting (" + account + ") to Lecturer Panel."); return UserRoleType.LECTURER; }
            case STUDENT -> { log.info("Redirecting (" + account + ") to Student Panel."); return UserRoleType.STUDENT; }
            default -> { return null; }
        }

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
     * admin update user details
     * @param userId
     * @param firstName
     * @param lastName
     * @return
     */
    @Override
    public boolean updateProfileById(Integer userId, String firstName, String lastName) {
        return userAccountDAO.update(userId, "first_name", firstName) && userAccountDAO.update(userId, "last_name", lastName);
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
     * get user counts
     * @return
     */
    @Override
    public JSONObject getUserCountsByRoles() {
        JSONObject counts = new JSONObject();
        counts.put("student", 0);
        counts.put("lecturer", 0);
        counts.put("pm", 0);
        counts.put("admin", 0);
        List<UserRole> ur =  userRoleDAO.getAllUserRoles();
        for (UserRole r : ur) {
            switch (r.getUserRoleType()) {
                case STUDENT -> {
                    counts.replace("student", (int) counts.get("student") + 1);
                }
                case LECTURER -> {
                    counts.replace("lecturer", (int) counts.get("lecturer") + 1);
                }
                case PROJECT_MANAGER -> {
                    counts.replace("pm", (int) counts.get("pm") + 1);
                }
                case ADMIN -> {
                    counts.replace("admin", (int) counts.get("admin") + 1);
                }
            }
        }
        return counts;
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
                List<Integer> lecturerList = userRoleDAO.filterUserByRole(roleType);

                JsonHandler lecturersJson = new JsonHandler();

                for (UserAccount ua : userAccounts) {
                    if (lecturerList.contains(ua.getUserId())) {
                        JSONObject lecturer = new JSONObject();
                        lecturer.put("id", ua.getUserId());
                        lecturer.put("username", ua.getUsername());
                        lecturer.put("first_name", ua.getFirstName());
                        lecturer.put("last_name", ua.getLastName());
                        lecturer.put("email", ua.getEmail());
                        lecturer.put("roleType", roleType.toString());

                        lecturersJson.addObject(lecturer);
                    }
                }

                return lecturersJson;
            }
            case PROJECT_MANAGER -> {
                List<Integer> pmList = userRoleDAO.filterUserByRole(roleType);
                JsonHandler PMsJson = new JsonHandler();

                for (UserAccount ua : userAccounts) {
                    if (pmList.contains(ua.getUserId())) {
                        JSONObject projectManager = new JSONObject();
                        projectManager.put("id", ua.getUserId());
                        projectManager.put("username", ua.getUsername());
                        projectManager.put("first_name", ua.getFirstName());
                        projectManager.put("last_name", ua.getLastName());
                        projectManager.put("email", ua.getEmail());
                        projectManager.put("roleType", roleType.toString().replace("_", " "));

                        PMsJson.addObject(projectManager);
                    }
                }

                return PMsJson;
            }
            case ADMIN -> {
                List<Integer> adminList = userRoleDAO.filterUserByRole(roleType);
                JsonHandler adminJson = new JsonHandler();

                for (UserAccount ua : userAccounts) {
                    if (adminList.contains(ua.getUserId())  && !ua.getUserId().equals(userAuthenticationDAO.checkUserAuthorization().getUserId())) {
                        JSONObject admin = new JSONObject();
                        admin.put("id", ua.getUserId());
                        admin.put("username", ua.getUsername());
                        admin.put("first_name", ua.getFirstName());
                        admin.put("last_name", ua.getLastName());
                        admin.put("email", ua.getEmail());
                        admin.put("roleType", roleType.toString());

                        adminJson.addObject(admin);
                    }
                }

                return adminJson;
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
            log.info("User Registration: " +
                    "By - " + userAuthenticationDAO.checkUserAuthorization().getUserRoleType() + " " +
                    userAuthenticationDAO.checkUserAuthorization().getUserId() + " " +
                    userAuthenticationDAO.checkUserAuthorization().getUsername());

            userRoleDAO.add(userId, roleType);
            log.info("User Registration: New user registered as " + roleType + ". ");

            if (roleType == UserRoleType.STUDENT) {
                int intakeId = intakesDAO.getIntakeIdByIntakeCode(userData.get("intake"));
                LocalDate endDateOfIntake = intakesDAO.getIntakeById(intakeId).getEndDate();
                List<Integer> modulesInIntake = moduleDAO.getModulesByIntakeId(intakeId);

                intakesDAO.addNewStudent(intakeId, userId);
                log.info("User Registration: New student registered into " + userData.get("intake") + " intake.");

                for (int moduleId : modulesInIntake) {
                    submissionDAO.createSubmission(moduleId, userId, endDateOfIntake);
                    presentationDAO.add(moduleId, 0, userId, endDateOfIntake);
                }
                log.info("User Registration: Module submission slots created for new student.");
                log.info("User Registration: Module presentation slots created for new student.");
            }

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Admin reset user password
     * @param newPassword
     * @return
     */
    @Override
    public boolean resetPassword(int userId, String newPassword) {
        return userAccountDAO.resetPasswordBySecurityPhrase(userId, newPassword);
    }

    @Override
    public boolean remove(UserRoleType roleType, Integer userId) {
        switch (roleType) {
            case STUDENT -> {
                try {
                    List<Map<String, String>> studentSubmissionsList = submissionDAO.getAllSubmissionDetailsByStudentId(userId);
                    for (Map<String, String> i : studentSubmissionsList) {
                        submissionDAO.delete(Integer.parseInt(i.get("id")));
                    }

                    List<Map<String, String>> studentPresentationsList = presentationDAO.getAllPresentationDetailsByStudentId(userId);
                    for (Map<String, String> i : studentPresentationsList) {
                        presentationDAO.delete(Integer.parseInt(i.get("id")));
                    }

                    for (Intake i : intakesDAO.getAllIntakes()) {
                        if (i.getStudentList().contains(userId)) {
                            intakesDAO.removeStudent(i.getIntakeId(), userId);
                        }
                    }

                    consultationDAO.getAllConsultations().removeIf(consultation -> consultation.getStudentId().equals(userId));
                    moduleFeedbackDAO.deleteAllFeedbacksByStudentId(userId);

                    userRoleDAO.remove(userId);
                    userAccountDAO.delete(userId);

                    return true;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return false;
                }

            }
            case LECTURER -> {
                try {
                    consultationDAO.getAllConsultations().removeIf(consultation -> consultation.getLecturerId().equals(userId));

                    List<ProjectModule> modules1 = moduleDAO.getModuleListByFirstMarkerId(userId);
                    List<ProjectModule> modules2 = moduleDAO.getModuleListBySecondMarkerId(userId);

                    for (ProjectModule m : modules1) {
                        moduleDAO.update(m.getModuleId(), "firstMarker", "0");
                    }

                    for (ProjectModule m : modules2) {
                        moduleDAO.update(m.getModuleId(), "secondMarker", "0");
                    }

                    List<Map<String, String>> presentations = presentationDAO.getPresentationByLecturerId(userId);
                    for (Map<String, String> p : presentations) {
                        presentationDAO.update(Integer.parseInt(p.get("id")), "lecturerId", "0");
                    }

                    userRoleDAO.remove(userId);
                    userAccountDAO.delete(userId);

                    return true;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return false;
                }
            }
            case PROJECT_MANAGER, ADMIN -> {
                try {
                    userRoleDAO.remove(userId);
                    userAccountDAO.delete(userId);
                    return true;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * check lecturer availability
     * @param lecturerId
     * @return
     */
    @Override
    public boolean checkLecturerAvailability(int lecturerId) {
        List<ProjectModule> projectModuleList = moduleDAO.getAllModules();
        int counter = 0;

        for (ProjectModule m : projectModuleList) {
            if ((m.getFirstMarker().equals(lecturerId) || m.getSecondMarker().equals(lecturerId)) && m.getEndDate().isAfter(LocalDate.now())) {
                counter++;
            }
        }

        if (counter == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * change pm & lecturer role
     * @param userId
     * @return
     */
    @Override
    public boolean changeRole(int userId) {
        UserRoleType userRole = userRoleDAO.checkRoleType(userId);

        try {
            if (userRole.equals(UserRoleType.LECTURER)) {
                log.info("Staff Role Change: New role - " + UserRoleType.PROJECT_MANAGER);
                return userRoleDAO.update(userId, "roleType", UserRoleType.PROJECT_MANAGER.toString());
            } else {
                log.info("Staff Role Change: New role - " + UserRoleType.LECTURER);
                return userRoleDAO.update(userId, "roleType", UserRoleType.LECTURER.toString());
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }

    }

    @Override
    public boolean checkPMAvailability(int projectManagerId) {
        List<ProjectModule> projectModuleList = moduleDAO.getAllModules();
        int counter = 0;

        for (ProjectModule m : projectModuleList) {
            if (m.getSupervisorId().equals(projectManagerId)) {
                counter++;
            }
        }

        if (counter == 0) {
            return true;
        } else {
            return false;
        }
    }
}
