package com.htsoft.est.dao.document.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   --  http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import java.util.List;
import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.est.dao.document.DocFolderDao;
import com.htsoft.est.model.document.DocFolder;

public class DocFolderDaoImpl extends BaseDaoImpl<DocFolder> implements DocFolderDao{

	public DocFolderDaoImpl() {
		super(DocFolder.class);
	}
	
	/**
	 * 取得某用户对应的所有文件夹
	 * @param userId
	 * @param parentId
	 * @return
	 */
	public List<DocFolder> getUserFolderByParentId(Long userId,Long parentId){
		
		String hql="from DocFolder df where df.isShared=0 and df.appUser.userId=? and parentId=?";
		return findByHql(hql, new Object[]{userId,parentId});
	}
	/**
	 * 取得某path下的所有Folder
	 * @param path
	 * @return
	 */
	public List<DocFolder> getFolderLikePath(String path){
		String hql="from DocFolder df where df.path like ?";
		return findByHql(hql,new Object[]{path+'%'});
	}

	@Override
	public List<DocFolder> getPublicFolderByParentId(Long parentId) {
        String hql="from DocFolder df where df.isShared=1 and df.parentId=? ";
		return findByHql(hql, new Object[]{parentId});
	}

	@Override
	public List<DocFolder> findByParentId(Long parentId) {
		String hql="from DocFolder df where df.parentId=?";
		return findByHql(hql,new Object[]{parentId});
	}

	@Override
	public List<DocFolder> findByUserAndName(Long userId, String foleName) {
		String hql="from DocFolder df where df.isShared=0 and df.appUser.userId=? and df.folderName like ?";
		return findByHql(hql,new Object[]{userId,"%"+foleName+"%"});
	}

	@Override
	public List<DocFolder> getOnlineFolderByParentId(Long parentId) {
		String hql="from DocFolder df where df.isShared=2 and df.parentId=? ";
		return findByHql(hql, new Object[]{parentId});
	}

}