package com.example.a01tow.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a01tow.R;
import com.example.a01tow.bean.FoodAllBean;
import com.example.a01tow.bean.FoodInfoBean;
import com.example.a01tow.util.Constant;

import java.util.ArrayList;
import java.util.List;

public class OrderListAdapter extends BaseAdapter {
  private List<List<FoodInfoBean.DataBean>> mData = new ArrayList<>();
  private List<String> mCount = new ArrayList<>();

  @Override
  public int getCount() {
    return mData.size();
  }

  static class ViewHolder {
    ImageView food_list_icon;
    TextView food_list_title, food_list_price, food_list_mark;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    FoodTabCardListAdapter.ViewHolder holder = null;
    if (convertView == null) {
      convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_food_tab_card_list_item, parent, false);
      holder = new FoodTabCardListAdapter.ViewHolder();

      List<FoodInfoBean.DataBean> dataBeans = mData.get(position);
      FoodInfoBean.DataBean data = dataBeans.get(0);

      // 找控件
      holder.food_list_title = convertView.findViewById(R.id.food_list_title);
      holder.food_list_price = convertView.findViewById(R.id.food_list_price);
      holder.food_list_mark = convertView.findViewById(R.id.food_list_mark);
      holder.food_list_icon = convertView.findViewById(R.id.food_list_icon);

      // 计算价格
      Double price = Double.parseDouble(data.getPrice()) * Double.parseDouble(mCount.get(position));
      // 填充数据
      holder.food_list_title.setText(data.getName());
      holder.food_list_price.setText("￥" + String.valueOf(price));
      holder.food_list_mark.setText("数量：" + mCount.get(position));

      // 加载图片
      String iconUrl = Constant.FOOD_ICON + (data.getImgsrc().substring(2));
      Glide.with(parent.getContext()).load(iconUrl).into(holder.food_list_icon);
    }
    return convertView;
  }

  public void setData(List<FoodInfoBean.DataBean> data, String count) {
    this.mData.add(data);
    this.mCount.add(count);
    notifyDataSetChanged();
  }

  @Override
  public Object getItem(int position) {
    return null;
  }

  @Override
  public long getItemId(int position) {
    return 0;
  }
}
