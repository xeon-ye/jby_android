package com.jkrm.education.ui.activity.me;

import android.widget.EditText;

import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.util.AwDataUtil;
import com.jkrm.education.R;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.rx.RxUpdateUserBean;
import com.jkrm.education.mvp.presenters.MeModifyNamePresent;
import com.jkrm.education.mvp.views.MeModifyNameView;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.util.UserUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/**
 * Created by hzw on 2019/8/5.
 */

public class MeModifyNameActivity extends AwMvpActivity<MeModifyNamePresent> implements MeModifyNameView.View {

    @BindView(R.id.et_name)
    EditText mEtName;

    @Override
    protected MeModifyNamePresent createPresenter() {
        return new MeModifyNamePresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_me_modify_name;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusTxtDark();
        setToolbarWithBackImgAndRightView("修改姓名", "保存", () -> {
            String nickName = mEtName.getText().toString();
            if(AwDataUtil.isEmpty(nickName)) {
                showDialog("请输入需要修改的姓名");
                return;
            }
            if(nickName.equals(MyApp.getInstance().getAppUser().getNickName())) {
                showDialog("姓名未做修改，无需保存");
                return;
            }
            mPresenter.updateRealName(RequestUtil.updateRealNameRequest(nickName,MyApp.getInstance().getAppUser().getId()));
            //mPresenter.updateNickname(RequestUtil.updateNicknameRequest(nickName, MyApp.getInstance().getAppUser().getId()));
        });
        mToolbar.setRTextColor(R.color.colorPrimary);
    }

    @Override
    public void updateNicknameSuccess(String code) {
        showMsg("修改成功");
        //更新本地存储, 更新我的页面和我的资料页面展示的昵称
        EventBus.getDefault().postSticky(new RxUpdateUserBean(UserUtil.getPhone(), UserUtil.getAvatar(), mEtName.getText().toString()));
        finish();
    }

    @Override
    public void updateRealNameSuccess(String code) {
        showMsg("修改成功");
        //更新本地存储, 更新我的页面和我的资料页面展示的昵称
        RxUpdateUserBean rxUpdateUserBean = new RxUpdateUserBean(UserUtil.getPhone(), UserUtil.getAvatar(), mEtName.getText().toString());
        rxUpdateUserBean.setRealName(mEtName.getText().toString());
        EventBus.getDefault().postSticky(rxUpdateUserBean);
        UserUtil.updateUserInfo(rxUpdateUserBean);
        finish();
    }
}
