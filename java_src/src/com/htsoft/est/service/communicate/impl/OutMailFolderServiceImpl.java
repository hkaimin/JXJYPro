package com.htsoft.est.service.communicate.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Limited company.
*/
import java.util.List;

import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.est.dao.communicate.OutMailFolderDao;
import com.htsoft.est.model.communicate.MailFolder;
import com.htsoft.est.model.communicate.OutMailFolder;
import com.htsoft.est.service.communicate.OutMailFolderService;

public class OutMailFolderServiceImpl extends BaseServiceImpl<OutMailFolder> implements OutMailFolderService{
	private OutMailFolderDao dao;
	
	public OutMailFolderServiceImpl(OutMailFolderDao dao) {
		super(dao);
		this.dao=dao;
	}
	@Override
	public List<OutMailFolder> getAllUserFolderByParentId(Long userId,
			Long parentId) {
		return dao.getAllUserFolderByParentId(userId,parentId);
	}
	@Override
	public List<OutMailFolder> getUserFolderByParentId(Long userId,Long parentId){
		return dao.getUserFolderByParentId(userId,parentId);
	}
	@Override
	public List<OutMailFolder> getFolderLikePath(String path){
		return dao.getFolderLikePath(path);
	}

}