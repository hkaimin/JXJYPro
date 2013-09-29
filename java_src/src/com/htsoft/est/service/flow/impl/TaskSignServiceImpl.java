package com.htsoft.est.service.flow.impl;

/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
 */
import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.est.dao.flow.TaskSignDao;
import com.htsoft.est.model.flow.TaskSign;
import com.htsoft.est.service.flow.TaskSignService;

/**
 * @description 任务会签
 * @class TaskSignServiceImpl
 * @author YHZ
 * @company www.jee-soft.cn
 * @data 2011-1-5PM
 * 
 */
public class TaskSignServiceImpl extends BaseServiceImpl<TaskSign> implements TaskSignService {
	private TaskSignDao dao;

	public TaskSignServiceImpl(TaskSignDao dao) {
		super(dao);
		this.dao = dao;
	}
	
	/**
	 * @description 根据assignId查询TaskSign对象数据
	 */
	@Override
	public TaskSign findByAssignId(Long assignId) {
		return dao.findByAssignId(assignId);
	}

}