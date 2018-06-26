package com.android.bjl.activity;

import com.android.bjl.R;
import com.android.bjl.adapter.ViewPagerAdapter;
import com.android.bjl.app.ActivityController;
import com.android.bjl.app.MyAppLication;
import com.android.bjl.fragment.FirstFragment;
import com.android.bjl.fragment.MyAccountFragment;
import com.android.bjl.fragment.SecondFragment;
import com.android.bjl.fragment.ThirdFragment;
import com.android.bjl.util.CommonUtil;
import com.android.bjl.util.MyToastUtil;
import com.android.bjl.util.PrefUtils;
import com.android.bjl.widget.NoScrollViewPager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 这是首页的入口
 */
public class IndexActivity extends BaseActivity implements View.OnClickListener {
    public static  RadioButton first, second, third, myaccount;
    public static NoScrollViewPager mPager;
    private FirstFragment firstFragment;
    private SecondFragment secondFragment;
    private ThirdFragment thirdFragment;
    private MyAccountFragment myAccountFragment;
    private static Boolean isExit = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        initView();
    }

    public void initView() {
        first = (RadioButton) findViewById(R.id.radio_first);
        second = (RadioButton) findViewById(R.id.radio_second);
        third = (RadioButton) findViewById(R.id.radio_third);
        myaccount = (RadioButton) findViewById(R.id.radio_myaccount);
        mPager = (NoScrollViewPager) findViewById(R.id.viewfragment);
        first.setOnClickListener(this);
        second.setOnClickListener(this);
        third.setOnClickListener(this);
        myaccount.setOnClickListener(this);
        firstFragment = new FirstFragment();
        secondFragment = new SecondFragment();
        thirdFragment = new ThirdFragment();
        myAccountFragment = new MyAccountFragment();
        List<Fragment> list = new ArrayList<Fragment>();
        list.add(firstFragment);
        list.add(secondFragment);
        list.add(thirdFragment);
        list.add(myAccountFragment);
        mPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), list));
//        mPager.setOnPageChangeListener(onPageChangeListener);
        mPager.setCurrentItem(0);
        mPager.setOffscreenPageLimit(4);
    }
//
//
//    ViewPager.OnPageChangeListener onPageChangeListener =new ViewPager.OnPageChangeListener() {
//        @Override
//        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//        }
//
//        @Override
//        public void onPageSelected(int position) {
//            switch (position) {
//                case 0:
//                    first.setChecked(true);
//
//                    break;
//                case 1:
//                    second.setChecked(true);
//
////                    orderListFragment.againOrderList();
//                    break;
//                case 2:
//                    third.setChecked(true);
//
////                    mineFragment.againAccount();
//                    break;
//                case 3:
//                    myaccount.setChecked(true);
//
//
//                    break;
//                default:
//                    break;
//            }
//        }
//
//        @Override
//        public void onPageScrollStateChanged(int state) {
//
//        }
//    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.radio_first:
                MyAppLication.page = 0;
                mPager.setCurrentItem(0);
                break;
            case R.id.radio_second:
                MyAppLication.page = 1;
                if (CommonUtil.checkLogin(IndexActivity.this)) {
                    mPager.setCurrentItem(1);
                }
                break;
            case R.id.radio_third:
                MyAppLication.page = 2;
                if (CommonUtil.checkLogin(IndexActivity.this)) {
                    mPager.setCurrentItem(2);
                }
                break;
            case R.id.radio_myaccount:
                MyAppLication.page = 3;
                if (CommonUtil.checkLogin(IndexActivity.this)) {
                    mPager.setCurrentItem(3);
                }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            exitBy2Click();
        }
        return false;
    }

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true;
            MyToastUtil.showMEssage(IndexActivity.this, getString(R.string.retime_out));
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            ActivityController.finishAll();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (PrefUtils.getString(IndexActivity.this, "isLogin", "").equals("1")) {
            mPager.setCurrentItem(MyAppLication.page);
        }else {
            MyAppLication.page=0;
            mPager.setCurrentItem(0);
            first.setChecked(true);
            second.setChecked(false);
            third.setChecked(false);
            myaccount.setChecked(false);
        }
    }
}
