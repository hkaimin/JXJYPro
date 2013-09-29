package com.htsoft.est.service.communicate.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import java.util.List;

import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.est.dao.communicate.MailBoxDao;
import com.htsoft.est.model.communicate.MailBox;
import com.htsoft.est.service.communicate.MailBoxService;

public class MailBoxServiceImpl extends BaseServiceImpl<MailBox> implements MailBoxService{
	private MailBoxDao dao;
	
	public MailBoxServiceImpl(MailBoxDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public Long CountByFolderId(Long folderId) {
		return dao.CountByFolderId(folderId);
	}

	public List<MailBox> findByFolderId(Long folderId){
		return dao.findByFolderId(folderId);
	}

	@Override
	public List<MailBox> findBySearch(String searchContent, PagingBean pb) {
		return dao.findBySearch(searchContent,pb);
	}
}