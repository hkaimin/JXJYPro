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
	
	@Override
	public List<Organization> getCompanyByParent(Long parentId, Long demId) {
		// TODO Auto-generated method stub
		ArrayList<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("from Organization o where o.orgSupId=?");
		params.add(parentId);
		if(demId != null && demId != 0) {
			hql.append(" and o.demension.demId=?");
			params.add(demId);
		}
//		hql.append(" and o.orgType=?");
//		params.add(Organization.ORG_TYPE_COMPANY);
		return this.findByHql(hql.toString(), params.toArray());
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