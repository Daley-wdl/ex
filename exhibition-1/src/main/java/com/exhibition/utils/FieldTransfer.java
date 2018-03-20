package com.exhibition.utils;

import com.exhibition.po.Exhibits;
import com.exhibition.po.Exhibitstore;
import com.exhibition.vo.ExhibitsForSearch;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

//将一个实例的属性赋值到另一个对象的同名属性中
public class FieldTransfer {

    private static Logger logger = Logger.getLogger(FieldTransfer.class);

    /**
     * 将对象中string值为""的属性设置为null
     * @param object
     */
    public static void transStrToNull(Object object) {
        if (object == null) {
            return;
        }
        Class clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        //筛选出类型为String的field
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(object);
                if (value != null && value.getClass().equals(String.class)) {
                    if ("".equals(value)) {
                        field.set(object,null);
                    }
                }
            } catch (IllegalAccessException e) {

            }

        }
    }

    /**
     * 将一个实例的属性赋值到另一个对象的同名属性中
     * <p><strong>复制的字段必须有对应的get和set方法</strong></p>
     * @param from  带有值的实例
     * @param to    待赋值的实例
     * @return
     */
    public static int fieldTrans(Object from, Object to) {
        int count = 0;//成功复制的个数
        Class fromClass = from.getClass();
        Class toClass = to.getClass();
        Field[] fromFields = fromClass.getDeclaredFields();
        Field[] toFields = toClass.getDeclaredFields();
        List<Field> fromFieldList = Arrays.asList(fromFields);
        boolean[] flags = new boolean[toFields.length];
        for (Field item : fromFieldList) {
            item.setAccessible(true);
            String fromName = item.getName();
            for (int i = 0; i < toFields.length; i++) {
                if (flags[i]) {
                    continue;
                }
                Field field = toFields[i];
                field.setAccessible(true);
                if (fromName.equals(field.getName()) && item.getType().equals(field.getType())) {
                    try {
                        flags[i] = true;
                        Object value = item.get(from);
                        if (value != null) {
                            field.set(to, value);
                            count++;
                        }
                        break;
                    } catch (IllegalAccessException e) {
                        if (logger.isDebugEnabled()) {
                            logger.debug(e);
                        }
                    }
                    break;
                }
            }
        }

        return count;
    }

    //测试
    public static void main(String[] args) {
        Exhibits exhibits = new Exhibits();
        ExhibitsForSearch exhibitsForSearch = new ExhibitsForSearch();
        exhibits.setId(5);
        exhibits.setStatus("0");
        exhibits.setExhibitsName("test");
        int count = FieldTransfer.fieldTrans(exhibits, exhibitsForSearch);
        System.out.println(exhibitsForSearch);
        System.out.println("成功:" + count);

    }
}
