package com.htsoft.est.service.ryxf.impl;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.est.dao.ryxf.JxjySfbzmxDao;

import com.htsoft.est.model.jxjy.JxjySfbzmx;
import com.htsoft.est.service.ryxf.JxjySfbzmxService;

public class JxjySfbzmxServiceImpl extends BaseServiceImpl<JxjySfbzmx> implements JxjySfbzmxService{
	@SuppressWarnings("unused")
	private JxjySfbzmxDao dao;
	
	public JxjySfbzmxServiceImpl(JxjySfbzmxDao dao) {
		super(dao);
		this.dao=dao;
	}

}