package com.jkrm.education.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzw.baselib.base.AwMvpFragment;
import com.hzw.baselib.base.AwMvpLazyFragment;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.jkrm.education.R;
import com.jkrm.education.adapter.ErrorQuestionAdapter;
import com.jkrm.education.adapter.ErrorQuestionNewAdapter;
import com.jkrm.education.bean.ErrorQuestionBean;
import com.jkrm.education.bean.rx.RxSituationType;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.presenters.ErrorQuestionNewPresent;
import com.jkrm.education.mvp.views.ErrorQuestionNewView;
import com.jkrm.education.ui.activity.ErrorQuestionActivity;
import com.jkrm.education.util.UserUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/6/3 10:49
 */

public class ErrorQuestionNewFragment extends AwMvpFragment<ErrorQuestionNewPresent> implements ErrorQuestionNewView.View {
    @BindView(R.id.toolbar_custom)
    AwViewCustomToolbar mToolbarCustom;
    @BindView(R.id.ll_title)
    LinearLayout mLlTitle;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    Unbinder unbinder;
    private ErrorQuestionNewAdapter mAdapter;

    @Override
    protected ErrorQuestionNewPresent createPresenter() {
        return new ErrorQuestionNewPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.error_question_new_layout;
    }

    @Override
    protected void initView() {
        super.initView();
        setToolbar("错题本", null);
        EventBus.getDefault().register(this);
        mToolbar.hideLeftView();
        mToolbar.hideLeft2ImgView();
        mToolbar.setToolbarTitleColor(R.color.white);
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
        showMsg(msg);
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
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RxSituationType rxSituationType){
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }
}
