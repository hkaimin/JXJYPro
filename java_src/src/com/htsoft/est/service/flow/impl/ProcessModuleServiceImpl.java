package com.htsoft.est.service.flow.impl;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.est.dao.flow.ProcessModuleDao;
import com.htsoft.est.model.flow.ProcessModule;
import com.htsoft.est.service.flow.ProcessModuleService;

public class ProcessModuleServiceImpl extends BaseServiceImpl<ProcessModule> implements ProcessModuleService{
	@SuppressWarnings("unused")
	private ProcessModuleDao dao;
	
	public ProcessModuleServiceImpl(ProcessModuleDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public ProcessModule getByKey(String string) {
		return dao.getByKey(string);
	}

}