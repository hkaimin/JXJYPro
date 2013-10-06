package com.htsoft.est.dao.project.impl;

import java.util.List;

import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.est.dao.project.DbryDao;
import com.htsoft.est.model.jxjy.JxjyDbry;

public class DbryDaoImpl extends BaseDaoImpl<JxjyDbry> implements DbryDao{

	public DbryDaoImpl() {
		super(JxjyDbry.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public JxjyDbry saveOrInsert(JxjyDbry dbry) {
		// TODO Auto-generated method stub
		String hql = "select ry from JxjyDbry ry where ry.rybh = ? and ry.nf = ?";
		List<JxjyDbry> list = this.findByHql(hql, new Object[] { dbry.getRybh(), dbry.getNf()} );
		//存在则修改
		if(list.size() > 0) {
			JxjyDbry ry = list.get(0);
			ry.setDb(dbry.getDb());
			return this.save(ry);
		} else {
			return this.save(dbry);
		}
	}

}
