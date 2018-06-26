package com.android.bjl.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;


import com.android.bjl.activity.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;


/**
 * 常用操作工具类
 *
 * @author ZHANGZZ
 */
public class CommonUtil {

    public static final String MONEY_FORMAT_SHOW = "##,###,###,###,##0.00";
    public static final String MONEY_FULL_SHOW = "##0.00";
    /**
     * 数据库存储的默认时间格式
     */
    public static final String DEFAULT_TIME_FORMAT_DB = "yyyyMMddHHmmss";

    /**
     * 日期
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyyMMdd";

    /**
     * obj to boolean
     *
     * @return
     */
    public static boolean objToBoolean(Object pObject) {
        boolean strValue = false;
        try {
            strValue = (Boolean) pObject;
        } catch (Exception e) {
            strValue = false;
        }
        return strValue;
    }

    /**
     * obj to String
     *
     * @return
     */
    public static String objToString(Object pObject) {
        String strValue = "";
        try {
            strValue = pObject.toString();
        } catch (Exception e) {
            strValue = "";
        }
        if ("null".equals(strValue)) {
            strValue = "";
        }
        return strValue;
    }

    /**
     * @param pObject
     * @return
     */
    public static BigDecimal objToBigDecimal(final Object pObject) {
        BigDecimal result = null;
        String str = "";

        if (pObject != null) {
            str = pObject.toString();
        } else {
            str = "";
        }

        try {
            if ("".equals(str)) {
                return new BigDecimal(0);
            } else {
                result = new BigDecimal(str);
            }
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
        //result = result.setScale(2,RoundingMode.HALF_UP);
        return result;
    }

    public static int objToint(final Object pObject) {
        int intReturn = 0;
        int intIndex = 0;
        try {
            if (pObject != null && !("".equals(pObject.toString().trim()))) {
                String strObject = pObject.toString().trim();
                intIndex = strObject.indexOf(".");
                if (intIndex == -1) {
                    intReturn = new Integer(strObject).intValue();
                } else {
                    intReturn = new Integer(strObject.substring(0, intIndex)).intValue();
                }
            } else {
                intReturn = 0;
            }
        } catch (Exception e) {
            intReturn = 0;
        }
        return intReturn;
    }

    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str) || "null".equals(str)) {
            return true;
        }
        return false;
    }

    /**
     * str1 加上  str2
     *
     * @param str1
     * @param str2
     * @return
     */
    public static String add(String str1, String str2) {
        return objToString(objToBigDecimal(str1).add(objToBigDecimal(str2)));
    }

    /**
     * str1 减去 str2
     *
     * @param str1
     * @param str2
     * @return
     */
    public static String subtract(String str1, String str2) {
        return objToString(objToBigDecimal(str1).subtract(objToBigDecimal(str2)));
    }

    /**
     * 比较
     *
     * @param str1
     * @param str2
     * @return 0 : str1 == str2
     * 1 : str1 >  str2
     * -1 : str1 <  str2
     */
    public static int compare(String str1, String str2) {
        if (str1 == null || "".equals(str1) || "null".equals(str1)) {
            str1 = "0";
        }
        if (str2 == null || "".equals(str2) || "null".equals(str2)) {
            str2 = "0";
        }
        BigDecimal big1 = new BigDecimal(str1);
        BigDecimal big2 = new BigDecimal(str2);
        int iRet = big1.compareTo(big2);
        if (iRet > 0) {
            return 1;
        } else if (iRet < 0) {
            return -1;
        }

        return 0;
    }

    /**
     * str1/str2
     *
     * @param str1
     * @param str2
     * @return
     */
    public static String divide(String str1, String str2) {
        if (str1 == null || "".equals(str1) || "null".equals(str1)) {
            str1 = "0";
        }
        if (str2 == null || "".equals(str2) || "null".equals(str2)) {
            str2 = "0";
        }
        BigDecimal big1 = new BigDecimal(str1);
        BigDecimal big2 = new BigDecimal(str2);
        BigDecimal iRet = big1.divide(big2, 2, BigDecimal.ROUND_HALF_EVEN);
        return iRet.toString();
    }

    /**
     * 相乘 str1*str2
     *
     * @param str1
     * @param str2
     * @return
     */
    public static String multiply(String str1, String str2) {
        if (str1 == null || "".equals(str1) || "null".equals(str1)) {
            str1 = "0";
        }
        if (str2 == null || "".equals(str2) || "null".equals(str2)) {
            str2 = "0";
        }
        BigDecimal big1 = new BigDecimal(str1);
        BigDecimal big2 = new BigDecimal(str2);
        BigDecimal iRet = big1.multiply(big2);
        return iRet.toString();
    }


    /**
     * 生成15位随机码
     *
     * @return
     */
    public static String getCode() {
        Random random = new Random();
        String sRand = "";
        for (int i = 0; i < 15; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
        }
        return sRand;
    }

    /**
     * 将金额格式化为xx,xxx,xxx.00类型显示
     *
     * @return
     */
    public static String formatMoney(String str) {
        DecimalFormat fmt = new DecimalFormat(CommonUtil.MONEY_FORMAT_SHOW);
        String outStr = null;
        double money;
        try {
            // format
            money = Double.parseDouble(str);
            outStr = fmt.format(money);
        } catch (Exception e) {

            return str;
        }
        return outStr;
    }

    /**
     * 将金额格式化为xxxx.00类型显示
     *
     * @return
     */
    public static String fillMoney(String str) {
        DecimalFormat fmt = new DecimalFormat(CommonUtil.MONEY_FULL_SHOW);
        String outStr = null;
        double money;
        try {
            // format
            money = Double.parseDouble(str);
            outStr = fmt.format(money);
        } catch (Exception e) {

            return str;
        }
        return outStr;
    }

    /**
     * 将金额反格式化
     *
     * @param str
     * @param flag 0时以元为单位，其他以分为单位
     * @return
     */
    public static String unFormatMoney(String str, String flag) {
        String outStr = "";
        int pointIndex = 0;
        //先判断是不是整金额
        pointIndex = str.indexOf(".");
        boolean boo = "00".equals(str.substring(pointIndex + 1, str.length()));
        //处理金额
        if (boo) {
            str = str.substring(0, pointIndex);
        }
        if ("0".equals(flag)) {//以元为单位
            outStr = str.replaceAll(",", "");
        } else {//以分为单位
            BigDecimal big = new BigDecimal(str.replaceAll(",", "")).multiply(new BigDecimal("100"));
            outStr = big.setScale(0, BigDecimal.ROUND_DOWN).toString();
        }
        return outStr;
    }

    /**
     * 将时间格式化为指定格式
     *
     * @return
     */
    public static String timeFormat(String time, String format) {
        String systemTime = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = sdf.parse(time);
            SimpleDateFormat todf = new SimpleDateFormat(format);// 设置日期格式
            systemTime = todf.format(date);
        } catch (Exception ex) {
            return "";
        }
        return systemTime;
    }

    /**
     * 将时间格式化为标准显示格式
     *
     * @return
     */
    public static String timeFormat(String time) {
        String systemTime = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = sdf.parse(time);
            SimpleDateFormat todf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
            systemTime = todf.format(date);
        } catch (Exception ex) {
            return systemTime;
        }
        return systemTime;
    }

    /**
     * 算出日期
     *
     * @param ca
     * @return
     */
    public static String getDate(Calendar ca) {

        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");

        return dateformat.format(ca);
    }

    /**
     * 算出日期
     *
     * @param ca
     * @return
     */
    public static String getDate(Calendar ca, String format) {

        SimpleDateFormat dateformat = new SimpleDateFormat(format);

        return dateformat.format(ca);
    }


    /**
     * 得到系统日期
     *
     * @return
     */
    public static String getSysDate(String format) {

        SimpleDateFormat dateformat = new SimpleDateFormat(format);

        return dateformat.format(new Date());
    }

    /**
     * 得到系统日期
     *
     * @return
     */
    public static String getSysDate() {

        SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd");

        return dateformat.format(new Date());
    }

    /**
     * 比较当前日期
     * 小于返回false
     */
    public static boolean compareDate(String date) {
        if (date.length() == 7) {
            date = date.substring(0, date.length() - 1) + "0" + date.substring(date.length() - 1, date.length());
        }
        boolean b = Integer.parseInt(date) < Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(new Date()));
        return !b;
    }

    /**
     * 取得若干天前/后的系统日期
     *
     * @param days       与现在相隔的日数，正数为当前日期之后，负数为当前日期之前
     * @param timeFormat 格式化
     * @return String
     */
    public static String getDifferentDate(int days, String timeFormat) {
        return getDifferentTime(24 * days, 0, 0, timeFormat);
    }


    /**
     * 取得指定时间间隔后的系统时间<br>
     * 示例：<br>
     * getDifferentTime( 1, 2, 3,"yyyyMMddHHmmss") <br>
     * 使用yyyyMMddHHmmss格式输出1小时2分3秒后的系统时间<br>
     * getDifferentTime( -24, 0, 0,"yyyyMMdd") <br>
     * 使用yyyyMMdd格式输出1天前的系统日期<br>
     *
     * @param hour       小时
     * @param minute     分钟
     * @param second     秒
     * @param timeFormat 格式化
     * @return String
     */
    public static String getDifferentTime(int hour, int minute, int second, String timeFormat) {
        String format = (timeFormat == null) ? CommonUtil.DEFAULT_TIME_FORMAT_DB : timeFormat;
        GregorianCalendar calendar = (GregorianCalendar) Calendar.getInstance();
        calendar.add(Calendar.HOUR, hour);
        calendar.add(Calendar.MINUTE, minute);
        calendar.add(Calendar.SECOND, second);
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(calendar.getTime());
    }

    /**
     * 给定日期得出周几
     */
    public static String getWeek(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String week = "";
        try {
            Date date = sdf.parse(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            switch (calendar.get(Calendar.DAY_OF_WEEK)) {
                case 1:
                    week = "天";
                    break;
                case 2:
                    week = "一";
                    break;
                case 3:
                    week = "二";
                    break;
                case 4:
                    week = "三";
                    break;
                case 5:
                    week = "四";
                    break;
                case 6:
                    week = "五";
                    break;
                case 7:
                    week = "六";
                    break;
                default:
                    break;
            }
            return week;
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            return week;
        }
    }

    /**
     * 从资源文件中获取指定大小的bitmap
     *
     * @param res
     * @param resId
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * 从base64字节数组中获取指定大小的bitmap
     *
     * @param base64byte
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap decodeSampledBitmapFromResource(byte[] base64byte,
                                                         int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(base64byte, 0, base64byte.length, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(base64byte, 0, base64byte.length, options);
    }

    /**
     * 从本地文件获取指定大小的bitmap
     *
     * @param src
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap decodeSampledBitmapFromResource(String src,
                                                         int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(src, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(src, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 判断网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isNetWorkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable() && mNetworkInfo.isConnected();
            }
        }
        return false;
    }

    /**
     * 异步土司
     *
     * @param activity
     * @param message  信息
     */
    public static void showToast(final Activity activity, final String message) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
//                LayoutInflater inflater=LayoutInflater.from(activity);
//                View layout=inflater.inflate(R.layout.layout_toast,null);
//                TextView textView=(TextView)layout.findViewById(R.id.tv_toast);
//                textView.setText(message);
//                Toast toast=new Toast(activity);
//                toast.setDuration(Toast.LENGTH_LONG);
//                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//                toast.setView(layout);
//                toast.show();
            }
        });
    }
    public static void showShortToast(final Activity activity, final String message) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
//                LayoutInflater inflater=LayoutInflater.from(activity);
//                View layout=inflater.inflate(R.layout.layout_toast,null);
//                TextView textView=(TextView)layout.findViewById(R.id.tv_toast);
//                textView.setText(message);
//                Toast toast=new Toast(activity);
//                toast.setDuration(Toast.LENGTH_SHORT);
//                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//                toast.setView(layout);
//                toast.show();
            }
        });
    }
    /**
     * 获取用户头像路径
     *
     * @param constomerNo
     * @param activity
     * @return
     */
    public static String getHeadPicPath(String constomerNo, Activity activity) {
        FilePathManager filePathManager = FilePathManager.getIntance(activity);
        String filePath = filePathManager.getImgPath() + constomerNo + ".png";
        return filePath;
    }

    public static String getStringFromResData(String json, String key) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json.toString());
            final JSONObject resData = jsonObject.getJSONObject("ResData");
            final String flag = resData.getString(key);
            return flag;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";

    }
    /**
     * 缩放Bitmap满屏
     *
     * @param bitmap
     * @param screenWidth
     * @param screenHight
     * @return
     */
    public static Bitmap getBitmap(Bitmap bitmap, int screenWidth,
                                   int screenHight)
    {
        Bitmap bmp = Bitmap.createScaledBitmap(bitmap, screenWidth, screenHight, true);
        if (bitmap != null && !bitmap.equals(bmp) && !bitmap.isRecycled())
        {
            bitmap.recycle();
            bitmap = null;
        }
        return bmp;// Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
    }

    public static String getConstomerName(String name) {
        if (name == null || name.length() < 1) {
            return "";
        }
        return "P" + name.substring(1);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }




    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;
        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }
    /**通过手机号查询相应姓名*/
    public static String getContectInfo(Context context, String phoneNo){
        String name="";
        // 获取联系人数据
        ContentResolver cr = context.getContentResolver();
//获取所有电话信息（而不是联系人信息），这样方便展示
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = {
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,// 姓名
                ContactsContract.CommonDataKinds.Phone.NUMBER,// 电话号码
        };
        Cursor cursor = cr.query(uri, projection, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String number = cursor.getString(1);
                if (number.equals(phoneNo)){
                    name=cursor.getString(0);
                }
            }
        }
        cursor.close();
        return name;
    }
    public static String getdouble(String amount) {//四舍五入保留两位小数
        if (!TextUtils.isEmpty(amount)) {
            amount= String.format("%.2f", Double.parseDouble(amount));
        }
        return amount;
    }
    public static String subZero(String s) {//去掉整数部分多余的0
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");
        }
        return s;
    }
    public static boolean getWei(String s){
        if (!TextUtils.isEmpty(s)&&s.contains(".")) {
            if ((s.length() - 1 - s.indexOf(".") > 1)) {//小数点后只能输入两位数
                return true;
            }
        }
        return false;
    }
    /**
     * URL编码
     * <p>若想自己指定字符集,可以使用{@link #urlEncode(String input, String charset)}方法</p>
     *
     * @param input 要编码的字符
     * @return 编码为UTF-8的字符串
     */
    public static String urlEncode(String input) {
        return urlEncode(input, "UTF-8");
    }

    /**
     * URL编码
     * <p>若系统不支持指定的编码字符集,则直接将input原样返回</p>
     *
     * @param input   要编码的字符
     * @param charset 字符集
     * @return 编码为字符集的字符串
     */
    public static String urlEncode(String input, String charset) {
        try {
            return URLEncoder.encode(input, charset);
        } catch (UnsupportedEncodingException e) {
            return input;
        }
    }

    /**
     * URL解码
     * <p>若想自己指定字符集,可以使用 {@link #urlDecode(String input, String charset)}方法</p>
     *
     * @param input 要解码的字符串
     * @return URL解码后的字符串
     */
    public static String urlDecode(String input) {
        return urlDecode(input, "UTF-8");
    }

    /**
     * URL解码
     * <p>若系统不支持指定的解码字符集,则直接将input原样返回</p>
     *
     * @param input   要解码的字符串
     * @param charset 字符集
     * @return URL解码为指定字符集的字符串
     */
    public static String urlDecode(String input, String charset) {
        try {
            return URLDecoder.decode(input, charset);
        } catch (UnsupportedEncodingException e) {
            return input;
        }
    }
    /**
     * Base64编码
     *
     * @param input 要编码的字符串
     * @return Base64编码后的字符串
     */
    public static String base64Encode(String input) {
        return base64Encode2String(input.getBytes());
    }
    /**
     * Base64编码
     *
     * @param input 要编码的字节数组
     * @return Base64编码后的字符串
     */
    public static String base64Encode2String(byte[] input) {
        return Base64.encodeToString(input, Base64.NO_WRAP);
    }
    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context)
    {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }
    /**WebZip是否存在*/
    public static void noWebZip(Context context, String menuId){
        // 获取文件路径
        FilePathManager filePathManager = FilePathManager.getIntance(context);
        String zipFilePath = filePathManager.getWebZipPath(menuId);
        // 判断文件是否存在
        File zipFile = new File(zipFilePath);
        // 如果文件不存在,则读assets中写入
//        if (!zipFile.exists()) {
//            InputStream in = null;
//            FileOutputStream out = null;
//            try {
//                in = context.getResources().getAssets().open("zips/" + menuId + ".zip"); // 从assets目录下复制
//                out = new FileOutputStream(zipFile);
//                int length = -1;
//                byte[] buf = new byte[10240];
//                while ((length = in.read(buf)) != -1) {
//                    out.write(buf, 0, length);
//                }
//                out.flush();
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    if (in != null) in.close();
//                    if (out != null) out.close();
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
//            }
//        }
    }

    /**
     * 校验是否登录
     * @param context
     * @return
     */
    public static boolean checkLogin(Context context){
        if (PrefUtils.getString(context,"userId","").equals("")){
            context.startActivity(new Intent(context,LoginActivity.class));
            return false;
        }else return true;
    }

    public static boolean checkIsThis(Context context){
        if (PrefUtils.getString(context,"userType","").equals("2")){
            MyToastUtil.showMEssage(context,"您不是本村人员，不能查看此功能");
            return false;
        }else {
            return  true;
        }
    }

    public static List<String> str2list(String str){
        str.replace(" http","http");
        str=str.substring(1,str.length()-1).trim();
        String[] arrs = str.split(",");
        for(int i=0; i < arrs.length; i++){
            arrs[i] = arrs[i].trim();
        }
        return Arrays.asList(arrs);
    }
}


