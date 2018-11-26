package com.jadmin.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * @Title:web框架
 * @Description:时间工具类
 * @Copyright:JAdmin (c) 2018年09月17日
 * @author:-jiujiya
 * @version:1.0
 */
public class DateTimeUtil {

    public static final long SECOND = 1000;

    public static final long MINUTE = 60 * SECOND;

    public static final long HOUR = 60 * MINUTE;

    public static final long DAY = 24 * HOUR;

    public static final long WEEK = 7 * DAY;

    public static final String SHORTFORMAT = "yyyy-MM-dd";

    public static final String SHORTFORMATNOSPIT = "yyyyMMdd";

    public static final String YEARMONTHFORMAT = "yyyy-MM";

    public static final String LONGFORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String TIMEDIVISION = "HH:mm";

    /**
     * 获得yymmdd类型的日期
     *
     * @param date
     * @return
     */
    @SuppressWarnings("deprecation")
    public static String getDateString(Date date) {
        String year = (date.getYear() + 1900) + "";
        String mm = (date.getMonth() + 1) + "";
        if (Integer.valueOf(mm).intValue() < 10) {
            mm = "0" + mm;
        }
        String day = date.getDate() + "";
        return year + mm + day;
    }

    /**
     * 获得yy-mm-dd类型的日期
     *
     * @param date
     * @return
     */
    @SuppressWarnings("deprecation")
    public static String DateString(Date date) {
        String year = (date.getYear() + 1900) + "";
        String mm = (date.getMonth() + 1) + "";
        if (Integer.valueOf(mm).intValue() < 10) {
            mm = "0" + mm;
        }
        String day = date.getDate() + "";
        if (day.length() == 1) {
            day = "0" + day;
        }
        return year + "-" + mm + "-" + day;
    }

    // 得到某一天是星期几
    public static int getDateInWeek(String strDate) {
        DateFormat df = DateFormat.getDateInstance();
        try {
            df.parse(strDate);
            java.util.Calendar c = df.getCalendar();
            int day = c.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
            return day;
        } catch (ParseException e) {
            return -1;
        }
    }

    // 得到当前日期
    public static String getCurrentDate() {
        java.text.SimpleDateFormat d = new java.text.SimpleDateFormat();
        d.applyPattern(SHORTFORMAT);
        java.util.Date nowdate = new java.util.Date();
        String str_date = d.format(nowdate);
        return str_date;
    }

    // 得到当前年份
    public static String getYear() {
        return new SimpleDateFormat("yyyy").format(new Date());
    }

    // 得到当前日期时间
    public static String getCurrentDateTime() {
        java.text.SimpleDateFormat d = new java.text.SimpleDateFormat();
        d.applyPattern("yyyy-MM-dd HH:mm:ss");
        java.util.Date nowdate = new java.util.Date();
        String str_date = d.format(nowdate);
        return str_date;

    }

    // 根据日期获得日期时间
    public static String getDateTime(java.util.Date date) {
        java.text.SimpleDateFormat d = new java.text.SimpleDateFormat();
        d.applyPattern("yyyy-MM-dd HH:mm:ss");
        String str_date = d.format(date);
        return str_date;
    }

    // 获得某月的最后一天
    public static int getDayNum(int year, int month) {
        if (month == 2) {
            return year % 400 != 0 && (year % 4 != 0 || year % 100 == 0) ? 28
                    : 29;
        }
        String SmallMonth = ",4,6,9,11,";
        return SmallMonth.indexOf(String.valueOf(String
                .valueOf((new StringBuffer(",")).append(String.valueOf(month))
                        .append(",")))) < 0 ? 31 : 30;
    }

    // 返回两个日期之间隔了多少天

    public static int DateDiff(Date date1, Date date2) {
        int i = (int) ((date1.getTime() - date2.getTime()) / 3600 / 24 / 1000);
        return i;
    }

    // 从放有日期的字符串中得到，相应的年，月，日 style is "Y"or"y" 返回年 style is "M"or"m" 返回月 style
    // is "D"or"d" 返回日 日期字符串要求 "YYYY-MM-DD"
    public static int getYearMonthDate(String strDate, String style) {
        int year;
        int month;
        int day;
        int firstDash;
        int secondDash;
        if (strDate == null) {
            return 0;
        }
        firstDash = strDate.indexOf('-');
        secondDash = strDate.indexOf('-', firstDash + 1);
        if ((firstDash > 0) & (secondDash > 0)
                & (secondDash < strDate.length() - 1)) {
            year = Integer.parseInt(strDate.substring(0, firstDash));
            month = Integer.parseInt(strDate.substring(firstDash + 1,
                    secondDash));
            day = Integer.parseInt(strDate.substring(secondDash + 1));
        } else {
            return 0;
        }
        if (style.equalsIgnoreCase("Y")) {
            return year;
        } else if (style.equalsIgnoreCase("M")) {
            return month;
        } else if (style.equalsIgnoreCase("D")) {
            return day;
        } else {
            return 0;
        }
    }

    // 某一天，过几天后是几号

    @SuppressWarnings("static-access")
    public static java.sql.Date DateAdd(java.sql.Date date, int addday) {
        java.sql.Date datenew = null;
        int year = DateTimeUtil.getYearMonthDate(date.toString(), "Y");
        int month = DateTimeUtil.getYearMonthDate(date.toString(), "M");
        int day = DateTimeUtil.getYearMonthDate(date.toString(), "D");
        day = day + addday;
        String dayStr = Integer.toString(year) + "-" + Integer.toString(month)
                + "-" + Integer.toString(day);
        datenew = datenew.valueOf(dayStr);
        // datenew.setTime(datenew.getTime()+day*3600*24*1000);
        // 有问题。 改
        return datenew;

    }

    // 某一天的前几天是几号

    @SuppressWarnings("static-access")
    public static java.sql.Date DateBefore(java.sql.Date date, int addday) {
        java.sql.Date datenew = null;
        int year = DateTimeUtil.getYearMonthDate(date.toString(), "Y");
        int month = DateTimeUtil.getYearMonthDate(date.toString(), "M");
        int day = DateTimeUtil.getYearMonthDate(date.toString(), "D");
        day = day - addday;
        String dayStr = Integer.toString(year) + "-" + Integer.toString(month)
                + "-" + Integer.toString(day);
        datenew = datenew.valueOf(dayStr);
        // datenew.setTime(datenew.getTime()+day*3600*24*1000);
        // 有问题。 改
        return datenew;
    }

    // 某一天是否是年的头一天

    public static boolean isYearFirstDay(java.sql.Date date) {
        boolean i = false;
        if ((DateTimeUtil.getYearMonthDate(date.toString(), "M") == 1)
                && (DateTimeUtil.getYearMonthDate(date.toString(), "D") == 1)) {
            i = true;
        }
        return i;
    }

    // 某一天是否是半年的头一天

    public static boolean isHalfYearFirstDay(java.sql.Date date) {
        boolean i = false;
        if (((DateTimeUtil.getYearMonthDate(date.toString(), "M") == 1) && (DateTimeUtil
                .getYearMonthDate(date.toString(), "D") == 1))
                || ((DateTimeUtil.getYearMonthDate(date.toString(), "M") == 7) && (DateTimeUtil
                .getYearMonthDate(date.toString(), "D") == 1))) {
            i = true;
        }
        return i;
    }

    public static String getHalfYearFirstDay(java.sql.Date date) {
        String month = "01";
        if (DateTimeUtil.getYearMonthDate(date.toString(), "M") >= 7) {
            month = "07";
        }
        String day = Integer.toString(DateTimeUtil.getYearMonthDate(
                date.toString(), "Y")) + "-" + month + "-01";
        return day;
    }

    // 某一天是否是半年的最后一天

    public static boolean isHalfYearLastDay(java.sql.Date date) {
        boolean i = false;
        if (((DateTimeUtil.getYearMonthDate(date.toString(), "M") == 12) && (DateTimeUtil
                .getYearMonthDate(date.toString(), "D") == 31))
                || ((DateTimeUtil.getYearMonthDate(date.toString(), "M") == 6) && (DateTimeUtil
                .getYearMonthDate(date.toString(), "D") == 30))) {
            i = true;
        }
        return i;
    }

    public static String getHalfYearLastDay(java.sql.Date date) {
        String month = "-06-30";
        if (DateTimeUtil.getYearMonthDate(date.toString(), "M") >= 7) {
            month = "-12-31";
        }
        String day = Integer.toString(getYearMonthDate(date.toString(), "Y"))
                + "-" + month;
        return day;
    }

    /**
     * 获得加过月份的日期
     *
     * @return
     * @throws ParseException
     */
    public static String getDateByAddMonth(String date, int Month) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        if (StringUtils.isNotBlank(date)) {
            try {
                cal.setTime(sdf.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        cal.add(Calendar.MONTH, Month);
        return sdf.format(cal.getTime());
    }

    /**
     * 获得前途日期
     *
     * @return
     */
    public static String getYesterdayYesterday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -2);
        String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal
                .getTime());
        return yesterday;
    }

    /**
     * 获得以前的日期
     *
     * @return
     */
    public static String getYesterday(int today) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -today);
        String day = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        return day;
    }

    /**
     * @return
     */
    public static String getLastMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        String day = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        return day;
    }

    /**
     * 获取今天是星期几<br>
     *
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate() {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 获得昨天日期
     *
     * @return
     */
    public static String getYesterday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal
                .getTime());
        return yesterday;
    }

    // 某一天是否是年的最后一天

    public static boolean isYearLastDay(java.sql.Date date) {
        boolean i = false;
        if ((DateTimeUtil.getYearMonthDate(date.toString(), "M") == 12)
                && (DateTimeUtil.getYearMonthDate(date.toString(), "D") == 31)) {
            i = true;
        }
        return i;
    }

    // 某一天是否是季的头一天

    public static boolean isQuarterFirstDay(java.sql.Date date) {
        boolean i = false;
        if (((DateTimeUtil.getYearMonthDate(date.toString(), "M") == 1)
                || (DateTimeUtil.getYearMonthDate(date.toString(), "M") == 4)
                || (DateTimeUtil.getYearMonthDate(date.toString(), "M") == 7) || (DateTimeUtil
                .getYearMonthDate(date.toString(), "M") == 10))
                && (DateTimeUtil.getYearMonthDate(date.toString(), "D") == 1)) {
            i = true;
        }
        return i;
    }

    public static String getQuarterFirstDay(java.sql.Date date) {
        String month = "01";
        if (DateTimeUtil.getYearMonthDate(date.toString(), "M") >= 10) {
            month = "10";
        } else if (DateTimeUtil.getYearMonthDate(date.toString(), "M") >= 7) {
            month = "07";
        } else if (DateTimeUtil.getYearMonthDate(date.toString(), "M") >= 4) {
            month = "04";
        } else if (DateTimeUtil.getYearMonthDate(date.toString(), "M") >= 1) {
            month = "01";
        }

        String day = Integer.toString(DateTimeUtil.getYearMonthDate(
                date.toString(), "Y")) + "-" + month + "-01";
        return day;
    }

    // 某一天是否是季的最后一天

    public static boolean isQuarterLastDay(java.sql.Date date) {
        boolean i = false;
        if ((DateTimeUtil.getYearMonthDate(date.toString(), "M") == 3)
                && (DateTimeUtil.getYearMonthDate(date.toString(), "D") == 31)) {
            i = true;
        }
        if ((DateTimeUtil.getYearMonthDate(date.toString(), "M") == 6)
                && (DateTimeUtil.getYearMonthDate(date.toString(), "D") == 30)) {
            i = true;
        }
        if ((DateTimeUtil.getYearMonthDate(date.toString(), "M") == 9)
                && (DateTimeUtil.getYearMonthDate(date.toString(), "D") == 30)) {
            i = true;
        }
        if ((DateTimeUtil.getYearMonthDate(date.toString(), "M") == 12)
                && (DateTimeUtil.getYearMonthDate(date.toString(), "D") == 31)) {
            i = true;
        }
        return i;
    }
    
	/**
	 * 现在日期减去month个月份
	 * @return
	 */
	public static String getLastMonthTime(int month){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -month);
		String tims = new SimpleDateFormat(LONGFORMAT).format(cal.getTime());
		return tims;
	}
    
	/**
	 * 获得去年日期
	 * @return
	 */
	public static String getLastYearTime(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -1);
		String tims = new SimpleDateFormat(LONGFORMAT).format(cal.getTime());
		return tims;
	}

    public static String getQuarterLastDay(java.sql.Date date) {
        String month = "-01-31";
        if (DateTimeUtil.getYearMonthDate(date.toString(), "M") >= 10) {
            month = "-12-31";
        } else if (DateTimeUtil.getYearMonthDate(date.toString(), "M") >= 7) {
            month = "-09-30";
        } else if (DateTimeUtil.getYearMonthDate(date.toString(), "M") >= 4) {
            month = "-06-30";
        }

        String day = Integer.toString(DateTimeUtil.getYearMonthDate(
                date.toString(), "Y")) + "-" + month;
        return day;
    }

    // 某一天是否是月的最后一天

    @SuppressWarnings("static-access")
    public static boolean isMonthLastDay(java.sql.Date date) {
        boolean i = false;
        java.sql.Date des_date = null;
        String month;
        String str_date = date.toString();
        String year = str_date.substring(0, str_date.indexOf("-"));
        int m = new Integer(str_date.substring(str_date.indexOf("-") + 1,
                str_date.lastIndexOf("-"))).intValue() + 1;
        month = new Integer(m).toString();
        if (m < 10) {
            month = "0" + month;
        }
        java.sql.Date mid_date = null;
        mid_date = mid_date.valueOf(year + "-" + month + "-01");
        des_date = DateTimeUtil.DateAdd(mid_date, -1);
        if (DateTimeUtil.DateDiff(des_date, date) == 0) {
            i = true;
        }
        return i;
    }

    // 某一天是否是月的第一天

    public static boolean isMonthFisrtDay(java.sql.Date date) {
        boolean i = false;
        if ((DateTimeUtil.getYearMonthDate(date.toString(), "D") == 1)) {
            i = true;
        }
        return i;
    }

    // 获得月的第一天
    public static String getMonthFisrtDay(java.sql.Date date) {
        String month;
        if (DateTimeUtil.getYearMonthDate(date.toString(), "M") > 9) {
            month = Integer.toString(DateTimeUtil.getYearMonthDate(date.toString(),
                    "M"));
        } else {
            month = "0"
                    + Integer.toString(DateTimeUtil.getYearMonthDate(
                    date.toString(), "M"));
        }
        String day = Integer.toString(DateTimeUtil.getYearMonthDate(
                date.toString(), "Y")) + "-" + month + "-01";
        return day;
    }

    public static java.sql.Timestamp getTimestamp() {
        try {
            java.text.SimpleDateFormat myFormat = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            String mystrdate = myFormat.format(calendar.getTime());
            return java.sql.Timestamp.valueOf(mystrdate);
        } catch (Exception e) {
            return null;
        }
    }

    public static java.sql.Timestamp getTimestamp(String datestr) {
        try {
            java.text.SimpleDateFormat myFormat = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            String mystrdate = myFormat.format(myFormat.parse(datestr));
            return java.sql.Timestamp.valueOf(mystrdate);
        } catch (Exception e) {
            return null;
        }
    }

    // 格式化日期（Y-年，M-月,D-日 HH:mm:ss 小时：分钟：秒）
    public static String getDate(java.util.Date date, String format) {
        String result = null;
        try {
            java.text.SimpleDateFormat myFormat = new SimpleDateFormat(format);
            result = myFormat.format(date);
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public static String getDate(java.util.Date date) {
        return getDate(date, LONGFORMAT);
    }

    // 转换成时间戳
    public static java.sql.Timestamp getDate(String datestr) {
        try {
            java.text.SimpleDateFormat myFormat = new SimpleDateFormat(
                    "yyyy-MM-dd");
            Date date = myFormat.parse(datestr);
            myFormat.applyLocalizedPattern("yyyy-MM-dd HH:mm:ss");
            String mystrdate = myFormat.format(date);
            return java.sql.Timestamp.valueOf(mystrdate);
        } catch (Exception e) {
            return null;
        }
    }

    // 将日期格式化成yyyy-MM-dd形式
    @SuppressWarnings("deprecation")
    public static java.util.Date format(String datestr) {
        try {
            java.text.SimpleDateFormat myFormat = new SimpleDateFormat(
                    "yyyy-MM-dd");
            Date date = myFormat.parse(datestr);
            return date;
        } catch (Exception e) {
            return new Date(datestr);
        }
    }

    // 格式化日期（Y-年，M-月,D-日 HH:mm:ss 小时：分钟：秒）
    public static java.util.Date format(String datestr, String format) {
        try {
            java.text.SimpleDateFormat myFormat = new SimpleDateFormat(format);
            Date date = myFormat.parse(datestr);
            return date;
        } catch (Exception e) {
            return null;
        }
    }

    // 获得数据库格式的日期
    public static java.sql.Date getSqlDate(java.util.Date date) {
        java.sql.Date result = null;
        try {
            java.text.SimpleDateFormat myFormat = new SimpleDateFormat(
                    "yyyy-MM-dd");
            String mystrdate = myFormat.format(date);
            result = java.sql.Date.valueOf(mystrdate);
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public static java.util.Date addMonth(java.util.Date date, int month) {
        String strdate = getDate(date, SHORTFORMAT);
        int curmonth = Integer.parseInt(strdate.substring(5, 7));
        int year = Integer.parseInt(strdate.substring(0, 4));
        int addyear = month / 12;
        year = year + addyear;
        curmonth = curmonth + (month % 12);
        if (curmonth > 12) {
            curmonth = 1;
            year = year + 1;
        }
        String strmonth = String.valueOf(curmonth);
        if (strmonth.length() == 1) {
            strmonth = "0" + strmonth;
        }
        strdate = String.valueOf(year) + "-" + strmonth + "-"
                + strdate.substring(8, 10);
        return format(strdate, SHORTFORMAT);
    }

    /**
     * 传递日期， 获得上个月的最后1天
     *
     * @param dt
     * @return
     */
    @SuppressWarnings("deprecation")
    public static String getUpMDate(Date dt) {
        dt.setDate(1);
        dt.setDate(dt.getDate() - 1);
        return dt.toLocaleString();
    }

    /**
     * 得到当前是那一天。
     */
    public static String getDate() {
        java.text.SimpleDateFormat d = new java.text.SimpleDateFormat();
        d.applyPattern("yyyy-MM-dd");
        java.util.Date nowdate = new java.util.Date();
        String str_date = d.format(nowdate);
        return str_date;
    }

    /**
     * 将10位日期格式化为8位
     *
     * @param dt
     * @return
     */
    public static String getShortDate(String dt) {
        if (dt != null) {
            java.text.SimpleDateFormat myFormat = new SimpleDateFormat(
                    "yyyy-MM-dd");
            try {
                Date date = myFormat.parse(dt);
                return getDate(date, SHORTFORMATNOSPIT);
            } catch (ParseException e) {
                return dt;
            }
        } else
            return dt;
    }

    /**
     * 将8位日期格式化为10位
     *
     * @param dt
     * @return
     */
    public static String getLongDate(String dt) {
        if (dt != null) {
            java.text.SimpleDateFormat myFormat = new SimpleDateFormat(
                    "yyyyMMdd");
            try {
                Date date = myFormat.parse(dt);
                return getDate(date, SHORTFORMAT);
            } catch (ParseException e) {
                return dt;
            }
        } else
            return dt;
    }

    /**
     * 判断是否是当月
     *
     * @return
     */
    public static boolean isSameYearMonth(String date) {
        try {
            String currdate = getCurrentDate();
            currdate = getShortDate(currdate).substring(0, 6);
            String lastdate = getShortDate(date).substring(0, 6);
            if (lastdate.equals(currdate)) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // 根据String类型日期获取Date类型日期
    public static Date stringToDate(String str) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        date = java.sql.Date.valueOf(str);

        return date;
    }

    // Date日期格式转换
    public static String dateToString(Date date, String type) {
        String str = null;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (type.equals("SHORT")) {
            // 16-1-6
            format = DateFormat.getDateInstance(DateFormat.SHORT);
            str = format.format(date);
        } else if (type.equals("MEDIUM")) {
            // 2016-1-6
            format = DateFormat.getDateInstance(DateFormat.MEDIUM);
            str = format.format(date);
        } else if (type.equals("FULL")) {
            // 2016年1月6日 星期三
            format = DateFormat.getDateInstance(DateFormat.FULL);
            str = format.format(date);
        }
        return str;
    }

    /**
     * 获取1年后的时间
     *
     * @return
     */
    public static String getAYear(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat(SHORTFORMAT);
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, +1);
        date = calendar.getTime();
        String formatDate = DateFormat.getDateInstance().format(date);

        return formatDate;
    }

    /**
     * 获取1年后的时间
     *
     * @return
     */
    public static String getAYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, +1);
        String formatDate = DateFormat.getDateInstance().format(
                calendar.getTime());
        return formatDate;
    }

    /**
     * 获取某一天的第2天
     *
     * @return
     */
    public static String getTomorrow(String dateTime) {
        String nextDate_2 = "";
        try {
            Date date = new Date();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.setTime(sf.parse(dateTime));
            // 通过秒获取下一天日期
            long time = (c.getTimeInMillis() / 1000) + 60 * 60 * 24;// 秒
            date.setTime(time * 1000);// 毫秒
            nextDate_2 = sf.format(date).toString();
        } catch (Exception e) {

        }

        return nextDate_2;
    }

    /**
     * 获得加过天的日期
     *
     * @return
     * @throws ParseException
     */
    public static String getDateByAddDay(String date, int day) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(SHORTFORMAT);
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(date));
        cal.add(Calendar.DATE, day);
        return sdf.format(cal.getTime());
    }

    public static List<String> getDates(String startDate, String endDate) {
        try {
            Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);//定义起始日期
            Date d2 = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);//定义结束日期
            Calendar dd = Calendar.getInstance();//定义日期实例
            dd.setTime(d1);//设置日期起始时间
            ArrayList<String> dates = new ArrayList<String>();
            while (d1.getTime() <= d2.getTime()) {//判断是否到结束日期
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String str = sdf.format(dd.getTime());
                dates.add(str);
                dd.add(Calendar.DAY_OF_MONTH, 1);//进行当前日期月份加1
                d1 = dd.getTime();
            }
            return dates;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    public static String addOneSecond(String date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(format(date, "yyyy-MM-dd HH:mm:ss"));
        calendar.add(Calendar.SECOND, -1);
        return getDate(calendar.getTime());
    }

    /**
     * 创建者: dzy
     * 描述:返回2个时间差的绝对值
     * 创建时间: 2018/9/20 10:40
     * [obj1, obj2] 可以是java.util.date类型 也可以是yyyy-MM-dd HH:mm:ss格式的string类型
     *
     * @return java.lang.Integer 返回的是秒
     */
    public static Long timeDifference(Object obj1, Object obj2) throws ParseException {
        Date d1;
        Date d2;
        d1 = getForMatDate(obj1);
        d2 = getForMatDate(obj2);
        if (d1 == null || d2 == null) {
            return null;
        }
        long difference = (d1.getTime() - d2.getTime()) / 1000;
        return Math.abs(difference);
    }

    private static Date getForMatDate(Object obj) throws ParseException {
        Date date = null;
        if (obj instanceof String) {
            date = new SimpleDateFormat(LONGFORMAT).parse((String) obj);
        } else if (obj instanceof Date) {
            date = (Date) obj;
        }

        return date;
    }

    public static void main(String[] args) {
        try {
            //yyyy-MM-dd HH:mm:ss
            System.out.println(timeDifference(
                    new SimpleDateFormat(LONGFORMAT).parse("2018-09-19 10:54:01")
                    , new SimpleDateFormat(LONGFORMAT).parse("2018-09-20 10:55:01")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
