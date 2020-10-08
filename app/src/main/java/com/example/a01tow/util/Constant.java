package com.example.a01tow.util;

public class Constant {
  public static final String BASE_URL = "http://192.168.1.5";
  // 登录接口
  public static final String USER_LOGIN = "" + BASE_URL + "/MobileApplicationDevelopment/orderSystem/route/UserRoute.php?type=login&";

  // 注册接口
  public static final String USER_REGISTER = "" + BASE_URL + "/MobileApplicationDevelopment/orderSystem/route/UserRoute.php?type=add&";

  // 获取所有食品数据接口
  public static final String FOOD_ALL = "" + BASE_URL + "/MobileApplicationDevelopment/orderSystem/route/FoodRoute.php?type=selAll";

  // 加载图片接口
  public static final String FOOD_ICON = "" + BASE_URL + "/MobileApplicationDevelopment/orderSystem/";

  // 通过id获取信息接口
  public static final String USER_INFO_QUERY = "" + BASE_URL + "/MobileApplicationDevelopment/orderSystem/route/UserRoute.php?type=selById&id=";

  // 编辑用户信息接口
  public static final String EDITOR_USER_MESSAGE = "" + BASE_URL + "/MobileApplicationDevelopment/orderSystem/route/UserRoute.php?type=update&";

  // 查询food详情接口
  public static final String FOOD_INFO = "" + BASE_URL + "/MobileApplicationDevelopment/orderSystem/route/FoodRoute.php?type=selById&";

  // 用户余额充值接口
  public static final String ADD_MONEY = "" + BASE_URL + "/MobileApplicationDevelopment/orderSystem/route/UserRoute.php?type=addMonery&id=";

  // 用户信息文件名
  public static final String USER_INFO_FILE_NAME = "user_info";

  // 订单文件名
  public static final String USER_ORDER_FILE_NAME = "order";

  // 用户是否看过引导页
  public static final String USER_IS_LOOK_GUIDE = "is_look_guide";

  // 数据库名
  public static final String DB_NAME = "history.db";

  // 数据库版本
  public static final int DB_VERSION = 1;

  // 数据表名
  public static final String TABLE_NAME = "history_item";
}
