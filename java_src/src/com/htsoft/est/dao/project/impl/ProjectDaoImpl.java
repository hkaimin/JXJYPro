package com.htsoft.est.dao.project.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.est.dao.project.CreditDao;
import com.htsoft.est.dao.project.ProjectDao;
import com.htsoft.est.model.jxjy.JxjyXflb;
import com.htsoft.est.model.jxjy.JxjyXmgl;
import com.htsoft.est.util.JxjyConstant;

import flex.messaging.io.ArrayList;

public class ProjectDaoImpl extends BaseDaoImpl<JxjyXmgl> implements ProjectDao{

	@Resource
	private CreditDao creditDao;
	
	public ProjectDaoImpl() {
		super(JxjyXmgl.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<JxjyXmgl> listProject(QueryFilter filter) {
		// TODO Auto-generated method stub
		StringBuffer hql= new StringBuffer("from JxjyXmgl vo where vo.zbbwid = ?");
		Object[] params = new Object[]{new Long(filter.getRequest().getParameter("orgId"))};
		
		String rights = filter.getRequest().getParameter("rights");
		
		if(rights != null && !rights.isEmpty() && rights.equals("rsj")) { //查看全部
			
		} else if(rights != null && !rights.isEmpty() && rights.equals("rsj")) { //人社局
			hql.append(" and vo.yysh = " + JxjyConstant.PROJECT_YYSH_TONG_GUO);
			
		} else if(rights != null && !rights.isEmpty() && rights.equals("yy")) { //医院用户
			hql.append(" and vo.sfysb= '" + JxjyConstant.YI_SHANG_BAO + "'");
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
		StringBuffer hql = new StringBuffer("select xm from JxjyXmgl xm where xm.zbbwid = ? and xm.zt = ?");
		Object[] params = new Object[]{ orgId , JxjyConstant.PROJECT_ZT_TONG_GUO};
		logger.debug("hql:" + hql);
		List<JxjyXmgl> list = this.findByHql(hql.toString(), params);
		if(list.size() > 0) {
			return list;
		} else {
			return Collections.EMPTY_LIST;
		}
	}

	@Override
	public List<JxjyXmgl> search(QueryFilter filter, JxjyXmgl xm) {
		// TODO Auto-generated method stub
		StringBuffer hql= new StringBuffer("from JxjyXmgl vo where vo.zbbwid = ?");
		List<Object> params = new ArrayList();
		params.add(new Long(filter.getRequest().getParameter("orgId")));
		
		if(xm.getXmbh() != null && !xm.getXmbh().isEmpty()) {
			hql.append(" and vo.xmbh like ?");
			params.add("%" + xm.getXmbh() + "%");
		}
		if(xm.getJbsj_start() != null && !xm.getJbsj_start().isEmpty()) {
			hql.append(" and vo.jbsj >= ?");
			params.add(xm.getJbsj_start());
		}
		if(xm.getJbsj_end() != null && !xm.getJbsj_end().isEmpty()) {
			hql.append(" and vo.jbsj <= ?");
			params.add(xm.getJbsj_end());
		}
		if(xm.getMc() != null && !xm.getMc().isEmpty()) {
			hql.append(" and vo.mc = ?");
			params.add(xm.getMc());
		}
		if(xm.getXmlb() != null && !"".equals(xm.getXmlb())) {
			hql.append(" and vo.xmlb = ?");
			params.add(xm.getXmlb());
		}
		if(xm.getXflb() != null && !"".equals(xm.getXflb())) {
			hql.append(" and vo.xflb like ?");
			params.add("%" + xm.getXflb() + "%");
		}
		
		String rights = filter.getRequest().getParameter("rights");
		
		if(rights != null && !rights.isEmpty() && rights.equals("rsj")) { //查看全部
			
		} else if(rights != null && !rights.isEmpty() && rights.equals("rsj")) { //人社局
			hql.append(" and vo.yysh = " + JxjyConstant.PROJECT_YYSH_TONG_GUO);
			
		} else if(rights != null && !rights.isEmpty() && rights.equals("yy")) { //医院用户
			hql.append(" and vo.sfysb= '" + JxjyConstant.YI_SHANG_BAO + "'");
		}
		
//		if(xm.getZt() != null && !"".equals(xm.getZt())) {
//			if(!xm.getZt().equals("all")) {
//				hql.append(" and vo.zt = ?");
//				params.add(xm.getZt());
//			}
//		}
		
		
		//获取需要排序字段名称
		String sort = (filter.getRequest().getParameter("sort") == null) ? ""
				: filter.getRequest().getParameter("sort");
		String dir = (filter.getRequest().getParameter("dir") == null) ? ""
				: filter.getRequest().getParameter("dir");
		
		if(sort != null && !sort.equals("") && dir != null && !dir.equals("")) {
			hql.append(" order by vo." + sort + " " + dir);
		}
		
		if(logger.isDebugEnabled()){
			logger.debug("hql:" + hql);
		}
		//显示多少条记录
		int totalItems=getTotalItems(hql.toString(),params.toArray()).intValue();
		filter.getPagingBean().setTotalItems(totalItems);
		
		return find(hql.toString(), params.toArray(),filter.getPagingBean().getFirstResult(),filter.getPagingBean().getPageSize());
	}


}
