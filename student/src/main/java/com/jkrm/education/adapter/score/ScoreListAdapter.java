package com.jkrm.education.adapter.score;

import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwDateUtils;
import com.hzw.baselib.util.AwEffectiveRequestViewUtil;
import com.hzw.baselib.util.MyDateUtil;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.RowsHomeworkBean;
import com.jkrm.education.util.CustomFontStyleUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hzw on 2019/5/9.
 */

public class ScoreListAdapter extends BaseQuickAdapter<RowsHomeworkBean, BaseViewHolder> {

    private List<RowsHomeworkBean> mList = new ArrayList<>();

    public ScoreListAdapter() {
        super(R.layout.adapter_mark_main);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final RowsHomeworkBean bean) {
        helper.setText(R.id.tv_classes,  bean.getCourseName() + "  " + bean.getHomeworkName())
                .setText(R.id.tv_num, helper.getLayoutPosition() + 1 + "")
                .setTypeface(R.id.tv_classes, CustomFontStyleUtil.getNewRomenType())
                .setText(R.id.tv_date, AwDataUtil.isEmpty(bean.getCreateTime()) ? "未知时间" : AwDateUtils.formatDate16.format(new Date(Long.valueOf(bean.getCreateTime()))));
//                .setText(R.id.tv_markingProgress, bean.getProgress())
                //2020.04.07 去掉学生端查看教师批阅进度
               // .setText(R.id.tv_markingProgress, "已批阅： " + MyDateUtil.getMarkRote(bean.getProgress()));
        if(bean.isHandle()) {
            helper.setText(R.id.tv_score, MyDateUtil.replace(bean.getScore()) + "分");
        } else {
            Button tv_score = helper.getView(R.id.tv_score);
            tv_score.setText("处理中");
            AwEffectiveRequestViewUtil.setButtonEnableChangeTxtColor(mContext, tv_score, false);
        }
        TextView tv_classes = helper.getView(R.id.tv_classes);
        tv_classes.setMovementMethod(ScrollingMovementMethod.getInstance());
        tv_classes.setHorizontallyScrolling(true);
    }

    public void addAllData(List<RowsHomeworkBean> dataList) {
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
