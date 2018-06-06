package com.ql.utils;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * 
 * @author dean.wu
 * 
 */
public class DateHelper {
	public static void main(String[] args) {
		Date today = new Date();
		String beforeDayStr = "20171007";
		Date beforeDay = paraseStringToDate(beforeDayStr, "yyyyMMdd");
		List<String> list = getBetweenDates(beforeDay, today);
		System.out.println(list);

	}

	private static final int FIRST_DAY = Calendar.MONDAY;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	public String getMonthStart()// 获取月初日期
	{
		Date d = new Date();
		// 月初
		// System.out.println("月初" + sdf.format(getMonthStart(d)));
		return sdf.format(getMonthStart(d));
	}

	/**
	 * 根据传入日期来获取一个月的开始时间
	 * 
	 * @param d
	 * @return
	 */
	public static String getMonthStartStr(Date d)// 根据传入日期来获取一个月的开始时间
	{
		return sdf.format(getMonthStart(d));
	}

	/**
	 * 根据传入时间获取一个月月末时间
	 * 
	 * @param d
	 * @return
	 */
	public static String getMonthEndStr(Date d)// 根据传入时间获取一个月月末时间
	{

		return sdf.format(getMonthEnd(d));
	}

	/**
	 * 根据月份获取当月的每一天
	 * 
	 * @param monthStr
	 */
	public static List<String> getMonthEveryDayByMonth(String monthStr) {
		List<String> dateList = new ArrayList<String>();
		Date d = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			d = sdf.parse(monthStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		int totalDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		for (int i = 1; i <= totalDays; i++) {
			c.set(Calendar.DAY_OF_MONTH, i);
			Date date = c.getTime();
			// System.out.println((new
			// SimpleDateFormat("yyyyMMdd")).format(date));
			dateList.add((new SimpleDateFormat("yyyyMMdd")).format(date));
		}
		return dateList;
	}

	/**
	 * 根据传入的日期获取所在月份所有日期
	 * 
	 * @param day
	 * @return
	 */
	public static List<String> getAllDaysMonthByDate(Date day)// 根据传入的日期获取所在月份所有日期
	{
		List<String> lst = new ArrayList();
		Date date = getMonthStart(day);
		Date monthEnd = getMonthEnd(day);
		while (!date.after(monthEnd)) {
			// System.out.println(sdf.format(date));
			lst.add(sdf.format(date));
			date = getNext(date);
		}
		return lst;
	}

	/**
	 * 获取从今天起，共days天的日期
	 * 
	 * @param days
	 * @return
	 */
	public static List<String> getDaysBeforeByToday(int days) {
		List<String> list = new ArrayList<String>();
		Date date = new Date();// 取时间
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		for (int i = 0; i < days; i++) {
			calendar = new GregorianCalendar();
			calendar.add(calendar.DATE, 0 - i);// 把日期往后增加一天.整数往后推,负数往前移动
			date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
			String dateString = formatter.format(date);
			System.out.println(dateString);
			list.add(dateString);
		}
		return list;
	}

	/**
	 * 获取从某天起，共days天的日期
	 * 
	 * @param days
	 * @param day
	 *            yyyyMMdd
	 * @return
	 */
	public static List<String> getDaysBeforeByAnyDay(String day, int days) {
		Date date = paraseStringToDate(day, "yyyyMMdd");
		List<String> list = new ArrayList<String>();
		// Date date=new Date();//取时间
		Calendar calendar = new GregorianCalendar();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Date date1 = null;
		for (int i = 0; i < days; i++) {
			calendar.setTime(date);
			// calendar = new GregorianCalendar();
			calendar.add(calendar.DATE, 0 - i);// 把日期往后增加一天.整数往后推,负数往前移动
			date1 = calendar.getTime(); // 这个时间就是日期往后推一天的结果
			String dateString = formatter.format(date1);
			System.out.println(dateString);
			list.add(dateString);
		}
		return list;
	}

	/**
	 * 将字符串转化为日期
	 * 
	 * @param timestr
	 * @return
	 */
	public static Date paraseStringToDate(String timestr)// 将字符串转化为日期
	{
		Date date = null;

		Format f = new SimpleDateFormat("yyyyMMdd");
		try {
			date = (Date) f.parseObject(timestr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 将字符串转化为日期
	 * 
	 * @param timestr
	 * @param format=yyyyMMdd|yyyy-MM-dd......
	 * @return
	 */
	public static Date paraseStringToDate(String timestr, String format)// 将字符串转化为日期
	{
		Date date = null;

		Format f = new SimpleDateFormat(format);
		try {
			date = (Date) f.parseObject(timestr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 将日期转化为字符串
	 * 
	 * @param timestr
	 * @param format=yyyyMMdd|yyyy-MM-dd......
	 * @return
	 */
	public static String paraseDateToString(Date date, String format)// 将字符串转化为日期
	{
		String dateStr = null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			dateStr = formatter.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateStr;
	}

	/**
	 * 获取当月末日期
	 * 
	 * @return
	 */
	public String getMonthEnd()// 获取月末日期
	{
		Date d = new Date();
		return sdf.format(getMonthEnd(d));
	}

	/**
	 * 取日期所在月的第一天
	 * 
	 * @param date
	 * @return
	 */
	private static Date getMonthStart(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int index = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.add(Calendar.DATE, (1 - index));
		return calendar.getTime();
	}

	/**
	 * 取日期所在月的最后一天
	 * 
	 * @param date
	 * @return
	 */
	private static Date getMonthEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		int index = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.add(Calendar.DATE, (-index));
		return calendar.getTime();
	}

	private static Date getNext(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);
		return calendar.getTime();
	}

	/**
	 * 根据日期来获取一周的第一天
	 * 
	 * @param d
	 * @return
	 */
	public static String getWeekStartDay(Date d) {// 根据日期来获取一周的第一天
		Calendar c = Calendar.getInstance();
		List<String> lst = new ArrayList();
		c.setTime(d);
		setToFirstDay(c);
		for (int i = 0; i < 7; i++) {
			String day = printDay(c);
			lst.add(day);
			c.add(Calendar.DATE, 1);
		}
		return lst.get(0);
	}

	/**
	 * 根据日期来获取一周的最后一天
	 * 
	 * @param d
	 * @return
	 */
	public static String getWeekEndtDay(Date d) {// 根据日期来获取一周的最后一天
		Calendar c = Calendar.getInstance();
		List<String> lst = new ArrayList();
		c.setTime(d);
		setToFirstDay(c);
		for (int i = 0; i < 7; i++) {
			String day = printDay(c);
			lst.add(day);
			c.add(Calendar.DATE, 1);
		}
		return lst.get(6);
	}

	/**
	 * 根据日期来获取其所在周的每一天
	 * 
	 * @param d
	 * @return
	 */
	public static List<String> getAllweekDays(Date d) {// 根据日期来获取其所在周的每一天
		Calendar c = Calendar.getInstance();
		List<String> lst = new ArrayList();
		c.setTime(d);
		setToFirstDay(c);
		for (int i = 0; i < 7; i++) {
			String day = printDay(c);
			lst.add(day);
			c.add(Calendar.DATE, 1);
		}
		return lst;
	}

	/**
	 * 获取两个日期之间的日期（不包括end）
	 * 
	 * @param start
	 *            开始日期
	 * @param end
	 *            结束日期
	 * @return 日期集合
	 */
	public static List<String> getBetweenDates(Date start, Date end) {
		List<String> result = new ArrayList<String>();
		Calendar tempStart = Calendar.getInstance();
		tempStart.setTime(start);
		tempStart.add(Calendar.DAY_OF_YEAR, 0);
		Calendar tempEnd = Calendar.getInstance();
		tempEnd.setTime(end);
		int day = tempEnd.get(Calendar.DATE);
		tempEnd.set(Calendar.DATE, day - 1);
		while (tempStart.before(tempEnd)) {
			result.add(paraseDateToString(tempStart.getTime(), "yyyyMMdd"));
			tempStart.add(Calendar.DAY_OF_YEAR, 1);
		}
		return result;
	}

	/**
	 * 获得指定日期的前一天
	 * 
	 * @param specifiedDay
	 * @return
	 * @throws Exception
	 */
	public static Date getSpecifiedDayBefore(Date date, String format) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);
		return c.getTime();
	}

	/**
	 * 日期格式化成字符串
	 * 
	 * @param date
	 * @param fmt
	 * @return
	 */
	public static String dateToString(Date date, String fmt) {
		DateFormat df = new SimpleDateFormat(fmt);
		return df.format(date);
	}

	public static List<String> getALlweekDays() {
		List<String> lst = new ArrayList();
		Calendar calendar = Calendar.getInstance();
		setToFirstDay(calendar);
		for (int i = 0; i < 7; i++) {
			String day = printDay(calendar);
			lst.add(day);
			calendar.add(Calendar.DATE, 1);
		}
		return lst;
	}

	private static void setToFirstDay(Calendar calendar) {
		while (calendar.get(Calendar.DAY_OF_WEEK) != FIRST_DAY) {
			calendar.add(Calendar.DATE, -1);
		}
	}

	private static String printDay(Calendar calendar) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		return dateFormat.format(calendar.getTime());
	}

	public static String getDateDiffengt(String beginDateStr) {
		Date endDate = new Date();
		Date beginDate = paraseStringToDate(beginDateStr, "yyyy-MM-dd HH:mm:ss");
		return getDateDiffengt(beginDate, endDate);
	}

	public static String getDateDiffengt(Date beginDate, Date endDate) {
		long between = (endDate.getTime() - beginDate.getTime());// 除以1000是为了转换成秒
		long hours = (between) / (1000 * 60 * 60);
		long minutes = (between - hours * (1000 * 60 * 60)) / (1000 * 60);
		return hours + "小时" + (minutes + 1) + "分";
	}
}