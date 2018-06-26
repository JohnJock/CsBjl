package com.android.bjl.util;


import android.content.Context;
import android.content.SharedPreferences;

/**
 * 缓存文件保存与读取
 * Created by john on 2016/11/10.
 */
public class PrefUtils {

    //modify   是否设置支付密码 0没有1设置
    //customerNo  商户id
    //name     姓名
    //certNo     身份证
    //sessionId
    //accountNo  缓存银行卡号
    //bankName  缓存银行卡名字
    //result
    //mobile


    private static final String SHARE_PREFS_NAME = "haierspfile";

    public static void putBoolean(Context ctx, String key, boolean value) {
        SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_PRIVATE);

        pref.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context ctx, String key,
                                     boolean defaultValue) {
        SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_PRIVATE);

        return pref.getBoolean(key, defaultValue);
    }

    public static void putString(Context ctx, String key, String value) {
        SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_PRIVATE);
        String var = "";
        try {
            // TODO 增加加密
//            var = new String(DesUtil.getInstance().encrypt(value.getBytes(),
//                    Constant.DES_KEY.getBytes(),
//                    Constant.DES_KEY.getBytes()), "utf-8");
            var = value;
        } catch (Exception e) {
            e.printStackTrace();
        }
        pref.edit().putString(key, var).commit();
    }

    public static String getString(Context ctx, String key, String defaultValue) {
        SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_PRIVATE);
        String var = "";
        try {
            // TODO 增加加密
//            var = new String(DesUtil.getInstance().decrypt(pref.getString(key, defaultValue).getBytes(),
//                    Constant.DES_KEY.getBytes(),
//                    Constant.DES_KEY.getBytes()), "utf-8");
            var = pref.getString(key, defaultValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return var;
    }

    public static void putInt(Context ctx, String key, int value) {
        SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_PRIVATE);

        pref.edit().putInt(key, value).commit();
    }

    public static int getInt(Context ctx, String key, int defaultValue) {
        SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_PRIVATE);

        return pref.getInt(key, defaultValue);
    }

}

