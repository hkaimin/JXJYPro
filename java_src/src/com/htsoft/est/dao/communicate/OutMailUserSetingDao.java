package com.htsoft.est.dao.communicate;

/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Limited company.
 */

import java.util.List;

import com.htsoft.core.dao.BaseDao;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.est.model.communicate.OutMailUserSeting;

/**
 * @description 外部邮箱管理
 * @class OutMailUserSetingDao
 * @extend BaseDao
 * 
 */
public interface OutMailUserSetingDao extends BaseDao<OutMailUserSeting> {

	/**
	 * 根据当前登陆人，取得外部邮箱设置
	 */
	OutMailUserSeting getByLoginId(Long loginId);
	
	public List findByUserAll();
	
	/**
	 *根据用户名查询邮箱设置 
	 */
	public List<OutMailUserSeting> findByUserAll(String userName,PagingBean pb);
}