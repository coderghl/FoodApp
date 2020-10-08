package com.example.a01tow.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a01tow.R;
import com.example.a01tow.bean.UserInfoBean;
import com.example.a01tow.fragment.AboutFragment;
import com.example.a01tow.util.Constant;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserInfoActivity extends BaseActivity implements View.OnClickListener {
  private TextView mUserInfoName, mUserInfoMark, mUserInfoSex, mUserInfoMoney, mUserInfoTel, mUserInfoAddress;
  private ImageView mUserInfoMenu, mUserInfoBack;
  private PopupMenu rightTopMenu;
  private List<UserInfoBean.DataBean> data;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_info);
    // 获取控件
    initView();
    // 获取数据
    getData();
    // 绑定事件
    bindEvent();
  }


  private void initView() {
    mUserInfoName = findViewById(R.id.user_info_name);
    mUserInfoMark = findViewById(R.id.user_info_mark);
    mUserInfoSex = findViewById(R.id.user_info_sex);
    mUserInfoMoney = findViewById(R.id.user_info_money);
    mUserInfoTel = findViewById(R.id.user_info_tel);
    mUserInfoAddress = findViewById(R.id.user_info_address);
    mUserInfoMenu = findViewById(R.id.user_info_menu);
    mUserInfoBack = findViewById(R.id.user_info_back);
  }

  private void bindEvent() {
    mUserInfoMenu.setOnClickListener(this);
    mUserInfoBack.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.user_info_back:
        finish();
        break;
      case R.id.user_info_menu:
        rightTopMenu = new PopupMenu(UserInfoActivity.this, v);
        // 设置menu
        setMenu();
        break;
    }
  }

  private void setMenu() {
    rightTopMenu.getMenuInflater().inflate(R.menu.about_info_menu, rightTopMenu.getMenu());
    rightTopMenu.show();
    // 监听menu点击
    rightTopMenuClick();
  }

  private void rightTopMenuClick() {
    rightTopMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
      @Override
      public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
          case R.id.edit_user_menu:
            jumpActivity(EditorUserInfoActivity.class);
            break;
          case R.id.refresh_menu:
            getData();
            initData();
            break;
        }
        return false;
      }
    });
  }

  private String setSex() {
    String sex = data.get(0).getSex();
    String result = null;
    int i = Integer.parseInt(sex);
    if (i == 1) {
      result = "男";
    } else {
      result = "女";
    }
    return result;
  }

  private void getData() {
    OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(10000, TimeUnit.MILLISECONDS).build();
    Request request = new Request.Builder().get().url(Constant.USER_INFO_QUERY + AboutFragment.id).build();
    client.newCall(request).enqueue(new Callback() {
      @Override
      public void onFailure(@NotNull Call call, @NotNull IOException e) {
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            showToast("当前网络不太好,请稍后再试");
          }
        });
      }

      @Override
      public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        String jsonStr = response.body().string();
        Gson gson = new Gson();
        final UserInfoBean userInfoBean = gson.fromJson(jsonStr, UserInfoBean.class);
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            data = userInfoBean.getData();
            initData();
          }
        });
      }
    });
  }

  private void initData() {
    mUserInfoName.setText(data.get(0).getName());
    mUserInfoMark.setText(data.get(0).getMark());
    mUserInfoMoney.setText("金额：" + data.get(0).getMonery());
    mUserInfoTel.setText("电话：" + data.get(0).getTelPhone());
    mUserInfoAddress.setText("地址：" + data.get(0).getAddress());
    mUserInfoSex.setText("性别：" + setSex());
  }

  @Override
  protected void onRestart() {
    super.onRestart();
    getData();
    initData();
  }
}