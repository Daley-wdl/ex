package com.exhibition.dao;

import com.exhibition.po.OrderCanceledItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 记录用户取消的订单
 */
public interface OrderCanceledItemDao {

    void insertByBatch(List<OrderCanceledItem> list);

    void delete(int id);

    List<OrderCanceledItem> getOrderCancaledItemForExhibitor(@Param("orderId") Integer orderId
                ,@Param("exhibitorId")Integer exhibitorId);

    int getCountForExhibitor(Integer exhibitorId);

    List<OrderCanceledItem> getOrderCancaledItemForUser(@Param("userId") Integer userId
            ,@Param("start")int start,@Param("size")int size);

    int getCountForUser(Integer userId);

    /**
     * genjuorderId列表获取订单详情信息
     * @param ids
     * @return
     */
    List<OrderCanceledItem> getOrderItemListByOrderIds(List<Integer> ids);

}
