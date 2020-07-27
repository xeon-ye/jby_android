package com.hzw.baselib.util;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hzw.baselib.R;
import com.hzw.baselib.base.AwBaseApplication;
import com.hzw.baselib.bean.UpdateBean;
import com.hzw.baselib.interfaces.IPermissionListener;
import com.hzw.baselib.interfaces.IUpdateCancelInterface;
import com.hzw.baselib.widgets.AwViewDialog;

import java.io.File;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by hzw on 2018/6/4.
 */

public class AwUpdateUtil {

    private static Activity mActivity;
    private static AwUpdateUtil instance;
    protected static AwViewDialog mDialog;

    public static AwUpdateUtil getInstance(Activity activity) {
        if(instance == null) {
            return new AwUpdateUtil(activity);
        }
        showDownloadDialog();
        return instance;
    }

    public AwUpdateUtil(Activity activity) {
        mActivity = activity;
        showDownloadDialog();
    }

    private static void showDownloadDialog() {
        mDialog = new AwViewDialog(mActivity);
    }

    public static void handleUpdate(final UpdateBean updateBean, final IUpdateCancelInterface iUpdateCancelInterface) {
        // 需要版本更新 展示弹框
        String textContent = "版本号：" + updateBean.getVersion() + "\n更新内容：\n" + updateBean.getUpdateContent();
        mDialog.showUpdateDialog(textContent, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                AwRxPermissionUtil.getInstance().checkPermission(mActivity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new IPermissionListener() {
                            @Override
                            public void granted() {
                                if(AwBaseApplication.netWatchdog.hasNet(mActivity)) {
                                    if(AwBaseApplication.netWatchdog.is4GConnected(mActivity)) {
                                        mDialog.showDialog("您当前使用的不是wifi，更新会产生一些网络流量，是否继续下载？", true,
                                                new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        mDialog.dismiss();
                                                        downloadFile(mActivity, updateBean.getUrl(), iUpdateCancelInterface);
                                                    }
                                                }, new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        mDialog.dismiss();
                                                    }
                                                });
                                    } else {
                                        downloadFile(mActivity, updateBean.getUrl(), iUpdateCancelInterface);
                                    }
                                }

                            }

                            @Override
                            public void shouldShowRequestPermissionRationale() {
                                Toast.makeText(mActivity, "APP版本更新失败，请您在系统设置授予APP存储权限后才可正常下载安装", Toast.LENGTH_LONG).show();
                                iUpdateCancelInterface.cancelUpdate();
//                                mActivity.finish();
                            }

                            @Override
                            public void needToSetting() {
                                Toast.makeText(mActivity, "APP版本更新失败，请您在系统设置授予APP存储权限后才可正常下载安装", Toast.LENGTH_LONG).show();
                                iUpdateCancelInterface.cancelUpdate();
//                                AwSystemIntentUtil.toSelfSetting(mActivity);
                            }
                        });
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                iUpdateCancelInterface.cancelUpdate();
            }
        });
    }

    private static void downloadFile(final Activity activity, final String url, final IUpdateCancelInterface iUpdateCancelInterface) {
        mDialog.showDownloadDialog();
        final ProgressBar pb_loading = (ProgressBar) mDialog.getDialog().findViewById(R.id.pb_loading);
        TextView btn_cancel = (TextView) mDialog.getDialog().findViewById(R.id.btn_cancel);
        final TextView tv_progress = (TextView) mDialog.getDialog().findViewById(R.id.tv_progress);
        pb_loading.setMax(100);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AwFileDownloadUtil.get().cancel(activity, url);
                mDialog.dismiss();
                iUpdateCancelInterface.cancelUpdate();
            }
        });
        AwFileDownloadUtil.get().downApk(activity, url, new AwFileDownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(String path) {
                Observable.just(path).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                Toast.makeText(activity, "下载成功", Toast.LENGTH_SHORT).show();
                                mDialog.dismiss();
                                File apkFile = new File(s);
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    // Log.w(TAG, "版本大于 N ，开始使用 fileProvider 进行安装");
                                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                    Uri contentUri = FileProvider.getUriForFile(
                                            activity
                                            , AwAPPUtils.getPackageName(activity) + ".base.fileProvider"
                                            , apkFile);
                                    intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                                } else {
                                    //Log.w(TAG, "正常进行安装");
                                    intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
                                }
                                activity.startActivity(intent);
                                android.os.Process.killProcess(android.os.Process.myPid());
                            }
                        });
            }

            @Override
            public void onDownloading(final int progress) {
                Observable.just(progress).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Integer>() {
                            @Override
                            public void call(Integer integer) {
                                pb_loading.setProgress(progress);
                                tv_progress.setText(progress + "%");

                            }
                        });
            }

            @Override
            public void onDownloadFailed(String path) {
                Observable.just(0).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Integer>() {
                            @Override
                            public void call(Integer integer) {
                                Toast.makeText(activity, "下载失败", Toast.LENGTH_SHORT).show();
                                AwFileDownloadUtil.get().cancel(activity, url);
                                mDialog.dismiss();
                                iUpdateCancelInterface.cancelUpdate();
                            }
                        });

            }

            @Override
            public void onDownloadIngRejectRepeat() {

            }
        });
    }
}
