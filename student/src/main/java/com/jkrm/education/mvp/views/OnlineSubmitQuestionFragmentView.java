package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.SaveReinforceBean;
import com.jkrm.education.bean.result.PracticeDataResultBean;
import com.jkrm.education.bean.result.VideoResultBean;

import okhttp3.RequestBody;

/**
 * @Description: 提交作答
 * @Author: xiangqian
 * @CreateDate: 2020/5/25 15:45
 */

public interface OnlineSubmitQuestionFragmentView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        //提交答案
       void saveReinforce(RequestBody requestBody);
    }

    interface View extends AwBaseView {
        void saveReinforceSuccess(SaveReinforceBean model);
        void saveReinforceFail(String msg);
    }
}
