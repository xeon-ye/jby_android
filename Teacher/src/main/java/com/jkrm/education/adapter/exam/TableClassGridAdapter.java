package com.jkrm.education.adapter.exam;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jkrm.education.R;
import com.jkrm.education.bean.exam.ClassBean;

import java.util.List;

/**
 * @Author: Zhoujing
 * Createdate: 2020/9/15 18:14
 * Description:
 */
public class TableClassGridAdapter extends BaseAdapter {

    private List<ClassBean> data;
    private Context mContext;

    public TableClassGridAdapter(Context context,List<ClassBean> data) {
        this.data = data;
        this.mContext = context;
    }

    public void setData(List<ClassBean> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    @SuppressLint("ViewHolder")
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_table_grade_item_layout, null);
        TextView text = view.findViewById(R.id.tv_title);
        text.setText(data.get(position).getClassName());
//        ContextCompat.getColor(mContext, R.color.blue);
        return view;
    }

}
