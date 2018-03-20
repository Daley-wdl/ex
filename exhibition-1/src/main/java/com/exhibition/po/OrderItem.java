package com.exhibition.po;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 订单详情项
 */
public class OrderItem implements Serializable {

    private Integer orderItemId;
    private Integer orderId;           //订单Id
    private Integer exhibitsId;       //展品Id
    private String exhibitsName;     //展品名
    private String picImg;            //展示图片地址
    private Integer price;            //价格
    private Integer buyNumber;
    private Integer productAmount;   //总金额
    private String commentStatus;    //评论状态 0=未评论，1=已评论
    private String shipmentId;      //快递单号
    private Timestamp shipmentTime;     //发货时间
    private String isPayOff;      //是否已经支付 0=未支付，1=已支付

    private Order order;

    public OrderItem() {
    }

    public Integer getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Integer orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getExhibitsId() {
        return exhibitsId;
    }

    public void setExhibitsId(Integer exhibitsId) {
        this.exhibitsId = exhibitsId;
    }

    public String getExhibitsName() {
        return exhibitsName;
    }

    public void setExhibitsName(String exhibitsName) {
        this.exhibitsName = exhibitsName;
    }

    public String getPicImg() {
        return picImg;
    }

    public void setPicImg(String picImg) {
        this.picImg = picImg;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(Integer productAmount) {
        this.productAmount = productAmount;
    }

    public String getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(String commentStatus) {
        this.commentStatus = commentStatus;
    }

    public Integer getBuyNumber() {
        return buyNumber;
    }

    public void setBuyNumber(Integer buyNumber) {
        this.buyNumber = buyNumber;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public Timestamp getShipmentTime() {
        return shipmentTime;
    }

    public void setShipmentTime(Timestamp shipmentTime) {
        this.shipmentTime = shipmentTime;
    }

    public String getIsPayOff() {
        return isPayOff;
    }

    public void setIsPayOff(String isPayOff) {
        this.isPayOff = isPayOff;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "orderItemId=" + orderItemId +
                ", orderId=" + orderId +
                ", exhibitsId=" + exhibitsId +
                ", exhibitsName='" + exhibitsName + '\'' +
                ", picImg='" + picImg + '\'' +
                ", price=" + price +
                ", buyNumber=" + buyNumber +
                ", productAmount=" + productAmount +
                ", commentStatus='" + commentStatus + '\'' +
                ", shipmentId='" + shipmentId + '\'' +
                ", shipmentTime=" + shipmentTime +
                ", isPayOff='" + isPayOff + '\'' +
                ", order=" + order +
                '}';
    }
}
