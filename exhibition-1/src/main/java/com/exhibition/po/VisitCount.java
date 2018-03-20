package com.exhibition.po;

import java.sql.Date;

/**
 * 统计访问量和在线人数的po
 */
public class VisitCount {
    private int id;
    private Date date;
    private int count;
    private int maxOnlineCount;

    public VisitCount() {
        date = new Date(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        return "VisitCount{" +
                "id=" + id +
                ", date=" + date +
                ", count=" + count +
                ", maxOnlineCount=" + maxOnlineCount +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getMaxOnlineCount() {
        return maxOnlineCount;
    }

    public void setMaxOnlineCount(int maxOnlineCount) {
        this.maxOnlineCount = maxOnlineCount;
    }
}
