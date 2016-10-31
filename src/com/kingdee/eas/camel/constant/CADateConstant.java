package com.kingdee.eas.camel.constant;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * 描述：时间常量的定义.
 * @author jason qu
 * 
 */
public class CADateConstant {
	/**yyyy-MM-dd*/
    public static final String PATTERN_DAY = "yyyy-MM-dd";
    /**yyyy-MM-dd HH:mm:ss*/
    public static final String PATTERN_TIMESTAMP = "yyyy-MM-dd HH:mm:ss";
    /**HH:mm:ss*/
    public static final String PATTERN_TIME = "HH:mm:ss";
    /**yyyy-MM*/
    public static final String PATTERN_MONTH = "yyyy-MM";
    /**yyyy年MM月*/
    public static final String PATTERN_MONTH_ZH = "yyyy年MM月";
    /**yyyy*/
    public static final String PATTERN_YEAR = "yyyy";
    
	/**yyyy-MM-dd*/
	public static final DateFormat DAY = new SimpleDateFormat(PATTERN_DAY);
	/**yyyy-MM-dd 23:59:59*/
    public static final DateFormat DAY_END = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
    /**yyyy-MM-dd 00:00:00*/
    public static final DateFormat DAY_BEGIN = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
    /**yyyy-MM-dd HH:mm:ss*/
    public static final DateFormat TIMESTAMP = new SimpleDateFormat(PATTERN_TIMESTAMP);
    /**HH:mm:ss*/
    public static final DateFormat TIME = new SimpleDateFormat(PATTERN_TIME);
    /**yyyy-MM*/
    public static final DateFormat MONTH = new SimpleDateFormat(PATTERN_MONTH);
    /**yyyy年MM月*/
    public static final DateFormat MONTH_ZH = new SimpleDateFormat(PATTERN_MONTH_ZH);
    /**yyyy*/
    public static final DateFormat YEAR = new SimpleDateFormat(PATTERN_YEAR);
}
