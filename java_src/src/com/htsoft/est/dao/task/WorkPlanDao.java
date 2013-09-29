package com.htsoft.est.dao.task;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   --  http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import java.util.List;

import com.htsoft.core.dao.BaseDao;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.est.model.system.AppUser;
import com.htsoft.est.model.task.WorkPlan;

/**
 * 
 * @author 
 *
 */
public interface WorkPlanDao extends BaseDao<WorkPlan>{
	
	/**
	 * 查找部门分配的计划
	 */
	public List<WorkPlan> findByDepartment(WorkPlan workPlan,AppUser user,PagingBean pb);
	
	/**
	 *计划到期提醒
	 */
	public List<WorkPlan> sendWorkPlanTime();
}