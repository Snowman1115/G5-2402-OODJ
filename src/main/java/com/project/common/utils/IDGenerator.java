package com.project.common.utils;

import java.util.List;
import java.util.Random;

public abstract class IDGenerator {

    protected Random random = new Random();
    public abstract int generateNewID(List<Integer> list);

    protected boolean idIsCanUse(int randomId, List<Integer> list) {
        for (Integer id : list) {
            if (id.equals(randomId)) {
                return false;
            }
        }
        return true;
    }

}
