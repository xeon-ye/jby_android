package com.jkrm.education.adapter;

import android.annotation.SuppressLint;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzw.baselib.util.AwDateUtils;
import com.hzw.baselib.util.MyDateUtil;
import com.hzw.baselib.util.RandomValueUtil;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.TeacherTodoBean;
import com.jkrm.education.util.CustomFontStyleUtil;
import com.jkrm.education.widget.CustomProgressView;

import java.util.ArrayList;
import java.util.List;

/**
 * 待办事项
 * Created by hzw on 2019/5/9.
 */

public class TeacherTodoListAdapter extends BaseQuickAdapter<TeacherTodoBean, BaseViewHolder> {

    private List<TeacherTodoBean> mList = new ArrayList<>();

    public TeacherTodoListAdapter() {
        super(R.layout.adapter_todo_list);
    }

    @SuppressLint("ResourceType")
    @Override
    protected void convert(final BaseViewHolder helper, final TeacherTodoBean bean) {
        helper.setText(R.id.tv_title, bean.getClassName() + " " + bean.getName() + " 待批阅")
                .setTypeface(R.id.tv_title, CustomFontStyleUtil.getNewRomenType())
                .setText(R.id.tv_date, AwDateUtils.dealDateFormat(bean.getTime()))
                .setText(R.id.tv_num, helper.getLayoutPosition() + 1 + "");
//                .setBackgroundRes(R.id.tv_num, bean.getIdBg());
        TextView tv_title = helper.getView(R.id.tv_title);
        if(bean.isAllowOperate()) {
            tv_title.setTextColor(mContext.getResources().getColor(R.color.black));
        } else {
            tv_title.setTextColor(mContext.getResources().getColor(R.color.color_B9B9B9));
        }
        CustomProgressView customProgressView = helper.getView(R.id.customProgressView);
//        customProgressView.setData(RandomValueUtil.getNum(1, 30), RandomValueUtil.getNum(1, 20), RandomValueUtil.getNum(1, 10));
        //新增--批阅中--状态
        if(bean.isHandle()) {
            customProgressView.setData(MyDateUtil.stringToInt(bean.getProgress()), MyDateUtil.stringToInt(bean.getUnprogress()),
                    MyDateUtil.stringToInt(bean.getPopulation()) - MyDateUtil.stringToInt(bean.getSubmitted())

            );
        } else {
            customProgressView.setData(0, 0, 0);
        }
        //已批阅  未批阅  未提交
      /*  if(bean.isHandle()) {
            customProgressView.setData(MyDateUtil.stringToInt(bean.getProgress()), MyDateUtil.stringToInt(bean.getUnprogress()),
                    MyDateUtil.stringToInt(bean.getPopulation()) - MyDateUtil.stringToInt(bean.getSubmitted())

            );
        } else {
            customProgressView.setData(0, 0, 0);
        }*/
    }

    public void addAllData(List<TeacherTodoBean> dataList) {
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
