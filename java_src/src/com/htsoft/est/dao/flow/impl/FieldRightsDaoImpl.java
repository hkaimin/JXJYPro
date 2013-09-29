package com.htsoft.est.dao.flow.impl;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import java.util.List;

import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.est.dao.flow.FieldRightsDao;
import com.htsoft.est.model.flow.FieldRights;

@SuppressWarnings("unchecked")
public class FieldRightsDaoImpl extends BaseDaoImpl<FieldRights> implements FieldRightsDao{

	public FieldRightsDaoImpl() {
		super(FieldRights.class);
	}

	@Override
	public List<FieldRights> getByMappingFieldTaskName(Long mappingId,
			Long fieldId, String taskName) {
		String hql="from FieldRights vo where vo.formField.fieldId=? and vo.mappingId=? and vo.taskName=?";
		return findByHql(hql, new Object[]{fieldId,mappingId,taskName});
	}

	@Override
	public List<FieldRights> getByMappingIdAndTaskName(Long mappingId,
			String taskName) {
		String hql="from FieldRights vo where vo.mappingId=? and vo.taskName=?";
		return findByHql(hql, new Object[]{mappingId,taskName});
	}

	@Override
	public List<FieldRights> getByMappingId(Long mappingId) {
		String hql="from FieldRights vo where vo.mappingId=?";
		return findByHql(hql, new Object[]{mappingId});
	}

}