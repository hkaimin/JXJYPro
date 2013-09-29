package com.htsoft.est.dao.flow;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   --  http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import java.util.List;

import com.htsoft.core.dao.BaseDao;
import com.htsoft.est.model.flow.RunData;

/**
 * 
 * @author 
 *
 */
public interface RunDataDao extends BaseDao<RunData>{

	/**
	 * 取得某个运行实例中某个字段的详细信息
	 * @param runId
	 * @param fieldName
	 * @return
	 */
	public RunData getByRunIdFieldName(Long runId,String fieldName);
	
	/**
	 * 取得某个流程对应的参数数据列表
	 * @param runId
	 * @return
	 */
	public List<RunData> getByRunId(Long runId);
}