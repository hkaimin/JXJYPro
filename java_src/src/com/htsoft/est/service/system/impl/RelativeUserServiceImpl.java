package com.htsoft.est.service.system.impl;

/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
 */
import java.util.List;
import java.util.Set;

import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.est.dao.system.RelativeUserDao;
import com.htsoft.est.model.system.AppUser;
import com.htsoft.est.model.system.RelativeUser;
import com.htsoft.est.service.system.RelativeUserService;

/**
 * @description 相对岗位人员管理
 * @author 宏天软件
 * @company www.jee-soft.con
 * @data 2010-12-13PM
 * 
 */
public class RelativeUserServiceImpl extends BaseServiceImpl<RelativeUser>
		implements RelativeUserService {

	private RelativeUserDao dao;

	public RelativeUserServiceImpl(RelativeUserDao dao) {
		super(dao);
		this.dao = dao;
	}

	/**
	 * 根据userId和jobUserId查询对应数据的总行数
	 */
	@Override
	public AppUser judge(Long userId, Long jobUserId) {
		return dao.judge(userId, jobUserId);
	}

	@Override
	public List<AppUser> findByUserIdReJobId(Long userId, Long reJobId) {
		return dao.findByUserIdReJobId(userId, reJobId);
	}

	@Override
	public List<RelativeUser> list(Long appUserId, Long reJobId, PagingBean pb) {
		return dao.list(appUserId, reJobId, pb);
	}
	
	/**
	 * 取得某个用户的相对岗位人员
	 * @param userId 用户ID
	 * @param reJobId  相对岗位ID
	 * @return
	 */
	public List<Long> getReJobUserIds(Long userId,Long reJobId){
		return dao.getReJobUserIds(userId, reJobId);
	}

	/**
	 * 取得某个用户的上级
	 */
	@Override
	public Set<AppUser> getUpUser(Long userId) {
		return dao.getUpUser(userId);
	}

}