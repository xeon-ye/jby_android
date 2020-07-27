package com.hzw.baselib.util;

import com.bin.david.form.core.TableConfig;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 正则表达式验证工具类
 * @Author: xiangqian
 * @CreateDate: 2020/4/24 18:09
 */

public class RegexUtil {

    /**
     * 判断字符串是否全是数字
     * @param str
     * @return
     */
    public static  boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");//这个也行
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     *  检测答题是否完全正确
     * @param rightAnswer  正确答案
     * @param customAnswer  作答
     * @return  0 完全正确   1  半对  2 错误
     */
    public static int isAllRight(String rightAnswer,String customAnswer){
        if(customAnswer.equalsIgnoreCase(rightAnswer)) {
            //全对
            return 0;
        } else {
            //非全是数字  并且长度不一样  为多选题  半对   橙色
            if(!RegexUtil.isNumeric(customAnswer)&&customAnswer.length()<rightAnswer.length()){
                char[] answerArr  = customAnswer.toCharArray();
                int flag = 1 ;
                for(int i=0;i<answerArr.length;i++)
                {
                    //标志，一旦有一个字符不在正确答案中 就变为 0,不得分
                    if(rightAnswer.indexOf(answerArr[i])==-1)
                    {
                        flag = 0;
                    }

                }
                if(flag==1){
                    //半对
                    return 1;
                }

            }
            //错误
            return 2;
        }
    }
    /**
     * 计算百分比
     * @param rate
     * @return
     */
    public static String calculatePercentage(String rate){
        if(AwDataUtil.isEmpty(rate)||"NaN".equals(rate)){
            return "0%";
        }
        DecimalFormat decimalFormat = new DecimalFormat("0%");
        return decimalFormat.format(Double.parseDouble(rate));
    }


    /**
     * 加密中间4位
     * @param phone
     * @return
     */
    public static String encryptionPhone(String phone){
      return   phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
    }
}
