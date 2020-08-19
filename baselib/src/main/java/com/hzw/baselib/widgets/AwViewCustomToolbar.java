package com.hzw.baselib.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzw.baselib.R;
import com.hzw.baselib.util.AwDisplayUtil;


/**
 * Created by hzw on 2018/7/18.
 */

public class AwViewCustomToolbar extends Toolbar {

    private TextView mTvLeftView;
    private TextView mTvMiddleView;
    private TextView mTvRightView;

    //针对部分UI, 左侧有2个图片. 单独加个图片控件
    private ImageView mIvLeftImg2;

    private Toolbar mToolbar;

    OnLeftClickListener mOnLeftClickListener;
    OnRightClickListener mOnRightClickListener;
    OnLeft2ImgClckListener mOnLeft2ImgClckListener;
    private Context mContext;

    public AwViewCustomToolbar(Context context) {
        this(context, null);
        mContext = context;
    }

    public AwViewCustomToolbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        mContext = context;
    }

    public AwViewCustomToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        inflate(context, R.layout.view_widgets_toolbar,this);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mTvLeftView = (TextView) findViewById(R.id.toolbar_left);
        mTvMiddleView = (TextView) findViewById(R.id.toolbar_title);
        mTvMiddleView.setMovementMethod(ScrollingMovementMethod.getInstance());
        //mTvMiddleView.setHorizontallyScrolling(true);
        mTvMiddleView.setFocusable(true);
        mTvRightView = (TextView) findViewById(R.id.toolbar_right);
        mIvLeftImg2 = findViewById(R.id.iv_leftImg2);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTvMiddleView.setSelected(true);
        mTvLeftView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnLeftClickListener.onLeftTextClick();
            }
        });

        mTvRightView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnRightClickListener.onRightTextClick();
            }
        });

        mIvLeftImg2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnLeft2ImgClckListener.onLeft2ImgClick();
            }
        });
    }


    public void setToolbarBackgroundColor(int color) {

        mToolbar.setBackgroundResource(color);

    }

    /**
     * 设置只显示标题
     */
    public void setOnlyTitle() {

        mTvLeftView.setVisibility(INVISIBLE);
        mTvRightView.setVisibility(INVISIBLE);
    }

    /**
     * 设置标题不显示
     */
    public void hideTitleView() {

        mTvMiddleView.setVisibility(INVISIBLE);
    }

    /**
     * 设置左侧不显示
     */
    public void hideLeftView() {

        mTvLeftView.setVisibility(INVISIBLE);
    }

    public void showLeft2ImgView() {
        mIvLeftImg2.setVisibility(VISIBLE);
    }

    public void hideLeft2ImgView() {
        mIvLeftImg2.setVisibility(GONE);
    }

    /**
     * 设置右侧显示
     */
    public void showRightView() {
        mTvRightView.setVisibility(VISIBLE);
    }

    /**
     * 设置右侧不显示
     */
    public void hideRightView() {

        mTvRightView.setVisibility(INVISIBLE);
    }

    /**
     * 设置左侧文字颜色
     * @param colorRes
     */
    @SuppressLint("ResourceType")
    public void setTitleTextColor(int colorRes) {
        mTvMiddleView.setTextColor(getResources().getColor(colorRes));
    }

    /**
     * 设置左侧文字颜色
     * @param colorRes
     */
    public void setLeftTextColor(int colorRes) {
        mTvLeftView.setTextColor(getResources().getColor(colorRes));
    }

    /**
     * 设置右侧文字颜色
     * @param colorRes
     */
    public void setRTextColor(int colorRes) {
        mTvRightView.setTextColor(getResources().getColor(colorRes));
    }

    /**
     * 设置标题
     * @param text
     */
    public void setToolbarTitle(String text) {

        this.setTitle("");
        mTvMiddleView.setVisibility(View.VISIBLE);
        mTvMiddleView.setText(text);


    }

    public void setToolbarMaxEms(int length){
        mTvMiddleView.setMaxEms(length);
    }

    /**
     * 设置标题颜色
     * @param color
     */
    public void setToolbarTitleColor(int color) {
        mTvMiddleView.setTextColor(getResources().getColor(color));
    }

    /**
     * 设置左侧文本
     * @param text
     */
    public void setLeftText(String text) {

        mTvLeftView.setVisibility(VISIBLE);
        mTvLeftView.setText(text);

        //设置文本则不显示图片
        mTvLeftView.setCompoundDrawables(null,null,null,null);

    }

    /**
     * 设置右边文本
     * @param text
     */
    public void setRightText(String text) {

        mTvRightView.setVisibility(VISIBLE);
        mTvRightView.setText(text);

        //设置文本则不显示图片
        mTvRightView.setCompoundDrawables(null,null,null,null);

    }

    public String getRightText() {
        return mTvRightView.getText().toString();
    }

    /**
     * 设置右边文本
     * @param text
     */
    public void setRightTextWithImg(String text) {

        mTvRightView.setVisibility(VISIBLE);
        mTvRightView.setText(text);
    }


    /**
     * 设置左侧图片
     * @param id
     */
    public void setLeftImg(int id) {
        mTvLeftView.setVisibility(VISIBLE);
        Drawable drawable = getResources().getDrawable(id);

        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());

        //设置图片则不显示文字
        mTvLeftView.setText("");

        mTvLeftView.setCompoundDrawables(drawable,null,null,null);


    }

    /**
     * 设置左侧图片(固定大小)
     * @param id
     */
    public void setLeftImgWithSizeValue(int id) {
        mTvLeftView.setVisibility(VISIBLE);
        Drawable drawable = getResources().getDrawable(id);
        drawable.setBounds(0, 0, AwDisplayUtil.dip2px(mContext, 20), AwDisplayUtil.dip2px(mContext, 20));

        //设置图片则不显示文字
        mTvLeftView.setText("");
        mTvLeftView.setCompoundDrawables(drawable,null,null,null);

    }

    /**
     * 设置左侧图片含文字
     * @param id
     */
    public void setLeftImgAndText(int id, String str) {

        mTvLeftView.setVisibility(VISIBLE);

        Drawable drawable = getResources().getDrawable(id);

        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());

        mTvLeftView.setText(str);

        mTvLeftView.setCompoundDrawables(drawable,null,null,null);


    }

    /**
     * 设置左侧图片含文字(图片在右侧)
     * @param id
     */
    public void setLeftRightImgAndText(int id, String str) {
        mTvLeftView.setVisibility(VISIBLE);

        Drawable drawable = getResources().getDrawable(id);

        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());

        mTvLeftView.setText(str);
        mTvLeftView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        mTvLeftView.setCompoundDrawables(null,null,drawable,null);


    }

    /**
     * 设置右侧图片含文字
     * @param id
     */
    public void setRightImgAndText(int id, String str) {

        Drawable drawable = getResources().getDrawable(id);

        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());

        mTvRightView.setCompoundDrawables(null,null,drawable,null);

        mTvRightView.setText(str);


    }

    public void setLeft2Img(int id) {
        mIvLeftImg2.setVisibility(VISIBLE);
        mIvLeftImg2.setImageResource(id);
    }


    /**
     * 设置右侧图片
     * @param id
     */
    public void setRightImg(int id) {

        Drawable drawable = getResources().getDrawable(id);

        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());

        //设置图片则不显示文字
        mTvRightView.setText("");
        mTvRightView.setCompoundDrawables(null,null,drawable,null);

    }

    /**
     * 设置右侧图片(固定大小)
     * @param id
     */
    public void setRightImgWithSizeValue(int id) {

        Drawable drawable = getResources().getDrawable(id);
        drawable.setBounds(0, 0, AwDisplayUtil.dip2px(mContext, 20), AwDisplayUtil.dip2px(mContext, 20));

        //设置图片则不显示文字
        mTvRightView.setText("");
        mTvRightView.setCompoundDrawables(null,null,drawable,null);

    }

    /**
     * 设置右侧图片
     * @param id
     */
    public void setRightImgWithTxt(int id) {

        Drawable drawable = getResources().getDrawable(id);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mTvRightView.setCompoundDrawables(null,null,drawable,null);

    }

    //左侧文本回调接口
    public interface OnLeftClickListener {
        void onLeftTextClick();
    }

    /**
     * 设置左侧文本回调
     * @param listener
     */
    public void setOnLeftClickListener(OnLeftClickListener listener) {
        this.mOnLeftClickListener = listener;
    }

    public interface OnLeft2ImgClckListener {
        void onLeft2ImgClick();
    }

    public void setOnLeft2ImgClckListener(OnLeft2ImgClckListener listener) {
        this.mOnLeft2ImgClckListener = listener;
    }

    //右侧文本回调接口
    public interface OnRightClickListener {
        void onRightTextClick();
    }

    /**
     * 设置右侧文本回调
     * @param litener
     */
    public void setOnRightClickListener(OnRightClickListener litener) {
        this.mOnRightClickListener = litener;
    }

    /**
     * 设置返回图片
     * @param id 图片的id
     */
    public void setbackIcon(int id) {

        this.setNavigationIcon(id);

        mTvLeftView.setVisibility(GONE);
        //左侧文本不设置draw
        mTvLeftView.setCompoundDrawables(null,null,null,null);
    }

}
