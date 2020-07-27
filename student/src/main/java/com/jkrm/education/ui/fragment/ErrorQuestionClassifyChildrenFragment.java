package com.jkrm.education.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzw.baselib.base.AwMvpLazyFragment;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwDateUtils;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.jkrm.education.R;
import com.jkrm.education.adapter.ErrorQuestionClassifyAdapter;
import com.jkrm.education.bean.ErrorQuestionClassifyBean;
import com.jkrm.education.bean.ErrorQuestionDetailBean;
import com.jkrm.education.bean.ErrorQuestionTimeBean;
import com.jkrm.education.bean.ErrorQuestionTimePagedBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.presenters.ErrorQuestionFragmentPresent;
import com.jkrm.education.mvp.views.ErrorQuestionFragmentView;
import com.jkrm.education.ui.activity.ErrorQuestionActivity;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.util.UserUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/6/3 16:15
 */

public class ErrorQuestionClassifyChildrenFragment extends AwMvpLazyFragment<ErrorQuestionFragmentPresent> implements ErrorQuestionFragmentView.View {

    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    Unbinder unbinder;
    private String mClassify = "1";
    private String mCourseID = "";
    private ErrorQuestionClassifyAdapter mErrorQuestionClassifyAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.error_question_classify_children_fragment_layout;
    }

    @Override
    protected void initView() {
        super.initView();
        mClassify = getArguments().getString(Extras.KEY_CLASSIFY);
        ErrorQuestionActivity activity = (ErrorQuestionActivity) getActivity();
        mCourseID = activity.getCourseId();
        mErrorQuestionClassifyAdapter = new ErrorQuestionClassifyAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(getActivity(),mRcvData,mErrorQuestionClassifyAdapter,false);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getByClassify(RequestUtil.getByClassifyBody(UserUtil.getAppUser().getStudId(), mCourseID, mClassify));
    }

    @Override
    protected ErrorQuestionFragmentPresent createPresenter() {
        return new ErrorQuestionFragmentPresent(this);
    }


    @Override
    public void getByClassifySuccess(List<ErrorQuestionClassifyBean> list) {
        if(AwDataUtil.isEmpty(list)){
            mErrorQuestionClassifyAdapter.clearData();
            mRcvData.removeAllViews();
            mErrorQuestionClassifyAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
            return;
        }
        ArrayList<Object> emptyList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if(AwDataUtil.isEmpty(list.get(i).getReaList())){
                emptyList.add(list.get(i));
            }
        }
        list.removeAll(emptyList);
        mErrorQuestionClassifyAdapter.addAllData(list);
        mErrorQuestionClassifyAdapter.notifyDataSetChanged();
    }

    @Override
    public void getByClassifyFail(String msg) {
        showMsg(msg);
        mErrorQuestionClassifyAdapter.clearData();
        mRcvData.removeAllViews();
        mErrorQuestionClassifyAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));

    }

    @Override
    public void getByTimeSuccess(List<ErrorQuestionTimeBean> list) {

    }

    @Override
    public void getByTimeSFail(String msg) {

    }

    @Override
    public void getErrorDetailSuccess(List<ErrorQuestionDetailBean> list) {

    }

    @Override
    public void getErrorDetailFail(String msg) {

    }

    @Override
    public void getByTimePagedSuccess(ErrorQuestionTimePagedBean bean) {

    }

    @Override
    public void getByTimePagedFail(String msg) {

    }


}