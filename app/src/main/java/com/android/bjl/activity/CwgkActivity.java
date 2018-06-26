package com.android.bjl.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.bjl.R;
import com.android.bjl.adapter.MyCaptialAdapter;
import com.android.bjl.adapter.MyIncomeAdapter;
import com.android.bjl.bean.IncommeAndExpenditureBean;
import com.android.bjl.bean.MyCapticalBean;
import com.android.bjl.http.Constant;
import com.android.bjl.http.HttpCommon;
import com.android.bjl.http.HttpEventCallBack;
import com.android.bjl.util.GsonUtils;
import com.android.bjl.util.MyToastUtil;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 财务公开
 */
public class CwgkActivity extends BaseActivity {

    MyIncomeAdapter adapter;
    ArrayList<MyCapticalBean> beans;
    @BindView(R.id.radio_18)
    RadioButton radio18;
    @BindView(R.id.radio_19)
    RadioButton radio19;
    @BindView(R.id.radio_group_receivemonery)
    RadioGroup radioGroupReceivemonery;
    @BindView(R.id.cwgk_tv2)
    TextView cwgkTv;
    @BindView(R.id.cwgk_lv)
    ListView cwgkLv;
    ArrayList<IncommeAndExpenditureBean> list1 = new ArrayList<>();

    ArrayList<IncommeAndExpenditureBean> list2 = new ArrayList<>();
    String total1, total2, type1, type2;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cwgk);
        ButterKnife.bind(this);
        showTitleLeftTvAndImg("财务公开", "返回", R.mipmap.back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        radio18.setOnClickListener(listener);
        radio19.setOnClickListener(listener);

        setData();
    }

    public void initData() {
//        beans = new ArrayList<MyCapticalBean>();
//        beans.add(new MyCapticalBean("耕地补助", "20元"));
//        beans.add(new MyCapticalBean("医疗补助", "30元"));
//        beans.add(new MyCapticalBean("住房补助", "50元"));
//        beans.add(new MyCapticalBean("失业补助", "100元"));
//        adapter = new MyCaptialAdapter(CwgkActivity.this, beans);
//        cwgkLv.setAdapter(adapter);
//        cwgkTv.setText("收入：13200元");
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.radio_18:
                    adapter.setData(list1);
                    cwgkTv.setText("总收入："+total1);
                    break;
                case R.id.radio_19:
                    adapter.setData(list2);
                    cwgkTv.setText("总支出："+total2);
                    break;

            }
        }
    };

    public void setData() {

        loadDialogShow();
        HashMap map = new LinkedHashMap();
        new HttpCommon(CwgkActivity.this, new HttpEventCallBack() {
            @Override
            public void onHttpSuccess(int requestId, Object content) {
                loadDialogDismiss();
                final JSONObject object;
                try {
                    object = new JSONObject(content.toString());
                    if (object.getString("code").equals("00")) {
                        JSONArray array = object.getJSONArray("iaeJson");
                        JSONObject object1 = array.getJSONObject(0);
                        total1 = object1.optString("total");
                        type1 = object1.optString("type");
                        if (type1.equals("收入")) {
                            list1 = GsonUtils.jsonToArrayList(object1.getJSONArray("data").toString(), IncommeAndExpenditureBean.class);

                        }
                        JSONObject object2 = array.getJSONObject(1);

                        total2 = object2.optString("total");
                        type2 = object2.optString("type");
                        if (type2.equals("支出")) {
                            list2 = GsonUtils.jsonToArrayList(object2.getJSONArray("data").toString(), IncommeAndExpenditureBean.class);
                        }

                        adapter = new MyIncomeAdapter(CwgkActivity.this, list1);
                        CwgkActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                cwgkLv.setAdapter(adapter);
                                cwgkTv.setText("总收入："+total1);
                            }
                        });


                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    MyToastUtil.showMEssage(CwgkActivity.this, object.getString("desc"));
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
                        MyToastUtil.showMEssage(CwgkActivity.this, "数据请求失败");

                    }
                });
            }

            @Override
            public void OnHttpReceived(int requestId, long nDataLen, long totalLength) {

            }
        }).post(map, Constant.INCOMEANDEXPENDITURE, 0);
    }
}
