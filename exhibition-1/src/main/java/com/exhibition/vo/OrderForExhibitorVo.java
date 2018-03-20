package com.exhibition.vo;

import java.sql.Timestamp;

public class OrderForExhibitorVo {
    private Integer orderId;			   		//Id
    private Integer userId;
    private String payType;		//支付方式 0=线下支付，1=在线支付
    private String shipmentType;	//配送方式 1=快递配送（免运费），2=快递配送（运费）,3=自取(默认方式)
    private Integer shipmentAmount;	//快递费
    private String orderStatus;	//订单状态,1=已完成，0=未完成,-1=已取消
    private Timestamp createTime;  		//创建时间
    private Timestamp updateTime;			//更新时间
    private String payment;				//是否已经付款:0=未付款，1=已付款
    private int orderAddressId;     //订单地址id
    /*
    是否已经发货，0=未发货，1=已发货
    */
    private String isDelivered;



    /*
    需要额外查询的信息
     */
    //用户信息
    private String username;


    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getShipmentType() {
        return shipmentType;
    }

    public void setShipmentType(String shipmentType) {
        this.shipmentType = shipmentType;
    }

    public Integer getShipmentAmount() {
        return shipmentAmount;
    }

    public void setShipmentAmount(Integer shipmentAmount) {
        this.shipmentAmount = shipmentAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getOrderAddressId() {
        return orderAddressId;
    }

    public void setOrderAddressId(int orderAddressId) {
        this.orderAddressId = orderAddressId;
    }

    public String getIsDelivered() {
        return isDelivered;
    }

    public void setIsDelivered(String isDelivered) {
        this.isDelivered = isDelivered;
    }

    @Override
    public String toString() {
        return "OrderForExhibitorVo{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", payType='" + payType + '\'' +
                ", shipmentType='" + shipmentType + '\'' +
                ", shipmentAmount=" + shipmentAmount +
                ", orderStatus='" + orderStatus + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", payment='" + payment + '\'' +
                ", orderAddressId=" + orderAddressId +
                ", isDelivered='" + isDelivered + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
