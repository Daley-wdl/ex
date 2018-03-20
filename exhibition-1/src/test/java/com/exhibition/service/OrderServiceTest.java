package com.exhibition.service;

import com.exhibition.constants.OrderConstants;
import com.exhibition.exceptions.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void addOrder() throws Exception {
        Integer userId = 18;
        Integer[] shoppingCartIds = {14,15,16,17};
        Integer addressId = 3;
        String payType = OrderConstants.PAY_TYPE_OFFLINE;//线下支付
        String shipment = OrderConstants.SHIPMENT_CHARGE;//自取
        try {
            orderService.addOrder(userId, shoppingCartIds, addressId, payType, shipment);
        } catch (Exception e) {
            e.printStackTrace();
        }

        
    }

    @Test
    public void cancelOrder() throws Exception {
        int orderId = 7;
        try {
            orderService.cancelOrder(orderId);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void getOrderList() throws Exception {
    }

}