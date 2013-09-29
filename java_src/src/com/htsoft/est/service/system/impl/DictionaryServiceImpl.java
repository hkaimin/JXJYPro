package com.htsoft.est.service.system.impl;

import java.util.List;

import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.est.dao.system.DictionaryDao;
import com.htsoft.est.model.system.Dictionary;
import com.htsoft.est.service.system.DictionaryService;

public class DictionaryServiceImpl extends BaseServiceImpl<Dictionary> implements DictionaryService{
	private DictionaryDao dao;
	
	public DictionaryServiceImpl(DictionaryDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<String> getAllItems() {
		return dao.getAllItems();
	}

	@Override
	public List<String> getAllByItemName(String itemName) {
		return dao.getAllByItemName(itemName);
	}
	
	@Override
	public List<Dictionary> getByItemName(String itemName) {
		return dao.getByItemName(itemName);
	}
}