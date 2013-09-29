package com.htsoft.test.flow;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.htsoft.test.BaseTestCase;
import com.htsoft.est.dao.flow.RunDataDao;
import com.htsoft.est.model.flow.RunData;

public class RunDataDaoTestCase extends BaseTestCase {
	@Resource
	private RunDataDao formDataDao;
	
	@Test
	@Rollback(false)
	public void add(){		
		RunData formData=new RunData();
//		TODO

		formDataDao.save(formData);
	}
}