package com.htsoft.est.dao.communicate;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   --  http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import java.util.List;

import com.htsoft.core.dao.BaseDao;
import com.htsoft.est.model.communicate.MailFolder;
import com.htsoft.est.model.document.DocFolder;

/**
 * 
 * @author 
 *
 */
public interface MailFolderDao extends BaseDao<MailFolder>{

	public List<MailFolder> getUserFolderByParentId(Long userId, Long parentId);

	public List<MailFolder> getAllUserFolderByParentId(Long userId,Long parentId);

	public List<MailFolder> getFolderLikePath(String path);
	
}