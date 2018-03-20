package com.exhibition.service;

import com.exhibition.po.VisitCount;

import java.sql.Date;
import java.util.List;

public interface VisitCountService {

    void insert(VisitCount visitCount);

    void update(VisitCount visitCount);

    /**
     * 根据日期获取当天访问人数和最高同时在线人数
     * @param date
     * @return
     */
    VisitCount select(Date date);

    /**
     * 获取访问次数列表
     * @param page
     * @param size
     * @return
     */
    List<VisitCount> selectAll(Integer page, Integer size);

    /**
     * 获取总访问人数
     * @return
     */
    int selectTotal();
}
