package com.exhibition.service.impl;

import com.exhibition.po.Carouse;
import com.exhibition.service.CarouseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class CarouseServiceImplTest {

    @Autowired
    private CarouseService carouseService;

    @Test
    public void getCarouseListForUser() throws Exception {
        List<Carouse> carouseList = carouseService.getCarouseListForUser();
        carouseList.forEach(System.out::println);
    }

    @Test
    public void add() throws Exception {
        Carouse carouse = new Carouse();
        carouse.setImgPath("/test2");
        carouse.setDetail("测试2");
        carouse.setSort(2);
        carouse.setSubmitDate(new Timestamp(System.currentTimeMillis()));
        carouse.setSubmitterName("me");
        carouseService.add(carouse);
    }

    @Test
    public void delete() throws Exception {
    }

    @Test
    public void update() throws Exception {
    }

}