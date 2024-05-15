package com.project.common.utils;

import java.util.List;

public class ShortIDGenerator extends IDGenerator {

    @Override
    public int generateNewID(List<Integer> list) {
        while (true) {
            int randomNumber = random.nextInt(100_000);
            if (idIsCanUse(randomNumber, list)) {
                return randomNumber;
            }
        }
    }

}
