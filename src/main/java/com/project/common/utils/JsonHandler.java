/**
 * Utils - JSON Object handler
 * @author AU YIK HOE
 * @version 1.0, Last edited on 2024-03-31
 * @since 2024-03-27
 */
package com.project.common.utils;
import com.project.common.constants.MessageConstant;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileWriter;

@Slf4j
public class JsonHandler {
    private JSONArray json_array = new JSONArray();
    private JSONObject json_object = new JSONObject();

    // ====================== //
    //    General methods     //
    // ====================== //

    /**
     * Object constructor and encode text json into JSONObject or JSONArray
     * @param jsonData
     */
    public void encode(String jsonData) {
        JSONParser parser = new JSONParser();
        try {
            if (parser.parse(jsonData) instanceof JSONObject) {
                json_object = (JSONObject) parser.parse(jsonData);
            } else if (parser.parse(jsonData) instanceof JSONArray) {
                json_array = (JSONArray) parser.parse(jsonData);
            } else {
                log.error("Error: " + MessageConstant.ERROR_INVALID_JSON_FORMAT_STRING);
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Decode json array or json object to json string
     * @param decodeType
     * @return
     */
    public String decode(String decodeType) {
        try {
            if (decodeType.equals("array")) {
                return json_array.toJSONString();
            } else if (decodeType.equals("object")) {
                return json_object.toJSONString();
            } else {
                log.error("Error: Invalid type");
                return null;
            }
        } catch (NullPointerException e) {
            switch (decodeType) {
                case "array" -> { log.error("Error: JSON Array is null"); }
                case "object" -> { log.error("Error: JSON Object is null"); }
            }
            return null;
        }
    }

    /**
     * Store json string data into text file
     * @param JSONString
     */
    private void store(String JSONString, String filePath) {
        try {
            FileWriter fw = new FileWriter(new File(filePath));
            fw.write(JSONString);
            fw.close();
        } catch (Exception e) {
            log.error(e.toString());
        }
    }

    // ====================== //
    //  General methods ends  //
    // ====================== //


    // ======================= //
    //    JSON array methods   //
    // ======================= //

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
    public JSONObject getObject(int index) {
        try {
            return (JSONObject) json_array.get(index);
        } catch (IndexOutOfBoundsException e) {
            log.error("Error: " + MessageConstant.ERROR_INDEX_OUT_OF_BOUND);
            return null;
        } catch (NullPointerException e) {
            log.error("Error: JSON Array is null");
            return null;
        }
    }

    /**
     * Add object into json array (direct update to save file)
     * @param object
     */
    public void addObject(JSONObject object, String filePath) {
        json_array.add(object);
        store(json_array.toJSONString(), filePath);
    }

    public void addObject(JSONObject object) {
        json_array.add(object);
    }

    /**
     * Update specific object from json array
     * @param objectId
     * @param attribute
     * @param value
     * @param filePath
     * @return boolean
     */
    public boolean update(Integer objectId, String attribute, String value, String filePath) {

        JSONObject obj = null;

        for (int i=0; i<(json_array.size()); i++) {
            JSONObject uo = (JSONObject) json_array.get(i);
            int uoId = Integer.parseInt(uo.get("id").toString());
            if (uoId == objectId) {
                obj = uo;
                json_array.remove(json_array.get(i));
            }
        }

        if (obj != null && obj.containsKey(attribute)) {
            obj.replace(attribute, value);
            json_array.add(obj);
            String jsonString = decode("array");
            if (!jsonString.isEmpty()) {
                store(jsonString, filePath);
                return true;
            } else {
                log.error("Error: " + MessageConstant.ERROR_JSON_STORE_DATA);
                return false;
            }
        } else if (obj != null && !obj.containsKey(attribute)) {
            log.error("Error: " + '"' + attribute + '"' + " " + MessageConstant.ERROR_JSON_ATTRIBUTE_NOT_FOUND);
            return false;
        } else {
            log.error("Error: " + MessageConstant.ERROR_JSON_OBJECT_NOT_FOUND);
            return false;
        }
    }

    /**
     * Remove/Delete record
     * @param objectId
     * @param filePath
     * @return boolean
     */
    public boolean delete(Integer objectId, String filePath) {
        for (int i=0; i<(json_array.size()); i++) {
            JSONObject uo = (JSONObject) json_array.get(i);
            int uoId = Integer.parseInt(uo.get("id").toString());
            if (uoId == objectId) {
                json_array.remove(uo);

                store(json_array.toJSONString(), filePath);
                return true;
            }
        }

        log.error("Error: " + MessageConstant.ERROR_JSON_OBJECT_NOT_FOUND);
        return false;
    }


    // ======================= //
    // JSON array methods ends //
    // ======================= //


    // ======================= //
    //   JSON object methods   //
    // ======================= //

    /**
     * Return the json object
     * @return JSONObject
     */
    public JSONObject object() {
        return json_object;
    }

    /**
     * Get set json object
     * @param object
     */
    public void setObject(JSONObject object) {
        json_object = object;
    }

    /**
     * Get value of an attribute in an object in String data type
     * @param attribute
     * @return attribute value (String)
     */
    public String get(String attribute) {
        Object val = json_object.get(attribute);

        if (json_object.containsKey(attribute)) {
            return (String) val;
        } else {
            log.error("Error: \"" + attribute + "\" " + MessageConstant.ERROR_JSON_ATTRIBUTE_NOT_FOUND);
            throw new RuntimeException(MessageConstant.ERROR_JSON_ATTRIBUTE_NOT_FOUND);
        }
    }

    /**
     * Get value of an attribute in an object in Integer data type
     * @param attribute
     * @return attribute value (Integer)
     */
    public Integer getInt(String attribute) {
        String val = json_object.get(attribute).toString();

        if (val.equalsIgnoreCase("null")) {
            return null;
        } else {
            try {
                return Integer.parseInt(val);
            } catch (NumberFormatException e) {
                log.error("Error: " + MessageConstant.ERROR_JSON_NUM_VALUE_PARSER + val);
                return null;
            }
        }
    }

    /**
     * Get value of an attribute in an object in Double data type
     * @param attribute
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
                log.error("Error: " + MessageConstant.ERROR_JSON_NUM_VALUE_PARSER + val);
                return null;
            }
        }
    }

    // ======================== //
    // JSON object methods ends //
    // ======================== //
}
