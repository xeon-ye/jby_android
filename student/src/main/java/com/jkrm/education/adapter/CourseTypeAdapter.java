package com.jkrm.education.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jkrm.education.bean.result.CourseTypeBean;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/3/13 10:47
 */

public class CourseTypeAdapter extends BaseAdapter {
    private Context mContext;
    private List<CourseTypeBean> list;

    public CourseTypeAdapter(Context context, List<CourseTypeBean> list) {
        mContext = context;
        this.list = list;
    }


    @Override
    public int getCount() {
        return null!=list&&list.size()>0?list.size():0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = View.inflate(mContext, android.R.layout.simple_list_item_1, null);
        TextView textView = view.findViewById(android.R.id.text1);
        textView.setMaxLines(1);
        textView.setTextSize(10);
        textView.setPadding(5,0,10,0);
        textView.setText(list.get(i).getName()+"");
        return view;
    }
}
