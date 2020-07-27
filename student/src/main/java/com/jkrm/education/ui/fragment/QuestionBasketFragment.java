package com.jkrm.education.ui.fragment;


import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hzw.baselib.base.AwMvpLazyFragment;
import com.hzw.baselib.constants.AwBaseConstant;
import com.hzw.baselib.interfaces.IOpenFileListener;
import com.hzw.baselib.interfaces.IPermissionListener;
import com.hzw.baselib.project.student.bean.AnswerSheetAllDataResultBean;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwFileUtil;
import com.hzw.baselib.util.AwFirUtil;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.AwPopupwindowUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.util.AwRxPermissionUtil;
import com.jkrm.education.R;
import com.jkrm.education.adapter.QuestionBasketAdapter;
import com.jkrm.education.adapter.score.MarkChoiceCourseAdapter;
import com.jkrm.education.bean.CourseBean;
import com.jkrm.education.bean.result.ParcticeQuestBean;
import com.jkrm.education.bean.result.PracticeDataResultBean;
import com.jkrm.education.bean.result.VideoResultBean;
import com.jkrm.education.bean.rx.RxRefreshQuestionBasketType;
import com.jkrm.education.bean.type.TypeCourseBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.constants.UrlConstant;
import com.jkrm.education.mvp.presenters.QuestionBasketPresent;
import com.jkrm.education.mvp.views.QuestionBasketFragmentView;
import com.jkrm.education.ui.activity.FamousTeacherLectureActivity;
import com.jkrm.education.ui.activity.QuestionBasketActivity;
import com.jkrm.education.ui.activity.ScanQrcodeActivity;
import com.jkrm.education.ui.activity.SeeTargetQuestionActivity;
import com.jkrm.education.util.DataUtil;
import com.jkrm.education.util.TestDataUtil;
import com.jkrm.education.util.UserUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 题篮(原练习模块, 已更改)
 * Created by hzw on 2019/5/5.
 */

public class QuestionBasketFragment extends AwMvpLazyFragment<QuestionBasketPresent> implements QuestionBasketFragmentView.View,
        SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.sfl_layout)
    SwipeRefreshLayout mSflLayout;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    @BindView(R.id.rcv_dataQustion)
    RecyclerView mRcvDataQuestion;
    @BindView(R.id.rcv_dataCourese)
    RecyclerView mRcvDataCourese;
    @BindView(R.id.btn_reset)
    Button mBtnReset;
    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;
    @BindView(R.id.tv_startDate)
    TextView mTvStartDate;
    @BindView(R.id.tv_endDate)
    TextView mTvEndDate;

    public static final int MSG_EXPORT_FINISH = 0;
    private List<TypeCourseBean> mTypeCourseBeanList = new ArrayList<>();
    private QuestionBasketAdapter mAdapter;
    //筛选内容
    private MarkChoiceCourseAdapter mCourseAdapter;
    private List<ParcticeQuestBean> mParcticeQuestBeanList = new ArrayList<>();
    //正在移出的问题bean
    private ParcticeQuestBean mParcticeQuestBean;
    private String courseId = "";
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_EXPORT_FINISH:
                    dismissLoadingDialog();
                    String filePath = (String) msg.obj;
                    showDialogWithCancelDismiss("导出完成，文件保存路径：" + filePath + " ，是否立即查看？", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dismissDialog();
                            AwFileUtil.openFileByThirdApp(mActivity, filePath, new IOpenFileListener() {
                                @Override
                                public void openResult(boolean isSuccess) {
                                    if(!isSuccess) {
                                        showDialog("本机尚未安装相关Office应用, 无法浏览导出的WORD内容, 请先安装office阅读软件");
                                    }
                                }
                            });
                        }
                    });
                    break;
            }

        }
    };

    @Override
    protected QuestionBasketPresent createPresenter() {
        return new QuestionBasketPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_question_basket;
    }

    @Override
    protected void initView() {
        super.initView();

//        setToolbarLeftImgWithRightTxt("题篮", R.mipmap.qingkong, "筛选", () -> {
//            if(AwDataUtil.isEmpty(mParcticeQuestBeanList)) {
//                showMsg("暂无题篮，无需清空");
//                return;
//            }
//            showDialogWithCancelDismiss("确认要删除题篮中的所有题目吗？", new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dismissDialog();
//                    mPresenter.delAllQuestionBasket(UserUtil.getStudId());
//                }
//            });
//                    }, () -> {
//                        if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
//                            mDrawerLayout.closeDrawers();
//                        } else {
//                            mDrawerLayout.openDrawer(Gravity.RIGHT);
//                        }
//                    });

        //TODO 导出题篮暂不可用 隐藏开始
        setToolbarLeftImgWithRightTxt("题篮", R.mipmap.output, "筛选", () -> {
            if(AwDataUtil.isEmpty(mParcticeQuestBeanList)) {
                showMsg("请先添加题目到题篮后才可导出");
                return;
            }
            AwRxPermissionUtil.getInstance().checkPermission(mActivity, AwRxPermissionUtil.permissionsStorage, new IPermissionListener() {
                @Override
                public void granted() {
                    StringBuffer sb = new StringBuffer();
                    for(ParcticeQuestBean temp : mParcticeQuestBeanList) {
                        sb.append(temp.getId()).append(",");
                    }
                    String ids = sb.toString().substring(0, sb.toString().length() - 1);

                    AwPopupwindowUtil.showCommonListWithTitlePopupWindow(mActivity, "请选择导出内容", DataUtil.getBasketExportChoiceList(), mToolbar, bean -> {
                        String temp = (String) bean;
                        if(temp.contains("仅")) {
                            exportBasket(ids, false);
                        } else if(temp.contains("答案")) {
                            exportBasket(ids, true);
                        }
                    });


                    //            exportQuestionBasket(RequestUtil.exportQuestionBasketRequest(ids));
                    //            mPresenter.exportQuestionBasket(RequestUtil.exportQuestionBasketRequest(ids));
                }

                @Override
                public void shouldShowRequestPermissionRationale() {
                    showDialog("导出需开启存储权限");
                }

                @Override
                public void needToSetting() {
                    showDialog("导出需开启存储权限");
                }
            });
        }, () -> {
            if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                mDrawerLayout.closeDrawers();
            } else {
                mDrawerLayout.openDrawer(Gravity.RIGHT);
            }
        });
        mToolbar.setLeft2Img(R.mipmap.qingkong);
        mToolbar.setOnLeft2ImgClckListener(() -> {
            if(AwDataUtil.isEmpty(mParcticeQuestBeanList)) {
                showMsg("暂无题篮，无需清空");
                return;
            }
            showDialogWithCancelDismiss("确认要删除题篮中的所有题目吗？", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissDialog();
                    mPresenter.delAllQuestionBasket(UserUtil.getStudId());
                }
            });
        });
        //TODO 导出题篮暂不可用 隐藏结束
        mToolbar.setToolbarTitleColor(R.color.white);
        mToolbar.setRTextColor(R.color.white);
        // 禁止手势滑动
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    protected void initData() {
        super.initData();
        EventBus.getDefault().register(this);
        AwRecyclerViewUtil.setSwipeRefreshLayout(mSflLayout, true);
        mAdapter = new QuestionBasketAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, mAdapter, false);

        initChoiceData();
        getData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshByBus(RxRefreshQuestionBasketType type) {
        if(type != null && !type.getTag().equals(RxRefreshQuestionBasketType.TAG_QUESTION_BASKET)) {
            getData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onRefresh() {
        getData();
    }

    private void getData() {
        mPresenter.getPracticeDataList(UserUtil.getStudId(), "", "", courseId, "");
    }

    @Override
    protected void initListener() {
        super.initListener();
        mSflLayout.setOnRefreshListener(this);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            ParcticeQuestBean bean = (ParcticeQuestBean) adapter.getItem(position);
//            showMsg(getString(R.string.common_function_is_dev));
            switch (view.getId()) {
                case R.id.btn_seeAnswer:
                    toClass(SeeTargetQuestionActivity.class, false, Extras.COMMON_BOOLEAN, true, Extras.COMMON_PARAMS, bean.getId());
                    break;
                case R.id.btn_delFromBasket:
                    showDialogWithCancelDismiss("确认要将该题从题篮移出吗？", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mParcticeQuestBean = bean;
                            dismissDialog();
                            mPresenter.delSomeOneQuestionBasket(bean.getId(), UserUtil.getStudId());
                        }
                    });
                    break;
                case R.id.btn_famousTeacherLecture:
                    mPresenter.getVideos(bean.getId());
                    break;
            }
        });
        mBtnReset.setOnClickListener(v -> {
            if(!AwDataUtil.isEmpty(mTypeCourseBeanList)) {
                for(TypeCourseBean temp : mTypeCourseBeanList) {
                    temp.setSelect("全部".equals(temp.getCourseName()));
                }
                mCourseAdapter.notifyDataSetChanged();
            }
            courseId = "";
        });
        mBtnConfirm.setOnClickListener(v -> {
            for(TypeCourseBean tempBean : mTypeCourseBeanList) {
                if(tempBean.isSelect()) {
                    if(AwBaseConstant.COMMON_INVALID_VALUE.equals(tempBean.getCourseId())) {
                        courseId = "";
                    } else {
                        courseId = tempBean.getCourseId();
                    }
                }
            }
            mDrawerLayout.closeDrawers();
            mAdapter.clearData();
            mRcvData.removeAllViews();
            getData();
        });
        mCourseAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            TypeCourseBean kindsBean = (TypeCourseBean) adapter.getItem(position);
            for(int i=0; i<adapter.getItemCount(); i++) {
                TypeCourseBean tempBean = (TypeCourseBean) adapter.getItem(i);
                tempBean.setSelect(false);
            }
            kindsBean.setSelect(true);
            mCourseAdapter.notifyDataSetChanged();
        });
    }

    /**
     *  初始化筛选框控件
     */
    private void initChoiceData() {
        //初始化 学科
        mCourseAdapter = new MarkChoiceCourseAdapter();
        AwRecyclerViewUtil.setRecyclerViewGridlayout(mActivity, mRcvDataCourese, mCourseAdapter, 4);

    }

    @Override
    public void getPracticeDataListSuccess(PracticeDataResultBean bean) {
        mSflLayout.setRefreshing(false);
        if(null == bean || AwDataUtil.isEmpty(bean.getList())) {
            mAdapter.clearData();
            mRcvData.removeAllViews();
            mAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
            return;
        }
        if(AwDataUtil.isEmpty(mTypeCourseBeanList)) {
            //组合学科列表
            TypeCourseBean typeCourseBean = new TypeCourseBean();
            typeCourseBean.setCourseId(AwBaseConstant.COMMON_INVALID_VALUE);
            typeCourseBean.setCourseName("全部");
            typeCourseBean.setSelect(true);
            mTypeCourseBeanList.add(0, typeCourseBean);
            if(bean != null && !AwDataUtil.isEmpty(bean.getCoursesList())) {
                List<CourseBean> tempList = bean.getCoursesList();
                for(CourseBean temp : tempList) {
                    boolean isExist = false;
                    for(TypeCourseBean temp2 : mTypeCourseBeanList) {
                        if(temp.getCourse().equals(temp2.getCourseName())) {
                            isExist = true;
                        }
                    }
                    if(!isExist) {
                        mTypeCourseBeanList.add(new TypeCourseBean(temp.getId(), temp.getCourse(), false));
                    }
                }
            }
            mCourseAdapter.addAllData(mTypeCourseBeanList);
            mCourseAdapter.loadMoreComplete();
            mCourseAdapter.setEnableLoadMore(false);
            mCourseAdapter.disableLoadMoreIfNotFullPage(mRcvDataCourese);
        }
        mParcticeQuestBeanList = bean.getQuest();
        mAdapter.addAllData(mParcticeQuestBeanList);
        mAdapter.loadMoreComplete();
        mAdapter.disableLoadMoreIfNotFullPage(mRcvData);
    }

    @Override
    public void getPracticeDataListFail(String msg) {
        mSflLayout.setRefreshing(false);
        mAdapter.clearData();
        mRcvData.removeAllViews();
        mAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
    }

    @Override
    public void getVideosSuccess(VideoResultBean result) {
        if(result == null) {
            showMsg(getString(R.string.hint_no_famouse_teacher_video));
            return;
        }
        if(result.getQuestionVideo() == null && AwDataUtil.isEmpty(result.getCataVideos())) {
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
    public void delSomeOneQuestionBasketSuccess(String msg) {
        showMsg("移出题篮成功");
        getData();
        List<String> questionIds = new ArrayList<>();
        questionIds.add(mParcticeQuestBean.getId());
        EventBus.getDefault().postSticky(new RxRefreshQuestionBasketType(RxRefreshQuestionBasketType.TAG_QUESTION_BASKET, true, questionIds));
    }

    @Override
    public void delAllQuestionBasketSuccess(String msg) {
        showMsg("清空题篮成功");
        getData();
        List<String> questionIds = new ArrayList<>();
        for(ParcticeQuestBean temp : mParcticeQuestBeanList) {
            questionIds.add(temp.getId());
        }
        EventBus.getDefault().postSticky(new RxRefreshQuestionBasketType(RxRefreshQuestionBasketType.TAG_QUESTION_BASKET, true, questionIds));

    }

    /**
     * 导出题篮
     * @param ids
     */
    private void exportBasket(final String ids, final boolean containAnswer) {
        showLoadingDialog();
        new Thread() {
            @Override
            public void run() {
                URL url;
                HttpURLConnection connection;
                try {
                    //统一资源
                    url = new URL(MyConstant.ServerConstant.API_BASE_URL + UrlConstant.QUESTION_BASKET_EXPORT_CUSTOM);
                    //打开链接
                    connection = (HttpURLConnection) url.openConnection();
                    //设置链接超时
                    connection.setConnectTimeout(30000);
                    //设置允许得到服务器的输入流,默认为true可以不用设置
                    connection.setDoInput(true);
                    //设置允许向服务器写入数据，一般get方法不会设置，大多用在post方法，默认为false
                    connection.setDoOutput(true);//此处只是为了方法说明
                    //设置请求方法
                    connection.setRequestMethod("POST");
                    connection.setUseCaches(false);//不使用缓存
                    //设置请求的字符编码
//                    connection.setRequestProperty("Charset", "utf-8");
                    connection.setRequestProperty("Content-type", "application/json");
                    //传参
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("allIds", ids);
                    //        hashMap.put("ids", ids);
                    hashMap.put("answerPos", "2"); //答案位置 1每道题后 2所有题目后
                    hashMap.put("answer", containAnswer ? "1" : "0"); //是否需要答案 1需要 0不需要
                    hashMap.put("explain", containAnswer ? "1" : "0"); //是否需要解析 1需要 0不需要
                    String params = new Gson().toJson(hashMap);

                    connection.getOutputStream().write(params.getBytes());
                    //设置connection打开链接资源
                    connection.connect();

                    //得到链接的响应码 200为成功
                    int responseCode = connection.getResponseCode();
                    AwLog.d("responseCode: " + responseCode);
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        FileOutputStream fos = null;
                        BufferedOutputStream bos = null;
                        InputStream is = null;
                        BufferedInputStream bis = null;

                        //创建一个文件对象用于存储下载的文件 此次的getFilesDir()方法只有在继承至Context类的类中
                        // 可以直接调用其他类中必须通过Context对象才能调用，得到的是内部存储中此应用包名下的文件路径
                        //如果使用外部存储的话需要添加文件读写权限，5.0以上的系统需要动态获取权限 此处不在不做过多说明。
                        File file = null;
                        int contentLength = 0;
                        int totle = 0;
                        try {
                            String fileName = "basket" + System.currentTimeMillis() + ".docx";
                            file = new File(Environment.getExternalStorageDirectory() + File.separator + "/jby" + File.separator + fileName);
                            if(!file.getParentFile().exists()) {
                                file.getParentFile().mkdir();
                            }
                            if(!file.exists()) {
                                file.createNewFile();
                            }
                            //创建一个文件输出流
                            fos = new FileOutputStream(file);
                            bos = new BufferedOutputStream(fos);
                            //得到服务器响应的输入流
                            is = connection.getInputStream();
                            //获取请求的内容总长度
                            contentLength = connection.getContentLength();
                            //设置progressBar的Max
                            //                        mPb.setMax(contentLength);
                            //创建缓冲输入流对象，相对于inputStream效率要高一些
                            bis = new BufferedInputStream(is);
                            //此处的len表示每次循环读取的内容长度
                            int len;
                            //已经读取的总长度
                            totle = 0;
                            //bytes是用于存储每次读取出来的内容
                            byte[] bytes = new byte[1024];
                            while ((len = bis.read(bytes)) != -1) {
                                //每次读取完了都将len累加在totle里
                                totle += len;
                                //每次读取的都更新一次progressBar
    //                            mPb.setProgress(totle);
                                //通过文件输出流写入从服务器中读取的数据
    //                            outputStream.write(bytes, 0, len);
                                bos.write(bytes, 0, len);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (bos != null) {
                                try {
                                    bos.close();
                                } catch (IOException e) {
                                    bos = null;
                                }
                            }
                            if (fos != null) {
                                try {
                                    fos.close();
                                } catch (IOException e) {
                                    fos = null;
                                }
                            }
                            if (bis != null) {
                                try {
                                    bis.close();
                                } catch (IOException e) {
                                    bis = null;
                                }
                            }
                            if (is != null) {
                                try {
                                    is.close();
                                } catch (IOException e) {
                                    is = null;
                                }
                            }
                        }
                        Message msg = Message.obtain();
                        msg.what = MSG_EXPORT_FINISH;
                        msg.obj = file.getAbsolutePath();
                        mHandler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    dismissLoadingDialog();
                }
            }
        }.start();
    }

}
