/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dragon.util;

import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

/**
 *
 * @author ban
 */
public class DateHelper {

    public final static String REPORT_BEGIN_TIME = "12:00:00";
    public final static String REPORT_END_TIME = "11:59:59";

    public static String getReportBeginTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 12);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date now = new Date();
        if (now.getTime() < cal.getTime().getTime()) {//没有过十二点，开始时间从昨天12点开始
            now = DateUtils.add(now, Calendar.DAY_OF_MONTH, -1);
        } else {//已经过了12点，开始时间从今天12点开始

        }
        return DateFormatUtils.format(now, "yyyy-MM-dd") + " 12:00:00";
    }

    public static String getReportEndTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 12);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date now = new Date();
        if (now.getTime() < cal.getTime().getTime()) {//没有过十二点，结束时间到今天12点开始
        } else {//已经过了12点，结束时间到明天12点开始
            now = DateUtils.add(now, Calendar.DAY_OF_MONTH, 1);
        }
        return DateFormatUtils.format(now, "yyyy-MM-dd") + " 11:59:59";
    }

    public static String getReportBeginDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 12);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date now = new Date();
        if (now.getTime() < cal.getTime().getTime()) {//没有过十二点，开始时间从昨天12点开始
            now = DateUtils.add(now, Calendar.DAY_OF_MONTH, -1);
        } else {//已经过了12点，开始时间从今天12点开始

        }
        return DateFormatUtils.format(now, "yyyy-MM-dd");
    }

    public static String getReportEndDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 12);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date now = new Date();
        if (now.getTime() < cal.getTime().getTime()) {//没有过十二点，结束时间到今天12点开始
        } else {//已经过了12点，结束时间到明天12点开始
            now = DateUtils.add(now, Calendar.DAY_OF_MONTH, 1);
        }
        return DateFormatUtils.format(now, "yyyy-MM-dd");
    }

    public static String getYesBeginDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 12);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date now = new Date();
        if (now.getTime() < cal.getTime().getTime()) {//没有过十二点，开始时间从前天12点开始
            now = DateUtils.add(now, Calendar.DAY_OF_MONTH, -2);
        } else {//已经过了12点，开始时间从昨天12点开始
            now = DateUtils.add(now, Calendar.DAY_OF_MONTH, -1);
        }
        return DateFormatUtils.format(now, "yyyy-MM-dd");
    }

    public static String getYesEndDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 12);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date now = new Date();
        if (now.getTime() < cal.getTime().getTime()) {//没有过十二点，结束时间到昨天12点
            now = DateUtils.add(now, Calendar.DAY_OF_MONTH, -1);
        } else {//已经过了12点，结束时间到今天12点

        }
        return DateFormatUtils.format(now, "yyyy-MM-dd");
    }

}
