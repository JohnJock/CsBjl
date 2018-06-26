package com.android.bjl.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.bjl.R;
import com.android.bjl.bean.CmplBean;
import com.android.bjl.http.Constant;
import com.android.bjl.http.HttpCommon;
import com.android.bjl.http.HttpEventCallBack;
import com.android.bjl.util.GsonUtils;
import com.android.bjl.util.MyToastUtil;
import com.android.bjl.util.PrefUtils;
import com.android.bjl.util.SoftKeyBoardListener;
import com.android.bjl.widget.ClearEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CLDEPLActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.cmpl_ll2)
    LinearLayout cmplLl2;
    @BindView(R.id.cmpl_et)
    ClearEditText cmplEt;
    @BindView(R.id.cmpl_btn)
    TextView cmplBtn;
    @BindView(R.id.scroll)
    ScrollView scroll;
    @BindView(R.id.cmpl_title)
    TextView cmplTitle;

    private ArrayList<CmplBean> beans;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmpl);
        ButterKnife.bind(this);
        showTitleLeftTvAndImg("村民评论", "返回", R.mipmap.back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cmplBtn.setOnClickListener(this);
        beans = new ArrayList<>();
        cmplTitle.setText(getIntent().getStringExtra("title"));

        findComment();
        SoftKeyBoardListener.setListener(CLDEPLActivity.this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                scrolltobottom();
//                Toast.makeText(CLDEPLActivity.this, "键盘显示 高度" + height, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void keyBoardHide(int height) {
//                Toast.makeText(CLDEPLActivity.this, "键盘隐藏 高度" + height, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.cmpl_btn:
                if (!TextUtils.isEmpty(cmplEt.getText().toString().trim())) {
                    comment(cmplEt.getText().toString().trim());

                } else {
                    MyToastUtil.showMEssage(CLDEPLActivity.this, "评论内容不能为空");
                }

                break;
        }

    }

    public void addView() {
        if (beans.size() > 0) {
            for (int i = 0; i < beans.size(); i++) {
                TextView textView = new TextView(this);
                textView.setText(Html.fromHtml("<font color='#EE6A50'>" + beans.get(i).getUserName() + ":" + "</font>" + beans.get(i).getContent()));
                textView.setTextSize(15);
                cmplLl2.addView(textView);
            }
        }

    }

    public void scrolltobottom() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                scroll.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    public void findComment() {
        loadDialogShow();
        HashMap map = new LinkedHashMap();
        map.put("villageId", getIntent().getStringExtra("id"));
        new HttpCommon(CLDEPLActivity.this, callBack).post(map, Constant.FINDCOMMENT, 2);


    }

    public void comment(String plString) {
        loadDialogShow();
        HashMap map = new LinkedHashMap();
        map.put("villageId", getIntent().getStringExtra("id"));
        map.put("content", plString);
        map.put("userId", PrefUtils.getString(CLDEPLActivity.this, "userId", ""));
        new HttpCommon(CLDEPLActivity.this, callBack).post(map, Constant.COMMENT, 1);


    }

    HttpEventCallBack callBack = new HttpEventCallBack() {
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
                                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(cmplEt.getWindowToken(), 0);
                                    TextView textView = new TextView(CLDEPLActivity.this);
                                    textView.setText(Html.fromHtml("<font color='#EE6A50'>" +
                                            "我" + ":" + "</font>" + cmplEt.getText().toString().trim()));
                                    textView.setTextSize(15);
                                    cmplLl2.addView(textView);
                                    scrolltobottom();
                                    cmplEt.setText("");
                                }
                            });
//                            try {
//                                ArrayList<DangyuanInfoBean>  beans = GsonUtils.jsonToArrayList(object.getJSONArray("vaList").toString(), DangyuanInfoBean.class);
//
//                                adapter=new DangyuanAdapter(DwgkListActivity.this,beans);
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        listView.setAdapter(adapter);
//                                        listView.setOnItemClickListener(DwgkListActivity.this);
//                                    }
//                                });
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
                            break;
                        case 2:
                            JSONArray array = object.optJSONObject("comment").optJSONArray("data");
                            beans = GsonUtils.jsonToArrayList(array.toString(), CmplBean.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    addView();
                                }
                            });

                            break;
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                MyToastUtil.showMEssage(CLDEPLActivity.this, object.getString("desc"));
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
                    MyToastUtil.showMEssage(CLDEPLActivity.this, "数据请求失败");
                }
            });
        }

        @Override
        public void OnHttpReceived(int requestId, long nDataLen, long totalLength) {
            loadDialogDismiss();
        }
    };


}
