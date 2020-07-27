package com.jkrm.education.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzw.baselib.base.AwMvpLazyFragment;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.util.AwSpUtil;
import com.jkrm.education.R;
import com.jkrm.education.adapter.BookExercisesAdapter;
import com.jkrm.education.adapter.BookExercisesMainAdapter;
import com.jkrm.education.bean.QuestionBasketAddRequestBean;
import com.jkrm.education.bean.result.BookExercisesBean;
import com.jkrm.education.bean.result.BookExercisesListBean;
import com.jkrm.education.bean.result.ClassHourBean;
import com.jkrm.education.bean.result.LessonHourBean;
import com.jkrm.education.bean.result.PracticeDataResultBean;
import com.jkrm.education.bean.result.TopicScoreBean;
import com.jkrm.education.bean.result.VideoPointResultBean;
import com.jkrm.education.bean.result.VideoResultBean;
import com.jkrm.education.bean.rx.RxRefreshQuestionBasketType;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.presenters.BookExercisesPresent;
import com.jkrm.education.mvp.views.BookExercisesFragmentView;
import com.jkrm.education.ui.activity.FamousTeacherLectureActivity;
import com.jkrm.education.ui.activity.OnlineAnswerActivity;
import com.jkrm.education.ui.activity.OnlineMultipleChoiceActivity;
import com.jkrm.education.ui.activity.OnlineNonMultipleChoiceActivity;
import com.jkrm.education.ui.activity.QuestionBasketActivity;
import com.jkrm.education.ui.activity.QuestionExpandActivity;
import com.jkrm.education.ui.activity.SeeTargetQuestionActivity;
import com.jkrm.education.ui.activity.VideoPointActivity;
import com.jkrm.education.util.CustomFontStyleUtil;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.util.UserUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jkrm.education.constants.Extras.COMMON_PARAMS;

/**
 * @Description: 作业习题
 * @Author: xiangqian
 * @CreateDate: 2020/2/19 16:24
 */

public class BookExercisesFramgment extends AwMvpLazyFragment<BookExercisesPresent> implements BookExercisesFragmentView.View,
        SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_screen)
    TextView mTvScreen;
    @BindView(R.id.img_question_basket)
    ImageView mImgQuestionBasket;
    @BindView(R.id.tv_change)
    TextView mTvChange;
    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.elv_book_exercises)
    ExpandableListView mElvBookExercises;
    @BindView(R.id.rcv_xueduan)
    RecyclerView mRcvXueDuan;
    @BindView(R.id.rcv_pinpai)
    RecyclerView mRcvPinPai;
    @BindView(R.id.rcv_nianfen)
    RecyclerView mRcvNianFen;
    @BindView(R.id.rcv_xueke)
    RecyclerView mRcvXueKe;
    @BindView(R.id.rcv_banben)
    RecyclerView mRcvBanBen;
    @BindView(R.id.rcv_mokuai)
    RecyclerView mRcvMoKuai;
    @BindView(R.id.rcv_zhuanyong)
    RecyclerView mRcvZhuanYong;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    @BindView(R.id.sfl_layout)
    SwipeRefreshLayout mSflLayout;
    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;
    @BindView(R.id.btn_reset)
    Button mBtnReset;
    @BindView(R.id.tv_sec_title)
    TextView mTvSecTitle;
    @BindView(R.id.tv_num)
    TextView mTvNum;
    @BindView(R.id.tv_question_basket)
    TextView mTvQuestionBasket;
    @BindView(R.id.tv_videoPoint)
    TextView mTvVideoPoint;
    private MyAdapter mElvAdapter;
    boolean isAuto = false;//首次进入自动请求 列表 数据

    private HashMap<String, List<BookExercisesBean.Value>> mBookExercisesValues;    //筛选数据
    private List<BookExercisesBean.Value> mXueDuanValues = new ArrayList<>();//学段数据
    private List<BookExercisesBean.Value> mPinPaiValues = new ArrayList<>();//品牌数据
    private List<BookExercisesBean.Value> mNianFenValues = new ArrayList<>();//年份数据
    private List<BookExercisesBean.Value> mXueKeValues = new ArrayList<>();//学科数据
    private List<BookExercisesBean.Value> mBanBenValues = new ArrayList<>();//版本数据
    private List<BookExercisesBean.Value> mMoKuaiValues = new ArrayList<>();//模块数据
    private List<BookExercisesBean.Value> mZhuanYongValues = new ArrayList<>();//专用数据
    private BookExercisesAdapter mXueDuanAdapter, mPinPaiAdapter, mNianFenAdapter, mXueKeAdapter, mBanBenAdapter, mMoKuaiAdapter, mZhuanYongAdapter;
    String mStrClassHour = "";//请求课时参数
    String mStrClassHourKey = "";//请求主列表参数
    String mStrTempLate = "";//微课视频

    private int index = 1;
    private int totalPages = Integer.MAX_VALUE;


    /**
     * 对点微课列表
     */
    private List<VideoPointResultBean> mVideoPointResultBeanList = new ArrayList<>();
    private List<ClassHourBean.ChildrenBeanX> mGroupValueList = new ArrayList<>();
    private List<List<ClassHourBean.ChildrenBeanX.ChildrenBean>> mChildrenValueList = new ArrayList<>();
    private BookExercisesMainAdapter mAdapter;
    //筛选内容
    private List<BookExercisesListBean> mParcticeQuestBeanList;
    private BookExercisesListBean bean;
    private String mCourseId = "";//加入题篮需要的courseid

    private ArrayList<String> mListScreenIDs = new ArrayList<>();//右侧筛选记录
    private String mStringClassHourIDs = "";//左侧课时记录
    private List<List<ClassHourBean.ChildrenBeanX.ChildrenBean>> mItemList;


    @Override
    protected BookExercisesPresent createPresenter() {
        return new BookExercisesPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_book_exercises;
    }

    @Override
    public void onRefresh() {
        getData(true);
    }

    @Override
    protected void initView() {
        super.initView();
        // 禁止手势滑动
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mTvSecTitle.setTypeface(CustomFontStyleUtil.getNewRomenType());
    }

    @Override
    protected void initData() {
        super.initData();
        EventBus.getDefault().register(this);
        mPresenter.getBookExercisesList();
        getQuestionNum();
        initChoiceData();
        mAdapter = new BookExercisesMainAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, mAdapter, false);
        mAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
    }

    //拼接并请求课时列表
    private void splitClassHour() {
        mStrClassHour = "";
        if (mXueDuanValues.size() > 0) {
            for (BookExercisesBean.Value mXueDuanValue : mXueDuanValues) {
                if (mXueDuanValue.isChecked()) {
                    mStrClassHour += mXueDuanValue.getValueId() + ",";
                }
            }
        }
        if (mPinPaiValues.size() > 0) {
            for (BookExercisesBean.Value mPinPaiValue : mPinPaiValues) {
                if (mPinPaiValue.isChecked()) {
                    mStrClassHour += mPinPaiValue.getValueId() + ",";
                }
            }
        }

        if (mNianFenValues.size() > 0) {
            for (BookExercisesBean.Value mNianFenValue : mNianFenValues) {
                if (mNianFenValue.isChecked()) {
                    mStrClassHour += mNianFenValue.getValueId() + ",";
                }
            }
        }

        if (mXueKeValues.size() > 0) {
            for (BookExercisesBean.Value mXueKeValue : mXueKeValues) {
                if (mXueKeValue.isChecked()) {
                    mStrClassHour += mXueKeValue.getValueId() + ",";
                }
            }
        }

        if (mBanBenValues.size() > 0) {
            for (BookExercisesBean.Value mBanBenValue : mBanBenValues) {
                if (mBanBenValue.isChecked()) {
                    mStrClassHour += mBanBenValue.getValueId() + ",";
                }
            }
        }

        if (mMoKuaiValues.size() > 0) {
            for (BookExercisesBean.Value mMoKuaiValue : mMoKuaiValues) {
                if (mMoKuaiValue.isChecked()) {
                    mStrClassHour += mMoKuaiValue.getValueId() + ",";
                }
            }
        }

        if (null != mZhuanYongValues && mZhuanYongValues.size() > 0) {
            for (BookExercisesBean.Value mZhuanYongValue : mZhuanYongValues) {
                if (mZhuanYongValue.isChecked()) {
                    mStrClassHour += mZhuanYongValue.getValueId();
                }
            }
        }
        mPresenter.getClassHourList(mStrClassHour);
        AwSpUtil.saveString(MyConstant.SPConstant.XML_USER_INFO, MyConstant.SPConstant.KEY_BOOKEXERCISES_SCREEN, mStrClassHour);//保存筛选记录
    }

    private void initChoiceData() {
        mXueDuanAdapter = new BookExercisesAdapter();
        AwRecyclerViewUtil.setRecyclerViewGridlayout(mActivity, mRcvXueDuan, mXueDuanAdapter, 4);

        mPinPaiAdapter = new BookExercisesAdapter();
        AwRecyclerViewUtil.setRecyclerViewGridlayout(mActivity, mRcvPinPai, mPinPaiAdapter, 4);

        mNianFenAdapter = new BookExercisesAdapter();
        AwRecyclerViewUtil.setRecyclerViewGridlayout(mActivity, mRcvNianFen, mNianFenAdapter, 4);

        mXueKeAdapter = new BookExercisesAdapter();
        AwRecyclerViewUtil.setRecyclerViewGridlayout(mActivity, mRcvXueKe, mXueKeAdapter, 4);

        mBanBenAdapter = new BookExercisesAdapter();
        AwRecyclerViewUtil.setRecyclerViewGridlayout(mActivity, mRcvBanBen, mBanBenAdapter, 4);

        mMoKuaiAdapter = new BookExercisesAdapter();
        AwRecyclerViewUtil.setRecyclerViewGridlayout(mActivity, mRcvMoKuai, mMoKuaiAdapter, 4);

        mZhuanYongAdapter = new BookExercisesAdapter();
        AwRecyclerViewUtil.setRecyclerViewGridlayout(mActivity, mRcvZhuanYong, mZhuanYongAdapter, 4);

        mElvAdapter = new MyAdapter(mActivity.getApplicationContext(), mGroupValueList, mChildrenValueList);
        mElvBookExercises.setAdapter(mElvAdapter);

    }


    @Override
    protected void initListener() {
        super.initListener();
        mSflLayout.setOnRefreshListener(this);
        mXueDuanAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            BookExercisesBean.Value bookBean = (BookExercisesBean.Value) adapter.getItem(position);
            for (int i = 0; i < adapter.getItemCount(); i++) {
                BookExercisesBean.Value tempBean = (BookExercisesBean.Value) adapter.getItem(i);
                tempBean.setChecked(false);
            }
            bookBean.setChecked(true);
            mXueDuanAdapter.notifyDataSetChanged();
            initPinPaiChoiceData();
        });
        mPinPaiAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            BookExercisesBean.Value bookBean = (BookExercisesBean.Value) adapter.getItem(position);
            for (int i = 0; i < adapter.getItemCount(); i++) {
                BookExercisesBean.Value tempBean = (BookExercisesBean.Value) adapter.getItem(i);
                tempBean.setChecked(false);
            }
            bookBean.setChecked(true);
            mPinPaiAdapter.notifyDataSetChanged();
            initNianfenChoiceData();
        });
        mNianFenAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            BookExercisesBean.Value bookBean = (BookExercisesBean.Value) adapter.getItem(position);
            for (int i = 0; i < adapter.getItemCount(); i++) {
                BookExercisesBean.Value tempBean = (BookExercisesBean.Value) adapter.getItem(i);
                tempBean.setChecked(false);
            }
            bookBean.setChecked(true);
            mNianFenAdapter.notifyDataSetChanged();
            initXueKeChoiceData();
        });
        mXueKeAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            BookExercisesBean.Value bookBean = (BookExercisesBean.Value) adapter.getItem(position);
            for (int i = 0; i < adapter.getItemCount(); i++) {
                BookExercisesBean.Value tempBean = (BookExercisesBean.Value) adapter.getItem(i);
                tempBean.setChecked(false);
            }
            bookBean.setChecked(true);
            mXueKeAdapter.notifyDataSetChanged();
            initBanBenChoiceData();
        });
        mBanBenAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            BookExercisesBean.Value bookBean = (BookExercisesBean.Value) adapter.getItem(position);
            for (int i = 0; i < adapter.getItemCount(); i++) {
                BookExercisesBean.Value tempBean = (BookExercisesBean.Value) adapter.getItem(i);
                tempBean.setChecked(false);
            }
            bookBean.setChecked(true);
            mBanBenAdapter.notifyDataSetChanged();
            initMoKuaiChoiceData();
        });
        mMoKuaiAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            BookExercisesBean.Value bookBean = (BookExercisesBean.Value) adapter.getItem(position);
            for (int i = 0; i < adapter.getItemCount(); i++) {
                BookExercisesBean.Value tempBean = (BookExercisesBean.Value) adapter.getItem(i);
                tempBean.setChecked(false);
            }
            bookBean.setChecked(true);
            mMoKuaiAdapter.notifyDataSetChanged();
            initZhuanYongChoiceData();
        });
        mZhuanYongAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            BookExercisesBean.Value bookBean = (BookExercisesBean.Value) adapter.getItem(position);
            for (int i = 0; i < adapter.getItemCount(); i++) {
                BookExercisesBean.Value tempBean = (BookExercisesBean.Value) adapter.getItem(i);
                tempBean.setChecked(false);
            }
            bookBean.setChecked(true);
            mZhuanYongAdapter.notifyDataSetChanged();
        });
        mElvBookExercises.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {

                if (null != mGroupValueList && null != mGroupValueList.get(i)) {
                    ClassHourBean.ChildrenBeanX.ChildrenBean childrenBean = mGroupValueList.get(i).getChildren().get(i1);
                    mElvAdapter.initFlagSelected();
                    childrenBean.setChecked(true);
                    mElvAdapter.notifyDataSetChanged();
                    mStrClassHourKey = childrenBean.getKey();
                    // mPresenter.getBookMainList(mStrClassHourKey,UserUtil.getStudId());
                    getData(true);
                    mDrawerLayout.closeDrawers();
                    mTvSecTitle.setText(childrenBean.getTitle() + "");
                    return true;
                } else {
                    return false;
                }
            }
        });
        //mAdapter.setOnLoadMoreListener(this, mRcvData);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
//            showMsg(getString(R.string.common_function_is_dev));
            bean = (BookExercisesListBean) adapter.getItem(position);
            switch (view.getId()) {
               /* case R.id.iv_img:
                    toClass(ImgActivity.class, false, Extras.IMG_URL, bean.getRawScan());
                    break;*/
               /* case R.id.btn_studentAnswer:
//                    showMsg(getString(R.string.common_function_is_dev));
                    if (bean.getSmDto() == null || AwDataUtil.isEmpty(bean.getSmDto().getRawScan())) {
                        showDialog("学霸答卷图片不存在，无法展示");
                        return;
                    }
                    toClass(ImgActivity.class, false, Extras.IMG_URL, bean.getSmDto().getRawScan());
                    break;*/
                case R.id.rl_questionTitle:
                    break;
                //类题加练
                case R.id.btn_questionExpand:
                 /*   if("2".equals(bean.getGroupQuestion())){
                        //组题
                        toClass(OnlineMultipleChoiceActivity.class,false, Extras.COMMON_PARAMS, bean.getId());
                    }else if("2".equals(bean.getType().getIsOption())){
                        //单选 选择题
                        toClass(OnlineAnswerActivity.class,false, Extras.COMMON_PARAMS, bean.getId());
                    }else{
                        //非选择题
                        toClass(OnlineNonMultipleChoiceActivity.class,false, Extras.COMMON_PARAMS, bean.getId());
                    }*/
                    toClass(OnlineAnswerActivity.class,false,COMMON_PARAMS,bean.getId(),Extras.COURSE_ID,mCourseId);
                    //toClass(QuestionExpandActivity.class, false, COMMON_PARAMS, bean.getId());
                    break;
                case R.id.btn_famousTeacherLecture:
                    mPresenter.getVideos(bean.getId());
                    break;
                case R.id.btn_delFromBasket:
                    //  mErrorQuestionResultBean = bean;
                    if ("1".equals(bean.getPractice())) {
                        showDialogWithCancelDismiss("确认要将该题从题篮移出吗？", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dismissDialog();
                                mPresenter.delSomeOneQuestionBasket(bean.getId(), UserUtil.getStudId());
                            }
                        });
                    } else {
                        QuestionBasketAddRequestBean requestBean = new QuestionBasketAddRequestBean();
                        requestBean.setStudentId(UserUtil.getStudId());
                        requestBean.setClassId(UserUtil.getClassId());
                        requestBean.setCourseId(mCourseId);
                        requestBean.setAnswer(bean.getAnswer());
                        //requestBean.setHomeworkId(bean.getHomeworkId());
                        requestBean.setQuestionId(bean.getId());
                        requestBean.setQuestionNum(bean.getQuestionNum());
                        requestBean.setStudCode(UserUtil.getStudCode());
                        requestBean.setQuestionTypeId(bean.getType().getId());
                        // requestBean.setScanImage(bean.getr());
                        QuestionBasketAddRequestBean q = requestBean;
                        mPresenter.addQuestionBasket(RequestUtil.addQuestionBasketRequest(requestBean));
                    }
                    break;
                //查看答案
                case R.id.btn_seeAnswer:
                    //toClass(SeeTargetQuestionActivity.class, false, Extras.COMMON_BOOLEAN, MyDateUtil.isChoiceQuestion(bean.getType().getName(), bean.getType().getIsOption()) ? false : true, Extras.COMMON_PARAMS, bean.getId());
                    toClass(SeeTargetQuestionActivity.class, false, COMMON_PARAMS, bean.getId());
                    break;
            }
        });


    }

    private void initPinPaiChoiceData() {//初始化品牌选项
        for (BookExercisesBean.Value value : mXueDuanValues) {//遍历学段
            if (value.isChecked()) {//获取学段选中位置
                mPinPaiValues = mBookExercisesValues.get(value.getCascade());//根据选择学段获取品牌数据
                if (mPinPaiValues != null && mPinPaiValues.size() > 0) {
                    for (BookExercisesBean.Value mPinPaiValue : mPinPaiValues) {
                        mPinPaiValue.setChecked(false);
                    }
                    initScreenState(mPinPaiValues);
                   // mPinPaiValues.get(0).setChecked(true);
                }//默认选中第一个
                mPinPaiAdapter.addAllData(mPinPaiValues);
                mPinPaiAdapter.loadMoreComplete();
                mPinPaiAdapter.setEnableLoadMore(false);
            }
        }
        initNianfenChoiceData();
    }

    private void initNianfenChoiceData() {
        for (BookExercisesBean.Value value : mPinPaiValues) {
            if (value.isChecked()) {
                mNianFenValues = mBookExercisesValues.get(value.getCascade());
                if (mNianFenValues != null && mNianFenValues.size() > 0) {
                    for (BookExercisesBean.Value mNianFenValue : mNianFenValues) {
                        mNianFenValue.setChecked(false);
                    }
                    initScreenState(mNianFenValues);
                  //  mNianFenValues.get(0).setChecked(true);
                }
                mNianFenAdapter.addAllData(mNianFenValues);
                mNianFenAdapter.loadMoreComplete();
                mNianFenAdapter.setEnableLoadMore(false);

            }
        }
        initXueKeChoiceData();
    }

    private void initXueKeChoiceData() {
        for (BookExercisesBean.Value value : mNianFenValues) {
            if (value.isChecked()) {
                mXueKeValues = mBookExercisesValues.get(value.getCascade());
                if (mXueKeValues != null && mXueKeValues.size() > 0) {
                    for (BookExercisesBean.Value mXueKeValue : mXueKeValues) {
                        mXueKeValue.setChecked(false);
                    }
                    initScreenState(mXueKeValues);
                    //mXueKeValues.get(0).setChecked(true);
                }
                mXueKeAdapter.addAllData(mXueKeValues);
                mXueKeAdapter.loadMoreComplete();
                mXueKeAdapter.setEnableLoadMore(false);

            }
        }
        initBanBenChoiceData();
    }

    private void initBanBenChoiceData() {
        for (BookExercisesBean.Value value : mXueKeValues) {
            if (value.isChecked()) {
                mBanBenValues = mBookExercisesValues.get(value.getCascade());
                if (mBanBenValues != null && mBanBenValues.size() > 0) {
                    for (BookExercisesBean.Value mBanBenValue : mBanBenValues) {
                        mBanBenValue.setChecked(false);
                    }
                    initScreenState(mBanBenValues);
                    //mBanBenValues.get(0).setChecked(true);
                }
                mBanBenAdapter.addAllData(mBanBenValues);
                mBanBenAdapter.loadMoreComplete();
                mBanBenAdapter.setEnableLoadMore(false);
            }
        }
        initMoKuaiChoiceData();
    }

    private void initMoKuaiChoiceData() {
        for (BookExercisesBean.Value value : mBanBenValues) {
            if (value.isChecked()) {
                mMoKuaiValues = mBookExercisesValues.get(value.getCascade());
                if (mMoKuaiValues != null && mMoKuaiValues.size() > 0) {
                    for (BookExercisesBean.Value mMoKuaiValue : mMoKuaiValues) {
                        mMoKuaiValue.setChecked(false);
                    }
                    initScreenState(mMoKuaiValues);
                    //mMoKuaiValues.get(0).setChecked(true);
                }
                mMoKuaiAdapter.addAllData(mMoKuaiValues);
                mMoKuaiAdapter.loadMoreComplete();
                mMoKuaiAdapter.setEnableLoadMore(false);
            }
        }
        initZhuanYongChoiceData();
    }

    private void initZhuanYongChoiceData() {
        for (BookExercisesBean.Value value : mMoKuaiValues) {
            if (value.isChecked()) {
                mZhuanYongValues = mBookExercisesValues.get(value.getCascade());
                if (mZhuanYongValues != null && mZhuanYongValues.size() > 0) {
                    for (BookExercisesBean.Value mZhuanYongValue : mZhuanYongValues) {
                        mZhuanYongValue.setChecked(false);
                    }
                    initScreenState(mZhuanYongValues);
                    //mZhuanYongValues.get(0).setChecked(true);
                }
                mZhuanYongAdapter.addAllData(mZhuanYongValues);
                mZhuanYongAdapter.loadMoreComplete();
                mZhuanYongAdapter.setEnableLoadMore(false);
            }
        }
        //首次自动请求习题数据  第二次起需要点击确定按钮
        if (isAuto) {
            return;
        }
        isAuto = true;
        splitClassHour();
    }

    @OnClick({R.id.tv_title, R.id.tv_screen, R.id.img_question_basket, R.id.tv_change, R.id.tv_videoPoint, R.id.btn_confirm, R.id.btn_reset, R.id.tv_question_basket})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_title:
                break;
            case R.id.tv_screen:
                if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    mDrawerLayout.closeDrawers();
                } else {
                    if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                        mDrawerLayout.closeDrawers();
                    }
                    mDrawerLayout.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.img_question_basket:
            case R.id.tv_question_basket:
                toClass(QuestionBasketActivity.class, false);
                break;
            case R.id.tv_change:
                if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    mDrawerLayout.closeDrawers();
                } else {
                    if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                        mDrawerLayout.closeDrawers();
                    }
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                }
                break;

            case R.id.tv_videoPoint:
                //mPresenter.getLessonHour(mStrClassHourKey);
                if (!AwDataUtil.isEmpty(mVideoPointResultBeanList) && !TextUtils.isEmpty(mStrTempLate)) {
                    startActivity(new Intent(mActivity, VideoPointActivity.class)
                            .putExtra(Extras.KEY_BEAN_VIDEO_RESULT, (Serializable) mVideoPointResultBeanList)
                            .putExtra(Extras.KEY_TEMPLATE_ID, mStrTempLate));

                    //不传递templateId到下一个页面
                    //   toClass(VideoPointActivity.class, false, Extras.KEY_BEAN_VIDEO_RESULT, mVideoPointResultBeanList, Extras.COMMON_PARAMS, mRowsHomeworkBean.getHomeworkName());
//                toClass(VideoPointActivity.class, false, Extras.KEY_BEAN_VIDEO_RESULT, mVideoPointResultBeanList, Extras.COMMON_PARAMS, mRowsHomeworkBean.getHomeworkName(),
//                        Extras.KEY_TEMPLATE_ID, mRowsHomeworkBean.getTemplateId());
                } else {
                    showDialog("暂无微课视频");
                }
                break;
            //重置
            case R.id.btn_reset:
                AwSpUtil.saveString(MyConstant.SPConstant.XML_USER_INFO, MyConstant.SPConstant.KEY_BOOKEXERCISES_SCREEN, "");
                reSetScreenData();
                initXueDuanChoiceData();
                break;
            //确定
            case R.id.btn_confirm:
                splitClassHour();
                mDrawerLayout.closeDrawers();
                break;

        }
    }

    private void reSetScreenData() {
        ArrayList<List<BookExercisesBean.Value>> arrayLists = new ArrayList<>();
        arrayLists.add(mXueDuanValues);
        arrayLists.add(mPinPaiValues);
        arrayLists.add(mNianFenValues);
        arrayLists.add(mXueKeValues);
        arrayLists.add(mBanBenValues);
        arrayLists.add(mMoKuaiValues);
        arrayLists.add(mZhuanYongValues);
        for (List<BookExercisesBean.Value> arrayList : arrayLists) {
            if(null!=arrayList&&arrayList.size()>0){
                for (BookExercisesBean.Value value : arrayList) {
                    value.setChecked(false);
                }
            }
        }
    }


    @Override
    public void getBookExercisesListSuccess(BookExercisesBean bookExercisesBean) {
        mBookExercisesValues = bookExercisesBean.getValues();
        initXueDuanChoiceData();
    }


    //设置筛选选中状态
    private void initXueDuanChoiceData() {
        mListScreenIDs.clear();
        mStringClassHourIDs="";
        String str_screen = AwSpUtil.getString(MyConstant.SPConstant.XML_USER_INFO, MyConstant.SPConstant.KEY_BOOKEXERCISES_SCREEN, "");
        if (!TextUtils.isEmpty(str_screen)) {
            mListScreenIDs = new ArrayList<>(Arrays.asList(str_screen.split(",")));
        }
        mStringClassHourIDs = AwSpUtil.getString(MyConstant.SPConstant.XML_USER_INFO, MyConstant.SPConstant.KEY_BOOKEXERCISES_CLASS_HOUR, "");
        //初始化
        if(null==mBookExercisesValues||mBookExercisesValues.size()==0){
            return;
        }
        mXueDuanValues = mBookExercisesValues.get("first");
          initScreenState(mXueDuanValues);
       // mXueDuanValues.get(0).setChecked(true);
        mXueDuanAdapter.addAllData(mXueDuanValues);
        mXueDuanAdapter.loadMoreComplete();
        mXueDuanAdapter.setEnableLoadMore(false);
        mXueDuanAdapter.disableLoadMoreIfNotFullPage(mRcvXueDuan);
        initPinPaiChoiceData();
    }

    private void initScreenState(List<BookExercisesBean.Value> list) {
        //选过
        if(mListScreenIDs.size()>0){
            for (String mListScreenID : mListScreenIDs) {
                for (BookExercisesBean.Value value : list) {
                    if (mListScreenID.equals(value.getValueId())) {
                        value.setChecked(true);
                    }
                }
            }
            if(!checkHasChecked(list)){
                list.get(0).setChecked(true);
            }

        }
        //未选过默认选中第一个
        else{
            list.get(0).setChecked(true);
        }


    }

    /**
     * 检测右侧筛选集合中是否有一个被选中 没
     * @param list
     * @return
     */
    private boolean checkHasChecked(List<BookExercisesBean.Value> list) {
        boolean hasChecked=false;
        for (BookExercisesBean.Value value : list) {
            if(value.isChecked()){
               hasChecked=true;
            }
        }
        return hasChecked;
    }

    @Override
    public void getBookExercisesListFail(String msg) {
        // showToastDialog(msg);
    }

    @Override
    public void getClassHourListSuccess(ClassHourBean classHourBean) {
        mItemList = new ArrayList<>();
        List<ClassHourBean.ChildrenBeanX> group = classHourBean.getChildren();
        List<ClassHourBean.ChildrenBeanX> children = classHourBean.getChildren();
        List<ClassHourBean.ChildrenBeanX> new_children=new ArrayList<>();
      /*  if (null != group && group.size() > 0) {
            group.get(0).getChildren().get(0).setChecked(true);//选中第一个
        }*/
        for (ClassHourBean.ChildrenBeanX childrenBeanX : group) {
            mItemList.add(childrenBeanX.getChildren());
        }
        for (ClassHourBean.ChildrenBeanX child : children) {
            if(null!=child&&null!=child.getChildren()&&child.getChildren().size()>0){
                new_children.add(child);
            }
        }
        mGroupValueList = new_children;
        mElvAdapter.addData(new_children, mItemList);
        mElvAdapter.notifyDataSetChanged();
        spreadListView();//展开第一组
        mTvTitle.setText(classHourBean.getTitle());
        initSecTitleAndBookMainList();
    }

    //初始化副标题和主页数据
    private void initSecTitleAndBookMainList() {
        for (ClassHourBean.ChildrenBeanX childrenBeanX : mGroupValueList) {
            for (ClassHourBean.ChildrenBeanX.ChildrenBean child : childrenBeanX.getChildren()) {
                if (child.isChecked()) {
                    mStrClassHourKey = child.getKey();
                    //mPresenter.getBookMainList(mStrClassHourKey,UserUtil.getUserId());
                    getData(true);
                    mTvSecTitle.setText(child.getTitle() + "");
                }
            }
        }
    }

    @Override
    public void getClassHourListFail(String msg) {
        // showToastDialog(msg);
    }


    @Override
    public void getBookMainListSuccess(List<BookExercisesListBean> list) {
        mParcticeQuestBeanList.addAll(list);
        mAdapter.addAllData(mParcticeQuestBeanList);
        mAdapter.loadMoreComplete();
        mSflLayout.setRefreshing(false);
        mAdapter.disableLoadMoreIfNotFullPage(mRcvData);
        mPresenter.getTopicScore(mStrClassHourKey);//获取第几题 和 分数
        mPresenter.getLessonHour(mStrClassHourKey);//获取科目id
    }

    @Override
    public void getBookMainListFail(String msg) {
        //showToastDialog(msg);
        mSflLayout.setRefreshing(false);
        mAdapter.clearData();
        mRcvData.removeAllViews();
        mAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));

    }

    @Override
    public void getTopicScoreSuccess(List<TopicScoreBean> list) {
        for (TopicScoreBean topicScoreBean : list) {
            for (BookExercisesListBean parcticeQuestBean : mParcticeQuestBeanList) {
                if (topicScoreBean.getQuestionId().equals(parcticeQuestBean.getId())) {
                    parcticeQuestBean.setQuestionNum(topicScoreBean.getQuestionNum());
                    parcticeQuestBean.setMaxScore(topicScoreBean.getMaxScore());
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void getLessonHour(LessonHourBean list) {
        mStrTempLate = list.getTemplateId();
        mCourseId = list.getCourseId();
        mPresenter.getVideoPointListByTemplate(mStrTempLate);

    }

    @Override
    public void getLessonHourFail(String msg) {
        //showToastDialog("暂无微课视频");
    }

    @Override
    public void getVideoPointListByTemplateSuccess(List<VideoPointResultBean> list) {
        mVideoPointResultBeanList = list;
        if (mVideoPointResultBeanList.size() > 0) {
            mTvVideoPoint.setText("对点微课");
            mTvVideoPoint.setTextColor(getResources().getColor(R.color.colorPrimary));
            mTvVideoPoint.setEnabled(true);
        } else {
            mTvVideoPoint.setText("暂无对点微课");
            mTvVideoPoint.setTextColor(getResources().getColor(R.color.color_999999));
            mTvVideoPoint.setEnabled(false);
        }

    }

    @Override
    public void getVideoPointListByTemolateFail(String msg) {
        mTvVideoPoint.setText("暂无对点微课");
        mTvVideoPoint.setTextColor(getResources().getColor(R.color.color_999999));
        mTvVideoPoint.setEnabled(false);
    }

    @Override
    public void getVideosSuccess(VideoResultBean result) {
        if (result == null) {
            showMsg(getString(R.string.hint_no_famouse_teacher_video));
            return;
        }
        if (result.getQuestionVideo() == null && AwDataUtil.isEmpty(result.getCataVideos())) {
            showMsg(getString(R.string.hint_no_famouse_teacher_video));
            return;
        }
        toClass(FamousTeacherLectureActivity.class, false, Extras.KEY_BEAN_VIDEO_RESULT, result);
    }

    @Override
    public void getVideoFail(String msg) {
        showMsg(getString(R.string.hint_no_famouse_teacher_video));
    }

    @Override
    public void addQuestionBasketSuccess(String msg) {
        showMsg("添加题篮成功");
        getQuestionNum();
        for (BookExercisesListBean bookExercisesListBean : mParcticeQuestBeanList) {
            if (bookExercisesListBean.getId().equals(bean.getId())) {
                bookExercisesListBean.setPractice("1");
            }
        }
        mAdapter.notifyDataSetChanged();
        List<String> questionIds = new ArrayList<>();
        questionIds.add(bean.getId());
        EventBus.getDefault().postSticky(new RxRefreshQuestionBasketType(RxRefreshQuestionBasketType.TAG_ERROR_QUESTION, false, questionIds));
    }

    @Override
    public void delSomeOneQuestionBasketSuccess(String msg) {
        showMsg("移出题篮成功");
        getQuestionNum();
        for (BookExercisesListBean bookExercisesListBean : mParcticeQuestBeanList) {
            if (bookExercisesListBean.getId().equals(bean.getId())) {
                bookExercisesListBean.setPractice("0");
            }
        }
        mAdapter.notifyDataSetChanged();
        List<String> questionIds = new ArrayList<>();
        questionIds.add(bean.getId());
        EventBus.getDefault().postSticky(new RxRefreshQuestionBasketType(RxRefreshQuestionBasketType.TAG_ERROR_QUESTION, true, questionIds));
    }

    private void getQuestionNum() {
        mPresenter.getPracticeDataList(UserUtil.getStudId(), "", "", "", "");
    }

    @Override
    public void getPracticeDataListSuccess(PracticeDataResultBean bean) {
        if (bean.getQuest().size() > 99) {
            mTvNum.setText("99");
        } else {
            mTvNum.setText(bean.getQuest().size() + "");
        }
    }

    @Override
    public void getPracticeDataListFail(String msg) {

    }

    @Override
    public void onLoadMoreRequested() {
        if (index < totalPages) {
            index++;
            getData(false);
        } else {
            mAdapter.loadMoreEnd(true);
        }
    }

    private void getData(boolean needResetIndex) {
        if (needResetIndex) {
            index = 1;
            mParcticeQuestBeanList = new ArrayList<>();
            mAdapter.notifyDataSetChanged();
        }
        mPresenter.getBookMainList(mStrClassHourKey, UserUtil.getStudId());
        AwSpUtil.saveString(MyConstant.SPConstant.XML_USER_INFO, MyConstant.SPConstant.KEY_BOOKEXERCISES_CLASS_HOUR, mStrClassHourKey);//保存课时选中记录
    }


    class MyAdapter extends BaseExpandableListAdapter {

        private Context mContext;
        private final LayoutInflater mInflater;

        private List<ClassHourBean.ChildrenBeanX> mGroup;
        private List<List<ClassHourBean.ChildrenBeanX.ChildrenBean>> mItemList;


        public MyAdapter(Context context, List<ClassHourBean.ChildrenBeanX> group, List<List<ClassHourBean.ChildrenBeanX.ChildrenBean>> itemList) {
            this.mContext = context;
            this.mGroup = group;
            this.mItemList = itemList;
            mInflater = LayoutInflater.from(context);
        }

        //父项的个数
        @Override
        public int getGroupCount() {
            return mGroup.size();
        }

        //某个父项的子项的个数
        @Override
        public int getChildrenCount(int groupPosition) {
            return mItemList.get(groupPosition).size();
        }

        //获得某个父项
        @Override
        public Object getGroup(int groupPosition) {
            return mGroup.get(groupPosition);
        }

        //获得某个子项
        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return mItemList.get(groupPosition).get(childPosition);
        }

        //父项的Id
        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        //子项的id

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        //获取父项的view
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.bookexercises_elv_group_item_layout, parent, false);
            }
            ClassHourBean.ChildrenBeanX childrenBeanX = mGroup.get(groupPosition);
            TextView tvGroup = (TextView) convertView.findViewById(R.id.tv_title);
            tvGroup.setTypeface(CustomFontStyleUtil.getNewRomenType());
            tvGroup.setText(childrenBeanX.getTitle());
            return convertView;
        }

        //获取子项的view
        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ClassHourBean.ChildrenBeanX.ChildrenBean childrenBean = mItemList.get(groupPosition).get(childPosition);
            final String child = childrenBean.getTitle();
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.bookexercises_elv_item_layout, parent, false);
            }
            TextView tvChild = (TextView) convertView.findViewById(R.id.tv_title);
            tvChild.setTypeface(CustomFontStyleUtil.getNewRomenType());
            if (childrenBean.isChecked()) {
                tvChild.setTextColor(getResources().getColor(R.color.colorPrimary));
            } else {
                tvChild.setTextColor(Color.BLACK);
            }
            tvChild.setText(child);
            return convertView;

        }

        //初始化子条目全部为未选中
        public void initFlagSelected() {
            if (null != mItemList && mItemList.size() > 0) {
                for (int t = 0; t < mItemList.size(); t++) {
                    if (null != mItemList.get(t) && mItemList.get(t).size() > 0) {
                        for (int k = 0; k < mItemList.get(t).size(); k++) {
                            mItemList.get(t).get(k).setChecked(false);
                        }
                    }
                }
            }

        }

        public void addData(List<ClassHourBean.ChildrenBeanX> group, List<List<ClassHourBean.ChildrenBeanX.ChildrenBean>> itemList) {
            this.mGroup = group;
            this.mItemList = itemList;
            notifyDataSetChanged();
        }

        //子项是否可选中,如果要设置子项的点击事件,需要返回true
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }


    /**
     * 默认展开第一组 要在setAdapter后调用
     */
    private void spreadListView() {
        int groupCount = mElvAdapter.getGroupCount();
        // if (groupCount > 0) {
        //  mElvBookExercises.expandGroup(0);
        // }
        //检测本地是否有课时记录  有的话选中
        for (int i = 0; i < mItemList.size(); i++) {
            List<ClassHourBean.ChildrenBeanX.ChildrenBean> childrenBeans = mItemList.get(i);
            for (ClassHourBean.ChildrenBeanX.ChildrenBean childrenBean : childrenBeans) {
                if(mStringClassHourIDs.equals(childrenBean.getKey())){
                    childrenBean.setChecked(true);
                   // mElvBookExercises.expandGroup(i);
                }
            }
        }
        //如 无课时记录则默认选中第一个
        if(!checkHasClassHour()){
            mItemList.get(0).get(0).setChecked(true);
        }
        //检测 课时所在组 并展开
        for (int i = 0; i < mItemList.size(); i++) {
            List<ClassHourBean.ChildrenBeanX.ChildrenBean> childrenBeans = mItemList.get(i);
            for (ClassHourBean.ChildrenBeanX.ChildrenBean childrenBean : childrenBeans) {
                if(childrenBean.isChecked()){
                    mElvBookExercises.expandGroup(i);
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getQuestionNum();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshByBus(RxRefreshQuestionBasketType type) {
        if (type != null && !type.getTag().equals(RxRefreshQuestionBasketType.TAG_ERROR_QUESTION)) {
            if (AwDataUtil.isEmpty(mParcticeQuestBeanList)) {
                return;
            }
            // boolean needRefresh = false;
            List<String> questionIds = type.getQuestionIds();
            for (BookExercisesListBean temp : mParcticeQuestBeanList) {
                for (String tempId : questionIds) {
                    if (temp.getId().equals(tempId)) {
                        if (type.isDel()) {
                            temp.setPractice("0");
                        } else {
                            temp.setPractice("1");
                        }
                        //           needRefresh = true;
                    }
                }
            }
           /* if (needRefresh) {
                mAdapter.notifyDataSetChanged();
            }*/
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 检测是否有课时被选中
     * @return
     */
    private boolean checkHasClassHour() {
        boolean hasChecked=false;
        for (List<ClassHourBean.ChildrenBeanX.ChildrenBean> childrenBeans : mItemList) {
            for (ClassHourBean.ChildrenBeanX.ChildrenBean childrenBean : childrenBeans) {
                if(childrenBean.isChecked()){
                    hasChecked=true;
                }
            }
        }
        return hasChecked;
    }

}
