package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.ReinfoRoceCouseBean;
import com.jkrm.education.bean.ReinforBean;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.result.WatchLogBean;

import java.util.List;

import okhttp3.RequestBody;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/6/1 9:57
 */

public interface AnswerRecordView extends AwBaseView {
    interface Presenter extends AwBasePresenter {
        void getReinforceCourseList(String studentId);
        void getReinforceList(RequestBody requestBody);
    }

    interface View extends AwBaseView {
        void getReinforceCourseListSucces(List<ReinfoRoceCouseBean> list);
        void getReinforceCourseListFail(String msg);

        void getReinforceListSuccess(ReinforBean data);
        void getReinforceListFail(String msg);
    }
}
