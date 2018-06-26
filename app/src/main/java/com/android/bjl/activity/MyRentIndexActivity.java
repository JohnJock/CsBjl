package com.android.bjl.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.bjl.R;
import com.android.bjl.bean.HouseInfoBean;
import com.android.bjl.http.Constant;
import com.android.bjl.http.HttpCommon;
import com.android.bjl.http.HttpEventCallBack;
import com.android.bjl.util.DialogUtils;
import com.android.bjl.util.MyToastUtil;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by john on 2018/4/20.
 */

public class MyRentIndexActivity extends BaseActivity implements OnItemClickListener {

    HouseInfoBean houseInfoBean;
    @BindView(R.id.myrentindex_call)
    Button myrentindexCall;
    @BindView(R.id.convenientBanner)
    ConvenientBanner convenientBanner;
    @BindView(R.id.tv_myrentindex_title)
    TextView tvMyrentindexTitle;
    @BindView(R.id.tv_myrentindex_price)
    TextView tvMyrentindexPrice;
    @BindView(R.id.tv_myrentindex_phone)
    TextView tvMyrentindexPhone;
    @BindView(R.id.tv_myrentindex_updatetime)
    TextView tvMyrentindexUpdatetime;
    @BindView(R.id.tv_myrentindex_desc)
    TextView tvMyrentindexDesc;
    String message;
    @BindView(R.id.tv_myrentindex_area)
    TextView tvMyrentindexArea;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myrentindex);
        ButterKnife.bind(this);
        initView();
        initdata();
    }

    private void initView() {
        showTitleLeftTvAndImg("租房信息", "返回", R.mipmap.back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (getIntent().getIntExtra("type", 0) == 1) {
            myrentindexCall.setText("解除发布");
            message = "你确定解除发布信息吗?";
        } else {
            myrentindexCall.setText("电话联系");
            message = "拨打电话?";
        }
        houseInfoBean = (HouseInfoBean) getIntent().getSerializableExtra("HouseInfo");
        tvMyrentindexTitle.setText(houseInfoBean.getTitle());
        tvMyrentindexPrice.setText(houseInfoBean.getPrice());
        tvMyrentindexArea.setText(houseInfoBean.getArea());
        tvMyrentindexPhone.setText(houseInfoBean.getTelephone());
        tvMyrentindexUpdatetime.setText(houseInfoBean.getUpdateTime());
        tvMyrentindexDesc.setText(houseInfoBean.getDescription());
        myrentindexCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getIntent().getIntExtra("type", 0) == 1) {
                    DialogUtils.showDialog(MyRentIndexActivity.this, false, message, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Map<String, String> map = new LinkedHashMap<>();
                            map.put("id", houseInfoBean.getId());
                            new HttpCommon(MyRentIndexActivity.this, new HttpEventCallBack() {
                                @Override
                                public void onHttpSuccess(int requestId, Object content) {
                                    loadDialogDismiss();
                                    try {
                                        final JSONObject object = new JSONObject(content.toString());
                                        if (object.getString("code").equals("00")) {
                                            switch (requestId) {
                                                case 1:
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            MyToastUtil.showMEssage(MyRentIndexActivity.this, "删除租房信息成功");

                                                        }
                                                    });
                                                    Intent intent = new Intent();
                                                    setResult(100, intent);
                                                    finish();

                                                    break;

                                            }
                                        } else {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        MyToastUtil.showMEssage(MyRentIndexActivity.this, object.getString("desc"));
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            });
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onHttpFail(int requestId, HttpRetId httpStatus, String msg) {
                                    loadDialogDismiss();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            MyToastUtil.showMEssage(MyRentIndexActivity.this, "数据请求失败");

                                        }
                                    });
                                }

                                @Override
                                public void OnHttpReceived(int requestId, long nDataLen, long totalLength) {

                                }
                            }).post(map, Constant.DELETEHOUSEINFO, 1);
                            loadDialogShow();
                        }
                    }, null);
                } else {
                    message = "是否拨打电话";
                    DialogUtils.showDialog(MyRentIndexActivity.this, false, message, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            Uri data = Uri.parse("tel:" + houseInfoBean.getTelephone());
                            intent.setData(data);
                            startActivity(intent);
                        }
                    }, null);
                }
            }
        });
    }

    public void initdata() {


        convenientBanner.setPages(new CBViewHolderCreator<ImageViewHolder>() {
            @Override
            public ImageViewHolder createHolder() {
                return new ImageViewHolder();
            }
        }, houseInfoBean.getFilePath()).setPointViewVisible(true)//设置指示器是否可见
                .startTurning(2000)//设置自动切换（同时设置了切换时间间隔）
//                .setPageIndicator(new int[]  {R.drawable.ponit_normal,R.drawable.point_select}) //设置两个点作为指示器
                .setPageIndicator(new int[]{R.drawable.cycle_white, R.drawable.cycle_blue}) //设置两个点作为指示器
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL) //设置指示器的方向水平  居中
                .setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(int position) {

    }


    public class ImageViewHolder implements Holder<String> {
        private View view;
        private ImageView imageView;
        private TextView tv;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
//            view = LayoutInflater.from(context).inflate(R.layout.banner_item, null, false);
//            return view;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
//            imageView = (ImageView) view.findViewById(R.id.banner_img);
//            tv = (TextView) view.findViewById(R.id.banner_tv);
//            imageView.setImageResource(data.getUrl());
//            tv.setText(data.getDescription());
            Glide.with(context)
                    .load(data)
                    .placeholder(R.mipmap.banner1)//图片加载出来前，显示的图片
                    .error(R.mipmap.banner1)//图片加载失败后，显示的图片
                    .into(imageView);
        }
    }
}
