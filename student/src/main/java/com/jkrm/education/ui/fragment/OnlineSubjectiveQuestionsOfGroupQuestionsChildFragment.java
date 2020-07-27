package com.jkrm.education.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.hzw.baselib.base.AwMvpFragment;
import com.hzw.baselib.base.AwMvpLazyFragment;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.widgets.MyGridView;
import com.hzw.baselib.widgets.PhotoFragmentDialog;
import com.jkrm.education.R;
import com.jkrm.education.adapter.CustomGridAdapter;
import com.jkrm.education.bean.result.SimilarResultBean;
import com.jkrm.education.constants.Extras;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.kexanie.library.MathView;

/**
 * @Description: 组题主观题  child 布局
 * @Author: xiangqian
 * @CreateDate: 2020/5/11 15:16
 */

public class OnlineSubjectiveQuestionsOfGroupQuestionsChildFragment extends AwMvpFragment {
    @BindView(R.id.math_view)
    MathView mMathView;
    @BindView(R.id.grid_view)
    MyGridView mGridView;
    @BindView(R.id.ll)
    LinearLayout mLl;
    Unbinder unbinder;
    private List<String> mImgList=new ArrayList<>();
    private CustomGridAdapter mCustomGridAdapter;

    @Override
    protected AwCommonPresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_onlinesubjectivequestions_group_child_questions_layout;
    }

    @Override
    protected void initData() {
        super.initData();
        SimilarResultBean.SubQuestionsBean subQuestionsBean = (SimilarResultBean.SubQuestionsBean) getArguments().getSerializable(Extras.SUB_QUESTION);
        mMathView.setText(subQuestionsBean.getContent());
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
                if (i == mImgList.size() - 1) {
                    PhotoFragmentDialog photoFragmentDialog = new PhotoFragmentDialog();
                    photoFragmentDialog.show(getFragmentManager(), "");
                    photoFragmentDialog.setOnDialogButtonClickListener(new PhotoFragmentDialog.OnDialogButtonClickListener() {
                        @Override
                        public void photoButtonClick() {
                            //拍照并裁剪
                            PictureSelector.create(OnlineSubjectiveQuestionsOfGroupQuestionsChildFragment.this).openCamera(PictureMimeType.ofImage()).enableCrop(true).freeStyleCropEnabled(true).rotateEnabled(true).forResult(PictureConfig.CHOOSE_REQUEST);
                        }

                        @Override
                        public void choseButtonClick() {
                            //相册选择并裁剪
                            PictureSelector.create(OnlineSubjectiveQuestionsOfGroupQuestionsChildFragment.this).openGallery(PictureMimeType.ofImage()).enableCrop(true).freeStyleCropEnabled(true).rotateEnabled(true).forResult(PictureConfig.CHOOSE_REQUEST);

                        }

                        @Override
                        public void cancelButtonClick() {

                        }
                    });
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != data) {
            //图片单选和多选数据都是以ArrayList的字符串数组返回的。
            // 如果同时开启裁剪和压缩，则取压缩路径为准因为是先裁剪后压缩
            List<LocalMedia> paths = PictureSelector.obtainMultipleResult(data);
            mImgList.add(paths.get(0).getPath());
            mCustomGridAdapter.notifyDataSetChanged();
            if (paths.get(0).isCompressed()) {
                String compressPath = paths.get(0).getCompressPath();
                showMsg(compressPath);
            }

        }
    }
}
