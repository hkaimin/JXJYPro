package com.htsoft.est.service.communicate.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import java.util.List;

import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.est.dao.communicate.MailFolderDao;
import com.htsoft.est.model.communicate.MailFolder;
import com.htsoft.est.model.document.DocFolder;
import com.htsoft.est.service.communicate.MailFolderService;

public class MailFolderServiceImpl extends BaseServiceImpl<MailFolder> implements MailFolderService{
	private MailFolderDao dao;
	
	public MailFolderServiceImpl(MailFolderDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<MailFolder> getUserFolderByParentId(Long curUserId,Long parentId) {
		
		return dao.getUserFolderByParentId(curUserId, parentId);
	}

	@Override
	public List<MailFolder> getAllUserFolderByParentId(Long userId,
			Long parentId) {
		return dao.getAllUserFolderByParentId(userId,parentId);
	}

	@Override
	public List<MailFolder> getFolderLikePath(String path) {
		return dao.getFolderLikePath(path);
	}

	

}