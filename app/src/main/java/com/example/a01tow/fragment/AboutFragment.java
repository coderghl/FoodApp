package com.example.a01tow.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a01tow.R;
import com.example.a01tow.activity.LoginActivity;
import com.example.a01tow.activity.OrderActivity;
import com.example.a01tow.activity.RechargeActivity;
import com.example.a01tow.activity.UserInfoActivity;
import com.example.a01tow.bean.UserInfoBean;
import com.example.a01tow.util.Constant;
import com.example.a01tow.util.SharedPreferencesUtil;
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

public class AboutFragment extends Fragment implements View.OnClickListener {
  private TextView mAboutUserName, mAboutUserMark, mAboutUserInfo,
    mAboutUserOrder, mAboutUserRecharge;
  private Button mAboutExitLogin;
  private SharedPreferencesUtil sharedPreferencesUtil;

  // 设置为静态id方便后续使用
  public static String id;
  public static List<UserInfoBean.DataBean> data = new ArrayList<>();

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_about, container, false);
    // 初始化控件
    initView(view);
    // 监听事件
    bindEvent();
    // 网络请求加载用户名以及签名
    sentNetWork();
    return view;
  }


  private void initView(View v) {
    mAboutUserName = v.findViewById(R.id.about_user_name);
    mAboutUserMark = v.findViewById(R.id.about_user_mark);
    mAboutUserInfo = v.findViewById(R.id.about_user_info);
    mAboutUserOrder = v.findViewById(R.id.about_user_order);
    mAboutUserRecharge = v.findViewById(R.id.about_user_recharge);
    mAboutExitLogin = v.findViewById(R.id.about_exit_login);
    sharedPreferencesUtil = new SharedPreferencesUtil(Constant.USER_INFO_FILE_NAME, getActivity(), Context.MODE_PRIVATE);
  }

  private void bindEvent() {
    mAboutUserInfo.setOnClickListener(this);
    mAboutUserOrder.setOnClickListener(this);
    mAboutUserRecharge.setOnClickListener(this);
    mAboutExitLogin.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    Intent intent = null;
    switch (v.getId()) {
      case R.id.about_user_info:
        intent = new Intent(getActivity(), UserInfoActivity.class);
        break;
      case R.id.about_exit_login:
        exitLogin();
        return;
      case R.id.about_user_order:
        intent = new Intent(getActivity(), OrderActivity.class);
        break;
      case R.id.about_user_recharge:
        intent = new Intent(getActivity(), RechargeActivity.class);
        break;
    }
    startActivity(intent);
  }

  private void exitLogin() {
    Intent intent = new Intent(getActivity(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    sharedPreferencesUtil.clearAllData();
    startActivity(intent);
  }

  private void sentNetWork() {
    id = sharedPreferencesUtil.mGetString("id");
    OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(10000, TimeUnit.MILLISECONDS).build();
    Request request = new Request.Builder().get().url(Constant.USER_INFO_QUERY + id).build();
    client.newCall(request).enqueue(new Callback() {
      @Override
      public void onFailure(@NotNull Call call, @NotNull IOException e) {
        getActivity().runOnUiThread(new Runnable() {
          @Override
          public void run() {
            Toast.makeText(getContext(), "当前网络不太好,请稍后再试", Toast.LENGTH_SHORT).show();
          }
        });
      }

      @Override
      public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        String jsonStr = response.body().string();
        Gson gson = new Gson();
        final UserInfoBean userInfoBean = gson.fromJson(jsonStr, UserInfoBean.class);
        getActivity().runOnUiThread(new Runnable() {
          @Override
          public void run() {
            if ("200".equals(userInfoBean.getCode())) {
              data = userInfoBean.getData();
              mAboutUserName.setText(data.get(0).getName());
              mAboutUserMark.setText(data.get(0).getMark());
            }
          }
        });
      }
    });
  }

  @Override
  public void onStart() {
    super.onStart();
    sentNetWork();
  }

  public AboutFragment() {
  }

  public static AboutFragment newInstance() {
    return new AboutFragment();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }


}