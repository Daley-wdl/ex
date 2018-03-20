package com.exhibition.service;

import com.exhibition.po.OrderAddress;

import java.util.List;

public interface OrderAddressService {

    /**
     * 获取收货地址
     * @param orderAddressId
     * @return
     */
    OrderAddress getOrderAddress(int orderAddressId);

    /**
     * 获取用户的所有使用过的收货地址
     * @param userId
     * @return
     */
    List<OrderAddress> getOrderAddressList(int userId);

    /**
     * 添加收货地址
     * @param orderAddress
     */
    OrderAddress insert(OrderAddress orderAddress);

    void delete(int orderAddressId);

    void update(OrderAddress orderAddress);
}
