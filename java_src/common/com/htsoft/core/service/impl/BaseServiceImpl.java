package com.htsoft.core.service.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import com.htsoft.core.dao.GenericDao;
import com.htsoft.core.service.BaseService;

public class BaseServiceImpl<T> extends GenericServiceImpl<T, Long> implements BaseService<T>{

	public BaseServiceImpl(GenericDao dao) {
		super(dao);
	}
	
}
