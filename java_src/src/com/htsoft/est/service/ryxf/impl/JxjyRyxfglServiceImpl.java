package com.htsoft.est.service.ryxf.impl;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import java.util.List;

import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.est.dao.ryxf.JxjyRyxfglDao;

import com.htsoft.est.model.jxjy.JxjyRyxfgl;
import com.htsoft.est.service.ryxf.JxjyRyxfglService;

public class JxjyRyxfglServiceImpl extends BaseServiceImpl<JxjyRyxfgl> implements JxjyRyxfglService{
	@SuppressWarnings("unused")
	private JxjyRyxfglDao dao;
	
	public JxjyRyxfglServiceImpl(JxjyRyxfglDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<JxjyRyxfgl> getRyxfBM(QueryFilter filter) {
		
		return dao.getRyxfBM(filter);
	}

}