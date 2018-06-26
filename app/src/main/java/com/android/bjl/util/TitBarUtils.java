package com.android.bjl.util;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.bjl.R;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;


/**
 * @Title: TitBarUtils
 * @Package com.rr.pay.ecspay.util
 * @Description: 封装标题栏工具类
 * @author guozhi
 * @date 2016/11/4 12:07
 * @version V1.0
 */
public class TitBarUtils {

    /**
    * @Description: 左右没有内容
    */
    public static void titleBar(Activity context, String title){
        TextView tv = (TextView) context.findViewById(R.id.title_center);
        tv.setText(title);
    }
    /**
     * @Description: 左边有文字
     */
    public static void titleBarLeftTv(Activity context, String title, String titleLeft, View.OnClickListener listener){
        TextView tv = (TextView) context.findViewById(R.id.title_center);
        tv.setText(title);
        TextView tvLeft = (TextView) context.findViewById(R.id.tv_left);
        tvLeft.setText(titleLeft);
        ImageView imgView = (ImageView) context.findViewById(R.id.img_left1);
        imgView.setVisibility(View.GONE);
        TextView tvRight = (TextView) context.findViewById(R.id.tv_right);
        tvRight.setVisibility(View.GONE);
        RelativeLayout reLeft = (RelativeLayout) context.findViewById(R.id.title_left);
        reLeft.setOnClickListener(listener);
    }
    /**
    * @Description: 右边有文字
    */
    public static void titleBarRightTv(Activity context, String title, String titleRight, View.OnClickListener listener){
        TextView tv = (TextView) context.findViewById(R.id.title_center);
        tv.setText(title);
        TextView tvRight = (TextView) context.findViewById(R.id.tv_right);
        tvRight.setText(titleRight);
        RelativeLayout reRight = (RelativeLayout) context.findViewById(R.id.title_right);
        reRight.setOnClickListener(listener);
    }
    /**
    * @Description: 左右都有文字
    */
    public static void titleTv(Activity context, String title, String titleLeft, String titleRight, View.OnClickListener leftListner, View.OnClickListener rightListener){
        TextView tv = (TextView) context.findViewById(R.id.title_center);
        tv.setText(title);

        TextView tvLeft = (TextView) context.findViewById(R.id.tv_left);
        tvLeft.setText(titleLeft);
        RelativeLayout reLeft = (RelativeLayout) context.findViewById(R.id.title_left);
        reLeft.setOnClickListener(leftListner);

        TextView tvRight = (TextView) context.findViewById(R.id.tv_right);
        tvRight.setText(titleRight);
        RelativeLayout reRight = (RelativeLayout) context.findViewById(R.id.title_right);
        reRight.setOnClickListener(rightListener);
    }
    /**
     * @Description:左边图片
     */
    public static void titleBarLeftImg(Activity context, String title, int img, View.OnClickListener listener){
        TextView tv = (TextView) context.findViewById(R.id.title_center);
        tv.setText(title);
        ImageView imgView = (ImageView) context.findViewById(R.id.img_left1);
        imgView.setImageResource(img);
        RelativeLayout reLeft = (RelativeLayout) context.findViewById(R.id.title_left);
        reLeft.setOnClickListener(listener);
    }
    /**
     * @Description:右边图片
     */
    public static void titleBarRightImg(Activity context, String title, int img, View.OnClickListener listener){
        TextView tv = (TextView) context.findViewById(R.id.title_center);
        tv.setText(title);
        ImageView imgView = (ImageView) context.findViewById(R.id.img_right);
        imgView.setImageResource(img);
        RelativeLayout reRight = (RelativeLayout) context.findViewById(R.id.title_right);
        reRight.setOnClickListener(listener);
    }
    /**
     * @Description:左右都是图片
     */
    public static void titleBarImg(Activity context, String title, int imgLeft, int imgRight, View.OnClickListener leftListener, View.OnClickListener rightListener){
        TextView tv = (TextView) context.findViewById(R.id.title_center);
        tv.setText(title);

        ImageView imgViewLeft = (ImageView) context.findViewById(R.id.img_left1);
        imgViewLeft.setImageResource(imgLeft);
        RelativeLayout reLeft = (RelativeLayout) context.findViewById(R.id.title_left);
        reLeft.setOnClickListener(leftListener);

        ImageView imgViewRight = (ImageView) context.findViewById(R.id.img_right);
        imgViewRight.setImageResource(imgRight);
        RelativeLayout reRight = (RelativeLayout) context.findViewById(R.id.title_right);
        reRight.setOnClickListener(rightListener);
    }
    /**
     * @Description:左边图片，右边文字
     */
    public static void titleBarRightText(Activity context, String title, int imgLeft, String textRight, View.OnClickListener leftListener, View.OnClickListener rightListener){
        TextView tv = (TextView) context.findViewById(R.id.title_center);
        tv.setText(title);
        ImageView imgViewLeft = (ImageView) context.findViewById(R.id.img_left1);
        imgViewLeft.setImageResource(imgLeft);
        RelativeLayout reLeft = (RelativeLayout) context.findViewById(R.id.title_left);
        reLeft.setOnClickListener(leftListener);
        RelativeLayout reRight = (RelativeLayout) context.findViewById(R.id.title_right);
        reRight.setOnClickListener(rightListener);
        TextView tvRight = (TextView) context.findViewById(R.id.tv_right);
        tvRight.setText(textRight);
    }
    /**
     * @Description:左边图片，右边文字
     */
    public static void titleBarRightTextImg(Activity context, String title, int imgLeft, int imgRight, String textRight, View.OnClickListener leftListener, View.OnClickListener rightListener){
        TextView tv = (TextView) context.findViewById(R.id.title_center);
        tv.setText(title);
        ImageView imgViewLeft = (ImageView) context.findViewById(R.id.img_left1);
        imgViewLeft.setImageResource(imgLeft);
        RelativeLayout reLeft = (RelativeLayout) context.findViewById(R.id.title_left);
        reLeft.setOnClickListener(leftListener);
        RelativeLayout reRight = (RelativeLayout) context.findViewById(R.id.title_right);
        reRight.setOnClickListener(rightListener);
        TextView tvRight = (TextView) context.findViewById(R.id.tv_right);
        tvRight.setText(textRight);
        ImageView imgRightTv = (ImageView)context.findViewById(R.id.img_tvright);
        imgRightTv.setImageDrawable(context.getResources().getDrawable(imgRight));

    }
    /**
     * @Description: 左边文字和图片，中间标题
     */
    public static void titleBarLeftTextImg(Activity context, String title, String titleLeft, int imgLeft, View.OnClickListener leftListner){
        TextView tv = (TextView) context.findViewById(R.id.title_center);
        tv.setText(title);

        TextView tvLeft = (TextView) context.findViewById(R.id.tv_left);
        tvLeft.setText(titleLeft);
        ImageView imgViewLeft = (ImageView) context.findViewById(R.id.img_left1);
        imgViewLeft.setImageResource(imgLeft);
        RelativeLayout reLeft = (RelativeLayout) context.findViewById(R.id.title_left);
        reLeft.setOnClickListener(leftListner);


    }
    /**
     * @Description: 左边文字和图片，中间标题
     */
    public static void titleBarLeftTextImgRightTv(Activity context, String title, String titleLeft, int imgLeft, String textRight,View.OnClickListener leftListner,View.OnClickListener rightListener){
        TextView tv = (TextView) context.findViewById(R.id.title_center);
        tv.setText(title);

        TextView tvLeft = (TextView) context.findViewById(R.id.tv_left);
        tvLeft.setText(titleLeft);
        ImageView imgViewLeft = (ImageView) context.findViewById(R.id.img_left1);
        imgViewLeft.setImageResource(imgLeft);
        RelativeLayout reLeft = (RelativeLayout) context.findViewById(R.id.title_left);
        reLeft.setOnClickListener(leftListner);
        RelativeLayout reRight = (RelativeLayout) context.findViewById(R.id.title_right);
        reRight.setOnClickListener(rightListener);
        TextView tvRight = (TextView) context.findViewById(R.id.tv_right);
        tvRight.setText(textRight);


    }
    /**
     * @Description: Fragment左右没有内容
     */
    public static void FragmentTitleBar(View view, String title){
        TextView tv = (TextView) view.findViewById(R.id.title_center);
        tv.setText(title);
    }
    /**
     * @Description: Fragment左右没有内容
     */
    public static void FragmentTitleBarRightTv(View view, String title,String rightTv,View.OnClickListener listener){
        TextView tv = (TextView) view.findViewById(R.id.title_center);
        tv.setText(title);
        RelativeLayout reRight = (RelativeLayout) view.findViewById(R.id.title_right);
        reRight.setOnClickListener(listener);
        TextView tvRight = (TextView) view.findViewById(R.id.tv_right);
        tvRight.setText(rightTv);
    }
}
