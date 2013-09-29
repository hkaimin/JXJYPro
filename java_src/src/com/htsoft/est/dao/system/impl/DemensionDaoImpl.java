package com.htsoft.est.dao.system.impl;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.est.dao.system.DemensionDao;
import com.htsoft.est.model.system.Demension;

@SuppressWarnings("unchecked")
public class DemensionDaoImpl extends BaseDaoImpl<Demension> implements DemensionDao{

	public DemensionDaoImpl() {
		super(Demension.class);
	}

}