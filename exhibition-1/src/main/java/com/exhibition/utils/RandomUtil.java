package com.exhibition.utils;

import java.util.Random;

public class RandomUtil {

    public static final Random random = new Random();

    public static int getRandInt(int start, int end) {
        return random.nextInt(end - start) + start;
    }
}
