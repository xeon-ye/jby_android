package com.jkrm.education.ui.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.interfaces.IPermissionListener;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwDateUtils;
import com.hzw.baselib.util.AwRxPermissionUtil;
import com.hzw.baselib.util.AwSystemIntentUtil;
import com.jkrm.education.R;
import com.jkrm.education.adapter.OnlineAnswerChoicePagerAdapter;
import com.jkrm.education.bean.result.SimilarResultBean;
import com.jkrm.education.bean.rx.RxErrorQuestion;
import com.jkrm.education.bean.rx.RxOnlineJumpType;
import com.jkrm.education.bean.rx.RxTurnpageType;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.OnlineAnswerChoicePresent;
import com.jkrm.education.mvp.views.OnlineAnswerChoiceView;
import com.jkrm.education.ui.fragment.ErrorQuestionFragment;
import com.jkrm.education.ui.fragment.ErrorQuestionTimeFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 在线答题
 */
public class OnlineAnswerActivity extends AwMvpActivity<OnlineAnswerChoicePresent> implements OnlineAnswerChoiceView.View {

    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_current)
    TextView mTvCurrent;
    @BindView(R.id.tv_sum)
    TextView mTvSum;
    @BindView(R.id.viewpageer)
    ViewPager mViewpageer;
    @BindView(R.id.ll_of_online_answer)
    LinearLayout mLlOfOnlineAnswer;
    @BindView(R.id.tv_hint)
    TextView mTvHint;
    @BindView(R.id.img_back)
    ImageView mImgBack;
    private String questionId = "";
    private Timer mTimer;
    private long mTime;
    List<SimilarResultBean> mList = new ArrayList<>();
    private String mCourseId;
    public String mStrType = "2";//默认类题加练
    private String mClassify;


    @Override
    protected OnlineAnswerChoicePresent createPresenter() {
        return new OnlineAnswerChoicePresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_online_answer;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusTxtDark();
        checkPermission();
    }

    public String getmStrType() {
        return mStrType;
    }


    @Override
    protected void initData() {
        super.initData();
        questionId = getIntent().getStringExtra(Extras.COMMON_PARAMS);
        mCourseId = getIntent().getStringExtra(Extras.COURSE_ID);
        //答题类型
        if (!AwDataUtil.isEmpty(getIntent().getStringExtra(Extras.KEY_QUESTION_TYPE))) {
            mStrType = getIntent().getStringExtra(Extras.KEY_QUESTION_TYPE);

        }
        mClassify = getIntent().getStringExtra(Extras.KEY_CLASSIFY);
        ArrayList<SimilarResultBean> similarResultBeans = (ArrayList<SimilarResultBean>) getIntent().getSerializableExtra(Extras.KEY_SIMILAR_LIST);
        if (!AwDataUtil.isEmpty(mClassify)) {
            mTvHint.setText(mClassify);
        }
        if (null != similarResultBeans) {
            getSimilarSuccess(similarResultBeans);
            return;
        }
        if (AwDataUtil.isEmpty(questionId) || AwDataUtil.isEmpty(mCourseId)) {
            showDialogToFinish("题目id获取失败");
            return;
        }
        mPresenter.getSimilar(questionId);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mViewpageer.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == mList.size() - 1) {
                    mTvHint.setText("所有题目");
                    showView(mTvCurrent,false);
                    showView(mTvSum,false);
                } else {
                    showView(mTvCurrent,true);
                    showView(mTvSum,true);
                    mTvHint.setText("类题加练");
                }
                mTvCurrent.setText(position + 1 + "");
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void getSimilarSuccess(List<SimilarResultBean> result) {
        if (AwDataUtil.isEmpty(result)) {
            showDialog("暂无类题加练");
            return;
        }
        setToolbarWithBackImg("类题加练（共" + result.size() + "题）", null);
        initScoreViewPager(result);
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTime += 1000;
                        setText(mTvTime, AwDateUtils.getmmssDateFormat(mTime));
                    }
                });
            }
        }, 0, 1000);
    }

    private void initScoreViewPager(List<SimilarResultBean> list) {
        if (AwDataUtil.isEmpty(list)) {
            showDialogToFinish("暂无习题加连");
            return;
        }
        //提交页面
        list.add(new SimilarResultBean());

        // 注意这里 的FragmentManager 是 getChildFragmentManager(); 因为是在Fragment里面, 而在activity里面用FragmentManager 是 getSupportFragmentManager()
        OnlineAnswerChoicePagerAdapter mViewPagerAdapter = new OnlineAnswerChoicePagerAdapter(getSupportFragmentManager(), list, mActivity);
        mViewpageer.setAdapter(mViewPagerAdapter);
        //全部预加载 会卡顿  但是会保存所有状态  后期在修改
        mViewpageer.setOffscreenPageLimit(list.size());
        mTvSum.setText("/" + (list.size() - 1));
        mList = list;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mTimer) {
            mTimer.cancel();
        }

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void jumpByInfo(RxOnlineJumpType type) {
        if (null != type) {
            //滑动至某一题
            mViewpageer.setCurrentItem(type.getOutSidePageNum());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void trunPage(RxTurnpageType rxTurnpageType) {
        if (null != rxTurnpageType) {
            if (rxTurnpageType.isOutSide()) {
                mViewpageer.setCurrentItem(mViewpageer.getCurrentItem() + 1);
            }
        }
    }



    public long stopTimer() {
        if (null != mTimer) {
            mTimer.cancel();
        }

        return mTime;
    }

    public String getCourseId() {
        return mCourseId;
    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
       // super.onSaveInstanceState(outState);

    }
    private void checkPermission() {
        AwRxPermissionUtil.getInstance().checkPermission(mActivity, AwRxPermissionUtil.permissionsStorage, new IPermissionListener() {
            @Override
            public void granted() {


            }

            @Override
            public void shouldShowRequestPermissionRationale() {
                showDialog("请允许获取存储权限才可正常进行导出操作", v -> {
                    dismissDialog();
                    AwSystemIntentUtil.toApplicationDetailSetting(mActivity);
                    finish();
                });
            }

            @Override
            public void needToSetting() {
                showDialog("请允许获取存储权限才可正常进行导出操作", v -> {
                    dismissDialog();
                    AwSystemIntentUtil.toApplicationDetailSetting(mActivity);
                    finish();
                });
            }
        });

    }
}
