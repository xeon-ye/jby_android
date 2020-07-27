package com.jkrm.education.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzw.baselib.base.AwMvpFragment;
import com.hzw.baselib.util.AwArraysUtil;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwMathViewUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.jkrm.education.R;
import com.jkrm.education.adapter.AnswerAnalyImgAdapter;
import com.jkrm.education.adapter.AnswerAnalyQuestionsOfGroupChildPagerAdapter;
import com.jkrm.education.adapter.CustomGridAdapter;
import com.jkrm.education.bean.BatchBean;
import com.jkrm.education.bean.result.SimilarResultBean;
import com.jkrm.education.bean.result.WatchLogBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.AnswerAnalysisPresent;
import com.jkrm.education.mvp.views.AnswerAnalysisView;
import com.jkrm.education.ui.activity.ImgActivity;
import com.jkrm.education.util.RequestUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.github.kexanie.library.MathView;

/**
 * @Description: 非组题 主观题
 * @Author: xiangqian
 * @CreateDate: 2020/4/29 16:17
 */

public class AnswerAnalyNonGroupSubjectiveQuestionsChildFragment extends AwMvpFragment<AnswerAnalysisPresent> implements AnswerAnalysisView.View {


    @BindView(R.id.math_view_title)
    MathView mMathViewTitle;
    @BindView(R.id.tv_right)
    TextView mTvRight;
    @BindView(R.id.tv_wrong)
    TextView mTvWrong;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    @BindView(R.id.tv_analysis)
    TextView mTvAnalysis;
    @BindView(R.id.math_view_analysis)
    MathView mMathViewAnalysis;
    Unbinder unbinder;
    @BindView(R.id.tv_answer_state)
    TextView mTvAnswerState;
    @BindView(R.id.tv_answer)
    MathView mTvAnswer;
    private SimilarResultBean mBean;
    private List<SimilarResultBean> mList;
    private List<String> mImgList = new ArrayList<>();
    private CustomGridAdapter mCustomGridAdapter;
    private String mUrl;
    BatchBean.SubQuestionsBean mBatchBean;
    AnswerAnalyImgAdapter mAnswerAnalyImgAdapter;
    private boolean isAnswer;//是否作答


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_answeranaly_nonchoice_child_layout;
    }

    @Override
    protected void initData() {
        super.initData();
        mBatchBean = (BatchBean.SubQuestionsBean) getArguments().getSerializable(Extras.BATCHBEAN_SUB);
        if (AwDataUtil.isEmpty(mBatchBean.getStudAnswer())) {
            mTvAnswerState.setText("未作答");
            mTvAnswerState.setTextColor(getResources().getColor(R.color.red));
            //正确答案
        }
        //外部大题 请求 类题
        BatchBean batchBean = AnswerAnalyQuestionsOfGroupChildPagerAdapter.getBatchBean();
        //题干
        mMathViewTitle.setText(mBatchBean.getContent());
        AwMathViewUtil.setImgScan(mMathViewTitle);
        //解析
        mMathViewAnalysis.setText(mBatchBean.getAnswerExplanation());
        AwMathViewUtil.setImgScan(mMathViewAnalysis);
        if (AwDataUtil.isEmpty(mBatchBean.getAnswerExplanation())) {
            showView(mTvAnalysis, false);
        }
        if (!AwDataUtil.isEmpty(mBatchBean.getAnswer())) {
            mTvAnswer.setText("答案\n\t"+mBatchBean.getAnswer());
            AwMathViewUtil.setImgScan(mTvAnswer);
        }
        //图片
        mAnswerAnalyImgAdapter = new AnswerAnalyImgAdapter();
        AwRecyclerViewUtil.setRecyclerViewGridlayout(mActivity, mRcvData, mAnswerAnalyImgAdapter, 3);
        //拆分数据
        if (!AwDataUtil.isEmpty(mBatchBean.getStudAnswer())) {
            ArrayList<String> strings = new ArrayList<>(AwArraysUtil.stringToList(mBatchBean.getStudAnswer()));
            ArrayList<String> emptyList = new ArrayList<>();
            for (String s : strings) {
                if (AwDataUtil.isEmpty(s)) {
                    emptyList.add(s);
                }
            }
            strings.removeAll(emptyList);
            mAnswerAnalyImgAdapter.addAllData(strings);
        }
        //是否可以点击
        if ("1".equals(mBatchBean.getQuestStatus())) {
            //正确
            mTvRight.setSelected(true);
        } else if ("3".equals(mBatchBean.getQuestStatus())) {
            mTvWrong.setSelected(true);
        } else if ("5".equals(mBatchBean.getQuestStatus())) {
            isAnswer = true;
            //可以批阅
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mAnswerAnalyImgAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
        });
        mAnswerAnalyImgAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<String> imgList = adapter.getData();
                toClass(ImgActivity.class, false, Extras.IMG_URL, imgList.get(position));
            }
        });


       /* mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==mImgList.size()-1){
                    PhotoFragmentDialog photoFragmentDialog=new PhotoFragmentDialog();
                    photoFragmentDialog.show(getFragmentManager(),"");
                    photoFragmentDialog.setOnDialogButtonClickListener(new PhotoFragmentDialog.OnDialogButtonClickListener() {
                        @Override
                        public void photoButtonClick() {
                            //拍照并裁剪
                            PictureSelector.create(AnswerAnalyNonGroupSubjectiveQuestionsFragment.this).openCamera(PictureMimeType.ofImage()).enableCrop(true).freeStyleCropEnabled(true).rotateEnabled(true).forResult(1);
                        }

                        @Override
                        public void choseButtonClick() {
                            //拍照并裁剪
                            PictureSelector.create(AnswerAnalyNonGroupSubjectiveQuestionsFragment.this).openGallery(PictureMimeType.ofImage()).maxSelectNum(1).freeStyleCropEnabled(true).rotateEnabled(true).forResult(1);

                        }

                        @Override
                        public void cancelButtonClick() {

                        }
                    });
                }
            }
        });
        mCustomGridAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                    if(mImgList.size()>1){

                        //有图片 已作答
                        EventBus.getDefault().post(new RxQuestionBean(mBean.getId(),true, AwArraysUtil.splitList(mImgList),mBean.getParentId()));
                    }else{
                        //无图片 未作答
                        EventBus.getDefault().post(new RxQuestionBean(mBean.getId(),false,"",mBean.getParentId()));
                    }
            }
        });
        mCustomGridAdapter.setOnDelelteClickListener(new CustomGridAdapter.OnDelelteClickListener() {

            @Override
            public void deleteButtonClick(int postion) {
                EventBus.getDefault().post(new RxQuestionBean(mBean.getId(),mImgList.get(postion),mBean.getParentId(),true,true));
            }
        });*/
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected AnswerAnalysisPresent createPresenter() {
        return new AnswerAnalysisPresent(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_right, R.id.tv_wrong})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                if (isAnswer) {
                    mTvRight.setSelected(true);
                    mTvWrong.setSelected(false);
                    mPresenter.readOverQuestion(RequestUtil.getReadOverBody(mBatchBean.getBatchId(), "1", mBatchBean.getQuestionId()));
                }

                break;
            case R.id.tv_wrong:
                if (isAnswer) {
                    mTvWrong.setSelected(true);
                    mTvRight.setSelected(false);
                    mPresenter.readOverQuestion(RequestUtil.getReadOverBody(mBatchBean.getBatchId(), "0", mBatchBean.getQuestionId()));
                }

                break;
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
