package com.exhibition.service.impl;

import com.exhibition.dao.OrderAddressDao;
import com.exhibition.po.OrderAddress;
import com.exhibition.service.OrderAddressService;
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
public class OrderAddressServiceImplTest {

    @Autowired
    private OrderAddressService orderAddressService;

    @Test
    public void getOrderAddress() throws Exception {
        int addressId = 3;
        OrderAddress orderAddress = orderAddressService.getOrderAddress(addressId);
        System.out.println(orderAddress);
    }

    @Test
    public void getOrderAddressList() throws Exception {
        List<OrderAddress> orderAddressList = orderAddressService.getOrderAddressList(17);
        orderAddressList.forEach(System.out::println);

    }

    @Test
    public void insert() throws Exception {
        OrderAddress orderAddress = new OrderAddress();
        orderAddress.setUserId(18);
        orderAddress.setUserName("yuan");
        orderAddress.setUserPhone("12345678910");
        orderAddress.setProvinceName("四川");
        orderAddress.setCityName("成都");
        orderAddress.setDistrictName("新都");
        orderAddress.setUserAdress("新都区椪柑中学");
        orderAddress.setUserZipcode("000000");
        orderAddress.setCreateTime(new Timestamp(System.currentTimeMillis()));
        try {
            OrderAddress insert = orderAddressService.insert(orderAddress);
            System.out.println(insert);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void delete() throws Exception {
        int id = 2;
        orderAddressService.delete(id);
    }

    @Test
    public void update() throws Exception {
    }

}