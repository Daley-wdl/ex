package com.exhibition.listener;

import com.exhibition.po.VisitCount;
import com.exhibition.service.VisitCountService;
import org.apache.log4j.Logger;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;

import javax.servlet.ServletContext;
import java.sql.Date;

/**
 * 记录当日访问量和最大同时在线人数
 * 在shiro.xml中通过bean标签配置
 */
public class VisitCountShiroListener extends SessionListenerAdapter {

    private static Logger logger = Logger.getLogger(VisitCountShiroListener.class);

    private VisitCountService visitCountService;

    public static final String VISIT_COUNT = "visit_count";
    public static final String ONLINE_COUNT = "online_count";

    private volatile int maxOnlineCount = 0;

    private long lastSaveTime = System.currentTimeMillis();//上次更新数据库的时间
    public static final int FRESH_DB_MIN = 10;//每隔FRESH_DB_MIN分钟刷新一次数据到数据库(如果有新的session创建)

    @Override
    public void onStart(Session session) {
        super.onStart(session);
        ServletContext context = ContextLoader.getCurrentWebApplicationContext().getServletContext();

        //设置访问总人数-->cintext,在线人数-->cintext
        Integer visit_count = (Integer) context.getAttribute(VISIT_COUNT);
        Integer online_count = (Integer) context.getAttribute(ONLINE_COUNT);

        if (visit_count == null || online_count==null) {
            VisitCountService visitCountService = ContextLoader.getCurrentWebApplicationContext().getBean(VisitCountService.class);
            if (visitCountService == null) {
                if (logger.isInfoEnabled()) {
                    logger.info("无法获取VisitCountService，初始化访问量失败");
                }
            } else {
                VisitCount visitCount = visitCountService.select(new Date(System.currentTimeMillis()));
                visit_count = (visitCount == null) ? 0 : visitCount.getCount();
                maxOnlineCount = (visitCount == null) ? 0 : visitCount.getMaxOnlineCount();
                online_count = 0;
            }
            context.setAttribute(VISIT_COUNT, visit_count + 1);
            context.setAttribute(ONLINE_COUNT, online_count + 1);
        } else {
            Integer value = visit_count + 1;
            context.setAttribute(VISIT_COUNT, value);
            value = online_count + 1;
            context.setAttribute(ONLINE_COUNT, value);
            maxOnlineCount = (maxOnlineCount<online_count)?online_count:maxOnlineCount;
        }

        if (logger.isInfoEnabled()) {
            logger.info("online_count:" + online_count + "\tvisit_count:" + visit_count);
        }

        //如果距离上次刷新数据库的时间超过10分钟，就刷新数据到数据库
        if ((System.currentTimeMillis() - lastSaveTime) > (1000 * 60 * FRESH_DB_MIN)) {
            if (logger.isInfoEnabled()) {
                logger.info("刷新访问量到数据库!");
            }
            saveToDB();
        }
    }

    @Override
    public void onStop(Session session) {
        super.onStop(session);
        ServletContext context = ContextLoader.getCurrentWebApplicationContext().getServletContext();
        //更新在线人数
        Object obj = context.getAttribute(ONLINE_COUNT);
        if (obj == null) {
            context.setAttribute(ONLINE_COUNT, Integer.valueOf(0));
        } else {
            Integer value = (Integer) obj;
            context.setAttribute(ONLINE_COUNT, value - 1);
        }
    }

    /**
     * 更新访问量等信息到数据库
     */
    public void saveToDB() {
        if (visitCountService == null) {
            visitCountService = ContextLoader.getCurrentWebApplicationContext().getBean(VisitCountService.class);
        }

        ServletContext context = ContextLoader.getCurrentWebApplicationContext().getServletContext();
        if (context == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("刷新访问量信息到数据库失败，ServletContext为null");
            }
        } else {
            if (logger.isInfoEnabled()) {
                logger.info("刷新访问量信息到数据库");
            }
            Integer visitCount = (Integer) context.getAttribute(VisitCountShiroListener.VISIT_COUNT);
            Integer onlineCount = (Integer) context.getAttribute(VisitCountShiroListener.ONLINE_COUNT);

            //如果context中的值还没有更新
            if (visitCount == null || onlineCount == null) {
                if (logger.isInfoEnabled()) {
                    logger.info("visitCount或onlineCount值为null，无法刷新");
                }
                return;
            }

            //组装需要更新的po
            VisitCount visitCountObj = new VisitCount();
            visitCountObj.setCount(visitCount);
            //更新最大在线人数
            if (onlineCount > maxOnlineCount) {
                maxOnlineCount = onlineCount;
                visitCountObj.setMaxOnlineCount(maxOnlineCount);
            }
            java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
            visitCountObj.setDate(date);

            //如果当天还没有记录，则先向数据库新增一条
            VisitCount visitCountDB = visitCountService.select(date);
            if (visitCountDB == null) {
                visitCountService.insert(visitCountObj);
            } else {
                //更新
                if (visitCountObj.getMaxOnlineCount() <= visitCountDB.getMaxOnlineCount()) {
                    //如果当前最大在线访问量小于数据库中的值
                    visitCountObj.setMaxOnlineCount(visitCountDB.getMaxOnlineCount());
                }
                visitCountService.update(visitCountObj);
            }

        }
    }

}
