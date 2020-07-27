package com.hzw.baselib.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hzw.baselib.R;
import com.hzw.baselib.util.AwBtnClickUtil;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.hzw.baselib.widgets.AwViewDialog;
import com.jph.takephoto.app.TakePhotoFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by hzw on 2017/11/29.
 */

public abstract class AwBaseFragment extends TakePhotoFragment implements AwBaseView, View.OnClickListener {

    protected AwBaseActivity mActivity;
    private Unbinder unbinder;
    protected CompositeSubscription mCompositeSubscription;
    protected String TAG = "";
    protected View view;
    protected AwViewCustomToolbar mToolbar;
    protected FragmentManager mFragmentManager;
    protected abstract int getLayoutId();
    protected void initData() {}
    protected void initView() {}
    protected void initListener() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (AwBaseActivity) context;
        mFragmentManager = mActivity.getMyFragmentManager();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getClass().getSimpleName();
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        initListener();
        view.setClickable(true);// 防止点击穿透，底层的fragment响应上层点击触摸事件
        return view;
    }

    protected void dismissDialog() {
        mActivity.dismissDialog();
    }

    @Override
    public void showLoadingDialog() {
        ((AwBaseActivity) getActivity()).showLoadingDialog();
    }

    @Override
    public void dismissLoadingDialog() {
        ((AwBaseActivity) getActivity()).dismissLoadingDialog();
    }

    public void showDialogToFinish(String msg) {
        mActivity.showDialogToFinish(msg);
    }

    protected void showDialogToBack(String msg) {
        mActivity.showDialogToBack(msg);
    }

    public void showDialog(String msg) {
        mActivity.showDialog(msg);
    }

    public void showToastDialog(String msg) {
        mActivity.showToastDialog(msg);
    }

    public void showToastDialog2(String msg) {
        mActivity.showToastDialog2(msg);
    }

    public void showDialog(String msg, View.OnClickListener listener) {
        mActivity.showDialog(msg, listener);
    }

    public void showDialogWithCancelDismiss(String msg, View.OnClickListener confirmListener) {
        mActivity.showDialogWithCancelDismiss(msg, confirmListener);
    }

    public void showDialogWithCancelFinish(String msg, View.OnClickListener confirmListener) {
        mActivity.showDialogWithCancelFinish(msg, confirmListener);
    }

    public void showDialogCustomLeftAndRight(String msg, String leftTxt, String rightTxt, View.OnClickListener leftListener, View.OnClickListener rightListener) {
        mActivity.showDialogCustomLeftAndRight(msg, leftTxt, rightTxt, leftListener, rightListener);
    }

    public void showInputDialog(String title, String hintMsg, AwViewDialog.InputCallback callback) {
        mActivity.showInputDialog(title, hintMsg, callback);
    }

    public void showInputDialogWithOld(String title, String text, AwViewDialog.InputCallback callback) {
        mActivity.showInputDialogWithOld(title, text, callback);
    }

    protected void setText(TextView tv, String content) {
        mActivity.setText(tv, content);
    }

    protected void setText(TextView tv, Spanned content) {
        mActivity.setText(tv, content);
    }

    protected void showView(View view, boolean show) {
        mActivity.showView(view, show);
    }

    @Override
    public void showMsg(String msg) {
        mActivity.showMsg(msg);
    }

    public void showMsg(int msgId) {
        mActivity.showMsg(msgId);
    }

    protected void setToolbar(String title, AwViewCustomToolbar.OnLeftClickListener leftClickListener) {
        mToolbar = (AwViewCustomToolbar) view.findViewById(R.id.toolbar_custom);
        if(mToolbar != null) {
            mToolbar.hideRightView();//一般很少有页面有右侧, 在需要的时候单独加代码显示, 这里统一隐藏
            mToolbar.setLeftImg(R.mipmap.ico_esc_gray);
            if(leftClickListener == null) {
                mToolbar.setOnLeftClickListener(new AwViewCustomToolbar.OnLeftClickListener() {
                    @Override
                    public void onLeftTextClick() {
                        fragmentToBack();
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

    protected void showToDevMsg() {
        mActivity.showToDevMsg();
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

    protected void setToolbarHideLeftWithRightTxt(String title, String rightTxt, AwViewCustomToolbar.OnRightClickListener rightClickListener) {
        setToolbar(title, null);
        if(mToolbar != null) {
            mToolbar.hideLeftView();
            mToolbar.showRightView();
            mToolbar.setRightText(rightTxt);
            mToolbar.setOnRightClickListener(rightClickListener);
        }
    }

    protected void setToolbarHideLeftWithRightImg(String title, int viewId, AwViewCustomToolbar.OnRightClickListener rightClickListener) {
        setToolbar(title, null);
        if(mToolbar != null) {
            mToolbar.hideLeftView();
            mToolbar.showRightView();
            mToolbar.setRightImg(viewId);
            mToolbar.setOnRightClickListener(rightClickListener);
        }
    }

    protected void setToolbarWithBackImgAndRightView(String title, String rightTxt, AwViewCustomToolbar.OnRightClickListener rigthClickListener) {
        setToolbar(title, null);
        if(mToolbar != null) {
            if(!AwDataUtil.isEmpty(title)) {
                mToolbar.setToolbarTitle(title);
            } else {
                mToolbar.hideTitleView();
            }
            mToolbar.setLeftImg(R.mipmap.icon_back);
            mToolbar.setRightText(rightTxt);
            if(null != rigthClickListener)
                mToolbar.setOnRightClickListener(rigthClickListener);
        }
    }

    /**
     * 跳转activity
     *
     * @param clazz
     * @param params 传值格式  key,value
     */
    public void toClass(Class clazz, boolean needFinish, Object... params) {
        mActivity.toClass(clazz, needFinish, params);
    }

    /**
     * 跳转activity(不传参)
     *
     * @param clazz
     */
    public void toClass(Class clazz, boolean needFinish) {
        mActivity.toClass(clazz, needFinish);
    }

    public void toClassForResult(Class clazz, int requestCode) {
        mActivity.toClassForResult(clazz, requestCode);
    }

    /**
     * 跳转fragment
     *
     * @param params 传值格式  key,value
     */
    public void toClass(int viewId, Fragment fragment, boolean addBackStack, Object... params) {
        mActivity.toClass(viewId, fragment, addBackStack, params);
    }

    /**
     * 跳转fragment
     *
     * @param params 传值格式  key,value
     */
    public void toClassChild(int viewId, Fragment fragment, boolean addBackStack, Object... params) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        boolean isTagNull = null == getChildFragmentManager().findFragmentByTag(fragment.getClass().getName());
        AwLog.d("toClassChild, fragment: " + fragment.getClass().getName() + " ,isAdded: " + fragment.isAdded() + " ,findTag is null?: " + isTagNull);
        if(!fragment.isAdded() && null == getChildFragmentManager().findFragmentByTag(fragment.getClass().getName())) {
            getChildFragmentManager().executePendingTransactions();
            if(params != null) {
                try {
                    fragment.setArguments(mActivity.getFragmentBundle(params));
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
     *
     */
    public void toClass(int viewId, Fragment fragment, boolean addBackStack) {
        mActivity.toClass(viewId, fragment, addBackStack, new Object[0]);
    }

    public void fragmentToBack() {
        mActivity.fragmentToBack();
    }

    public void popAllToBack() {
        mActivity.popAllToBack();
    }

    @Override
    public void onClick(View v) {
        if(AwBtnClickUtil.isFastDoubleClick(v.getId())) {
            return;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
