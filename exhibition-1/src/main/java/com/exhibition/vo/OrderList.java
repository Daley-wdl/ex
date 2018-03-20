package com.exhibition.vo;

import com.exhibition.po.Exhibits;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;

/**
 * @Since: JDK 1.8
 * @Author: ZhaoKunsong
 * @Description: 展示订单
 * @Date: 2017/9/21 15:48
 **/
public class OrderList implements Serializable {

    private String id;
    private Timestamp createTime;
    private double totalMoney;
    private String receiveName;
    private String contactTel;
    private String receiveAddress;
    private int status;
    private Map<Exhibits,Integer> exhibitsMap;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Map<Exhibits, Integer> getExhibitsMap() {
        return exhibitsMap;
    }

    public void setExhibitsMap(Map<Exhibits, Integer> exhibitsMap) {
        this.exhibitsMap = exhibitsMap;
    }
}
