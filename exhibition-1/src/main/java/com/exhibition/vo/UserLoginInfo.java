package com.exhibition.vo;

/**
 * 表示当前用户登录状态的vo，包含的信息用来修改用户页面导航栏信息的状态(main.html)
 * 示例:<p>
 {
 "img":"img/img2.jpg",
 "status":"1",
 "user":"我(用户)",
 "message":"消息",
 "badge":"24",
 "geren":"个人中心",
 "xiugai":"修改信息",
 "guanli":"安全管理",
 "tuichu":"退出",
 "center":"个人中心"

 }
 * </p>
 */
public class UserLoginInfo {

    private String img;
    private String status;
    private String user;
    private String message;
    private int badge;//徽章
    private String geren;
    private String xiugai;
    private String guanli;
    private String tuichu;
    private String center;

    public UserLoginInfo() {
    }

    /**
     * 创建默认的用户信息的vo
     * @return
     */
    public static UserLoginInfo defaultInstance() {
        UserLoginInfo userLoginInfo = new UserLoginInfo();
//        "img":"img/img2.jpg",
//                "status":"1",
//                "user":"我(用户)",
//                "message":"消息",
//                "badge":"24",
//                "geren":"个人中心",
//                "xiugai":"修改信息",
//                "guanli":"安全管理",
//                "tuichu":"退出",
//                "center":"个人中心"
        userLoginInfo.setBadge(1).setStatus("1").setUser("我(用户)").setMessage("消息").setGeren("个人中心").setGuanli("安全管理")
                .setCenter("个人中心").setTuichu("退出").setXiugai("修改信息").setImg("/static/img/defaultIcon.jpg");

        return userLoginInfo;
    }

    public String getImg() {
        return img;
    }

    public UserLoginInfo setImg(String img) {
        this.img = img;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public UserLoginInfo setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getUser() {
        return user;
    }

    public UserLoginInfo setUser(String user) {
        this.user = user;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public UserLoginInfo setMessage(String message) {
        this.message = message;
        return this;
    }

    public int getBadge() {
        return badge;
    }

    public UserLoginInfo setBadge(int badge) {
        this.badge = badge;
        return this;
    }

    public String getGeren() {
        return geren;
    }

    public UserLoginInfo setGeren(String geren) {
        this.geren = geren;
        return this;
    }

    public String getXiugai() {
        return xiugai;
    }

    public UserLoginInfo setXiugai(String xiugai) {
        this.xiugai = xiugai;
        return this;
    }

    public String getGuanli() {
        return guanli;
    }

    public UserLoginInfo setGuanli(String guanli) {
        this.guanli = guanli;
        return this;
    }

    public String getTuichu() {
        return tuichu;
    }

    public UserLoginInfo setTuichu(String tuichu) {
        this.tuichu = tuichu;
        return this;
    }

    public String getCenter() {
        return center;
    }

    public UserLoginInfo setCenter(String center) {
        this.center = center;
        return this;
    }
}
