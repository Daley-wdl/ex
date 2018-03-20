package com.exhibition.service.impl;

import com.exhibition.constants.OrderConstants;
import com.exhibition.dao.OrderCanceledItemDao;
import com.exhibition.dao.OrderDao;
import com.exhibition.dao.OrderItemDao;
import com.exhibition.dao.ShoppingCartDao;
import com.exhibition.dto.ListDataDto;
import com.exhibition.enums.ExceptionEnums;
import com.exhibition.exceptions.ServiceException;
import com.exhibition.po.Order;
import com.exhibition.po.OrderCanceledItem;
import com.exhibition.po.OrderItem;
import com.exhibition.po.ShoppingCart;
import com.exhibition.service.OrderItemService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
@Transactional
public class OrderItemServiceImpl implements OrderItemService {

    private static final Logger logger = Logger.getLogger(OrderItemServiceImpl.class);

    @Autowired
    private ShoppingCartDao shoppingCartDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    private OrderCanceledItemDao orderCanceledItemDao;


    /**
     * 为展商展示订单列表
     *
     * @param exhibitorId 展商的UserID
     * @param completed  是否已经完成
     * @param page
     * @param size
     * @return
     */
    @Override
    public ListDataDto<OrderItem> getOrderItemListForExhibitor(Integer exhibitorId, boolean completed, int page, int size) {
        if (exhibitorId <= 0) {
            return null;
        }
        size = (size <= 0) ? OrderConstants.DEFAULT_PAGE_SIZE : size;
        page = Math.max(page, 1);
        int start = (page - 1) * size;

        String orderStatus = null;
        if (completed) {
            orderStatus = OrderConstants.ORDER_COMPLETED;
        } else {
            orderStatus = OrderConstants.ORDER_UNCOMPLETED;
        }

        List<OrderItem> orderItemList = orderItemDao.getOrderItemByExhibitorId(exhibitorId, orderStatus, start, size);
        int count = orderItemDao.getCountByExhibitorId(exhibitorId, orderStatus);

        return new ListDataDto<>(orderItemList, count);
    }

    /**
     * 为展商展示订单列表
     *
     * @param orderId    订单id
     * @return
     */
    @Override
    public List<OrderItem> getOrderItemListForUser(Integer orderId) {
        List<OrderItem> orderItemList = orderItemDao.getOrderItemList(orderId);
        return orderItemList;
    }

    /**
     * 为用户显示取消的订单列表
     *
     * @param userId
     * @param page
     * @param size
     * @return
     */
    @Override
    public ListDataDto<OrderCanceledItem> getOrderCanceledItemForUser(int userId, int page, int size) {
        size = (size <= 0) ? OrderConstants.DEFAULT_PAGE_SIZE : size;
        page = Math.max(page, 1);
        int start = (page - 1) * size;
        List<OrderCanceledItem> canceledItemList = orderCanceledItemDao.getOrderCancaledItemForUser(userId, start, size);
        int count = orderCanceledItemDao.getCountForUser(userId);
        return new ListDataDto<>(canceledItemList,count);
    }

    /**
     * 设置单件的快递单号
     *
     * @param shipmentId  快递单号
     * @param exhibitorId 展商id
     * @param orderId     订单id
     */
    @Override
    public void setShipmentId(String shipmentId, int exhibitorId, int orderId) {
        /*
        查询订单中属于该展商的商品
         */
        if (StringUtils.isEmpty(shipmentId)) {
            throw new ServiceException(ExceptionEnums.WrongLength);
        }
        /*
        如果订单中，所有的商品都已经发货，则更新订单发货状态
         */
        List<OrderItem> orderItems = orderItemDao.getOrderItemList(orderId);
        boolean isAllDelivered = true;
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getShipmentId() == null) {
                isAllDelivered = false;
            }
        }
        if (isAllDelivered) {
            orderDao.updateIsDelivered(orderId, OrderConstants.IS_DELIVERED_YES);
        }
        /*
        更新order_item状态
         */
        List<OrderItem> orderItemList = orderItemDao.getOrderItemGroupByExhibitorId(orderId, exhibitorId);
        orderItemList.forEach(orderItem -> {
            if (orderItem.getShipmentId() == null) {
                orderItemDao.setShipmentId(shipmentId,new Timestamp(System.currentTimeMillis()),orderItem.getOrderItemId());
            }
        });
    }
}
