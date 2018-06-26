package com.android.bjl.fragment;

import android.view.View;

import com.android.bjl.R;
import com.android.bjl.util.TitBarUtils;

public class SecondFragment extends BaseFragment {
    @Override
    protected void initView(View view) {
        TitBarUtils.FragmentTitleBar(view, "农村治理");
    }

    @Override
    protected int getViewRes() {
        return R.layout.fragment_second;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
    }
}
