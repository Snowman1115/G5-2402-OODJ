/**
 * Utils - JSON Object handler
 * @author AU YIK HOE
 * @version 1.0, Last edited on 2024-03-29
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
import java.io.IOException;

@Slf4j
public class JsonHandler {
    private JSONArray json_array;
    private JSONObject json_object;

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
                log.info("Error: " + MessageConstant.ERROR_INVALID_JSON_FORMAT_STRING);
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
        if (decodeType.equals("array")) {
            return json_array.toJSONString();
        } else if (decodeType.equals("object")) {
            return json_object.toJSONString();
        } else {
            log.info("Error: Invalid type");
            return null;
        }
    }

    /**
     * Store json string data into text file
     * @param JSONString
     */
    private void store(String JSONString) {
        try {
            FileWriter fw = new FileWriter(new File(PropertiesReader.getProperty("UserFile")));
            fw.write(JSONString);
            fw.close();
        } catch (Exception e) {
            log.info(e.toString());
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
    public Object getObject(int index) {
        try {
            return json_array.get(index);
        } catch (IndexOutOfBoundsException e) {
            log.info("Error: " + MessageConstant.ERROR_INDEX_OUT_OF_BOUND);
            return null;
        }
    }

    /**
     * Add object into json array
     * @param object
     */
    public void addObject(Object object) {

    }

    /**
     * Update specific object from json array
     * @param objectId
     * @param attribute
     * @param value
     * @return boolean
     */
    public boolean update(Integer objectId, String attribute, String value) {
        JSONObject obj = null;

        for (int i=0; i<(json_array.size()); i++) {
            JSONObject uo = (JSONObject) json_array.get(i);
            int uoId = Integer.parseInt(uo.get("id").toString());
            if (uoId == objectId) {
                obj = uo;
                json_array.remove(json_array.get(i));
            }
        }

        if (obj != null) {
            boolean updateSuccess = switch (attribute) {
                case "username" -> {
                    obj.replace("username", value);
                    json_array.add(obj);

                    yield true;
                }
                case "first_name" -> {
                    obj.replace("first_name", value);
                    json_array.add(obj);

                    yield true;
                }
                case "last_name" -> {
                    obj.replace("last_name", value);
                    json_array.add(obj);

                    yield true;
                }
                case "password" -> {
                    obj.replace("password", value);
                    json_array.add(obj);

                    yield true;
                }
                case "email" -> {
                    obj.replace("email", value);
                    json_array.add(obj);

                    yield true;
                }
                case "safeWord" -> {
                    obj.replace("safeWord", value);
                    json_array.add(obj);

                    yield true;
                }
                default -> {
                    log.info("Error: " + '"' + attribute + '"' + MessageConstant.ERROR_JSON_ATTRIBUTE_NOT_FOUND);
                    yield false;
                }
            };

            // store updated data into text file
            if (updateSuccess) {
                String jsonString = decode("array");
                if (!jsonString.isEmpty()) {
                    store(jsonString);
                } else {
                    log.info("Error: " + MessageConstant.ERROR_JSON_STORE_DATA);
                    return false;
                }
            }

            return updateSuccess;
        } else {
            log.info("Error: " + MessageConstant.ERROR_JSON_OBJECT_NOT_FOUND);
            return false;
        }
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
     * Get value of an attribute in an object in String data type
     * @param attribute
     * @return attribute value (String)
     */
    public String get(String attribute) {
        try {
            String val = json_object.get(attribute).toString();

            if (!val.equals("null")) {
                return val;
            } else {
                return null;
            }
        } catch (NullPointerException e) {
            log.info("Error: " + '"' + attribute + '"' + MessageConstant.ERROR_JSON_ATTRIBUTE_NOT_FOUND);
            return null;
        }
    }

    /**
     * Get set json object
     * @param object
     */
    public void setObject(Object object) {
        json_object = (JSONObject) object;
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
                log.info("Error: " + MessageConstant.ERROR_JSON_NUM_VALUE_PARSER + val);
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
                log.info("Error: " + MessageConstant.ERROR_JSON_NUM_VALUE_PARSER + val);
                return null;
            }
        }
    }

    // ======================== //
    // JSON object methods ends //
    // ======================== //
}
