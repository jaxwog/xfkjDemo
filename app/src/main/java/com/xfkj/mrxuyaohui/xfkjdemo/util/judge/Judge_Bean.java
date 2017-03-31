package com.xfkj.mrxuyaohui.xfkjdemo.util.judge;

/**
 * Created by Mr.xuyaohui on 2015/4/27.
 */
public class Judge_Bean {

    private int commentId;
    private String commentMessage;
    private int commentScore;
    private String commentTime;
    private int docId;
    private int userId;
    private String userPhone;

    public Judge_Bean(int commentId, String commentMessage, int commentScore, String commentTime, int docId, int userId, String userPhone) {
        this.commentId = commentId;
        this.commentMessage = commentMessage;
        this.commentScore = commentScore;
        this.commentTime = commentTime;
        this.docId = docId;
        this.userId = userId;
        this.userPhone = userPhone;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public void setCommentMessage(String commentMessage) {
        this.commentMessage = commentMessage;
    }

    public void setCommentScore(int commentScore) {
        this.commentScore = commentScore;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public int getCommentId() {
        return commentId;
    }

    public int getDocId() {
        return docId;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public int getCommentScore() {
        return commentScore;
    }

    public String getCommentMessage() {
        return commentMessage;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserPhone() {
        return userPhone;
    }
}
