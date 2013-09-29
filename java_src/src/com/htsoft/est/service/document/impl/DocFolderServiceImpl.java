package com.htsoft.est.service.document.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import java.util.List;

import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.est.dao.document.DocFolderDao;
import com.htsoft.est.model.document.DocFolder;
import com.htsoft.est.service.document.DocFolderService;

public class DocFolderServiceImpl extends BaseServiceImpl<DocFolder> implements DocFolderService{
	private DocFolderDao dao;
	
	public DocFolderServiceImpl(DocFolderDao dao) {
		super(dao);
		this.dao=dao;
	}
	
	public List<DocFolder> getUserFolderByParentId(Long userId,Long parentId){
		return dao.getUserFolderByParentId(userId, parentId);
	}
	
	/**
	 * 取得某path下的所有Folder
	 * @param path
	 * @return
	 */
	public List<DocFolder> getFolderLikePath(String path){
		return dao.getFolderLikePath(path);
	}

	@Override
	public List<DocFolder> getPublicFolderByParentId(Long parentId) {
		return dao.getPublicFolderByParentId( parentId);
	}

	@Override
	public List<DocFolder> findByParentId(Long parentId) {
		
		return dao.findByParentId(parentId);
	}

	@Override
	public List<DocFolder> findByUserAndName(Long userId, String foleName) {
		return dao.findByUserAndName(userId, foleName);
	}

	@Override
	public List<DocFolder> getOnlineFolderByParentId(Long parentId) {
		return dao.getOnlineFolderByParentId(parentId);
	}

}