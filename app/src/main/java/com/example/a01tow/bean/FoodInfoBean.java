package com.example.a01tow.bean;

import java.util.List;

public class FoodInfoBean {

  /**
   * code : 200
   * msg : 这个是食品详情信息
   * data : [{"fid":"1","name":"陆明糕饼","type":" 漳平美食","imgsrc":"../upload/foodImage/001.jpg","price":"20.00","key":"美味,实惠,干净","describe":"陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼"}]
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
     * fid : 1
     * name : 陆明糕饼
     * type :  漳平美食
     * imgsrc : ../upload/foodImage/001.jpg
     * price : 20.00
     * key : 美味,实惠,干净
     * describe : 陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼陆明糕饼
     */

    private String fid;
    private String name;
    private String type;
    private String imgsrc;
    private String price;
    private String key;
    private String describe;

    public String getFid() {
      return fid;
    }

    public void setFid(String fid) {
      this.fid = fid;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

    public String getImgsrc() {
      return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
      this.imgsrc = imgsrc;
    }

    public String getPrice() {
      return price;
    }

    public void setPrice(String price) {
      this.price = price;
    }

    public String getKey() {
      return key;
    }

    public void setKey(String key) {
      this.key = key;
    }

    public String getDescribe() {
      return describe;
    }

    public void setDescribe(String describe) {
      this.describe = describe;
    }
  }
}
