package com.exhibition.dao;

import com.exhibition.po.OrderAddress;

import java.util.List;

public interface OrderAddressDao {

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
    void insert(OrderAddress orderAddress);

    void delete(int id);

    void update(OrderAddress orderAddress);

    /**
     * 获取用户名
     * @param id
     * @return
     */
    String getUserName(int id);

}
