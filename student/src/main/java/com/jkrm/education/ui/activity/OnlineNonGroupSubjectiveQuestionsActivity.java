package com.jkrm.education.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwDateUtils;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.hzw.baselib.widgets.MyGridView;
import com.hzw.baselib.widgets.PhotoFragmentDialog;
import com.jkrm.education.R;
import com.jkrm.education.adapter.CustomGridAdapter;
import com.jkrm.education.adapter.OnlineNonGroupSubjectiveQuestionsAdapter;
import com.jkrm.education.bean.result.SimilarResultBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.OnlineNonGroupSubjectiveQuestionsPresent;
import com.jkrm.education.mvp.views.OnlineNonGroupSubjectiveQuestionsView;
import com.jkrm.education.ui.fragment.OnlineNonGroupSubjectiveQuestionsFragment;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.kexanie.library.MathView;

/**
 * 非组题 主观题
 */
public class OnlineNonGroupSubjectiveQuestionsActivity extends AwMvpActivity<OnlineNonGroupSubjectiveQuestionsPresent> implements OnlineNonGroupSubjectiveQuestionsView.View {

    @BindView(R.id.toolbar_custom)
    AwViewCustomToolbar mToolbarCustom;
    @BindView(R.id.ll_title)
    LinearLayout mLlTitle;
    @BindView(R.id.tv_current)
    TextView mTvCurrent;
    @BindView(R.id.tv_sum)
    TextView mTvSum;
    @BindView(R.id.viewpageer)
    ViewPager mViewpageer;
    String questionId;
    @BindView(R.id.math_view)
    MathView mMathView;
    @BindView(R.id.grid_view)
    MyGridView mGridView;
    List<SimilarResultBean> mList = new ArrayList<>();
    private List<String> mImgList = new ArrayList<>();
    private CustomGridAdapter mCustomGridAdapter;
    private int postion;//当前第几题
    private Timer mTimer;//计时
    private long mTime;

    @Override
    protected OnlineNonGroupSubjectiveQuestionsPresent createPresenter() {
        return new OnlineNonGroupSubjectiveQuestionsPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_non_group_subjective_questions;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusBlack();
        setToolbarWithBackImg("", null);
        mImgList.add("");
        mCustomGridAdapter = new CustomGridAdapter(mActivity, mImgList);
        mGridView.setAdapter(mCustomGridAdapter);
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
            showDialogToFinish("暂无类题加连");
            return;
        }
        mList = result;
        mTvSum.setText("/" + mList.size());
        initQuestion(mList.get(0), postion);
        // initScoreViewPager(result);
    }

    private void initQuestion(SimilarResultBean similarResultBean, int postion) {
        mMathView.setText(similarResultBean.getContent());//题
        mTvCurrent.setText(postion + 1 + "");
        initTimer();//开始计时
    }

    private void doNext() {
        if (postion < mList.size()) {
            postion++;
            initQuestion(mList.get(postion), postion);
        } else {
            showMsg("已是最后一题");
        }
    }

    @Override
    public void getSimilarFail(String msg) {
        showDialogToFinish("暂无类题加连");
    }

    @Override
    protected void initListener() {
        super.initListener();
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == mImgList.size() - 1) {
                    PhotoFragmentDialog photoFragmentDialog = new PhotoFragmentDialog();
                    photoFragmentDialog.show(getSupportFragmentManager(), "");
                    photoFragmentDialog.setOnDialogButtonClickListener(new PhotoFragmentDialog.OnDialogButtonClickListener() {
                        @Override
                        public void photoButtonClick() {
                            //拍照并裁剪
                            PictureSelector.create(OnlineNonGroupSubjectiveQuestionsActivity.this).openCamera(PictureMimeType.ofImage()).enableCrop(true).freeStyleCropEnabled(true).rotateEnabled(true).forResult(PictureConfig.CHOOSE_REQUEST);
                        }

                        @Override
                        public void choseButtonClick() {
                            //相册选择并裁剪
                            PictureSelector.create(OnlineNonGroupSubjectiveQuestionsActivity.this).openGallery(PictureMimeType.ofImage()).maxSelectNum(1).enableCrop(true).freeStyleCropEnabled(true).rotateEnabled(true).forResult(PictureConfig.CHOOSE_REQUEST);

                        }

                        @Override
                        public void cancelButtonClick() {

                        }
                    });
                }
            }
        });
    }

    private void initScoreViewPager(List<SimilarResultBean> list) {
        if (AwDataUtil.isEmpty(list)) {
            return;
        }
        // 注意这里 的FragmentManager 是 getChildFragmentManager(); 因为是在Fragment里面, 而在activity里面用FragmentManager 是 getSupportFragmentManager()
        OnlineNonGroupSubjectiveQuestionsAdapter mViewPagerAdapter = new OnlineNonGroupSubjectiveQuestionsAdapter(getSupportFragmentManager(), list, mActivity);
        mViewpageer.setOffscreenPageLimit(5);
        mViewpageer.setAdapter(mViewPagerAdapter);
        mTvSum.setText("/" + list.size());
    }


    /* @Override
     protected void initListener() {
         super.initListener();
         mViewpageer.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
             @Override
             public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
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
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != data) {
            //图片单选和多选数据都是以ArrayList的字符串数组返回的。
            // 如果同时开启裁剪和压缩，则取压缩路径为准因为是先裁剪后压缩
            List<LocalMedia> paths = PictureSelector.obtainMultipleResult(data);
            mImgList.add(paths.get(0).getPath());
            mCustomGridAdapter.notifyDataSetChanged();
            if (paths.get(0).isCompressed()) {
                String compressPath = paths.get(0).getCompressPath();
                showMsg(compressPath);
            }

        }
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
                        mToolbar.setRightText(AwDateUtils.getmmssDateFormat(mTime));
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
}
