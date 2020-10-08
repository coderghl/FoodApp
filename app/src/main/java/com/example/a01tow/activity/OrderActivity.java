package com.example.a01tow.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a01tow.R;
import com.example.a01tow.adapter.OrderListAdapter;
import com.example.a01tow.bean.FoodInfoBean;
import com.example.a01tow.util.Constant;
import com.example.a01tow.util.SharedPreferencesUtil;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OrderActivity extends BaseActivity {
  private ImageView mOrderBack;
  private ListView mOrderList;
  private OrderListAdapter mAdapter;
  private SharedPreferencesUtil sharedPreferencesUtil;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_order);
    initView();
    // 绑定事件
    bindEvent();
  }

  private void bindEvent() {
    mOrderBack.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
  }

  private void initView() {
    mOrderBack = findViewById(R.id.order_back);
    mOrderList = findViewById(R.id.order_list);
    mAdapter = new OrderListAdapter();
    sharedPreferencesUtil = new SharedPreferencesUtil(Constant.USER_ORDER_FILE_NAME, OrderActivity.this, Context.MODE_APPEND);
    // 获取数据
    getData();
  }

  private void getData() {
    OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(10000, TimeUnit.MILLISECONDS).build();
    String fid1 = sharedPreferencesUtil.mGetString("food1");
    String fid2 = sharedPreferencesUtil.mGetString("food2");
    String fid3 = sharedPreferencesUtil.mGetString("food3");

    String food_count1 = sharedPreferencesUtil.mGetString("foodCount1");
    String food_count2 = sharedPreferencesUtil.mGetString("foodCount2");
    String food_count3 = sharedPreferencesUtil.mGetString("foodCount3");

    requestParam(fid1, food_count1, client);
    requestParam(fid2, food_count2, client);
    requestParam(fid3, food_count3, client);
  }

  private void requestParam(String fid, final String foodCount, OkHttpClient client) {
    Request request;
    if (fid != null && foodCount != null) {
      request = new Request.Builder().get().url(Constant.FOOD_INFO + "id=" + fid + "").build();
      client.newCall(request).enqueue(new Callback() {
        @Override
        public void onFailure(@NotNull Call call, @NotNull IOException e) {

        }

        @Override
        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
          Gson gson = new Gson();
          String jsonStr = response.body().string();
          final FoodInfoBean foodInfoBean = gson.fromJson(jsonStr, FoodInfoBean.class);
          runOnUiThread(new Runnable() {
            @Override
            public void run() {
              mAdapter.setData(foodInfoBean.getData(), foodCount);
              mOrderList.setAdapter(mAdapter);
            }
          });
        }
      });
    } else {
      return;
    }
  }
}