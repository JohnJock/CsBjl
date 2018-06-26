package com.android.bjl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.bjl.R;
import com.example.mjj.selectphotodemo.FollowWeChatPhotoActivity;

/**
 * Created by john on 2018/4/18.
 */

public class CommIndexActivity extends BaseActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commindex);
        startActivity(new Intent(this, FollowWeChatPhotoActivity.class));
    }
}
