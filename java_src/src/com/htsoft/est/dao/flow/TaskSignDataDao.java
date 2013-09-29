package com.htsoft.est.dao.flow;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import java.util.List;

import com.htsoft.core.dao.BaseDao;
import com.htsoft.est.model.flow.TaskSignData;

/**
 * 任务会签情况Dao
 * @author 
 *
 */
public interface TaskSignDataDao extends BaseDao<TaskSignData>{
	/**
	 * 返回某个（父）任务的投票情况
	 * @param taskId 任务ID
	 * @param isAgree 是否同意
	 * @return
	 */
	public Long getVoteCounts(String taskId,Short isAgree);
	
	/**
	 * 取得某任务对应的会签投票情况
	 * @param taskId
	 * @return
	 */
	public List<TaskSignData> getByTaskId(String taskId);
}