package com.android.bjl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.bjl.R;
import com.android.bjl.adapter.FirstLvAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 一级activity
 * Created by john on 2018/4/17.
 */

public class FirstActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private int type;
    private ArrayList<Map<String, Object>> dataList;
    private ListView lv;
    private FirstLvAdapter adapter;
    private String title;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        type = getIntent().getIntExtra("type", -1);
        title = getIntent().getStringExtra("title");
        initViews();
        initData(type);
    }

    private void initViews() {
        lv = (ListView) findViewById(R.id.first_lv);
        showTitleLeftTvAndImg(title, "返回", R.mipmap.back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        dataList = new ArrayList<Map<String, Object>>();
        adapter = new FirstLvAdapter(FirstActivity.this, dataList);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }

    private void initData(int type) {
        switch (type) {
            case 0:


                break;
            case 1:
                //图标
                int icno1[] = {R.mipmap.icon111, R.mipmap.icon111, R.mipmap.icon111,
                        R.mipmap.icon111};
                //图标下的文字
                String name1[] = {"四议两公开", "网络化管理", "公开公示",
                        "三重一大"};

                for (int i = 0; i < icno1.length; i++) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("img", icno1[i]);
                    map.put("text", name1[i]);
                    dataList.add(map);
                }
                adapter.notifyDataSetChanged();
                break;
            case 2:
                //图标
                int icno2[] = {R.mipmap.icon111, R.mipmap.icon111, R.mipmap.icon111,
                        R.mipmap.icon111, R.mipmap.icon111, R.mipmap.icon111};
                //图标下的文字
                String name2[] = {"党组织班子成员信息", "党员发展", "党内关怀",
                        "组织生活展示", "党费收缴与使用", "党员风采"};

                for (int i = 0; i < icno2.length; i++) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("img", icno2[i]);
                    map.put("text", name2[i]);
                    dataList.add(map);
                }
                adapter.notifyDataSetChanged();
                break;
            case 3:
                break;
            case 4:

                break;
            case 5:
                break;
            case 6:

                //图标
                int icno4[] = {R.mipmap.icon111, R.mipmap.icon111, R.mipmap.icon111};
                //图标下的文字
                String name4[] = {"房屋出租", "招聘", "个人求职"};

                for (int i = 0; i < icno4.length; i++) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("img", icno4[i]);
                    map.put("text", name4[i]);
                    dataList.add(map);
                }
                adapter.notifyDataSetChanged();
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
                break;
            case 11:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent();

        switch (type) {
            case 0:


                break;
            case 1:
                intent.setClass(FirstActivity.this,DwgkListActivity.class);
                intent.putExtra("type",1);
                intent.putExtra("position",position);
                intent.putExtra("title",(String) adapter.getItem(position).get("text"));
                break;
            case 2:
                intent.setClass(FirstActivity.this,DwgkListActivity.class);
                intent.putExtra("type",0);
                intent.putExtra("position",position);
                intent.putExtra("title",(String) adapter.getItem(position).get("text"));
                break;
            case 3:
                intent.setClass(FirstActivity.this,DwgkListActivity.class);
                intent.putExtra("type",2);
                intent.putExtra("position",position);
                intent.putExtra("title",(String) adapter.getItem(position).get("text"));
                break;
            case 4:

                break;
            case 5:
                break;
            case 6:
                intent.setClass(FirstActivity.this,MyRentListActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("position",position);
                intent.putExtra("title",(String) adapter.getItem(position).get("text"));
                break;
            case 7:
                break;

        }
        startActivity(intent);
    }

}
