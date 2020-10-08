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
import com.example.a01tow.util.Constant;

import java.util.List;

public class FoodTabCardListAdapter extends BaseAdapter {
  private List<FoodAllBean.DataBean> mData;

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
    ViewHolder holder = null;
    if (convertView == null) {
      convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_food_tab_card_list_item, parent, false);
      holder = new ViewHolder();
      FoodAllBean.DataBean dataBean = mData.get(position);

      // 找控件
      holder.food_list_title = convertView.findViewById(R.id.food_list_title);
      holder.food_list_price = convertView.findViewById(R.id.food_list_price);
      holder.food_list_mark = convertView.findViewById(R.id.food_list_mark);
      holder.food_list_icon = convertView.findViewById(R.id.food_list_icon);

      // 填充数据
      holder.food_list_title.setText(dataBean.getName());
      holder.food_list_price.setText(dataBean.getPrice());
      holder.food_list_mark.setText(dataBean.getDescribe());

      // 加载图片
      String iconUrl = Constant.FOOD_ICON + (dataBean.getImgsrc().substring(2));
      Glide.with(parent.getContext()).load(iconUrl).into(holder.food_list_icon);
    }
    return convertView;
  }

  public void setData(List<FoodAllBean.DataBean> data) {
    this.mData = data;
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
