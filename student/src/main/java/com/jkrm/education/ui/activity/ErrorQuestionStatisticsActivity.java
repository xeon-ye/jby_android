package com.jkrm.education.ui.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.jkrm.education.R;
import com.jkrm.education.adapter.ErrorQuestionStatisticsAdapter;
import com.jkrm.education.bean.ErrorQuestionStatisticsBean;
import com.jkrm.education.bean.result.ErrorQuestionResultBean;
import com.jkrm.education.bean.rx.RxRefreshErrorQuestionListType;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.presenters.ErrorQuestionStatisticsPresent;
import com.jkrm.education.mvp.views.ErrorQuestionStatisticsView;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.util.TestDataUtil;
import com.jkrm.education.util.UserUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by hzw on 2019/9/29.
 */

public class ErrorQuestionStatisticsActivity extends AwMvpActivity<ErrorQuestionStatisticsPresent> implements ErrorQuestionStatisticsView.View {

    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    private ErrorQuestionStatisticsAdapter mAdapter;
    private String templateId = "";

    private List<ErrorQuestionResultBean> mList = new ArrayList<>();
    private ErrorQuestionResultBean currentBean;
    private boolean isChange = false;

    @Override
    protected ErrorQuestionStatisticsPresent createPresenter() {
        return new ErrorQuestionStatisticsPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_error_question_statistics;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusTxtDark();
        setToolbarWithBackImg("错题统计", null);
    }

    @Override
    protected void initData() {
        super.initData();
        templateId = getIntent().getStringExtra(Extras.KEY_TEMPLATE_ID);
        mPresenter.getErrorQuestionStatisticsList(templateId, UserUtil.getStudId());

        mAdapter = new ErrorQuestionStatisticsAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, mAdapter, true);
        mRcvData.setFocusable(false);
//        mAdapter.addAllData(TestDataUtil.createErrorQuestionStatisticsBeanList());
    }

    @Override
    protected void initListener() {
        super.initListener();
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ErrorQuestionResultBean bean = mAdapter.getItem(i);
                toClass(SeeTargetQuestionActivity.class, false, Extras.COMMON_PARAMS, bean.getQuestionId());
            }
        });
        mAdapter.setOnItemChildClickListener((baseQuickAdapter, view, position) -> {
            currentBean = mAdapter.getItem(position);
            if(view.getId() == R.id.iv_switch) {
                if(currentBean.isSelect()) {
                    mPresenter.delErrorQuestion(currentBean.getQuestionId(), UserUtil.getStudId());
                } else {
                    mPresenter.addErrorQuestion(RequestUtil.addQuestionErrorRequest(currentBean));
                }
            }
        });
    }

    @Override
    public void getErrorQuestionStatisticsListSuccess(List<ErrorQuestionResultBean> result) {
        if(AwDataUtil.isEmpty(result)) {
            mAdapter.clearData();
            mRcvData.removeAllViews();
            mAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
            return;
        }
        mList = result;
        mAdapter.addAllData(mList);
    }

    @Override
    public void getErrorQuestionStatisticsListFail(String msg) {
        mAdapter.clearData();
        mRcvData.removeAllViews();
        mAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
        showDialogToFinish(msg);
    }

    @Override
    public void addErrorQuestionSuccess() {
        showMsg("加入错题本成功");
        currentBean.setSelect(true);
        mAdapter.notifyDataSetChanged();
        isChange = true;
    }

    @Override
    public void delErrorQuestionSuccess() {
        showMsg("移出错题本成功");
        currentBean.setSelect(false);
        mAdapter.notifyDataSetChanged();
        isChange = true;
    }

    @Override
    protected void onDestroy() {
        if(isChange) {
            EventBus.getDefault().postSticky(new RxRefreshErrorQuestionListType());
        }
        super.onDestroy();
    }
}
