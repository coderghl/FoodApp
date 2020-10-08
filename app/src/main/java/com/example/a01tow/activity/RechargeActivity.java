package com.example.a01tow.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a01tow.R;
import com.example.a01tow.db.HistoryDbHelp;
import com.example.a01tow.fragment.AboutFragment;
import com.example.a01tow.util.Constant;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;

public class RechargeActivity extends BaseActivity implements View.OnClickListener {
  private ImageView mRechargeBack;
  private Button mRechargeUp;
  private TextView mRechargeHistory, mRechargeId;
  private EditText mRechargeInp;
  private HistoryDbHelp dbHelp;
  private String money;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recharge);
    // 设置数据库
    setDataBase();
    // 获取控件
    initView();
    // 监听点击事件
    bindEvent();
  }

  private void setDataBase() {
    // 设置数据库可视化
    SQLiteStudioService.instance().start(this);
    dbHelp = new HistoryDbHelp(RechargeActivity.this);
  }

  private void initView() {
    mRechargeBack = findViewById(R.id.recharge_back);
    mRechargeUp = findViewById(R.id.recharge_up);
    mRechargeHistory = findViewById(R.id.recharge_history);
    mRechargeId = findViewById(R.id.recharge_id);
    mRechargeInp = findViewById(R.id.recharge_inp);
    // 设置账户
    mRechargeId.setText(AboutFragment.id);
  }

  public void bindEvent() {
    mRechargeBack.setOnClickListener(this);
    mRechargeHistory.setOnClickListener(this);
    mRechargeUp.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.recharge_back:
        finish();
        break;
      case R.id.recharge_up:
        sendNetWork();
        break;
      case R.id.recharge_history:
        jumpActivity(HistoryActivity.class);
        break;
    }
  }

  private void sendNetWork() {
    money = mRechargeInp.getText().toString();
    int resultMoney = Integer.parseInt(money);

    if (resultMoney == 0 || money.length() == 0) {
      showToast("请输入正确的值");
      return;
    }

    // 拼接url
    String resultUrl = Constant.ADD_MONEY + AboutFragment.id + "&monery=" + money + "";
    OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(10000, TimeUnit.MILLISECONDS).build();
    Request request = new Request.Builder().url(resultUrl).get().build();
    client.newCall(request).enqueue(new Callback() {
      @Override
      public void onFailure(@NotNull Call call, @NotNull IOException e) {
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            showToast("当前网络不太好，请稍后再试");
          }
        });
      }

      @Override
      public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        final String data = response.body().string();
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            int i = Integer.parseInt(data);
            if (i == 1) {
              // 设置数据并且保存到数据库
              saveData();
            }
            if (i == 0) {
              showToast("充值失败");
            }
          }
        });
      }
    });
  }

  private void saveData() {
    // 创建数据库or打开数据库
    SQLiteDatabase db = dbHelp.getWritableDatabase();
    ContentValues values = new ContentValues();

    // 获取当前时间
    String nowTime = null;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");

    //获取当前时间
    Date date = new Date(System.currentTimeMillis());
    nowTime = simpleDateFormat.format(date);

    values.put("accost", AboutFragment.id);
    values.put("rechargeMoney", money);
    values.put("time", nowTime);

    db.insert(Constant.TABLE_NAME, null, values);

    showToast("充值成功!");
    // 清空输入框
    mRechargeInp.setText("");
    // 关闭释放资源
    values.clear();
    db.close();
  }

}