package com.htsoft.est.service.system.impl;
/*
 *  广州宏天软件有限公司
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Limited Company.
*/
import java.util.List;

import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.est.dao.system.UserOrgDao;
import com.htsoft.est.model.system.UserOrg;
import com.htsoft.est.service.system.UserOrgService;

public class UserOrgServiceImpl extends BaseServiceImpl<UserOrg> implements UserOrgService{
	@SuppressWarnings("unused")
	private UserOrgDao dao;
	
	public UserOrgServiceImpl(UserOrgDao dao) {
		super(dao);
		this.dao=dao;
	}
	
	/**
	 * 取某一用户所有的部门
	 * @param userId
	 * @return
	 */
	public List<UserOrg> getDepOrgsByUserId(Long userId){
		return dao.getDepOrgsByUserId(userId);
	}

	/**
	 * 删除UserOrg对象
	 * @param userId
	 * @param orgId
	 * @return
	 */
	public void delUserOrg(Long orgId) {
		dao.delUserOrg(orgId);
	}
	
	public List<UserOrg> getByUserIdOrgId(Long userId,Long orgId){
		return dao.getByUserIdOrgId(userId,orgId);
	}

}