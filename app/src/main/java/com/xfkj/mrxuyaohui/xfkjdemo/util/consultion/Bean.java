package com.xfkj.mrxuyaohui.xfkjdemo.util.consultion;

/**
 * Created by Mr.xuyaohui on 2015/4/19.
 */
public class Bean {

    private String db_secondId;
    private int docId;//医生的ID
    private String name;
    private String address;
    private String sex;
    private String date;//出生年月
    private String password;
    private String attending;//主治
    private String massage;//医生信息介绍
    private String score;
    private String  scoreTime;
    private int age;
    private String imageUrl;
    private String phone;
    //添加的医生服务
    private String docServe;

    public String getDocServe() {
        return docServe;
    }

    public void setDocServe(String docServe) {
        this.docServe = docServe;
    }

    public String getDb_secondId() {
        return db_secondId;
    }

    public int getDocId() {
        return docId;
    }

    public void setDb_secondId(String db_secondId) {
        this.db_secondId = db_secondId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public Bean(String name, String address, String sex, String date, String password, String attending, String massage, String score, String scoreTime, int age, String imageUrl, String phone,String db_secondId,int docId,String docServe) {
        this.name = name;
        this.address = address;
        this.sex = sex;
        this.date = date;
        this.password = password;
        this.attending = attending;
        this.massage = massage;
        this.score = score;
        this.scoreTime = scoreTime;
        this.age = age;
        this.imageUrl = imageUrl;
        this.phone = phone;
        this.db_secondId=db_secondId;
        this.docId=docId;
        this.docServe=docServe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAttending() {
        return attending;
    }

    public void setAttending(String attending) {
        this.attending = attending;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getScoreTime() {
        return scoreTime;
    }

    public void setScoreTime(String scoreTime) {
        this.scoreTime = scoreTime;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
