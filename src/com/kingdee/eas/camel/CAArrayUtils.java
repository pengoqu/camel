package com.kingdee.eas.camel;


public class CAArrayUtils {
	
	/**
	 * 判断指定的数组是否为空或者长度为0.
	 * 
	 * @param param
	 * @return true if the param is null or empty, false otherwise.
	 */
	public static boolean isEmpty(Object[] param) {
		return ((param == null) || (param.length == 0) || (param[0] == null));
	}
	
	public static String[] toStringArray(Object[] params) {
		if (null == params) {
			return null;
		}
		if (0 == params.length) {
			return new String[0];
		}
		
		String[] results = new String[params.length];
		for (int i = 0; i < params.length; i++) {
			results[i] = (String)params[i];
		}
		
		return results;
	}
}
