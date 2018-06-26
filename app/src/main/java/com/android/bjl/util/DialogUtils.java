package com.android.bjl.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.bjl.R;
import com.android.bjl.dialog.LoadingDialog;


/**
 * Created by guozhi on 2016/10/31.
 */
public class DialogUtils {

    @SuppressLint("NewApi")
    public static Dialog loadingDialog(final Activity context) {
        if (context == null || context.isFinishing()) {
            return null;
        }
        final Dialog dialog = new LoadingDialog(context);
        dialog.setCancelable(true);
        return dialog;
    }

    public static Dialog showDialog(final Activity context, boolean flag,
                                    String msg, final View.OnClickListener okListener,
                                    final View.OnClickListener cancelListener) {
        if (context == null || context.isFinishing()) {
            return null;
        }
        final Dialog dialog = new Dialog(context, R.style.FullScreenDialogWithBackground);
        LayoutInflater mLayoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = mLayoutInflater.inflate(R.layout.dialog_utils, null);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        // 显示宽度为屏幕的4/5
        params.width = context.getWindowManager().getDefaultDisplay().getWidth() * 4 / 5;
        dialog.setCancelable(false);
        TextView tvMsg = (TextView) layout.findViewById(R.id.dialog_msg);
        tvMsg.setText(msg);
        Button btnOK = (Button) layout.findViewById(R.id.dialog_ok);
        Button btnCancel = (Button) layout.findViewById(R.id.dialog_cancle);
        if (flag) {
            btnCancel.setVisibility(View.GONE);
        }
        btnOK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (okListener != null)
                    okListener.onClick(arg0);
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cancelListener != null)
                    cancelListener.onClick(view);
                dialog.dismiss();
            }
        });
        dialog.addContentView(layout, params);
        if (dialog.getContext() != null && context !=null) {
            dialog.show();
        }
        return dialog;
    }
}
