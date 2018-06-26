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
import java.util.Map;

/**
 * 便民服务
 * Created by john on 2018/4/17.
 */

public class ServiceActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private int type;
    private ArrayList<Map<String, Object>> dataList;
    private ListView lv;
    private FirstLvAdapter adapter;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        type = getIntent().getIntExtra("type",0);

        initViews();
        initData();
    }

    private void initViews() {
        lv = (ListView) findViewById(R.id.first_lv);
        showTitleLeftTvAndImg("便民服务", "返回", R.mipmap.back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        dataList = new ArrayList<Map<String, Object>>();
        adapter = new FirstLvAdapter(ServiceActivity.this, dataList);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }

    private void initData() {
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
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent();
        if (position==0){
            intent.setClass(ServiceActivity.this,MyRentListActivity.class);
        }else  if(position==1){
            intent.setClass(ServiceActivity.this,AdvertiseListActivity.class);

        }else if (position==2){
            intent.setClass(ServiceActivity.this,MyJobWantedListActivity.class);

        }

        intent.putExtra("type",type);
        intent.putExtra("position",position);
        intent.putExtra("title",(String) adapter.getItem(position).get("text"));
        startActivity(intent);
    }

}
