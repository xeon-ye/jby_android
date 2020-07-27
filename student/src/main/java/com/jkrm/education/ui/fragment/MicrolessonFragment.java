package com.jkrm.education.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzw.baselib.base.AwMvpLazyFragment;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.jkrm.education.R;
import com.jkrm.education.adapter.CourseAttrRcvAdapter;
import com.jkrm.education.adapter.CourseAttrSniAdapter;
import com.jkrm.education.adapter.CourseTypeAdapter;
import com.jkrm.education.adapter.MicroLessonAdapter;
import com.jkrm.education.bean.result.CourseAttrBean;
import com.jkrm.education.bean.result.CourseTypeBean;
import com.jkrm.education.bean.result.MicroLessonResultBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.presenters.MicroLessonPresent;
import com.jkrm.education.mvp.views.MicroLessonFragmentView;
import com.jkrm.education.ui.activity.CourseNotpurchasedActivity;
import com.jkrm.education.ui.activity.CoursePurchasedActivity;
import com.jkrm.education.util.RequestUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @Description: 微课
 * @Author: xiangqian
 * @CreateDate: 2020/3/5 10:11
 */

public class MicrolessonFragment extends AwMvpLazyFragment<MicroLessonPresent> implements MicroLessonFragmentView.View {
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    @BindView(R.id.btn_reset)
    Button mBtnReset;
    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;
    @BindView(R.id.ll_setting)
    LinearLayout mLlSetting;
    @BindView(R.id.rcv_xueduan)
    RecyclerView mRcvXueduan;
    @BindView(R.id.rcv_nianji)
    RecyclerView mRcvNianji;
    Unbinder unbinder;
    @BindView(R.id.spinner_xueke)
    Spinner mSpinnerXueke;
    @BindView(R.id.spinner_banben)
    Spinner mSpinnerBanben;
    @BindView(R.id.spinner_mokuai)
    Spinner mSpinnerMokuai;
    @BindView(R.id.spinner_zhuanyong)
    Spinner mSpinnerZhuanyong;
    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;


    private MicroLessonAdapter mMicroLessonAdapter;
    private List<MicroLessonResultBean> mList = new ArrayList<>();
    private List<CourseTypeBean> mTypeBeanList = new ArrayList<>();
    private CourseTypeAdapter mCourseTypeAdapter;
    private HashMap<String, List<CourseAttrBean.Value>> mCourseAttrValues;
    private List<CourseAttrBean.Value> mXueduanValues = new ArrayList<>(), mNianjiValues = new ArrayList<>(), mXuekeValues = new ArrayList<>(), mBanbenValues = new ArrayList<>(), mMokuaiValues = new ArrayList<>();
    private CourseAttrRcvAdapter mXueduanAdapter, mNianjiAdapter;
    private CourseAttrSniAdapter mXuekeAdapter, mBanbenAdapter, mMokuaiAdapter;
    private List<String> mListScreenIDs = new ArrayList<>();
    private String mStrPcvId = "", mStrTypeId = "";
    boolean isAuto = false;//首次进入自动请求 列表 数据



    @Override
    protected MicroLessonPresent createPresenter() {
        return new MicroLessonPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_microlesson;
    }

    @Override
    protected void initView() {
        super.initView();
        setToolbar("微课视频", null);
        mToolbar.hideLeftView();
        mToolbar.hideLeft2ImgView();
        mToolbar.setToolbarTitleColor(R.color.white);
        mToolbar.setLeftTextColor(R.color.white);
        mToolbar.setLeftText("高一");
    }

    @Override
    protected void initData() {
        mMicroLessonAdapter = new MicroLessonAdapter();
        //主列表
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, mMicroLessonAdapter, false);
        //类型
        mCourseTypeAdapter = new CourseTypeAdapter(getActivity(), mTypeBeanList);
        //学段 年纪
        mXueduanAdapter = new CourseAttrRcvAdapter();
        AwRecyclerViewUtil.setRecyclerViewGridlayout(mActivity, mRcvXueduan, mXueduanAdapter, 4);
        mNianjiAdapter = new CourseAttrRcvAdapter();
        AwRecyclerViewUtil.setRecyclerViewGridlayout(mActivity, mRcvNianji, mNianjiAdapter, 4);
        //最后一个下拉框
        mSpinnerZhuanyong.setAdapter(mCourseTypeAdapter);
        mPresenter.getCourseType(RequestUtil.emptyRequest());//请求微课类型  最右侧下拉选项
        mPresenter.getCourseAttr();
    }

    private void getData() {
        isAuto = true;
        mStrPcvId = "";
        mStrTypeId = "";
        splitPcvId();
        if (AwDataUtil.isEmpty(mTypeBeanList) || mSpinnerZhuanyong.getCount() == 0||-1==mSpinnerZhuanyong.getSelectedItemPosition()) {
            return;
        }
        mStrTypeId = mTypeBeanList.get(mSpinnerZhuanyong.getSelectedItemPosition()).getId();
        mPresenter.getCourseList(RequestUtil.getCourseRequestBody(mStrPcvId, mStrTypeId));
    }

    private void splitPcvId() {
        if (mXueduanValues.size() > 0) {
            for (CourseAttrBean.Value xuekeValue : mXueduanValues) {
                if (xuekeValue.isChecked()) {
                    mStrPcvId += xuekeValue.getValueId() + ",";
                }
            }
        }
        if (mNianjiValues.size() > 0) {
            for (CourseAttrBean.Value nianjiValue : mNianjiValues) {
                if (nianjiValue.isChecked()) {
                    mStrPcvId += nianjiValue.getValueId() + ",";
                }
            }
        }
        if (mXuekeValues.size() > 0) {
            for (CourseAttrBean.Value xueduanValue : mXuekeValues) {
                if (xueduanValue.isChecked()) {
                    mStrPcvId += xueduanValue.getValueId() + ",";
                }
            }
        }
        if (mBanbenValues.size() > 0) {
            for (CourseAttrBean.Value banbenValue : mBanbenValues) {
                if (banbenValue.isChecked()) {
                    mStrPcvId += banbenValue.getValueId() + ",";
                }
            }
        }
        if (null!=mMokuaiValues&&mMokuaiValues.size() > 0) {
            for (CourseAttrBean.Value mokuai : mMokuaiValues) {
                if (mokuai.isChecked()) {
                    mStrPcvId += mokuai.getValueId();
                }
            }
        }
    }


    @Override
    protected void initListener() {
        super.initListener();
        //左侧侧滑菜单
        mToolbar.setOnLeftClickListener(new AwViewCustomToolbar.OnLeftClickListener() {
            @Override
            public void onLeftTextClick() {
                if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    mDrawerLayout.closeDrawers();
                } else {
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                }
            }
        });
        //主页面列表点击
        mMicroLessonAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                MicroLessonResultBean microLessonResultBean = mList.get(position);
                //校园通可以看所有
                if("1".equals(microLessonResultBean.getWhetherBuySch())){
                    toClass(CoursePurchasedActivity.class, false, Extras.KEY_COURSE_BEAN, mList.get(position));
                }
                //vip
                else if("1".equals(microLessonResultBean.getWhetherVip())){
                    toClass(CoursePurchasedActivity.class, false, Extras.KEY_COURSE_BEAN, mList.get(position));
                }
                //未购买
                else if ("0".equals(microLessonResultBean.getWhetherBuy())) {
                    toClass(CourseNotpurchasedActivity.class, false, Extras.KEY_COURSE_BEAN, mList.get(position));
                 //已购买
                } else if ("1".equals(microLessonResultBean.getWhetherBuy())) {
                    toClass(CoursePurchasedActivity.class, false, Extras.KEY_COURSE_BEAN, mList.get(position));
                   //免费
                } else if ("0".equals(microLessonResultBean.getWhetherFree())) {
                    toClass(CoursePurchasedActivity.class, false, Extras.KEY_COURSE_BEAN, mList.get(position));
                }


            }
        });
        //学段
        mXueduanAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            CourseAttrBean.Value bookBean = (CourseAttrBean.Value) adapter.getItem(position);
            for (int i = 0; i < adapter.getItemCount(); i++) {
                CourseAttrBean.Value tempBean = (CourseAttrBean.Value) adapter.getItem(i);
                tempBean.setChecked(false);
            }
            bookBean.setChecked(true);
            mXueduanAdapter.notifyDataSetChanged();
            initNianfenChoiceData();
        });
        //年级
        mNianjiAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            CourseAttrBean.Value bookBean = (CourseAttrBean.Value) adapter.getItem(position);
            for (int i = 0; i < adapter.getItemCount(); i++) {
                CourseAttrBean.Value tempBean = (CourseAttrBean.Value) adapter.getItem(i);
                tempBean.setChecked(false);
            }
            bookBean.setChecked(true);
            for (int i = 0; i < adapter.getItemCount(); i++) {
                CourseAttrBean.Value tempBean = (CourseAttrBean.Value) adapter.getItem(i);
                if (tempBean.isChecked()) {
                    mToolbar.setLeftText(tempBean.getValueName());
                }
            }
            mNianjiAdapter.notifyDataSetChanged();
            initXuekeChoiseData();
        });
        mSpinnerXueke.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                for (int i1 = 0; i1 < mXuekeValues.size(); i1++) {
                    CourseAttrBean.Value value = mXuekeValues.get(i1);
                    if (i1 == i) {
                        value.setChecked(true);
                    } else {
                        value.setChecked(false);
                    }
                }
                getData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mSpinnerBanben.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                for (int j = 0; j < mBanbenValues.size(); j++) {
                    CourseAttrBean.Value value = mBanbenValues.get(j);
                    if (i == j) {
                        value.setChecked(true);
                    } else {
                        value.setChecked(false);
                    }

                }
                getData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mSpinnerMokuai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                for (int i1 = 0; i1 < mMokuaiValues.size(); i1++) {
                    CourseAttrBean.Value value = mMokuaiValues.get(i1);
                    if (i1 == i) {
                        value.setChecked(true);
                    } else {
                        value.setChecked(false);
                    }
                }
                getData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mSpinnerZhuanyong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void initNianfenChoiceData() {
        for (CourseAttrBean.Value value : mXueduanValues) {
            if (value.isChecked()) {
                mNianjiValues = mCourseAttrValues.get(value.getCascade());
                if (mNianjiValues != null && mNianjiValues.size() > 0) {
                    for (CourseAttrBean.Value nianji : mNianjiValues) {
                        nianji.setChecked(false);
                    }
                    initScreenState(mNianjiValues);
                    for (CourseAttrBean.Value mNianjiValue : mNianjiValues) {
                        if (mNianjiValue.isChecked()) {
                            mToolbar.setLeftText(mNianjiValue.getValueName());
                        }
                    }

                }//默认选中第一个
                mNianjiAdapter.addAllData(mNianjiValues);
                mNianjiAdapter.loadMoreComplete();
                mNianjiAdapter.setEnableLoadMore(false);
            }
        }

        initXuekeChoiseData();
    }

    private void initXuekeChoiseData() {
        for (CourseAttrBean.Value value : mNianjiValues) {
            if (value.isChecked()) {
                mXuekeValues = mCourseAttrValues.get(value.getCascade());
                mXuekeValues.get(0).setChecked(true);

                //  mXuekeValues.addAll(mCourseAttrValues.get(value.getCascade()));
                //  mXuekeAdapter.notifyDataSetChanged();
             /*   if (mXuekeValues != null && mXuekeValues.size() > 0) {
                    for (CourseAttrBean.Value xueke : mXuekeValues) {
                        xueke.setChecked(false);
                    }
                    initScreenState(mXuekeValues);
                }*/

            }
        }

        mXuekeAdapter = new CourseAttrSniAdapter(getActivity(), mXuekeValues);
        mSpinnerXueke.setAdapter(mXuekeAdapter);
        mXuekeAdapter.notifyDataSetChanged();
        initBanBenChoiseData();
    }

    private void initBanBenChoiseData() {
        CourseAttrBean.Value value = mXuekeValues.get(mSpinnerXueke.getSelectedItemPosition());
        mBanbenValues = mCourseAttrValues.get(value.getCascade());
        mBanbenValues.get(0).setChecked(true);
        mBanbenAdapter = new CourseAttrSniAdapter(getActivity(), mBanbenValues);
        mSpinnerBanben.setAdapter(mBanbenAdapter);
        initMoKuaiChoiseData();
    }

    private void initMoKuaiChoiseData() {
        CourseAttrBean.Value value = mBanbenValues.get(mSpinnerBanben.getSelectedItemPosition());
        mMokuaiValues = mCourseAttrValues.get(value.getCascade());
        if (null != mMokuaiValues && mMokuaiValues.size() > 0) {
            mMokuaiValues.get(0).setChecked(true);
        }
        mMokuaiAdapter = new CourseAttrSniAdapter(getActivity(), mMokuaiValues);
        mSpinnerMokuai.setAdapter(mMokuaiAdapter);
        getData();
    }


    @Override
    public void getCourseTypeSuccess(List<CourseTypeBean> list) {
        CourseTypeBean courseTypeBean = new CourseTypeBean();
        courseTypeBean.setName("全部类型");
        courseTypeBean.setId("");
        list.add(0, courseTypeBean);
        mTypeBeanList.addAll(list);
        mCourseTypeAdapter.notifyDataSetChanged();
    }

    @Override
    public void getCourseTypeFail(String msg) {
        showMsg(msg);
    }

    @Override
    public void getCourseAttrSuccess(CourseAttrBean attrBean) {
        mCourseAttrValues = attrBean.getValues();
        initXueDuanChoiceData();
    }

    private void initXueDuanChoiceData() {
        mXueduanValues = mCourseAttrValues.get("first");
        mXueduanAdapter.addAllData(mXueduanValues);
        mXueduanAdapter.loadMoreComplete();
        mXueduanAdapter.setEnableLoadMore(false);
        mXueduanAdapter.disableLoadMoreIfNotFullPage(mRcvXueduan);
        initScreenState(mXueduanValues);
        initNianfenChoiceData();
    }

    private void initScreenState(List<CourseAttrBean.Value> list) {
        //选过
        if (mListScreenIDs.size() > 0) {
            for (String mListScreenID : mListScreenIDs) {
                for (CourseAttrBean.Value value : list) {
                    if (mListScreenID.equals(value.getValueId())) {
                        value.setChecked(true);
                    }
                }
            }
            if (!checkHasChecked(list)) {
                list.get(0).setChecked(true);
            }

        }
        //未选过默认选中第一个
        else {
            list.get(0).setChecked(true);
        }

    }

    /**
     * 检测右侧筛选集合中是否有一个被选中 没
     *
     * @param list
     * @return
     */
    private boolean checkHasChecked(List<CourseAttrBean.Value> list) {
        boolean hasChecked = false;
        for (CourseAttrBean.Value value : list) {
            if (value.isChecked()) {
                hasChecked = true;
            }
        }
        return hasChecked;
    }

    @Override
    public void getCourseAttrFail(String msg) {

    }

    @Override
    public void getCourseListSuccess(List<MicroLessonResultBean> list) {
        if (AwDataUtil.isEmpty(list)) {
            mMicroLessonAdapter.clearData();
            mRcvData.removeAllViews();
            mMicroLessonAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
            return;
        }
        mList.clear();
        mList.addAll(list);
        mMicroLessonAdapter.addAllData(mList);
        mMicroLessonAdapter.notifyDataSetChanged();
    }

    @Override
    public void getCourseListFail(String msg) {
        showMsg(msg);
        mRcvData.removeAllViews();
        mMicroLessonAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
    }


    @OnClick({R.id.btn_reset, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_reset:
                //重置学段选中第一个
                for (CourseAttrBean.Value mXueduanValue : mXueduanValues) {
                    mXueduanValue.setChecked(false);
                }
                if (mXueduanValues.size() > 0) {
                    mXueduanValues.get(0).setChecked(true);
                }
                mXueduanAdapter.notifyDataSetChanged();
                //重置年级选中第一个
                for (CourseAttrBean.Value mNianjiValue : mNianjiValues) {
                    mNianjiValue.setChecked(false);
                }
                if (mNianjiValues.size() > 0) {
                    mNianjiValues.get(0).setChecked(true);
                }
                mNianjiAdapter.notifyDataSetChanged();
                break;
            case R.id.btn_confirm:
                mDrawerLayout.closeDrawers();
                getData();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }
}
