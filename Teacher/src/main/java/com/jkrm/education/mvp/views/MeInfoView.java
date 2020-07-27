package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.request.RequestClassesBean;
import com.jkrm.education.bean.result.AnswerSheetProgressResultBean;
import com.jkrm.education.bean.result.HomeworkDetailResultBean;
import com.jkrm.education.bean.result.OssResultBean;
import com.jkrm.education.bean.result.StudentSingleQuestionAnswerResultBean;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Body;


/**
 * Created by hzw on 2018/11/13
 */
public interface MeInfoView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void uploadOss( String model, String filePath);
        void updateAvatar(@Body RequestBody requestBody);
        void getClassesById(String teacherId);
    }

    interface View extends AwBaseView {

        void uploadOssSuccess(OssResultBean bean);
        void uploadOssFail(String msg);

        void updateAvatarSuccess(String msg);
        void updateAvatarFail(String msg);

        void getClassesByIdSuccess(List<RequestClassesBean> data);
        void getClassesByIdFail(String msg);
    }

}