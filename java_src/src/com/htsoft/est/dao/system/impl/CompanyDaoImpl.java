package com.htsoft.est.dao.system.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   --  http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import java.util.List;

import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.est.dao.system.CompanyDao;
import com.htsoft.est.model.system.Company;

public class CompanyDaoImpl extends BaseDaoImpl<Company> implements CompanyDao{

	public CompanyDaoImpl() {
		super(Company.class);
	}

	public List<Company> findCompany(){
		String hql = "from Company c";
		return findByHql(hql);
		
	}
	


	
}
