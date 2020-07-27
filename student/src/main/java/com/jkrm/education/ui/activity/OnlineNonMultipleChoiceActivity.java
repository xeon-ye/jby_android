package com.jkrm.education.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.widgets.PhotoFragmentDialog;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.SimilarResultBean;
import com.jkrm.education.mvp.presenters.OnlineNonMultipleChoicePresent;
import com.jkrm.education.mvp.views.OnlineNonMultipleChoiceView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.sobot.chat.utils.ToastUtil;

import java.util.List;

/**
 * 在线答题 非选择题
 */
public class OnlineNonMultipleChoiceActivity extends AwMvpActivity<OnlineNonMultipleChoicePresent> implements OnlineNonMultipleChoiceView.View  {

    private final int  IMAGES_CODE=1;

    @Override
    protected OnlineNonMultipleChoicePresent createPresenter() {
        return new OnlineNonMultipleChoicePresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_online_non_multiple_choice;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusTxtDark();
        setToolbarWithBackImg("",null);
    }

    @Override
    protected void initData() {
        super.initData();
        PhotoFragmentDialog photoFragmentDialog=new PhotoFragmentDialog();
        photoFragmentDialog.show(getSupportFragmentManager(),"");
        photoFragmentDialog.setOnDialogButtonClickListener(new PhotoFragmentDialog.OnDialogButtonClickListener() {
            @Override
            public void photoButtonClick() {
                //拍照并裁剪
                PictureSelector.create(OnlineNonMultipleChoiceActivity.this).openCamera(PictureMimeType.ofImage()).enableCrop(true).freeStyleCropEnabled(true).rotateEnabled(true).forResult(IMAGES_CODE);
            }

            @Override
            public void choseButtonClick() {
                //拍照并裁剪
                PictureSelector.create(OnlineNonMultipleChoiceActivity.this).openGallery(PictureMimeType.ofImage()).enableCrop(true).freeStyleCropEnabled(true).rotateEnabled(true).forResult(IMAGES_CODE);
                //PictureSelector.create(mActivity).openGallery(PictureMimeType.ofImage()).forResult(PictureConfig.CHOOSE_REQUEST);

            }

            @Override
            public void cancelButtonClick() {

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGES_CODE && resultCode == Activity.RESULT_OK) {
            if (null != data) {
                //图片单选和多选数据都是以ArrayList的字符串数组返回的。
                // 如果同时开启裁剪和压缩，则取压缩路径为准因为是先裁剪后压缩
                List<LocalMedia> paths = PictureSelector.obtainMultipleResult(data);
                if(paths.get(0).isCompressed()){
                    String compressPath = paths.get(0).getCompressPath();
                    ToastUtil.showLongToast(OnlineNonMultipleChoiceActivity.this,compressPath);
                }

            }
        }
    }

    @Override
    public void getSimilarSuccess(List<SimilarResultBean> result) {

    }

    @Override
    public void getSimilarFail(String msg) {

    }
}
