package com.exhibition.po;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;
import java.util.List;

public class Order implements java.io.Serializable {

	private Integer orderId;			   		//Id
	private Integer userId;        		//用户id
	@NotNull
	private Integer orderAddressId;	//订单地址id
	@NotNull
	@Pattern(regexp = "^(0|1)$")
	private String payType;		//支付方式 0=线下支付，1=在线支付
	@NotNull
	@Pattern(regexp = "^(2|1|3)$")
	private String shipmentType;	//配送方式 0=快递配送（免运费），1=快递配送（运费）,3=自取(默认方式)
	private Integer shipmentAmount;	//快递费
	private String orderStatus;	//订单状态,1=已完成，0=未完成,-1=已取消
	private Timestamp createTime;  		//创建时间
	private Timestamp updateTime;			//更新时间
	private Integer orderAmount;			//订单金额
	private Integer payAmount;				//支付已支付金额
	private Integer buyNumber;				//商品总数量
	private String payment;				//是否已经付款:0=未付款，1=已付款
	private String isDelivered;      //是否已发货 0=no ，1=yes


	private List<OrderItem> orderItemList;   //订单项


	public Order() {
	}

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

	public Integer getOrderAddressId() {
		return orderAddressId;
	}

	public void setOrderAddressId(Integer orderAddressId) {
		this.orderAddressId = orderAddressId;
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

	public Integer getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Integer orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Integer getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Integer payAmount) {
		this.payAmount = payAmount;
	}

	public Integer getBuyNumber() {
		return buyNumber;
	}

	public void setBuyNumber(Integer buyNumber) {
		this.buyNumber = buyNumber;
	}

	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getIsDelivered() {
		return isDelivered;
	}

	public void setIsDelivered(String isDelivered) {
		this.isDelivered = isDelivered;
	}

	@Override
	public String toString() {
		return "Order{" +
				"orderId=" + orderId +
				", userId=" + userId +
				", orderAddressId=" + orderAddressId +
				", payType='" + payType + '\'' +
				", shipmentType='" + shipmentType + '\'' +
				", shipmentAmount=" + shipmentAmount +
				", orderStatus='" + orderStatus + '\'' +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				", orderAmount=" + orderAmount +
				", payAmount=" + payAmount +
				", buyNumber=" + buyNumber +
				", payment='" + payment + '\'' +
				", isDelivered='" + isDelivered + '\'' +
				", orderItemList=" + orderItemList +
				'}';
	}
}