package com.project.service.Impl;

import com.project.common.constants.UserRoleType;
import com.project.common.utils.JsonHandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class AdminAccountServiceImpl extends UserAccountServiceImpl {

    /**
     * get admin details
     * @return
     */
    public JsonHandler getAdmins() {
        return super.getUsersByRole(UserRoleType.ADMIN);
    }

    /**
     * get system logs
     * @return
     */
    public String getSystemLogs() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/Data/Logback/logback.log"));
            List<String> logList = new ArrayList<>();

            String line = br.readLine();
            while (line != null && !line.isEmpty()) {
                logList.add(line);
                line = br.readLine();
            }

            return String.join("\n", logList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }
}
