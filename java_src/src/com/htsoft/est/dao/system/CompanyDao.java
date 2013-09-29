package com.htsoft.est.dao.system;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   --  http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import java.util.List;
import com.htsoft.core.dao.BaseDao;
import com.htsoft.est.model.system.Company;

/**
 * @author Student LHH
 *
 */
public interface CompanyDao extends BaseDao<Company> {
	
	public List<Company> findByHql(final String hql);
	public List<Company> findCompany();
	
}
