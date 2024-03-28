package com.project.dao;

import com.project.common.utils.DateTimeUtils;
import com.project.common.utils.PropertiesReader;
import com.project.common.utils.json_handler;
import com.project.pojo.UserAccount;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserAccountDAO {

    private static final String USER_ACCOUNT = PropertiesReader.getProperty("UserFile");
    private static List<UserAccount> users = new ArrayList<>();

    static {
        loadUserData();
    }

    public static void main(String[] args) {
        UserAccount test = users.get(users.size() - 1);
        System.out.println(test);
    }

    // Load user data
    private static void loadUserData() {
        String json_txt = null;
        try {
            json_txt = new BufferedReader(new FileReader(new File(USER_ACCOUNT))).readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        json_handler j_handler = new json_handler(json_txt);

        for (int i=0; i<(j_handler.getAll().size()); i++) {
            json_handler obj = new json_handler(j_handler.getObject(i));

            UserAccount ua = new UserAccount();
            ua.setUserId(obj.getInt("id"));
            ua.setUsername(obj.get("username"));
            ua.setFirstName(obj.get("first_name"));
            ua.setLastName(obj.get("last_name"));
            ua.setUpdated_at(DateTimeUtils.formatDateTime(obj.get("updated_at")));
            ua.setCreated_at(DateTimeUtils.formatDateTime(obj.get("created_at")));
            users.add(ua);
        }
    }


//    public static boolean updateUserProfile(Integer userId,  String xxx) {
//        for (UserAccount user : users) {
//            if (user.getUserId() == userId) {
//                user.setUsername(xxx);
//            }
//        }
//        return true;
//    }

    // JSON UPDATEFILE










}
