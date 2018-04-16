package com.zj.api.common.utils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import org.apache.commons.lang.time.DateUtils;

/**
 *@Title:
 *@Description:日期处理类
 *@Author:赵亚龙
 *@Since:2016年2月28日
 *@Version:1.1.0
 */
public class DateUtil {
	
	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public final static String FORMAT_GENERAL = "yyyy-MM-dd HH:mm:ss";
	/**
	 * yyyy-MM-dd
	 */
	public final static String FORMAT_GENERAL1 = "yyyy-MM-dd";
	/**
	 * MM/dd
	 */
	public final static String FORMAT_GENERAL2 = "MM/dd";
	/**
	 * HH:mm
	 */
	public final static String FORMAT_GENERAL3 = "HH:mm";
	/**
	 * yyyy-MM-dd HH:mm
	 */
	public final static String FORMAT_GENERAL4 = "yyyy-MM-dd";
	
	/**
	 * MM-dd
	 */
	public final static String FORMAT_GENERAL5 = "MM-dd";
	/**
	 * HH:mm MM-dd
	 */
	public final static String FORMAT_GENERAL6 = "HH:mm MM-dd";
	
	/**
	 * MM-dd
	 */
	public final static String FORMAT_FLIGHT_PLAN = "HHmm";
	
	/**
	 * 早上8点到下午16点，每隔半小时(一共是16个半小时)
	 */
	public final static int EIGHTH_TO_SIXTEEN = 16;
	
	/**
	 * 获取12小时制当前日期字符串
	 * 
	 * @return
	 */
	public static String getStrDate_12() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date currentTime = new Date();
		String strDate = formatter.format(currentTime);
		return strDate;
	}

	/**
	 * 获取24小时制当前日期字符串
	 * 
	 * @return
	 */
	public static String getStrDate_24() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currentTime = new Date();
		String strDate = formatter.format(currentTime);
		return strDate;
	}

	/**
	 * 获取格式化当前时间、毫秒字符串
	 * 
	 * @return
	 */
	public static String getStrDateS() {
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss:SS");
		Date currentTime = new Date();
		String strDate = formatter.format(currentTime);
		return strDate;
	}

	/**
	 * 转换日期为字符串格式
	 * <p>
	 * 
	 * @param Date
	 * @return
	 */
	public static String DateToStr(java.util.Date Date) {
		if(Date != null){
			SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_GENERAL);
			String strDate = formatter.format(Date);
			return strDate;
		}
		return "";
	}

	/**
	 * 奖date类型的日期转换为指定格式
	 */
	public static Date DateToDate(java.util.Date Date, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date d = new Date();
		String dd = formatter.format(d);
		Date ddd = null;
		try {
			ddd = formatter.parse(dd);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ddd;
	}

	/**
	 * 转换日期为格式化字符串
	 * 
	 * @param Date
	 * @param format
	 * @return
	 */
	public static String DateToFormatStr(java.util.Date Date, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String strDate = formatter.format(Date);
		return strDate;
	}

	/**
	 * 获取当前日期 格式为 yyyy-MM-dd
	 * 
	 * @return strDate
	 */
	public static String getNowStrDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String strDate = formatter.format(date);
		return strDate;
	}

	/**
	 * 获取当间时间字符串 yyyyMMddHHmmss
	 * 
	 * @return
	 */
	public static String getLongDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String strDate = formatter.format(date);
		return strDate;
	}

	/**
	 * 获取当间时间字符串 yyyyMMddHHmmssSS
	 * 
	 * @return
	 */
	public static String getLongDateS() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSS");
		Date date = new Date();
		String strDate = formatter.format(date);
		return strDate;
	}

	/**
	 * 比较二个日期
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean Compare_Date(java.util.Date date1,
			java.util.Date date2) {
		return date1.equals(date2);
	}

	/**
	 * 将字符串类型的时间转化为Date型
	 * 
	 * @param strDate
	 * @param formatDate
	 * @return Date
	 * @throws ParseException
	 */
	public static Date str2Date(String strDate, String formatDate)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(formatDate);
		return sdf.parse(strDate);
	}

	/**
	 * 将字符串类型的时间转化为Date型，并将在此时间上进行增加或减少相应天
	 * 
	 * @param strDate
	 * @param formatDate
	 * @return Date
	 * @throws ParseException
	 */
	public static Date otherDate(String strDate, String formatDate, int num)
			throws ParseException {
		Calendar c = new GregorianCalendar();

		Date date = str2Date(strDate, formatDate);

		c.setTime(date);

		c.add(Calendar.DATE, num);

		SimpleDateFormat sdf = new SimpleDateFormat(formatDate);

		return str2Date(sdf.format(c.getTime()), formatDate);
	}

	/**
	 * 获取现在时间
	 * 
	 * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
	 */
	public static Date getNowDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(8);
		Date currentTime_2 = formatter.parse(dateString, pos);
		return currentTime_2;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return返回短时间格式 yyyy-MM-dd
	 */
	public static Date getNowDateShort() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(8);
		Date currentTime_2 = formatter.parse(dateString, pos);
		return currentTime_2;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getStringDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return 返回短时间字符串格式yyyy-MM-dd
	 */
	public static String getStringDateShort() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 获取时间 小时:分;秒 HH:mm:ss
	 * 
	 * @return
	 */
	public static String getTimeShort() {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		Date currentTime = new Date();
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDateLong(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateDate
	 * @return
	 */
	public static String dateToStrLong(java.util.Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * 将短时间格式时间转换为字符串 yyyy-MM-dd
	 * 
	 * @param dateDate
	 * @param k
	 * @return
	 */
	public static String dateToStr(java.util.Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * 将短时间格式字符串转换为时间 yyyy-MM-dd
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 得到现在时间
	 * 
	 * @return
	 */
	public static Date getNow() {
		Date currentTime = new Date();
		return currentTime;
	}

	/**
	 * 提取一个月中的最后一天
	 * 
	 * @param day
	 * @return
	 */
	public static Date getLastDate(long day) {
		Date date = new Date();
		long date_3_hm = date.getTime() - 3600000 * 34 * day;
		Date date_3_hm_date = new Date(date_3_hm);
		return date_3_hm_date;
	}

	/**
	 * 得到现在时间
	 * 
	 * @return 字符串 yyyyMMdd HHmmss
	 */
	public static String getStringToday() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 得到现在小时
	 */
	public static String getHour() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		String hour;
		hour = dateString.substring(11, 13);
		return hour;
	}

	/**
	 * 得到现在分钟
	 * 
	 * @return
	 */
	public static String getTime() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		String min;
		min = dateString.substring(14, 16);
		return min;
	}

	/**
	 * 根据用户传入的时间表示格式，返回当前时间的格式 如果是yyyyMMdd，注意字母y不能大写。
	 * 
	 * @param sformat
	 *            yyyyMMddhhmmss
	 * @return
	 */
	public static String getUserDate(String sformat) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(sformat);
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 二个小时时间间的差值,必须保证二个时间都是"HH:MM"的格式，返回字符型的分钟
	 */
	public static String getTwoHour(String st1, String st2) {
		String[] kk = null;
		String[] jj = null;
		kk = st1.split(":");
		jj = st2.split(":");
		if (Integer.parseInt(kk[0]) < Integer.parseInt(jj[0]))
			return "0";
		else {
			double y = Double.parseDouble(kk[0]) + Double.parseDouble(kk[1])
					/ 60;
			double u = Double.parseDouble(jj[0]) + Double.parseDouble(jj[1])
					/ 60;
			if ((y - u) > 0)
				return y - u + "";
			else
				return "0";
		}
	}

	/**
	 * 得到二个日期间的间隔天数
	 */
	public static String getTwoDay(String sj1, String sj2) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		long day = 0;
		try {
			java.util.Date date = myFormatter.parse(sj1);
			java.util.Date mydate = myFormatter.parse(sj2);
			day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			return "";
		}
		return day + "";
	}

	/**
	 * 时间前推或后推分钟,其中JJ表示分钟.
	 */
	public static String getPreTime(String sj1, String jj) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String mydate1 = "";
		try {
			Date date1 = format.parse(sj1);
			long Time = (date1.getTime() / 1000) + Integer.parseInt(jj) * 60;
			date1.setTime(Time * 1000);
			mydate1 = format.format(date1);
		} catch (Exception e) {
		}
		return mydate1;
	}

	/**
	 * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
	 */
	public static String getNextDay(String nowdate, String delay) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String mdate = "";
			Date d = strToDate(nowdate);
			long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24
					* 60 * 60;
			d.setTime(myTime * 1000);
			mdate = format.format(d);
			return mdate;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 判断是否润年
	 * 
	 * @param ddate
	 * @return
	 */
	public static boolean isLeapYear(String ddate) {

		/**
		 * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
		 * 3.能被4整除同时能被100整除则不是闰年
		 */
		Date d = strToDate(ddate);
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(d);
		int year = gc.get(Calendar.YEAR);
		if ((year % 400) == 0)
			return true;
		else if ((year % 4) == 0) {
			if ((year % 100) == 0)
				return false;
			else
				return true;
		} else
			return false;
	}

	/**
	 * 返回美国时间格式 26 Apr 2006
	 * 
	 * @param str
	 * @return
	 */
	public static String getEDate(String str) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(str, pos);
		String j = strtodate.toString();
		String[] k = j.split(" ");
		return k[2] + k[1].toUpperCase() + k[5].substring(2, 4);
	}

	/**
	 * 获取一个月的最后一天
	 * 
	 * @param dat
	 * @return
	 */
	public static String getEndDateOfMonth(String dat) {// yyyy-MM-dd
		String str = dat.substring(0, 8);
		String month = dat.substring(5, 7);
		int mon = Integer.parseInt(month);
		if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8
				|| mon == 10 || mon == 12) {
			str += "31";
		} else if (mon == 4 || mon == 6 || mon == 9 || mon == 11) {
			str += "30";
		} else {
			if (isLeapYear(dat)) {
				str += "29";
			} else {
				str += "28";
			}
		}
		return str;
	}

	/**
	 * 判断二个时间是否在同一个周
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameWeekDates(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
		if (0 == subYear) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2
					.get(Calendar.WEEK_OF_YEAR))
				return true;
		} else if (1 == subYear && 11 == cal2.get(Calendar.MONTH)) {
			// 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2
					.get(Calendar.WEEK_OF_YEAR))
				return true;
		} else if (-1 == subYear && 11 == cal1.get(Calendar.MONTH)) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2
					.get(Calendar.WEEK_OF_YEAR))
				return true;
		}
		return false;
	}

	/**
	 * 获得一个日期所在的周的星期几的日期，如要找出2002年2月3日所在周的星期一是几号
	 * 
	 * @param sdate
	 * @param num
	 * @return
	 */
	public static String getWeek(String sdate, String num) {
		// 再转换为时间
		Date dd = DateUtil.strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(dd);
		if (num.equals("1")) // 返回星期一所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		else if (num.equals("2")) // 返回星期二所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
		else if (num.equals("3")) // 返回星期三所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		else if (num.equals("4")) // 返回星期四所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
		else if (num.equals("5")) // 返回星期五所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		else if (num.equals("6")) // 返回星期六所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		else if (num.equals("0")) // 返回星期日所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	}

	/**
	 * 根据一个日期，返回是星期几的字符串
	 * 
	 * @param sdate
	 * @return
	 */
	public static String getWeek(String sdate) {
		// 再转换为时间
		Date date = DateUtil.strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// int hour=c.get(Calendar.DAY_OF_WEEK);
		// hour中存的就是星期几了，其范围 1~7
		// 1=星期日 7=星期六，其他类推
		return new SimpleDateFormat("EEEE").format(c.getTime());
	}

	public static String getWeekStr(String sdate) {
		String str = "";
		str = DateUtil.getWeek(sdate);
		if ("1".equals(str)) {
			str = "星期日";
		} else if ("2".equals(str)) {
			str = "星期一";
		} else if ("3".equals(str)) {
			str = "星期二";
		} else if ("4".equals(str)) {
			str = "星期三";
		} else if ("5".equals(str)) {
			str = "星期四";
		} else if ("6".equals(str)) {
			str = "星期五";
		} else if ("7".equals(str)) {
			str = "星期六";
		}
		return str;
	}

	/**
	 * 两个时间之间的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getDays(String date1, String date2) {
		if (date1 == null || date1.equals(""))
			return 0;
		if (date2 == null || date2.equals(""))
			return 0;
		// 转换为标准时间
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = null;
		java.util.Date mydate = null;
		try {
			date = myFormatter.parse(date1);
			mydate = myFormatter.parse(date2);
		} catch (Exception e) {
		}
		long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}

	/**
	 * 形成如下的日历 ， 根据传入的一个时间返回一个结构 星期日 星期一 星期二 星期三 星期四 星期五 星期六 下面是当月的各个时间
	 * 此函数返回该日历第一行星期日所在的日期
	 * 
	 * @param sdate
	 * @return
	 */
	public static String getNowMonth(String sdate) {
		// 取该时间所在月的一号
		sdate = sdate.substring(0, 8) + "01";

		// 得到这个月的1号是星期几
		Date date = DateUtil.strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int u = c.get(Calendar.DAY_OF_WEEK);
		String newday = DateUtil.getNextDay(sdate, (1 - u) + "");
		return newday;
	}

	/**
	 * 取得数据库主键 生成格式为yyyymmddhhmmss+k位随机数
	 * 
	 * @param k
	 *            表示是取几位随机数，可以自己定
	 */

	public static String getNo(int k) {

		return getUserDate("yyyyMMddhhmmss") + getRandom(k);
	}

	/**
	 * 
	 * 获得当前时间的后多少天
	 * 
	 * @param curr 
	 * @param count
	 * @return
	 */
	public static Date getNextDate(Date curr, int count) {
	    Calendar  calendar = Calendar.getInstance();
	    calendar.setTime(curr);
	    calendar.add(Calendar.DAY_OF_MONTH, count);  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date=null;
		try {
			date= str2Date(sdf.format(calendar.getTime()),"yyyy-MM-dd");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 返回一个随机数
	 * 
	 * @param i
	 * @return
	 */
	public static String getRandom(int i) {
		Random jjj = new Random();
		// int suiJiShu = jjj.nextInt(9);
		if (i == 0)
			return "";
		String jj = "";
		for (int k = 0; k < i; k++) {
			jj = jj + jjj.nextInt(9);
		}
		return jj;
	}

	/**
	 * 
	 * @param args
	 */
	public static boolean RightDate(String date) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		;
		if (date == null)
			return false;
		if (date.length() > 10) {
			sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		} else {
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		}
		try {
			sdf.parse(date);
		} catch (ParseException pe) {
			return false;
		}
		return true;
	}

	/**
	 * 返回两个年份的差值，多少年保留一位小数 prram yyyy-MM-dd
	 */
	public static String timeToTime(String startdate, String enddate) {
		String date1[] = startdate.split("-");
		int year1 = 365;
		int month1 = 31;
		int day1 = Integer.parseInt(date1[2]);
		Long totalday1;
		String date2[] = enddate.split("-");
		int year2 = 365;
		int month2 = 31;
		int day2 = Integer.parseInt(date2[2]);
		Long totalday2;
		float time;
		if ("04".equals(date1[2]) || "06".equals(date1[2])
				|| "09".equals(date1[2]) || "11".equals(date1[2])) {

			month1 = 30;
		}
		if ("02".equals(date1[2])) {
			if (isLeapYear(startdate)) {
				month1 = 29;
			} else {
				month1 = 28;
			}
		}
		if ("04".equals(date2[2]) || "06".equals(date2[2])
				|| "09".equals(date2[2]) || "11".equals(date2[2])) {

			month2 = 30;
		}
		if ("02".equals(date2[2])) {
			if (isLeapYear(enddate)) {
				month2 = 29;
			} else {
				month2 = 28;
			}
		}
		totalday1 = Long.parseLong(date1[0]) * year1
				+ Integer.parseInt(date1[1]) * month1 + day1;
		totalday2 = Long.parseLong(date2[0]) * year2
				+ Integer.parseInt(date2[1]) * month2 + day2;
		time = (float) (totalday1 - totalday2) / 365;
		DecimalFormat df = new DecimalFormat("0.0");// 格式化小数，不足的补0
		return df.format(time);// 返回的是String类型的
	}
	/**   
     * @param date1 需要比较的时间 不能为空(null),需要正确的日期格式 ,如：2009-09-12  
     * @param date2 被比较的时间  为空(null)则为当前时间   
     * @param stype 返回值类型   0为多少天，1为多少个月，2为多少年   
     * @return   
     * 举例： 
     * compareDate("2009-09-12", null, 0);//比较天 
     * compareDate("2009-09-12", null, 1);//比较月 
     * compareDate("2009-09-12", null, 2);//比较年 
     */   
public static int compareDate(String startDay,String endDay,int stype){    
    int n = 0;    
    String[] u = {"天","月","年"};    
    String formatStyle = stype==1?"yyyy-MM":"yyyy-MM-dd";    
        
    endDay = endDay==null?getNowStrDate():endDay;    
        
    DateFormat df = new SimpleDateFormat(formatStyle);    
    Calendar c1 = Calendar.getInstance();    
    Calendar c2 = Calendar.getInstance();    
    try {    
        c1.setTime(df.parse(startDay));    
        c2.setTime(df.parse(endDay));    
    } catch (Exception e3) {    
        System.out.println("wrong occured");    
    }    
    //List list = new ArrayList();    
    while (!c1.after(c2)) {                   // 循环对比，直到相等，n 就是所要的结果    
        //list.add(df.format(c1.getTime()));    // 这里可以把间隔的日期存到数组中 打印出来    
        n++;    
        if(stype==1){    
            c1.add(Calendar.MONTH, 1);          // 比较月份，月份+1    
        }    
        else{    
            c1.add(Calendar.DATE, 1);           // 比较天数，日期+1    
        }    
    }    
    n = n-1;    
    if(stype==2){    
        n = (int)n/365;    
    }       
    System.out.println(startDay+" -- "+endDay+" 相差多少"+u[stype]+":"+n);          
    return n;    
}     
	/**
	 * 向前或者向后推几个月
	 * 
	 * @param date
	 * @param month
	 * @return
	 */
	public static Date timeToBeforeOrAfter(String strDate, String formatDate,
			int num) {
		try {
			Calendar c = new GregorianCalendar();
			Date d = str2Date(strDate, "yyyy-MM-dd");
			c.setTime(d);
			c.add(Calendar.MONTH, num);
			SimpleDateFormat sdf = new SimpleDateFormat(formatDate);
			return str2Date(sdf.format(c.getTime()), formatDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 得到两日期相差几个月
	 * 
	 * @param String
	 * @return
	 */
	public static long getMonthDiff(String startDate, String endDate)
			throws ParseException {
		long monthday;
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate1 = fmt.parse(startDate);

		Calendar starCal = Calendar.getInstance();
		starCal.setTime(startDate1);

		int sYear = starCal.get(Calendar.YEAR);
		int sMonth = starCal.get(Calendar.MONTH);
		int sDay = starCal.get(Calendar.DAY_OF_MONTH);

		Date endDate1 = fmt.parse(endDate);
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDate1);
		int eYear = endCal.get(Calendar.YEAR);
		int eMonth = endCal.get(Calendar.MONTH);
		int eDay = endCal.get(Calendar.DAY_OF_MONTH);

		monthday = ((eYear - sYear) * 12 + (eMonth - sMonth));

		// 这里计算零头的情况，根据实际确定是否要加1 还是要减1
		if (sDay < eDay) {
			monthday = monthday + 1;
		}
		return monthday;
	}
	
	/** 
	 * 奖string类型的日期转换为指定格式
	 */
	public static Date stringToDate(String dateStr) {
		SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_GENERAL1);
		Date ddd = null;
		try {
			ddd = formatter.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ddd;
	}
	
	/**
	 * 奖date类型的日期转换为指定格式
	 */
	public static String dateToString(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_GENERAL1);
		String ddd = null;
		ddd = formatter.format(date);
		return ddd;
	}
	
	public static String dateToString(Date date, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String ddd = null;
		ddd = formatter.format(date);
		return ddd;
	}
	
	/**
	 * 将string类型的日期转换为指定格式：返回格式1的日期
	 * 错误将返回当前时间
	 */
	public static Date stringToDate1(String dateStr) {
		SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_GENERAL);
		Date ddd = new Date();
		try {
			ddd = formatter.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ddd;
	}
	
	/**
	 * 
	 * 获得当前时间加上或者减去几天的时间的字符串
	 * 
	 * @param curr 
	 * @param count
	 * @return
	 */
	public static String getAddDateStr(String dateStr, int addDate) {
	    Calendar  calendar = Calendar.getInstance();
	    calendar.setTime(stringToDate(dateStr));
	    calendar.add(Calendar.DATE, addDate);  
		return dateToString(calendar.getTime());
	}
	
	/**
	 * 
	 * 获得当前时间加上或者减去几天的时间的字符串
	 * 
	 * @param curr 
	 * @param count
	 * @return
	 */
	public static String getAddDateStr(Date date, int addDate) {
	    Calendar  calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.add(Calendar.DATE, addDate);  
		return dateToString(calendar.getTime());
	}
	
	public static Date getAddDate(Date date, int addType, int addDate) {
	    Calendar  calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.add(addType, addDate);  
		return calendar.getTime();
	}
	
	public static String formatDate(Date date, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}
	
	public static Date parseDate(String dateStr, String format) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.parse(dateStr);
	}
	
	public static Date parseDate(Date date, String format) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.parse(formatter.format(date));
	}
	
	/**
	 * 定位到早上8点
	 */
	public static Date goToEighth(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 8);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	/**
	 * 定位到0点
	 */
	public static Date goToZero(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	/**
	 * @param d1 被减时间
	 * @param d2 减时间
	 * @return 两个时间相差的天数(忽略小时分钟秒)
	 */
	public static int getDaysDiffer(Date d1, Date d2){
		double millisPerDay = DateUtils.MILLIS_PER_DAY;
		return (int) (d2.getTime()/millisPerDay-d1.getTime()/millisPerDay);
	}
	
	public static long getMinuteDIffer(String d1,String d2){
		try {
		DateFormat df_parseDate = new SimpleDateFormat(FORMAT_FLIGHT_PLAN);
		Date date1;
	
			date1 = (Date) df_parseDate.parse(d1);
	
		Date date2= (Date) df_parseDate.parse(d2);
		return (date1.getTime()-date2.getTime())/(1000*60);//得到分钟数
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
		
	}
	/**
	 * @param d1
	 * @return 返回日期是星期几
	 */
	public static int getDateWeek(Date d1){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d1);
		int i = calendar.get(Calendar.DAY_OF_WEEK);
		if(i == 1){
			return 8;
		}
		return i;
	}
	
	public static void main(String[] args) throws ParseException {
//		System.out.println(parseDate("2015-08-28 14:10", FORMAT_GENERAL4));
//		System.out.println(getDateWeek(parseDate("2015-09-28", FORMAT_GENERAL1)));
//		System.out.println(getDateWeek(new Date()));
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 0);
		System.out.println(getDaysDiffer(goToZero(new Date()), goToZero(c.getTime())));
	}
	
}
