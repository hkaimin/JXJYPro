package com.htsoft.est.dao.flow.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Limited company.
*/
import java.util.List;

import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.est.dao.flow.FormDefDao;
import com.htsoft.est.model.flow.FormDef;

public class FormDefDaoImpl extends BaseDaoImpl<FormDef> implements FormDefDao{

	public FormDefDaoImpl() {
		super(FormDef.class);
	}

	@Override
	public List<FormDef> getByDeployId(String deployId) {
		String hql="from FormDef fd where deployId=?";
		return findByHql(hql, new Object[]{deployId});
	}
	
	/**
	 * 
	 * @param deployId
	 * @param activityName
	 * @return
	 */
	public FormDef getByDeployIdActivityName(String deployId,String activityName){
		String hql="from FormDef fd where fd.deployId=? and fd.activityName=?";
		return (FormDef)findUnique(hql, new Object[]{deployId,activityName});
	}
}