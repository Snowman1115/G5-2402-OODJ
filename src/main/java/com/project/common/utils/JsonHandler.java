/**
 * Utils - JSON Object handler
 * @author AU YIK HOE
 * @version 1.0, Last edited on 2024-03-29
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

    // ====================== //
    //    General methods     //
    // ====================== //

    /**
     * Encode string json to JSONArray or JSONObject
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
                log.info("Error: String is not in JSON format");
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
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
            log.info("Error: Array index out of bound");
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
     */
    public void update(int objectId, String attribute, String value) {
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
                    log.info("Error: (" + attribute + ") Attribute does not exist in object");
                    yield false;
                }
            };

            if (updateSuccess) {
                String json_text = json_array.toJSONString();
                System.out.println(json_text);
            }
        } else {
            log.info("Error: Object not found");
        }

    }


    // ======================= //
    // JSON array methods ends //
    // ======================= //


    // ======================= //
    //   JSON object methods   //
    // ======================= //

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
            log.info("Error: Attribute => [" + attribute + "] is not found in object");
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
                log.info("Error: Invalid method getInt(), Value contains non-numeric characters: " + val);
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
                log.info("Error: Invalid method getDouble(), Value contains non-numeric characters: " + val);
                return null;
            }
        }
    }

    // ======================== //
    // JSON object methods ends //
    // ======================== //
}
