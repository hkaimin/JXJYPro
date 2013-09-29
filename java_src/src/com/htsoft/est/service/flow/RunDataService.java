package com.htsoft.est.service.flow;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/


import java.util.Map;

import com.htsoft.core.service.BaseService;
import com.htsoft.est.model.flow.RunData;

public interface RunDataService extends BaseService<RunData>{
	
	/**
	 * 取得某个运行实例中某个字段的详细信息
	 * @param runId
	 * @param fieldName
	 * @return
	 */
	public RunData getByRunIdFieldName(Long runId,String fieldName);
	
	/**
	 * 保存流程实例对应的变量
	 * @param runId
	 * @param vars
	 */
	public void saveFlowVars(Long runId,Map<String,Object> vars);
	/**
	 * 取得某个流程对应的所有参数Map
	 * @param runId
	 * @return
	 */
	public Map<String,Object> getMapByRunId(Long runId);
}


