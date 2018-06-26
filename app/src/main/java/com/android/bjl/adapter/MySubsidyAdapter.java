package com.android.bjl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.bjl.R;
import com.android.bjl.bean.MyCapticalBean;
import com.android.bjl.bean.SubsidyBean;

import java.util.ArrayList;

public class MySubsidyAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<SubsidyBean> beans;

    public MySubsidyAdapter(Context context, ArrayList<SubsidyBean> beans) {
        this.context = context;
        this.beans = beans;
    }

    public MySubsidyAdapter(Context context) {
        this.context = context;
    }
    @Override
    public int getCount() {
        return beans.size();
    }

    @Override
    public SubsidyBean getItem(int i) {
        return beans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public void setData(ArrayList<SubsidyBean> mList) {
        this.beans = mList;
        notifyDataSetChanged();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.mycaptical_lv_item, parent, false);
            holder.money = (TextView) convertView.findViewById(R.id.mycapticel_item_tv2);
            holder.title = (TextView) convertView.findViewById(R.id.mycapticel_item_tv1);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        SubsidyBean bean = getItem(position);
        holder.title.setText(bean.getSubsidyTypeName());
        holder.money.setText(bean.getTotal());

        return convertView;
    }


    class ViewHolder {

        TextView title, money;
    }

}
