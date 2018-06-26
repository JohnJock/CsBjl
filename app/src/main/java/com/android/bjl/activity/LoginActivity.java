package com.android.bjl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.bjl.R;
import com.android.bjl.bean.UserBean;
import com.android.bjl.http.Constant;
import com.android.bjl.http.HttpCommon;
import com.android.bjl.http.HttpEventCallBack;
import com.android.bjl.util.GsonUtils;
import com.android.bjl.util.MyToastUtil;
import com.android.bjl.util.PrefUtils;
import com.android.bjl.widget.ClearEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 王京杰
 * 日期：2017/12/28
 * 邮箱：wangjj@bjrrtx.com
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.login_tv_name)
    ClearEditText etName;
    @BindView(R.id.login_tv_pwd)
    ClearEditText etPwd;
    @BindView(R.id.login_btn)
    Button btnLogin;
    @BindView(R.id.login_forgetps)
    TextView loginForgetps;
    @BindView(R.id.login_regist)
    TextView loginRegist;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        btnLogin.setOnClickListener(this);
        loginForgetps.setOnClickListener(this);
        loginRegist.setOnClickListener(this);
        showTitleLeftTvAndImg("登录", "返回", R.mipmap.back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,IndexActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.login_btn) {
            if (checkData()) {
                HashMap map = new LinkedHashMap();
                map.put("certNo", etName.getText().toString().trim());
                map.put("password", etPwd.getText().toString().trim());
                loadDialogShow();
                new HttpCommon(LoginActivity.this, new HttpEventCallBack() {
                    @Override
                    public void onHttpSuccess(int requestId, Object content) {
                        loadDialogDismiss();
                        try {
                            final JSONObject object = new JSONObject(content.toString());
                            if (object.getString("code").equals("00")) {
                                UserBean bean = GsonUtils.parseJSON(object.getJSONObject("user").toString(), UserBean.class);
                                PrefUtils.putString(LoginActivity.this, "isLogin", "1");
                                /**
                                 *  {
                                 "certNo": "410422199009257000",
                                 "mobilePhone": "18515838630",
                                 "status": "0",
                                 "userId": "12",
                                 "userName": "王京杰",
                                 "userType": "0"
                                 }
                                 */

                                PrefUtils.putString(LoginActivity.this, "certNo", bean.getCertNo());
                                PrefUtils.putString(LoginActivity.this, "mobilePhone", bean.getMobilePhone());
                                PrefUtils.putString(LoginActivity.this, "status", bean.getStatus());
                                PrefUtils.putString(LoginActivity.this, "userId", bean.getUserId());
                                PrefUtils.putString(LoginActivity.this, "userName", bean.getUserName());
                                PrefUtils.putString(LoginActivity.this, "userType", bean.getUserType());
                              startActivity(new Intent(LoginActivity.this,IndexActivity.class));
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            MyToastUtil.showMEssage(LoginActivity.this, object.getString("desc"));
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
                                MyToastUtil.showMEssage(LoginActivity.this, "数据请求失败");

                            }
                        });
                    }

                    @Override
                    public void OnHttpReceived(int requestId, long nDataLen, long totalLength) {
                        loadDialogDismiss();
                    }
                }).post(map, Constant.LOGIN, 1);
            }
        } else if (view.getId() == R.id.login_forgetps) {
            startActivity(new Intent(LoginActivity.this, ForgetpsActivity.class));

        } else if (view.getId() == R.id.login_regist) {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

        }
    }

    private boolean checkData() {
        if (etName.getText() == null || TextUtils.isEmpty(etName.getText().toString().trim())) {
            MyToastUtil.showMEssage(LoginActivity.this, "用户名不能为空");
            return false;
        } else if (etPwd.getText() == null || TextUtils.isEmpty(etPwd.getText().toString().trim())) {
            MyToastUtil.showMEssage(LoginActivity.this, "密码不能为空");
            return false;
        }
        return true;
    }
}
