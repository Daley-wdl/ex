package com.exhibition.timers;

import com.exhibition.listener.VisitCountShiroListener;
import com.exhibition.po.VisitCount;
import com.exhibition.service.VisitCountService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;

import javax.servlet.ServletContext;

/**
 * 定时更新访问量到数据库中
 */
public class VisitCounterTask {

    private static Logger logger = Logger.getLogger(VisitCounterTask.class);

    @Autowired
    private VisitCountService visitCountService;

    private int maxOnlineCount = 0;//最大在线人数

    public void run() {
        if (logger.isInfoEnabled()) {
            logger.info("调度器开始工作!");
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
                return;
            }

            //更新最大在线人数
            if (onlineCount > maxOnlineCount) {
                maxOnlineCount = onlineCount;
            }

            //组装需要更新的po
            VisitCount visitCountObj = new VisitCount();
            visitCountObj.setCount(visitCount);
            visitCountObj.setMaxOnlineCount(maxOnlineCount);
            java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
            visitCountObj.setDate(date);

            //如果当天还没有记录，则先想数据库新增一条
            if (visitCountService.select(date) == null) {
                visitCountService.insert(visitCountObj);
            } else {
                //更新
                visitCountService.update(visitCountObj);
            }

        }
    }
}
