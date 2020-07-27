package com.jkrm.education.old;

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
import com.jkrm.education.adapter.ClassesStudentChoiceAdapter;
import com.jkrm.education.bean.result.ClassStudentBean;
import com.jkrm.education.bean.rx.RxStatisticsStudentType;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.presenters.ClassesStudentChoicePresent;
import com.jkrm.education.mvp.views.ClassesStudentChoiceView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by hzw on 2019/6/3.
 */

public class ClassesStudentChoiceActivity extends AwMvpActivity<ClassesStudentChoicePresent> implements ClassesStudentChoiceView.View {

    @BindView(R.id.ll_layout)
    LinearLayout mLlLayout;
    @BindView(R.id.iv_selectAll)
    ImageView mIvSelectAll;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;

    private ClassesStudentChoiceAdapter mAdapter;
    //已选择的列表项
    private List<ClassStudentBean> mSelectList = new ArrayList<>();
    //接口返回的所有列表项
    private List<ClassStudentBean> mClassStudentBeanList = new ArrayList<>();
    private int prePageTag;//从哪个页面进入的, 作为回传判断
    private String classesId = "";

    @Override
    protected ClassesStudentChoicePresent createPresenter() {
        return new ClassesStudentChoicePresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_person_choice;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusTxtDark();
        setToolbarWithBackImgAndRightView("人员", "确定", () -> {
            mSelectList = new ArrayList<>();
            if(mIvSelectAll.isSelected()) {
                mSelectList = mClassStudentBeanList;
            } else {
                for(ClassStudentBean temp : mClassStudentBeanList) {
                    if(temp.isSelect()) {
                        mSelectList.add(temp);
                    }
                }
            }
            if(AwDataUtil.isEmpty(mSelectList)) {
                showDialog("请至少选择一个学生");
                return;
            }
            EventBus.getDefault().postSticky(new RxStatisticsStudentType(mSelectList, prePageTag));
            finish();
        });
        mToolbar.setRTextColor(R.color.blue);
        mIvSelectAll.setSelected(false);
    }

    @Override
    protected void initData() {
        super.initData();
        classesId = getIntent().getStringExtra(Extras.COMMON_PARAMS2);
        if(AwDataUtil.isEmpty(classesId)) {
            showDialogToFinish("获取班级id失败，无法获取学生列表");
            return;
        }
        prePageTag = getIntent().getIntExtra(Extras.COMMON_PARAMS, 0);
        mSelectList = (List<ClassStudentBean>) getIntent().getSerializableExtra(Extras.KEY_BEAN_CLASSES_STUDENT_LIST);
        if(AwDataUtil.isEmpty(mSelectList)) {
            AwLog.d("ClassesStudentChoiceActivity, mSelectList is null");
        } else {
            AwLog.d("ClassesStudentChoiceActivity, mSelectList size: " + mSelectList.size() + " ,prePageTag: " + prePageTag);
        }

        mAdapter = new ClassesStudentChoiceAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, mAdapter, false);

        mPresenter.getClassesStudentList(classesId);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mLlLayout.setOnClickListener(v -> {
            if(AwDataUtil.isEmpty(mClassStudentBeanList)) {
                return;
            }
            mIvSelectAll.setSelected(!mIvSelectAll.isSelected());
            for(ClassStudentBean temp : mClassStudentBeanList) {
                temp.setSelect(mIvSelectAll.isSelected());
            }
            mAdapter.notifyDataSetChanged();
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ClassStudentBean bean = (ClassStudentBean) adapter.getItem(position);
                AwLog.d("班级选择onItemChildClick bean: " + bean.toString());
                for(ClassStudentBean temp : mClassStudentBeanList) {
                    if(temp.getStudCode().equals(bean.getStudCode())) {
                        temp.setSelect(!temp.isSelect());
                    }
                }
                for(ClassStudentBean temp : mClassStudentBeanList) {
                    if(!temp.isSelect()) {
                        mIvSelectAll.setSelected(false);
                        break;
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void getClassesStudentListSuccess(List<ClassStudentBean> list) {
        mClassStudentBeanList = list;
        if (AwDataUtil.isEmpty(list)) {
            AwLog.d("getTeacherClassListSuccess list is null");
            mAdapter.clearData();
            mRcvData.removeAllViews();
            mAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
            return;
        }
        AwLog.d("getTeacherClassListSuccess list size: " + list.size());
        if(!AwDataUtil.isEmpty(mSelectList)) {
            for(ClassStudentBean tempAll : mClassStudentBeanList) {
                for(ClassStudentBean tempSelect : mSelectList) {
                    if(tempAll.getStudCode().equals(tempSelect.getStudCode())) {
                        tempAll.setSelect(true);
                    }
                }
            }
        }
        //初始化判断, 是否包含未选中的项
        boolean isSelectAll = true;
        for(ClassStudentBean temp : mClassStudentBeanList) {
            if(!temp.isSelect()) {
                isSelectAll = false;
                break;
            }
        }
        mIvSelectAll.setSelected(isSelectAll);
        mAdapter.addAllData(list);
        mAdapter.loadMoreComplete();
        mAdapter.setEnableLoadMore(false);
        mAdapter.disableLoadMoreIfNotFullPage(mRcvData);
    }

    @Override
    public void getClassesStudentListFail(String msg) {
        showMsg("获取学生列表失败");
        mAdapter.clearData();
        mRcvData.removeAllViews();
        mAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
    }
}
