package com.android.bjl.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 王京杰
 * 日期：2017/12/28
 * 邮箱：wangjj@bjrrtx.com
 */

public class PreUtils {


    private  static  final String SHARE_PREFES_NAME="toolsApp";

    public static void putString(Context ctx, String key, String value){
        SharedPreferences preferences =ctx.getSharedPreferences(SHARE_PREFES_NAME, Context.MODE_PRIVATE);
        preferences.edit().putString(key,value).commit();
    }

    public static void putInt(Context ctx, String key, int value){
        SharedPreferences preferences =ctx.getSharedPreferences(SHARE_PREFES_NAME, Context.MODE_PRIVATE);
        preferences.edit().putInt(key,value).commit();
    }

    public static void putBoolean(Context ctx, String key, boolean value){
        SharedPreferences preferences =ctx.getSharedPreferences(SHARE_PREFES_NAME, Context.MODE_PRIVATE);
        preferences.edit().putBoolean(key,value).commit();
    }

    public static String getString(Context ctx, String key, String defaultvalue){
        SharedPreferences preferences =ctx.getSharedPreferences(SHARE_PREFES_NAME, Context.MODE_PRIVATE);
        return preferences.getString(key,defaultvalue);
    }

    public static int getInt(Context ctx, String key, int defaultvalue){
        SharedPreferences preferences =ctx.getSharedPreferences(SHARE_PREFES_NAME, Context.MODE_PRIVATE);
        return preferences.getInt(key,defaultvalue);
    }

    public static Boolean getBoolean(Context ctx, String key, boolean defaultvalue){
        SharedPreferences preferences =ctx.getSharedPreferences(SHARE_PREFES_NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(key,false);
    }
}
