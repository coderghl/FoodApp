package com.example.a01tow.fragment;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.a01tow.R;

import java.util.List;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

  private final Context mContext;
  private final List<String> mTitleList;
  private final List<Fragment> mFragmentList;

  public SectionsPagerAdapter(Context context, FragmentManager fm, List<String> titleList, List<Fragment> fragmentList) {
    super(fm);
    mContext = context;
    this.mTitleList = titleList;
    this.mFragmentList = fragmentList;
  }

  @Override
  public Fragment getItem(int position) {
    return mFragmentList.get(position);
  }

  @Nullable
  @Override
  public CharSequence getPageTitle(int position) {
    return mTitleList.get(position);
  }

  @Override
  public int getCount() {
    return mFragmentList.size();
  }
}