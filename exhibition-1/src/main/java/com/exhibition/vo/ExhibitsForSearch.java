package com.exhibition.vo;

import org.apache.solr.client.solrj.beans.Field;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 用于前台搜索展品的po
 * 包含展品属性：展品id、展品名、展商名、上架时间、价格、数量、图片路径、介绍、类别
 */
public class ExhibitsForSearch {

    @Field("id")
    private Integer id;
    @Field("exhibitsName")
    private String exhibitsName;
    @Field(value = "creatTime")
    private Date creatTime;
    @Field("intro")
    private String intro;
    @Field("mainPhotoPath")
    private String mainPhotoPath;//封面照片访问路径
    @Field("exhibitorName")
    private String exhibitorName;
    @Field("category")
    private String category;
    @Field("number")
    private Integer number;
    @Field("price")
    private Integer price;

    public ExhibitsForSearch() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExhibitsName() {
        return exhibitsName;
    }

    public void setExhibitsName(String exhibitsName) {
        this.exhibitsName = exhibitsName;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getMainPhotoPath() {
        return mainPhotoPath;
    }

    public void setMainPhotoPath(String mainPhotoPath) {
        this.mainPhotoPath = mainPhotoPath;
    }

    public String getExhibitorName() {
        return exhibitorName;
    }

    public void setExhibitorName(String exhibitorName) {
        this.exhibitorName = exhibitorName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "ExhibitsForSearch{" +
                "id=" + id +
                ", exhibitsName='" + exhibitsName + '\'' +
                ", creatTime=" + creatTime +
                ", intro='" + intro + '\'' +
                ", mainPhotoPath='" + mainPhotoPath + '\'' +
                ", exhibitorName='" + exhibitorName + '\'' +
                ", category='" + category + '\'' +
                ", number=" + number +
                ", price=" + price +
                '}';
    }
}
