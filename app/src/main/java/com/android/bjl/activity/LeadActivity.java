package com.android.bjl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;


import com.android.bjl.R;
import com.android.bjl.util.PreUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 王京杰
 * 日期：2017/12/28
 * 邮箱：wangjj@bjrrtx.com
 */

public class LeadActivity extends BaseActivity {

    @BindView(R.id.vp_lead)
    ViewPager viewpager;

    List<View> views = new ArrayList<View>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);
        ButterKnife.bind(this);

        views.add(View.inflate(this, R.layout.lead_1, null));
        views.add(View.inflate(this, R.layout.lead_2, null));
        View view =(View.inflate(this, R.layout.lead_3, null));

        view.findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreUtils.putInt(LeadActivity.this,"lead",1);
                Intent intent = new Intent();
//                if (PreUtils.getInt(LeadActivity.this, "login", 0)==1) {
//                    intent.setClass(LeadActivity.this, MainActivity.class);
//                } else {
//                    intent.setClass(LeadActivity.this, LoginActivity.class);
//
//                }
                startActivity(IndexActivity.class);
                finish();
            }
        });
        views.add(view);
        viewpager.setAdapter(new PagerAdapter() {
            /**
             * 获取View的总数
             *
             * @return View总数
             */
            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view ==object;
            }
            /**
             * 为给定的位置创建相应的View。创建View之后,需要在该方法中自行添加到container中。
             *
             * @param container ViewPager本身
             * @param position  给定的位置
             * @return 提交给ViewPager进行保存的实例对象
             */
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                 container.addView(views.get(position));
                return  views.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
    }
}
