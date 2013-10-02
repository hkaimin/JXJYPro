package com.htsoft.est.service.ryxf.impl;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.est.dao.ryxf.JxjyXmglDao;
import com.htsoft.est.model.jxjy.JxjyXmgl;
import com.htsoft.est.service.ryxf.JxjyXmglService;

public class JxjyXmglServiceImpl extends BaseServiceImpl<JxjyXmgl> implements JxjyXmglService{
	@SuppressWarnings("unused")
	private JxjyXmglDao dao;
	
	public JxjyXmglServiceImpl(JxjyXmglDao dao) {
		super(dao);
		this.dao=dao;
	}

}