package com.kingdee.eas.camel.constant;

import java.awt.Color;

/**
 * 颜色常量定义.
 * @author Jason qu<br/>
 * @since 1.0
 */
public class CAColorConstant {
	/**必录色*/
	public static final Color REQUIRED = new Color(252, 251, 223);
	/**非必录色*/
    public static final Color UNREQUIRED = new Color(252, 252, 252);
    /**冻结色*/
    public static final Color FREEZE = new Color(232, 232, 227);
    
    /**不可用背景色*/
    public static final Color KDTABLE_DISABLE_BG_COLOR = new Color(15263971);
    
    /**小计背景色*/
    public static final Color KDTABLE_SUBTOTAL_BG_COLOR = new Color(16119270);
    
    /**合计背景色*/
    public static final Color KDTABLE_TOTAL_BG_COLOR = new Color(16185023);
    
    /**正常色*/
    public static final Color KDTABLE_COMMON_BG_COLOR = new Color(16579551);
	
	private CAColorConstant(){
		//do nothing
	}
}
