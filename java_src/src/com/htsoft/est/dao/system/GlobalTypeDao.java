package com.htsoft.est.dao.system;
/*
 *  广州宏天软件有限公司企业管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-20010 GuangZhou HongTian Software Limited company.
*/
import java.util.List;

import com.htsoft.core.dao.BaseDao;
import com.htsoft.est.model.system.AppUser;
import com.htsoft.est.model.system.GlobalType;

/**
 * 
 * @author 
 *
 */
public interface GlobalTypeDao extends BaseDao<GlobalType>{
	/**
	 * 取得某种分类下的子结点列表
	 * @param parentId
	 * @param catKey
	 * @return
	 */
	public List<GlobalType> getByParentIdCatKey(Long parentId,String catKey);
	
	/**
	 * 取得某种分类下某个用户的子结点列表
	 * @param parentId
	 * @param catKey
	 * @return
	 */
	List<GlobalType> getByParentIdCatKeyAndNodeKey(Long parentId, String catKey);
	
	/**
	 * 取得某种分类下某个用户的子结点列表
	 * @param parentId
	 * @param catKey
	 * @return
	 */
	public List<GlobalType> getByParentIdCatKeyUserId(Long parentId,String catKey,Long userId);
	/**
	 * 取得该分类下的数目
	 * @param parentId
	 * @return
	 */
	public Integer getCountsByParentId(Long parentId);
	
	/**
	 * 取得该分类下的所有子分类
	 * @param parentId
	 * @return
	 */
	public List<GlobalType> getByParentId(Long parentId);
	
	/**
	 * 
	 * @param path
	 * @return
	 */
	public List<GlobalType> getByPath(String path);

	public GlobalType findByTypeName(String typeName);
	/**
	 * 根据当前用户权限产生流程分类树
	 * @param curUser
	 * @param catKey
	 * @return
	 */
	public List<GlobalType> getByRightsCatKey(AppUser curUser, String catKey);
	
	/**
	 * 根据proTypeId删除，下面所有的子节点信息
	 * @param proTypeId
	 */
	void delChildrens(Long proTypeId);
}