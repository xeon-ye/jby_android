package com.jkrm.education.ui.activity;

import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzw.baselib.base.AwBaseActivity;
import com.hzw.baselib.interfaces.IOpenFileListener;
import com.hzw.baselib.interfaces.IPermissionListener;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwFileUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.util.AwRxPermissionUtil;
import com.hzw.baselib.util.AwSystemIntentUtil;
import com.jkrm.education.R;
import com.jkrm.education.adapter.OutExcelPDFAdapter;
import com.jkrm.education.bean.AchievementBean;
import com.jkrm.education.bean.result.RowsHomeworkBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.util.DataUtil;
import com.jkrm.education.util.UserUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OutExecelActivity extends AwBaseActivity {


    @BindView(R.id.ll_of)
    LinearLayout llOf;
    @BindView(R.id.tv_out)
    TextView tvOut;
    @BindView(R.id.rcv_left)
    RecyclerView rcvLeft;
    @BindView(R.id.rcv_right)
    RecyclerView rcvRight;
    @BindView(R.id.tv_class)
    TextView mTvClass;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.fab_back)
    FloatingActionButton mFabBack;
    @BindView(R.id.fab_out)
    FloatingActionButton mFabOut;
    @BindView(R.id.ll_of_setting)
    LinearLayout mLlOfSetting;
    private List<AchievementBean> mAchievementList, mLeftList, mRightList;
    RowsHomeworkBean mRowsHomeworkBean;
    private OutExcelPDFAdapter mLeftAdpter, mRightAdapter;
    private String mStrClassName = "";


    private long lastClickTime = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_out_execel;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusTransparent();
        mAchievementList = (List<AchievementBean>) getIntent().getSerializableExtra(Extras.KEY_ACHIEVMENT);
        mRowsHomeworkBean = (RowsHomeworkBean) getIntent().getSerializableExtra(Extras.KEY_BEAN_ROWS_HOMEWORK);
        mLeftList = new ArrayList<>();
        mRightList = new ArrayList<>();
        mTvTime.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        if (null != UserUtil.getAppUser().getSchool().getName()) {
            tvOut.setText(UserUtil.getAppUser().getSchool().getName() + "作业");
        }
        //
        if (!AwDataUtil.isEmpty(UserUtil.getAppUser().getNickName())) {
            mStrClassName += UserUtil.getAppUser().getNickName();
        } else if (!AwDataUtil.isEmpty(UserUtil.getAppUser().getRealName())) {
            mStrClassName += UserUtil.getAppUser().getRealName();
        }
        if (!AwDataUtil.isEmpty(mRowsHomeworkBean.getClasses().getName())) {
            mStrClassName += "-" + mRowsHomeworkBean.getClasses().getName();
        }
        if (!AwDataUtil.isEmpty(mRowsHomeworkBean.getName())) {
            mStrClassName += "-" + mRowsHomeworkBean.getName();
        }
        mTvClass.setText(mStrClassName);
        List<List<AchievementBean>> lists = DataUtil.averageAssign(mAchievementList, 2);
        for (int i = 0; i < lists.size(); i++) {
            if (0 == i) {
                mLeftList = lists.get(0);
            }
            if (1 == i) {
                mRightList = lists.get(1);
            }
        }
        mLeftAdpter = new OutExcelPDFAdapter();
        mRightAdapter = new OutExcelPDFAdapter();
        mLeftAdpter.addAllData(mLeftList);
        mRightAdapter.addAllData(mRightList);

        AwRecyclerViewUtil.setRecyclerViewLinearlayout(this, rcvLeft, mLeftAdpter, false);
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(this, rcvRight, mRightAdapter, false);
        checkPermission();
    }

    private void checkPermission() {
        AwRxPermissionUtil.getInstance().checkPermission(mActivity, AwRxPermissionUtil.permissionsStorage, new IPermissionListener() {
            @Override
            public void granted() {


            }

            @Override
            public void shouldShowRequestPermissionRationale() {
                showDialog("请允许获取存储权限才可正常进行导出操作", v -> {
                    dismissDialog();
                    AwSystemIntentUtil.toApplicationDetailSetting(mActivity);
                    finish();
                });
            }

            @Override
            public void needToSetting() {
                showDialog("请允许获取存储权限才可正常进行导出操作", v -> {
                    dismissDialog();
                    AwSystemIntentUtil.toApplicationDetailSetting(mActivity);
                    finish();
                });
            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mFabOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("aaaaa");
            }
        });
    }

    @OnClick({R.id.fab_back, R.id.fab_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fab_back:
                finish();
                break;
            case R.id.fab_out:
                if (!onDoubClick()) {
                    return;
                }
                mLlOfSetting.setVisibility(View.GONE);
                PdfDocument pdfDocument = new PdfDocument();
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + mRowsHomeworkBean.getClasses().getName() + "-" + mRowsHomeworkBean.getName() + ".pdf");

                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(
                        llOf.getWidth(),
                        llOf.getHeight(), 1).create();//2
                PdfDocument.Page page = pdfDocument.startPage(pageInfo);
                llOf.draw(page.getCanvas());//3
                pdfDocument.finishPage(page);//4
                try {
                    pdfDocument.writeTo(new FileOutputStream(file));
                    AwFileUtil.openFileByThirdApp(mActivity, file.getPath(), new IOpenFileListener() {
                        @Override
                        public void openResult(boolean isSuccess) {
                            if (!isSuccess) {
                                showDialog("本机尚未安装相关Office应用, 无法浏览导出的PDF内容, 请先安装office阅读软件");
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();

                }
                pdfDocument.close();

                mLlOfSetting.setVisibility(View.VISIBLE);
                break;
        }
    }

    public boolean onDoubClick() {
        boolean flag = false;
        long time = System.currentTimeMillis() - lastClickTime;

        if (time > 500) {
            flag = true;
        }
        lastClickTime = System.currentTimeMillis();
        return flag;
    }
}
