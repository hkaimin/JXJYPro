package com.htsoft.est.service.system.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Limited company.
*/
import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.est.dao.system.SystemLogDao;
import com.htsoft.est.model.system.SystemLog;
import com.htsoft.est.service.system.SystemLogService;

public class SystemLogServiceImpl extends BaseServiceImpl<SystemLog> implements SystemLogService{
	private SystemLogDao dao;
	
	public SystemLogServiceImpl(SystemLogDao dao) {
		super(dao);
		this.dao=dao;
	}

}