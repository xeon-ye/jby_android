package com.jkrm.education.ui.activity;

import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzw.baselib.base.AwBaseActivity;
import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwDateUtils;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.jkrm.education.R;
import com.jkrm.education.adapter.OnlineSubjectiveQuestionsOfGroupQuestionsAdapter;
import com.jkrm.education.bean.result.SimilarResultBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.OnlineGroupObjectQuestionPresent;
import com.jkrm.education.mvp.presenters.OnlineMultipleChoicePresent;
import com.jkrm.education.mvp.views.OnlineGroupObjectiveQuestionsView;
import com.jkrm.education.mvp.views.OnlineMultipleChoiceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.kexanie.library.MathView;

/**
 * 组题客观题
 */
public class OnlineGroupObjectiveQuestionsActivity extends AwMvpActivity<OnlineGroupObjectQuestionPresent> implements OnlineGroupObjectiveQuestionsView.View {

    @BindView(R.id.toolbar_custom)
    AwViewCustomToolbar mToolbarCustom;
    @BindView(R.id.ll_title)
    LinearLayout mLlTitle;
    @BindView(R.id.tv_current)
    TextView mTvCurrent;
    @BindView(R.id.tv_sum)
    TextView mTvSum;
    @BindView(R.id.math_view_title)
    MathView mMathViewTitle;

    @BindView(R.id.tv_ans_cur)
    TextView mTvAnsCur;
    @BindView(R.id.viewpageer)
    ViewPager mViewpageer;
    private List<SimilarResultBean> mList=new ArrayList<>();
    String questionId;
    private int postion;//当前第几题
    private Timer mTimer;//计时
    private long mTime;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_online_group_objective_questions;
    }

 /*   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_group_objective_questions);
        ButterKnife.bind(this);
        setStatusTransparent();
       // mTvAnsCur.setText("----->");
        //mTvSum.setText("/"+list.size());
    }*/

    @Override
    protected void initView() {
        super.initView();
        setStatusTransparent();
        setToolbarWithBackImg("",null);
      /*  LinearLayout linearLayout = (LinearLayout)findViewById(R.id.ll_of_bottom_sheet);
        final BottomSheetBehavior bottomSheetBehavior  = BottomSheetBehavior.from(linearLayout);
        bottomSheetBehavior.setHideable(false);//禁止滑动关闭*/
        SimilarResultBean similarResultBean = new SimilarResultBean();
        SimilarResultBean similarResultBean2 = new SimilarResultBean();
        similarResultBean.setContent("SDADASD");
        similarResultBean2.setContent("SDADASD2");
        mList.add(similarResultBean);
        mList.add(similarResultBean2);
        mList.add(new SimilarResultBean());
        OnlineSubjectiveQuestionsOfGroupQuestionsAdapter mViewPagerAdapter = new OnlineSubjectiveQuestionsOfGroupQuestionsAdapter(getSupportFragmentManager(), mList, OnlineGroupObjectiveQuestionsActivity.this);
        mViewpageer.setAdapter(mViewPagerAdapter);
        mTvAnsCur.setText(mList.size()+"");
    }

    @Override
    protected void initData() {
        super.initData();
        questionId = getIntent().getStringExtra(Extras.COMMON_PARAMS);
        if (AwDataUtil.isEmpty(questionId)) {
            showDialogToFinish("题目id获取失败");
        }
        mPresenter.getSimilar(questionId);
    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    @Override
    protected OnlineGroupObjectQuestionPresent createPresenter() {
        return new OnlineGroupObjectQuestionPresent(this);
    }

    @Override
    public void getSimilarSuccess(List<SimilarResultBean> result) {
        if (AwDataUtil.isEmpty(result)) {
            showDialogToFinish("暂无类题加连");
            return;
        }
        mList = result;
        mTvSum.setText("/" + mList.size());
        initQuestion(mList.get(0), postion);
    }

    @Override
    public void getSimilarFail(String msg) {
        showDialogToFinish("暂无类题加连");
    }
    private void initQuestion(SimilarResultBean similarResultBean, int postion) {
        mMathViewTitle.setText(similarResultBean.getContent());//题
        mTvCurrent.setText(postion + 1 + "");
        initScoreViewPager(similarResultBean);
        initTimer();//开始计时
    }

    private void initScoreViewPager(SimilarResultBean similarResultBean) {

    }

    private void initTimer() {
        mTime=0;
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTime += 1000;
                        mToolbarCustom.setRightText(AwDateUtils.getmmssDateFormat(mTime));
                    }
                });
            }
        }, 0, 1000);
    }

    private void cancelTimer(){
        if(null!=mTimer){
            mTimer.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelTimer();
    }
    private void doNext() {
        if (postion < mList.size()) {
            postion++;
            initQuestion(mList.get(postion), postion);
        } else {
            showMsg("已是最后一题");
        }
    }
}
