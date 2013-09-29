package com.htsoft.est.service.system.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Limited company.
*/
import java.util.List;

import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.est.dao.system.IndexDisplayDao;
import com.htsoft.est.model.system.IndexDisplay;
import com.htsoft.est.service.system.IndexDisplayService;

public class IndexDisplayServiceImpl extends BaseServiceImpl<IndexDisplay> implements IndexDisplayService{
	private IndexDisplayDao dao;
	
	public IndexDisplayServiceImpl(IndexDisplayDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<IndexDisplay> findByUser(Long userId) {
		return dao.findByUser(userId);
	}

}