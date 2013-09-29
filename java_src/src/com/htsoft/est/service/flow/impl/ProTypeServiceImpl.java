package com.htsoft.est.service.flow.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.est.dao.flow.ProTypeDao;
import com.htsoft.est.model.flow.ProType;
import com.htsoft.est.service.flow.ProTypeService;

public class ProTypeServiceImpl extends BaseServiceImpl<ProType> implements ProTypeService{
	private ProTypeDao dao;
	
	public ProTypeServiceImpl(ProTypeDao dao) {
		super(dao);
		this.dao=dao;
	}

}