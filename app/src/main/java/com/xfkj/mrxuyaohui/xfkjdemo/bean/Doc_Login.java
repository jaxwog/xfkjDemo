package com.xfkj.mrxuyaohui.xfkjdemo.bean;

/**
 * Created by Mr.xuyaohui on 2015/6/1.
 */
public class Doc_Login {
    //[{"DocId":1,"Sucess":"sucess","DocPhone":"15236524865"}]
    private int docId;
    private String success;
    private String docphone;

    public Doc_Login(int docId, String success, String docphone) {
        this.docId = docId;
        this.success = success;
        this.docphone = docphone;
    }

    public int getDocId() {
        return docId;
    }

    public String getDocphone() {
        return docphone;
    }

    public String getSuccess() {
        return success;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public void setDocphone(String docphone) {
        this.docphone = docphone;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
