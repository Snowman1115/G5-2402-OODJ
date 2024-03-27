package com.project.dao;

import com.project.common.utils.DateTimeUtils;
import com.project.common.utils.PropertiesReader;
import com.project.common.utils.JsonHandler;
import com.project.pojo.UserAccount;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class UserAccountDAO {

    private static final String USER_ACCOUNT = PropertiesReader.getProperty("UserFile");
    private static List<UserAccount> users = new ArrayList<>();

    static {
        loadUserData();
    }

    public static void main(String[] args) throws IOException {
//        Scanner sc = new Scanner(System.in);
//        System.out.println("Enter id: ");
//        int targetID = sc.nextInt();
//        System.out.println("Enter new name: ");
//        String newName = sc.nextLine();
//        updateUsername(targetID, newName);
        System.out.println(users.get(9));
    }

    /**
     * Get user account by username / email
     * @param account
     * @return user account
     */
    public UserAccount getUserAccount(String account) {
        for (UserAccount user:users) {
            if (user.getUsername().equals(account)) {
                return user;
            }
        }
        return null;
    }

    // Load user data
    private static void loadUserData() {
        String json_txt = null;
        try {
            json_txt = new BufferedReader(new FileReader(new File(USER_ACCOUNT))).readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JsonHandler j_handler = new JsonHandler(json_txt);

        for (int i=0; i<(j_handler.getAll().size()); i++) {
            JsonHandler obj = new JsonHandler(j_handler.getObject(i));

            UserAccount ua = new UserAccount();
            ua.setUserId(obj.getInt("id"));
            ua.setUsername(obj.get("username"));
            ua.setFirstName(obj.get("first_name"));
            ua.setLastName(obj.get("last_name"));
            ua.setUpdatedAt(DateTimeUtils.formatDateTime(obj.get("updated_at")));
            ua.setCreatedAt(DateTimeUtils.formatDateTime(obj.get("created_at")));
            users.add(ua);
        }
    }


    public static void updateUsername(Integer userId,  String name) throws IOException {
        for (UserAccount user : users) {
            if (user.getUserId() == userId) {
                user.setUsername(name);
                JsonHandler userJson = new JsonHandler(new BufferedReader(new FileReader(new File(USER_ACCOUNT))).readLine());

            }
        }
    }

    // JSON UPDATEFILE










}
