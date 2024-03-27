package com.project.dao;

import com.project.common.utils.PropertiesReader;
import com.project.pojo.UserAccount;

import java.util.ArrayList;
import java.util.List;

public class UserAccountDAO {

    private static final String USER_ACCOUNT = PropertiesReader.getProperty("UserFile");
    private static List<UserAccount> users = new ArrayList<>();

    static {
        loadUserAccount();
    }

    public static boolean updateUserProfile(Integer userId,  String xxx) {
        for (UserAccount user : users) {
            if (user.getUserId() == userId) {
                user.setUsername(xxx);
            }
        }
        return true;
    }
        // JSON UPDATEFILE */


    public static void loadUserAccount(){
        /*// JSON ReadData (USER_ACCOUNf)
        for (int i = 0; i < 10; i++) {
            UserAccount ua = new UserAccount();
            ua.setUserId(userid);
            users.add(ua);
        }*/
    }









}
