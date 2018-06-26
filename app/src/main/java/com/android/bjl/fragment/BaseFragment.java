package com.android.bjl.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.bjl.util.DialogUtils;
import com.android.bjl.util.TitBarUtils;

/**
 * Created by john on 2018/4/28.
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener{


    protected Dialog loadingDialog;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            loadingDialog = DialogUtils.loadingDialog(getActivity());
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);
            View view = inflater.inflate(getViewRes(), container, false);
            initView(view);
            return view;
        }

        /**
         * @Description: 子类复写此方法进行UI的绑定
         * @para 碎片已加载进来的View
         */
        protected abstract void initView(View view);

        /**
         * @Description: 子类复写此方法进行View的设定
         * @para 需要加载的View
         */
        protected abstract int getViewRes();

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            initUIafterViewCreated(view);
        }

        protected void initUIafterViewCreated(View view) {
        }




    protected void loadDialogShow() {
        if (getActivity().getApplicationContext() != null) {
            if (loadingDialog != null && !loadingDialog.isShowing()) {
                loadingDialog.show();
            }
        }
    }

    protected void loadDialogDismiss() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }






    @Override
    public void onClick(View v) {

    }
}
