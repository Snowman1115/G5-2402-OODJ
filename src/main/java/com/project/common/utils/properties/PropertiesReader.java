/**
 * Read Properties From application.properties Function
 * @author CHAN HOONG JIAN
 * @version 1.0, Last edited on 2024-03-26
 * @since 2024-03-26
 */

package com.project.common.utils.properties;

import lombok.extern.slf4j.Slf4j;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Slf4j
public class PropertiesReader {

    private static final String FILEPATH = "src/main/resources/application.properties";
    private static final Map<String, String> properties = new HashMap<>();

    static {
        try {
            loadProperties();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Get Property Value
     * @param key
     * @return Selected property key value
     */
    public static String getProperty(String key) {
        return properties.get(key);
    }

    /**
     * Read application.properties
     */
    private static void loadProperties() throws IOException {
        Properties prop = new Properties();
        prop.load(new FileReader(FILEPATH));

        prop.forEach((k,v) -> properties.put((String) k, (String) v));
    }

}
