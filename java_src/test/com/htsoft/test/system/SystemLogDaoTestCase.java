package com.htsoft.test.system;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Limited company.
*/
import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.htsoft.test.BaseTestCase;
import com.htsoft.est.dao.system.SystemLogDao;
import com.htsoft.est.model.system.SystemLog;

public class SystemLogDaoTestCase extends BaseTestCase {
	@Resource
	private SystemLogDao systemLogDao;
	
	@Test
	@Rollback(false)
	public void add(){		
		SystemLog systemLog=new SystemLog();
//		TODO

		systemLogDao.save(systemLog);
	}
}