package com.jkrm.education.ui.activity.achievement;

import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.widget.Button;
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
import com.jkrm.education.R;
import com.jkrm.education.adapter.QuestionOptionAdapter;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.AchievementBean;
import com.jkrm.education.bean.QuestionOptionBean;
import com.jkrm.education.bean.result.AllStudentScoreResultBean;
import com.jkrm.education.bean.result.QuestionResultBean;
import com.jkrm.education.bean.result.RowsHomeworkBean;
import com.jkrm.education.bean.result.StudentSingleQuestionAnswerResultBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.presenters.SeeAchievementPresent;
import com.jkrm.education.mvp.views.SeeAchievementView;
import com.jkrm.education.util.AchievementDataUtil;
import com.jkrm.education.util.CustomFontStyleUtil;
import com.jkrm.education.util.RequestUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by hzw on 2019/5/14.
 */

public class SeeChoiceAchievementOnlyOneActivity extends AwMvpActivity<SeeAchievementPresent> implements SeeAchievementView.View,
        SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.sfl_layout)
    SwipeRefreshLayout mSflLayout;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_info)
    TextView mTvInfo;
    @BindView(R.id.btn_reset)
    Button mBtnReset;
    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    private SmartTable<AchievementBean> mSmartTable;
    private List<AchievementBean> mAchievementList = new ArrayList<>();
    private List<String> mRightResultList = new ArrayList<>();
    private List<QuestionOptionBean> mQuestionOptionBeanList = new ArrayList<>();
    private Map<String, String> mOptionsMap = new HashMap<>();
    private QuestionOptionAdapter mAdapter;

    private RowsHomeworkBean mRowsHomeworkBean;

    private String homeworkId = "";
    private String currentStudentId = "";
    private String currentStudentName = "";
    private String currentQuestionNum = "";
    private String currentStudentAnswer = "";
    private String currentQuestionId = "";

    private String selectQuesetionAnswer = "";

    @Override
    protected SeeAchievementPresent createPresenter() {
        return new SeeAchievementPresent(this);
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
        mSmartTable = findViewById(R.id.smartTableLayout);
        // 禁止手势滑动
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        mAdapter = new QuestionOptionAdapter();
        AwRecyclerViewUtil.setRecyclerViewGridlayout(mActivity, mRcvData, mAdapter, 2);
    }

    @Override
    protected void initData() {
        super.initData();
        AwRecyclerViewUtil.setSwipeRefreshLayout(mSflLayout, true);
        mRowsHomeworkBean = (RowsHomeworkBean) getIntent().getSerializableExtra(Extras.KEY_BEAN_ROWS_HOMEWORK);
        currentStudentId = getIntent().getStringExtra(Extras.STUDENT_ID);
        if(mRowsHomeworkBean != null && !AwDataUtil.isEmpty(currentStudentId)) {
            homeworkId= mRowsHomeworkBean.getId();
            mPresenter.getHomeworkScoreStudentAll(mRowsHomeworkBean.getId(), mRowsHomeworkBean.getClasses().getId(), MyApp.getInstance().getAppUser().getTeacherId());
        } else {
            getHomeworkScoreStudentAllFail("");
        }
    }

    @Override
    public void onRefresh() {
        if(mRowsHomeworkBean != null) {
            homeworkId= mRowsHomeworkBean.getId();
            mPresenter.getHomeworkScoreStudentAll(mRowsHomeworkBean.getId(), mRowsHomeworkBean.getClasses().getId(), MyApp.getInstance().getAppUser().getTeacherId());
        } else {
            getHomeworkScoreStudentAllFail("");
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mSflLayout.setOnRefreshListener(this);
        mBtnReset.setOnClickListener(v -> {
            if(AwDataUtil.isEmpty(mOptionsMap)) {
                showDialog("选项列表不存在");
                return;
            }
            setChoiceListData();
        });
        mBtnConfirm.setOnClickListener(v -> {
            if(AwDataUtil.isEmpty(currentStudentId)) {
                showDialog("学生id获取失败，无法修改答案");
                return;
            }
            if(AwDataUtil.isEmpty(homeworkId)) {
                showDialog("作业id获取失败，无法修改答案");
                return;
            }
            if (AwDataUtil.isEmpty(mQuestionOptionBeanList)) {
                SeeChoiceAchievementOnlyOneActivity.this.showDialog("选项列表不存在");
                return;
            }
            for (QuestionOptionBean tempBean : mQuestionOptionBeanList) {
                if (tempBean.isSelect()) {
                    selectQuesetionAnswer = tempBean.getSerialNum();
                    //TODO 仅针对单选题, 目前尚无多选判断字段, 有选中的直接退出循环.
                    break;
                }
            }
            if (AwDataUtil.isEmpty(selectQuesetionAnswer)) {
                SeeChoiceAchievementOnlyOneActivity.this.showDialog("您尚未选择需要修改的答案");
                return;
            }
            if (currentStudentAnswer.equals(selectQuesetionAnswer)) {
                SeeChoiceAchievementOnlyOneActivity.this.showDialog("未修改答案，不可执行确认修改操作");
                return;
            }
            //需先获取学生作答(答题详情)接口返回数据, 获取答题id, 再根据该id去修改答案.
            mPresenter.getSingleStudentQuestionAnswer(homeworkId, currentQuestionId, currentStudentId);
        });
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            QuestionOptionBean bean = (QuestionOptionBean) adapter.getItem(position);
            //TODO 仅针对单选题, 目前尚无多选判断字段
            for(int i=0; i<adapter.getItemCount(); i++) {
                QuestionOptionBean tempBean = (QuestionOptionBean) adapter.getItem(i);
                tempBean.setSelect(false);
            }
            bean.setSelect(true);
            mAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void getHomeworkScoreStudentAllSuccess(List<AllStudentScoreResultBean> model) {
        mSflLayout.setRefreshing(false);
        if(AwDataUtil.isEmpty(model)) {
            AwLog.d("查看成绩 getHomeworkScoreStudentAllSuccess list is null");
            showDialog("暂无成绩列表");
            return;
        }
        AwLog.d("选择题成绩查看 getHomeworkScoreStudentAllSuccess list size: " + model.size());
        List<AllStudentScoreResultBean> onlyOneStudentList = new ArrayList<>();
        for(AllStudentScoreResultBean temp : model) {
            if(temp.getStudentId().equals(currentStudentId)) {
                onlyOneStudentList.add(temp);
            }
        }
        for(AllStudentScoreResultBean temp : model) {
            if(temp.getStudentName().equals("标准")) {
                onlyOneStudentList.add(temp);
            }
        }
        mAchievementList = AchievementDataUtil.convertChoiceData(onlyOneStudentList);
        AwLog.d("选择题成绩查看 列表size: " + mAchievementList.size());
        if(AwDataUtil.isEmpty(mAchievementList)) {
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
        if(resultBean != null && resultBean.getOptions() != null) {
            AwLog.d("选择题成绩查看 option: " + resultBean.getOptions());
            mOptionsMap = (Map<String, String>) resultBean.getOptions();
            if(AwDataUtil.isEmpty(mOptionsMap)) {
                showDialog("选项列表不存在");
                return;
            }
            if(!AwDataUtil.isEmpty(currentQuestionNum) && !AwDataUtil.isEmpty(currentStudentName)) {
                setText(mTvInfo, currentStudentName + "   第" + currentQuestionNum + "题");
            }
            setChoiceListData();
            //打开抽屉
            if (!mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                mDrawerLayout.openDrawer(Gravity.RIGHT);
            }
        } else {
            showDialog("选项列表不存在");
        }
    }

    @Override
    public void getQuestionFail(String msg) {
        showDialog("获取选项列表失败");
    }

    @Override
    public void getSingleStudentQuestionAnswerSuccess(StudentSingleQuestionAnswerResultBean bean) {
        if(bean == null) {
            showDialog("学生作答卡题目id获取失败，无法修改答案");
            return;
        }
        String studentQuestionId = bean.getId();
        if(AwDataUtil.isEmpty(studentQuestionId)) {
            showDialog("学生作答卡题目id获取失败，无法修改答案");
            return;
        }
        mPresenter.updateStudentQuestionAnswer(RequestUtil.getUpdateStudentQuestionAnswerRequest(studentQuestionId, selectQuesetionAnswer));
    }

    @Override
    public void getSingleStudentQuestionAnswerFail(String msg) {
        showDialog("学生作答卡题目id获取失败，无法修改答案");
    }

    @Override
    public void updateStudentQuestionAnswerSuccess() {
        showMsg("修改成功");
        //修改成功, 更新页面数据
        mPresenter.getHomeworkScoreStudentAll(mRowsHomeworkBean.getId(), mRowsHomeworkBean.getClasses().getId(), MyApp.getInstance().getAppUser().getTeacherId());
        if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
            mDrawerLayout.closeDrawers();
        }
    }

    @Override
    public void updateStudentQuestionAnswerFail(String msg) {
        showMsg("修改答案失败");
    }

    /**
     * 选择题选项列表数据填充.
     */
    private void setChoiceListData() {
        mQuestionOptionBeanList = new ArrayList<>();
        int index = 0;
        for (Map.Entry<String, String> entry : mOptionsMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            if(AwDataUtil.isEmpty(entry.getValue())) {
                continue;
            }
            mQuestionOptionBeanList.add(new QuestionOptionBean(QuestionOptionBean.SERIAL_NUMS[index], entry.getValue(), currentStudentAnswer.equals(QuestionOptionBean.SERIAL_NUMS[index])));
            index++;
        }
        if (AwDataUtil.isEmpty(mQuestionOptionBeanList)) {
            mAdapter.clearData();
            mRcvData.removeAllViews();
            mAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
        } else {
            mAdapter.addAllData(mQuestionOptionBeanList);
            mAdapter.loadMoreComplete();
            mAdapter.setEnableLoadMore(false);
            mAdapter.disableLoadMoreIfNotFullPage(mRcvData);
        }
    }

    /**
     * 表格数据填充
     */
    private void setData() {
        mRightResultList = AchievementDataUtil.getRightResultRowsData(mAchievementList, true);
        mAchievementList= MyDateUtil.exchangeList(mAchievementList);//将标准答案融合到第一行
        List<Column> columns = new ArrayList<>();

        Column column = new Column<>("姓名", "studentName");
        column.setFast(true);
        columns.add(column);
        column.setFixed(true); //固定该列不滑动

        Column column1 = new Column<>("学号", "studCode");
        column1.setFast(true);
        columns.add(column1);

        AwLog.d("选择题成绩查看 总题目列数: " + mAchievementList.get(0).getTotalCount());
        if(mAchievementList.get(0).getTotalCount() > 0) { //题目id列表正常来说不为空.
            for(int i=0; i< mAchievementList.get(0).getQuestionNumList().size(); i++) {
                Column<Object> objectColumn = new Column<>("  "+mRightResultList.get(i+2)+"  ", "question" + (i + 1));
                Column column5= new Column<>("第" + mAchievementList.get(0).getQuestionNumList().get(i) + "题",objectColumn);
                columns.add(column5);
            }
        }
        TableData<AchievementBean> tableData = new TableData<>("成绩表",mAchievementList,columns);
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
                if(cellInfo.col < 2) { //前2列不做操作
                    return super.getTextColor(cellInfo);
                } else {
                    if(cellInfo.value.equalsIgnoreCase(mRightResultList.get(cellInfo.col))) {
                        return TableConfig.INVALID_COLOR;
                    } else {
                        //非全是数字  并且长度不一样  为多选题  半对   橙色
                        if(!RegexUtil.isNumeric(cellInfo.value)&&cellInfo.value.length()<mRightResultList.get(cellInfo.col).length()){
                            char[] answerArr  = cellInfo.value.toCharArray();
                            int flag = 1 ;
                            for(int i=0;i<answerArr.length;i++)
                            {
                                //标志，一旦有一个字符不在正确答案中 就变为 0,不得分
                                if(mRightResultList.get(cellInfo.col).indexOf(answerArr[i])==-1)
                                {
                                    flag = 0;
                                }

                            }
                            if(flag==1){
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
        mSmartTable.getConfig().setShowXSequence(false); //是否显示顶部序号列
        mSmartTable.getConfig().setShowYSequence(false); //是否显示左侧序号列
        mSmartTable.getConfig().setFixedXSequence(true); //固定顶部
        mSmartTable.getConfig().setFixedTitle(true); //固定标题
        mSmartTable.getConfig().setShowTableTitle(false); //是否显示表格标题
        mSmartTable.setTableData(tableData);
        mSmartTable.getConfig().setTableTitleStyle(new FontStyle(this,11,getResources().getColor(R.color.black)));
        mSmartTable.getConfig().setContentStyle(new FontStyle(this,11,getResources().getColor(R.color.black)));
//        mSmartTable.getMatrixHelper().flingRight(200);
        Paint paint = new Paint();
        paint.setTypeface(CustomFontStyleUtil.getNewRomenType());
        mSmartTable.getConfig().setPaint(paint);
        tableData.setOnItemClickListener(new TableData.OnItemClickListener() {
            @Override
            public void onClick(Column column, String value, Object o, int col, int row) {
                AwLog.d("选择题成绩查看 tab onclick value: " + value + " ,col: " + col + " ,row: " + row);
                if(col != 0 && col != 1 && !tableData.getT().get(row).getStudentName().equals("标准")) {
                    currentQuestionId = mAchievementList.get(0).getQuestionIdList().get(col - 2);//直接获取第一行的数据, 以免其他部分行无数据返回造成crash, 通用的id列, 另需减掉前2列(姓名和学号)
                    AwLog.d("选择题成绩查看 tab onclick questionId: " + currentQuestionId);
                    mPresenter.getQuestion(currentQuestionId);
                    currentStudentId = tableData.getT().get(row).getStudentId();
                    currentStudentName = tableData.getT().get(row).getStudentName();
                    if(!AwDataUtil.isEmpty(tableData.getT().get(row).getQuestionNumList())) {
                        currentQuestionNum = tableData.getT().get(row).getQuestionNumList().get(col - 2);
                    } else {
                        currentQuestionNum = "";
                    }
                    if(!AwDataUtil.isEmpty(tableData.getT().get(row).getQuestionAnswerList())) {
                        currentStudentAnswer = tableData.getT().get(row).getQuestionAnswerList().get(col - 2);
                    } else {
                        currentStudentAnswer = "";
                    }
                    AwLog.d("选择题成绩查看 currentStudentId: " + currentStudentId + " ,currentStudentName: " + currentStudentName +
                            " ,currentQuestionNum: " + currentQuestionNum + " ,currentStudentAnswer: " + currentStudentAnswer + " ,currentQuestionId: " + currentQuestionId);
                }
            }
        });
    }
}
