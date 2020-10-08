package com.example.a01tow.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
  private Context mContext;
  private Toast mToast;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.mContext = this;
  }

  public void jumpActivity(Class target) {
    Intent intent = new Intent(mContext, target);
    startActivity(intent);
  }

  public void jumpActivityClearBefore(Class target) {
    Intent intent = new Intent(mContext, target).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
  }

  public void showToast(String message) {
    if (mToast == null) {
      mToast = Toast.makeText(mContext, message, Toast.LENGTH_SHORT);
    } else {
      mToast.setText(message);
    }
    mToast.show();
  }
}
