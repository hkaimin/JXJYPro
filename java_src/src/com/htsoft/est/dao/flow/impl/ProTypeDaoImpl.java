package com.htsoft.est.dao.flow.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   --  http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.est.dao.flow.ProTypeDao;
import com.htsoft.est.model.flow.ProType;

public class ProTypeDaoImpl extends BaseDaoImpl<ProType> implements ProTypeDao{

	public ProTypeDaoImpl() {
		super(ProType.class);
	}

}