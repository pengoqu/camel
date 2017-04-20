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
	private static Logger logger = Logger.getLogger(CADbUtils.class);
	private static final int MAX_DATA_ROW = 8000;
	
	/**
	 * 
	 * 描述：使用批量执行方式执行sql，获取更好的性能
	 * 
	 * @param ctx
	 * @param sql
	 *            将要执行的sql语句
	 * @param paramsList
	 *            sql参数集合｛Object[]...｝
	 * @throws BOSException
	 */
	public static void executeBatch(Context ctx, String sql, Collection<Object[]> paramsList) throws BOSException {
		Connection conn = null;
		PreparedStatement ps = null;

		try
		{
			conn = EJBFactory.getConnection(ctx);
			ps = conn.prepareStatement(sql);

			Object[] params = null;
			int k = 1;
			boolean flag = false;
			Iterator<Object[]> iter = paramsList.iterator();
			
			int count = 0;
			while (iter.hasNext()) {
				params = iter.next();
				for (int j = 0; j < params.length; j++)
				{
					if (params[j] != null)
					{
						ps.setObject(j + 1, params[j]);
					} else
					{
						ps.setNull(j + 1, Types.VARCHAR);
					}
				}
				ps.addBatch();
				count++;
				
				if (count == k * MAX_DATA_ROW)
				{
					flag = true;
				}
				if (count > k * MAX_DATA_ROW && flag)
				{
					ps.executeBatch();
					k++;
					flag = false;
					ps.clearBatch();
				}
			}

			if (!flag)
			{
				ps.executeBatch();
			}
		} catch (SQLException exc)
		{
			logger.error(sql,exc);
			throw new BOSException("Sql222 execute exception : " + sql, exc);
		} finally
		{
			SQLUtils.cleanup(ps, conn);
		}
	}
	 
	/**
	 * 
	 * 描述：使用批量执行方式执行sql，获取更好的性能
	 * 
	 * @param ctx
	 * @param sql
	 *            将要执行的sql语句
	 * @param paramsList
	 *            sql参数集合｛Object[]...｝
	 * @throws BOSException
	 * @see {@link #executeBatch(Context, String, Collection)}
	 */
	public static void executeBatch(Context ctx, String sql, List<Object[]> paramsList) throws BOSException {
		executeBatch(ctx, sql, (Collection<Object[]>)paramsList);
	}
	
	/**
	 * 使用制定的列创建临时表，并使用 columnValueColl 值集合给列赋值.
	 * 
	 * @param ctx
	 * @param columnValueColl  列值
	 * @param columnKey  列名
	 * @return  临时表名称
	 * @throws BOSException
	 * @see HRTableTools
	 */
	public static String createTempTableForSpecifiedColumn(Context ctx,
			Collection<Object[]> columnValueColl, String columnKey) throws BOSException {
		String sql = " create TABLE aa (" + columnKey + " varchar(44) not null primary key) ";
		String temporaryTableName = null;
		try {
			temporaryTableName = TempTablePool.getInstance(ctx).createTempTable(sql);
		} catch (Exception e) {
			throw new BOSException();
		}
		StringBuffer sb = new StringBuffer(512);
		sb.append(" INSERT INTO ").append(temporaryTableName)
			.append("(" + columnKey + ")VALUES (?)");
		executeBatch(ctx, sql, columnValueColl);
		return temporaryTableName;
	}
	
	/**
	 * 检查列是否存在.
	 * 
	 * @param ctx
	 * @param tableName 表名
	 * @param field 列名
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
	 * 检查表是否存在.
	 * 
	 * @param ctx
	 * @param tableName 表名
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
