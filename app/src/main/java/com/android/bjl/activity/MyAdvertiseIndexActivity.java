package com.android.bjl.activity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.bjl.R;
import com.android.bjl.bean.AdvertiseBean;

import com.android.bjl.http.Constant;
import com.android.bjl.http.HttpCommon;
import com.android.bjl.http.HttpEventCallBack;
import com.android.bjl.util.DialogUtils;
import com.android.bjl.util.MyToastUtil;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by john on 2018/4/20.
 */

public class MyAdvertiseIndexActivity extends BaseActivity  {

    AdvertiseBean advertiseBean;
    @BindView(R.id.myadvertiseindex_call)
    Button myadvertiseindexCall;
    @BindView(R.id.tv_myadvertiseindex_title)
    TextView tvMyadvertiseindexTitle;
    @BindView(R.id.tv_myadvertiseindex_price)
    TextView tvMyadvertiseindexPrice;
    @BindView(R.id.tv_myadvertiseindex_jobaddress)
    TextView tvMyadvertiseindexJobaddress;
    @BindView(R.id.tv_myadvertiseindex_phone)
    TextView tvMyadvertiseindexPhone;
    @BindView(R.id.tv_myadvertiseindex_updatetime)
    TextView tvMyadvertiseindexUpdatetime;
    @BindView(R.id.tv_myadvertiseindex_demand)
    TextView tvMyadvertiseindexDemand;
    @BindView(R.id.tv_myadvertiseindex_desc)
    TextView tvMyadvertiseindexDesc;

    String message;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myadvertiseindex);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        showTitleLeftTvAndImg("招聘信息", "返回", R.mipmap.back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (getIntent().getIntExtra("type", 0) == 1) {
            myadvertiseindexCall.setText("解除发布");
            message = "你确定解除发布信息吗?";
        } else {
            myadvertiseindexCall.setText("电话联系");
            message = "拨打电话?";
        }
        advertiseBean = (AdvertiseBean) getIntent().getSerializableExtra("HouseInfo");
        tvMyadvertiseindexTitle.setText(advertiseBean.getTitle());
        tvMyadvertiseindexPrice.setText(advertiseBean.getPrice()+"/月");
        tvMyadvertiseindexJobaddress.setText(advertiseBean.getJobAddress());
        tvMyadvertiseindexPhone.setText(advertiseBean.getTelephone());
        tvMyadvertiseindexUpdatetime.setText(advertiseBean.getUpdateTime());
        tvMyadvertiseindexDemand.setText(advertiseBean.getDemand());
        tvMyadvertiseindexDesc.setText(advertiseBean.getDescription());
        myadvertiseindexCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getIntent().getIntExtra("type", 0) == 1) {
                    DialogUtils.showDialog(MyAdvertiseIndexActivity.this, false, message, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Map<String, String> map = new LinkedHashMap<>();
                            map.put("id", advertiseBean.getId());
                            new HttpCommon(MyAdvertiseIndexActivity.this, new HttpEventCallBack() {
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
                                                            MyToastUtil.showMEssage(MyAdvertiseIndexActivity.this, "删除租房信息成功");

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
                                                        MyToastUtil.showMEssage(MyAdvertiseIndexActivity.this, object.getString("desc"));
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
                                            MyToastUtil.showMEssage(MyAdvertiseIndexActivity.this, "数据请求失败");

                                        }
                                    });
                                }

                                @Override
                                public void OnHttpReceived(int requestId, long nDataLen, long totalLength) {

                                }
                            }).post(map, Constant.DELETEJADVERTISEFORJOB, 1);
                            loadDialogShow();
                        }
                    }, null);
                } else {
                    message = "是否拨打电话";
                    DialogUtils.showDialog(MyAdvertiseIndexActivity.this, false, message, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            Uri data = Uri.parse("tel:" + advertiseBean.getTelephone());
                            intent.setData(data);
                            startActivity(intent);
                        }
                    }, null);
                }
            }
        });
    }





}
