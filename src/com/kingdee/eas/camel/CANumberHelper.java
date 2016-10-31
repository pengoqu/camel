package com.kingdee.eas.camel;

import java.math.BigDecimal;

import com.kingdee.eas.camel.constant.CANumberConstant;

/**
 * <p>
 * <code>BigDecimal</code>帮助类.
 * </p>
 * 
 * @author jason qu
 * @see BigDecimal
 */
public class CANumberHelper implements CANumberConstant {
	/**
	 * 描述：obj 对象转化为<code>BigDecimal</code>对象.
	 * @see #ZERO
	 * @return
	 * 		如果obj是null,
	 */
	public static BigDecimal toBigDecimal(Object obj) {
		if (obj == null)
			return ZERO;
		if (obj instanceof BigDecimal)
			return (BigDecimal) obj;
		if (obj instanceof Integer)
			return new BigDecimal(((Integer) obj).toString());
		if (obj instanceof Long)
			return new BigDecimal(((Long) obj).toString());
		if (obj instanceof Double)
			return new BigDecimal(((Double) obj).doubleValue());
		if (obj.toString() == null)
			return ZERO;
		String str = obj.toString().trim();
		if (str.toLowerCase().indexOf("e") > -1)
			try {
				return new BigDecimal(str);
			} catch (NumberFormatException e) {
				return ZERO;
			}
		if (str.matches("^[+-]?\\d+[\\.\\d]?\\d*+$"))
			return new BigDecimal(str);
		else
			return ZERO;
	}
	
	/**
	 * 返回<code>BigDecimal</code>, 它的精度为指定值. 
	 * <p>roundingMode 默认为 {@link BigDecimal#ROUND_HALF_UP} </p>
	 * @param obj
	 * @param scale
	 * 		scale of the BigDecimal value to be returned.
	 */
	public static BigDecimal toBigDecimal(Object obj, int scale) {
		return toBigDecimal(obj).setScale(scale, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 返回<code>BigDecimal</code>, 它的值是(dec1 + dec2).
	 * @return null if both dec1 and dec2 is null, a BigDecimal otherwise.
	 */
	public static BigDecimal add(Object dec1, Object dec2) {
		if (dec1 == null && dec2 == null)
			return null;
		else
			return toBigDecimal(dec1).add(toBigDecimal(dec2));
	}
	
	public static BigDecimal add(Object[] dec) {
		if (null == dec || 0 == dec.length) {
			return null;
		} else {
			BigDecimal result = ZERO;
			for (int i = 0; i < dec.length; i++) {
				result = add(result, toBigDecimal(dec[i]));
			}
			return result;
		}
	}
	
	/**
	 * 返回<code>BigDecimal</code>, 它的值是(dec1 - dec2).
	 * @return null if both dec1 and dec2 is null, a BigDecimal otherwise.
	 */
	public static BigDecimal subtract(Object dec1, Object dec2) {
		if (dec1 == null && dec2 == null)
			return null;
		else
			return toBigDecimal(dec1).subtract(toBigDecimal(dec2));
	}

	/**
	 * <p>返回<code>BigDecimal</code>, 它的值是(dec1 / dec2).
	 * 默认精度为2.</p>
	 * @return null if both dec1 and dec2 is null, a BigDecimal otherwise.
	 */
	public static BigDecimal divide(Object dec1, Object dec2) {
		return divide(dec1, dec2, 2, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 返回<code>BigDecimal</code>, 它的值是(dec1 / dec2).
	 * @return null if both dec1 and dec2 is null, a BigDecimal otherwise.
	 * @param dec1
	 * @param dec2 value by which this BigDecimal is to be divided.
	 * @param scale scale of the BigDecimal quotient to be returned.
	 * @param roundingMode rounding mode to apply. 
	 */
	public static BigDecimal divide(Object dec1, Object dec2, int scale,
			int roundingMode) {
		if (dec1 == null && dec2 == null)
			return null;
		if (toBigDecimal(dec2).signum() == 0)
			return null;
		else
			return toBigDecimal(dec1).divide(toBigDecimal(dec2), scale,
					roundingMode);
	}

	/**
	 * 返回<code>BigDecimal</code>, 它的值是(dec1 * dec2).
	 * @return null if both dec1 and dec2 is null, a BigDecimal otherwise.
	 */
	public static BigDecimal multiply(Object dec1, Object dec2) {
		if (dec1 == null && dec2 == null)
			return null;
		else
			return toBigDecimal(dec1).multiply(toBigDecimal(dec2));
	}
	
	/**
	 * 批量乘.
	 * 
	 * @param dec
	 * @return
	 */
	public static BigDecimal multiply(Object[] dec) {
		if (CAArrayUtils.isEmpty(dec)) {
			return null;
		} else {
			BigDecimal result = ZERO;
			result = multiply(dec[0], dec[1]);
			for (int i = 2; i < dec.length; i++) {
				result = multiply(result, toBigDecimal(dec[i]));
			}
			return result;
		}
	}

	/**
	 * Check if obj is greater than zero.
	 * @param obj
	 * @return
	 * 		true if obj is greater than zero, false otherwise.
	 */
	public static boolean isGtBigDecimal(Object obj) {
		boolean isValid = false;
		if (toBigDecimal(obj).compareTo(ZERO) > 0)
			isValid = true;
		return isValid;
	}
	
	/**
	 * Check if obj is less than zero.
	 * @param obj
	 * @return
	 * 		true if obj is less than zero, false otherwise.
	 */
	public static boolean isLtBigDecimal(Object obj) {
		boolean isValid = false;
		if (toBigDecimal(obj).compareTo(ZERO) < 0)
			isValid = true;
		return isValid;
	}
	
	/**
	 * Compare its two arguments for order.
	 * 
	 * @param o1 the first object to be compared
	 * @param o2 the second object to be compared
	 * @return true if o1 is less than or equal to o2, false otherwise.
	 */
	public static boolean isLteBigDecimal(Object o1, Object o2) {
		boolean isValid = false;
		if (toBigDecimal(o1).compareTo(toBigDecimal(o2)) <= 0)
			isValid = true;
		return isValid;
	}
	
	/**
	 * Check if obj is equal to zero.
	 * @param obj
	 * @return
	 * 		ture if obj is equal to zero, false otherwise.
	 */
	public static boolean isEqBigDecimal(Object obj) {
		boolean isValid = false;
		if (toBigDecimal(obj).compareTo(ZERO) == 0)
			isValid = true;
		return isValid;
	}

	/**
	 * Check bd is null or zero.
	 * @param bd
	 * @return
	 * 		ture if db is null or zero, false otherwise.s
	 */
	public static boolean isNullZero(BigDecimal bd) {
		boolean b = false;
		if (bd == null)
			b = true;
		if (bd != null && bd.compareTo(ZERO) == 0)
			b = true;
		return b;
	}
}
