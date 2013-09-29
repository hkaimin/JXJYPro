package com.htsoft.est.service.flow.impl;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.est.dao.flow.ProDefRightsDao;
import com.htsoft.est.model.flow.ProDefRights;
import com.htsoft.est.service.flow.ProDefRightsService;

public class ProDefRightsServiceImpl extends BaseServiceImpl<ProDefRights> implements ProDefRightsService{
	@SuppressWarnings("unused")
	private ProDefRightsDao dao;
	
	public ProDefRightsServiceImpl(ProDefRightsDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public ProDefRights findByDefId(Long defId) {
		return dao.findByDefId(defId);
	}

	@Override
	public ProDefRights findByTypeId(Long proTypeId) {
		return dao.findByTypeId(proTypeId);
	}

}