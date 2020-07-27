package com.jkrm.education.ui.fragment;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.hzw.baselib.base.AwMvpLazyFragment;
import com.hzw.baselib.interfaces.IOpenFileListener;
import com.hzw.baselib.interfaces.IPermissionListener;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwFileUtil;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.AwPopupwindowUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.util.AwRxPermissionUtil;
import com.hzw.baselib.util.AwSpUtil;
import com.jkrm.education.R;
import com.jkrm.education.adapter.ErrorQuesitonTimeAdapter;
import com.jkrm.education.api.APIService;
import com.jkrm.education.bean.BatchBean;
import com.jkrm.education.bean.ErrorQuestionClassifyBean;
import com.jkrm.education.bean.ErrorQuestionDetailBean;
import com.jkrm.education.bean.ErrorQuestionTimeBean;
import com.jkrm.education.bean.ErrorQuestionTimePagedBean;
import com.jkrm.education.bean.result.SimilarResultBean;
import com.jkrm.education.bean.rx.RxEditType;
import com.jkrm.education.bean.rx.RxErrorQuestion;
import com.jkrm.education.bean.rx.RxErrorTypeBean;
import com.jkrm.education.bean.rx.RxLQuestionType;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.constants.UrlConstant;
import com.jkrm.education.mvp.presenters.ErrorQuestionFragmentPresent;
import com.jkrm.education.mvp.views.ErrorQuestionFragmentView;
import com.jkrm.education.ui.activity.AnswerAnalysisActivity;
import com.jkrm.education.ui.activity.ErrorQuestionActivity;
import com.jkrm.education.ui.activity.OnlineAnswerActivity;
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
import butterknife.Unbinder;

import static com.umeng.commonsdk.proguard.e.e.i;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/6/3 16:15
 */

public class ErrorQuestionTimeFragment extends AwMvpLazyFragment<ErrorQuestionFragmentPresent> implements ErrorQuestionFragmentView.View ,  BaseQuickAdapter.RequestLoadMoreListener{


    @BindView(R.id.tv_out)
    TextView mTvOut;
    @BindView(R.id.btn_start)
    Button mBtnStart;
    Unbinder unbinder;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    @BindView(R.id.ll_bottom)
    LinearLayout mLlofBottom;
    private String mCourseId;
    private ErrorQuesitonTimeAdapter mErrorQuesitonTimeAdapter;
    private String mErrorType = "";
    List<ErrorQuestionTimeBean> mTimeBeanList=new ArrayList<>();
    public static final int MSG_EXPORT_FINISH = 0;
    private int current=1;
    private int size= Integer.MAX_VALUE;;
    private boolean isEdit;
    public static ArrayList<SimilarResultBean>  mSimilarResultBeans=new ArrayList<>();

    public static ArrayList<SimilarResultBean> getSimilarResultBeans() {
        return mSimilarResultBeans;
    }

    public void setSimilarResultBeans(ArrayList<SimilarResultBean> similarResultBeans) {
        mSimilarResultBeans = similarResultBeans;
    }

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
    protected int getLayoutId() {
        return R.layout.error_question_time_fragment_layout;
    }

    @Override
    protected void initView() {
        super.initView();
        EventBus.getDefault().register(this);
        ErrorQuestionActivity activity = (ErrorQuestionActivity) getActivity();
        mCourseId = activity.getCourseId();
        mErrorQuesitonTimeAdapter = new ErrorQuesitonTimeAdapter();
        mErrorQuesitonTimeAdapter.addAllData(mTimeBeanList);
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
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(getActivity(), mRcvData, mErrorQuesitonTimeAdapter, true);
        checkPermission();
    }



    private void getData(RxErrorTypeBean errorType) {
       // mPresenter.getByTime(RequestUtil.getByTimeBody(UserUtil.getAppUser().getStudId(), mCourseId, errorType.getErrorType(), "", ""));
        mPresenter.getByTimePaged(RequestUtil.getByTimeBody(UserUtil.getAppUser().getStudId(),mCourseId,errorType.getErrorType(),current+"", APIService.COMMON_PAGE_SIZE+""));
    }

    @Override
    protected void initListener() {
        super.initListener();

        mErrorQuesitonTimeAdapter.setOnLoadMoreListener(this, mRcvData);
        mErrorQuesitonTimeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!isEdit) {
                    ErrorQuestionTimeBean errorQuestionTimeBean = (ErrorQuestionTimeBean) adapter.getData().get(position);
                    Gson gson = new Gson();
                    ArrayList<BatchBean> batchBeans = new ArrayList<>();
                    BatchBean batchBean = gson.fromJson(gson.toJson(errorQuestionTimeBean), BatchBean.class);
                    if(!AwDataUtil.isEmpty(errorQuestionTimeBean.getSubQuestions())){
                        batchBean.setGroupQuestion("2");
                    }
                    batchBeans.add(batchBean);
                    toClass(AnswerAnalysisActivity.class, false, Extras.EXERCISEREPORT, (Serializable) batchBeans, Extras.KEY_QUESTION_TYPE, errorQuestionTimeBean.getErrorTypeName(),Extras.KEY_QUESTION_TIME,errorQuestionTimeBean.getShowTime()+"");
                }
            }
        });
        mErrorQuesitonTimeAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                EventBus.getDefault().postSticky(new RxEditType(true, false));
                mLlofBottom.setVisibility(View.VISIBLE);

                List<ErrorQuestionTimeBean> data = adapter.getData();
                for (ErrorQuestionTimeBean datum : data) {
                    datum.setChose(false);
                }
                int i = 0;
                for (ErrorQuestionTimeBean errorQuestionTimeBean : data) {
                    if (errorQuestionTimeBean.isChose()) {
                        i++;
                    }
                }
                EventBus.getDefault().post(new RxLQuestionType("已选择" + i + "题(" + data.size() + ")", 1));
                mErrorQuesitonTimeAdapter.notifyDataSetChanged();
                return true;
            }
        });
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<SimilarResultBean> errorQuestionBeans = new ArrayList<>();
                for (ErrorQuestionTimeBean errorQuestionTimeBean : mTimeBeanList) {
                    if (errorQuestionTimeBean.isChose()) {
                        Gson gson = new Gson();
                        SimilarResultBean similarResultBean = gson.fromJson(gson.toJson(errorQuestionTimeBean), SimilarResultBean.class);
                        errorQuestionBeans.add(similarResultBean);
                    }
                }
                mSimilarResultBeans=errorQuestionBeans;
                if (mSimilarResultBeans.size()==0) {
                    if("错题在线练".equals(mBtnStart.getText().toString())){
                        EventBus.getDefault().postSticky(new RxEditType(true, false));
                    }else{
                        showDialog("请先选择题目");
                    }
                    return;
                }
                toClass(OnlineAnswerActivity.class, false, Extras.KEY_SIMILAR_LIST, errorQuestionBeans,Extras.KEY_QUESTION_TYPE,"1",Extras.COURSE_ID,ErrorQuestionActivity.mCourseId);
                EventBus.getDefault().postSticky(new RxEditType(false, false));
                EventBus.getDefault().postSticky(new RxErrorQuestion(errorQuestionBeans));
            }

        });
        mTvOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuffer sb = new StringBuffer();
                List<ErrorQuestionTimeBean> mList = new ArrayList<>();
                for (ErrorQuestionTimeBean errorQuestionTimeBean : mTimeBeanList) {

                    if (errorQuestionTimeBean.isChose()) {
                        mList.add(errorQuestionTimeBean);
                        if(!AwDataUtil.isEmpty(errorQuestionTimeBean.getSubQuestions())){
                            for (ErrorQuestionTimeBean.SubQuestionsBean subQuestion : errorQuestionTimeBean.getSubQuestions()) {
                                ErrorQuestionTimeBean childError = new ErrorQuestionTimeBean();
                                childError.setId(subQuestion.getId());
                                mList.add(childError);

                            }
                        }
                    }
                }
                if (AwDataUtil.isEmpty(mList)) {
                    EventBus.getDefault().postSticky(new RxEditType(true, false));
                    showDialog("请先选择题目");
                    return;
                }
                for (ErrorQuestionTimeBean temp : mList) {
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
                    EventBus.getDefault().postSticky(new RxEditType(false, false));

                });

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
        if (AwDataUtil.isEmpty(list)) {
            mErrorQuesitonTimeAdapter.clearData();
            mRcvData.removeAllViews();
            mErrorQuesitonTimeAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
            return;
        }
       // mTimeBeanList = list;
       // mErrorQuesitonTimeAdapter.addAllData(list);
    }

    @Override
    public void getByTimeSFail(String msg) {
        showMsg(msg);
        mErrorQuesitonTimeAdapter.clearData();
        mRcvData.removeAllViews();
        mErrorQuesitonTimeAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
    }

    @Override
    public void getErrorDetailSuccess(List<ErrorQuestionDetailBean> list) {

    }

    @Override
    public void getErrorDetailFail(String msg) {

    }

    @Override
    public void getByTimePagedSuccess(ErrorQuestionTimePagedBean bean) {
        if (AwDataUtil.isEmpty(bean.getRows())) {
            if(size==1){
                mErrorQuesitonTimeAdapter.clearData();
                mRcvData.removeAllViews();
                mErrorQuesitonTimeAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
            }

        }else{

            size=Integer.parseInt(bean.getPages());
            for (ErrorQuestionTimePagedBean.RowsBean row : bean.getRows()) {
                Gson gson = new Gson();
                ErrorQuestionTimeBean errorQuestionTimeBean= gson.fromJson(gson.toJson(row),ErrorQuestionTimeBean.class);
                mTimeBeanList.add(errorQuestionTimeBean);
            }
            mErrorQuesitonTimeAdapter.loadMoreComplete();
            mErrorQuesitonTimeAdapter.notifyDataSetChanged();
            EventBus.getDefault().post(new RxLQuestionType("已选择" + 0 + "题(" + mTimeBeanList.size() + ")", 1));
        }
    }

    @Override
    public void getByTimePagedFail(String msg) {
        showMsg(msg);
        mErrorQuesitonTimeAdapter.clearData();
        mRcvData.removeAllViews();
        mErrorQuesitonTimeAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEditType(RxEditType rxEditType) {
        if (rxEditType != null) {
            isEdit = rxEditType.isEdit();
            //选择状态
           /* if (!rxEditType.isEdit()) {
                mLlofBottom.setVisibility(View.GONE);
            }*/
            mErrorQuesitonTimeAdapter.setChose(rxEditType.isEdit());
            if (rxEditType.isChoseAll()) {
                for (ErrorQuestionTimeBean errorQuestionTimeBean : mTimeBeanList) {
                    errorQuestionTimeBean.setChose(true);
                }
            } else {
                for (ErrorQuestionTimeBean errorQuestionTimeBean : mTimeBeanList) {
                    errorQuestionTimeBean.setChose(false);
                }
            }
            mErrorQuesitonTimeAdapter.notifyDataSetChanged();
        }
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onErrorType(RxErrorTypeBean rxErrorTypeBean) {
        if (rxErrorTypeBean != null) {
            getData(rxErrorTypeBean);
        }
    }
    //通知选了题目数量
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChoseChangeEvent(RxLQuestionType rxLQuestionType){
        if(1==rxLQuestionType.getType()){
           if(rxLQuestionType.getChoseNum()>0){
               mBtnStart.setText("开始练习");
           }else{
               mBtnStart.setText("错题在线练");
           }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

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
                    connection.setRequestProperty("clientFlag", "android");
                    connection.setRequestProperty("CLIENTSESSIONID", AwSpUtil.getString(MyConstant.SPConstant.XML_USER_INFO, MyConstant.SPConstant.KEY_CLI, ""));

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

    private void checkPermission(){
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
    public void onLoadMoreRequested() {
        if(current < size) {
            current++;
            getData(new RxErrorTypeBean(mErrorType));
        } else {
            mErrorQuesitonTimeAdapter.loadMoreEnd(true);
        }
    }
}
