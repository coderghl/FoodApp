package com.example.a01tow.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 这个类就是快速调用sharedPreferences，用来保存常用数据
 */
public class SharedPreferencesUtil {
  private SharedPreferences mPreferences;
  private SharedPreferences.Editor editor;

  public SharedPreferencesUtil(String fileName, Context context, int mode) {
    // 通过传递过来的文件名和context进行实例化
    mPreferences = context.getSharedPreferences(fileName, mode);

    // 创建editor方便后续向文件写入内容
    editor = mPreferences.edit();
  }

  public void mSetString(String key, String value) {
    editor.putString(key, value);
    editor.apply();
  }

  public void mSetBoolean(String key, Boolean value) {
    editor.putBoolean(key, value);
    editor.apply();
  }

  public void mSetInt(String key, int value) {
    editor.putInt(key, value);
    editor.apply();
  }

  public String mGetString(String key) {
    // 默认返回null
    return mPreferences.getString(key, null);
  }

  public boolean mGetBoolean(String key) {
    // 默认返回false
    return mPreferences.getBoolean(key, false);
  }

  public void clearAllData() {
    editor.clear();
    editor.apply();
  }
}
