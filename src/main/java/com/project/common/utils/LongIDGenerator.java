package com.project.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class LongIDGenerator extends IDGenerator{

    @Override
    public int generateNewID(List<Integer> list) {
        while (true) {
            int randomNumber = random.nextInt(100_000_000);
            if (idIsCanUse(randomNumber, list)) {
                return randomNumber;
            }
        }
    }

}
