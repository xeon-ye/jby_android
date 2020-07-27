package com.jkrm.education.ui.activity.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwImgUtil;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.AwSpUtil;
import com.hzw.baselib.util.AwTakePhotoUtil;
import com.hzw.baselib.util.RegexUtil;
import com.hzw.baselib.widgets.AwViewCircleImage;
import com.jkrm.education.R;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.result.OssResultBean;
import com.jkrm.education.bean.rx.RxUpdateUserBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.presenters.MeInfoPresent;
import com.jkrm.education.mvp.views.MeInfoView;
import com.jkrm.education.ui.activity.login.ChoiceSchoolActivity;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.util.UserUtil;
import com.jph.takephoto.model.TResult;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hzw on 2019/8/4.
 */

public class MeInfoActivity extends AwMvpActivity<MeInfoPresent> implements MeInfoView.View {

    @BindView(R.id.civ_avatar)
    AwViewCircleImage mCivAvatar;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_mobile)
    TextView mTvMobile;
    @BindView(R.id.tv_school)
    TextView mTvSchool;
    @BindView(R.id.tv_course)
    TextView mTvCourse;
    @BindView(R.id.tv_acc)
    TextView mTvAcc;
    /**
     * 选取的头像图片地址
     */
    private String filePath = "";
    /**
     * 保存到服务器的地址
     */
    private String serverFilePath = "";

    @Override
    protected MeInfoPresent createPresenter() {
        return new MeInfoPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_me_info;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusTxtDark();
        setToolbarWithBackImg("个人资料", null);
    }

    @Override
    protected void initData() {
        super.initData();
        setText(mTvName, AwDataUtil.isEmpty(MyApp.getInstance().getAppUser().getRealName()) ? "暂无姓名" : MyApp.getInstance().getAppUser().getRealName());
        setText(mTvMobile, AwDataUtil.isEmpty(MyApp.getInstance().getAppUser().getPhone()) ? "暂无手机号" : RegexUtil.encryptionPhone(MyApp.getInstance().getAppUser().getPhone()));
        setText(mTvSchool, AwDataUtil.isEmpty(MyApp.getInstance().getAppUser().getSchool().getName()) ? "" : MyApp.getInstance().getAppUser().getSchool().getName());
        setText(mTvCourse, AwDataUtil.isEmpty(MyApp.getInstance().getAppUser().getGradeName()) ? "" : MyApp.getInstance().getAppUser().getGradeName());
        setText(mTvAcc, AwSpUtil.getString(MyConstant.SPConstant.XML_USER_INFO,MyConstant.SPConstant.KEY_ACC,""));
        AwImgUtil.setImgAvatar(mActivity, mCivAvatar, UserUtil.getAvatar());

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
        }if(!AwDataUtil.isEmpty(type.getRealName())){
            setText(mTvName,type.getRealName());
        }
        UserUtil.updateUserInfo(type);
    }

    @OnClick({R.id.ll_avatar, R.id.ll_name, R.id.ll_mobile, R.id.ll_change_psw,R.id.ll_school,R.id.ll_course,R.id.ll_classes})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_avatar:
                showDialogCustomLeftAndRight("请选择头像获取方式", "拍照", "相册", v1 -> {
                    dismissDialog();
                    getTakePhoto().onPickFromCaptureWithCrop(AwTakePhotoUtil.getFile(), AwTakePhotoUtil.getCropOptions());
                }, v12 -> {
                   // getTakePhoto().onPickMultipleWithCrop(1, AwTakePhotoUtil.getCropOptions());//限制1张
                    PictureSelector.create(MeInfoActivity.this).openGallery(PictureMimeType.ofImage()).maxSelectNum(1).forResult(PictureConfig.CHOOSE_REQUEST);
                    dismissDialog();
                });
                break;
            case R.id.ll_name:
                toClass(MeModifyNameActivity.class, false);
                break;
            case R.id.ll_mobile:
                toClass(MeModifyMobileStep1Activity.class, false);
                break;
            case R.id.ll_change_psw:
                toClass(MeModifyPwdActivity.class, false);
                break;
            case  R.id.ll_school:
                toClass(ChoiceSchoolActivity.class,false, Extras.KEY_REGISTER_ID,UserUtil.getAppUser().getId());
                break;
            case R.id.ll_course:
                //toClass(ChoiceCourseActivity.class,false);
                break;
            case R.id.ll_classes:
               // toClass(MeClassesActivity.class,false);
                break;
        }
    }

    @Override
    public void takeSuccess(TResult result) {
        filePath = result.getImage().getOriginalPath();
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(R.mipmap.touxiang);
        Glide.with(this).load(new File(filePath)).apply(requestOptions).into(mCivAvatar);
        mPresenter.uploadOss("user_server", filePath);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(null!=data){
            List<LocalMedia> localMedia = PictureSelector.obtainMultipleResult(data);
            filePath =localMedia.get(0).getPath();
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.error(R.mipmap.touxiang);
            Glide.with(this).load(new File(filePath)).apply(requestOptions).into(mCivAvatar);
            mPresenter.uploadOss("user_server", filePath);
        }
    }

    @Override
    public void takeFail(TResult result, String msg) {
        AwLog.d("takeFail msg头像更改失败原因: " + msg);
    }

    @Override
    public void takeCancel() {
        AwLog.d("takeCancel 头像更改操作取消");
    }

    @Override
    public void uploadOssSuccess(OssResultBean bean) {
        serverFilePath = bean.getAccessUrl();
        mPresenter.updateAvatar(RequestUtil.updateAvatarRequest(bean.getAccessUrl(), UserUtil.getUserId()));
    }

    @Override
    public void uploadOssFail(String msg) {
        AwImgUtil.setImgAvatar(mActivity, mCivAvatar, UserUtil.getAvatar());
    }

    @Override
    public void updateAvatarSuccess(String msg) {
        showMsg("头像修改成功");
        //更新本地存储, 更新我的页面和我的资料页面展示的昵称
        EventBus.getDefault().postSticky(new RxUpdateUserBean(UserUtil.getPhone(), serverFilePath, UserUtil.getNickname()));
    }

    @Override
    public void updateAvatarFail(String msg) {
        serverFilePath = "";
        AwImgUtil.setImgAvatar(mActivity, mCivAvatar, UserUtil.getAvatar());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
