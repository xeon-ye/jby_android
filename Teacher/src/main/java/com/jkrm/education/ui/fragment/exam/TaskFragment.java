package com.jkrm.education.ui.fragment.exam;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzw.baselib.base.AwMvpLazyFragment;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.jkrm.education.R;
import com.jkrm.education.adapter.TaskBeanAdapter;
import com.jkrm.education.bean.TaskBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.TaskPresent;
import com.jkrm.education.mvp.views.TaskView;
import com.jkrm.education.ui.activity.exam.ExamTaskActivity;
import com.jkrm.education.util.UserUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Description: 阅卷任务
 * @Author: xiangqian
 * @CreateDate: 2020/8/26 10:27
 */

public class TaskFragment extends AwMvpLazyFragment<TaskPresent> implements TaskView.View {


    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    private TaskBeanAdapter mTaskBeanAdapter;
    private List<TaskBean.Bean> mBeanList;

    @Override
    protected int getLayoutId() {
        return R.layout.task_layout;
    }


    @Override
    protected void initView() {
        super.initView();
        mTaskBeanAdapter=new TaskBeanAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(getActivity(),mRcvData,mTaskBeanAdapter,false);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getExamList(UserUtil.getTeacherId());
    }

    @Override
    protected void initListener() {
        super.initListener();
        mTaskBeanAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                TaskBean.Bean bean = mBeanList.get(position);
                switch (view.getId()){
                    case R.id.tv_step:
                        if("1".equals(bean.getIsRead())){
                            showMsg("开始阅卷");
                            toClass(ExamTaskActivity.class,false, Extras.EXAM_ID,bean.getExamId(),Extras.PAPER_ID,bean.getPaperManageId());
                        }else if("2".equals(bean.getIsRead())){
                            showMsg("暂停阅卷");
                        }
                        else{
                            toClass(ExamTaskActivity.class,false, Extras.EXAM_ID,bean.getExamId(),Extras.PAPER_ID,bean.getPaperManageId());
                        }
                       /* else if("3".equals(bean.getIsRead())){
                            showMsg("批阅完成");
                            toClass(ExamTaskActivity.class,false, Extras.EXAM_ID,bean.getExamId(),Extras.PAPER_ID,bean.getPaperManageId());
                        }else if("4".equals(bean.getIsRead())){

                        }*/
                        break;
                }
            }
        });
    }

    @Override
    public void getExamListSuccess(TaskBean bean) {
        mBeanList = bean.getList();
        mTaskBeanAdapter.addAllData(mBeanList);
    }

    @Override
    public void getExamListFail(String msg) {
        showMsg(msg);
    }

    @Override
    protected TaskPresent createPresenter() {
        return new TaskPresent(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        initData();
    }
}
