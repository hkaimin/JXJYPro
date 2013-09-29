package com.htsoft.est.dao.flow;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   --  http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import java.util.List;
import java.util.Map;

import com.htsoft.core.dao.BaseDao;
import com.htsoft.est.model.flow.ProcessForm;

/**
 * 
 * @author 
 *
 */
public interface ProcessFormDao extends BaseDao<ProcessForm>{
	/**
	 * 取得某个流程实例的所有表单
	 * @param runId
	 * @return
	 */
	public List getByRunId(Long runId);
	
	/**
	 * 查找某个流程某个任务的表单数据
	 * @param runId
	 * @param activityName
	 * @return
	 */
	public ProcessForm getByRunIdActivityName(Long runId,String activityName);
	
	/**
	 * 取得某一流程某一任务已经执行的次数，如某一任务被不断驳回，就会被执行多次。
	 * @return
	 */
	public Long getActvityExeTimes(Long runId,String activityName);
	
//	/**
//	 * 构造最新的流程实例对应的所有字段及数据
//	 * @param runId
//	 * @return
//	 */
//	public Map getVariables(Long runId);
	
	/**
	 * 取得某个任务其对应的流程表单信息（流程历史表单）
	 * @param taskId
	 * @return
	 */
	public ProcessForm getByTaskId(String taskId);
	
	/**
	 * 按runId及TaskName取到某个ProcessForm，目前使用的目的是为了取到某个流程开始的历史表单信息
	 * @param runId
	 * @param taskName
	 * @return
	 */
	public ProcessForm getByRunIdTaskName(Long runId,String taskName);
}