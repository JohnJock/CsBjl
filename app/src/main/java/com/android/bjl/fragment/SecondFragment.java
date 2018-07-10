package com.android.bjl.fragment;

import android.content.Intent;
import android.view.View;

import com.android.bjl.R;
import com.android.bjl.activity.BaoGuangActivity;
import com.android.bjl.activity.NullActivity;
import com.android.bjl.util.TitBarUtils;

public class SecondFragment extends BaseFragment {
    @Override
    protected void initView(View view) {
        TitBarUtils.FragmentTitleBar(view, "农村治理");
        view.findViewById(R.id.second_rl1).setOnClickListener(this);
        view.findViewById(R.id.second_rl2).setOnClickListener(this);
        view.findViewById(R.id.second_rl3).setOnClickListener(this);
        view.findViewById(R.id.second_rl4).setOnClickListener(this);
    }

    @Override
    protected int getViewRes() {
        return R.layout.fragment_second;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.second_rl1:
                intent.setClass(getActivity(), BaoGuangActivity.class);
                intent.putExtra("title", "垃圾分类");
                break;
            case R.id.second_rl2:
                intent.setClass(getActivity(), BaoGuangActivity.class);
                intent.putExtra("title", "污水治理");
                break;
            case R.id.second_rl3:
                intent.setClass(getActivity(), BaoGuangActivity.class);
                intent.putExtra("title", "房前屋后整治");
                break;
            case R.id.second_rl4:
                intent.setClass(getActivity(), NullActivity.class);
                intent.putExtra("title", "护卫队");
                break;
            default:
                break;
        }
        startActivity(intent);
    }
}
