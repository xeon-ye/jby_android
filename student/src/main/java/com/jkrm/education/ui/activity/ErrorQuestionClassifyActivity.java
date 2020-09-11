package com.jkrm.education.ui.activity;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.interfaces.IOpenFileListener;
import com.hzw.baselib.interfaces.IPermissionListener;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwFileUtil;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.AwPopupwindowUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.util.AwRxPermissionUtil;
import com.hzw.baselib.util.AwSpUtil;
import com.hzw.baselib.widgets.AwCommonListPopupWindow;
import com.jkrm.education.R;
import com.jkrm.education.adapter.ErrorQuesitonDetailAdapter;
import com.jkrm.education.api.UrlInterceptor;
import com.jkrm.education.bean.BatchBean;
import com.jkrm.education.bean.ErrorQuestionClassifyBean;
import com.jkrm.education.bean.ErrorQuestionDetailBean;
import com.jkrm.education.bean.ErrorQuestionTimeBean;
import com.jkrm.education.bean.ErrorQuestionTimePagedBean;
import com.jkrm.education.bean.result.SimilarResultBean;
import com.jkrm.education.bean.rx.RxEditType;
import com.jkrm.education.bean.rx.RxLQuestionType;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.constants.UrlConstant;
import com.jkrm.education.mvp.presenters.ErrorQuestionFragmentPresent;
import com.jkrm.education.mvp.views.ErrorQuestionFragmentView;
import com.jkrm.education.ui.fragment.ErrorQuestionClassifyFragment;
import com.jkrm.education.util.DataUtil;
import com.jkrm.education.util.RequestUtil;
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
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 错题本
 */
public class ErrorQuestionClassifyActivity extends AwMvpActivity<ErrorQuestionFragmentPresent> implements ErrorQuestionFragmentView.View {


    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    @BindView(R.id.img_back)
    ImageView mImgBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_chose)
    TextView mTvChose;
    @BindView(R.id.rl_of_tab)
    RelativeLayout mRlOfTab;
    @BindView(R.id.id_chose_all)
    TextView mIdChoseAll;
    @BindView(R.id.tv_chose_num)
    TextView mTvChoseNum;
    @BindView(R.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R.id.rl_of_chose)
    RelativeLayout mRlOfChose;
    @BindView(R.id.fl)
    FrameLayout mFl;
    @BindView(R.id.tv_out)
    TextView mTvOut;
    @BindView(R.id.btn_start)
    Button mBtnStart;
    @BindView(R.id.ll_of_start)
    LinearLayout mLlofBottom;

    private String mClassify = "1";
    private String mCourseID = "";
    private String mTemplateID = "";
    private String mTilte;
    private ErrorQuesitonDetailAdapter mDetailAdapter;
    private boolean isAllChose = true;
    private boolean isEdit;
    public static BatchBean mBatchBean;


    List<ErrorQuestionDetailBean> mDetailBeans = new ArrayList<>();
    public static final int MSG_EXPORT_FINISH = 0;
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
                                    if (!isSuccess) {
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
    protected ErrorQuestionFragmentPresent createPresenter() {
        return new ErrorQuestionFragmentPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_error_question_classify;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusBlue();
        mTemplateID = getIntent().getStringExtra(Extras.KEY_TEMPLATE_ID);
        mTilte = getIntent().getStringExtra(Extras.KEY_TEMPLATE_TITLE);
        mClassify = ErrorQuestionClassifyFragment.CLASSIFY_ID;
        mCourseID = ErrorQuestionActivity.mCourseId;
        checkPermission();
    }


    @Override
    protected void initData() {
        super.initData();
        mTvTitle.setText(mTilte);
        mPresenter.getErrorDetail(RequestUtil.getErrorDetailBody(mTemplateID, mClassify, mCourseID, UserUtil.getAppUser().getStudId()));
        mDetailAdapter = new ErrorQuesitonDetailAdapter();
        mRcvData.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);
            }

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.left = 8;
                outRect.right = 8;
                outRect.bottom = 8;
                outRect.top = 8;
            }
        });
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(this, mRcvData, mDetailAdapter, true);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDetailAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!isEdit) {
                    ErrorQuestionDetailBean errorQuestionTimeBean = (ErrorQuestionDetailBean) adapter.getData().get(position);
                    Gson gson = new Gson();
                    ArrayList<BatchBean> batchBeans = new ArrayList<>();
                    BatchBean batchBean = gson.fromJson(gson.toJson(errorQuestionTimeBean), BatchBean.class);
                    mBatchBean = batchBean;
                    if (!AwDataUtil.isEmpty(errorQuestionTimeBean.getSubQuestions())) {
                        batchBean.setGroupQuestion("2");
                    }
                    batchBeans.add(batchBean);
                    toClass(AnswerAnalysisActivity.class, false, Extras.EXERCISEREPORT, (Serializable) batchBeans, Extras.KEY_QUESTION_TYPE, errorQuestionTimeBean.getErrorTypeName(), Extras.KEY_QUESTION_TIME, errorQuestionTimeBean.getShowTime() + "");
                }
            }
        });
        mDetailAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                initTitle(true);
                mDetailAdapter.setChose(true);
                mDetailAdapter.notifyDataSetChanged();
                List<ErrorQuestionDetailBean> data = adapter.getData();
                for (ErrorQuestionDetailBean datum : data) {
                    datum.setChose(false);
                }
                int i = 0;
                for (ErrorQuestionDetailBean errorQuestionTimeBean : data) {
                    if (errorQuestionTimeBean.isChose()) {
                        i++;
                    }
                }
                EventBus.getDefault().post(new RxLQuestionType("已选择" + i + "题(" + data.size() + ")", 2));
                return true;
            }
        });

        mIdChoseAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAllChose) {
                    for (ErrorQuestionDetailBean errorQuestionDetailBean : mDetailBeans) {
                        errorQuestionDetailBean.setChose(true);
                    }
                    isAllChose = false;
                } else {
                    for (ErrorQuestionDetailBean errorQuestionDetailBean : mDetailBeans) {
                        errorQuestionDetailBean.setChose(false);
                    }
                    isAllChose = true;
                }
                if(isAllChose){
                    mIdChoseAll.setText("全选");
                }else{
                    mIdChoseAll.setText("取消全选");
                }
                mDetailAdapter.notifyDataSetChanged();
            }
        });

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
        if (AwDataUtil.isEmpty(list)) {
            mRcvData.removeAllViews();
            mDetailAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(this, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
            return;
        }
        mDetailBeans = list;
        mDetailAdapter.addAllData(list);
        EventBus.getDefault().post(new RxLQuestionType("已选择" + 0 + "题(" + mDetailBeans.size() + ")", 2));

    }

    @Override
    public void getErrorDetailFail(String msg) {
        showMsg(msg);
        mDetailAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(this, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));

    }

    @Override
    public void getByTimePagedSuccess(ErrorQuestionTimePagedBean bean) {

    }

    @Override
    public void getByTimePagedFail(String msg) {

    }

    @OnClick({R.id.tv_cancel, R.id.tv_out, R.id.btn_start})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                initTitle(false);
                mBtnStart.setText("错题在线练");
                break;
            case R.id.tv_out:
                initTitle(true);
                StringBuffer sb = new StringBuffer();
                List<ErrorQuestionDetailBean> mList = new ArrayList<>();
                for (ErrorQuestionDetailBean errorQuestionTimeBean : mDetailBeans) {
                    if (errorQuestionTimeBean.isChose()) {
                        mList.add(errorQuestionTimeBean);
                        if(!AwDataUtil.isEmpty(errorQuestionTimeBean.getSubQuestions())){
                            for (ErrorQuestionTimeBean.SubQuestionsBean subQuestion : errorQuestionTimeBean.getSubQuestions()) {
                                ErrorQuestionDetailBean errorQuestionDetailBean=new ErrorQuestionDetailBean();
                                errorQuestionDetailBean.setId(subQuestion.getId());
                                mList.add(errorQuestionDetailBean);
                            }
                        }
                    }
                }
                mDetailAdapter.setChose(true);
                mDetailAdapter.notifyDataSetChanged();
                if (AwDataUtil.isEmpty(mList)) {
                    showDialog("请选择题目");
                    if ("错题在线练".equals(mBtnStart.getText().toString())) {
                       //initTitle(true);
                    }
                    return;
                }
                for (ErrorQuestionDetailBean temp : mList) {
                    sb.append(temp.getId()).append(",");
                }
                String ids = sb.toString().substring(0, sb.toString().length() - 1);

                AwPopupwindowUtil.showCommonListWithTitlePopupWindow(mActivity, "请选择导出内容", DataUtil.getBasketExportChoiceList(), mTvOut, bean -> {
                    String temp = (String) bean;
                    if (temp.contains("仅")) {
                        exportBasket(ids, false);
                    } else if (temp.contains("答案")) {
                        exportBasket(ids, true);
                    }
                    initTitle(false);
                });
                for (ErrorQuestionDetailBean detailBean : mDetailBeans) {
                    detailBean.setChose(false);
                }
                mDetailAdapter.notifyDataSetChanged();
                break;
            case R.id.btn_start:
                ArrayList<SimilarResultBean> errorQuestionBeans = new ArrayList<>();
                for (ErrorQuestionDetailBean errorQuestionTimeBean : mDetailBeans) {
                    if (errorQuestionTimeBean.isChose()) {
                        Gson gson = new Gson();
                        SimilarResultBean similarResultBean = gson.fromJson(gson.toJson(errorQuestionTimeBean), SimilarResultBean.class);
                        errorQuestionBeans.add(similarResultBean);
                    }
                }
                if (errorQuestionBeans.size() == 0) {
                    if ("错题在线练".equals(mBtnStart.getText().toString())) {
                        initTitle(true);
                        mDetailAdapter.setChose(true);
                        mDetailAdapter.notifyDataSetChanged();
                    } else {
                        showDialog("请先选择题目");
                    }
                    for (ErrorQuestionDetailBean detailBean : mDetailBeans) {
                        detailBean.setChose(false);
                    }
                    mDetailAdapter.notifyDataSetChanged();
                    return;
                }
                initTitle(false);
                toClass(OnlineAnswerActivity.class, false, Extras.KEY_SIMILAR_LIST, errorQuestionBeans, Extras.KEY_CLASSIFY, "错题重练", Extras.COURSE_ID, ErrorQuestionActivity.mCourseId);

                break;
        }
    }

    /**
     * 导出题篮
     *
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
                    connection.setRequestProperty("Authorization", AwSpUtil.getString(MyConstant.SPConstant.XML_USER_INFO, MyConstant.SPConstant.KEY_TOKEN, ""));
                    connection.setRequestProperty("client", "android");
                    connection.setRequestProperty("CLIENTSESSIONID", AwSpUtil.getString(MyConstant.SPConstant.XML_USER_INFO, MyConstant.SPConstant.KEY_CLI, ""));
                    connection.setRequestProperty("Role", UrlInterceptor.getRoleId());
                    connection.setRequestProperty("t", UrlInterceptor.getT());
                    connection.setRequestProperty("encrypt", UrlInterceptor.getSafe());
                    connection.setRequestProperty("App","student");
                    //传参
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
                            String fileName = "习题文件" + System.currentTimeMillis() + ".docx";
                            file = new File(Environment.getExternalStorageDirectory() + File.separator + "/jby" + File.separator + fileName);
                            if (!file.getParentFile().exists()) {
                                file.getParentFile().mkdir();
                            }
                            if (!file.exists()) {
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

    /**
     * 选择状态时 上下切换
     *
     * @param isChose
     */
    public void initTitle(boolean isChose) {
        isEdit = isChose;
        if (isChose) {
            showView(mRlOfChose, true);
            showView(mRlOfTab, false);
            //showView(mLlofBottom,true);

        } else {
            showView(mRlOfChose, false);
            showView(mRlOfTab, true);
            //showView(mLlofBottom,false);

            mDetailAdapter.setChose(false);
            mDetailAdapter.notifyDataSetChanged();
        }
        mIdChoseAll.setText("全选");
    }

    //通知选了题目数量
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChoseChangeEvent(RxLQuestionType rxLQuestionType) {
        if (2 == rxLQuestionType.getType()) {
            mTvChoseNum.setText(rxLQuestionType.getNum());
            if (rxLQuestionType.getChoseNum() > 0) {
                mBtnStart.setText("开始练习");
            } else {
                mBtnStart.setText("错题在线练");
            }
        }
    }

    public static BatchBean getmBatchBean() {
        return mBatchBean;
    }

    public static void setmBatchBean(BatchBean mBatchBean) {
        ErrorQuestionClassifyActivity.mBatchBean = mBatchBean;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }

    private void checkPermission() {
        AwRxPermissionUtil.getInstance().checkPermission(mActivity, AwRxPermissionUtil.permissionsStorage, new IPermissionListener() {
            @Override
            public void granted() {

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
    }
    @Override
    protected void onPause() {
        super.onPause();
        isAllChose=false;
        mIdChoseAll.setText("全选");

    }
}
