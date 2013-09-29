package com.htsoft.est.service.flow.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.core.util.ContextUtil;
import com.htsoft.est.dao.flow.ProcessFormDao;
import com.htsoft.est.model.flow.ProcessForm;
import com.htsoft.est.model.system.AppUser;
import com.htsoft.est.service.flow.ProcessFormService;

public class ProcessFormServiceImpl extends BaseServiceImpl<ProcessForm> implements ProcessFormService{
	private ProcessFormDao dao;
	
	public ProcessFormServiceImpl(ProcessFormDao dao) {
		super(dao);
		this.dao=dao;
	}
	
	/**
	 * 取得某个流程实例的所有表单
	 * @param runId
	 * @return
	 */
	public List getByRunId(Long runId){
		return dao.getByRunId(runId);
	}
	
	/**
	 * 查找某个流程某个任务的表单数据
	 * @param runId
	 * @param activityName
	 * @return
	 */
	public ProcessForm getByRunIdActivityName(Long runId,String activityName){
		return dao.getByRunIdActivityName(runId, activityName);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.htsoft.est.service.flow.ProcessFormService#getActvityExeTimes(java.lang.Long, java.lang.String)
	 */
	public Long getActvityExeTimes(Long runId,String activityName){
		return dao.getActvityExeTimes(runId, activityName);
	}
	
//	/**
//	 * 构造最新的流程实例对应的所有字段及数据
//	 * @param runId
//	 * @return
//	 */
//	public Map getVariables(Long runId){
//		return dao.getVariables(runId);
//	}
	/**
	 * 初始一个未持久化的历史 
	 * @return
	 */
	public ProcessForm getInitProcessForm(){
		ProcessForm processForm=new ProcessForm();
		
		processForm.setCreatetime(new Date());
		AppUser curUser=ContextUtil.getCurrentUser();
		processForm.setCreatorId(curUser.getUserId());
		processForm.setCreatorName(curUser.getFullname());
		
		processForm.setStatus(ProcessForm.STATUS_INIT);
		processForm.setDurTimes(0);
		processForm.setEndtime(new Date());
		
		return processForm;
	}
	
	/**
	 * 取得某个任务其对应的流程表单信息（流程历史表单）
	 * @param taskId
	 * @return
	 */
	public ProcessForm getByTaskId(String taskId){
		return dao.getByTaskId(taskId);
	}
	/*
	 * (non-Javadoc)
	 * @see com.htsoft.est.service.flow.ProcessFormService#getByRunIdTaskName(java.lang.Long, java.lang.String)
	 */
	public ProcessForm getByRunIdTaskName(Long runId,String taskName){
		return dao.getByRunIdTaskName(runId, taskName);
	}

}