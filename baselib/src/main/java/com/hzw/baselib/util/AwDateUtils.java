package com.hzw.baselib.util;

import android.text.TextUtils;
import android.text.format.DateUtils;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by wangdawu on 15/10/29.
 */
public class AwDateUtils {
    public static SimpleDateFormat formatDate = new SimpleDateFormat("yyyy年M月d日");
    public static SimpleDateFormat formatYearMonth = new SimpleDateFormat("yyyy年M月");
    public static SimpleDateFormat formatISODate = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat formatDay = new SimpleDateFormat("d");
    public static SimpleDateFormat formatWeekday = new SimpleDateFormat("EEE");
    public static SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
    public static SimpleDateFormat formatISODateTime = new SimpleDateFormat("yyyy-MM-dd HH_mm_ss");
    public static SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");
    public static SimpleDateFormat formatDateTime = new SimpleDateFormat("yyyy年M月d日 HH:mm");
    public static SimpleDateFormat formatLongTime = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
    public static SimpleDateFormat formatDateDay = new SimpleDateFormat("dd");
    public static SimpleDateFormat formatClip = new SimpleDateFormat("EEEE HH:mm");
    public static SimpleDateFormat formatDateDay1 = new SimpleDateFormat("d日");
    public static SimpleDateFormat formatDate1 = new SimpleDateFormat("yyyy.M.d");
    public static SimpleDateFormat formatDate2 = new SimpleDateFormat("M月d日");
    public static SimpleDateFormat formatDate3 = new SimpleDateFormat("EEEE");
    public static SimpleDateFormat formatDate4 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static SimpleDateFormat formatDate5 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
    public static SimpleDateFormat formatDate6 = new SimpleDateFormat("yyyy年M月d日 EEE HH:mm");
    public static SimpleDateFormat formatDate7 = new SimpleDateFormat("M月d日 EEE");
    public static SimpleDateFormat formatDate8 = new SimpleDateFormat("yyyy年M月d日 EEE");
    public static SimpleDateFormat formatDate9 = new SimpleDateFormat("M月d日 HH:mm");
    public static SimpleDateFormat formatDate10 = new SimpleDateFormat("M月");
    public static SimpleDateFormat formatDate11 = new SimpleDateFormat("yyyy年M月d日 HH:mm:ss");
    public static SimpleDateFormat formatDate12 = new SimpleDateFormat("yyyy年M月d日 HH:mm");
    public static SimpleDateFormat formatDate13 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat formatDate14 = new SimpleDateFormat("MM-dd");
    public static SimpleDateFormat formatDate15 = new SimpleDateFormat("MM月dd日");
    public static SimpleDateFormat formatDate16 = new SimpleDateFormat("yy/M/dd HH:mm");
    public static SimpleDateFormat formatDate17 = new SimpleDateFormat("yyyy-M-dd");
    public static SimpleDateFormat formatTime2 = new SimpleDateFormat("HH");
    public static SimpleDateFormat formatData18 = new SimpleDateFormat("mm:ss");
    public static SimpleDateFormat formatData19 = new SimpleDateFormat("ss");
    public static SimpleDateFormat formatData20 = new SimpleDateFormat("HH时mm分ss秒");
    public static SimpleDateFormat formatData21 = new SimpleDateFormat("mm分ss秒");
    public static SimpleDateFormat formatData22 = new SimpleDateFormat("MM月dd日HH时mm分");

    public static String getWeekString(Calendar date) {
        int index = date.get(Calendar.DAY_OF_WEEK);
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.setTime(date.getTime());
        end.setTime(date.getTime());
        start.add(Calendar.DATE, 1 - index);
        end.add(Calendar.DATE, 7 - index);
        return formatDateDay.format(start.getTime()) + "-" + formatDateDay.format(end.getTime());
    }

    public static String getWeekString1(Calendar date) {
        int index = date.get(Calendar.DAY_OF_WEEK);
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.setTime(date.getTime());
        end.setTime(date.getTime());
        start.add(Calendar.DATE, 1 - index);
        end.add(Calendar.DATE, 7 - index);
        return (formatDate.format(start.getTime())).replace("日", "") + "-" + (formatDate.format(end.getTime()).substring(8));
    }

    /**
     * https://blog.csdn.net/u011084603/article/details/72829790
     * 获取前n天日期、后n天日期
     *
     * @param distanceDay 前几天 如获取前7天日期则传-7即可；如果后7天则传7
     * @return
     */
    public static String getOldDate(int distanceDay) {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) + distanceDay);
        Date endDate = null;
        try {
            endDate = dft.parse(dft.format(date.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        AwLog.d("前7天==" + dft.format(endDate));
        return dft.format(endDate);
    }

    public static Date getOldDate(String date) {
        try {
            return formatDate17.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    /**
     * 获取当前周的第一天
     *
     * @param date 需要获取的时间
     * @return 返回：xxxx年xx月xx日
     */
    public static String getWeekFirst(Calendar date) {
        date.set(Calendar.DAY_OF_WEEK, 1);
        return formatDate.format(date.getTime());
    }

    /**
     * 获取当前周的最后一天
     *
     * @param date 需要获取的时间
     * @return 返回：xxxx年xx月xx日
     */
    public static String getWeekLast(Calendar date) {
        date.set(Calendar.DAY_OF_WEEK, date.getActualMaximum(Calendar.DAY_OF_WEEK));
        return formatDate.format(date.getTime());
    }

    /**
     * 获取当前周
     *
     * @param date 当前时间
     * @return xxxx年xx月xx日-xx日
     */
    public static String getWeekString2(Calendar date) {
        int index = date.get(Calendar.DAY_OF_WEEK);
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.setTime(date.getTime());
        end.setTime(date.getTime());
        start.add(Calendar.DATE, 1 - index);
        end.add(Calendar.DATE, 7 - index);
        return formatDate.format(start.getTime()) + "-" + formatDateDay1.format(end.getTime());
    }

    public static Calendar getSelectCalendar(int mPageNumber) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        if (mPageNumber != 500) {
            int len = (mPageNumber - 500) * 7;
            calendar.add(Calendar.DATE, len);
        }
        return calendar;
    }

    public static int getSelectWeekPageNumber(Date date) {
        Calendar calendar = Calendar.getInstance();
        int index = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DATE, 1 - index);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        if (date.getDay() == 0 || date.after(new Date()))
            return 500 + (int) ((date.getTime() - calendar.getTimeInMillis()) / (7 * 24 * 60 * 60 * 1000));
        else
            return 500 + (int) ((date.getTime() - calendar.getTimeInMillis()) / (7 * 24 * 60 * 60 * 1000));
    }

    public static int getSelectMonthPageNumber(Date date) {
        Calendar calendar = Calendar.getInstance();
        Calendar selectCalendar = Calendar.getInstance();
        selectCalendar.setTime(date);

        int year = calendar.get(Calendar.YEAR);
        int selectYear = selectCalendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int selectMonth = selectCalendar.get(Calendar.MONTH);

        return 500 + (selectYear - year) * 12 - month + selectMonth;
    }

    public static Calendar getSelectCalendarDay(int mPageNumber) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        if (mPageNumber != 500) {
            int len = (mPageNumber - 500) * 1;
            calendar.add(Calendar.DATE, len);
        }
        return calendar;
    }

    public static Calendar getSelectCalendarMonth(int mPageNumber) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        if (mPageNumber != 500) {
            int len = (mPageNumber - 500) * 1;
            calendar.add(Calendar.MONTH, len);
        }
        return calendar;
    }

    public static boolean isToday(Calendar mDate) {
        if (mDate == null) {
            return false;
        } else {
            Calendar today = Calendar.getInstance();
            return ((mDate.get(Calendar.YEAR) == today.get(Calendar.YEAR)) &&
                    (mDate.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)));
        }
    }

    public static boolean isToday(Date date) {
        if (date == null) {
            return false;
        }
        Date currentDate = new Date();
        String dateStr = formatDate13.format(date);
        String dateStrCurrent = formatDate13.format(currentDate);
        if (dateStr != null && dateStrCurrent != null && dateStr.equals(dateStrCurrent))
            return true;
        return false;
    }

    public static boolean isToday(String dateStr) {
        if (dateStr == null) {
            return false;
        }
        Date currentDate = new Date();
        try {
            dateStr = formatISODate.format(formatISODate.parse(dateStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dateStrCurrent = formatISODate.format(currentDate);
        if (dateStr != null && dateStrCurrent != null && dateStr.equals(dateStrCurrent))
            return true;
        return false;
    }

    public static int getDayCount(Date startDate, Date endDate) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(calendar1.getTime());
        calendar1.set(startDate.getYear(), startDate.getMonth(), startDate.getDate());
        calendar2.set(endDate.getYear(), endDate.getMonth(), endDate.getDate());
        int count = 1;
        while (calendar1.before(calendar2)) {
            calendar1.add(Calendar.DAY_OF_MONTH, 1);
            count++;
        }
        return count;
    }

    public static String getDateHourString() {
        Date d = new Date();
        if (d.getHours() < 6) {
            return "凌晨好";
        } else if (d.getHours() < 8) {
            return "早晨好";
        } else if (d.getHours() < 11) {
            return "上午好";
        } else if (d.getHours() < 13) {
            return "中午好";
        } else if (d.getHours() < 18) {
            return "下午好";
        } else if (d.getHours() < 24) {
            return "晚上好";
        }
        return "";
    }

    public static boolean isAfterFirstDateParams(String dateStr1, String dateStr2) {
        AwLog.d("时间比较前后isAfterFirstDateParams参数1: " + dateStr1 + " ,参数2: " + dateStr2);
        if (TextUtils.isEmpty(dateStr1) || TextUtils.isEmpty(dateStr2))
            return true;
        if (dateStr1.equals(dateStr2)) {
            return true;
        }
        Date date1 = getDateISODate(dateStr1);
        Date date2 = getDateISODate(dateStr2);
        if (date2.getTime() >= date1.getTime())
            return true;
        return false;
    }

    /**
     * 日期转换 --- 获取 yyyy年M月d日
     *
     * @param params
     * @return
     */
    public static Date getDateISODate(String params) {
        if (params == null)
            return null;
        try {
            return formatISODate.parse(params);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * https://www.cnblogs.com/zhaoyan001/p/6370883.html
     * 日期格式转换yyyy-MM-dd‘T‘HH:mm:ss.SSSXXX  TO  yyyy-MM-dd HH:mm:ss
     *
     * @throws ParseException
     */
    public static String dealDateFormat(String oldDateStr) {
        //此格式只有  jdk 1.7才支持  yyyy-MM-dd‘T‘HH:mm:ss.SSSXXX
        //这个后面的.SSSXXX写了的话这一行就直接抛异常了，所以我去掉了，还有前面的T  一定要用英文的单引号包裹起来
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = df.parse(oldDateStr);
            SimpleDateFormat df1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
            Date date1 = df1.parse(date.toString());
            return formatDate16.format(date1);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    /**
     * java如何处理2016-09-03T00:00:00.000+08:00时间格式
     * https://blog.csdn.net/weixin_38538232/article/details/84877574
     * @param oldDateStr
     * @return
     * @throws ParseException
     */
    public static String dealDate(String oldDateStr) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = df.parse(oldDateStr);
        SimpleDateFormat df1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
        Date date1 =  df1.parse(date.toString());
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df2.format(date1);
    }

    /**
     * 00:00
     *
     * @param time
     * @return
     */
    public static String getmmssDateFormat(long time) {
        String format = formatData18.format(new Date(time));
        return format;
    }

    public static String getssDateFormat(long time) {
        String format = formatData19.format(new Date(time));
        return format;
    }

    public static String getHHmmss(long time) {
        return formatData20.format(new Date(time));
    }

    public static String getMMddHHmm(long time) {
        return formatData22.format(new Date(time));
    }

    public static String getmmss(long time) {
        return formatData21.format(new Date(time));
    }

    public static String getDate(int date) {
        if (date < 60) {
            return date + "秒";
        } else if (date > 60 && date < 3600) {
            int m = date / 60;
            int s = date % 60;
            return m + "分" + s + "秒";
        } else {
            int h = date / 3600;
            int m = (date % 3600) / 60;
            int s = (date % 3600) % 60;
            return h + "小时" + m + "分" + s + "秒";
        }
    }

    public static String getyyyyMMddHHmmss(long time) {
        if (0 == time || AwDataUtil.isEmpty(time)) {
            return "";
        }
        return formatDate11.format(new Date(time));
    }



}
