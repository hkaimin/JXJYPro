package com.htsoft.est.service.document.impl;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.est.dao.document.SealDao;
import com.htsoft.est.model.document.Seal;
import com.htsoft.est.service.document.SealService;

public class SealServiceImpl extends BaseServiceImpl<Seal> implements SealService{
	@SuppressWarnings("unused")
	private SealDao dao;
	
	public SealServiceImpl(SealDao dao) {
		super(dao);
		this.dao=dao;
	}

}