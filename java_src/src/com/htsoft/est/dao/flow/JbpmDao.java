package com.htsoft.est.dao.flow;

import java.util.Date;

public interface JbpmDao {
	/**
	 * 取得流程定义的XML
	 * @param deployId
	 * @return
	 */
	public String getDefXmlByDeployId(String deployId);
	
	/**
	 * 把修改过的xml更新至回流程定义中
	 * @param deployId
	 * @param defXml
	 */
	public void wirteDefXml(String deployId,String defXml);
	
}
