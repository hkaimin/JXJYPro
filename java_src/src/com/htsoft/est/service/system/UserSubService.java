package com.htsoft.est.service.system;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import java.util.List;
import java.util.Set;

import com.htsoft.core.service.BaseService;
import com.htsoft.est.model.system.AppUser;
import com.htsoft.est.model.system.UserSub;

public interface UserSubService extends BaseService<UserSub>{
	/**
	 *  查找所有上属IDs
	 */
	public Set<Long> findAllUpUser(Long userId);
	
	/**
	 * 查找已经是下属的ID列表
	 */
	public List<Long> subUsers(Long userId);
	
	/**
	 * 查找上一级的ID
	 */
	public List<Long> upUser(Long userId);
	
}


