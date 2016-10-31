package com.kingdee.eas.camel.constant;

import java.math.BigDecimal;

import com.kingdee.eas.fi.gl.GlUtils;

public interface CANumberConstant {
	public static final BigDecimal ZERO = new BigDecimal("0");
	
	public static final BigDecimal ONE = new BigDecimal("1");
	
	public static final BigDecimal ONE_HUNDRED = new BigDecimal("100");
	public static final BigDecimal TEN = new BigDecimal("10");
	
	public static final BigDecimal MAX_VALUE = GlUtils.maxBigDecimal.divide(ONE_HUNDRED, 4);
	public static final BigDecimal MIN_VALUE = GlUtils.minBigDecimal.divide(ONE_HUNDRED, 4);
	
	/**999999999999999.9999*/
	public static final BigDecimal MAX_DECIMAL = new BigDecimal("999999999999999.9999");
	/**-999999999999999.9999*/
	public static final BigDecimal MIN_DECIMAL = new BigDecimal("-999999999999999.9999");
}
