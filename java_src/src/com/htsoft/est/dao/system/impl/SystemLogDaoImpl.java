package com.htsoft.est.dao.system.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Limited company.
*/
import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.est.dao.system.SystemLogDao;
import com.htsoft.est.model.system.SystemLog;

public class SystemLogDaoImpl extends BaseDaoImpl<SystemLog> implements SystemLogDao{

	public SystemLogDaoImpl() {
		super(SystemLog.class);
	}

}