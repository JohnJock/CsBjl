package com.android.bjl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.bjl.R;
import com.android.bjl.adapter.MyCaptialAdapter;
import com.android.bjl.adapter.MySubsidyAdapter;
import com.android.bjl.bean.JobWantedBean;
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
 * 补贴公示
 */
public class MySubsidyActivity extends BaseActivity {
    @BindView(R.id.mysubsidy_tv0)
    TextView mysubsidyTv0;
    @BindView(R.id.radio_18)
    RadioButton radio18;
    @BindView(R.id.radio_19)
    RadioButton radio19;
    @BindView(R.id.radio_20)
    RadioButton radio20;
    @BindView(R.id.radio_group_receivemonery)
    RadioGroup radioGroupReceivemonery;
    @BindView(R.id.mysubsidy_tv1)
    TextView mysubsidyTv1;
    @BindView(R.id.mysubsidy_tv2)
    TextView mysubsidyTv2;
    @BindView(R.id.mysubsidy_lv)
    ListView mysubsidyLv;
    MySubsidyAdapter adapter;
    ArrayList<Subsidy> beans;
   public  Double yearTotal=0.00;
    int year=2018;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysubsidy);
        ButterKnife.bind(this);
        showTitleLeftTvAndImg("补贴公示", "返回", R.mipmap.back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        radio18.setOnClickListener(listener);
        radio19.setOnClickListener(listener);
        radio20.setOnClickListener(listener);
        setData(year);
        mysubsidyLv.setFooterDividersEnabled(true);

        mysubsidyLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent =new Intent(MySubsidyActivity.this,MySubsidyIndexActivity.class);
                intent.putExtra("type",adapter.getItem(i).getSubsidyType());
                intent.putExtra("year",year);
                startActivity(intent);
            }
        });
    }



    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.radio_18:
                    year=2018;
                    setData(year);
                    break;
                case R.id.radio_19:
                    year=2019;
                    setData(year);

                    break;
                case R.id.radio_20:
                    year=2020;
                    setData(year);
                    break;
            }
        }
    };

    public void setData( final int year) {

//        /AppSubsidy/subsidyInfo
//        这个是补贴公示
//        @RequestParam(value="userId", required=false) Long userId,
//        @RequestParam(value="currentPage", required=false) Integer currentPage,
//        @RequestParam(value="pageNum", required=false) Integer pageNum,
//        @RequestParam(value="title", required=false) String title,
//        @RequestParam(value="userName", required=false) String userName,
//        @RequestParam(value="beginTime", required=false) String beginTime,
//        @RequestParam(value="endTime", required=false) String endTime





//        @RequestParam(value="userId", required=false) Long userId,
//        @RequestParam(value="currentPage", required=false) Integer currentPage,
//        @RequestParam(value="currentPage", required=false) Integer pageNum,
//        @RequestParam(value="title", required=false) String title,
//        @RequestParam(value="criterion", required=false) String criterion,
//        @RequestParam(value="detail", required=false) String detail,
//        @RequestParam(value="year", required=false) String year
        HashMap map = new LinkedHashMap();

        map.put("beginTime", year+"");
        map.put("endTime", (year+1)+"");

//        map.put("userId", PrefUtils.getString(MySubsidyActivity.this, "userId", ""));
        map.put("userId", "");
        new HttpCommon(MySubsidyActivity.this, new HttpEventCallBack() {
            @Override
            public void onHttpSuccess(int requestId, Object content) {
                loadDialogDismiss();
                yearTotal=0.00;

                try {

                    final JSONObject object = new JSONObject(content.toString());
                    if (object.getString("code").equals("00")) {

//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//
//                                MyToastUtil.showMEssage(MySubsidyActivity.this, "发布招聘信息成功!");
//
//                                Intent intent = new Intent();
//                                setResult(100, intent);
//                                finish();
//
//
//                            }
//                        });
                        final String total =object.optString("total");//资金总收入
                        JSONObject object1 =object.getJSONObject("subsidyJson");
                        JSONArray array=object1.getJSONArray("data");
                        ArrayList<SubsidyBean> beans=new ArrayList<SubsidyBean>();
                        if (array.length()>0){
                            for (int i=0;i<array.length();i++){
                                JSONObject object2=array.getJSONObject(i);
                                SubsidyBean subsidyBean =new SubsidyBean();
                                subsidyBean.setSubsidyTypeName(object2.optString("subsidyTypeName"));
                                subsidyBean.setSubsidyType(object2.optString("subsidyType"));
                                subsidyBean.setTotal(object2.optString("total"));
                                subsidyBean.setSubsidyTotal(object2.optString("subsidyTotal"));
                                subsidyBean.setUserTotal(object2.optString("userTotal"));
                                JSONArray array1 =object2.getJSONArray("subsidyData");
                                ArrayList<Subsidy> list =GsonUtils.jsonToArrayList(array1.toString(),Subsidy.class);
                                subsidyBean.setSubsidyData(list);
                                beans.add(subsidyBean);
                                yearTotal+=Double.valueOf(object2.optString("total"));

                            }
                        }


                        adapter = new MySubsidyAdapter(MySubsidyActivity.this, beans);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mysubsidyLv.setAdapter(adapter);
                                if (total.equals("") || total.equals("0.0") || total.equals("0.00") || total.equals("0")) {
                                    mysubsidyTv0.setText("补贴总金额：" + 0.00 + "元");
                                } else {
                                    mysubsidyTv0.setText("补贴总金额：" + total + "元");
                                }

                                mysubsidyTv1.setText(year+"年补贴总收入：");
                                java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");
                                String string =df.format(yearTotal);
                                if (string.equals(".00")){
                                    string="0.00";
                                }
                                mysubsidyTv2.setText(string+"元");
                            }
                        });


                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    MyToastUtil.showMEssage(MySubsidyActivity.this, object.getString("desc"));
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
                        MyToastUtil.showMEssage(MySubsidyActivity.this, "数据请求失败");

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


