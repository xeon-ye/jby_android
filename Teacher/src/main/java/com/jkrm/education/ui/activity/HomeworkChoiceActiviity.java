package com.jkrm.education.ui.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.jkrm.education.R;
import com.jkrm.education.adapter.ClassesChoiceAdapter;
import com.jkrm.education.adapter.HomeworkChoiceAdapter;
import com.jkrm.education.api.APIService;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.result.AnswerSheetDataResultBean;
import com.jkrm.education.bean.result.RowsStatisticsHomeworkResultBean;
import com.jkrm.education.bean.result.TeacherClassBean;
import com.jkrm.education.bean.rx.RxStatisticsClassesType;
import com.jkrm.education.bean.rx.RxStatisticsCourseType;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.presenters.ClassesChoicePresent;
import com.jkrm.education.mvp.presenters.HomeworkChoicePresent;
import com.jkrm.education.mvp.views.ClassesChoiceView;
import com.jkrm.education.mvp.views.HomeworkChoiceView;
import com.jkrm.education.util.RequestUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by hzw on 2019/6/3.
 */

public class HomeworkChoiceActiviity extends AwMvpActivity<HomeworkChoicePresent> implements HomeworkChoiceView.View {

    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;

    //接口返回的所有列表项
    private List<RowsStatisticsHomeworkResultBean> mList = new ArrayList<>();
    //已选择项
    private RowsStatisticsHomeworkResultBean mSelectBean = null;
    private HomeworkChoiceAdapter mAdapter;

    private int prePageTag;//从哪个页面进入的, 作为回传判断

    @Override
    protected HomeworkChoicePresent createPresenter() {
        return new HomeworkChoicePresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_homework_choice;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusTxtDark();
        setToolbarWithBackImgAndRightView("课时列表", "确定", () -> {
            if(null == mSelectBean) {
                showDialog("请至少选择一个课时");
                return;
            }
            EventBus.getDefault().postSticky(new RxStatisticsCourseType(mSelectBean));
            finish();
        });
        mToolbar.setRTextColor(R.color.blue);
    }

    @Override
    protected void initData() {
        super.initData();
        prePageTag = getIntent().getIntExtra(Extras.COMMON_PARAMS, 0);
        mSelectBean = (RowsStatisticsHomeworkResultBean) getIntent().getSerializableExtra(Extras.KEY_BEAN_ROWS_HOMEWORK);

        mAdapter = new HomeworkChoiceAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, mAdapter, false);

        mPresenter.getStatisticsScoreHomeworkList(MyApp.getInstance().getAppUser().getTeacherId(), RequestUtil.getStatisticsScoreHomeworkList(1, APIService.COMMON_PAGE_SIZE));
    }

    @Override
    protected void initListener() {
        super.initListener();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            RowsStatisticsHomeworkResultBean bean = (RowsStatisticsHomeworkResultBean) adapter.getItem(position);
            if(bean.isSelect()) {
                return;
            }
            for(RowsStatisticsHomeworkResultBean temp : mList) {
                if(temp.getId().equals(bean.getId())) {
                    temp.setSelect(true);
                    mSelectBean = bean;
                } else {
                    temp.setSelect(false);
                }
            }
            mAdapter.notifyDataSetChanged();
        });
    }

    private void refreshView() {
        if (AwDataUtil.isEmpty(mList)) {
            mAdapter.clearData();
            mRcvData.removeAllViews();
            mAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
            return;
        }
        if(!AwDataUtil.isEmpty(mList) && null != mSelectBean) {
            for(RowsStatisticsHomeworkResultBean tempAll : mList) {
                if(tempAll.getId().equals(mSelectBean.getId())) {
                    tempAll.setSelect(true);
                } else {
                    tempAll.setSelect(false);
                }
            }
        }
        mAdapter.addAllData(mList);
        mAdapter.loadMoreComplete();
        mAdapter.setEnableLoadMore(false);
        mAdapter.disableLoadMoreIfNotFullPage(mRcvData);
    }

    @Override
    public void getStatisticsScoreHomeworkListSuccess(AnswerSheetDataResultBean data, List<RowsStatisticsHomeworkResultBean> list, int total, int size, int pages, int current) {
        mList = list;
        refreshView();
    }

    @Override
    public void getStatisticsScoreHomeworkListFail(String msg) {
        showMsg("获取教师所有班级作业列表失败");
        mAdapter.clearData();
        mRcvData.removeAllViews();
        mAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
    }
}
