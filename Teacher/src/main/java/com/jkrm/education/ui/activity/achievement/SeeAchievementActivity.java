package com.jkrm.education.ui.activity.achievement;

import android.annotation.TargetApi;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.DisplayCutout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.bg.BaseBackgroundFormat;
import com.bin.david.form.data.format.bg.BaseCellBackgroundFormat;
import com.bin.david.form.data.format.bg.ICellBackgroundFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.table.TableData;
import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwDisplayUtil;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.util.MyDateUtil;
import com.hzw.baselib.util.RegexUtil;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.jkrm.education.R;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.AchievementBean;
import com.jkrm.education.bean.result.AllStudentScoreResultBean;
import com.jkrm.education.bean.result.QuestionResultBean;
import com.jkrm.education.bean.result.RowsHomeworkBean;
import com.jkrm.education.bean.result.StudentSingleQuestionAnswerResultBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.SeeAchievementPresent;
import com.jkrm.education.mvp.views.SeeAchievementView;
import com.jkrm.education.ui.activity.OutExecelActivity;
import com.jkrm.education.util.AchievementDataUtil;
import com.jkrm.education.util.CustomFontStyleUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hzw on 2019/5/14.
 */

public class SeeAchievementActivity extends AwMvpActivity<SeeAchievementPresent> implements SeeAchievementView.View,
        SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.sfl_layout)
    SwipeRefreshLayout mSflLayout;
    @BindView(R.id.toolbar_custom)
    AwViewCustomToolbar mToolbarCustom;
    @BindView(R.id.ll_title)
    LinearLayout mLlTitle;
    @BindView(R.id.tv_out)
    TextView mTvOut;
    @BindView(R.id.smartTableLayout)
    SmartTable mSmartTableLayout;
    private SmartTable<AchievementBean> mSmartTable;
    private List<AchievementBean> mAchievementList;
    private List<String> mRightResultList = new ArrayList<>();

    private RowsHomeworkBean mRowsHomeworkBean;

    @Override
    protected SeeAchievementPresent createPresenter() {
        return new SeeAchievementPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_see_achievement;
    }

    @Override
    protected void initData() {
        super.initData();
        AwRecyclerViewUtil.setSwipeRefreshLayout(mSflLayout, true);
        mRowsHomeworkBean = (RowsHomeworkBean) getIntent().getSerializableExtra(Extras.KEY_BEAN_ROWS_HOMEWORK);
        if (mRowsHomeworkBean != null) {
            mPresenter.getHomeworkScoreStudentAll(mRowsHomeworkBean.getId(), mRowsHomeworkBean.getClasses().getId(), MyApp.getInstance().getAppUser().getTeacherId());
        } else {
            getHomeworkScoreStudentAllFail("");
        }
    }


    @Override
    protected void initView() {
        super.initView();
        setStatusTxtDark();
       /* if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getNotchParams();
        }*/
        //  setToolbarWithBackImg("查看成绩", null);
        setToolbarWithBackImgAndRightView("查看成绩", "横屏", new AwViewCustomToolbar.OnRightClickListener() {
            @Override
            public void onRightTextClick() {
                boolean or = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
                if (or) {
                    mToolbar.setRightText("横屏");
                } else {
                    mToolbar.setRightText("取消");
                }
                setRequestedOrientation(!or ? ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE : ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        });
        mSmartTable = findViewById(R.id.smartTableLayout);
    }


    @TargetApi(28)
    public void getNotchParams() {
        final View decorView = getWindow().getDecorView();

        decorView.post(new Runnable() {
            @Override
            public void run() {
                DisplayCutout displayCutout = decorView.getRootWindowInsets().getDisplayCutout();
                if (null == displayCutout) {
                    return;
                }
                AwLog.d("TAG", "安全区域距离屏幕左边的距离 SafeInsetLeft:" + displayCutout.getSafeInsetLeft());
                AwLog.d("TAG", "安全区域距离屏幕右部的距离 SafeInsetRight:" + displayCutout.getSafeInsetRight());
                AwLog.d("TAG", "安全区域距离屏幕顶部的距离 SafeInsetTop:" + displayCutout.getSafeInsetTop());
                AwLog.d("TAG", "安全区域距离屏幕底部的距离 SafeInsetBottom:" + displayCutout.getSafeInsetBottom());

                List<Rect> rects = displayCutout.getBoundingRects();
                if (rects == null || rects.size() == 0) {
                    AwLog.d("TAG", "不是刘海屏");
                } else {
                    AwLog.d("TAG", "刘海屏数量:" + rects.size());
                    for (Rect rect : rects) {
                        AwLog.d("TAG", "刘海屏区域：" + rect);
                    }
                }

                mSflLayout.setPadding(displayCutout.getSafeInsetLeft(), 0, 0, 0);
//                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                layoutParams.setMargins(displayCutout.getSafeInsetLeft(),0,0,0);//4个参数按顺序分别是左上右下
//                mFlImgFlLayout.setLayoutParams(layoutParams);
            }
        });
    }


    @Override
    protected void initListener() {
        super.initListener();
        mSflLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        if (mRowsHomeworkBean != null) {
            mPresenter.getHomeworkScoreStudentAll(mRowsHomeworkBean.getId(), mRowsHomeworkBean.getClasses().getId(), MyApp.getInstance().getAppUser().getTeacherId());
        } else {
            getHomeworkScoreStudentAllFail("");
        }
    }

    @Override
    public void getHomeworkScoreStudentAllSuccess(List<AllStudentScoreResultBean> model) {
        mSflLayout.setRefreshing(false);
        if (AwDataUtil.isEmpty(model)) {
            AwLog.d("查看成绩 getHomeworkScoreStudentAllSuccess list is null");
            showDialog("暂无成绩列表");
            return;
        }
        AwLog.d("查看成绩 getHomeworkScoreStudentAllSuccess list size: " + model.size());
        mAchievementList = AchievementDataUtil.convertData(model);
        AwLog.d("查看成绩 列表size: " + mAchievementList.size());
        if (AwDataUtil.isEmpty(mAchievementList)) {
            showDialog("暂无成绩列表");
            return;
        }
        setData();

    }

    @Override
    public void getHomeworkScoreStudentAllFail(String msg) {
        mSflLayout.setRefreshing(false);
        showDialog("获取成绩列表失败");
    }

    @Override
    public void getQuestionSuccess(QuestionResultBean resultBean) {

    }

    @Override
    public void getQuestionFail(String msg) {

    }

    @Override
    public void getSingleStudentQuestionAnswerSuccess(StudentSingleQuestionAnswerResultBean bean) {

    }

    @Override
    public void getSingleStudentQuestionAnswerFail(String msg) {

    }

    @Override
    public void updateStudentQuestionAnswerSuccess() {

    }

    @Override
    public void updateStudentQuestionAnswerFail(String msg) {

    }

    private void setData() {
        mRightResultList = AchievementDataUtil.getRightResultRowsData(mAchievementList, false);
        mAchievementList = MyDateUtil.exchangeList(mAchievementList);//将标准答案融合到第一行

        List<Column> columns = new ArrayList<>();

        Column column = new Column<>("标准", "studentName");
        column.setFast(true);
       // columns.add(column);
        column.setFixed(true); //固定该列不滑动

        Column merge1 = new Column("姓名", column);
        columns.add(merge1);
        merge1.setFixed(true);


        Column column1 = new Column<>("--", "studCode");
        //column1.setFast(true);
       // columns.add(column1);
        Column merge2 = new Column("学号", column1);
        columns.add(merge2);

        Column column2 = new Column<>("--", "totalScore");
        Column merge3 = new Column("得分", column2);

        column2.setFast(true);
        columns.add(merge3);

        Column column3 = new Column<>("   --   ", "classRank");
        Column merge4 = new Column("班级排名", column3);
        columns.add(merge4);

        column3.setFast(true);
              //  columns.add(column3);

        Column column4 = new Column<>("   --   ", "gradeRank");
        Column merge5 = new Column("年级排名", column4);

        column4.setFast(true);
                columns.add(merge5);

        Column totalColumn1 = new Column("排名", column3, column4);
       // columns.add(totalColumn1);
        AwLog.d("查看成绩 总题目列数: " + mAchievementList.get(0).getTotalCount());
        if (mAchievementList.get(0).getTotalCount() > 0) { //题目id列表正常来说不为空.
            for (int i = 0; i < mAchievementList.get(0).getQuestionNumList().size(); i++) {
                Column<Object> objectColumn = new Column<>("  " + mRightResultList.get(i + 5) + "  ", "question" + (i + 1));
                Column column5 = new Column<>("第" + mAchievementList.get(0).getQuestionNumList().get(i) + "题", objectColumn);
                columns.add(column5);
                //columns.add(new Column<>("第" + mAchievementList.get(0).getQuestionNumList().get(i) + "题", "question" + (i+1)));

            }
        }
        TableData<AchievementBean> tableData = new TableData<>("成绩表", mAchievementList, columns);
        ICellBackgroundFormat<CellInfo> backgroundFormat = new BaseCellBackgroundFormat<CellInfo>() {
            @Override
            public int getBackGroundColor(CellInfo cellInfo) {
                if (cellInfo.row % 2 == 0) {
                    return TableConfig.INVALID_COLOR;
                }
                return ContextCompat.getColor(mActivity, R.color.color_F2F9FF);
            }

            @Override
            public int getTextColor(CellInfo cellInfo) {
                if (cellInfo.col < 5) { //前5列不做操作
                    return super.getTextColor(cellInfo);
                } else {
                    AwLog.d("backgroundFormat col: " + cellInfo.col + " ,cellInfo value: " + cellInfo.value + " ,正确答案: " + mRightResultList.get(cellInfo.col));
                    //正确答案  黑色
                    if (cellInfo.value.equalsIgnoreCase(mRightResultList.get(cellInfo.col))) {
                        return TableConfig.INVALID_COLOR;
                    } else {
                        //非全是数字  并且长度不一样  为多选题  半对   橙色     2020/04/24
                        if (!RegexUtil.isNumeric(cellInfo.value) && cellInfo.value.length() < mRightResultList.get(cellInfo.col).length()) {
                            char[] answerArr = cellInfo.value.toCharArray();
                            int flag = 1;
                            for (int i = 0; i < answerArr.length; i++) {
                                //标志，一旦有一个字符不在正确答案中 就变为 0,不得分
                                if (mRightResultList.get(cellInfo.col).indexOf(answerArr[i]) == -1) {
                                    flag = 0;
                                }

                            }
                            if (flag == 1) {
                                return getResources().getColor(R.color.color_F19F10);
                            }

                        }
                        return getResources().getColor(R.color.red);
                    }
                }
            }
        };
        tableData.setShowCount(false);//关闭统计行
        mSmartTable.getConfig().setColumnTitleBackground(new BaseBackgroundFormat(getResources().getColor(R.color.color_F2F9FF))); //列标题背景
        mSmartTable.getConfig().setCountBackground(new BaseBackgroundFormat(getResources().getColor(R.color.color_F2F9FF))); //统计行背景
        mSmartTable.getConfig().setContentCellBackgroundFormat(backgroundFormat);//内容行背景色
        mSmartTable.getConfig().setVerticalPadding(AwDisplayUtil.dip2px(mActivity, 15));//上下间距
        mSmartTable.getConfig().setSequenceVerticalPadding(AwDisplayUtil.dip2px(mActivity, 15)); //列序列上下间距
        mSmartTable.getConfig().setColumnTitleVerticalPadding(AwDisplayUtil.dip2px(mActivity, 15)); //标题行上下间距
        mSmartTable.getConfig().setFixedCountRow(false); //是否固定统计行
        mSmartTable.getConfig().setFixedFirstColumn(true);
        mSmartTable.getConfig().setShowXSequence(false); //是否显示顶部序号列
        mSmartTable.getConfig().setShowYSequence(false); //是否显示左侧序号列
        mSmartTable.getConfig().setFixedXSequence(true); //固定顶部
        mSmartTable.getConfig().setFixedTitle(true); //固定标题
        mSmartTable.getConfig().setShowTableTitle(false); //是否显示表格标题
        mSmartTable.setTableData(tableData);
        mSmartTable.getConfig().setTableTitleStyle(new FontStyle(this, 11, getResources().getColor(R.color.black)));
        mSmartTable.getConfig().setContentStyle(new FontStyle(this, 11, getResources().getColor(R.color.black)));
        mSmartTable.getMatrixHelper().flingRight(200);
        Paint paint = new Paint();
        paint.setTypeface(CustomFontStyleUtil.getNewRomenType());
        mSmartTable.getConfig().setPaint(paint);
        tableData.setOnItemClickListener(new TableData.OnItemClickListener() {
            @Override
            public void onClick(Column column, String value, Object o, int col, int row) {
                //                if(col > 4 && !tableData.getT().get(row).getName().equals("标准")) {
                //                    Toast.makeText(mActivity, "点击了 " + value, Toast.LENGTH_SHORT).show();
                //                }
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //横屏操作 -- 适配刘海屏
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                getNotchParams();
            }
        } else {
            //竖屏操作
            mSflLayout.setPadding(0, 0, 0, 0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_out)
    public void onViewClicked() {
        toClass(OutExecelActivity.class, false, Extras.KEY_ACHIEVMENT, mAchievementList, Extras.KEY_BEAN_ROWS_HOMEWORK,mRowsHomeworkBean);
    }
}
