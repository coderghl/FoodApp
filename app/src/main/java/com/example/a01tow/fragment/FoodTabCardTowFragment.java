package com.example.a01tow.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a01tow.R;
import com.example.a01tow.activity.FoodInfoActivity;
import com.example.a01tow.adapter.FoodTabCardListAdapter;
import com.example.a01tow.bean.FoodAllBean;
import com.example.a01tow.util.Constant;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

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

public class FoodTabCardTowFragment extends Fragment implements OnBannerListener {
  private ListView mFoodTabCardTowListContent;
  private FoodTabCardListAdapter mAdapter;
  private Banner mBannerMain;
  private FoodAllBean foodAllBean;
  private List<FoodAllBean.DataBean> data;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_food_tab_card_tow, container, false);
    initView(view);
    bindEvent();
    return view;
  }

  private void initView(View v) {
    mFoodTabCardTowListContent = v.findViewById(R.id.food_tab_card_tow_list_content);
    // 获取banner布局
    View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_banner, mFoodTabCardTowListContent, false);
    mBannerMain = view.findViewById(R.id.banner_main);
    mAdapter = new FoodTabCardListAdapter();
    // 请求数据
    getData();
  }

  private void bindEvent() {
    mFoodTabCardTowListContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // 通过食品id去请求数据
        String fid = data.get(position - 1).getFid();
        Intent intent = new Intent(getActivity(), FoodInfoActivity.class);
        intent.putExtra("fid", fid);
        startActivity(intent);
      }
    });

    mFoodTabCardTowListContent.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
      @Override
      public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("确定要收藏吗？");

        alertDialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            Toast.makeText(getContext(), "已收藏", Toast.LENGTH_SHORT).show();
          }
        });

        alertDialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            Toast.makeText(getContext(), "已取消", Toast.LENGTH_SHORT).show();
          }
        });
        alertDialog.show();
        return true;
      }
    });
  }

  private void getData() {
    OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(10000, TimeUnit.MILLISECONDS).build();
    Request request = new Request.Builder().url(Constant.FOOD_ALL).get().build();
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
        String strJson = response.body().string();
        Gson gson = new Gson();
        foodAllBean = gson.fromJson(strJson, FoodAllBean.class);

        getActivity().runOnUiThread(new Runnable() {
          @Override
          public void run() {
            // 向adapter中添加数据
            data = foodAllBean.getData();
            mAdapter.setData(data);
            // 轮播图添加到顶部
            mFoodTabCardTowListContent.addHeaderView(mBannerMain);
            setBannerData();
            mFoodTabCardTowListContent.setAdapter(mAdapter);
          }
        });
      }
    });
  }

  // 给轮播图设置数据
  public void setBannerData() {
    List<FoodAllBean.DataBean> data = foodAllBean.getData();
    List<String> titleList = new ArrayList<>();
    List<String> imgUrlList = new ArrayList<>();
    for (FoodAllBean.DataBean datum : data) {
      titleList.add(datum.getName());
      String iconUrl = Constant.FOOD_ICON + (datum.getImgsrc().substring(2));
      imgUrlList.add(iconUrl);
    }
    // 图片加载
    mBannerMain.setImageLoader(new ImageLoader() {
      @Override
      public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).into(imageView);
      }
    });

    //设置轮播的动画效果,里面有很多种特效,可以到GitHub上查看文档。
    mBannerMain.setBannerAnimation(Transformer.Accordion);
    mBannerMain.setImages(imgUrlList);//设置图片资源
    mBannerMain.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);//设置banner显示样式（带标题的样式）
    mBannerMain.setBannerTitles(titleList); //设置标题集合（当banner样式有显示title时）
    //设置指示器位置（即图片下面的那个小圆点）
    mBannerMain.setIndicatorGravity(BannerConfig.CENTER);
    mBannerMain.setDelayTime(3000);//设置轮播时间3秒切换下一图
    mBannerMain.setOnBannerListener(this);//设置监听
    mBannerMain.start();//开始进行banner渲染
  }

  @Override
  public void OnBannerClick(int position) {
    String fid = data.get(position).getFid();
    Intent intent = new Intent(getActivity(), FoodInfoActivity.class);
    intent.putExtra("fid", fid);
    startActivity(intent);
  }

  public FoodTabCardTowFragment() {
  }

  public static FoodTabCardTowFragment newInstance() {
    return new FoodTabCardTowFragment();
  }


}