package com.htsoft.est.dao.task;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   --  http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import java.util.List;

import com.htsoft.core.dao.BaseDao;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.est.model.task.Appointment;

/**
 * 
 * @author 
 *
 */
public interface AppointmentDao extends BaseDao<Appointment>{
	//首页中根据当前登录用户显示约会列表
	public List showAppointmentByUserId(Long userId,PagingBean pb);
}