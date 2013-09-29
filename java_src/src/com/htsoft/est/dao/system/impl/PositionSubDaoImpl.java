package com.htsoft.est.dao.system.impl;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import java.util.List;

import org.hibernate.Query;

import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.est.dao.system.PositionSubDao;
import com.htsoft.est.model.system.Position;
import com.htsoft.est.model.system.PositionSub;

@SuppressWarnings("unchecked")
public class PositionSubDaoImpl extends BaseDaoImpl<PositionSub> implements PositionSubDao{

	public PositionSubDaoImpl() {
		super(PositionSub.class);
	}

}