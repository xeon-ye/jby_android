package com.jkrm.education.ui.activity.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.bean.AddressBean;
import com.hzw.baselib.bean.SchoolBean;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwMd5Util;
import com.hzw.baselib.util.AwSpUtil;
import com.hzw.baselib.widgets.ChoseAddressDialogFragment;
import com.hzw.baselib.widgets.ChoseSchoolDialogFragment;
import com.jkrm.education.R;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.result.login.LoginResult;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.presenters.ChoseSchoolPresent;
import com.jkrm.education.mvp.views.ChoiceSchoolView;
import com.jkrm.education.ui.activity.MainActivity;
import com.jkrm.education.util.RequestUtil;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ChoiceSchoolActivity extends AwMvpActivity<ChoseSchoolPresent> implements ChoiceSchoolView.View {
    @BindView(R.id.tv_skip)
    TextView mTvSkip;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.tv_school)
    TextView mTvSchool;
    @BindView(R.id.btn_next)
    Button mBtnNext;
    @BindView(R.id.ll_of_context)
    LinearLayout mLlOfContext;
    private String mStrProId = "", mStrAreaId = "", mStrCityId = "", mStrAddress = "";
    private SchoolBean.RowsBean mRowsBean;
    private String mStrProName;
    private String mStrCityName;
    private String mStrAreaName;
    private String mRegisterID;
    private String mStrCourseID;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_choice_school;
    }

    @OnClick({R.id.tv_skip, R.id.tv_address, R.id.tv_school, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_skip:
                break;
            case R.id.tv_address:
                getData("", 0);
                break;
            case R.id.tv_school:
                if (AwDataUtil.isEmpty(mStrProId) || AwDataUtil.isEmpty(mStrAreaId) || AwDataUtil.isEmpty(mStrCityId)) {
                    showMsg("请先选择所在地区");
                    return;
                }
                String t = String.valueOf(System.currentTimeMillis());
                mPresenter.getSchoolList(AwMd5Util.md5("jby-xinjiaoyu" + t + mStrProId + mStrCityId + mStrAreaId), t, mStrProId, mStrCityId, mStrAreaId);
                break;
            case R.id.btn_next:
                if (AwDataUtil.isEmpty(mRowsBean)) {
                    showMsg("请先选择学校");
                    return;
                }
                String phone = AwSpUtil.getString(MyConstant.SPConstant.XML_TEMPORARY, MyConstant.SPConstant.KEY_TEMPORARY_MOBILE, "");
                mPresenter.registerInitUser(RequestUtil.getRegisterInitUserBody(mRegisterID, phone, mRowsBean.getId(), mRowsBean.getSchName(),
                        MyApp.mStrSection, MyApp.mStrYear, mStrProId, mStrProName, mStrCityId, mStrCityName, mStrAreaId, mStrAreaName,
                        mStrCourseID));
                break;
        }
    }

    @Override
    protected void initData() {
        super.initData();
        mRegisterID = getIntent().getStringExtra(Extras.KEY_REGISTER_ID);
        mStrCourseID = getIntent().getStringExtra(Extras.KEY_COURSE_ID);
    }

    @Override
    protected ChoseSchoolPresent createPresenter() {
        return new ChoseSchoolPresent(this);
    }


    private void getData(String pcode, int type) {
        String t = String.valueOf(System.currentTimeMillis());
        mPresenter.getResions(AwMd5Util.md5("jby-xinjiaoyu" + t + pcode), t, pcode, type);
    }

    @Override
    public void getResionsSuccess(List<AddressBean> data, int type) {
        switch (type) {
            case 0:
                ChoseAddressDialogFragment proDialog = new ChoseAddressDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Extras.KEY_ADDRESS, (Serializable) data);
                proDialog.setArguments(bundle);
                proDialog.show(getSupportFragmentManager(), "");
                proDialog.setOnItemClickListener(new ChoseAddressDialogFragment.onItemClickListener() {
                    @Override
                    public void onItemChickListener(AddressBean addressBean) {
                        String pcode = addressBean.getCode();
                        //省id
                        mStrProId = addressBean.getCode();
                        mStrProName = addressBean.getName();
                        getData(pcode, 1);
                        mStrAddress = "";
                        mStrAddress += addressBean.getName();
                    }
                });
                break;
            case 1:
                ChoseAddressDialogFragment areaDialog = new ChoseAddressDialogFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable(Extras.KEY_ADDRESS, (Serializable) data);
                areaDialog.setArguments(bundle1);
                areaDialog.show(getSupportFragmentManager(), "");
                areaDialog.setOnItemClickListener(new ChoseAddressDialogFragment.onItemClickListener() {
                    @Override
                    public void onItemChickListener(AddressBean addressBean) {
                        String pcode = addressBean.getCode();
                        //市
                        mStrCityId = addressBean.getCode();
                        mStrCityName = addressBean.getName();
                        getData(pcode, 2);
                        mStrAddress += addressBean.getName();
                    }
                });
                break;
            case 2:
                ChoseAddressDialogFragment cityDialog = new ChoseAddressDialogFragment();
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable(Extras.KEY_ADDRESS, (Serializable) data);
                cityDialog.setArguments(bundle2);
                cityDialog.show(getSupportFragmentManager(), "");
                cityDialog.setOnItemClickListener(new ChoseAddressDialogFragment.onItemClickListener() {
                    @Override
                    public void onItemChickListener(AddressBean addressBean) {
                        //区
                        mStrAreaId = addressBean.getCode();
                        mStrAreaName = addressBean.getName();
                        mStrAddress += addressBean.getName();
                        mTvAddress.setText(mStrAddress);
                    }
                });
                break;
        }
    }

    @Override
    public void getResionFail(String msg) {
        showDialog(msg);
    }

    @Override
    public void getSchoolSuccess(SchoolBean schoolBean) {
     /*   if (AwDataUtil.isEmpty(schoolBean.getRows())) {
            showMsg("此地区暂无学校！");
            return;
        }*/
        ChoseSchoolDialogFragment choseSchoolDialogFragment = new ChoseSchoolDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("school", (Serializable) schoolBean.getRows());
        choseSchoolDialogFragment.setArguments(bundle);
        choseSchoolDialogFragment.show(getSupportFragmentManager(), "");
        choseSchoolDialogFragment.setOnItemClickListener(new ChoseSchoolDialogFragment.onItemClickListener() {
            @Override
            public void onItemChickListener(SchoolBean.RowsBean school) {
                mRowsBean = school;
                mTvSchool.setText(mRowsBean.getSchName());
            }

            @Override
            public void onCrateSchoolListener(SchoolBean.RowsBean school) {
                if(!school.getSchName().contains("中")&&!school.getSchName().contains("学")&&!school.getSchName().contains("校")){
                    showMsg("学校名称不规范，需包含‘中学’或‘学校’");
                    return;
                }
                mRowsBean = school;
                mTvSchool.setText(mRowsBean.getSchName());
            }
        });
    }

    @Override
    public void getSchoolFail(String msg) {
        showMsg(msg);
    }

    @Override
    public void registerInitUserSuccess(LoginResult registerBean) {
        // TODO: 2020/7/13  绑定学校暂时有问题
        MyApp.getInstance().saveToken(registerBean.getToken());
        MyApp.getInstance().saveRoleID(registerBean.getRoleId());
        MyApp.getInstance().saveLoginUser(registerBean.getUser());
        MyApp.getInstance().saveAcc(registerBean.getUser().getUsername());
        toClass(MainActivity.class,true);
    }

    @Override
    public void registerInitUserFail(String msg) {
        showMsg(msg);
    }
}
