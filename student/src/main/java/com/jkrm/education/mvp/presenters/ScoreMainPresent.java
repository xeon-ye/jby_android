package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiListCallback;
import com.hzw.baselib.interfaces.AwApiListSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwLog;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.common.ResponseListBean;
import com.jkrm.education.bean.result.AnswerSheetDataResultBean;
import com.jkrm.education.bean.result.RowsHomeworkBean;
import com.jkrm.education.bean.test.TestMarkBean;
import com.jkrm.education.mvp.views.ScoreMainFragmentView;
import com.jkrm.education.util.TestDataUtil;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

public class ScoreMainPresent extends AwCommonPresenter implements ScoreMainFragmentView.Presenter {

    private ScoreMainFragmentView.View mView;

    public ScoreMainPresent(ScoreMainFragmentView.View view) {
        this.mView = view;
    }

    @Override
    public void getAnswerSheets(String student_id, String start_date, String end_date, String class_id, String course_id, int page_) {
        Observable<ResponseListBean<AnswerSheetDataResultBean, RowsHomeworkBean>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .answer_sheets(student_id, start_date, end_date, class_id, course_id, page_, APIService.COMMON_PAGE_SIZE);

        addIOSubscription(observable, new AwApiListSubscriber(new AwApiListCallback<AnswerSheetDataResultBean, RowsHomeworkBean>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(AnswerSheetDataResultBean data, List<RowsHomeworkBean> list, int total, int size, int pages, int current) {
                mView.getAnswerSheetsSuccess(data, list, total, size, pages, current);
//                AwLog.d("getAnswerSheets success total: " + total + " ,size: " + size + " ,pages: " + pages + " ,current: " + current);
//                if(data != null) {
//                    AwLog.d("getAnswerSheets success data: " + data.toString());
//                } else {
//                    AwLog.d("getAnswerSheets success data is null");
//                }
//                if(list != null) {
//                    AwLog.d("getAnswerSheets success list: " + list.size());
//                } else {
//                    AwLog.d("getAnswerSheets success list is null");
//                }

            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getAnswerSheets(student_id, start_date, end_date, class_id,  course_id, page_);
                } else {
                    mView.getAnswerSheetsFail(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }
}
