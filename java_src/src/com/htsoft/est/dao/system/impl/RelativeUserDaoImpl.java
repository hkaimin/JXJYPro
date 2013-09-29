package com.htsoft.est.dao.system.impl;

/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
 */
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.est.dao.system.RelativeUserDao;
import com.htsoft.est.model.system.AppUser;
import com.htsoft.est.model.system.RelativeUser;

/**
 * @description 相对岗位人员管理
 * @author YHZ
 * @company www.jee-soft.cn
 * @data 2010-12-13AM
 */
@SuppressWarnings("unchecked")
public class RelativeUserDaoImpl extends BaseDaoImpl<RelativeUser> implements
		RelativeUserDao {

	public RelativeUserDaoImpl() {
		super(RelativeUser.class);
	}

	/**
	 * 根据userId和jobUserId查询对应数据的总条数，返回:对应的总数据条数
	 */
	@Override
	public AppUser judge(Long userId, Long jobUserId) {
		String hql = "select r from RelativeUser r where r.appUser.userId = ? and r.jobUser.userId = ? ";
		Object[] paramList = { userId, jobUserId };
		List<RelativeUser> list = findByHql(hql, paramList);
		logger.debug(hql);
		return (list != null && list.size() > 0) ? list.get(0).getJobUser()
				: null;
	}

	/**
	 * 返回某个用户相对岗位的所有用户（如他主管的所有人）
	 * 
	 * @param userId
	 * @param reJobId
	 * @return
	 */
	public List<AppUser> findByUserIdReJobId(Long userId, Long reJobId) {
		String hql = "select ru.jobUser from RelativeUser ru where ru.appUser.userId=? and ru.relativeJob.reJobId=? ";
		Object result = findByHql(hql, new Object[] { userId, reJobId });
		return (List<AppUser>) result;
	}

	@Override
	public List<RelativeUser> list(Long appUserId, Long reJobId, PagingBean pb) {
		ArrayList<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer(
				"select r from RelativeUser r where 1=1 ");
		if (appUserId != null && appUserId > 0) {
			hql.append("and r.appUser.userId = ? "); // set of this userId
			paramList.add(appUserId);
		}
		if (reJobId != null && reJobId > 0) { // set of this reJobId
			hql.append("and r.relativeJob.reJobId = ? ");
			paramList.add(reJobId);
		}
		logger.debug("自定义：RelativeUserDaoImpl:" + hql.toString());
		return (ArrayList<RelativeUser>) findByHql(hql.toString(), paramList
				.toArray(), pb);
	}

	/**
	 * 取得某个用户的相对岗位人员
	 * 
	 * @param userId
	 *            用户ID
	 * @param reJobId
	 *            相对岗位ID
	 * @return
	 */
	public List<Long> getReJobUserIds(Long userId, Long reJobId) {
		String hql = "select jobUser.userId from RelativeUser ru where ru.appUser.userId=? and relativeJob.reJobId=? ";
		List list = findByHql(hql, new Object[] { userId, reJobId });
		return list;
	}

	/**
	 * 取得某个用户的上级
	 */
	public Set<AppUser> getUpUser(Long userId){
		//分两种情况 
		//1:自己添加上级 
		String hql1 = "select  ru.jobUser from RelativeUser ru where ru.appUser.userId =? and ru.isSuper =?";
		List list1 = findByHql(hql1, new Object[]{userId,RelativeUser.SUPER_FLAG_TRUE});
		
		//2:别人把自己加为下级
		String hql2 = "select  ru.appUser from RelativeUser ru where  ru.jobUser.userId = ? and ru.isSuper = ?";
		List list2 = findByHql(hql2, new Object[]{userId,RelativeUser.SUPER_FLAG_FALSE});
		
		Set<AppUser> result = new HashSet<AppUser>();
		result.addAll(list1);
		result.addAll(list2);
		
		return result;
	}
}