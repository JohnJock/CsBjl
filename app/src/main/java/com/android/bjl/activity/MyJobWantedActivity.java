package com.android.bjl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.bjl.R;
import com.android.bjl.http.Constant;
import com.android.bjl.http.HttpCommon;
import com.android.bjl.http.HttpEventCallBack;
import com.android.bjl.util.MyToastUtil;
import com.android.bjl.util.PrefUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我要招聘
 * Created by john on 2018/4/18.
 */

public class MyJobWantedActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.et_adversise_title)
    EditText title;
    @BindView(R.id.et_jobwanted_name)
    EditText name;
    @BindView(R.id.et_jobwanted_sex)
    EditText sex;
    @BindView(R.id.et_jobwanted_age)
    EditText age;
    @BindView(R.id.et_jobwanted_price)
    EditText price;
    @BindView(R.id.et_jobwanted_telephone)
    EditText telephone;
    @BindView(R.id.et_jobwanted_des)
    EditText description;
    @BindView(R.id.btn_jobwanted_done)
    Button done;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobwanted);
        ButterKnife.bind(this);
        showTitleLeftTvAndImg("我要求职", "返回", R.mipmap.back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        done.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        String titleString = title.getText().toString().trim();
        String nameString = name.getText().toString().trim();
        String priceString = price.getText().toString().trim();
        String phoneString = telephone.getText().toString().trim();
        String sexString = sex.getText().toString().trim();
        String ageString = age.getText().toString().trim();
        String decriptionString = description.getText().toString().trim();
        if (TextUtils.isEmpty(titleString)) {
            MyToastUtil.showMEssage(MyJobWantedActivity.this, "请输入正确的标题");
            return;
        }
        if (TextUtils.isEmpty(nameString)) {
            MyToastUtil.showMEssage(MyJobWantedActivity.this, "请输入正确的姓名");
            return;
        }
        if (TextUtils.isEmpty(sexString)) {
            MyToastUtil.showMEssage(MyJobWantedActivity.this, "请输入正确的性别");
            return;
        }
        if (TextUtils.isEmpty(ageString)) {
            MyToastUtil.showMEssage(MyJobWantedActivity.this, "请输入正确的年龄");
            return;
        }
        if (TextUtils.isEmpty(priceString)) {
            MyToastUtil.showMEssage(MyJobWantedActivity.this, "请输入正确的月薪");
            return;
        }
        if (TextUtils.isEmpty(phoneString)) {
            MyToastUtil.showMEssage(MyJobWantedActivity.this, "请输入正确的电话");
            return;
        }
        if (TextUtils.isEmpty(decriptionString)) {
            MyToastUtil.showMEssage(MyJobWantedActivity.this, "请输入自我介绍");
            return;
        }

//        NSMutableDictionary * dic = [NSMutableDictionary dictionary];
//    [dic setValue:_titleTF.value forKey:@"title"];
//    [dic setValue:_priceTF.value forKey:@"price"];
//    [dic setValue:_nameTF.value forKey:@"name"];
//    [dic setValue:_ageTF.value forKey:@"age"];
//    [dic setValue:_sexTF.value forKey:@"sex"];
//    [dic setValue:_telTF.value forKey:@"telephone"];
//    [dic setValue:_markTV.text forKey:@"description"];
//    [dic setValue:theInfo.userId forKey:@"userId"];
        HashMap map = new LinkedHashMap();
        map.put("title", titleString);
        map.put("name", nameString);
        map.put("age", ageString);
        map.put("sex", sexString);
        map.put("price", priceString);
        map.put("telephone", phoneString);
        map.put("description", decriptionString);
        map.put("userId", PrefUtils.getString(MyJobWantedActivity.this, "userId", ""));


        new HttpCommon(MyJobWantedActivity.this, new HttpEventCallBack() {
            @Override
            public void onHttpSuccess(int requestId, Object content) {
                loadDialogDismiss();
                try {

                    final JSONObject object = new JSONObject(content.toString());
                    if (object.getString("code").equals("00")) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                MyToastUtil.showMEssage(MyJobWantedActivity.this, "发布信息成功!");

                                Intent intent = new Intent();
                                setResult(100, intent);
                                finish();


                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    MyToastUtil.showMEssage(MyJobWantedActivity.this, object.getString("desc"));
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
                        MyToastUtil.showMEssage(MyJobWantedActivity.this, "数据请求失败");

                    }
                });
            }

            @Override
            public void OnHttpReceived(int requestId, long nDataLen, long totalLength) {

            }
        }).post(map, Constant.PUSHJOBWANTEDED, 0);
        loadDialogShow();
    }


}
