package com.example.a01tow.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.a01tow.R;
import com.example.a01tow.util.Constant;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterActivity extends BaseActivity {
  private EditText mUserNameEd, mUserPasswordEd,
    mUserPasswordConfirmEd, mUserAddressEd,
    mUserTelEd, mUserMoneyEd, mUserMarkEd;
  private RadioGroup mUserSexRg;
  private Button mRegisterUserUpBtn;

  private String userName, userPassword, userPasswordConfirm, userAddress, userTel, userMoney, userMark;
  private String userSex = "1";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);
    // 初始化
    initView();

    // 选择性别
    chooseSex();

    // 点击注册按钮
    registerUp();

    // 填充数据
    initData();
  }

  private void initData() {

  }


  private void initView() {
    mUserNameEd = findViewById(R.id.register_user_name);
    mUserPasswordEd = findViewById(R.id.register_user_password);
    mUserPasswordConfirmEd = findViewById(R.id.register_user_confirm_password);
    mUserAddressEd = findViewById(R.id.register_user_address);
    mUserTelEd = findViewById(R.id.register_user_tel);
    mUserMoneyEd = findViewById(R.id.register_user_money);
    mUserMarkEd = findViewById(R.id.register_user_mark);
    mUserSexRg = findViewById(R.id.register_user_sex);
    mRegisterUserUpBtn = findViewById(R.id.register_user_up);
  }


  private void sendNetWorkRequest(String url) {
    OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(10000, TimeUnit.MILLISECONDS).build();
    Request request = new Request.Builder().url(url).get().build();
    client.newCall(request).enqueue(new Callback() {
      @Override
      public void onFailure(@NotNull Call call, @NotNull IOException e) {
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            showToast("当前网络似乎不太好，请稍后重试");
          }
        });
      }

      @Override
      public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        final String strData = response.body().string();
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            int data = Integer.parseInt(strData);
            if (data == 0) {
              showToast("注册失败!");
              return;
            }

            if (data == 1) {
              showToast("注册成功！");
              finish();
              jumpActivity(LoginActivity.class);
              return;
            }
          }
        });
      }
    });
  }

  private void chooseSex() {
    mUserSexRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
          case R.id.register_man:
            userSex = "1";
            break;
          case R.id.register_woman:
            userSex = "0";
            break;
        }
      }
    });
  }

  private void registerUp() {
    mRegisterUserUpBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (isEmpty()) return;
        // 拼接url
        String param = "name=" + userName + "&password=" + userPassword + "&telPhone=" + userTel + "&sex=" + userSex + "" +
          "&address=" + userAddress + "&monery=" + userMoney + "&mark=" + userMark + "";
        String url = Constant.USER_REGISTER;
        sendNetWorkRequest(url);
      }
    });
  }

  private Boolean isEmpty() {
    userName = mUserNameEd.getText().toString();
    userPassword = mUserPasswordEd.getText().toString();
    userPasswordConfirm = mUserPasswordConfirmEd.getText().toString();
    userAddress = mUserAddressEd.getText().toString();
    userTel = mUserTelEd.getText().toString();
    userMoney = mUserMoneyEd.getText().toString();
    userMark = mUserMarkEd.getText().toString();

    if (userName.length() == 0) {
      showToast("用户名不能为空");
      return true;
    }

    if (userPassword.length() == 0) {
      showToast("密码不能为空");
      return true;
    }

    if (userPasswordConfirm.length() == 0) {
      showToast("确认密码不能为空");
      return true;
    }

    if (!userPasswordConfirm.equals(userPassword)) {
      showToast("两次密码不一致");
      return true;
    }

    if (userAddress.length() == 0) {
      showToast("地址不能为空");
      return true;
    }

    if (userTel.length() == 0) {
      showToast("电话不能为空");
      return true;
    }

    if (userMoney.length() == 0) {
      showToast("金额不能为空");
      return true;
    }


    if (userMark.length() == 0) {
      showToast("简介不能为空");
      return true;
    }

    if (userSex == null || userSex.length() == 0) {
      showToast("请选择性别");
      return true;
    }

    return false;
  }
}