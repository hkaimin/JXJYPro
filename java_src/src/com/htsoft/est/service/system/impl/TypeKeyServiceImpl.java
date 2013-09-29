package com.htsoft.est.service.system.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Limited company.
*/
import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.est.dao.system.TypeKeyDao;
import com.htsoft.est.model.system.TypeKey;
import com.htsoft.est.service.system.TypeKeyService;

public class TypeKeyServiceImpl extends BaseServiceImpl<TypeKey> implements TypeKeyService{
	private TypeKeyDao dao;
	
	public TypeKeyServiceImpl(TypeKeyDao dao) {
		super(dao);
		this.dao=dao;
	}

}