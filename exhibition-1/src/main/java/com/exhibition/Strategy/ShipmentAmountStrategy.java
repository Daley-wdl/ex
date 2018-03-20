package com.exhibition.Strategy;

import com.exhibition.po.ShoppingCart;

import java.util.List;

/**
 * 使用策略模式实现对快递费的计算
 */
public interface ShipmentAmountStrategy {

    /**
     * 根据用户选择的商品计算快递费
     * @param shoppingCartList
     * @return
     */
    int getShipmentAmount(List<ShoppingCart> shoppingCartList);
}
