package com.exhibition.po;

import java.io.Serializable;
import java.sql.Timestamp;



public class Reply implements Serializable {

	private Integer id;
	private Integer commentId;              //回复所针对的评论
	private String replyContent;			//回复内容
	private Timestamp replyDate;			//回复时间
	private Integer productId;              //展品id
	private Integer userId;					//用户id
	private String status;					//状态
	private Integer exhibitorId;			//回复所属展商的Id
	private String photoPath;				//回复的图片的路径

	public Reply() {
	}

	public Reply(Integer id, Integer commentId, String replyContent, Timestamp replyDate, Integer productId, Integer userId, String status, Integer exhibitorId, String photoPath) {
		this.id = id;
		this.commentId = commentId;
		this.replyContent = replyContent;
		this.replyDate = replyDate;
		this.productId = productId;
		this.userId = userId;
		this.status = status;     //状态 0-有效 1-无效
		this.exhibitorId = exhibitorId;
		this.photoPath = photoPath;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public Timestamp getReplyDate() {
		return replyDate;
	}

	public void setReplyDate(Timestamp replyDate) {
		this.replyDate = replyDate;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getExhibitorId() {
		return exhibitorId;
	}

	public void setExhibitorId(Integer exhibitorId) {
		this.exhibitorId = exhibitorId;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	@Override
	public String toString() {
		return "Reply{" +
				"id=" + id +
				", commentId=" + commentId +
				", replyContent='" + replyContent + '\'' +
				", replyDate=" + replyDate +
				", productId=" + productId +
				", userId=" + userId +
				", status='" + status + '\'' +
				", exhibitorId=" + exhibitorId +
				", photoPath='" + photoPath + '\'' +
				'}';
	}
}