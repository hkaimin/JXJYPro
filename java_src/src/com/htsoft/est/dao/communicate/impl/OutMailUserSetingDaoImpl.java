package com.htsoft.est.dao.communicate.impl;

/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Limited company.
 */

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.est.dao.communicate.OutMailUserSetingDao;
import com.htsoft.est.model.communicate.OutMailUserSeting;

/**
 * @description 外部邮箱设置管理
 * @class OutMailUserSetingDaoImpl
 * @extend BaseDaoImpl
 */
@SuppressWarnings("unchecked")
public class OutMailUserSetingDaoImpl extends BaseDaoImpl<OutMailUserSeting>
		implements OutMailUserSetingDao {

	public OutMailUserSetingDaoImpl() {
		super(OutMailUserSeting.class);
	}

	/*
	 * 根据当前登陆人，取得外部邮箱设置
	 */
	@Override
	public OutMailUserSeting getByLoginId(Long loginid) {
		String hql = "select a from OutMailUserSeting a where a.appUser.userId ="
				+ loginid;
		List<OutMailUserSeting> loginList = getHibernateTemplate().find(hql);
		return (loginList != null && loginList.size() > 0) ? (OutMailUserSeting) loginList
				.get(0)
				: null;
	}

	@Override
	public List findByUserAll() {
		String hql = "select au,vo from OutMailUserSeting au right join au.appUser vo where vo.delFlag = 0";
		return findByHql(hql);
	}

	@Override
	public List<OutMailUserSeting> findByUserAll(String userName,PagingBean pb) {
		List params=new ArrayList();
		String hql = "select au from OutMailUserSeting au right join au.appUser vo where vo.delFlag = 0";
		if(StringUtils.isNotEmpty(userName)){
			hql +="and vo.fullname like ?";
			params.add("%"+userName+"%");
		}
		return findByHql(hql,params.toArray(),pb);
	}
	
}