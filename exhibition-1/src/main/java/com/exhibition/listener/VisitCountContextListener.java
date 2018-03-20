package com.exhibition.listener;

import com.exhibition.po.VisitCount;
import com.exhibition.service.VisitCountService;
import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoader;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 当关闭tomcat时，把登录信息刷新到数据库
 */
public class VisitCountContextListener implements ServletContextListener {

    private Logger logger = Logger.getLogger(VisitCountContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        VisitCountService visitCountService = ContextLoader.getCurrentWebApplicationContext().getBean(VisitCountService.class);
        ServletContext context = ContextLoader.getCurrentWebApplicationContext().getServletContext();
        Integer count = (Integer) context.getAttribute(VisitCountShiroListener.VISIT_COUNT);
        Integer onlineCount = (Integer) context.getAttribute(VisitCountShiroListener.ONLINE_COUNT);

        //如果context中的值还没有更新
        if (count == null || onlineCount == null) {
            if (logger.isInfoEnabled()) {
                logger.info("context值为null，无法刷新");
            }
            return;
        }

        if (logger.isInfoEnabled()) {
            logger.info("保存访问人数信息到数据库:total:" + count + "\tmax-online=" + onlineCount);
        }

        //组装需要更新的po
        VisitCount visitCountObj = new VisitCount();
        visitCountObj.setCount(count);
        //更新最大在线人数
        visitCountObj.setMaxOnlineCount(onlineCount);
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
