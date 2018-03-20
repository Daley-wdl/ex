package com.exhibition.service;

import com.exhibition.dto.ListDataDto;
import com.exhibition.po.OrderCanceledItem;
import com.exhibition.po.OrderItem;

import java.util.List;

/**
 * 订单商品
 */
public interface OrderItemService {

    /**
     * 为展商展示订单列表
     * @param exhibitorId    展商的UserID
     * @param completed     是否已经完成
     * @param page
     * @param size
     * @return
     */
    ListDataDto<OrderItem> getOrderItemListForExhibitor(Integer exhibitorId, boolean completed, int page, int size);


    /**
     * 为展商展示订单列表
     * @param orderId    订单id
     * @return
     */
    List<OrderItem> getOrderItemListForUser(Integer orderId);


    /**
     * 为用户显示取消的订单列表
     * @param userId
     * @param page
     * @param size
     * @return
     */
    ListDataDto<OrderCanceledItem> getOrderCanceledItemForUser(int userId,int page,int size);

    /**
     * 设置单件的快递单号
     * @param shipmentId    快递单号
     * @param exhibitorId   展商id
     * @param orderId   订单id
     */
    void setShipmentId(String shipmentId,int exhibitorId,int orderId);

}
