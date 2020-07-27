package com.jkrm.education.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzw.baselib.base.AwMvpFragment;
import com.hzw.baselib.base.AwMvpLazyFragment;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.widgets.MyGridView;
import com.hzw.baselib.widgets.PhotoFragmentDialog;
import com.jkrm.education.R;
import com.jkrm.education.adapter.CustomGridAdapter;
import com.jkrm.education.adapter.OnlineSubjectiveQuestionsOfGroupQuestionsChildPagerAdapter;
import com.jkrm.education.bean.result.SimilarResultBean;
import com.jkrm.education.bean.rx.RxOnlineJumpType;
import com.jkrm.education.bean.rx.RxTurnpageType;
import com.jkrm.education.constants.Extras;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.kexanie.library.MathView;

/**
 * @Description: 组题 选择题
 * @Author: xiangqian
 * @CreateDate: 2020/4/29 15:51
 */

public class OnlineChoiceOfGroupQuestionsFragment extends AwMvpFragment {
    @BindView(R.id.grid_view)
    MyGridView mGridView;
    @BindView(R.id.math_view_title)
    MathView mMathView;
    @BindView(R.id.ll)
    LinearLayout mLl;
    Unbinder unbinder;
    @BindView(R.id.viewpageer)
    ViewPager mViewpageer;
    @BindView(R.id.tv_ans_cur)
    TextView mTvAnsCur;
    Unbinder unbinder1;
    @BindView(R.id.tv_question_type)
    TextView mTvQuestionType;

    private SimilarResultBean mBean;
    private List<SimilarResultBean> mList;
    private List<String> mImgList = new ArrayList<>();
    CustomGridAdapter mCustomGridAdapter;

    @Override
    protected AwCommonPresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_onlinesubjectivequestions_groupquestions_layout;
    }

    @Override
    protected void initData() {
        super.initData();
        EventBus.getDefault().register(this);

        mBean = (SimilarResultBean) getArguments().getSerializable(Extras.KEY_BEAN_QUESTION_SIMILAR);
        mList = (List<SimilarResultBean>) getArguments().getSerializable(Extras.KEY_BEAN_QUESTION_SIMILAR_LIST);
        if (mBean == null) {
            return;
        }
        mMathView.setText(mBean.getContent());
        mImgList.add("");
        mTvQuestionType.setText(mBean.getType().getName());
        mCustomGridAdapter = new CustomGridAdapter(mActivity, mImgList);
        mGridView.setAdapter(mCustomGridAdapter);
        OnlineSubjectiveQuestionsOfGroupQuestionsChildPagerAdapter onlineSubjectiveQuestionsOfGroupQuestionsChildPagerAdapter = new OnlineSubjectiveQuestionsOfGroupQuestionsChildPagerAdapter(getChildFragmentManager(), mBean.getSubQuestions(), getActivity());
        onlineSubjectiveQuestionsOfGroupQuestionsChildPagerAdapter.setBean(mBean);//需要外面的题目id 区分滑动
        mViewpageer.setAdapter(onlineSubjectiveQuestionsOfGroupQuestionsChildPagerAdapter);
        mViewpageer.setOffscreenPageLimit(mBean.getSubQuestions().size());
    }

    @Override
    protected void initListener() {
        super.initListener();
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == mImgList.size() - 1) {
                    PhotoFragmentDialog photoFragmentDialog = new PhotoFragmentDialog();
                    photoFragmentDialog.show(getFragmentManager(), "");
                    photoFragmentDialog.setOnDialogButtonClickListener(new PhotoFragmentDialog.OnDialogButtonClickListener() {
                        @Override
                        public void photoButtonClick() {
                            //拍照并裁剪
                            PictureSelector.create(OnlineChoiceOfGroupQuestionsFragment.this).openCamera(PictureMimeType.ofImage()).enableCrop(true).freeStyleCropEnabled(true).rotateEnabled(true).forResult(PictureConfig.CHOOSE_REQUEST);
                        }

                        @Override
                        public void choseButtonClick() {
                            //相册选择并裁剪
                            PictureSelector.create(OnlineChoiceOfGroupQuestionsFragment.this).openGallery(PictureMimeType.ofImage()).enableCrop(true).freeStyleCropEnabled(true).rotateEnabled(true).forResult(PictureConfig.CHOOSE_REQUEST);

                        }

                        @Override
                        public void cancelButtonClick() {

                        }
                    });
                }
            }
        });
        mViewpageer.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mTvAnsCur.setText((position + 1) + "/" + mBean.getSubQuestions().size());
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

    //翻页
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RxTurnpageType turnpageType) {
        if(turnpageType.getId().equals(mBean.getId())){
            mViewpageer.setCurrentItem(mViewpageer.getCurrentItem() + 1);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void jumpByInfo(RxOnlineJumpType type) {
        if (null != type) {
            //滑动至某一题
            mViewpageer.setCurrentItem(type.getInSidePageNum());
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder1.unbind();
    }
}
