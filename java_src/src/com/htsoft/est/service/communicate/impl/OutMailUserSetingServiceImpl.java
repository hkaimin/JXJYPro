package com.htsoft.est.service.communicate.impl;

/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Limited company.
 */

import java.util.List;

import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.est.dao.communicate.OutMailUserSetingDao;
import com.htsoft.est.model.communicate.OutMailUserSeting;
import com.htsoft.est.service.communicate.OutMailUserSetingService;

/**
 * @description 外部邮箱设置管理
 * @class OutMailUserSetingServiceImpl
 * 
 */
public class OutMailUserSetingServiceImpl extends
		BaseServiceImpl<OutMailUserSeting> implements OutMailUserSetingService {
	private OutMailUserSetingDao dao;

	public OutMailUserSetingServiceImpl(OutMailUserSetingDao dao) {
		super(dao);
		this.dao = dao;
	}

	public OutMailUserSeting getByLoginId(Long loginid) {
		return dao.getByLoginId(loginid);
	}

	@Override
	public List findByUserAll() {
		return dao.findByUserAll();
	}

	@Override
	public List<OutMailUserSeting> findByUserAll(String userName,PagingBean pb){
		return dao.findByUserAll(userName,pb);
	}

}