package com.exhibition.listener;

import com.exhibition.po.Exhibits;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * @Since: JDK 1.8
 * @Author: ZhaoKunsong
 * @Description: 监听器
 * @Date: 2017/9/20 21:10
 **/
public class MyHttpSessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

        //创建session的同时初始化购物车
        Map<Exhibits, Integer> cartMap = new HashMap<>();
        httpSessionEvent.getSession().setAttribute("cartMap", cartMap);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

    }
}
