package com.htsoft.est.service.flow;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Limited company.
*/
import java.util.List;
import java.util.Map;

import com.htsoft.core.service.BaseService;
import com.htsoft.est.model.flow.FormDef;
import com.htsoft.est.model.flow.FormTable;

public interface FormDefService extends BaseService<FormDef>{
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
	
	public FormDef saveFormDef(FormDef formDef,Map<FormTable,String> datas);
}


