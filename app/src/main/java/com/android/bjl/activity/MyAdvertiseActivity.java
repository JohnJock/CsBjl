package com.android.bjl.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.bjl.R;
import com.android.bjl.http.Constant;
import com.android.bjl.http.HttpCommon;
import com.android.bjl.http.HttpEventCallBack;
import com.android.bjl.util.FilePathManager;
import com.android.bjl.util.FileUtils;
import com.android.bjl.util.MyToastUtil;
import com.android.bjl.util.PrefUtils;
import com.android.bjl.widget.headerlistview.MyGridView;
import com.example.mjj.selectphotodemo.PhotoPickerActivity;
import com.example.mjj.selectphotodemo.PreviewPhotoActivity;
import com.example.mjj.selectphotodemo.beans.ImageItem;
import com.example.mjj.selectphotodemo.utils.Bimp;
import com.example.mjj.selectphotodemo.utils.BitmapUtils;
import com.example.mjj.selectphotodemo.utils.MPermissionUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我要招聘
 * Created by john on 2018/4/18.
 */

public class MyAdvertiseActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.et_adversise_title)
    EditText title;
    @BindView(R.id.et_advertise_jobAddress)
    EditText address;
    @BindView(R.id.et_advertise_price)
    EditText price;
    @BindView(R.id.et_advertise_telephone)
    EditText telephone;
    @BindView(R.id.et_advertise_demand)
    EditText demand;
    @BindView(R.id.et_advertise_description)
    EditText description;
    @BindView(R.id.btn_advertise_done)
    Button done;
    private List<Bitmap> mResults = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertise);
        ButterKnife.bind(this);
        showTitleLeftTvAndImg("发布招聘", "返回", R.mipmap.back, new View.OnClickListener() {
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
        String addressString = address.getText().toString().trim();
        String priceString = price.getText().toString().trim();
        String phoneString = telephone.getText().toString().trim();
        String demandString = demand.getText().toString().trim();
        String decriptionString = description.getText().toString().trim();
        if (TextUtils.isEmpty(titleString)) {
            MyToastUtil.showMEssage(MyAdvertiseActivity.this, "请输入正确的标题");
            return;
        }
        if (TextUtils.isEmpty(addressString)) {
            MyToastUtil.showMEssage(MyAdvertiseActivity.this, "请输入正确的工作地点");
            return;
        }
        if (TextUtils.isEmpty(priceString)) {
            MyToastUtil.showMEssage(MyAdvertiseActivity.this, "请输入正确的月薪");
            return;
        }
        if (TextUtils.isEmpty(phoneString)) {
            MyToastUtil.showMEssage(MyAdvertiseActivity.this, "请输入正确的电话");
            return;
        }
        if (TextUtils.isEmpty(decriptionString)) {
            MyToastUtil.showMEssage(MyAdvertiseActivity.this, "请输入正确的职位要求");
            return;
        }
        if (TextUtils.isEmpty(decriptionString)) {
            MyToastUtil.showMEssage(MyAdvertiseActivity.this, "请输入正确的职位描述");
            return;
        }
//
///NSMutableDictionary * dic = [NSMutableDictionary dictionary];
//    [dic setValue:_titleTF.value forKey:@"title"];
//    [dic setValue:_addressTF.value forKey:@"jobAddress"];
//    [dic setValue:_moneyTF.value forKey:@"price"];
//    [dic setValue:_phoneTF.value forKey:@"telephone"];
//    [dic setValue:_requestview.text forKey:@"demand"];
//    [dic setValue:_markview.text forKey:@"description"];
//    [dic setValue:theInfo.userId forKey:@"userId"];
        HashMap map = new LinkedHashMap();
        map.put("title", titleString);
        map.put("jobAddress", addressString);
        map.put("price", priceString);
        map.put("telephone", phoneString);
        map.put("demand", demandString);
        map.put("description", decriptionString);
        map.put("userId", PrefUtils.getString(MyAdvertiseActivity.this, "userId", ""));


        new HttpCommon(MyAdvertiseActivity.this, new HttpEventCallBack() {
            @Override
            public void onHttpSuccess(int requestId, Object content) {
                loadDialogDismiss();
                try {

                    final JSONObject object = new JSONObject(content.toString());
                    if (object.getString("code").equals("00")) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                MyToastUtil.showMEssage(MyAdvertiseActivity.this, "发布招聘信息成功!");

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
                                    MyToastUtil.showMEssage(MyAdvertiseActivity.this, object.getString("desc"));
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
                        MyToastUtil.showMEssage(MyAdvertiseActivity.this, "数据请求失败");

                    }
                });
            }

            @Override
            public void OnHttpReceived(int requestId, long nDataLen, long totalLength) {

            }
        }).post(map, Constant.PUSHADVERTISEFOR, 0);
        loadDialogShow();
    }


}
