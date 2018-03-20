package com.exhibition.po;

import java.sql.Timestamp;

/**
 * 首页轮播图
 */
public class Carouse {

    private Integer id ;
    private String imgPath;      //图片路径
    private Timestamp submitDate;   //上传时间
    private String submitterName;      //上传人
    private String detail;      //简介
    private Integer sort;     //排序

    public Carouse() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Timestamp getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Timestamp submitDate) {
        this.submitDate = submitDate;
    }

    public String getSubmitterName() {
        return submitterName;
    }

    public void setSubmitterName(String submitterName) {
        this.submitterName = submitterName;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Carouse ownClone() {
        Carouse carouse = new Carouse();
        carouse.setId(this.id);
        carouse.setSubmitterName(this.submitterName);
        carouse.setSubmitDate(this.submitDate);
        carouse.setSort(this.sort);
        carouse.setDetail(this.detail);
        carouse.setImgPath(this.imgPath);
        return carouse;
    }

    @Override
    public String toString() {
        return "Carouse{" +
                "id=" + id +
                ", imgPath='" + imgPath + '\'' +
                ", submitDate=" + submitDate +
                ", submitterName='" + submitterName + '\'' +
                ", detail='" + detail + '\'' +
                ", sort=" + sort +
                '}';
    }
}
