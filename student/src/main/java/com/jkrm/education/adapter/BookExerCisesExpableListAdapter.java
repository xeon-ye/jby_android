package com.jkrm.education.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.jkrm.education.R;
import com.jkrm.education.bean.result.ClassHourBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/2/23 20:26
 */

public class BookExerCisesExpableListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private  LayoutInflater mInflater;

    private List<ClassHourBean.ChildrenBeanX> mGroup;
    private ArrayList<ArrayList<ClassHourBean.ChildrenBeanX.ChildrenBean>> mItemList;


    public BookExerCisesExpableListAdapter(Context context, List<ClassHourBean.ChildrenBeanX> group, ArrayList<ArrayList<ClassHourBean.ChildrenBeanX.ChildrenBean>> itemList) {
        this.mContext = context;
        this.mGroup = group;
        this.mItemList = itemList;
        mInflater = LayoutInflater.from(context);
    }

    //父项的个数
    @Override
    public int getGroupCount() {
        return mGroup.size();
    }

    //某个父项的子项的个数
    @Override
    public int getChildrenCount(int groupPosition) {
        return mItemList.get(groupPosition).size();
    }

    //获得某个父项
    @Override
    public Object getGroup(int groupPosition) {
        return mGroup.get(groupPosition);
    }

    //获得某个子项
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mItemList.get(groupPosition).get(childPosition);
    }

    //父项的Id
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //子项的id

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    //获取父项的view
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.bookexercises_elv_group_item_layout, parent, false);
        }
        ClassHourBean.ChildrenBeanX childrenBeanX = mGroup.get(groupPosition);
        TextView tvGroup = (TextView) convertView.findViewById(R.id.tv_title);
        tvGroup.setText(childrenBeanX.getTitle());
        return convertView;
    }

    //获取子项的view
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ClassHourBean.ChildrenBeanX.ChildrenBean childrenBean = mItemList.get(groupPosition).get(childPosition);
        final String child = childrenBean.getTitle();
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.bookexercises_elv_item_layout, parent, false);
        }
        TextView tvChild = (TextView) convertView.findViewById(R.id.tv_title);
        if (childrenBean.isChecked()) {
            tvChild.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        } else {
            tvChild.setTextColor(Color.BLACK);
        }
        tvChild.setText(child);
        return convertView;

    }

    //初始化子条目全部为未选中
    public void initFlagSelected() {
        if(null!=mItemList&&mItemList.size()>0){
            for (int t = 0; t < mItemList.size(); t++) {
                for (int k = 0; k < mItemList.get(t).size(); k++) {
                    mItemList.get(t).get(k).setChecked(false);
                }

            }
        }

    }

    public void addData(List<ClassHourBean.ChildrenBeanX> group, ArrayList<ArrayList<ClassHourBean.ChildrenBeanX.ChildrenBean>> itemList){
        this.mGroup=group;
        this.mItemList=itemList;
        notifyDataSetChanged();
    }

    //子项是否可选中,如果要设置子项的点击事件,需要返回true
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
