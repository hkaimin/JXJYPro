package com.htsoft.est.service.system.impl;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.est.dao.system.DemensionDao;
import com.htsoft.est.model.system.Demension;
import com.htsoft.est.service.system.DemensionService;

public class DemensionServiceImpl extends BaseServiceImpl<Demension> implements DemensionService{
	@SuppressWarnings("unused")
	private DemensionDao dao;
	
	public DemensionServiceImpl(DemensionDao dao) {
		super(dao);
		this.dao=dao;
	}

}