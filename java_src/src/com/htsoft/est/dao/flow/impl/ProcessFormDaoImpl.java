package com.htsoft.est.dao.flow.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   --  http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.est.dao.flow.ProcessFormDao;
import com.htsoft.est.model.flow.ProcessForm;
import com.htsoft.est.model.flow.RunData;

public class ProcessFormDaoImpl extends BaseDaoImpl<ProcessForm> implements ProcessFormDao{

	public ProcessFormDaoImpl() {
		super(ProcessForm.class);
	}
	/*
	 * (non-Javadoc)
	 * @see com.htsoft.est.dao.flow.ProcessFormDao#getByRunId(java.lang.Long)
	 */
	public List getByRunId(Long runId){
		String hql="from ProcessForm pf where pf.processRun.runId=? and pf.endtime is not null order by pf.formId asc";
		return findByHql(hql, new Object[]{runId});
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.htsoft.est.dao.flow.ProcessFormDao#getByRunIdActivityName(java.lang.Long, java.lang.String)
	 */
	public ProcessForm getByRunIdActivityName(Long runId,String activityName){
		//取得最新的sn号
		Integer maxSn=getActvityExeTimes(runId, activityName).intValue();
		String hql="from ProcessForm pf where pf.processRun.runId=? and pf.activityName=? and pf.sn=?";
		return (ProcessForm)findUnique(hql, new Object[]{runId,activityName,maxSn});
	}
	
//	/**
//	 * 构造最新的流程实例对应的所有字段及数据
//	 * @param runId
//	 * @return
//	 */
//	public Map getVariables(Long runId){
//		Map variables=new HashMap();
//		String hql="from ProcessForm pf where pf.processRun.runId=? order by pf.createtime desc";
//		List<ProcessForm> forms=findByHql(hql,new Object[]{runId});
//		
//		for(ProcessForm form : forms){
//			Iterator<RunData> formDataIt = form.getFormDatas().iterator();
//			while(formDataIt.hasNext()){
//				RunData formData=formDataIt.next();
//				if(!variables.containsKey(formData.getFieldName())){//放置最新的值
//					variables.put(formData.getFieldName(), formData.getVal());
//				}
//			}
//		}
//		return variables;
//	}
	
	/*
	 * (non-Javadoc)
	 * @see com.htsoft.est.dao.flow.ProcessFormDao#getActvityExeTimes(java.lang.Long, java.lang.String)
	 */
	public Long getActvityExeTimes(Long runId,String activityName){
		String hql="select count(pf.formId) from ProcessForm pf where pf.processRun.runId=? and pf.activityName=? ";
		return (Long)findUnique(hql, new Object[]{runId,activityName});
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.htsoft.est.dao.flow.ProcessFormDao#getByTaskId(java.lang.String)
	 */
	public ProcessForm getByTaskId(String taskId){
		String hql="from ProcessForm pf where pf.taskId=? order by pf.createtime desc";
		List<ProcessForm> pfs=findByHql(hql, new Object[]{taskId});
		if(pfs.size()>0){
			return pfs.get(0);
		}
		return null;
		//return (ProcessForm)findUnique(hql, new Object[]{taskId});
	}
	/*
	 * (non-Javadoc)
	 * @see com.htsoft.est.dao.flow.ProcessFormDao#getByRunIdTaskName(java.lang.Long, java.lang.String)
	 */
	public ProcessForm getByRunIdTaskName(Long runId,String taskName){
		String hql="from ProcessForm pf where pf.processRun.runId=?  and pf.activityName=?";
		List<ProcessForm> pfs=findByHql(hql, new Object[]{runId,taskName});
		if(pfs.size()>0){
			return pfs.get(0);
		}
		return null;
	}
	
}