package com.htsoft.test.system;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.htsoft.test.BaseTestCase;
import com.htsoft.est.dao.system.DictionaryDao;
import com.htsoft.est.model.system.Dictionary;

public class DictionaryDaoTestCase extends BaseTestCase {
	@Resource
	private DictionaryDao dictionaryDao;
	
	@Test
	@Rollback(false)
	public void add(){		
		Dictionary dictionary=new Dictionary();
//		TODO

		dictionaryDao.save(dictionary);
	}
}