package com.htsoft.est.dao.system.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Limited company.
*/
import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.est.dao.system.TypeKeyDao;
import com.htsoft.est.model.system.TypeKey;

public class TypeKeyDaoImpl extends BaseDaoImpl<TypeKey> implements TypeKeyDao{

	public TypeKeyDaoImpl() {
		super(TypeKey.class);
	}

}