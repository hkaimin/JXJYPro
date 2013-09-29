package com.htsoft.est.service.system;
import java.util.List;

import com.htsoft.core.service.BaseService;
import com.htsoft.est.model.system.Dictionary;

public interface DictionaryService extends BaseService<Dictionary>{

	public List<String> getAllItems();

	public List<String> getAllByItemName(String itemName);
	
	public List<Dictionary> getByItemName(final String itemName);
	
}


