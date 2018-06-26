package com.android.bjl.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.bjl.R;
import com.android.bjl.util.CommonUtil;

import java.util.ArrayList;
import java.util.Map;


/**
 * Created by john on 2018/3/5.
 */

public class FirstFragmentGvAdapter extends BaseAdapter {
    private ArrayList<Map<String, Object>> arrayList;
    private Context context;

    public FirstFragmentGvAdapter(Context context, ArrayList<Map<String, Object>> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return 12;
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.main_gv_item, null);
//指定Item的宽高
            DisplayMetrics dm = new DisplayMetrics();
            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
            int heightPixels = dm.widthPixels;//宽度
            convertView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (heightPixels- CommonUtil.dip2px(context,30)) / 3));
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.tv = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position >= 0 && position <= 10) {
            holder.img.setImageResource((Integer) (arrayList.get(position).get("img")));
            holder.tv.setText(arrayList.get(position).get("text").toString());
        }
        return convertView;
    }

    class ViewHolder {
        ImageView img;
        TextView tv;
    }
}
