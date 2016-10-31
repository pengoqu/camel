package com.kingdee.eas.camel.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.framework.ejb.EJBFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.db.SQLUtils;

public final class CADbUtils {
	
	/**
	 * <p>��������</p>
	 * note that :<br/>
	 * sql�е�?�����records������list������������һ��,  ����Ϊһһ��Ӧ��ϵ.
	 * 
	 * @param ctx
	 * @param sql
	 * @param records �м�¼
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static void executeBatch(Context ctx, String sql, List records) throws BOSException, 
			EASBizException {
		Connection cn = null;
		PreparedStatement ps = null;
		
		try {
			cn = EJBFactory.getConnection(ctx);
			ps = cn.prepareStatement(sql);
			List[] lists = (List[])records.toArray(new List[0]);
			
			for(int i = 0,length = lists.length;i < length;i++){
				Object[] params = getUpdateValues(lists[i]);
				for(int k = 0,size = params.length; k<size; k++){
					if(params[k] == null)
						ps.setString(k+1,(String) params[k]);
					else{
						ps.setObject(k+1, params[k]);
					}
				}
				ps.addBatch();
			}
			ps.executeBatch();
		} catch (SQLException e) {
			throw new BOSException(e);
		} finally {
			SQLUtils.cleanup(ps, cn);
			records.clear();
		}
	}
	
	private static Object[] getUpdateValues(List list) {
		Object[] params = new Object[list.size()];
		for (int i = 0, size = list.size(); i < size; i++) {
			params[i] = list.get(i);
		}
		return params;
	}
	
	/**
	 * ������Ƿ����.
	 * 
	 * @param ctx
	 * @param tableName ����
	 * @param field ����
	 * @return true if the column is exist, false otherwise.
	 */
	public static boolean checkColumnIfExist(Context ctx, String tableName, String field) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from KSQL_USERCOLUMNS where KSQL_COL_NAME ='").append(field).append("'");
		sql.append("    and KSQL_COL_TABNAME='").append(tableName).append("'");
		try {
			IRowSet rs = DbUtil.executeQuery(ctx, sql.toString());
			if (rs.next())
				return true;
		} catch (Exception e) {
			return false;
		}
		return false;
	}
	
	/**
	 * �����Ƿ����.
	 * 
	 * @param ctx
	 * @param tableName ����
	 * @return true if the table is exist, false otherwise.
	 */
	public static boolean checkTableIfExist(Context ctx, String tableName) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from KSQL_USERTABLES where KSQL_TABNAME ='").append(tableName).append("'");
		try {
			IRowSet rs = DbUtil.executeQuery(ctx, sql.toString());
			if (rs.next())
				return true;
		} catch (Exception e) {
			return false;
		}
		return false;
	}
}
