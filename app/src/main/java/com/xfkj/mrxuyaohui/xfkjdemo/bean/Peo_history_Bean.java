package com.xfkj.mrxuyaohui.xfkjdemo.bean;

/**
 * Created by Mr.xuyaohui on 2015/5/11.
 */
public class Peo_history_Bean {
    //billId":2,"billMoney":200,"billOvertime":"2015-01-05 2:00","billState":1,
    // "billTape":"","billTime":"2015-01-05 2:00",
    // "billappointmenttime":"2015-01-05 2:00","docId":3,"refund":0,"refundMoney":0,
    // "refundTime":"","serveId":2,"userId":2}]}]
    private int billId;
    private int billMoney;
    private String billOvertime;
    private int billState;
    private String billTape;
    private String billTime;
    private String billappointmenttime;
    private int docId;
    private int refund;
    private String docName;
    private int refundMoney;
    private int refundTime;
    private int serveId;
    private int userId;
    public Peo_history_Bean(String billTime,int docId,String docName)
    {
        this.billTime=billTime;
        this.docId=docId;
        this.docName=docName;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public Peo_history_Bean(int userId, int serveId, int billId, int billMoney, String billOvertime, int billState, String billTape, String billTime, String billappointmenttime, int docId, int refund, int refundMoney, int refundTime) {
        this.userId = userId;
        this.serveId = serveId;
        this.billId = billId;
        this.billMoney = billMoney;
        this.billOvertime = billOvertime;
        this.billState = billState;
        this.billTape = billTape;
        this.billTime = billTime;
        this.billappointmenttime = billappointmenttime;
        this.docId = docId;
        this.refund = refund;
        this.refundMoney = refundMoney;
        this.refundTime = refundTime;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public void setBillMoney(int billMoney) {
        this.billMoney = billMoney;
    }

    public void setBillOvertime(String billOvertime) {
        this.billOvertime = billOvertime;
    }

    public void setBillState(int billState) {
        this.billState = billState;
    }

    public void setBillTape(String billTape) {
        this.billTape = billTape;
    }

    public void setBillTime(String billTime) {
        this.billTime = billTime;
    }

    public void setBillappointmenttime(String billappointmenttime) {
        this.billappointmenttime = billappointmenttime;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public void setRefund(int refund) {
        this.refund = refund;
    }

    public void setRefundMoney(int refundMoney) {
        this.refundMoney = refundMoney;
    }

    public void setRefundTime(int refundTime) {
        this.refundTime = refundTime;
    }

    public void setServeId(int serveId) {
        this.serveId = serveId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBillId() {
        return billId;
    }

    public int getBillMoney() {
        return billMoney;
    }

    public String getBillOvertime() {
        return billOvertime;
    }

    public int getBillState() {
        return billState;
    }

    public String getBillTape() {
        return billTape;
    }

    public String getBillTime() {
        return billTime;
    }

    public String getBillappointmenttime() {
        return billappointmenttime;
    }

    public int getDocId() {
        return docId;
    }

    public int getRefund() {
        return refund;
    }

    public int getRefundMoney() {
        return refundMoney;
    }

    public int getRefundTime() {
        return refundTime;
    }

    public int getServeId() {
        return serveId;
    }

    public int getUserId() {
        return userId;
    }
}
