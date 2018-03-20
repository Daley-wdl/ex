package com.exhibition.service;

import com.exhibition.dto.ListDataDto;
import com.exhibition.po.Order;
import com.exhibition.po.OrderCanceledItem;
import com.exhibition.po.OrderItem;
import com.exhibition.vo.OrderForExhibitorVo;
import com.exhibition.vo.OrderInfoForUser;
import com.exhibition.vo.OrderList;

import java.util.List;

public interface OrderService {

    /**
     * 将用户购物车中的展品添加至订单中，并清空购物车
     * @param userId
     * @param shoppingCartIdList
     * @param orderAddressId
     * @param payType   支付方式
     * @param shipmentType     配送方式
     */
    Order addOrder(Integer userId,Integer[] shoppingCartIdList,Integer orderAddressId,String payType,String shipmentType);

    /**
     * 取消订单
     * @param orderId
     */
    void cancelOrder(Integer orderId);


    /**
     * 获取展商的订单列表
     * @param exhibitorId
     * @param canceled     订单是否取消
     * @param page
     * @param size
     * @return
     */
    ListDataDto<OrderForExhibitorVo> getOrdersForExhibitor(int exhibitorId, boolean canceled, int page, int size);

    /**
     * 将订单中属于指定展商的物品设置为已付款
     * @param exhibitorId
     * @param orderId
     */
    void updateIsPayOffByExhibitor(int exhibitorId, int orderId);

    /**
     * 将订单的详细项都设置为已付款
     * @param orderId
     */
    void updateIsPayOffByOrder(int orderId);

    /**
     * 获取订单中展商的商品列表
     * @param orderId
     * @param exhibitorId
     * @return
     */
    List<OrderItem> getOrderItemsForExhibitor(int orderId, int exhibitorId);

    /**
     * 获取已经取消的订单中展商的商品列表
     * @param orderId
     * @param exhibitorId
     * @return
     */
    List<OrderCanceledItem> getCanceledOrderItemsForExhibitor(int orderId, int exhibitorId);


    /**
     * 获取用户的订单信息（普通订单和已取消订单）
     * @param userId
     * @param page
     * @param size
     * @return
     */
    List<OrderInfoForUser> getOrderInfoForUser(int userId, int page, int size);

}
