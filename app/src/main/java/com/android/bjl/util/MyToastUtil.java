package com.android.bjl.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.bjl.R;


/**
 * 王京杰
 * 日期：2017/12/28
 * 邮箱：wangjj@bjrrtx.com
 */

public class MyToastUtil {

    private static Toast mToast;

    public static void showMEssage(Context ctx, String msg) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.def_layout_toast, null);
        TextView toast = (TextView) view.findViewById(R.id.tv_toast);
        toast.setText(msg);
        mToast = new Toast(ctx);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setView(view);
        mToast.setGravity(Gravity.BOTTOM, 0, 180);//x >0向右偏移，小于0向左偏移
        mToast.show();
    }

    public static void setGravity(int gravity, int xOffset, int yOffset) {
        if (mToast != null) {
            mToast.setGravity(gravity, xOffset, yOffset);
        }
    }
}
