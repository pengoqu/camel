package com.kingdee.eas.camel;

import java.sql.SQLException;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.base.permission.RolePermCollection;
import com.kingdee.eas.base.permission.RolePermFactory;
import com.kingdee.eas.base.permission.UserOrgPermCollection;
import com.kingdee.eas.base.permission.UserOrgPermFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.jdbc.rowset.IRowSet;

public class CAPermissionHelper {
	
	/**
	 * 禁用后台权限项控制。<br/>
	 * 使用场景：webserivce等。
	 * @param ctx
	 */
	public static void disablePermissionForKScript(Context ctx) {
		ctx.put("disablePermissionForKScript", Boolean.TRUE);
	}
	
	/**
	 * 根据名称检查是否有权限
	 * @param userPK
	 * @param orgPK
	 * @param permItemName
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	public static boolean checkFunctionPermission(String userPK, String orgPK, String permItemName) throws EASBizException, BOSException {
		EntityViewInfo ev = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("owner.id", userPK, CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("org.id", orgPK, CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("permItem.name", permItemName, CompareType.EQUALS));
		ev.setFilter(filter);
		UserOrgPermCollection perColl = UserOrgPermFactory.getRemoteInstance().getUserOrgPermCollection(ev);
		
		if(perColl.size() < 1){
			return false;
		}
		return true;
	}
	public static boolean checkFunctionPermission(String userPK, String orgPK, String permItemName,String longNumber) throws EASBizException, BOSException {
		EntityViewInfo ev = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("owner.id", userPK, CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("org.id", orgPK, CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("permItem.longNumber", longNumber, CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("permItem.name", permItemName, CompareType.EQUALS));
		ev.setFilter(filter);
		UserOrgPermCollection perColl = UserOrgPermFactory.getRemoteInstance().getUserOrgPermCollection(ev);
		
		if(perColl.size() < 1){
			return false;
		}
		return true;
	}
	/**
	 * 检查当前用户所分配的角色是否拥有指定的权限
	 * @param userPK
	 * @param permItemName
	 * @param longNumber
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 * @throws SQLException 
	 */
	public static boolean checkRolePermission(String userPK, String orgPK, String permItemName,String longNumber) throws EASBizException, BOSException, SQLException {
		
		
		String  sql = "select FROLEID from T_PM_UserRoleOrg where FUSERID = '"+userPK+"' and FORGID = '"+orgPK+"'";
		
		IRowSet rowSet = SQLExecutorFactory.getRemoteInstance(sql).executeSQL();
		if(rowSet  == null){
			return false;
		}
		String role = "";
		int i = 0;
		while(rowSet.next()){
			i++;
			role += rowSet.getString("FROLEID");
			if(i< rowSet.size() ){
				role += ",";
			}
		}
		EntityViewInfo ev = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("role.id", role, CompareType.INCLUDE));
		if(longNumber != null){
			filter.getFilterItems().add(new FilterItemInfo("permItem.longNumber", longNumber, CompareType.EQUALS));
		}
		filter.getFilterItems().add(new FilterItemInfo("permItem.name", permItemName, CompareType.EQUALS));
		ev.setFilter(filter);
		
		RolePermCollection perColl = RolePermFactory.getRemoteInstance().getRolePermCollection(ev);
		if(perColl.size() < 1){
			return false;
		}
		return true;
	}
}
