package com.example.a01tow.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a01tow.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class FoodFragment extends Fragment {
  private TabLayout mTabCard;
  private ViewPager mViewPager;

  private List<String> mTitleList = new ArrayList<>();
  private List<Fragment> mFragmentList = new ArrayList<>();

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View homeView = inflater.inflate(R.layout.fragment_food, container, false);
    // 防止重复创建fragment
    if (mFragmentList.size() == 0) {
      initData();
    }
    // 获取控件以及一些初始化
    initView(homeView);
    return homeView;
  }


  private void initView(View v) {
    mTabCard = v.findViewById(R.id.tab_card);
    mViewPager = v.findViewById(R.id.view_pager);

    // 设置TabCard
    SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getContext(), getActivity().getSupportFragmentManager(), mTitleList, mFragmentList);
    mViewPager.setAdapter(sectionsPagerAdapter);
    mTabCard.setupWithViewPager(mViewPager);
  }

  private void initData() {
    mTitleList.add("肉菜");
    mTitleList.add("素菜");

    mFragmentList.add(FoodTabCardOneFragment.newInstance());
    mFragmentList.add(FoodTabCardTowFragment.newInstance());
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }


  public FoodFragment() {
  }

  public static FoodFragment newInstance() {
    return new FoodFragment();
  }
}