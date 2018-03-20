package com.exhibition.po;


import javax.validation.constraints.Size;

/**
 * Created by final on 17-7-22.
 */
public class Exhibitor {

    private Integer id;
    private Integer userId;//与user中的id相同，相当于外键的作用
    @Size(min = 2,max = 40,message = "用户姓名长度在2-40之间")
    private String realName;
    private String status;
    private String email;
    @Size(min = 11,max = 11,message = "电话号码为11位")
    private String phone;
    private String licensePhoto;
    @Size(min = 1,max = 200,message = "展品介绍在1-200之间")
    private String intro;

    public Exhibitor() {
    }

    public Exhibitor(Integer userId, String realName,
                     String status, String email, String phone, String licensePhoto, String intro) {
        this.userId = userId;
        this.realName = realName;
        this.status = status;
        this.email = email;
        this.phone = phone;
        this.licensePhoto = licensePhoto;
        this.intro = intro;
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


    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLicensePhoto() {
        return licensePhoto;
    }

    public void setLicensePhoto(String licensePhoto) {
        this.licensePhoto = licensePhoto;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    @Override
    public String toString() {
        return "Exhibitor{" + "id=" + id + ", userId=" + userId + ", realName='" + realName + '\'' + ", status='" + status + '\'' + ", email='" + email + '\'' + ", phone='" + phone + '\'' + ", licensePhoto='" + licensePhoto + '\'' + ", intro='" + intro + '\'' + '}';
    }
}
