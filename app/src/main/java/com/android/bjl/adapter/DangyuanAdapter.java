package com.android.bjl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.bjl.R;
import com.android.bjl.bean.DangyuanInfoBean;


import java.util.ArrayList;

public class DangyuanAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<DangyuanInfoBean> beans;

    public DangyuanAdapter(Context context, ArrayList<DangyuanInfoBean> beans) {
        this.context = context;
        this.beans = beans;
    }

    public DangyuanAdapter(Context context) {
        this.context = context;
    }
    @Override
    public int getCount() {
        return beans.size();
    }

    @Override
    public DangyuanInfoBean getItem(int i) {
        return beans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public void setData(ArrayList<DangyuanInfoBean> mList) {
        this.beans = mList;
        notifyDataSetChanged();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.dangyuan_lv_item, parent, false);
            holder.date = (TextView) convertView.findViewById(R.id.myrent_item_tv_date);
            holder.title = (TextView) convertView.findViewById(R.id.dangyuan_item_tv_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        DangyuanInfoBean bean = getItem(position);
        holder.title.setText(bean.getTitle());
        holder.date.setText(bean.getCreateTime());

        return convertView;
    }


    class ViewHolder {

        TextView title, date;
    }

}
