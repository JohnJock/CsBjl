package com.android.bjl.activity;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.android.bjl.R;
import com.android.bjl.app.ActivityController;
import com.android.bjl.data.ContentManager;
import com.android.bjl.util.DialogUtils;
import com.android.bjl.util.TitBarUtils;
import com.gyf.barlibrary.ImmersionBar;

import java.util.List;

/**
 * 王京杰
 * 日期：2017/12/28
 * 邮箱：wangjj@bjrrtx.com
 */

public class BaseActivity extends AppCompatActivity  {
    private ImmersionBar immersionBar;
    protected Dialog loadingDialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingDialog = DialogUtils.loadingDialog(this);//每个Activity全局只有一个，避免多个实例忘了取消弾框
        ActivityController.addActivity(this);
//        immersionBar = ImmersionBar.with(this);
////        immersionBar.statusBarDarkFont(true, 0.2f);
//        immersionBar.statusBarColor(R.color.titleColor)
//                .fitsSystemWindows(true);
//        immersionBar.init();

//        ImmersionBar.with(this)
//                .transparentStatusBar()  //透明状态栏，不写默认透明色
//                .transparentNavigationBar()  //透明导航栏，不写默认黑色(设置此方法，fullScreen()方法自动为true)
//                .transparentBar()             //透明状态栏和导航栏，不写默认状态栏为透明色，导航栏为黑色（设置此方法，fullScreen()方法自动为true）
//                .statusBarColor(R.color.colorPrimary)     //状态栏颜色，不写默认透明色
//                .navigationBarColor(R.color.colorPrimary) //导航栏颜色，不写默认黑色
//                .barColor(R.color.colorPrimary)  //同时自定义状态栏和导航栏颜色，不写默认状态栏为透明色，导航栏为黑色
//                .statusBarAlpha(0.3f)  //状态栏透明度，不写默认0.0f
//                .navigationBarAlpha(0.4f)  //导航栏透明度，不写默认0.0F
//                .barAlpha(0.3f)  //状态栏和导航栏透明度，不写默认0.0f
//                .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
//                .flymeOSStatusBarFontColor(R.color.btn3)  //修改flyme OS状态栏字体颜色
//                .fullScreen(true)      //有导航栏的情况下，activity全屏显示，也就是activity最下面被导航栏覆盖，不写默认非全屏
//                .hideBar(BarHide.FLAG_HIDE_BAR)  //隐藏状态栏或导航栏或两者，不写默认不隐藏
//                .addViewSupportTransformColor(toolbar)  //设置支持view变色，可以添加多个view，不指定颜色，默认和状态栏同色，还有两个重载方法
//                .titleBar(view)    //解决状态栏和布局重叠问题，任选其一
//                .titleBarMarginTop(view)     //解决状态栏和布局重叠问题，任选其一
//                .statusBarView(view)  //解决状态栏和布局重叠问题，任选其一
//                .fitsSystemWindows(true)    //解决状态栏和布局重叠问题，任选其一，默认为false，当为true时一定要指定statusBarColor()，不然状态栏为透明色，还有一些重载方法
//                .supportActionBar(true) //支持ActionBar使用
//                .statusBarColorTransform(R.color.orange)  //状态栏变色后的颜色
//                .navigationBarColorTransform(R.color.orange) //导航栏变色后的颜色
//                .barColorTransform(R.color.orange)  //状态栏和导航栏变色后的颜色
//                .removeSupportView(toolbar)  //移除指定view支持
//                .removeSupportAllView() //移除全部view支持
//                .navigationBarEnable(true)   //是否可以修改导航栏颜色，默认为true
//                .navigationBarWithKitkatEnable(true)  //是否可以修改安卓4.4和emui3.1手机导航栏颜色，默认为true
//                .fixMarginAtBottom(true)   //已过时，当xml里使用android:fitsSystemWindows="true"属性时,解决4.4和emui3.1手机底部有时会出现多余空白的问题，默认为false，非必须
//                .addTag("tag")  //给以上设置的参数打标记
//                .getTag("tag")  //根据tag获得沉浸式参数
//                .reset()  //重置所以沉浸式参数
//                .keyboardEnable(true)  //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
//                .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)  //单独指定软键盘模式
//                .setOnKeyboardListener(new OnKeyboardListener() {    //软键盘监听回调
//                    @Override
//                    public void onKeyboardChange(boolean isPopup, int keyboardHeight) {
//                        LogUtils.e(isPopup);  //isPopup为true，软键盘弹出，为false，软键盘关闭
//                    }
//                })
//                .init();  //必须调用方可沉浸式

    }

    @Override
    protected void onStop() {
        if (!isAppOnForeground()) {
            //app 进入后台
            ContentManager.getInstance(this).setActive(true);
            Log.i("ACTIVITY", "程序进入后台");
        }
        super.onStop();
    }

    /**
     * APP是否处于前台唤           醒状态
     *
     * @return
     */
    public boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

    protected void loadDialogShow() {
        if (getApplicationContext() != null) {
            if (loadingDialog != null && !loadingDialog.isShowing()) {
                loadingDialog.show();
            }
        }
    }

    protected void loadDialogDismiss() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }
    /**
     * @Description: 只有中间的标题
     */
    protected void showTitle(String title) {
        TitBarUtils.titleBar(this, title);
    }

    /**
     * @Description: 左边是文字
     */
    protected void showTitleLeft(String title, String leftTitle, View.OnClickListener listener) {
        TitBarUtils.titleBarLeftTv(this, title, leftTitle, listener);
    }
    /**
     * @Description: 右边是文字
     */
    protected void showTitleRight(String title, String RightTitle,
                                  View.OnClickListener listener) {
        TitBarUtils.titleBarRightTv(this, title, RightTitle, listener);
    }

    /**
     * @Description:  左右都有文字
     */
    protected void showTitleTv(String title, String leftTitle,
                               String rightTitle, View.OnClickListener leftListener,
                               View.OnClickListener rightListener) {
        TitBarUtils.titleTv(this, title, leftTitle, rightTitle, leftListener,
                rightListener);
    }

    /**
     * @Description:  左边是图片
     */
    protected void showTitleLeftImg(String title, int leftTitle,
                                    View.OnClickListener listener) {
        TitBarUtils.titleBarLeftImg(this, title, leftTitle, listener);
    }

    /**
     * @Description:  右边是图片
     */
    protected void showTitleRightImg(String title, int rightTitle,
                                     View.OnClickListener listener) {
        TitBarUtils.titleBarRightImg(this, title, rightTitle, listener);
    }

    /**
     * @Description:  左右都是图片
     */
    protected void showTitleImg(String title, int leftTitle, int rightTitle,
                                View.OnClickListener leftListener, View.OnClickListener rightListener) {
        TitBarUtils.titleBarImg(this, title, leftTitle, rightTitle,
                leftListener, rightListener);
    }

    /**
     * @Description:  左边是图片，右边是文字和图片
     */
    protected void showTitleRightTvAndImg(String title, int leftTitle, int rightTitleImg, String rightTitle,
                                          View.OnClickListener leftListener, View.OnClickListener rightListener) {
        TitBarUtils.titleBarRightTextImg(this, title, leftTitle, rightTitleImg, rightTitle, leftListener, rightListener);
    }

    /**
     * @Description:  左边图片，右边文字
     */
    protected void showTitleTvAndImg(String title, int leftTitle, String rightTitle,
                                     View.OnClickListener leftListener, View.OnClickListener rightListener) {
        TitBarUtils.titleBarRightText(this, title, leftTitle, rightTitle, leftListener, rightListener);
    }
    /**
     * @Description:  左边文字和图片，中间标题
     */
    protected void showTitleLeftTvAndImg(String title, String titleLeft, int imgLeft, View.OnClickListener leftListner) {
        TitBarUtils.titleBarLeftTextImg(this, title, titleLeft, imgLeft, leftListner);
    }
    /**
     * @Description:  左边文字和图片，中间标题,右边文字
     */
    protected void showTitleLeftTvAndImgAndRightTv(String title, String titleLeft, int imgLeft, String textRight,View.OnClickListener leftListner,View.OnClickListener rightListener) {
        TitBarUtils.titleBarLeftTextImgRightTv(this, title, titleLeft, imgLeft,textRight, leftListner,rightListener);
    }
    protected  void showDialogMsg(String msg){
        DialogUtils.showDialog(this, true, msg, null, null);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (immersionBar != null) {
            immersionBar.destroy();//必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
        }
    }

    // startActivity
    protected void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    // startActivity
    protected void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    // startActivityForResult
    protected void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    // startActivityForResult
    protected void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    // getIntent
    protected Bundle getIntentExtra() {
        Intent intent = getIntent();
        Bundle bundle = null;
        if (null != intent)
            bundle = intent.getExtras();
        return bundle;
    }





}
