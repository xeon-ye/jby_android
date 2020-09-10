package com.jkrm.education.ui.activity.exam;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzw.baselib.base.AwBaseActivity;
import com.hzw.baselib.base.AwMvpActivity;
import com.jkrm.education.R;
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_exam_search;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusTransparent();
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
                mPresenter.getAnalysisList(RequestUtil.getAnalysisRequestBody("1",trim,UserUtil.getAppUser().getGradeName(), UserUtil.getRoleld(),"100","",UserUtil.getAppUser().getTeacherId()));
                break;
        }
    }

    @Override
    public void getAnalysisListSuccess(List<String> data) {
        showMsg(data.toString());
    }

    @Override
    public void getAnalysisListFail(String msg) {
        showMsg(msg);
    }
}
