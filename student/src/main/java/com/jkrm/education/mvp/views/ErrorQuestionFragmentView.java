package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.ErrorQuestionClassifyBean;
import com.jkrm.education.bean.ErrorQuestionDetailBean;
import com.jkrm.education.bean.ErrorQuestionTimeBean;
import com.jkrm.education.bean.ErrorQuestionTimePagedBean;
import com.jkrm.education.ui.activity.ErrorQuestionActivity;

import java.util.List;

import okhttp3.RequestBody;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/3/6 15:27
 */

public interface ErrorQuestionFragmentView {
    interface Presenter extends AwBasePresenter {
        void getByClassify(RequestBody requestBody);
        void getByTime(RequestBody requestBody);
        void getErrorDetail(RequestBody requestBody);
        void getByTimePaged(RequestBody requestBody);
    }

    interface View extends AwBaseView {
        void getByClassifySuccess(List<ErrorQuestionClassifyBean> list);
        void getByClassifyFail(String msg);

        void getByTimeSuccess(List<ErrorQuestionTimeBean> list);
        void getByTimeSFail(String msg);

        void getErrorDetailSuccess(List<ErrorQuestionDetailBean> list);
        void getErrorDetailFail(String msg);

        void getByTimePagedSuccess(ErrorQuestionTimePagedBean bean);
        void getByTimePagedFail(String msg);
    }
}
