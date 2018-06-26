package com.android.bjl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.bjl.R;
import com.android.bjl.bean.AdvertiseBean;
import com.android.bjl.bean.JobWantedBean;

import java.util.ArrayList;

/**
 * Created by john on 2018/4/20.
 */

public class MyJobWantedAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<JobWantedBean> beans;

    public MyJobWantedAdapter(Context context, ArrayList<JobWantedBean> beans) {
        this.context = context;
        this.beans = beans;
    }

    @Override
    public int getCount() {
        return beans.size();
    }

    @Override
    public JobWantedBean getItem(int position) {
        return beans.get(position);
    }

    public void setData(ArrayList<JobWantedBean> mList) {
        this.beans = mList;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.myrent_lv_item, parent, false);
            holder.img = (ImageView) convertView.findViewById(R.id.myrent_item_img);
            holder.phone = (TextView) convertView.findViewById(R.id.myrent_item_tv_phone);
            holder.rentAmount = (TextView) convertView.findViewById(R.id.myrent_item_tv_price);
            holder.title = (TextView) convertView.findViewById(R.id.myrent_item_tv_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        JobWantedBean bean = getItem(position);
        holder.phone.setText(bean.getCreateTime());
        holder.title.setText(bean.getTitle());
        holder.rentAmount.setText(bean.getPrice() + "元/月");
       holder.img.setVisibility(View.GONE);
        return convertView;
    }

    class ViewHolder {
        ImageView img;
        TextView title, rentAmount, phone;
    }
}
