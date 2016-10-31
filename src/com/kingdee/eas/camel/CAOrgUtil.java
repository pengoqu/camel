package com.kingdee.eas.camel;

import java.sql.SQLException;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.basedata.org.JobCategoryFactory;
import com.kingdee.eas.basedata.org.JobCategoryInfo;
import com.kingdee.eas.basedata.org.JobFactory;
import com.kingdee.eas.basedata.org.JobInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;

public class CAOrgUtil {
	/**
	 * 获取人员对应职务类型对象.
	 * @return
	 */
	public static JobCategoryInfo getJobCategoryByPerson(Context ctx, String personID) throws BOSException, EASBizException {
		StringBuffer sql = new StringBuffer();
		sql.append("select t3.fid");
		sql.append(" from T_ORG_PositionMember t");
		sql.append(" left join T_ORG_Position t1 on t.fpositionid=t1.fid");
		sql.append(" left join T_ORG_Job t2 on t1.fjobid=t2.fid");
		sql.append(" left join T_ORG_JobCategory t3 on t2.fjobcategoryid=t3.fid");
		sql.append(" where 1=1");
		sql.append(" and fisprimary=1");
		sql.append(" and t.fpersonid=?");
		IRowSet rs = DbUtil.executeQuery(ctx, sql.toString(), new String[]{personID});
		String jobCategoryID = null;
		try {
			if (rs.next()) {
				jobCategoryID = rs.getString("fid");
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		
		if (!StringUtils.isEmpty(jobCategoryID)) {
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("id");
			sic.add("number");
			sic.add("name");
			return JobCategoryFactory.getLocalInstance(ctx).getJobCategoryInfo(new ObjectUuidPK(jobCategoryID), sic);
		}
		return null;
	}
	
	public static JobInfo getJobByPerson(Context ctx, String personID) throws BOSException, EASBizException {
		StringBuffer sql = new StringBuffer();
		sql.append("select t2.fid");
		sql.append(" from T_ORG_PositionMember t");
		sql.append(" left join T_ORG_Position t1 on t.fpositionid=t1.fid");
		sql.append(" left join T_ORG_Job t2 on t1.fjobid=t2.fid");
		sql.append(" where 1=1");
		sql.append(" and fisprimary=1");
		sql.append(" and t.fpersonid=?");
		IRowSet rs = DbUtil.executeQuery(ctx, sql.toString(), new String[]{personID});
		String jobID = null;
		try {
			if (rs.next()) {
				jobID = rs.getString("fid");
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		if (!StringUtils.isEmpty(jobID)) {
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("id");
			sic.add("number");
			sic.add("name");
			//职业资格类别
			sic.add("certifyCompetencyType.id");
			sic.add("certifyCompetencyType.number");
			sic.add("certifyCompetencyType.name");
			return JobFactory.getLocalInstance(ctx).getJobInfo(new ObjectUuidPK(jobID), sic);
		}
		return null;
	}
}
