package com.jkrm.education.old;

import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.widget.Toast;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.bg.BaseBackgroundFormat;
import com.bin.david.form.data.format.bg.BaseCellBackgroundFormat;
import com.bin.david.form.data.format.bg.ICellBackgroundFormat;
import com.bin.david.form.data.format.draw.TextDrawFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.table.TableData;
import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDisplayUtil;
import com.hzw.baselib.util.AwLog;
import com.jkrm.education.R;
import com.jkrm.education.bean.test.TestStudentChoiceQuestionSeeBean;
import com.jkrm.education.util.TestDataUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 选择题查阅测试类
 * Created by hzw on 2019/5/14.
 */

public class SeeChoiceAchievementActivityTest extends AwMvpActivity {

    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    private SmartTable<TestStudentChoiceQuestionSeeBean> mSmartTable;
    private List<TestStudentChoiceQuestionSeeBean> mChoiceQuesionList;
    private List<String> rightResultList = new ArrayList<>();

    @Override
    protected AwCommonPresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choice_question_see;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusTxtDark();
        setToolbarWithBackImg("选择题查阅", null);

        // 禁止手势滑动
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    protected void initData() {
        super.initData();
        mSmartTable = findViewById(R.id.smartTableLayout);
        mChoiceQuesionList = TestDataUtil.createkStudentChoiceQuestionSeeBeanList();
        TestStudentChoiceQuestionSeeBean bean = mChoiceQuesionList.get(mChoiceQuesionList.size() - 1);
        rightResultList.add(bean.getName());
        rightResultList.add(bean.getStudentId());
        rightResultList.add(bean.getOther1());
        rightResultList.add(bean.getOther2());
        rightResultList.add(bean.getOther3());
        rightResultList.add(bean.getOther4());
        rightResultList.add(bean.getOther5());
        rightResultList.add(bean.getOther6());
        rightResultList.add(bean.getOther7());
        List<Column> columns = new ArrayList<>();

        Column column = new Column<>("姓名", "name");
        column.setFast(true);
        columns.add(column);
        column.setFixed(true); //固定该列不滑动

        Column column1 = new Column<>("学号", "studentId");
        column1.setFast(true);
        columns.add(column1);

        Column column5 = new Column<>("题目1", "other1", new TextDrawFormat<>());
        column5.setFast(true);
        columns.add(column5);

        Column column6 = new Column<>("题目2", "other2");
        column6.setFast(true);
        columns.add(column6);

        Column column7 = new Column<>("题目3", "other3");
        column7.setFast(true);
        columns.add(column7);

        Column column8 = new Column<>("题目4", "other4");
        column8.setFast(true);
        columns.add(column8);

        Column column9 = new Column<>("题目5", "other5");
        column9.setFast(true);
        columns.add(column9);

        Column column10 = new Column<>("题目6", "other6");
        column10.setFast(true);
        columns.add(column10);

        Column column11 = new Column<>("题目7", "other7");
        column11.setFast(true);
        columns.add(column11);


        TableData<TestStudentChoiceQuestionSeeBean> tableData = new TableData<>("成绩表", mChoiceQuesionList,columns);
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
                if(cellInfo.col != 0 && cellInfo.col != 1) {
                    if(cellInfo.value.equalsIgnoreCase(rightResultList.get(cellInfo.col))) {
                        return TableConfig.INVALID_COLOR;
                    } else {
                        return getResources().getColor(R.color.red);
                    }
                } else {
                    return super.getTextColor(cellInfo);
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
                AwLog.d("tab onclick value: " + value + " ,col: " + col + " ,row: " + row);
                if(col != 0 && col != 1 && !tableData.getT().get(row).getName().equals("标准")) {
                    Toast.makeText(mActivity, "点击了 " + value, Toast.LENGTH_SHORT).show();
                    if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                        mDrawerLayout.closeDrawers();
                    } else {
                        mDrawerLayout.openDrawer(Gravity.RIGHT);
                    }
                }
            }
        });
    }
}
