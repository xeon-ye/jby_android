package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.result.OssResultBean;

import okhttp3.RequestBody;
import retrofit2.http.Body;

/**
 * @Description: 图片上传
 * @Author: xiangqian
 * @CreateDate: 2020/5/25 10:45
 */

public interface OnlineNonGroupSubjectiveQuestionsFragmentView extends AwBaseView {
    interface Presenter extends AwBasePresenter {
        void uploadOss(String model, String filePath);
    }

    interface View extends AwBaseView {

        void uploadOssSuccess(OssResultBean bean);
        void uploadOssFail(String msg);


    }
}
