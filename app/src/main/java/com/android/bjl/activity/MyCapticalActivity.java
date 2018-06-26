package com.android.bjl.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.bjl.R;
import com.android.bjl.adapter.MyCaptialAdapter;
import com.android.bjl.adapter.MySubsidyAdapter;
import com.android.bjl.bean.MyCapticalBean;
import com.android.bjl.bean.Subsidy;
import com.android.bjl.bean.SubsidyBean;
import com.android.bjl.http.Constant;
import com.android.bjl.http.HttpCommon;
import com.android.bjl.http.HttpEventCallBack;
import com.android.bjl.util.GsonUtils;
import com.android.bjl.util.MyToastUtil;
import com.android.bjl.util.PrefUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的资金
 */
public class MyCapticalActivity extends BaseActivity {
    @BindView(R.id.title_right)
    RelativeLayout titleRight;
    @BindView(R.id.mycapticel_tv0)
    TextView mycapticelTv0;
    @BindView(R.id.radio_18)
    RadioButton radio18;
    @BindView(R.id.radio_19)
    RadioButton radio19;
    @BindView(R.id.radio_20)
    RadioButton radio20;
    @BindView(R.id.radio_group_receivemonery)
    RadioGroup radioGroupReceivemonery;
    @BindView(R.id.mycapticel_tv1)
    TextView mycapticelTv1;
    @BindView(R.id.mycapticel_tv2)
    TextView mycapticelTv2;
    @BindView(R.id.mycapticel_lv)
    ListView mycapticelLv;
    MyCaptialAdapter adapter;
    ArrayList<Subsidy> beans;
    public Double yearTotal = 0.00;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycaptical);
        ButterKnife.bind(this);
        showTitleLeftTvAndImg("我的资金", "返回", R.mipmap.back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        radio18.setOnClickListener(listener);
        radio19.setOnClickListener(listener);
        radio20.setOnClickListener(listener);
        mycapticelLv.setFooterDividersEnabled(true);
        setData("2018");
    }

//    public void initData() {
//        beans = new ArrayList<MyCapticalBean>();
//        beans.add(new MyCapticalBean("耕地补助", "20元"));
//        beans.add(new MyCapticalBean("医疗补助", "30元"));
//        beans.add(new MyCapticalBean("住房补助", "50元"));
//        beans.add(new MyCapticalBean("失业补助", "100元"));
//        adapter = new MyCaptialAdapter(MyCapticalActivity.this, beans);
//        mycapticelLv.setAdapter(adapter);
//        mycapticelTv0.setText("资金总金额：100元");
//        mycapticelTv1.setText("2018年年度总收入：");
//        mycapticelTv2.setText("100元");
//    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.radio_18:
                    setData("2018");
                    break;
                case R.id.radio_19:
                    setData("2019");

                    break;
                case R.id.radio_20:
                    setData("2020");
                    break;
            }
        }
    };

    public void setData(final String year) {
        loadDialogShow();
        HashMap map = new LinkedHashMap();

        map.put("year", year);

        map.put("userId", PrefUtils.getString(MyCapticalActivity.this, "userId", ""));

        new HttpCommon(MyCapticalActivity.this, new HttpEventCallBack() {
            @Override
            public void onHttpSuccess(int requestId, Object content) {
                loadDialogDismiss();
                yearTotal = 0.00;

                try {

                    final JSONObject object = new JSONObject(content.toString());
                    if (object.getString("code").equals("00")) {

//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//
//                                MyToastUtil.showMEssage(MyCapticalActivity.this, "发布招聘信息成功!");
//
//                                Intent intent = new Intent();
//                                setResult(100, intent);
//                                finish();
//
//
//                            }
//                        });
                        final String total = object.optString("total");//资金总收入
                        JSONObject object1 = object.getJSONObject("subsidyJson");
                        JSONArray array = object1.getJSONArray("data");
                        ArrayList<Subsidy> beans = new ArrayList<Subsidy>();
                        if (array.length() > 0) {
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object2 = array.getJSONObject(i);
                                SubsidyBean subsidyBean = new SubsidyBean();
                                subsidyBean.setSubsidyTypeName(object2.optString("subsidyTypeName"));
                                subsidyBean.setTotal(object2.optString("total"));
                                subsidyBean.setSubsidyTotal(object2.optString("subsidyTotal"));
                                subsidyBean.setUserTotal(object2.optString("userTotal"));
                                JSONArray array1 = object2.getJSONArray("subsidyData");
                                ArrayList<Subsidy> list = GsonUtils.jsonToArrayList(array1.toString(), Subsidy.class);
                                subsidyBean.setSubsidyData(list);
                                beans.addAll(list);
                                yearTotal += Double.valueOf(object2.optString("total"));

                            }
                        }


                        adapter = new MyCaptialAdapter(MyCapticalActivity.this, beans);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mycapticelLv.setAdapter(adapter);
                                if (total.equals("") || total.equals("0.0") || total.equals("0.00") || total.equals("0")) {
                                    mycapticelTv0.setText("资金总收入：0.00元");
                                } else {
                                    mycapticelTv0.setText("资金总收入：" + total + "元");
                                }
                                mycapticelTv1.setText(year + "年资金总收入：");
                                java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
                                String string = df.format(yearTotal);
                                if (string.equals(".00")) {
                                    string = "0.00";
                                }
                                mycapticelTv2.setText(string + "元");
                            }
                        });


                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    MyToastUtil.showMEssage(MyCapticalActivity.this, object.getString("desc"));
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
                        MyToastUtil.showMEssage(MyCapticalActivity.this, "数据请求失败");

                    }
                });
            }

            @Override
            public void OnHttpReceived(int requestId, long nDataLen, long totalLength) {

            }
        }).post(map, Constant.SUBSIDYINFO, 0);
        loadDialogShow();
    }
}
