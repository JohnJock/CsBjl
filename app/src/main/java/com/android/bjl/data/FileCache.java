
package com.android.bjl.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * 
 * @author wudongze 
 * 
 * @Package com.example.data
 * 
 * @ClassName: FileCache   
 * 
 * @description 文件存储
 * 
 * @date 2016-3-24 下午6:16:59
 */
public class FileCache {
    private static FileCache sInstance;
    private SharedPreferences mPrefs;
    private static final String EXAMPLE = "example";
    private static final String COOKIE = "cookie";
    private static final String MERNAME = "merName";
    private static final String USERPHONENO = "userPhoneNo";
    private static final String USERNAME = "userName";
    private static final String PREINF = "preInf";
    private static final String ADMINISTRATORS = "administrators";
    private static final String ISSHOW = "isShow";
    private static final String PASS = "pass";
    private static final String LOGIN = "login";
    private static final String BALANCE = "balance";
    private static final String NOTE = "note";
    private static final String LOGINNAME = "loginName";
    private static final String DIRECT = "direct";
    private static final String ACTIVE = "active";
    public static void init(Context context) {
        sInstance = new FileCache(context);
    }
    private FileCache(Context context) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }
    public static FileCache getInstance() {
        return sInstance;
    }

    public boolean setFileExample(String str){
        try {
            Editor editor = mPrefs.edit();
            editor.putString(EXAMPLE, str);
            return editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public String getFileExample() {
        try {
            return mPrefs.getString(EXAMPLE, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getCookie() {
        try {
            return mPrefs.getString(COOKIE, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public boolean setCookie(String cookie) {
        try {
            Editor editor = mPrefs.edit();
            editor.putString(COOKIE, cookie);
            return editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getMerName() {
        try {
            return mPrefs.getString(MERNAME, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public boolean setMerName(String merName) {
        try {
            Editor editor = mPrefs.edit();
            editor.putString(MERNAME, merName);
            return editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getUserPhoneNo() {
        try {
            return mPrefs.getString(USERPHONENO, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public boolean setUserPhoneNo(String userPhoneNo) {
        try {
            Editor editor = mPrefs.edit();
            editor.putString(USERPHONENO, userPhoneNo);
            return editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getUserName() {
        try {
            return mPrefs.getString(USERNAME, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public boolean setUserName(String userName) {
        try {
            Editor editor = mPrefs.edit();
            editor.putString(USERNAME, userName);
            return editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getPreInf() {
        try {
            return mPrefs.getString(PREINF, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public boolean setPreInf(String preInf) {
        try {
            Editor editor = mPrefs.edit();
            editor.putString(PREINF, preInf);
            return editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getadministrators() {
        try {
            return mPrefs.getString(ADMINISTRATORS, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public boolean setadministrators(String administrators) {
        try {
            Editor editor = mPrefs.edit();
            editor.putString(ADMINISTRATORS, administrators);
            return editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getIsShow() {
        try {
            return mPrefs.getString(ISSHOW, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public boolean setIsShow(String isShow) {
        try {
            Editor editor = mPrefs.edit();
            editor.putString(ISSHOW, isShow);
            return editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getPass() {
        try {
            return mPrefs.getString(PASS, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public boolean setPass(String pass) {
        try {
            Editor editor = mPrefs.edit();
            editor.putString(PASS, pass);
            return editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getLogin() {
        try {
            return mPrefs.getString(LOGIN, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public boolean setLogin(String login) {
        try {
            Editor editor = mPrefs.edit();
            editor.putString(LOGIN, login);
            return editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getBalance() {
        try {
            return mPrefs.getString(BALANCE, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public boolean setBalance(String balance) {
        try {
            Editor editor = mPrefs.edit();
            editor.putString(BALANCE, balance);
            return editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public String getNote() {
        try {
            return mPrefs.getString(NOTE, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public boolean setNote(String note) {
        try {
            Editor editor = mPrefs.edit();
            editor.putString(NOTE, note);
            return editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public String getLoginName() {
        try {
            return mPrefs.getString(LOGINNAME, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public boolean setLoginName(String loginName) {
        try {
            Editor editor = mPrefs.edit();
            editor.putString(LOGINNAME, loginName);
            return editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public String getDirect() {
        try {
            return mPrefs.getString(DIRECT, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public boolean setDirect(String direct) {
        try {
            Editor editor = mPrefs.edit();
            editor.putString(DIRECT, direct);
            return editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean getActive() {
        try {
            return mPrefs.getBoolean(ACTIVE, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean setActive(boolean active) {
        try {
            Editor editor = mPrefs.edit();
            editor.putBoolean(ACTIVE, active);
            return editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
