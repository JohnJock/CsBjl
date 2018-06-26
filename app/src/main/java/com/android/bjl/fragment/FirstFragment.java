package com.android.bjl.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.bjl.R;
import com.android.bjl.activity.CommIndexActivity;
import com.android.bjl.activity.CwgkActivity;
import com.android.bjl.activity.DwgkListActivity;
import com.android.bjl.activity.FirstActivity;
import com.android.bjl.activity.IndexActivity;
import com.android.bjl.activity.LoginActivity;
import com.android.bjl.activity.MainActivity;
import com.android.bjl.activity.MyCapticalActivity;
import com.android.bjl.activity.MySubsidyActivity;
import com.android.bjl.activity.ServiceActivity;
import com.android.bjl.adapter.FirstFragmentGvAdapter;
import com.android.bjl.adapter.MainGvAdapter;
import com.android.bjl.bean.BannerBean;
import com.android.bjl.util.CommonUtil;
import com.android.bjl.util.PrefUtils;
import com.android.bjl.util.TitBarUtils;
import com.android.bjl.widget.headerlistview.MyGridView;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirstFragment extends BaseFragment {
    private ConvenientBanner convenientBanner;//顶部广告栏控件
    private ArrayList<BannerBean> localImages = new ArrayList<BannerBean>();
    private MyGridView myGridView;
    private ScrollView scrollView;
    private ArrayList<Map<String, Object>> dataList;
    TextView tvRight, title;
    RelativeLayout reRight;
    private
    //图标下的文字
    final String name[] = {"政务公开", "党务公开",
            "财务公开", "惠民政策", "一事一议", "文化礼堂",
            "便民服务", "补贴公示", "村里的事",
            "干部工作", "我的资金"};
    int icno[] = {R.mipmap.icon2, R.mipmap.icon3,
            R.mipmap.icon4, R.mipmap.icon5, R.mipmap.icon6, R.mipmap.icon11,
            R.mipmap.icon7, R.mipmap.icon8, R.mipmap.icon9,
            R.mipmap.icon10, R.mipmap.icon1
    };

    @Override
    protected void initView(View view) {
        TitBarUtils.FragmentTitleBarRightTv(view, "最新公开", "未登陆", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        convenientBanner = (ConvenientBanner) view.findViewById(R.id.fragment_first_convenientBanner);
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int widthPixels = dm.widthPixels;//宽度
        convenientBanner.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, widthPixels * 5/ 11));

        TextView tv = (TextView) view.findViewById(R.id.title_center);
        tv.setText("高岭村村务系统");
        reRight = (RelativeLayout) view.findViewById(R.id.title_right);


        tvRight = (TextView) view.findViewById(R.id.tv_right);

        if (PrefUtils.getString(getActivity(), "isLogin", "-1").equals("1")) {
            tvRight.setText(PrefUtils.getString(getActivity(), "userName", ""));
        } else {
            tvRight.setText("未登录");
        }
        reRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!PrefUtils.getString(getActivity(), "isLogin", "-1").equals("1")) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {
                    IndexActivity.mPager.setCurrentItem(3);
                    IndexActivity.first.setChecked(false);
                    IndexActivity.second.setChecked(false);
                    IndexActivity.third.setChecked(false);
                    IndexActivity.myaccount.setChecked(true);
                }
            }
        });


        myGridView = (MyGridView) view.findViewById(R.id.fragment_first_gv);
        scrollView = (ScrollView) view.findViewById(R.id.fragment_first_scrollview);
        localImages.add(new BannerBean(R.mipmap.banner2, ""));
        localImages.add(new BannerBean(R.mipmap.banner1, ""));
        localImages.add(new BannerBean(R.mipmap.banner3, ""));

        convenientBanner.setPages(new CBViewHolderCreator<ImageViewHolder>() {
            @Override
            public ImageViewHolder createHolder() {
                return new ImageViewHolder();
            }
        }, localImages).setPointViewVisible(true)//设置指示器是否可见
                .startTurning(2000)//设置自动切换（同时设置了切换时间间隔）
//                .setPageIndicator(new int[]  {R.drawable.ponit_normal,R.drawable.point_select}) //设置两个点作为指示器
                .setPageIndicator(new int[]{R.drawable.cycle_white, R.drawable.cycle_blue}) //设置两个点作为指示器
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL) //设置指示器的方向水平  居中
                .setOnItemClickListener(null);


        dataList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < icno.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("img", icno[i]);
            map.put("text", name[i]);
            dataList.add(map);
        }
        String[] from = {"img", "text"};

        int[] to = {R.id.img, R.id.text};
//        adapter=new SimpleAdapter(this,dataList,R.layout.main_gv_item,from,to);
//        gv.setAdapter(adapter);
        myGridView.setAdapter(new FirstFragmentGvAdapter(getActivity(), dataList));
        myGridView.setOnItemClickListener(onItemClickListener);
    }

    @Override
    protected int getViewRes() {
        return R.layout.fragment_first;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
    }


    public class ImageViewHolder implements Holder<BannerBean> {
        private View view;
        private ImageView imageView;
        private TextView tv;

        @Override
        public View createView(Context context) {
//            imageView = new ImageView(context);
//            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//            return imageView;
            view = LayoutInflater.from(context).inflate(R.layout.banner_item, null, false);
            return view;
        }

        @Override
        public void UpdateUI(Context context, int position, BannerBean data) {
            imageView = (ImageView) view.findViewById(R.id.banner_img);
            tv = (TextView) view.findViewById(R.id.banner_tv);
            imageView.setImageResource(data.getUrl());
            tv.setText(data.getDescription());
        }
    }

    GridView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position >= 0 && position <= 10) {
                if (CommonUtil.checkLogin(getActivity())) {
                    Intent intent = new Intent();
                    switch (position) {

                        case 0:
                            if (CommonUtil.checkIsThis(getActivity())) {
                                intent.setClass(getActivity(), DwgkListActivity.class);
                                intent.putExtra("type", 1);
                                intent.putExtra("title", "政务公开");
                                startActivity(intent);
                            }
                            break;
                        case 1:
                            if (CommonUtil.checkIsThis(getActivity())) {
                                intent.setClass(getActivity(), DwgkListActivity.class);
                                intent.putExtra("type", 0);
                                intent.putExtra("title", "党务公开");
                                startActivity(intent);
                            }
                            break;
                        case 2:
                            if (CommonUtil.checkIsThis(getActivity())) {
                                intent.setClass(getActivity(), CwgkActivity.class);
                                intent.putExtra("type", 3);
                                intent.putExtra("title", "财务公开");
                                startActivity(intent);
                            }
                            break;
                        case 3:
                            intent.setClass(getActivity(), DwgkListActivity.class);
                            intent.putExtra("type", 3);
                            intent.putExtra("position", 0);
                            intent.putExtra("title", "惠民政策");
                            startActivity(intent);
                            break;
                        case 4:
                            if (CommonUtil.checkIsThis(getActivity())) {
                                intent.setClass(getActivity(), DwgkListActivity.class);
                                intent.putExtra("type", 4);
                                intent.putExtra("position", 0);
                                intent.putExtra("title", "一事一议");
                                startActivity(intent);
                            }
                            break;
                        case 5:
                            if (CommonUtil.checkIsThis(getActivity())) {
                                intent.setClass(getActivity(), DwgkListActivity.class);
                                intent.putExtra("type", 7);
                                intent.putExtra("position", 0);
                                intent.putExtra("title", "文化礼堂");
                                startActivity(intent);
                            }
                            break;
                        case 6:

                            intent.setClass(getActivity(), ServiceActivity.class);
                            startActivity(intent);

                            break;
                        case 7:
                            if (CommonUtil.checkIsThis(getActivity())) {
                                intent.setClass(getActivity(), MySubsidyActivity.class);
                                intent.putExtra("type", 7);
                                intent.putExtra("title", "补贴公示");
                                startActivity(intent);
                            }
                            break;
                        case 8:
                            intent.setClass(getActivity(), DwgkListActivity.class);
                            intent.putExtra("type", 5);
                            intent.putExtra("position", 0);
                            intent.putExtra("title", "村里的事");
                            startActivity(intent);

                            break;
                        case 9:
                            if (CommonUtil.checkIsThis(getActivity())) {
                                intent.setClass(getActivity(), DwgkListActivity.class);
                                intent.putExtra("type", 6);
                                intent.putExtra("position", 0);
                                intent.putExtra("title", "干部工作");
                                startActivity(intent);
                            }
                            break;
                        case 10:
                            if (CommonUtil.checkIsThis(getActivity())) {
                                intent.setClass(getActivity(), MyCapticalActivity.class);
                                intent.putExtra("type", 0);
                                intent.putExtra("title", "我的资金");
                                startActivity(intent);
                            }
                            break;
                    }
                }
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();

        if (PrefUtils.getString(getActivity(), "isLogin", "-1").equals("1")) {
            tvRight.setText(PrefUtils.getString(getActivity(), "userName", ""));
        } else {
            tvRight.setText("未登录");
        }
    }
}
