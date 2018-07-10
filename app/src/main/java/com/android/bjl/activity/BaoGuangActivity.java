package com.android.bjl.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.android.bjl.R;

/**
 * author ：wangjingjie
 * date：2018/7/10
 */
public class BaoGuangActivity extends BaseActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baoguang);
        showTitleLeftTvAndImg(getIntent().getStringExtra("title"), "返回", R.mipmap.back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
