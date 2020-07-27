package com.hzw.baselib.interfaces;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * Created by hzw on 2017/12/8.
 *
 */
public class TextChangeListener implements TextWatcher {

	private static final InputFilter[] INPUT_FILTER_ARRAY = new InputFilter[1];
	private CharSequence temp;
	private ImageView ivInit;
	private ImageView ivDel;
	private TextView tvCount;
	private TextView mEditText;
	private int maxLength;
	private boolean limitLength = false;
	private int default_decimal_length = 0;
	private int defalut_max_length = Integer.MAX_VALUE;
	private OnTextEditListener mOnTextEditListener;
	private boolean isInitData = true;

	public TextChangeListener(TextView et, TextView tv_count, int maxLength, boolean isInitData, OnTextEditListener listener){
		this.mOnTextEditListener = listener;
		this.isInitData = isInitData;
		temp = et.getText();
		this.mEditText = et;
		this.tvCount = tv_count;
		this.maxLength = maxLength;
	}

	public TextChangeListener(TextView tv, ImageView iv_del){
		temp = tv.getText();
		this.ivDel = iv_del;
	}

	public TextChangeListener(TextView tv, ImageView iv_del, boolean isInitData, OnTextEditListener listener){
		this.mOnTextEditListener = listener;
		this.isInitData = isInitData;
		temp = tv.getText();
		this.ivDel = iv_del;
	}

	public TextChangeListener(TextView et, ImageView iv_del, boolean limitLength, int default_decimal_length, int defalut_max_length, boolean isInitData, OnTextEditListener listener){
		temp = et.getText();
		this.mOnTextEditListener = listener;
		this.isInitData = isInitData;
		this.mEditText = et;
		this.ivDel = iv_del;
		this.limitLength = limitLength;
		this.default_decimal_length = default_decimal_length;
		this.defalut_max_length = defalut_max_length;
	}

	public TextChangeListener(TextView tv, ImageView iv_init, ImageView iv_del){
		temp = tv.getText();
		this.ivInit = iv_init;
		this.ivDel = iv_del;
	}

	public TextChangeListener(TextView tv, ImageView iv_init, ImageView iv_del, boolean isInitData, OnTextEditListener listener){
		this.mOnTextEditListener = listener;
		this.isInitData = isInitData;
		temp = tv.getText();
		this.ivInit = iv_init;
		this.ivDel = iv_del;
	}

    public TextChangeListener() {

    }

    @Override
	public void afterTextChanged(Editable s) {
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		temp = s;
		if(temp.length() == 0) {
			showDelView(false);
		} else {
			showDelView(true);
		}
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if(null != tvCount)
			tvCount.setText((maxLength - mEditText.getText().toString().length()) + "");
		if(s.length() > 0) {
			showDelView(true);
			if(limitLength) {
				String inputContent = s.toString();
				if (inputContent.contains(".")) {
					int maxLength = inputContent.indexOf(".") + 3;
					INPUT_FILTER_ARRAY[0] = new InputFilter.LengthFilter(maxLength);
				} else {
					INPUT_FILTER_ARRAY[0] = new InputFilter.LengthFilter(defalut_max_length);
				}
				mEditText.setFilters(INPUT_FILTER_ARRAY);
			}
		} else {
			showDelView(false);
		}
		if(mOnTextEditListener != null) {
			if(isInitData) {
				isInitData = false;
				return;
			}
			mOnTextEditListener.onEdited();
		}
	}

	private void showDelView(boolean show) {
		if(show) {
			if(null != ivDel)
				ivDel.setVisibility(View.VISIBLE);
			if(null != ivInit)
				ivInit.setVisibility(View.GONE);
		} else {
			if(null != ivDel)
				ivDel.setVisibility(View.GONE);
			if(null != ivInit)
				ivInit.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * calendar菜单选择监听接口
	 */
	public interface OnTextEditListener {
		void onEdited();
	}

	/**
	 * 设置菜单监听
	 */
	public void setOnTextEditListener(OnTextEditListener listener) {
		this.mOnTextEditListener = listener;
	}
}
