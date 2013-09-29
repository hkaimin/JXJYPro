package com.htsoft.est.service.system.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import java.util.Map;
import java.util.Set;
import java.util.HashMap;

import javax.jws.WebService;

import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.est.dao.system.AppRoleDao;
import com.htsoft.est.model.system.AppRole;
import com.htsoft.est.service.system.AppRoleService;
@WebService
public class AppRoleServiceImpl extends BaseServiceImpl<AppRole> implements AppRoleService{
	private AppRoleDao dao;
	
	public AppRoleServiceImpl(AppRoleDao dao) {
		super(dao);
		this.dao=dao;
	}
	
	public AppRole getByRoleName(String roleName){
		return dao.getByRoleName(roleName);
	}
	/*
	 * (non-Javadoc)
	 * @see com.htsoft.est.service.system.AppRoleService#getSecurityDataSource()
	 */
	public HashMap<String,Set<String>>getSecurityDataSource(){
		return dao.getSecurityDataSource();
	}
	
	
	public AppRole getById(Long roleId){
		return get(roleId);
	}

}
