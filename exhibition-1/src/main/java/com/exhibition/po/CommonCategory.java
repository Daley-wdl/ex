package com.exhibition.po;

import java.sql.Timestamp;

/**
 * 描述展商常用类别的po
 */
public class CommonCategory {

    private Integer id;
    private String tag;
    private Integer userId;
    private Timestamp postTime;

    public CommonCategory() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Timestamp getPostTime() {
        return postTime;
    }

    public void setPostTime(Timestamp postTime) {
        this.postTime = postTime;
    }

    @Override
    public String toString() {
        return "CommonCategory{" +
                "id=" + id +
                ", tag='" + tag + '\'' +
                ", userId=" + userId +
                ", postTime=" + postTime +
                '}';
    }
}
