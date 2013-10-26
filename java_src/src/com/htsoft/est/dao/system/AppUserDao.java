package com.htsoft.est.dao.system;

/*
 *  广州宏天软件有限公司 EST-BPM管理平台   --  http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
 */
import java.util.List;
import java.util.Set;

import com.htsoft.core.dao.BaseDao;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.est.model.system.AppUser;
import com.htsoft.est.model.system.Department;
import com.htsoft.est.model.system.UserOrg;

/**
 * @description 用户操作
 * @class AppUserDao
 * @author 宏天软件
 * @updater YHZ
 * @company www.jee-soft.cn
 * @data 2010-12-27AM
 */
public interface AppUserDao extends BaseDao<AppUser> {
	public AppUser findByUserName(String username);

	public List<AppUser> findByDepartment(String path, PagingBean pb);
	
	/**
	 * @description 根据userIds查询不对应的数据
	 * @param path
	 *            路径
	 * @param userIds
	 *            userId组成的字符串
	 * @param pb
	 *            PagingBean
	 * @return List<AppUser>
	 */
	List<AppUser> findByDepartment(String path, String userIds, PagingBean pb);

	public List<AppUser> findByDepartment(String path);

	public List<AppUser> findByDepartment(Department department);

	public List<AppUser> findByRole(Long roleId);
	
	public List<AppUser> findByRoleName(String roleName);

	public List<AppUser> findByRole(Long roleId, PagingBean pb);

	public List<AppUser> findByRoleId(Long roleId);

	/**
	 * 根据部门查找不是上属的用户
	 */
	public List<AppUser> findSubAppUser(String path, Set<Long> userIds,
			PagingBean pb);

	/**
	 * 根据角色查找不是上属的用户
	 */
	public List<AppUser> findSubAppUserByRole(Long roleId, Set<Long> userIds,
			PagingBean pb);

	/**
	 * 查找某个部门下的所有用户
	 * 
	 * @param depId
	 * @return
	 */
	public List<AppUser> findByDepId(Long depId);

	/**
	 * 查找某组角色列表下所有的用户
	 * 
	 * @param roleIds
	 * @return
	 */
	public List<AppUser> findUsersByRoleIds(String roleIds);

	/**
	 * @description 根据用户userId查询对应用户的上下级信息
	 * @author YHZ
	 * @data 2010-12-23PM
	 * @param userId
	 *            用户id
	 * @return List<AppUser>
	 */
	public List<AppUser> findRelativeUsersByUserId(String sameLevel);
	
	/**
	 * 按角色取得用户列表
	 * @param roleId
	 * @return
	 */
	public List<AppUser> getUsersByRoleId(Long roleId);
	
	/**
	 * 按部门取得用户列表
	 * @param orgPath
	 * @return
	 */
	public List<AppUser> getDepUsers(String orgPath,PagingBean pb);
	
	/**
	 * 取得想对岗位用户列表
	 * @param reJobId
	 * @return
	 */
	public List<AppUser> getReLevelUser(String reJobId);
	
	/**
	 * 取得组织主要负责人
	 * @param userOrg
	 * @return
	 */
	public List<AppUser> getChargeOrgUsers(Set userOrgs);
	
	/**
	 * 获取人员，根据内部编号，内部编号为fx
	 * @param fx
	 * @return
	 */
	public AppUser getByFx(String fx);
	public void yjActivity();
}
