package com.htsoft.est.dao.flow;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Limited company.
*/
import java.util.List;

import com.htsoft.core.dao.BaseDao;
import com.htsoft.est.model.flow.FormDef;

/**
 * 
 * @author 
 *
 */
public interface FormDefDao extends BaseDao<FormDef>{
	/**
	 * 取某流程对应的所有任务表单定义
	 * @param deployId
	 * @return
	 */
	public List<FormDef> getByDeployId(String deployId);
	
	/**
	 * 按流程定义ID及任务名称查找对应的表单定义
	 * @param deployId
	 * @param activityName
	 * @return
	 */
	public FormDef getByDeployIdActivityName(String deployId,String activityName);
}