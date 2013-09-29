package com.htsoft.est.dao.system;

import java.util.List;

import com.htsoft.core.dao.BaseDao;
import com.htsoft.est.model.system.Dictionary;

/**
 * 
 * @author 
 *
 */
public interface DictionaryDao extends BaseDao<Dictionary>{

	public List<String> getAllItems();

	public List<String> getAllByItemName(String itemName);
	
	public List<Dictionary> getByItemName(final String itemName);
	
}