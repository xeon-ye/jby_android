package com.jkrm.education.ui.fragment;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzw.baselib.base.AwMvpFragment;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.util.MyDateUtil;
import com.jkrm.education.R;
import com.jkrm.education.adapter.QuestionBasketOptionsAdapter;
import com.jkrm.education.bean.QuestionOptionBean;
import com.jkrm.education.bean.result.SimilarResultBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.ui.activity.ImgActivity;
import com.zzhoujay.richtext.RichText;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.github.kexanie.library.MathView;

/**
 * Created by hzw on 2019/6/1.
 */

public class QuestionExtendFragment extends AwMvpFragment {

    @BindView(R.id.iv_open)
    ImageView mIvOpen;
    @BindView(R.id.mv_questionTxt)
    MathView mMvQuestionTxt;
    @BindView(R.id.mv_answerContent)
    MathView mMvAnswerContent;
    @BindView(R.id.mv_resolveContent)
    MathView mMvResolveContent;
    @BindView(R.id.iv_shrink)
    ImageView mIvShrink;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    @BindView(R.id.ll_bottomLayout)
    LinearLayout mLlBottomLayout;

    private SimilarResultBean mBean;

    @Override
    protected AwCommonPresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_question_extend;
    }

    @Override
    protected void initView() {
        super.initView();
        showView(mLlBottomLayout, false);
        showView(mIvOpen, true);
    }

    @Override
    protected void initData() {
        super.initData();
        mBean = (SimilarResultBean) getArguments().getSerializable(Extras.KEY_BEAN_QUESTION_SIMILAR);
        if(mBean == null) {
            return;
        }
        AwLog.d("QuestionExtendFragment bean typename: " + mBean.getTypeName());
        // WebView设置图片自适应屏幕宽度
        mMvQuestionTxt.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String javascript = "javascript:function ResizeImages() {" +
                        "var myimg,oldwidth;" +
                        "var maxwidth = document.body.clientWidth;" +
                        "for(i=0;i <document.images.length;i++){" +
                        "myimg = document.images[i];" +
                        "if(myimg.width > maxwidth){" +
                        "oldwidth = myimg.width;" +
                        "myimg.width = maxwidth;" +
                        "}" +
                        "}" +
                        "}";
                view.loadUrl(javascript);
                view.loadUrl("javascript:ResizeImages();");
            }
        });
        mMvQuestionTxt.loadUrl("file:///android_asset/fonts.html");
        if(!AwDataUtil.isEmpty(mBean.getContent())) {
            mMvQuestionTxt.setText(mBean.getContent());
        } else {
            mMvQuestionTxt.setText("");
        }


        if(!AwDataUtil.isEmpty(mBean.getAnswer())) {
            //html 设置罗马字体
            mBean.setAnswer("<span style=\"font-family: 'Times New Roman';\"><span style=\"font-family: 'Times New Roman';\">"+mBean.getAnswer()+"</span></span>");
            mMvAnswerContent.setText(mBean.getAnswer());
        } else {
            mMvAnswerContent.setText("");
        }

        if(!AwDataUtil.isEmpty(mBean.getAnswerExplanation())) {
            //html 设置罗马字体
            mBean.setAnswerExplanation("<span style=\"font-family: 'Times New Roman';\"><span style=\"font-family: 'Times New Roman';\">"+mBean.getAnswerExplanation()+"</span></span>");
            mMvResolveContent.setText(mBean.getAnswerExplanation());
        } else {
            mMvResolveContent.setText("");
        }

        if(mBean.getType() != null && mBean.getType().isChoiceQuestion() && null != mBean.getOptions()) {
            mRcvData.setVisibility(View.VISIBLE);
            QuestionBasketOptionsAdapter questionBasketOptionsAdapter = new QuestionBasketOptionsAdapter();
            AwRecyclerViewUtil.setRecyclerViewLinearlayout((Activity) mActivity, mRcvData, questionBasketOptionsAdapter, false);
            setChoiceListData(mBean, questionBasketOptionsAdapter, mRcvData);
        } else {
            mRcvData.setVisibility(View.GONE);
        }

    }

    @Override
    protected void initListener() {
        super.initListener();
        mIvOpen.setOnClickListener(v -> {
            showView(mLlBottomLayout, true);
            showView(mIvOpen, false);
        });
        mIvShrink.setOnClickListener(v -> {
            showView(mLlBottomLayout, false);
            showView(mIvOpen, true);
        });
    }

    /**
     * 选择题选项列表数据填充.
     */
    private void setChoiceListData(SimilarResultBean bean, QuestionBasketOptionsAdapter adapter, RecyclerView rcvData) {
        Map<String, String> mOptionsMap = (Map<String, String>) bean.getOptions();
        List<QuestionOptionBean> mQuestionOptionBeanList = new ArrayList<>();
        int index = 0;
        for (Map.Entry<String, String> entry : mOptionsMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            if(AwDataUtil.isEmpty(entry.getValue())) {
                continue;
            }
            mQuestionOptionBeanList.add(new QuestionOptionBean(QuestionOptionBean.SERIAL_NUMS[index], entry.getValue(), false));
            index++;
        }
        if (AwDataUtil.isEmpty(mQuestionOptionBeanList)) {
            adapter.clearData();
            rcvData.removeAllViews();
            adapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
            mRcvData.setVisibility(View.GONE);
        } else {
            adapter.addAllData(mQuestionOptionBeanList);
            adapter.loadMoreComplete();
            adapter.setEnableLoadMore(false);
            adapter.disableLoadMoreIfNotFullPage(rcvData);
        }
    }
}
