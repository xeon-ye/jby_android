package com.jkrm.education.ui.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.jkrm.education.R;
import com.jkrm.education.adapter.QuestionBasketOptionsAdapter;
import com.jkrm.education.bean.QuestionOptionBean;
import com.jkrm.education.bean.result.QuestionResultBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.presenters.SeeTargetQuestionPresent;
import com.jkrm.education.mvp.views.SeeTargetQuestionView;
import com.zzhoujay.richtext.RichText;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.github.kexanie.library.MathView;

/**
 * 查看某题(原题等)
 * Created by hzw on 2019/5/24.
 */

@SuppressLint("SetJavaScriptEnabled")
public class SeeTargetQuestionActivity extends AwMvpActivity<SeeTargetQuestionPresent> implements SeeTargetQuestionView.View {

    @BindView(R.id.iv_img)
    ImageView mIvImg;
    @BindView(R.id.mv_questionTxt)
    MathView mMvQuestionTxt;
    @BindView(R.id.iv_open)
    ImageView mIvOpen;
    @BindView(R.id.mv_answerContent)
    MathView mMvAnswerContent;
    @BindView(R.id.mv_resolveContent)
    MathView mMvResolveContent;
    @BindView(R.id.tv_judgeLogic)
    TextView mTvJudgeLogic;
    @BindView(R.id.iv_shrink)
    ImageView mIvShrink;
    @BindView(R.id.ll_bottomLayout)
    LinearLayout mLlBottomLayout;
    @BindView(R.id.ll_judgeScoreLayout)
    LinearLayout mLlJudgeScoreLayout;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    @BindView(R.id.view_noData)
    RelativeLayout mViewNoData;

    private String imgUrl = "";
    private String questionId = "";

    @Override
    protected SeeTargetQuestionPresent createPresenter() {
        return new SeeTargetQuestionPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_homework_see_answer;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusTxtDark();
        setToolbarWithBackImg("查看原题", null);

        //默认打开
        showView(mLlBottomLayout, true);
        showView(mIvOpen, false);

    }

    @Override
    protected void initData() {
        super.initData();
        //TODO 20190704 取消展示判分逻辑(需求修改了)
//        boolean isShowJudgeScore = getIntent().getBooleanExtra(Extras.COMMON_BOOLEAN, false);
//        showView(mLlJudgeScoreLayout, isShowJudgeScore);

        questionId = getIntent().getStringExtra(Extras.COMMON_PARAMS);
        if(AwDataUtil.isEmpty(questionId)) {
            showDialogToFinish("题目id获取失败");
        }
        mPresenter.getQuestion(questionId);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mIvImg.setOnClickListener(v -> toClass(ImgActivity.class, false, Extras.IMG_URL, imgUrl));
        mIvOpen.setOnClickListener(v -> {
            showView(mLlBottomLayout, true);
            showView(mIvOpen, false);
        });
        mIvShrink.setOnClickListener(v -> {
            showView(mLlBottomLayout, false);
            showView(mIvOpen, true);
        });
    }

    private final class JSInterface{
        @SuppressLint("JavascriptInterface")
        @JavascriptInterface
        public void openImage(String img) {
            toClass(ImgActivity.class, false, Extras.IMG_URL, img);
        }
    }

    @SuppressLint("JavascriptInterface")
    @Override
    public void getQuestionSuccess(QuestionResultBean resultBean) {
        if(resultBean != null) {
            showView(mViewNoData, false);
//            AwLog.d("getQuestionSuccess BEAN: " + resultBean.toString());

            if(!AwDataUtil.isEmpty(resultBean.getContent())) {
                mMvQuestionTxt.setText(resultBean.getContent());
//                mMvQuestionTxt.getSettings().setJavaScriptEnabled(true);
//                mMvQuestionTxt.addJavascriptInterface(new JSInterface(), "imagelistner");
//                mMvQuestionTxt.setWebViewClient(mWebViewClient);
            } else {
                mMvQuestionTxt.setText("");
            }


            if(!AwDataUtil.isEmpty(resultBean.getAnswer())) {
                resultBean.setAnswer("<span style=\"font-family: 'Times New Roman';\"><span style=\"font-family: 'Times New Roman';\">"+resultBean.getAnswer()+"</span></span>");
                mMvAnswerContent.setText(resultBean.getAnswer());
            } else {
                mMvAnswerContent.setText("");
            }

            if(!AwDataUtil.isEmpty(resultBean.getAnswerExplanation())) {
                mMvResolveContent.setText(resultBean.getAnswerExplanation());
            } else {
                mMvResolveContent.setText("");
            }

            setText(mTvJudgeLogic, "");

            if(resultBean.getType() != null && resultBean.getType().isChoiceQuestion() && null != resultBean.getOptions()) {
                mRcvData.setVisibility(View.VISIBLE);
                QuestionBasketOptionsAdapter questionBasketOptionsAdapter = new QuestionBasketOptionsAdapter();
                AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, questionBasketOptionsAdapter, false);
                setChoiceListData(resultBean, questionBasketOptionsAdapter, mRcvData);
            } else {
                mRcvData.setVisibility(View.GONE);
            }
        } else {
            showView(mViewNoData, true);
        }

    }

    @Override
    public void getQuestionFail(String msg) {
        showView(mViewNoData, true);
    }

    @Override
    protected void onDestroy() {
        RichText.recycle();
        super.onDestroy();
    }

    /**
     * 选择题选项列表数据填充.
     */
    private void setChoiceListData(QuestionResultBean bean, QuestionBasketOptionsAdapter adapter, RecyclerView rcvData) {
        Map<String, String> mOptionsMap = (Map<String, String>) bean.getOptions();
        List<QuestionOptionBean>  mQuestionOptionBeanList = new ArrayList<>();
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

    WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            showLoadingDialog();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            dismissLoadingDialog();
            view.loadUrl("javascript:(function(){"
                    + "var objs = document.getElementsByTagName(\"img\"); "
                    + "for(var i=0;i<objs.length;i++)  " + "{"
                    + "    objs[i].onclick=function()  " + "    {  "
                    + "        window.imagelistner.openImage(this.src);  "
                    + "    }  " + "}" + "})()");
        }
    };
}
