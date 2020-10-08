package com.example.a01tow.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a01tow.R;
import com.example.a01tow.bean.HistoryBean;

import java.util.List;

public class HistoryListContentAdapter extends RecyclerView.Adapter<HistoryListContentAdapter.InnerHolder> {
  private List<HistoryBean.DataBean> mData;

  @NonNull
  @Override
  public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_history_list_item, parent, false);
    return new InnerHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
    holder.accost.setText(mData.get(position).getAccost());
    holder.rechargeMoney.setText(mData.get(position).getRechargeMoney());
    holder.time.setText(mData.get(position).getTime());
  }

  @Override
  public int getItemCount() {
    return mData.size();
  }

  public void setData(List<HistoryBean.DataBean> data) {
    this.mData = data;
  }

  public class InnerHolder extends RecyclerView.ViewHolder {
    private TextView accost, rechargeMoney, time;

    public InnerHolder(@NonNull View itemView) {
      super(itemView);
      accost = itemView.findViewById(R.id.accost);
      rechargeMoney = itemView.findViewById(R.id.rechargeMoney);
      time = itemView.findViewById(R.id.time);
    }
  }
}
