package com.xfkj.mrxuyaohui.xfkjdemo.util;

/**
 * Created by Administrator on 2015/6/5.
 */
public class Welcome {
    private int id;
    private String image;
    private String desc;

    public Welcome(int id,String image,String desc)
    {
        this.id=id;
        this.image=image;
        this.desc=desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
