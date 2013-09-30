package com.htsoft.est.service.system;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import java.util.List;

import com.htsoft.core.service.BaseService;
import com.htsoft.est.model.system.Organization;

public interface OrganizationService extends BaseService<Organization>{
	/**
	 * 取得某个节点下的所有子节点
	 * @param parentId
	 * @param demId
	 * @return
	 */
	public List<Organization> getByParent(Long parentId,Long demId);
	/**
	 * 按路径查找所有节点
	 * @param path
	 * @return
	 */
	public List<Organization> getByPath(String path);
	
	/**
	 * 取得某个节点下的所有公司节点
	 * @param parentId
	 * @param demId
	 * @return
	 */
	public List<Organization> getCompanyByParent(Long parentId, Long demId);
	
	/**
	 * 删除某个岗位及其下属组织
	 * @param posId
	 */
	public void delCascade(Long orgId);
}


