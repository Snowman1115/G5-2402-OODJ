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
     * @return object
     */
    public Object getObject(int index) {
        return  json_array.get(index);
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
                System.out.println("************************************************************************\n\n" +
                        "Invalid method getInt(), Value contains non-numeric characters: " + val +
                        "\n\n************************************************************************");
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
                System.out.println("************************************************************************\n\n" +
                        "Invalid method getDouble(), Value contains non-numeric characters: " + val +
                        "\n\n************************************************************************");
                return null;
            }
        }
    }
}
