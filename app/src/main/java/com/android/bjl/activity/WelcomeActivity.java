package com.android.bjl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.ScaleAnimation;

import com.android.bjl.R;
import com.android.bjl.util.PreUtils;


/**
 * 王京杰
 * 日期：2017/12/28
 * 邮箱：wangjj@bjrrtx.com
 */

public class WelcomeActivity extends BaseActivity {

    AlphaAnimation alphaAnimation;
    ScaleAnimation scaleAnimation;
    View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        view = findViewById(R.id.activity_wel);
        alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(1000);
        scaleAnimation = new ScaleAnimation(0, 2, 0, 2);
        scaleAnimation.setDuration(1000);
        view.startAnimation(alphaAnimation);
//        view.startAnimation(scaleAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                if (PreUtils.getInt(WelcomeActivity.this, "lead", 0) == 0) {
//                    startActivity(LeadActivity.class);
//                } else {
//                    startActivity(IndexActivity.class);
//                }
                startActivity(IndexActivity.class);
                WelcomeActivity.this.finish();
            }
        }, 1000);
    }
}
