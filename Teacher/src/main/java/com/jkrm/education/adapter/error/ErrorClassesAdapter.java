package com.jkrm.education.adapter.error;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.error.ErrorClassesBean;
import com.jkrm.education.bean.result.error.ErrorCourseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/7/23 11:28
 */

public class ErrorClassesAdapter extends BaseQuickAdapter<ErrorClassesBean, BaseViewHolder> {
    private List<ErrorClassesBean> mList = new ArrayList<>();

    public ErrorClassesAdapter() {
        super(R.layout.item_error_question_classes_layout);
    }

    public void addAllData(List<ErrorClassesBean> dataList) {
        this.mList = dataList;
        this.setNewData(mList);
        notifyDataSetChanged();
    }

    public void clearData() {
        if (mList != null) {
            int startPosition = 0;//hasHeader is 1
            int preSize = this.mList.size();
            if (preSize > 0) {
                this.mList.clear();
                notifyItemRangeRemoved(startPosition, preSize);
            }
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, ErrorClassesBean item) {
        helper.setText(R.id.checkbox,item.getName()).addOnClickListener(R.id.checkbox);
        CheckBox checkbox = helper.getView(R.id.checkbox);
        checkbox.setChecked(item.isChecked());
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                item.setChecked(b);
            }
        });
    }
}
