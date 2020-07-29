package com.jkrm.education.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.Result;
import com.gyf.barlibrary.ImmersionBar;
import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.util.AwAPPUtils;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwTextviewUtil;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.hzw.baselib.widgets.AwViewDialog;
import com.hzw.baselib.widgets.AwViewLoadingDialog;
import com.jkrm.education.R;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.AnswerSheetBean;
import com.jkrm.education.bean.result.VideoPointResultBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.ui.activity.login.ChoiceClassesActivity;
import com.jkrm.education.util.UserUtil;
import com.king.zxing.CaptureActivity;
import com.king.zxing.ViewfinderView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import retrofit2.Retrofit;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hzw on 2019/7/8.
 */

public class ScanQrcodeActivity extends CaptureActivity {

    private ViewfinderView mViewfinderView;
    private AwViewCustomToolbar mToolbar;

    private Activity mActivity = ScanQrcodeActivity.this;
    private AwViewLoadingDialog mLoadingDialog;
    protected AwViewDialog mDialog;
    List<VideoPointResultBean> mList=new ArrayList<>();
    AnswerSheetBean mAnswerSheetBean=new AnswerSheetBean();

    private boolean isOpenLight = false;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        ImmersionBar.with(mActivity).fitsSystemWindows(true)
                .statusBarDarkFont(true, 0.2f)
                .statusBarColor(com.hzw.baselib.R.color.white)
                .keyboardEnable(true)
                .init();
        setContentView(R.layout.activity_scan_qrcode);
        initView();
    }

    protected void initView() {
        mViewfinderView = findViewById(R.id.viewfinder_view);
        mToolbar = findViewById(com.hzw.baselib.R.id.toolbar_custom);
        mToolbar.setToolbarTitle("扫一扫");
        mToolbar.setLeftImg(com.hzw.baselib.R.mipmap.icon_back);
        mToolbar.setOnLeftClickListener(() -> finish());
        mToolbar.setRightImgWithSizeValue(com.hzw.baselib.R.mipmap.icon_light_close);
        mToolbar.setOnRightClickListener(() -> {
            if (isOpenLight) {
                mToolbar.setRightImgWithSizeValue(com.hzw.baselib.R.mipmap.icon_light_close);
                offFlash();
            } else {
                mToolbar.setRightImgWithSizeValue(com.hzw.baselib.R.mipmap.icon_light_open);
                openFlash();
            }
        });

//        restartPreviewAndDecode();
        getBeepManager().setPlayBeep(true);
        getBeepManager().setVibrate(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void showLoadingDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mLoadingDialog == null) {
                    mLoadingDialog = new AwViewLoadingDialog(mActivity);
                }
                if (!mLoadingDialog.isShowing()) {
                    mLoadingDialog.show();
                }
            }
        });

    }

    public void dismissLoadingDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                    mLoadingDialog.dismiss();
                }
            }
        });
    }

    protected void initDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
        if (mDialog == null)
            mDialog = new AwViewDialog(mActivity);
    }

    protected void dismissDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    protected void showDialogToFinish(String msg) {
        initDialog();
        mDialog.showDialog(msg, v -> {
            dismissDialog();
            finish();
        }, false);
    }

    public void showDialogWithCancelFinish(String msg, View.OnClickListener confirmListener) {
        initDialog();
        mDialog.showDialog(msg, true, confirmListener, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
                finish();
            }
        });
    }


    protected void showDialogWithLeftAndRightTxt(String msg, String leftTxt, String rightTxt, View.OnClickListener leftListener, View.OnClickListener rightListener) {
        initDialog();
        mDialog.showDialogCustomLeftColor(msg, leftTxt, rightTxt, leftListener, rightListener);
    }

    /**
     * 关闭闪光灯（手电筒）
     */
    private void offFlash() {
        Camera camera = getCameraManager().getOpenCamera().getCamera();
        Camera.Parameters parameters = camera.getParameters();
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(parameters);
        isOpenLight = false;
    }

    /**
     * 开启闪光灯（手电筒）
     */
    public void openFlash() {
        Camera camera = getCameraManager().getOpenCamera().getCamera();
        Camera.Parameters parameters = camera.getParameters();
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(parameters);
        isOpenLight = true;
    }

    /**
     * 接收扫码结果，想支持连扫时，可将{@link #isContinuousScan()}返回为{@code true}并重写此方法
     * 如果{@link #isContinuousScan()}支持连扫，则默认重启扫码和解码器；当连扫逻辑太复杂时，
     * 请将{@link #isAutoRestartPreviewAndDecode()}返回为{@code false}，并手动调用{@link #restartPreviewAndDecode()}
     *
     * @param result 扫码结果
     */
    @Override
    public void onResult(Result result) {
        //        super.onResult(result);
        String stuid=UserUtil.getStudId();
        String s=result.getText();
//        AwToastUtil.showShort(mActivity, "扫描结果: " + result.getText());
        if (result != null && !AwDataUtil.isEmpty(result.getText())) {
            getVideoPointListByTemplate(result.getText().trim());
            // getVideoPointListByTemplate(AwTextviewUtil.replaceBlank(result.getText()).trim());
        } else {
            showDialogWithCancelFinish("扫码失败，是否继续扫码？", v -> {
                dismissDialog();
                restartPreviewAndDecode();
            });
        }

    }

    private void getVideoPointListByTemplate(String template_id) {
        RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getVideoPointListByTemplate(template_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AwApiSubscriber(new AwApiCallback<List<VideoPointResultBean>>() {
                    @Override
                    public void onStart() {
                        showLoadingDialog();
                    }

                    @Override
                    public void onSuccess(List<VideoPointResultBean> model) {
                        if (!AwDataUtil.isEmpty(model)) {
                            showDialogWithLeftAndRightTxt("获取扫码结果成功", "提交作业", "查看微课", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    getAnswerSheet(template_id);
                                   /* dismissDialog();
                                    finish();*/
                                }
                            }, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    startActivity(new Intent(mActivity, VideoPointActivity.class)
                                            .putExtra(Extras.KEY_BEAN_VIDEO_RESULT, (Serializable) model)
                                            .putExtra(Extras.KEY_TEMPLATE_ID, template_id));

                                    dismissDialog();
                                    finish();
                                }
                            });
                            /*showDialogWithLeftAndRightTxt("获取扫码结果成功", "查看习题", "查看微课", v -> {
                                startActivity(new Intent(mActivity, ErrorQuestionStatisticsActivity.class)
                                        .putExtra(Extras.KEY_TEMPLATE_ID, template_id));
                                dismissDialog();
                                finish();
                            }, v -> {
                                startActivity(new Intent(mActivity, VideoPointActivity.class)
                                        .putExtra(Extras.KEY_BEAN_VIDEO_RESULT, (Serializable) model)
                                        .putExtra(Extras.KEY_TEMPLATE_ID, template_id));
                                dismissDialog();
                                finish();
                            });*/
                        } else {
                            getAnswerSheet(template_id);
                           /* showDialogWithLeftAndRightTxt("未获取到微课视频", "查看习题", "继续扫码", v -> {
                                startActivity(new Intent(mActivity, ErrorQuestionStatisticsActivity.class)
                                        .putExtra(Extras.KEY_TEMPLATE_ID, template_id));
                                dismissDialog();
                                finish();
                            }, v -> {
                                dismissDialog();
                                restartPreviewAndDecode();
                            });*/
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        showDialogWithCancelFinish(msg+ template_id + "，是否继续扫码？", v -> {
                            dismissDialog();
                            restartPreviewAndDecode();
                        });
                    }

                    @Override
                    public void onCompleted() {
                        dismissLoadingDialog();
                    }
                }));
    }

    private void getAnswerSheet(String template_id) {
        RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getAnswerSheet(template_id, UserUtil.getAppUser().getStudId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AwApiSubscriber(new AwApiCallback<AnswerSheetBean>() {
                    @Override
                    public void onStart() {
                        showLoadingDialog();
                    }

                    @Override
                    public void onSuccess(AnswerSheetBean data) {
                        Intent intent=null;
                        if("1".equals(data.isStudentAnswer())){
                            intent = new Intent(ScanQrcodeActivity.this, AnswerSituationActivity.class);
                        }else{
                            intent = new Intent(ScanQrcodeActivity.this, AnswerSheetActivity.class);
                        }
                        intent.putExtra(Extras.KEY_ANSWERSHEET,(Serializable) data);
                        intent.putExtra(Extras.KEY_TEMPLATE_ID,template_id);
                        startActivity(intent);
                        finish();
                    }



                    @Override
                    public void onFailure(int code, String msg) {
                        int c=code;
                        //未选择
                        if(600==code){
                            Toast.makeText(ScanQrcodeActivity.this,"请先绑定班级",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(ScanQrcodeActivity.this, ChoiceClassesActivity.class);
                            startActivity(intent);
                            finish();
                            return;
                        }
                        showDialogWithCancelFinish(msg + template_id + "，是否继续扫码？", v -> {
                            dismissDialog();
                            restartPreviewAndDecode();
                        });
                    }

                    @Override
                    public void onCompleted() {
                        dismissLoadingDialog();
                    }
                }));

    }


}
