package com.htsoft.est.dao.flow.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   --  http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/

import java.util.List;

import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.est.dao.flow.RunDataDao;
import com.htsoft.est.model.flow.RunData;

public class RunDataDaoImpl extends BaseDaoImpl<RunData> implements RunDataDao{

	public RunDataDaoImpl() {
		super(RunData.class);
	}
	
	public RunData getByRunIdFieldName(Long runId,String fieldName){
		String hql="from RunData rd where rd.processRun.runId=? and rd.fieldName=?";
		return (RunData)findUnique(hql,new Object[]{runId,fieldName});
	}
	/*
	 * (non-Javadoc)
	 * @see com.htsoft.est.dao.flow.RunDataDao#getByRunId(java.lang.Long)
	 */
	public List<RunData> getByRunId(Long runId){
	    String hql="from RunData rd where rd.processRun.runId=?";
	    return findByHql(hql, new Object[]{runId});
	}

}