package com.xfkj.mrxuyaohui.xfkjdemo.util.news;

/**
 * Created by Mr.xuyaohui on 2015/4/27.
 */
public class News {
    private String newsDate;
    private int newsId;
    private String newsImg;
    private String newsMessage;
    private String newsTitle;

    public News(String newsDate, int newsId, String newsImg, String newsMessage, String newsTitle) {
        this.newsDate = newsDate;
        this.newsId = newsId;
        this.newsImg = newsImg;
        this.newsMessage = newsMessage;
        this.newsTitle = newsTitle;
    }

    public void setNewsDate(String newsDate) {
        this.newsDate = newsDate;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public void setNewsImg(String newsImg) {
        this.newsImg = newsImg;
    }

    public void setNewsMessage(String newsMessage) {
        this.newsMessage = newsMessage;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public int getNewsId() {
        return newsId;
    }

    public String getNewsImg() {
        return newsImg;
    }

    public String getNewsMessage() {
        return newsMessage;
    }

    public String getNewsTitle() {
        return newsTitle;
    }
}
