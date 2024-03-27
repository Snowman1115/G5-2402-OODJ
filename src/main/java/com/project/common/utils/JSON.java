/**
 * Utils - JSON Object handler
 * @author AU YIK HOE
 * @version 1.0, Last edited on 2024-03-27
 * @since 2024-03-27
 */
package com.project.common.utils;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class JSON {
    private JSONArray users = (JSONArray) new JSONParser().parse(new FileReader("users.txt"));

    public JSON() throws IOException, ParseException {

    }
}
