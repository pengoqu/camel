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
 * <p>���ϵĸ�������.</p>
 * @author jason qu
 *
 */
public class CACollectionHelper {
	
	/**
	 * <p>���������ָ�������Ƿ�Ϊnull���߿�.</p>
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
	 * ���src��dest�Ƿ���ȫƥ��.
	 * 
	 * @param src
	 * 		ԭ����
	 * @param dest
	 * 		Ŀ�꼯��
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
	 * ���dest�е������Ƿ񶼰�����src��.
	 * <p>���src����destΪnull,����false.</p>
	 * @param src
	 * 		ԭ����
	 * @param dest
	 * 		Ŀ�꼯��
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
	 * ����һ��<code>Set</code>ʵ��.
	 * <p>
	 * note that: collection ������Ԫ�ر�����<code>IObjectValue</code>������.
	 * </p>
	 * @param collection
	 * @param property		
	 * 		ʵ������
	 * @return 
	 * 		null�����collection ���� property ��null. ���򷵻�һ��<code>HashSet</code>ʵ��.
	 */
	public static Set toSet(Collection collection, String property) {
		if (null == collection || null == property) {
			return null;
		} else {
			return toSet(collection.iterator(), property);
		}
	}
	
	/**
	 * ת��Ϊ<code>Set</code>.<br/>
	 * note that: collection ������Ԫ�ر�����<code>IObjectValue</code>������.
	 * @param collection
	 * @param fieldName		ʵ������
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
	 * <p>ָ��������ת��Ϊ<code>Set</code>����.</p>
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
	 * ת��Ϊ<code>Set</code>.<br>
	 * note that: iterator ������Ԫ�ر�����<code>IObjectValue</code>������.
	 * @param iterator
	 * @param fieldName		ʵ������
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
	 * ��objectValue�л�ȡproperty��Ӧ������, �����Ϊ��, ����ӵ�set��. ����������.
	 * @param objectValue
	 * 		vo
	 * @param property
	 * 		����
	 * @param set
	 */
	private static void addElement(IObjectValue objectValue, String property, Set set) {
		Object data = objectValue.get(property);
		if (null != data ) {
			set.add(data);
		}
	}
}
