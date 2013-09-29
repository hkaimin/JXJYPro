package com.htsoft.est.dao.flow;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   --  http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import java.util.List;

import com.htsoft.core.dao.BaseDao;
import com.htsoft.est.model.flow.ProUserAssign;

/**
 * 
 * @author 
 *
 */
public interface ProUserAssignDao extends BaseDao<ProUserAssign>{
	public List<ProUserAssign> getByDeployId(String deployId);
	
	/**
	 * 取得某流程某个任务的授权信息
	 * @param deployId
	 * @param activityName
	 * @return
	 */
	public ProUserAssign getByDeployIdActivityName(String deployId,String activityName);
}