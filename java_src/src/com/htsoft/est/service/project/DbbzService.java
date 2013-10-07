package com.htsoft.est.service.project;

import java.util.List;

import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.service.BaseService;
import com.htsoft.est.model.jxjy.JxjyDbbz;

public interface DbbzService extends BaseService<JxjyDbbz>{
	
	/**
	 * 保存达标标准
	 * @param bz
	 * @return
	 */
	public JxjyDbbz saveBiaozhun(JxjyDbbz bz);
	
	/**
	 * 获取列表
	 * @param filter
	 * @return
	 */
	public List<JxjyDbbz> list(QueryFilter filter);
	
	public void multiDel(String[] ids);
	
	/**
	 * 个人审核
	 * @param userNO
	 * @param yearNo
	 */
	public void checkPerson(String userNO, String yearNo, String flage);
	
	/**
	 * 单位审核
	 * @param orgId
	 * @param yearNo
	 */
	public void checkOrg(String orgId, String zc, String yearNo, String flage);

}
