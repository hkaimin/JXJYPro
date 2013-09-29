package com.htsoft.est.service.flow.impl;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import java.util.List;

import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.est.dao.flow.FieldRightsDao;
import com.htsoft.est.model.flow.FieldRights;
import com.htsoft.est.service.flow.FieldRightsService;

public class FieldRightsServiceImpl extends BaseServiceImpl<FieldRights> implements FieldRightsService{
	@SuppressWarnings("unused")
	private FieldRightsDao dao;
	
	public FieldRightsServiceImpl(FieldRightsDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<FieldRights> getByMappingFieldTaskName(Long mappingId,
			Long fieldId, String taskName) {
		return dao.getByMappingFieldTaskName(mappingId, fieldId, taskName);
	}

	@Override
	public List<FieldRights> getByMappingIdAndTaskName(Long mappingId,
			String taskName) {
		return dao.getByMappingIdAndTaskName(mappingId, taskName);
	}

	@Override
	public List<FieldRights> getByMappingId(Long mappingId) {
		return dao.getByMappingId(mappingId);
	}

}