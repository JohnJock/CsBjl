package com.android.bjl.util;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by john on 2018/3/5.
 */

public class DownloadUtil {

    //	private String apkUrl = "http://softfile.3g.qq.com:8080/msoft/179/24659/43549/qq_hd_mini_1.4.apk";
    private static DownloadUtil downloadUtil;
    private final OkHttpClient okHttpClient;
    private NotificationManager manager;
    private NotificationCompat.Builder builder;
    private   Activity context;

    public static DownloadUtil get(Activity context) {
        if (downloadUtil == null) {
            downloadUtil = new DownloadUtil(context);
        }
        return downloadUtil;
    }

    private DownloadUtil(Activity context) {
        okHttpClient = new OkHttpClient();
        this.context =context;
        manager= (NotificationManager) context.getSystemService(Activity.NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(context);
    }

    /**
     * @param url      下载连接
     * @param saveDir  储存下载文件的SDCard目录
     * @param listener 下载监听
     */
    public void download(final String url, final String saveDir, final OnDownloadListener listener) {
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                // 下载失败
                listener.onDownloadFailed();
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                // 储存下载文件的目录
//                String savePath = isExistDir(saveDir);
                String savePath = getFilePath(context);
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
//                    File file = new File(savePath, getNameFromUrl(url));
                    File file = new File(savePath);
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        // 下载中
                        listener.onDownloading(progress);
                    }
                    fos.flush();
                    // 下载完成
                    listener.onDownloadSuccess(savePath);
                } catch (Exception e) {
                    listener.onDownloadFailed();
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                }
            }
        });
    }

    /**
     * @param saveDir
     * @return
     * @throws IOException 判断下载目录是否存在
     */
    private String isExistDir(String saveDir) throws IOException {
        // 下载位置
        File downloadFile = new File(Environment.getExternalStorageDirectory(), saveDir);
        if (!downloadFile.mkdirs()) {
            downloadFile.createNewFile();
        }
        String savePath = downloadFile.getAbsolutePath();
        return savePath;
    }

    /**
     * @param url
     * @return 从下载连接中解析出文件名
     */
    @NonNull
    private String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    public interface OnDownloadListener {
        /**
         * 下载成功
         */
        void onDownloadSuccess(String savePath);

        /**
         * @param progress 下载进度
         */
        void onDownloading(int progress);

        /**
         * 下载失败
         */
        void onDownloadFailed();
    }


    /**
     * Method Name：getFilePath Description：获取工程文件目录
     * data/data/com.rrtx.rrtxandroid/files
     * @param activity Creator：muzhengjun Create DateTime：2013-10-08
     */
    public static String getFilePath(Activity activity) {
        String zipPath="";
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())||!Environment.isExternalStorageRemovable()){
            zipPath=activity.getExternalFilesDir(null).getAbsolutePath().toString();
        }else {
            zipPath = activity.getFilesDir().toString();
        }
        String  saveFileName="";
        if(Build.VERSION.SDK_INT> Build.VERSION_CODES.M){
            saveFileName = zipPath+"/rrtx"+new Random().nextInt()+"app.apk";
        }else {
            File file = new File(Environment.getExternalStorageDirectory().getPath()+"/updateApkDemo/");
            if (!file.exists()) {
                file.mkdirs();
            }
            saveFileName=Environment.getExternalStorageDirectory().getPath()+"/updateApkDemo/"+"rrtx"+new Random().nextInt()+"app.apk";
        }
        return saveFileName;
    }
}
