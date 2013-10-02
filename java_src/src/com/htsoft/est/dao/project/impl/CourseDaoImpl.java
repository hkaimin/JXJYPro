package com.htsoft.est.dao.project.impl;

import java.util.List;

import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.est.dao.project.CourseDao;
import com.htsoft.est.model.jxjy.JxjyKtgl;

public class CourseDaoImpl extends BaseDaoImpl<JxjyKtgl> implements CourseDao{

	public CourseDaoImpl() {
		super(JxjyKtgl.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<JxjyKtgl> listCourse(QueryFilter filter) {
		// TODO Auto-generated method stub
		StringBuffer hql= new StringBuffer("from JxjyKtgl vo where vo.xmId = ?");
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

}
