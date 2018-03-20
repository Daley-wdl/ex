package com.exhibition.service.impl;

import com.exhibition.dao.VisitCountDao;
import com.exhibition.po.VisitCount;
import com.exhibition.service.VisitCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class VisitCountServiceImpl implements VisitCountService {

    @Autowired
    private VisitCountDao visitCountDao;

    @Override
    public void insert(VisitCount visitCount) {
        visitCountDao.insert(visitCount);
    }

    @Override
    public void update(VisitCount visitCount) {
        visitCountDao.update(visitCount);
    }

    /**
     * 根据日期获取当天访问人数和最高同时在线人数
     *
     * @param date
     * @return
     */
    @Override
    public VisitCount select(Date date) {
        return visitCountDao.select(date);
    }

    /**
     * 获取访问次数列表
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public List<VisitCount> selectAll(Integer page, Integer size) {
        Integer start = (page == null || page <= 0 || size==null) ? 0 : (page - 1) * size;
        Integer end = (size == null || size <= 0) ? 20 : size;//如果size不合法，则默认为20
        return visitCountDao.selectList(start,size);
    }

    /**
     * 获取总访问人数
     *
     * @return
     */
    @Override
    public int selectTotal() {
        return visitCountDao.selectTotalCount();
    }
}
