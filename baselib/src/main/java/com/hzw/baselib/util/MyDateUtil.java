package com.hzw.baselib.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzw.baselib.R;
import com.zzhoujay.richtext.RichText;

import java.util.Arrays;
import java.util.List;

import static android.provider.MediaStore.Video.Thumbnails.MINI_KIND;

/**
 * 项目数据处理util
 * Created by hzw on 2019/5/31.
 */

public class MyDateUtil {


    public static int stringToInt(String param) {
        if(AwDataUtil.isEmpty(param) || "0".equals(param))
            return 0;
        return Integer.parseInt(param);
    }

    /**
     * 获取批阅进度百分比(xx/xx格式)
     * @param progress
     * @return
     */
    public static String getMarkRote(String progress) {
        if(AwDataUtil.isEmpty(progress)) {
            return "0%";
        }
        String[] strs = progress.split("/");
        int preStr = Integer.parseInt(strs[0]);
        int latterStr = Integer.parseInt(strs[1]);
        float pressent = (float) preStr / latterStr * 100;//i 和 mNumber都是int型数
        if(pressent > 100) {
            pressent = 100;
        }
        return String.valueOf(AwConvertUtil.double2String(Double.parseDouble(String.valueOf(pressent)), 2) + "%");
    }

    /**
     * 获取批阅进度百分比(xx/xx格式)
     * @param progress
     * @return
     */
    public static boolean getMarkRoteLessThan50(String progress) {
        if(AwDataUtil.isEmpty(progress)) {
            return true;
        }
        String[] strs = progress.split("/");
        int preStr = Integer.parseInt(strs[0]);
        int latterStr = Integer.parseInt(strs[1]);
        float pressent = (float) preStr / latterStr * 100;//i 和 mNumber都是int型数
        return pressent < 50;
    }

    /**
     * 获取批阅进度(xx/xx格式)
     * @param progress
     * @return
     */
    public static String[] getMarkProgress(String progress) {
        if(AwDataUtil.isEmpty(progress)) {
            return new String[]{"0", "0"};
        }
        return progress.split("/");
    }

    /**
     * 获取批阅进度百分比(保留0位小数)
     * @param progress
     * @return
     */
    public static String getMarkRote(String progress, String total) {
        if("0".equals(progress) || "0".equals(total) || AwDataUtil.isEmpty(progress) || AwDataUtil.isEmpty(total)) {
            return "0%";
        }
        int preStr = Integer.parseInt(progress);
        int latterStr = Integer.parseInt(total);
        double pressent = (double) preStr / latterStr * 100;//i 和 mNumber都是int型数
        return AwConvertUtil.double2String(pressent, 0) + "%";
    }

    /**
     * 获取批阅进度百分比
     * @param progress
     * @return
     */
    public static boolean isMarkRoteFinish(String progress) {
        String[] strs = progress.split("/");
        if (strs.length > 0) {
            int preStr = Integer.parseInt(strs[0]);
            int latterStr = Integer.parseInt(strs[1]);
            return preStr == latterStr;
        } else {
            return false;
        }
    }

    /**
     * 获取名称, 如XXX.MP4, 则返回XXX
     * @param name
     * @return
     */
    public static String getFileName(String name) {
        if(AwDataUtil.isEmpty(name)) {
            return "";
        }
        if(name.contains(".")) {
            return name.substring(0, name.lastIndexOf("."));
        } else {
            return name;
        }
    }

    /**
     * 使用java正则表达式去掉多余的.与0
     * @param s
     * @return  string
     */
    public static String replace(String s){
        if(AwDataUtil.isEmpty(s)) {
            return "";
        }
        if(null != s && s.indexOf(".") > 0){
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

    public static boolean isChoiceQuestion(String typeName,String isOption) {
        if(AwDataUtil.isEmpty(typeName)) {
            return false;
        }
        if("选择题".equals(typeName) || "单选题".equals(typeName) || "多选题".equals(typeName) || "双选题".equals(typeName) ||"2".equals(isOption)||"完形填空".equals(typeName)) {
            return true;
        }
        return false;
    }

    public static boolean isChoiceQuestionByOptionParam(String option) {
        if(AwDataUtil.isEmpty(option)) {
            return false;
        }
        if("2".equals(option)) {
            return true;
        }
        return false;
    }

    /**
     * 获取视频缩略图
     * @param path
     * @return
     */
    public static Bitmap getThumbnailBitmap(String path) {
        return ThumbnailUtils.createVideoThumbnail(path, MINI_KIND);
    }

    /**
     * 设置缩略图
     * @param path
     * @return
     */
    public static void setThumbnailToImageView(String path, ImageView iv) {
        if(iv == null || AwDataUtil.isEmpty(path)) {
            return;
        }
        iv.setImageBitmap(getThumbnailBitmap(path));
    }

    /**
     * 设置顶部分数颜色样式 xxx:y 样式
     */
    public static void setScoreStyle(Context context, TextView tv, String str, int resId) {
        if(AwDataUtil.isEmpty(str) || tv == null){
            return;
        }
        SpannableString spannableString = new SpannableString(str);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(context.getResources().getColor(resId));
        spannableString.setSpan(colorSpan, str.indexOf("：") + 1, str.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        tv.setText(spannableString);
    }

    /**
     * 设置单选多选框选中与否样式
     * @param iv
     * @param isSelect
     */
    public static void setSelectImgStyle(ImageView iv, boolean isSelect) {
        if(iv == null)
            return;
        iv.setImageResource(isSelect ? R.mipmap.icon_select_yes : R.mipmap.icon_select_no);
    }

    public static String replaceMsqrtContent(String content) {
        if(!AwDataUtil.isEmpty(content)) {
            AwLog.d("content replaceAll前: " + content);
            if (content.contains("<msqrt>")) {
//                String math1 = "<math  xmlns=\"http://www.w3.org/1998/Math/MathML\"><msqrt><mn>";
//                String math2 = "<math  xmlns=\"http://www.w3.org/1998/Math/MathML\"><msqrt><mn>";
//                String math3 = "</mn></msqrt></math>";
//                String math4 = "<p>";
//                String math5 = "</p>";
                String math6 = "<msqrt>";
                String math7 = "</msqrt>";
                content = content.replace(math6, math6 + "根号√ (").replace(math7, ")" + math7);
//                content = content.replace(math6, math6 + "根号√").replace(math7, "" + math7);
//                content = content.replace("<p>", "").replace("</p>", "").replace(math1, "根号√").replace(math2, "");
                AwLog.d("content replaceAll后: " + content);
            }
        }
        return content;
    }

    /**
     * 获取表单填充颜色集合
     * @param context
     * @return
     */
    public static List<Integer> getChartColorsList(Context context) {
        return Arrays.asList(
                context.getResources().getColor(R.color.color_chart_004EB8), context.getResources().getColor(R.color.color_chart_00CECB),
                context.getResources().getColor(R.color.color_chart_58CB74), context.getResources().getColor(R.color.color_chart_FCD44B),
                context.getResources().getColor(R.color.color_chart_FF5564), context.getResources().getColor(R.color.color_chart_0093FF),
                context.getResources().getColor(R.color.color_chart_F4667C), context.getResources().getColor(R.color.color_chart_FFED75),
                context.getResources().getColor(R.color.color_chart_FA7528), context.getResources().getColor(R.color.color_chart_990033),
                context.getResources().getColor(R.color.color_chart_9020C6), context.getResources().getColor(R.color.color_chart_000033),
                context.getResources().getColor(R.color.color_chart_663366), context.getResources().getColor(R.color.color_chart_663300),
                context.getResources().getColor(R.color.color_chart_0072CD), context.getResources().getColor(R.color.color_chart_FCCE06),
                context.getResources().getColor(R.color.color_chart_FF0000), context.getResources().getColor(R.color.color_chart_669900)
        );
    }

    /**
     * 获取表单填充颜色集合
     * @param context
     * @return
     */
    public static List<Integer> getChartColorsList2(Context context) {
        return Arrays.asList(
                context.getResources().getColor(R.color.color_40A0FF), context.getResources().getColor(R.color.color_chart_F19F10),
                context.getResources().getColor(R.color.color_chart_0072CD), context.getResources().getColor(R.color.color_chart_00CECB),
                context.getResources().getColor(R.color.color_chart_58CB74), context.getResources().getColor(R.color.color_chart_FCD44B),
                context.getResources().getColor(R.color.color_chart_F4667C), context.getResources().getColor(R.color.color_chart_FFED75),
                context.getResources().getColor(R.color.color_chart_FA7528), context.getResources().getColor(R.color.color_chart_990033),
                context.getResources().getColor(R.color.color_chart_9020C6), context.getResources().getColor(R.color.color_chart_000033),
                context.getResources().getColor(R.color.color_chart_663366), context.getResources().getColor(R.color.color_chart_663300),
                context.getResources().getColor(R.color.color_chart_FF5564), context.getResources().getColor(R.color.color_chart_004EB8),
                context.getResources().getColor(R.color.color_chart_FF0000), context.getResources().getColor(R.color.color_chart_669900)
        );
    }

    /**
     * 获取表单填充颜色集合
     * @param context
     * @return
     */
    public static List<Integer> getChartLineColorsList(Context context) {
        return Arrays.asList(
                context.getResources().getColor(R.color.color_chart_63C234), context.getResources().getColor(R.color.color_chart_F47949),
                context.getResources().getColor(R.color.color_chart_0072CD), context.getResources().getColor(R.color.color_chart_00CECB),
                context.getResources().getColor(R.color.color_chart_58CB74), context.getResources().getColor(R.color.color_chart_FCD44B),
                context.getResources().getColor(R.color.color_chart_F4667C), context.getResources().getColor(R.color.color_chart_FFED75),
                context.getResources().getColor(R.color.color_chart_FA7528), context.getResources().getColor(R.color.color_chart_990033),
                context.getResources().getColor(R.color.color_chart_9020C6), context.getResources().getColor(R.color.color_chart_000033),
                context.getResources().getColor(R.color.color_chart_663366), context.getResources().getColor(R.color.color_chart_663300),
                context.getResources().getColor(R.color.color_chart_FF5564), context.getResources().getColor(R.color.color_chart_004EB8),
                context.getResources().getColor(R.color.color_chart_FF0000), context.getResources().getColor(R.color.color_chart_669900)
        );
    }

    /**
     * 获取表单填充颜色数组
     * @param context
     * @return
     */
    public static int[] getChartColorsArray(Context context) {
        return new int[] {
                context.getResources().getColor(R.color.color_chart_004EB8), context.getResources().getColor(R.color.color_chart_00CECB),
                context.getResources().getColor(R.color.color_chart_0072CD), context.getResources().getColor(R.color.color_chart_0093FF),
                context.getResources().getColor(R.color.color_chart_58CB74), context.getResources().getColor(R.color.color_chart_FCD44B),
                context.getResources().getColor(R.color.color_chart_F4667C), context.getResources().getColor(R.color.color_chart_FFED75),
                context.getResources().getColor(R.color.color_chart_FA7528), context.getResources().getColor(R.color.color_chart_990033),
                context.getResources().getColor(R.color.color_chart_9020C6), context.getResources().getColor(R.color.color_chart_000033),
                context.getResources().getColor(R.color.color_chart_663366), context.getResources().getColor(R.color.color_chart_663300),
                context.getResources().getColor(R.color.color_chart_FF5564), context.getResources().getColor(R.color.color_chart_FCCE06),
                context.getResources().getColor(R.color.color_chart_FF0000), context.getResources().getColor(R.color.color_chart_669900)
        };
    }

    /**
     * 获取表单填充颜色数组(作业详情)
     * @param context
     * @return
     */
    public static int[] getChartColorsArrayHomework(Context context) {
        return new int[] {
                context.getResources().getColor(R.color.color_chart_669900), context.getResources().getColor(R.color.color_chart_0072CD),
                context.getResources().getColor(R.color.color_chart_00CECB), context.getResources().getColor(R.color.color_chart_FCD44B),
                context.getResources().getColor(R.color.color_chart_FF0000), context.getResources().getColor(R.color.color_chart_9020C6)
        };
    }

    /**
     * 标准答案移动到第一行
     * @param list
     * @param <E>
     * @return
     */
    public static  <E> List<E> exchangeList(List<E> list) {
        //list.add(0,list.get(list.size()-1));
        list.remove(list.size()-1);
    /*    for (int i = 1; i < list.size() - 1; i++) {
            list.set(i, list.get(i - 1));
        }
        list.set(0, list.get(list.size() - 1));*/
        return list;
    }
}
