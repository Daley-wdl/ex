package com.exhibition.utils;

import com.exhibition.Strategy.ShipmentAmountStrategy;
import com.exhibition.controller.OrderController;
import com.exhibition.po.ShoppingCart;
import com.exhibition.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用于在生成订单时，计算快递费
 */
@Component
public class ShipmentAmountCounter {
    //ShipmentAmountStrategy
    @Autowired
    private ShipmentAmountStrategy shipmentAmountStrategy;

    public int getShipmentAmount(List<ShoppingCart> shoppingCartList) {
        return shipmentAmountStrategy.getShipmentAmount(shoppingCartList);
    }

}
