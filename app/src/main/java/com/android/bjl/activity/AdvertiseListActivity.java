package com.android.bjl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.android.bjl.R;
import com.android.bjl.adapter.MyAdvertiseAdapter;
import com.android.bjl.bean.AdvertiseBean;
import com.android.bjl.http.Constant;
import com.android.bjl.http.HttpCommon;
import com.android.bjl.http.HttpEventCallBack;
import com.android.bjl.util.GsonUtils;
import com.android.bjl.util.MyToastUtil;
import com.android.bjl.util.PrefUtils;
import com.android.bjl.widget.headerlistview.XListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 招聘列表
 */
public class AdvertiseListActivity extends BaseActivity {
    String title;
    private int page = 0, pages = 0;//page 第几页，当前一共加载了几页
    int position;
    int type;
    XListView xListView;
    TextView tvNone;
    ArrayList<AdvertiseBean> beans = new ArrayList<>();
    MyAdvertiseAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initviews();
        initdata();


    }

    private void initdata() {
        title = getIntent().getStringExtra("title");
        type = getIntent().getIntExtra("type", 0);
        if (type == 1) {
            showTitleLeftTvAndImg(title, "返回", R.mipmap.back, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } else {
            showTitleLeftTvAndImgAndRightTv(title, "返回", R.mipmap.back, "发布招聘", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivityForResult(MyAdvertiseActivity.class, 100);
                }
            });
        }
        position = getIntent().getIntExtra("position", -1);


        getDataList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            page = 0;
            pages = 0;
            getDataList();
        }
    }

    private void initviews() {
        xListView = (XListView) findViewById(R.id.second_lv);
        xListView.setXListViewListener(ixListViewListener);
        xListView.setPullLoadEnable(true);

        xListView.setOnItemClickListener(listener);
        tvNone = (TextView) findViewById(R.id.tv_none);
    }

    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(AdvertiseListActivity.this, MyAdvertiseIndexActivity.class);
            intent.putExtra("type", type);
            intent.putExtra("HouseInfo", adapter.getItem((int) parent.getAdapter().getItemId(position)));
            startActivityForResult(intent, 100);
        }
    };
    XListView.IXListViewListener ixListViewListener = new XListView.IXListViewListener() {
        @Override
        public void onRefresh() {

            page = 0;
            pages = 0;
            getDataList();
            xListView.setPullLoadEnable(true);
            xListView.stopRefresh();
        }

        @Override
        public void onLoadMore() {
            if (page >= pages) {
                MyToastUtil.showMEssage(AdvertiseListActivity.this,getString(R.string.no_data_order));
                xListView.setPullLoadEnable(false);
                return;
            }
            page++;
            getDataList();
        }
    };


    public void getDataList() {

        loadDialogShow();
        HashMap map = new LinkedHashMap();
        map.put("page", page + "");
//        map.put("userId", "");
        map.put("userId", PrefUtils.getString(AdvertiseListActivity.this, "userId", ""));
        new HttpCommon(AdvertiseListActivity.this, callBack).post(map, Constant.ADVERTISEFORJOB, 1);
    }

    HttpEventCallBack callBack = new HttpEventCallBack() {
        @Override
        public void onHttpSuccess(int requestId, Object content) {
            loadDialogDismiss();

            try {
                final JSONObject jsonObject = new JSONObject(content.toString());

                if (jsonObject.getString("code").equals("00")) {
                    switch (requestId) {
                        case 1:
                            final JSONObject object = jsonObject.getJSONObject("advertiseForjson");
                            final int countPage = object.optInt("countPage");
                            final int countRows = object.optInt("countRows");
                            int currentPage = object.optInt("currentPage");
                            JSONArray data = object.optJSONArray("data");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (countRows == 0) {
                                        tvNone.setVisibility(View.GONE);

                                        return;
                                    } else {
                                        pages = countPage;
                                        tvNone.setVisibility(View.GONE);
                                    }
                                }
                            });

                            if (page == 0) {
                                /**
                                 * area : 20
                                 * description : 靠近大门的车位，好停车，好出去，现在出租，有意向的尽快联系我。
                                 * filePath : [http://192.168.0.107:8088/glc_mobile/static/convenience/file_11/20180501/066d5a86-2413-43e6-8c2b-ce017a13f5b4.png, http://192.168.0.107:8088/glc_mobile/static/convenience/file_11/20180501/7e3003dc-c06c-4b4f-b1ae-b43b50e32a5e.png, http://192.168.0.107:8088/glc_mobile/static/convenience/file_11/20180501/1efdfa52-655d-4e0b-add7-a1b9eff73091.png, http://192.168.0.107:8088/glc_mobile/static/convenience/file_11/20180501/44caab04-0eac-46d9-a5b3-47bbd4189f9b.png, http://192.168.0.107:8088/glc_mobile/static/convenience/file_11/20180501/523f4f07-6a65-4863-ad26-b5afdaa1f34b.png, http://192.168.0.107:8088/glc_mobile/static/convenience/file_11/20180501/6d9d95d0-46b5-429d-b7c6-61ba01a79a35.png]
                                 * id : 6
                                 * price : 2000
                                 * telephone : 18701557456
                                 * title : 车位出租
                                 * userName : 张恒
                                 */
                                beans.clear();
                                beans = GsonUtils.jsonToArrayList(data.toString(), AdvertiseBean.class);

                                adapter = new MyAdvertiseAdapter(AdvertiseListActivity.this, beans);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        xListView.setAdapter(adapter);
                                    }
                                });

                            } else {

                                ArrayList<AdvertiseBean> bean2 = GsonUtils.jsonToArrayList(data.toString(), AdvertiseBean.class);
                                beans.addAll(bean2);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter.notifyDataSetChanged();
                                    }
                                });

                            }
                            try {


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                MyToastUtil.showMEssage(AdvertiseListActivity.this, jsonObject.getString("desc"));
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
                    MyToastUtil.showMEssage(AdvertiseListActivity.this, "数据请求失败");

                }
            });
        }

        @Override
        public void OnHttpReceived(int requestId, long nDataLen, long totalLength) {
            loadDialogDismiss();
        }
    };

}
