package com.exhibition.po;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by final on 17-5-20.
 */
public class User {

    private Integer userId;
    @NotNull
    @Size(min = 3,max = 30,message = "用户名长度在3-30之间")
    private String username;
    @NotNull
    @Size(min = 6,max = 12,message = "密码长度在6-12之间")
    private String password;
    private String salt;
    private List<Role> roles;
    private Boolean locked;//表示账号是否被锁定，默认值为false
    private Timestamp creatTime;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password,String salt, List<Role> roles) {
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.roles = roles;
        this.creatTime = new Timestamp(System.currentTimeMillis());
        this.locked = false;
    }

    public User() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getCredentialsSalt(){
        return username + salt;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Timestamp getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Timestamp creatTime) {
        this.creatTime = creatTime;
    }

    @Override
    public String toString() {
        return "User{" + "userId=" + userId + ", username='" + username + '\'' + ", password='" + password + '\''
                + ", salt='" + salt + '\'' + ", roles=" + roles + ", locked=" + locked + ", creatTime=" + creatTime + '}';
    }
}
