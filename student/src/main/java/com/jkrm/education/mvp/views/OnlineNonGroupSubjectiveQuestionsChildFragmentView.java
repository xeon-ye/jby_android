package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.result.OssResultBean;

/**
 * @Description:  上传图片
 * @Author: xiangqian
 * @CreateDate: 2020/5/27 10:28
 */

public interface OnlineNonGroupSubjectiveQuestionsChildFragmentView extends AwBaseView {
    interface Presenter extends AwBasePresenter {
        void uploadOss(String model, String filePath);
    }

    interface View extends AwBaseView {

        void uploadOssSuccess(OssResultBean bean);
        void uploadOssFail(String msg);


    }
}
