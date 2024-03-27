/**
 * Utils - JSON Object handler
 * @author AU YIK HOE
 * @version 1.0, Last edited on 2024-03-27
 * @since 2024-03-27
 */
package com.project.common.utils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class json_handler {
    private JSONArray json_array;
    private JSONObject json_object;
    public json_handler(String json_text) {
        try {
            json_array = (JSONArray) new JSONParser().parse(json_text);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public json_handler(Object object) {
        json_object = (JSONObject) object;
    }
    // JSON array methods
    public JSONArray getAll() {
        return json_array;
    }
    public Object getArrayEle(int index) {
        return  json_array.get(index);
    }

    // JSON object methods
}
