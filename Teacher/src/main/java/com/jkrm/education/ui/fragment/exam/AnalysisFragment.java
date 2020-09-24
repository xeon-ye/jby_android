package com.jkrm.education.ui.fragment.exam;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzw.baselib.base.AwMvpLazyFragment;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.jkrm.education.R;
import com.jkrm.education.adapter.exam.ExamSearchAdapter;
import com.jkrm.education.bean.exam.ClassBean;
import com.jkrm.education.bean.exam.ExamSearchBean;
import com.jkrm.education.bean.exam.GradeBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.AnalysisPresent;
import com.jkrm.education.mvp.views.AnalysisView;
import com.jkrm.education.ui.activity.exam.CommonlyMultipleActivity;
import com.jkrm.education.ui.activity.exam.ExamSearchActivity;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.util.UserUtil;
import com.jkrm.education.widget.ClassListDialogFrament;
import com.jkrm.education.widget.GradeListDialogFrament;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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
    ExamSearchAdapter mExamSearchAdapter;
    @BindView(R.id.tv_grade)
    TextView mTvGrade;
    @BindView(R.id.tv_class)
    TextView mTvClass;
    private List<GradeBean> mGradeBeans = new ArrayList<>();
    private List<ClassBean> mClassBeans = new ArrayList<>();
    private String mStrGradeId = "", mStrClassId = "";

    @Override
    protected int getLayoutId() {
        return R.layout.analysis_layout;
    }


    @Override
    protected void initView() {
        super.initView();
        // toClass(StudentAnalyseActivity.class,false);
        //toClass(ViewStudentAnswerSheetActivity.class,false);
        mExamSearchAdapter = new ExamSearchAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, mExamSearchAdapter, false);

    }

    @Override
    protected void initData() {
        super.initData();
        getData();
        mPresenter.getClassList(UserUtil.getAppUser().getTeacherId());
        mPresenter.getGradeList(RequestUtil.getGradeList("1", UserUtil.getAppUser().getSchoolId(), "10"));
    }

    private void getData() {
        mPresenter.getAnalysisList(RequestUtil.getAnalysisRequestBody("1", "",
                mStrGradeId, UserUtil.getRoleld(), "100", mStrClassId, UserUtil.getAppUser().getTeacherId()));
    }

    @Override
    protected void initListener() {
        super.initListener();
        mIvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toClass(ExamSearchActivity.class, false);
            }
        });
        mTvGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GradeListDialogFrament gradeListDialogFrament = new GradeListDialogFrament();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Extras.KEY_GRADE_LIST, (Serializable) mGradeBeans);
                gradeListDialogFrament.setArguments(bundle
                );
                gradeListDialogFrament.show(getFragmentManager(), "");
                gradeListDialogFrament.setOnItemClickListener(new GradeListDialogFrament.OnItemClickListener() {
                    @Override
                    public void onItemChick(BaseQuickAdapter adapter, int position) {
                        GradeBean gradeBean = mGradeBeans.get(position);
                        mStrGradeId = gradeBean.getGradeId();
                        mTvGrade.setText(gradeBean.getGradeName());
                        getData();
                    }
                });

            }
        });
        mTvClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClassListDialogFrament classListDialogFrament = new ClassListDialogFrament();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Extras.KEY_CLASS_LIST, (Serializable) mClassBeans);
                classListDialogFrament.setArguments(bundle
                );
                classListDialogFrament.show(getFragmentManager(), "");
                classListDialogFrament.setOnItemClickListener(new ClassListDialogFrament.OnItemClickListener() {
                    @Override
                    public void onItemChick(BaseQuickAdapter adapter, int position) {
                        ClassBean classBean = mClassBeans.get(position);
                        mStrClassId = classBean.getClassId();
                        mTvClass.setText(classBean.getClassName());
                        getData();
                    }
                });
            }
        });
        mExamSearchAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<ExamSearchBean.RowsBean> data = adapter.getData();
                String classId = data.get(position).getClassId();
                String courseId = data.get(position).getCourseId();
                String id = data.get(position).getId();
                toClass(CommonlyMultipleActivity.class, false, "class_list", mClassBeans, Extras.EXAM_ID, id);


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
        mGradeBeans = data;
        GradeBean gradeBean = new GradeBean();
        gradeBean.setGradeName("全部");
        gradeBean.setGradeId("");
        gradeBean.setSelect(true);
        mGradeBeans.add(0, gradeBean);
    }


    @Override
    public void getGradeListFail(String msg) {
        showMsg(msg);
    }

    @Override
    public void getClassListSuccess(List<ClassBean> data) {
        mClassBeans = data;
        ClassBean classBean = new ClassBean();
        classBean.setClassName("全部");
        classBean.setClassId("");
        classBean.setSelect(true);

        mClassBeans.add(0, classBean);

    }


    @Override
    public void getClassListFail(String msg) {
        showMsg(msg);

    }

    @Override
    protected AnalysisPresent createPresenter() {
        return new AnalysisPresent(this);
    }


}
