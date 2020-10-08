package com.example.a01tow.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.request.transition.Transition;
import com.example.a01tow.R;
import com.example.a01tow.util.Constant;
import com.example.a01tow.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends BaseActivity {
  private Button mGuideStart;
  private ViewPager mViewPager;
  private SharedPreferencesUtil sharedPreferencesUtil;
  private List<View> mListData = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_guide);

    //设置数据
    initData();

    // 初始化
    initView();

    // 绑定事件
    bindEvent();
  }

  private void initView() {
    sharedPreferencesUtil = new SharedPreferencesUtil(Constant.USER_IS_LOOK_GUIDE, GuideActivity.this, Context.MODE_PRIVATE);
    mGuideStart = findViewById(R.id.guide_start);
    mViewPager = findViewById(R.id.guide_view_pager);
    mViewPager.setAdapter(new mViewPageAdapter());
  }

  private void initData() {
    ImageView view1 = new ImageView(GuideActivity.this);
    view1.setScaleType(ImageView.ScaleType.FIT_XY);
    view1.setImageResource(R.mipmap.guid1);
    mListData.add(view1);

    ImageView view2 = new ImageView(GuideActivity.this);
    view2.setScaleType(ImageView.ScaleType.FIT_XY);
    view2.setImageResource(R.mipmap.guid2);
    mListData.add(view2);

    ImageView view3 = new ImageView(GuideActivity.this);
    view3.setScaleType(ImageView.ScaleType.FIT_XY);
    view3.setImageResource(R.mipmap.guid3);
    mListData.add(view3);

    ImageView view4 = new ImageView(GuideActivity.this);
    view4.setScaleType(ImageView.ScaleType.FIT_XY);
    view4.setImageResource(R.mipmap.guid4);
    mListData.add(view4);
  }

  private void bindEvent() {
    mGuideStart.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        sharedPreferencesUtil.mSetBoolean("lookGuide", true);
        // 跳转
        jumpActivityClearBefore(LoginActivity.class);
      }
    });

    mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

      }

      @Override
      public void onPageSelected(int position) {
        if (position == 3) {
          mGuideStart.setVisibility(View.VISIBLE);
        } else {
          mGuideStart.setVisibility(View.GONE);
        }
      }

      @Override
      public void onPageScrollStateChanged(int state) {

      }
    });
  }

  class mViewPageAdapter extends PagerAdapter {

    @Override
    public int getCount() {
      return mListData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
      return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
      container.addView(mListData.get(position));
      return mListData.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
      container.removeView(mListData.get(position));
    }
  }
}