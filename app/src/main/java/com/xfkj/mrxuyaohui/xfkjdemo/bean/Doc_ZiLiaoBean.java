package com.xfkj.mrxuyaohui.xfkjdemo.bean;

import java.security.PrivateKey;

/**
 * Created by Administrator on 2015/6/12.
 */
public class Doc_ZiLiaoBean {
    private String attending;
    private String docAddress;
    private int docAge;
    private String docDate;
    private String docMassage;
    private String docName;
    private String docPhone;
    private String docServe;
    private String docSex;
    private String db_secondId;
    private String docScore;
    private String docSoreTime;

    public String getDocScore() {
        return docScore;
    }

    public String getDocSoreTime() {
        return docSoreTime;
    }

    public void setDocScore(String docScore) {
        this.docScore = docScore;
    }

    public void setDocSoreTime(String docSoreTime) {
        this.docSoreTime = docSoreTime;
    }

    public String getDb_secondId() {
        return db_secondId;
    }

    public void setDb_secondId(String db_secondId) {
        this.db_secondId = db_secondId;
    }

    public Doc_ZiLiaoBean(String attending, String docAddress, int docAge, String docSex, String docServe, String docPhone, String docName, String docMassage, String docDate,String db_secondId,String docScore,String docSoreTime) {
        this.attending = attending;
        this.docAddress = docAddress;
        this.docAge = docAge;
        this.docSex = docSex;
        this.docServe = docServe;
        this.docPhone = docPhone;
        this.docName = docName;
        this.docMassage = docMassage;
        this.docDate = docDate;
        this.db_secondId=db_secondId;
        this.docScore=docScore;
        this.docSoreTime=docSoreTime;
    }

    public void setAttending(String attending) {
        this.attending = attending;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public void setDocAddress(String docAddress) {
        this.docAddress = docAddress;
    }

    public void setDocAge(int docAge) {
        this.docAge = docAge;
    }

    public void setDocDate(String docDate) {
        this.docDate = docDate;
    }

    public void setDocMassage(String docMassage) {
        this.docMassage = docMassage;
    }

    public void setDocPhone(String docPhone) {
        this.docPhone = docPhone;
    }

    public void setDocServe(String docServe) {
        this.docServe = docServe;
    }

    public void setDocSex(String docSex) {
        this.docSex = docSex;
    }

    public String getDocName() {
        return docName;
    }

    public int getDocAge() {
        return docAge;
    }


    public String getAttending() {
        return attending;
    }

    public String getDocAddress() {
        return docAddress;
    }

    public String getDocDate() {
        return docDate;
    }

    public String getDocMassage() {
        return docMassage;
    }

    public String getDocPhone() {
        return docPhone;
    }

    public String getDocServe() {
        return docServe;
    }

    public String getDocSex() {
        return docSex;
    }
}
