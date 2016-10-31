package com.kingdee.eas.camel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;

/**
 * <p>集合的辅助操作.</p>
 * @author jason qu
 *
 */
public class CACollectionHelper {
	
	/**
	 * <p>描述：检查指定集合是否为null或者空.</p>
	 * <pre>
	 * isNullOrEmpty(null)                  = null.
	 * </pre>
	 * 
	 * @param coll
	 * @return
	 */
	public static boolean isNullOrEmpty(Collection coll) {
		return null == coll || coll.isEmpty() ? true : false;
	}
	
	/**
	 * 检查src和dest是否完全匹配.
	 * 
	 * @param src
	 * 		原集合
	 * @param dest
	 * 		目标集合
	 */
	public static boolean matching(Set src, Set dest) {
		if (null == src && null != dest) {
			return false;
		} else if (null == src && null == dest) {
			return true;
		} else if (null != src && null == dest) {
			return false;
		} else if (src.isEmpty() && !dest.isEmpty()) {
			return false;
		} else if (!src.isEmpty() && dest.isEmpty()) {
			return false;
		} else if (src.isEmpty() && dest.isEmpty()) {
			return true;
		} else {
			if (include(src, dest) && include(dest, src))
				return true;
			else 
				return false;
		}
	}
	
	/**
	 * 检查dest中的内容是否都包含在src中.
	 * <p>如果src或者dest为null,返回false.</p>
	 * @param src
	 * 		原集合
	 * @param dest
	 * 		目标集合
	 */
	public static boolean include(Set src, Set dest) {
		if (null == src || null == dest) {
			return false;
		}
		for(Iterator iterator = dest.iterator(); iterator.hasNext();) {
			if (!src.contains(iterator.next())) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 返回一个<code>Set</code>实例.
	 * <p>
	 * note that: collection 的所有元素必须是<code>IObjectValue</code>的子类.
	 * </p>
	 * @param collection
	 * @param property		
	 * 		实体属性
	 * @return 
	 * 		null：如果collection 或者 property 是null. 否则返回一个<code>HashSet</code>实例.
	 */
	public static Set toSet(Collection collection, String property) {
		if (null == collection || null == property) {
			return null;
		} else {
			return toSet(collection.iterator(), property);
		}
	}
	
	/**
	 * 转化为<code>Set</code>.<br/>
	 * note that: collection 的所有元素必须是<code>IObjectValue</code>的子类.
	 * @param collection
	 * @param fieldName		实体属性
	 * @return
	 */
	public static Set toSet(IObjectCollection collection, String fieldName) {
		if (null == collection || null == fieldName) {
			return new HashSet();
		} else {
			return toSet(collection.iterator(), fieldName);
		}
	}
	
	/**
	 * <p>指定的数组转化为<code>Set</code>对象.</p>
	 * <pre>
	 * toSet(null)                        = null;
	 * toSet(new String[]{})              = null;
	 * </pre>
	 * @param strs
	 * @return
	 */
	public static Set toSet(String[] strs) {
		if (null == strs || 0 == strs.length) {
			return null;
		}
		Set set = new HashSet();
		
		for (int i = 0; i < strs.length; i++) {
			set.add(strs[i]);
		}
		
		return set;
	}
	
	public static List toList(String[] strs) {
		if (null == strs || 0 == strs.length) {
			return null;
		}
		List list = new ArrayList();
		
		for (int i = 0; i < strs.length; i++) {
			list.add(strs[i]);
		}
		
		return list;
	}
	
	/**
	 * 转化为<code>Set</code>.<br>
	 * note that: iterator 的所有元素必须是<code>IObjectValue</code>的子类.
	 * @param iterator
	 * @param fieldName		实体属性
	 * @return
	 */
	public static Set toSet(Iterator iterator, String fieldName) {
		Set set = new HashSet();
		if (null == iterator || null == fieldName) {
			return set;
		}
		
		IObjectValue objectValue = null;
		for (; iterator.hasNext(); addElement(objectValue, fieldName, set)) {
			objectValue = (IObjectValue)iterator.next();
		}
		
		return set;
	}
	
	/**
	 * 从objectValue中获取property对应的内容, 如果不为空, 将添加到set中. 否则不做处理.
	 * @param objectValue
	 * 		vo
	 * @param property
	 * 		属性
	 * @param set
	 */
	private static void addElement(IObjectValue objectValue, String property, Set set) {
		Object data = objectValue.get(property);
		if (null != data ) {
			set.add(data);
		}
	}
}
