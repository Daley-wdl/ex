package com.exhibition.aop;

import com.exhibition.dao.ExhibitiorDao;
import com.exhibition.dao.ExhibitsDao;
import com.exhibition.po.Exhibitor;
import com.exhibition.po.Exhibits;
import com.exhibition.solr.SolrService;
import com.exhibition.utils.FieldTransfer;
import com.exhibition.vo.ExhibitsForSearch;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServerException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

/**
 * ExhibitsService的增强器
 * 在对展品进行增删改时，同步更新到solr中
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Component
@Aspect
public class ExhibitsServiceAdvice {

    private static final Logger logger = Logger.getLogger(ExhibitsServiceAdvice.class);

    @Autowired
    private ExhibitsDao exhibitsDao;
    @Autowired
    private ExhibitiorDao exhibitorDao;
    @Autowired
    private SolrService solrService;

    /**
     * 在ExhibitsService增加展品后，将展品同步加入到Solr引擎中
     * @param joinPoint
     */
    @After("execution(* com.exhibition.service.impl.ExhibitsServiceImpl.addExhibits(..))")
    public void afterAddExhibits(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return;
        }
        Object arg = args[0];
        if (!(arg instanceof Exhibits)) {
            return;
        }
        Exhibits exhibits = (Exhibits) arg;
        Integer userId = exhibits.getExhibitorId();
        Exhibitor exhibitor = exhibitorDao.selectByUserId(userId);
        ExhibitsForSearch exhibitsForSearch = new ExhibitsForSearch();
        FieldTransfer.fieldTrans(exhibits, exhibitsForSearch);
        exhibitsForSearch.setExhibitorName(exhibitor.getRealName());
        exhibitsForSearch.setId(exhibits.getId());
        exhibitsForSearch.setCreatTime(new Date());

        try {
            solrService.add(exhibitsForSearch,true);
        } catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.info("添加展品信息到Solr失败",e);
            }

        }
    }

    /**
     * 删除展品后同步更新到solr
     * @param joinPoint
     */
    @After("execution(* com.exhibition.service.impl.ExhibitsServiceImpl.delete(..))")
    public void afterDeleteExhibits(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        //1、获取唯一参数：展品id
        if (args == null || args.length == 0 || !(args[0] instanceof Integer)) {
            if (logger.isInfoEnabled()) {
                logger.info("更新删除信息到solr失败：缺少id");
            }
            return;
        }
        String exhibitsId = String.valueOf(args[0]);
        try {
            solrService.delete(Arrays.asList(exhibitsId));
        } catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.info(e);
            }
        }
    }

    /**
     * 更新solr展品信息(自动提交)
     * @param joinPoint
     */
    @After("execution(* com.exhibition.service.impl.ExhibitsServiceImpl.update*(..))")
    public void afterUpdateExhibits(JoinPoint joinPoint) {
        Exhibits exhibitsToUpdate = null;
        Object[] args = joinPoint.getArgs();
        //入参不能为空
        if (args == null || args.length == 0) {
            if (logger.isDebugEnabled()) {
                logger.debug("同步展品更新到solr失败：缺少参数");
            }
            return;
        }
        Object firstArg = args[0];
        if (firstArg instanceof Exhibits) {
            //调用update（Exhibits exhibits）更新的情况
            exhibitsToUpdate = (Exhibits) firstArg;
            ExhibitsForSearch exhibitsForSearch = new ExhibitsForSearch();
            FieldTransfer.fieldTrans(exhibitsToUpdate, exhibitsForSearch);
            try {
                solrService.update(exhibitsForSearch,true);
            } catch (IOException|SolrServerException e) {
                if (logger.isDebugEnabled()) {
                    logger.debug(e);
                }
            }
        } else if (firstArg instanceof Integer && args.length==2) {
            //更新展品封面的情况,入参：展品id，新的封面访问路径
            Integer id = (Integer) firstArg;
            Object secondArg = args[1];
            if (!(secondArg instanceof String)) {
                return;
            }
            ExhibitsForSearch exhibitsForSearch = new ExhibitsForSearch();
            exhibitsForSearch.setId(id);
            exhibitsForSearch.setMainPhotoPath((String) secondArg);
            try {
                solrService.update(exhibitsForSearch,true);
            } catch (IOException|SolrServerException e) {
                if (logger.isInfoEnabled()) {
                    logger.info(e);
                }
            }
        } else {
            //其他情况,暂无
        }
    }
}
