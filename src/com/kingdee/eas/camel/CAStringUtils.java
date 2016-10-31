package com.kingdee.eas.camel;

import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CAStringUtils {
	
	/**  
    * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为1,英文字符长度为0.5  
    * @param String s 需要得到长度的字符串  
    * @return int 得到的字符串长度  
    */
	public static double getLength(String s) {
		double valueLength = 0; 
		String chinese = "[\u4e00-\u9fa5]"; 
		// 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 
		for (int i = 0; i < s.length(); i++) { 
			// 获取一个字符
			String temp = s.substring(i, i + 1);
			// 判断是否为中文字符  
			if (temp.matches(chinese)) {  
				// 中文字符长度为1 
				valueLength += 1; 
			} else {  
				// 其他字符长度为0.5 
				valueLength += 0.5;
			}
		}
		
		return  Math.ceil(valueLength);  
	}
	
	/**
	 * <p>Removes the string any charactor exclude digit.</p>
	 * <pre>
	 * CAStringUtils.str2Num("test10")           = 10;
	 * CAStringUtils.str2Num("test")             = "";
	 * CAStringUtils.str2Num("1test 20")         = 120;
	 * </pre>
	 * @param str
	 * @return
	 */
	public static String str2Num(String str) {
		String regex = "[\\D+]";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}
	
	/**
	 * <p>Converts an array of object string to a string that use ' to split.</p>
	 * 
	 * <p>If the array is null or empty, it'll return null.</p>
	 * 
	 * <pre>
	 * array = null;
	 * CAStringUtils.toString(array)               = null;
	 * array = new String[]{"a", "b"};
	 * CAStringUtils.toString(array)               = "a,b";
	 * </pre>
	 * @param array
	 * @return 
	 */
	public static String toString(String[] array) {
		if (CAArrayUtils.isEmpty(array)) {
			return null;
		}
		return append(array, "", ",");
	}
	
	/**
	 * 转化为数据库可识别的字符串.<br/>
	 * note that: 如果collection是null或者空, 将返回一个空的字符串.
	 * @param collection
	 * @return
	 */
	public static String toSqlString(Collection collection) {
		if (null == collection || collection.isEmpty()) {
			return "";
		}
		return append(collection.iterator(), "'", ",");
	}
	
	/**
	 * 转化为数据库可识别的字符串.<br/>
	 * note that: 如果array是null或者空, 将返回一个空的字符串.
	 * @param array		字符数组
	 * @return
	 */
	public static String toSqlString(String[] array) {
		if (null == array || 0 == array.length) {
			return "";
		}
		return append(array, "'", ",");
	}
	
	public static String append(Iterator it, String upper, String seperator) {
        if(it == null || upper == null || seperator == null)
            return "";
        StringBuffer sb = new StringBuffer();
        if(it.hasNext()) {
            Object obj = it.next();
            sb.append(upper).append(obj.toString()).append(upper);
            for(; it.hasNext(); sb.append(seperator).append(upper).append(obj.toString()).append(upper)) {
                obj = it.next();
            }
        }
        return sb.toString();
    }
	
	private static String append(String params[], String upper, String seperator) {
        if(params == null || params.length == 0 || upper == null || seperator == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        sb.append(upper).append(params[0]).append(upper);
        for(int i = 1; i < params.length; i++) {
            sb.append(seperator).append(upper).append(params[i]).append(upper);
        }
        return sb.toString();
    }
}
