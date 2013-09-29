package com.htsoft.est.dao.system;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   --  http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import java.util.List;

import com.htsoft.core.dao.BaseDao;
import com.htsoft.est.model.system.UserSub;

/**
 * 
 * @author 
 *
 */
public interface UserSubDao extends BaseDao<UserSub>{
	
	/**
	 * 查找上属的ID
	 */
	public List<Long> upUser(Long userId);
	
	/**
	 * 查找已经是下属的ID列表
	 */
	public List<Long> subUsers(Long userId);
}