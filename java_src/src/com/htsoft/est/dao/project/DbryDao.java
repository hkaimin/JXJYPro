package com.htsoft.est.dao.project;

import com.htsoft.core.dao.BaseDao;
import com.htsoft.est.model.jxjy.JxjyDbry;

public interface DbryDao extends BaseDao<JxjyDbry>{
	
	public JxjyDbry saveOrInsert(JxjyDbry dbry);

}
