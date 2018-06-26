package com.android.bjl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.bjl.R;
import com.android.bjl.bean.IncommeAndExpenditureBean;
import com.android.bjl.bean.SubsidyBean;

import java.util.ArrayList;

public class MyIncomeAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<IncommeAndExpenditureBean> beans;

    public MyIncomeAdapter(Context context, ArrayList<IncommeAndExpenditureBean> beans) {
        this.context = context;
        this.beans = beans;
    }

    public MyIncomeAdapter(Context context) {
        this.context = context;
    }
    @Override
    public int getCount() {
        return beans.size();
    }

    @Override
    public IncommeAndExpenditureBean getItem(int i) {
        return beans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public void setData(ArrayList<IncommeAndExpenditureBean> mList) {
        this.beans = mList;
        notifyDataSetChanged();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.cwgk_lv_item, parent, false);
            holder.money = (TextView) convertView.findViewById(R.id.cwgk_item_tv4);
            holder.title = (TextView) convertView.findViewById(R.id.cwgk_item_tv1);
            holder.time = (TextView) convertView.findViewById(R.id.cwgk_item_tv3);
            holder.name = (TextView) convertView.findViewById(R.id.cwgk_item_tv2);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        IncommeAndExpenditureBean bean = getItem(position);
        holder.title.setText(bean.getProject());
        holder.money.setText(bean.getAmount());
        holder.name.setText(bean.getOperator());
        holder.time.setText(bean.getOperator_time());
        return convertView;
    }


    class ViewHolder {

        TextView title, money,time,name;
    }

}
