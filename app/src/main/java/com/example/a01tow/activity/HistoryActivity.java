package com.example.a01tow.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.a01tow.R;
import com.example.a01tow.adapter.HistoryListContentAdapter;
import com.example.a01tow.bean.HistoryBean;
import com.example.a01tow.db.HistoryDbHelp;
import com.example.a01tow.util.Constant;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends BaseActivity {
  private RecyclerView mHistoryListContent;
  private ImageView mHistoryBack;
  private HistoryDbHelp historyDbHelp;
  private HistoryListContentAdapter mAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_history);
    // 初始化
    initView();
    // mHistoryBack点击
    bindEvent();
  }

  private void bindEvent() {
    mHistoryBack.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
  }

  public void initView() {
    mHistoryBack = findViewById(R.id.history_back);
    mHistoryListContent = findViewById(R.id.history_list_content);

    // 设置布局管理器
    LinearLayoutManager layoutManager = new LinearLayoutManager(HistoryActivity.this);
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    mHistoryListContent.setLayoutManager(layoutManager);

    // 实例化adapter
    mAdapter = new HistoryListContentAdapter();
    // 从数据库中获取数据
    setData();
  }

  private void setData() {
    historyDbHelp = new HistoryDbHelp(HistoryActivity.this);
    SQLiteDatabase dbHelp = historyDbHelp.getWritableDatabase();
    Cursor cursor = dbHelp.query(Constant.TABLE_NAME, null, null, null, null, null, null, null);
    List<HistoryBean.DataBean> data = new ArrayList<>();
    if (cursor.moveToFirst()) {
      while (cursor.moveToNext()) {
        HistoryBean.DataBean dataBean = new HistoryBean.DataBean();
        dataBean.setAccost(cursor.getString(cursor.getColumnIndex("accost")));
        dataBean.setRechargeMoney(cursor.getString(cursor.getColumnIndex("rechargeMoney")));
        dataBean.setTime(cursor.getString(cursor.getColumnIndex("time")));
        data.add(dataBean);
        // 设置进适配器数据
        mAdapter.setData(data);
      }
    }
    // 设置适配器
    mHistoryListContent.setAdapter(mAdapter);
    cursor.close();
    dbHelp.close();
  }
}