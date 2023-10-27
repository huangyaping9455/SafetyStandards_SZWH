/**
 * Copyright (C), 2015-2021
 * FileName: aaa
 * Author:   呵呵哒
 * Date:     2021/1/29 15:51
 * Description:
 */
package org.springblade.common.tool;

/**
 * @创建人 hyp
 * @创建时间 2021/1/29
 * @描述
 */

import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Pattern;

public class DateUtils {
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 在给定的日期加上或减去指定月份后的日期
	 *
	 * @param sourceDate 原始时间
	 * @param month      要调整的月份，向前为负数，向后为正数
	 * @return
	 */
	public static Date stepMonth(Date sourceDate, int month) {
		Calendar c = Calendar.getInstance();
		c.setTime(sourceDate);
		c.add(Calendar.MONTH, month);

		return c.getTime();
	}

	/**
	 * 获取过去或者未来 任意天内的日期数组
	 * @param intervals      intervals天内
	 * @return              日期数组
	 */
	public static ArrayList<String> test(int intervals ) {
		ArrayList<String> pastDaysList = new ArrayList<>();
		ArrayList<String> fetureDaysList = new ArrayList<>();
		for (int i = 0; i <intervals; i++) {
			pastDaysList.add(getPastDate(i));
			fetureDaysList.add(getFetureDate(i));
		}
		return pastDaysList;
	}

	/**
	 * 获取过去第几天的日期
	 *
	 * @param past
	 * @return
	 */
	public static String getPastDate(int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
		Date today = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String result = format.format(today);
		return result;
	}

	/**
	 * 获取未来 第 past 天的日期
	 * @param past
	 * @return
	 */
	public static String getFetureDate(int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
		Date today = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String result = format.format(today);
		return result;
	}

	/**
	 * 用SimpleDateFormat计算时间差
	 * @throws ParseException
	 */
	public static void calculateTimeDifferenceBySimpleDateFormat() throws ParseException {
		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		/*天数差*/
		Date fromDate1 = simpleFormat.parse("2018-03-01 12:00");
		Date toDate1 = simpleFormat.parse("2018-03-12 12:00");
		long from1 = fromDate1.getTime();
		long to1 = toDate1.getTime();
		int days = (int) ((to1 - from1) / (1000 * 60 * 60 * 24));
		System.out.println("两个时间之间的天数差为：" + days);

		/*小时差*/
		Date fromDate2 = simpleFormat.parse("2018-03-01 12:00");
		Date toDate2 = simpleFormat.parse("2018-03-12 12:00");
		long from2 = fromDate2.getTime();
		long to2 = toDate2.getTime();
		int hours = (int) ((to2 - from2) / (1000 * 60 * 60));
		System.out.println("两个时间之间的小时差为：" + hours);

		/*分钟差*/
		Date fromDate3 = simpleFormat.parse("2018-03-01 12:00");
		Date toDate3 = simpleFormat.parse("2018-03-12 12:00");
		long from3 = fromDate3.getTime();
		long to3 = toDate3.getTime();
		int minutes = (int) ((to3 - from3) / (1000 * 60));
		System.out.println("两个时间之间的分钟差为：" + minutes);
	}

	/**
	 * 计算两个时间相差天数
	 * @return
	 */
	public static String differDays(String dateTimes){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date parse = null;
		long between;
		long day = 0;
		long hour;
		long min;
		long s;
		try {
			//传入历史时间
			parse = format.parse(dateTimes);
			//获取当前日期
			Date now = new Date();
			System.out.println("当前日期：" + DATE_FORMAT.format(now));
			Date date = format.parse(DATE_FORMAT.format(now));
			//计算时间差
			between = date.getTime() - parse.getTime();
			//计算时间差 天数
			day = between / (24 * 60 * 60 * 1000);
			//计算时间差 小时数
			hour = (between / (60 * 60 * 1000) - day * 24);
			//计算时间差 分钟数
			min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
			//计算时间差 秒钟数
			s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
			System.out.println(day + "天" + hour + "小时" + min + "分" + s + "秒");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return Long.toString(day);
	}

	//判断字符串是为日期
	public static boolean isDateString(String datevalue, String dateFormat) {
		if(StringUtils.isBlank(datevalue)) {
			return false;
		}
		if(StringUtils.isBlank(dateFormat)) {
			dateFormat = "yyyy-MM-dd";
		}
		try {
			SimpleDateFormat fmt = new SimpleDateFormat(dateFormat);
			Date dd = fmt.parse(datevalue);
			if (datevalue.equals(fmt.format(dd))) {
				return true;
			} else {
				dateFormat = "yyyy-MM-dd HH:mm:ss";
				fmt = new SimpleDateFormat(dateFormat);
				dd = fmt.parse(datevalue);
				if(datevalue.equals(fmt.format(dd))) {
					return true;
				}else{
					return false;
				}
			}
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 判断时间是否在时间段内 开始时间大于结束时间返回true
	 * @param beginTime
	 * @param endTime
	 * @return true
	 * Date1.after(Date2),当Date1大于Date2时，返回TRUE，当小于等于时，返回false；
	 * Date1.before(Date2)，当Date1小于Date2时，返回TRUE，当大于等于时，返回false；
	 * 如果业务数据存在相等的时候，而且相等时也需要做相应的业务判断或处理时，请注意。
	 */
	public static boolean belongCalendar(Date beginTime, Date endTime) {

		Calendar begin = Calendar.getInstance();
		begin.setTime(beginTime);

		Calendar end = Calendar.getInstance();
		end.setTime(endTime);
		if (begin.before(end)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取指定日期是星期几<br>
	 * @param date
	 * @return 指定日期是星期几
	 */
	public static String getWeekOfDate(Date date) {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0){
			w = 0;
		}
		return weekDays[w];
	}

	/**
	 * 获取指定月份中的周三、周四
	 * @param month
	 * @return
	 * @throws Exception
	 */
	public static String getwendsor(int month,int week) throws Exception {
		if(month<1||month>12){
			throw new Exception("请指定一个合法的月份。");
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal=Calendar.getInstance();
		cal.set(Calendar.MONTH,month-1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		int day=1;
		String weekDays="";
		do{
			//System.out.println(sdf.format(cal.getTime())+" "+cal.get(Calendar.DAY_OF_WEEK)+" "+Calendar.WEDNESDAY);
//			if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY){
//				System.out.println(month+"月份的"+sdf.format(cal.getTime())+ "是周一");
//				weekDays +=sdf.format(cal.getTime())+",";
//			}
//			if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.TUESDAY){
//				System.out.println(month+"月份的"+sdf.format(cal.getTime())+ "是周二");
//				weekDays +=sdf.format(cal.getTime())+",";
//			}
//			if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.WEDNESDAY){
//				System.out.println(month+"月份的"+sdf.format(cal.getTime())+ "是周三");
//				weekDays +=sdf.format(cal.getTime())+",";
//			}
//			if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.THURSDAY){
//				System.out.println(month+"月份的"+sdf.format(cal.getTime())+ "是周四");
//			}
//			if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY){
//				System.out.println(month+"月份的"+sdf.format(cal.getTime())+ "是周五");
//				weekDays +=sdf.format(cal.getTime())+",";
//			}
//			if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY){
//				System.out.println(month+"月份的"+sdf.format(cal.getTime())+ "是周六");
//				weekDays +=sdf.format(cal.getTime())+",";
//			}
//			if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
//				System.out.println(month+"月份的"+sdf.format(cal.getTime())+ "是周天");
//				weekDays +=sdf.format(cal.getTime())+",";
//			}
			if(cal.get(Calendar.DAY_OF_WEEK)==week){
//				System.out.println(month+"月份的"+sdf.format(cal.getTime())+ "是周天");
				weekDays +=sdf.format(cal.getTime())+",";
			}
			day++;
			cal.set(Calendar.DAY_OF_MONTH, day);
		}while(cal.get(Calendar.MONTH)+1==month);
		return weekDays;
	}

	/**
	 * 获取指定月份的第一天
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getFirstDay(int year, int month) {
		// 获取Calendar类的实例
		Calendar c = Calendar.getInstance();
		// 设置年份
		c.set(Calendar.YEAR, year);
		// 设置月份，因为月份从0开始，所以用month - 1
		c.set(Calendar.MONTH, month - 1);
		// 设置日期
		c.set(Calendar.DAY_OF_MONTH, 1);

		return c.getTime();
	}

	/**
	 * 获取指定月份的最后一天
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getLastDay(int year, int month) {
		// 获取Calendar类的实例
		Calendar c = Calendar.getInstance();
		// 设置年份
		c.set(Calendar.YEAR, year);
		// 设置月份，因为月份从0开始，所以用month - 1
		c.set(Calendar.MONTH, month - 1);
		// 获取当前时间下，该月的最大日期的数字
		int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		// 将获取的最大日期数设置为Calendar实例的日期数
		c.set(Calendar.DAY_OF_MONTH, lastDay);

		return c.getTime();
	}

	/**
	 * 获取获取指定日期所在周的第一天和最后一天
	 * @param dataStr
	 * @return
	 * @throws ParseException
	 */
	public static String getFirstAndLastOfWeek(String dataStr) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(dataStr));
		int d = 0;
		String weekDays = "";
		if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
			d = -6;
		} else {
			d = 2 - cal.get(Calendar.DAY_OF_WEEK);
		}
		cal.add(Calendar.DAY_OF_WEEK, d);
		// 所在周开始日期
		String data1 = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		cal.add(Calendar.DAY_OF_WEEK, 6);
		// 所在周结束日期
		String data2 = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		weekDays = data1 + "," + data2;
		return weekDays;

	}

	/**
	 * 某一个月第一天和最后一天
	 *
	 * @param date 指定日期
	 * @param pattern 日期格式
	 * @param isNeedHms 是否需要时分秒
	 * @return
	 */
	public static Map<String, Object> getFirstLastDayByMonth(Date date,
															 String pattern, boolean isNeedHms) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);
		Date theDate = calendar.getTime();

		// 第一天
		GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
		gcLast.setTime(theDate);
		gcLast.set(Calendar.DAY_OF_MONTH, 1);
		String day_first = df.format(gcLast.getTime());
		StringBuffer str = new StringBuffer().append(day_first);
		if (isNeedHms)
			str.append(" 00:00:00");
		day_first = str.toString();

		// 最后一天
		calendar.add(Calendar.MONTH, 1); // 加一个月
		calendar.set(Calendar.DATE, 1); // 设置为该月第一天
		calendar.add(Calendar.DATE, -1); // 再减一天即为上个月最后一天
		String day_last = df.format(calendar.getTime());
		StringBuffer endStr = new StringBuffer().append(day_last);
		if (isNeedHms)
			endStr.append(" 23:59:59");
		day_last = endStr.toString();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("first", day_first);
		map.put("last", day_last);
		return map;
	}

	/**
	 * 获取当前季度
	 *
	 */
	public static int getQuarter() {
		Calendar c = Calendar.getInstance();
		int month = c.get(c.MONTH) + 1;
		int quarter = 0;
		if (month >= 1 && month <= 3) {
			quarter = 1;
		} else if (month >= 4 && month <= 6) {
			quarter = 2;
		} else if (month >= 7 && month <= 9) {
			quarter = 3;
		} else {
			quarter = 4;
		}
		return quarter;
	}

	/**
	 * 获取某季度的第一天和最后一天
	 *	@param num 第几季度
	 */
	public static String[] getCurrQuarter(int num) {
		String[] s = new String[2];
		String str = "";
		// 设置本年的季
		Calendar quarterCalendar = null;
		switch (num) {
			case 1: // 本年到现在经过了一个季度，在加上前4个季度
				quarterCalendar = Calendar.getInstance();
				quarterCalendar.set(Calendar.MONTH, 3);
				quarterCalendar.set(Calendar.DATE, 1);
				quarterCalendar.add(Calendar.DATE, -1);
				str = DateUtils.formatDate(quarterCalendar.getTime(), "yyyy-MM-dd");
				s[0] = str.substring(0, str.length() - 5) + "01-01";
				s[1] = str;
				break;
			case 2: // 本年到现在经过了二个季度，在加上前三个季度
				quarterCalendar = Calendar.getInstance();
				quarterCalendar.set(Calendar.MONTH, 6);
				quarterCalendar.set(Calendar.DATE, 1);
				quarterCalendar.add(Calendar.DATE, -1);
				str = DateUtils.formatDate(quarterCalendar.getTime(), "yyyy-MM-dd");
				s[0] = str.substring(0, str.length() - 5) + "04-01";
				s[1] = str;
				break;
			case 3:// 本年到现在经过了三个季度，在加上前二个季度
				quarterCalendar = Calendar.getInstance();
				quarterCalendar.set(Calendar.MONTH, 9);
				quarterCalendar.set(Calendar.DATE, 1);
				quarterCalendar.add(Calendar.DATE, -1);
				str = DateUtils.formatDate(quarterCalendar.getTime(), "yyyy-MM-dd");
				s[0] = str.substring(0, str.length() - 5) + "07-01";
				s[1] = str;
				break;
			case 4:// 本年到现在经过了四个季度，在加上前一个季度
				quarterCalendar = Calendar.getInstance();
				str = DateUtils.formatDate(quarterCalendar.getTime(), "yyyy-MM-dd");
				s[0] = str.substring(0, str.length() - 5) + "10-01";
				s[1] = str.substring(0, str.length() - 5) + "12-31";
				break;
		}
		return s;
	}

	/**
	 * 用途：以指定的格式格式化日期字符串
	 * @param pattern 字符串的格式
	 * @param currentDate 被格式化日期
	 * @return String 已格式化的日期字符串
	 * @throws NullPointerException 如果参数为空
	 */
	public static String formatDate(Date currentDate, String pattern){
		if(currentDate == null || "".equals(pattern) || pattern == null){
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(currentDate);
	}

	/**
	 *
	 * 1 第一季度 2 第二季度 3 第三季度 4 第四季度
	 *
	 * @param date
	 * @return
	 */
	public static int getSeason(Date date) {

		int season = 0;

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int month = c.get(Calendar.MONTH);
		switch (month) {
			case Calendar.JANUARY:
			case Calendar.FEBRUARY:
			case Calendar.MARCH:
				season = 1;
				break;
			case Calendar.APRIL:
			case Calendar.MAY:
			case Calendar.JUNE:
				season = 2;
				break;
			case Calendar.JULY:
			case Calendar.AUGUST:
			case Calendar.SEPTEMBER:
				season = 3;
				break;
			case Calendar.OCTOBER:
			case Calendar.NOVEMBER:
			case Calendar.DECEMBER:
				season = 4;
				break;
			default:
				break;
		}
		return season;
	}

	/**
	 * 根据年份获取年份下所有月份
	 * @param year
	 * @return
	 */

	public static String[] getYearFullMonth(String year){
		String[] array = new String[12];
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,Integer.parseInt(year));
		cal.set(Calendar.MONTH, 0);
		for(int i=0;i<12;i++){
			array[i] = sdf.format(cal.getTime());
			cal.add(Calendar.MONTH, 1);
		}
		return array;
	}

	/**
	 * 获取某月天数
	 * @param strDate
	 * @return
	 */
	public static int getDaysFullMonth(String strDate) throws ParseException {
		//方法1
//		String strDate = "2012-02";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Calendar calendar = new GregorianCalendar();
		Date date1 = sdf.parse(strDate);
		//放入你的日期
		calendar.setTime(date1);
		System.out.println("天数为=" + calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public final static String FORMAT_STRING = "yyyy-MM-dd HH:mm:ss";

	public final static String[] REPLACE_STRING = new String[]{"GMT+0800", "GMT+08:00"};

	public final static String SPLIT_STRING = "(中国标准时间)";

	public static Date str2Date(String dateString) {
		try {
			dateString = dateString.split(Pattern.quote(SPLIT_STRING))[0].replace(REPLACE_STRING[0], REPLACE_STRING[1]);
			SimpleDateFormat sf1 = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss z", Locale.US);
			Date date = sf1.parse(dateString);
			return date;
		} catch (Exception e) {
			throw new RuntimeException("时间转化格式错误" + "[dateString=" + dateString + "]" + "[FORMAT_STRING=" + FORMAT_STRING + "]");
		}
	}

	public static List<String> getDays(String startTime, String endTime) {
		List<String> days = new ArrayList();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {
			Date start = dateFormat.parse(startTime);
			Date end = dateFormat.parse(endTime);
			Calendar tempStart = Calendar.getInstance();
			tempStart.setTime(start);
			Calendar tempEnd = Calendar.getInstance();
			tempEnd.setTime(end);
			tempEnd.add(5, 1);

			while(tempStart.before(tempEnd)) {
				days.add(dateFormat.format(tempStart.getTime()));
				tempStart.add(6, 1);
			}
		} catch (ParseException var8) {
			var8.printStackTrace();
		}

		return days;
	}

	public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
		if (nowTime.getTime() != startTime.getTime() && nowTime.getTime() != endTime.getTime()) {
			Calendar date = Calendar.getInstance();
			date.setTime(nowTime);
			Calendar begin = Calendar.getInstance();
			begin.setTime(startTime);
			Calendar end = Calendar.getInstance();
			end.setTime(endTime);
			return date.after(begin) && date.before(end);
		} else {
			return true;
		}
	}

	public static String formatDateZero(String pattern) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date time = simpleDateFormat.parse(pattern);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		int year = calendar.get(1);
		int month = calendar.get(2) + 1;
		int day = calendar.get(5);
		String months = month >= 10 ? String.valueOf(month) : "0" + month;
		String days = day >= 10 ? String.valueOf(day) : "0" + day;
		pattern = year + "-" + months + "-" + days;
		return pattern;
	}

	public static int fun(String s1, String s2) {
		//ctrl+alt+/提示方法参数
		//指定日期格式
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		//按照指定格式转化为LocalDate对象
		LocalDate time1 = LocalDate.parse(s1, dateTimeFormatter);
		LocalDate time2 = LocalDate.parse(s2, dateTimeFormatter);
		//调方法计算两个LocalDate的天数差
		long between = ChronoUnit.DAYS.between(time1, time2);
		return (int) between;
	}


	public static void main(String[] args) throws Exception {

		String startTime = "2021-10-18 14:05:00";
		String endTime = "2021-10-18 14:07:00";
		System.out.println(getTime(startTime, endTime));

		String s1="2023-06-17";
       	String s2="2023-10-27";
		int between = fun(s1, s2);
		System.out.println("两个时间间隔天数为："+between);

		System.out.println("111111111111111111111");

		s1="2023-07-01";
		s2="2023-10-27";
		between = fun(s1, s2);
		System.out.println("两个时间间隔天数为："+between);

		System.out.println("222222222222222222222");


		System.out.println("获取指定日期月的第一天、最后一天");
		System.out.println(getFirstLastDayByMonth(new Date(),"yyyy-MM-dd HH:mm:ss",true));
		Map<String, Object> map = getFirstLastDayByMonth(new Date(),"yyyy-MM-dd",true);
		String beginDate = map.get("first").toString();
		System.out.println("开始时间："+beginDate);
		System.out.println("222222222222222");

		System.out.println(getPastDate(5));

		System.out.println(test(5));

		String [] days = DateUtils.test(5).toArray(new String[0]);
		System.out.println(days);
		for (int i=0;i<days.length;i++){
			System.out.println(days[i]);
		}

//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date parse = null;
//		try {
			Date now = new Date();
//			System.out.println("当前日期：" + DATE_FORMAT.format(now));
//			Date newDate = stepMonth(now, -13);
//			System.out.println("当前时间前13个月的日期：" + DATE_FORMAT.format(newDate));
//			//计算两个时间相差天数
//			parse = format.parse("2021-07-05 14:13:11");
//			Date date = format.parse("2021-07-13 14:12:11");
//			long between = date.getTime() - parse.getTime();
//			long day = between / (24 * 60 * 60 * 1000);
//			long hour = (between / (60 * 60 * 1000) - day * 24);
//			long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
//			long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
//			System.out.println(day + "天" + hour + "小时" + min + "分" + s + "秒");
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}

//		String date = "fsdfsd";
//		System.out.println(isDateString(date,null));
//
//		String data[] = {"男","女"};
//		for(int i = 0; i < data.length; i++) {
////			System.out.println(data[i]);
////			System.out.println("男".contains(data[i]));
//			i=i++;
//			System.out.println(i);
//		}


//		String[] arr = {"1", "2", "3", "4", "5", "6", "9"};
//		System.out.println(ArrayUtils.contains(arr, "8"));

//		String begdate = "2021-09-05 08:23:34",enddate="2021-09-06 08:23:34";
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		System.out.println(belongCalendar(format.parse(begdate),format.parse(enddate)));

//		System.out.println(getwendsor(11));
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		Date now = sdf.parse("2021-11-07");
//		System.out.println(getWeekOfDate(now));

//		Date date = new Date();
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(date);
//		int maxday =calendar.getActualMaximum(Calendar.DATE);
//		for(int i=1;i<=maxday;i++)
//		{
//			calendar.set(Calendar.DATE, i);
//			if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.WEDNESDAY)//||calendar.get(Calendar.DAY_OF_WEEK)==Calendar.THURSDAY
//			{
//				System.out.println(String.valueOf(calendar.get(Calendar.DATE))+"是星期"
//						+(calendar.get(Calendar.DAY_OF_WEEK)-1));
//			}
//		}
//
//		System.out.println(DateUtils.getwendsor(11,7));

//		int year1 = 2021;
//		int month1 = 11;
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		System.out.println(year1 + "年" + month1 + "月第一天：" + format.format(getFirstDay(year1, month1)));
//		System.out.println(year1 + "年" + month1 + "月最后一天：" + format.format(getLastDay(year1, month1)));
//		System.out.println("==============================");
//		int year2 = 2020;
//		int month2 = 2;
//		System.out.println(year2 + "年" + month2 + "月第一天：" + format.format(getFirstDay(year2, month2)));
//		System.out.println(year2 + "年" + month2 + "月最后一天：" + format.format(getLastDay(year2, month2)));
//
//		System.out.println("==============获取获取指定日期所在周的第一天和最后一天================");
//		System.out.println(getFirstAndLastOfWeek("2021-11-03"));
//		String weekDays = getFirstAndLastOfWeek("2021-11-03");
//		String[] weekDays_idsss = weekDays.split(",");
//		//去除素组中重复的数组
//		List<String> weekDays_listid = new ArrayList<String>();
//		for (int q=0; q<weekDays_idsss.length; q++) {
//			if(!weekDays_listid.contains(weekDays_idsss[q])) {
//				weekDays_listid.add(weekDays_idsss[q]);
//			}
//		}
//		//返回一个包含所有对象的指定类型的数组
//		String[] weekDays_idss= weekDays_listid.toArray(new String[1]);
//		System.out.println(weekDays_idss[0]);
//		System.out.println(weekDays_idss[1]);
//
//		System.out.println("==============获取获取指定季度的第一天和最后一天================");
//		Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2021-11-07");
//		Calendar ca = Calendar.getInstance();
//		ca.setTime(date);
//		int monthss = ca.get(Calendar.MONTH);
//		System.out.println(monthss);
//		String[] jidu = getCurrQuarter(getSeason(date));
//		System.out.println(jidu[0]);
//		System.out.println(jidu[1]);

//		System.out.println(getYearFullMonth("2021"));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		System.out.println(format.format(new Date()));
//		Date nowDate_Date = format.parse(format.format(new Date()));
//		Date endDate_Date = format.parse("2021-11-17");
//		int compareTo = nowDate_Date.compareTo(endDate_Date);
//		System.out.println(compareTo);
//		if( compareTo == -1 ){
//			System.out.println("大");
//		}else{
//			System.out.println("小");
//		}

		//方法1
//		String strDate = "2021-11";
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
//		Calendar calendar = new GregorianCalendar();
//		Date date1 = sdf.parse(strDate);
//		calendar.setTime(date1); //放入你的日期
//		System.out.println("天数为=" + calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		System.out.println(Integer.parseInt(format.format(new Date()).substring(format.format(new Date()).lastIndexOf("-")+1)));

//		String path="/home/henry/Desktop/1.txt";
//		//获得"Desktop/1.txt",并且不需要前面的"/"
//		String oo = path.substring(path.lastIndexOf("/",path.lastIndexOf("/")-1)+1);
//		//"+1"代表在定位时往后取一位,即去掉"/"
//		//"-1"代表以"/"字符定位的位置向前取一位
//		//从path.lastIndexOf("/")-1位置开始向前寻找倒数第二个"/"的位置
//		oo = oo.substring(0, oo.indexOf("/"));
//		System.out.println(oo);
//
//		System.out.println(format.format(str2Date("Mon Nov 22 2021 00:00:00 GMT+0800 (中国标准时间) 00:00:00")));
//
//
//		String date = DateUtils.getPastDate(5);
//		System.out.println(date);


//		Map map = abc();
//		System.out.println(map);
//		System.out.println("通过value获取Key:"+getKey("3"));
//		JSONArray jArray = new JSONArray();
//		jArray.add(map);
//		String str = jArray.toString();
//		System.out.println(str);
//		JSONArray json = JSONUtil.parseArray(str);
//		List<Map<String,Object>> lists = (List)json;
//		for(Map<String,Object> a:lists){
//			System.out.println(String.valueOf(a.get("deptId")).trim());
//		}

//		for (int i = 9;i<=16;i++){
//			System.out.println(numberToLetter(i));
//		}
//
//		String a = "0-1111341-1111343";
//		int n = a.length()-a.replaceAll("-", "").length();
//		System.out.println("字符串中zhuan字符-有"+n+"个");


//		System.out.println(getLatest12Month(now));
//
//		System.out.println(Arrays.toString(getYearFullMonth("2022")));
//		String[] dateValue = DateUtils.getYearFullMonth("2022");
//		for(int p=0;p<dateValue.length;p++){
//			System.out.println(dateValue[p]);
//		}
//		System.out.println(dateValue.length);
//
//		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
//		System.out.println(format1.format(new Date()));
	}


	public static Map abc() {
		int  value = 1;
		char key   = 'A' - 1;
		char end   = 'Z';
		Map map = Maps.newHashMap();
		while (key < end) {
			key++;
			// 跳过这些 字母
			// if (key == 'I' || key == 'Q' || key == 'O') {
			// continue;
			// }
			map.put(key, value);
			value++;
		}
//		System.out.println(map.toString());
		return map;
	}

	private static ArrayList getKey(String value) {
		ArrayList keyList = new ArrayList();
		String key = null;
		Map map = abc();
		Set set = map.entrySet();// entrySet()方法就是把map中的每个键值对变成对应成Set集合中的一个对象.
		// set对象中的内容如下:[3=c, 2=b, 1=a, 5=e, 4=c]
		Iterator it = set.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			 // entry中的内容就是set集合中的每个对象(map集合中的一个键值对)3=c....
			 // Map.Entry就是一种类型,专值map中的一个键值对组成的对象.
			 if (entry.getValue().equals(value)){
				 key = (String) entry.getKey();
				 keyList.add(key);
				}
			}
		return keyList;
	}

	//数字转字母 1-26 ： A-Z
	public static String numberToLetter(int num) {
		if (num <= 0) {
			return null;
		}
		String letter = "";
		num--;
		do {
			if (letter.length() > 0) {
				num--;
			}
			letter = ((char) (num % 26 + (int) 'A')) + letter;
			num = (int) ((num - num % 26) / 26);
////			 跳过这些 字母
//			 if (letter.equals("I") || letter.equals("Q") || letter.equals("O")) {
//				 num++;
//				 letter = ((char) (num % 26 + (int) 'A')) + letter;
//				 num = (int) ((num - num % 26) / 26);
//			 }
		} while (num > 0);

		return letter;
	}

	//字母转数字  A-Z ：1-26
	public static int letterToNumber(String letter) {
		int length = letter.length();
		int num = 0;
		int number = 0;
		for(int i = 0; i < length; i++) {
			char ch = letter.charAt(length - i - 1);
			num = (int)(ch - 'A' + 1) ;
			num *= Math.pow(26, i);
			number += num;
		}
		return number;
	}

	/**
	 * 获取当前系统时间最近12月的年月（含当月）
	 */
	public static String getLatest12Month(Date date){
		Calendar  from  =  Calendar.getInstance();
		from.setTime(date);
		String str1 = from.get(Calendar.YEAR)+"-"+fillZero(from.get(Calendar.MONTH)+1);
		from.add(Calendar.MONTH, -11);//11个月前
		String str2 = from.get(Calendar.YEAR)+"-"+fillZero(from.get(Calendar.MONTH)+1);
		return str2+"~"+str1;
	}


	/**
	 * 格式化月份
	 */
	public static String fillZero(int i){
		String month = "";
		if(i<10){
			month = "0" + i;
		}else{
			month = String.valueOf(i);
		}
		return month;
	}

	//计算两个时间相差的秒数
	public static long getTime(String startTime, String endTime) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long eTime = df.parse(endTime).getTime();
		long sTime = df.parse(startTime).getTime();
		long diff = (eTime - sTime) / 1000;
		return diff;
	}

}
