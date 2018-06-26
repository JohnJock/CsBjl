package com.android.bjl.util;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.android.bjl.R;

public class MyCountDownTimer extends CountDownTimer {
    TextView textView;
    public MyCountDownTimer(long millisInFuture, long countDownInterval,TextView textView) {
        super(millisInFuture, countDownInterval);
        this.textView =textView;
    }

    @Override
    public void onTick(long l) {
        textView.setClickable(false);
        textView.setTextColor(Color.parseColor("#333333"));
        textView.setText(l/1000+"s");

    }

    @Override
    public void onFinish() {
        //重新给Button设置文字
        textView.setText("重新获取验证码");
        textView.setTextColor(Color.parseColor("#2e88ff"));
        //设置可点击
        textView.setClickable(true);
    }
}
