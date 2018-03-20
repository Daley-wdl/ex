package com.exhibition.po;

import java.sql.Timestamp;

/**
 * 评论预览
 */
public class CommentPreview {
    private Integer exhibitsId;//展品id
    private String exhibitsName;//展品名
    private Float averageGrade;//平均评分   0-差评    1-中评    2-好评
    private Integer commentsNum;//评论数量
    private Timestamp lastPostTime;//最近的评论发表时间

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

    public Float getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(Float averageGrade) {
        this.averageGrade = averageGrade;
    }

    public Integer getCommentsNum() {
        return commentsNum;
    }

    public void setCommentsNum(Integer commentsNum) {
        this.commentsNum = commentsNum;
    }

    public Timestamp getLastPostTime() {
        return lastPostTime;
    }

    public void setLastPostTime(Timestamp lastPostTime) {
        this.lastPostTime = lastPostTime;
    }

    @Override
    public String toString() {
        return "CommentPreview{" +
                "exhibitsId=" + exhibitsId +
                ", exhibitsName='" + exhibitsName + '\'' +
                ", averageGrade=" + averageGrade +
                ", commentsNum=" + commentsNum +
                ", lastPostTime=" + lastPostTime +
                '}';
    }
}
