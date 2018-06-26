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
import com.android.bjl.util.SoftHideKeyBoardUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.et_register_name)
    EditText etRegisterName;
    @BindView(R.id.et_register_certNo)
    EditText etRegisterCertNo;
    @BindView(R.id.et_register_mobilePhone)
    EditText etRegisterMobilePhone;
    @BindView(R.id.et_register_checkCode)
    EditText etRegisterCheckCode;
    @BindView(R.id.et_register_password)
    EditText etRegisterPassword;
    @BindView(R.id.btn_register)
    Button btnRegister;
    String certNo, mobilePhone, checkCode, password, passwordconfirm, userName;
    @BindView(R.id.et_register_passwordconfirm)
    EditText etRegisterPasswordconfirm;
    @BindView(R.id.tv_register_getcode)
    TextView tvRegisterGetcode;
    MyCountDownTimer timer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);
        showTitleLeftTvAndImg("注册", "返回", R.mipmap.back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnRegister.setOnClickListener(this);
        tvRegisterGetcode.setOnClickListener(this);
        timer = new MyCountDownTimer(60000, 1000, tvRegisterGetcode);
    }

    private boolean checkData() {
        userName = etRegisterName.getText().toString().trim();
        certNo = etRegisterCertNo.getText().toString().trim();
        mobilePhone = etRegisterMobilePhone.getText().toString().trim();
        checkCode = etRegisterCheckCode.getText().toString().trim();
        password = etRegisterPassword.getText().toString().trim();
        passwordconfirm = etRegisterPasswordconfirm.getText().toString().trim();
        if (TextUtils.isEmpty(certNo)) {
            MyToastUtil.showMEssage(RegisterActivity.this, "身份证号不能为空");
            return false;
        } else if (TextUtils.isEmpty(mobilePhone)) {
            MyToastUtil.showMEssage(RegisterActivity.this, "手机号不能为空");
            return false;
        } else if (TextUtils.isEmpty(checkCode)) {
            MyToastUtil.showMEssage(RegisterActivity.this, "验证码不能为空");
            return false;
        } else if (TextUtils.isEmpty(password)) {
            MyToastUtil.showMEssage(RegisterActivity.this, "登录密码不能为空");
            return false;
        } else if (TextUtils.isEmpty(passwordconfirm)) {
            MyToastUtil.showMEssage(RegisterActivity.this, "确认密码不能为空");
            return false;
        } else if (!password.equals(passwordconfirm)) {
            MyToastUtil.showMEssage(RegisterActivity.this, "密码必须保持一致");
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
                                    MyToastUtil.showMEssage(RegisterActivity.this, "发送验证码成功");

                                }
                            });


                            break;
                        case 2:
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    MyToastUtil.showMEssage(RegisterActivity.this, "注册成功，请重新登陆");
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
                                MyToastUtil.showMEssage(RegisterActivity.this, object.getString("desc"));
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
                    MyToastUtil.showMEssage(RegisterActivity.this, "数据请求失败");

                }
            });
        }

        @Override
        public void OnHttpReceived(int requestId, long nDataLen, long totalLength) {
            loadDialogDismiss();
        }
    };


    public void getCheckCode() {
        mobilePhone = etRegisterMobilePhone.getText().toString().trim();
        if (TextUtils.isEmpty(mobilePhone)) {
            MyToastUtil.showMEssage(RegisterActivity.this, "手机号不能为空");
            return;
        }
        HashMap<String, String> map = new LinkedHashMap<String, String>();
        map.put("mobilePhone", mobilePhone);
//            map.put("isCheck", checkCode);
        map.put("isCheck", "0");
        new HttpCommon(RegisterActivity.this, callBack
        ).post(map, Constant.REGISTERSENGSMS, 1);

        timer.start();
        loadDialogShow();

    }

    public void register() {
        if (checkData()) {
            HashMap<String, String> map = new LinkedHashMap<String, String>();
            map.put("certNo", certNo);
            map.put("password", password);
            map.put("mobilePhone", mobilePhone);
            map.put("checkCode", checkCode);
            map.put("village", "000001");
            map.put("userName", userName);
            new HttpCommon(RegisterActivity.this, callBack
            ).post(map, Constant.REGISTERUSER, 2);
            loadDialogShow();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                register();
                break;
            case R.id.tv_register_getcode:

                getCheckCode();
                break;
        }
    }
}
