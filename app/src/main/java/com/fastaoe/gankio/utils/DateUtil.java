package com.fastaoe.gankio.utils;

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
}
