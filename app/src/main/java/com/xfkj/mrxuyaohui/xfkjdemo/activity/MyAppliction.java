package com.xfkj.mrxuyaohui.xfkjdemo.activity;

import android.app.Application;

/**
 * Created by Mr.xuyaohui on 2015/5/9.
 */
public class MyAppliction extends Application {
    private boolean flag=false;//用户是否登录判断
    private int userId;//
    private int docId;
    private String phone;
    private boolean ispush=false;
    private String weixin_zhifu;
    private String password;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWeixin_zhifu() {
        return weixin_zhifu;
    }

    public void setWeixin_zhifu(String weixin_zhifu) {
        this.weixin_zhifu = weixin_zhifu;
    }

    public void setIspush(boolean ispush) {
        this.ispush = ispush;
    }
    public boolean getIspush() {
        return ispush;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
