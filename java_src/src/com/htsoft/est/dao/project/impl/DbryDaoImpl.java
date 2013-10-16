package com.htsoft.est.dao.project.impl;

import java.util.List;

import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.est.dao.project.DbryDao;
import com.htsoft.est.model.jxjy.JxjyDbry;

import flex.messaging.io.ArrayList;

public class DbryDaoImpl extends BaseDaoImpl<JxjyDbry> implements DbryDao{

	public DbryDaoImpl() {
		super(JxjyDbry.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public JxjyDbry saveOrInsert(JxjyDbry dbry) {
		// TODO Auto-generated method stub
		String hql = "select ry from JxjyDbry ry where ry.rybh = ? and ry.nf = ? and ry.type = ?";
		List<JxjyDbry> list = this.findByHql(hql, new Object[] { dbry.getRybh(), dbry.getNf(), dbry.getType()} );
		//存在则修改d
		if(list.size() > 0) {
			JxjyDbry ry = list.get(0);
			ry.setDb(dbry.getDb());
			return this.save(ry);
		} else {
			return this.save(dbry);
		}
	}

	@Override
	public List<JxjyDbry> getAllList(QueryFilter filter, JxjyDbry ry) {
		// TODO Auto-generated method stub
		StringBuffer hql= new StringBuffer("from JxjyDbry vo where 1=1");
		List<Object> params = new ArrayList();
		
		if(ry.getXm() != null && !ry.getXm().isEmpty()) {
			hql.append(" and vo.xm like ?");
			params.add("%" + ry.getXm() + "%");
		}
		if(ry.getRybh() != null) {
			hql.append(" and vo.rybh = ?");
			params.add(ry.getRybh());
		}
		
		//获取需要排序字段名称
		String sort = (filter.getRequest().getParameter("sort") == null) ? ""
				: filter.getRequest().getParameter("sort");
		String dir = (filter.getRequest().getParameter("dir") == null) ? ""
				: filter.getRequest().getParameter("dir");
		
		if(sort != null && !sort.equals("") && dir != null && !dir.equals("")) {
			hql.append(" order by vo." + sort + " " + dir);
		}
		
		//显示多少条记录
		int totalItems=getTotalItems(hql.toString(),params.toArray()).intValue();
		filter.getPagingBean().setTotalItems(totalItems);
		if(logger.isDebugEnabled()){
			logger.debug("hql:" + hql);
		}
		return find(hql.toString(), params.toArray(),filter.getPagingBean().getFirstResult(),filter.getPagingBean().getPageSize());
	}

}
