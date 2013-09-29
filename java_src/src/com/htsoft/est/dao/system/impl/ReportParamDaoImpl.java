package com.htsoft.est.dao.system.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   --  http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import java.util.List;

import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.est.dao.system.ReportParamDao;
import com.htsoft.est.model.system.ReportParam;

public class ReportParamDaoImpl extends BaseDaoImpl<ReportParam> implements ReportParamDao{

	public ReportParamDaoImpl() {
		super(ReportParam.class);
	}

	@Override
	public List<ReportParam> findByRepTemp(Long reportId) {
		String hql="from ReportParam vo where vo.reportTemplate.reportId=? order by vo.sn asc";
		Object []objs={reportId};
		return findByHql(hql, objs);
	}

}