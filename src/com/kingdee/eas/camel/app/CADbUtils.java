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
	 * ������ʹ������ִ�з�ʽִ��sql����ȡ���õ�����
	 * 
	 * @param ctx
	 * @param sql
	 *            ��Ҫִ�е�sql���
	 * @param paramsList
	 *            sql�������ϣ�Object[]...��
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
	 * ������ʹ������ִ�з�ʽִ��sql����ȡ���õ�����
	 * 
	 * @param ctx
	 * @param sql
	 *            ��Ҫִ�е�sql���
	 * @param paramsList
	 *            sql�������ϣ�Object[]...��
	 * @throws BOSException
	 * @see {@link #executeBatch(Context, String, Collection)}
	 */
	public static void executeBatch(Context ctx, String sql, List<Object[]> paramsList) throws BOSException {
		executeBatch(ctx, sql, (Collection<Object[]>)paramsList);
	}
	
	/**
	 * ʹ���ƶ����д�����ʱ����ʹ�� columnValueColl ֵ���ϸ��и�ֵ.
	 * 
	 * @param ctx
	 * @param columnValueColl  ��ֵ
	 * @param columnKey  ����
	 * @return  ��ʱ������
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
