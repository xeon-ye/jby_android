package com.jkrm.education.ui.fragment.exam;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hzw.baselib.base.AwMvpLazyFragment;
import com.jkrm.education.R;
import com.jkrm.education.mvp.presenters.AnalysisPresent;
import com.jkrm.education.mvp.views.AnalysisView;
import com.jkrm.education.ui.activity.exam.ExamSearchActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Description: 成绩分析
 * @Author: xiangqian
 * @CreateDate: 2020/8/26 10:28
 */

public class AnalysisFragment extends AwMvpLazyFragment<AnalysisPresent> implements AnalysisView.View {
    @BindView(R.id.iv_search)
    ImageView mIvSearch;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    Unbinder unbinder;

    @Override
    protected int getLayoutId() {
        return R.layout.analysis_layout;
    }


    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mIvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toClass(ExamSearchActivity.class,false);
            }
        });
    }

    @Override
    public void getAnalysisListSuccess(List<String> data) {

    }

    @Override
    public void getAnalysisListFail(String msg) {

    }

    @Override
    protected AnalysisPresent createPresenter() {
        return new AnalysisPresent(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
