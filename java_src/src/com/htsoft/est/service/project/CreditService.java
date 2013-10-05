package com.htsoft.est.service.project;

import java.util.List;

import com.htsoft.core.service.BaseService;
import com.htsoft.est.model.jxjy.JxjyXflb;

public interface CreditService extends BaseService<JxjyXflb>{

	public JxjyXflb saveLb(JxjyXflb lb);
	
	/**
	 * 加载所有的学分类别出来
	 * @return
	 */
	public List<JxjyXflb> loadAll();
	
	/**
	 * 批量修改项目类别审核方式
	 * @param ids
	 * @param flage
	 */
	public void batchModified(String[] ids, String flage);
	
	public void multiDel(String[] ids) ;
}
