package com.exhibition.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUtils {

    /**
     * 返回 original字符串中匹配正则的字符（matcher.group(groupId) ）
     * @param original
     * @param regex
     * @param groupId
     * @return
     */
    public static String getPatternStr(String original, String regex,int groupId) {
        if (original == null || "".equals(original)) {
            return null;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(original);
        if (matcher.find()) {
            try {
                String result = matcher.group(groupId);
                return result;
            } catch (IndexOutOfBoundsException e) {
                return null;
            }
        }
        return null;
    }
}
