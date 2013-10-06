package com.htsoft.est.dao.project;

import java.util.List;

import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.dao.BaseDao;
import com.htsoft.est.model.jxjy.JxjyDbry;

public interface DbryDao extends BaseDao<JxjyDbry>{
	
	public JxjyDbry saveOrInsert(JxjyDbry dbry);
	
	public List<JxjyDbry> getAllList(QueryFilter filter, JxjyDbry ry);

}
