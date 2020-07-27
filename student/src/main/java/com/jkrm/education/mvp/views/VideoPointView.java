package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.result.ErrorQuestionResultBean;
import com.jkrm.education.bean.result.TemplateInfoResultBean;

import java.util.List;

import okhttp3.RequestBody;


/**
 * Created by hzw on 2018/11/13
 */
public interface VideoPointView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void templateInfo(String templateId);
    }

    interface View extends AwBaseView {

        void templateInfoSuccess(TemplateInfoResultBean result);
    }

}