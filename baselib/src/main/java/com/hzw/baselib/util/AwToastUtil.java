package com.hzw.baselib.util;

import android.widget.Toast;

import com.hzw.baselib.base.AwBaseApplication;

/**
 * Toast统一管理类
 * 
 * @author hzw
 * 
 */
public class AwToastUtil {

	private int mTextId;
	private String mTempStr = "";
	private Toast toast = null;
	public static AwToastUtil awToastUtil;

	/*********************************/
	public static AwToastUtil getInsance(){
		if(awToastUtil == null)
			awToastUtil = new AwToastUtil();
		return awToastUtil;
	}

	private AwToastUtil(){}

	public void show(String text) {
		if (toast == null) {
			mTempStr = text;
			toast = Toast.makeText(AwBaseApplication.getInstance(), text, Toast.LENGTH_SHORT);
		} else {
			if (mTempStr.equals(text)) {
				mTempStr = "";
				return;
			}
			toast.cancel();
			toast = Toast.makeText(AwBaseApplication.getInstance(), text, Toast.LENGTH_SHORT);
			mTempStr = text;
		}
		toast.show();
	}

	public void show(int textId) {
		if (toast == null) {
			mTextId = textId;
			toast = Toast.makeText(AwBaseApplication.getInstance(), textId, Toast.LENGTH_SHORT);
		} else {
			if (mTextId == textId) {
				return;
			}
			toast.cancel();
			toast = Toast.makeText(AwBaseApplication.getInstance(), textId, Toast.LENGTH_SHORT);
			mTextId = textId;
		}
		toast.show();
	}

}
