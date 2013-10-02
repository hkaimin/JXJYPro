package com.htsoft.est.dao.project.impl;

import java.util.Collections;
import java.util.List;

import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.est.dao.project.ProjectDao;
import com.htsoft.est.model.jxjy.JxjyXmgl;

public class ProjectDaoImpl extends BaseDaoImpl<JxjyXmgl> implements ProjectDao{

	public ProjectDaoImpl() {
		super(JxjyXmgl.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<JxjyXmgl> listProject(QueryFilter filter) {
		// TODO Auto-generated method stub
		StringBuffer hql= new StringBuffer("from JxjyXmgl vo where vo.zbbwid = ?");
		Object[] params = new Object[]{new Long(filter.getRequest().getParameter("orgId"))};
		
		//获取需要排序字段名称
		String sort = (filter.getRequest().getParameter("sort") == null) ? ""
				: filter.getRequest().getParameter("sort");
		String dir = (filter.getRequest().getParameter("dir") == null) ? ""
				: filter.getRequest().getParameter("dir");
		
		if(sort != null && !sort.equals("") && dir != null && !dir.equals("")) {
			hql.append(" order by vo." + sort + " " + dir);
		}
		
		//显示多少条记录
		int totalItems=getTotalItems(hql.toString(),params).intValue();
		filter.getPagingBean().setTotalItems(totalItems);
		if(logger.isDebugEnabled()){
			logger.debug("hql:" + hql);
		}
		return find(hql.toString(), params,filter.getPagingBean().getFirstResult(),filter.getPagingBean().getPageSize());
	}

	@Override
	public List<JxjyXmgl> getProjectByOrg(Long orgId) {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer("select xm from JxjyXmgl xm where xm.zbbwid = ?");
		Object[] params = new Object[]{ orgId };
		logger.debug("hql:" + hql);
		List<JxjyXmgl> list = this.findByHql(hql.toString(), params);
		if(list.size() > 0) {
			return list;
		} else {
			return Collections.EMPTY_LIST;
		}
	}


}
