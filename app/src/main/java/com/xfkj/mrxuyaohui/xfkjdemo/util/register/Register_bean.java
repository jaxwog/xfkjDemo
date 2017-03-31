package com.xfkj.mrxuyaohui.xfkjdemo.util.register;

/**
 * Created by Mr.xuyaohui on 2015/5/22.
 */
public class Register_bean {
    private String ports;
    private String phone;

    public Register_bean(String ports, String phone) {
        this.ports = ports;
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public String getPorts() {
        return ports;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPorts(String ports) {
        this.ports = ports;
    }
}
