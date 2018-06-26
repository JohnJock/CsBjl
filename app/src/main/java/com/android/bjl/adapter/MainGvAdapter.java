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
import java.util.Objects;

/**
 * Created by john on 2018/3/5.
 */

public class MainGvAdapter extends BaseAdapter {
    private ArrayList<Map<String,Object>> arrayList;
    private Context context;
    private int width,height;
    public MainGvAdapter(Context context,ArrayList<Map<String, Object>> arrayList) {
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
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        int viewWidth = width/3;
        int viewHeight = height*3/(2*8);
        ViewHolder holder=null;
        if (convertView == null) {
            holder=new ViewHolder();
            convertView = View.inflate(context, R.layout.main_gv_item, null);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(viewWidth,viewHeight);
            convertView.setLayoutParams(params);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            ViewGroup.LayoutParams ps = holder.img.getLayoutParams();
            ps.width = viewWidth/2;
            ps.height = viewHeight/2;
            holder.img.setLayoutParams(ps);
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
