/**
 * Utils - JSON Object handler
 * @author AU YIK HOE
 * @version 1.0, Last edited on 2024-03-28
 * @since 2024-03-27
 */
package com.project.common.utils;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
@Slf4j
public class JsonHandler {
    private JSONArray json_array;
    private JSONObject json_object;
    public JsonHandler(String json_text) {
        try {
            json_array = (JSONArray) new JSONParser().parse(json_text);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public JsonHandler(Object object) {
        json_object = (JSONObject) object;
    }
    // ======== JSON array methods ========
    /**
     * Get json array
     * @return json array
     */
    public JSONArray getAll() {
        return json_array;
    }

    /**
     * Get specific object from json array
     * @param index
     * @return object
     */
    public Object getObject(int index) {
        return  json_array.get(index);
    }

    /**
     * Update specific object from json array
     * @param objectId
     * @param attribute
     * @param value
     */
    public void update(int objectId, String attribute, String value) {
        for (Object o : json_array) {
            JSONObject uo = (JSONObject) o;
            int uoId = Integer.parseInt(uo.get("id").toString());
            if (uoId == objectId) {
                json_array.remove(uo);

                switch (attribute) {
                    case "username" -> {
                        uo.replace("username", value);
                        json_array.add(uo);
                    }
                    case "first_name" -> {
                        uo.replace("first_name", value);
                        json_array.add(uo);
                    }
                    case "last_name" -> {
                        uo.replace("last_name", value);
                        json_array.add(uo);
                    }
                    default -> {
                        log.info("(" + attribute + ") Attribute not found.");
                    }
                }

                break;
            }
        }


    }

    // ======== JSON object methods ========
    /**
     * Get value of an attribute in an object in String data type
     * @return attribute value (String)
     */
    public String get(String attribute) {
        String val = json_object.get(attribute).toString();

        if (!val.equals("null")) {
            return val;
        } else {
            return null;
        }
    }

    /**
     * Get value of an attribute in an object in Integer data type
     * @return attribute value (Integer)
     */
    public Integer getInt(String attribute) {
        String val = json_object.get(attribute).toString();

        if (val.equalsIgnoreCase("null")) {
            return null;
        } else {
            // Check if value contains non-numeric characters, return null if true
            try {
                return Integer.parseInt(val);
            } catch (NumberFormatException e) {
                log.info("Invalid method getInt(), Value contains non-numeric characters: " + val);
                return null;
            }
        }
    }

    /**
     * Parse String Date To LocalDate
     * @return attribute value (Double)
     */
    public Double getDouble(String attribute) {
        String val = json_object.get(attribute).toString();

        if (val.equalsIgnoreCase("null")) {
            return null;
        } else {
            // Check if value contains non-numeric characters, return null if true
            try {
                return Double.parseDouble(val);
            } catch (NumberFormatException e) {
                log.info("Invalid method getDouble(), Value contains non-numeric characters: " + val);
                return null;
            }
        }
    }
}
