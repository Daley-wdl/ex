package com.exhibition.dao;

import com.exhibition.po.VisitCount;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

@SuppressWarnings("SpringJavaAutowiringInspection")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class VisitCountDaoTest {

    @Autowired
    private VisitCountDao visitCountDao;

    @Test
    public void insert() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017,11,1);

        VisitCount visitCount = new VisitCount();
        visitCount.setCount(8000);
        visitCount.setMaxOnlineCount(500);
        visitCount.setDate(new Date(calendar.getTime().getTime()));
        visitCountDao.insert(visitCount);
    }

    @Test
    public void update() throws Exception {
        VisitCount visitCount = new VisitCount();
        visitCount.setCount(10000);
        visitCount.setMaxOnlineCount(500);
        visitCount.setDate(new Date(System.currentTimeMillis()));
        visitCountDao.update(visitCount);

    }

    @Test
    public void select() throws Exception {
        VisitCount visitCount = visitCountDao.select(new Date(System.currentTimeMillis()));
        System.out.println(visitCount);
    }

    @Test
    public void selectTotalCount() throws Exception {
        int total = visitCountDao.selectTotalCount();
        System.out.println("total:\n" + total);
    }

    @Test
    public void selectListTest() {
        List<VisitCount> list = visitCountDao.selectList(0, 10);
        for (VisitCount visitCount : list) {
            System.out.println(visitCount);
        }
    }

}