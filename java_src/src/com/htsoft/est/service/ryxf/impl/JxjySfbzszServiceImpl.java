package com.htsoft.est.service.ryxf.impl;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.est.dao.ryxf.JxjySfbzszDao;

import com.htsoft.est.model.jxjy.JxjySfbzsz;
import com.htsoft.est.service.ryxf.JxjySfbzszService;

public class JxjySfbzszServiceImpl extends BaseServiceImpl<JxjySfbzsz> implements JxjySfbzszService{
	@SuppressWarnings("unused")
	private JxjySfbzszDao dao;
	
	public JxjySfbzszServiceImpl(JxjySfbzszDao dao) {
		super(dao);
		this.dao=dao;
	}

}