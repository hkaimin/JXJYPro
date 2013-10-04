package com.htsoft.est.service.project;

import java.util.List;

import com.htsoft.core.service.BaseService;
import com.htsoft.est.model.jxjy.JxjyZc;

public interface ZcService extends BaseService<JxjyZc>{

	/**
	 * 获取职称根据父亲Id
	 * @param pId
	 * @return
	 */
	public List<JxjyZc> getZcByParentId(Long pId);
}
