package com.exhibition.po;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;


public class Comment implements Serializable {

	private Integer id;
	private Integer userId;              //评论者
	@NotNull
	private Integer productId;					//评论的商品
	private Timestamp commentDate;				//评论时间
	@NotNull
	@Size(max = 300,min = 1)
	private String commentContent;				//评论内容
	@NotNull
	@Range(min = 1,max = 5)
	private Integer productGrade;				//总体评分   0-差评    1-中评    2-好评
	private String photoPath;					//与评论有关的图片
	private String status;						//状态  0-有效 1-无效

	private String username;	//评论者用户名
	private String exhibitsName;	//评论的商品名
	/*
	订单item的id
	*/
	@NotNull
	private Integer orderItemId;


	public String getUserName() {
		return username;
	}

	public void setUserName(String userName) {
		this.username = userName;
	}

	public String getExhibitsName() {
		return exhibitsName;
	}

	public void setExhibitsName(String exhibitsName) {
		this.exhibitsName = exhibitsName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Timestamp getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Timestamp commentDate) {
		this.commentDate = commentDate;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public Integer getProductGrade() {
		return productGrade;
	}

	public void setProductGrade(Integer productGrade) {
		this.productGrade = productGrade;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}



	public Comment() {
	}

	public Comment(Integer id, Integer userId, Integer productId, Timestamp commentDate, String commentContent, Integer productGrade, String photoPath, String status) {
		this.id = id;
		this.userId = userId;
		this.productId = productId;
		this.commentDate = commentDate;
		this.commentContent = commentContent;
		this.productGrade = productGrade;
		this.photoPath = photoPath;
		this.status = status;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Integer orderItemId) {
		this.orderItemId = orderItemId;
	}

	@Override
	public String toString() {
		return "Comment{" +
				"id=" + id +
				", userId=" + userId +
				", productId=" + productId +
				", commentDate=" + commentDate +
				", commentContent='" + commentContent + '\'' +
				", productGrade=" + productGrade +
				", photoPath='" + photoPath + '\'' +
				", status='" + status + '\'' +
				", username='" + username + '\'' +
				", exhibitsName='" + exhibitsName + '\'' +
				", orderItemId='" + orderItemId + '\'' +
				'}';
	}
}