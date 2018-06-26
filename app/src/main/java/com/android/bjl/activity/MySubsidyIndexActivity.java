package com.android.bjl.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.TextView;

import com.android.bjl.R;
import com.android.bjl.adapter.MySubsidyIndexAdapter;
import com.android.bjl.bean.Subsidy;
import com.android.bjl.bean.SubsidyBean;
import com.android.bjl.http.Constant;
import com.android.bjl.http.HttpCommon;
import com.android.bjl.http.HttpEventCallBack;
import com.android.bjl.util.GsonUtils;
import com.android.bjl.util.MyToastUtil;
import com.android.bjl.widget.ClearEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MySubsidyIndexActivity extends BaseActivity {

    @BindView(R.id.tv_mysubsidyindex_cm)
    TextView tvMysubsidyindexCm;
    @BindView(R.id.tv_mysubsidyindex_cmzs)
    TextView tvMysubsidyindexCmzs;
    @BindView(R.id.tv_mysubsidyindex_sbtrs)
    TextView tvMysubsidyindexSbtrs;
    @BindView(R.id.tv_mysubsidyindex_money)
    TextView tvMysubsidyindexMoney;
    @BindView(R.id.et)
    ClearEditText et;
    @BindView(R.id.search)
    TextView search;
    @BindView(R.id.lv)
    ListView lv;

    SubsidyBean bean;
    MySubsidyIndexAdapter adapter;
    ArrayList<Subsidy> list = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subsidyindex);
        ButterKnife.bind(this);
        showTitleLeftTvAndImg("补贴详情", "返回", R.mipmap.back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                setData();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
                query();
            }
        });
        query();

    }

//    public void setData() {
//        list.clear();
//        String name = et.getText().toString().trim();
//        if (TextUtils.isEmpty(name)) {
//            initData();
//        } else {
//            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(et.getWindowToken(), 0); //强制隐藏键盘
//            if (bean.getSubsidyData().size() > 0) {
//                et.setText("");
//                for (int i = 0; i < bean.getSubsidyData().size(); i++) {
//                    Subsidy subsidy = bean.getSubsidyData().get(i);
//                    if (subsidy.getUserName().contains(name)) {
//                        list.add(subsidy);
//                    }
//                }
//                adapter = new MySubsidyIndexAdapter(MySubsidyIndexActivity.this, list);
//                lv.setAdapter(adapter);
//            }
//        }
//    }
//
//    public void initData() {
//        if (bean.getSubsidyData().size() > 0) {
//
//            for (int i = 0; i < bean.getSubsidyData().size(); i++) {
//                Subsidy subsidy = bean.getSubsidyData().get(i);
//
//                list.add(subsidy);
//
//            }
//            adapter = new MySubsidyIndexAdapter(MySubsidyIndexActivity.this, list);
//            lv.setAdapter(adapter);
//        }
//    }

    public void query() {
        loadDialogShow();
        HashMap map = new LinkedHashMap();
        int year=getIntent().getIntExtra("year",2018);
        String name = et.getText().toString().trim();
        map.put("beginTime", year+"");
        map.put("endTime", (year+1)+"");
        map.put("userName", name);
        map.put("type", getIntent().getStringExtra("type"));
        new HttpCommon(MySubsidyIndexActivity.this, new HttpEventCallBack() {
            @Override
            public void onHttpSuccess(int requestId, Object content) {
                loadDialogDismiss();
                final JSONObject object;
                try {
                    object = new JSONObject(content.toString());
                    if (object.getString("code").equals("00")) {
                         bean =new SubsidyBean();
                        bean.setSubsidyTypeName(object.optString("subsidyTypeName"));
                        bean.setSubsidyType(object.optString("subsidyType"));
                        bean.setTotal(object.optString("total"));
                        bean.setSubsidyTotal(object.optString("subsidyTotal"));
                        bean.setUserTotal(object.optString("userTotal"));
                        JSONArray array1 =object.getJSONObject("subsidyJson").getJSONArray("data");
                        list = GsonUtils.jsonToArrayList(array1.toString(),Subsidy.class);
                        bean.setSubsidyData(list);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvMysubsidyindexCm.setText("高岭村");
                                tvMysubsidyindexCmzs.setText(bean.getUserTotal() + "人");
                                tvMysubsidyindexMoney.setText(bean.getTotal() + "元");
                                tvMysubsidyindexSbtrs.setText(bean.getSubsidyTotal() + "人");
                                adapter = new MySubsidyIndexAdapter(MySubsidyIndexActivity.this,list);
                                lv.setAdapter(adapter);
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    MyToastUtil.showMEssage(MySubsidyIndexActivity.this, object.getString("desc"));
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
                        MyToastUtil.showMEssage(MySubsidyIndexActivity.this, "数据请求失败");

                    }
                });
            }

            @Override
            public void OnHttpReceived(int requestId, long nDataLen, long totalLength) {

            }
        }).post(map, Constant.SUBSIDYINFOBYUSERNAME, 1);
    }
}
