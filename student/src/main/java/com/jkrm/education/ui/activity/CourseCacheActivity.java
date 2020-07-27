package com.jkrm.education.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.hzw.baselib.base.AwBaseActivity;
import com.hzw.baselib.util.MemoryTool;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.jkrm.education.R;
import com.jkrm.education.adapter.CourseSuccessAdapter;
import com.jkrm.education.db.DaoVideoBean;
import com.jkrm.education.db.util.DaoUtil;
import com.jkrm.education.mvp.views.CourseCacheSuccessView;
import com.jkrm.education.ui.fragment.BookExercisesFramgment;
import com.jkrm.education.ui.fragment.CourseCacheInProFragment;
import com.jkrm.education.ui.fragment.CourseCacheSuccessFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 课程缓存
 */
public class CourseCacheActivity extends AwBaseActivity {

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpageer)
    ViewPager mViewpageer;
    private List<String> mTitle=new ArrayList<>();
    private List<Fragment> mFragment=new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_course_cache;
    }

    @Override
    protected void initView() {
        super.initView();
        setToolbar("课程缓存", new AwViewCustomToolbar.OnLeftClickListener() {
            @Override
            public void onLeftTextClick() {
                finish();
            }
        });
        getAvailableMemorySize();
        mToolbar.setRTextColor(R.color.color_999999);
        mToolbar.setOnRightClickListener(new AwViewCustomToolbar.OnRightClickListener() {
            @Override
            public void onRightTextClick() {
                //toClass(CourseCacheInProActivity.class,false);
               // toClass(CourseCacheSuccessActivity.class,false);
            }
        });
        mTitle.add("已完成");
        mTitle.add("缓存中");
        mFragment.add(new CourseCacheSuccessFragment());
        mFragment.add(new CourseCacheInProFragment());
        mViewpageer.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            @Override
            public int getCount() {
                return mFragment.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }
        });
        //绑定
        mTabLayout.setupWithViewPager(mViewpageer);
    }

    private void getAvailableMemorySize() {
        mToolbar.setRightText("剩余空间"+ MemoryTool.getAvailableInternalMemorySize(this));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(DaoVideoBean info) {
        if (DaoVideoBean.DOWNLOAD_OVER.equals(info.getDownloadStatus())) {
            getAvailableMemorySize();
        }
    }

}
