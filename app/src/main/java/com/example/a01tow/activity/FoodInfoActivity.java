package com.example.a01tow.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a01tow.R;
import com.example.a01tow.bean.FoodInfoBean;
import com.example.a01tow.bean.UserInfoBean;
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

public class FoodInfoActivity extends BaseActivity {
  private TextView mFoodName, mFoodKey, mFoodPrice;
  private Button mFoodBuy;
  private ImageView mFoodIcon;
  private String fid;
  private List<FoodInfoBean.DataBean> FoodInfoData;

  private SharedPreferencesUtil sharedPreferencesUtil, sharedPreferencesUtil2;
  private List<UserInfoBean.DataBean> userInfoData;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_food_info);
    // 获取用户当前金钱
    queryUserMoney();

    // 初始化
    initView();

    // 购买按钮点击
    clickBuy();
  }


  private void initView() {
    Intent intent = getIntent();
    fid = intent.getStringExtra("fid");
    mFoodName = findViewById(R.id.food_info_name);
    mFoodKey = findViewById(R.id.food_info_key);
    mFoodPrice = findViewById(R.id.food_info_price);
    mFoodBuy = findViewById(R.id.food_info_buy);
    mFoodIcon = findViewById(R.id.food_info_icon);
    // 请求数据
    getData();
  }

  private void clickBuy() {
    mFoodBuy.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // 获取添加得数量，计算是否能购买
        isBuy();
      }
    });
  }

  private void isBuy() {
    final AlertDialog.Builder mAlert = new AlertDialog.Builder(FoodInfoActivity.this);
    final View alertView = LayoutInflater.from(FoodInfoActivity.this).inflate(R.layout.layout_alert, null);
    mAlert.setMessage("请输入您要购买得数量");
    mAlert.setView(alertView);

    mAlert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        EditText buyCountView = alertView.findViewById(R.id.food_info_buy_count);
        // 获取食物购买数量
        String count = buyCountView.getText().toString();
        if (count.length() == 0) {
          showToast("请输入正确的值");
          return;
        }
        // 获取食物单价
        String foodPrice = FoodInfoData.get(0).getPrice();
        // 获取食物总价
        Double FoodPriceAll = (Double.parseDouble(foodPrice)) * (Double.parseDouble(count));
        // 获取自己得金钱
        Double userMoney = Double.parseDouble(userInfoData.get(0).getMonery());

        if (FoodPriceAll > userMoney) {
          showToast("抱歉您目前没有这么多钱");
          return;
        }

        if (FoodPriceAll < userMoney) {
          String userSurplusMoney = String.valueOf(userMoney - FoodPriceAll);
          // 更新用户金额
          updataUserMoney(userSurplusMoney);

          // 保存数据到本地 方便实现我的订单
          sharedPreferencesUtil.mSetString("food" + fid, fid);
          sharedPreferencesUtil.mSetString("foodCount" + fid, count);
          sharedPreferencesUtil.mSetString("foodPrice" + fid, String.valueOf(FoodPriceAll));
        }
      }
    });
    mAlert.show();
  }


  private void getData() {
    OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(10000, TimeUnit.MILLISECONDS).build();
    String url = Constant.FOOD_INFO + "id=" + fid + "";
    Request request = new Request.Builder().url(url).get().build();

    client.newCall(request).enqueue(new Callback() {
      @Override
      public void onFailure(@NotNull Call call, @NotNull IOException e) {
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            showToast("加载失败，请稍后再试");
          }
        });
      }

      @Override
      public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        String jsonStr = response.body().string();
        Gson gson = new Gson();
        final FoodInfoBean foodInfoBean = gson.fromJson(jsonStr, FoodInfoBean.class);
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            FoodInfoData = foodInfoBean.getData();
            // 填充数据
            initData();
          }
        });
      }
    });
  }

  private void initData() {
    mFoodName.setText(FoodInfoData.get(0).getName());
    mFoodKey.setText(FoodInfoData.get(0).getKey());
    mFoodPrice.setText("￥" + FoodInfoData.get(0).getPrice());
    String url = Constant.FOOD_ICON + (FoodInfoData.get(0).getImgsrc().substring(2));
    // 加载图片
    Glide.with(FoodInfoActivity.this).load(url).into(mFoodIcon);
  }

  private void queryUserMoney() {
    sharedPreferencesUtil = new SharedPreferencesUtil(Constant.USER_ORDER_FILE_NAME, FoodInfoActivity.this, Context.MODE_APPEND);
    sharedPreferencesUtil2 = new SharedPreferencesUtil(Constant.USER_INFO_FILE_NAME, FoodInfoActivity.this, Context.MODE_PRIVATE);
    String id = sharedPreferencesUtil2.mGetString("id");
    // 发送网络请求
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder()
      .get()
      .url(Constant.USER_INFO_QUERY + id)
      .build();
    client.newCall(request).enqueue(new Callback() {
      @Override
      public void onFailure(@NotNull Call call, @NotNull IOException e) {
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            showToast("加载失败，请稍后再试");
          }
        });
      }

      @Override
      public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        String jsonStr = response.body().string();
        // 获取用户信息数据
        if (response.code() == 200) {
          Gson gson = new Gson();
          UserInfoBean mUserInfoBean = gson.fromJson(jsonStr, UserInfoBean.class);
          userInfoData = mUserInfoBean.getData();
        }
      }
    });
  }

  private void updataUserMoney(String money) {
    OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(10000, TimeUnit.MILLISECONDS).build();
    // 拼接数据
    String param = "id=" + userInfoData.get(0).getUid() + "&name=" + userInfoData.get(0).getName() + "&password=" + userInfoData.get(0).getPassword() + "&" +
      "telPhone=" + userInfoData.get(0).getTelPhone() + "&sex=" + userInfoData.get(0).getSex() + "&address=" + userInfoData.get(0).getAddress() + "" +
      "&monery=" + money + "&mark=" + userInfoData.get(0).getMark() + "";
    Request request = new Request.Builder().get().url(Constant.EDITOR_USER_MESSAGE + param).build();
    client.newCall(request).enqueue(new Callback() {
      @Override
      public void onFailure(@NotNull Call call, @NotNull IOException e) {
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            showToast("网络不好，请稍后重试");
          }
        });
      }

      @Override
      public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            showToast("购买成功");
          }
        });
      }
    });
  }
}