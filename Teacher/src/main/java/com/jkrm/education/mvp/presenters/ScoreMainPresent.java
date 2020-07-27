package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.bean.AwResponseListBean;
import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiListCallback;
import com.hzw.baselib.interfaces.AwApiListSubscriber;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwLog;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.result.AnswerSheetDataResultBean;
import com.jkrm.education.bean.result.RowsHomeworkBean;
import com.jkrm.education.bean.result.TeacherClassCouresResultBean;
import com.jkrm.education.mvp.views.ScoreMainFragmentView;

import java.util.List;

import rx.Observable;

public class ScoreMainPresent extends AwCommonPresenter implements ScoreMainFragmentView.Presenter {

    private ScoreMainFragmentView.View mView;

    public ScoreMainPresent(ScoreMainFragmentView.View view) {
        this.mView = view;
    }

    @Override
    public void getTeacherClassAndCourseList(String teacher_id) {
        Observable<ResponseBean<List<TeacherClassCouresResultBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getTeacherClassAndCourseList(teacher_id);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<TeacherClassCouresResultBean>>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(List<TeacherClassCouresResultBean> model) {
                mView.getTeacherClassAndCourseListSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(code==600){
                    mView.needBingSchoolClass();
                }else{
                    mView.getTeacherClassAndCourseListFail(msg);
                }
            }

            @Override
            public void onCompleted() {
            }
        }));
    }

    @Override
    public void getAnswerSheets(String teacher_id, String start_date, String end_date, String class_ids, String course_id, int page_) {
        Observable<AwResponseListBean<AnswerSheetDataResultBean, RowsHomeworkBean>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .answer_sheets(teacher_id, start_date, end_date, class_ids, course_id, page_, APIService.COMMON_PAGE_SIZE);

        addIOSubscription(observable, new AwApiListSubscriber(new AwApiListCallback<AnswerSheetDataResultBean, RowsHomeworkBean>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(AnswerSheetDataResultBean data, List<RowsHomeworkBean> list, int total, int size, int pages, int current) {
                mView.getAnswerSheetsSuccess(data, list, total, size, pages, current);
                AwLog.d("getAnswerSheets success total: " + total + " ,size: " + size + " ,pages: " + pages + " ,current: " + current);
                if(data != null) {
                    AwLog.d("getAnswerSheets success data: " + data.toString());
                } else {
                    AwLog.d("getAnswerSheets success data is null");
                }
                if(list != null) {
                    AwLog.d("getAnswerSheets success list: " + list.size());
                } else {
                    AwLog.d("getAnswerSheets success list is null");
                }

            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    return;
                }
                if(code==600){
                    mView.needBingSchoolClass();
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getAnswerSheets(teacher_id, start_date, end_date, class_ids, course_id, page_);
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
