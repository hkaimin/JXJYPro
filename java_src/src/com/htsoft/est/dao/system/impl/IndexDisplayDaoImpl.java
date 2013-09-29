package com.htsoft.est.dao.system.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Limited company.
*/
import java.util.List;

import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.est.dao.system.IndexDisplayDao;
import com.htsoft.est.model.system.IndexDisplay;

public class IndexDisplayDaoImpl extends BaseDaoImpl<IndexDisplay> implements IndexDisplayDao{

	public IndexDisplayDaoImpl() {
		super(IndexDisplay.class);
	}

	@Override
	public List<IndexDisplay> findByUser(Long userId) {
		String hql="from IndexDisplay vo where vo.appUser.userId=?";
		return findByHql(hql,new Object[]{userId});
	}

}