package com.htsoft.est.dao.communicate;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Limited company.
*/
import java.util.List;

import com.htsoft.core.dao.BaseDao;
import com.htsoft.est.model.communicate.OutMail;

import java.util.*;

/**
 * 
 * @author 
 *
 */
public interface OutMailDao extends BaseDao<OutMail>{
	public List<OutMail> findByFolderId(Long folderId);
	public Long CountByFolderId(Long folderId);
	public Map getUidByUserId(Long userId);
}