package com.kingdee.eas.camel;

import java.util.ArrayList;
import java.util.HashSet;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.framework.IDynamicObject;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.util.StringUtils;

/**
 * 服务端和客户端通用帮助类.
 * 
 * @author jason
 *
 */
public class CAUtil {
	
	/**
	 * 根据id从对象集合中获取对应的对象信息。<br>
	 * 如果coll是null，empty或者id是空，返回 <code>null</code>
	 * @param coll
	 * @param entryID
	 * @return
	 */
	@SuppressWarnings("unused")
	public static IObjectValue getObjectValue(IObjectCollection coll, String id) {
    	if (null == coll || coll.isEmpty() || StringUtils.isEmpty(id)) {
    		return null;
    	}
    	IObjectValue objectValue = null;
    	for (int i = 0, size = coll.size(); i < size; i++) {
    		objectValue = coll.getObject(i);
    		
    		if (id.equals(objectValue.getString("id"))) {
    			break;
    		}
    	}
    	return objectValue;
    }
	
	/**
	 * <p>检查编码是否重复.</p>
	 * note that: 不考虑CU隔离问题.
	 * @param ctx
	 * @param model
	 * @return true: 存在, false: 不存在.
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static boolean checkNumberDup(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		CoreBillBaseInfo dataBaseInfo = (CoreBillBaseInfo)model;
        FilterInfo filter = new FilterInfo();
        FilterItemInfo filterItem = new FilterItemInfo("number", dataBaseInfo.getNumber());
        filter.getFilterItems().add(filterItem);
        if(dataBaseInfo.getId() != null) {
            filterItem = new FilterItemInfo("id", dataBaseInfo.getId(), CompareType.NOTEQUALS);
            filter.getFilterItems().add(filterItem);
            filter.setMaskString("#0 and #1");
        }
        IDynamicObject iDynamicObject = null;
        if(ctx != null)
            iDynamicObject = DynamicObjectFactory.getLocalInstance(ctx);
        else
            iDynamicObject = DynamicObjectFactory.getRemoteInstance();
        return iDynamicObject.exists(model.getBOSType(), filter);
	}
	
	/**
	 * 获取查询元素集合.
	 * @param items
	 * @return
	 */
	public static SelectorItemCollection getSic(String[] items) {
		SelectorItemCollection sic = new SelectorItemCollection();
		for (int i = 0; i < items.length; i++) {
			sic.add(items[i]);
		}
		return sic;
	}
	
	public static NEntityViewInfo newEntityViewInfo() {
		return new NEntityViewInfo();
	}
	
	public static class NEntityViewInfo {
		private EntityViewInfo view = null;
		private FilterInfo filter = null;
		private boolean isSimpleSelector = false;
		
		/**
		 * =
		 * @param compareExpr
		 * @param compareValue
		 * @return
		 */
		public NEntityViewInfo eq(String compareExpr, Object compareValue) {
			view.getFilter().getFilterItems().add(new FilterItemInfo(compareExpr, compareValue));
			return this;
		}
		
		/**
		 * ≠
		 * @param compareExpr
		 * @param compareValue
		 * @return
		 */
		public NEntityViewInfo ne(String compareExpr, Object compareValue) {
			view.getFilter().getFilterItems().add(new FilterItemInfo(compareExpr, compareValue, CompareType.NOTEQUALS));
			return this;
		}
		
		/**
		 * ＜
		 * @param compareExpr
		 * @param compareValue
		 * @return
		 */
		public NEntityViewInfo lt(String compareExpr, Object compareValue) {
			view.getFilter().getFilterItems().add(new FilterItemInfo(compareExpr, compareValue, CompareType.LESS));
			return this;
		}
		
		/**
		 * ＞
		 * @param compareExpr
		 * @param compareValue
		 * @return
		 */
		public NEntityViewInfo gt(String compareExpr, Object compareValue) {
			view.getFilter().getFilterItems().add(new FilterItemInfo(compareExpr, compareValue, CompareType.GREATER));
			return this;
		}
		
		/**
		 * ≤
		 * @param compareExpr
		 * @param compareValue
		 * @return
		 */
		public NEntityViewInfo le(String compareExpr, Object compareValue) {
			view.getFilter().getFilterItems().add(new FilterItemInfo(compareExpr, compareValue, CompareType.LESS_EQUALS));
			return this;
		}
		
		/**
		 * ≥
		 * @param compareExpr
		 * @param compareValue
		 * @return
		 */
		public NEntityViewInfo ge(String compareExpr, Object compareValue) {
			view.getFilter().getFilterItems().add(new FilterItemInfo(compareExpr, compareValue, CompareType.GREATER_EQUALS));
			return this;
		}
		
		/**
		 * add {@code ArrayList} support
		 * @param compareExpr
		 * @param compareValue
		 * @return
		 */
		public NEntityViewInfo in(String compareExpr, Object compareValue) {
			if (compareValue instanceof ArrayList) {
				compareValue = new HashSet((ArrayList)compareValue);
			}
			view.getFilter().getFilterItems().add(new FilterItemInfo(compareExpr, compareValue, CompareType.INCLUDE));
			return this;
		}
		
		public NEntityViewInfo like(String compareExpr, Object compareValue) {
			view.getFilter().getFilterItems().add(new FilterItemInfo(compareExpr, "%" + compareValue + "%", CompareType.LIKE));
			return this;
		}
		
		public EntityViewInfo getEntityViewInfo() {
			if (isSimpleSelector) {
				view.getSelector().add("id");
				view.getSelector().add("number");
				view.getSelector().add("name");
			}
			return view;
		}
		
		public NEntityViewInfo setSimpleSelector(boolean isSimpleSelector) {
			this.isSimpleSelector = isSimpleSelector;
			return this;
		}
		
		public NEntityViewInfo addSelector(String item) {
			view.getSelector().add(item);
			return this;
		}

		public FilterInfo getFilterInfo() {
			return filter;
		}
		
		public NEntityViewInfo() {
			view = new EntityViewInfo();
			filter = new FilterInfo();
			view.setFilter(filter);
		}
	}
}
