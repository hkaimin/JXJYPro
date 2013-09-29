package com.htsoft.est.service.system;
/*
 *  广州宏天软件有限公司
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Limited Company.
*/
import java.util.List;

import com.htsoft.core.service.BaseService;
import com.htsoft.est.model.system.UserOrg;

public interface UserOrgService extends BaseService<UserOrg>{
	/**
	 * 取某一用户所有的部门
	 * @param userId
	 * @return
	 */
	public List<UserOrg> getDepOrgsByUserId(Long userId);
	
	/**
	 * 删除UserOrg对象
	 * @param userId
	 * @param orgId
	 * @return
	 */
	public void delUserOrg(Long orgId);
	/**
	 * 
	 * @param userId
	 * @param orgId
	 * @return
	 */
	public List<UserOrg> getByUserIdOrgId(Long userId,Long orgId);
}


