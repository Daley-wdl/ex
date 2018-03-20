package com.exhibition.vo;

import com.exhibition.po.Order;
import com.exhibition.po.OrderCanceledItem;
import com.exhibition.po.OrderItem;

import java.util.Arrays;
import java.util.List;

/**
 * 为用户展示订单信息的vo
 */
public class OrderInfoForUser {

    private Order order;
    private List<OrderItem> orderItems;
    private List<OrderCanceledItem> canceledItems;

    public OrderInfoForUser() {
    }

    public OrderInfoForUser(Order order, List<OrderItem> orderItems, List<OrderCanceledItem> canceledItems) {
        this.order = order;
        this.orderItems = orderItems;
        this.canceledItems = canceledItems;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public List<OrderCanceledItem> getCanceledItems() {
        return canceledItems;
    }

    public void setCanceledItems(List<OrderCanceledItem> canceledItems) {
        this.canceledItems = canceledItems;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(order.toString());
        stringBuilder.append("\n");
        for (OrderItem orderItem : orderItems) {
            stringBuilder.append("\t").append(orderItem).append("\n");
        }
        for (OrderCanceledItem canceledItem : canceledItems) {
            stringBuilder.append("\t").append(canceledItem).append("\n");
        }
        return stringBuilder.toString();
    }
}
