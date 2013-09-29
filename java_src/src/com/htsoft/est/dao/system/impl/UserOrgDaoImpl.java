package com.htsoft.est.dao.system.impl;
/*
 *  广州宏天软件有限公司
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Limited Company.
*/
import java.util.List;

import org.hibernate.Query;

import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.est.dao.system.UserOrgDao;
import com.htsoft.est.model.system.Organization;
import com.htsoft.est.model.system.UserOrg;

@SuppressWarnings("unchecked")
public class UserOrgDaoImpl extends BaseDaoImpl<UserOrg> implements UserOrgDao{

	public UserOrgDaoImpl() {
		super(UserOrg.class);
	}
	/**
	 * 取某一用户所有的部门
	 * @param userId
	 * @return
	 */
	public List<UserOrg> getDepOrgsByUserId(Long userId){
		//String hql="from UserOrg uo where uo.appUser.userId=? and (uo.organization.orgType=? or uo.organization.orgType=?)";
		String hql="from UserOrg uo where uo.appUser.userId=? ";
		//return findByHql(hql,new Object[]{userId,Organization.ORG_TYPE_COMPANY,Organization.ORG_TYPE_DEPARTMENT});
		 return findByHql(hql,new Object[]{userId});

	}
	
	/**
	 * 删除UserOrg对象
	 * @param userId
	 * @param orgId
	 * @return
	 */
	public void delUserOrg(Long orgId) {
		String hql = "delete UserOrg uo where uo.organization.orgId=?";
		getSession().createQuery(hql);
	}
	
	public List<UserOrg> getByUserIdOrgId(Long userId,Long orgId){
		String hql="from UserOrg uo where uo.appUser.userId=? and uo.organization.orgId=?";
		return(List<UserOrg>) findByHql(hql,new Object[]{userId,orgId});
	}

}