package com.android.bjl.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.bjl.R;
import com.android.bjl.adapter.MainGvAdapter;
import com.android.bjl.bean.BannerBean;
import com.android.bjl.bean.HistoryIndex;
import com.android.bjl.bean.Result;
import com.android.bjl.http.HttpCommon;
import com.android.bjl.http.HttpEventCallBack;
import com.android.bjl.util.GsonUtils;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity implements OnItemClickListener {
    private ConvenientBanner convenientBanner;//顶部广告栏控件
    private ArrayList<BannerBean> localImages = new ArrayList<BannerBean>();
    private GridView gv;
    private SimpleAdapter adapter;
    private ArrayList<Map<String, Object>> dataList;
    private static String IMAGE_FILE_LOCATION = "";
    private ImageView login;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();

    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, "position:" + position, Toast.LENGTH_SHORT).show();
//        startActivity(new Intent(MainActivity.this,MyDownLoadActivity.class));
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {

            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                }
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
            }
        }

    }

    public class ImageViewHolder implements Holder<BannerBean> {
        private View view;
        private ImageView imageView;
        private TextView tv;

        @Override
        public View createView(Context context) {
//            imageView = new ImageView(context);
//            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//            return imageView;
            view = LayoutInflater.from(context).inflate(R.layout.banner_item, null, false);
            return view;
        }

        @Override
        public void UpdateUI(Context context, int position, BannerBean data) {
            imageView = (ImageView) view.findViewById(R.id.banner_img);
            tv = (TextView) view.findViewById(R.id.banner_tv);
            imageView.setImageResource(data.getUrl());
            tv.setText(data.getDescription());
        }
    }


    void initData() {
        localImages.add(new BannerBean(R.mipmap.banner1, "这是第一张图片"));
        localImages.add(new BannerBean(R.mipmap.banner2, "这是第二张图片"));
        localImages.add(new BannerBean(R.mipmap.banner3, "这是第三张图片"));

        convenientBanner.setPages(new CBViewHolderCreator<ImageViewHolder>() {
            @Override
            public ImageViewHolder createHolder() {
                return new ImageViewHolder();
            }
        }, localImages).setPointViewVisible(true)//设置指示器是否可见
                .startTurning(2000)//设置自动切换（同时设置了切换时间间隔）
//                .setPageIndicator(new int[]  {R.drawable.ponit_normal,R.drawable.point_select}) //设置两个点作为指示器
                .setPageIndicator(new int[]{R.mipmap.check_no, R.mipmap.check}) //设置两个点作为指示器
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT) //设置指示器的方向水平  居中
                .setOnItemClickListener(this);

        //图标
        int icno[] = {R.mipmap.icon1, R.mipmap.icon2, R.mipmap.icon3,
                R.mipmap.icon4, R.mipmap.icon5, R.mipmap.icon6,
                R.mipmap.icon7, R.mipmap.icon8, R.mipmap.icon9};
        //图标下的文字
        final String name[] = {"我的资金", "村务公开", "惠民政策",
                "一事一议", "便民服务", "身边新闻",
                "村里的事", "村情互动", "干部工作"};
        dataList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < icno.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("img", icno[i]);
            map.put("text", name[i]);
            dataList.add(map);
        }
        String[] from = {"img", "text"};

        int[] to = {R.id.img, R.id.text};
//        adapter=new SimpleAdapter(this,dataList,R.layout.main_gv_item,from,to);
//        gv.setAdapter(adapter);
        gv.setAdapter(new MainGvAdapter(MainActivity.this, dataList));
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "position:" + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                switch (position) {
                    case 0:
                        intent.setClass(MainActivity.this, FirstActivity.class);
                        intent.putExtra("type", 0);
                        intent.putExtra("title", name[0]);
                        break;
                    case 1:
                        intent.setClass(MainActivity.this, FirstActivity.class);
                        intent.putExtra("type", 1);
                        intent.putExtra("title", name[1]);
                        break;
                    case 2:
                        intent.setClass(MainActivity.this, FirstActivity.class);
                        intent.putExtra("type", 2);
                        intent.putExtra("title", name[2]);
                        break;
                    case 3:
                        break;
                    case 4:
                        intent.setClass(MainActivity.this, FirstActivity.class);
                        intent.putExtra("type", 4);
                        intent.putExtra("title", name[4]);
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        intent.setClass(MainActivity.this, CommIndexActivity.class);
                        intent.putExtra("type", 7);
                        intent.putExtra("title", name[7]);
                        break;
                    case 8:
                        break;


                }
                startActivity(intent);
                //测试网络
//                HashMap map = new LinkedHashMap();
//                map.put("v", "1.0");
//                map.put("month", 10 + "");
//                map.put("day", 1 + "");
//                map.put("key", "a7c3e8e7c78a7d6f28efa7abb1130f95");
//                new HttpCommon(MainActivity.this, new HttpEventCallBack() {
//                    @Override
//                    public void onHttpSuccess(int requestId, Object content) {
//                        String result = content.toString();
//                        Result<List<HistoryIndex>> result1 = GsonUtils.fromJsonArray(result, HistoryIndex.class);
//                        List<HistoryIndex> historylist = result1.result;
//
//                    }
//
//                    @Override
//                    public void onHttpFail(int requestId, HttpRetId httpStatus, String msg) {
//
//                    }
//
//                    @Override
//                    public void OnHttpReceived(int requestId, long nDataLen, long totalLength) {
//
//                    }
//                }).post(map, "toh", 1);
            }
        });
    }

    void initView() {

        convenientBanner = (ConvenientBanner) findViewById(R.id.convenientBanner);
        gv = (GridView) findViewById(R.id.main_gv);
        login = (ImageView) findViewById(R.id.img_tvright);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }

    public static final int REQUEST_CODE_PICK_IMAGE = 1;

    public void updateImg() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");//相片类型
            startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
        } else {
            Toast.makeText(MainActivity.this, "请确认已经插入SD卡", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_PICK_IMAGE:
                break;
            default:
                break;
        }
    }

}
