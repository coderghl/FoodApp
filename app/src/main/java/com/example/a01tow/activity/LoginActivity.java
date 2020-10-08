package com.example.a01tow.activity;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.a01tow.MainActivity;
import com.example.a01tow.R;
import com.example.a01tow.util.Constant;
import com.example.a01tow.util.SharedPreferencesUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
  private Button mLoginUserUpBtn, mLoginUserRegisterBtn;
  private EditText mLoginUserPasswordEd, mLoginUserNameEd;
  private CheckBox mLoginUserSavePasswordCb, mLoginUserAutoLoginCb;
  private SharedPreferencesUtil sharedPreferencesUtil;

  private boolean isAutoLogin = false;
  private boolean isSavePassword = false;

  private String userName;
  private String userPassword;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    // 实例化sharedPreferencesUtil
    sharedPreferencesUtil = new SharedPreferencesUtil(Constant.USER_INFO_FILE_NAME, LoginActivity.this, Context.MODE_PRIVATE);

    // 初始化
    initView();

    // 判断是否保存密码以及是否需要自动登录
    isSavePasswordAndLoginAuto();

    // 绑定事件
    bindEvent();

    chooseOptions();
  }


  private void initView() {
    mLoginUserUpBtn = findViewById(R.id.login_user_up);
    mLoginUserRegisterBtn = findViewById(R.id.login_user_register);
    mLoginUserNameEd = findViewById(R.id.login_user_name);
    mLoginUserPasswordEd = findViewById(R.id.login_user_password);
    mLoginUserSavePasswordCb = findViewById(R.id.login_save_password);
    mLoginUserAutoLoginCb = findViewById(R.id.login_auto_login);

  }

  private void bindEvent() {
    mLoginUserUpBtn.setOnClickListener(this);
    mLoginUserRegisterBtn.setOnClickListener(this);
  }

  private void chooseOptions() {
    mLoginUserSavePasswordCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
          isSavePassword = true;
        } else {
          isSavePassword = false;
        }
      }
    });

    mLoginUserAutoLoginCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
          isAutoLogin = true;
          mLoginUserSavePasswordCb.setChecked(true); // 自动登录就保存密码
        } else {
          isAutoLogin = false;
          mLoginUserSavePasswordCb.setChecked(false);
        }
      }
    });
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.login_user_up:
        loginUp();
        break;
      case R.id.login_user_register:
        jumpActivity(RegisterActivity.class);
        break;
    }
  }

  private void loginUp() {
    userName = mLoginUserNameEd.getText().toString();
    userPassword = mLoginUserPasswordEd.getText().toString();

    if (userName.length() == 0) {
      showToast("请输入用户名");
      return;
    }

    if (userPassword.length() == 0) {
      showToast("请输入密码");
      return;
    }

    // url拼接参数
    String resultUrl = Constant.USER_LOGIN + "name=" + userName + "&password=" + userPassword + "";
    // 发送网络请求
    sendNetWorkRequest(resultUrl);
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
            showToast("当前网络貌似不太好，请稍后再试");
          }
        });
      }

      @Override
      public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        final String strData = response.body().string();
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            if (strData.length() < 3) {
              int data = Integer.parseInt(strData);

              if (data == -1) {
                showToast("当前用户名不存在");
                return;
              }

              if (data == 0) {
                showToast("密码错误,请重新输入");
                return;
              }
            } else {
              String[] userInfo = strData.split("-");
              String id = userInfo[0];
              // 保存id到本地
              sharedPreferencesUtil.mSetString("id", id);
              showToast("登录成功");
              jumpActivityClearBefore(MainActivity.class);
            }
          }
        });
      }
    });
  }


  private void isSavePasswordAndLoginAuto() {
    isSavePassword = sharedPreferencesUtil.mGetBoolean("isSavePassword");
    isAutoLogin = sharedPreferencesUtil.mGetBoolean("isAutoLogin");

    if (isSavePassword) {
      mLoginUserPasswordEd.setText(sharedPreferencesUtil.mGetString("userPassWord"));
      mLoginUserNameEd.setText(sharedPreferencesUtil.mGetString("userName"));
      mLoginUserSavePasswordCb.setChecked(true);
    }

    if (isAutoLogin) {
      mLoginUserPasswordEd.setText(sharedPreferencesUtil.mGetString("userPassWord"));
      mLoginUserNameEd.setText(sharedPreferencesUtil.mGetString("userName"));
      mLoginUserSavePasswordCb.setChecked(true);
      mLoginUserAutoLoginCb.setChecked(true);
      loginUp();
    }
  }


  private void saveInfo() {
    sharedPreferencesUtil.mSetString("userName", userName);
    sharedPreferencesUtil.mSetString("userPassWord", userPassword);
    sharedPreferencesUtil.mSetBoolean("isSavePassword", isSavePassword);
    sharedPreferencesUtil.mSetBoolean("isAutoLogin", isAutoLogin);
  }

  private void noSaveInfo() {
    sharedPreferencesUtil.mSetString("userName", "");
    sharedPreferencesUtil.mSetString("userPassWord", "");
    sharedPreferencesUtil.mSetBoolean("isSavePassword", false);
    sharedPreferencesUtil.mSetBoolean("isAutoLogin", false);
  }

  // 自动登录
  @Override
  protected void onDestroy() {
    super.onDestroy();

    if (isSavePassword) {
      saveInfo();
      return;
    } else {
      noSaveInfo();
    }

    if (isAutoLogin) {
      saveInfo();
      return;
    } else {
      noSaveInfo();
    }
  }
}