package com.android.bjl.fragment;

import android.content.Intent;
import android.view.View;

import com.android.bjl.R;
import com.android.bjl.activity.BaoGuangActivity;
import com.android.bjl.activity.MyRentActivity;
import com.android.bjl.activity.MyRentListActivity;
import com.android.bjl.activity.NullActivity;
import com.android.bjl.util.TitBarUtils;

public class ThirdFragment extends BaseFragment {
    @Override
    protected void initView(View view) {
        TitBarUtils.FragmentTitleBar(view, "出租房管理");
        view.findViewById(R.id.third_rl1).setOnClickListener(this);
        view.findViewById(R.id.third_rl2).setOnClickListener(this);
        view.findViewById(R.id.third_rl3).setOnClickListener(this);
        view.findViewById(R.id.third_rl4).setOnClickListener(this);}

    @Override
    protected int getViewRes() {
        return R.layout.fragment_third;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.third_rl1:
                intent.setClass(getActivity(), MyRentListActivity.class);
                intent.putExtra("title", "房屋出租");
                intent.putExtra("type",2);
                break;
            case R.id.third_rl2:
                intent.setClass(getActivity(), MyRentActivity.class);
                break;
            case R.id.third_rl3:
                intent.setClass(getActivity(), NullActivity.class);
                intent.putExtra("title", "租客信息登记");
                break;
            case R.id.third_rl4:
                intent.setClass(getActivity(), NullActivity.class);
                intent.putExtra("title", "房东信息登记");
                break;
            default:
                break;
        }
        startActivity(intent);
    }
}
