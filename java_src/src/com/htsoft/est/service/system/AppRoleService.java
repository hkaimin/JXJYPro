package com.htsoft.est.service.system;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/

import java.util.HashMap;
import java.util.Set;

import javax.jws.WebService;

import com.htsoft.core.service.BaseService;
import com.htsoft.est.model.system.AppRole;
@WebService
public interface AppRoleService extends BaseService<AppRole>{
	public AppRole getByRoleName(String roleName);
	
	/**
	 * 获取安全认证的数据源
	 * @return
	 */
	public HashMap<String,Set<String>>getSecurityDataSource();
	
	public AppRole getById(Long roleId);
}
