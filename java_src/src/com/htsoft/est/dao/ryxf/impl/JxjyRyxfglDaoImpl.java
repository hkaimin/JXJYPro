package com.htsoft.est.dao.ryxf.impl;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import java.util.List;

import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.est.dao.ryxf.JxjyRyxfglDao;
import com.htsoft.est.model.jxjy.JxjyRyxfgl;
import com.htsoft.est.util.JxjyConstant;


@SuppressWarnings("unchecked")
public class JxjyRyxfglDaoImpl extends BaseDaoImpl<JxjyRyxfgl> implements JxjyRyxfglDao{

	public JxjyRyxfglDaoImpl() {
		super(JxjyRyxfgl.class);
	}

	@Override
	public List<JxjyRyxfgl> getRyxfBM(QueryFilter filter) {
		
		StringBuffer hql =new StringBuffer(" select ry from JxjyRyxfgl ry where ry.shzt='0'  ") ;
		Object[] params = new Object[]{};
		String rq = filter.getRequest().getParameter("rq");
		String kt = filter.getRequest().getParameter("kt");
		String xf = filter.getRequest().getParameter("xf");
		String xs = filter.getRequest().getParameter("xs");
		
		if(rq!=null && !rq.isEmpty() ){
			hql.append(" and ry.rq= '" + rq + "'");
		}else if(kt!=null && !kt.isEmpty() ){
			hql.append(" and ry.kt like  '%" + kt + "%'");
		}else if(xf!=null && !xf.isEmpty() ){
			hql.append(" and ry.xf= '" + xf + "'");
		}else if(xs!=null && !xs.isEmpty() ){
			hql.append(" and ry.xs= '" + xs + "'");
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