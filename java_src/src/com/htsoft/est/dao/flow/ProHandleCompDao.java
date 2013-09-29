package com.htsoft.est.dao.flow;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import java.util.List;

import com.htsoft.core.dao.BaseDao;
import com.htsoft.est.model.flow.ProHandleComp;

/**
 * 
 * @author csx
 *
 */
public interface ProHandleCompDao extends BaseDao<ProHandleComp>{
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
	 * 获取某发布流程所有的配置
	 * @param deployId
	 * @return
	 */
	public List<ProHandleComp> getByDeployId(String deployId);
}