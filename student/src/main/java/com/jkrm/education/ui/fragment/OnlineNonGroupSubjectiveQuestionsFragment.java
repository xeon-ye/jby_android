package com.jkrm.education.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.hzw.baselib.base.AwMvpFragment;
import com.hzw.baselib.base.AwMvpLazyFragment;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwArraysUtil;
import com.hzw.baselib.util.AwMathViewUtil;
import com.hzw.baselib.widgets.MyGridView;
import com.hzw.baselib.widgets.PhotoFragmentDialog;
import com.jkrm.education.R;
import com.jkrm.education.adapter.CustomGridAdapter;
import com.jkrm.education.bean.result.OssResultBean;
import com.jkrm.education.bean.result.SimilarResultBean;
import com.jkrm.education.bean.rx.RxQuestionBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.HomeworkDetailScorePresent;
import com.jkrm.education.mvp.presenters.OnlineNonGroupSubjectiveQuestionsFragmentPresent;
import com.jkrm.education.mvp.views.HomeworkDetailScoreView;
import com.jkrm.education.mvp.views.OnlineNonGroupSubjectiveQuestionsFragmentView;
import com.jkrm.education.ui.activity.ImgActivity;
import com.jkrm.education.ui.activity.OnlineNonMultipleChoiceActivity;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.sobot.chat.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.kexanie.library.MathView;

/**
 * @Description: 非组题 主观题
 * @Author: xiangqian
 * @CreateDate: 2020/4/29 16:17
 */

public class OnlineNonGroupSubjectiveQuestionsFragment extends AwMvpFragment<OnlineNonGroupSubjectiveQuestionsFragmentPresent> implements OnlineNonGroupSubjectiveQuestionsFragmentView.View {
    @BindView(R.id.grid_view)
    MyGridView mGridView;
    @BindView(R.id.math_view)
    MathView mMathViewTitle;
    Unbinder unbinder;

    private SimilarResultBean mBean;
    private List<SimilarResultBean> mList;
    private List<String> mImgList=new ArrayList<>();
    private CustomGridAdapter mCustomGridAdapter;
    private String mUrl;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_onlinenonsubjectivequestions_groupquestions_layout;
    }

    @Override
    protected void initData() {
        super.initData();
        mBean = (SimilarResultBean) getArguments().getSerializable(Extras.KEY_BEAN_QUESTION_SIMILAR);
        mList = (List<SimilarResultBean>) getArguments().getSerializable(Extras.KEY_BEAN_QUESTION_SIMILAR_LIST);
        if (mBean == null) {
            return;
        }
        mMathViewTitle.setText(mBean.getContent());
        AwMathViewUtil.setImgScan(mMathViewTitle);
        mImgList.add("");
        mCustomGridAdapter = new CustomGridAdapter(mActivity,mImgList);
        mGridView.setAdapter(mCustomGridAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==mImgList.size()-1){
                    PhotoFragmentDialog photoFragmentDialog=new PhotoFragmentDialog();
                    photoFragmentDialog.show(getFragmentManager(),"");
                    photoFragmentDialog.setOnDialogButtonClickListener(new PhotoFragmentDialog.OnDialogButtonClickListener() {
                        @Override
                        public void photoButtonClick() {
                            //拍照并裁剪
                            PictureSelector.create(OnlineNonGroupSubjectiveQuestionsFragment.this).openCamera(PictureMimeType.ofImage()).enableCrop(true).freeStyleCropEnabled(true).compress(true).rotateEnabled(true).forResult(1);
                        }

                        @Override
                        public void choseButtonClick() {
                            //拍照并裁剪
                            PictureSelector.create(OnlineNonGroupSubjectiveQuestionsFragment.this).openGallery(PictureMimeType.ofImage()).maxSelectNum(1).freeStyleCropEnabled(true).compress(true).rotateEnabled(true).forResult(1);

                        }

                        @Override
                        public void cancelButtonClick() {

                        }
                    });
                }else {
                    toClass(ImgActivity.class, false, Extras.IMG_URL, mImgList.get(i+1));
                }
            }
        });
        mCustomGridAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                    if(mImgList.size()>1){

                        //有图片 已作答
                        EventBus.getDefault().post(new RxQuestionBean(mBean.getId(),true, AwArraysUtil.splitList(mImgList),mBean.getParentId()));
                    }else{
                        //无图片 未作答
                        EventBus.getDefault().post(new RxQuestionBean(mBean.getId(),false,"",mBean.getParentId()));
                    }
            }
        });
        mCustomGridAdapter.setOnDelelteClickListener(new CustomGridAdapter.OnDelelteClickListener() {

            @Override
            public void deleteButtonClick(int postion) {
                EventBus.getDefault().post(new RxQuestionBean(mBean.getId(),mImgList.get(postion),mBean.getParentId(),true,true));
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected OnlineNonGroupSubjectiveQuestionsFragmentPresent createPresenter() {
        return new OnlineNonGroupSubjectiveQuestionsFragmentPresent(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (null != data) {
                //图片单选和多选数据都是以ArrayList的字符串数组返回的。
                // 如果同时开启裁剪和压缩，则取压缩路径为准因为是先裁剪后压缩
                List<LocalMedia> paths = PictureSelector.obtainMultipleResult(data);
                //压缩
                if(paths.get(0).isCompressed()){
                    // 如果同时开启裁剪和压缩，则取压缩路径为准因为是先裁剪后压缩
                    mPresenter.uploadOss("user_server",paths.get(0).getCompressPath());
                }else{
                    mPresenter.uploadOss("user_server",paths.get(0).getPath());
                }


            }
    }

    @Override
    public void uploadOssSuccess(OssResultBean bean) {
        showMsg("上传成功");
        mUrl = bean.getAccessUrl();
        EventBus.getDefault().post(new RxQuestionBean(mBean.getId(),mUrl,mBean.getParentId(),true,false));
        mImgList.add(bean.getAccessUrl());
        mCustomGridAdapter.notifyDataSetChanged();


    }

    @Override
    public void uploadOssFail(String msg) {
        showMsg("上传失败");
    }
}
