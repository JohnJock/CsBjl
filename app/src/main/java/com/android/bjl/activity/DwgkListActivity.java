package com.android.bjl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.bjl.R;
import com.android.bjl.adapter.DangyuanAdapter;
import com.android.bjl.bean.DangyuanInfoBean;
import com.android.bjl.http.Constant;
import com.android.bjl.http.HttpCommon;
import com.android.bjl.http.HttpEventCallBack;
import com.android.bjl.util.GsonUtils;
import com.android.bjl.util.MyToastUtil;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class DwgkListActivity extends BaseActivity implements AdapterView.OnItemClickListener{
    private int type, position;
    private String title;
    private ListView listView;
    private TextView tv;
    private DangyuanAdapter adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dwgklist);
        initData();
        initView();
        getData();
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.dwgk_lv);
        tv = (TextView) findViewById(R.id.idwgk_v_order_noidea);

    }

    private void initData() {
        title = getIntent().getStringExtra("title");
        type = getIntent().getIntExtra("type", -1);
        position = getIntent().getIntExtra("position", -1);
        showTitleLeftTvAndImg(title, "返回", R.mipmap.back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void getData() {
        loadDialogShow();
        HashMap map = new LinkedHashMap();
        map.put("type", type + "" + 0);
        new HttpCommon(DwgkListActivity.this, callBack).post(map, Constant.GOVERNMENTAFFAIRS, Constant.RequestCode.GOVERNMENTAFFAIRS_CODE);


    }


    HttpEventCallBack callBack = new HttpEventCallBack() {
        @Override
        public void onHttpSuccess(int requestId, Object content) {
            loadDialogDismiss();
            try {
                final JSONObject object = new JSONObject(content.toString());

                if (object.getString("code").equals("00")) {
                    switch (requestId) {
                        case Constant.RequestCode.GOVERNMENTAFFAIRS_CODE:

                            try {
                              ArrayList<DangyuanInfoBean>  beans = GsonUtils.jsonToArrayList(object.getJSONArray("vaList").toString(), DangyuanInfoBean.class);

                                adapter=new DangyuanAdapter(DwgkListActivity.this,beans);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        listView.setAdapter(adapter);
                                        listView.setOnItemClickListener(DwgkListActivity.this);
                                    }
                                });

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                MyToastUtil.showMEssage(DwgkListActivity.this, object.getString("desc"));
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
                    MyToastUtil.showMEssage(DwgkListActivity.this, "数据请求失败");
                }
            });        }

        @Override
        public void OnHttpReceived(int requestId, long nDataLen, long totalLength) {
            loadDialogDismiss();
        }
    };

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Intent intent=new Intent(DwgkListActivity.this,WebViewActivity.class);
        intent.putExtra("content",adapter.getItem(i).getContent());
        intent.putExtra("type",type);
        if (type ==5){

            intent.putExtra("id",adapter.getItem(i).getId());
        }
        intent.putExtra("title",adapter.getItem(i).getTitle());
        startActivity(intent);
    }
}
