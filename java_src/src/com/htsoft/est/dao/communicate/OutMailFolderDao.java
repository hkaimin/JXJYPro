package com.htsoft.est.dao.communicate;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Limited company.
*/
import java.util.List;

import com.htsoft.core.dao.BaseDao;
import com.htsoft.est.model.communicate.MailFolder;
import com.htsoft.est.model.communicate.OutMailFolder;

/**
 * 
 * @author 
 *
 */
public interface OutMailFolderDao extends BaseDao<OutMailFolder>{
	public List<OutMailFolder> getAllUserFolderByParentId(Long userId,Long parentId);
	public List<OutMailFolder> getUserFolderByParentId(Long userId,Long parentId);
	public List<OutMailFolder> getFolderLikePath(String path);
	
	
	
}