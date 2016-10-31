package com.kingdee.eas.camel;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.kingdee.bos.Context;
import com.kingdee.eas.base.usermonitor.UserMonitorFactory;
import com.kingdee.eas.camel.constant.CADateConstant;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.util.StringUtils;

/**
 * 时间辅助类.
 * <p>
 * 	note that: 如有修改请添加注释.
 * </p>
 * @author jason qu
 *
 */
public class CADateHelper {
public static final int MILLIS_OF_DAY = 86400000;
	
	/**
	 * 
	 * 注意：
	 * <ul>
	 * 	<li>不能多线程操作该类的对象,否则结果不可预料.
	 *  <li>month方法设置/得到的月份是从1开始，Calendar提供的API是从0开始，使用时必须注意点
	 * </ul>
	 * @author jason qu
	 */
	public static class DateModel {
		/**最大的毫秒值	999999999*/
		private static final int MAX_NANOS = 999999999;
		private static final int MAX_HOUR = 23;
		private static final int MAX_MIN_SEC = 59;
		private static final int MAX_MILLIS = 999;
		private Calendar calendar = null;
		private Date date = null;
		
		/**
		 * 设置年为指定的值.
		 * @param year
		 * @see Calendar#YEAR
		 * @return
		 */
		public DateModel setYear(int year) {
			calendar.set(Calendar.YEAR, year);
			return this;
		}
		
		/**
		 * 设置月为指定的值.
		 * @param month
		 * @see Calendar#MONTH
		 * @return
		 */
		public DateModel setMonth(int month) {
			calendar.set(Calendar.MONTH, month - 1);
			return this;
		}
		
		/**
		 * 设置天为指定的值.
		 * @param day
		 * @see Calendar#DAY_OF_MONTH
		 * @return
		 */
		public DateModel setDay(int day) {
			calendar.set(Calendar.DAY_OF_MONTH, day);
			return this;
		}
		
		/**
		 * 设置时为指定的值.
		 * @param hour
		 * @see Calendar#HOUR_OF_DAY
		 * @return
		 */
		public DateModel setHour(int hour) {
			calendar.set(Calendar.HOUR_OF_DAY, hour);
			return this;
		}
		
		/**
		 * 设置分为指定的值.
		 * @param minute
		 * @see Calendar#MINUTE
		 * @return
		 */
		public DateModel setMinute(int minute) {
			calendar.set(Calendar.MINUTE, minute);
			return this;
		}
		
		/**
		 * 设置秒为指定的值.<br/>
		 * @param second
		 * @see Calendar#SECOND
		 * @return
		 */
		public DateModel setSecond(int second) {
			calendar.set(Calendar.SECOND, second);
			return this;
		}
		
		/**
		 * 设置周的第一天.
		 * @param value
		 * @return
		 */
		public DateModel setFirstDayOfWeek(int value) {
			calendar.setFirstDayOfWeek(value);
			return this;
		}
		
		/**
		 * 设置毫秒为指定的值.<br/>
		 * @param milliSecond
		 * @see Calendar#MILLISECOND
		 * @return
		 */
		public DateModel setMilliSecond(int milliSecond) {
			calendar.set(Calendar.MILLISECOND, milliSecond);
			return this;
		}
		
		/**
		 * 设置周对应的天.
		 * 
		 * @param value
		 * @return
		 */
		public DateModel setDayOfWeek(int value) {
			calendar.set(Calendar.DAY_OF_WEEK, value);
			return this;
		}
		
		/**
		 * 添加指定的年数量.
		 * @param year
		 * @see Calendar#YEAR
		 * @return
		 */
		public DateModel addYear(int year) {
			calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + year);
			return this;
		}
		
		/**
		 * 添加指定的月数量.
		 * @param month
		 * @see Calendar#MONTH
		 * @return
		 */
		public DateModel addMonth(int month) {
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + month);
			return this;
		}
		
		/**
		 * 添加指定的天数量.
		 * @param day
		 * @see Calendar#DAY_OF_MONTH
		 * @return
		 */
		public DateModel addDay(int day) {
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + day);
			return this;
		}
		
		/**
		 * 添加指定的小时.
		 * @param hour
		 * @see Calendar#HOUR_OF_DAY
		 * @return
		 */
		public DateModel addHour(int hour) {
			calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + hour);
			return this;
		}
		
		/**
		 * 添加指定的分钟.
		 * @param minute
		 * @see Calendar#MINUTE
		 * @return
		 */
		public DateModel addMinute(int minute) {
			calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + minute);
			return this;
		}
		
		/**
		 * 添加指定的秒.
		 * @param second
		 * @see Calendar#SECOND
		 * @return
		 */
		public DateModel addSecond(int second) {
			calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) + second);
			return this;
		}
		
		/**
		 * 添加指定的毫秒.
		 * @param milliSecond
		 * @see Calendar#MILLISECOND
		 * @return
		 */
		public DateModel addMilliSecond(int milliSecond) {
			calendar.set(Calendar.MILLISECOND, calendar.get(Calendar.MILLISECOND) + milliSecond);
			return this;
		}
		
		/**
		 * 返回年信息.
		 * @see Calendar#YEAR
		 * @return
		 */
		public int getYear() {
			return calendar.get(Calendar.YEAR);
		}
		
		/**
		 * 返回月信息.
		 * @see Calendar#MONTH
		 * @return
		 */
		public int getMonth() {
			return calendar.get(Calendar.MONTH) + 1;
		}
		
		/**
		 * 返回天信息.
		 * @see Calendar#DAY_OF_MONTH
		 * @return
		 */
		public int getDay() {
			return calendar.get(Calendar.DAY_OF_MONTH);
		}
		
		/**
		 * 返回时信息.
		 * @see Calendar#HOUR_OF_DAY
		 * @return
		 */
		public int getHour() {
			return calendar.get(Calendar.HOUR_OF_DAY);
		}
		
		/**
		 * 返回分信息.
		 * @see Calendar#MINUTE
		 * @return
		 */
		public int getMinute() {
			return calendar.get(Calendar.MINUTE);
		}
		
		/**
		 * 返回秒信息.
		 * @see Calendar#SECOND
		 * @return
		 */
		public int getSecond() {
			return calendar.get(Calendar.SECOND);
		}
		
		/**
		 * 返回毫秒信息.
		 * @see Calendar#MILLISECOND
		 * @return
		 */
		public int getMilliSecond() {
			return calendar.get(Calendar.MILLISECOND);
		}
		
		/**
		 * 返回季节信息.
		 * @return
		 */
		public int getSeason() {
			int month = getMonth();
			int reason = -1;
			switch (month) {
			case 1:
			case 2:
			case 3:
				reason = 1;
				break;
			case 4:
			case 5:
			case 6:
				reason = 2;
				break;
			case 7:
			case 8:
			case 9:
				reason = 3;
				break;
			case 10:
			case 11:
			case 12:
				reason = 4;
				break;
			}
			
			return reason;
		}
		
		/**
		 * 返回天的开始日期.<br/>
		 * 备注: 时,分,秒,毫秒都为0.
		 * @return
		 */
		public DateModel getStartOfDay() {
			return setHour(0).setMinute(0).setSecond(0).setMilliSecond(0);
		}
		
		/**
		 * 返回天的结束日期.<br/>
		 * 备注: 时=23; 分=59; 秒=59; 毫秒=999.
		 * @return
		 */
		public DateModel getEndOfDay() {
			return setHour(MAX_HOUR).setMinute(MAX_MIN_SEC).setSecond(MAX_MIN_SEC).setMilliSecond(MAX_MILLIS);
		}
		
		/**
		 * 返回周的开始日期.<br/>
		 * 备注: 时,分,秒,毫秒都为0.
		 * @return
		 */
		public DateModel getStartOfWeek() {
			return setFirstDayOfWeek(Calendar.MONDAY).setHour(0).setMinute(0).setSecond(0).setMilliSecond(0).setDayOfWeek(Calendar.MONDAY);
		}
		
		/**
		 * 返回周的结束日期.<br/>
		 * 备注: 时=23; 分=59; 秒=59; 毫秒=999.
		 * @return
		 */
		public DateModel getEndOfWeek() {
			return setFirstDayOfWeek(Calendar.MONDAY).setHour(MAX_HOUR).setMinute(MAX_MIN_SEC).setSecond(MAX_MIN_SEC).setMilliSecond(MAX_MILLIS).setDayOfWeek(Calendar.SUNDAY);
		}
		
		/**
		 * 返回月的开始日期.<br/>
		 * 备注: 时,分,秒,毫秒都为0, 天为1.
		 * @return
		 */
		public DateModel getStartOfMonth() {
			return setDay(1).getStartOfDay();
		}
		
		/**
		 * 返回月的结束日期.<br/>
		 * 备注: 时=23; 分=59; 秒=59; 毫秒=999, 天为当月最后一天.
		 * @return
		 */
		public DateModel getEndOfMonth() {
			return setDay(calendar.getActualMaximum(Calendar.DAY_OF_MONTH)).getEndOfDay();
		}
		
		/**
		 * 返回一个<code>DateModel</code>对象,此对象用指定的date设置<code>Calendar</code>的时间.
		 * @param date		指定的日期
		 * @return
		 */
		public DateModel reset(Date date) {
			if (null == date) {
				throw new NullPointerException();
			}
			calendar.setTime(date);
			return this;
		}
		
		public DateModel reset(DateModel dm) {
			calendar.setTime(dm.getTime());
			return this;
		}
		
		/**
		 * 返回一个日期对象,代表<code>Calendar</code>时间值.
		 * @return
		 */
		public Date getTime() {
			return calendar.getTime();
		}
		
		public Timestamp getTimestamp() {
			return new Timestamp(calendar.getTimeInMillis());
		}
		
		public long getTimeInMillis() {
			return calendar.getTimeInMillis();
		}
		
		public Time getSqlTime() {
            setYear(1970).setMonth(1).setDay(1);
            Time time = new Time(calendar.getTimeInMillis());
            return time;
        }
		
		/**
		 * 格式化一个日期, 自定义样式为HH:mm:ss.
		 * @see DateFormatConstant#TIME
		 */
		public String toTimeString() {
			return CADateConstant.TIME.format(getTime());
		}
		
		/**
		 * 格式化一个日期, 自定义样式为yyyy-MM-dd.
		 * @see DateFormatConstant#DAY
		 */
		public String toDateString() {
			return CADateConstant.DAY.format(getTime());
		}
		
		/**
		 * 格式化一个日期, 自定义样式为yyyy-MM-dd HH:mm:ss.
		 * @see DateFormatConstant#TIMESTAMP
		 */
		public String toString() {
			return CADateConstant.TIMESTAMP.format(getTime());
		}
		
		/**
		 * 构建一个新实例.
		 */
		DateModel() {
			this(new Date());
		}
		
		/**
		 * 用指定的内容构建一个日期实例.
		 * <p>note that: 如果date是null, 将使用当前日期.</p>
		 * @param date
		 */
		DateModel(Date date) {
			calendar = Calendar.getInstance();
			this.date = date;
			if (null != date) {
				calendar.setTime(date);
			}
		}
		
		/**
		 * 构建一个实例,使用指定的时间.
		 * @param time
		 */
		DateModel(long time) {
			calendar = Calendar.getInstance();
			calendar.setTimeInMillis(time);
		}
		
		/**
		 * 构建一个实例,使用指定的时间.
		 * @param time
		 */
		DateModel(Timestamp timestamp) {
			calendar = Calendar.getInstance();
			if (null != timestamp) {
				calendar.setTimeInMillis(timestamp.getTime());
			}
		}
	}
	
	/**
	 * 返回一个新的DateModel对象, 默认时间为当前时间.
	 * @return
	 */
	public static DateModel now() {
		return new DateModel();
	}
	
	/**
	 * 返回一个新的DateModel对象, 默认时间为指定时间.
	 * @return
	 */
	public static DateModel date(Date date) {
		return new DateModel(date);
	}
	
	/**
	 * 比较date时间值, 是否大于等于lDate, 或者小于等于rDate.
	 * @param date
	 * @param lDate		开始时间
	 * @param rDate     结束时间
	 * @return
	 */
	public static boolean isBetweenDate(Date date, Date lDate, Date rDate) {
		return date.getTime() >= lDate.getTime() ? date.getTime() <= rDate.getTime() : false;
	}
	
	/**
	 * 比较date时间值, 是否大于等于lDate, 或者小于等于rDate. <br/>
	 * 注意：此方法只比较时,分,秒. 忽略年, 月, 日.
	 * @param date
	 * @param lDate		开始时间
	 * @param rDate		结束时间
	 * @return
	 */
	public static boolean isBetweenTime(Date date, Date lDate, Date rDate) {
		return isBetweenDate(new DateModel(date).getSqlTime(), new DateModel(lDate).getSqlTime(), new DateModel(rDate).getSqlTime());
	}
	
	/**
	 * 返回一个字符按照指定的模式转化的日期.
	 * @param s
	 * @param pattern		模式
	 * @return
	 * @throws ParseException
	 */
	public static Date parse(String s, String pattern) throws ParseException {
		if (StringUtils.isEmpty(s)) return null;
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.parse(s);
	}
	
	/**
	 * 返回一个字符按照指定的模式转化的日期.<br/>
	 * @param s
	 * @return
	 * @see DateFormatConstant#PATTERN_DAY
	 * @throws ParseException
	 */
	public static Date parseDay(String s) throws ParseException {
		try {
			return parse(s, CADateConstant.PATTERN_DAY);
		} catch (Exception e) {
		}
		try {
			return parse(s, "yyyy.MM.dd");
		} catch (Exception e) {
		}
		try {
			return parse(s, "yyyy.MM");
		} catch (Exception e) {
		}
		try {
			return parse(s, "yyyy-MM");
		} catch (Exception e) {
			throw new ParseException("can not udstand your format", -1);
		}
	}
	
	/**
	 * 返回一个字符按照指定的模式转化的日期.<br/>
	 * @param s
	 * @return
	 * @see DateFormatConstant#PATTERN_TIMESTAMP
	 * @throws ParseException
	 */
	public static Date parseTimsStamp(String s) throws ParseException {
		return parse(s, CADateConstant.PATTERN_TIMESTAMP);
	}
	
	/**
	 * 格式化一个日期对象为一个字符.
	 * @param date
	 * @param pattern		模式
	 * @return		格式化的字符串
	 */
	public static String format(Date date, String pattern) {
		if (null == date) {
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}
	
	/**
	 * 格式化一个日期对象为一个字符.<br/>
	 * @param date
	 * @see DateFormatConstant#PATTERN_DAY
	 * @return
	 */
	public static String formatDay(Date date) {
		return format(date, CADateConstant.PATTERN_DAY);
	}
	
	/**
	 * 格式化一个日期对象为一个字符.
	 * @param date
	 * @see DateFormatConstant#PATTERN_TIMESTAMP
	 * @return
	 */
	public static String formatTimeStamp(Date date) {
		return format(date, CADateConstant.PATTERN_TIMESTAMP);
	}
	
	/**
	 * 格式化一个日期对象为一个字符.
	 * @param date
	 * @see DateFormatConstant#PATTERN_TIME
	 * @return
	 */
	public static String formatSqlTime(Date date) {
		return format(date, CADateConstant.PATTERN_TIME);
	}
	
	/**
	 * 返回一个合法的ksql字符串.
	 * <pre>
	 * 	<h5>Example:</h5>
	 *        String ksql = getCommonSql("fbizdate", "2012-1-1 00:00:00", "fsaledate", "2012-1-31 23:59:59");
	 *        result = " (fbizdate >= {ts'2012-1-1 00:00:00'} and fsaledate <= {ts'2012-1-31 23:59:59'})";
	 * </pre>
	 * @param lField
	 * 		左字段名
	 * @param lDateStr
	 * 		左日期
	 * @param rField
	 * 		右字段名
	 * @param rDateStr
	 * 		右日期
	 * @return
	 */
	public static String getCommonSql(String lField, String lDateStr, String rField, String rDateStr) {
		StringBuffer sql = new StringBuffer();
		sql.append(" (").append(lField).append(" >= {ts'").append(lDateStr).append("'} ").append("and ").append(rField).append(" <={ts'").append(rDateStr).append("'} )");
		return sql.toString();
	}
	
	/**
	 * 返回一个合法的ksql字符串.
	 * @see {@link #getCommonSql(String, String, String, String)}
	 */
	public static String getCommonSql(String field, String lDateStr, String rDateStr) {
		StringBuffer sql = new StringBuffer();
		sql.append(" (").append(field).append(" >= {ts'").append(lDateStr).append("'} ").append("and ").append(field).append(" <={ts'").append(rDateStr).append("'} )");
		return sql.toString();
	}
	
	/**
	 * 返回一个合法的ksql字符串.
	 * @param lField
	 * @param lDate
	 * 		当前
	 * @param rField
	 * @param rDate
	 * 		当前月的最后一天.
	 * @see {@link #getCommonSql(String, String, String, String)}
	 */
	public static String getCommonSql(String lField, Date lDate, String rField, Date rDate) {
		return getCommonSql(lField, new DateModel(lDate).getStartOfDay().toString(), rField, new DateModel(rDate).getEndOfDay().toString());
	}
	
	/**
	 * 返回一个合法的ksql字符串.
	 * @see {@link #getCommonSql(String, Date, String, Date)}
	 */
	public static String getCommonSql(String field, Date lDate, Date rDate) {
		return getCommonSql(field, lDate, field, rDate);
	}
	
	/**
	 * 获取应用服务器当前时间，支持客户端和服务端调用
     * @author:hongguang_wang
	 * @param ctx
	 *            服务端上下文，客户端调用时请传入NULL
	 * @return Date
	 * @throws EASBizException
	 */
	public static Date serverDate(Context ctx) throws EASBizException {
		Date serverDate = null;
		if (ctx == null) {
			serverDate = UserMonitorFactory.getRemoteInstance().getCurrentTime();
		} else {
			serverDate = UserMonitorFactory.getLocalInstance(ctx).getCurrentTime();
		}
		return serverDate;
	}
	
	/**
	 * 获得当前服务器日期,不带时间,格式为yyyy-MM-dd.
	 * @return
	 * @throws EASBizException
	 * @throws ParseException
	 */
	public static Date getServerDate(Context ctx) throws EASBizException, ParseException{
		Date serverDate = null;
		if (ctx == null) {
			serverDate = UserMonitorFactory.getRemoteInstance().getCurrentTime();
		} else {
			serverDate = UserMonitorFactory.getLocalInstance(ctx).getCurrentTime();
		}		
		
		Date curDate = CADateHelper.parseDay(CADateHelper.formatDay(serverDate));
		return curDate;
	}
	
	public static Date getServerDate2(Context ctx) throws EASBizException, ParseException{
		Date serverDate = null;
		if (ctx == null) {
			serverDate = UserMonitorFactory.getRemoteInstance().getCurrentTime();
		} else {
			serverDate = UserMonitorFactory.getLocalInstance(ctx).getCurrentTime();
		}		
		
		return serverDate;
	}
	
	/**
	 * 描述：某天的开始时间，yyyy-MM-dd 00:00:00.000
	 * 		date为空，默认为当前日期
	 * @param date java.util.Date 源日期
	 * @return 目标日期
	 */
	public static Date startOfDay(Date date){
		return date(date).getStartOfDay().getTime();
	}
	
	/**
	 *描述：某天的结束时间，yyyy-MM-dd 23:59:59.999
	 *		date为空，默认为当前日期
	 * @param date java.util.Date 源日期
	 * @return 目标日期
	 */
	public static Date endOfDay(Date date){
		return date(date).getEndOfDay().getTime();
	}
	
	/**
	 *描述：以某天为基准的所在月第一天的开始时间，yyyy-MM-01 00:00:00.000
	 *		date为空，默认为当前日期
	 * @param date 源日期
	 * @return  目标日期
	 */
	public static Date startOfMonth(Date date){
		return date(date).getStartOfMonth().getTime();
	}
	
	/**
	 *描述： 以某天为基准的所在月第一天的结束时间，yyyy-MM-XX 23:59:59.999，
	 * 		 其中XX为所在月的最后一天。date为空，默认为当前日期
	 * @param date 源日期
	 * @return	目标日期
	 */
	public static Date endOfMonth(Date date){
		return date(date).getEndOfMonth().getTime();
	}
	
	/**
	 * 描述：指定日期上一周的开始日期.
	 * @return
	 */
	public static Date startOfPreWeek(Date date) {
		//本周的第一天的上一天.
		Date dd = date(date).getStartOfWeek().addDay(-1).getTime();
		return date(dd).getStartOfWeek().getTime();
	}
	
	/**
	 * 描述：当前日期上一周的结束日期.
	 * @return
	 */
	public static Date endOfPreWeek(Date date) {
		Date dd = date(date).getStartOfWeek().addDay(-1).getTime();
		return date(dd).getEndOfWeek().getTime();
	}
	
	/**
	 * 描述：指定日期下一周的开始日期.
	 * @return
	 */
	public static Date startOfNextWeek(Date date) {
		//本周的最后一天在加一天.
		Date dd = date(date).getEndOfWeek().addDay(1).getTime();
		return date(dd).getStartOfWeek().getTime();
	}
	
	/**
	 * 描述：当前日期下一周的结束日期.
	 * @return
	 */
	public static Date endOfNextWeek(Date date) {
		Date dd = date(date).getEndOfWeek().addDay(1).getTime();
		return date(dd).getEndOfWeek().getTime();
	}

    
   /**
    * 描述： 获取某个日期所在月的最后一天的日期
    * @param theDate
    * @return
    */
    public static String getLastDay(Date theDate) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance(); 
        cal.setTime(theDate); 
        cal.set(Calendar.DAY_OF_MONTH, 1); 
        cal.roll(Calendar.DAY_OF_MONTH, -1); 
	    return df.format(cal.getTime())+" 23:59:59";
    }
    
    /**
     * 计算日期相差天数
     * 
     * @param beginDate 开始时间
     * @param endDate 结束时间
     * @return 相差天数.
     */
    public static int subTwoDate(Date beginDate,Date endDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(beginDate);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long slong = cal.getTimeInMillis();

        cal.setTime(endDate);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long elong = cal.getTimeInMillis();

        return (int) ((elong - slong) / (1000 * 3600 * 24));
    }
    
    /**
     * 根据出生日期计算年龄
     * @param birth 出生日期
     * @return
     */
    public static int calculateAge(Date birth) {
    	DateModel now = now();
    	if (now.getTime().before(birth)) {
    		throw new IllegalArgumentException("The birthday must be after now.");
    	}
    	//取出系统当前时间的年、月、日部分
    	int yearNow  = now.getYear();
    	int monthNow = now.getMonth();
    	int dayOfMonthNow = now.getDay();
    	
    	DateModel birthday = date(birth);
    	int yearBirth = birthday.getYear();
    	int monthBirth = birthday.getMonth();
    	int dayOfMonthBirth = birthday.getDay();
    	
    	//当前年份与出生年份相减，初步计算年龄
        int age = yearNow - yearBirth;
        
        //当前月份与出生日期的月份相比，如果月份小于出生月份，则年龄上减1，表示不满多少周岁
        if (monthNow <= monthBirth) { 
        	//如果月份相等，在比较日期，如果当前日，小于出生日，也减1，表示不满多少周岁
        	if (monthNow == monthBirth) {
        		if (dayOfMonthNow < dayOfMonthBirth) age--;
        	} else {
        		age--;
        	}
        }
        
        return age;
    }
}
