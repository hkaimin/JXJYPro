package com.htsoft.est.dao.project.impl;

import java.util.List;

import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.est.dao.project.ZcDao;
import com.htsoft.est.model.jxjy.JxjyZc;

public class ZcDaoImpl extends BaseDaoImpl<JxjyZc> implements ZcDao{

	public ZcDaoImpl() {
		super(JxjyZc.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<JxjyZc> getZcByParentId(Long pId) {
		// TODO Auto-generated method stub
		String hql = "select zc from JxjyZc zc where zc.fqZcId = ?";
		Object[] params = new Object[] { pId };
		return this.findByHql(hql, params);
	}

}
