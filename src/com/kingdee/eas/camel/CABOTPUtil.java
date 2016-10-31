package com.kingdee.eas.camel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.framework.ejb.EJBFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.util.db.SQLUtils;

public class CABOTPUtil {
	/**
	 * 关联关系
	 * @param ctx
	 * @param srcObjectID  源对象ID
	 * @param srcBosType   源实体ID
	 * @param destObjectID    目标对象ID
	 * @param destBosType    目标实体ID
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static void writeBOTRelation(Context ctx, String srcObjectID, String srcBosType, String destObjectID, String destBosType) throws BOSException, EASBizException {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO T_BOT_Relation (FID,FSrcObjectID,FDestObjectID,FSRCENTITYID,FDESTENTITYID,");
		sql.append("FOPERATORID,FTYPE,FISEFFECTED,FDATE) VALUES(NEWBOSID('59302EC6'),?,?,?,?,?,?,?, now())");
		
		Connection cn = null;
		PreparedStatement pstmt = null;
		try {
			cn = EJBFactory.getConnection(ctx);
			pstmt = cn.prepareStatement(sql.toString());
			pstmt.setString(1, srcObjectID);
			pstmt.setString(2, destObjectID);
			pstmt.setString(3, srcBosType);
			pstmt.setString(4, destBosType);
			pstmt.setString(5, ctx.getUserName());
			pstmt.setInt(6, 0);
			pstmt.setInt(7, 1);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new BOSException(e);
		} finally {
			SQLUtils.cleanup(pstmt, cn);
		}
	}
}
