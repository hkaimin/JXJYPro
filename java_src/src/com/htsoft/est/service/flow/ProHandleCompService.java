package com.htsoft.est.service.flow;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import java.util.List;

import com.htsoft.core.service.BaseService;
import com.htsoft.est.model.flow.ProHandleComp;

public interface ProHandleCompService extends BaseService<ProHandleComp>{
	/**
	 * 取得某个流程的某个节点的各事件及监听对应的列表
	 * @param deployId
	 * @param activityName
	 * @return
	 */
	public List<ProHandleComp> getByDeployIdActivityName(String deployId,String activityName);
	/**
	 * 
	 * @param deployId
	 * @param activityName
	 * @param handleType
	 * @return
	 */
	public List<ProHandleComp> getByDeployIdActivityNameHandleType(String deployId,String activityName,Short handleType);
	
	/**
	 * 
	 * @param deployId
	 * @param activityName
	 * @param eventName
	 * @return
	 */
	public ProHandleComp getProHandleComp(String deployId,String activityName,String eventName);
	
	/**
	 * 拷贝原流程的配置至新流程中
	 * @param oldDeployId
	 * @param newDeployId
	 */
	public void copyNewConfig(String oldDeployId,String newDeployId);
}


