package com.htsoft.est.dao.communicate.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   --  http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import java.util.List;

import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.est.dao.communicate.MailFolderDao;
import com.htsoft.est.model.communicate.MailFolder;
import com.htsoft.est.model.document.DocFolder;

public class MailFolderDaoImpl extends BaseDaoImpl<MailFolder> implements MailFolderDao{

	public MailFolderDaoImpl() {
		super(MailFolder.class);
	}
	/**
	 * 根据用户ID,或文件夹ParentID取得文件夹
	 */
	@Override
	public List<MailFolder> getUserFolderByParentId(Long userId, Long parentId) {
		String hql = "from MailFolder mf where mf.appUser.userId=? and parentId=?";
		return findByHql(hql, new Object[]{userId, parentId});
	}
	
	/**
	 * 根据用户ID,或文件夹ParentID,或用户ID为空取得文件夹
	 * 取得MailFolder第一层文件夹,包括收件箱,发件箱,草稿箱,删除箱
	 */
	@Override
	public List<MailFolder> getAllUserFolderByParentId(Long userId,Long parentId) {
		String hql = "from MailFolder mf where mf.appUser.userId=? and parentId=? or userId is null";
		return findByHql(hql, new Object[]{userId, parentId});
	}

	/**
	 * 取得某path下的所有Folder
	 * @param path
	 * @return
	 */
	@Override
	public List<MailFolder> getFolderLikePath(String path) {
		String hql="from MailFolder mf where mf.path like ?";
		return findByHql(hql,new Object[]{path+'%'});
	}

}