package com.example.a01tow.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.example.a01tow.R;
import com.example.a01tow.util.Constant;
import com.example.a01tow.util.SharedPreferencesUtil;

import java.util.Timer;
import java.util.TimerTask;

public class WeComeActivity extends BaseActivity {

  private SharedPreferencesUtil sharedPreferencesUtil;
  private TextView jumpTimeText;
  private int timeNum = 3; // 延迟三秒跳转
  private Timer timer = new Timer();
  private Handler handler;
  private Runnable runnable;
  // 文字倒计时跳转
  private TimerTask task = new TimerTask() {
    @Override
    public void run() {
      runOnUiThread(new Runnable() {
        @Override
        public void run() {
          timeNum--;
          jumpTimeText.setText("跳过" + timeNum);
          if (timeNum == 0) {
            jumpTimeText.setVisibility(View.GONE);
          }
        }
      });
    }
  };


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_we_come);
    initView();

    timer.schedule(task, 1000, 1000);// 等待一秒停顿一秒

    // 倒计时跳转
    loadingJump();

    // 监听事件
    bindEvent();
  }

  private void loadingJump() {
    handler = new Handler();
    handler.postDelayed(runnable = new Runnable() {
      @Override
      public void run() {
        isLooKGuide();
      }
    }, 3000);
  }

  private void bindEvent() {
    jumpTimeText.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        handler.removeCallbacks(runnable);
        isLooKGuide();
      }
    });
  }

  private void isLooKGuide() {
    // 判断用户是否看过引导页
    if (sharedPreferencesUtil.mGetBoolean("lookGuide")) {
      jumpActivityClearBefore(LoginActivity.class);
    } else {
      jumpActivityClearBefore(GuideActivity.class);
    }
  }

  private void initView() {
    jumpTimeText = findViewById(R.id.jump_time_text);
    sharedPreferencesUtil = new SharedPreferencesUtil(Constant.USER_IS_LOOK_GUIDE, WeComeActivity.this, Context.MODE_PRIVATE);
  }

}