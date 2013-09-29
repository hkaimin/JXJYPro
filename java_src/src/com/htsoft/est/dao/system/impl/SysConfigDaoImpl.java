package com.htsoft.est.dao.system.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   --  http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import java.util.List;

import org.hibernate.Query;

import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.est.dao.system.SysConfigDao;
import com.htsoft.est.model.system.SysConfig;

public class SysConfigDaoImpl extends BaseDaoImpl<SysConfig> implements SysConfigDao{

	public SysConfigDaoImpl() {
		super(SysConfig.class);
	}

	@Override
	public SysConfig findByKey(String key) {
		String hql="from SysConfig vo where vo.configKey=?";
		Object[] objs={key};
		List<SysConfig> list=findByHql(hql, objs);
		if(list.size()>0)
		return (SysConfig)list.get(0);
		else return null;
	}

	@Override
	public List<SysConfig> findConfigByTypeKey(String typeKey) {
		String hql="from SysConfig vo where vo.typeKey=?";
		Object[] objs={typeKey};
		return findByHql(hql, objs);
	}

	@Override
	public List findTypeKeys() {
		String sql="select vo.typeKey from SysConfig vo group by vo.typeKey";
		Query query=getSession().createQuery(sql);
		return query.list();
	}
}