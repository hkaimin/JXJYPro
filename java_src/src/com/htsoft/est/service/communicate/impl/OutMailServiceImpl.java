package com.htsoft.est.service.communicate.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Limited company.
*/
import java.util.List;
import java.util.Map;

import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.est.dao.communicate.OutMailDao;
import com.htsoft.est.model.communicate.OutMail;
import com.htsoft.est.service.communicate.OutMailService;

public class OutMailServiceImpl extends BaseServiceImpl<OutMail> implements OutMailService{
	private OutMailDao dao;
	
	public OutMailServiceImpl(OutMailDao dao) {
		super(dao);
		this.dao=dao;
	}
	public List<OutMail> findByFolderId(Long folderId){
		return dao.findByFolderId( folderId);
	}

	public Long CountByFolderId(Long folderId){
		return dao.CountByFolderId(folderId);
	}
	public Map getUidByUserId(Long userId){
		return dao.getUidByUserId(userId);
	}
	
}