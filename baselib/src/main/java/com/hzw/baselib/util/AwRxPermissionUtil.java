package com.hzw.baselib.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;

import com.hzw.baselib.interfaces.IPermissionListener;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;

/**
 * Created by hzw on 2018/7/20.
 */

public class AwRxPermissionUtil {

    private static AwRxPermissionUtil instance;

    /**
     * 读写权限  自己可以添加需要判断的权限
     */
    public static String[] permissionsStorage = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    /**
     * 位置权限
     */
    public static String[] permissionsLocation = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};

    /**
     * 相机权限
     */
    public static String[] permissionsCamera = {
            Manifest.permission.CAMERA};

    /**
     * 蓝牙权限
     */
    public static String[] permissionsBluetooth = {
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN};

    /**
     * 电话权限
     */
    public static String[] permissionsPhone = {
            Manifest.permission.CALL_PHONE};
    /**
     * 判断权限集合
     * permissions 权限数组
     * return true-表示没有改权限  false-表示权限已开启
     */
    public boolean hasStoragePermission (Context mContexts) {
        for (String permission : permissionsStorage) {
            if (lacksPermission(mContexts,permission)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasLocationPermission(Context context) {
        for (String permission : permissionsLocation) {
            if (lacksPermission(context,permission)) {
                return true;
            }
        }
        return false;
    }
    public boolean hasBluetoothPermission(Context context) {
        for (String permission : permissionsBluetooth) {
            if (lacksPermission(context,permission)) {
                return true;
            }
        }
        return false;
    }
    public boolean hasCameraPermission(Context context) {
        for (String permission : permissionsCamera) {
            if (lacksPermission(context,permission)) {
                return true;
            }
        }
        return false;
    }
    public boolean hasPhonePermission(Context context) {
        for (String permission : permissionsPhone) {
            if (lacksPermission(context,permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否缺少权限
     */
    private static boolean lacksPermission(Context mContexts, String permission) {
        return ContextCompat.checkSelfPermission(mContexts, permission) == PackageManager.PERMISSION_GRANTED;
    }

    private AwRxPermissionUtil() {

    }

    public static AwRxPermissionUtil getInstance() {
        if(instance == null) {
            instance = new AwRxPermissionUtil();
        }
        return instance;
    }

    public void checkSinglePermission(Activity activity, String[] permissions, final IPermissionListener listener) {
        RxPermissions rxPermission = new RxPermissions(activity);
        rxPermission.request(permissions)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if(aBoolean) {
                            listener.granted();
                        } else {
                            listener.needToSetting();
                        }
                    }
                });
    }

    public void checkPermission(Activity activity, String[] permissions, final IPermissionListener listener) {
        RxPermissions rxPermission = new RxPermissions(activity);
        rxPermission.requestEach(permissions)
                .subscribe(new Action1<Permission>() {
                    @Override
                    public void call(Permission permission) {
                        if (permission.granted) {
                            listener.granted();
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            listener.shouldShowRequestPermissionRationale();
                        } else {
                            listener.needToSetting();
                        }

                    }
                });
    }

    public static void toAppSetting(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + activity.getPackageName()));
        activity.startActivity(intent);
    }
}
