package com.xfkj.mrxuyaohui.xfkjdemo.bean;

/**
 * Created by Mr.xuyaohui on 2015/6/1.
 */
public class User_ziliao {
    //alipayNum":"","userAge":"25","userBalance":"20","userId":2,"userMessage":"老爸","userName":"啦","userPasswd":"202CB962AC59075B964B07152D234B70","userPhmessage":"","userPhone":"15638808580","userSex":"nan"}}]
    private String alipayNum;
    private int userAge;
    private double userBalance;
    private int userId;
    private String userMessage;
    private String userName;
    private String passwd;
    private String userPhmessage;
    private String userPhone;
    private String userSex;

    public User_ziliao(String alipayNum, String userSex, String userPhone, String userPhmessage, String passwd, String userName, String userMessage, int userId, double userBalance, int userAge) {
        this.alipayNum = alipayNum;
        this.userSex = userSex;
        this.userPhone = userPhone;
        this.userPhmessage = userPhmessage;
        this.passwd = passwd;
        this.userName = userName;
        this.userMessage = userMessage;
        this.userId = userId;
        this.userBalance = userBalance;
        this.userAge = userAge;
    }

    public void setAlipayNum(String alipayNum) {
        this.alipayNum = alipayNum;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public void setUserBalance(double userBalance) {
        this.userBalance = userBalance;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public void setUserPhmessage(String userPhmessage) {
        this.userPhmessage = userPhmessage;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getAlipayNum() {
        return alipayNum;
    }

    public int getUserAge() {
        return userAge;
    }

    public String getUserSex() {
        return userSex;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getUserPhmessage() {
        return userPhmessage;
    }

    public String getPasswd() {
        return passwd;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public int getUserId() {
        return userId;
    }

    public double getUserBalance() {
        return userBalance;
    }
}
