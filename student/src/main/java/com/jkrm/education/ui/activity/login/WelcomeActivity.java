package com.jkrm.education.ui.activity.login;

import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import com.hzw.baselib.base.AwBaseActivity;
import com.hzw.baselib.base.AwBaseApplication;
import com.hzw.baselib.bean.FirUpdateBean;
import com.hzw.baselib.bean.UpdateBean;
import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.interfaces.IPermissionListener;
import com.hzw.baselib.util.AwAPPUtils;
import com.hzw.baselib.util.AwFileDownloadUtil;
import com.hzw.baselib.util.AwFirUtil;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.AwNetWatchdog;
import com.hzw.baselib.util.AwRxPermissionUtil;
import com.hzw.baselib.util.AwUpdateUtil;
import com.hzw.baselib.util.AwVersionUtil;
import com.jkrm.education.R;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.AnswerSheetBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.ui.activity.AnswerSheetActivity;
import com.jkrm.education.ui.activity.AnswerSituationActivity;
import com.jkrm.education.ui.activity.MainActivity;
import com.jkrm.education.ui.activity.ScanQrcodeActivity;
import com.jkrm.education.util.UserUtil;

import java.io.Serializable;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * 欢迎页
 * Created by hzw on 2019/5/5.
 */
public class WelcomeActivity extends AwBaseActivity {

    private AwNetWatchdog netWatchdog;

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
        //toNextStep();
        toNextPage();
       /* RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getSafeCode(template_id, UserUtil.getAppUser().getStudId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AwApiSubscriber(new AwApiCallback<String>() {
                    @Override
                    public void onStart() {
                        showLoadingDialog();
                    }

                    @Override
                    public void onSuccess(String data) {

                    }
                    @Override
                    public void onFailure(int code, String msg) {
                       showMsg(msg);
                    }

                    @Override
                    public void onCompleted() {
                        dismissLoadingDialog();
                    }
                }));*/
    }



    @Override
    protected void onDestroy() {
        if(netWatchdog != null) {
            netWatchdog.stopWatch();
        }
        super.onDestroy();
    }

    private void toNextStep() {
        netWatchdog = new AwNetWatchdog(this);
        netWatchdog.setNetConnectedListener(new AwNetWatchdog.NetConnectedListener() {

            @Override
            public void onReNetConnected(boolean isReconnect) {

            }

            @Override
            public void onNetUnConnected() {
                if(AwBaseApplication.downCalls == null || AwBaseApplication.downCalls.size() == 0) {
                    return;
                }
                for (String key : AwBaseApplication.downCalls.keySet()) {
                    AwFileDownloadUtil.get().cancel(mActivity, key);
                }
                showDialogToFinish(getString(R.string.common_network_offline));
            }
        });
        netWatchdog.startWatch();

        if(netWatchdog.hasNet(mActivity)) {
            if(!netWatchdog.is4GConnected(mActivity)) {
                toNextPage();
                //TODO 20190505 暂未上传, 无接口可调用
//                toCheckUpdate();
            } else {
                showDialogToFinish(getString(R.string.common_network_offline));
            }
        }
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

    private void toCheckUpdate() {
        String url = FirUpdateBean.URL + FirUpdateBean.API_TEACHER_ID + "?api_token=" + FirUpdateBean.API_TOKEN + "&type=android";
        AwLog.d("update url: " + url);
        new Thread(() -> {
            AwFirUtil.checkUpdateInfo(url, updateBean -> {
                toHandleCheckResult(updateBean);
            });
        }).start();
    }

    private void toHandleCheckResult(UpdateBean updateBean) {
        runOnUiThread(() -> {
            if(updateBean != null && AwVersionUtil.compareVersions(AwAPPUtils.getAppVersionInfo(mActivity, AwAPPUtils.TYPE_VERSION.TYPE_VERSION_NAME), updateBean.getVersion()) > 0) {
                AwUpdateUtil.getInstance(mActivity).handleUpdate(updateBean, () -> toNextPage());
            } else {
                toNextPage();
            }
        });
    }
}
