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
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.result.TeacherClassBean;
import com.jkrm.education.bean.rx.RxStatisticsClassesType;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.presenters.ClassesChoicePresent;
import com.jkrm.education.mvp.views.ClassesChoiceView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by hzw on 2019/6/3.
 */

public class ClassesChoiceActiviity extends AwMvpActivity<ClassesChoicePresent> implements ClassesChoiceView.View {

    @BindView(R.id.ll_layout)
    LinearLayout mLlLayout;
    @BindView(R.id.iv_selectAll)
    ImageView mIvSelectAll;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;

    private ClassesChoiceAdapter mAdapter;
    //已选择的列表项
    private List<TeacherClassBean> mSelectList = new ArrayList<>();
    //接口返回的所有列表项
    private List<TeacherClassBean> mTeacherClassBeanList = new ArrayList<>();
    private int prePageTag;//从哪个页面进入的, 作为回传判断

    @Override
    protected ClassesChoicePresent createPresenter() {
        return new ClassesChoicePresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_classes_choice;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusTxtDark();
        setToolbarWithBackImgAndRightView("班级", "确定", () -> {
            mSelectList = new ArrayList<>();
            if(mIvSelectAll.isSelected()) {
                mSelectList = mTeacherClassBeanList;
            } else {
                for(TeacherClassBean temp : mTeacherClassBeanList) {
                    if(temp.isSelect()) {
                        mSelectList.add(temp);
                    }
                }
            }
            if(AwDataUtil.isEmpty(mSelectList)) {
                showDialog("请至少选择一个班级");
                return;
            }
            EventBus.getDefault().postSticky(new RxStatisticsClassesType(mSelectList, prePageTag));
            finish();
        });
        mToolbar.setRTextColor(R.color.blue);
        mIvSelectAll.setSelected(false);
    }

    @Override
    protected void initData() {
        super.initData();
        prePageTag = getIntent().getIntExtra(Extras.COMMON_PARAMS, 0);
        mSelectList = (List<TeacherClassBean>) getIntent().getSerializableExtra(Extras.KEY_BEAN_TEACHER_CLASSES_LIST);
        if(AwDataUtil.isEmpty(mSelectList)) {
            AwLog.d("ClassesChoiceActiviity, mSelectList is null");
        } else {
            AwLog.d("ClassesChoiceActiviity, mSelectList size: " + mSelectList.size() + " ,prePageTag: " + prePageTag);
        }

        mAdapter = new ClassesChoiceAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, mAdapter, false);

        //TODO 不使用该接口了, 这是获取所有班级的, 需获取作业所有的班级
        mPresenter.getTeacherClassList(MyApp.getInstance().getAppUser().getTeacherId());
        mTeacherClassBeanList = MyApp.mTeacherClassHomeworkBeanList;
        if(AwDataUtil.isEmpty(mTeacherClassBeanList)) {
            getTeacherClassListFail("");
            return;
        }
        refreshView();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mLlLayout.setOnClickListener(v -> {
            if(AwDataUtil.isEmpty(mTeacherClassBeanList)) {
                return;
            }
            mIvSelectAll.setSelected(!mIvSelectAll.isSelected());
            for(TeacherClassBean temp : mTeacherClassBeanList) {
                temp.setSelect(mIvSelectAll.isSelected());
            }
            mAdapter.notifyDataSetChanged();
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TeacherClassBean bean = (TeacherClassBean) adapter.getItem(position);
                AwLog.d("班级选择onItemChildClick bean: " + bean.toString());
                for(TeacherClassBean temp : mTeacherClassBeanList) {
                    if(temp.getClassId().equals(bean.getClassId())) {
                        temp.setSelect(!temp.isSelect());
                    }
                }
                for(TeacherClassBean temp : mTeacherClassBeanList) {
                    if(!temp.isSelect()) {
                        mIvSelectAll.setSelected(false);
                        break;
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void refreshView() {
        if (AwDataUtil.isEmpty(mTeacherClassBeanList)) {
            AwLog.d("getTeacherClassListSuccess list is null");
            mAdapter.clearData();
            mRcvData.removeAllViews();
            mAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
            return;
        }
        AwLog.d("getTeacherClassListSuccess list size: " + mTeacherClassBeanList.size());
        if(!AwDataUtil.isEmpty(mSelectList)) {
            for(TeacherClassBean tempAll : mTeacherClassBeanList) {
                for(TeacherClassBean tempSelect : mSelectList) {
                    if(tempAll.getClassId().equals(tempSelect.getClassId())) {
                        tempAll.setSelect(true);
                    }
                }
            }
        }
        //初始化判断, 是否包含未选中的项
        boolean isSelectAll = true;
        for(TeacherClassBean temp : mTeacherClassBeanList) {
            if(!temp.isSelect()) {
                isSelectAll = false;
                break;
            }
        }
        mIvSelectAll.setSelected(isSelectAll);
        mAdapter.addAllData(mTeacherClassBeanList);
        mAdapter.loadMoreComplete();
        mAdapter.setEnableLoadMore(false);
        mAdapter.disableLoadMoreIfNotFullPage(mRcvData);
    }

    @Override
    public void getTeacherClassListSuccess(List<TeacherClassBean> list) {
        mTeacherClassBeanList = list;
        refreshView();
    }

    @Override
    public void getTeacherClassListFail(String msg) {
        mAdapter.clearData();
        mRcvData.removeAllViews();
        mAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
    }
}
