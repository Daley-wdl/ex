package com.exhibition.utils;

import org.apache.log4j.Logger;

import java.lang.reflect.Field;

public class NameValidUtils {

    private static final Logger logger = Logger.getLogger(NameValidUtils.class);

    /**
     * 验证对象中的String字段是否带有违规内容
     * @param object
     * @return
     */
    public static boolean validStr(Object object) {
        Class<?> klass = object.getClass();
        if (klass.isArray()) {
            //数组类型
            try {
                Object[] array = (Object[]) object;
                if (array.length == 0) {
                    return true;
                }
                Class<?> itemClass = array[0].getClass();
                Field[] fields = itemClass.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                }

                for (Object o : array) {
                    for (Field field : fields) {
                        try {
                            Object value = field.get(o);
                            if (value instanceof String) {
                                boolean passed = validStr(value);
                                if (!passed) {
                                    return false;
                                }
                            }
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {
                if (logger.isInfoEnabled()) {
                    logger.info(e);
                }
            }

        } else {
            Field[] fields = klass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    Object value = field.get(object);
                    if (value instanceof String) {
                        boolean passed = validStr(value);
                        if (!passed) {
                            return passed;
                        }
                    }
                } catch (IllegalAccessException e) {
                    if (logger.isInfoEnabled()) {
                        logger.info(e);
                    }
                }

            }
        }
        return true;
    }

    /**
     * 验证输入的字符串是否带有违规内容
     * @param name
     * @return
     */
    public static boolean validStr(String name) {
        return true;
    }

    public static void main(String[] args) {
        String[] strings = {"123", "456"};
        Class<?> componentType = strings.getClass().getComponentType();
        System.out.println(componentType);
        Object object = strings;
        Object[] array = (Object[]) object;
        for (Object o : array) {
            System.out.println(o);
        }
    }
}
