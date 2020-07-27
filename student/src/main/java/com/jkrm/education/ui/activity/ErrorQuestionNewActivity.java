package com.jkrm.education.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.jkrm.education.R;
import com.jkrm.education.adapter.ErrorQuestionNewAdapter;
import com.jkrm.education.bean.ErrorQuestionBean;
import com.jkrm.education.bean.rx.RxSituationType;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.presenters.ErrorQuestionNewPresent;
import com.jkrm.education.mvp.views.ErrorQuestionNewView;
import com.jkrm.education.util.UserUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ErrorQuestionNewActivity extends AwMvpActivity<ErrorQuestionNewPresent> implements ErrorQuestionNewView.View {

    @BindView(R.id.toolbar_custom)
    AwViewCustomToolbar mToolbarCustom;
    @BindView(R.id.ll_title)
    LinearLayout mLlTitle;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    private ErrorQuestionNewAdapter mAdapter;

    @Override

    protected int getLayoutId() {
        return R.layout.activity_error_question_new;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusTransparent();
        setToolbar("错题本", null);
        mAdapter = new ErrorQuestionNewAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, mAdapter, false);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getErrorQuestionList(UserUtil.getAppUser().getStudId());
    }

    @Override
    protected void initListener() {
        super.initListener();
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<ErrorQuestionBean> data = adapter.getData();
                toClass(ErrorQuestionActivity.class,false, Extras.COURSE_ID,data.get(position).getCourseId());
            }
        });
    }

    @Override
    public void getErrorQuestionSuccess(List<ErrorQuestionBean> list) {
        if(AwDataUtil.isEmpty(list)){
            mAdapter.clearData();
            mRcvData.removeAllViews();
            mAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
        }else{
            //自己 移除空数据
            ArrayList<Object> emptyList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if("0".equals(list.get(i).getErrorNum())){
                    emptyList.add(list.get(i));
                }
            }
            list.removeAll(emptyList);
            mAdapter.addAllData(list);
            mAdapter.loadMoreComplete();
        }
    }

    @Override
    public void getErrorQuestionFail(String msg) {
        mAdapter.clearData();
        mRcvData.removeAllViews();
        mAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
    }

    @Override
    protected ErrorQuestionNewPresent createPresenter() {
        return new ErrorQuestionNewPresent(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


}
