package com.jkrm.education.ui.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hzw.baselib.base.AwMvpFragment;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.AwMathViewUtil;
import com.hzw.baselib.util.AwSpUtil;
import com.jkrm.education.R;
import com.jkrm.education.adapter.AnswerAnalyQuestionsOfGroupChildPagerAdapter;
import com.jkrm.education.adapter.AnswerAnalysisPagerAdapter;
import com.jkrm.education.adapter.OnlineSubjectiveQuestionsOfGroupQuestionsChildPagerAdapter;
import com.jkrm.education.bean.BatchBean;
import com.jkrm.education.bean.result.SimilarResultBean;
import com.jkrm.education.bean.result.WatchLogBean;
import com.jkrm.education.bean.rx.RxLastBean;
import com.jkrm.education.bean.rx.RxNextpageType;
import com.jkrm.education.bean.rx.RxTurnpageType;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.AnswerAnalysisPresent;
import com.jkrm.education.mvp.views.AnswerAnalysisView;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.util.UserUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.github.kexanie.library.MathView;

/**
 * @Description: 组题 拼接题类型  答案解析
 * @Author: xiangqian
 * @CreateDate: 2020/4/29 15:51
 */

public class AnswerAnalyQuestionsOfGroupQuestionsFragment extends AwMvpFragment<AnswerAnalysisPresent> implements AnswerAnalysisView.View {


    @BindView(R.id.cb_collect)
    CheckBox mCbCollect;
    @BindView(R.id.btn_next)
    Button mBtnNext;
    @BindView(R.id.math_view_title)
    MathView mMathViewTitle;
    @BindView(R.id.handler)
    ImageButton mHandler;
    @BindView(R.id.tv_question_type)
    TextView mTvQuestionType;
    @BindView(R.id.tv_ans_cur)
    TextView mTvAnsCur;
    @BindView(R.id.viewpageer)
    ViewPager mViewpageer;
    @BindView(R.id.ll)
    RelativeLayout mLl;
    @BindView(R.id.ll_of_collect)
    LinearLayout mLlofCollec;
    Unbinder unbinder;

    public static BatchBean getmBatchBean() {
        return mBatchBean;
    }

    public static void setmBatchBean(BatchBean mBatchBean) {
        AnswerAnalyQuestionsOfGroupQuestionsFragment.mBatchBean = mBatchBean;
    }

    public static BatchBean mBatchBean;




    private int inSidePos;

    @Override
    protected AnswerAnalysisPresent createPresenter() {
        return new AnswerAnalysisPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_answer_analysis_groupquestions_layout;
    }

    @Override
    protected void initView() {
        super.initView();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initData() {
        super.initData();
        mBatchBean = (BatchBean) getArguments().getSerializable(Extras.BATCHBEAN);
        EventBus.getDefault().postSticky(mBatchBean);
        if (null == mBatchBean) {
            return;
        }
        //错题 隐藏下一题
        if (!AwDataUtil.isEmpty(mBatchBean.getErrorTypeName())) {
            showView(mLlofCollec, false);
        }
        mMathViewTitle.setText(mBatchBean.getContent());
        AwMathViewUtil.setImgScan(mMathViewTitle);
        mTvQuestionType.setText(mBatchBean.getType().getName());
        List<BatchBean.SubQuestionsBean> subQuestions = mBatchBean.getSubQuestions();
        //从错题本进来
        if (AnswerAnalysisPagerAdapter.isError()) {
            //填充页面 展 示 视频和  类题
            subQuestions.add(new BatchBean.SubQuestionsBean());
        }
        AnswerAnalyQuestionsOfGroupChildPagerAdapter answerAnalyQuestionsOfGroupChildPagerAdapter = new AnswerAnalyQuestionsOfGroupChildPagerAdapter(getChildFragmentManager(), subQuestions, getActivity());
        mViewpageer.setAdapter(answerAnalyQuestionsOfGroupChildPagerAdapter);
        mViewpageer.setOffscreenPageLimit(mBatchBean.getSubQuestions().size());
        //获取位置
        inSidePos= AwSpUtil.getInt(Extras.KEY_INSIDEPOS,Extras.KEY_INSIDEPOS,0);
        String localID = AwSpUtil.getString(Extras.KEY_BATQUESTION, Extras.KEY_BATQUESTION, "");
        if(mBatchBean.getId().equals(localID)){
            mViewpageer.setCurrentItem(inSidePos);
        }

    }

    @Override
    protected void initListener() {
        super.initListener();

        mViewpageer.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mTvAnsCur.setText((position + 1) + "/" + mViewpageer.getChildCount());
               /* if(position+1==mViewpageer.getChildCount()){
                    showDialog("里面最后一题");
                }*/
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mCbCollect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mPresenter.collectQuestion(RequestUtil.getCollectBody(UserUtil.getAppUser().getStudId(), mBatchBean.getCourseId(), mBatchBean.getQuestionId(), "2"));
                } else {
                    mPresenter.removeCollectQuestion(RequestUtil.getRemoveCollectBody(UserUtil.getAppUser().getStudId(), mBatchBean.getQuestionId()));
                }
            }
        });

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @OnClick(R.id.btn_next)
    public void onViewClicked() {
        EventBus.getDefault().post(new RxNextpageType(false, mBatchBean.getId()));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNextPage(RxNextpageType rxNextpageType) {
        if (!rxNextpageType.isOutSide()) {
            if (rxNextpageType.getId().equals(mBatchBean.getId())) {
                //内嵌viewpager最后一页  通知外层viewpager 滑动
                if (mViewpageer.getCurrentItem() + 1 == mViewpageer.getChildCount()) {
                    EventBus.getDefault().post(new RxNextpageType(true));
                } else {
                    //滑动至下一页
                    mViewpageer.setCurrentItem(mViewpageer.getCurrentItem() + 1);
                }
            }


        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLastPage(RxLastBean rxLastBean) {
        if (null != rxLastBean) {
            if (rxLastBean.isLast()) {
                mBtnNext.setEnabled(false);
                mBtnNext.setText("已是最后一题");
            } else {
                mBtnNext.setEnabled(true);
                mBtnNext.setText("下一题");
            }
        }
    }


    @Override
    public void collectQuestionSuccess(WatchLogBean request) {
        showMsg("收藏成功");
    }

    @Override
    public void collectQuestionFail(String msg) {
        showMsg("收藏失败");
    }

    @Override
    public void removeCollectQuestionSuccess(WatchLogBean request) {
        showMsg("移除收藏成功");
    }

    @Override
    public void removeCollectQuestionFail(String msg) {
        showMsg("移除收藏失败");
    }

    @Override
    public void readOverQuestionSuccess(WatchLogBean request) {
        showMsg("批阅成功");
    }

    @Override
    public void readOverQuestionFail(String msg) {
        showMsg("批阅失败");
    }

    @Override
    public void getSimilarSuccess(List<SimilarResultBean> result) {

    }

    @Override
    public void getSimilarFail(String msg) {

    }


}
