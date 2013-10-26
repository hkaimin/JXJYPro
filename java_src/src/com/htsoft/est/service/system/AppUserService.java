package com.htsoft.est.service.system;

/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
 */
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.htsoft.core.service.BaseService;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.est.model.system.AppUser;
import com.htsoft.est.model.system.UserOrg;

public interface AppUserService extends BaseService<AppUser> {

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

	public List<AppUser> findByRole(Long roleId, PagingBean pb);

	public List<AppUser> findByRoleId(Long roleId);
	
	/**
	 * 匹配角色名字找到相应的人员
	 * @param roleName
	 * @return
	 */
	public List<AppUser> findByRoleName(String roleName);

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

	public List<AppUser> findByDepId(Long depId);

	/**
	 * 动态密码接口服务方法
	 * 
	 * @param input
	 *            参数数组
	 * @param function
	 *            接口名称
	 * @return ok=成功 fail=失败
	 */
	public String initDynamicPwd(HashMap<String, String> input, String function);

	/**
	 * 按角色ID查找用户
	 * 
	 * @param roleIds
	 *            角色Id，通过','分割
	 * @return
	 */
	public List<AppUser> findUsersByRoleIds(String roleIds);

	/**
	 * @description 根据用户userId查询对应用户的上下级信息
	 * @author YHZ
	 * @data 2010-12-23PM
	 * @param userId
	 *            用户id
	 * @param level
	 *            0.下级,1.上级,2.同级
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
	 * 返回当前用户的信息，以Json格式返回
	 * @return
	 */
	public String getCurUserInfo();
	
	/**
	 * 按部门取得用户列表
	 * @param orgPath
	 * @return
	 */
	public List<AppUser> getDepUsers(String orgPath,PagingBean pb);
	
	/**
	 * 取得相对岗位用户列表
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
	
	public void yjActivity();
}
