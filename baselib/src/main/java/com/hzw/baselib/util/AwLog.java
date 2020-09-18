package com.hzw.baselib.util;

import android.util.Log;

/**
 * 日志
 * Created by hzw on 2017/11/29.
 */

public class AwLog {
	public final static boolean Is_Loggable = true;
	private final static boolean Is_LogIn_File = true;
	private final static int Local_File_Size_Max = 10;// 单位MB
	private final static String tag = "aweiapp";
	private static String log_file_name = "aweiappLog.txt";

	static private void record(String s) {
//		if (!Is_LogIn_File)
//			return;
//		if (!FileUtil.sdCardIsAvailable()) {
//			LogUtil.e("SD卡不存在或无权限, 无法写入");
//			return;
//		}
//		try {
//			File mFile = new File(BaseConstant.PAHT_APP_DERECTORY);
//			if (mFile.canWrite()) {
//				mFile = new File(mFile, log_file_name);
//				if (mFile.exists()) {
//					if(FileUtil.getFileOrFilesSize(mFile.getAbsolutePath(), com.hzw.baselib.util.FileUtil.SIZETYPE_MB) > Local_File_Size_Max) {
//						FileUtil.deleteFile(mFile);
//					}
//				} else {
//					mFile.createNewFile();
//				}
//				FileWriter mFileWriter = new FileWriter(mFile, true);
//				BufferedWriter out = new BufferedWriter(mFileWriter);
//				SimpleDateFormat sdf = new SimpleDateFormat(
//						"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
//				Date date = new Date(System.currentTimeMillis());
//				out.write(sdf.format(date) + "---->");
//				out.write(s + "\n");
//				out.close();
//			}
//		} catch (IOException e) {
//			LogUtil.e("Could not write file " + e.getMessage());
//		}
	}

	public static void i(String msg) {
		if (null == msg)
			return;
		if (!Is_Loggable)
			return;
		Log.i(tag, msg);
		if (Is_LogIn_File)
			record(msg);
	}

	public static void withTime(String msg) {
		if (null == msg)
			return;
		i(msg + " Time: " + (int) Math.floor(System.currentTimeMillis() / 1000));
	}

	public static void d(String msg) {
		if (null == msg)
			return;
		if (!Is_Loggable)
			return;
		Log.d(tag, msg);
		record(msg);
	}

	public static void e(String msg) {
		if (null == msg)
			return;
		if (!Is_Loggable)
			return;
		Log.e(tag, msg);
		if (Is_LogIn_File)
			record(msg);
	}

	public static void v(String msg) {
		if (null == msg)
			return;
		if (!Is_Loggable)
			return;
		Log.v(tag, msg);
		if (Is_LogIn_File)
			record(msg);
	}

	public static void w(String msg) {
		if (null == msg)
			return;
		if (!Is_Loggable)
			return;
		Log.w(tag, msg);
		if (Is_LogIn_File)
			record(msg);
	}

	public static void i(String tag1, String msg) {
		if (null == msg)
			return;
		if (!Is_Loggable)
			return;
		Log.i(tag + tag1, msg);
		if (Is_LogIn_File)
			record(msg);
	}

	public static void d(String tag1, String msg) {
		if (null == msg)
			return;
		if (!Is_Loggable)
			return;
		Log.d(tag + tag1, msg);
		if (Is_LogIn_File)
			record(msg);
	}

	public static void e(String tag1, String msg) {
		if (null == msg)
			return;
		if (!Is_Loggable)
			return;
		Log.e(tag + tag1, msg);
		if (Is_LogIn_File)
			record(msg);
	}

	public static void v(String tag1, String msg) {
		if (null == msg)
			return;
		if (!Is_Loggable)
			return;
		Log.v(tag + tag1, msg);
		if (Is_LogIn_File)
			record(msg);
	}

	public static void w(String tag1, String msg) {
		if (null == msg)
			return;
		if (!Is_Loggable)
			return;
		Log.w(tag + tag1, msg);
		if (Is_LogIn_File)
			record(msg);
	}



}
