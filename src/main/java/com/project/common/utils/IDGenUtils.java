package com.project.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Random;

@Slf4j
public class IDGenUtils {

    private static Random random = new Random();

    public static int generateNewID(List<Integer> list) {
        while (true) {
            int randomNumber = random.nextInt(100_000_000);
            if (idIsCanUse(randomNumber, list)) {
                return randomNumber;
            }
        }
    }
    private static boolean idIsCanUse(int randomId, List<Integer> lists) {
        for (Integer id : lists) {
            if (id.equals(randomId)) {
                return false;
            }
        }
        return true;
    }

}
