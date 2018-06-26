package com.android.bjl.fragment;

import android.view.View;

import com.android.bjl.R;
import com.android.bjl.util.TitBarUtils;

public class ThirdFragment extends BaseFragment {
    @Override
    protected void initView(View view) {
        TitBarUtils.FragmentTitleBar(view, "出租房管理");    }

    @Override
    protected int getViewRes() {
        return R.layout.fragment_third;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
    }
}
