package com.jkrm.education.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hzw.baselib.base.AwMvpLazyFragment;
import com.jkrm.education.R;
import com.jkrm.education.bean.ErrorQuestionClassifyBean;
import com.jkrm.education.bean.ErrorQuestionDetailBean;
import com.jkrm.education.bean.ErrorQuestionTimeBean;
import com.jkrm.education.bean.ErrorQuestionTimePagedBean;
import com.jkrm.education.bean.rx.RxEditType;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.ErrorQuestionFragmentPresent;
import com.jkrm.education.mvp.views.ErrorQuestionFragmentView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * @Description: 分类
 * @Author: xiangqian
 * @CreateDate: 2020/6/3 16:15
 */

public class ErrorQuestionClassifyFragment extends AwMvpLazyFragment<ErrorQuestionFragmentPresent> implements ErrorQuestionFragmentView.View {
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpageer)
    ViewPager mViewpageer;
    private ArrayList<String> mTitleList=new ArrayList<>();
    public static  String CLASSIFY_ID="1";


    @Override
    protected int getLayoutId() {
        return R.layout. error_question_classify_fragment_layout;
    }

    @Override
    protected void initView() {
        super.initView();
        mTitleList.add("作业");
        //mTitleList.add("考试");
        mTitleList.add("练习");
        mViewpageer.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public int getCount() {
                return mTitleList.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitleList.get(position);
            }

            @Override
            public int getItemPosition(@NonNull Object object) {
                return super.getItemPosition(object);
            }

            @Override
            public Fragment getItem(int position) {
                ErrorQuestionClassifyChildrenFragment errorQuestionClassifyChildrenFragment = new ErrorQuestionClassifyChildrenFragment();
                Bundle bundle = new Bundle();


                int tabType=1;
                if(position>0){
                    tabType=3;
                }
                bundle.putString(Extras.KEY_CLASSIFY,tabType+"");
                errorQuestionClassifyChildrenFragment.setArguments(bundle);
                return  errorQuestionClassifyChildrenFragment;
            }

        });
        mViewpageer.setOffscreenPageLimit(mTitleList.size());
        mTabLayout.setupWithViewPager(mViewpageer);

    }

    @Override
    protected void initListener() {
        super.initListener();
        mViewpageer.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // TODO: 2020/6/23 分类少一个
                if(position>0){
                    CLASSIFY_ID="3";
                }else{
                    CLASSIFY_ID=position+1+"";
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected ErrorQuestionFragmentPresent createPresenter() {
        return new ErrorQuestionFragmentPresent(this);
    }


    @Override
    public void getByClassifySuccess(List<ErrorQuestionClassifyBean> list) {

    }

    @Override
    public void getByClassifyFail(String msg) {

    }

    @Override
    public void getByTimeSuccess(List<ErrorQuestionTimeBean> list) {

    }

    @Override
    public void getByTimeSFail(String msg) {

    }

    @Override
    public void getErrorDetailSuccess(List<ErrorQuestionDetailBean> list) {

    }

    @Override
    public void getErrorDetailFail(String msg) {

    }

    @Override
    public void getByTimePagedSuccess(ErrorQuestionTimePagedBean bean) {

    }

    @Override
    public void getByTimePagedFail(String msg) {

    }


}