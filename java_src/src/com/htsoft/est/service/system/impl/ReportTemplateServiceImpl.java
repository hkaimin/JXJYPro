package com.htsoft.est.service.system.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.est.dao.system.ReportTemplateDao;
import com.htsoft.est.model.system.ReportTemplate;
import com.htsoft.est.service.system.ReportTemplateService;

public class ReportTemplateServiceImpl extends BaseServiceImpl<ReportTemplate> implements ReportTemplateService{
	private ReportTemplateDao dao;
	
	public ReportTemplateServiceImpl(ReportTemplateDao dao) {
		super(dao);
		this.dao=dao;
	}

}