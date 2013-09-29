package com.htsoft.est.service.system;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import java.util.List;

import com.htsoft.core.service.BaseService;
import com.htsoft.est.model.system.Company;

public interface CompanyService extends BaseService<Company> {

	public List<Company> findByHql(final String hql);
	public List<Company> findCompany();
}
