package com.android.bjl.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.bjl.R;
import com.android.bjl.http.Constant;
import com.android.bjl.http.HttpCommon;
import com.android.bjl.http.HttpEventCallBack;
import com.android.bjl.util.MyCountDownTimer;
import com.android.bjl.util.MyToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgetpsActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.et_forgetps_certNo)
    EditText etForgetpsCertNo;
    @BindView(R.id.et_forgetps_mobilePhone)
    EditText etForgetpsMobilePhone;
    @BindView(R.id.et_forgetps_checkCode)
    EditText etForgetpsCheckCode;
    @BindView(R.id.et_forgetps_password)
    EditText etForgetpsPassword;
    @BindView(R.id.btn_forgetps)
    Button btnForgetps;
    String certNo, mobilePhone, checkCode, password;
    @BindView(R.id.tv_forgerps_getcode)
    TextView tvForgerpsGetcode;
    MyCountDownTimer timer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetps);
        ButterKnife.bind(this);
        showTitleLeftTvAndImg("找回密码", "返回", R.mipmap.back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnForgetps.setOnClickListener(this);
        tvForgerpsGetcode.setOnClickListener(this);
        timer = new MyCountDownTimer(60000, 1000, tvForgerpsGetcode);
    }


    private boolean checkData() {
        certNo = etForgetpsCertNo.getText().toString().trim();
        mobilePhone = etForgetpsMobilePhone.getText().toString().trim();
        checkCode = etForgetpsCheckCode.getText().toString().trim();
        password = etForgetpsPassword.getText().toString().trim();
        if (TextUtils.isEmpty(certNo)) {
            MyToastUtil.showMEssage(ForgetpsActivity.this, "身份证号不能为空");
            return false;
        } else if (TextUtils.isEmpty(mobilePhone)) {
            MyToastUtil.showMEssage(ForgetpsActivity.this, "手机号不能为空");
            return false;
        } else if (TextUtils.isEmpty(checkCode)) {
            MyToastUtil.showMEssage(ForgetpsActivity.this, "验证码不能为空");
            return false;
        } else if (TextUtils.isEmpty(password)) {
            MyToastUtil.showMEssage(ForgetpsActivity.this, "登录密码不能为空");
            return false;
        }
        return true;
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
                                    MyToastUtil.showMEssage(ForgetpsActivity.this, "发送验证码成功");

                                }
                            });


                            break;
                        case 2:
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    MyToastUtil.showMEssage(ForgetpsActivity.this, "密码成功找回，请重新登陆");
                                    startActivity(LoginActivity.class);
                                    finish();
                                }
                            });


                            break;
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                MyToastUtil.showMEssage(ForgetpsActivity.this, object.getString("desc"));
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
                    MyToastUtil.showMEssage(ForgetpsActivity.this, "数据请求失败");

                }
            });
        }

        @Override
        public void OnHttpReceived(int requestId, long nDataLen, long totalLength) {

        }
    };


    public void getCheckCode() {
        mobilePhone = etForgetpsMobilePhone.getText().toString().trim();
        if (TextUtils.isEmpty(mobilePhone)) {
            MyToastUtil.showMEssage(ForgetpsActivity.this, "手机号不能为空");
            return;
        }
        HashMap<String, String> map = new LinkedHashMap<String, String>();
        map.put("mobilePhone", mobilePhone);
//            map.put("isCheck", checkCode);
        map.put("isCheck", "0");
        new HttpCommon(ForgetpsActivity.this, callBack
        ).post(map, Constant.REGISTERSENGSMS, 1);

        timer.start();
        loadDialogShow();
    }

    public void findPass() {
        if (checkData()) {
            HashMap map = new LinkedHashMap();
            map.put("certNo", certNo);
            map.put("password", password);
            map.put("mobilePhone", mobilePhone);
            map.put("checkCode", checkCode);
            new HttpCommon(ForgetpsActivity.this, callBack
            ).post(map, Constant.FINDPASSWPRD, 2);
            loadDialogShow();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_forgetps:
                findPass();
                break;
            case R.id.tv_forgerps_getcode:
                getCheckCode();
                break;
        }
    }
}
