package com.htsoft.test.flow;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.htsoft.test.BaseTestCase;
import com.htsoft.est.dao.flow.ProcessRunDao;
import com.htsoft.est.model.flow.ProcessRun;

public class ProcessRunDaoTestCase extends BaseTestCase {
	@Resource
	private ProcessRunDao processRunDao;
	
	@Test
	@Rollback(false)
	public void add(){		
		ProcessRun processRun=new ProcessRun();
//		TODO

		processRunDao.save(processRun);
	}
}