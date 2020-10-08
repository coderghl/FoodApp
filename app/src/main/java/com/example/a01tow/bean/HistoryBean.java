package com.example.a01tow.bean;

import java.util.List;

public class HistoryBean {
  List<DataBean> data;

  public List<DataBean> getData() {
    return data;
  }

  public void setData(List<DataBean> data) {
    this.data = data;
  }

  public static class DataBean {
    private String accost;
    private String rechargeMoney;
    private String time;

    public String getAccost() {
      return accost;
    }

    public void setAccost(String accost) {
      this.accost = accost;
    }

    public String getRechargeMoney() {
      return rechargeMoney;
    }

    public void setRechargeMoney(String rechargeMoney) {
      this.rechargeMoney = rechargeMoney;
    }

    public String getTime() {
      return time;
    }

    public void setTime(String time) {
      this.time = time;
    }
  }
}
