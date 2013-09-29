package com.htsoft.est.dao.system;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Limited company.
*/
import java.util.List;

import com.htsoft.core.dao.BaseDao;
import com.htsoft.est.model.system.IndexDisplay;

/**
 * 
 * @author 
 *
 */
public interface IndexDisplayDao extends BaseDao<IndexDisplay>{
	/**
	 * 根据当前用户查找相应的模块。
	 * @param userId
	 * @return
	 */
	public List<IndexDisplay> findByUser(Long userId);
}