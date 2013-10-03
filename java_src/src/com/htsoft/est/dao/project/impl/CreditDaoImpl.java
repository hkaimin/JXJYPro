package com.htsoft.est.dao.project.impl;

import java.util.List;

import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.est.dao.project.CreditDao;
import com.htsoft.est.model.jxjy.JxjyXflb;

public class CreditDaoImpl extends BaseDaoImpl<JxjyXflb> implements CreditDao{

	public CreditDaoImpl() {
		super(JxjyXflb.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<JxjyXflb> getLb(Long xfbz) {
		// TODO Auto-generated method stub
		String hql = "select lb from JxjyXflb lb where lb.xfbz = ?";
		Object[] params = new Object[]{xfbz};
		return this.findByHql(hql, params);
	}

}
