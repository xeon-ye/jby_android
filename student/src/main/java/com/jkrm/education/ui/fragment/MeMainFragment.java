package com.jkrm.education.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzw.baselib.base.AwBaseApplication;
import com.hzw.baselib.base.AwMvpLazyFragment;
import com.hzw.baselib.bean.UpdateBean;
import com.hzw.baselib.util.AwAPPUtils;
import com.hzw.baselib.util.AwBtnClickUtil;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwImgUtil;
import com.hzw.baselib.util.AwUpdateUtil;
import com.hzw.baselib.util.AwVersionUtil;
import com.hzw.baselib.widgets.AwViewCircleImage;
import com.jkrm.education.R;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.result.RequestClassesBean;
import com.jkrm.education.bean.result.TeachersResultBean;
import com.jkrm.education.bean.result.VersionResultBean;
import com.jkrm.education.bean.rx.RxUpdateUserBean;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.presenters.MeMainFragmentPresent;
import com.jkrm.education.mvp.views.MeMainFragmentView;
import com.jkrm.education.ui.activity.AnswerRecordActivity;
import com.jkrm.education.ui.activity.CourseCacheNewActivity;
import com.jkrm.education.ui.activity.RepaidBalanceActivity;
import com.jkrm.education.ui.activity.login.ChoiceClassesActivity;
import com.jkrm.education.ui.activity.me.BalanceDetailsActivity;
import com.jkrm.education.ui.activity.me.MeAgreementActivity;
import com.jkrm.education.ui.activity.me.MeInfoActivity;
import com.jkrm.education.ui.activity.me.MeModifyPwdActivity;
import com.jkrm.education.ui.activity.me.MeTeacherContractActivity;
import com.jkrm.education.ui.activity.me.MyOrderActivity;
import com.jkrm.education.util.ReLoginUtil;
import com.jkrm.education.util.UserUtil;
import com.sobot.chat.SobotApi;
import com.sobot.chat.api.model.Information;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 我的
 * Created by hzw on 2019/5/5.
 */

public class MeMainFragment extends AwMvpLazyFragment<MeMainFragmentPresent> implements MeMainFragmentView.View {

    @BindView(R.id.civ_avatar)
    AwViewCircleImage mCivAvatar;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_mobile)
    TextView mTvMobile;
    @BindView(R.id.tv_version)
    TextView mTvVersion;
    @BindView(R.id.tv_cache)
    TextView mTvCache;
    Unbinder unbinder;
    @BindView(R.id.iv_school)
    ImageView mIvSchool;
    @BindView(R.id.tv_school)
    TextView mTvSchool;
    @BindView(R.id.ll_school)
    LinearLayout mLlSchool;
    @BindView(R.id.iv_course)
    ImageView mIvCourse;
    @BindView(R.id.tv_course)
    TextView mTvCourse;
    @BindView(R.id.ll_course)
    LinearLayout mLlCourse;
    @BindView(R.id.iv_classess)
    ImageView mIvClassess;
    @BindView(R.id.tv_classes)
    TextView mTvClasses;
    @BindView(R.id.ll_classes)
    LinearLayout mLlClasses;
    @BindView(R.id.tv_contract)
    TextView mTvContract;
    @BindView(R.id.tv_answer_answerrecord)
    TextView mTvAnswerAnswerrecord;
    @BindView(R.id.tv_modifyPwd)
    TextView mTvModifyPwd;
    @BindView(R.id.tv_sobot)
    TextView mTvSobot;
    @BindView(R.id.iv_mobileNext)
    ImageView mIvMobileNext;
    @BindView(R.id.ll_version)
    LinearLayout mLlVersion;
    @BindView(R.id.tv_userAggrement)
    TextView mTvUserAggrement;
    @BindView(R.id.btn_logout)
    Button mBtnLogout;
    @BindView(R.id.iv_acc)
    ImageView mIvAcc;
    @BindView(R.id.tv_acc)
    TextView mTvAcc;
    @BindView(R.id.ll_acc)
    LinearLayout mLlAcc;
    @BindView(R.id.iv_myorder)
    ImageView mIvMyorder;
    @BindView(R.id.tv_myorder)
    TextView mTvMyorder;
    @BindView(R.id.ll_myorder)
    LinearLayout mLlMyorder;


    @Override
    protected MeMainFragmentPresent createPresenter() {
        return new MeMainFragmentPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me_main;
    }

    @Override
    protected void initView() {
        super.initView();
        setToolbarHideLeftAndRight("我的", null);
    }

    @Override
    protected void initData() {
        super.initData();
        EventBus.getDefault().register(this);

        setText(mTvName, AwDataUtil.isEmpty(MyApp.getInstance().getAppUser().getRealName()) ? "暂无姓名" : MyApp.getInstance().getAppUser().getRealName());
        setText(mTvMobile, AwDataUtil.isEmpty(MyApp.getInstance().getAppUser().getPhone()) ? "暂无手机号" : MyApp.getInstance().getAppUser().getPhone());
        setText(mTvSchool, AwDataUtil.isEmpty(MyApp.getInstance().getAppUser().getSchool().getName()) ? "" : MyApp.getInstance().getAppUser().getSchool().getName());
        setText(mTvCourse, AwDataUtil.isEmpty(MyApp.getInstance().getAppUser().getGradeName()) ? "" : MyApp.getInstance().getAppUser().getGradeName());
        setText(mTvVersion, "版本号 " + AwAPPUtils.getAppVersionInfo(mActivity, AwAPPUtils.TYPE_VERSION.TYPE_VERSION_NAME));
        //https://randomuser.me/api/portraits/women/12.jpg
        AwImgUtil.setImgAvatar(mActivity, mCivAvatar, UserUtil.getAvatar());
        mPresenter.getClassesById(UserUtil.getStudId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    @OnClick({R.id.tv_mobile, R.id.btn_logout, R.id.tv_contract, R.id.tv_modifyPwd, R.id.tv_sobot, R.id.ll_version, R.id.tv_userAggrement, R.id.tv_cache, R.id.tv_answer_answerrecord, R.id.ll_classes, R.id.ll_myorder,R.id.ll_acc,R.id.ll_balancedetails,R.id.ll_account})
    @Override
    public void onClick(View v) {
        if (AwBtnClickUtil.isFastDoubleClick(v.getId())) {
            return;
        }
        switch (v.getId()) {
            case R.id.tv_mobile:
                toClass(MeInfoActivity.class, false);
                break;
            case R.id.btn_logout:
                showDialogWithCancelDismiss("确认要退出登录吗？", v1 -> {
                    showMsg("退出登录成功");
                    ReLoginUtil.reLogin(mActivity);
                    mPresenter.logout();
                });
                break;
            case R.id.tv_contract:
                toClass(MeTeacherContractActivity.class, false);
                break;
            case R.id.tv_modifyPwd:
                toClass(MeModifyPwdActivity.class, false);
                break;
            case R.id.tv_sobot:
                Information information = new Information();
                information.setAppkey(MyConstant.ServerConstant.SOBOT_APPKEY);  //分配给App的的密钥
                /**
                 * @param context 上下文对象
                 * @param information 初始化参数
                 */
                SobotApi.startSobotChat(mActivity, information);
                break;
            case R.id.ll_version:
                mPresenter.getVersionInfo();
                break;
            case R.id.tv_userAggrement:
                toClass(MeAgreementActivity.class, false);
                break;
            case R.id.tv_cache:
                //toClass(CourseCacheActivity.class, false);
                toClass(CourseCacheNewActivity.class, false);
                break;
            case R.id.tv_answer_answerrecord:
                toClass(AnswerRecordActivity.class, false);
                break;
            case R.id.ll_classes:
                toClass(ChoiceClassesActivity.class, false);
                break;
            case R.id.ll_myorder:
                toClass(MyOrderActivity.class, false);
                break;
            case R.id.ll_acc:
                toClass(RepaidBalanceActivity.class, false);
                break;
            case R.id.ll_balancedetails:
                toClass(BalanceDetailsActivity.class,false);
                break;
            case R.id.ll_account:
                toClass(RepaidBalanceActivity.class,false);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshByBus(RxUpdateUserBean type) {
        if (type == null)
            return;
        if (!AwDataUtil.isEmpty(type.getMobile())) {
            setText(mTvMobile, type.getMobile());
        }
        if (!AwDataUtil.isEmpty(type.getAvatar())) {
            AwImgUtil.setImgAvatar(mActivity, mCivAvatar, type.getAvatar());
        }
        if (!AwDataUtil.isEmpty(type.getNickName())) {
            setText(mTvName, type.getNickName());
        }
        UserUtil.updateUserInfo(type);
    }

    @Override
    public void getTeacherListSuccess(List<TeachersResultBean> list) {
    }

    @Override
    public void getTeacherListFail(String msg) {

    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.getClassesById(UserUtil.getStudId());

    }

    @Override
    public void getVersionInfoSuccess(VersionResultBean bean) {
        if (bean != null && AwVersionUtil.compareVersions(AwAPPUtils.getAppVersionInfo(mActivity, AwAPPUtils.TYPE_VERSION.TYPE_VERSION_NAME), bean.getVersion()) > 0) {
            if (AwBaseApplication.netWatchdog.hasNet(mActivity)) {
                UpdateBean updateBean = new UpdateBean();
                updateBean.setVersion(bean.getVersion());
                updateBean.setUpdateContent(bean.getUpdateContent());
                updateBean.setUrl(bean.getUrl());
                AwUpdateUtil.getInstance(mActivity).handleUpdate(updateBean, () -> {
                });
            }
        } else {
            showMsg("已是最新版本");
        }
    }

    @Override
    public void getVersionFail(String msg) {
        showDialog("服务器数据获取失败，暂时无法获取检查更新结果");
    }

    @Override
    public void getClassesByIdSuccess(List<RequestClassesBean> data) {
        if (null != data && data.size() > 0 && null != mTvClasses) {
            mTvClasses.setText(data.get(0).getClassName());
        }
    }

    @Override
    public void getClassesByIdFail(String msg) {

    }

}
