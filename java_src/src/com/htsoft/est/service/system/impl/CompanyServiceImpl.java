package com.htsoft.est.service.system.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import java.util.List;

import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.est.dao.system.CompanyDao;
import com.htsoft.est.model.system.Company;
import com.htsoft.est.service.system.CompanyService;


public  class CompanyServiceImpl extends BaseServiceImpl<Company> implements
		CompanyService {
	
	private CompanyDao companyDao;
	
	public CompanyServiceImpl(CompanyDao companyDao) {
		super(companyDao);
		this.companyDao=companyDao;
	}

	@Override
	public List<Company> findCompany() {
		
		return companyDao.findCompany();
	}

	@Override
	public List<Company> findByHql(String hql) {
		// TODO Auto-generated method stub
		return null;
	}


}
