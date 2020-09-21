package com.jkrm.education.ui.activity.exam;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.jkrm.education.R;
import com.jkrm.education.adapter.exam.ChooseExamCourseAdapter;
import com.jkrm.education.bean.exam.ExamCompreBean;
import com.jkrm.education.bean.exam.ExamCourseBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.ViewStudentAnswerSheetPresent;
import com.jkrm.education.mvp.views.ViewStudentAnswerSheetView;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.widget.CanvasImageViewWithScale;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewStudentAnswerSheetActivity extends AwMvpActivity<ViewStudentAnswerSheetPresent> implements ViewStudentAnswerSheetView.View {


    String EXAM_ID;
    String STUDENT_ID;
    String COURSE_ID;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    @BindView(R.id.iv_questionImg)
    CanvasImageViewWithScale mIvQuestionImg;
    @BindView(R.id.tv_grades)
    TextView mTvGrades;
    @BindView(R.id.iv_lastQuestion)
    ImageView mIvLastQuestion;
    @BindView(R.id.iv_nextQuestion)
    ImageView mIvNextQuestion;
    @BindView(R.id.fl_imgFlLayout)
    FrameLayout mFlImgFlLayout;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    private List<ExamCourseBean> mExamCourseBeanList;
    private ChooseExamCourseAdapter mChooseExamCourseAdapter;
    private ExamCompreBean mBean;
    private String mStrFront, mStrBack;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_view_student_answer_sheet;
    }

    @Override
    protected ViewStudentAnswerSheetPresent createPresenter() {
        return new ViewStudentAnswerSheetPresent(this);
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusBlue();
        mChooseExamCourseAdapter = new ChooseExamCourseAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, mChooseExamCourseAdapter, false);

    }

    @Override
    protected void initData() {
        super.initData();
        EXAM_ID = getIntent().getExtras().getString(Extras.EXAM_ID);
        STUDENT_ID = getIntent().getExtras().getString(Extras.STUDENT_ID);
        mExamCourseBeanList = (List<ExamCourseBean>) getIntent().getSerializableExtra(Extras.KEY_EXAM_COURSE_LIST);
        mChooseExamCourseAdapter.addAllData(mExamCourseBeanList);
    }

    private void getData() {
        mPresenter.getCourseList(RequestUtil.getCourseListBody(EXAM_ID, STUDENT_ID, COURSE_ID));
    }

    @Override
    public void getCourseListSuccess(ExamCompreBean data) {
        mBean = data;
        String[] split = mBean.getRawScan().split(",");
        //正面
        mStrFront = split[0];
        //反面
        mStrBack = split[1];
        setGradeData();
    }

    private void setGradeData() {
        Glide.with(mActivity).load(mStrFront).into(mIvQuestionImg);
        mTvGrades.setText(mBean.getGradeMaxScore() + "分");
        mTvTitle.setText("姓名："+mBean.getStudName()+"考号："+mBean.getStudCode()+"班级："+mBean.getClassName());
    }

    @Override
    public void getCourseListFail(String msg) {
        showMsg("暂无试卷");
    }

    @Override
    protected void initListener() {
        super.initListener();
        mChooseExamCourseAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                for (int i = 0; i < mExamCourseBeanList.size(); i++) {
                    if (i == position) {
                        mExamCourseBeanList.get(i).setChecked(true);
                    } else {
                        mExamCourseBeanList.get(i).setChecked(false);
                    }
                }
                mChooseExamCourseAdapter.notifyDataSetChanged();
                COURSE_ID = mExamCourseBeanList.get(position).getCourseId();
                getData();
            }
        });
        mIvLastQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(mActivity).load(mStrFront).into(mIvQuestionImg);
            }
        });
        mIvNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(mActivity).load(mStrBack).into(mIvQuestionImg);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
