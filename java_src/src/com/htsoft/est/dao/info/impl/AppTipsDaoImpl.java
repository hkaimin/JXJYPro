package com.htsoft.est.dao.info.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Limited company.
*/
import java.util.List;

import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.est.dao.info.AppTipsDao;
import com.htsoft.est.model.info.AppTips;

public class AppTipsDaoImpl extends BaseDaoImpl<AppTips> implements AppTipsDao{

	public AppTipsDaoImpl() {
		super(AppTips.class);
	}

	@Override
	public List<AppTips> findByName(String name) {
		String hql="from AppTips vo where vo.tipsName=?";
		return findByHql(hql,new Object[]{name});
	}

}