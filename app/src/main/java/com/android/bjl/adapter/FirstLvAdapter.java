package com.android.bjl.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.bjl.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by john on 2018/3/5.
 */

public class FirstLvAdapter extends BaseAdapter {
    private ArrayList<Map<String,Object>> arrayList;
    private Context context;
    private int width,height;
    public FirstLvAdapter(Context context, ArrayList<Map<String, Object>> arrayList) {
        this.arrayList = arrayList;
        this.context=context;
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
         width = wm.getDefaultDisplay().getWidth();
         height = wm.getDefaultDisplay().getHeight();
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Map<String, Object> getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder=null;
        if (convertView == null) {
            holder=new ViewHolder();
            convertView = View.inflate(context, R.layout.first_lv_item, null);
            holder.img = (ImageView) convertView.findViewById(R.id.img);

            holder.tv=(TextView)convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.img.setImageResource((Integer) (arrayList.get(position).get("img")));
        holder.tv.setText(arrayList.get(position).get("text").toString());
        return convertView;
    }
    class ViewHolder{
        ImageView img;
        TextView tv;
    }
}
