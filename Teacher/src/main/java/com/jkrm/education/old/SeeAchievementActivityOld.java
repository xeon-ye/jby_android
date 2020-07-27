package com.jkrm.education.old;

import android.support.v4.content.ContextCompat;

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
import com.jkrm.education.R;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.result.AllStudentScoreResultBean;
import com.jkrm.education.bean.result.QuestionResultBean;
import com.jkrm.education.bean.result.RowsHomeworkBean;
import com.jkrm.education.bean.result.StudentSingleQuestionAnswerResultBean;
import com.jkrm.education.bean.test.TestStudentAchievementAllQuestionBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.SeeAchievementPresent;
import com.jkrm.education.mvp.views.SeeAchievementView;
import com.jkrm.education.util.AchievementDataUtil;
import com.jkrm.education.util.TestDataUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 原成绩查阅
 * Created by hzw on 2019/5/14.
 */

public class SeeAchievementActivityOld extends AwMvpActivity<SeeAchievementPresent> implements SeeAchievementView.View {

    private SmartTable<TestStudentAchievementAllQuestionBean> mSmartTable;
    private List<TestStudentAchievementAllQuestionBean> mAchievementList;
    private List<String> rightResultList = new ArrayList<>();

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
        mRowsHomeworkBean = (RowsHomeworkBean) getIntent().getSerializableExtra(Extras.KEY_BEAN_ROWS_HOMEWORK);
        if(mRowsHomeworkBean != null) {
            mPresenter.getHomeworkScoreStudentAll(mRowsHomeworkBean.getId(), mRowsHomeworkBean.getClasses().getId(), MyApp.getInstance().getAppUser().getTeacherId());
        } else {
            getHomeworkScoreStudentAllFail("");
        }
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusTxtDark();
        setToolbarWithBackImg("查看成绩", null);

        mSmartTable = findViewById(R.id.smartTableLayout);
        mAchievementList = TestDataUtil.createkStudentAchievementAllQuestionBeanList();
    }

    @Override
    public void getHomeworkScoreStudentAllSuccess(List<AllStudentScoreResultBean> model) {
        if(AwDataUtil.isEmpty(model)) {
            AwLog.d("查看成绩 getHomeworkScoreStudentAllSuccess list is null");
        } else {
            AwLog.d("查看成绩 getHomeworkScoreStudentAllSuccess list size: " + model.size());
        }
        mAchievementList = AchievementDataUtil.convertDataToTestBean(model);
        setData();
    }

    @Override
    public void getHomeworkScoreStudentAllFail(String msg) {
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
        TestStudentAchievementAllQuestionBean bean = mAchievementList.get(mAchievementList.size() - 1);
        rightResultList.add(bean.getName());
        rightResultList.add(bean.getStudentId());
        rightResultList.add(bean.getScore());
        rightResultList.add(bean.getScore());
        rightResultList.add(bean.getClassesPosition());
        rightResultList.add(bean.getPhasePosition());
        rightResultList.add(bean.getOther1());
        rightResultList.add(bean.getOther2());
        rightResultList.add(bean.getOther3());
        rightResultList.add(bean.getOther4());
        rightResultList.add(bean.getOther5());

        List<Column> columns = new ArrayList<>();

        Column column = new Column<>("姓名", "name");
        column.setFast(true);
        columns.add(column);
        column.setFixed(true); //固定该列不滑动

        Column column1 = new Column<>("学号", "studentCode");
        column1.setFast(true);
        columns.add(column1);

        Column column2 = new Column<>("得分", "score");
        column2.setFast(true);
        columns.add(column2);

        Column column3 = new Column<>("班级", "classesPosition");
        column3.setFast(true);
        //        columns.add(column3);

        Column column4 = new Column<>("年级", "phasePosition");
        column4.setFast(true);
        //        columns.add(column4);

        Column totalColumn1 = new Column("排名", column3, column4);
        columns.add(totalColumn1);

        Column column5 = new Column<>("选择题1", "other1");
        column5.setFast(true);
        columns.add(column5);

        Column column6 = new Column<>("选择题2", "other2");
        column6.setFast(true);
        columns.add(column6);

        Column column7 = new Column<>("选择题3", "other3");
        column7.setFast(true);
        columns.add(column7);

        Column column8 = new Column<>("作文题", "other4");
        column8.setFast(true);
        columns.add(column8);

        Column column9 = new Column<>("解答题", "other5");
        column9.setFast(true);
        columns.add(column9);


        TableData<TestStudentAchievementAllQuestionBean> tableData = new TableData<>("成绩表",mAchievementList,columns);
        ICellBackgroundFormat<CellInfo> backgroundFormat = new BaseCellBackgroundFormat<CellInfo>() {
            @Override
            public int getBackGroundColor( CellInfo cellInfo) {
                if(cellInfo.row %2 == 0) {
                    return TableConfig.INVALID_COLOR;
                }
                return ContextCompat.getColor(mActivity, R.color.color_F2F9FF);
            }

            @Override
            public int getTextColor(CellInfo cellInfo) {
                if(cellInfo.col < 5) { //前5列不做操作
                    return super.getTextColor(cellInfo);
                } else {
                    if(cellInfo.value.equalsIgnoreCase(rightResultList.get(cellInfo.col + 1))) { //TODO +1是因为排名做了合并, 需处理
                        return TableConfig.INVALID_COLOR;
                    } else {
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
        mSmartTable.getConfig().setShowXSequence(false); //是否显示顶部序号列
        mSmartTable.getConfig().setShowYSequence(false); //是否显示左侧序号列
        mSmartTable.getConfig().setFixedXSequence(true); //固定顶部
        mSmartTable.getConfig().setFixedTitle(true); //固定标题
        mSmartTable.getConfig().setShowTableTitle(false); //是否显示表格标题
        mSmartTable.setTableData(tableData);
        mSmartTable.getConfig().setTableTitleStyle(new FontStyle(this,11,getResources().getColor(R.color.black)));
        mSmartTable.getConfig().setContentStyle(new FontStyle(this,11,getResources().getColor(R.color.black)));
        mSmartTable.getMatrixHelper().flingRight(200);

        tableData.setOnItemClickListener(new TableData.OnItemClickListener() {
            @Override
            public void onClick(Column column, String value, Object o, int col, int row) {
                //                if(col > 4 && !tableData.getT().get(row).getName().equals("标准")) {
                //                    Toast.makeText(mActivity, "点击了 " + value, Toast.LENGTH_SHORT).show();
                //                }
            }
        });
    }

    private void setDataTest() {
        TestStudentAchievementAllQuestionBean bean = mAchievementList.get(mAchievementList.size() - 1);
        rightResultList.add(bean.getName());
        rightResultList.add(bean.getStudentId());
        rightResultList.add(bean.getScore());
        rightResultList.add(bean.getScore());
        rightResultList.add(bean.getClassesPosition());
        rightResultList.add(bean.getPhasePosition());
        rightResultList.add(bean.getOther1());
        rightResultList.add(bean.getOther2());
        rightResultList.add(bean.getOther3());
        rightResultList.add(bean.getOther4());
        rightResultList.add(bean.getOther5());

        List<Column> columns = new ArrayList<>();

        Column column = new Column<>("姓名", "name");
        column.setFast(true);
        columns.add(column);
        column.setFixed(true); //固定该列不滑动

        Column column1 = new Column<>("学号", "studentCode");
        column1.setFast(true);
        columns.add(column1);

        Column column2 = new Column<>("得分", "score");
        column2.setFast(true);
        columns.add(column2);

        Column column3 = new Column<>("班级", "classesPosition");
        column3.setFast(true);
        //        columns.add(column3);

        Column column4 = new Column<>("年级", "phasePosition");
        column4.setFast(true);
        //        columns.add(column4);

        Column totalColumn1 = new Column("排名", column3, column4);
        columns.add(totalColumn1);

        Column column5 = new Column<>("选择题1", "other1");
        column5.setFast(true);
        columns.add(column5);

        Column column6 = new Column<>("选择题2", "other2");
        column6.setFast(true);
        columns.add(column6);

        Column column7 = new Column<>("选择题3", "other3");
        column7.setFast(true);
        columns.add(column7);

        Column column8 = new Column<>("作文题", "other4");
        column8.setFast(true);
        columns.add(column8);

        Column column9 = new Column<>("解答题", "other5");
        column9.setFast(true);
        columns.add(column9);


        TableData<TestStudentAchievementAllQuestionBean> tableData = new TableData<>("成绩表",mAchievementList,columns);
        ICellBackgroundFormat<CellInfo> backgroundFormat = new BaseCellBackgroundFormat<CellInfo>() {
            @Override
            public int getBackGroundColor( CellInfo cellInfo) {
                if(cellInfo.row %2 == 0) {
                    return TableConfig.INVALID_COLOR;
                }
                return ContextCompat.getColor(mActivity, R.color.color_F2F9FF);
            }

            @Override
            public int getTextColor(CellInfo cellInfo) {
                if(cellInfo.col < 5) { //前5列不做操作
                    return super.getTextColor(cellInfo);
                } else {
                    if(cellInfo.value.equalsIgnoreCase(rightResultList.get(cellInfo.col + 1))) { //TODO +1是因为排名做了合并, 需处理
                        return TableConfig.INVALID_COLOR;
                    } else {
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
        mSmartTable.getConfig().setShowXSequence(false); //是否显示顶部序号列
        mSmartTable.getConfig().setShowYSequence(false); //是否显示左侧序号列
        mSmartTable.getConfig().setFixedXSequence(true); //固定顶部
        mSmartTable.getConfig().setFixedTitle(true); //固定标题
        mSmartTable.getConfig().setShowTableTitle(false); //是否显示表格标题
        mSmartTable.setTableData(tableData);
        mSmartTable.getConfig().setTableTitleStyle(new FontStyle(this,11,getResources().getColor(R.color.black)));
        mSmartTable.getConfig().setContentStyle(new FontStyle(this,11,getResources().getColor(R.color.black)));
        mSmartTable.getMatrixHelper().flingRight(200);

        tableData.setOnItemClickListener(new TableData.OnItemClickListener() {
            @Override
            public void onClick(Column column, String value, Object o, int col, int row) {
                //                if(col > 4 && !tableData.getT().get(row).getName().equals("标准")) {
                //                    Toast.makeText(mActivity, "点击了 " + value, Toast.LENGTH_SHORT).show();
                //                }
            }
        });
    }
}
