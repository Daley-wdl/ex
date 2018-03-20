package com.exhibition.dao;

import com.exhibition.po.VisitCount;
import org.apache.ibatis.annotations.Param;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * 统计每天的在线访问人数和总浏览人数
 */
public interface VisitCountDao {

    void insert(VisitCount visitCount);

    void update(VisitCount visitCount);

    /**
     * 根据日期查询当天的访问量和最大在线人数
     * @param data
     * @return
     */
    VisitCount select(Date data);

    List<VisitCount> selectList(@Param("start")int start,@Param("end")int end);

    int selectTotalCount();
}
