package com.htsoft.est.service.flow;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/

import java.util.List;
import java.util.Set;

import org.jbpm.api.Execution;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.task.TaskImpl;

import com.htsoft.core.jbpm.pv.TaskInfo;
import com.htsoft.core.service.BaseService;
import com.htsoft.core.web.paging.PagingBean;

public interface TaskService extends BaseService<TaskImpl>{
	
	/**
	 * 查找所有的任务信息
	 * @param taskName
	 * @param pb
	 * @return
	 */
	public List<TaskInfo> getAllTaskInfos(String taskName,PagingBean pb);
	/**
	 * 取得用户的对应的任务列表
	 * @param userId
	 * @return
	 */
	public List<TaskImpl> getTasksByUserId(String userId,PagingBean pb);
	
	/**
	 * 
	 * @param userId
	 * @param pb
	 * @return
	 */
	public List<TaskInfo> getTaskInfosByUserId(String userId,PagingBean pb);
	/**
	 * 根据活动名称及参数Key取得参与人Id
	 * @param activityName
	 * @param varKey
	 * @param value
	 * @return
	 */
	public Set<Long> getHastenByActivityNameVarKeyLongVal(String activityName,String varKey,Long value);
	
	
	/**
	 * 取得某个用户候选的任务列表
	 * @param userId
	 * @param pb
	 * @return
	 */
	public List<TaskImpl> getCandidateTasks(String userId,PagingBean pb);
	
	/**
	 * 查找个人归属任务，不包括其角色归属的任务
	 * @param userId
	 * @param pb
	 * @return
	 */
	public List<TaskImpl> getPersonTasks(String userId,PagingBean pb);
	
	/**
	 * 按主键查找execution实体
	 * @param dbid
	 * @return
	 */
	public Execution getExecutionByDbid(Long dbid);
	
	/**
	 * 保存executionimpl
	 * @param executionImpl
	 */
	public void save(ExecutionImpl executionImpl);
	
	/**
	 * 去掉某个execution的子execution及其相关联的记录
	 * @param parentDbid
	 */
	public void removeExeByParentId(Long parentDbid);
}
