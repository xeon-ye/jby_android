package com.jkrm.education.ui.activity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;
import com.hzw.baselib.base.AwBaseActivity;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwImgUtil;
import com.jkrm.education.R;
import com.jkrm.education.constants.Extras;

import butterknife.BindView;

/**
 * Created by hzw on 2019/5/27.
 */

public class ImgActivity extends AwBaseActivity {

    @BindView(R.id.iv_close)
    ImageView mIvClose;
    @BindView(R.id.iv_img)
    PhotoView mIvImg;

    private String imgUrl = "";
    private String height = "0.6";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_img;
    }

    @Override
    protected void initView() {
        super.initView();
        if(getIntent().getBooleanExtra(Extras.IMG_NEED_PORTRAIT, false)) {
            if(this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT){
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }
        Window window = this.getWindow();
        if(!AwDataUtil.isEmpty(getIntent().getStringExtra(Extras.IMG_HEIGHT))) {
            height = getIntent().getStringExtra(Extras.IMG_HEIGHT);
            window.setGravity(Gravity.BOTTOM);
        } else {
            window.setGravity(Gravity.CENTER);
        }
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (d.getWidth() * 1); // 宽度设置为屏幕的weight
        p.height = (int) (d.getHeight() * Float.parseFloat(height)); // 宽度设置为屏幕的weight
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setAttributes(p);
    }

    @Override
    protected void initData() {
        super.initData();
        imgUrl = getIntent().getStringExtra(Extras.IMG_URL);
        AwImgUtil.setImg(mActivity, mIvImg, imgUrl);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mIvClose.setOnClickListener(v -> finish());
    }
}
