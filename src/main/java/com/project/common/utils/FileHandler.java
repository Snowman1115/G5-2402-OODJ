package com.project.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Slf4j
public class FileHandler {

    /**
     * Read text file
     * @param filePath
     * @return String
     */
    public static String readFile(String filePath) {
        try {
            return new BufferedReader(new FileReader(filePath)).readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
