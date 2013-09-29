package com.htsoft.est.dao.system.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   --  http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.est.dao.system.ReportTemplateDao;
import com.htsoft.est.model.system.ReportTemplate;

public class ReportTemplateDaoImpl extends BaseDaoImpl<ReportTemplate> implements ReportTemplateDao{

	public ReportTemplateDaoImpl() {
		super(ReportTemplate.class);
	}

}