package com.exhibition.others;

import org.junit.Test;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DemoTest {

    @Test
    public void photoPathTest() {

        String s = "#{userId},#{orderAddressId},#{payType},#{shipmentType},#{shipmentAmount},#{orderStatus},#{createTime}\n" +
                "        ,#{orderAmount},#{payAmount},#{buyNumber},#{payment}";
        String[] split = s.split(",");
        System.out.println(split.length);

    }
}
