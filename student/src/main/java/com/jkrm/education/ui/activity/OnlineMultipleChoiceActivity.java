package com.jkrm.education.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwDateUtils;
import com.jkrm.education.R;
import com.jkrm.education.adapter.OnlineAnswerChoicePagerAdapter;
import com.jkrm.education.adapter.OnlineMultipleChoicePagerAdapter;
import com.jkrm.education.bean.result.SimilarResultBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.OnlineMultipleChoicePresent;
import com.jkrm.education.mvp.views.OnlineMultipleChoiceView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 在线答题多选题 组题 客观题
 */
public class OnlineMultipleChoiceActivity extends AwMvpActivity<OnlineMultipleChoicePresent> implements OnlineMultipleChoiceView.View {

    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_current)
    TextView mTvCurrent;
    @BindView(R.id.tv_sum)
    TextView mTvSum;
    @BindView(R.id.viewpageer)
    ViewPager mViewpageer;
    private String questionId = "";
    private Timer mTimer;
    private long mTime;




    @Override
    protected OnlineMultipleChoicePresent createPresenter() {
        return new OnlineMultipleChoicePresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_online_multiple_choice;
    }

    @Override
    protected void initView() {
        super.initView();
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
    public void getSimilarSuccess(List<SimilarResultBean> result) {
        if (AwDataUtil.isEmpty(result)) {
            showDialog("暂无类题加练");
            return;
        }
        setToolbarWithBackImg("类题加练（共" + result.size() + "题）", null);
        initScoreViewPager(result);
        mTimer=new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTime+=1000;
                        setText(mTvTime,AwDateUtils.getmmssDateFormat(mTime));
                    }
                });
            }
        },0,1000);
    }

    private void initScoreViewPager(List<SimilarResultBean> list) {
        if(AwDataUtil.isEmpty(list)){
            return;
        }
        // 注意这里 的FragmentManager 是 getChildFragmentManager(); 因为是在Fragment里面, 而在activity里面用FragmentManager 是 getSupportFragmentManager()
        OnlineMultipleChoicePagerAdapter mViewPagerAdapter = new OnlineMultipleChoicePagerAdapter(getSupportFragmentManager(), list, mActivity);
        mViewpageer.setAdapter(mViewPagerAdapter);
        mTvSum.setText("/"+list.size());
    }
    @Override
    public void getSimilarFail(String msg) {
        showMsg(msg);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
