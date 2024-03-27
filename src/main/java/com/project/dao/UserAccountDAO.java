package com.project.dao;

import com.project.common.utils.PropertiesReader;
import com.project.common.utils.json_handler;
import com.project.pojo.UserAccount;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserAccountDAO {

    private static final String USER_ACCOUNT = PropertiesReader.getProperty("UserFile");
    private static List<UserAccount> users = new ArrayList<>();

//    public static boolean updateUserProfile(Integer userId,  String xxx) {
//        for (UserAccount user : users) {
//            if (user.getUserId() == userId) {
//                user.setUsername(xxx);
//            }
//        }
//        return true;
//    }

    public static void main(String[] args) throws IOException {
        String json_txt = new BufferedReader(new FileReader(new File(USER_ACCOUNT))).readLine();
        json_handler j_handler = new json_handler(json_txt);

//        System.out.println(j_handler.getAll().size());

        for (int i=0; i<(j_handler.getAll().size() - 1); i++) {
            json_handler obj = new json_handler(j_handler.getArrayEle(i));

            UserAccount ua = new UserAccount();
//            ua.setUserId();
        }
    }

    // JSON UPDATEFILE










}
