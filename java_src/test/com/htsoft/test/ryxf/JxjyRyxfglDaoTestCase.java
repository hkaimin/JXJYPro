package com.htsoft.test.ryxf;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.htsoft.test.BaseTestCase;
import com.htsoft.est.dao.ryxf.JxjyRyxfglDao;
import com.htsoft.est.model.ryxf.JxjyRyxfgl;

public class JxjyRyxfglDaoTestCase extends BaseTestCase {
	@Resource
	private JxjyRyxfglDao jxjyRyxfglDao;
	
	@Test
	@Rollback(false)
	public void add(){		
		JxjyRyxfgl jxjyRyxfgl=new JxjyRyxfgl();
//		TODO

		jxjyRyxfglDao.save(jxjyRyxfgl);
	}
}