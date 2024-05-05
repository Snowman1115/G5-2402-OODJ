package com.project.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class IDGenUtils {

    private static Random random = new Random();
    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {

            System.out.println(generateNewID());
        }
    }

    public static int generateNewID() {
        int id;
        while (true) {
            int randomNumber = random.nextInt(100_000_000);

            return randomNumber;
            /*if (idIsCanUse(randomNumber)) {
                return randomNumber;
            }*/
        }
    }
/*
    public static boolean idIsCanUse(int id) {
        for ()
        return true;
    }*/

}
