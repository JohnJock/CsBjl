package com.android.bjl.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.bjl.R;
import com.android.bjl.util.DownloadUtil;

import java.io.File;

/**
 * Created by john on 2018/3/5.
 */

public class MyDownLoadActivity extends BaseActivity {
    private  ProgressBar progressBar;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
         progressBar =(ProgressBar)findViewById(R.id.progressBar);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {

        } else {

            ActivityCompat.requestPermissions(MyDownLoadActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        findViewById(R.id.btn_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadUtil.get(MyDownLoadActivity.this).download("http://softfile.3g.qq.com:8080/msoft/179/24659/43549/qq_hd_mini_1.4.apk", "download", new DownloadUtil.OnDownloadListener() {
                    @Override
                    public void onDownloadSuccess(String savePath) {
                        Toast.makeText(MyDownLoadActivity.this, "下载完成",Toast.LENGTH_SHORT).show();
                        // 安装APk
                        installApk(MyDownLoadActivity.this,savePath);
                    }
                    @Override
                    public void onDownloading(int progress) {
                        progressBar.setProgress(progress);
                    }
                    @Override
                    public void onDownloadFailed() {
                        Toast.makeText(MyDownLoadActivity.this, "下载失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    /**
     * 安装apk
     *
     */
    private void installApk(Context context,String path) {
        File apkfile = new File(path);
        if (!apkfile.exists()) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(Build.VERSION.SDK_INT> Build.VERSION_CODES.M){
            Uri uriForFile =  FileProvider.getUriForFile(context, "com.android.bjl.fileProvider", apkfile);
            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            i.setDataAndType(uriForFile, "application/vnd.android.package-archive");
        }else {
            i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        }
        context.startActivity(i);

    }
}
