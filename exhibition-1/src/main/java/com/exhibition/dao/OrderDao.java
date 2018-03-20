package com.exhibition.dao;

import com.exhibition.po.Order;
import com.exhibition.po.OrderItem;
import com.exhibition.vo.OrderForExhibitorVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderDao {

    void insert(Order order);

    void delete(int orderId);

    Order getOrder(int orderId);

    List<Order> getOrderListByUserId(@Param("userId") int userId
            , @Param("start") int start, @Param("size") int size);

    int getCountByUserId(@Param("userId") int userId, @Param("orderStatus") String orderStatus);

    /**
     * 取消订单
     * 将orderStatus修改为取消状态
     * @param orderId
     */
    void updateOrderStatus(@Param("orderId") int orderId,@Param("orderStatus")String orderStatus
        ,@Param("payment")String payment);

    /**
     * 获取为展商显示的订单列表
     * @param exhibitorId
     * @param start
     * @param size
     * @return
     */
    List<OrderForExhibitorVo> getOrdersForExhibitor(@Param("exhibitorId")int exhibitorId
            ,@Param("start") int start,@Param("size") int size);

    /**
     * 获取为展商显示的已取消的订单列表
     * @param exhibitorId
     * @param start
     * @param size
     * @return
     */
    List<OrderForExhibitorVo> getCanceledOrdersForExhibitor(@Param("exhibitorId") int exhibitorId
            , @Param("start") int start, @Param("size") int size);

    /**
     * 获取展商未被取消的订单总数
     * @param exhibitorId
     * @return
     */
    int getCountForExhibitor(@Param("exhibitorId") int exhibitorId);

    /**
     * 获取展商被取消的订单总数
     * @param exhibitorId
     * @return
     */
    int getCanceledCountForExhibitor(@Param("exhibitorId") int exhibitorId);

    /**
     * 更新订单的已支付金额信息
     * @param orderId
     * @param payAmount
     */
    void updatePayAmount(@Param("orderId") int orderId, @Param("payAmount") int payAmount);

    /**
     * 获取用户订单id信息
     * @param userId
     * @param start
     * @param size
     * @return
     */
    List<Integer> getOrderIdForUser(@Param("userId") int userId, @Param("start") int start,@Param("size") int size);

    /**
     * 设置订单是否发货
     * @param orderId
     * @param isDelivered
     */
    void updateIsDelivered(@Param("orderId") int orderId, @Param("isDelivered") String isDelivered);

    /**
     * 获取使用指定订单地址,且未发货的订单数量
     * @param orderAddressId
     * @return
     */
    int getCountByOrderAddressId(int orderAddressId);

}
