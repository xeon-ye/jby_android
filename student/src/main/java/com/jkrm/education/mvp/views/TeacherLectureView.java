package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.result.LoginResult;
import com.jkrm.education.bean.result.VideoResultBean;

import okhttp3.RequestBody;


/**
 * Created by hzw on 2018/11/13
 */
public interface TeacherLectureView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getVideos(String params);
    }

    interface View extends AwBaseView {
        void getVideosSuccess(VideoResultBean result);
        void getVideoFail(String msg);
    }

}