package com.htsoft.est.dao.system.impl;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import java.util.ArrayList;
import java.util.List;

import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.est.dao.system.OrganizationDao;
import com.htsoft.est.model.system.Organization;

@SuppressWarnings("unchecked")
public class OrganizationDaoImpl extends BaseDaoImpl<Organization> implements OrganizationDao{

	public OrganizationDaoImpl() {
		super(Organization.class);
	}
	
	/**
	 * 取得某个节点下的所有子节点
	 * @param parentId
	 * @param demId
	 * @return
	 */
	public List<Organization> getByParent(Long parentId,Long demId){
		ArrayList params=new ArrayList();
		String hql="from Organization p where p.orgSupId=?";
		params.add(parentId);
		if(demId!=0 && demId!=null){
			hql+= " and p.demension.demId=? ";
			params.add(demId);
		}
		return findByHql(hql, params.toArray());
	}
	/**
	 * 按路径查找所有节点
	 * @param path
	 * @return
	 */
	public List<Organization> getByPath(String path){
		String hql="from Organization p where p.path like ?";
		
		return findByHql(hql,new Object[]{path+"%"});
	}

}