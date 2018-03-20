package com.exhibition.dao;

import com.exhibition.po.Carouse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.*;

@SuppressWarnings("SpringJavaAutowiringInspection")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class CarouseDaoTest {
    
    @Autowired
    private CarouseDao carouseDao;     
    
    
    @Test
    public void insert() throws Exception {
        Carouse carouse = new Carouse();
        carouse.setImgPath("/test");
        carouse.setDetail("测试");
        carouse.setSort(2);
        carouse.setSubmitDate(new Timestamp(System.currentTimeMillis()));
        carouse.setSubmitterName("me");
        carouseDao.insert(carouse);
    }

    @Test
    public void delete() throws Exception {
    }

    @Test
    public void update() throws Exception {
    }

    @Test
    public void getCarouseListForUser() throws Exception {
        List<Carouse> carouseList = carouseDao.getCarouseListForUser(10);
        carouseList.forEach(System.out::println);
    }

}