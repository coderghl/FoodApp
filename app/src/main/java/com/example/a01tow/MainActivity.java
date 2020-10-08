package com.example.a01tow;

import android.os.Bundle;

import com.example.a01tow.fragment.AboutFragment;
import com.example.a01tow.fragment.FoodFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
  private TextView mFoodFragmentNav, mAboutFragmentNav;
  private SparseArray<Fragment> mFragmentArrayContent = new SparseArray<>();
  private FragmentManager fragmentManager;
  private FragmentTransaction fragmentTransaction;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    if (mFragmentArrayContent.size() == 0) {
      createFragment();
    }
    // 初始化
    initView();
    // 绑定事件
    bindEvent();
  }


  private void initView() {
    mFoodFragmentNav = findViewById(R.id.food_fragment_nav);
    mAboutFragmentNav = findViewById(R.id.about_fragment_nav);
    // 获取fragment管理器
    fragmentManager = getSupportFragmentManager();
    // 默认展示FoodFragment
    defaultFragment();
  }

  private void defaultFragment() {
    fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.add(R.id.main_fragment_content, mFragmentArrayContent.get(R.id.food_fragment_nav));
    fragmentTransaction.add(R.id.main_fragment_content, mFragmentArrayContent.get(R.id.about_fragment_nav));

    // 默认展示food_fragment_nav
    fragmentTransaction.show(mFragmentArrayContent.get(R.id.food_fragment_nav));
    fragmentTransaction.hide(mFragmentArrayContent.get(R.id.about_fragment_nav));
    fragmentTransaction.commit();
  }

  private void createFragment() {
    // 把fragment存储到数组中
    mFragmentArrayContent.append(R.id.food_fragment_nav, FoodFragment.newInstance());
    mFragmentArrayContent.append(R.id.about_fragment_nav, AboutFragment.newInstance());
  }

  private void bindEvent() {
    mFoodFragmentNav.setOnClickListener(this);
    mAboutFragmentNav.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.food_fragment_nav:
        switchFragment(R.id.food_fragment_nav);
        break;
      case R.id.about_fragment_nav:
        switchFragment(R.id.about_fragment_nav);
        break;
    }
  }

  private void switchFragment(int FragmentId) {
    fragmentTransaction = fragmentManager.beginTransaction();
    if (FragmentId == R.id.food_fragment_nav) {
      mFoodFragmentNav.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.food_menu_action), null, null);
      mAboutFragmentNav.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.my), null, null);
      fragmentTransaction.hide(mFragmentArrayContent.get(R.id.about_fragment_nav));
      fragmentTransaction.show(mFragmentArrayContent.get(R.id.food_fragment_nav));
    }

    if (FragmentId == R.id.about_fragment_nav) {
      mFoodFragmentNav.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.food_menu), null, null);
      mAboutFragmentNav.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.my_action), null, null);
      fragmentTransaction.hide(mFragmentArrayContent.get(R.id.food_fragment_nav));
      fragmentTransaction.show(mFragmentArrayContent.get(R.id.about_fragment_nav));
    }
    fragmentTransaction.commit();
  }
}