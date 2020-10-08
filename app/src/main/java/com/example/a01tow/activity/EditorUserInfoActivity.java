package com.example.a01tow.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.a01tow.R;
import com.example.a01tow.bean.UserInfoBean;
import com.example.a01tow.fragment.AboutFragment;
import com.example.a01tow.util.Constant;
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

public class EditorUserInfoActivity extends BaseActivity implements View.OnClickListener {
  private EditText mEditorUserName, mEditorUserPassword, mEditorUserPasswordConfirm,
    mEditorUserAddress, mEditorUserTel, mEditorUserMoney, mEditorUserMark;
  private RadioGroup mEditorSex;
  private Button mEditorUp;
  private ImageView mEditorBack;
  private RadioButton mMan, mWoman;

  private String userName, userPassword, userPasswordConfirm,
    userAddress, userTel, userMoney, userMark, userSex;
  private List<UserInfoBean.DataBean> data;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_editor_user_info);
    // 初始化控件
    initView();
    // 绑定事件
    bindEvent();
    // 填充数据
    initData();
    // 选择性别
    chooseSex();
  }


  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.editor_user_up:
        sendNetWorkEditUserInfo();
        break;
      case R.id.editor_user_back:
        finish();
        break;
    }
  }


  private void initView() {
    mEditorUserName = findViewById(R.id.editor_user_name);
    mEditorUserPassword = findViewById(R.id.editor_user_password);
    mEditorUserPasswordConfirm = findViewById(R.id.editor_user_confirm_password);
    mEditorUserAddress = findViewById(R.id.editor_user_address);
    mEditorUserTel = findViewById(R.id.editor_user_tel);
    mEditorUserMoney = findViewById(R.id.editor_user_money);
    mEditorUserMark = findViewById(R.id.editor_user_mark);
    mEditorSex = findViewById(R.id.editor_user_sex);
    mEditorUp = findViewById(R.id.editor_user_up);
    mEditorBack = findViewById(R.id.editor_user_back);
    mMan = findViewById(R.id.editor_man);
    mWoman = findViewById(R.id.editor_woman);
  }

  private void bindEvent() {
    mEditorUp.setOnClickListener(this);
    mEditorBack.setOnClickListener(this);
  }

  private void chooseSex() {
    mEditorSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
          case R.id.editor_man:
            userSex = "1";
            break;
          case R.id.editor_woman:
            userSex = "0";
            break;
        }
      }
    });
  }

  private void initData() {
    data = AboutFragment.data;
    mEditorUserName.setText(data.get(0).getName());
    mEditorUserAddress.setText(data.get(0).getAddress());
    mEditorUserTel.setText(data.get(0).getTelPhone());
    mEditorUserMoney.setText(data.get(0).getMonery());
    mEditorUserMark.setText(data.get(0).getMark());
    setSex();
  }

  private void setSex() {
    String sex = data.get(0).getSex();
    int i = Integer.parseInt(sex);
    if (i == 1) {
      mMan.setChecked(true);
    } else {
      mWoman.setChecked(true);
    }
  }

  private void sendNetWorkEditUserInfo() {
    if (isEmpty()) return;
    // 拼接url
    String resultUrl = Constant.EDITOR_USER_MESSAGE + "id=" + AboutFragment.id + "&name=" + userName + "&" +
      "password=" + userPassword + "&telPhone=" + userTel + "&sex=" + userSex + "&address=" + userAddress + "&" +
      "monery=" + userMoney + "&mark=" + userMark + "";

    OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(10000, TimeUnit.MILLISECONDS).build();
    Request request = new Request.Builder().url(resultUrl).get().build();
    client.newCall(request).enqueue(new Callback() {
      @Override
      public void onFailure(@NotNull Call call, @NotNull IOException e) {
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            showToast("网络不太好，请稍后再试");
          }
        });
      }

      @Override
      public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        final String str = response.body().string();
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            int i = Integer.parseInt(str);
            if (i == 1) {
              showToast("修改成功");
              finish();
            }

            if (i == 0) {
              showToast("修改失败");
            }
          }
        });
      }
    });
  }


  // 判空
  private Boolean isEmpty() {
    userName = mEditorUserName.getText().toString();
    userPassword = mEditorUserPassword.getText().toString();
    userPasswordConfirm = mEditorUserPasswordConfirm.getText().toString();
    userAddress = mEditorUserAddress.getText().toString();
    userTel = mEditorUserTel.getText().toString();
    userMoney = mEditorUserMoney.getText().toString();
    userMark = mEditorUserMark.getText().toString();

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