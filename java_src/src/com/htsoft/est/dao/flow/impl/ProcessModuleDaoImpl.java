package com.htsoft.est.dao.flow.impl;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import java.util.List;

import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.est.dao.flow.ProcessModuleDao;
import com.htsoft.est.model.flow.ProcessModule;

@SuppressWarnings("unchecked")
public class ProcessModuleDaoImpl extends BaseDaoImpl<ProcessModule> implements ProcessModuleDao{

	public ProcessModuleDaoImpl() {
		super(ProcessModule.class);
	}

	@Override
	public ProcessModule getByKey(String string) {
		String hql = "from ProcessModule pm where pm.modulekey=?";
		List<ProcessModule> list = findByHql(hql, new Object[]{string});
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

}