package com.hzw.baselib.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spanned;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.hzw.baselib.R;
import com.hzw.baselib.bean.RxLoginRemoteLoginType;
import com.hzw.baselib.bean.RxLoginTimeOutType;
import com.hzw.baselib.constants.AwBaseConstant;
import com.hzw.baselib.util.AwBtnClickUtil;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.AwStatusBarUtil;
import com.hzw.baselib.util.AwToastUtil;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.hzw.baselib.widgets.AwViewDialog;
import com.hzw.baselib.widgets.AwViewLoadingDialog;
import com.hzw.baselib.widgets.CustomDialog;
import com.jph.takephoto.app.TakePhotoFragmentActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hzw on 2017/11/29.
 */

public abstract class AwBaseActivity extends TakePhotoFragmentActivity implements AwBaseView, View.OnClickListener {
    protected Activity mActivity = AwBaseActivity.this;
    public String TAG = AwBaseActivity.class.getSimpleName();
    private AwViewLoadingDialog mLoadingDialog;
    protected AwViewDialog mDialog;
    private Unbinder unbinder;
    //    protected CompositeSubscription mCompositeSubscription;

    protected AwViewCustomToolbar mToolbar;
    protected abstract int getLayoutId();
    protected void initView() {}
    protected void initData() {}
    protected void initListener() {}
    protected void reLogin() {}
    private FragmentManager mFragmentManager;
    private Handler mHandler = new Handler();
    private static Toast mToast = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O && isTranslucentOrFloating()) {
            boolean result = fixOrientation();
        }
        super.onCreate(savedInstanceState);
        TAG = getClass().getSimpleName();
        if(AwBaseConstant.LAYOUT_TRANSPARENT != getLayoutId())
            setContentView(getLayoutId());
        mActivity = this;
        mFragmentManager = getSupportFragmentManager();
        unbinder = ButterKnife.bind(this);
        setStatusTransparent();
        initView();
        initData();
        initListener();
        EventBus.getDefault().register(this);
    }

    /**
     * 限制app字体大小跟随系统问题
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }

    /**
    * 重写getResources()方法，让APP的字体不受系统设置字体大小影响
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }


    public FragmentManager getMyFragmentManager() {
        return mFragmentManager;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshByBus(RxLoginTimeOutType type) {
        showDialog("登录过期，请重新登录", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reLogin();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void remoteLogin(RxLoginRemoteLoginType type){
        showDialog("您的账号已在其他地方登录\n\t请重新登录", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reLogin();
            }
        });
    }

    @Override
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

    @Override
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

    protected void dismissDialog() {
        if(mDialog != null) {
            mDialog.dismiss();
        }
    }

    protected void initDialog() {
        if(mDialog != null) {
            mDialog.dismiss();
        }
        if(mDialog == null)
            mDialog = new AwViewDialog(mActivity);
    }

    protected void showDialogToFinish(String msg) {
        initDialog();
        mDialog.showDialog(msg, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
                finish();
            }
        }, false);
    }

    protected void showDialogToBack(String msg) {
        initDialog();
        mDialog.showDialog(msg, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
                fragmentToBack();
            }
        }, false);
    }

    public void showDialog(String msg) {
        initDialog();
        mDialog.showMsgDialog(msg);
    }

    public void showToastDialog(String msg) {
        mHandler.removeCallbacks(mRunnable);
        initDialog();
        mDialog.showToastDialog(msg);
        mHandler.postDelayed(mRunnable, 2000);
    }

    public void showToastDialog2(String msg) {
        mHandler.removeCallbacks(mRunnable);
        initDialog();
        mDialog.showToastDialog2(msg);
        mHandler.postDelayed(mRunnable, 2000);
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            dismissDialog();
        }
    };

    public void showDialog(String msg, View.OnClickListener listener) {
        initDialog();
        mDialog.showDialog(msg, listener, false);
    }

    public void showDialogWithCancelDismiss(String msg, View.OnClickListener confirmListener) {
        initDialog();
        mDialog.showDialog(msg, true, confirmListener, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
            }
        });
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

    public void showDialogCustomLeftAndRight(String msg, String leftTxt, String rightTxt, View.OnClickListener leftListener, View.OnClickListener rightListener) {
        initDialog();
        mDialog.showDialog(msg, leftTxt, rightTxt, leftListener, rightListener);
    }

    public void showInputDialog(String title, String hintMsg, AwViewDialog.InputCallback callback) {
        initDialog();
        mDialog.showInputDialog(title, hintMsg, callback);
    }

    public void showInputDialogWithOld(String title, String text, AwViewDialog.InputCallback callback) {
        initDialog();
        mDialog.showInputDialogWithOld(title, text, callback);
    }


    @Override
    public void showMsg(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                AwToastUtil.getInsance().show(msg);
                if (mToast == null) {
                    mToast = Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT);
                } else {
                    mToast.setText(msg);
                    mToast.setDuration(Toast.LENGTH_SHORT);
                }

                mToast.show();
            }
        });

    }

    public void showMsg(final int msgId) {
//        showToastDialog(getResources().getString(msgId));
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                AwToastUtil.getInsance().show(msgId);
//                Toast.makeText(mActivity, getString(msgId), Toast.LENGTH_SHORT).show();
                if (mToast == null) {
                    mToast = Toast.makeText(mActivity, getString(msgId), Toast.LENGTH_SHORT);
                } else {
                    mToast.setText(getString(msgId));
                    mToast.setDuration(Toast.LENGTH_SHORT);
                }

                mToast.show();
            }
        });

    }

    public  void showMsgInCenter(final String msg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                AwToastUtil.getInsance().show(msg);
                if (mToast == null) {
                    mToast = Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT);
                } else {
                    mToast.setText(msg);
                    mToast.setDuration(Toast.LENGTH_SHORT);
                }
                mToast.setGravity(Gravity.CENTER,0,0);

                mToast.show();
            }
        });
    }


    public String formatString(String params1, String params2, boolean isSuccess) {
        if(isSuccess)
            return String.format(getString(R.string.common_success_log), params1, params2);
        else
            return String.format(getString(R.string.common_failure_log), params1, params2);
    }

    @Override
    public void onClick(View v) {
        if(AwBtnClickUtil.isFastDoubleClick(v.getId())) {
            return;
        }
    }

    /**
     * 跳转activity
     *
     * @param clazz
     * @param params 传值格式  key,value
     */
    public void toClass(Class clazz, boolean needFinish, Object... params) {
        if (params != null) {
            startActivity(getActivityIntent(clazz, params));
            if(needFinish)
                finish();
        }
    }

    /**
     * 跳转activity(不传参)
     *
     * @param clazz
     */
    public void toClass(Class clazz, boolean needFinish) {
        startActivity(getActivityIntent(clazz, new Object[0]));
        if(needFinish)
            finish();
    }

    public void toClassForResult(Class clazz, int requestCode) {
        startActivityForResult(getActivityIntent(clazz, new Object[0]), requestCode);
    }

    public void toClassForResult(Class clazz, int requestCode, Object... params) {
        startActivityForResult(getActivityIntent(clazz, params), requestCode);
    }

    private Intent getActivityIntent(Class var1, Object[] var2) {
        Intent var3 = new Intent(this, var1);
        if(var2 == null || var2.length == 0) {
            return var3;
        } else {
            int var4 = var2.length;
            if(var4 % 2 != 0) {
                showMsg("参数格式为key,value");
            } else {
                var4 /= 2;

                for(int var5 = 0; var5 < var4; ++var5) {
                    Object var6 = var2[var5 + var5];
                    Object var7 = var2[var5 + var5 + 1];
                    if(!(var6 instanceof String)) {
                        showMsg("参数key类型不对");
                        return var3;
                    }

                    String var8 = (String)var6;
                    if(var7 instanceof String) {
                        var3.putExtra(var8, (String)var7);
                    } else if(var7 instanceof Integer) {
                        var3.putExtra(var8, ((Integer)var7).intValue());
                    } else if(var7 instanceof Boolean) {
                        var3.putExtra(var8, ((Boolean)var7).booleanValue());
                    } else if(var7 instanceof Parcelable) {
                        var3.putExtra(var8, (Parcelable)var7);
                    } else if(var7 instanceof Serializable) {
                        var3.putExtra(var8, (Serializable)var7);
                    }
                }
            }

            return var3;
        }
    }

    /**
     * 跳转fragment
     *
     * @param params 传值格式  key,value
     */
    public void toClass(int viewId, Fragment fragment, boolean addBackStack, Object... params) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        boolean isTagNull = null == mFragmentManager.findFragmentByTag(fragment.getClass().getName());
        AwLog.d("toClass, fragment: " + fragment.getClass().getName() + " ,isAdded: " + fragment.isAdded() + " ,findTag is null?: " + isTagNull);
        if(!fragment.isAdded() && null == mFragmentManager.findFragmentByTag(fragment.getClass().getName())) {
            mFragmentManager.executePendingTransactions();
            if(params != null) {
                try {
                    fragment.setArguments(getFragmentBundle(params));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if(addBackStack) {
                transaction.add(viewId, fragment, fragment.getClass().getName());
                transaction.addToBackStack(fragment.getClass().getName());
            } else {
                transaction.replace(viewId, fragment, fragment.getClass().getName());
            }
            transaction.commitAllowingStateLoss();
        } else {
            AwLog.d("toClassChild, fragment: " + fragment.getClass().getName() + " 已经存在, 直接展示出来");
            transaction.show(fragment).commitAllowingStateLoss();
        }
    }

    /**
     * 跳转fragment(不传参)
     */
    public void toClass(int viewId, Fragment fragment, boolean addBackStack) {
        toClass(viewId, fragment, addBackStack, new Object[0]);
    }

    public Bundle getFragmentBundle(Object[] var2) {
        Bundle bundle = new Bundle();
        if(var2 == null || var2.length == 0) {
            return bundle;
        } else {
            int var4 = var2.length;
            if(var4 % 2 != 0) {
                showMsg("参数格式为key,value");
            } else {
                var4 /= 2;

                for(int var5 = 0; var5 < var4; ++var5) {
                    Object var6 = var2[var5 + var5];
                    Object var7 = var2[var5 + var5 + 1];
                    if(!(var6 instanceof String)) {
                        showMsg("参数key类型不对");
                        return bundle;
                    }

                    String var8 = (String)var6;
                    if(var7 instanceof String) {
                        bundle.putString(var8, (String)var7);
                    } else if(var7 instanceof Integer) {
                        bundle.putInt(var8, ((Integer)var7).intValue());
                    } else if(var7 instanceof Boolean) {
                        bundle.putBoolean(var8, ((Boolean)var7).booleanValue());
                    } else if(var7 instanceof Parcelable) {
                        bundle.putParcelable(var8, (Parcelable)var7);
                    } else if(var7 instanceof Serializable) {
                        bundle.putSerializable(var8, (Serializable)var7);
                    }
                }
            }

            return bundle;
        }
    }

    public Fragment getVisibleFragment() {
        @SuppressLint("RestrictedApi") List<Fragment> fragments = mFragmentManager.getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (Fragment fragment : fragments) {
                if (fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
        toBack();
    }

    public void toBack() {
        if (hasPopBackStack()) {
            mFragmentManager.popBackStack();
        } else {
            finish();
        }
    }

    public boolean hasPopBackStack() {
        return mFragmentManager.getBackStackEntryCount() > 0;
    }

    public void fragmentToBack() {
        mFragmentManager.popBackStack();
    }

    public void popAllToBack() {
        int count = mFragmentManager.getBackStackEntryCount();
        AwLog.d("popAllToBack count: " + count);
        if(count > 0) {
            for (int i = 0; i < count; ++i) {
                FragmentManager.BackStackEntry backstatck = mFragmentManager.getBackStackEntryAt(i);
                AwLog.d("popAllToBack Fragment", backstatck.getName());
                mFragmentManager.popBackStack();
            }
//            getSupportFragmentManager().popBackStackImmediate(fragment.getClass().getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);

        }
    }

    /**
     * 强制退出程序
     */
    public void ExitAppForced() {
        AwBaseApplication.getInstance().cancelNetWatchdog();
        dismissLoadingDialog();
        //        ActCollector.release();
        //        mApp.clearService();
        finishAffinity();
        System.exit(0);
    }

    public void replaceFragment(int id, Fragment fragment, boolean isAddBack) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.replace(id, fragment);
        if (isAddBack) {
            ft.addToBackStack(null);
        }
        ft.commitAllowingStateLoss();
    }

    public void replaceFragmentArgs(int id, Fragment fragment, Object obj,
                                    boolean isAddBack) {
        Bundle b = new Bundle();
        b.putSerializable("params", (Serializable) obj);
        fragment.setArguments(b);
        replaceFragment(id, fragment, isAddBack);
    }

    protected void setToolbar(String title, AwViewCustomToolbar.OnLeftClickListener leftClickListener) {
        mToolbar = (AwViewCustomToolbar) findViewById(R.id.toolbar_custom);
        if(mToolbar != null) {
            mToolbar.hideRightView();
            mToolbar.setLeftImg(R.mipmap.icon_back);
            if(leftClickListener == null) {
                mToolbar.setOnLeftClickListener(new AwViewCustomToolbar.OnLeftClickListener() {
                    @Override
                    public void onLeftTextClick() {
                        finish();
                    }
                });
            } else {
                mToolbar.setOnLeftClickListener(leftClickListener);
            }
            if(!AwDataUtil.isEmpty(title)) {
                mToolbar.setToolbarTitle(title);
            } else {
                mToolbar.hideTitleView();
            }
        }
    }

    protected void setToolbarWithBackImg(String title, AwViewCustomToolbar.OnLeftClickListener leftClickListener) {
        setToolbar(title, leftClickListener);
        if(mToolbar != null) {
            mToolbar.setLeftImg(R.mipmap.icon_back);
            mToolbar.hideRightView();
        }
    }

    protected void setToolbarHideLeftAndRight(String title, AwViewCustomToolbar.OnLeftClickListener leftClickListener) {
        setToolbar(title, leftClickListener);
        if(mToolbar != null) {
            mToolbar.hideLeftView();
            mToolbar.hideRightView();
        }
    }

    protected void setToolbarWithBackImgAndRightView(String title, String rightTxt, AwViewCustomToolbar.OnRightClickListener rigthClickListener) {
        mToolbar = (AwViewCustomToolbar) findViewById(R.id.toolbar_custom);
        if(mToolbar != null) {
            if(!AwDataUtil.isEmpty(title)) {
                mToolbar.setToolbarTitle(title);
            } else {
                mToolbar.hideTitleView();
            }
            mToolbar.setLeftImg(R.mipmap.icon_back);
            mToolbar.setRightText(rightTxt);
            mToolbar.setOnLeftClickListener(new AwViewCustomToolbar.OnLeftClickListener() {
                @Override
                public void onLeftTextClick() {
                    finish();
                }
            });
            if(null != rigthClickListener)
                mToolbar.setOnRightClickListener(rigthClickListener);
        }
    }

    protected void setToolbarWithBackImgAndRightImg(String title, AwViewCustomToolbar.OnRightClickListener rigthClickListener) {
        mToolbar = (AwViewCustomToolbar) findViewById(R.id.toolbar_custom);
        if(mToolbar != null) {
            if(!AwDataUtil.isEmpty(title)) {
                mToolbar.setToolbarTitle(title);
            } else {
                mToolbar.hideTitleView();
            }
            mToolbar.setLeftImg(R.mipmap.icon_back);
//            mToolbar.setRightImg(R.mipmap.search_icon);
            mToolbar.setRightImgWithSizeValue(R.mipmap.search_icon);
            mToolbar.setOnLeftClickListener(new AwViewCustomToolbar.OnLeftClickListener() {
                @Override
                public void onLeftTextClick() {
                    finish();
                }
            });
            if(null != rigthClickListener)
                mToolbar.setOnRightClickListener(rigthClickListener);
        }
    }

    protected void setStatusColor(int colorRes) {
        if(mToolbar != null) {
            mToolbar.setBackgroundColor(colorRes);
            AwStatusBarUtil.setColor(mActivity, colorRes, 0);//0 透明 255 黑色
        }
    }

    protected void setToolbarTitleColor(int colorRes) {
        if(mToolbar != null) {
            mToolbar.setToolbarTitleColor(colorRes);
        }
    }

    protected void setText(TextView tv, String content) {
        if(null == tv)
            return;
        tv.setText(content);
    }

    protected void setText(TextView tv, Spanned content) {
        if(null == tv)
            return;
        tv.setText(content);
    }

    protected void showView(View view, boolean show) {
        if(null == view)
            return;
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    /**
     * 筛选部分页面, 不允许更改状态栏颜色值
     * @return
     */
    private boolean filterActivity() {
//        if(mActivity instanceof MainActivity
//                || mActivity instanceof NestedScrollingActivity) {
//            return false;
//        }
        return true;
    }

    protected void showToDevMsg() {
        showMsg("开发中，敬请期待");
    }

    protected void setStatusTransparent() {
        ImmersionBar.with(mActivity).fitsSystemWindows(false)
                .transparentStatusBar()
                .statusBarDarkFont(false)
                .keyboardEnable(true)

                .init();
    }
    protected void setStatusTransparentWithNavigationBar() {
        ImmersionBar.with(mActivity).fitsSystemWindows(false)
                .transparentNavigationBar()
                .statusBarDarkFont(false)
                .keyboardEnable(true)
                .init();
    }

    protected void setStatusTxtDark() {
        ImmersionBar.with(mActivity).fitsSystemWindows(true)
                .statusBarDarkFont(true, 0.2f)
                .statusBarColor(R.color.white)
                .keyboardEnable(true)
                .init();
    }

    protected void setStatusBlue() {
        ImmersionBar.with(mActivity).fitsSystemWindows(true)
                .statusBarDarkFont(false)
                .statusBarColor(R.color.colorPrimary)
                .keyboardEnable(true)
                .init();
    }

    protected void setStatusBlack() {
        ImmersionBar.with(mActivity).fitsSystemWindows(true)
                .statusBarDarkFont(false)
                .statusBarColor(R.color.black, 0.5f)
                .keyboardEnable(true)
                .init();
    }


    /** 针对8.0设备, 透明Activity会导致Only fullscreen opaque activities can request orientation, 参考https://blog.csdn.net/starry_eve/article/details/82777160解决如下 */
    private boolean fixOrientation(){
        try {
            Field field = Activity.class.getDeclaredField("mActivityInfo");
            field.setAccessible(true);
            ActivityInfo o = (ActivityInfo)field.get(this);
            o.screenOrientation = -1;
            field.setAccessible(false);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isTranslucentOrFloating(){
        boolean isTranslucentOrFloating = false;
        try {
            int [] styleableRes = (int[]) Class.forName("com.android.internal.R$styleable").getField("Window").get(null);
            final TypedArray ta = obtainStyledAttributes(styleableRes);
            Method m = ActivityInfo.class.getMethod("isTranslucentOrFloating", TypedArray.class);
            m.setAccessible(true);
            isTranslucentOrFloating = (boolean)m.invoke(null, ta);
            m.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTranslucentOrFloating;
    }

    @Override
    public void setRequestedOrientation(int requestedOrientation) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O && isTranslucentOrFloating()) {
            return;
        }
        super.setRequestedOrientation(requestedOrientation);
    }
    /** 针对8.0设备, 透明Activity会导致Only fullscreen opaque activities can request orientation, 参考https://blog.csdn.net/starry_eve/article/details/82777160解决如上 */

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
        ImmersionBar.with(this).destroy();
    }



    // +++++++++++++++++++++++++++++++
    // 干掉进程的第四种方式。使用system/bin下的am命令： am force-stop packageName
    // 发送signal 查看linux signale命令
    // +++++++++++++++++++++++++++++++

    /**
     * 该方式最好，最彻底。
     *
     * @param ctx
     * @param packageName
     */
    public void amForceAppProcess(Context ctx, String packageName) {

        Process process = null;
        DataOutputStream os = null;

        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(" am force-stop " + packageName + " \n ");
            os.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            process.waitFor();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
