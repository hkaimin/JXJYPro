package com.htsoft.est.service.flow;

/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
 */
import com.htsoft.core.service.BaseService;
import com.htsoft.est.model.flow.TaskSign;

/**
 * @description 任务会签
 * @class TaskSignService
 * @author YHZ
 * @company www.jee-soft.cn
 * @data 2011-1-5PM
 * 
 */
public interface TaskSignService extends BaseService<TaskSign> {

	/**
	 * @description 根据assignId查询TaskSign对象
	 * @param assignId
	 *            assignId
	 * @return TaskSign
	 */
	public TaskSign findByAssignId(Long assignId);
}
