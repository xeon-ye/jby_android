package com.jkrm.education.adapter.exam;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzw.baselib.project.student.bean.MarkBean;
import com.jkrm.education.R;

import java.util.ArrayList;
import java.util.List;

public class LevelGridViewAdapter extends BaseAdapter {
    List<MarkBean> markBeanList=new ArrayList<>();
    private Context context;

    public List<MarkBean> getMarkBeanList() {
        return markBeanList;
    }

    public void setMarkBeanList(List<MarkBean> markBeanList) {
        this.markBeanList = markBeanList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return markBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=View.inflate(context, R.layout.adapter__item_level_layout,null);
        TextView tv_title = convertView.findViewById(R.id.tv_title);
        ImageView iv_level_tri = convertView.findViewById(R.id.iv_level_tri);
        MarkBean item = markBeanList.get(position);
        tv_title.setText(item.getTitle());
        if(item.isSelect()){
            tv_title.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            iv_level_tri.setVisibility(View.VISIBLE);
        }else{
            tv_title.setTextColor(context.getResources().getColor(R.color.black));
            iv_level_tri.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }
}
