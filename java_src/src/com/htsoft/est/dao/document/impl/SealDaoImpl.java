package com.htsoft.est.dao.document.impl;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.est.dao.document.SealDao;
import com.htsoft.est.model.document.Seal;

@SuppressWarnings("unchecked")
public class SealDaoImpl extends BaseDaoImpl<Seal> implements SealDao{

	public SealDaoImpl() {
		super(Seal.class);
	}

}