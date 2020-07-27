package com.jkrm.education.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.hzw.baselib.base.AwBaseActivity;
import com.hzw.baselib.util.AwDataUtil;
import com.jkrm.education.R;
import com.jkrm.education.bean.rx.RxEditType;
import com.jkrm.education.bean.rx.RxErrorTypeBean;
import com.jkrm.education.bean.rx.RxLQuestionType;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.ui.fragment.ErrorQuestionClassifyFragment;
import com.jkrm.education.ui.fragment.ErrorQuestionTimeFragment;
import com.shizhefei.view.viewpager.SViewPager;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ErrorQuestionActivity extends AwBaseActivity {

    @BindView(R.id.svp)
    SViewPager mSvp;
    ArrayList<Fragment> mFragmentList;
    @BindView(R.id.magic_indicator)
    MagicIndicator mMagicIndicator;
    @BindView(R.id.tv_chose)
    TextView mTvChose;
    @BindView(R.id.rl_of_tab)
    RelativeLayout mRlOfTab;
    @BindView(R.id.id_chose_all)
    TextView mIdChoseAll;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R.id.rl_of_chose)
    RelativeLayout mRlOfChose;
    @BindView(R.id.img_back)
    ImageView mImgBack;
    @BindView(R.id.spinner)
    Spinner mSpinner;

    private MyAdapter mAdapter;
    public static String mCourseId;
    private ArrayList<String> mList = new ArrayList<>();
    private boolean isAllChose=true;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_error_question;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusTxtDark();
        mCourseId = getIntent().getStringExtra(Extras.COURSE_ID);
        if (AwDataUtil.isEmpty(mCourseId)) {
            showDialogToFinish("获取信息失败");
            return;
        }
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new ErrorQuestionTimeFragment());
        mFragmentList.add(new ErrorQuestionClassifyFragment());
        mAdapter = new MyAdapter(getSupportFragmentManager());
        mSvp.setAdapter(mAdapter);
        initMagicIndicator();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mSvp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (0 == position) {
                  //  mTvChose.setVisibility(View.VISIBLE);
                    mSpinner.setVisibility(View.VISIBLE);
                } else if (position == mList.size() - 1) {
                   // mTvChose.setVisibility(View.GONE);
                    mSpinner.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        EventBus.getDefault().post(new RxErrorTypeBean(""));
                        break;
                    case 1:
                        EventBus.getDefault().post(new RxErrorTypeBean("1"));
                        break;
                    case 2:
                        EventBus.getDefault().post(new RxErrorTypeBean("2"));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initMagicIndicator() {
        mList.add("时间");
        mList.add("分类");
        mMagicIndicator.setBackgroundResource(R.drawable.round_indicator_bg);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mList == null ? 0 : mList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);
                clipPagerTitleView.setText(mList.get(index));
              /*  clipPagerTitleView.setTextColor(getResources().getColor(R.color.colorAccent));
                clipPagerTitleView.setClipColor(Color.WHITE);*/
                clipPagerTitleView.setTextColor(Color.WHITE);
                clipPagerTitleView.setClipColor(getResources().getColor(R.color.colorAccent));
                clipPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mSvp.setCurrentItem(index);
                    }
                });
                return clipPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                float navigatorHeight = context.getResources().getDimension(R.dimen.dimen_30);
                float borderWidth = UIUtil.dip2px(context, 1);
                float lineHeight = navigatorHeight - 2 * borderWidth;
                indicator.setLineHeight(lineHeight);
                indicator.setRoundRadius(lineHeight / 2);
                indicator.setYOffset(borderWidth);
                indicator.setColors(getResources().getColor(R.color.white));
                return indicator;
            }
        });
        mMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mSvp);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.id_chose_all, R.id.tv_cancel, R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_chose_all:
                EventBus.getDefault().post(new RxEditType(true, isAllChose));
                isAllChose=!isAllChose;
                if(isAllChose){
                    mIdChoseAll.setText("全选");
                }else{
                    mIdChoseAll.setText("取消全选");
                }
                break;
            case R.id.tv_cancel:
                //非选择状态
                initTitle(false);
                EventBus.getDefault().post(new RxEditType(false, false));
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }

    private class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }

    public String getCourseId() {
        return mCourseId;
    }

    public void setCourseId(String courseId) {
        mCourseId = courseId;
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEvent(RxEditType rxEditType) {
        if (rxEditType != null) {
            //选择状态
            if (rxEditType.isEdit()) {
                initTitle(true);
            } else {
                initTitle(false);
            }
        }

    }
    //通知选了题目数量
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChoseChangeEvent(RxLQuestionType rxLQuestionType){
        if(1==rxLQuestionType.getType()){
            mTvTitle.setText(rxLQuestionType.getNum());
        }
    }

    public void initTitle(boolean isChose) {
        if (isChose) {
            showView(mRlOfChose,true);
            showView(mRlOfTab,false);
        } else {
            showView(mRlOfChose,false);
            showView(mRlOfTab,true);

        }
        mIdChoseAll.setText("全选");

    }

    @Override
    protected void onPause() {
        super.onPause();
        isAllChose=false;
        mIdChoseAll.setText("全选");
    }
}
