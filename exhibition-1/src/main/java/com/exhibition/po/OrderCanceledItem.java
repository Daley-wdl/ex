package com.exhibition.po;

import java.sql.Timestamp;

/**
 * 记录用户取消的订单中的商品
 */
public class OrderCanceledItem {
    private Integer id;

    private Integer orderItemId;      //订单商品id
    private Integer orderId;           //订单Id
    private Integer exhibitsId;       //展品Id
    private Integer exhibitorId;
    private Integer userId;
    private Timestamp cancelTime;
    private String picImg;            //展示图片地址
    private String exhibitsName;     //展品名
    private Integer price;            //价格
    private Integer buyNumber;
    private Integer productAmount;   //总金额

    public OrderCanceledItem() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getExhibitorId() {
        return exhibitorId;
    }

    public void setExhibitorId(Integer exhibitorId) {
        this.exhibitorId = exhibitorId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Timestamp getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Timestamp cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getPicImg() {
        return picImg;
    }

    public void setPicImg(String picImg) {
        this.picImg = picImg;
    }

    public String getExhibitsName() {
        return exhibitsName;
    }

    public void setExhibitsName(String exhibitsName) {
        this.exhibitsName = exhibitsName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getBuyNumber() {
        return buyNumber;
    }

    public void setBuyNumber(Integer buyNumber) {
        this.buyNumber = buyNumber;
    }

    public Integer getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(Integer productAmount) {
        this.productAmount = productAmount;
    }

    @Override
    public String toString() {
        return "OrderCanceledItem{" +
                "id=" + id +
                ", orderItemId=" + orderItemId +
                ", orderId='" + orderId + '\'' +
                ", exhibitsId=" + exhibitsId +
                ", exhibitorId=" + exhibitorId +
                ", userId=" + userId +
                ", cancelTime=" + cancelTime +
                ", picImg='" + picImg + '\'' +
                ", exhibitsName='" + exhibitsName + '\'' +
                ", price=" + price +
                ", buyNumber=" + buyNumber +
                ", productAmount=" + productAmount +
                '}';
    }
}
