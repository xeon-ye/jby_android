package com.jkrm.education.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
import com.jkrm.education.ui.activity.MainActivity;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.util.UserUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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
    @BindView(R.id.toolbar_custom)
    AwViewCustomToolbar toolbarCustom;
    @BindView(R.id.ll_title)
    LinearLayout llTitle;
    @BindView(R.id.tv_xueke)
    TextView tvXueke;
    @BindView(R.id.tv_banben)
    TextView tvBanben;
    @BindView(R.id.tv_mokuai)
    TextView tvMokuai;
    @BindView(R.id.tv_zhuanyong)
    TextView tvZhuanyong;
    @BindView(R.id.rcv_xueke)
    RecyclerView rcvXueke;
    @BindView(R.id.rcv_banben)
    RecyclerView rcvBanben;
    @BindView(R.id.rcv_mokuai)
    RecyclerView rcvMokuai;
    @BindView(R.id.rcv_zhuanyong)
    RecyclerView rcvZhuanyong;
    @BindView(R.id.ll_of_setting)
    LinearLayout mLlOfSetting;


    private MicroLessonAdapter mMicroLessonAdapter=new MicroLessonAdapter();
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
    public static String mStrCourseId,mStrCourseName;



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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MainActivity.MyTouchListener myTouchListener = new MainActivity.MyTouchListener() {
            @Override
            public boolean onTouchEvent(MotionEvent event) {
                if(touchEventInView(mLlOfSetting,event.getX(),event.getY())||touchEventInView(rcvXueke,event.getX(),event.getY())||
                        touchEventInView(rcvBanben,event.getX(),event.getY())||
                        touchEventInView(rcvMokuai,event.getX(),event.getY())||
                        touchEventInView(rcvZhuanyong,event.getX(),event.getY())){
                    return false;
                }else{
                    hideSettingLayout(5);
                    return true;
                }
                // 处理手势事件
            }
        };
        // 将myTouchListener注册到分发列表
        ((MainActivity)this.getActivity()).registerMyTouchListener(myTouchListener);
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    protected void initData() {
        //主列表
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, mMicroLessonAdapter, false);
        //类型
        mCourseTypeAdapter = new CourseTypeAdapter();
        //学段 年纪
        mXueduanAdapter = new CourseAttrRcvAdapter();
        AwRecyclerViewUtil.setRecyclerViewGridlayout(mActivity, mRcvXueduan, mXueduanAdapter, 4);
        mNianjiAdapter = new CourseAttrRcvAdapter();
        AwRecyclerViewUtil.setRecyclerViewGridlayout(mActivity, mRcvNianji, mNianjiAdapter, 4);
        //最后一个下拉框
        //mSpinnerZhuanyong.setAdapter(mCourseTypeAdapter);
        AwRecyclerViewUtil.setRecyclerViewGridlayout(mActivity, rcvZhuanyong, mCourseTypeAdapter, 4);
        mXuekeAdapter = new CourseAttrSniAdapter();
        mBanbenAdapter = new CourseAttrSniAdapter();
        mMokuaiAdapter = new CourseAttrSniAdapter();
        AwRecyclerViewUtil.setRecyclerViewGridlayout(mActivity, rcvXueke, mXuekeAdapter, 4);
        AwRecyclerViewUtil.setRecyclerViewGridlayout(mActivity, rcvBanben, mBanbenAdapter, 4);
        AwRecyclerViewUtil.setRecyclerViewGridlayout(mActivity, rcvMokuai, mMokuaiAdapter, 4);
        mPresenter.getCourseType(RequestUtil.emptyRequest());//请求微课类型  最右侧下拉选项
        mPresenter.getCourseAttr();
    }

    private void getData() {
        isAuto = true;
        mStrPcvId = "";
        mStrTypeId = "";
        splitPcvId();
        for (int i = 0; i < mTypeBeanList.size(); i++) {
            if(mTypeBeanList.get(i).isChecked()){
                mStrTypeId=mTypeBeanList.get(i).getId();
            }
        }
        mPresenter.getCourseList(RequestUtil.getCourseRequestBody(mStrPcvId, mStrTypeId));
    }

    private void splitPcvId() {
        if (mXueduanValues.size() > 0) {
            for (CourseAttrBean.Value xueduan : mXueduanValues) {
                if (xueduan.isChecked()) {
                    mStrPcvId += xueduan.getValueId() + ",";
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
            for (CourseAttrBean.Value xueke : mXuekeValues) {
                if (xueke.isChecked()) {
                    mStrPcvId += xueke.getValueId() + ",";
                    mStrCourseId=xueke.getId();
                    mStrCourseName=xueke.getName();
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

        if (null != mMokuaiValues && mMokuaiValues.size() > 0) {
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
                hideSettingLayout(0);
                showView(mLlOfSetting, !(mLlOfSetting.getVisibility() == View.VISIBLE));
            }
        });
        //主页面列表点击
        mMicroLessonAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                hideSettingLayout(5);
                MicroLessonResultBean microLessonResultBean = mList.get(position);
                //校园通可以看所有
                if ("1".equals(microLessonResultBean.getWhetherBuySch())) {
                    toClass(CoursePurchasedActivity.class, false, Extras.KEY_COURSE_BEAN, mList.get(position));
                }
                //免费
                else if ("0".equals(microLessonResultBean.getWhetherFree())) {
                    toClass(CoursePurchasedActivity.class, false, Extras.KEY_COURSE_BEAN, mList.get(position));
                }
                //vip
                else if ("1".equals(microLessonResultBean.getWhetherVip())) {
                    toClass(CoursePurchasedActivity.class, false, Extras.KEY_COURSE_BEAN, mList.get(position));
                }
                //未购买
                else if ("0".equals(microLessonResultBean.getWhetherBuy())) {
                    toClass(CourseNotpurchasedActivity.class, false, Extras.KEY_COURSE_BEAN, mList.get(position));
                    //已购买
                } else if ("1".equals(microLessonResultBean.getWhetherBuy())) {
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
        mXuekeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<CourseAttrBean.Value> data = adapter.getData();
                for (int i = 0; i < data.size(); i++) {
                    if (position == i) {
                        data.get(position).setChecked(true);
                        tvXueke.setText(data.get(position).getValueName());
                    } else {
                        data.get(i).setChecked(false);
                    }
                }

                mXuekeAdapter.notifyDataSetChanged();
                initBanBenChoiseData();
                hideSettingLayout(5);

            }
        });
        mBanbenAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<CourseAttrBean.Value> data = adapter.getData();
                for (int i = 0; i < data.size(); i++) {
                    if (position == i) {
                        data.get(position).setChecked(true);
                        tvBanben.setText(data.get(position).getValueName());

                    } else {
                        data.get(i).setChecked(false);
                    }
                }
                mBanbenAdapter.notifyDataSetChanged();
                initMoKuaiChoiseData();
                hideSettingLayout(5);
            }
        });
        mMokuaiAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<CourseAttrBean.Value> data = adapter.getData();
                for (int i = 0; i < data.size(); i++) {
                    if (position == i) {
                        data.get(position).setChecked(true);
                        tvMokuai.setText(data.get(position).getValueName());
                    } else {
                        data.get(i).setChecked(false);
                    }
                }
                mMokuaiAdapter.notifyDataSetChanged();
                hideSettingLayout(5);

                getData();
            }
        });

        mCourseTypeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<CourseTypeBean> data = adapter.getData();
                for (int i = 0; i < data.size(); i++) {
                    if (position == i) {
                        data.get(position).setChecked(true);
                        tvZhuanyong.setText(data.get(position).getName());
                    } else {
                        data.get(i).setChecked(false);
                    }
                }
                mCourseTypeAdapter.notifyDataSetChanged();
                hideSettingLayout(5);
                getData();
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
            }
        }


        mXuekeAdapter.addAllData(mXuekeValues);
        initBanBenChoiseData();
    }

    private void initBanBenChoiseData() {

        for (int i = 0; i < mXuekeValues.size(); i++) {
            if (mXuekeValues.get(i).isChecked()) {
                CourseAttrBean.Value value = mXuekeValues.get(i);
                tvXueke.setText(value.getValueName());
                mBanbenValues = mCourseAttrValues.get(value.getCascade());
                mBanbenValues.get(0).setChecked(true);
                mBanbenAdapter.addAllData(mBanbenValues);
                initMoKuaiChoiseData();
                break;
            }
        }

    }

    private void initMoKuaiChoiseData() {
        for (int i = 0; i < mBanbenValues.size(); i++) {
            if (mBanbenValues.get(i).isChecked()) {
                CourseAttrBean.Value value = mBanbenValues.get(i);
                tvBanben.setText(value.getValueName());
                mMokuaiValues = mCourseAttrValues.get(value.getCascade());
                if (null != mMokuaiValues && mMokuaiValues.size() > 0) {
                    mMokuaiValues.get(0).setChecked(true);
                }
                mMokuaiAdapter.addAllData(mMokuaiValues);
                for (CourseAttrBean.Value mMokuaiValue : mMokuaiValues) {
                    if (mMokuaiValue.isChecked()) {
                        tvMokuai.setText(mMokuaiValue.getValueName());
                    }
                }
                getData();
            }

        }

    }


    @Override
    public void getCourseTypeSuccess(List<CourseTypeBean> list) {
        CourseTypeBean courseTypeBean = new CourseTypeBean();
        courseTypeBean.setName("全部类型");
        courseTypeBean.setId("");
        list.add(0, courseTypeBean);
        mTypeBeanList.addAll(list);
        mTypeBeanList.get(0).setChecked(true);
        tvZhuanyong.setText(mTypeBeanList.get(0).getName());
        mCourseTypeAdapter.addAllData(mTypeBeanList);

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
        initScreenState(mXueduanValues);
   /*     boolean b = checkIsHighSchool();
        if(b){
            for (CourseAttrBean.Value mXueduanValue : mXueduanValues) {
                if(mXueduanValue.getValueName().contains("高")){
                    mXueduanValue.setChecked(true);
                }else{
                    mXueduanValue.setChecked(false);
                }
            }
        }else{
            for (CourseAttrBean.Value mXueduanValue : mXueduanValues) {
                if(!mXueduanValue.getValueName().contains("高")){
                    mXueduanValue.setChecked(true);
                }else{
                    mXueduanValue.setChecked(false);
                }
            }
        }*/
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
        showMsg(msg);
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
        mMicroLessonAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
    }


    @OnClick({R.id.btn_reset, R.id.btn_confirm, R.id.tv_xueke, R.id.tv_banben, R.id.tv_mokuai, R.id.tv_zhuanyong})
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
                initNianfenChoiceData();
                break;
            case R.id.btn_confirm:
                hideSettingLayout(0);
                showView(mLlOfSetting, false);
                getData();
                break;
            case R.id.tv_xueke:
                hideSettingLayout(1);
                break;
            case R.id.tv_banben:
                hideSettingLayout(2);
                break;
            case R.id.tv_mokuai:
                hideSettingLayout(3);
                break;
            case R.id.tv_zhuanyong:
                hideSettingLayout(4);
                break;
        }
    }

    private void hideSettingLayout(int type) {
        switch (type) {
            case 0:
                showView(mLlOfSetting, false);
                showView(rcvXueke, false);
                showView(rcvBanben, false);
                showView(rcvMokuai, false);
                showView(rcvZhuanyong, false);
                break;
            case 1:
                showView(mLlOfSetting, false);
                showView(rcvXueke, true);
                showView(rcvBanben, false);
                showView(rcvMokuai, false);
                showView(rcvZhuanyong, false);
                break;
            case 2:
                showView(mLlOfSetting, false);
                showView(rcvXueke, false);
                showView(rcvBanben, true);
                showView(rcvMokuai, false);
                showView(rcvZhuanyong, false);
                break;
            case 3:
                showView(mLlOfSetting, false);
                showView(rcvXueke, false);
                showView(rcvBanben, false);
                showView(rcvMokuai, true);
                showView(rcvZhuanyong, false);
                break;
            case 4:
                showView(mLlOfSetting, false);
                showView(rcvXueke, false);
                showView(rcvBanben, false);
                showView(rcvMokuai, false);
                showView(rcvZhuanyong, true);
                break;
            case 5:
                showView(mLlOfSetting, false);
                showView(rcvXueke, false);
                showView(rcvBanben, false);
                showView(rcvMokuai, false);
                showView(rcvZhuanyong, false);
                break;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    /**
     * 该方法检测一个点击事件是否落入在一个View内，换句话说，检测这个点击事件是否发生在该View上。
     *
     * @param view
     * @param x
     * @param y
     * @return
     */
    private boolean touchEventInView(View view, float x, float y) {
        if (view == null) {
            return false;
        }

        int[] location = new int[2];
        view.getLocationOnScreen(location);

        int left = location[0];
        int top = location[1];

        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();

        if (y >= top && y <= bottom && x >= left && x <= right) {
            return true;
        }

        return false;
    }

    /**
     * 包含高字选中高中 否则选中初中
     * @return
     */
    private boolean checkIsHighSchool(){
        return UserUtil.getAppUser().getGradeName().contains("高");
    }
}
