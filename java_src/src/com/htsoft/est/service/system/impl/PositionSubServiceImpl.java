package com.htsoft.est.service.system.impl;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import java.util.List;

import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.est.dao.system.PositionSubDao;
import com.htsoft.est.model.system.PositionSub;
import com.htsoft.est.service.system.PositionSubService;

public class PositionSubServiceImpl extends BaseServiceImpl<PositionSub> implements PositionSubService{
	@SuppressWarnings("unused")
	private PositionSubDao dao;
	
	public PositionSubServiceImpl(PositionSubDao dao) {
		super(dao);
		this.dao=dao;
	}

}