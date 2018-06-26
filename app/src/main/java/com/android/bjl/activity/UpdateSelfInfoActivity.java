package com.android.bjl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class UpdateSelfInfoActivity extends BaseActivity {
    @BindView(R.id.et_register_address)
    EditText etRegisterAddress;
    @BindView(R.id.et_update_isMarry)
    EditText etUpdateIsMarry;
    @BindView(R.id.et_update_education)
    EditText etUpdateEducation;
    @BindView(R.id.et_update_graduateSchool)
    EditText etUpdateGraduateSchool;
    @BindView(R.id.et_update_theInfo)
    EditText etUpdateTheInfo;
    @BindView(R.id.et_update_nation)
    EditText etUpdateNation;
    @BindView(R.id.btn_update_submit)
    Button btnUpdateSubmit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateself);
        ButterKnife.bind(this);
        showTitleLeftTvAndImg("完善信息", "返回", R.mipmap.back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnUpdateSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData();
            }
        });

    }
    public void initData(){
//        完善信息
//        NSMutableDictionary * jsonDic = [NSMutableDictionary dictionary];
//    [jsonDic setValue:_isMarryTV.value forKey:@"maritalStatus"];
//    [jsonDic setValue:_driveCardTV.value forKey:@"driveCardNo"];
//    [jsonDic setValue:_addressTV.value forKey:@"existingResidentialAddress"];
//    [jsonDic setValue:_emailTV.value forKey:@"email"];
//    [jsonDic setValue:_minZuTV.value forKey:@"nation"];
//    [jsonDic setValue:_jycdTV.value forKey:@"education"];
//    [jsonDic setValue:_schoolTV.value forKey:@"graduateSchool"];
//    [jsonDic setValue:_shsfTV.value forKey:@"socialIdentity"];
//    [jsonDic setValue:theInfo.userId forKey:@"userId"];

        String addressString = etRegisterAddress.getText().toString().trim();
        String ismarryString = etUpdateIsMarry.getText().toString().trim();
        String educationString = etUpdateEducation.getText().toString().trim();
        String scrollString = etUpdateGraduateSchool.getText().toString().trim();
        String TheInfoString = etUpdateTheInfo.getText().toString().trim();
        String nationString = etUpdateNation.getText().toString().trim();
        HashMap<String,String> map=new LinkedHashMap<>();
        map.put("maritalStatus",ismarryString);
        map.put("existingResidentialAddress",addressString);
        map.put("nation",nationString);
        map.put("graduateSchool",scrollString);
        map.put("socialIdentity",TheInfoString);
        map.put("userId", PrefUtils.getString(UpdateSelfInfoActivity.this,"userId",""));
        map.put("education",educationString);
        loadDialogShow();
        new HttpCommon(UpdateSelfInfoActivity.this, new HttpEventCallBack() {
            @Override
            public void onHttpSuccess(int requestId, Object content) {
                loadDialogDismiss();
                try {

                    final JSONObject object = new JSONObject(content.toString());
                    if (object.getString("code").equals("00")) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                MyToastUtil.showMEssage(UpdateSelfInfoActivity.this, "修改信息成功!");

                                finish();


                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    MyToastUtil.showMEssage(UpdateSelfInfoActivity.this, object.getString("desc"));
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
                        MyToastUtil.showMEssage(UpdateSelfInfoActivity.this, "数据请求失败");

                    }
                });
            }

            @Override
            public void OnHttpReceived(int requestId, long nDataLen, long totalLength) {

            }
        }).post(map, Constant.SUBMITUSERINFO,1);
    }
}
