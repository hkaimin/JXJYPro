package com.htsoft.est.dao.project;

import java.util.List;

import com.htsoft.core.dao.BaseDao;
import com.htsoft.est.model.jxjy.JxjyXflb;

public interface CreditDao extends BaseDao<JxjyXflb>{

	
	/**
	 * 根据类别获取学分类别
	 * @param xfbz
	 * @return
	 */
	public List<JxjyXflb> getLb(Long xfbz);
	
}
