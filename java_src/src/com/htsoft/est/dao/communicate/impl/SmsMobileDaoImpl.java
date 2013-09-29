package com.htsoft.est.dao.communicate.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Limited company.
*/
import java.util.List;

import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.est.dao.communicate.SmsMobileDao;
import com.htsoft.est.model.communicate.SmsMobile;

public class SmsMobileDaoImpl extends BaseDaoImpl<SmsMobile> implements SmsMobileDao{

	public SmsMobileDaoImpl() {
		super(SmsMobile.class);
	}

	@Override
	public List<SmsMobile> getNeedToSend() {
		String hql = "from SmsMobile sm where sm.status = ? order by sm.sendTime desc";
		Object[] params = {SmsMobile.STATUS_NOT_SENDED};
		return findByHql(hql, params);
	}

}