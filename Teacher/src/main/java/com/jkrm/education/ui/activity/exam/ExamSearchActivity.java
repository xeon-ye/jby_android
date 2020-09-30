package com.jkrm.education.ui.activity.exam;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzw.baselib.base.AwBaseActivity;
import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.jkrm.education.R;
import com.jkrm.education.adapter.exam.ExamSearchAdapter;
import com.jkrm.education.bean.exam.ClassBean;
import com.jkrm.education.bean.exam.ExamSearchBean;
import com.jkrm.education.bean.exam.GradeBean;
import com.jkrm.education.bean.exam.RoleBean;
import com.jkrm.education.mvp.presenters.AnalysisPresent;
import com.jkrm.education.mvp.views.AnalysisView;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.util.UserUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 搜索
 */
public class ExamSearchActivity extends AwMvpActivity<AnalysisPresent> implements AnalysisView.View {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_search)
    TextView mTvSearch;
    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    private ExamSearchAdapter mExamSearchAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_exam_search;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusTransparent();
        mExamSearchAdapter=new ExamSearchAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity,mRcvData,mExamSearchAdapter,false);
    }

    @Override
    protected AnalysisPresent createPresenter() {
        return new AnalysisPresent(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_back, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_search:
                String trim = mEtSearch.getText().toString().trim();
                mPresenter.getAnalysisList(RequestUtil.getAnalysisRequestBody("1",trim,"", UserUtil.getRoleld(),"100","",UserUtil.getAppUser().getTeacherId()));
                break;
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mExamSearchAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                toClass(CommonlyMultipleActivity.class,false);
            }
        });
    }

    @Override
    public void getAnalysisListSuccess(ExamSearchBean data) {
        mExamSearchAdapter.addAllData(data.getRows());
    }

    @Override
    public void getAnalysisListFail(String msg) {
        showMsg(msg);
    }

    @Override
    public void getGradeListSuccess(List<GradeBean> data) {

    }


    @Override
    public void getGradeListFail(String msg) {

    }

    @Override
    public void getClassListSuccess(List<ClassBean> data) {

    }


    @Override
    public void getClassListFail(String msg) {

    }

    @Override
    public void getRoleListSuccess(List<RoleBean> data) {

    }


    @Override
    public void getRoleListFail(String msg) {

    }
}
