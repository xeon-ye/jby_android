package com.jkrm.education.ui.activity.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

import com.hzw.baselib.base.AwBaseActivity;
import com.hzw.baselib.base.AwBaseApplication;
import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.bean.UpdateBean;
import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.util.AwAPPUtils;
import com.hzw.baselib.util.AwMd5Util;
import com.hzw.baselib.util.AwUpdateUtil;
import com.hzw.baselib.util.AwVersionUtil;
import com.jkrm.education.R;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.api.UrlInterceptor;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.result.VersionResultBean;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.presenters.MainPresent;
import com.jkrm.education.mvp.views.MainView;
import com.jkrm.education.ui.activity.MainActivity;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * 启动页
 * Created by hzw on 2019/5/5.
 */
public class WelcomeActivity extends AwMvpActivity<MainPresent> implements MainView.View  {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void initView() {
        setStatusTxtDark();
        mPresenter.getVersionInfo();
        toNextPage();
        String cli = UrlInterceptor.getCli();
        String t = UrlInterceptor.getT();
        RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getSafeCode(cli, t,"android", AwMd5Util.md5("android"+cli+ t))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AwApiSubscriber(new AwApiCallback<String>() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(String data) {
                        //  MyApp.getInstance().saveSafeCode(data);
                        SharedPreferences sharedPreferences=mActivity.getSharedPreferences(MyConstant.SPConstant.KEY_SAFE, Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = sharedPreferences.edit();
                        edit.putString( MyConstant.SPConstant.KEY_SAFE,data);
                        edit.commit();
                    }
                    @Override
                    public void onFailure(int code, String msg) {
                        showMsg(msg);
                    }

                    @Override
                    public void onCompleted() {
                    }
                }));
    }

    private void toNextPage() {
        new Handler().postDelayed(() -> {
//            if (!AwSpUtil.getBoolean(MyConstant.SPConstant.XML_APP_INFO, MyConstant.SPConstant.KEY_IS_FIRST, false)) {
//                toClass(GuideActivity.class, true);
//            } else {
//                if (MyApp.getInstance().getAppUser() != null) {
//                    toClass(MainActivity.class, true);
//                } else {
//                    toClass(LoginActivity.class, true);
//                }
//            }
            //TODO 需求貌似没有引导页, 取消进入引导页逻辑
            if (MyApp.getInstance().getAppUser() != null) {
                toClass(MainActivity.class, true);
            } else {
                toClass(PswLoginActivity.class, true);
            }
        }, 600);
    }


    @Override
    protected MainPresent createPresenter() {
        return new MainPresent(this);
    }

    @Override
    public void getVersionInfoSuccess(VersionResultBean bean) {
        if(bean != null && AwVersionUtil.compareVersions(AwAPPUtils.getAppVersionInfo(mActivity, AwAPPUtils.TYPE_VERSION.TYPE_VERSION_NAME), bean.getVersion()) > 0) {
            if(AwBaseApplication.netWatchdog.hasNet(mActivity)) {
                UpdateBean updateBean = new UpdateBean();
                updateBean.setVersion(bean.getVersion());
                updateBean.setUpdateContent(bean.getUpdateContent());
                updateBean.setUrl(bean.getUrl());
                AwUpdateUtil.getInstance(mActivity).handleUpdate(updateBean, () -> {});
            }
        }
    }
}
