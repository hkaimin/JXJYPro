package com.htsoft.test.flow;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.htsoft.test.BaseTestCase;
import com.htsoft.est.dao.flow.ProTypeDao;
import com.htsoft.est.model.flow.ProType;

public class ProTypeDaoTestCase extends BaseTestCase {
	@Resource
	private ProTypeDao proTypeDao;
	
	@Test
	@Rollback(false)
	public void add(){		
		ProType proType=new ProType();
//		TODO

		proTypeDao.save(proType);
	}
}