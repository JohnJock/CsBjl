package com.android.bjl.data;

import android.content.Context;

/**
 * Created by guozhi on 2016/10/31.
 * 提供缓存和文件存储功能的工具类
 */
public class ContentManager {


    private static ContentManager sInstance;

    private ContentManager(Context context) {
        MemoryCache.init();
        FileCache.init(context);
    }

    public static void init(Context context) {
        sInstance = new ContentManager(context);
    }

    public static ContentManager getInstance(Context context) {
        if (sInstance==null)
            sInstance =new ContentManager(context);
        return sInstance;
    }

    /**
     * 缓存
     */
    public void setExample(String example) {
        MemoryCache.getInstance().setExample(example);
    }

    public String getExample() {
        return MemoryCache.getInstance().getExample();
    }
    /**
     * 文件储存
     */
    public void setFileExample(String example) {
        FileCache.getInstance().setFileExample(example);
    }

    public String getFileExample() {
        return FileCache.getInstance().getFileExample();
    }

    public void setCookie(String cookie) {
        FileCache.getInstance().setCookie(cookie);
    }
    public String getCookie() {
        return FileCache.getInstance().getCookie();
    }

    public void setMerName(String merName) {
        FileCache.getInstance().setMerName(merName);
    }
    public String getMerName() {
        return FileCache.getInstance().getMerName();
    }

    public void setUserPhoneNo(String userPhoneNo) {
        FileCache.getInstance().setUserPhoneNo(userPhoneNo);
    }
    public String getUserPhoneNo() {
        return FileCache.getInstance().getUserPhoneNo();
    }
    //预留信息
    public void setPreInf(String preInf) {
        FileCache.getInstance().setPreInf(preInf);
    }
    public String getPreInf() {
     return   FileCache.getInstance().getPreInf();
    }

    public void setadministrators(String userName) {
        FileCache.getInstance().setadministrators(userName);
    }
    public String getadministrators() {
        return FileCache.getInstance().getadministrators();
    }

    public void setUserName(String userName) {
        FileCache.getInstance().setUserName(userName);
    }
    public String getUserName() {
        return FileCache.getInstance().getUserName();
    }
    //手势
    public void setIsShow(String userName) {
        FileCache.getInstance().setIsShow(userName);
    }
    public String getIsShow() {
        return FileCache.getInstance().getIsShow();
    }
    //手势密码
    public void setPass(String pass) {
        FileCache.getInstance().setPass(pass);
    }
    public String getPass() {
        return FileCache.getInstance().getPass();
    }
    //登录
    public void setLogin(String login) {
        FileCache.getInstance().setLogin(login);
    }
    public String getLogin() {
        return FileCache.getInstance().getLogin();
    }
    //余额
    public void setBalance(String balance) {
        FileCache.getInstance().setBalance(balance);
    }
    public String getBalance() {
        return FileCache.getInstance().getBalance();
    }
    //备注
    public void setNote(String note) {
        FileCache.getInstance().setNote(note);
    }
    public String getNote() {
        return FileCache.getInstance().getNote();
    }
    //登录历史记录
    public void setLoginName(String loginName) {
        FileCache.getInstance().setLoginName(loginName);
    }
    public String getLoginName() {
        return FileCache.getInstance().getLoginName();
    }
    //是否选中个人扫码
    public void setMersacn(boolean mersacn) {
        MemoryCache.getInstance().setMersacn(mersacn);
    }
    public boolean getMersacn() {
        return MemoryCache.getInstance().getMersacn();
    }
    //是否直连账户
    public void setDirect(String direct) {
        FileCache.getInstance().setDirect(direct);
    }
    public String getDirect() {
        return FileCache.getInstance().getDirect();
    }
    //是否处于后台
    public void setActive(boolean active) {
        FileCache.getInstance().setActive(active);
    }
    public boolean getActive() {
        return FileCache.getInstance().getActive();
    }
}
