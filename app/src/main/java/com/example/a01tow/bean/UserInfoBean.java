package com.example.a01tow.bean;

import java.util.List;

public class UserInfoBean {

  /**
   * code : 200
   * msg : 这是用户信息
   * data : [{"uid":"3","name":"dio","password":"e10adc3949ba59abbe56e057f20f883e","sex":"1","logo":"","monery":"10904.00","telPhone":"23812","address":"hengyang","mark":"konodioda"}]
   */

  private String code;
  private String msg;
  private List<DataBean> data;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public List<DataBean> getData() {
    return data;
  }

  public void setData(List<DataBean> data) {
    this.data = data;
  }

  public static class DataBean {
    /**
     * uid : 3
     * name : dio
     * password : e10adc3949ba59abbe56e057f20f883e
     * sex : 1
     * logo :
     * monery : 10904.00
     * telPhone : 23812
     * address : hengyang
     * mark : konodioda
     */

    private String uid;
    private String name;
    private String password;
    private String sex;
    private String logo;
    private String monery;
    private String telPhone;
    private String address;
    private String mark;

    public String getUid() {
      return uid;
    }

    public void setUid(String uid) {
      this.uid = uid;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }

    public String getSex() {
      return sex;
    }

    public void setSex(String sex) {
      this.sex = sex;
    }

    public String getLogo() {
      return logo;
    }

    public void setLogo(String logo) {
      this.logo = logo;
    }

    public String getMonery() {
      return monery;
    }

    public void setMonery(String monery) {
      this.monery = monery;
    }

    public String getTelPhone() {
      return telPhone;
    }

    public void setTelPhone(String telPhone) {
      this.telPhone = telPhone;
    }

    public String getAddress() {
      return address;
    }

    public void setAddress(String address) {
      this.address = address;
    }

    public String getMark() {
      return mark;
    }

    public void setMark(String mark) {
      this.mark = mark;
    }
  }
}
