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
 * ʱ�丨����.
 * <p>
 * 	note that: �����޸������ע��.
 * </p>
 * @author jason qu
 *
 */
public class CADateHelper {
public static final int MILLIS_OF_DAY = 86400000;
	
	/**
	 * 
	 * ע�⣺
	 * <ul>
	 * 	<li>���ܶ��̲߳�������Ķ���,����������Ԥ��.
	 *  <li>month��������/�õ����·��Ǵ�1��ʼ��Calendar�ṩ��API�Ǵ�0��ʼ��ʹ��ʱ����ע���
	 * </ul>
	 * @author jason qu
	 */
	public static class DateModel {
		/**���ĺ���ֵ	999999999*/
		private static final int MAX_NANOS = 999999999;
		private static final int MAX_HOUR = 23;
		private static final int MAX_MIN_SEC = 59;
		private static final int MAX_MILLIS = 999;
		private Calendar calendar = null;
		private Date date = null;
		
		/**
		 * ������Ϊָ����ֵ.
		 * @param year
		 * @see Calendar#YEAR
		 * @return
		 */
		public DateModel setYear(int year) {
			calendar.set(Calendar.YEAR, year);
			return this;
		}
		
		/**
		 * ������Ϊָ����ֵ.
		 * @param month
		 * @see Calendar#MONTH
		 * @return
		 */
		public DateModel setMonth(int month) {
			calendar.set(Calendar.MONTH, month - 1);
			return this;
		}
		
		/**
		 * ������Ϊָ����ֵ.
		 * @param day
		 * @see Calendar#DAY_OF_MONTH
		 * @return
		 */
		public DateModel setDay(int day) {
			calendar.set(Calendar.DAY_OF_MONTH, day);
			return this;
		}
		
		/**
		 * ����ʱΪָ����ֵ.
		 * @param hour
		 * @see Calendar#HOUR_OF_DAY
		 * @return
		 */
		public DateModel setHour(int hour) {
			calendar.set(Calendar.HOUR_OF_DAY, hour);
			return this;
		}
		
		/**
		 * ���÷�Ϊָ����ֵ.
		 * @param minute
		 * @see Calendar#MINUTE
		 * @return
		 */
		public DateModel setMinute(int minute) {
			calendar.set(Calendar.MINUTE, minute);
			return this;
		}
		
		/**
		 * ������Ϊָ����ֵ.<br/>
		 * @param second
		 * @see Calendar#SECOND
		 * @return
		 */
		public DateModel setSecond(int second) {
			calendar.set(Calendar.SECOND, second);
			return this;
		}
		
		/**
		 * �����ܵĵ�һ��.
		 * @param value
		 * @return
		 */
		public DateModel setFirstDayOfWeek(int value) {
			calendar.setFirstDayOfWeek(value);
			return this;
		}
		
		/**
		 * ���ú���Ϊָ����ֵ.<br/>
		 * @param milliSecond
		 * @see Calendar#MILLISECOND
		 * @return
		 */
		public DateModel setMilliSecond(int milliSecond) {
			calendar.set(Calendar.MILLISECOND, milliSecond);
			return this;
		}
		
		/**
		 * �����ܶ�Ӧ����.
		 * 
		 * @param value
		 * @return
		 */
		public DateModel setDayOfWeek(int value) {
			calendar.set(Calendar.DAY_OF_WEEK, value);
			return this;
		}
		
		/**
		 * ���ָ����������.
		 * @param year
		 * @see Calendar#YEAR
		 * @return
		 */
		public DateModel addYear(int year) {
			calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + year);
			return this;
		}
		
		/**
		 * ���ָ����������.
		 * @param month
		 * @see Calendar#MONTH
		 * @return
		 */
		public DateModel addMonth(int month) {
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + month);
			return this;
		}
		
		/**
		 * ���ָ����������.
		 * @param day
		 * @see Calendar#DAY_OF_MONTH
		 * @return
		 */
		public DateModel addDay(int day) {
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + day);
			return this;
		}
		
		/**
		 * ���ָ����Сʱ.
		 * @param hour
		 * @see Calendar#HOUR_OF_DAY
		 * @return
		 */
		public DateModel addHour(int hour) {
			calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + hour);
			return this;
		}
		
		/**
		 * ���ָ���ķ���.
		 * @param minute
		 * @see Calendar#MINUTE
		 * @return
		 */
		public DateModel addMinute(int minute) {
			calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + minute);
			return this;
		}
		
		/**
		 * ���ָ������.
		 * @param second
		 * @see Calendar#SECOND
		 * @return
		 */
		public DateModel addSecond(int second) {
			calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) + second);
			return this;
		}
		
		/**
		 * ���ָ���ĺ���.
		 * @param milliSecond
		 * @see Calendar#MILLISECOND
		 * @return
		 */
		public DateModel addMilliSecond(int milliSecond) {
			calendar.set(Calendar.MILLISECOND, calendar.get(Calendar.MILLISECOND) + milliSecond);
			return this;
		}
		
		/**
		 * ��������Ϣ.
		 * @see Calendar#YEAR
		 * @return
		 */
		public int getYear() {
			return calendar.get(Calendar.YEAR);
		}
		
		/**
		 * ��������Ϣ.
		 * @see Calendar#MONTH
		 * @return
		 */
		public int getMonth() {
			return calendar.get(Calendar.MONTH) + 1;
		}
		
		/**
		 * ��������Ϣ.
		 * @see Calendar#DAY_OF_MONTH
		 * @return
		 */
		public int getDay() {
			return calendar.get(Calendar.DAY_OF_MONTH);
		}
		
		/**
		 * ����ʱ��Ϣ.
		 * @see Calendar#HOUR_OF_DAY
		 * @return
		 */
		public int getHour() {
			return calendar.get(Calendar.HOUR_OF_DAY);
		}
		
		/**
		 * ���ط���Ϣ.
		 * @see Calendar#MINUTE
		 * @return
		 */
		public int getMinute() {
			return calendar.get(Calendar.MINUTE);
		}
		
		/**
		 * ��������Ϣ.
		 * @see Calendar#SECOND
		 * @return
		 */
		public int getSecond() {
			return calendar.get(Calendar.SECOND);
		}
		
		/**
		 * ���غ�����Ϣ.
		 * @see Calendar#MILLISECOND
		 * @return
		 */
		public int getMilliSecond() {
			return calendar.get(Calendar.MILLISECOND);
		}
		
		/**
		 * ���ؼ�����Ϣ.
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
		 * ������Ŀ�ʼ����.<br/>
		 * ��ע: ʱ,��,��,���붼Ϊ0.
		 * @return
		 */
		public DateModel getStartOfDay() {
			return setHour(0).setMinute(0).setSecond(0).setMilliSecond(0);
		}
		
		/**
		 * ������Ľ�������.<br/>
		 * ��ע: ʱ=23; ��=59; ��=59; ����=999.
		 * @return
		 */
		public DateModel getEndOfDay() {
			return setHour(MAX_HOUR).setMinute(MAX_MIN_SEC).setSecond(MAX_MIN_SEC).setMilliSecond(MAX_MILLIS);
		}
		
		/**
		 * �����ܵĿ�ʼ����.<br/>
		 * ��ע: ʱ,��,��,���붼Ϊ0.
		 * @return
		 */
		public DateModel getStartOfWeek() {
			return setFirstDayOfWeek(Calendar.MONDAY).setHour(0).setMinute(0).setSecond(0).setMilliSecond(0).setDayOfWeek(Calendar.MONDAY);
		}
		
		/**
		 * �����ܵĽ�������.<br/>
		 * ��ע: ʱ=23; ��=59; ��=59; ����=999.
		 * @return
		 */
		public DateModel getEndOfWeek() {
			return setFirstDayOfWeek(Calendar.MONDAY).setHour(MAX_HOUR).setMinute(MAX_MIN_SEC).setSecond(MAX_MIN_SEC).setMilliSecond(MAX_MILLIS).setDayOfWeek(Calendar.SUNDAY);
		}
		
		/**
		 * �����µĿ�ʼ����.<br/>
		 * ��ע: ʱ,��,��,���붼Ϊ0, ��Ϊ1.
		 * @return
		 */
		public DateModel getStartOfMonth() {
			return setDay(1).getStartOfDay();
		}
		
		/**
		 * �����µĽ�������.<br/>
		 * ��ע: ʱ=23; ��=59; ��=59; ����=999, ��Ϊ�������һ��.
		 * @return
		 */
		public DateModel getEndOfMonth() {
			return setDay(calendar.getActualMaximum(Calendar.DAY_OF_MONTH)).getEndOfDay();
		}
		
		/**
		 * ����һ��<code>DateModel</code>����,�˶�����ָ����date����<code>Calendar</code>��ʱ��.
		 * @param date		ָ��������
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
		 * ����һ�����ڶ���,����<code>Calendar</code>ʱ��ֵ.
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
		 * ��ʽ��һ������, �Զ�����ʽΪHH:mm:ss.
		 * @see DateFormatConstant#TIME
		 */
		public String toTimeString() {
			return CADateConstant.TIME.format(getTime());
		}
		
		/**
		 * ��ʽ��һ������, �Զ�����ʽΪyyyy-MM-dd.
		 * @see DateFormatConstant#DAY
		 */
		public String toDateString() {
			return CADateConstant.DAY.format(getTime());
		}
		
		/**
		 * ��ʽ��һ������, �Զ�����ʽΪyyyy-MM-dd HH:mm:ss.
		 * @see DateFormatConstant#TIMESTAMP
		 */
		public String toString() {
			return CADateConstant.TIMESTAMP.format(getTime());
		}
		
		/**
		 * ����һ����ʵ��.
		 */
		DateModel() {
			this(new Date());
		}
		
		/**
		 * ��ָ�������ݹ���һ������ʵ��.
		 * <p>note that: ���date��null, ��ʹ�õ�ǰ����.</p>
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
		 * ����һ��ʵ��,ʹ��ָ����ʱ��.
		 * @param time
		 */
		DateModel(long time) {
			calendar = Calendar.getInstance();
			calendar.setTimeInMillis(time);
		}
		
		/**
		 * ����һ��ʵ��,ʹ��ָ����ʱ��.
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
	 * ����һ���µ�DateModel����, Ĭ��ʱ��Ϊ��ǰʱ��.
	 * @return
	 */
	public static DateModel now() {
		return new DateModel();
	}
	
	/**
	 * ����һ���µ�DateModel����, Ĭ��ʱ��Ϊָ��ʱ��.
	 * @return
	 */
	public static DateModel date(Date date) {
		return new DateModel(date);
	}
	
	/**
	 * �Ƚ�dateʱ��ֵ, �Ƿ���ڵ���lDate, ����С�ڵ���rDate.
	 * @param date
	 * @param lDate		��ʼʱ��
	 * @param rDate     ����ʱ��
	 * @return
	 */
	public static boolean isBetweenDate(Date date, Date lDate, Date rDate) {
		return date.getTime() >= lDate.getTime() ? date.getTime() <= rDate.getTime() : false;
	}
	
	/**
	 * �Ƚ�dateʱ��ֵ, �Ƿ���ڵ���lDate, ����С�ڵ���rDate. <br/>
	 * ע�⣺�˷���ֻ�Ƚ�ʱ,��,��. ������, ��, ��.
	 * @param date
	 * @param lDate		��ʼʱ��
	 * @param rDate		����ʱ��
	 * @return
	 */
	public static boolean isBetweenTime(Date date, Date lDate, Date rDate) {
		return isBetweenDate(new DateModel(date).getSqlTime(), new DateModel(lDate).getSqlTime(), new DateModel(rDate).getSqlTime());
	}
	
	/**
	 * ����һ���ַ�����ָ����ģʽת��������.
	 * @param s
	 * @param pattern		ģʽ
	 * @return
	 * @throws ParseException
	 */
	public static Date parse(String s, String pattern) throws ParseException {
		if (StringUtils.isEmpty(s)) return null;
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.parse(s);
	}
	
	/**
	 * ����һ���ַ�����ָ����ģʽת��������.<br/>
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
	 * ����һ���ַ�����ָ����ģʽת��������.<br/>
	 * @param s
	 * @return
	 * @see DateFormatConstant#PATTERN_TIMESTAMP
	 * @throws ParseException
	 */
	public static Date parseTimsStamp(String s) throws ParseException {
		return parse(s, CADateConstant.PATTERN_TIMESTAMP);
	}
	
	/**
	 * ��ʽ��һ�����ڶ���Ϊһ���ַ�.
	 * @param date
	 * @param pattern		ģʽ
	 * @return		��ʽ�����ַ���
	 */
	public static String format(Date date, String pattern) {
		if (null == date) {
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}
	
	/**
	 * ��ʽ��һ�����ڶ���Ϊһ���ַ�.<br/>
	 * @param date
	 * @see DateFormatConstant#PATTERN_DAY
	 * @return
	 */
	public static String formatDay(Date date) {
		return format(date, CADateConstant.PATTERN_DAY);
	}
	
	/**
	 * ��ʽ��һ�����ڶ���Ϊһ���ַ�.
	 * @param date
	 * @see DateFormatConstant#PATTERN_TIMESTAMP
	 * @return
	 */
	public static String formatTimeStamp(Date date) {
		return format(date, CADateConstant.PATTERN_TIMESTAMP);
	}
	
	/**
	 * ��ʽ��һ�����ڶ���Ϊһ���ַ�.
	 * @param date
	 * @see DateFormatConstant#PATTERN_TIME
	 * @return
	 */
	public static String formatSqlTime(Date date) {
		return format(date, CADateConstant.PATTERN_TIME);
	}
	
	/**
	 * ����һ���Ϸ���ksql�ַ���.
	 * <pre>
	 * 	<h5>Example:</h5>
	 *        String ksql = getCommonSql("fbizdate", "2012-1-1 00:00:00", "fsaledate", "2012-1-31 23:59:59");
	 *        result = " (fbizdate >= {ts'2012-1-1 00:00:00'} and fsaledate <= {ts'2012-1-31 23:59:59'})";
	 * </pre>
	 * @param lField
	 * 		���ֶ���
	 * @param lDateStr
	 * 		������
	 * @param rField
	 * 		���ֶ���
	 * @param rDateStr
	 * 		������
	 * @return
	 */
	public static String getCommonSql(String lField, String lDateStr, String rField, String rDateStr) {
		StringBuffer sql = new StringBuffer();
		sql.append(" (").append(lField).append(" >= {ts'").append(lDateStr).append("'} ").append("and ").append(rField).append(" <={ts'").append(rDateStr).append("'} )");
		return sql.toString();
	}
	
	/**
	 * ����һ���Ϸ���ksql�ַ���.
	 * @see {@link #getCommonSql(String, String, String, String)}
	 */
	public static String getCommonSql(String field, String lDateStr, String rDateStr) {
		StringBuffer sql = new StringBuffer();
		sql.append(" (").append(field).append(" >= {ts'").append(lDateStr).append("'} ").append("and ").append(field).append(" <={ts'").append(rDateStr).append("'} )");
		return sql.toString();
	}
	
	/**
	 * ����һ���Ϸ���ksql�ַ���.
	 * @param lField
	 * @param lDate
	 * 		��ǰ
	 * @param rField
	 * @param rDate
	 * 		��ǰ�µ����һ��.
	 * @see {@link #getCommonSql(String, String, String, String)}
	 */
	public static String getCommonSql(String lField, Date lDate, String rField, Date rDate) {
		return getCommonSql(lField, new DateModel(lDate).getStartOfDay().toString(), rField, new DateModel(rDate).getEndOfDay().toString());
	}
	
	/**
	 * ����һ���Ϸ���ksql�ַ���.
	 * @see {@link #getCommonSql(String, Date, String, Date)}
	 */
	public static String getCommonSql(String field, Date lDate, Date rDate) {
		return getCommonSql(field, lDate, field, rDate);
	}
	
	/**
	 * ��ȡӦ�÷�������ǰʱ�䣬֧�ֿͻ��˺ͷ���˵���
     * @author:hongguang_wang
	 * @param ctx
	 *            ����������ģ��ͻ��˵���ʱ�봫��NULL
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
	 * ��õ�ǰ����������,����ʱ��,��ʽΪyyyy-MM-dd.
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
	 * ������ĳ��Ŀ�ʼʱ�䣬yyyy-MM-dd 00:00:00.000
	 * 		dateΪ�գ�Ĭ��Ϊ��ǰ����
	 * @param date java.util.Date Դ����
	 * @return Ŀ������
	 */
	public static Date startOfDay(Date date){
		return date(date).getStartOfDay().getTime();
	}
	
	/**
	 *������ĳ��Ľ���ʱ�䣬yyyy-MM-dd 23:59:59.999
	 *		dateΪ�գ�Ĭ��Ϊ��ǰ����
	 * @param date java.util.Date Դ����
	 * @return Ŀ������
	 */
	public static Date endOfDay(Date date){
		return date(date).getEndOfDay().getTime();
	}
	
	/**
	 *��������ĳ��Ϊ��׼�������µ�һ��Ŀ�ʼʱ�䣬yyyy-MM-01 00:00:00.000
	 *		dateΪ�գ�Ĭ��Ϊ��ǰ����
	 * @param date Դ����
	 * @return  Ŀ������
	 */
	public static Date startOfMonth(Date date){
		return date(date).getStartOfMonth().getTime();
	}
	
	/**
	 *������ ��ĳ��Ϊ��׼�������µ�һ��Ľ���ʱ�䣬yyyy-MM-XX 23:59:59.999��
	 * 		 ����XXΪ�����µ����һ�졣dateΪ�գ�Ĭ��Ϊ��ǰ����
	 * @param date Դ����
	 * @return	Ŀ������
	 */
	public static Date endOfMonth(Date date){
		return date(date).getEndOfMonth().getTime();
	}
	
	/**
	 * ������ָ��������һ�ܵĿ�ʼ����.
	 * @return
	 */
	public static Date startOfPreWeek(Date date) {
		//���ܵĵ�һ�����һ��.
		Date dd = date(date).getStartOfWeek().addDay(-1).getTime();
		return date(dd).getStartOfWeek().getTime();
	}
	
	/**
	 * ��������ǰ������һ�ܵĽ�������.
	 * @return
	 */
	public static Date endOfPreWeek(Date date) {
		Date dd = date(date).getStartOfWeek().addDay(-1).getTime();
		return date(dd).getEndOfWeek().getTime();
	}
	
	/**
	 * ������ָ��������һ�ܵĿ�ʼ����.
	 * @return
	 */
	public static Date startOfNextWeek(Date date) {
		//���ܵ����һ���ڼ�һ��.
		Date dd = date(date).getEndOfWeek().addDay(1).getTime();
		return date(dd).getStartOfWeek().getTime();
	}
	
	/**
	 * ��������ǰ������һ�ܵĽ�������.
	 * @return
	 */
	public static Date endOfNextWeek(Date date) {
		Date dd = date(date).getEndOfWeek().addDay(1).getTime();
		return date(dd).getEndOfWeek().getTime();
	}

    
   /**
    * ������ ��ȡĳ�����������µ����һ�������
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
     * ���������������
     * 
     * @param beginDate ��ʼʱ��
     * @param endDate ����ʱ��
     * @return �������.
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
     * ���ݳ������ڼ�������
     * @param birth ��������
     * @return
     */
    public static int calculateAge(Date birth) {
    	DateModel now = now();
    	if (now.getTime().before(birth)) {
    		throw new IllegalArgumentException("The birthday must be after now.");
    	}
    	//ȡ��ϵͳ��ǰʱ����ꡢ�¡��ղ���
    	int yearNow  = now.getYear();
    	int monthNow = now.getMonth();
    	int dayOfMonthNow = now.getDay();
    	
    	DateModel birthday = date(birth);
    	int yearBirth = birthday.getYear();
    	int monthBirth = birthday.getMonth();
    	int dayOfMonthBirth = birthday.getDay();
    	
    	//��ǰ����������������������������
        int age = yearNow - yearBirth;
        
        //��ǰ�·���������ڵ��·���ȣ�����·�С�ڳ����·ݣ��������ϼ�1����ʾ������������
        if (monthNow <= monthBirth) { 
        	//����·���ȣ��ڱȽ����ڣ������ǰ�գ�С�ڳ����գ�Ҳ��1����ʾ������������
        	if (monthNow == monthBirth) {
        		if (dayOfMonthNow < dayOfMonthBirth) age--;
        	} else {
        		age--;
        	}
        }
        
        return age;
    }
}
