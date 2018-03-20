package com.exhibition.dao;

import com.exhibition.po.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

public interface OrderItemDao {

    void insertByBatch(List<OrderItem> list);

    void insert(OrderItem orderItem);

    void deleteByBatch(List<Integer> list);

    void deleteByOrderId(int orderId);

    /**
     * 获取订单包含的商品信息
     * @param orderId
     * @return
     */
    List<OrderItem> getOrderItemList(int orderId);

    /**
     * 根据id列表获取订单详情信息
     * @param ids
     * @return
     */
    List<OrderItem> getOrderItemListByOrderIds(List<Integer> ids);

    /**
     * 获取展商的订单列表
     *
     * @param exhibitorId
     * @param orderStatus 订单状态
     * @param start
     * @param size
     * @return
     */
    List<OrderItem> getOrderItemByExhibitorId(@Param("exhibitorId") int exhibitorId, @Param("orderStatus") String orderStatus
            , @Param("start") int start, @Param("size") int size);

    int getCountByExhibitorId(@Param("exhibitorId") int exhibitorId, @Param("orderStatus") String orderStatus);

    /**
     * 设置单件的快递单号
     * @param shipmentId    快递单号
     * @param orderItemId   订单详情表id
     */
    void setShipmentId(@Param("shipmentId") String shipmentId ,@Param("shipmentTime") Timestamp shipmentTime
            ,@Param("orderItemId") int orderItemId);

    /**
     * 获取订单中指定展商的展品列表
     * @param orderId
     * @param exhibitorId
     * @return
     */
    List<OrderItem> getOrderItemGroupByExhibitorId(@Param("orderId") int orderId, @Param("exhibitorId") int exhibitorId);

    /**
     * 获取订单中，展商的展品总价
     * @param orderId
     * @return
     */
    int getTotalPriceForExhibitor(@Param("orderId") int orderId, @Param("exhibitorId") int exhibitorId);

    /**
     * 将订单中属于指定展商的物品设置为已付款
     * @param exhibitorId
     * @param orderId
     */
    void updateIsPayOffByExhibitor(@Param("exhibitorId") int exhibitorId,@Param("orderId") int orderId);

    /**
     * 将订单的详细项都设置为已付款
     * @param orderId
     */
    void updateIsPayOffByOrder(int orderId);

    /**
     * 更新订单的评论状态
     * @param itemId    订单id
     * @param status    评论状态
     */
    void updateCommentStatus(@Param("itemId") int itemId, @Param("status") String status);

    /**
     * 获取快递单号
     * @param orderId
     * @return
     */
    List<String> getShipmentIdListByOrderId(int orderId);

}
