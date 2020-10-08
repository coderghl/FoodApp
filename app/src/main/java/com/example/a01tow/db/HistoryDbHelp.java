package com.example.a01tow.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.a01tow.util.Constant;

public class HistoryDbHelp extends SQLiteOpenHelper {
  private Context mContext;
  // 创建数据库
  String createSql = "create table " + Constant.TABLE_NAME + "(" +
    " id integer primary key autoincrement," +
    "accost varchar(30)," +
    "rechargeMoney varchar(30)," +
    "time varchar(30))";

  public HistoryDbHelp(Context context) {
    super(context, Constant.DB_NAME, null, Constant.DB_VERSION);
    this.mContext = context;
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(createSql);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

  }
}
