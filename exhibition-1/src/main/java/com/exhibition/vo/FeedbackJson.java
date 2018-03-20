package com.exhibition.vo;

import java.util.List;

/**
 * @Since: JDK 1.8
 * @Author: ZhaoKunsong
 * @Description: 封装反馈的Json信息
 * @Date: 2017/9/2 10:13
 **/
public class FeedbackJson {

    private String error;         //0-正确  1-错误
    private String reason;        //反馈原因
    private List detailList;      //记录列表信息
    private Object detailObject;  //记录对象信息

    public FeedbackJson() {
    }

    public FeedbackJson(String error, String reason, List detailList, Object detailObject) {
        this.error = error;
        this.reason = reason;
        this.detailList = detailList;
        this.detailObject = detailObject;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List getDetailList() {
        return detailList;
    }

    public void setDetailList(List detailList) {
        this.detailList = detailList;
    }

    public Object getDetailObject() {
        return detailObject;
    }

    public void setDetailObject(Object detailObject) {
        this.detailObject = detailObject;
    }
}

