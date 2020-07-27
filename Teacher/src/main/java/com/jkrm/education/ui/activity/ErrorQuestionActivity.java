package com.jkrm.education.ui.activity;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
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
import com.jkrm.education.R;
import com.jkrm.education.adapter.error.ErrorBasketAdapter;
import com.jkrm.education.bean.ErrorBasketBean;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.constants.UrlConstant;
import com.jkrm.education.mvp.presenters.ErrorQuestionPresent;
import com.jkrm.education.mvp.views.ErrorQuestionView;
import com.jkrm.education.util.DataUtil;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.util.UserUtil;

import org.greenrobot.eventbus.EventBus;

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
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ErrorQuestionActivity extends AwMvpActivity<ErrorQuestionPresent> implements ErrorQuestionView.View {

    @BindView(R.id.tv_clear)
    TextView mTvClear;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.btn_out)
    Button btnOut;
    private ErrorBasketAdapter mErrorBasketAdapter;
    List<ErrorBasketBean> mList;
    private ErrorBasketBean mErrorBasket;
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
    protected int getLayoutId() {
        return R.layout.activity_error_question;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusBlue();
        mErrorBasketAdapter = new ErrorBasketAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(ErrorQuestionActivity.this, mRcvData, mErrorBasketAdapter, false);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getErrorBasket(UserUtil.getTeacherId());
        checkPermission();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mErrorBasketAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                tvNum.setText("共" + mErrorBasketAdapter.getData().size() + "题");
            }
        });
        mErrorBasketAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                mErrorBasket = (ErrorBasketBean) adapter.getData().get(position);
                mPresenter.deleteErrorBasket(RequestUtil.deleteErrorBasket(UserUtil.getTeacherId(), mErrorBasket.getId()));
            }
        });
    }

    @OnClick({R.id.tv_clear,R.id.btn_out})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.tv_clear:
                mPresenter.clearErrorBasket(UserUtil.getTeacherId());
                break;
            case R.id.btn_out:
                StringBuffer sb = new StringBuffer();
                if(AwDataUtil.isEmpty(mList)){
                    return;
                }
                for (ErrorBasketBean temp : mList) {
                    sb.append(temp.getId()).append(",");
                }
                String ids = sb.toString().substring(0, sb.toString().length() - 1);

                AwPopupwindowUtil.showCommonListWithTitlePopupWindow(mActivity, "请选择导出内容", DataUtil.getBasketExportChoiceList(), btnOut, bean -> {
                    String temp = (String) bean;
                    if (temp.contains("仅")) {
                        exportBasket(ids, false);
                    } else if (temp.contains("答案")) {
                        exportBasket(ids, true);
                    }

                });
                break;
        }
    }

    @Override
    protected ErrorQuestionPresent createPresenter() {
        return new ErrorQuestionPresent(this);
    }


    @Override
    public void getErrorBasketSuccess(List<ErrorBasketBean> data) {
        mList = data;
        mErrorBasketAdapter.addAllData(mList);
        if (AwDataUtil.isEmpty(mList)) {
            mErrorBasketAdapter.clearData();
            mRcvData.removeAllViews();
            mErrorBasketAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, "暂无数据", -1));
        }
    }


    @Override
    public void getErrorBasketFail(String msg) {

    }

    @Override
    public void deleteErrorBasketSuccess(String data) {
        if (AwDataUtil.isEmpty(mList)) {
            mErrorBasketAdapter.clearData();
            mRcvData.removeAllViews();
            mErrorBasketAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, "暂无数据", -1));
        }
        mList.remove(mErrorBasket);
        mErrorBasketAdapter.notifyDataSetChanged();

    }

    @Override
    public void deleteErrorBasketFail(String msg) {
        showMsg(msg);
    }

    @Override
    public void clearErrorBasketSuccess(String data) {
        mList.clear();
        mErrorBasketAdapter.notifyDataSetChanged();
    }

    @Override
    public void clearErrorBasketFail(String msg) {
        showMsg(msg);
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

}
