package com.jkrm.education.adapter;

import android.annotation.SuppressLint;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkrm.education.R;
import com.jkrm.education.bean.todo.TodoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 待办事项
 * Created by hzw on 2019/5/9.
 */

public class TodoListAdapter extends BaseQuickAdapter<TodoBean, BaseViewHolder> {

    private List<TodoBean> mList = new ArrayList<>();

    public TodoListAdapter() {
        super(R.layout.adapter_todo_list);
    }

    @SuppressLint("ResourceType")
    @Override
    protected void convert(final BaseViewHolder helper, final TodoBean bean) {
        helper.setText(R.id.tv_title, bean.getTitle())
                .setText(R.id.tv_date, bean.getDate())
                .setText(R.id.tv_num, bean.getId() + "")
                .setBackgroundRes(R.id.tv_num, bean.getIdBg());
        TextView tv_title = helper.getView(R.id.tv_title);
        if(bean.isAllowOperate()) {
            tv_title.setTextColor(mContext.getResources().getColor(R.color.black));
        } else {
            tv_title.setTextColor(mContext.getResources().getColor(R.color.color_B9B9B9));
        }
    }

    public void addAllData(List<TodoBean> dataList) {
        this.mList = dataList;
        this.setNewData(mList);
        notifyDataSetChanged();
    }

    public void clearData() {
        if(mList != null) {
            int startPosition = 0;//hasHeader is 1
            int preSize = this.mList.size();
            if(preSize > 0) {
                this.mList.clear();
                notifyItemRangeRemoved(startPosition, preSize);
            }
        }
    }
}
