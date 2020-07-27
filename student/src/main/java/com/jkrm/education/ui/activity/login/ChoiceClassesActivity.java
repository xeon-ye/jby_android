package com.jkrm.education.ui.activity.login;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.jkrm.education.R;
import com.jkrm.education.adapter.ChooseASchoolAdapter;
import com.jkrm.education.adapter.ChooseClassesAdapter;
import com.jkrm.education.bean.ClassesBean;
import com.jkrm.education.bean.ClassessBean;
import com.jkrm.education.bean.test.TestMarkKindsBean;
import com.jkrm.education.mvp.presenters.ChoiceClassesPresent;
import com.jkrm.education.mvp.views.ChoiceClassesView;
import com.jkrm.education.util.DataUtil;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.util.UserUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChoiceClassesActivity extends AwMvpActivity<ChoiceClassesPresent> implements ChoiceClassesView.View {

    @BindView(R.id.iv_close)
    ImageView mIvClose;
    @BindView(R.id.tv_skip)
    TextView mTvSkip;
    @BindView(R.id.rcv_year)
    RecyclerView mRcvYear;
    @BindView(R.id.rcv_classes)
    RecyclerView mRcvClasses;
    @BindView(R.id.et_input_class)
    EditText mEtInputClass;
    @BindView(R.id.btn_next)
    Button mBtnNext;
    ChooseASchoolAdapter mYearAdapter;
    private ChooseClassesAdapter mClassesAdapter;
    private List<TestMarkKindsBean> mLearingyearList, mClassesList;
    private Map<String, List<ClassessBean.Bean>> mDataMap;
    private String mStrYear, mStrClasses, mStrClassId;
    private String mStringgradeId;
    private ArrayList<ClassesBean> mClassesBeans=new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_choice_classes;
    }


    @Override
    protected ChoiceClassesPresent createPresenter() {
        return new ChoiceClassesPresent(this);
    }

    @Override
    protected void initView() {
        super.initView();
        mYearAdapter = new ChooseASchoolAdapter();
        mClassesAdapter = new ChooseClassesAdapter();
        AwRecyclerViewUtil.setRecyclerViewGridlayout(mActivity, mRcvYear, mYearAdapter, 3);
        AwRecyclerViewUtil.setRecyclerViewGridlayout(mActivity, mRcvClasses, mClassesAdapter, 3);
    }

    @Override
    protected void initData() {
        super.initData();
        mLearingyearList = DataUtil.createLearnYear();
        mYearAdapter.addAllData(mLearingyearList);
        String schoolId = UserUtil.getAppUser().getSchool().getId();
        String userId = UserUtil.getAppUser().getId();
        mPresenter.getClasses(schoolId, userId);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mYearAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<TestMarkKindsBean> data = adapter.getData();
                for (int i = 0; i < data.size(); i++) {
                    if (i == position) {
                        data.get(i).setChecked(true);
                        mStrYear = data.get(i).getName();
                    } else {
                        data.get(i).setChecked(false);
                    }
                }
                if (!AwDataUtil.isEmpty(mDataMap)&&!AwDataUtil.isEmpty(mDataMap.keySet())) {
                    Object[] objects = mDataMap.keySet().toArray();
                    if (position < objects.length) {
                        mClassesAdapter.addAllData(mDataMap.get(objects[position]));
                        mYearAdapter.notifyDataSetChanged();
                        changeBtnState();
                    }
                }
            }
        });
        mClassesAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<ClassessBean.Bean> data = adapter.getData();
                for (int i = 0; i < data.size(); i++) {
                    if (i == position) {
                        data.get(i).setChecked(true);
                        mStrClasses = data.get(i).getName();
                        mStrClassId = data.get(i).getId();
                        mStringgradeId = data.get(i).getGradeId();
                        mClassesBeans.clear();
                        for (ClassessBean.Bean datum : data) {
                            if(datum.isChecked()){
                                ClassesBean classesBean = new ClassesBean(datum.getId(),datum.getName());
                                mClassesBeans.add(classesBean);
                            }
                        }
                    } else {
                        data.get(i).setChecked(false);
                    }
                }
                mClassesAdapter.notifyDataSetChanged();
                changeBtnState();
            }
        });
        mEtInputClass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() > 0) {
                    mStrClasses = editable.toString();
                    mStrClassId = "";
                    mStringgradeId = "";
                    List<ClassessBean.Bean> data = mClassesAdapter.getData();
                    for (int i = 0; i < data.size(); i++) {
                        data.get(i).setChecked(false);
                    }
                    mClassesAdapter.notifyDataSetChanged();
                    mClassesBeans.clear();
                    mClassesBeans.add(new ClassesBean("",editable.toString().trim()));
                    mClassesAdapter.notifyDataSetChanged();
                    changeBtnState();
                }
            }
        });

    }

    private void changeBtnState() {
        if (!AwDataUtil.isEmpty(mStrYear) && mClassesBeans.size()>0) {
            mBtnNext.setEnabled(true);
        } else {
            mBtnNext.setEnabled(false);
        }
    }

    @OnClick({R.id.iv_close, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                finish();
                break;
            case R.id.btn_next:
                mPresenter.bindClasses(RequestUtil.bindSchoolClass(UserUtil.getAppUser().getId(), mStringgradeId, mStrYear, mStrClassId, mStrClasses, UserUtil.getAppUser().getStudId(), UserUtil.getAppUser().getTeacherId(),mClassesBeans));
                break;
        }
    }


    @Override
    public void getClassesSuccess(Map<String, List<ClassessBean.Bean>> data) {
        mDataMap = data;
    }


    @Override
    public void getClasssesFail(String msg) {
        showMsg(msg);
    }

    @Override
    public void bindClassesSuccess(String data) {
        showMsg("绑定成功");
        finish();
    }

    @Override
    public void bindClassesFail(String msg) {
        showMsg(msg);
    }
}
