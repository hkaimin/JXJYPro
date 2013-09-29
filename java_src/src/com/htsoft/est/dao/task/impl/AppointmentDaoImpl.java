package com.htsoft.est.dao.task.impl;

/*
 *  广州宏天软件有限公司 EST-BPM管理平台   --  http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
 */
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.est.dao.task.AppointmentDao;
import com.htsoft.est.model.task.Appointment;

@SuppressWarnings("unchecked")
public class AppointmentDaoImpl extends BaseDaoImpl<Appointment> implements
		AppointmentDao {

	public AppointmentDaoImpl() {
		super(Appointment.class);
	}

	/**
	 * 根据当前用户ID读取约会列表在首页显示
	 */
	@Override
	public List<Appointment> showAppointmentByUserId(Long userId, PagingBean pb) {
		ArrayList<Object> paramList = new ArrayList<Object>();
		Calendar cal = Calendar.getInstance();
		StringBuffer hql = new StringBuffer(
				"select vo from Appointment vo where vo.appUser.userId=? and vo.startTime > ? order by vo.startTime ASC");
		paramList.add(userId);
		paramList.add(cal.getTime());
		return findByHql(hql.toString(), paramList.toArray(), pb);
	}

}