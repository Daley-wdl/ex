package com.exhibition.service;

import com.exhibition.po.ShoppingCart;
import com.exhibition.utils.RandomUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ShoppingCartServiceTest {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Test
    public void add() throws Exception {

        for (int i = 0; i < 10; i++) {
            int userId = 18;
            int exhibitsId = i;;
            int butNumber = 20;
            try {
                shoppingCartService.add(userId,exhibitsId,butNumber);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void delete() throws Exception {
        Integer[] ids = {8};
        shoppingCartService.delete(ids);
    }

    @Test
    public void getShoppingCartList() throws Exception {
        int userId = 18;
        List<ShoppingCart> shoppingCartList = shoppingCartService.getShoppingCartList(userId);
        shoppingCartList.forEach(System.out::println);
    }

    @Test
    public void getCount() throws Exception {
        int userId = 18;
        int count = shoppingCartService.getCount(userId);
        System.out.println(count);
        int count1 = shoppingCartService.getCount(-1);
        System.out.println(count1);
        int count2 = shoppingCartService.getCount(999);
        System.out.println(count2);
    }

    @Test
    public void getShoppingCartByIds() throws Exception {
        Integer[] ids = {8, 9};
        List<ShoppingCart> shoppingCartList = shoppingCartService.getShoppingCartByIds(ids);
        System.out.println(shoppingCartList.size());
        shoppingCartList.forEach(System.out::println);
    }

}