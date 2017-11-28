package com.fastaoe.gankio.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

/**
 * Created by jinjin on 17/11/15.
 * description:
 */

public class DateUtil {

    /**
     * 是否是同一天
     *
     * @param one
     * @param another
     * @return
     */
    public static boolean isTheSameDay(Date one, Date another) {
        Calendar _one = Calendar.getInstance();
        _one.setTime(one);
        Calendar _another = Calendar.getInstance();
        _another.setTime(another);
        int oneDay = _one.get(Calendar.DAY_OF_YEAR);
        int anotherDay = _another.get(Calendar.DAY_OF_YEAR);

        return oneDay == anotherDay;
    }

    // 获取当前时间,自定义format格式 yyyy-MM-dd HH:mm:ss
    public static String nowTimeDetail() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        //        LogUtil.d("DateUtils", format.format(calendar.getTime()));
        return format.format(calendar.getTime());
    }


    // 获取当前时间,自定义format格式 yyyy-MM-dd
    public static String nowTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        //        LogUtil.d("DateUtils", format.format(calendar.getTime()));
        return format.format(calendar.getTime());
    }

    public static int nowMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int nowYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 日期转换成字符串
     *
     * @param date
     * @return str
     */
    public static String DateToStr(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = format.format(date);
        return str;
    }

    /**
     * 获取指定月的第一天
     *
     * @param position 0为当月,正数为+,负数为减
     */
    public static String monthFirst(int position) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取当前日期
        Calendar calendar = Calendar.getInstance();
        //设置月
        calendar.add(Calendar.MONTH, position);
        //设置日
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        //        LogUtil.d("DateUtils", format.format(calendar.getTime()));
        return format.format(calendar.getTime());
    }

    /**
     * 获取指定月的最后一天
     *
     * @param position 0为当月,正数为+,负数为减
     */
    public static String monthLast(int position) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        //设置月
        calendar.add(Calendar.MONTH, position);
        //设置日
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        //        LogUtil.d("DateUtils", format.format(calendar.getTime()));
        return format.format(calendar.getTime());
    }

    /**
     * 获取指定时间与当前时间相差的月份
     *
     * @param date
     * @return
     */
    public static int monthGap(String date) {
        int nowMonth = nowMonth();
        int nowYear = nowYear();
        int dateMonth = getDateMonth(date);
        int dateYear = getDateYear(date);
        if (dateYear == nowYear) {
            // 在同一年份
            return dateMonth - nowMonth;
        } else if (dateYear > nowYear) {
            // 指定年份比当前年份大
            return (dateYear - nowYear - 1) * 12 + (12 - nowMonth) + dateMonth;
        } else {
            // 指定年份比当前年份小
            return (nowYear - dateYear - 1) * 12 + (12 - dateMonth) + nowMonth;
        }
    }

    public static int getDateMonth(String date) {
        long l = Long.parseLong(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(l));
        // 这里要注意，月份是从0开始。
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int getDateYear(String date) {
        long l = Long.parseLong(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(l));
        // 获取年
        return calendar.get(Calendar.YEAR);
    }

    public static int getDateYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 获取年
        return calendar.get(Calendar.YEAR);
    }

    public static int getDateMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 这里要注意，月份是从0开始。
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int getDateDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }
}
