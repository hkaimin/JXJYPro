package com.htsoft.est.service.system.impl;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import java.util.List;

import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.est.dao.system.OrganizationDao;
import com.htsoft.est.model.system.Organization;
import com.htsoft.est.service.system.OrganizationService;

public class OrganizationServiceImpl extends BaseServiceImpl<Organization> implements OrganizationService{
	@SuppressWarnings("unused")
	private OrganizationDao dao;
	
	public OrganizationServiceImpl(OrganizationDao dao) {
		super(dao);
		this.dao=dao;
	}
	
	/**
	 * 取得某个节点下的所有子节点
	 * @param parentId
	 * @param demId
	 * @return
	 */
	public List<Organization> getByParent(Long parentId,Long demId){
		return dao.getByParent(parentId, demId);
	}
	/**
	 * 按路径查找所有节点
	 * @param path
	 * @return
	 */
	public List<Organization> getByPath(String path){
		return dao.getByPath(path);
	}
	
	/**
	 * 删除某个组织及其下属组织
	 * @param posId
	 */
	public void delCascade(Long orgId){
		Organization org=get(orgId);
		evict(org);
		List<Organization> listOrgs=getByPath(org.getPath());
		for(Organization o:listOrgs){
			remove(o);
		}
	}

}