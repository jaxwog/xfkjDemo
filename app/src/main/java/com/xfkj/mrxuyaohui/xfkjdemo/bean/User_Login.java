package com.xfkj.mrxuyaohui.xfkjdemo.bean;

/**
 * Created by Mr.xuyaohui on 2015/5/4.
 */
public class User_Login {
    private int userId;
    private String result;
    private String phone;

    public User_Login(int userId, String result, String phone) {
        this.userId = userId;
        this.result = result;
        this.phone = phone;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getUserId() {
        return userId;
    }

    public String getResult() {
        return result;
    }

    public String getPhone() {
        return phone;
    }
}
