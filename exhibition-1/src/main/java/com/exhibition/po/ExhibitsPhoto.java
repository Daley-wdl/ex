package com.exhibition.po;

import java.sql.Timestamp;

/**
 * 表示展品详情图片的po
 */
public class ExhibitsPhoto {
    private Integer id;
    private Integer exhibitsId;
    private String path;
    private Integer exhibitorId;
    private Timestamp creatTime;

    public ExhibitsPhoto() {
    }

    public ExhibitsPhoto(Integer exhibitsId, String path, Integer exhibitorId) {
        this.exhibitsId = exhibitsId;
        this.path = path;
        this.exhibitorId = exhibitorId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getExhibitsId() {
        return exhibitsId;
    }

    public void setExhibitsId(Integer exhibitsId) {
        this.exhibitsId = exhibitsId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getExhibitorId() {
        return exhibitorId;
    }

    public void setExhibitorId(Integer exhibitorId) {
        this.exhibitorId = exhibitorId;
    }

    public Timestamp getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Timestamp creatTime) {
        this.creatTime = creatTime;
    }
}
