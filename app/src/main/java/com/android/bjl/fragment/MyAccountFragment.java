package com.android.bjl.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.android.bjl.R;
import com.android.bjl.activity.ForgetpsActivity;
import com.android.bjl.activity.LoginActivity;
import com.android.bjl.activity.MyCapticalActivity;
import com.android.bjl.activity.ServiceActivity;
import com.android.bjl.activity.UpdateSelfInfoActivity;
import com.android.bjl.bean.UserBean;
import com.android.bjl.util.CommonUtil;
import com.android.bjl.util.DialogUtils;
import com.android.bjl.util.GsonUtils;
import com.android.bjl.util.PrefUtils;
import com.android.bjl.util.TitBarUtils;

public class MyAccountFragment extends BaseFragment {
    private RelativeLayout rl1, rl2, rl3, rl4;
    private Button cancle;
    @Override
    protected void initView(View view) {
        TitBarUtils.FragmentTitleBar(view, "个人中心");
        rl1 = (RelativeLayout) view.findViewById(R.id.center_rl1);
        rl2 = (RelativeLayout) view.findViewById(R.id.center_rl2);
        rl3 = (RelativeLayout) view.findViewById(R.id.center_rl3);
        rl4 = (RelativeLayout) view.findViewById(R.id.center_rl4);
        cancle = (Button) view.findViewById(R.id.center_cancle);
        rl1.setOnClickListener(this);
        rl2.setOnClickListener(this);
        rl3.setOnClickListener(this);
        rl4.setOnClickListener(this);
        cancle.setOnClickListener(this);
    }

    @Override
    protected int getViewRes() {
        return R.layout.fragment_myaccount;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.center_rl1:
                if (CommonUtil.checkLogin(getActivity())) {
                    startActivity(new Intent(getActivity(), MyCapticalActivity.class));
                }
                break;
            case R.id.center_rl2:
                if (CommonUtil.checkLogin(getActivity())) {
                    Intent intent = new Intent(getActivity(), ServiceActivity.class);
                    intent.putExtra("type", 1);//type==1是从个人中心过去的
                    startActivity(intent);
                }
                break;
            case R.id.center_rl3:
                if (CommonUtil.checkLogin(getActivity())) {
                    startActivity(new Intent(getActivity(), UpdateSelfInfoActivity.class));
                }
                break;
            case R.id.center_rl4:
                if (CommonUtil.checkLogin(getActivity())) {
                    startActivity(new Intent(getActivity(), ForgetpsActivity.class));
                }
                break;
            case R.id.center_cancle:
                if (CommonUtil.checkLogin(getActivity())) {

                    DialogUtils.showDialog(getActivity(), false, "退出登录", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            PrefUtils.putString(getActivity(), "isLogin", "0");
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

                            PrefUtils.putString(getActivity(), "certNo", "");
                            PrefUtils.putString(getActivity(), "mobilePhone", "");
                            PrefUtils.putString(getActivity(), "status", "");
                            PrefUtils.putString(getActivity(), "userId", "");
                            PrefUtils.putString(getActivity(), "userName", "");
                            PrefUtils.putString(getActivity(), "userType", "");
                            Intent intent =new Intent(getActivity(),LoginActivity.class);
                            startActivity(intent);
                        }
                    }, null);
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!PrefUtils.getString(getActivity(),"isLogin","").equals("1")){
            cancle.setText("登录");
        }else {
            cancle.setText("注销登录");
        }
    }
}
