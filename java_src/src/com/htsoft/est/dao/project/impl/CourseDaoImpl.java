package com.htsoft.est.dao.project.impl;

import java.util.List;

import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.est.dao.project.CourseDao;
import com.htsoft.est.model.jxjy.JxjyKtgl;

import flex.messaging.io.ArrayList;

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

	@Override
	public List<JxjyKtgl> search(QueryFilter filter, JxjyKtgl kt) {
		// TODO Auto-generated method stub
		StringBuffer hql= new StringBuffer("from JxjyKtgl vo where vo.xmId = ?");
		List<Object> params = new ArrayList();
		params.add(new Long(filter.getRequest().getParameter("orgId")));
		
		if(kt.getKtmc() != null && !kt.getKtmc().isEmpty()) {
			hql.append(" and vo.ktmc like ?");
			params.add("%" + kt.getKtmc() + "%");
		}
		if(kt.getJsmc() != null && !kt.getJsmc().isEmpty()) {
			hql.append(" and vo.jsmc like ?");
			params.add("%" + kt.getJsmc() + "%");
		}
		if(kt.getJszc() != null && !kt.getJszc().isEmpty()) {
			hql.append(" and vo.jszc like ?");
			params.add("%" + kt.getJszc() + "%");
		}
		if(kt.getSkdd() != null && !kt.getSkdd().isEmpty()) {
			hql.append(" and vo.skdd like ?");
			params.add("%" + kt.getSkdd() + "%");
		}
		if(kt.getSksjStart() != null && !kt.getSksjStart().isEmpty()) {
			hql.append(" and vo.sksj >= ?");
			params.add(kt.getSksjStart());
		}
		if(kt.getSksjEnd() != null && !kt.getSksjEnd().isEmpty()) {
			hql.append(" and vo.sksj <= ?");
			params.add(kt.getSksjEnd());
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
